package com.taxiking.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.model.OrderHistory;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppDataUtilities;

public class OrderHistoryFragment extends BaseFragment {

	private ListView listView;
	private OrderAdapter adapter;
	private List<OrderHistory> arrOrders;
	
	public static OrderHistoryFragment newInstance() {
		OrderHistoryFragment fragment = new OrderHistoryFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (MainActivity.instance != null)
			MainActivity.instance.setTitle(getActivity().getString(R.string.menu_order));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_order_list, null);

		listView = (ListView)rootview.findViewById(R.id.order_list);
		adapter = new OrderAdapter(parent, R.layout.view_order_history_item);
		listView.setAdapter(adapter);
		
		arrOrders = AppDataUtilities.sharedInstance().orderHistoryArray;
		return rootview;
	}
	
	public void setOrderData(List<OrderHistory> orders) {
		if (arrOrders != null)
			arrOrders.clear();
		else
			arrOrders = new ArrayList<OrderHistory>();
		
		arrOrders = orders;

		if (adapter != null)
			adapter.notifyDataSetChanged();
	}
	
	class OrderAdapter extends ArrayAdapter<OrderHistory> {
		LayoutInflater inflater;
		
		public OrderAdapter(Context context, int resource) {
			super(context, resource);
			
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			convertView = inflater.inflate(R.layout.view_order_history_item, null, false);
		
			holder = new ViewHolder();
			holder.txtStartLocation = (TextView) convertView.findViewById(R.id.txt_start_location);
			holder.txtPrice = (TextView) convertView.findViewById(R.id.txt_price);
			holder.txtOrderId = (TextView) convertView.findViewById(R.id.txt_order_id);
			holder.txtTime = (TextView) convertView.findViewById(R.id.txt_date);
			holder.txtState = (TextView) convertView.findViewById(R.id.txt_status);
					
			OrderHistory orderItem = arrOrders.get(position);
			
			if (orderItem != null) {
				
				holder.txtStartLocation.setText(orderItem.address);
				holder.txtPrice.setText(orderItem.wechat_charge + getResources().getString(R.string.money_unit));
				holder.txtOrderId.setText(orderItem.order_id);
				holder.txtTime.setText(orderItem.end_time);
				if (orderItem.rating == 0 || orderItem.rating > 5) {
					holder.txtState.setText("未评级");
				} else {
					holder.txtState.setText("已完成");
				}
			}
			
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					OrderHistory orderHistoryItem = arrOrders.get(position);
					if (orderHistoryItem.rating == 0 || orderHistoryItem.rating > 5) {
						AppDataUtilities.sharedInstance().status.transaction_id = orderHistoryItem.order_id;
						MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_RATING, null);
					}
				}
			});
			
			return convertView;
		}

		class ViewHolder {
			TextView txtStartLocation;
			TextView txtPrice;
			TextView txtOrderId;
			TextView txtTime;
			TextView txtState;
		}
		
		@Override
		public int getCount () {
			if (arrOrders == null)
				return 0;
			return arrOrders.size();
		}
	}
}