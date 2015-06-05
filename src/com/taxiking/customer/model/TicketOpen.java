package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

public class TicketOpen {

	public String ticket_id;
	public String loadNo;
	public String loadDate;
	public String lease;
	public String facilityID;
	public String delivLoc;
	public String comments;
	public String forAccountOf;
	
	public static TicketOpen fromJSON(JSONObject obj) {
		TicketOpen mTicketOpen = new TicketOpen();

		try {
			mTicketOpen.ticket_id	= obj.getString("ticket_id");
			mTicketOpen.loadNo		= obj.getString("loadNo");
			mTicketOpen.loadDate	= obj.getString("loadDate");
			mTicketOpen.lease		= obj.getString("lease");
			mTicketOpen.facilityID	= obj.getString("facilityID");
			mTicketOpen.delivLoc	= obj.getString("delivLoc");
			mTicketOpen.comments	= obj.getString("comments");
			mTicketOpen.forAccountOf= obj.getString("forAccountOf");
		} catch (JSONException e) {
		}

		mTicketOpen.forAccountOf= (mTicketOpen.forAccountOf == null) ? "" : mTicketOpen.forAccountOf;
		return mTicketOpen;
	}
}
