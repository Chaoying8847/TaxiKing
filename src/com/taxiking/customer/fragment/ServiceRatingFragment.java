package com.taxiking.customer.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.taxiking.customer.MainActivity;
import com.taxiking.customer.R;
import com.taxiking.customer.apiservice.HttpApi;
import com.taxiking.customer.apiservice.HttpApi.METHOD;
import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.utils.AppConstants;
import com.taxiking.customer.utils.AppDataUtilities;
import com.taxiking.customer.utils.CommonUtil;
import com.taxiking.customer.view.dialog.SSMessageDialog;
import com.taxiking.customer.view.dialog.SSMessageDialog.MessageDilogListener;

public class ServiceRatingFragment extends BaseFragment implements OnClickListener {

	private RatingBar	ratingView;
	private Button 		btnConfirm;
	private EditText 	txtComment;
	
	public static ServiceRatingFragment newInstance() {
		ServiceRatingFragment fragment = new ServiceRatingFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (MainActivity.instance != null)
			MainActivity.instance.setTitle(getActivity().getString(R.string.service_rating));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootview = inflater.inflate(R.layout.fragment_service_rating, null);

		ratingView	= (RatingBar)rootview.findViewById(R.id.ratingView);
		txtComment	= (EditText)rootview.findViewById(R.id.txt_comment);
		btnConfirm	= (Button)rootview.findViewById(R.id.btn_confirm);
		
		btnConfirm.setOnClickListener(this);
		
		return rootview;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_confirm:
			final float ratingValue	= ratingView.getRating();
			final String comments	= txtComment.getText().toString();

			if(ratingValue == 0) {
				Toast.makeText(parent, R.string.msg_select_rating, Toast.LENGTH_LONG).show();
				return;
			} else {
				SSMessageDialog alert = new SSMessageDialog(parent,
						parent.getString(R.string.share_with), "", parent.getString(R.string.weixin),
						parent.getString(R.string.weibo));
				alert.show();
				alert.setMessageDilogListener(new MessageDilogListener() {
					@Override
					public void onButtonClick(int id) {
						if (id == R.id.btn_0){
						} else if (id == R.id.btn_1){
						}
						
						if (!CommonUtil.isNetworkAvailable(parent)) {
							CommonUtil.showWaringDialog(parent, parent.getString(R.string.warning), parent.getString(R.string.msg_network_error));
						} else {
							new RatingAsyncTask().execute(AppDataUtilities.sharedInstance().status.transaction_id, String.format("%.2f", ratingValue), comments);
						}
					}
				});
			}
			break;
		case R.id.btn_back:
			MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_HOME, null);
			break;
		default:
			break;
		}
	}
	
	public class RatingAsyncTask extends AsyncTask<String, String, JSONObject> {

		@Override
		protected void onPreExecute() {
			MainActivity.instance.showWaitView();
			super.onPreExecute();
		}
		
		@Override
		protected JSONObject doInBackground(String... args) {
			String transaction_id = args[0];
			String rating	= args[1];
			String comment 	= args[2];
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("transaction_id", transaction_id));
			params.add(new BasicNameValuePair("rating", rating));
			params.add(new BasicNameValuePair("comment", comment));
			params.add(new BasicNameValuePair("session_token", prefs.getSession()));

			return HttpApi.callToJson(AppConstants.HOST_RATE, METHOD.POST, params, null);
		}

		@Override
		protected void onPostExecute(JSONObject res) {
			MainActivity.instance.hideWaitView();
			if (AppDataUtilities.sharedInstance().account.first_time_order) {
				MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_SEND_INFO, null);
			} else {
				MainActivity.instance.SwitchContent(AppConstants.SW_FRAGMENT_HOME, null);
			}
		}
	}
}
