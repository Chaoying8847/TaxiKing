package com.taxiking.customer.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.model.Driver;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppDataUtilities;

public class MapFragment extends BaseFragment implements OnGetGeoCoderResultListener {
	
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private GeoCoder mSearch = null;
	private UiSettings mUiSettings;
	
	private BitmapDescriptor mMark = BitmapDescriptorFactory.fromResource(R.drawable.icon_map_mark);
	
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
				MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_ORDER_REQUEST, null);
			}
		});
		
		mMapView = (MapView)rootview.findViewById(R.id.map_view);
		mBaiduMap = mMapView.getMap();
		String latitude = prefs.getLatitude();
		String longitude = prefs.getLongitude();
		LatLng p = new LatLng(Float.valueOf(latitude), Float.valueOf(longitude));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(p));
		
		// compass added
		mUiSettings = mBaiduMap.getUiSettings();
		mUiSettings.setCompassEnabled(true);
		
		//search address
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(p));
		
		initOverlay();
		return rootview;
	}
	
	public void initOverlay() {
		ArrayList <Driver> driverArray = AppDataUtilities.sharedInstance().driverArray;
		
		for (int i=0; i<driverArray.size(); i++) {
			Driver object = driverArray.get(i);
			LatLng p = new LatLng(object.latitude, object.longitude);
			OverlayOptions ooA = new MarkerOptions().position(p).icon(mMark)
					.zIndex(9).draggable(true);
			mBaiduMap.addOverlay(ooA);
		}
		
//		String latitude = prefs.getLatitude();
//		String longitude = prefs.getLongitude();
//		LatLng p = new LatLng(Float.valueOf(latitude), Float.valueOf(longitude));
//		OverlayOptions ooA = new MarkerOptions().position(p).icon(mMark)
//				.zIndex(9).draggable(true);
//		mBaiduMap.addOverlay(ooA);
	}
	
	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(parent, "抱歉，未能找到结果", Toast.LENGTH_LONG)	.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_map_mark)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		String strInfo = String.format("纬度：%f 经度：%f",
				result.getLocation().latitude, result.getLocation().longitude);
		Toast.makeText(parent, strInfo, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(parent, "抱歉，未能找到结果", Toast.LENGTH_LONG)
					.show();
			return;
		}
		AppDataUtilities.sharedInstance().CurrentAddress = result.getAddress();
//		Toast.makeText(parent, result.getAddress(),	Toast.LENGTH_LONG).show();
	}
}
