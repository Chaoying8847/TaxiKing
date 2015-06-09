package com.taxiking.customer;

import com.taxiking.customer.service.GPSTracker;

import android.app.Application;

public class TaxiKing extends Application {

	public GPSTracker gpsTracker;
	@Override
	public void onCreate() {
		super.onCreate();
		gpsTracker = new GPSTracker(this);
		gpsTracker.startTracking();
	}
}
