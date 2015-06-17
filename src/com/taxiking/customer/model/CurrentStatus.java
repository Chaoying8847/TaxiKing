package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CurrentStatus {

	public static final String TAG = "JSON Parse Err In User";
	
	public String state;
	public String transaction_id;
	public String order_time;
	public String order_address;
	public double price;
	public String driver_name;
	public String driver_phone;
	public String estimate_end;
	public String error;
	public String result;
	
	public static CurrentStatus fromJSON(JSONObject obj) {
		CurrentStatus status = new CurrentStatus();
	
		try {
			status.state			= obj.getString("state");
			status.transaction_id	= obj.getString("transaction_id");
			status.order_time		= obj.getString("order_time");
			status.order_address	= obj.getString("order_address");
			status.price			= obj.getDouble("price");
			status.driver_name		= obj.getString("driver_name");
			status.driver_phone		= obj.getString("driver_phone");
			status.estimate_end		= obj.getString("estimate_end");
			status.error			= "";
			status.result			= "success";
		} catch (JSONException e) {
			Log.d(TAG, e.toString());
		}
	
		return status;
	}
	
	public static CurrentStatus fromJSONError(JSONObject obj) {
		CurrentStatus status = new CurrentStatus();
	
		try {
			status.state			= "";
			status.transaction_id	= "";
			status.order_time		= "";
			status.order_address	= "";
			status.price			= 0;
			status.driver_name		= "";
			status.driver_phone		= "";
			status.estimate_end		= "";
			status.error			= obj.getString("error");	
			status.result			= obj.getString("result");				
		} catch (JSONException e) {
			Log.d(TAG, e.toString());
		}
	
		return status;
	}


}
