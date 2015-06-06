package com.taxiking.customer;

import me.tangke.slidemenu.SlideMenu;
import me.tangke.slidemenu.SlideMenu.OnSlideStateChangeListener;
import android.content.Intent;
import android.os.Bundle;

import com.taxiking.customer.base.BaseFragmentActivity;

public abstract class BaseRightMenuActivity extends BaseFragmentActivity implements OnSlideStateChangeListener {
	public final static String OFFSET_PERCENT = "OffsetPercent";
	public final static String SLIDE_STATE = "SlideState";
	public final static int REQ_SIGN = 1000;

	// UI
	public SlideMenu mSlideMenu;

	// Data
	public int mSlideState;
	public float mOffsetPercent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(getLayoutResourceId());
		initMenu();
	}

	abstract protected int getLayoutResourceId();

	protected void initMenu() {
		mSlideMenu = (SlideMenu) findViewById(R.id.slideMenu);
		
		// set right menu
		//getLayoutInflater().inflate(R.layout.slidemenu_main_right, mSlideMenu, true);

		mSlideMenu.setSlideMode(SlideMenu.MODE_SLIDE_WINDOW);
		mSlideMenu.setOnSlideStateChangeListener(this);
	}
	
	@Override
	public void onSlideStateChange(int slideState) {
		mSlideState = slideState;
		if (mSlideState == SlideMenu.STATE_OPEN_RIGHT) {

		} else if (mSlideState == SlideMenu.STATE_OPEN_LEFT) {
			onLeftMenuOpened();

		} else if (mSlideState == SlideMenu.STATE_CLOSE) {
			onMenuClosed();
		}

		updateWithSlidingMenu();
	}

	@Override
	public void onSlideOffsetChange(float offsetPercent) {
		mOffsetPercent = offsetPercent;
		updateWithSlidingMenu();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mOffsetPercent = savedInstanceState.getFloat(OFFSET_PERCENT);
		mSlideState = savedInstanceState.getInt(SLIDE_STATE);
		updateWithSlidingMenu();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putFloat(OFFSET_PERCENT, mOffsetPercent);
		outState.putInt(SLIDE_STATE, mSlideState);
	}

	protected void updateWithSlidingMenu() {
	}

	protected void onLeftMenuOpened() {
	}

	protected void onMenuClosed() {
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_SIGN) {
			if (resultCode == RESULT_OK) {
				mSlideMenu.open(true, true);
			}
		}
	}
}

