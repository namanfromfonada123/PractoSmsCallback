package com.SmsCallback.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateOrTimeFormat {
	


	public static String changeDateFormate( String date , String Formate1, String Formate2) throws ParseException {

		SimpleDateFormat sdfSource = new SimpleDateFormat(Formate1);
	    Date dateobj = sdfSource.parse(date);
	    
	    SimpleDateFormat sdfDestination = new SimpleDateFormat(Formate2);
	    String dateString= sdfDestination.format(dateobj);

	    return dateString;
	    
	}
	
	
}
