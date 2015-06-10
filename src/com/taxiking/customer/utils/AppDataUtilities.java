package com.taxiking.customer.utils;

import java.util.ArrayList;

import android.content.Context;

import com.taxiking.customer.model.Driver;
import com.taxiking.customer.model.OrderHistory;
import com.taxiking.customer.model.ServiceType;

public class AppDataUtilities {
	
	public static AppDataUtilities instance;
	public static Context context;
	
	private ArrayList <Driver> arrDrivers;
	private ArrayList <OrderHistory> arrOrderHistory;
	private ArrayList <ServiceType> arrService;
	
	public AppDataUtilities(Context context) {
		
		instance = this;
		this.context = context;
		
	}
}
