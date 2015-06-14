package com.taxiking.customer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.model.CurrentStatus;
import com.taxiking.customer.utils.AppDataUtilities;
import com.taxiking.customer.view.dialog.SSMessageDialog;
import com.taxiking.customer.view.dialog.SSMessageDialog.MessageDilogListener;

public class OrderCompleteFragment extends BaseFragment implements View.OnClickListener {
	
	private TextView txtOrderId;
	private TextView txtOrderTime;
	private TextView txtOrderAddress;
	private TextView txtOrderPrice;
	private Button btnCallToDriver;
	private Button btnComplete;
	
	private CurrentStatus status;

	public static OrderCompleteFragment newInstance() {
		OrderCompleteFragment fragment = new OrderCompleteFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (MainActivity.instance != null)
			MainActivity.instance.setTitle(getActivity().getString(R.string.order_success));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_order_complete, null);

		status = AppDataUtilities.sharedInstance().status;
		
		txtOrderId = (TextView)rootview.findViewById(R.id.txt_order_id);
		txtOrderTime = (TextView)rootview.findViewById(R.id.txt_order_time);
		txtOrderAddress = (TextView)rootview.findViewById(R.id.txt_address);
		txtOrderPrice = (TextView)rootview.findViewById(R.id.txt_order_total_price);
		
		btnCallToDriver = (Button)rootview.findViewById(R.id.btn_call_driver);
		btnCallToDriver.setOnClickListener(this);

		btnComplete = (Button)rootview.findViewById(R.id.btn_complete);
		btnComplete.setOnClickListener(this);
		
		txtOrderId.setText(status.transaction_id);
		txtOrderTime.setText(status.order_time);
		txtOrderAddress.setText(status.order_address);
		txtOrderPrice.setText(String.format("%.0f¥", status.price));
		
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_complete:
			SSMessageDialog alert = new SSMessageDialog(parent,
					parent.getString(R.string.confirm_order_complete), "", parent.getString(R.string.cancel),
					parent.getString(R.string.confirm));
			alert.show();
			alert.setMessageDilogListener(new MessageDilogListener() {
				@Override
				public void onButtonClick(int id) {
					if (id == R.id.btn_1){
						OrderCompleteFragment.this.parent.showFragment(ServiceRatingFragment.newInstance(), true);
					}
				}
			});
			break;
		case R.id.btn_call_driver:
			if (status.driver_phone!=null && !status.driver_phone.equalsIgnoreCase("")) {
				// call
			}
			break;
		default:
			break;
		}
	}
}
