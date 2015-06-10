package com.taxiking.customer;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.taxiking.customer.service.GPSTracker;

public class TaxiKing extends Application {

	public GPSTracker gpsTracker;
	@Override
	public void onCreate() {
		super.onCreate();
		gpsTracker = new GPSTracker(this);
		gpsTracker.startTracking();
		
		SDKInitializer.initialize(this);
	}
}
