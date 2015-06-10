package com.taxiking.customer.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceType {
	public String type = "";
	public double price = 0;
	
	public static ServiceType fromJSON(JSONObject obj) {
		ServiceType object = new ServiceType();

		try {
			JSONArray arrNames 	= obj.names();
			object.type		= arrNames.getString(0);
			object.price	= obj.getDouble(object.type);
			
		} catch (JSONException e) {
		}
		return object;
	}
}
