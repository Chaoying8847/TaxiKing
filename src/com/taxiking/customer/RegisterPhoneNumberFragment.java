package com.taxiking.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.taxiking.customer.base.BaseFragment;

public class RegisterPhoneNumberFragment extends BaseFragment  implements View.OnClickListener {

	private Button btnSend;
	private Button btnBack;
	private EditText txtPhoneNumber;
	
	public static RegisterPhoneNumberFragment newInstance() {
		RegisterPhoneNumberFragment fragment = new RegisterPhoneNumberFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_input_phonenumber, null);
		
		btnSend			= (Button)rootview.findViewById(R.id.btn_send);
		btnBack			= (Button)rootview.findViewById(R.id.btn_back);
		txtPhoneNumber	= (EditText)rootview.findViewById(R.id.txt_phonenumber);
		
		btnSend.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_send:
			RegisterPhoneNumberFragment.this.parent.showFragment(RegisterConfirmFragment.newInstance(), true);
			break;
		case R.id.btn_back:
			parent.goBack();
			break;
		default:
			break;
		}
	}
}
