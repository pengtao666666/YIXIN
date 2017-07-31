package com.kf.framework;

import android.app.Activity;
import android.content.Context;

import com.kf.framework.exception.api.KFStatisticException;
import com.kf.utils.KFLog;
import com.kf.utils.ToastBuilder;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * StatisticInstance
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
public class Statisticyixin implements IStatistic {

    private Context context;
    private Activity activity;
    private IStatistic adapter;

    public Statisticyixin(Context context) {
        this.context = context;
        try {
            this.activity = (Activity) context;
        } catch (Exception e) {
            throw new KFStatisticException(Statisticyixin.class.getCanonicalName() + " need Activity context");
        }
        this.adapter = this;
    }

    @Override
    public void recordLogin(final HashMap<String, Object> params) {
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (!checkKFSDKInitError()) {
                    AgentOfyixin.getInstance().recordLogin(params);
                }
            }
        });
    }

    @Override
    public void recordRoleUp(final HashMap<String, Object> params) {
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (!checkKFSDKInitError()) {
                    AgentOfyixin.getInstance().recordRoleUp(params);
                }
            }
        });
    }

    @Override
    public void recordRoleCreate(final HashMap<String, Object> params) {
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (!checkKFSDKInitError()) {
                    AgentOfyixin.getInstance().recordRoleCreate(params);
                }
            }
        });
    }

    @Override
    public void recordBtnClicked(final HashMap<String, Object> params) {
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (!checkKFSDKInitError()) {
                    AgentOfyixin.getInstance().recordBtnClicked(params);
                }
            }
        });
    }

    @Override
    public void recordPay(final HashMap<String, Object> params) {
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (!checkKFSDKInitError()) {
                    AgentOfyixin.getInstance().recordPay(params);
                }
            }
        });
    }

    @Override
    public void recordServerRoleInfo(final HashMap<String, Object> params) {
        taskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (!checkKFSDKInitError()) {
                    AgentOfyixin.getInstance().recordServerRoleInfo(params);
                }
            }
        });
    }

    @Override
    public boolean isSupportFunction(String functionName) {
        Method[] methods = Statisticyixin.class.getMethods();
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
            ToastBuilder.make(context, "还未初始化", ToastBuilder.DEFAULT_TOAST_SINGLE);
            return true;
        }
        return false;
    }

    private void taskOnMainThread(Runnable runnable) {
        SDKPluginWrapper.runOnMainThread(runnable);
    }
}
