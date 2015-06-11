package com.taxiking.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.apiservice.HttpApi;
import com.taxiking.customer.apiservice.HttpApi.METHOD;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.model.Order;
import com.taxiking.customer.model.OrderHistory;
import com.taxiking.customer.model.ServiceType;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppDataUtilities;
import com.taxiking.customer.view.dialog.NumberPicker;
import com.taxiking.customer.view.dialog.NumberPicker.NumberPickerListener;

public class OrderRequestFragment extends BaseFragment {

	private ListView listView;
	private TextView txtAddress;
	private TextView txtTotalPrice;
	private Button btnPay;
	private int totalPrice = 0;
	
	private OrderAdapter adapter;
	private ArrayList<ServiceType> serviceArray;
	private ArrayList<Order> orderArray;
	
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

		serviceArray = AppDataUtilities.sharedInstance().serviceArray;
		orderArray = new ArrayList<Order>();
		for (int i=0; i<serviceArray.size(); i++) {
			Order order = Order.fromService(serviceArray.get(i));
			order.count = (order.type.equalsIgnoreCase("taxi")) ? 1 : 0;	
			orderArray.add(order);
		}
		
		listView = (ListView)rootview.findViewById(R.id.order_list);
		adapter = new OrderAdapter(parent, R.layout.view_order_item);
		listView.setAdapter(adapter);
		
		txtAddress = (TextView)rootview.findViewById(R.id.txt_address);
		txtTotalPrice = (TextView)rootview.findViewById(R.id.txt_total_price);
		btnPay = (Button)rootview.findViewById(R.id.btn_pay);
		btnPay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (totalPrice == 0) {
					Toast.makeText(parent, R.string.msg_select_car, Toast.LENGTH_LONG).show();
				} else {
					final String latitude 	= prefs.getLatitude();
					final String longitude 	= prefs.getLongitude();
					new RequestAsyncTask().execute(String.format("%d", totalPrice), "同善桥南街8号附1号", latitude, longitude);
				}
			}
		});
		
		refreshTotalPrice();
		
		return rootview;
	}
	
	class changeClickListener implements NumberPickerListener {
		private Order order;
		private int position;
		private int position_in_all;

		public changeClickListener(Order c, int p) {
			order = c;
			position = p;
		}

		@Override
		public void onValueChanged(int value) {
			order.count = value;
			orderArray.set(position, order);
			if (adapter != null)
				adapter.notifyDataSetChanged();
			refreshTotalPrice();
		}
	}
	
	private void refreshTotalPrice() {
		totalPrice = 0;
		for (int i=0; i<orderArray.size(); i++) {
			Order order = orderArray.get(i);
			totalPrice += order.price *order.count;
		}
		
		txtTotalPrice.setText(String.format("%d￥", totalPrice));
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
			holder.number_picker = (NumberPicker) convertView.findViewById(R.id.number_picker);
			
			Order orderItem = orderArray.get(position);
			
			if (orderItem != null) {
				if (orderItem.type.equalsIgnoreCase("taxi")) {
					holder.imgType.setImageResource(R.drawable.ic_taxi);
					holder.txtCount.setText(String.format("%d辆 Taxi", orderItem.count));
				} else {
					holder.imgType.setImageResource(R.drawable.ic_limo);
					holder.txtCount.setText(String.format("%d辆 Limo", orderItem.count));
				}
				holder.number_picker.setCurrentValue(orderItem.count);
				holder.number_picker.setNumberPickerListener(new changeClickListener(orderItem, position));
			}
			
			return convertView;
		}

		class ViewHolder {
			ImageView imgType;
			TextView txtCount;
			NumberPicker number_picker;
		}
		
		@Override
		public int getCount () {
			if (orderArray == null)
				return 0;
			return orderArray.size();
		}
	}
	
	public class RequestAsyncTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			MainActivity.instance.showWaitView();
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			String final_price = args[0];
			String order_address = args[1];
			String order_latitude = args[2];
			String order_longitude = args[3];
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));
			params.add(new BasicNameValuePair("final_price", final_price));
			params.add(new BasicNameValuePair("order_address", order_address));
			params.add(new BasicNameValuePair("order_latitude", order_latitude));
			params.add(new BasicNameValuePair("order_longitude", order_longitude));
			
			String items_json = "";
			for (int i=0; i<orderArray.size(); i++) {
				Order order = orderArray.get(i);
				if (order.count > 0) {
					if (!items_json.equalsIgnoreCase("")) {
						items_json +=",";
					}
					if (order.type.equalsIgnoreCase("taxi")) {
						items_json += String.format("o:%d", order.count);
					} else {
						items_json += String.format("c:%d", order.count);
					}
				}
			}
			
			items_json = "{" + items_json + "}";
			
			params.add(new BasicNameValuePair("items_json", items_json));
			
			return HttpApi.callToJson(AppConstants.HOST_REQUEST, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			MainActivity.instance.hideWaitView();
			try {
				String result = res.getString("result");
	
				if (result.equalsIgnoreCase("success")) {
					try {
						JSONObject wechat_payment_info = res.getJSONObject("wechat_payment_info");
						
						AppDataUtilities.sharedInstance().transaction_id = res.getString("transaction_id");
						AppDataUtilities.sharedInstance().pay_id = wechat_payment_info.getString("pay_id");
						AppDataUtilities.sharedInstance().pay_key = wechat_payment_info.getString("pay_key");
						
						parent.showFragment(OrderStatusCheckFragment.newInstance(), true);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					try {
						String errorMsg = res.getString("error");
						Toast.makeText(parent, errorMsg, Toast.LENGTH_LONG).show();
					}catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}