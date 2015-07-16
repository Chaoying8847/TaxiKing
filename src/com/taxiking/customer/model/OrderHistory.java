package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderHistory {
	
	public String order_id = "";
	public String address = "";
	public String end_time = "";
	public String price = "";
	public String promo_amount = "";
	public String wechat_charge = "";
	public int rating = 0;
	public String driverPhone = "";
	
	public static OrderHistory fromJSON(JSONObject obj) {
		OrderHistory object = new OrderHistory();

		try {
			object.order_id		= obj.getString("transaction_id");
			object.address		= obj.getString("address_string");
			object.end_time			= obj.getString("actual_end_time");
			object.price		= obj.getString("price");
			object.promo_amount		= obj.getString("price");
			object.wechat_charge		= obj.getString("price");
			object.rating		= obj.getInt("price");
			
		} catch (JSONException e) {
		}

		return object;
	}
}
