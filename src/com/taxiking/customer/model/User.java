package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class User {
	public static final String TAG = "JSON Parse Err In User";

	public String user_id;
	public String username;
	public String firstName;
	public String lastName;
	public String phoneNo;
	public String email;
	public int type;
	public String driverNo;
	public String tractorNo;
	public String owner;
	public String deviceSerial;
	public boolean active;

	public static User fromJSON(JSONObject obj) {
		User mUser = new User();

		try {
			mUser.user_id	= obj.getString("id");
			mUser.firstName	= obj.getString("firstName");
			mUser.lastName	= obj.getString("lastName");
			mUser.username	= obj.getString("username");
			mUser.phoneNo	= obj.getString("phoneNo");
			mUser.email		= obj.getString("email");
			mUser.type		= obj.getInt("type");
			mUser.driverNo	= obj.getString("driverNo");
			mUser.tractorNo	= obj.getString("tractorNo");
			mUser.owner		= obj.getString("owner");
			mUser.deviceSerial = obj.getString("deviceSerial");
			mUser.active	= true;
		} catch (JSONException e) {
			Log.d(TAG, e.toString());
		}

		return mUser;
	}

}