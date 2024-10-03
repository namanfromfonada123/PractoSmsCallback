package com.SmsCallback.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Data
@Table(name = "callbackpractos", uniqueConstraints = {@UniqueConstraint(columnNames = {"txid"})})
public class callbackpracto {
	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  long id;
	  
	  private String corelationid;
	  
	  private String txid;
	  
	  private String tok;
	  
	  private String fromk;
	  
	  private String description;
	  
	  private String pdu;
	  
	  private String text;
	  
	  private String deliverystatus;
	  
	  private String deliverydt;
	  
	  private int flag;
	  
	  private LocalDate created_date;
	  
	  
 
}
