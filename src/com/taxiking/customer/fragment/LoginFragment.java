package com.taxiking.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.taxiking.customer.LoginActivity;
import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.apiservice.HttpApi;
import com.taxiking.customer.apiservice.HttpApi.METHOD;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppDataUtilities;
import com.taxiking.customer.utils.CommonUtil;

public class LoginFragment extends BaseFragment {

	private Button btnLogin;
	private Button btnBack;
	private EditText txtPhoneNumber;
	private EditText txtPassword;
	
	public static LoginFragment newInstance() {
		LoginFragment fragment = new LoginFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_login, null);
		
		btnLogin		= (Button)rootview.findViewById(R.id.btn_login);
		btnBack			= (Button)rootview.findViewById(R.id.btn_back);
		txtPhoneNumber	= (EditText)rootview.findViewById(R.id.txt_phonenumber);
		txtPassword		= (EditText)rootview.findViewById(R.id.txt_password);
		
		btnLogin.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	
		// test code
		txtPhoneNumber.setText("12345678901");
		txtPassword.setText("1111");
		
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_login:
			final String phoneNumber= txtPhoneNumber.getText().toString();
			final String password 	= txtPassword.getText().toString();
			final String latitude 	= prefs.getLatitude();
			final String longitude 	= prefs.getLongitude();

			if(phoneNumber.equals("")) {
				Toast.makeText(parent, R.string.msg_input_phone_number, Toast.LENGTH_LONG).show();
				return;
			} else if(password.equals("")) {
				Toast.makeText(parent, R.string.msg_input_password, Toast.LENGTH_LONG).show();
				return;
			} else if (latitude.equals("")){
				CommonUtil.showWaringDialog(parent, parent.getString(R.string.warning), parent.getString(R.string.msg_location_error));
			} else if (!CommonUtil.isNetworkAvailable(parent)) {
				CommonUtil.showWaringDialog(parent, parent.getString(R.string.warning), parent.getString(R.string.msg_network_error));
			} else {
//				new LoginAsyncTask().execute(phoneNumber, password, "39.9558222", "116.2789134");
				new LoginAsyncTask().execute(phoneNumber, password, latitude, longitude);
			}
			break;
		case R.id.btn_back:
			parent.goBack();
			break;
		default:
			break;
		}
	}
	

	public class LoginAsyncTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			LoginActivity.instance.showWaitView();
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			String phone_number = args[0];
			String password = args[1];
			String latitude = args[2];
			String longitude = args[3];
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("phone_number", phone_number));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("latitude", latitude));
			params.add(new BasicNameValuePair("longitude", longitude));

			return HttpApi.callToJson(AppConstants.HOST_LOGIN, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			LoginActivity.instance.hideWaitView();
			try {
				String result = res.getString("result");
	
				if (result.equalsIgnoreCase("success")) {
					try {
						String session_token = res.getString("session_token");
						parent.prefs.setSession(session_token);
						
						AppDataUtilities.sharedInstance().setDataFromLoginJsonData(res);
						
						getActivity().finish();
			            Intent intent = new Intent(getActivity(), MainActivity.class);
			            startActivity(intent);
					} catch (JSONException e) {
						e.printStackTrace();
					}
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
