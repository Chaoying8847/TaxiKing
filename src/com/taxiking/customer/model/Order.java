package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Order {
	
	public String order_id = "";
	public String state = "";
	public String time = "";
	public String address = "";
	public String price = "";
	public String driverName = "";
	public String driverPhone = "";
	
	public static Order fromJSON(JSONObject obj) {
		Order mOrder = new Order();

		try {
			mOrder.order_id		= obj.getString("transaction_id");
			mOrder.state		= obj.getString("state");
			mOrder.time			= obj.getString("order_time");
			mOrder.address		= obj.getString("order_address");
			mOrder.price		= obj.getString("price");
			mOrder.driverName	= obj.getString("driver_name");
			mOrder.driverPhone	= obj.getString("driver_phone");
			
		} catch (JSONException e) {
		}

		return mOrder;
	}
}
