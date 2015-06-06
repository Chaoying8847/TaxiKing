package com.taxiking.customer.utils;

import java.text.DecimalFormat;


public class AppConstants {

//	public static final String SERVER_BASE_URL = "http://10.70.3.88/coms/mobile/commonAPI.php";
	public static final String SERVER_BASE_URL = "http://www.scalehauling.com/coms/mobile/commonAPI.php";
	public static final String SERVER_HOST = "www.app-flow-data.com.au";
	
	public static final int SELECT_PICTURE_CAMERA = 1001;
	public static final int SELECT_PICTURE_GALLERY = 1002;
	
	public static final String PHTOP_DIR_NAME = "deductibles";
	
//	public static final SimpleDateFormat exportDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//	public static final SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	public static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd.MM.yyyy");
//	public static final SimpleDateFormat deductionDateFormat = new SimpleDateFormat("yy-M-d");
//	public static final SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DecimalFormat priceFormat = new DecimalFormat("0.00");
	
	public static final String SERVER_BUSY = "-1";
	public static final String NO_INTERNET = "-2";
	
	public static final String RESULT_OK = "ok";
	public static final String RESULT_ERROR = "error";

	public static final String REFRESH_SCREEN = "refeshScreen";
	public static final String HAS_SUBMITTED = "hasSubmitted";
	
	public static final int ERR_OK = 0;
	public static final int ERR_SQL = 1;
	public static final int ERR_INVALID_PKEY = 2;
	public static final int ERR_NODATA = 3;

	public static final int ERR_FAILLOGIN = 4;
	public static final int ERR_ALREADYLOGIN = 5;
	public static final int ERR_NOT_LOGINED = 6;
	
	// for client error (network, unknown etc.)
	public static final int ERR_CLIENT_NETWORK = 10001;
	public static final int ERR_CLIENT_UNKNOWN = 10002;
	public static final int ERR_CLIENT_PARSE = 10003;
	
	public static final int SQL_NOT_SUBMIT	= 0;
	public static final int SQL_WILL_SUBMIT	= 1;
	
	public static final String ACCOUNT_TYPE[] = new String[] {"","Phillips66", "Valero", "Mercuria"};
	public static final String STATE_TYPE[] = new String[] {"","Oklahoma", "Texas"};
	
	public enum DeductionMode {
		MODE_NONE,
		MODE_READONLY,
		MODE_EDITABLE,
		MODE_NEW
	};
	
	public enum DeductionType {
		TYPE_NONE,
		TYPE_MY_EXPENSES,
		TYPE_AWAITIG_FILING
	};
	
	/*
	 * Fragment IDs
	 */
	public static final int SW_FRAGMENT_HOME = 0x100;
	public static final int SW_FRAGMENT_ORDER = 0x200;
	public static final int SW_FRAGMENT_PRICE_LIST = 0x300;
	public static final int SW_FRAGMENT_MORE = 0x400;
	
}
