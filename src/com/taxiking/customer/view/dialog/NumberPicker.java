/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taxiking.customer.view.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taxiking.customer.R;


/**
 * This class has been pulled from the Android platform source code, its an internal widget that hasn't been
 * made public so its included in the project in this fashion for use with the preferences screen; I have made
 * a few slight modifications to the code here, I simply put a MAX and MIN default in the code but these values
 * can still be set publically by calling code.
 *
 * @author Google
 */
public class NumberPicker extends LinearLayout implements OnClickListener { 

	// UI
	private LinearLayout container;
	private EditText edt_number;
	private TextView txt_minus;
	private TextView txt_plus;

	// Data
	private int MAX = 10;
	private int MIN = 0;
	private int mCurrentNumber;
	private boolean mIsHorizontal = true;

	public NumberPicker(Context context) {
		this(context, null);
	}

	public NumberPicker(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public NumberPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberPicker);

		mIsHorizontal = a.getBoolean(R.styleable.NumberPicker_npIsHorizontal, mIsHorizontal);

		a.recycle();

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (mIsHorizontal)
			inflater.inflate(R.layout.number_picker_hor, this, true);
		else
			inflater.inflate(R.layout.number_picker_vert, this, true);

		container = (LinearLayout) findViewById(R.id.layout_container);
		edt_number = (EditText)findViewById(R.id.edt_number);
		edt_number.setText(String.valueOf(mCurrentNumber));
		txt_minus = (TextView)findViewById(R.id.txt_minus);
		txt_plus = (TextView)findViewById(R.id.txt_plus);

		txt_minus.setClickable(true);
		txt_plus.setClickable(true);

		txt_minus.setOnClickListener(this);
		txt_plus.setOnClickListener(this);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		edt_number.setEnabled(enabled);
		txt_minus.setEnabled(enabled);
		txt_plus.setEnabled(enabled);
	}

	public void setColor(int colorResId) {
		txt_minus.setBackgroundColor(colorResId);
		txt_plus.setBackgroundColor(colorResId);
	}

	public void setBackgroundColor(int colorResId) {
		container.setBackgroundColor(colorResId);
	}

	public void setMax(int max) {
		if (max > 0)
			this.MAX = max;
	}

	public void setMin(int min) {
		if (min >= 0 && min < this.MAX)
			this.MIN = min;
	}

	public void setCurrentValue(int value) {
		mCurrentNumber = value;
		edt_number.setText(String.valueOf(mCurrentNumber));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_minus:
			mCurrentNumber--;
			if (mCurrentNumber < MIN) {
				mCurrentNumber = MIN;

			} else {
				edt_number.setText(String.valueOf(mCurrentNumber));
				if (mHorizontalNumberPickerListener != null)
					mHorizontalNumberPickerListener.onValueChanged(mCurrentNumber);
			}

			break;

		case R.id.txt_plus:
			mCurrentNumber++;
			if (mCurrentNumber > MAX) {
				mCurrentNumber = MAX;

			} else {
				edt_number.setText(String.valueOf(mCurrentNumber));
				if (mHorizontalNumberPickerListener != null)
					mHorizontalNumberPickerListener.onValueChanged(mCurrentNumber);
			}
			break;

		default:
			break;
		}
	}

	/*
	 * Interface
	 */
	private NumberPickerListener mHorizontalNumberPickerListener;
	public void setNumberPickerListener(NumberPickerListener listener) {
		mHorizontalNumberPickerListener = listener;
	}

	public interface NumberPickerListener {
		public void onValueChanged(int value);
	}
}
