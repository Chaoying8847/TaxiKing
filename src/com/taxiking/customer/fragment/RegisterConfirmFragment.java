package com.taxiking.customer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.taxiking.customer.R;
import com.taxiking.customer.base.BaseFragment;

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
			RegisterConfirmFragment.this.parent.showFragment(RegisterCompleteFragment.newInstance(), true);
			break;
		case R.id.btn_back:
			parent.goBack();
			break;
		default:
			break;
		}
	}
}