package com.taxiking.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.model.Order;
import com.taxiking.customer.model.OrderHistory;

public class OrderRequestFragment extends BaseFragment {

	private ListView listView;
	private TextView txtAddress;
	private TextView txtTotalPrice;
	private Button btnPay;
	
	private OrderAdapter adapter;
	private List<Order> arrOrders;
	
	public static OrderRequestFragment newInstance() {
		OrderRequestFragment fragment = new OrderRequestFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (MainActivity.instance != null)
			MainActivity.instance.setTitle(getActivity().getString(R.string.menu_need));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootview = inflater.inflate(R.layout.fragment_order_request, null);

		listView = (ListView)rootview.findViewById(R.id.order_list);
		adapter = new OrderAdapter(parent, R.layout.view_order_item);
		listView.setAdapter(adapter);
		
		arrOrders = new ArrayList<Order>();
		for (int i=0; i<2; i++) {
			Order order = new Order();
			order.type = (i % 2 == 0)?"taxi":"limo";
			order.count = i+1;
			
			arrOrders.add(order);
		}
		
		txtAddress = (TextView)rootview.findViewById(R.id.txt_address);
		txtTotalPrice = (TextView)rootview.findViewById(R.id.txt_total_price);
		btnPay = (Button)rootview.findViewById(R.id.btn_pay);
		btnPay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		return rootview;
	}
	
	public void setOrderData(List<Order> orders) {
		if (arrOrders != null)
			arrOrders.clear();
		else
			arrOrders = new ArrayList<Order>();
		
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
			convertView = inflater.inflate(R.layout.view_order_item, null, false);
		
			holder = new ViewHolder();
			holder.imgType = (ImageView) convertView.findViewById(R.id.img_taxi_type);
			holder.txtCount = (TextView) convertView.findViewById(R.id.txt_count);
			holder.txtPrice = (TextView) convertView.findViewById(R.id.txt_price);
			
			Order orderItem = arrOrders.get(position);
			
			if (orderItem != null) {
				if (orderItem.type.equalsIgnoreCase("taxi")) {
					holder.imgType.setImageResource(R.drawable.ic_taxi);
					holder.txtCount.setText(String.format("%d辆 Taxi", orderItem.count));
					holder.txtPrice.setText(String.format("%d", orderItem.count * 10));
				} else {
					holder.imgType.setImageResource(R.drawable.ic_limo);
					holder.txtCount.setText(String.format("%d辆 Limo", orderItem.count));
					holder.txtPrice.setText(String.format("%d", orderItem.count * 30));
				}
			}
			
			return convertView;
		}

		class ViewHolder {
			ImageView imgType;
			TextView txtCount;
			TextView txtPrice;
		}
		
		@Override
		public int getCount () {
			if (arrOrders == null)
				return 0;
			return arrOrders.size();
		}
	}
}