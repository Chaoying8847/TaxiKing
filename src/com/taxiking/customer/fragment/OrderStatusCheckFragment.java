package com.taxiking.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.apiservice.HttpApi;
import com.taxiking.customer.apiservice.HttpApi.METHOD;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.model.CurrentStatus;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppDataUtilities;

public class OrderStatusCheckFragment extends BaseFragment {
	
	private int count = 0;

	public static OrderStatusCheckFragment newInstance() {
		OrderStatusCheckFragment fragment = new OrderStatusCheckFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (MainActivity.instance != null)
			MainActivity.instance.setTitle(getActivity().getString(R.string.menu_order));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_order_status_check, null);

		callCurrentStatusCheck();
		return rootview;
	}
	
	private void callCurrentStatusCheck() {
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (MainActivity.instance.shouldCallStatus) {
					count++;
					if (count < 20) {
						new CheckStatusAsyncTask().execute();
						callCurrentStatusCheck();
					} else {
						MainActivity.instance.hideWaitView();
						MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_HOME, null);
					}
				} else {
					callCurrentStatusCheck();
				}
			}
		}, 5000);
	}
	
	public class CheckStatusAsyncTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			MainActivity.instance.showWaitView();
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));
			
			return HttpApi.callToJson(AppConstants.HOST_CURRENT_STATUS, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			try {
				String result = res.getString("result");
				if (result.equalsIgnoreCase("success")) {
					
					AppDataUtilities.sharedInstance().status = CurrentStatus.fromJSON(res);
					if (AppDataUtilities.sharedInstance().status.state.equals("enroute")) {
						if (MainActivity.instance.shouldCallStatus) {
							MainActivity.instance.hideWaitView();
							MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_ORDER_COMPLETE, null);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
