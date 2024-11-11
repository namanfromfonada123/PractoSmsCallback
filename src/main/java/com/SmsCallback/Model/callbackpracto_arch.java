package com.SmsCallback.Model;

import java.time.LocalDate;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
@Table(name = "callbackpractos_arch", uniqueConstraints = {@UniqueConstraint(columnNames = {"txid"})})
public class callbackpracto_arch {
	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private long id;
	  
	  private String corelationid;
	  
	  private String txid;
	  
	  private String tok;
	  
	  private String fromk;
	  
	  private String description;
	  
	  private String pdu;
	  
	  private String text;
	  
	  private String deliverystatus;
	  
	  private String deliverydt;
	  
	  private String response;
	  
	  private LocalDate created_date;

}
