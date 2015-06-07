package com.taxiking.customer.apiservice;

import org.json.JSONException;
import org.json.JSONObject;

import com.taxiking.customer.apiservice.base.HttpComm;
import com.taxiking.customer.apiservice.base.HttpRequestParams;
import com.taxiking.customer.model.User;
import com.taxiking.customer.utils.AppConstants;

/**
 * API class
 */

public class APIUtil {

//	private static final String TAG = "APIUtil";

	public static String token_id;
	public static String user_id;
	
//	public ArrayList<TicketOpen> ticketOpens;
//	public ArrayList<TicketSub> ticketSubs;
//	public ArrayList<TicketSub> ticketCloseds;

	private HttpComm conn;

	public APIUtil() {
		conn = new HttpComm();
		if (token_id == null)
			token_id = "";
		if (user_id == null)
			user_id = "";
//		ticketOpens = new ArrayList<TicketOpen>();
//		ticketSubs = new ArrayList<TicketSub>();
//		ticketCloseds = new ArrayList<TicketSub>();
	}

	private HttpRequestParams createParams() {
		HttpRequestParams params = new HttpRequestParams();
		if (token_id != null && token_id.length() > 0) {
			params.addParam("token_id", token_id);
		}
		return params;
	}

	// Common APIs
	public int login(final APIListener listener, final String userName, final String password) {
		token_id = "";
		HttpRequestParams params = createParams();
		params.addParam("request", "login");
		params.addParam("username", userName);
		params.addParam("password", password);
		
		return CGAPI.callCommonAPI(conn, "login", params, new APIListener() {
			@Override
			public void onResult(Object ret, int err) {
				User newUser = new User();
				if (err == AppConstants.ERR_OK) {
					JSONObject json = (JSONObject) ret;
					try {
						newUser = User.fromJSON(json);
						user_id = json.getString("id");
						token_id = json.getString("token_id");
					} catch (JSONException e) {
					}
				}
				listener.onResult(newUser, err);
			}
		});
	}

	public int logout(final APIListener listener) {
		HttpRequestParams params = createParams();
		params.addParam("request", "logout");
		return CGAPI.callCommonAPI(conn, "logout", params, new APIListener() { 
			@Override
			public void onResult(Object ret, int err) {
				if (err == AppConstants.ERR_OK) {
					user_id = "";
					token_id = "";
				}
				listener.onResult(ret, err);
			}
		});
	}

//	public int getTicketOpen(final APIListener listener) {
//		HttpRequestParams params = createParams();
//		params.addParam("request", "ticketopen");
//		params.addParam("user_id", user_id);
//		
//		return CGAPI.callCommonAPI(conn, "ticketopen", params, new APIListener() {
//			@Override
//			public void onResult(Object ret, int err) {
//				ticketOpens.clear();
//				if (err == AppConstants.ERR_OK) {
//					JSONObject json = (JSONObject) ret;
//					try {
//						JSONArray ticketJArray = json.getJSONArray("ticketopen");
//						int count = ticketJArray.length();
//						for (int i = 0; i < count; i++) {
//							TicketOpen newTOpen = TicketOpen.fromJSON(ticketJArray.getJSONObject(i));
//							ticketOpens.add(newTOpen);
//						}
//					} catch (JSONException e) {
//					}
//				}
//				listener.onResult(ticketOpens, err);
//			}
//		});
//	}
//	
//	public int getTicketSub(final APIListener listener) {
//		HttpRequestParams params = createParams();
//		params.addParam("request", "ticketsub");
//		params.addParam("user_id", user_id);
//		
//		return CGAPI.callCommonAPI(conn, "ticketsub", params, new APIListener() {
//			@Override
//			public void onResult(Object ret, int err) {
//				ticketSubs.clear();
//				if (err == AppConstants.ERR_OK) {
//					JSONObject json = (JSONObject) ret;
//					try {
//						JSONArray ticketJArray = json.getJSONArray("ticketsub");
//						int count = ticketJArray.length();
//						for (int i = 0; i < count; i++) {
//							TicketSub newTicket = TicketSub.fromJSON(ticketJArray.getJSONObject(i));
//							ticketSubs.add(newTicket);
//						}
//					} catch (JSONException e) {
//					}
//				}
//				listener.onResult(ticketSubs, err);
//			}
//		});
//	}
//	
//	public int getTicketClosed(final APIListener listener) {
//		HttpRequestParams params = createParams();
//		params.addParam("request", "ticketclosed");
//		params.addParam("user_id", user_id);
//		
//		return CGAPI.callCommonAPI(conn, "ticketclosed", params, new APIListener() {
//			@Override
//			public void onResult(Object ret, int err) {
//				ticketCloseds.clear();
//				if (err == AppConstants.ERR_OK) {
//					JSONObject json = (JSONObject) ret;
//					try {
//						JSONArray ticketJArray = json.getJSONArray("ticketclosed");
//						int count = ticketJArray.length();
//						for (int i = 0; i < count; i++) {
//							TicketSub newTicket = TicketSub.fromJSON(ticketJArray.getJSONObject(i));
//							ticketCloseds.add(newTicket);
//						}
//					} catch (JSONException e) {
//					}
//				}
//				listener.onResult(ticketCloseds, err);
//			}
//		});
//	}
//	
//	public int totalTicketFromUserId(final APIListener listener) {
//		HttpRequestParams params = createParams();
//		params.addParam("request", "totalTicketFromUserId");
//		params.addParam("user_id", user_id);
//		
//		return CGAPI.callCommonAPI(conn, "totalTicketFromUserId", params, new APIListener() {
//			@Override
//			public void onResult(Object ret, int err) {
//				ticketOpens.clear();
//				ticketSubs.clear();
//				ticketCloseds.clear();
//				if (err == AppConstants.ERR_OK) {
//					JSONObject json = (JSONObject) ret;
//					try {
//						JSONObject jsonOpen = (JSONObject) json.get("0");
//						JSONArray ticketJArray = jsonOpen.getJSONArray("ticketopen");
//						int count = ticketJArray.length();
//						for (int i = 0; i < count; i++) {
//							TicketOpen newTOpen = TicketOpen.fromJSON(ticketJArray.getJSONObject(i));
//							ticketOpens.add(newTOpen);
//						}
//					} catch (JSONException e) {
//					}
//					
//					try {
//						JSONObject jsonSub = (JSONObject) json.get("1");
//						JSONArray ticketJArray = jsonSub.getJSONArray("ticketsub");
//						int count = ticketJArray.length();
//						for (int i = 0; i < count; i++) {
//							TicketSub newTicket = TicketSub.fromJSON(ticketJArray.getJSONObject(i));
//							ticketSubs.add(newTicket);
//						}
//					} catch (JSONException e) {
//					}
//					
//					try {
//						JSONObject jsonClosed = (JSONObject) json.get("2");
//						JSONArray ticketJArray = jsonClosed.getJSONArray("ticketclosed");
//						int count = ticketJArray.length();
//						for (int i = 0; i < count; i++) {
//							TicketSub newTicket = TicketSub.fromJSON(ticketJArray.getJSONObject(i));
//							ticketCloseds.add(newTicket);
//						}
//					} catch (JSONException e) {
//					}
//				}
//				listener.onResult(null, err);
//			}
//		});
//	}
//
//	public int submitTicket(final APIListener listener, final TicketSub ticket, final String ticket_id) {
//		HttpRequestParams params = createParams();
//		params.addParam("request", "submitTicket");
//		params.addParam("userID", ticket.userID);
//		params.addParam("leaseRunTicket", ticket.leaseRunTicket);
//		params.addParam("receiptTicket", ticket.receiptTicket);
//		params.addParam("estimatedBarrels", ticket.estimatedBarrels);
//		params.addParam("opFieldLoc", ticket.opFieldLoc);
//		params.addParam("leaseNo", ticket.leaseNo);
//		params.addParam("leaseCompName", ticket.leaseCompName);
//		params.addParam("stationNo", ticket.stationNo);
//		params.addParam("forAccountOf", ticket.forAccountOf);
//		params.addParam("stationName", ticket.stationName);
//		params.addParam("leaseLegalDesc", ticket.leaseLegalDesc);
//		params.addParam("county", ticket.county);
//		params.addParam("state", ticket.state);
//		params.addParam("nameReceiver", ticket.nameReceiver);
//		params.addParam("tractorNo", ticket.tractorNo);
//		params.addParam("trailerNo", ticket.trailerNo);
//		params.addParam("tankNo", ticket.tankNo);
//		params.addParam("tankSize", ticket.tankSize);
//		params.addParam("tankHeight", ticket.tankHeight);
//		params.addParam("obsGravity", ticket.obsGravity);
//		params.addParam("obsTemp", ticket.obsTemp);
//		params.addParam("sw", ticket.sw);
//		params.addParam("date", ticket.date);
//		params.addParam("ticketNo", ticket.ticketNo);
//		params.addParam("highDegreeF", ticket.highDegreeF);
//		params.addParam("highOilFeet", ticket.highOilFeet);
//		params.addParam("highOilInch", ticket.highOilInch);
//		params.addParam("highOilQrt", ticket.highOilQrt);
//		params.addParam("hightankTableBarrels", ticket.hightankTableBarrels);
//		params.addParam("highTankBottoms", ticket.highTankBottoms);
//		params.addParam("lowDegreeF", ticket.lowDegreeF);
//		params.addParam("lowOilFeet", ticket.lowOilFeet);
//		params.addParam("lowOilInch", ticket.lowOilInch);
//		params.addParam("lowOilQrt", ticket.lowOilQrt);
//		params.addParam("lowTankTableBarrels", ticket.lowTankTableBarrels);
//		params.addParam("lowTankBottoms", ticket.lowTankBottoms);
//		params.addParam("totalBarrels", ticket.totalBarrels);
//		params.addParam("netBarrels", ticket.netBarrels);
//		params.addParam("levelOff", ticket.levelOff);
//		params.addParam("levelOn", ticket.levelOn);
//		params.addParam("loadedMiles", ticket.loadedMiles);
//		params.addParam("meterFactor", ticket.meterFactor);
//		params.addParam("meteredBarrels", ticket.meteredBarrels);
//		params.addParam("remarks", ticket.remarks);
//		params.addParam("rejectedReason", ticket.comments);
//		params.addParam("firstName", ticket.firstName);
//		params.addParam("lastName", ticket.lastName);
//		params.addParam("userID", ticket.userID);
//		params.addParam("openID", ticket.openID);
//		params.addParam("owner", ticket.owner);
//		params.addParam("noOil", ticket.noOil);
//		params.addParam("lockTicket", ticket.lockTicket);
//		params.addParam("ticketID", ticket_id);
//		
//		
//		return CGAPI.callCommonAPI(conn, "submitTicket", params, new APIListener() {
//			@Override
//			public void onResult(Object ret, int err) {
//				listener.onResult(ret, err);
//			}
//		});
//	}
//	
//	public int ticketSubFromId(final APIListener listener, String ticketId) {
//		HttpRequestParams params = createParams();
//		params.addParam("request", "ticketSubFromId");
//		params.addParam("ticketId", ticketId);
//		
//		return CGAPI.callCommonAPI(conn, "ticketSubFromId", params, new APIListener() {
//			@Override
//			public void onResult(Object ret, int err) {
//				TicketSub ticket = new TicketSub();
//				if (err == AppConstants.ERR_OK) {
//					JSONObject json = (JSONObject) ret;
//					ticket = TicketSub.fromJSON(json);
//				}
//				listener.onResult(ticket, err);
//			}
//		});
//	}
//
//	public int ticketClosedFromId(final APIListener listener, String ticketId) {
//		HttpRequestParams params = createParams();
//		params.addParam("request", "ticketClosedFromId");
//		params.addParam("ticketId", ticketId);
//		
//		return CGAPI.callCommonAPI(conn, "ticketClosedFromId", params, new APIListener() {
//			@Override
//			public void onResult(Object ret, int err) {
//				TicketSub ticket = new TicketSub();
//				if (err == AppConstants.ERR_OK) {
//					JSONObject json = (JSONObject) ret;
//					ticket = TicketSub.fromJSON(json);
//				}
//				listener.onResult(ticket, err);
//			}
//		});
//	}

	public int statusChange(final APIListener listener, final String ticketId, final int ticketType, final int status) {
		HttpRequestParams params = createParams();
		params.addParam("request", "statusChange");
		params.addParam("ticketId", ticketId);
		params.addParam("ticketType", String.valueOf(ticketType));
		params.addParam("status", String.valueOf(status));
		
		return CGAPI.callCommonAPI(conn, "statusChange", params, new APIListener() {
			@Override
			public void onResult(Object ret, int err) {
				if (err == AppConstants.ERR_OK) {
				}
				if (listener != null)
					listener.onResult(ret, err);
			}
		});
	}
	
	public int gpsTrack(final APIListener listener, final String userid, final String latitude, final String longitude) {
		HttpRequestParams params = createParams();
		params.addParam("request", "gpsTrack");
		params.addParam("userID", userid);
		params.addParam("latitude", latitude);
		params.addParam("longitude", longitude);
		
		return CGAPI.callCommonAPI(conn, "statusChange", params, new APIListener() {
			@Override
			public void onResult(Object ret, int err) {
				if (err == AppConstants.ERR_OK) {
				}
				if (listener != null)
					listener.onResult(ret, err);
			}
		});
	}

	public static interface APIListener {
		public void onResult(Object ret, int err);
	}
	
}
