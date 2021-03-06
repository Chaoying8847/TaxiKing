package com.taxiking.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.apiservice.HttpApi;
import com.taxiking.customer.apiservice.HttpApi.METHOD;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.model.CurrentStatus;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppDataUtilities;
import com.taxiking.customer.utils.CommonUtil;
import com.taxiking.customer.view.dialog.SSMessageDialog;
import com.taxiking.customer.view.dialog.SSMessageDialog.MessageDilogListener;

public class OrderCompleteFragment extends BaseFragment implements View.OnClickListener {
	
	private TextView txtOrderId;
	private TextView txtOrderTime;
	private TextView txtOrderAddress;
	private TextView txtOrderPrice;
	private Button btnCallToDriver;
	private Button btnComplete;
	
	private CurrentStatus status;

	public static OrderCompleteFragment newInstance() {
		OrderCompleteFragment fragment = new OrderCompleteFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (MainActivity.instance != null)
			MainActivity.instance.setTitle(getActivity().getString(R.string.order_success));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_order_complete, null);

		status = AppDataUtilities.sharedInstance().status;
		
		txtOrderId = (TextView)rootview.findViewById(R.id.txt_order_id);
		txtOrderTime = (TextView)rootview.findViewById(R.id.txt_order_time);
		txtOrderAddress = (TextView)rootview.findViewById(R.id.txt_address);
		txtOrderPrice = (TextView)rootview.findViewById(R.id.txt_order_total_price);
		
		btnCallToDriver = (Button)rootview.findViewById(R.id.btn_call_driver);
		btnCallToDriver.setOnClickListener(this);

		btnComplete = (Button)rootview.findViewById(R.id.btn_complete);
		btnComplete.setOnClickListener(this);
		
		txtOrderId.setText(status.transaction_id);
		txtOrderTime.setText(status.order_time);
		txtOrderAddress.setText(status.order_address);
		txtOrderPrice.setText(String.format("%.0f�", status.price));
		
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_complete:
			SSMessageDialog alert = new SSMessageDialog(parent,
					parent.getString(R.string.confirm_order_complete), "", parent.getString(R.string.cancel),
					parent.getString(R.string.confirm));
			alert.show();
			alert.setMessageDilogListener(new MessageDilogListener() {
				@Override
				public void onButtonClick(int id) {
					if (id == R.id.btn_1){
						new ConfirmFinishAsyncTask().execute();
					}
				}
			});
			break;
		case R.id.btn_call_driver:
			if (status.driver_phone!=null && !status.driver_phone.equalsIgnoreCase("")) {
				makeCall(status.driver_phone);
			}
			break;
		default:
			break;
		}
	}
	
	public class ConfirmFinishAsyncTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			MainActivity.instance.showWaitView();
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));
			params.add(new BasicNameValuePair("transaction_id", status.transaction_id));
			
			return HttpApi.callToJson(AppConstants.HOST_CONFIRM_FINISH, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			try {
				String result = res.getString("result");
	
				if (result.equalsIgnoreCase("success")) {
					MainActivity.instance.hideWaitView();
					MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_RATING, null);
				} else {
					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void makeCall(String mDialSting) {
		if (((TelephonyManager)parent.getSystemService(Context.TELEPHONY_SERVICE)).getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
			CommonUtil.showMessageDialog(parent, getString(R.string.error),  "This device has not call ability.");
			return;
		}

		try {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
	        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        callIntent.setData(Uri.parse("tel:"+mDialSting));
	        if (Build.VERSION.SDK_INT > 20) // Build.VERSION_CODES.KITKAT
	        	callIntent.setPackage("com.android.server.telecom");
	        else
	        	callIntent.setPackage("com.android.phone");
	        startActivity(callIntent);
		} catch (ActivityNotFoundException activityException) {
	    	System.out.println("Call Failed");
	    }
	}
}
