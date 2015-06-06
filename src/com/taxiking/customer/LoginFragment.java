package com.taxiking.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.taxiking.customer.base.BaseFragment;

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
	
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_login:
			
			break;
		case R.id.btn_back:
			parent.goBack();
			break;
		default:
			break;
		}
	}
}
