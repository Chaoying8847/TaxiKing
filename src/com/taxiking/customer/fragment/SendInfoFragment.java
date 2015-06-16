package com.taxiking.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.apiservice.HttpApi;
import com.taxiking.customer.apiservice.HttpApi.METHOD;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.CommonUtil;

public class SendInfoFragment extends BaseFragment implements OnClickListener {

	private Button 		btnConfirm;
	private EditText 	txtName;
	private EditText 	txtPhone;
	private EditText 	txtAddress;
	
	public static SendInfoFragment newInstance() {
		SendInfoFragment fragment = new SendInfoFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (MainActivity.instance != null)
			MainActivity.instance.setTitle(getActivity().getString(R.string.send_info));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_send_info, null);
		
		txtName		= (EditText)rootview.findViewById(R.id.txt_name);
		txtPhone	= (EditText)rootview.findViewById(R.id.txt_phone);
		txtAddress	= (EditText)rootview.findViewById(R.id.txt_address);
		btnConfirm	= (Button)rootview.findViewById(R.id.btn_confirm);
		
		btnConfirm.setOnClickListener(this);
		
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_confirm:
			final String name	= txtName.getText().toString();
			final String phone	= txtPhone.getText().toString();
			final String address= txtAddress.getText().toString();

			if(name.equalsIgnoreCase("") || phone.equalsIgnoreCase("") || address.equalsIgnoreCase("")) {
				Toast.makeText(parent, R.string.msg_input_anything, Toast.LENGTH_LONG).show();
				return;
			} else if (!CommonUtil.isNetworkAvailable(parent)) {
				CommonUtil.showWaringDialog(parent, parent.getString(R.string.warning), parent.getString(R.string.msg_network_error));
			} else {
				new SendInfoAsyncTask().execute(name, phone, address);
			}
			break;
		default:
			break;
		}
	}
	

	public class SendInfoAsyncTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			MainActivity.instance.showWaitView();
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			String full_name = args[0];
			String phone_number	= args[1];
			String address 	= args[2];
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("full_name", full_name));
			params.add(new BasicNameValuePair("phone_number", phone_number));
			params.add(new BasicNameValuePair("address", address));
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));

			return HttpApi.callToJson(AppConstants.HOST_SEND_INFO, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			MainActivity.instance.hideWaitView();
			MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_HOME, null);
		}
	}
}
