package com.taxiking.customer.bean;

public class Ticket {

	public static final int OPEN_TICKET		= 0;
	public static final int SUB_TICKET		= 1;
	public static final int CLOSED_TICKET	= 2;
	
	public static final int STATUS_OPEN		= 0;
	public static final int STATUS_PROGRESS	= 1;
	public static final int STATUS_SUB		= 2;
	public static final int STATUS_CLOSED	= 3;
	
	public String id = "";
	
	public String showText1 = new String();
	public String showText2 = new String();
	public String showText3 = new String();
	public String showText4 = new String();
	public String showText5 = new String();
	public String showText6 = new String();

	public String comment = new String();

}
