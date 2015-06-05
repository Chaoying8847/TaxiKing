package com.taxiking.customer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences {

	//private static final String CLASS_NAME = "AppPreferences";
	private static String APP_SHARED_PREFS;

	private final String NO_LOGIN = "NO_LOGIN";
	
	private final String CATEGORY_ENABLED = "CATEGORY_ENABLED";
	
	private final String USER_UID = "USER_UID";
	private final String USER_FNAME = "USER_FNAME";
	private final String USER_LNAME = "USER_LNAME";
	private final String USER_TYPE = "USER_TYPE";

	private final String SERVER_IP = "SERVER_IP";

	private final String USER_UNAME = "USER_UNAME";
	private final String USER_PWORD = "USER_PWORD";
	private final String USER_ID = "USER_ID";
	private final String USER_LOGGED_IN = "USER_LOGGED_IN";
	private final String FEEDBACK_NAME = "FEEDBACK_NAME";
	private final String FEEDBACK_EMAIL = "FEEDBACK_EMAIL";
	private final String PDF_LINK = "PDF_LINK";
	private final String FINANCIAL_YEAR = "FINANCIAL_YEAR";
	private final String SYNC_WIFI = "SYNC_WIFI";
	private final String WEEKLY_REMINDERS = "WEEKLY_REMINDERS";
	private final String LAST_TICKET = "LAST_TICKET";
	private final String WILL_SUBMIT = "WILL_SUBMIT";
	
	
	private SharedPreferences mPrefs;
	private Editor mPrefsEditor;

	public AppPreferences(Context context) {

		APP_SHARED_PREFS = context.getApplicationContext().getPackageName();
		this.mPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
		this.mPrefsEditor = mPrefs.edit();
	}
	
	public void setFirstName(String value) {
		mPrefsEditor.putString(USER_FNAME, value);
		mPrefsEditor.commit();
	}
	
	public String getFirstName() {
		String value = mPrefs.getString(USER_FNAME, "");
		return value;
	}
	
	public void setLastName(String value) {
		mPrefsEditor.putString(USER_LNAME, value);
		mPrefsEditor.commit();
	}
	
	public String getLastName() {
		String value = mPrefs.getString(USER_LNAME, "");
		return value;
	}
	
	public void setWillTicketSubmit(boolean value) {
		mPrefsEditor.putBoolean(WILL_SUBMIT, value);
		mPrefsEditor.commit();
	}
	
	public boolean isTicketSubmitted() {
		boolean value = mPrefs.getBoolean(WILL_SUBMIT, false);
		return !value;
	}
	
	public String getLastTicket() {
		String value = mPrefs.getString(LAST_TICKET, "");
		return value;
	}
	
	public void saveLastTicket(String ticket_id) {
		if (ticket_id != null) {
			mPrefsEditor.putString(LAST_TICKET, ticket_id);
		} else {
			mPrefsEditor.putString(LAST_TICKET, "");
		}
		mPrefsEditor.commit();
	}

	public void setNoLogin(boolean value) {
		mPrefsEditor.putBoolean(NO_LOGIN, value);
		mPrefsEditor.commit();
	}

	public boolean isNoLogin() {
		boolean value = mPrefs.getBoolean(NO_LOGIN, true);
		return value;
	}
	
	public void setCategoryEnabled(String value) {
		mPrefsEditor.putString(CATEGORY_ENABLED, value);
		mPrefsEditor.commit();
	}

	public String getCategoryEnabled() {
		String value = mPrefs.getString(CATEGORY_ENABLED, "");
		return value;
	}


	public void setServerIP(String serverIP) {
		mPrefsEditor.putString(SERVER_IP, serverIP);
		mPrefsEditor.commit();
	}

	public String getServerIP() {
		String serverIP = mPrefs.getString(SERVER_IP, "");
		return serverIP;
	}

	public void setFeedbackName(String value) {
		mPrefsEditor.putString(FEEDBACK_NAME, value);
		mPrefsEditor.commit();
	}

	public String getFeedbackName() {
		String value = mPrefs.getString(FEEDBACK_NAME, "");
		return value;
	}

	public void setFeedbackMail(String value) {
		mPrefsEditor.putString(FEEDBACK_EMAIL, value);
		mPrefsEditor.commit();
	}

	public String getFeedbackMail() {
		String value = mPrefs.getString(FEEDBACK_EMAIL, "");
		return value;
	}

	public void setAccountName(String value) {
		mPrefsEditor.putString(USER_UNAME, value);
		mPrefsEditor.commit();
	}

	public String getAccountName() {
		String value = mPrefs.getString(USER_UNAME, "");
		return value;
	}	

	public void setAccountPasswd(String value) {
		mPrefsEditor.putString(USER_PWORD, value);
		mPrefsEditor.commit();
	}

	public String getAccountPasswd() {
		String value = mPrefs.getString(USER_PWORD, "");
		return value;
	}

	public void setAccountId(int value) {
		mPrefsEditor.putInt(USER_ID, value);
		mPrefsEditor.commit();
	}

	public int getAccountId() {
		int value = mPrefs.getInt(USER_ID, -1);
		return value;
	}	

	public void setPDFLink(String value) {
		mPrefsEditor.putString(PDF_LINK, value);
		mPrefsEditor.commit();
	}

	public String getPDFLink() {
		String value = mPrefs.getString(PDF_LINK, "");
		return value;
	}	

	public void setFinancialYear(String value) {
		mPrefsEditor.putString(FINANCIAL_YEAR, value);
		mPrefsEditor.commit();
	}

	public String getFinancialYear() {
//		int value = mPrefs.getInt(FINANCIAL_YEAR, 2014);
//		return value;
		String value = mPrefs.getString(FINANCIAL_YEAR, "2013/2014");
		return value;
	}	

	public void setSyncWifi(boolean value) {
		mPrefsEditor.putBoolean(SYNC_WIFI, value);
		mPrefsEditor.commit();
	}

	public boolean isSyncWifi() {
		boolean value = mPrefs.getBoolean(SYNC_WIFI, false);
		return value;
	}	

	public void setWeeklyReminders(boolean value) {
		mPrefsEditor.putBoolean(WEEKLY_REMINDERS, value);
		mPrefsEditor.commit();
	}

	public boolean isWeeklyReminders() {
		boolean value = mPrefs.getBoolean(WEEKLY_REMINDERS, false);
		return value;
	}

}
