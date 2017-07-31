package com.kf.framework;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Base64;
import android.util.Log;

import com.android.volleyplus.Response;
import com.android.volleyplus.VolleyError;
import com.kf.framework.callback.InitCallback;
import com.kf.framework.callback.OrderCallback;
import com.kf.framework.callback.PayCallback;
import com.kf.framework.callback.UserCallback;
import com.kf.framework.exception.KFInitializationException;
import com.kf.framework.util.KFNetwork;
import com.kf.framework.volley.SDKRequest;
import com.kf.framework.volley.SDKRequestInstance;
import com.kf.utils.KFLog;
import com.kf.widget.dialog.DialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import im.yixin.gamesdk.api.YXGameApi;
import im.yixin.gamesdk.api.YXGameApiFactory;
import im.yixin.gamesdk.api.YXGameCallbackListener;
import im.yixin.gamesdk.api.YXGameStatusCode;
import im.yixin.gamesdk.exception.YXGameListenerException;
import im.yixin.gamesdk.meta.GameAccount;
import im.yixin.paysdk.api.YXPayApi;
import im.yixin.paysdk.api.YXPayConstants;
import im.yixin.paysdk.api.YXPayDelegate;
import im.yixin.paysdk.api.YXPayResultCode;
import im.yixin.paysdk.api.YXTrade;
import im.yixin.sdk.api.SendAuthToYX;
import im.yixin.sdk.util.StringUtil;

/**
 * Agent of Channel
 * <pre>
 *     sinlov
 *
 *     /\__/\
 *    /`    '\
 *  ≈≈≈ 0  0 ≈≈≈ Hello world!
 *    \  --  /
 *   /        \
 *  /          \
 * |            |
 *  \  ||  ||  /
 *   \_oo__oo_/≡≡≡≡≡≡≡≡o
 *
 * </pre>
 * Created by sinlov on 16/4/25.
 */
public class AgentOfyixin extends ChannelBaseAggent {

    private static AgentOfyixin instance = null;
    protected String channelSDKVersion="3.1.0";
    private YXGameCallbackListener<Void> yxGameCallbackListener;
    public YXGameApi api;
    private String gameId;
    private String gameSecret;
    private String userID;
    private String userNickName;
    public UserCallback loginLisenner;
    private boolean isActive;

    public static AgentOfyixin getInstance() {
        if (null == instance) {
            instance = new AgentOfyixin();
        }
        return instance;
    }

    @Override
    public void initSDK(Context ctx, Hashtable<String, String> cpInfo, final InitCallback initCallback) {
        if (mInited) {
            return;
        }
        if (null != channelSDKVersion) {
            SDKPluginWrapper.addDeveloperInfo(Params.DeveloperInfo.KEY_CHANNEL_SDK_VERSION, channelSDKVersion);// 这个函数可以设置键值到 developer.properties 中
        } else {
            new KFInitializationException("You are not setting channelSDKVersion key").printStackTrace();
            mInited = false; // 这个标志位用于确认是否初始化成功，有大作用！不要无脑使用
        }
        mActivity = (Activity) ctx; // 当前类的所有Activity都使用这个,在某些游戏接入的时候使用

        yxGameCallbackListener = new YXGameCallbackListener<Void>() {

            @Override
            public void callback(int status, Void aVoid) {

                if (status == YXGameStatusCode.INIT_SUCCESS) {
                    try {
                        UserWrapper.init(mActivity, initCallback);

                    } catch (final Exception e) {
                        e.printStackTrace();
                        initCallback.onFail(UserWrapper.ACTION_RET_INIT_FAIL, "初始化失败");
                    }
                } else if (status == YXGameStatusCode.INIT_ERROR) {
                    initCallback.onFail(UserWrapper.ACTION_RET_INIT_FAIL, "渠道初始化失败");
                } else if (status == YXGameStatusCode.LOGIN_SUCCESS) {
                    //用户登陆成功会返回该code，通login，fetchToken接口中的callback是并列关系，接口callback处理了，这里就可以忽略，2选1

                } else if (status == YXGameStatusCode.ACCOUNT_CHANGE) {
                    //该次登陆的用户与之前缓存的用户信息不是同一个用户，会返回该code

                } else if (status == YXGameStatusCode.LOGOUT) {
                    //悬浮窗内退出账户会返回该code

                }
            }
        };

        api = YXGameApiFactory.init(mActivity, yxGameCallbackListener);
        api.registerGame();
        gameId = api.getGameId();
        gameSecret = api.getGameSecret();
//        UserWrapper.init(mActivity, initCallback);
        //生命周期,一定要记住要接啊
        SDKPluginWrapper.setActivityCallback(new IActivityCallback() {
            @Override
            public void onPause() {
                UserWrapper.onPause(mActivity);
            }

            @Override
            public void onResume() {
                UserWrapper.onResume(mActivity);
                if (api == null) {
                    api = YXGameApiFactory.get();
                }
                if(!isActive) {
                    isActive = true;
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            api.onGameStart();
                        }
                    });
                }
                api.showBubble(mActivity);
            }

            @Override
            public void onRestart() {

            }

            @Override
            public void onBackPressed() {

            }

            @Override
            public void onNewIntent(Intent intent) {

            }

            @Override
            public void onStop() {
                UserWrapper.onStop(mActivity);
                isActive = isAppOnForeground();
                if (api == null) {
                    api = YXGameApiFactory.get();
                }
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        api.onGameEnd();
                    }
                });
                    api.destroyBubble(mActivity);

            }

            @Override
            public void onDestroy() {

            }

            @Override
            public void onActivityResult(int i, int i1, Intent intent) {

            }

            @Override
            public void onConfigurationChanged(Configuration configuration) {

            }
        });
    }


    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) mActivity.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = mActivity.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
    @Override
    public void userLogin(Activity activity, final UserCallback listener) {
        loginLisenner =listener;
        SendAuthToYX.Req req = new SendAuthToYX.Req();
        // 使用web授权时候state的回复(如果用户未安装易信，sdk会自动打开webview，暂不支持)
        req.state = "asdfsdaf";
        // 使用客户端授权时候的单次调用回复
        req.transaction = String.valueOf(System.currentTimeMillis());
        // 游戏的高级授权，让用户选择是否关注后台注册的公众号
        req.scope = "follow_public_account";
        // web授权回调页 (如果用户未安装易信，sdk会自动打开webview，暂不支持)
        req.redirectUrl = "https://open.yixin.im/resource/oauth2_callback.html";

        try {
            //自动登录完成会从这里回调，第一次输入账号登录回调在YXEntryActivity.java中
            api.login(mActivity, req, new YXGameCallbackListener<Void>() {

                @Override
                public void callback(int status, Void aVoid) {
                    if(status == YXGameStatusCode.SUCCESS){

                        try {
                            api.getAccountInfo(new YXGameCallbackListener<GameAccount>() {

                                @Override
                                public void callback(int status, GameAccount account) {
                                    if (status == YXGameStatusCode.SUCCESS) {

                                        userID =account.getAccountId();
                                        userNickName =account.getNick();
                                        //TODO
                                        System.out.println("用户唯一id：！！！！！！" + account.getAccountId());
                                        JSONObject jsonObject=new JSONObject();
                                        try {
                                            jsonObject.put("userid",userID);
                                            jsonObject.put("uname",userNickName);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        UserWrapper.getAccessToken(userID, jsonObject, listener);

                                    }else {
                                        listener.onFail(UserWrapper.ACTION_RET_LOGIN_FAIL,"渠道登录失败");
                                    }

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            });
        } catch (YXGameListenerException e) {
            e.printStackTrace();
        }



        //result at UserWrapper.verifyUser(userObj, listener, new VerifyUserCallback(){}) 如果需要验证调用 UserWrapper.verifyUser 参数打印后传入需要的
        //call at listener.onFail(UserWrapper.ACTION_RET_LOGIN_FAIL,"登陆失败"); 不要忘了传登录失败
    }

    @Override
    public void logout(Activity activity, final UserCallback listener) {
        //listener.onSuccess(UserWrapper.ACTION_RET_LOGOUT_SUCCESS,"注销成功!",null);
        if (api.isLogin()) {
            api.logout();
        }
        listener.onFail(UserWrapper.ACTION_RET_LOGOUT_SUCCESS,"注销成功");
        mLogined = false; // 这个标志位用于确认是否已经登陆，有大作用！不要无脑使用
    }

    @Override
    public void changeAccount(Activity activity, UserCallback listener) {
        mLogined = false;
        userLogin(activity, listener);
    }

    @Override
    public void exitDialog(final Activity activity, final UserCallback listener) {
        DialogUtil.showExitDialog(activity, listener);
    }

    @Override
    public void exit() {
        mLogined = false;
    }

    @Override
    public void pay(Activity activity, final HashMap<String, Object> params, final PayCallback listener) {
        String order_no = String.valueOf(params.get("order_no"));
        Log.e("KF_log","order_no="+order_no);

        PayWrapper.getOrderId(params, new OrderCallback() {
            @Override
            public void getOrderSuccess(int code, final HashMap<String, Object> params) {

                try {
                    for (Map.Entry m:
                         params.entrySet()) {
                        KFLog.d("key: " + m.getKey() + " |value: " + m.getValue().toString());
                    }
                    int amount = Integer.parseInt(params.get(Params.Pay.KEY_AMOUNT).toString());
                    String orderId = params.get(Params.Pay.KEY_ORDER_NO).toString();
                    String roleName = checkStringParams(params, Params.Pay.KEY_ROLE_NAME, "roleName");
                    String productName = checkStringParams(params, Params.Pay.KEY_PRODUCT_NAME, "productName");
                    String roleId = checkStringParams(params, Params.Pay.KEY_ROLE_ID, "1");
                    String productId = checkStringParams(params, Params.Pay.KEY_PRODUCT_ID, "1");
                    String serverId = checkStringParams(params, Params.Pay.KEY_SERVER_ID, "1");


                    HashMap<String, Object> yixinparams = new HashMap<String, Object>();
                    yixinparams.put("order_no", orderId);
                    //由于易信后台不允许goodsName是中文，所以这么改为传productId
                    yixinparams.put("product_name", new String(Base64.encode(String.valueOf(productId).getBytes(),Base64.DEFAULT)));
                    yixinparams.put("v",new String(Base64.encode(String.valueOf(YXPayConstants.YX_PAY_SDK_VERSION).getBytes(),Base64.DEFAULT)));
                    yixinparams.put("access_token", new String(Base64.encode(String.valueOf(api.getToken()).getBytes(), Base64.DEFAULT)));


                    // 这里开始设置渠道支付,特殊渠道使用 SDKRequestInstance.postReq 来获取渠道订单后支付,别忘了设置回调
                    SDKRequest sdkRequest = SDKRequestInstance.postReq(":Volley:Tag:String", "http://z.haojieru.com/order/yixinorder/", yixinparams, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            String var2 = jsonObject.toString();
                            KFLog.d("kf_response:GetOrderIdResponse:" + var2);
                            try {
                                byte[] trade_serialid=Base64.decode(jsonObject.getJSONObject("data").getString("trade_serialid"), Base64.DEFAULT);
                                byte[] pay_url=Base64.decode(jsonObject.getJSONObject("data").getString("pay_url"), Base64.DEFAULT);
                                String serialid = new String(trade_serialid);
                                String url = new String(pay_url);
                                KFLog.d("KF_log","serialid=" + serialid);
                                KFLog.d("KF_log","url=" + url);
                                final YXTrade trade = new YXTrade();
                                trade.setId(serialid);
                                trade.setGateUrl(url);


                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new YXPayApi(mActivity, new YXPayDelegate() {
                                            @Override
                                            public void onTradeComplete(int resultCode) {
                                                switch (resultCode) {
                                                    case YXPayResultCode.SUCCESS:
                                                        mActivity.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                listener.onSuccess(PayWrapper.PAYRESULT_SUCCESS, "支付成功",params);
                                                            }
                                                        });


                                                        break;
                                                    case YXPayResultCode.USER_CANCEL:
                                                        mActivity.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                listener.onFail(PayWrapper.PAYRESULT_FAIL, "用户取消支付",params);
                                                            }
                                                        });

                                                        break;

                                                    default:
                                                        mActivity.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                listener.onFail(PayWrapper.PAYRESULT_FAIL, "支付失败",params);
                                                            }
                                                        });

                                                        break;
                                                }
                                            }
                                        }).pay(trade);
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });

                    KFNetwork.addRequest(sdkRequest);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    listener.onFail(PayWrapper.PAYRESULT_FAIL, "支付失败 支付金额错误!");
                }
            }

            @Override
            public void getOrderFail(int code, String msg) {
                listener.onFail(PayWrapper.PAYRESULT_FAIL, "支付失败 code: " + code + " |msg: " + msg);
            }
        });


    }

    /**
     * 用于支付参数检查,设置默认值使用
     *
     * @param params     参数
     * @param key        参数key
     * @param defaultStr 默认字符串
     * @return {@link String}
     */
    private String checkStringParams(HashMap<String, Object> params, String key, String defaultStr) {
        Object obj = params.get(key);
        if (null == obj) {
            return defaultStr;
        } else {
            String s = obj.toString();
            if (s.equals("")) {
                return defaultStr;
            } else {
                return s;
            }
        }
    }

    @Override
    public void userCenter(Activity activity) {

    }

    @Override
    public void recordLogin(HashMap<String, Object> params) {
        StatisticWrapper.recordLogin(params);
    }

    @Override
    public void recordRoleUp(HashMap<String, Object> params) {
        StatisticWrapper.recordRoleUp(params);
    }

    @Override
    public void recordRoleCreate(HashMap<String, Object> params) {
        StatisticWrapper.recordRoleCreate(params);
    }

    @Override
    public void recordBtnClicked(HashMap<String, Object> params) {
        StatisticWrapper.recordBtnClicked(params);
    }

    @Override
    public void recordPay(HashMap<String, Object> params) {
        StatisticWrapper.recordPay(params);
    }

    @Override
    public void recordServerRoleInfo(HashMap<String, Object> params) {
        StatisticWrapper.recordServerRoleInfo(params);
        // record server by yourself
    }

    private boolean checkSDKInit() {
        return isInited();
    }

    private boolean checkLogin() {
        return checkSDKInit() && ismLogined();
    }
}
