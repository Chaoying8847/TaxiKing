package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Driver {
	public String last_update = "";
	public double longitude = 0.0;
	public double latitude = 0.0;
	
	public static Driver fromJSON(JSONObject obj) {
		Driver object = new Driver();

		try {
			object.last_update	= obj.getString("last_update");
			object.longitude	= obj.getDouble("longitude");
			object.latitude		= obj.getDouble("latitude");
			
		} catch (JSONException e) {
		}
		return object;
	}

}
