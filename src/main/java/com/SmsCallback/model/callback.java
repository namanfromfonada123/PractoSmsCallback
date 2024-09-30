package com.SmsCallback.Model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "callback_dhani" , uniqueConstraints = @UniqueConstraint(columnNames = "txid"))
public class callback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	private String corelationid;

	@Column(length = 50)
	private String txid;//20
	
	@Column(length = 20)
	private String tok;//15 mo
	
	@Column(length = 35)
	private String fromk; //30
	
	@Column(length = 150)
	private String description;
	
	@Column(length = 20)
	private String pdu;
	
	@Column(length = 1000)
	private String text; // 1000
	
	@Column(length = 50)
	private String deliverystatus; //15
	
	@Column(length = 20)
	private String deliverydt;//20
	
	@Column(length = 20)
	private String submitDate;//20
	
	@Column(columnDefinition = "varchar(1) default 0")
	private String clicked;
	private int flag; 
	
	@Column(length = 20)
	private String createdOn;
	
	@Column(length = 20)
	private String updatedOn;
	  
	  @PrePersist
	  protected void onCreate() {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	      createdOn = LocalDateTime.now().format(formatter);
	  }
	  
	  @PreUpdate
	  protected void onUpdate() {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	      updatedOn = LocalDateTime.now().format(formatter);
	  }
}
