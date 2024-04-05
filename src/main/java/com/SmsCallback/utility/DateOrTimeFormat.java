package com.SmsCallback.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateOrTimeFormat {
	


	public static String changeDateFormate( String date) throws ParseException {
		SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MM-yyyy");
	    Date dateobj = sdfSource.parse(date);
	    SimpleDateFormat sdfDestination = new SimpleDateFormat("yyyy-MM-dd");
	    String dateString= sdfDestination.format(dateobj);
	    System.out.println(dateString);
	    return dateString;
	    
	}
	
	
}
