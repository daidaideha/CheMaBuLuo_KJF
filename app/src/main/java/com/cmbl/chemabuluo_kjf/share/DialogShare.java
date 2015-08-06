package com.cmbl.chemabuluo_kjf.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cmbl.chemabuluo_kjf.R;
import com.witalk.widget.CMBLTools;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


/***
 * 分享弹出框
 * 
 * @author YuLing
 * @UpdateDate 2014-11-05 16:22:32
 */
public class DialogShare extends Dialog implements
		View.OnClickListener {
	/***
	 * 取消按钮
	 */
	private Button btn_cancel;
	/***
	 * ll_share_wechat:分享到微信 ll_share_sina:分享到新浪微博 ll_share_qq:分享到QQ
	 * ll_share_wechat_f:分享到微信朋友圈 ll_share_qzone:分享到QQ空间 ll_share_rr:分享到人人网
	 */
	private LinearLayout ll_share_wechat, ll_share_sina, ll_share_qq,
			ll_share_wechat_f, ll_share_qzone, ll_share_rr;

	/***
	 * 腾讯QQ分享
	 */
	private TencentSetting tencentSetting;
	/***
	 * WeChat分享
	 * 
	 */
	private WechatSetting weiXinSetting;
	/***
	 * weibo设置
	 */
	private SinaWeiBoSetting sinasetting;
	/***
	 * 上下文对象
	 */
	private Context context;

	/***
	 * 分享实体类
	 */
	private ShareUnit unit;
	/***
	 * 分享标题
	 */
	private String title = "";

	/***
	 * 分享到微信异步
	 */
	private CreateBitampTask createBitampTask;

	public DialogShare(Context context, int theme, ShareUnit unit) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.unit = unit;
		setContentView(R.layout.dialog_share);
		
		title = unit.getTitle();

		btn_cancel = (Button) findViewById(R.id.btn_share_cancel);
		ll_share_wechat = (LinearLayout) findViewById(R.id.ll_share_wechat);
		ll_share_sina = (LinearLayout) findViewById(R.id.ll_share_sina);
		ll_share_qq = (LinearLayout) findViewById(R.id.ll_share_qq);
		ll_share_wechat_f = (LinearLayout) findViewById(R.id.ll_share_wechat_f);
		ll_share_qzone = (LinearLayout) findViewById(R.id.ll_share_qzone);
		ll_share_rr = (LinearLayout) findViewById(R.id.ll_share_rr);

		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

		ll_share_wechat.setOnClickListener(this);
		ll_share_sina.setOnClickListener(this);
		ll_share_qq.setOnClickListener(this);
		ll_share_wechat_f.setOnClickListener(this);
		ll_share_qzone.setOnClickListener(this);
		ll_share_rr.setOnClickListener(this);
	}

	/***
	 * 新浪微博响应intent回调（用在onNewIntent中）
	 * 
	 * @param intent
	 */
	public void sinaResponse(Intent intent) {
		if (sinasetting != null) {
			sinasetting.weiboShareAPI.handleWeiboResponse(intent, sinasetting);
		}
	}

	/***
	 * 新浪微博activity回调（用在onActivityResult中）
	 * 
	 * @param requestCode
	 *            响应code
	 * @param resultCode
	 *            回调code
	 * @param data
	 *            回调内容
	 */
	public void sinaCallBack(int requestCode, int resultCode, Intent data) {
		if (sinasetting != null) {
			sinasetting.ssoHandler.authorizeCallBack(requestCode, resultCode,
					data);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String text = "";
		switch (v.getId()) {
		case R.id.ll_share_wechat:
			shareToWechat(0);
			break;
		case R.id.ll_share_sina:
//			String content = Html.fromHtml(unit.getSummary()).toString();
//			if (content.length() > 30) {
//				content = content.substring(0, 30) + "...\n";
//			} else {
//				content += "\n";
//			}
//			text = context.getString(R.string.label_sina_title) + unit.getTitle() + unit.getUrl();
			sinasetting = new SinaWeiBoSetting((Activity) context);
			sinasetting.setShareText(title);
			sinasetting.initSina();
			break;
		case R.id.ll_share_qq:
			tencentSetting = new TencentSetting((Activity) context);
			tencentSetting.initTencent();
			tencentSetting
					.sharedToQQ(title, unit.getUrl(), 0, Html
							.fromHtml(unit.getSummary()).toString(), unit
							.getImageUrl());
			break;
		case R.id.ll_share_wechat_f:
			shareToWechat(1);
			break;
		case R.id.ll_share_qzone:
			tencentSetting = new TencentSetting((Activity) context);
			tencentSetting.initTencent();
			ArrayList<String> als = new ArrayList<String>(0);
			als.add(unit.getImageUrl());
			tencentSetting.sharedToQZone(title, unit.getUrl(), "",
					Html.fromHtml(unit.getSummary()).toString(), als);
			break;
		default:
			break;
		}
	}

	/***
	 * 分享到微信操作
	 * 
	 * @param type
	 *            0：表示到微信好友 1：表示到微信朋友圈
	 */
	private void shareToWechat(int type) {
		if (CMBLTools.isConnection(context)) {
			if (createBitampTask == null) {
				createBitampTask = new CreateBitampTask(type);
				createBitampTask.execute();
			}
		} else {
			CMBLTools.toastMsg(context.getString(R.string.err_net_link),
					context);
		}
	}

	/***
	 * 分享到微信异步
	 * 
	 * @author YuLing
	 * 
	 */
	private class CreateBitampTask extends AsyncTask<Void, Void, Bitmap> {
		private int type = 0;

		/***
		 * 微信分享
		 * 
		 * @param type
		 *            0：表示微信好友 1：表示微信朋友圈
		 */
		public CreateBitampTask(int type) {
			// TODO Auto-generated constructor stub
			this.type = type;
		}

		@Override
		protected Bitmap doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			InputStream inStream = null;
			final Options options = new Options();
			try {
				HttpGet httpRequest = new HttpGet(unit.getImageUrl());
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) httpclient
						.execute(httpRequest);
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
						entity);
				inStream = bufferedHttpEntity.getContent();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				options.inPurgeable = true;
				options.inInputShareable = true;
				options.inJustDecodeBounds = false;
				return BitmapFactory.decodeStream(inStream, null, options);
			} catch (SocketTimeoutException ste) {
			} catch (Exception e) {
				System.out.println(e.toString());
			} finally {
				closeInputStream(inStream);
				// if(conn != null) conn.disconnect();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			createBitampTask = null;
			if (result != null) {
				weiXinSetting = new WechatSetting((Activity) context);
				String text = Html.fromHtml(unit.getSummary()).toString();
				if (text.length() > 50) {
					text = text.substring(0, 50) + "...";
				}
				if (type == 0) {
					weiXinSetting.sendMessageToWXF(
							weiXinSetting.createWebpage(unit.getUrl(), result),
							text, title);
				} else {
					weiXinSetting.sendMessageToWXQ(
							weiXinSetting.createWebpage(unit.getUrl(), result),
							text, title);
				}
			} else {
				CMBLTools.toastMsg(context.getString(R.string.err_net_link),
						context);
			}
		}

		private void closeInputStream(InputStream in) {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }

    //设置窗口显示
    public void windowDeploy(int x, int y){
        Window window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        window.setBackgroundDrawableResource(R.color.vifrification); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
        wl.x = x; //x小于0左移，大于0右移
        wl.y = y; //y小于0上移，大于0下移
//            wl.alpha = 0.6f; //设置透明度
        wl.gravity = Gravity.BOTTOM; //设置重力
        window.setAttributes(wl);
    }
}
