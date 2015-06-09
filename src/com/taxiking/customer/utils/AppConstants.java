package com.taxiking.customer.utils;

import java.text.DecimalFormat;


public class AppConstants {
	
	public static final String HOST = "http://184.73.117.45/android";
	public static final String HOST_REGISTER_1		= HOST + "/register_1";
	public static final String HOST_REGISTER_2 		= HOST + "/register_2";
	public static final String HOST_REGISTER_3 		= HOST + "/register_3";
	public static final String HOST_LOGIN 			= HOST + "/login";
	public static final String HOST_AUTO_LOGIN 		= HOST + "/auto_login";
	public static final String HOST_REQUEST 		= HOST + "/request";
	public static final String HOST_CURRENT_STATUS 	= HOST + "/current_status";
	public static final String HOST_CONFIRM_FINISH 	= HOST + "/confirm_finish";
	public static final String HOST_RATE 			= HOST + "/rate";
	public static final String HOST_SEND_INFO 		= HOST + "/send_info";
	
	public static final String API_KEY = "xxx";
	public static final String API_SECRET = "xxx";

//	public static final SimpleDateFormat exportDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//	public static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	public static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");
//	public static final SimpleDateFormat deductionDateFormat = new SimpleDateFormat("yy-M-d");
//	public static final SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DecimalFormat priceFormat = new DecimalFormat("0.00");
	
	/*
	 * Fragment IDs
	 */
	public static final int SW_FRAGMENT_HOME = 0x100;
	public static final int SW_FRAGMENT_ORDER = 0x200;
	public static final int SW_FRAGMENT_PRICE_LIST = 0x300;
	public static final int SW_FRAGMENT_MORE = 0x400;
	
}
