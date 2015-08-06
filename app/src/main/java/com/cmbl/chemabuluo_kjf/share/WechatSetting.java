package com.cmbl.chemabuluo_kjf.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WechatSetting {
	Activity activity;
	private String APP_ID = "wx0d3353514fb99643";
	private IWXAPI wxapi;
	private static final int THUMB_SIZE = 150;
	Bitmap thumbBmp;

	public WechatSetting(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		initWeiXin();
	}

	private void initWeiXin() {
		wxapi = WXAPIFactory.createWXAPI(activity, APP_ID, true);
		wxapi.registerApp(APP_ID);
	}

	/***
	 * 创建一个图片形式的IMediaObject（用bitmap做来源）
	 * 
	 * @param bmp
	 *            图片来源
	 * @return imageObject
	 */
	public WXImageObject createImageFormBitmap(Bitmap bmp) {
		WXImageObject imageObject = new WXImageObject(bmp);
		thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
		bmp.recycle();
		return imageObject;
	}

	/***
	 * 创建一个图片形式的IMediaObject（用网络图片地址做来源）
	 * 
	 * @param Url
	 *            图片来源
	 * @return imageObject
	 */
	public WXImageObject createImageFormUrl(String Url) {
		WXImageObject imageObject = new WXImageObject();
		imageObject.imageUrl = Url;
		try {
			Bitmap bmp = BitmapFactory.decodeStream(new URL(Url).openStream());
			thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE,
					true);
			bmp.recycle();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageObject;
	}

	/***
	 * 创建一个图片形式的IMediaObject（用本地图片路径做来源）
	 * 
	 * @param Path
	 *            图片来源
	 * @return imageObject
	 */
	public WXImageObject createImageFormPath(String Path) {
		WXImageObject imageObject = new WXImageObject();
		imageObject.imagePath = Path;
		Bitmap bmp = BitmapFactory.decodeFile(Path);
		thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
		bmp.recycle();
		return imageObject;
	}

	/***
	 * 创建一个文字形式的IMediaObject
	 * 
	 * @param text
	 *            文字内容
	 * @return textObject
	 */
	public WXTextObject createText(String text) {
		WXTextObject textObject = new WXTextObject();
		textObject.text = text;
		return textObject;
	}

	public WXWebpageObject createWebpage(String url, Bitmap bmp) {
		WXWebpageObject webpageObject = new WXWebpageObject();
		webpageObject.webpageUrl = url;

		thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
		// bmp.recycle();
		return webpageObject;
	}

	/***
	 * 分享到微信好友
	 * 
	 * @param des
	 *            用于文字形式内容（分享的内容）
	 * @param mediaObject
	 *            分享的内容形式
	 */
	@SuppressWarnings("static-access")
	public void sendMessageToWXF(WXMediaMessage.IMediaObject mediaObject, String des,
			String title) {
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = mediaObject;
		msg.description = des;
		msg.title = title;
		if (thumbBmp != null) {
			msg.thumbData = Bitmap2Bytes(thumbBmp);
		}
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = req.WXSceneSession;
		wxapi.sendReq(req);
	}

	/***
	 * 分享到微信朋友圈
	 * 
	 * @param title
	 *            用于文字形式内容（分享的内容）
	 * @param mediaObject
	 *            分享的内容形式
	 */
	@SuppressWarnings("static-access")
	public void sendMessageToWXQ(WXMediaMessage.IMediaObject mediaObject, String des,
			String title) {
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = mediaObject;
		msg.description = des;
		msg.title = title;
		if (thumbBmp != null) {
			msg.thumbData = Bitmap2Bytes(thumbBmp);
		}
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = req.WXSceneTimeline;
		wxapi.sendReq(req);
	}

	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}
}
