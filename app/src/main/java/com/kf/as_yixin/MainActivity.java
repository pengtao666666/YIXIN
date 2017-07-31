package com.kf.as_yixin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kf.framework.KFHelper;
import com.kf.framework.KFSDKListener;
import com.kf.framework.Params;
import com.kf.framework.PayWrapper;
import com.kf.framework.SDKPluginWrapper;
import com.kf.framework.UserWrapper;
import com.kf.framework.exception.KFAPIException;
import com.kf.framework.java.KFSDK;
import com.kf.framework.java.KFSDKPay;
import com.kf.framework.java.KFSDKStatistic;
import com.kf.framework.java.KFSDKUser;
import com.kf.utils.KFLog;
import com.kf.utils.ToastBuilder;
import com.wyd.hero.yqlfc.cb1.mzw.yixin.R;

import java.util.HashMap;

public class MainActivity extends Activity implements View.OnClickListener {

    Button btnMainLogin;
    Button btnMainLogout;
    Button btnMainSwitchAccount;
    Button btnMainExit;
    Button btnMainPay;
    Button btnMainGetOrderInfo;
    Button btnMainRecordLogin;
    Button btnMainRecordRoleUp;
    Button btnMainRecordRoleCreate;
    Button btnMainRecordClicked;
    Button btnMainRecordServerInfo;
    TextView tvMainResult;
    TextView tvMainBaseInfo;
    String waitingTestStr = "just testing please wait...";
    String sendStatisticFinish = "发送模拟统计完成";
    String testServerID = "1";
    String testServerName = "s1";
    String testRoleID = "2051";
    String testRoleName = "角色名称2051";
    String testRoleLevel = "9527";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);
        initView();
        initKFSDK();
        refreshBaseInfo();
    }

    private void refreshBaseInfo() {
        tvMainBaseInfo.setText(getDemoBaseInfo());
    }

    private void initView() {
        btnMainLogin = bindButtonViewById(R.id.btn_main_login);
        btnMainLogout = bindButtonViewById(R.id.btn_main_logout);
        btnMainSwitchAccount = bindButtonViewById(R.id.btn_main_switch_account);
        btnMainExit = bindButtonViewById(R.id.btn_main_exit);
        btnMainPay = bindButtonViewById(R.id.btn_main_pay);
        btnMainGetOrderInfo = bindButtonViewById(R.id.btn_main_get_order_info);
        btnMainRecordLogin = bindButtonViewById(R.id.btn_main_record_login);
        btnMainRecordRoleUp = bindButtonViewById(R.id.btn_main_record_role_up);
        btnMainRecordRoleCreate = bindButtonViewById(R.id.btn_main_record_role_create);
        btnMainRecordClicked = bindButtonViewById(R.id.btn_main_get_device_code);
        btnMainRecordServerInfo = bindButtonViewById(R.id.btn_main_record_server_info);
        tvMainResult = (TextView) findViewById(R.id.tv_main_result);
        tvMainBaseInfo = (TextView) findViewById(R.id.tv_main_base_info);
    }

    private Button bindButtonViewById(int id) {
        Button viewById = (Button) findViewById(id);
        viewById.setOnClickListener(this);
        return viewById;
    }

    private String getDemoBaseInfo() {
        StringBuilder sb = new StringBuilder();
        String appName = KFHelper.getAPPName();
        String packageName = KFHelper.getPackageName();
        sb.append("基础信息");
        sb.append("\n应用名称 ");
        sb.append(appName);
        sb.append(" 测试包名 ");
        sb.append(packageName);
        sb.append("\n");
        if (SDKPluginWrapper.getDeveloperInfo().get("debugMode").equals("0")) {
            sb.append("开启SDK日志");
        } else {
            sb.append("关闭SDK日志");
        }
        return sb.toString();
    }

    private void initKFSDK() {
        KFSDK.getInstance().init(this);
        KFSDKUser.getInstance().setListener(new KFSDKListener() {
            @Override
            public void onCallBack(int code, String msg) {
                printUserCallBackAtTextViewResult(code, "KFSDKUser " + msg);
                switch (code) {
                    case UserWrapper.ACTION_RET_LOGIN_FAIL:
                        //login fail job
                        break;
                    case UserWrapper.ACTION_RET_LOGIN_SUCCESS:
                        HashMap<String, String> params = new HashMap<>();
                        //用户标识：只用添加用户ID即可
                        params.put(Params.Statistic.KEY_ROLE_USERMARK, KFSDKUser.getInstance().getUserId() + "@" + KFSDKUser.getInstance().getChannel());
                        // 用户类型：0为临时账户，1为注册用户，2为第三方用户
                        params.put(Params.Statistic.KEY_ROLE_USERTYPE, "1");
                        // 服务器ID
                        params.put(Params.Statistic.KEY_ROLE_SERVER_ID, testServerID);
                        // 角色ID
                        params.put(Params.Statistic.KEY_ROLE_ID, testRoleID);
                        // 账户id
                        params.put(Params.Statistic.KEY_ROLE_USERID, testRoleID);
                        // 角色名称
                        String roleName64 = new String(Base64.encode(testRoleName.getBytes(), Base64.DEFAULT));
                        params.put(Params.Statistic.KEY_ROLE_NAME, roleName64);
                        // 角色等级
                        params.put(Params.Statistic.KEY_ROLE_GRADE, testRoleLevel);
                        // 服务器名称
                        params.put(Params.Statistic.KEY_ROLE_SERVER_NAME, testServerName);
                        // 账户account
                        params.put(Params.Statistic.KEY_ROLE_USENICK, "account");
                        KFSDKStatistic.getInstance().recordLogin(params);
                        HashMap<String, String> srInfo = new HashMap<>();
                        // cp标示
                        srInfo.put(Params.Statistic.KEY_ROLE_USERMARK, KFSDKUser.getInstance().userId + "@" + KFSDKUser.getInstance().getChannel());
                        // 角色id
                        srInfo.put(Params.Statistic.KEY_ROLE_ID, testRoleID);
                        // 角色等级
                        srInfo.put(Params.Statistic.KEY_ROLE_LEVEL, testRoleLevel);
                        // 服务器ID
                        srInfo.put(Params.Statistic.KEY_ROLE_SERVER_ID, testServerID);
                        // 角色昵称
                        srInfo.put(Params.Statistic.KEY_ROLE_NAME, testRoleName);
                        // 服务器名称
                        srInfo.put(Params.Statistic.KEY_ROLE_SERVER_NAME, testServerName);
                        // 角色所在帮派或工会名称
                        srInfo.put(Params.Statistic.KEY_ROLE_PARTY_NAME, "角色所在帮派或工会名称");
                        // VIP等级
                        srInfo.put(Params.Statistic.KEY_ROLE_VIP_LEVEL, "VIP等级");
                        KFSDKStatistic.getInstance().recordServerRoleInfo(srInfo);
                        printLoginFullInfo(code, msg);
                        break;
                    case UserWrapper.ACTION_RET_INIT_SUCCESS:
                        break;
                    case UserWrapper.ACTION_RET_EXIT_CANCEL:
                        break;
                    case UserWrapper.ACTION_RET_EXIT_SUCCESS:
                        break;
                    case UserWrapper.ACTION_RET_LOGOUT_FAIL:
                        break;
                    case UserWrapper.ACTION_RET_LOGOUT_SUCCESS:
                        break;
                    case UserWrapper.ACTION_RET_LOGOUT_CANCEL:
                        break;
                    case UserWrapper.ACTION_RET_INIT_FAIL:
                        break;
                    case UserWrapper.ACTION_RET_CHANGE_ACCOUNT_SUCCESS:
                        printLoginFullInfo(code, msg);
                        break;
                    case UserWrapper.ACTION_RET_CHANGE_ACCOUNT_FAIL:
                        break;
                    default:
                        throw new KFAPIException("登陆回调异常 Check you setting of " +
                                "KFSDKUser.getInstance().setListener code" +
                                "|this code is " + code + " |message is " + msg);
                }
            }

            private void printLoginFullInfo(int code, String msg) {
                StringBuilder sb = new StringBuilder();
                String openId = KFSDKUser.getInstance().getOpenId();
                sb.append(checkCallBack2String("openId", openId));
                String token = KFSDKUser.getInstance().getToken();
                sb.append(checkCallBack2String("token", token));
                String channel = KFSDKUser.getInstance().getChannel();
                sb.append(checkCallBack2String("channel", channel));
                String userId = KFSDKUser.getInstance().getUserId();
                sb.append(checkCallBack2String("userId", userId));
                String userName = KFSDKUser.getInstance().getUserName();
                sb.append(checkCallBack2String("userName", userName));
                String orderNo = KFSDKPay.getInstance().getOrderNo();
                sb.append(checkCallBack2String("orderNo", orderNo));
                printUserCallBackAtTextViewResult(code, msg + "\n登录详细信息: \n" + sb.toString());
            }

            private String checkCallBack2String(String key, String params) {
                String linePass = "\n--------------\n";
                if (null == params) {
                    return key + " is null. " + linePass;
                } else if (params.equals("")) {
                    return key + " is \"\" ." + linePass;
                } else {
                    return key + ": " + params + linePass;
                }
            }
        });
        KFSDKPay.getInstance().setListener(new KFSDKListener() {
            @Override
            public void onCallBack(int code, String msg) {
                printPayBack(code, msg);
                switch (code) {
                    case PayWrapper.PAYRESULT_SUCCESS:
                        break;
                    case PayWrapper.PAYRESULT_FAIL:
                        break;
                    case PayWrapper.PAYRESULT_CANCEL:
                        break;
                    case PayWrapper.PAYRESULT_ORDER_INFO_SUCCESS:
                        if (KFSDKPay.getInstance().getOrderStatus() == 0) {
                            ToastBuilder.make(KFHelper.getAppContext(),
                                    KFSDKPay.getInstance().getOrderStatusMessage(), ToastBuilder.DEFAULT_TOAST_SINGLE);
                        } else {
                            ToastBuilder.make(KFHelper.getAppContext(),
                                    KFSDKPay.getInstance().getOrderStatusMessage(), ToastBuilder.DEFAULT_TOAST_SINGLE);
                        }
                        printPayBack(code, msg + "\n" + KFSDKPay.getInstance().getOrderStatusMessage());
                        break;
                    case PayWrapper.PAYRESULT_ORDER_INFO_FAIL:
                        break;
                    default:
                        ToastBuilder.make(KFHelper.getAppContext(),
                                "订单支付默认回复" +
                                        " code" + code +
                                        " msg: " + msg,
                                ToastBuilder.DEFAULT_TOAST_SINGLE);
                        break;
                }
            }

            private void printPayBack(int code, String msg) {
                String orderNo = KFSDKPay.getInstance().getOrderNo();
                KFLog.d(orderNo);
                if (!TextUtils.isEmpty(orderNo)) {
                    printPayCallBackAtTextViewResult(code, "KFSDKPay " + msg + "\norderNo: " + orderNo);
                } else {
                    printPayCallBackAtTextViewResult(code, "KFSDKPay " + msg);
                }
            }
        });
    }


    public void onClick(View view) {
        HashMap<String, Object> paramsObj = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();
        tvMainResult.setText(waitingTestStr);
        switch (view.getId()) {
            case R.id.btn_main_login:
                KFSDKUser.getInstance().login();
                //test login print at tvMainResult.setText();
                break;
            case R.id.btn_main_logout:
                KFSDKUser.getInstance().logout();
                //test logout print at tvMainResult.setText();
                break;
            case R.id.btn_main_switch_account:
                KFSDKUser.getInstance().changeAccout();
                //test switch account print at tvMainResult.setText();
                break;
            case R.id.btn_main_exit:
                //test exit print at tvMainResult.setText();
                KFSDKUser.getInstance().exit();
                break;
            case R.id.btn_main_pay:
                //test pay print at tvMainResult.setText();
               // paramsObj.put(Params.Pay.KEY_ORDER_NO, "");//购买商品金额
                paramsObj.put(Params.Pay.KEY_AMOUNT, "1");//购买商品金额
                paramsObj.put(Params.Pay.KEY_SERVER_ID, testServerID);//服务器ID
                paramsObj.put(Params.Pay.KEY_PRODUCT_ID, 648);//购买商品的商品ID
                paramsObj.put(Params.Pay.KEY_PRODUCT_NUM, "1");//购买商品的数量
                paramsObj.put(Params.Pay.KEY_GAMEEXTEND, "415");//额外参数,没有传""
                paramsObj.put(Params.Pay.KEY_NOTIFY_URL, "");//应用方提供的支付结果通知uri,没有先传任意测试字符串
                paramsObj.put(Params.Pay.KEY_CONIN_NAME, "金币");
                paramsObj.put(Params.Pay.KEY_RATE, "10");
                paramsObj.put(Params.Pay.KEY_ROLE_ID, testRoleID);
                paramsObj.put(Params.Pay.KEY_ROLE_NAME, testRoleName);
                paramsObj.put(Params.Pay.KEY_ROLE_LEVEL, testRoleLevel);
                paramsObj.put(Params.Pay.KEY_PRODUCT_NAME, "商品名字");
                paramsObj.put(Params.Pay.KEY_SERVER_NAME, testServerName);//服务器ID
                KFSDKPay.getInstance().pay(paramsObj);
                break;
            case R.id.btn_main_get_order_info:
                //test order info print at tvMainResult.setText();
                if (!paramsObj.isEmpty()) {
                    paramsObj.clear();
                }
                String orderNo = KFSDKPay.getInstance().getOrderNo();
                if (!TextUtils.isEmpty(orderNo)) {
                    paramsObj.put(Params.Pay.KEY_PAY_ORDER_ID, orderNo);
                    KFSDKPay.getInstance().getOrderInfo(paramsObj);
                } else {
                    ToastBuilder.make(getApplicationContext(), "获取订单号错误，请确认有订单后再尝试", ToastBuilder.DEFAULT_TOAST_SINGLE);
                }
                break;
            case R.id.btn_main_get_device_code:
                // must update com.kf.framework:kfsdk 1.0.30+
                String deviceCode = KFHelper.getDeviceCode();
                if (!TextUtils.isEmpty(deviceCode)) {
                    String deviceStr = "当前的设备的唯一标识是:\n" + deviceCode;
                    tvMainResult.setText(deviceStr);
                } else {
                    ToastBuilder.make(getApplicationContext(), "获取设备信息错误", ToastBuilder.DEFAULT_TOAST_SINGLE);
                }

                break;
            case R.id.btn_main_record_login:
                //test record login print at tvMainResult.setText();
                //用户标识：只用添加用户ID即可
                params.put(Params.Statistic.KEY_ROLE_USERMARK, KFSDKUser.getInstance().userId + "@" + KFSDKUser.getInstance().getChannel());
                // 角色ID
                params.put(Params.Statistic.KEY_ROLE_ID, testRoleID);
                // 服务器ID
                params.put(Params.Statistic.KEY_ROLE_SERVER_ID, testServerID);
                // 服务器名称
                params.put(Params.Statistic.KEY_ROLE_SERVER_NAME, testServerName);
                // 角色名称
                params.put(Params.Statistic.KEY_ROLE_NAME, testRoleName);
                // 角色等级
                params.put(Params.Statistic.KEY_ROLE_GRADE, testRoleLevel);
                // 账户account - 非必须
                params.put(Params.Statistic.KEY_ROLE_USENICK, "account");
                // 用户类型：0为临时账户，1为注册用户，2为第三方用户  - 非必须
                params.put(Params.Statistic.KEY_ROLE_USERTYPE, "1");
                KFSDKStatistic.getInstance().recordLogin(params);
                String rlRes = sendStatisticFinish + " 模拟登陆";
                tvMainResult.setText(rlRes);
                break;
            case R.id.btn_main_record_role_up:
                //test record role up print at tvMainResult.setText();
                params.put(Params.Statistic.KEY_ROLE_USERMARK, KFSDKUser.getInstance().userId + "@" + KFSDKUser.getInstance().getChannel());
                // 角色ID
                params.put(Params.Statistic.KEY_ROLE_ID, testRoleID);
                // 角色等级
                params.put(Params.Statistic.KEY_ROLE_LEVEL, testRoleLevel);
                //角色账号 - 非必须
                params.put(Params.Statistic.KEY_ROLE_USERID, testRoleID);
                // 服务器ID - 非必须
                params.put(Params.Statistic.KEY_ROLE_SERVER_ID, testServerID);
                // 角色昵称 - 非必须
                params.put(Params.Statistic.KEY_ROLE_NAME, testRoleName);
                //角色等级 - 非必须
                params.put(Params.Statistic.KEY_ROLE_GRADE, testRoleLevel);
                // 服务器名称 - 非必须
                params.put(Params.Statistic.KEY_ROLE_SERVER_NAME, testServerName);
                KFSDKStatistic.getInstance().recordRoleUp(params);
                String ruRes = sendStatisticFinish + " 模拟角色升级";
                tvMainResult.setText(ruRes);
                break;
            case R.id.btn_main_record_role_create:
                //test role create print at tvMainResult.setText();
                params.put(Params.Statistic.KEY_ROLE_USERMARK, KFSDKUser.getInstance().userId + "@" + KFSDKUser.getInstance().getChannel());
                // 服务器id
                params.put(Params.Statistic.KEY_ROLE_SERVER_ID, testServerID);
                //角色id
                params.put(Params.Statistic.KEY_ROLE_ID, testRoleID);
                // 角色账号 - 非必须
                params.put(Params.Statistic.KEY_ROLE_USERID, testRoleID);
                // 角色等级 - 非必须
                params.put(Params.Statistic.KEY_ROLE_LEVEL, testRoleLevel);
                // 角色昵称 - 非必须
                params.put(Params.Statistic.KEY_ROLE_NAME, testRoleName);
                // 服务器名称 - 非必须
                params.put(Params.Statistic.KEY_ROLE_SERVER_NAME, testServerName);
                KFSDKStatistic.getInstance().recordRoleCreate(params);
                String rcRes = sendStatisticFinish + " 模拟创建角色";
                tvMainResult.setText(rcRes);
                break;
            case R.id.btn_main_record_server_info:
                //test record server info print at tvMainResult.setText();
                // cp标示
                params.put(Params.Statistic.KEY_ROLE_USERMARK, KFSDKUser.getInstance().userId + "@" + KFSDKUser.getInstance().getChannel());
                // 角色id
                params.put(Params.Statistic.KEY_ROLE_ID, testRoleID);
                // 角色等级
                params.put(Params.Statistic.KEY_ROLE_LEVEL, testRoleLevel);
                // 服务器ID
                params.put(Params.Statistic.KEY_ROLE_SERVER_ID, testServerID);
                // 角色昵称 - 非必须
                params.put(Params.Statistic.KEY_ROLE_NAME, testRoleName);
                // 服务器名称 - 非必须
                params.put(Params.Statistic.KEY_ROLE_SERVER_NAME, testServerName);
                // 角色所在帮派或工会名称 - 非必须
                params.put(Params.Statistic.KEY_ROLE_PARTY_NAME, "角色所在帮派或工会名称");
                // VIP等级 - 非必须
                params.put(Params.Statistic.KEY_ROLE_VIP_LEVEL, "1");
                KFSDKStatistic.getInstance().recordServerRoleInfo(params);
                String ssTest = sendStatisticFinish + " 自定义统计信息, 根据渠道设置";
                tvMainResult.setText(ssTest);
                break;
        }
    }

    @Override
    protected void onResume() {
        SDKPluginWrapper.onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SDKPluginWrapper.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        SDKPluginWrapper.onPause();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        SDKPluginWrapper.onRestart();
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        SDKPluginWrapper.onRestart();
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        SDKPluginWrapper.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        SDKPluginWrapper.onNewIntent(intent);
        super.onNewIntent(intent);
    }

    @Override
    protected void onStop() {
        SDKPluginWrapper.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        SDKPluginWrapper.onDestroy();
        super.onDestroy();
    }

    private void printUserCallBackAtTextViewResult(int code, String msg) {
        String codeAtoS;
        switch (code) {
            case UserWrapper.ACTION_RET_INIT_SUCCESS:
                codeAtoS = "UserWrapper.ACTION_RET_INIT_SUCCESS";
                break;
            case UserWrapper.ACTION_RET_INIT_FAIL:
                codeAtoS = "UserWrapper.ACTION_RET_INIT_FAIL";
                break;
            case UserWrapper.ACTION_RET_LOGIN_SUCCESS:
                codeAtoS = "UserWrapper.ACTION_RET_LOGIN_SUCCESS";
                break;
            case UserWrapper.ACTION_RET_LOGIN_FAIL:
                codeAtoS = "UserWrapper.ACTION_RET_LOGIN_FAIL";
                break;
            case UserWrapper.ACTION_RET_LOGOUT_SUCCESS:
                codeAtoS = "UserWrapper.ACTION_RET_LOGOUT_SUCCESS";
                break;
            case UserWrapper.ACTION_RET_LOGOUT_FAIL:
                codeAtoS = "UserWrapper.ACTION_RET_LOGOUT_FAIL";
                break;
            case UserWrapper.ACTION_RET_LOGOUT_CANCEL:
                codeAtoS = "UserWrapper.ACTION_RET_LOGOUT_CANCEL";
                break;
            case UserWrapper.ACTION_RET_CHANGE_ACCOUNT_SUCCESS:
                codeAtoS = "UserWrapper.ACTION_RET_CHANGE_ACCOUNT_SUCCESS";
                break;
            case UserWrapper.ACTION_RET_CHANGE_ACCOUNT_FAIL:
                codeAtoS = "UserWrapper.ACTION_RET_CHANGE_ACCOUNT_FAIL";
                break;
            case UserWrapper.ACTION_RET_EXIT_CANCEL:
                codeAtoS = "UserWrapper.ACTION_RET_EXIT_CANCEL";
                break;
            case UserWrapper.ACTION_RET_EXIT_SUCCESS:
                codeAtoS = "UserWrapper.ACTION_RET_EXIT_SUCCESS";
                break;
            case UserWrapper.ACTION_RET_EXIT_FAIL:
                codeAtoS = "UserWrapper.ACTION_RET_EXIT_FAIL";
                break;
            default:
                codeAtoS = "登录回调错误返回码设置,不通过测试";
                break;
        }
        String resStr = "返回码:  " + code + "\n2S: " + codeAtoS + "\n回调消息: " + msg;
        tvMainResult.setText(resStr);
    }

    private void printPayCallBackAtTextViewResult(int code, String msg) {
        String codeAtoS;
        switch (code) {
            case PayWrapper.PAYRESULT_SUCCESS:
                codeAtoS = "PayWrapper.PAYRESULT_SUCCESS";
                break;
            case PayWrapper.PAYRESULT_FAIL:
                codeAtoS = "PayWrapper.PAYRESULT_FAIL";
                break;
            case PayWrapper.PAYRESULT_CANCEL:
                codeAtoS = "PayWrapper.PAYRESULT_CANCEL";
                break;
            case PayWrapper.PAYRESULT_NETWORK_ERROR:
                codeAtoS = "PayWrapper.PAYRESULT_NETWORK_ERROR";
                break;
            case PayWrapper.PAYRESULT_PRODUCTIONINFOR_INCOMPLETE:
                codeAtoS = "PayWrapper.PAYRESULT_PRODUCTIONINFOR_INCOMPLETE";
                break;
            case PayWrapper.PAYRESULT_INIT_SUCCESS:
                codeAtoS = "PayWrapper.PAYRESULT_INIT_SUCCESS";
                break;
            case PayWrapper.PAYRESULT_INIT_FAIL:
                codeAtoS = "PayWrapper.PAYRESULT_INIT_FAIL";
                break;
            case PayWrapper.PAYRESULT_NOW_PAYING:
                codeAtoS = "PayWrapper.PAYRESULT_NOW_PAYING";
                break;
            case PayWrapper.PAYRESULT_RECHARGE_SUCCESS:
                codeAtoS = "PayWrapper.PAYRESULT_RECHARGE_SUCCESS";
                break;
            case PayWrapper.PAYRESULT_ORDERNO_SUCCESS:
                codeAtoS = "PayWrapper.PAYRESULT_ORDERNO_SUCCESS";
                break;
            case PayWrapper.PAYRESULT_ORDERNO_FAIL:
                codeAtoS = "PayWrapper.PAYRESULT_ORDERNO_FAIL";
                break;
            case PayWrapper.PAYRESULT_ORDER_INFO_FAIL:
                codeAtoS = "PayWrapper.PAYRESULT_ORDER_INFO_FAIL";
                break;
            case PayWrapper.PAYRESULT_ORDER_INFO_SUCCESS:
                codeAtoS = "PayWrapper.PAYRESULT_ORDER_INFO_SUCCESS";
                break;
            case PayWrapper.PAYRESULT_PAYEXTENSION:
                codeAtoS = "PayWrapper.PAYRESULT_ORDER_INFO_SUCCESS";
                break;
            default:
                codeAtoS = "支付回调错误返回码设置,不通过测试";
                break;
        }
        String resStr = "返回码:  " + code + "\n|C2S: " + codeAtoS + "\n回调消息: " + msg;
        tvMainResult.setText(resStr);
    }
}
