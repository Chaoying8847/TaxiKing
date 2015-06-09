package com.taxiking.customer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.taxiking.customer.R;

public class SSMessageDialog extends Dialog implements View.OnClickListener {
	private final int BUTTON_COUNT = 6;
	
	private TextView txt_title;
	private View divider_title;
	private TextView txt_message;

	private TextView btns[] = new TextView[BUTTON_COUNT];
	private View divider[] = new View[BUTTON_COUNT];
	
	// data
	private int btnCount = 0; 
	
	public SSMessageDialog(Context context, String title, String message, String... btn_name) {
		super(context);
		init(title, message, btn_name);
	}

	private void init(String title, String message, String...btn_name) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dialog_message);

		getWindow().setBackgroundDrawable(new ColorDrawable(0));
		WindowManager.LayoutParams lp = getWindow().getAttributes();  
		lp.dimAmount = 0.3f; 
		getWindow().setAttributes(lp);

		btnCount = btn_name.length;
		
		txt_title = (TextView) findViewById(R.id.txt_title);
		divider_title = findViewById(R.id.divider);
		txt_message = (TextView) findViewById(R.id.txt_message);
		txt_title.setVisibility(View.GONE);
		divider_title.setVisibility(View.GONE);
		txt_message.setVisibility(View.GONE);
		
		if (!TextUtils.isEmpty(title)) {
			txt_title.setText(title);
			txt_title.setVisibility(View.VISIBLE);
		}
			
		if (!TextUtils.isEmpty(message)) {
			txt_message.setText(Html.fromHtml(message));
			txt_message.setVisibility(View.VISIBLE);
		}
		
		if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(message))
			divider_title.setVisibility(View.VISIBLE);
		
		divider[0] = findViewById(R.id.divider0);
		divider[1] = findViewById(R.id.divider00);
		divider[2] = findViewById(R.id.divider01);
		divider[3] = findViewById(R.id.divider1);
		divider[4] = findViewById(R.id.divider10);
		divider[5] = findViewById(R.id.divider11);
		
		for (int i = 0; i < BUTTON_COUNT; i++)
			divider[i].setVisibility(View.GONE);
		
		btns[0] = (TextView) findViewById(R.id.btn_0);
		btns[1] = (TextView) findViewById(R.id.btn_1);
		btns[2] = (TextView) findViewById(R.id.btn_2);
		btns[3] = (TextView) findViewById(R.id.btn_3);
		btns[4] = (TextView) findViewById(R.id.btn_4);
		btns[5] = (TextView) findViewById(R.id.btn_5);
		
		for (int i = 0; i < BUTTON_COUNT; i++)
			btns[i].setVisibility(View.GONE);

		if (btn_name != null && btnCount > 0) {
			for (int i = 0; i < btnCount; i++) {
				btns[i].setVisibility(View.VISIBLE);
				btns[i].setText(btn_name[i]);
				btns[i].setOnClickListener(this);
			}
				
			for (int i = 0; i < btnCount; i++)
				divider[i].setVisibility(View.VISIBLE);
		}
	}
	
	private MessageDilogListener mMessageDilogListener;
	public void setMessageDilogListener(MessageDilogListener listener) {
		mMessageDilogListener = listener;
	}

	public interface MessageDilogListener {
		public void onButtonClick(int id);
	}

	@Override
	public void onClick(View v) {
		if (mMessageDilogListener != null) {
			mMessageDilogListener.onButtonClick(v.getId());
		}
		dismiss();
	}
}
