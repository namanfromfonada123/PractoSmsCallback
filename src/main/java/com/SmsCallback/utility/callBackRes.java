package com.SmsCallback.utility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class callBackRes{
	
	private String corelationid;
	private String sessionid;
	private String dest;
	private String senderid;
	private String description;
	private String pdu;
	private String status;
	private String dtime;
	private String stime;
	private String clicked;
	
}
