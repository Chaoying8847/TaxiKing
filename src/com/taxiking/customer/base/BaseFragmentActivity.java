package com.taxiking.customer.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.taxiking.customer.R;
import com.taxiking.customer.utils.AppPreferences;

public class BaseFragmentActivity extends FragmentActivity/* implements GestureDetector.OnGestureListener*/ {
	
//	private static final String DEBUG_TAG = "BaseFragmentActivity";
	public static final int RESULT_LOGOUT = 100;
	
//	private GestureDetectorCompat mDetector;
	
	public AppPreferences prefs;
	
	public FragmentManager fragmentManager;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
//		mDetector = new GestureDetectorCompat(this, this);
		
		prefs = new AppPreferences(this);
		
		fragmentManager = getSupportFragmentManager();
	}
	
	public void showFragment(Fragment fragment, boolean isStack) {
		showFragment(fragment, isStack, true);
	}
	
	public void showFragment(Fragment fragment, boolean isStack, boolean isAnimation) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		
		if(isAnimation)
			transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
		
		transaction.replace(R.id.view_body, fragment);
		
		if(isStack)
			transaction.addToBackStack(null);
		
		transaction.commit();
	}

	public void goBack() {
		if(fragmentManager.getBackStackEntryCount() > 0) {
			fragmentManager.popBackStack();
		}	
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		Log.d(DEBUG_TAG, "onTouchEvent: " + event.toString());
//		this.mDetector.onTouchEvent(event);
//		// Be sure to call ther superclass implementation
//		return super.onTouchEvent(event);
//	}
//	
//	@Override
//	public boolean onDown(MotionEvent event) {
//		Log.d(DEBUG_TAG, "onDown: " + event.toString());
//		return true;
//	}
//
//	@Override
//	public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX,
//			float velocityY) {
//		Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
//		return true;
//	}
//
//	@Override
//	public void onLongPress(MotionEvent event) {
//		Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
//	}
//
//	@Override
//	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//			float distanceY) {
//		Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
//		return true;
//	}
//
//	@Override
//	public void onShowPress(MotionEvent event) {
//		Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
//	}
//
//	@Override
//	public boolean onSingleTapUp(MotionEvent event) {
//		Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
//		return true;
//	}
	
}
