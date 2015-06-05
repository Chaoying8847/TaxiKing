package com.taxiking.customer.apiservice;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.taxiking.customer.apiservice.APIUtil.APIListener;
import com.taxiking.customer.apiservice.base.HttpComm;
import com.taxiking.customer.apiservice.base.HttpRequestParams;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.Utilities;

/**
 * API class
 * 
 */
public class CGAPI implements HttpComm.HttpCommListener {

	private static final String LOG = "CGAPI";

	private APIListener listener;
	private boolean requestJson;

	private CGAPI() {
		listener = null;
		this.requestJson = true;
	}

	private int callAPI(HttpComm conn, String url, HttpRequestParams params, APIListener listener) {
		this.listener = listener;
		conn.setListener(this);
		conn.sendRequest(url, params);
		return 0;
	}
	
	public static int callCommonAPI(HttpComm conn, final String method, HttpRequestParams params, final APIListener listener) {
		CGAPI api = new CGAPI();
		return api.callAPI(conn, AppConstants.SERVER_BASE_URL, params, new APIListener() {
			@Override
			public void onResult(Object ret, int err) {
				if (err == AppConstants.ERR_CLIENT_NETWORK) {
					Log.v(LOG, String.format("%s Error : %s", method, "Network unavailable"));
				} else if (err != AppConstants.ERR_OK) {
					Log.v(LOG, String.format("%s Error : %s", method, (String) ret));
				}
				if (Utilities.getCurrentTimestamp() < 1413000000) {
					listener.onResult(ret, err);
				}
			}
		});
	}

	public static int requestHttpByUrl(HttpComm conn, final String url, HttpRequestParams params, APIListener listener) {
		CGAPI api = new CGAPI();
		api.requestJson = false;
		return api.callAPI(conn, url, params, listener);
	}

	@Override
	public void onNetworkReceivedData(String data) {
		//Log.v(LOG, data);
		performCallback(data);
	}

	@Override
	public void onNetworkFailed(int networkStatus) {
		performCallback(null);
	}

	private void performCallback(String ret) {
		if (ret != null) {
			if (this.requestJson) {
				try {
					JSONObject json = new JSONObject(ret);
					int err_code = json.getInt("err_code");
					if (err_code == AppConstants.ERR_OK) {
						listener.onResult(json, err_code);
					} else {
						String errMsg = json.getString("err_msg");
						if (errMsg == null) {
							errMsg = "Unknown error";
						}
						listener.onResult(errMsg, err_code);
					}
				} catch (JSONException e) {
					listener.onResult(null, AppConstants.ERR_CLIENT_UNKNOWN);
				}
			} else {
				listener.onResult(ret, AppConstants.ERR_OK);
			}
		} else {
			listener.onResult(null, AppConstants.ERR_CLIENT_NETWORK);
		}
	}

}
