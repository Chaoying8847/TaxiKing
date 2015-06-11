package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Order {
	
	public String type = "";
	public int count = 0;
	public double price = 0;
	
	public static Order fromJSON(JSONObject obj) {
		Order object = new Order();

		try {
			object.type		= obj.getString("type");
			object.count	= obj.getInt("count");
			object.price	= obj.getDouble("price");
			
		} catch (JSONException e) {
		}
		return object;
	}
	
	public static Order fromService(ServiceType service) {
		Order object = new Order();
		object.type		= service.type;
		object.price	= service.price;

		return object;
	}
	
}
