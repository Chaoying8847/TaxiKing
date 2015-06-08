package com.taxiking.customer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.base.BaseFragment;

public class MoreFragment extends BaseFragment {

	public static MoreFragment newInstance() {
		MoreFragment fragment = new MoreFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (MainActivity.instance != null)
			MainActivity.instance.setTitle(getActivity().getString(R.string.menu_more));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_more, null);

//		View layout_sharing_use = rootview.findViewById(R.id.layout_sharing_use);
//		View layout_terms_of_service = rootview.findViewById(R.id.layout_terms_of_service);
//		View layout_customer_call = rootview.findViewById(R.id.layout_customer_call);
//		View layout_business_colaboration = rootview.findViewById(R.id.layout_business_colaboration);
//
//		layout_sharing_use.setOnClickListener(this);
//		layout_terms_of_service.setOnClickListener(this);
//		layout_customer_call.setOnClickListener(this);
//		layout_business_colaboration.setOnClickListener(this);
		
		return rootview;
	}
	
//	@Override
//	public void onClick(final View v) {
//		switch (v.getId()) {
//		case R.id.layout_sharing_use:
//			MoreFragment.this.parent.showFragment(TermsFragment.newInstance(), true);
//			break;
//		case R.id.layout_terms_of_service:
//			MoreFragment.this.parent.showFragment(TermsFragment.newInstance(), true);
//			break;
//
//		case R.id.layout_customer_call:
//			MoreFragment.this.parent.showFragment(TermsFragment.newInstance(), true);
//			break;
//
//		case R.id.layout_business_colaboration:
//			MoreFragment.this.parent.showFragment(TermsFragment.newInstance(), true);
//			break;
//		}
//	}
}
