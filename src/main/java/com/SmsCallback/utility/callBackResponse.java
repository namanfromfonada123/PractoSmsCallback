package com.SmsCallback.utility;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class callBackResponse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	private String corelationid;
	private String txid;
	private String tok;
	private String description;
	private String pdu;
	private String deliverystatus;
	private String deliverydt;
	
}
