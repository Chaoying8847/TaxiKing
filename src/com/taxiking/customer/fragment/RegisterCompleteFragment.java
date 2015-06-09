package com.taxiking.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.taxiking.customer.LoginActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.apiservice.HttpApi;
import com.taxiking.customer.apiservice.HttpApi.METHOD;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.CommonUtil;

public class RegisterCompleteFragment extends BaseFragment {

	private Button btnComplete;
	private Button btnBack;
	private EditText txtPassword;
	private String thirdPartyMethod;
	private String thirdPartyId;
	
	public static RegisterCompleteFragment newInstance() {
		RegisterCompleteFragment fragment = new RegisterCompleteFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_register_complete, null);
		
		btnComplete		= (Button)rootview.findViewById(R.id.btn_complete);
		btnBack			= (Button)rootview.findViewById(R.id.btn_back);
		txtPassword	= (EditText)rootview.findViewById(R.id.txt_password);
		
		btnComplete.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		
		thirdPartyMethod = "";
		thirdPartyId = "";
	
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_complete:
			final String password = txtPassword.getText().toString();
			final String latitude 	= prefs.getLatitude();
			final String longitude 	= prefs.getLongitude();

			if(txtPassword.equals("")) {
				Toast.makeText(parent, R.string.msg_input_password, Toast.LENGTH_LONG).show();
				return;
			} else if (latitude.equals("")){
				CommonUtil.showLocationWaringDialog(parent);
			} else if (!CommonUtil.isNetworkAvailable(parent)) {
				CommonUtil.showNetworkWaringDialog(parent);
			} else {
				new SetPasswordAsyncTask().execute(password, latitude, longitude);
			}
			break;
		case R.id.btn_back:
			parent.goBack();
			break;
		default:
			break;
		}
	}
	
	public class SetPasswordAsyncTask extends AsyncTask<String, String, JSONObject> {
		private String password;

		@Override
		protected void onPreExecute() {
			LoginActivity.instance.showWaitView();
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			password = args[0];
			String latitude = args[1];
			String longitude = args[2];
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("latitude", latitude));
			params.add(new BasicNameValuePair("longitude", longitude));

			return HttpApi.callToJson(AppConstants.HOST_REGISTER_3, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			LoginActivity.instance.hideWaitView();
			try {
				String result = res.getString("result");
	
				if (result.equalsIgnoreCase("success")) {
					
				} else {
					try {
						String errorMsg = res.getString("error");
						Toast.makeText(parent, errorMsg, Toast.LENGTH_LONG).show();
					}catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
