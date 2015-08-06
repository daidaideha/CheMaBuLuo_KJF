/**
 * @author Pro.Linyl
 * @CreateTime 2015年6月5日
 * @UpdateAuthor Pro.Linyl
 * @UpdateTime	 2015年6月5日
 */
package com.cmbl.chemabuluo_kjf.http;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.cmbl.chemabuluo_kjf.base.FinalValue;
import com.witalk.widget.CMBLTools;

import org.kymjs.kjframe.utils.KJLoger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***
 * 
 * @author Pro.Linyl
 * @CreateTime 2015年6月5日
 * @UpdateAuthor Pro.Linyl
 * @UpdateTime	 2015年6月5日
 * @description Http链接工具类
 *
 */
public class HttpTools {
	/***
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月5日
	 * @UpdateAuthor Pro.Linyl
	 * @UpdateTime 2015年6月8日（加入空值判断）
	 * @description 设置固定入参
	 *
	 * @param base http请求消息类
	 * @param context application
	 */
	public static void setFixedInfo(HttpBodyBase base, Application context) {
		if (base == null) {
			Log.e(FinalValue.Http_Tag, "the HttpBodyBase is null");
			return;
		}
		String[] versionInfo = CMBLTools.getVersion(context).split(",");
		PackageInfo ai = null;
		try {
			ai = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ai.applicationInfo.metaData == null) {
			Toast.makeText(context, "app_key为空，请在AndroidManifest.xml文件中加入你的app_key", Toast.LENGTH_LONG).show();
			return;
		}
		Object app_key = ai.applicationInfo.metaData.get("app_key");
		if (app_key == null) {
			Toast.makeText(context, "app_key为空，请在AndroidManifest.xml文件中加入你的app_key", Toast.LENGTH_LONG).show();
			return;
		}
		KJLoger.state(FinalValue.Http_Tag, "app_key = " + ai.applicationInfo.metaData.get("app_key").toString());
		Object app_secret = ai.applicationInfo.metaData.get("app_secret");
		if (app_secret == null) {
			Toast.makeText(context, "app_secret为空，请在AndroidManifest.xml文件中加入你的app_secret", Toast.LENGTH_LONG).show();
			return;
		}
		KJLoger.state(FinalValue.Http_Tag, "app_secret = " + ai.applicationInfo.metaData.get("app_secret").toString());
		long currentTimeMillis = System.currentTimeMillis() / 1000;
		base.setAppClient("android");
		base.setAppSecret(app_secret.toString());
		base.setAppKey(app_key.toString());
		base.setAppVersion(versionInfo[0]);
		base.setVersionCode(Integer.valueOf(versionInfo[1]));
		base.setTimestamp(currentTimeMillis);
	}
}
