package com.taxiking.customer;

import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.taxiking.customer.apiservice.APIUtil;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.bean.Ticket;
import com.taxiking.customer.model.TicketOpen;
import com.taxiking.customer.model.TicketSub;
import com.taxiking.customer.sqllite.DatabaseHandler;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppPreferences;

public final class TicketStartFragment extends BaseFragment {
	private EditText edit_estimated_barrels;
	private EditText edit_lease_number;
	private EditText edit_operator;
	private EditText edit_station_number;
	private EditText edit_lease;
	private EditText edit_station_name;
	private EditText edit_county;
	private EditText edit_lease_legal;
	private EditText edit_name_receiver;
	private EditText edit_tractor_number;
	private TextView edit_driver_name;
	private EditText edit_trailer_number;
	private EditText edit_tank_number;
	private EditText edit_ticket_number;
	
	private EditText edit_tank_size;
	private EditText edit_tank_height;
	private EditText edit_for_account;
//	private Spinner spinner_for_account;
	private Spinner spinner_state;
	private CheckBox checkbox_no_oil;

	private TicketOpen mticket;
	private Button btnContinue;
	private Button btnSave;
	private Button btnCancel;
	
	DatabaseHandler dbHandler;
	
	
	public static TicketStartFragment newInstance(TicketOpen ticket) {
		TicketStartFragment fragment = new TicketStartFragment();
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

		View rootview = inflater.inflate(R.layout.fragment_ticket_start1, null);
		parent.prefs = new AppPreferences(this.parent.getApplicationContext());
		parent.prefs.setWillTicketSubmit(true);
		MainActivity.mainActivity.setTabClickable();
		
		edit_estimated_barrels = (EditText)rootview.findViewById(R.id.edit_estimated_barrels);
		edit_lease_number = (EditText)rootview.findViewById(R.id.edit_lease_number);
		edit_operator = (EditText)rootview.findViewById(R.id.edit_operator);
		edit_station_number = (EditText)rootview.findViewById(R.id.edit_station_number);
		edit_lease = (EditText)rootview.findViewById(R.id.edit_lease);
		edit_station_name = (EditText)rootview.findViewById(R.id.edit_station_name);
		edit_county = (EditText)rootview.findViewById(R.id.edit_county);
		edit_lease_legal = (EditText)rootview.findViewById(R.id.edit_lease_legal);
		edit_name_receiver = (EditText)rootview.findViewById(R.id.edit_name_receiver);
		edit_tractor_number = (EditText)rootview.findViewById(R.id.edit_tractor_number);
		edit_driver_name = (TextView)rootview.findViewById(R.id.edit_driver_name);
		edit_trailer_number = (EditText)rootview.findViewById(R.id.edit_trailer_number);
		edit_tank_number = (EditText)rootview.findViewById(R.id.edit_tank_number);
		edit_ticket_number = (EditText)rootview.findViewById(R.id.edit_ticket_number);
		edit_for_account = (EditText)rootview.findViewById(R.id.edit_for_account);

		edit_tank_size = (EditText)rootview.findViewById(R.id.edit_tank_size);
		edit_tank_height = (EditText)rootview.findViewById(R.id.edit_tank_height);
//		spinner_for_account = (Spinner)rootview.findViewById(R.id.spinner_for_account);
//		ArrayAdapter<String> adapterForAccount = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, AppConstants.ACCOUNT_TYPE);
//		spinner_for_account.setAdapter(adapterForAccount);
		
		spinner_state = (Spinner)rootview.findViewById(R.id.spinner_state);
		ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, AppConstants.STATE_TYPE);
		spinner_state.setAdapter(adapterState);
		
		checkbox_no_oil = (CheckBox)rootview.findViewById(R.id.checkbox_no_oil);

		dbHandler = new DatabaseHandler(parent.getApplicationContext());
		
		TicketSub newTicket = dbHandler.get_Ticket(mticket.ticket_id);
		edit_estimated_barrels.setText(newTicket.estimatedBarrels);
		edit_lease_number.setText(newTicket.leaseNo);
		edit_operator.setText(newTicket.opFieldLoc);
		edit_station_number.setText(newTicket.stationNo);
		edit_station_name.setText(newTicket.stationName);
		edit_county.setText(newTicket.county);
		edit_lease_legal.setText(newTicket.leaseLegalDesc);
		edit_tractor_number.setText(newTicket.tractorNo);
		edit_driver_name.setText(parent.prefs.getFirstName() + " " + parent.prefs.getLastName());
		edit_trailer_number.setText(newTicket.trailerNo);
		edit_tank_number.setText(newTicket.tankNo);
		edit_ticket_number.setText(newTicket.ticketNo);
		edit_lease.setText(newTicket.leaseCompName);
		edit_name_receiver.setText(newTicket.nameReceiver);
		
		edit_tank_size.setText(newTicket.tankSize);
		edit_tank_height.setText(newTicket.tankHeight);
		if (newTicket.noOil.equalsIgnoreCase("No Oil"))
			checkbox_no_oil.setChecked(true);
		if (mticket != null) {
			edit_lease_number.setText(mticket.facilityID);
			edit_lease.setText(mticket.lease);
			edit_name_receiver.setText(mticket.delivLoc);
			edit_ticket_number.setText(mticket.loadNo);
			edit_driver_name.setText(parent.prefs.getFirstName() + " " + parent.prefs.getLastName());
			parent.prefs.saveLastTicket(mticket.ticket_id);
			edit_for_account.setText(mticket.forAccountOf);
		}

//		spinner_for_account.setSelection(getIndexForAccount(newTicket.forAccountOf));
		spinner_state.setSelection(getIndexOfState(newTicket.state));
		
		btnContinue = (Button)rootview.findViewById(R.id.button_continue);
		btnSave = (Button)rootview.findViewById(R.id.button_save);
		btnCancel = (Button)rootview.findViewById(R.id.button_cancel);
		
		btnContinue.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		return rootview;
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		TicketSub newTicket = new TicketSub();
		final String estimatedBarrels = edit_estimated_barrels.getText().toString();
		final String leaseNo = edit_lease_number.getText().toString();
		final String opFieldLoc = edit_operator.getText().toString();
		final String stationNo = edit_station_number.getText().toString();
		final String leaseCompName = edit_lease.getText().toString();
		final String stationName = edit_station_name.getText().toString();
		final String county = edit_county.getText().toString();
		final String leaseLegalDesc = edit_lease_legal.getText().toString();
		final String nameReceiver = edit_name_receiver.getText().toString();
		final String tractorNo = edit_tractor_number.getText().toString();
		final String trailerNo = edit_trailer_number.getText().toString();
		final String tankNo = edit_tank_number.getText().toString();
		final String ticketNo = edit_ticket_number.getText().toString();
		final String noOil = (checkbox_no_oil.isChecked() ? "No Oil" : "");
		
		final String forAccountOf = edit_for_account.getText().toString();
		final String state = AppConstants.STATE_TYPE[spinner_state.getSelectedItemPosition()];

		final String tankSize = edit_tank_size.getText().toString();
		final String tankHeight = edit_tank_height.getText().toString();
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		switch (v.getId()) {
		case R.id.button_continue:
			if (estimatedBarrels.isEmpty()) {
				Toast.makeText(parent.getBaseContext(), "Please fill \"ESTIMATEDBARRELS\" field", Toast.LENGTH_LONG).show();
				break;
			}
			if(leaseNo.isEmpty()) {
				Toast.makeText(parent.getBaseContext(), "Please fill \"LEASENUMBER\" field", Toast.LENGTH_LONG).show();
				break;
			}
			else if(opFieldLoc.isEmpty()) {
				Toast.makeText(parent.getBaseContext(), "Please fill \"OPERATOR ON FIELD LOCATION\" field", Toast.LENGTH_LONG).show();
				break;
			}
			else if(leaseCompName.isEmpty()) {
				Toast.makeText(parent.getBaseContext(), "Please fill \"LEASE OR COMPANY NAME\" field", Toast.LENGTH_LONG).show();
				break;
			}
			else if(nameReceiver.isEmpty()) {
				Toast.makeText(parent.getBaseContext(), "Please fill \"NAME OF RECEIVER\" field", Toast.LENGTH_LONG).show();
				break;
			}
			else if(tractorNo.isEmpty()) {
				Toast.makeText(parent.getBaseContext(), "Please fill \"TRACTOR NUMBER\" field", Toast.LENGTH_LONG).show();
				break;
			}
			else if(trailerNo.isEmpty()) {
				Toast.makeText(parent.getBaseContext(), "Please fill \"TRAILER NUMBER\" field", Toast.LENGTH_LONG).show();
				break;
			}else if(ticketNo.isEmpty()) {
				Toast.makeText(parent.getBaseContext(), "Please fill \"TICKET NUMBER\" field", Toast.LENGTH_LONG).show();
				break;
			}
			else {
				if (!dbHandler.hasRow(mticket.ticket_id)) {
					newTicket.estimatedBarrels	= estimatedBarrels;
					newTicket.leaseNo			= leaseNo;
					newTicket.opFieldLoc		= opFieldLoc;
					newTicket.stationNo			= stationNo;
					newTicket.leaseCompName		= leaseCompName;
					newTicket.stationName		= stationName;
					newTicket.forAccountOf		= forAccountOf;
					newTicket.county			= county;
					newTicket.leaseLegalDesc	= leaseLegalDesc;
					newTicket.state 			= state;
					newTicket.nameReceiver 		= nameReceiver;
					newTicket.tractorNo 		= tractorNo;
					newTicket.trailerNo 		= trailerNo;
					newTicket.tankNo 			= tankNo;
					
					newTicket.tankSize			= tankSize;
					newTicket.tankHeight		= tankHeight;
					
					newTicket.ticketNo 			= ticketNo;
					newTicket.userID 			= String.valueOf(parent.prefs.getAccountId());
					newTicket.firstName			= parent.prefs.getFirstName();
					newTicket.lastName			= parent.prefs.getLastName();
					newTicket.noOil				= noOil;
					dbHandler.add_Ticket(newTicket, mticket.ticket_id, AppConstants.SQL_NOT_SUBMIT);
				} else {
					map.clear();
					map.put(DatabaseHandler.KEY_ESTIMATED_BARRELS, estimatedBarrels);
					map.put(DatabaseHandler.KEY_LEASE_NO, leaseNo);
					map.put(DatabaseHandler.KEY_OP_FIELD_LOC, opFieldLoc);
					map.put(DatabaseHandler.KEY_STATION_NO, stationNo);
					map.put(DatabaseHandler.KEY_LEASE_COMP_NAME, leaseCompName);
					map.put(DatabaseHandler.KEY_STATION_NAME, stationName);
					map.put(DatabaseHandler.KEY_FOR_ACCOUNT_OF, forAccountOf);
					map.put(DatabaseHandler.KEY_COUNTY, county);
					map.put(DatabaseHandler.KEY_STATE, state);
					map.put(DatabaseHandler.KEY_NAME_RECEIVER, nameReceiver);
					map.put(DatabaseHandler.KEY_TRACTOR_NO, tractorNo);
					map.put(DatabaseHandler.KEY_TRAILER_NO, trailerNo);
					map.put(DatabaseHandler.KEY_TANK_NO, tankNo);
					map.put(DatabaseHandler.KEY_TANK_SIZE, tankSize);
					map.put(DatabaseHandler.KEY_TANK_HEIGHT, tankHeight);
					map.put(DatabaseHandler.KEY_TICKET_NO, ticketNo);
					map.put(DatabaseHandler.KEY_USER_ID, String.valueOf(parent.prefs.getAccountId()));
					map.put(DatabaseHandler.KEY_FIRST_NAME, parent.prefs.getFirstName());
					map.put(DatabaseHandler.KEY_LAST_NAME, parent.prefs.getLastName());
					map.put(DatabaseHandler.KEY_NO_OIL, noOil);
					dbHandler.update_field(map, mticket.ticket_id);
				}

				parent.showFragment(TicketStartFragment2.newInstance(mticket), true);
			}
			break;
		case R.id.button_save:
			if (!dbHandler.hasRow(mticket.ticket_id)) {
				newTicket.estimatedBarrels	= estimatedBarrels;
				newTicket.leaseNo			= leaseNo;
				newTicket.opFieldLoc		= opFieldLoc;
				newTicket.stationNo			= stationNo;
				newTicket.leaseCompName		= leaseCompName;
				newTicket.stationName		= stationName;
				newTicket.forAccountOf		= forAccountOf;
				newTicket.county			= county;
				newTicket.leaseLegalDesc	= leaseLegalDesc;
				newTicket.state 			= state;
				newTicket.nameReceiver 		= nameReceiver;
				newTicket.tractorNo 		= tractorNo;
				newTicket.trailerNo 		= trailerNo;
				newTicket.tankNo 			= tankNo;
				newTicket.tankSize 			= tankSize;
				newTicket.tankHeight		= tankHeight;
				newTicket.ticketNo 			= ticketNo;
				newTicket.userID 			= String.valueOf(parent.prefs.getAccountId());
				newTicket.firstName			= parent.prefs.getFirstName();
				newTicket.lastName			= parent.prefs.getLastName();
				newTicket.noOil				= noOil;
				dbHandler.add_Ticket(newTicket, mticket.ticket_id, AppConstants.SQL_NOT_SUBMIT);
			} else {
				map.clear();
				map.put(DatabaseHandler.KEY_ESTIMATED_BARRELS, estimatedBarrels);
				map.put(DatabaseHandler.KEY_LEASE_NO, leaseNo);
				map.put(DatabaseHandler.KEY_OP_FIELD_LOC, opFieldLoc);
				map.put(DatabaseHandler.KEY_STATION_NO, stationNo);
				map.put(DatabaseHandler.KEY_LEASE_COMP_NAME, leaseCompName);
				map.put(DatabaseHandler.KEY_STATION_NAME, stationName);
				map.put(DatabaseHandler.KEY_FOR_ACCOUNT_OF, forAccountOf);
				map.put(DatabaseHandler.KEY_COUNTY, county);
				map.put(DatabaseHandler.KEY_STATE, state);
				map.put(DatabaseHandler.KEY_NAME_RECEIVER, nameReceiver);
				map.put(DatabaseHandler.KEY_TRACTOR_NO, tractorNo);
				map.put(DatabaseHandler.KEY_TRAILER_NO, trailerNo);
				map.put(DatabaseHandler.KEY_TANK_NO, tankNo);
				map.put(DatabaseHandler.KEY_TANK_SIZE, tankSize);
				map.put(DatabaseHandler.KEY_TANK_HEIGHT, tankHeight);
				map.put(DatabaseHandler.KEY_TICKET_NO, ticketNo);
				map.put(DatabaseHandler.KEY_USER_ID, String.valueOf(parent.prefs.getAccountId()));
				map.put(DatabaseHandler.KEY_FIRST_NAME, parent.prefs.getFirstName());
				map.put(DatabaseHandler.KEY_LAST_NAME, parent.prefs.getLastName());
				map.put(DatabaseHandler.KEY_NO_OIL, noOil);
				dbHandler.update_field(map, mticket.ticket_id);
			}
			Toast.makeText(parent.getBaseContext(), "Ticket saved successfully.", Toast.LENGTH_LONG).show();
			break;
		case R.id.button_cancel:
			parent.prefs.setWillTicketSubmit(false);
			parent.prefs.saveLastTicket(null);
			final APIUtil api = new APIUtil();
			api.statusChange(null, mticket.ticket_id, Ticket.OPEN_TICKET, Ticket.STATUS_OPEN);
			
			MainActivity.mainActivity.setTabClickable();
			parent.goBack();
			break;
		default:
			break;
		}
	}
	
//	private int getIndexForAccount(String forAccount) {
//		int index = 0;
//		for (int i=0; i<AppConstants.ACCOUNT_TYPE.length;i++) {
//			if (forAccount.equalsIgnoreCase(AppConstants.ACCOUNT_TYPE[i])) {
//				index = i;
//				break;
//			}
//				
//		}
//		return index;
//	}
	
	private int getIndexOfState(String state) {
		int index = 0;
		for (int i=0; i<AppConstants.STATE_TYPE.length;i++) {
			if (state.equalsIgnoreCase(AppConstants.STATE_TYPE[i])) {
				index = i;
				break;
			}
		}
		return index;
	}
}
