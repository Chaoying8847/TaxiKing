package com.taxiking.customer.apiservice.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.util.Log;

/**
 * class for Http Data Download
 * 
 */
public class HttpDownload {

	private static final String TAG = "HttpDownload";

	protected static final int TIMEOUT_CONNECTION = 3000;
	protected static final int TIMEOUT_READ = 3000;

	public static InputStream downloadFromURL(String url) {
		HttpURLConnection conn = null;
		InputStream inputStream = null;

		try {
			conn = GetConnection(url);
			if (conn == null)
				return null;

			if (conn.getResponseCode() != 200) {
				conn.disconnect();
				return null;
			}

			int contentSize = conn.getContentLength();

			if (contentSize <= 0) {
				conn.disconnect();
				return null;
			}

			inputStream = conn.getInputStream();

			byte[] buffer = new byte[contentSize];
			byte[] readBuffer = new byte[8 * 1024];

			int position = 0;
			int readLength = 0;
			for (; true; position += readLength) {
				readLength = inputStream.read(readBuffer, 0, readBuffer.length);

				if (readLength <= 0 || (position + readLength) > contentSize) {
					break;
				}

				System.arraycopy(readBuffer, 0, buffer, position, readLength);
			}

			if (position != contentSize) {
				inputStream.close();
				conn.disconnect();
				return null;
			}

			inputStream.close();
			conn.disconnect();

			return new ByteArrayInputStream(buffer);

		} catch (IOException e) {
			Log.e(TAG, "downloadFromURL()", e);

			try {
				if (inputStream != null)
					inputStream.close();
			} catch (Exception e1) {
			}
			inputStream = null;

			if(conn != null)
				conn.disconnect();
		}

		return null;
	}

	protected static HttpURLConnection GetConnection(String actionUrl) throws ProtocolException {
		URL connectURL = null;

		try {
			connectURL = new URL(actionUrl);
		} catch (MalformedURLException e) {
			return null;
		}

		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)connectURL.openConnection();
		} catch (IOException e) {
			return null;
		}

		connection.setConnectTimeout(TIMEOUT_CONNECTION);
		connection.setReadTimeout(TIMEOUT_READ);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false); 
		connection.setRequestMethod("POST"); 
		connection.setRequestProperty("Connection","Keep-Alive"); 

		return connection;
	}
}
