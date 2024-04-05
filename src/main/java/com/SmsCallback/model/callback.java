package com.SmsCallback.model;

import java.time.LocalDateTime;

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
@Table(name = "callbackril" , uniqueConstraints = @UniqueConstraint(columnNames = "txid"))
public class callback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	private String corelationid;
	private String txid;
	private String tok;
	private String fromk; // -- leave
	private String description;
	private String pdu;
	private String text;// -- leave
	private String deliverystatus;
	private String deliverydt;
	private int flag; // --leave
	

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

//http://{clienturl path}?corelationid={ corelationid }&txid={transactionId}&to={recpient}&from={sender}&description={description}&pdu={totalPdu}&text={message}&deliverystatus={status}&deliverydt={doneDate} 

//	 callbackril_arch--done
// get data by date --> date is taken as query param in get method--> done