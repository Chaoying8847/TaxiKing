package com.taxiking.customer.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.taxiking.customer.utils.AppPreferences;

public class BaseFragment extends Fragment implements OnClickListener {
	
	protected AppPreferences prefs;
	
	protected final String KEY_CONTENT = getClass().getName() + ":Content";
	protected String mContent = getClass().getName();

	protected BaseFragmentActivity parent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}

		parent = (BaseFragmentActivity)getActivity();
		prefs = new AppPreferences(getActivity());
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}

	@Override
	public void onClick(View v) {
		
	}
	
}
