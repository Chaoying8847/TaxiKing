package com.taxiking.customer;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.model.TicketSub;

public final class TicketSubDetailFragment2 extends BaseFragment {
	
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
	private TextView txt_meter_off;
	private TextView txt_meter_on;
	private TextView txt_loaded_miles;
	private TextView txt_meter_factor;
	private TextView txt_metered_barrels;
	private TextView txt_remarks;
	private TextView txt_rejected_reason;
	private TextView txt_seal_off;
	private TextView txt_seal_on;
	
	private TicketSub mTicket;
	
	public static TicketSubDetailFragment2 newInstance(TicketSub ticket) {
		TicketSubDetailFragment2 fragment = new TicketSubDetailFragment2();
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

		View rootview = inflater.inflate(R.layout.fragment_ticket_sub_detail2, null);

		txt_date = (TextView)rootview.findViewById(R.id.txt_date);
		txt_obs_gravity = (TextView)rootview.findViewById(R.id.txt_obs_gravity);
		txt_obs_temp = (TextView)rootview.findViewById(R.id.txt_obs_temp);
		txt_s_w = (TextView)rootview.findViewById(R.id.txt_s_w);
		txt_oil_temp_h = (TextView)rootview.findViewById(R.id.txt_oil_temp_h);
		txt_feet_h = (TextView)rootview.findViewById(R.id.txt_feet_h);
		txt_oil_levels_h = (TextView)rootview.findViewById(R.id.txt_oil_levels_h);
		txt_qrt_h = (TextView)rootview.findViewById(R.id.txt_qrt_h);
		txt_tank_table_h = (TextView)rootview.findViewById(R.id.txt_tank_table_h);
		txt_tank_bottoms_h = (TextView)rootview.findViewById(R.id.txt_tank_bottoms_h);
		txt_oil_temp_l = (TextView)rootview.findViewById(R.id.txt_oil_temp_l);
		txt_feet_l = (TextView)rootview.findViewById(R.id.txt_feet_l);
		txt_oil_levels_l = (TextView)rootview.findViewById(R.id.txt_oil_levels_l);
		txt_qrt_l = (TextView)rootview.findViewById(R.id.txt_qrt_l);
		txt_tank_table_l = (TextView)rootview.findViewById(R.id.txt_tank_table_l);
		txt_tank_bottoms_l = (TextView)rootview.findViewById(R.id.txt_tank_bottoms_l);
		txt_total_barrels = (TextView)rootview.findViewById(R.id.txt_total_barrels);
		txt_net_barrels = (TextView)rootview.findViewById(R.id.txt_net_barrels);
		txt_meter_off = (TextView)rootview.findViewById(R.id.txt_meter_off);
		txt_meter_on = (TextView)rootview.findViewById(R.id.txt_meter_on);
		txt_loaded_miles = (TextView)rootview.findViewById(R.id.txt_loaded_miles);
		txt_meter_factor = (TextView)rootview.findViewById(R.id.txt_meter_factor);
		txt_metered_barrels = (TextView)rootview.findViewById(R.id.txt_metered_barrels);
		txt_remarks = (TextView)rootview.findViewById(R.id.txt_remarks);
		txt_rejected_reason = (TextView)rootview.findViewById(R.id.txt_rejected_reason);
		txt_seal_off = (TextView)rootview.findViewById(R.id.txt_seal_off);
		txt_seal_on = (TextView)rootview.findViewById(R.id.txt_seal_on);

		rootview.findViewById(R.id.button_ok).setOnClickListener(this);

		if (mTicket != null) {
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
			txt_meter_off.setText(mTicket.levelOff);
			txt_meter_on.setText(mTicket.levelOn);
			txt_loaded_miles.setText(mTicket.loadedMiles);
			txt_meter_factor.setText(mTicket.meterFactor);
			txt_metered_barrels.setText(mTicket.meteredBarrels);
			txt_remarks.setText(mTicket.remarks);
			txt_rejected_reason.setText(mTicket.comments);
			txt_seal_off.setText(mTicket.sealOff);
			txt_seal_on.setText(mTicket.sealOn);
			
			if (mTicket.noOil.equalsIgnoreCase("No Oil")) {
				txt_obs_gravity.setBackgroundColor(Color.WHITE);
				txt_obs_temp.setBackgroundColor(Color.WHITE);
				txt_s_w.setBackgroundColor(Color.WHITE);
				txt_oil_temp_h.setBackgroundColor(Color.WHITE);
				txt_oil_temp_l.setBackgroundColor(Color.WHITE);
				txt_net_barrels.setBackgroundColor(Color.WHITE);
				txt_meter_off.setBackgroundColor(Color.WHITE);
				txt_meter_on.setBackgroundColor(Color.WHITE);
				txt_loaded_miles.setBackgroundColor(Color.WHITE);
				txt_meter_factor.setBackgroundColor(Color.WHITE);
				txt_metered_barrels.setBackgroundColor(Color.WHITE);
			}
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
		case R.id.button_ok:
			parent.fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			break;
		default:
			break;
		}
	}
}
