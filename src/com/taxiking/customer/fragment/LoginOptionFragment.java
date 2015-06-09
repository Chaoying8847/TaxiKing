package com.taxiking.customer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.taxiking.customer.R;
import com.taxiking.customer.base.BaseFragment;

public class LoginOptionFragment extends BaseFragment implements View.OnClickListener {

	private LayoutInflater layoutInflater;
	
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

		layoutInflater = inflater;
		View rootview = inflater.inflate(R.layout.fragment_login_option, null);
		
		btnLogin	= (Button)rootview.findViewById(R.id.btn_login);
		btnRegister	= (Button)rootview.findViewById(R.id.btn_register);
		btnTerms	= (Button)rootview.findViewById(R.id.btn_terms);
		
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		btnTerms.setOnClickListener(this);
	
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
}
