package com.taxiking.customer;

import android.os.Bundle;

import com.taxiking.customer.base.BaseFragmentActivity;
import com.taxiking.customer.fragment.LoginOptionFragment;
import com.taxiking.customer.utils.WaitDialog;

public class LoginActivity extends BaseFragmentActivity {

	private LoginOptionFragment loginOptionFragment;
	public static LoginActivity instance; 
	private WaitDialog waitDlg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		loginOptionFragment = LoginOptionFragment.newInstance();
		
		instance = this;
		waitDlg = new WaitDialog(this);
		
		showFragment(loginOptionFragment, false, false);
	}
	
	public void showWaitView() {
		waitDlg.show();
	}
	
	public void hideWaitView() {
		waitDlg.cancel();
	}
}
