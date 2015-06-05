package com.taxiking.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.model.TicketSub;

public final class TicketSubDetailFragment1 extends BaseFragment {
	private TextView txt_estimated_barrels;
	private TextView txt_lease_number;
	private TextView txt_operator;
	private TextView txt_station_number;
	private TextView txt_lease;
	private TextView txt_station_name;
	private TextView txt_county;
	private TextView txt_lease_legal;
	private TextView txt_name_receiver;
	private TextView txt_tractor_number;
	private TextView txt_driver_name;
	private TextView txt_trailer_number;
	private TextView txt_tank_number;
	private TextView txt_tank_size;
	private TextView txt_tank_height;
	private TextView txt_ticket_number;
	private TextView txt_for_account;
	private TextView txt_state;
	private CheckBox checkbox_no_oil;

	private TicketSub mTicket;
	
	public static TicketSubDetailFragment1 newInstance(TicketSub ticket) {
		TicketSubDetailFragment1 fragment = new TicketSubDetailFragment1();
		fragment.mTicket = ticket;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_ticket_sub_detail1, null);

		txt_estimated_barrels = (TextView)rootview.findViewById(R.id.txt_estimated_barrels);
		txt_lease_number = (TextView)rootview.findViewById(R.id.txt_lease_number);
		txt_operator = (TextView)rootview.findViewById(R.id.txt_operator);
		txt_station_number = (TextView)rootview.findViewById(R.id.txt_station_number);
		txt_lease = (TextView)rootview.findViewById(R.id.txt_lease);
		txt_station_name = (TextView)rootview.findViewById(R.id.txt_station_name);
		txt_county = (TextView)rootview.findViewById(R.id.txt_county);
		txt_lease_legal = (TextView)rootview.findViewById(R.id.txt_lease_legal);
		txt_name_receiver = (TextView)rootview.findViewById(R.id.txt_name_receiver);
		txt_tractor_number = (TextView)rootview.findViewById(R.id.txt_tractor_number);
		txt_driver_name = (TextView)rootview.findViewById(R.id.txt_driver_name);
		txt_trailer_number = (TextView)rootview.findViewById(R.id.txt_trailer_number);
		txt_tank_number = (TextView)rootview.findViewById(R.id.txt_tank_number);
		txt_tank_size = (TextView)rootview.findViewById(R.id.txt_tank_size);
		txt_tank_height = (TextView)rootview.findViewById(R.id.txt_tank_height);
		txt_ticket_number = (TextView)rootview.findViewById(R.id.txt_ticket_number);
		txt_for_account = (TextView)rootview.findViewById(R.id.txt_for_account);
		txt_state = (TextView)rootview.findViewById(R.id.txt_state);
		checkbox_no_oil = (CheckBox)rootview.findViewById(R.id.checkbox_no_oil);
		

		if (mTicket != null) {
			txt_estimated_barrels.setText(mTicket.estimatedBarrels);
			txt_lease_number.setText(mTicket.leaseNo);
			txt_operator.setText(mTicket.opFieldLoc);
			txt_station_number.setText(mTicket.stationNo);
			txt_lease.setText(mTicket.leaseCompName);
			txt_station_name.setText(mTicket.stationName);
			txt_county.setText(mTicket.county);
			txt_lease_legal.setText(mTicket.leaseLegalDesc);
			txt_name_receiver.setText(mTicket.nameReceiver);
			txt_tractor_number.setText(mTicket.tractorNo);
			txt_driver_name.setText(mTicket.firstName + " " + mTicket.lastName);
			txt_trailer_number.setText(mTicket.trailerNo);
			txt_tank_number.setText(mTicket.tankNo);
			txt_tank_size.setText(mTicket.tankSize);
			txt_tank_height.setText(mTicket.tankHeight);
			txt_ticket_number.setText(mTicket.ticketNo);
			txt_for_account.setText(mTicket.forAccountOf);
			txt_state.setText(mTicket.state);
			if (mTicket.noOil.equalsIgnoreCase("No Oil"))
				checkbox_no_oil.setChecked(true);
		}

		rootview.findViewById(R.id.button_continue).setOnClickListener(this);
		rootview.findViewById(R.id.button_cancel).setOnClickListener(this);

		return rootview;
	}

	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.button_continue:
			parent.showFragment(TicketSubDetailFragment2.newInstance(mTicket), true);
			break;
		case R.id.button_cancel:
			parent.goBack();
			break;
		default:
			break;
		}
	}
}
