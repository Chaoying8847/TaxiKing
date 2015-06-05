package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

public class TicketClosed {
	
	public String ticket_id = "";
	public String leaseRunTicket = "";
	public String receiptTicket = "";
	public String estimatedBarrels = "";
	public String opFieldLoc = "";
	public String leaseNo = "";
	public String leaseCompName = "";
	public String stationNo = "";
	public String forAccountOf = "0";
	public String stationName = "";
	public String leaseLegalDesc = "";
	public String county = "";
	public String state = "0";
	public String nameReceiver = "";
	public String tractorNo = "";
	public String trailerNo = "";
	public String tankNo = "";
	// added on 20 aug
	public String tankSize = "";
	public String tankHeight = "";
	
	public String obsGravity = "";
	public String obsTemp = "";
	public String sw = "";
	public String date = "";
	public String ticketNo = "";
	public String highDegreeF = "";
	public String highOilFeet = "";
	public String highOilInch = "";
	public String highOilQrt = "";
	public String hightankTableBarrels = "";
	public String highTankBottoms = "";
	public String lowDegreeF = "";
	public String lowOilFeet = "";
	public String lowOilInch = "";
	public String lowOilQrt = "";
	public String lowTankTableBarrels = "";
	public String lowTankBottoms = "";
	public String totalBarrels = "";
	public String netBarrels = "";
	public String levelOff = "";
	public String levelOn = "";
	public String loadedMiles = "";
	public String meterFactor = "";
	public String meteredBarrels = "";
	public String remarks = "";
	public String firstName = "";
	public String lastName = "";
	public String userID = "";
	public String openID = "";
	public String owner = "";
	public String noOil = "";
	public String lockTicket = "";
	public String comments = "";
	public String sealOff = "";
	public String sealOn = "";
	
	public static TicketClosed fromJSON(JSONObject obj) {
		TicketClosed mTicketClosed = new TicketClosed();

		try {
			mTicketClosed.ticket_id		= obj.getString("ticket_id");
			mTicketClosed.leaseNo		= obj.getString("leaseNo");
			mTicketClosed.leaseCompName= obj.getString("leaseCompName");
			mTicketClosed.nameReceiver	= obj.getString("nameReceiver");
			mTicketClosed.date			= obj.getString("date");
			mTicketClosed.ticketNo		= obj.getString("ticketNo");
			mTicketClosed.comments		= obj.getString("rejectedReason");
			
//			mTicketClosed.leaseRunTicket	= obj.getString("leaseRunTicket");
//			mTicketClosed.receiptTicket	= obj.getString("receiptTicket");
			mTicketClosed.estimatedBarrels	= obj.getString("estimatedBarrels");
			mTicketClosed.opFieldLoc		= obj.getString("opFieldLoc");
			mTicketClosed.stationNo 		= obj.getString("stationNo");
			mTicketClosed.forAccountOf 		= obj.getString("forAccountOf");
			mTicketClosed.stationName 		= obj.getString("stationName");
			mTicketClosed.leaseLegalDesc 	= obj.getString("leaseLegalDesc");
			mTicketClosed.county 			= obj.getString("county");
			mTicketClosed.state				= obj.getString("state");
			mTicketClosed.tractorNo			= obj.getString("tractorNo");
			mTicketClosed.trailerNo 		= obj.getString("trailerNo");
			mTicketClosed.tankNo 			= obj.getString("tankNo");
			
			// added on 20 aug
			mTicketClosed.tankSize 			= obj.getString("tankSize");
			mTicketClosed.tankHeight		= obj.getString("tankHeight");
			mTicketClosed.obsGravity 		= obj.getString("obsGravity");
			mTicketClosed.obsTemp 			= obj.getString("obsTemp");
			mTicketClosed.sw 				= obj.getString("sw");
			mTicketClosed.highDegreeF 		= obj.getString("highDegreeF");
			mTicketClosed.highOilFeet 		= obj.getString("highOilFeet");
			mTicketClosed.highOilInch 		= obj.getString("highOilInch");
			mTicketClosed.highOilQrt 		= obj.getString("highOilQrt");
			mTicketClosed.hightankTableBarrels = obj.getString("hightankTableBarrels");
			mTicketClosed.highTankBottoms 	= obj.getString("highTankBottoms");
			mTicketClosed.lowDegreeF 		= obj.getString("lowDegreeF");
			mTicketClosed.lowOilFeet 		= obj.getString("lowOilFeet");
			mTicketClosed.lowOilInch 		= obj.getString("lowOilInch");
			mTicketClosed.lowOilQrt 		= obj.getString("lowOilQrt");
			mTicketClosed.lowTankTableBarrels = obj.getString("lowTankTableBarrels");
			mTicketClosed.lowTankBottoms 	= obj.getString("lowTankBottoms");
			mTicketClosed.totalBarrels 		= obj.getString("totalBarrels");
			mTicketClosed.netBarrels 		= obj.getString("netBarrels");
			mTicketClosed.levelOff 			= obj.getString("levelOff");
			mTicketClosed.levelOn 			= obj.getString("levelOn");
			mTicketClosed.loadedMiles 		= obj.getString("loadedMiles");
			mTicketClosed.meterFactor 		= obj.getString("meterFactor");
			mTicketClosed.meteredBarrels 	= obj.getString("meteredBarrels");
			mTicketClosed.remarks 			= obj.getString("remarks");
			mTicketClosed.firstName 		= obj.getString("firstName");
			mTicketClosed.lastName 			= obj.getString("lastName");
			mTicketClosed.userID 			= obj.getString("userID");
			mTicketClosed.openID 			= obj.getString("openID");
			mTicketClosed.owner 			= obj.getString("owner");
			mTicketClosed.noOil 			= obj.getString("noOil");
			mTicketClosed.lockTicket 		= obj.getString("lockTicket");
			mTicketClosed.sealOff 			= obj.getString("sealOff");
			mTicketClosed.sealOn 			= obj.getString("sealOn");
		} catch (JSONException e) {
		}

		return mTicketClosed;
	}
}
