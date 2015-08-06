package com.cmbl.chemabuluo_kjf.share;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class TencentSetting {
	Activity activity;
	private Tencent mTencent;
	private String APP_ID = "101126714";

	public TencentSetting(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

	/***
	 * 分享到QQ
	 * 
	 * @param Title
	 *            信息的标题，必填
	 * @param URL
	 *            信息的链接地址，必填
	 * @param Summary
	 *            信息的内容，可选
	 * @param Type
	 *            信息的类型，必填 例如：图文信息：Tencent.SHARE_TO_QQ_SUMMARY
	 * @param ImageUrl
	 *            分享图片的URL或者本地路径,可选
	 */
	public void sharedToQQ(String Title, String URL, int Type, String Summary,
			String ImageUrl) {
		Bundle params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
				QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, Title);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, URL);
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, Summary);
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ImageUrl);
		mTencent.shareToQQ(activity, params, new BaseUiListener());
	}

	/***
	 * 分享到QZone
	 * 
	 * @param Title
	 *            信息的标题，必填
	 * @param URL
	 *            信息的链接地址，必填
	 * @param Summary
	 *            信息的内容，可选
	 * @param Type
	 *            信息的类型，必填 例如：图文信息：Tencent.SHARE_TO_QQ_SUMMARY
	 * @param ImageUrl
	 *            分享图片的URL或者本地路径,可选
	 */
	public void sharedToQZone(String Title, String URL, String Type,
			String Summary, ArrayList<String> ImageUrl) {
		Bundle params = new Bundle();
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, Title);
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, URL);
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
				QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, Summary);
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, ImageUrl);
		mTencent.shareToQzone(activity, params, new BaseUiListener());
	}

	public void initTencent() {
		// TODO Auto-generated method stub
		mTencent = Tencent.createInstance(APP_ID,
				activity.getApplicationContext());

	}

	private class BaseUiListener implements IUiListener {
		@SuppressWarnings("unused")
		public void onComplete(JSONObject response) {
			doComplete(response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			showToast("code:" + e.errorCode + ", msg:" + e.errorMessage
					+ ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			showToast("");
		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub

		}
	}

	private void showToast(String msg) {
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
	}
}
