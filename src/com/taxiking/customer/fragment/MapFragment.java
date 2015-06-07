package com.taxiking.customer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.base.BaseFragment;

public class MapFragment extends BaseFragment {

	public static MapFragment newInstance() {
		MapFragment fragment = new MapFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (MainActivity.instance != null)
			MainActivity.instance.setTitle(getActivity().getString(R.string.menu_need));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_map, null);

		return rootview;
	}
}
