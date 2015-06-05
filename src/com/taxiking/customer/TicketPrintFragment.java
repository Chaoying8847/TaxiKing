package com.taxiking.customer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.model.TicketSub;
import com.taxiking.customer.utils.Utilities;

public final class TicketPrintFragment extends BaseFragment {
	private LinearLayout view_print;
	private TextView txt_publish_date;
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
	private TextView txt_ticket_number;
	private TextView txt_ticket_number2;
	private TextView txt_for_account;
	private TextView txt_state;
	private TextView txt_date;
	private TextView txt_obs_gravity;
	private TextView txt_obs_temp;
	private TextView txt_s_w;
	private TextView txt_oil_temp_h;
	private TextView txt_feet_h;
	private TextView txt_oil_levels_h;
	private TextView txt_qrt_h;
	private TextView txt_tank_table_h;
	private TextView txt_tank_bottoms_h;
	private TextView txt_oil_temp_l;
	private TextView txt_feet_l;
	private TextView txt_oil_levels_l;
	private TextView txt_qrt_l;
	private TextView txt_tank_table_l;
	private TextView txt_tank_bottoms_l;
	private TextView txt_total_barrels;
	private TextView txt_net_barrels;
	private TextView txt_off;
	private TextView txt_on;
	private TextView txt_loaded_miles;
	private TextView txt_meter_factor;
	private TextView txt_metered_barrels;
	private TextView txt_remarks;
	private TextView txt_rejected_reason;

	private TicketSub mTicket;
	private Button btnOk;

	public static TicketPrintFragment newInstance(TicketSub ticket) {
		TicketPrintFragment fragment = new TicketPrintFragment();
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

		View rootview = inflater.inflate(R.layout.view_ticket_print, null);

		view_print = (LinearLayout) rootview.findViewById(R.id.layout_print);
		txt_publish_date = (TextView)rootview.findViewById(R.id.txt_publish_date);
		txt_ticket_number = (TextView)rootview.findViewById(R.id.txt_ticket_number);
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
		txt_ticket_number2 = (TextView)rootview.findViewById(R.id.txt_ticket_number2);
		txt_for_account = (TextView)rootview.findViewById(R.id.txt_for_account);
		txt_state = (TextView)rootview.findViewById(R.id.txt_state);
		txt_date = (TextView)rootview.findViewById(R.id.txt_date);
		txt_obs_gravity = (TextView)rootview.findViewById(R.id.txt_obs_gravity);
		txt_obs_temp = (TextView)rootview.findViewById(R.id.txt_obs_temp);
		txt_s_w = (TextView)rootview.findViewById(R.id.txt_s_w);
		txt_oil_temp_h = (TextView)rootview.findViewById(R.id.txt_oil_temp_h);
		txt_feet_h = (TextView)rootview.findViewById(R.id.txt_oil_feet_h);
		txt_oil_levels_h = (TextView)rootview.findViewById(R.id.txt_oil_inch_h);
		txt_qrt_h = (TextView)rootview.findViewById(R.id.txt_qrt_h);
		txt_tank_table_h = (TextView)rootview.findViewById(R.id.txt_tank_table_h);
		txt_tank_bottoms_h = (TextView)rootview.findViewById(R.id.txt_tank_bottoms_h);
		txt_oil_temp_l = (TextView)rootview.findViewById(R.id.txt_oil_temp_l);
		txt_feet_l = (TextView)rootview.findViewById(R.id.txt_oil_feet_l);
		txt_oil_levels_l = (TextView)rootview.findViewById(R.id.txt_oil_inch_l);
		txt_qrt_l = (TextView)rootview.findViewById(R.id.txt_qrt_l);
		txt_tank_table_l = (TextView)rootview.findViewById(R.id.txt_tank_table_l);
		txt_tank_bottoms_l = (TextView)rootview.findViewById(R.id.txt_tank_bottoms_l);
		txt_total_barrels = (TextView)rootview.findViewById(R.id.txt_total_barrels);
		txt_net_barrels = (TextView)rootview.findViewById(R.id.txt_net_barrels);
		txt_off = (TextView)rootview.findViewById(R.id.txt_level_off);
		txt_on = (TextView)rootview.findViewById(R.id.txt_level_on);
		txt_loaded_miles = (TextView)rootview.findViewById(R.id.txt_loaded_miles);
		txt_meter_factor = (TextView)rootview.findViewById(R.id.txt_meter_factor);
		txt_metered_barrels = (TextView)rootview.findViewById(R.id.txt_metered_barrels);
		txt_remarks = (TextView)rootview.findViewById(R.id.txt_remark);
		txt_rejected_reason = (TextView)rootview.findViewById(R.id.txt_rejected_reason);
		
		setData();
		btnOk = (Button)rootview.findViewById(R.id.button_ok);
		btnOk.setOnClickListener(this);
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
		case R.id.button_ok:
			create_label_img();
			parent.goBack();
			break;
		default:
			break;
		}
	}
	
	private void setData() {
		if (mTicket != null) {
			txt_publish_date.setText(Utilities.getDate());
			txt_ticket_number.setText(mTicket.ticketNo);
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
			txt_ticket_number2.setText(mTicket.ticketNo);
			txt_for_account.setText(mTicket.forAccountOf);
			txt_state.setText(mTicket.state);
			txt_date.setText(mTicket.date);
			txt_obs_gravity.setText(mTicket.obsGravity);
			txt_obs_temp.setText(mTicket.obsTemp);
			txt_s_w.setText(mTicket.sw);
			txt_oil_temp_h.setText(mTicket.highDegreeF);
			txt_feet_h.setText(mTicket.highOilFeet);
			txt_oil_levels_h.setText(mTicket.highOilInch);
			txt_qrt_h.setText(mTicket.highOilQrt);
			txt_tank_table_h.setText(mTicket.hightankTableBarrels);
			txt_tank_bottoms_h.setText(mTicket.highTankBottoms);
			txt_oil_temp_l.setText(mTicket.lowDegreeF);
			txt_feet_l.setText(mTicket.lowOilFeet);
			txt_oil_levels_l.setText(mTicket.lowOilInch);
			txt_qrt_l.setText(mTicket.lowOilQrt);
			txt_tank_table_l.setText(mTicket.lowTankTableBarrels);
			txt_tank_bottoms_l.setText(mTicket.lowTankBottoms);
			txt_total_barrels.setText(mTicket.totalBarrels);
			txt_net_barrels.setText(mTicket.netBarrels);
			txt_off.setText(mTicket.levelOff);
			txt_on.setText(mTicket.levelOn);
			txt_loaded_miles.setText(mTicket.loadedMiles);
			txt_meter_factor.setText(mTicket.meterFactor);
			txt_metered_barrels.setText(mTicket.meteredBarrels);
			txt_remarks.setText(mTicket.remarks);
			txt_rejected_reason.setText(mTicket.comments);
		}
	}

	private void create_label_img () {		 
		view_print.setDrawingCacheEnabled(true);		 
		view_print.buildDrawingCache(true);
		
		Bitmap captureView = Bitmap.createBitmap(view_print.getMeasuredWidth(), 
				view_print.getMeasuredHeight(), Bitmap.Config.RGB_565);
		if (captureView == null)
			return;
		
		Canvas screenShotCanvas = new Canvas(captureView);
		view_print.draw(screenShotCanvas); 
		
		try {
			long time= System.currentTimeMillis();
			String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/";
			String fileName = dir + Long.toString(time) + ".jpg";
			File file = new File(fileName);
			if (!file.exists()) {
				captureView.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
				view_print.setDrawingCacheEnabled(false);
			}

			Toast.makeText(parent.getBaseContext(), "This file saved in " + fileName, Toast.LENGTH_LONG).show();

//			Intent picIntent = new Intent();
//			picIntent.setAction(android.content.Intent.ACTION_VIEW);
//			picIntent.setDataAndType(Uri.fromFile(file), "image/*");
//			startActivity(picIntent);
			
//			Intent printIntent = new Intent(getActivity().getApplicationContext(), PrinterTypeActivity.class);
//			startActivity(printIntent);
            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		return;
	}
}
