package com.taxiking.customer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Toast;

import com.taxiking.customer.apiservice.APIUtil;
import com.taxiking.customer.apiservice.APIUtil.APIListener;
import com.taxiking.customer.base.BaseFragmentActivity;
import com.taxiking.customer.bean.Ticket;
import com.taxiking.customer.service.GeoLocationService;
import com.taxiking.customer.service.SubmitService;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppDeviceUtils;
import com.taxiking.customer.utils.AppPreferences;
import com.taxiking.customer.utils.WaitDialog;

public class MainActivity	extends BaseFragmentActivity
							implements OnCheckedChangeListener, OnClickListener {

	private RadioButton radioOpen;
	private RadioButton radioSubmmited;
	private RadioButton radioClosed;
	private Button btnRefresh;
	
	private TicketFragment ticketFragment;

	public AppPreferences appPreference;
	public static MainActivity mainActivity; 
	
	private TempReceiver mReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		radioOpen = (RadioButton)findViewById(R.id.radio_open);
		radioSubmmited = (RadioButton)findViewById(R.id.radio_submitted);
		radioClosed = (RadioButton)findViewById(R.id.radio_closed);
		btnRefresh = (Button)findViewById(R.id.btn_refresh);
		
		radioOpen.setOnCheckedChangeListener(this);
		radioSubmmited.setOnCheckedChangeListener(this);
		radioClosed.setOnCheckedChangeListener(this);
		btnRefresh.setOnClickListener(this);

		ticketFragment = TicketFragment.newInstance();
		
		appPreference = new AppPreferences(this);
		appPreference.setWillTicketSubmit(false);

		mainActivity = this;

		setTabClickable();
		loadDataFromServer();

		mReceiver = new TempReceiver();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked) {
			switch (buttonView.getId()) {
			case R.id.radio_open:
				showFragment(Ticket.OPEN_TICKET);
				break;
			case R.id.radio_submitted:
				showFragment(Ticket.SUB_TICKET);
				break;
			case R.id.radio_closed:
				showFragment(Ticket.CLOSED_TICKET);
				break;
			default:
				break;
			}
		}
	}

	public void setTabClickable() {
		if (appPreference.isTicketSubmitted()) {
			radioOpen.setClickable(true);
			radioSubmmited.setClickable(true);
			radioClosed.setClickable(true);
			btnRefresh.setClickable(true);
		} else {
			radioOpen.setClickable(false);
			radioSubmmited.setClickable(false);
			radioClosed.setClickable(false);
			btnRefresh.setClickable(false);
		}
	}

	@Override
	public void onBackPressed() {
		final WaitDialog waitDialog = new WaitDialog(this);
		waitDialog.show();
		final APIUtil api = new APIUtil();
		api.logout(new APIListener(){
			@Override
			public void onResult(Object ret, int err) {
				appPreference.setNoLogin(true);
				waitDialog.cancel();
				finish();
			}
		});
		super.onBackPressed();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(mReceiver, new IntentFilter(AppConstants.REFRESH_SCREEN));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mReceiver);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_refresh:
			GeoLocationService.saveLocation();

			if (radioOpen.isChecked()) {
				refreshFragmentTicketOpens();
			} else if (radioSubmmited.isChecked()) {
				refreshFragmentTicketSubs();
			} else if (radioClosed.isChecked()) {
				refreshFragmentTicketCloseds();
			}
			break;
		default:
			break;
		}
		
	}

	public void showFragment(int ticketType) {
		ticketFragment.setTicketType(ticketType);
		ticketFragment.notifyDataSetChanged();
		showFragment(ticketFragment, false, false);
	}

	public void refreshFragmentTicketOpens() {
		final WaitDialog waitDialog = new WaitDialog(this);
		waitDialog.show();
		final APIUtil api = new APIUtil();
		api.getTicketOpen(new APIListener() {
			@Override
			public void onResult(Object ret, int err) {
				switch (err) {
				case AppConstants.ERR_OK:
					ticketFragment.setOpenData(api.ticketOpens);
					ticketFragment.notifyDataSetChanged();
					break;
				case AppConstants.ERR_NODATA:
					ticketFragment.setOpenData(null);
					ticketFragment.notifyDataSetChanged();
					break;
				case AppConstants.ERR_CLIENT_NETWORK:
					Toast.makeText(MainActivity.this, "Please check network status", Toast.LENGTH_LONG).show();
					break;
				}
				waitDialog.cancel();
			}
		});
	}

	public void refreshFragmentTicketSubs() {
		final WaitDialog waitDialog = new WaitDialog(MainActivity.this);
		waitDialog.show();
		final APIUtil api = new APIUtil();
		api.getTicketSub(new APIListener() {
			@Override
			public void onResult(Object ret, int err) {
				switch (err) {
				case AppConstants.ERR_OK:
					ticketFragment.setSubData(api.ticketSubs);
					ticketFragment.notifyDataSetChanged();
					break;
				case AppConstants.ERR_NODATA:
					ticketFragment.setSubData(null);
					ticketFragment.notifyDataSetChanged();
					break;
				case AppConstants.ERR_CLIENT_NETWORK:
					Toast.makeText(MainActivity.this, "Please check network status", Toast.LENGTH_LONG).show();
					break;
				}
				waitDialog.cancel();
			}
		});
	}
	
	private void refreshFragmentTicketCloseds() {
		final WaitDialog waitDialog = new WaitDialog(MainActivity.this);
		waitDialog.show();
		final APIUtil api = new APIUtil();
		api.getTicketClosed(new APIListener() {
			@Override
			public void onResult(Object ret, int err) {
				switch (err) {
				case AppConstants.ERR_OK:
					ticketFragment.setClosedData(api.ticketCloseds);
					ticketFragment.notifyDataSetChanged();
					break;
				case AppConstants.ERR_NODATA:
					ticketFragment.setClosedData(null);
					ticketFragment.notifyDataSetChanged();
					break;
				case AppConstants.ERR_CLIENT_NETWORK:
					Toast.makeText(MainActivity.this, "Please check network status", Toast.LENGTH_LONG).show();
					break;
				}
				waitDialog.cancel();
			}
		});
	}
	
	private void loadDataFromServer() {
		final WaitDialog waitDialog = new WaitDialog(MainActivity.this);
		waitDialog.show();
		final APIUtil api = new APIUtil();
		if (AppDeviceUtils.isOnline(this)) {
			api.totalTicketFromUserId(new APIListener() {
				@Override
				public void onResult(Object ret, int err) {
					switch (err) {
					case AppConstants.ERR_OK:
						ticketFragment.setOpenData(api.ticketOpens);
						ticketFragment.setSubData(api.ticketSubs);
						ticketFragment.setClosedData(api.ticketCloseds);
						break;
					case AppConstants.ERR_NODATA:
					default:
						ticketFragment.setOpenData(null);
						break;
					}
					showFragment(Ticket.OPEN_TICKET);
					waitDialog.cancel();
				}
			});
		} else {
			Toast.makeText(MainActivity.this, "Please check network status", Toast.LENGTH_LONG).show();
			finish();
		}
	}

	private class TempReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			
			if (action.equals(AppConstants.REFRESH_SCREEN)) {
				boolean isrefresh = intent.getBooleanExtra(AppConstants.HAS_SUBMITTED, false);
				if (isrefresh && radioOpen.isChecked()) {
//					Log.d("debug", "refreshed");
					refreshFragmentTicketOpens();
				}
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		
		stopService( new Intent(this, GeoLocationService.class));

		stopService( new Intent(this, SubmitService.class));

		super.onDestroy();
	}
}
