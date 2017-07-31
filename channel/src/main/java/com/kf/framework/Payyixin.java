package com.kf.framework;

import android.app.Activity;
import android.content.Context;
import android.util.Base64;

import com.kf.framework.callback.OrderInfoCallback;
import com.kf.framework.callback.PayCallback;
import com.kf.framework.exception.api.KFPayException;
import com.kf.framework.java.KFSDKStatistic;
import com.kf.framework.java.KFSDKUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * PayInstance
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
 * Created by "sinlov" on 16/4/27.
 */
public class Payyixin implements IPay {

    private Context context;
    private Activity activity;
    private IPay adapter;
    private String orderId;
    private int orderStatus;
    private String orderStatusMessage;

    @Override
    public String toString() {
        return "orderId: " + orderId +
                " |orderStatus: " + orderStatus +
                " |orderStatusMessage: " + orderStatusMessage;
    }

    public Payyixin(Context context) {
        this.context = context;
        try {
            activity = (Activity) context;
        } catch (Exception e) {
            throw new KFPayException(Payyixin.class.getCanonicalName() + " need activity context");
        }
        this.adapter = this;
    }

    @Override
    public void pay(final HashMap<String, Object> params) {
        if (UXUtils.isFastRequest()) {
            return;
        }
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (!checkPayWithLoginError()) {
                    AgentOfyixin.getInstance().pay(activity, params, new PayCallback() {
                        @Override
                        public void onSuccess(int code, String msg, HashMap<String, Object> params) {
                            if (null == params) {
                                throw new KFPayException("params is null");
                            } else {
                                if (null != params.get(Params.Pay.KEY_ORDER_NO).toString()) {
                                    orderId = params.get(Params.Pay.KEY_ORDER_NO).toString();
                                    actionResult(code, msg);
                                    HashMap<String, String> paramsRecord = new HashMap<String, String>();
                                    // CP 标识
                                    paramsRecord.put(Params.Statistic.KEY_ROLE_USERMARK, KFSDKUser.getInstance().getUserId() + "@" + KFSDKUser.getInstance().getChannel());
                                    // 支付金额
                                    paramsRecord.put(Params.Statistic.KEY_ROLE_AMOUNT, params.get(Params.Pay.KEY_AMOUNT).toString());
                                    // 角色唯一标识, 只使用0
                                    paramsRecord.put(Params.Statistic.KEY_ROLE_MARK, "0");
                                    // 支付商品描述,或者商品名称
                                    String product_Desc = params.get(Params.Pay.KEY_PRODUCT_NAME).toString();
                                    product_Desc = letParamsBase64(product_Desc);
                                    paramsRecord.put(Params.Statistic.KEY_ROLE_PRODUCT_DESC, params.get(Params.Pay.KEY_PRODUCT_NAME) != null ? product_Desc : "productName");
                                    // 支付方式，例如支付宝 - 非必须
                                    paramsRecord.put(Params.Statistic.KEY_PAY_NAME, "default");
                                    // 服务器id - 非必须
                                    paramsRecord.put(Params.Statistic.KEY_ROLE_SERVER_ID, params.get(Params.Pay.KEY_SERVER_ID) != null ? params.get(Params.Pay.KEY_SERVER_ID).toString() : "1");
                                    // 用户标识 - 非必须
                                    String userName = KFSDKUser.getInstance().getUserName();
                                    userName = letParamsBase64(userName);
                                    paramsRecord.put(Params.Statistic.KEY_ROLE_USERID, userName);
                                    // 订单号 - 非必须
                                    paramsRecord.put(Params.Statistic.KEY_ROLE_ORDERNUMBER, params.get(Params.Pay.KEY_ORDER_NO) != null ? params.get(Params.Pay.KEY_ORDER_NO).toString() : "1");
                                    // 角色等级 - 非必须
                                    paramsRecord.put(Params.Statistic.KEY_UPGRADE, params.get(Params.Pay.KEY_ROLE_LEVEL) != null ? params.get(Params.Pay.KEY_ROLE_LEVEL).toString() : "1");
                                    KFSDKStatistic.getInstance().recordPay(paramsRecord);
                                } else {
                                    new KFPayException("Order Number is empty").printStackTrace();
                                }
                            }
                        }

                        private String letParamsBase64(String param) {
                            return new String(Base64.encode(param.getBytes(), Base64.DEFAULT));
                        }

                        @Override
                        public void onFail(int code, String msg, HashMap<String, Object> params) {
                            if (null == params) {
                                new KFPayException("pay params is Null").printStackTrace();
                            } else {
                                if (null != params.get(Params.Pay.KEY_ORDER_NO).toString()) {
                                    orderId = params.get(Params.Pay.KEY_ORDER_NO).toString();
                                }
                                actionResult(code, msg);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getOrderInfo(final HashMap<String, Object> params) {
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (!checkPayWithLoginError()) {
                    PayWrapper.getOrderInfo(params, new OrderInfoCallback() {
                        @Override
                        public void success(int code, String msg, JSONObject orderInfo) {
                            parseOrderInfo(orderInfo, msg);
                            actionResult(code, msg);
                        }

                        @Override
                        public void fail(int code, String msg) {
                            actionResult(code, msg);
                        }
                    });
                }
            }

            private void parseOrderInfo(JSONObject orderInfo, String msg) {
                try {
                    orderStatus = orderInfo.getJSONObject("data").getInt("status");
                    orderStatusMessage = orderInfo.getJSONObject("data").getString("desc");
                } catch (JSONException e) {
                    e.printStackTrace();
                    actionResult(PayWrapper.PAYRESULT_ORDER_INFO_FAIL, msg);
                }
            }
        });
    }

    @Override
    public String getOrderNo() {
        return orderId;
    }

    @Override
    public int getOrderStatus() {
        return orderStatus;
    }

    @Override
    public String getOrderStatusMessage() {
        return orderStatusMessage;
    }

    private boolean checkKFSDKInitError() {
        if (!AgentOfyixin.getInstance().isInited()) {
            actionResult(PayWrapper.PAYRESULT_INIT_FAIL, "还未初始化");
            return true;
        }
        return false;
    }

    /**
     * is not login is true
     *
     * @return boolean
     */
    private boolean checkPayWithLoginError() {
        if (checkKFSDKInitError()) {
            return true;
        } else {
            if (!AgentOfyixin.getInstance().ismLogined()) {
                actionResult(PayWrapper.PAYRESULT_ORDER_INFO_FAIL, "未登陆!");
                return true;
            } else {
                return false;
            }
        }
    }

    private void actionResult(int code, String msg) {
        PayWrapper.onPayResult(adapter, code, msg);
    }

    private void actionResult(int code, int msgID) {
        PayWrapper.onPayResult(adapter, code, KFHelper.getAppContext().getString(msgID));
    }

    @Override
    public boolean isSupportFunction(String functionName) {
        Method[] methods = Payyixin.class.getMethods();
        for (Method method :
                methods) {
            if (method.getName().equals(functionName)) {
                return true;
            }
        }
        return false;
    }

    private void taskOnMainThread(Runnable runnable) {
        SDKPluginWrapper.runOnMainThread(runnable);
    }
}
