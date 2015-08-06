package com.cmbl.chemabuluo_kjf.share;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class SinaWeiBoSetting implements IWeiboHandler.Response {
	Activity context;
	private WeiboAuth mWeibo;
	public SsoHandler ssoHandler;
	public IWeiboShareAPI weiboShareAPI;
	private TextObject textObject;

	public SinaWeiBoSetting(Activity context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.textObject = new TextObject();
	}

	public void initSina() {
		// TODO Auto-generated method stub
		mWeibo = new WeiboAuth(context, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		ssoHandler = new SsoHandler(context, mWeibo);
		ssoHandler.authorize(new AuthListener(), null);
		weiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Constants.APP_KEY);
		if (weiboShareAPI.checkEnvironment(true)) {
			weiboShareAPI.registerApp();
		}

	}
	
	/***
	 * 设置分享的内容
	 * @param text 分享的内容
	 */
	public void setShareText(String text){
		textObject.text = text;
	}

	private void shareForSina() {
		WeiboMessage weiboMessage = new WeiboMessage();
		weiboMessage.mediaObject = textObject;
		SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.message = weiboMessage;
		weiboShareAPI.sendRequest(request);
	}

	@Override
	public void onResponse(BaseResponse baseResp) {
		// TODO Auto-generated method stub
		switch (baseResp.errCode) {
		case com.sina.weibo.sdk.constant.WBConstants.ErrorCode.ERR_OK:
			showToast("分享成功");
			break;
		case com.sina.weibo.sdk.constant.WBConstants.ErrorCode.ERR_CANCEL:
			showToast("取消分享");
			break;
		case com.sina.weibo.sdk.constant.WBConstants.ErrorCode.ERR_FAIL:
			showToast(baseResp.errMsg + "分享失败");
			break;

		default:
			break;
		}
	}

	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			// String token = values.getString("access_token");
			// String expires_in = values.getString("expires_in");
			shareForSina();
		}

		@Override
		public void onCancel() {
			showToast("取消授权");
		}

		@Override
		public void onWeiboException(WeiboException e) {
			// TODO Auto-generated method stub
			showToast("授权失败：" + e.getMessage());
		}

	}

	private void showToast(String msg) {
		Toast.makeText(context, msg, 1000).show();
	}
}
