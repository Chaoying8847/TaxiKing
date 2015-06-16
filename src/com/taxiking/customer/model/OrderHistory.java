package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderHistory {
	
	public String order_id = "";
	public String state = "";
	public String time = "";
	public String address = "";
	public String price = "";
	public String driverName = "";
	public String driverPhone = "";
	
	public static OrderHistory fromJSON(JSONObject obj) {
		OrderHistory object = new OrderHistory();

		try {
			object.order_id		= obj.getString("transaction_id");
			object.time			= obj.getString("order_time");
			object.address		= obj.getString("order_address");
			object.price		= obj.getString("price");
			object.driverName	= obj.getString("driver_name");
			object.driverPhone	= obj.getString("driver_phone");
			object.state		= obj.getString("state");
			
		} catch (JSONException e) {
		}

		return object;
	}
}
