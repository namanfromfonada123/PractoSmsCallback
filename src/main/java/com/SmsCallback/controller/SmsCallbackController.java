package com.SmsCallback.controller;


import com.SmsCallback.Model.PractoMessageData;
import com.SmsCallback.Model.callbackpracto_arch;
import com.SmsCallback.Repository.PractoMesssageDataRepository;
import com.SmsCallback.Repository.callbackpractoRepository;
import com.SmsCallback.Service.SmsCallbackService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SmsCallbackController {
  @Autowired
  SmsCallbackService smscbService;
   
  @Autowired
  callbackpractoRepository cbpRepository;
  
  @Autowired
  PractoMesssageDataRepository practoMesssageDataRepository;
  
  Logger logger = LoggerFactory.getLogger(SmsCallbackController.class);
  
  @GetMapping({"/practo"})
  public void CallBackpracto(@RequestParam Map<String, String> requestdata) {
	 
	  logger.info("Data from Operator : "+ requestdata.toString());
	  
    this.smscbService.saveCallbackpractoData(requestdata);
  }
  
//  @GetMapping({"/get/transid"})
//  public callbackpracto getbytransid(@RequestParam String transid, @RequestHeader("Auth-header") String authToken) throws Exception {
//    if (authToken == null) {
//      System.out.println("Self token authentication failed");
//      throw new Exception("TOKEN_NOT_FOUND");
//    } 
//    if (!"UHJhY3RvQEZvbmFkYSFAJQ==".equals(authToken)) {
//      System.out.println("token authentication failed");
//      throw new Exception("AUTH_FAILED");
//    } 
//    callbackpracto cb = this.cbpRepository.findAllBytxid(transid);
//    System.out.println(cb);
//    return cb;
//  }
  
  @GetMapping({"/get/transid"})
  public callbackpracto_arch getpushedbytransid(@RequestParam String transid, @RequestHeader("Auth-header") String authToken) throws Exception {
    if (authToken == null) {
      System.out.println("Self token authentication failed");
      throw new Exception("TOKEN_NOT_FOUND");
    } 
    if (!"UHJhY3RvQEZvbmFkYSFAJQ==".equals(authToken)) {
      System.out.println("token authentication failed");
      throw new Exception("AUTH_FAILED");
    } 
		 List<callbackpracto_arch> cb = this.cbpRepository.findTransidinlastThreePartition(transid, 1);
		 		 
		 if (!cb.isEmpty()) {
			 
			 logger.info("Data Regarding txid : {} => callbackData : {} ",transid,cb.get(0));
			 
			return cb.get(0);
			
		}else {
			
			logger.info("NO Data found Regarding txid : {} ",transid );
			
			return null;
		}
		   
  }
  
//  @GetMapping("/save")
//	public void saveMultipleData() {
//	  
//	  System.out.println("saving Data to database");
//		for (int i = 0; i < 10; i++) {
//
//			Map<String, String > calbackPractoDataMap = new HashMap<>();
//			
//	
//			calbackPractoDataMap.put("corelationid", "9999999890.1200000000.1654002362.20220531.0.wzrk_default.-1");
//			calbackPractoDataMap.put("txid", UUID.randomUUID().getMostSignificantBits() + "");
//			calbackPractoDataMap.put("to", "919999778080");
//			calbackPractoDataMap.put("from", "IPRCTO");
//			calbackPractoDataMap.put("description", "Message delivered successfully");
//			calbackPractoDataMap.put("pdu", "1");
//			calbackPractoDataMap.put("text", "Hi, your followup visit at Excellence Esthetics is due on 1st Oct. - Practo");
//			calbackPractoDataMap.put("deliverystatus", "DELIVERY_SUCCESS");
//			calbackPractoDataMap.put("deliverydt", "01-08-2024 03:30:46");
//			
//			this.smscbService.saveCallbackpractoData(calbackPractoDataMap);
//			
//		}
//  }
  
  	
//  @PostMapping(value = "/savePractoDataExcel", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
//  public void uploadUserIpMappingFile(@RequestPart("file") MultipartFile file) throws IOException {
//      // Automatically close resources
////      try (InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
////           CSVParser csvParser = new CSVParser(inputStreamReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
////          
////          List<Map<String, String>> practoMapList = new ArrayList<>();
////          
////          for (CSVRecord csvRecord : csvParser) {
////              Map<String, String> calbackPractoDataMap = new HashMap<>();
////              calbackPractoDataMap.put("corelationid", csvRecord.get("corelationid"));
////              calbackPractoDataMap.put("txid", csvRecord.get("message_id"));
////              calbackPractoDataMap.put("to", csvRecord.get("msisdn"));
////              calbackPractoDataMap.put("from", csvRecord.get("senderID"));
////              calbackPractoDataMap.put("description", csvRecord.get("description"));
////              calbackPractoDataMap.put("pdu", csvRecord.get("message_pdu"));
////              calbackPractoDataMap.put("text", csvRecord.get("message_text"));
////              calbackPractoDataMap.put("deliverystatus", csvRecord.get("message_state"));
////              calbackPractoDataMap.put("deliverydt", csvRecord.get("done_date"));
////
////              logger.info("Data from Operator : " + calbackPractoDataMap.toString());
////
////              practoMapList.add(calbackPractoDataMap);
////              
////              
////              this.smscbService.saveCallbackpractoData(calbackPractoDataMap);
////          }
//	  	  
//	  
//	  
//	  	Thread thread1 = new Thread(()->{
//	  		this.processBatch(9);
//	  		
//	  	});
//	  	
//	  	Thread thread2 = new Thread(()->{
//	  		this.processBatch(10);
//	  	});
//	  
//	  	
////	  	Thread thread3 = new Thread(()->{
////	  		this.processBatch(3);
////	  	});
//	  	
//	 // Start all threads
//	  	thread1.start();
//	  	thread2.start();
////	  	thread3.start();
//
//	  	// Wait for all threads to finish
//	  	try {
//	  	    thread1.join();
//	  	    thread2.join();
////	  	    thread3.join();
//	  	} catch (InterruptedException e) {
//	  	    e.printStackTrace();
//	  	}
//	  
//	  	
//	  	
//   
//  }
//
//  
//  private void processBatch(int pagenumber) {
//	  
//	  Pageable pageable = PageRequest.of(pagenumber, 100000);
//	  Page<PractoMessageData> page = practoMesssageDataRepository.findAll(pageable);
//	  List<PractoMessageData> dataBatch = page.getContent();
//	  
//	  
//	  
//      List<Map<String, String>> practoMapList = new ArrayList<>();
//      
//      for (PractoMessageData practo_message_data : dataBatch) {
//          Map<String, String> calbackPractoDataMap = new HashMap<>();
//          calbackPractoDataMap.put("corelationid", practo_message_data.getCorelationId());
//          calbackPractoDataMap.put("txid", practo_message_data.getMessageId().toString());
//          calbackPractoDataMap.put("to", practo_message_data.getMsisdn());
//          calbackPractoDataMap.put("from", practo_message_data.getSenderID());
//          calbackPractoDataMap.put("description", practo_message_data.getDescription());
//          calbackPractoDataMap.put("pdu", practo_message_data.getMessagePdu());
//          calbackPractoDataMap.put("text", practo_message_data.getMessageText());
//          calbackPractoDataMap.put("deliverystatus", practo_message_data.getMessageState());
//          calbackPractoDataMap.put("deliverydt", practo_message_data.getDoneDate());
//
//          logger.info("Data from Operator : " + calbackPractoDataMap.toString());
//
//          practoMapList.add(calbackPractoDataMap);
//
//          // Call service method
//          this.smscbService.saveCallbackpractoData(calbackPractoDataMap);
//      }
//
//      logger.info(" total Element : " + page.getTotalElements());
//      logger.info(" total page : " + page.getTotalPages());
//      logger.info("Batch records processed : " + practoMapList.size());
//  }
//  
//  
  
  
  
  
  
}