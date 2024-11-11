package com.SmsCallback.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Indedd {

	public static void main(String[] args) {
	
//		DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		String createdDate = "2024-10-07";
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyyMMdd");

		
		
	
			String partition = "P"+ simpleDateFormat2.format(LocalDate.parse(createdDate,dateTimeFormatter));			
			System.out.println("partition : "+ partition);
			System.out.println("epoch time : "+ LocalDate.parse(createdDate).toEpochDay());
		
//		System.out.println("partition : "+ partition);
		
	}
}
