package com.taxiking.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.taxiking.customer.base.BaseFragment;

public class TermsFragment extends BaseFragment {

	private Button btnBack;
	
	public static TermsFragment newInstance() {
		TermsFragment fragment = new TermsFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_terms, null);
		
		btnBack	= (Button)rootview.findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				parent.goBack();
			}
		});
		return rootview;
	}
}
