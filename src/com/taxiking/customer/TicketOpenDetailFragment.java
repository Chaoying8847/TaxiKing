package com.taxiking.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.bean.Ticket;

public class TicketOpenDetailFragment extends BaseFragment {
	private TextView txtLoadNo;
	private TextView txtDriver;
	private TextView txtLease;
	private TextView txtFacilityId;
	private TextView txtDelivLoc;
	private TextView txtComments;
	private ImageButton btnBack;
	
	private TextView lblLoadNo;
	private TextView lblDriver;
	private TextView lblLease;
	private TextView lblFacilityId;
	private TextView lblDelivLoc;
	
	private int mTicketType;
	private Ticket mTicket;
	
	public static TicketOpenDetailFragment newInstance(Ticket ticket, int ticketType) {
		TicketOpenDetailFragment fragment = new TicketOpenDetailFragment();
		fragment.mTicket = ticket;
		fragment.mTicketType = ticketType;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_ticket_open_detail, null);
		txtLoadNo = (TextView)rootview.findViewById(R.id.txt_load_no);
		txtDriver = (TextView)rootview.findViewById(R.id.txt_driver);
		txtLease = (TextView)rootview.findViewById(R.id.txt_lease);
		txtFacilityId = (TextView)rootview.findViewById(R.id.txt_facility_id);
		txtDelivLoc = (TextView)rootview.findViewById(R.id.txt_deliv_loc);
		txtComments = (TextView)rootview.findViewById(R.id.txt_comments);
		
		btnBack = (ImageButton)rootview.findViewById(R.id.button_back);
		btnBack.setOnClickListener(this);
		
		txtLoadNo.setText(mTicket.showText1);
		txtDriver.setText(mTicket.showText2);
		txtLease.setText(mTicket.showText3);
		txtFacilityId.setText(mTicket.showText4);
		txtDelivLoc.setText(mTicket.showText5);
		txtComments.setText(mTicket.comment);
		
		lblLoadNo = (TextView)rootview.findViewById(R.id.lbl_load_no);
		lblDriver = (TextView)rootview.findViewById(R.id.lbl_driver);
		lblLease = (TextView)rootview.findViewById(R.id.lbl_lease);
		lblFacilityId = (TextView)rootview.findViewById(R.id.lbl_facility_id);
		lblDelivLoc = (TextView)rootview.findViewById(R.id.lbl_deliv_loc);
		
		switch (mTicketType) {
		case Ticket.OPEN_TICKET:
			txtDriver.setText(MainActivity.mainActivity.appPreference.getFirstName() + " " + MainActivity.mainActivity.appPreference.getLastName());
			lblLoadNo.setText(getString(R.string.column_load_no_upper));
			lblDriver.setText(getString(R.string.column_driver_upper));
			lblLease.setText(getString(R.string.column_lease_upper));
			lblFacilityId.setText(getString(R.string.column_facility_id_upper));
			lblDelivLoc.setText(getString(R.string.column_deliv_loc_upper));
			break;
		case Ticket.SUB_TICKET:
		case Ticket.CLOSED_TICKET:
			lblLoadNo.setText(getString(R.string.column_lease_no_upper));
			lblDriver.setText(getString(R.string.column_lease_upper));
			lblLease.setText(getString(R.string.column_receiver_upper));
			lblFacilityId.setText(getString(R.string.column_date_upper));
			lblDelivLoc.setText(getString(R.string.column_ticket_no_upper));
			break;
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
		
		switch (v.getId()) {
		case R.id.button_back:
			parent.goBack();
			break;
		default:
			break;
		}
	}

}
