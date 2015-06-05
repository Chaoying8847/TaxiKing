package com.taxiking.customer.model;

import org.json.JSONException;
import org.json.JSONObject;

public class TicketSub {

	public String ticket_id = "";
	public String leaseRunTicket = "";
	public String receiptTicket = "";
	public String estimatedBarrels = "";
	public String opFieldLoc = "";
	public String leaseNo = "";
	public String leaseCompName = "";
	public String stationNo = "";
	public String forAccountOf = "";
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
	
	
	public static TicketSub fromJSON(JSONObject obj) {
		TicketSub mTicketSub = new TicketSub();

		try {
			mTicketSub.ticket_id	= obj.getString("ticket_id");
			mTicketSub.leaseNo		= obj.getString("leaseNo");
			mTicketSub.leaseCompName= obj.getString("leaseCompName");
			mTicketSub.nameReceiver	= obj.getString("nameReceiver");
			mTicketSub.date			= obj.getString("date");
			mTicketSub.ticketNo		= obj.getString("ticketNo");
			mTicketSub.comments		= obj.getString("rejectedReason");
			
//			mTicketSub.leaseRunTicket	= obj.getString("leaseRunTicket");
//			mTicketSub.receiptTicket	= obj.getString("receiptTicket");
			mTicketSub.estimatedBarrels	= obj.getString("estimatedBarrels");
			mTicketSub.opFieldLoc		= obj.getString("opFieldLoc");
			mTicketSub.stationNo 		= obj.getString("stationNo");
			mTicketSub.forAccountOf 	= obj.getString("forAccountOf");
			mTicketSub.stationName 		= obj.getString("stationName");
			mTicketSub.leaseLegalDesc 	= obj.getString("leaseLegalDesc");
			mTicketSub.county 			= obj.getString("county");
			mTicketSub.state			= obj.getString("state");
			mTicketSub.tractorNo		= obj.getString("tractorNo");
			mTicketSub.trailerNo 		= obj.getString("trailerNo");
			mTicketSub.tankNo 			= obj.getString("tankNo");
			// added on 20 aug
			mTicketSub.tankSize 		= obj.getString("tankSize");
			mTicketSub.tankHeight		= obj.getString("tankHeight");
			
			mTicketSub.obsGravity 		= obj.getString("obsGravity");
			mTicketSub.obsTemp 			= obj.getString("obsTemp");
			mTicketSub.sw 				= obj.getString("sw");
			mTicketSub.highDegreeF 		= obj.getString("highDegreeF");
			mTicketSub.highOilFeet 		= obj.getString("highOilFeet");
			mTicketSub.highOilInch 		= obj.getString("highOilInch");
			mTicketSub.highOilQrt 		= obj.getString("highOilQrt");
			mTicketSub.hightankTableBarrels = obj.getString("hightankTableBarrels");
			mTicketSub.highTankBottoms 	= obj.getString("highTankBottoms");
			mTicketSub.lowDegreeF 		= obj.getString("lowDegreeF");
			mTicketSub.lowOilFeet 		= obj.getString("lowOilFeet");
			mTicketSub.lowOilInch 		= obj.getString("lowOilInch");
			mTicketSub.lowOilQrt 		= obj.getString("lowOilQrt");
			mTicketSub.lowTankTableBarrels = obj.getString("lowTankTableBarrels");
			mTicketSub.lowTankBottoms 	= obj.getString("lowTankBottoms");
			mTicketSub.totalBarrels 	= obj.getString("totalBarrels");
			mTicketSub.netBarrels 		= obj.getString("netBarrels");
			mTicketSub.levelOff 		= obj.getString("levelOff");
			mTicketSub.levelOn 			= obj.getString("levelOn");
			mTicketSub.loadedMiles 		= obj.getString("loadedMiles");
			mTicketSub.meterFactor 		= obj.getString("meterFactor");
			mTicketSub.meteredBarrels 	= obj.getString("meteredBarrels");
			mTicketSub.remarks 			= obj.getString("remarks");
			mTicketSub.firstName 		= obj.getString("firstName");
			mTicketSub.lastName 		= obj.getString("lastName");
			mTicketSub.userID 			= obj.getString("userID");
			mTicketSub.openID 			= obj.getString("openID");
			mTicketSub.owner 			= obj.getString("owner");
			mTicketSub.noOil 			= obj.getString("noOil");
			mTicketSub.lockTicket 		= obj.getString("lockTicket");
			mTicketSub.sealOff 			= obj.getString("sealOff");
			mTicketSub.sealOn 			= obj.getString("sealOn");
		} catch (JSONException e) {
		}

		return mTicketSub;
	}
}
