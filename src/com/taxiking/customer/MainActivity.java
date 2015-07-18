package com.taxiking.customer;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.taxiking.customer.apiservice.HttpApi;
import com.taxiking.customer.apiservice.HttpApi.METHOD;
import com.taxiking.customer.fragment.MapFragment;
import com.taxiking.customer.fragment.MoreFragment;
import com.taxiking.customer.fragment.OrderCompleteFragment;
import com.taxiking.customer.fragment.OrderHistoryFragment;
import com.taxiking.customer.fragment.OrderRequestFragment;
import com.taxiking.customer.fragment.OrderStatusCheckFragment;
import com.taxiking.customer.fragment.PriceListFragment;
import com.taxiking.customer.fragment.SendInfoFragment;
import com.taxiking.customer.fragment.ServiceRatingFragment;
import com.taxiking.customer.model.CurrentStatus;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppDataUtilities;
import com.taxiking.customer.utils.CommonUtil;
import com.taxiking.customer.utils.WaitDialog;

public class MainActivity extends BaseRightMenuActivity implements OnClickListener {
	public static MainActivity instance = null;
	private static Boolean shouldCallStatus;
	
	private WaitDialog waitDlg;
	private TextView mTitleTextView;

	private static final String LTAG = MainActivity.class.getSimpleName();
	private SDKReceiver mReceiver;
	// Data
	public int mCurrentFragmentIndex = AppConstants.SW_FRAGMENT_MORE;
	public static boolean isBackPressed = false;

	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			Log.d(LTAG, "action: " + s);
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				CommonUtil.showMessageDialog(MainActivity.this, getString(R.string.error), "百度SDK 验证出错");
			} else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				CommonUtil.showWaringDialog(MainActivity.this, getString(R.string.warning), getString(R.string.msg_network_error));
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		instance = this;
		getLayoutInflater().inflate(R.layout.slidemenu_main_left, mSlideMenu, true);

		// action bar
		View img_app_icon = mSlideMenu.getPrimaryMenu().findViewById(R.id.img_app_icon);
		img_app_icon.setOnClickListener(this);

		ActionBar mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);
		
		LinearLayout mCustomView = (LinearLayout)mInflater.inflate(R.layout.custom_actionbar, null);
		mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);

		Button menuButton = (Button) mCustomView.findViewById(R.id.btn_menu);
		menuButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mSlideMenu.isOpen()) {
					mSlideMenu.close(true);
				} else { 
					mSlideMenu.open(false, true);
				}
			}
		});

		mActionBar.setCustomView(mCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);
		mActionBar.setBackgroundDrawable(getResources().getDrawable(R.color.main_pink_color));

		// left menu
		View layout_home = mSlideMenu.getPrimaryMenu().findViewById(R.id.layout_home);
		View layout_order = mSlideMenu.getPrimaryMenu().findViewById(R.id.layout_order);
		View layout_price_list = mSlideMenu.getPrimaryMenu().findViewById(R.id.layout_price_list);
		View layout_more = mSlideMenu.getPrimaryMenu().findViewById(R.id.layout_more);
		View layout_logout = mSlideMenu.getPrimaryMenu().findViewById(R.id.layout_logout);

		layout_home.setOnClickListener(this);
		layout_order.setOnClickListener(this);
		layout_price_list.setOnClickListener(this);
		layout_more.setOnClickListener(this);
		layout_logout.setOnClickListener(this);
		
		waitDlg = new WaitDialog(this);
		
		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
		
		/*
		 * show home first
		 */
		if (AppDataUtilities.sharedInstance().status.state.equalsIgnoreCase("requested")) {
			SwitchContent(AppConstants.SW_FRAGMENT_ORDER_CHECK, null);
		} else if (AppDataUtilities.sharedInstance().status.state.equalsIgnoreCase("enorute")) {
			SwitchContent(AppConstants.SW_FRAGMENT_ORDER_COMPLETE, null);
		} else if (AppDataUtilities.sharedInstance().status.state.equalsIgnoreCase("finished")) {
			SwitchContent(AppConstants.SW_FRAGMENT_RATING, null);
		} else {
			SwitchContent(AppConstants.SW_FRAGMENT_HOME, null);
		}		
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.activity_main;
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitleTextView.setText(title);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case android.R.id.home:
			if (!mSlideMenu.isOpen())
				mSlideMenu.open(false, true);
			else
				mSlideMenu.close(true);
			return false;
		}
		return (super.onOptionsItemSelected(menuItem));
	}

	@Override
	public void onBackPressed() {
//		super.onBackPressed();
		onBackButtonPressed();
	}

	boolean isBackAllowed = false;
	private void onBackButtonPressed() {
		if (mSlideMenu.isOpen()) {
			mSlideMenu.close(true);
			return;
		}
	
		if (mCurrentFragmentIndex != AppConstants.SW_FRAGMENT_HOME) {
			SwitchContent(AppConstants.SW_FRAGMENT_HOME, null);
			return;
		}

		if(!isBackAllowed) {
			Toast.makeText(this, R.string.msg_exit_confirm, Toast.LENGTH_SHORT).show();
			isBackAllowed = true;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					isBackAllowed = false;
				}
			}, 2000);
		} else {
			finish();
		}
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.img_app_icon:
			if (mSlideMenu.isOpen())
				mSlideMenu.close(true);
			break;
		case R.id.layout_home:
			SwitchContent(AppConstants.SW_FRAGMENT_HOME, null);
			break;

		case R.id.layout_order:
			SwitchContent(AppConstants.SW_FRAGMENT_ORDER_HISTORY, null);
			break;

		case R.id.layout_price_list:
			SwitchContent(AppConstants.SW_FRAGMENT_PRICE_LIST, null);
			break;

		case R.id.layout_more:
			SwitchContent(AppConstants.SW_FRAGMENT_MORE, null);
			break;

		case R.id.layout_logout:
			prefs.setSession("");
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			break;
		}
	}

	public void SwitchContent(int fragment_index, Bundle bundle) {
		// update the main content by replacing fragments
		
		Fragment fragment = null;
		if (mCurrentFragmentIndex != fragment_index) {
			if (fragment_index == AppConstants.SW_FRAGMENT_HOME) {
				fragment = MapFragment.newInstance();
			} else if (fragment_index == AppConstants.SW_FRAGMENT_ORDER_REQUEST) {
				fragment = OrderRequestFragment.newInstance();  
			} else if (fragment_index == AppConstants.SW_FRAGMENT_ORDER_CHECK) {
				fragment = OrderStatusCheckFragment.newInstance();  
			} else if (fragment_index == AppConstants.SW_FRAGMENT_ORDER_COMPLETE) {
				fragment = OrderCompleteFragment.newInstance();
			} else if (fragment_index == AppConstants.SW_FRAGMENT_RATING) {
				fragment = ServiceRatingFragment.newInstance();
			} else if (fragment_index == AppConstants.SW_FRAGMENT_SEND_INFO) {
				fragment = SendInfoFragment.newInstance();
			} else if (fragment_index == AppConstants.SW_FRAGMENT_ORDER_HISTORY) {
				fragment = OrderHistoryFragment.newInstance();  
			} else if (fragment_index == AppConstants.SW_FRAGMENT_PRICE_LIST) {
				fragment = PriceListFragment.newInstance();
			} else if (fragment_index == AppConstants.SW_FRAGMENT_MORE) {
				fragment = MoreFragment.newInstance();
			}

			if (fragment != null) {
				if (fragment_index < AppConstants.SW_FRAGMENT_ORDER_HISTORY && fragment_index<mCurrentFragmentIndex) {
					popFragment(fragment, true);
				} else {
					showFragment(fragment, false, true);
				}
				
				mCurrentFragmentIndex = fragment_index;
			}
		}

		if (mSlideMenu.isOpen())
			mSlideMenu.close(true);
	}

	@Override
	protected void updateWithSlidingMenu() {
		super.updateWithSlidingMenu();

//		// rotate action bar's icon
//		Matrix homeIconMatrix = new Matrix();
//		ImageView homeIcon = (ImageView)findViewById(android.R.id.home);
//		homeIcon.setScaleType(ScaleType.MATRIX);   //required
//		homeIconMatrix.postRotate((float)(mOffsetPercent*360),
//				homeIcon.getDrawable().getBounds().width()/2,
//				homeIcon.getDrawable().getBounds().height()/2);
//		homeIcon.setImageMatrix(homeIconMatrix);
//
//		// rotate left slider's icon
//		Matrix leftSlidingMenuIconMatrix = new Matrix();
//		ImageView img_app_icon = (ImageView)mSlideMenu.getPrimaryMenu().findViewById(R.id.img_app_icon);
//		img_app_icon.setScaleType(ScaleType.MATRIX);   //required
//		leftSlidingMenuIconMatrix.postRotate((float)(mOffsetPercent*360),
//				img_app_icon.getDrawable().getBounds().width()/2,
//				img_app_icon.getDrawable().getBounds().height()/2);
//		img_app_icon.setImageMatrix(leftSlidingMenuIconMatrix);
	}

	@Override
	protected void onLeftMenuOpened() {
		super.onLeftMenuOpened();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		shouldCallStatus = true;
		callCurrentStatus();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		shouldCallStatus = false;
	}
	
	@Override
	protected void onDestroy() {
//		stopService( new Intent(this, GPSTracker.class));
		unregisterReceiver(mReceiver);
		
		super.onDestroy();
	}
	
	public void showWaitView() {
		waitDlg.show();
	}
	
	public void hideWaitView() {
		waitDlg.cancel();
	}

	private void callCurrentStatus() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (shouldCallStatus) {
					if (mCurrentFragmentIndex == AppConstants.SW_FRAGMENT_HOME) {
						new CheckStatusAsyncTask().execute();
					}
					callCurrentStatus();
				}
			}
		}, 5000);
	}
	
	public class CheckStatusAsyncTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));
		
			return HttpApi.callToJson(AppConstants.HOST_CURRENT_STATUS, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			try {
				String result = res.getString("result");
				if (result.equalsIgnoreCase("success")) {
					AppDataUtilities.sharedInstance().status = CurrentStatus.fromJSON(res);
					if (AppDataUtilities.sharedInstance().status.state.equals("requested")) {
						SwitchContent(AppConstants.SW_FRAGMENT_ORDER_CHECK, null);
					} else if (AppDataUtilities.sharedInstance().status.state.equals("enroute")) {
						SwitchContent(AppConstants.SW_FRAGMENT_ORDER_COMPLETE, null);
					} else if (AppDataUtilities.sharedInstance().status.state.equals("finished")) {
						SwitchContent(AppConstants.SW_FRAGMENT_RATING, null);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}