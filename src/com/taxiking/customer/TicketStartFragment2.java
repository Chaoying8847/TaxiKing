package com.taxiking.customer;

import java.util.HashMap;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.taxiking.customer.apiservice.APIUtil;
import com.taxiking.customer.apiservice.APIUtil.APIListener;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.model.TicketOpen;
import com.taxiking.customer.model.TicketSub;
import com.taxiking.customer.sqllite.DatabaseHandler;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.Utilities;
import com.taxiking.customer.utils.WaitDialog;

public final class TicketStartFragment2 extends BaseFragment {
//	private TicketSub mTicket;
	private Button btnContinue;
	private Button btnSave;
	private Button btnCancel;
	
	private TextView edit_date;
	private EditText edit_obs_gravity;
	private EditText edit_obs_temp;
	private EditText edit_s_w;
	private EditText edit_oil_temp_h;
	private EditText edit_feet_h;
	private EditText edit_oil_levels_h;
	private EditText edit_qrt_h;
	private EditText edit_tank_table_h;
	private EditText edit_tank_bottoms_h;
	private EditText edit_oil_temp_l;
	private EditText edit_feet_l;
	private EditText edit_oil_levels_l;
	private EditText edit_qrt_l;
	private EditText edit_tank_table_l;
	private EditText edit_tank_bottoms_l;
	private EditText edit_total_barrels;
	private EditText edit_net_barrels;
	private EditText edit_meter_off;
	private EditText edit_meter_on;	
	private EditText edit_loaded_miles;
	private EditText edit_meter_factor;
	private EditText edit_metered_barrels;
	private EditText edit_remarks;
	private EditText edit_rejected_reason;
	private EditText edit_seal_off;
	private EditText edit_seal_on;
	
	private TicketOpen mticket;
	private TicketSub newTicket;
	
	private DatabaseHandler dbHandler;
	
	public static TicketStartFragment2 newInstance(TicketOpen ticket) {
		TicketStartFragment2 fragment = new TicketStartFragment2();
		fragment.mticket = ticket;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_ticket_start2, null);
		
		btnContinue = (Button)rootview.findViewById(R.id.button_submit);
		btnSave = (Button)rootview.findViewById(R.id.button_save);
		btnCancel = (Button)rootview.findViewById(R.id.button_cancel);
		
		btnContinue.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
		edit_date = (TextView)rootview.findViewById(R.id.edit_date);
		edit_obs_gravity = (EditText)rootview.findViewById(R.id.edit_obs_gravity);
		edit_obs_temp = (EditText)rootview.findViewById(R.id.edit_obs_temp);
		edit_s_w = (EditText)rootview.findViewById(R.id.edit_s_w);
		edit_oil_temp_h = (EditText)rootview.findViewById(R.id.edit_oil_temp_h);
		edit_feet_h = (EditText)rootview.findViewById(R.id.edit_feet_h);
		edit_oil_levels_h = (EditText)rootview.findViewById(R.id.edit_oil_levels_h);
		edit_qrt_h = (EditText)rootview.findViewById(R.id.edit_qrt_h);
		edit_tank_table_h = (EditText)rootview.findViewById(R.id.edit_tank_table_h);
		edit_tank_bottoms_h = (EditText)rootview.findViewById(R.id.edit_tank_bottoms_h);
		edit_oil_temp_l = (EditText)rootview.findViewById(R.id.edit_oil_temp_l);
		edit_feet_l = (EditText)rootview.findViewById(R.id.edit_feet_l);
		edit_oil_levels_l = (EditText)rootview.findViewById(R.id.edit_oil_levels_l);
		edit_qrt_l = (EditText)rootview.findViewById(R.id.edit_qrt_l);
		edit_tank_table_l = (EditText)rootview.findViewById(R.id.edit_tank_table_l);
		edit_tank_bottoms_l = (EditText)rootview.findViewById(R.id.edit_tank_bottoms_l);
		edit_total_barrels = (EditText)rootview.findViewById(R.id.edit_total_barrels);
		edit_net_barrels = (EditText)rootview.findViewById(R.id.edit_net_barrels);
		edit_meter_off = (EditText)rootview.findViewById(R.id.edit_meter_off);
		edit_meter_on = (EditText)rootview.findViewById(R.id.edit_meter_on);
		edit_loaded_miles = (EditText)rootview.findViewById(R.id.edit_loaded_miles);
		edit_meter_factor = (EditText)rootview.findViewById(R.id.edit_meter_factor);
		edit_metered_barrels = (EditText)rootview.findViewById(R.id.edit_metered_barrels);
		edit_remarks = (EditText)rootview.findViewById(R.id.edit_remarks);
		edit_rejected_reason = (EditText)rootview.findViewById(R.id.edit_rejected_reason);
		edit_seal_off = (EditText)rootview.findViewById(R.id.edit_seal_off);
		edit_seal_on = (EditText)rootview.findViewById(R.id.edit_seal_on);
		
		TextWatcher textWatcher = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				float levelOff = 0;
				float levelOn = 0;
				try {
					levelOff = Float.parseFloat(edit_meter_off.getText().toString());
				} catch(NumberFormatException nfe) { }
				try {
					levelOn = Float.parseFloat(edit_meter_on.getText().toString());
				} catch(NumberFormatException nfe) { }
				
				String strDiff = String.format(("%.2f"), Math.abs(levelOff - levelOn));
				edit_metered_barrels.setText(strDiff);
				
				levelOff = 0;
				levelOn = 0;
				try {
					levelOff = Float.parseFloat(edit_tank_table_h.getText().toString());
				} catch(NumberFormatException nfe) { }
				try {
					levelOn = Float.parseFloat(edit_tank_table_l.getText().toString());
				} catch(NumberFormatException nfe) { }

				strDiff = String.format(("%.2f"), Math.abs(levelOff - levelOn));
				edit_total_barrels.setText(strDiff);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		};
		
		edit_meter_off.addTextChangedListener(textWatcher);
		edit_meter_on.addTextChangedListener(textWatcher);
		edit_tank_table_h.addTextChangedListener(textWatcher);
		edit_tank_table_l.addTextChangedListener(textWatcher);
		
		dbHandler = new DatabaseHandler(parent.getApplicationContext());
		edit_date.setText(Utilities.getDate());
		
		newTicket = dbHandler.get_Ticket(mticket.ticket_id);
		if (newTicket != null) {
			edit_obs_gravity.setText(newTicket.obsGravity);
			edit_obs_temp.setText(newTicket.obsTemp);
			edit_s_w.setText(newTicket.sw);
			edit_oil_temp_h.setText(newTicket.highDegreeF);
			edit_feet_h.setText(newTicket.highOilFeet);
			edit_oil_levels_h.setText(newTicket.highOilInch);
			edit_qrt_h.setText(newTicket.highOilQrt);
			edit_tank_table_h.setText(newTicket.hightankTableBarrels);
			edit_tank_bottoms_h.setText(newTicket.highTankBottoms);
			edit_oil_temp_l.setText(newTicket.lowDegreeF);
			edit_feet_l.setText(newTicket.lowOilFeet);
			edit_oil_levels_l.setText(newTicket.lowOilInch);
			edit_qrt_l.setText(newTicket.lowOilQrt);
			edit_tank_table_l.setText(newTicket.lowTankTableBarrels);
			edit_tank_bottoms_l.setText(newTicket.lowTankBottoms);
			edit_total_barrels.setText(newTicket.totalBarrels);
			edit_net_barrels.setText(newTicket.netBarrels);
			edit_meter_off.setText(newTicket.levelOff);
			edit_meter_on.setText(newTicket.levelOn);
			edit_loaded_miles.setText(newTicket.loadedMiles);
			edit_meter_factor.setText(newTicket.meterFactor);
			edit_metered_barrels.setText(newTicket.meteredBarrels);
			edit_remarks.setText(newTicket.remarks);
			edit_rejected_reason.setText(newTicket.comments);
			edit_seal_off.setText(newTicket.sealOff);
			edit_seal_on.setText(newTicket.sealOn);
			
			if (newTicket.noOil.equalsIgnoreCase("No Oil")) {
				edit_obs_gravity.setBackgroundColor(Color.WHITE);
				edit_obs_temp.setBackgroundColor(Color.WHITE);
				edit_s_w.setBackgroundColor(Color.WHITE);
				edit_oil_temp_h.setBackgroundColor(Color.WHITE);
				edit_oil_temp_l.setBackgroundColor(Color.WHITE);
				edit_net_barrels.setBackgroundColor(Color.WHITE);
				edit_meter_off.setBackgroundColor(Color.WHITE);
				edit_meter_on.setBackgroundColor(Color.WHITE);
				edit_loaded_miles.setBackgroundColor(Color.WHITE);
				edit_meter_factor.setBackgroundColor(Color.WHITE);
				edit_metered_barrels.setBackgroundColor(Color.WHITE);
			}
		}
		if (mticket != null) {
			edit_remarks.setText(mticket.comments);
		}
		return rootview;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
 
	@Override
	public void onClick(View v) {
		super.onClick(v);
		final String date			= edit_date.getText().toString();
		final String obs_gravity	= edit_obs_gravity.getText().toString();
		final String obs_temp		= edit_obs_temp.getText().toString();
		final String s_w			= edit_s_w.getText().toString();
		final String oil_temp_h		= edit_oil_temp_h.getText().toString();
		final String feet_h			= edit_feet_h.getText().toString();
		final String oil_levels_h	= edit_oil_levels_h.getText().toString();
		final String qrt_h			= edit_qrt_h.getText().toString();
		final String tank_table_h	= edit_tank_table_h.getText().toString();
		final String tank_bottoms_h	= edit_tank_bottoms_h.getText().toString();
		final String oil_temp_l		= edit_oil_temp_l.getText().toString();
		final String feet_l			= edit_feet_l.getText().toString();
		final String oil_levels_l	= edit_oil_levels_l.getText().toString();
		final String qrt_l			= edit_qrt_l.getText().toString();
		final String tank_table_l	= edit_tank_table_l.getText().toString();
		final String tank_bottoms_l	= edit_tank_bottoms_l.getText().toString();
		final String total_barrels	= edit_total_barrels.getText().toString();
		final String net_barrels	= edit_net_barrels.getText().toString();
		final String meter_off		= edit_meter_off.getText().toString();
		final String meter_on		= edit_meter_on.getText().toString();
		final String loaded_miles	= edit_loaded_miles.getText().toString();
		final String meter_factor	= edit_meter_factor.getText().toString();
		final String metered_barrels= edit_metered_barrels.getText().toString();
		final String remarks		= edit_remarks.getText().toString();
		final String rejected_reason= edit_rejected_reason.getText().toString();
		final String seal_off		= edit_seal_off.getText().toString();
		final String seal_on		= edit_seal_on.getText().toString();
		
		HashMap<String, String> map = new HashMap<String, String>();
		final APIUtil api = new APIUtil();
		switch (v.getId()) {
		case R.id.button_submit:
			if (!newTicket.noOil.equalsIgnoreCase("No Oil")) {
				if (obs_gravity.isEmpty()) {
					Toast.makeText(parent.getBaseContext(), "Please fill \"OBS GRAVITY\" field", Toast.LENGTH_LONG).show();
					break;
				} else if (obs_temp.isEmpty()) {
					Toast.makeText(parent.getBaseContext(), "Please fill \"OBS TEMP\" field", Toast.LENGTH_LONG).show();
					break;
				} else if (s_w.isEmpty()) {
					Toast.makeText(parent.getBaseContext(), "Please fill \"S W\" field", Toast.LENGTH_LONG).show();
					break;
				} else if (oil_temp_h.isEmpty()) {
					Toast.makeText(parent.getBaseContext(), "Please fill \"HIGH OIL TEMP\" field", Toast.LENGTH_LONG).show();
					break;
				} else if (oil_temp_l.isEmpty()) {
					Toast.makeText(parent.getBaseContext(), "Please fill \"LOW OIL TEMP\" field", Toast.LENGTH_LONG).show();
					break;
				} else if (loaded_miles.isEmpty()) {
					Toast.makeText(parent.getBaseContext(), "Please fill \"LOADED MILES\" field", Toast.LENGTH_LONG).show();
					break;
				}
			}
			
			map.clear();
			map.put(DatabaseHandler.KEY_DATE, date);
			map.put(DatabaseHandler.KEY_OBS_GRAVITY, obs_gravity);
			map.put(DatabaseHandler.KEY_OBS_TEMP, obs_temp);
			map.put(DatabaseHandler.KEY_SW, s_w);
			map.put(DatabaseHandler.KEY_HIGH_DEGREE_F, oil_temp_h);
			map.put(DatabaseHandler.KEY_HIGH_OIL_FEET, feet_h);
			map.put(DatabaseHandler.KEY_HIGH_OIL_INCH, oil_levels_h);
			map.put(DatabaseHandler.KEY_HIGH_OIL_QRT, qrt_h);
			map.put(DatabaseHandler.KEY_HIGH_TANK_TABLE_BARRELS, tank_table_h);
			map.put(DatabaseHandler.KEY_HIGH_TANK_BOTTOMS, tank_bottoms_h);
			map.put(DatabaseHandler.KEY_LOW_DEGREE_F, oil_temp_l);
			map.put(DatabaseHandler.KEY_LOW_OIL_FEET, feet_l);
			map.put(DatabaseHandler.KEY_LOW_OIL_INCH, oil_levels_l);
			map.put(DatabaseHandler.KEY_LOW_OIL_QRT, qrt_l);
			map.put(DatabaseHandler.KEY_LOW_TANK_TABLE_BARRELS, tank_table_l);
			map.put(DatabaseHandler.KEY_LOW_TANK_BOTTOMS, tank_bottoms_l);
			map.put(DatabaseHandler.KEY_TOTAL_BARRELS, total_barrels);
			map.put(DatabaseHandler.KEY_NET_BARRELS, net_barrels);
			map.put(DatabaseHandler.KEY_LEVEL_OFF, meter_off);
			map.put(DatabaseHandler.KEY_LEVEL_ON, meter_on);
			map.put(DatabaseHandler.KEY_LOADED_MILES, loaded_miles);
			map.put(DatabaseHandler.KEY_METER_FACTOR, meter_factor);
			map.put(DatabaseHandler.KEY_METERED_BARRELS, metered_barrels);
			map.put(DatabaseHandler.KEY_REMARKS, remarks);
			map.put(DatabaseHandler.KEY_COMMENTS, rejected_reason);
			map.put(DatabaseHandler.KEY_SEAL_OFF, seal_off);
			map.put(DatabaseHandler.KEY_SEAL_ON, seal_on);
			dbHandler.update_field(map, mticket.ticket_id);

			final WaitDialog waitDialog = new WaitDialog(parent.getBaseContext());
			waitDialog.show();
			api.submitTicket(new APIListener() {
				@Override
				public void onResult(Object ret, int err) {

					String msg="";
					switch (err) {
					case AppConstants.ERR_OK:
						dbHandler.delete_Ticket(mticket.ticket_id);
						msg = "Ticket was submitted successfully.";
						
						setDefaultSetting();
						MainActivity.mainActivity.refreshFragmentTicketSubs();
						MainActivity.mainActivity.refreshFragmentTicketOpens();
						waitDialog.cancel();
						Toast.makeText(parent.getBaseContext(), msg, Toast.LENGTH_LONG).show();
						parent.fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
						break;
					case AppConstants.ERR_CLIENT_NETWORK:
					default:
						HashMap<String, String> mapsubmit = new HashMap<String, String>();
						mapsubmit.put(DatabaseHandler.KEY_WILL_SUBMIT, String.valueOf(AppConstants.SQL_WILL_SUBMIT));
						dbHandler.update_field(mapsubmit, mticket.ticket_id);
						msg = "Network connection failed. Your ticket will submit at next connection automatically";
						
						setDefaultSetting();
						parent.fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//							MainActivity.mainActivity.showFragmentTicketOpens();
						waitDialog.cancel();
						Toast.makeText(parent.getBaseContext(), msg, Toast.LENGTH_LONG).show();
						break;
					}
					
				}
			}, dbHandler.get_Ticket(mticket.ticket_id), mticket.ticket_id);
			break;
		case R.id.button_save:
			map.clear();
			map.put(DatabaseHandler.KEY_OBS_GRAVITY, obs_gravity);
			map.put(DatabaseHandler.KEY_OBS_TEMP, obs_temp);
			map.put(DatabaseHandler.KEY_SW, s_w);
			map.put(DatabaseHandler.KEY_HIGH_DEGREE_F, oil_temp_h);
			map.put(DatabaseHandler.KEY_HIGH_OIL_FEET, feet_h);
			map.put(DatabaseHandler.KEY_HIGH_OIL_INCH, oil_levels_h);
			map.put(DatabaseHandler.KEY_HIGH_OIL_QRT, qrt_h);
			map.put(DatabaseHandler.KEY_HIGH_TANK_TABLE_BARRELS, tank_table_h);
			map.put(DatabaseHandler.KEY_HIGH_TANK_BOTTOMS, tank_bottoms_h);
			map.put(DatabaseHandler.KEY_LOW_DEGREE_F, oil_temp_l);
			map.put(DatabaseHandler.KEY_LOW_OIL_FEET, feet_l);
			map.put(DatabaseHandler.KEY_LOW_OIL_INCH, oil_levels_l);
			map.put(DatabaseHandler.KEY_LOW_OIL_QRT, qrt_l);
			map.put(DatabaseHandler.KEY_LOW_TANK_TABLE_BARRELS, tank_table_l);
			map.put(DatabaseHandler.KEY_LOW_TANK_BOTTOMS, tank_bottoms_l);
			map.put(DatabaseHandler.KEY_TOTAL_BARRELS, total_barrels);
			map.put(DatabaseHandler.KEY_NET_BARRELS, net_barrels);
			map.put(DatabaseHandler.KEY_LEVEL_OFF, meter_off);
			map.put(DatabaseHandler.KEY_LEVEL_ON, meter_on);
			map.put(DatabaseHandler.KEY_LOADED_MILES, loaded_miles);
			map.put(DatabaseHandler.KEY_METER_FACTOR, meter_factor);
			map.put(DatabaseHandler.KEY_METERED_BARRELS, metered_barrels);
			map.put(DatabaseHandler.KEY_REMARKS, remarks);
			map.put(DatabaseHandler.KEY_COMMENTS, rejected_reason);
			map.put(DatabaseHandler.KEY_SEAL_OFF, seal_off);
			map.put(DatabaseHandler.KEY_SEAL_ON, seal_on);
			
			dbHandler.update_field(map, mticket.ticket_id);
			Toast.makeText(parent.getBaseContext(), "Ticket saved successfully.", Toast.LENGTH_LONG).show();
			break;
		case R.id.button_cancel:
			parent.goBack();
//			api.statusChange(null, mticket.ticket_id, Ticket.OPEN_TICKET, Ticket.STATUS_OPEN);
			
//			setDefaultSetting();
//			parent.fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			break;
		default:
			break;
		}
	}
	
	private void setDefaultSetting() {
		parent.prefs.setWillTicketSubmit(false);
		parent.prefs.saveLastTicket(null);
		MainActivity.mainActivity.setTabClickable();
	}
}
