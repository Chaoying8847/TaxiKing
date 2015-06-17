package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderHistory {
	
	public String order_id = "";
	public String address = "";
	public String end_time = "";
	public String driverName = "";
	public String price = "";
	public String promo_amount = "";
	public String wechat_charge = "";
	public String rating = "";
	public String driverPhone = "";
	
	public static OrderHistory fromJSON(JSONObject obj) {
		OrderHistory object = new OrderHistory();

		try {
			object.order_id		= obj.getString("transaction_id");
			object.address		= obj.getString("address_string");
			object.end_time			= obj.getString("actual_end_time");
			object.driverName	= obj.getString("driver_name");
			object.price		= obj.getString("price");
			object.promo_amount		= obj.getString("price");
			object.wechat_charge		= obj.getString("price");
			object.rating		= obj.getString("price");
			
		} catch (JSONException e) {
		}

		return object;
	}
}
