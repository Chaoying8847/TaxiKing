package com.taxiking.customer;

import android.os.Bundle;

import com.taxiking.customer.base.BaseFragmentActivity;
import com.taxiking.customer.utils.AppPreferences;

public class LoginActivity extends BaseFragmentActivity {

	private AppPreferences appPreference;
	private LoginOptionFragment loginOptionFragment;
	public static LoginActivity loginActivity; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		appPreference = new AppPreferences(this);
		loginOptionFragment = LoginOptionFragment.newInstance();
		
		loginActivity = this;
		
		showFragment(loginOptionFragment, false, false);
	}
}
