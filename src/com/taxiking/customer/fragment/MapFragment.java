package com.taxiking.customer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.base.BaseFragment;

public class MapFragment extends BaseFragment {
	
	private MapView mMapView;
	private BaiduMap mBaiduMap;

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

		Button btnRequest = (Button)rootview.findViewById(R.id.btn_request);
		btnRequest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MapFragment.this.parent.showFragment(OrderRequestFragment.newInstance(), true);
			}
		});
		
		mMapView = (MapView)rootview.findViewById(R.id.map_view);
		mBaiduMap = mMapView.getMap();
		String latitude = prefs.getLatitude();
		String longitude = prefs.getLongitude();
		LatLng p = new LatLng(Float.valueOf(latitude), Float.valueOf(longitude));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(p));
		return rootview;
	}
}
