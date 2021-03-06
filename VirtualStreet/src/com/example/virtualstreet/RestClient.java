package com.example.virtualstreet;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RestClient {
	private static final String BASE_URL = "http://virtualsback-uniquegames.rhcloud.com:80/api/";
	
	private static AsyncHttpClient client = new AsyncHttpClient();
	
	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
		client.get(getAbsoluteUrl(url),params, responseHandler);
	}
	
	public static void post (String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}
	
	public static void put (String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
		client.put(getAbsoluteUrl(url), params, responseHandler);
	}
	
	private static String getAbsoluteUrl(String relativeUrl){
		return BASE_URL + relativeUrl;
	}
}
