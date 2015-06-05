package com.taxiking.customer.apiservice.base;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * parameter for Http Post
 * 
 */
public class HttpRequestParams {
	private ArrayList<String> params = new ArrayList<String>();

	public String getParams() {
		StringBuilder builder = new StringBuilder();

		int index = 0;
		for (String param : params) {
			if (index > 0) {
				builder.append("&");
			}
			builder.append(param);
			index ++;
		}

		return builder.toString();
	}

	public void addParam(String field, String value) {
		params.add(String.format("%s=%s", field, URLEncoder.encode(value)));
	}

}
