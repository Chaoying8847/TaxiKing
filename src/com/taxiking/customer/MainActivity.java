package com.taxiking.customer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.taxiking.customer.fragment.MapFragment;
import com.taxiking.customer.fragment.MoreFragment;
import com.taxiking.customer.fragment.OrderFragment;
import com.taxiking.customer.fragment.PriceListFragment;
import com.taxiking.customer.utils.AppConstants;

public class MainActivity extends BaseRightMenuActivity implements OnClickListener {
	public static MainActivity instance = null;

	// Data
	private int mCurrentFragmentIndex = AppConstants.SW_FRAGMENT_MORE;
	public static boolean isBackPressed = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		instance = this;
		getLayoutInflater().inflate(R.layout.slidemenu_main_left, mSlideMenu, true);

		// action bar
		View img_app_icon = mSlideMenu.getPrimaryMenu().findViewById(R.id.img_app_icon);
		img_app_icon.setOnClickListener(this);

		android.app.ActionBar actionBar = this.getActionBar();
		if (actionBar != null) {
			actionBar.setIcon(R.drawable.ic_menu_list);
			
//			actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_list);
//			actionBar.setIcon(getResources().getDrawable(R.drawable.ic_menu_list));
		}

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

		/*
		 * show home first
		 */
		SwitchContent(AppConstants.SW_FRAGMENT_HOME, null);
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.activity_main;
	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
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
		super.onBackPressed();
//		onBackButtonPressed();
	}

	boolean isBackAllowed = false;
	private void onBackButtonPressed() {
		if (mSlideMenu.isOpen()) {
			mSlideMenu.close(true);
			return;
		} else {
			if (mCurrentFragmentIndex != AppConstants.SW_FRAGMENT_HOME) {
				SwitchContent(AppConstants.SW_FRAGMENT_HOME, null);
				return;
			}
		}

		if(!isBackAllowed) {
			Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();
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
			SwitchContent(AppConstants.SW_FRAGMENT_ORDER, null);
			break;

		case R.id.layout_price_list:
			SwitchContent(AppConstants.SW_FRAGMENT_PRICE_LIST, null);
			break;

		case R.id.layout_more:
			SwitchContent(AppConstants.SW_FRAGMENT_MORE, null);
			break;

		case R.id.layout_logout:
			finish();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);	
			break;
		}
	}

	public void SwitchContent(int fragment_index, Bundle bundle) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		if (mCurrentFragmentIndex != fragment_index) {
			mCurrentFragmentIndex = fragment_index;
			if (mCurrentFragmentIndex == AppConstants.SW_FRAGMENT_HOME) {
				fragment = MapFragment.newInstance();
			} else if (mCurrentFragmentIndex == AppConstants.SW_FRAGMENT_ORDER) {
				fragment = OrderFragment.newInstance();  
			} else if (mCurrentFragmentIndex == AppConstants.SW_FRAGMENT_PRICE_LIST) {
				fragment = PriceListFragment.newInstance();
			} else if (mCurrentFragmentIndex == AppConstants.SW_FRAGMENT_MORE) {
				fragment = MoreFragment.newInstance();
			}

			if (fragment != null) {
				try {
					if (bundle != null)
						fragment.setArguments(bundle);

					FragmentManager fragmentManager = this.getSupportFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.view_body, fragment).commit();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
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
}

//public class MainActivity	extends BaseFragmentActivity
//							implements OnCheckedChangeListener, OnClickListener {
//
//	private RadioButton radioOpen;
//	private RadioButton radioSubmmited;
//	private RadioButton radioClosed;
//	private Button btnRefresh;
//	
//	private TicketFragment ticketFragment;
//
//	public AppPreferences appPreference;
//	public static MainActivity mainActivity; 
//	
//	private TempReceiver mReceiver;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//
//		radioOpen = (RadioButton)findViewById(R.id.radio_open);
//		radioSubmmited = (RadioButton)findViewById(R.id.radio_submitted);
//		radioClosed = (RadioButton)findViewById(R.id.radio_closed);
//		btnRefresh = (Button)findViewById(R.id.btn_refresh);
//		
//		radioOpen.setOnCheckedChangeListener(this);
//		radioSubmmited.setOnCheckedChangeListener(this);
//		radioClosed.setOnCheckedChangeListener(this);
//		btnRefresh.setOnClickListener(this);
//
//		ticketFragment = TicketFragment.newInstance();
//		
//		appPreference = new AppPreferences(this);
//		appPreference.setWillTicketSubmit(false);
//
//		mainActivity = this;
//
//		setTabClickable();
//		loadDataFromServer();
//
//		mReceiver = new TempReceiver();
//	}
//
//	@Override
//	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		if(isChecked) {
//			switch (buttonView.getId()) {
//			case R.id.radio_open:
//				showFragment(Ticket.OPEN_TICKET);
//				break;
//			case R.id.radio_submitted:
//				showFragment(Ticket.SUB_TICKET);
//				break;
//			case R.id.radio_closed:
//				showFragment(Ticket.CLOSED_TICKET);
//				break;
//			default:
//				break;
//			}
//		}
//	}
//
//	public void setTabClickable() {
//		if (appPreference.isTicketSubmitted()) {
//			radioOpen.setClickable(true);
//			radioSubmmited.setClickable(true);
//			radioClosed.setClickable(true);
//			btnRefresh.setClickable(true);
//		} else {
//			radioOpen.setClickable(false);
//			radioSubmmited.setClickable(false);
//			radioClosed.setClickable(false);
//			btnRefresh.setClickable(false);
//		}
//	}
//
//	@Override
//	public void onBackPressed() {
//		final WaitDialog waitDialog = new WaitDialog(this);
//		waitDialog.show();
//		final APIUtil api = new APIUtil();
//		api.logout(new APIListener(){
//			@Override
//			public void onResult(Object ret, int err) {
//				appPreference.setNoLogin(true);
//				waitDialog.cancel();
//				finish();
//			}
//		});
//		super.onBackPressed();
//	}
//	
//	@Override
//	protected void onResume() {
//		super.onResume();
//		registerReceiver(mReceiver, new IntentFilter(AppConstants.REFRESH_SCREEN));
//	}
//	
//	@Override
//	protected void onPause() {
//		super.onPause();
//		unregisterReceiver(mReceiver);
//	}
//
//	@Override
//	public void onClick(View v) {
//		int id = v.getId();
//		switch (id) {
//		case R.id.btn_refresh:
//			GeoLocationService.saveLocation();
//
//			if (radioOpen.isChecked()) {
//				refreshFragmentTicketOpens();
//			} else if (radioSubmmited.isChecked()) {
//				refreshFragmentTicketSubs();
//			} else if (radioClosed.isChecked()) {
//				refreshFragmentTicketCloseds();
//			}
//			break;
//		default:
//			break;
//		}
//		
//	}
//
//	public void showFragment(int ticketType) {
//		ticketFragment.setTicketType(ticketType);
//		ticketFragment.notifyDataSetChanged();
//		showFragment(ticketFragment, false, false);
//	}
//
//	public void refreshFragmentTicketOpens() {
//		final WaitDialog waitDialog = new WaitDialog(this);
//		waitDialog.show();
//		final APIUtil api = new APIUtil();
//		api.getTicketOpen(new APIListener() {
//			@Override
//			public void onResult(Object ret, int err) {
//				switch (err) {
//				case AppConstants.ERR_OK:
//					ticketFragment.setOpenData(api.ticketOpens);
//					ticketFragment.notifyDataSetChanged();
//					break;
//				case AppConstants.ERR_NODATA:
//					ticketFragment.setOpenData(null);
//					ticketFragment.notifyDataSetChanged();
//					break;
//				case AppConstants.ERR_CLIENT_NETWORK:
//					Toast.makeText(MainActivity.this, "Please check network status", Toast.LENGTH_LONG).show();
//					break;
//				}
//				waitDialog.cancel();
//			}
//		});
//	}
//
//	public void refreshFragmentTicketSubs() {
//		final WaitDialog waitDialog = new WaitDialog(MainActivity.this);
//		waitDialog.show();
//		final APIUtil api = new APIUtil();
//		api.getTicketSub(new APIListener() {
//			@Override
//			public void onResult(Object ret, int err) {
//				switch (err) {
//				case AppConstants.ERR_OK:
//					ticketFragment.setSubData(api.ticketSubs);
//					ticketFragment.notifyDataSetChanged();
//					break;
//				case AppConstants.ERR_NODATA:
//					ticketFragment.setSubData(null);
//					ticketFragment.notifyDataSetChanged();
//					break;
//				case AppConstants.ERR_CLIENT_NETWORK:
//					Toast.makeText(MainActivity.this, "Please check network status", Toast.LENGTH_LONG).show();
//					break;
//				}
//				waitDialog.cancel();
//			}
//		});
//	}
//	
//	private void refreshFragmentTicketCloseds() {
//		final WaitDialog waitDialog = new WaitDialog(MainActivity.this);
//		waitDialog.show();
//		final APIUtil api = new APIUtil();
//		api.getTicketClosed(new APIListener() {
//			@Override
//			public void onResult(Object ret, int err) {
//				switch (err) {
//				case AppConstants.ERR_OK:
//					ticketFragment.setClosedData(api.ticketCloseds);
//					ticketFragment.notifyDataSetChanged();
//					break;
//				case AppConstants.ERR_NODATA:
//					ticketFragment.setClosedData(null);
//					ticketFragment.notifyDataSetChanged();
//					break;
//				case AppConstants.ERR_CLIENT_NETWORK:
//					Toast.makeText(MainActivity.this, "Please check network status", Toast.LENGTH_LONG).show();
//					break;
//				}
//				waitDialog.cancel();
//			}
//		});
//	}
//	
//	private void loadDataFromServer() {
//		final WaitDialog waitDialog = new WaitDialog(MainActivity.this);
//		waitDialog.show();
//		final APIUtil api = new APIUtil();
//		if (AppDeviceUtils.isOnline(this)) {
//			api.totalTicketFromUserId(new APIListener() {
//				@Override
//				public void onResult(Object ret, int err) {
//					switch (err) {
//					case AppConstants.ERR_OK:
//						ticketFragment.setOpenData(api.ticketOpens);
//						ticketFragment.setSubData(api.ticketSubs);
//						ticketFragment.setClosedData(api.ticketCloseds);
//						break;
//					case AppConstants.ERR_NODATA:
//					default:
//						ticketFragment.setOpenData(null);
//						break;
//					}
//					showFragment(Ticket.OPEN_TICKET);
//					waitDialog.cancel();
//				}
//			});
//		} else {
//			Toast.makeText(MainActivity.this, "Please check network status", Toast.LENGTH_LONG).show();
//			finish();
//		}
//	}
//
//	private class TempReceiver extends BroadcastReceiver {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//			
//			if (action.equals(AppConstants.REFRESH_SCREEN)) {
//				boolean isrefresh = intent.getBooleanExtra(AppConstants.HAS_SUBMITTED, false);
//				if (isrefresh && radioOpen.isChecked()) {
////					Log.d("debug", "refreshed");
//					refreshFragmentTicketOpens();
//				}
//			}
//		}
//	}
//	
//	@Override
//	protected void onDestroy() {
//		
//		stopService( new Intent(this, GeoLocationService.class));
//
//		stopService( new Intent(this, SubmitService.class));
//
//		super.onDestroy();
//	}
//}
