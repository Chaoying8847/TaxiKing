package com.taxiking.customer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.taxiking.customer.R;
import com.taxiking.customer.base.BaseFragment;

public class RegisterCompleteFragment extends BaseFragment {

	private Button btnComplete;
	private Button btnBack;
	private EditText txtPassword;
	
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
	
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_complete:
			break;
		case R.id.btn_back:
			parent.goBack();
			break;
		default:
			break;
		}
	}
}
