package com.taxiking.customer.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Utilities {

	/**
	 * This will print the any string in Console/Logcat . See below for @params.
	 */
	private static DecimalFormat timerFormat = new DecimalFormat("00");

	public static void printStatement(String classname, String message) {
		System.out.println(classname + "----" + message);
	}

	/**
	 * This will show Toast/Alert ( Duration- Long ) for the User with any
	 * Specific Message . See below for @params.
	 */
	public static void showAppToast(Context ctx, String message) {
		Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * This will show Toast/Alert ( Duration- Long ) for the User with any
	 * Specific Message . See below for @params.
	 */
	public static void showAppToast(Context ctx, int messageId) {
		Toast.makeText(ctx, messageId, Toast.LENGTH_LONG).show();
	}

	/**
	 * This will show Dialog/Pop Alert for the User with any Specific Message .
	 * See below for @params.
	 */
	public static void showAppAlert(Context ctx, String title, int messageId) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		if (title.length() > 0) {
			alertDialogBuilder.setTitle(title);
		}
		// set dialog message
		alertDialogBuilder.setMessage(messageId).setCancelable(false)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public static void showAppAlert(Context ctx, String title, String message) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		if (title.length() > 0) {
			alertDialogBuilder.setTitle(title);
		}
		// set dialog message
		alertDialogBuilder.setMessage(message).setCancelable(false)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	public static void showAppAlert(Context ctx, String title, String message, final Runnable runnable) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		if (title.length() > 0) {
			alertDialogBuilder.setTitle(title);
		}
		// set dialog message
		alertDialogBuilder.setMessage(message).setCancelable(false)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						runnable.run();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	/**
	 * This will show Dialog/Pop Alert to the User When GPS is Disable with any
	 * Specific Message . See below for @params.
	 */
	public static void showGPSAlert(final Context ctx, String title,
			int messageId) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
		if (title.length() > 0) {
			alertDialogBuilder.setTitle(title);
		}
		// set dialog message
		alertDialogBuilder
				.setMessage(messageId)
				.setCancelable(false)
				.setPositiveButton("Enable",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								ctx.startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public static long getUTCtimetick(long time) {
		Calendar c = Calendar.getInstance();
		TimeZone z = c.getTimeZone();
		int offset = z.getRawOffset();
		if (z.inDaylightTime(new Date())) {
			offset = offset + z.getDSTSavings();
		}
		return time + offset;
	}
	
	public static long getCurrentTimestamp() {
		Long tsLong = System.currentTimeMillis()/1000;
		String ts = tsLong.toString();
		Log.d("Currnet Timestamp", ts);
		return tsLong;
	}

	public static String getStringFromTick(long tick) {
		String str = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tick);
		int mY = calendar.get(Calendar.YEAR);
		int mM = calendar.get(Calendar.MONTH) + 1;
		int mD = calendar.get(Calendar.DATE);
		int mHour = calendar.get(Calendar.HOUR_OF_DAY);
		int mMinute = calendar.get(Calendar.MINUTE);
		int mS = calendar.get(Calendar.SECOND);
		str += mY + "-" + mM + "-" + mD + " " + mHour + ":"
				+ timerFormat.format(mMinute) + ":" + timerFormat.format(mS);
		return str;
	}

	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < messageDigest.length; i++) {
				String subHexStr = Integer.toHexString(0xFF & messageDigest[i]);
				if (subHexStr.length() == 1)
					subHexStr = "0" + subHexStr;

				hexString.append(subHexStr);
			}

			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void setTypefaceAllView(ViewGroup vg, Typeface face) {

		for (int i = 0; i < vg.getChildCount(); ++i) {

			View child = vg.getChildAt(i);

			if (child instanceof ViewGroup) {

				setTypefaceAllView((ViewGroup) child, face);

			} else if (child != null) {
				if (child instanceof TextView) {
					TextView textView = (TextView) child;
					textView.setTypeface(face);
				}
			}
		}
	}

	public static int getResourceId(Context c, String resFileName) {
		int resId = android.R.color.transparent;

		if (resFileName == null || resFileName.equals(""))
			return resId;
		
		//resName = "flag_" + countryName.toLowerCase();

		try {
			String resName = "@drawable/" + resFileName;
			//resName = resName.substring(0, resName.indexOf("."));
			resId = c.getResources().getIdentifier(resName, "drawable", c.getPackageName());		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resId;
	}
	
	public static String getDate() {
		String[] formats = new String[] {
				"MM/dd/yyyy",
				"yyyy-MM-dd",
				"yyyy-MM-dd HH:mm",
				"yyyy-MM-dd HH:mmZ",
				"yyyy-MM-dd HH:mm:ss.SSSZ",
				"yyyy-MM-dd'T'HH:mm:ss.SSSZ",
				};
		SimpleDateFormat sdf = new SimpleDateFormat(formats[0], Locale.UK);
		return sdf.format(new Date());
	}
}
