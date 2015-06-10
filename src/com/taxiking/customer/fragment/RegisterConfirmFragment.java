package com.taxiking.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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

public class RegisterConfirmFragment extends BaseFragment implements View.OnClickListener {

	private Button btnConfirm;
	private Button btnBack;
	private EditText txtConfirmNumber;
	
	public static RegisterConfirmFragment newInstance() {
		RegisterConfirmFragment fragment = new RegisterConfirmFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_register_confirm, null);
		
		btnConfirm		= (Button)rootview.findViewById(R.id.btn_confirm);
		btnBack			= (Button)rootview.findViewById(R.id.btn_back);
		txtConfirmNumber	= (EditText)rootview.findViewById(R.id.txt_confirmnumber);
		
		btnConfirm.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_confirm:
			final String confirmNumber = txtConfirmNumber.getText().toString();

			if(confirmNumber.equals("")) {
				Toast.makeText(parent, R.string.msg_input_verify_code, Toast.LENGTH_LONG).show();
				return;
			} else if (!CommonUtil.isNetworkAvailable(parent)) {
				CommonUtil.showWaringDialog(parent, parent.getString(R.string.warning), parent.getString(R.string.msg_network_error));
			} else {
				new VerifyAsyncTask().execute(confirmNumber);
			}
			break;
		case R.id.btn_back:
			parent.goBack();
			break;
		default:
			break;
		}
	}

	public class VerifyAsyncTask extends AsyncTask<String, String, JSONObject> {
		private String verification_code;

		@Override
		protected void onPreExecute() {
			LoginActivity.instance.showWaitView();
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			verification_code = args[0];
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));
			params.add(new BasicNameValuePair("verification_code", verification_code));

			return HttpApi.callToJson(AppConstants.HOST_REGISTER_2, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			LoginActivity.instance.hideWaitView();
			try {
				String result = res.getString("result");
	
				if (result.equalsIgnoreCase("success")) {
					RegisterConfirmFragment.this.parent.showFragment(RegisterCompleteFragment.newInstance(), true);
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