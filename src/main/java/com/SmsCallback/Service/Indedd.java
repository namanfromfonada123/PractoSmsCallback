package com.SmsCallback.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Indedd {

	public static void main(String[] args) {
	
		DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String partition = "P"+LocalDate.now().format(dFormatter);
		System.out.println("partition : "+ partition);
		
	}
}
