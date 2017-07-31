/**
 *
 */
package com.wyd.hero.yqlfc.cb1.mzw.yixin.yxapi;

import android.content.Context;
import im.yixin.gamesdk.api.YXGameApiFactory;
import im.yixin.sdk.api.YXAPIBaseBroadcastReceiver;
import im.yixin.sdk.channel.YXMessageProtocol;

/**
 * @author yixinopen@188.com
 */
public class AppRegister extends YXAPIBaseBroadcastReceiver {

    /* (non-Javadoc)
     * @see im.yixin.sdk.api.YXAPIBaseBroadcastReceiver#getAppId()
     */
    @Override
    protected String getAppId(Context context) {
        return YXGameApiFactory.get().getGameId();
    }

    /***********************
     * 易信启动后，会广播消息给第三方APP，第三方APP注册之后，系统会调用此函数。
     * 当第三方APP需要在易信启动后，完成相关工作，可以实现此函数。
     * 此函数默认实现为空。
     *
     * @param protocol
     */
    protected void onAfterYixinStart(final YXMessageProtocol protocol) {
    }


}
