package com.taxiking.customer.service;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.taxiking.customer.model.OrderHistory;
import com.taxiking.customer.sqllite.DatabaseHandler;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppDeviceUtils;
import com.taxiking.customer.utils.AppPreferences;
import com.taxiking.customer.utils.Utilities;

public class SubmitService extends Service {
	private static final String TAG = "UpdateService";
	private AppPreferences preference;
	private Timer timerCheckProvider = null;
	private int timerInterval = 300000;
	// for test
//	private int timerInterval = 10000;

	private DatabaseHandler dbHandler;
	private ArrayList<OrderHistory> orderList;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d(TAG, "service started!");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(timerCheckProvider != null)
			timerCheckProvider.cancel();
		dbHandler = new DatabaseHandler(getApplicationContext());
		
		preference = new AppPreferences(getApplicationContext());
		timerCheckProvider = new Timer();
		timerCheckProvider.scheduleAtFixedRate(new checkSubmitTask(), 0, timerInterval);
		timerCheckProvider.scheduleAtFixedRate(new checkLoginTask(), 0, timerInterval);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		if(timerCheckProvider != null)
			timerCheckProvider.cancel();
		if(dbHandler != null)
			dbHandler.close();
		super.onDestroy();
	}
	
	
	static int count = 0;
	private class checkSubmitTask extends TimerTask {
		public void run() {
//			Log.d(TAG, String.valueOf(count++));
			count = (count > 1000) ? 0 : count;
			try {
				if (AppDeviceUtils.isOnline(getBaseContext())) {
//					orderList = dbHandler.Get_tickets();
//					if (orderList != null) {
//						for (int i = 0; i< orderList.size(); i++)
//							submitTicket(orderList.get(i));
//					}
				} else {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private class checkLoginTask extends TimerTask {
		public void run() {
			try {
				if (AppDeviceUtils.isOnline(getBaseContext())) {
					LoginTask();
				} else {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void submitTicket(final OrderHistory order) {
//		Log.d(TAG, "Ticket " + ticket.ticket_id + " submit started by service");
//		final APIUtil api = new APIUtil();
//		api.submitTicket(new APIListener() {
//			@Override
//			public void onResult(Object ret, int err) {
//				switch (err) {
//				case AppConstants.ERR_OK:
////					Log.d(TAG, "Ticket " + ticket.ticket_id + " submited by service");
//					dbHandler.delete_Ticket(order.order_id);
//
//					Intent intent = new Intent(AppConstants.REFRESH_SCREEN);
//					intent.putExtra(AppConstants.HAS_SUBMITTED, true);
//					getBaseContext().sendBroadcast(intent);
//
//					break;
//				case AppConstants.ERR_CLIENT_NETWORK:
//				default:
////					Log.d(TAG, "Ticket " + ticket.ticket_id + " submit failed by service");
//					break;
//				}
//			}
//		}, order, order.order_id);
	}
	
	private void LoginTask() {
//		if (!preference.isNoLogin()) {
//			final APIUtil api = new APIUtil();
//			api.login(new APIListener() {
//				@Override
//				public void onResult(Object ret, int err) {
//					switch (err) {
//					case AppConstants.ERR_OK:
//						Log.d(TAG, "login success!");
//						break;
//					case AppConstants.ERR_CLIENT_NETWORK:
//					default:
//						Log.d(TAG, "login failed!");
////						LoginTask();
//						break;
//					}
//				}
//			}, preference.getAccountName(), Utilities.md5(preference.getAccountPasswd()));
//		}
	}

}
