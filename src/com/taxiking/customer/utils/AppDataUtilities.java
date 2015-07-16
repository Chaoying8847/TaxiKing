package com.taxiking.customer.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.taxiking.customer.model.CurrentStatus;
import com.taxiking.customer.model.Driver;
import com.taxiking.customer.model.OrderHistory;
import com.taxiking.customer.model.ServiceType;
import com.taxiking.customer.model.User;

public class AppDataUtilities {
	
	private static AppDataUtilities instance;
	
	// drivers parse
	public ArrayList <Driver> driverArray;
	public ArrayList <ServiceType> serviceArray;
	public ArrayList <OrderHistory> orderHistoryArray;
	public User account;
	public CurrentStatus status;
	public String CurrentAddress;
	
	public String transaction_id;
	public String pay_id;
	public String pay_key;
	
	
	public AppDataUtilities() {
		
		driverArray = new ArrayList<Driver>();
		serviceArray = new ArrayList<ServiceType>();
		orderHistoryArray = new ArrayList<OrderHistory>();
		
		transaction_id	= "";
		pay_id			= "";
		pay_key			= "";
		CurrentAddress	= "";
	}
	
	public static AppDataUtilities sharedInstance() {
		if (instance == null) {
			instance = new AppDataUtilities();
		}
		return instance;
	}
	
	
	
	public void setDataFromLoginJsonData(JSONObject res) {
		try {
			// drivers parse
			driverArray = new ArrayList<Driver>();
			JSONArray driverJsonArray = res.getJSONArray("drivers");
			for (int i=0; i<driverJsonArray.length(); i++) {
				Driver driver = Driver.fromJSON(driverJsonArray.getJSONObject(i));
				driverArray.add(driver);
			}
			
			// account info parse
			account = User.fromJSON(res.getJSONObject("account_info"));
			
			// current status
			JSONObject statusObject = res.getJSONObject("current_status");
			status = CurrentStatus.fromJSON(statusObject);
			
			// service type parse
			serviceArray = new ArrayList<ServiceType>();
			JSONObject serviceObject = res.getJSONObject("service_info");
			JSONArray serviceNames = serviceObject.names();
			for (int i=0; i<serviceNames.length(); i++) {
				ServiceType serviceType = new ServiceType();
				serviceType.type = serviceNames.getString(i);
				serviceType.price = serviceObject.getDouble(serviceType.type);
				serviceArray.add(serviceType);
			}
			
			// past history
			orderHistoryArray = new ArrayList<OrderHistory>();
			JSONArray hostoryJsonArray = res.getJSONArray("past_orders");
			for (int i=0; i<hostoryJsonArray.length(); i++) {
				OrderHistory orderHistory = OrderHistory.fromJSON(hostoryJsonArray.getJSONObject(i));
				orderHistoryArray.add(orderHistory);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
}
