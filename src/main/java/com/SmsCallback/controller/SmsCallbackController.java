package com.SmsCallback.controller;

import com.SmsCallback.Model.callbackpracto;
import com.SmsCallback.Repository.callbackpractoRepository;
import com.SmsCallback.Service.SmsCallbackService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsCallbackController {
  @Autowired
  SmsCallbackService smscbService;
  
  @Autowired
  callbackpractoRepository cbpRepository;
  
  
  //@GetMapping({"/practo"})
  public void CallBackpracto(@RequestParam Map<String, String> requestdata) {
    this.smscbService.saveCallbackpractoData(requestdata);
  }
  
  @GetMapping({"/get/transid"})
  public callbackpracto getbytransid(@RequestParam String transid, @RequestHeader("Auth-header") String authToken) throws Exception {
    if (authToken == null) {
      System.out.println("Self token authentication failed");
      throw new Exception("TOKEN_NOT_FOUND");
    } 
    if (!"UHJhY3RvQEZvbmFkYSFAJQ==".equals(authToken)) {
      System.out.println("token authentication failed");
      throw new Exception("AUTH_FAILED");
    } 
    callbackpracto cb = this.cbpRepository.findAllBytxid(transid);
    System.out.println(cb);
    return cb;
  }
  
  
  @GetMapping("/save")
	public void saveMultipleData() {
	  
	  System.out.println("saving Data to database");
		for (int i = 0; i < 10000; i++) {

			Map<String, String > calbackPractoDataMap = new HashMap<>();
			
	
			calbackPractoDataMap.put("corelationid", "9999999890.1200000000.1654002362.20220531.0.wzrk_default.-1");
			calbackPractoDataMap.put("txid", UUID.randomUUID().getMostSignificantBits() + "");
			calbackPractoDataMap.put("to", "919999778080");
			calbackPractoDataMap.put("from", "IPRCTO");
			calbackPractoDataMap.put("description", "Message delivered successfully");
			calbackPractoDataMap.put("pdu", "1");
			calbackPractoDataMap.put("text", "Hi, your followup visit at Excellence Esthetics is due on 1st Oct. - Practo");
			calbackPractoDataMap.put("deliverystatus", "DELIVERY_SUCCESS");
			calbackPractoDataMap.put("deliverydt", "2024-10-01T03:30:46");

			
			this.smscbService.saveCallbackpractoData(calbackPractoDataMap);
			
		}
  }
  
  
}