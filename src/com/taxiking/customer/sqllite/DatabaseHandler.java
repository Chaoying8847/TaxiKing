package com.taxiking.customer.sqllite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "TicketManager";

	// tickets table name
	private static final String TABLE_TICKETS = "Tickets";
	
	private static int TABLE_INDEX = -1;

	// tickets Table Columns names
	public static final String KEY_ID = "id";
	public static final String KEY_TICKET_ID = "ticketId";
	public static final String KEY_LEASE_RUN_TICKET = "leaseRunTicket";
	public static final String KEY_RECEIPT_TICKET = "receiptTicket";
	public static final String KEY_ESTIMATED_BARRELS = "estimatedBarrels";
	public static final String KEY_OP_FIELD_LOC = "opFieldLoc";
	public static final String KEY_LEASE_NO = "leaseNo";
	public static final String KEY_LEASE_COMP_NAME = "leaseCompName";
	public static final String KEY_STATION_NO = "stationNo";
	public static final String KEY_FOR_ACCOUNT_OF = "forAccountOf";
	public static final String KEY_STATION_NAME = "stationName";
	public static final String KEY_LEASE_LEGAL_DESC = "leaseLegalDesc";
	public static final String KEY_COUNTY = "county";
	public static final String KEY_STATE = "state";
	public static final String KEY_NAME_RECEIVER = "nameReceiver";
	public static final String KEY_TRACTOR_NO = "tractorNo";
	public static final String KEY_TRAILER_NO = "trailerNo";
	public static final String KEY_TANK_NO = "tankNo";
	// added on 20 aug
	public static final String KEY_TANK_SIZE = "tankSize";
	public static final String KEY_TANK_HEIGHT = "tankHeight";
	
	public static final String KEY_OBS_GRAVITY = "obsGravity";
	public static final String KEY_OBS_TEMP = "obsTemp";
	public static final String KEY_SW = "sw";
	public static final String KEY_DATE = "date";
	public static final String KEY_TICKET_NO = "ticketNo";
	public static final String KEY_HIGH_DEGREE_F = "highDegreeF";
	public static final String KEY_HIGH_OIL_FEET = "highOilFeet";
	public static final String KEY_HIGH_OIL_INCH = "highOilInch";
	public static final String KEY_HIGH_OIL_QRT = "highOilQrt";
	public static final String KEY_HIGH_TANK_TABLE_BARRELS = "hightankTableBarrels";
	public static final String KEY_HIGH_TANK_BOTTOMS = "highTankBottoms";
	public static final String KEY_LOW_DEGREE_F = "lowDegreeF";
	public static final String KEY_LOW_OIL_FEET = "lowOilFeet";
	public static final String KEY_LOW_OIL_INCH = "lowOilInch";
	public static final String KEY_LOW_OIL_QRT = "lowOilQrt";
	public static final String KEY_LOW_TANK_TABLE_BARRELS = "lowTankTableBarrels";
	public static final String KEY_LOW_TANK_BOTTOMS = "lowTankBottoms";
	public static final String KEY_TOTAL_BARRELS = "totalBarrels";
	public static final String KEY_NET_BARRELS = "netBarrels";
	public static final String KEY_LEVEL_OFF = "levelOff";
	public static final String KEY_LEVEL_ON = "levelOn";
	public static final String KEY_LOADED_MILES = "loadedMiles";
	public static final String KEY_METER_FACTOR = "meterFactor";
	public static final String KEY_METERED_BARRELS = "meteredBarrels";
	public static final String KEY_REMARKS = "remarks";
	public static final String KEY_FIRST_NAME = "firstName";
	public static final String KEY_LAST_NAME = "lastName";
	public static final String KEY_USER_ID = "userID";
	public static final String KEY_OPEN_ID = "openID";
	public static final String KEY_OWNER = "owner";
	public static final String KEY_NO_OIL = "noOil";
	public static final String KEY_LOCK_TICKET = "lockTicket";
	public static final String KEY_COMMENTS = "comments";
	public static final String KEY_WILL_SUBMIT = "willSubmit";
	public static final String KEY_SEAL_OFF = "sealOff";
	public static final String KEY_SEAL_ON = "sealOn";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TICKETS_TABLE = "CREATE TABLE " + TABLE_TICKETS + "(" +
			KEY_ID + " INTEGER PRIMARY KEY," + 
			KEY_TICKET_ID + " TEXT," +
			KEY_LEASE_RUN_TICKET + " TEXT," +
			KEY_RECEIPT_TICKET + " TEXT," +
			KEY_ESTIMATED_BARRELS + " TEXT," +
			KEY_OP_FIELD_LOC + " TEXT," +
			KEY_LEASE_NO + " TEXT," +
			KEY_LEASE_COMP_NAME + " TEXT," +
			KEY_STATION_NO + " TEXT," +
			KEY_FOR_ACCOUNT_OF + " TEXT," +
			KEY_STATION_NAME + " TEXT," +
			KEY_LEASE_LEGAL_DESC + " TEXT," +
			KEY_COUNTY + " TEXT," +
			KEY_STATE + " TEXT," +
			KEY_NAME_RECEIVER + " TEXT," +
			KEY_TRACTOR_NO + " TEXT," +
			KEY_TRAILER_NO + " TEXT," +
			KEY_TANK_NO + " TEXT," +
			KEY_TANK_SIZE + " TEXT," +
			KEY_TANK_HEIGHT + " TEXT," +
			KEY_OBS_GRAVITY + " TEXT," +
			KEY_OBS_TEMP + " TEXT," +
			KEY_SW + " TEXT," +
			KEY_DATE + " TEXT," +
			KEY_TICKET_NO + " TEXT," +
			KEY_HIGH_DEGREE_F + " TEXT," +
			KEY_HIGH_OIL_FEET + " TEXT," +
			KEY_HIGH_OIL_INCH + " TEXT," +
			KEY_HIGH_OIL_QRT + " TEXT," +
			KEY_HIGH_TANK_TABLE_BARRELS + " TEXT," +
			KEY_HIGH_TANK_BOTTOMS + " TEXT," +
			KEY_LOW_DEGREE_F + " TEXT," +
			KEY_LOW_OIL_FEET + " TEXT," +
			KEY_LOW_OIL_INCH + " TEXT," +
			KEY_LOW_OIL_QRT + " TEXT," +
			KEY_LOW_TANK_TABLE_BARRELS + " TEXT," +
			KEY_LOW_TANK_BOTTOMS + " TEXT," +
			KEY_TOTAL_BARRELS + " TEXT," +
			KEY_NET_BARRELS + " TEXT," +
			KEY_LEVEL_OFF + " TEXT," +
			KEY_LEVEL_ON + " TEXT," +
			KEY_LOADED_MILES + " TEXT," +
			KEY_METER_FACTOR + " TEXT," +
			KEY_METERED_BARRELS + " TEXT," +
			KEY_REMARKS + " TEXT," +
			KEY_FIRST_NAME + " TEXT," +
			KEY_LAST_NAME + " TEXT," +
			KEY_USER_ID + " TEXT," +
			KEY_OPEN_ID + " TEXT," +
			KEY_OWNER + " TEXT," +
			KEY_NO_OIL + " TEXT," +
			KEY_LOCK_TICKET + " TEXT," +
			KEY_COMMENTS + " TEXT," +
			KEY_SEAL_OFF + " TEXT," +
			KEY_SEAL_ON + " TEXT," +
			KEY_WILL_SUBMIT + " INTEGER " + ")";
		
		db.execSQL(CREATE_TICKETS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
	
		// Create tables again
		onCreate(db);
	}
	
	public boolean hasRow(String ticketid) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_TICKETS, null, KEY_TICKET_ID + "=?",
				new String[] { String.valueOf(ticketid) }, null, null, null, null);
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToFirst();
			TABLE_INDEX = cursor.getInt(0);
			return true;
		} else {
			TABLE_INDEX = -1;
			return false;
		}
	}

	// Adding new ticket
//	public void add_Ticket(TicketSub ticket, String ticketId, int willSubmit) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(KEY_TICKET_ID               , ticketId);
//		values.put(KEY_LEASE_RUN_TICKET        , ticket.leaseRunTicket);
//		values.put(KEY_RECEIPT_TICKET          , ticket.receiptTicket);
//		values.put(KEY_ESTIMATED_BARRELS       , ticket.estimatedBarrels);
//		values.put(KEY_OP_FIELD_LOC            , ticket.opFieldLoc);
//		values.put(KEY_LEASE_NO                , ticket.leaseNo);
//		values.put(KEY_LEASE_COMP_NAME         , ticket.leaseCompName);
//		values.put(KEY_STATION_NO              , ticket.stationNo);
//		values.put(KEY_FOR_ACCOUNT_OF          , ticket.forAccountOf);
//		values.put(KEY_STATION_NAME            , ticket.stationName);
//		values.put(KEY_LEASE_LEGAL_DESC        , ticket.leaseLegalDesc);
//		values.put(KEY_COUNTY                  , ticket.county);
//		values.put(KEY_STATE                   , ticket.state);
//		values.put(KEY_NAME_RECEIVER           , ticket.nameReceiver);
//		values.put(KEY_TRACTOR_NO              , ticket.tractorNo);
//		values.put(KEY_TRAILER_NO              , ticket.trailerNo);
//		values.put(KEY_TANK_NO                 , ticket.tankNo);
//		
//		values.put(KEY_TANK_SIZE               , ticket.tankSize);
//		values.put(KEY_TANK_HEIGHT             , ticket.tankHeight);
//		
//		values.put(KEY_OBS_GRAVITY             , ticket.obsGravity);
//		values.put(KEY_OBS_TEMP                , ticket.obsTemp);
//		values.put(KEY_SW                      , ticket.sw);
//		values.put(KEY_DATE                    , ticket.date);
//		values.put(KEY_TICKET_NO               , ticket.ticketNo);
//		values.put(KEY_HIGH_DEGREE_F           , ticket.highDegreeF);
//		values.put(KEY_HIGH_OIL_FEET           , ticket.highOilFeet);
//		values.put(KEY_HIGH_OIL_INCH           , ticket.highOilInch);
//		values.put(KEY_HIGH_OIL_QRT            , ticket.highOilQrt);
//		values.put(KEY_HIGH_TANK_TABLE_BARRELS , ticket.hightankTableBarrels);
//		values.put(KEY_HIGH_TANK_BOTTOMS       , ticket.highTankBottoms);
//		values.put(KEY_LOW_DEGREE_F            , ticket.lowDegreeF);
//		values.put(KEY_LOW_OIL_FEET            , ticket.lowOilFeet);
//		values.put(KEY_LOW_OIL_INCH            , ticket.lowOilInch);
//		values.put(KEY_LOW_OIL_QRT             , ticket.lowOilQrt);
//		values.put(KEY_LOW_TANK_TABLE_BARRELS  , ticket.lowTankTableBarrels);
//		values.put(KEY_LOW_TANK_BOTTOMS        , ticket.lowTankBottoms);
//		values.put(KEY_TOTAL_BARRELS           , ticket.totalBarrels);
//		values.put(KEY_NET_BARRELS             , ticket.netBarrels);
//		values.put(KEY_LEVEL_OFF               , ticket.levelOff);
//		values.put(KEY_LEVEL_ON                , ticket.levelOn);
//		values.put(KEY_LOADED_MILES            , ticket.loadedMiles);
//		values.put(KEY_METER_FACTOR            , ticket.meterFactor);
//		values.put(KEY_METERED_BARRELS         , ticket.meteredBarrels);
//		values.put(KEY_REMARKS                 , ticket.remarks);
//		values.put(KEY_FIRST_NAME              , ticket.firstName);
//		values.put(KEY_LAST_NAME               , ticket.lastName);
//		values.put(KEY_USER_ID                 , ticket.userID);
//		values.put(KEY_OPEN_ID                 , ticket.openID);
//		values.put(KEY_OWNER                   , ticket.owner);
//		values.put(KEY_NO_OIL                  , ticket.noOil);
//		values.put(KEY_LOCK_TICKET             , ticket.lockTicket);
//		values.put(KEY_COMMENTS                , ticket.comments);
//		values.put(KEY_SEAL_OFF                , ticket.sealOff);
//		values.put(KEY_SEAL_ON                 , ticket.sealOn);
//		values.put(KEY_WILL_SUBMIT             , willSubmit);
//		// Inserting Row
//		TABLE_INDEX = (int)db.insert(TABLE_TICKETS, null, values);
//		db.close(); // Closing database connection
//	}
//
//	// Getting single ticket
//	public TicketSub get_Ticket(String ticketId) {
//		SQLiteDatabase db = this.getReadableDatabase();
//		if (!hasRow(ticketId))
//			return new TicketSub();
//		Cursor cursor = db.query(TABLE_TICKETS, null, KEY_ID + "=?",
//				new String[] { String.valueOf(TABLE_INDEX) }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//		else 
//			return new TicketSub();
//	
//		TicketSub ticket = new TicketSub();
//		ticket.leaseRunTicket		= cursor.getString( 2);
//		ticket.receiptTicket		= cursor.getString( 3);
//		ticket.estimatedBarrels		= cursor.getString( 4);
//		ticket.opFieldLoc			= cursor.getString( 5);
//		ticket.leaseNo				= cursor.getString( 6);
//		ticket.leaseCompName		= cursor.getString( 7);
//		ticket.stationNo			= cursor.getString( 8);
//		ticket.forAccountOf			= cursor.getString( 9);
//		ticket.stationName			= cursor.getString(10);
//		ticket.leaseLegalDesc		= cursor.getString(11);
//		ticket.county				= cursor.getString(12);
//		ticket.state				= cursor.getString(13);
//		ticket.nameReceiver			= cursor.getString(14);
//		ticket.tractorNo			= cursor.getString(15);
//		ticket.trailerNo			= cursor.getString(16);
//		ticket.tankNo				= cursor.getString(17);
//		
//		ticket.tankSize				= cursor.getString(18);
//		ticket.tankHeight			= cursor.getString(19);
//		
//		ticket.obsGravity			= cursor.getString(20);
//		ticket.obsTemp				= cursor.getString(21);
//		ticket.sw					= cursor.getString(22);
//		ticket.date					= cursor.getString(23);
//		ticket.ticketNo				= cursor.getString(24);
//		ticket.highDegreeF			= cursor.getString(25);
//		ticket.highOilFeet			= cursor.getString(26);
//		ticket.highOilInch			= cursor.getString(27);
//		ticket.highOilQrt			= cursor.getString(28);
//		ticket.hightankTableBarrels	= cursor.getString(29);
//		ticket.highTankBottoms		= cursor.getString(30);
//		ticket.lowDegreeF			= cursor.getString(31);
//		ticket.lowOilFeet			= cursor.getString(32);
//		ticket.lowOilInch			= cursor.getString(33);
//		ticket.lowOilQrt			= cursor.getString(34);
//		ticket.lowTankTableBarrels	= cursor.getString(35);
//		ticket.lowTankBottoms		= cursor.getString(36);
//		ticket.totalBarrels			= cursor.getString(37);
//		ticket.netBarrels			= cursor.getString(38);
//		ticket.levelOff				= cursor.getString(39);
//		ticket.levelOn				= cursor.getString(40);
//		ticket.loadedMiles			= cursor.getString(41);
//		ticket.meterFactor			= cursor.getString(42);
//		ticket.meteredBarrels		= cursor.getString(43);
//		ticket.remarks				= cursor.getString(44);
//		ticket.firstName			= cursor.getString(45);
//		ticket.lastName				= cursor.getString(46);
//		ticket.userID				= cursor.getString(47);
//		ticket.openID				= cursor.getString(48);
//		ticket.owner				= cursor.getString(49);
//		ticket.noOil				= cursor.getString(50);
//		ticket.lockTicket			= cursor.getString(51);
//		ticket.comments				= cursor.getString(52);
//		ticket.sealOff				= cursor.getString(53);
//		ticket.sealOn				= cursor.getString(54);
//
//		cursor.close();
//		db.close();
//	
//		return ticket;
//	}
//
//	// Updating single ticket
//	public int update_ticket(TicketSub ticket, String ticketID, int willSubmit) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(KEY_TICKET_ID               , ticketID);
//		values.put(KEY_LEASE_RUN_TICKET        , ticket.leaseRunTicket);
//		values.put(KEY_RECEIPT_TICKET          , ticket.receiptTicket);
//		values.put(KEY_ESTIMATED_BARRELS       , ticket.estimatedBarrels);
//		values.put(KEY_OP_FIELD_LOC            , ticket.opFieldLoc);
//		values.put(KEY_LEASE_NO                , ticket.leaseNo);
//		values.put(KEY_LEASE_COMP_NAME         , ticket.leaseCompName);
//		values.put(KEY_STATION_NO              , ticket.stationNo);
//		values.put(KEY_FOR_ACCOUNT_OF          , ticket.forAccountOf);
//		values.put(KEY_STATION_NAME            , ticket.stationName);
//		values.put(KEY_LEASE_LEGAL_DESC        , ticket.leaseLegalDesc);
//		values.put(KEY_COUNTY                  , ticket.county);
//		values.put(KEY_STATE                   , ticket.state);
//		values.put(KEY_NAME_RECEIVER           , ticket.nameReceiver);
//		values.put(KEY_TRACTOR_NO              , ticket.tractorNo);
//		values.put(KEY_TRAILER_NO              , ticket.trailerNo);
//		values.put(KEY_TANK_NO                 , ticket.tankNo);
//
//		values.put(KEY_TANK_SIZE               , ticket.tankSize);
//		values.put(KEY_TANK_HEIGHT             , ticket.tankHeight);
//		
//		values.put(KEY_OBS_GRAVITY             , ticket.obsGravity);
//		values.put(KEY_OBS_TEMP                , ticket.obsTemp);
//		values.put(KEY_SW                      , ticket.sw);
//		values.put(KEY_DATE                    , ticket.date);
//		values.put(KEY_TICKET_NO               , ticket.ticketNo);
//		values.put(KEY_HIGH_DEGREE_F           , ticket.highDegreeF);
//		values.put(KEY_HIGH_OIL_FEET           , ticket.highOilFeet);
//		values.put(KEY_HIGH_OIL_INCH           , ticket.highOilInch);
//		values.put(KEY_HIGH_OIL_QRT            , ticket.highOilQrt);
//		values.put(KEY_HIGH_TANK_TABLE_BARRELS , ticket.hightankTableBarrels);
//		values.put(KEY_HIGH_TANK_BOTTOMS       , ticket.highTankBottoms);
//		values.put(KEY_LOW_DEGREE_F            , ticket.lowDegreeF);
//		values.put(KEY_LOW_OIL_FEET            , ticket.lowOilFeet);
//		values.put(KEY_LOW_OIL_INCH            , ticket.lowOilInch);
//		values.put(KEY_LOW_OIL_QRT             , ticket.lowOilQrt);
//		values.put(KEY_LOW_TANK_TABLE_BARRELS  , ticket.lowTankTableBarrels);
//		values.put(KEY_LOW_TANK_BOTTOMS        , ticket.lowTankBottoms);
//		values.put(KEY_TOTAL_BARRELS           , ticket.totalBarrels);
//		values.put(KEY_NET_BARRELS             , ticket.netBarrels);
//		values.put(KEY_LEVEL_OFF               , ticket.levelOff);
//		values.put(KEY_LEVEL_ON                , ticket.levelOn);
//		values.put(KEY_LOADED_MILES            , ticket.loadedMiles);
//		values.put(KEY_METER_FACTOR            , ticket.meterFactor);
//		values.put(KEY_METERED_BARRELS         , ticket.meteredBarrels);
//		values.put(KEY_REMARKS                 , ticket.remarks);
//		values.put(KEY_FIRST_NAME              , ticket.firstName);
//		values.put(KEY_LAST_NAME               , ticket.lastName);
//		values.put(KEY_USER_ID                 , ticket.userID);
//		values.put(KEY_OPEN_ID                 , ticket.openID);
//		values.put(KEY_OWNER                   , ticket.owner);
//		values.put(KEY_NO_OIL                  , ticket.noOil);
//		values.put(KEY_LOCK_TICKET             , ticket.lockTicket);
//		values.put(KEY_COMMENTS                , ticket.comments);
//		values.put(KEY_SEAL_OFF                , ticket.sealOff);
//		values.put(KEY_SEAL_ON                 , ticket.sealOn);
//		values.put(KEY_WILL_SUBMIT             , willSubmit);
//		// updating row
//		return db.update(TABLE_TICKETS, values, KEY_TICKET_ID + " = ?",
//			new String[] { String.valueOf(ticketID) });
//	}
//	
//	public int update_field(HashMap<String, String> map, String ticketID) {
//		SQLiteDatabase db = this.getWritableDatabase();
//	
//		ContentValues values = new ContentValues();
//		Object[] keySet = map.keySet().toArray();
//		for (int i=0; i<keySet.length; i++) {
//			values.put(keySet[i].toString(), map.get(keySet[i]).toString());
//		}
//		
//		// updating row
//		return db.update(TABLE_TICKETS, values, KEY_TICKET_ID + " = ?",
//			new String[] { String.valueOf(ticketID) });
//	}
//
//		// Deleting single ticket
//	public void delete_Ticket(String ticketID) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_TICKETS, KEY_TICKET_ID + " = ?",
//			new String[] { String.valueOf(ticketID) });
//		db.close();
//		TABLE_INDEX = -1;
//	}
//
//	// Getting tickets Count
//	public int Get_Total_Tickets() {
//		String countQuery = "SELECT  * FROM " + TABLE_TICKETS;
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cursor = db.rawQuery(countQuery, null);
//		cursor.close();
//	
//		// return count
//		return cursor.getCount();
//	}
//	// Getting All tickets
//	public ArrayList<TicketSub> Get_tickets() {
//		try {
//			ArrayList<TicketSub> ticket_list = new ArrayList<TicketSub>();
//			SQLiteDatabase db = this.getReadableDatabase();
//			Cursor cursor = db.query(TABLE_TICKETS, null, KEY_WILL_SUBMIT + "=?",
//					new String[] { String.valueOf(AppConstants.SQL_WILL_SUBMIT) }, null, null, null, null);
//			if (cursor == null)
//				return null;
//	
//			// looping through all rows and adding to list
//			if (cursor.moveToFirst()) {
//			do {
//				TicketSub ticket = new TicketSub();
//				ticket.ticket_id			= cursor.getString(1);
//				ticket.leaseRunTicket		= cursor.getString( 2);
//				ticket.receiptTicket		= cursor.getString( 3);
//				ticket.estimatedBarrels		= cursor.getString( 4);
//				ticket.opFieldLoc			= cursor.getString( 5);
//				ticket.leaseNo				= cursor.getString( 6);
//				ticket.leaseCompName		= cursor.getString( 7);
//				ticket.stationNo			= cursor.getString( 8);
//				ticket.forAccountOf			= cursor.getString( 9);
//				ticket.stationName			= cursor.getString(10);
//				ticket.leaseLegalDesc		= cursor.getString(11);
//				ticket.county				= cursor.getString(12);
//				ticket.state				= cursor.getString(13);
//				ticket.nameReceiver			= cursor.getString(14);
//				ticket.tractorNo			= cursor.getString(15);
//				ticket.trailerNo			= cursor.getString(16);
//				ticket.tankNo				= cursor.getString(17);
//				
//				ticket.tankSize				= cursor.getString(18);
//				ticket.tankHeight			= cursor.getString(19);
//				
//				ticket.obsGravity			= cursor.getString(20);
//				ticket.obsTemp				= cursor.getString(21);
//				ticket.sw					= cursor.getString(22);
//				ticket.date					= cursor.getString(23);
//				ticket.ticketNo				= cursor.getString(24);
//				ticket.highDegreeF			= cursor.getString(25);
//				ticket.highOilFeet			= cursor.getString(26);
//				ticket.highOilInch			= cursor.getString(27);
//				ticket.highOilQrt			= cursor.getString(28);
//				ticket.hightankTableBarrels	= cursor.getString(29);
//				ticket.highTankBottoms		= cursor.getString(30);
//				ticket.lowDegreeF			= cursor.getString(31);
//				ticket.lowOilFeet			= cursor.getString(32);
//				ticket.lowOilInch			= cursor.getString(33);
//				ticket.lowOilQrt			= cursor.getString(34);
//				ticket.lowTankTableBarrels	= cursor.getString(35);
//				ticket.lowTankBottoms		= cursor.getString(36);
//				ticket.totalBarrels			= cursor.getString(37);
//				ticket.netBarrels			= cursor.getString(38);
//				ticket.levelOff				= cursor.getString(39);
//				ticket.levelOn				= cursor.getString(40);
//				ticket.loadedMiles			= cursor.getString(41);
//				ticket.meterFactor			= cursor.getString(42);
//				ticket.meteredBarrels		= cursor.getString(43);
//				ticket.remarks				= cursor.getString(44);
//				ticket.firstName			= cursor.getString(45);
//				ticket.lastName				= cursor.getString(46);
//				ticket.userID				= cursor.getString(47);
//				ticket.openID				= cursor.getString(48);
//				ticket.owner				= cursor.getString(49);
//				ticket.noOil				= cursor.getString(50);
//				ticket.lockTicket			= cursor.getString(51);
//				ticket.comments				= cursor.getString(52);
//				ticket.sealOff				= cursor.getString(53);
//				ticket.sealOn				= cursor.getString(54);
//				
//				// Adding ticket to list
//				ticket_list.add(ticket);
//			} while (cursor.moveToNext());
//			}
//	
//			// return ticket list
//			cursor.close();
//			db.close();
//			return ticket_list;
//		} catch (Exception e) {
//			Log.e("all_ticket", "" + e);
//			return null;
//		}
//	}

}
