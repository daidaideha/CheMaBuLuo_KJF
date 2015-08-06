/**
 * @author Pro.Linyl
 * @CreateTime 2015年6月5日
 * @UpdateAuthor Pro.Linyl
 * @UpdateTime 2015年6月5日 
 */
package com.cmbl.chemabuluo_kjf.http;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cmbl.chemabuluo_kjf.R;
import com.cmbl.chemabuluo_kjf.base.FinalValue;
import com.witalk.widget.CMBLTools;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.ui.ViewInject;

/***
 * 
 * @author Pro.Linyl
 * @CreateTime 2015年6月5日
 * @UpdateAuthor Pro.Linyl
 * @UpdateTime 2015年6月5日
 * @description http请求操作类
 *
 */
public class HttpThread {
	/***
	 * 上下文对象
	 */
	private Context context;
	
	public HttpThread(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

    /***
     *
     * @author Pro.Linyl
     * @CreateTime 2015年6月5日
     * @UpdateAuthor Pro.Linyl
     *
     * @param bodyBase http请求消息类(AJAX格式,Get)
     * @param handler 返回监听handler
     */
    public void doRequestGet(final HttpBodyBase bodyBase, HttpCallBack handler) {
        if (bodyBase == null) {
            Log.e(FinalValue.Http_Tag, "the HttpBodyBase is null");
            return;
        }
        KJHttp kjh = new KJHttp();
        if (!CMBLTools.isConnection(context)) {
            ViewInject.toast(context.getString(R.string.err_net_link));
        } else {
            kjh.get(bodyBase.getUrl(), handler);
        }
    }

	/***
	 * 
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月5日
	 * @UpdateAuthor Pro.Linyl
	 *
	 * @param bodyBase http请求消息类(AJAX格式)
	 * @param handler 返回监听handler
	 */
	public void doRequest(final HttpBodyBase bodyBase, HttpCallBack handler) {
		if (bodyBase == null) {
			Log.e(FinalValue.Http_Tag, "the HttpBodyBase is null");
			return;
		}
		HttpParams params = new HttpParams();
		if (!CMBLTools.isValueEmpty(bodyBase.getToken())) {
			params.put("token", bodyBase.getToken());
		}
		if (!CMBLTools.isValueEmpty(bodyBase.getParams().toString())) {
			params.put("params", bodyBase.getParams().toString());
		}
		params.put("timestamp", bodyBase.getTimestamp() + "");
		params.put("appSign", bodyBase.getAppSign());
		params.put("appKey", bodyBase.getAppKey());
		params.put("appClient", bodyBase.getAppClient());
		params.put("appVersion", bodyBase.getAppVersion());
		params.put("versionCode", bodyBase.getVersionCode() + "");
		KJHttp kjh = new KJHttp();
		if (!CMBLTools.isConnection(context)) {
            ViewInject.toast(context.getString(R.string.err_net_link));
		} else {
			kjh.post(bodyBase.getUrl(), params, handler);
		}
	}

	/***
	 *
	 * @author Pro.Linyl
	 * @CreateTime 2015年6月5日
	 * @UpdateAuthor Pro.Linyl
	 *
	 * @param bodyBase http请求消息类(json格式)
	 * @param handler 返回监听handler
	 */
	public void doJsonRequest(final HttpBodyBase bodyBase, final Handler handler) {
		if (bodyBase == null) {
			Log.e(FinalValue.Http_Tag, "the HttpBodyBase is null");
			return;
		}
		HttpParams params = new HttpParams();
		if (!CMBLTools.isValueEmpty(bodyBase.getToken())) {
			params.put("token", bodyBase.getToken());
		}
		if (!CMBLTools.isValueEmpty(bodyBase.getParams().toString())) {
			params.put("params", bodyBase.getParams().toString());
		}
		params.put("timestamp", bodyBase.getTimestamp() + "");
		params.put("appSign", bodyBase.getAppSign());
		params.put("appKey", bodyBase.getAppKey());
		params.put("appClient", bodyBase.getAppClient());
		params.put("appVersion", bodyBase.getAppVersion());
		params.put("versionCode", bodyBase.getVersionCode() + "");
		KJHttp kjh = new KJHttp();
		if (!CMBLTools.isConnection(context)) {
            ViewInject.toast(context.getString(R.string.err_net_link));
		} else {
			kjh.jsonPost(bodyBase.getUrl(), params, getCallBack(handler, bodyBase.getAction()));
		}
	}

    /***
     * 获取http操作监听
     * @param handler 请求成功后返回监听
     * @param action http请求action
     * @return HttpCallBack http操作监听
     */
	private HttpCallBack getCallBack(final Handler handler, final String action) {
		HttpCallBack httpCallBack = new HttpCallBack() {
			@Override
			public void onPreStart() {
				super.onPreStart();
			}

			@Override
			public void onSuccess(String response) {
				super.onSuccess(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("code");
                    String message = jsonObject.getString("msg");
                    if (code == FinalValue.HTTP_SUCCESS) {
                        String data = jsonObject.getString("data");
                        Message msg = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("data", data);
                        bundle.putString("action", action);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    } else {
                        ViewInject.toast(message);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				super.onFailure(errorNo, strMsg);
                ViewInject.toast(context.getString(R.string.err_net_link));
			}

			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);
			}
		};
		return httpCallBack;
	}

}
