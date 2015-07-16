package com.taxiking.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.taxiking.customer.LoginActivity;
import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.apiservice.HttpApi;
import com.taxiking.customer.apiservice.HttpApi.METHOD;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppDataUtilities;

public class LoginOptionFragment extends BaseFragment implements View.OnClickListener {

	private Button btnLogin;
	private Button btnRegister;
	private Button btnTerms;
	
	public static LoginOptionFragment newInstance() {
		LoginOptionFragment fragment = new LoginOptionFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_login_option, null);
		
		btnLogin	= (Button)rootview.findViewById(R.id.btn_login);
		btnRegister	= (Button)rootview.findViewById(R.id.btn_register);
		btnTerms	= (Button)rootview.findViewById(R.id.btn_terms);
		
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		btnTerms.setOnClickListener(this);
	
		if (!prefs.getSession().equalsIgnoreCase("")) {
			final String latitude 	= prefs.getLatitude();
			final String longitude 	= prefs.getLongitude();
			new AutoLoginAsyncTask().execute(latitude, longitude);
		}
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_login:
			LoginOptionFragment.this.parent.showFragment(LoginFragment.newInstance(), true);
			break;
		case R.id.btn_register:
			LoginOptionFragment.this.parent.showFragment(RegisterPhoneNumberFragment.newInstance(), true);
			break;
		case R.id.btn_terms:
			LoginOptionFragment.this.parent.showFragment(TermsFragment.newInstance(), true);
			break;
		default:
			break;
		}
	}
	
	public class AutoLoginAsyncTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			LoginActivity.instance.showWaitView();
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			String latitude = args[0];
			String longitude = args[1];
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));
			params.add(new BasicNameValuePair("latitude", latitude));
			params.add(new BasicNameValuePair("longitude", longitude));
						
			return HttpApi.callToJson(AppConstants.HOST_AUTO_LOGIN, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			LoginActivity.instance.hideWaitView();
			
			AppDataUtilities.sharedInstance().setDataFromLoginJsonData(res);
			
			getActivity().finish();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
		}
	}
}
