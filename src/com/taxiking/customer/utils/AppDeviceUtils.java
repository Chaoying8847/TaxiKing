package com.taxiking.customer.utils;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class AppDeviceUtils {

	private static final String CLASS_NAME = "AppDeviceUtils";

	/**
	 * This Function return the Android DeviceID in String format. Always to add
	 * following Permission in Manifest.
	 * 
	 * Requires Permission: READ_PHONE_STATE
	 * 
	 * See below for @params.
	 */

	public static String getDeviceID(Context ctx) {

		TelephonyManager telephonyManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String iemi = telephonyManager.getDeviceId();
		return iemi;
	}

	/**
	 * This Function check the GPS enable/disable Status. Remember to add
	 * required Permission in Manifest.
	 * 
	 * See below for @params.
	 */

	public static boolean isGPSEnable(Context ctx) {
		LocationManager locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static boolean isNetworkEnable(Context ctx) {

		LocationManager locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	/**
	 * This Function convert the DP to Pixel.
	 * 
	 * See below for @params.
	 */

	public static int convertDPtoPX(Context ctx, int dp) {

		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) ctx).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		float logicalDensity = metrics.density;
		int px = (int) (dp * logicalDensity + 0.5);
		System.out.println("DP to Pixel-->" + dp + " = " + px);
		return px;
	}

	/**
	 * This Function return the Android Device Display Width.
	 * 
	 * See below for @params.
	 */

	public static int getMyDispalyWidth(Context ctx) {

		int Width = ((Activity) ctx).getWindowManager().getDefaultDisplay()
				.getWidth();
		return Width;
	}

	/**
	 * This Function return the Android Device Display Height.
	 * 
	 * See below for @params.
	 */

	public static int getMyDispalyHight(Context ctx) {

		int Hight = ((Activity) ctx).getWindowManager().getDefaultDisplay()
				.getHeight();
		return Hight;
	}

	/**
	 * This Function check SD Card is mounted to Android Device (return
	 * true/false).
	 */

	public static boolean isSdPresent() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * This Function check Internet is Available or Not in the Android Device (
	 * return true/false).
	 * 
	 * * Requires Permission: android.permission.ACCESS_NETWORK_STATE See below
	 * for @params.
	 */

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

}
