package com.taxiking.customer.apiservice.base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

/**
 * Http communication API
 * 
 */
public class HttpComm {
	private HttpCommListener listener;

	public HttpComm() {
	}

	public void setListener(HttpCommListener listener) {
		this.listener = listener;
	}

	public void sendRequest(String serverUrl, HttpRequestParams params) {
		CommTask task = new CommTask(serverUrl, params, listener);
		task.execute();
	}

	private static class CommTask extends AsyncTask<Object, Object, String> {

		private HttpCommListener listener;
		private HttpRequestParams httpParams;
		private String serverUrl;
		private int statusCode;

		public CommTask(String serverUrl,
				HttpRequestParams params, HttpCommListener listener) {
			this.serverUrl = serverUrl;
			this.httpParams = params;
			this.listener = listener;
		}

		@Override
		protected String doInBackground(Object... params) {
			URL connectURL = null;

			try {
				connectURL = new URL(serverUrl);
			} catch (MalformedURLException e) {
				return null;
			}

			HttpURLConnection conn = null;

			try {
				conn = (HttpURLConnection) connectURL.openConnection();

				conn.setConnectTimeout(9000);
				conn.setReadTimeout(9000);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", 
				           "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", "" + 
			               Integer.toString(httpParams.getParams().getBytes().length));
				conn.setRequestProperty("Content-Language", "en-US");  
				

//				conn.setRequestProperty("Connection","Keep-Alive");

				OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
				writer.write(httpParams.getParams());
				writer.flush();
				writer.close();

				conn.connect();
				statusCode = conn.getResponseCode();
				if (statusCode != HttpURLConnection.HTTP_OK) {
					conn.disconnect();
					return null;
				}

//				int size = conn.getContentLength();
				InputStream is = conn.getInputStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is));
				String line;
				StringBuffer response = new StringBuffer(); 
				while((line = rd.readLine()) != null) {
					response.append(line);
					response.append('\r');
				}
				rd.close();
				conn.disconnect();

				return response.toString();
				
//				InputStreamReader reader = new InputStreamReader(is);
//				char[] buffer = new char[size];
//				reader.read(buffer, 0, size);
//				reader.close();
//
//				String content = new String(buffer);
//				return content.trim();
			} catch (Exception e) {
			      e.printStackTrace();
			      if(conn != null)
			    	  conn.disconnect(); 
		    }

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				listener.onNetworkFailed(statusCode);
			} else {
				listener.onNetworkReceivedData(result);
			}
		}
	}

	public static interface HttpCommListener {
		public void onNetworkFailed(int networkStatus);
		public void onNetworkReceivedData(String data);
	}
}
