package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class User {
	public static final String TAG = "JSON Parse Err In User";
	
	public String phone_number;
	public Boolean first_time_order;
	public String promo_info_value;
	public String promo_info_code;

	public static User fromJSON(JSONObject obj) {
		User mUser = new User();

		try {
			mUser.phone_number	= obj.getString("phone_number");
			mUser.first_time_order	= obj.getBoolean("first_time_order");
			
			JSONObject promo_info = obj.getJSONObject("promo_info");
			mUser.promo_info_value	= promo_info.getString("value");
			mUser.promo_info_code	= promo_info.getString("code");
		} catch (JSONException e) {
			Log.d(TAG, e.toString());
		}

		return mUser;
	}

}