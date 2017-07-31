/**
 * 
 */
package com.wyd.hero.yqlfc.cb1.mzw.yixin.yxapi;

import im.yixin.gamesdk.api.BaseYXGameEntryActivity;
import im.yixin.gamesdk.api.YXGameApi;
import im.yixin.gamesdk.api.YXGameApiFactory;
import im.yixin.gamesdk.api.YXGameCallbackListener;
import im.yixin.gamesdk.api.YXGameStatusCode;
import im.yixin.gamesdk.meta.GameAccount;
import im.yixin.sdk.api.BaseReq;
import im.yixin.sdk.api.BaseResp;
import im.yixin.sdk.api.SendAuthToYX;
import im.yixin.sdk.api.SendMessageToYX;
import im.yixin.sdk.api.ShowYXMessageFromYX;
import im.yixin.sdk.util.YixinConstants;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.kf.framework.AgentOfyixin;
import com.kf.framework.UserWrapper;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author yixinopen@188.com
 * 
 */
public class YXEntryActivity extends BaseYXGameEntryActivity {

	private static final String TAG = "KuaifaYiXin.YXEntryActivity";


	


	/*******************
	 * 返回第三方app根据app id创建的IYXAPI，
	 * 
	 * @return 易信Api接口实现
	 */
	protected YXGameApi getIYXGameApi() {
		return YXGameApiFactory.get();
	}

	/**
	 * 易信调用调用时的触发函数
	 */
	@Override
	public void onResp(BaseResp resp) {
//		this.showUI();
		Log.i(TAG, "onResp called: errCode=" + resp.errCode + ",errStr=" + resp.errStr + ",transaction="
				+ resp.transaction);
		switch (resp.getType()) {
		case YixinConstants.RESP_SEND_MESSAGE_TYPE:
			SendMessageToYX.Resp resp1 = (SendMessageToYX.Resp) resp;
			switch (resp1.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				Toast.makeText(YXEntryActivity.this, "分享成功", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_COMM:
				Toast.makeText(YXEntryActivity.this, "分享失败", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				Toast.makeText(YXEntryActivity.this, "用户取消", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_SENT_FAILED:
				Toast.makeText(YXEntryActivity.this, "发送失败", Toast.LENGTH_LONG).show();
				break;
			}
			break;
		case YixinConstants.RESP_SEND_AUTH_TYPE:
			SendAuthToYX.Resp resp2 = (SendAuthToYX.Resp) resp;

			
			
			
			switch (resp2.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				this.getToken(resp2.code);
				break;
			case BaseResp.ErrCode.ERR_COMM:
				AgentOfyixin.getInstance().loginLisenner.onFail(UserWrapper.ACTION_RET_LOGIN_FAIL, "登录失败");
				YXEntryActivity.this.finish();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				AgentOfyixin.getInstance().loginLisenner.onFail(UserWrapper.ACTION_RET_LOGIN_FAIL,"用户取消");
				YXEntryActivity.this.finish();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				AgentOfyixin.getInstance().loginLisenner.onFail(UserWrapper.ACTION_RET_LOGIN_FAIL,"用户拒绝");

				YXEntryActivity.this.finish();
				break;
			}
		}

	}

	/*****************
	 * 获取token
	 * 
	 * @param code
	 */
	private void getToken(String code) {



		  getIYXGameApi().fetchToken(this, code, new YXGameCallbackListener<Void>() {
	            @Override
	            public void callback(int status, Void aVoid) {




	                if (status == YXGameStatusCode.SUCCESS) {
	                	try {
							AgentOfyixin.getInstance().api.getAccountInfo(new YXGameCallbackListener<GameAccount>() {
								@Override
								public void callback(int status, GameAccount account) {

									if (status == YXGameStatusCode.SUCCESS) {
										JSONObject jsonObject = new JSONObject();
										try {
											jsonObject.put("userid", account.getAccountId());
											jsonObject.put("uname", account.getNick());
										} catch (JSONException e) {
											e.printStackTrace();
										}

										UserWrapper.getAccessToken(account.getAccountId(), jsonObject, AgentOfyixin.getInstance().loginLisenner);
										YXEntryActivity.this.finish();
									} else {
										AgentOfyixin.getInstance().loginLisenner.onFail(UserWrapper.ACTION_RET_LOGIN_FAIL,"登录失败");
										YXEntryActivity.this.finish();
									}
								}
							});
						} catch (Exception e) {
							e.printStackTrace();
							AgentOfyixin.getInstance().loginLisenner.onFail(UserWrapper.ACTION_RET_LOGIN_FAIL, "登录失败");
		    					YXEntryActivity.this.finish();
						}


	                } else {
						AgentOfyixin.getInstance().loginLisenner.onFail(UserWrapper.ACTION_RET_LOGIN_FAIL, "登录失败");
	    				YXEntryActivity.this.finish();
	                }
	            }
	        });
	}

	/**
	 * 易信调用调用时的触发函数
	 */
	@Override
	public void onReq(BaseReq req) {
		Log.i(TAG, "onReq called: transaction=" + req.transaction);
		switch (req.getType()) {
		case YixinConstants.RESP_SEND_MESSAGE_TYPE:
			SendMessageToYX.Req req1 = (SendMessageToYX.Req) req;
			break;
		case YixinConstants.REQ_ON_CLICK_MESSAGE_TYPE:
			ShowYXMessageFromYX.Req req2 = (ShowYXMessageFromYX.Req) req;
			Handler handler = new Handler();
			handler.post(new Runnable() {
				public void run() {
					// 启动主页面
					Intent intent = YXEntryActivity.this.getApplicationContext().getPackageManager()
							.getLaunchIntentForPackage(YXEntryActivity.this.getPackageName());
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					YXEntryActivity.this.startActivity(intent);
					YXEntryActivity.this.finish();
				}
			});

			break;
		}
	}

	
	
	



	
	
	
}
