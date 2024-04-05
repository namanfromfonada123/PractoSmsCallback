package com.SmsCallback.model;

import java.time.LocalDateTime;

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
@Table(name = "callbackril_arch" , uniqueConstraints = @UniqueConstraint(columnNames = "txid"))
public class callback_arch {
	
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
	
	
	@Column(length = 65535, columnDefinition = "Text")
	private String response;
	

	  private String createdOn;
	  private String updatedOn;
	  
	  @PrePersist
	  protected void onCreate() {
	      createdOn = LocalDateTime.now().toString();
	  }
	  
	  @PreUpdate
	  protected void onUpdate() {
	      updatedOn = LocalDateTime.now().toString();
	  }
}