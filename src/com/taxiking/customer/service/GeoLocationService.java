package com.taxiking.customer.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.taxiking.customer.apiservice.APIUtil;
import com.taxiking.customer.utils.AppDeviceUtils;
import com.taxiking.customer.utils.AppPreferences;


public class GeoLocationService extends Service {

	private LocationManager _locationManager = null;

	private GeoLocationListener _locationListener = null;

	private static AppPreferences prefs = null;

	private Timer timerCheckProvider = null;
	public static Location _curLocation = null;
	public static long recordOldTick = 0;
	public static long minIntervalFrequency;
	public static int accuracy = 100;
	private int IntervaListener = 30 * 1000;
	private boolean isGPSLogging = false;

	@Override
	public void onCreate() {
		prefs = new AppPreferences(getApplicationContext());

		_curLocation = null;

		// Get the location service
		_locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = _locationManager.getBestProvider(criteria, true);
		//_curLocation = _locationManager.getLastKnownLocation(provider);
		
		isGPSLogging = isGPSEnable();

		_locationListener = new GeoLocationListener();

		if (isGPSLogging) {
			_locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, IntervaListener, 0, _locationListener);

		} else {
			_locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, IntervaListener, 0, _locationListener);
		}

//		saveLocation(_curLocation);

		recordOldTick = System.currentTimeMillis();

		timerCheckProvider = new Timer();
		timerCheckProvider.scheduleAtFixedRate(new checkGPSTask(), 0, 600000);
	}

	private boolean isGPSEnable() {
		if(!AppDeviceUtils.isGPSEnable(getApplicationContext()))
			return false;
		
		return true;
	}

	private class checkGPSTask extends TimerTask {
		public void run() {
			checkHandler.sendEmptyMessage(0);
		}
	}

	private final Handler checkHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try{

				long curTick = System.currentTimeMillis();
				minIntervalFrequency = 10 * 60 * 1000;	/* Interval to update GPS info */;
				Log.i("GPSLogger", "location check event");				
				if((curTick - recordOldTick) >= minIntervalFrequency) {
					saveLocation();
				}

				// check gps logging
				boolean gpsLogging = isGPSEnable();
				if(gpsLogging!= isGPSLogging) {
					onGPSProviderChange(gpsLogging);
				}
			} catch(Exception e) {
				if (null != e.getMessage()) {
					Log.e("GPSLogger", e.getMessage());
				}				
			}
		}
	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onDestroy() {
		if(timerCheckProvider != null)
			timerCheckProvider.cancel();
		if (null != _locationListener && null != _locationManager) {
			Log.i("GPSLogger", "In onDestory, removing location listener.");
			_locationManager.removeUpdates(_locationListener);
		}

		super.onDestroy();
	}

	public static void saveLocation() {
		if(_curLocation == null)
			return;
		
		APIUtil api = new APIUtil();	
		
		String uid = Integer.toString(prefs.getAccountId());
		if (uid == "-1")
			return;

		api.gpsTrack(null, uid, String.valueOf(_curLocation.getLatitude()), String.valueOf(_curLocation.getLongitude()));
		recordOldTick = System.currentTimeMillis();
	}

	private void onGPSProviderChange(boolean enable) {
		_locationManager.removeUpdates(_locationListener);
		if(enable)
			_locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, IntervaListener, 0,
					_locationListener);
		else
			_locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, IntervaListener, 0,
					_locationListener);			
	}

	private class GeoLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			if(location != null)
			{
				_curLocation = new Location(location);
				checkHandler.sendEmptyMessage(0);
			}
		}
		@Override
		public void onProviderDisabled(String arg0) {
			Log.i("GPSLogger", arg0 + " Provider Disabled");
			if (0 == arg0.compareTo(LocationManager.GPS_PROVIDER)) {
				Log.i("GPSLogger", "GPS location provider is disabled hence adding network provider listener.");
				onGPSProviderChange(false);
			}
		}
		@Override
		public void onProviderEnabled(String arg0) {
			Log.i("GPSLogger", arg0 + " Provider Enabled");
			if (0 == arg0.compareTo(LocationManager.GPS_PROVIDER)) {
				Log.i("GPSLogger", "GPS location provider is enabled hence removing network provider listener.");
				Log.i("GPSLogger", "In onProviderEnabled, removing location listener.");
				onGPSProviderChange(true);
			}
		}
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

		}
	}

	
//	class UploadLocationAsyncTask extends AsyncTask<Void, Void, String> {
//
//		private Location location;
//		
//		public UploadLocationAsyncTask(Location location) {
//			this.location = location;
//		}
//		
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//		}
//		
//		@Override
//		protected String doInBackground(Void... arg0) {
//			
//			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
//			postParameters.clear();
//			postParameters.add(new BasicNameValuePair("uid", prefs.getUserInfo().userName));
//			postParameters.add(new BasicNameValuePair("lat", AppConstants.locationFormat.format(location.getLatitude())));
//			postParameters.add(new BasicNameValuePair("lon", AppConstants.locationFormat.format(location.getLongitude())));
//			postParameters.add(new BasicNameValuePair("client_time", AppConstants.gpsDateFormat.format(new Date(System.currentTimeMillis()))));
//			
//			String url_update = "http://" + prefs.getServerIP() + "/dmt/tracking.php";
//			
//			String result = RESTfulService.sendHttpRequestbyPost(getBaseContext(), url_update, postParameters);
//			
//			return result;
//		}
		
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//			
//			_curLocation = null;
////			recordOldTick = System.currentTimeMillis();
//		}
//		
//	}
}