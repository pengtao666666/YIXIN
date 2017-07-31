package com.kf.framework;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.kf.framework.callback.InitCallback;
import com.kf.framework.callback.UserCallback;
import com.kf.framework.exception.api.KFUserException;
import com.kf.utils.KFLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Hashtable;

/**
 * UserInstance
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
public class Useryixin implements IUser {

    private Context context;
    private Activity activity;
    private IUser adapter;
    private String loginUserId;
    private String openId;
    private String token;
    private String channel;
    private String userName;

    @Override
    public String toString() {
        return "loginUserId: " + loginUserId +
                " |openId: " + openId +
                " |token: " + token +
                " |channel: " + channel +
                " |userName: " + userName;
    }


    public Useryixin(Context context) {
        this.context = context;
        try {
            activity = (Activity) context;
        } catch (Exception e) {
            throw new KFUserException(Useryixin.class.getCanonicalName() + " need activity context");
        }
        initDeveloperInfo(SDKPluginWrapper.getDeveloperInfo());
        adapter = this;
    }

    private void initDeveloperInfo(Hashtable<String, String> developerInfo) {
        if (!AgentOfyixin.getInstance().isInited()) {
            taskOnMainThread(new InitSDKTask(developerInfo));
        }
    }

    private class InitSDKTask implements Runnable {
        Hashtable<String, String> curCPInfo;

        public InitSDKTask(Hashtable<String, String> curCPInfo) {
            this.curCPInfo = curCPInfo;
        }

        @Override
        public void run() {
            AgentOfyixin.getInstance().initSDK(context, curCPInfo, new KFSDKInitCallBack());
        }

        private class KFSDKInitCallBack extends InitCallback {
            @Override
            public void onSuccess(int code, String msg) {
                actionResult(code, msg);
                AgentOfyixin.getInstance().setmInited(true);
            }

            @Override
            public void onFail(int code, String msg) {
                actionResult(code, msg);
                AgentOfyixin.getInstance().setmInited(false);
            }
        }
    }

    @Override
    public void login() {
        // can change to other way to show fast request see class UXUtils
        if (UXUtils.isFastRequest(true, KFHelper.getAppContext(), "处理中，请稍后再试")) {
            return;
        }
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
               /* if (checkLoginError()) {
                    //FIXME B7 if game need relogin please fix it. 防止重复登录登出锁,这里整个代码可以屏蔽掉
                    throw new KFUserException("you are relogin need fix it");
                } else {
                    //FIXME B7 if single login use this  防止重复登录登出锁,这里整个代码可以屏蔽掉
                }*/
                AgentOfyixin.getInstance().userLogin(activity, new UserCallback() {
                    @Override
                    public void onSuccess(int code, String msg, JSONObject userInfo) {
                        parseUserInfo(userInfo, code, msg);
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        AgentOfyixin.getInstance().setmLogined(false);
                        actionResult(code, msg);
                    }
                });
            }

            private void parseUserInfo(JSONObject userInfo, int code, String msg) {
                if (null != userInfo) {
                    try {
                        //FIXME B7 channel login parse 这里解析失败会导致登录正确回调，却报告登录失败 调试完毕请删除
                        KFLog.d("这里解析失败会导致登录正确回调，却报告登录失败" + userInfo.toString());
                        loginUserId = userInfo.getJSONObject("data").getString("guid");
                        if (TextUtils.isEmpty(loginUserId)) {
                            throw new KFUserException("User id is empty");
                        }
                        openId = userInfo.getJSONObject("data").getString("openid");
                        token = userInfo.getJSONObject("data").getString("token");
                        channel = userInfo.getJSONObject("data").getString("cp");
                        userName = userInfo.getJSONObject("data").getString("guid");
                        AgentOfyixin.getInstance().setmLogined(true);
                        actionResult(code, msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        actionResult(code, "渠道登录成功,解析登录失败 在这个方法修复 Useryixin.parseUserInfo 一般是删除多余的解析");
                        AgentOfyixin.getInstance().setmLogined(false);
                    }
                } else {
                    throw new KFUserException("User 回调返回为空,请检查渠道的特殊性,修复逻辑");
//                    actionResult(code, msg);
//                    AgentOfyixin.getInstance().setmLogined(false);
                }
            }
        });
    }

    @Override
    public String getOpenId() {
        return openId;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public String getUserId() {
        return loginUserId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void changeAccount() {
        if (UXUtils.isFastRequest()) {
            return;
        }
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (checkLoginError()) {
                    //FIXME B changeAccount need login
                } else {
                    AgentOfyixin.getInstance().changeAccount(activity, new UserCallback() {
                        @Override
                        public void onSuccess(int code, String msg, JSONObject userInfo) {
                            parseChangeAccount(code, msg, userInfo);
                        }

                        @Override
                        public void onFail(int code, String msg) {
                            AgentOfyixin.getInstance().setmLogined(false);
                            actionResult(UserWrapper.ACTION_RET_CHANGE_ACCOUNT_FAIL, "切换用户失败");
                        }
                    });
                }
            }

            private void parseChangeAccount(int code, String msg, JSONObject userInfo) {
                if (null != userInfo) {
                    try {
                        //FIXME B7 channel login parse 这里解析失败会导致登录正确回调，却报告登录失败 调试完毕请删除
                        KFLog.d("这里解析失败会导致登录正确回调，却报告切换失败" + userInfo.toString());
                        loginUserId = userInfo.getJSONObject("data").getString("guid");
                        openId = userInfo.getJSONObject("data").getString("openid");
                        token = userInfo.getJSONObject("data").getString("token");
                        channel = userInfo.getJSONObject("data").getString("cp");
                        userName = userInfo.getJSONObject("data").getString("guid");
                        AgentOfyixin.getInstance().setmLogined(true);
                        actionResult(UserWrapper.ACTION_RET_CHANGE_ACCOUNT_SUCCESS, "切换用户成功");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        actionResult(code, "渠道登录成功,解析切换帐户失败 在这个方法修复 Useryixin.parseChangeAccount 一般是删除多余的解析");
                        AgentOfyixin.getInstance().setmLogined(false);
                    }
                } else {
                    throw new KFUserException("User 回调返回为空,请检查渠道的特殊性,修复逻辑");
//                    actionResult(code, msg);
//                    AgentOfyixin.getInstance().setmLogined(false);
                }
            }
        });
    }

    @Override
    public void logout() {
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (checkLoginError()) {
                    //FIXME B logout without login
                } else {
                    AgentOfyixin.getInstance().logout(activity, new UserCallback() {
                        @Override
                        public void onSuccess(int code, String msg, JSONObject userInfo) {
                            actionResult(code, msg);
                        }

                        @Override
                        public void onFail(int code, String msg) {
                            actionResult(code, msg);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void exit() {
        if (UXUtils.isFastRequest()) {
            return;
        }
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (checkLoginError()) {
                    //FIXME B do exit without login
                } else {
                    //FIXME B do exit with login
                }
                AgentOfyixin.getInstance().exitDialog(activity, new UserCallback() {
                    @Override
                    public void onSuccess(int code, String msg, JSONObject userInfo) {
                        AgentOfyixin.getInstance().exit();
                        actionResult(code, msg);
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        actionResult(code, msg);
                    }
                });
            }
        });
    }

    @Override
    public void userCenter() {

    }

    @Override
    public boolean isSupportFunction(String functionName) {
        Method[] methods = Useryixin.class.getMethods();
        for (Method method :
                methods) {
            if (method.getName().equals(functionName)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkKFSDKInitError() {
        if (!AgentOfyixin.getInstance().isInited()) {
            actionResult(UserWrapper.ACTION_RET_LOGIN_FAIL, "还未初始化");
            return true;
        }
        return false;
    }

    private boolean checkLoginError() {
        if (checkKFSDKInitError()) {
            return true;
        } else {
            if (!AgentOfyixin.getInstance().ismLogined()) {
                actionResult(UserWrapper.ACTION_RET_LOGIN_FAIL, "未设定登录成功，或者未登陆！");
                return true;
            } else {
                return false;
            }
        }
    }

    private void taskOnMainThread(Runnable runnable) {
        SDKPluginWrapper.runOnMainThread(runnable);
    }

    public void actionResult(int code, String msg) {
        UserWrapper.onActionResult(adapter, code, msg);
    }

    public void actionResult(int code, int msgID) {
        UserWrapper.onActionResult(adapter, code, KFHelper.getAppContext().getString(msgID));
    }

    @Override
    public void changeContext(Activity activity) {
        this.activity = activity;
        this.context = activity;
        AgentOfyixin.getInstance().changeContext(activity);
    }
}
