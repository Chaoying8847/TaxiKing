package com.taxiking.customer.apiservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import android.util.Log;

public class HttpApi {
	public static class METHOD {
		public static final int GET = 0x1000;
		public static final int POST = 0x1001;
	}

	private static DefaultHttpClient httpClient;
	private static CookieStore cookieStore = null;

	public static String call(String url, int method, List<NameValuePair> params, Map<String, String> header) {
		if (method != METHOD.GET && method != METHOD.POST)
			return null;

		try {
			InputStream is = callToInputStream(url, method, params, header);

			// get response
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/*
	 * send multipart form image 
	 */
	public static String callWithMultipart(String url, 
			Map<String, String> header, Map<String, String> params,
			String imgKey, String imgFilePath) {
		InputStream is = null;

		try {
			// http client
			if (httpClient == null)
				httpClient = new DefaultHttpClient();

			// Create a local instance of cookie store
			if (cookieStore == null)
				cookieStore = new BasicCookieStore();

			// Create local HTTP context
			HttpContext localContext = new BasicHttpContext();
			// Bind custom cookie store to the local context
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			// request method is POST
			HttpPost httpPost = new HttpPost(url);
//			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//			if (header != null) {
//				for (Map.Entry<String, String> entry : header.entrySet()) {
//					httpPost.addHeader(entry.getKey(), entry.getValue());
//				}
//			}
//			if (params != null) {
//				for (Map.Entry<String, String> entry : params.entrySet()) {
//					reqEntity.addPart(entry.getKey(), new StringBody(entry.getValue()));
//				}
//			}
//			if (imgFilePath != null) {
//				File file = new File(imgFilePath);
//				FileBody filebody = new FileBody(file);
//				reqEntity.addPart(imgKey, filebody);
//			}
//			
//			httpPost.setEntity(reqEntity);

			HttpResponse httpResponse = httpClient.execute(httpPost, localContext);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
						
			// get response
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static InputStream callToInputStream(String url, int method, List<NameValuePair> params, Map<String, String> header) {
		InputStream is = null;
		
		// add api key and secret
		if (params == null) {
			params = new ArrayList<NameValuePair>();
		}

		try {
			// http client
			if (httpClient == null)
				httpClient = new DefaultHttpClient();

			// Create a local instance of cookie store
			if (cookieStore == null)
				cookieStore = new BasicCookieStore();

			// Create local HTTP context
			HttpContext localContext = new BasicHttpContext();
			// Bind custom cookie store to the local context
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			// check for request method
			if (method == METHOD.GET) {
				// request method is GET
				if (params != null) {
					String paramString = URLEncodedUtils.format(params, "utf-8");
					if (url.contains("?"))
						url += "&" + paramString;
					else
						url += "?" + paramString;
				}
				HttpGet httpGet = new HttpGet(url);
				if (header != null) {
					for (Map.Entry<String, String> entry : header.entrySet()) {
						httpGet.addHeader(entry.getKey(), entry.getValue());
					}
				}

				HttpResponse httpResponse = httpClient.execute(httpGet, localContext);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();

			} else if(method == METHOD.POST) {
				// request method is POST
				HttpPost httpPost = new HttpPost(url);
				if (params != null) {
					httpPost.setEntity(new UrlEncodedFormEntity(params));
					httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				}
				if (header != null) {
					for (Map.Entry<String, String> entry : header.entrySet()) {
						httpPost.addHeader(entry.getKey(), entry.getValue());
					}
				}

				HttpResponse httpResponse = httpClient.execute(httpPost, localContext);
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return is;
	}
	
	public static JSONObject callToJson(String url, int method, List<NameValuePair> params, Map<String, String> header) {
		String response = HttpApi.call(url, method, params, header);
		if (response == null)
			return null;

		// try parse the string to a JSON object
		try {
			// return JSON String
			return new JSONObject(response);

		} catch (Exception e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		return null;
	}
}