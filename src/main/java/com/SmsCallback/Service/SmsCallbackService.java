package com.SmsCallback.Service;

import com.SmsCallback.InternalRestRequest.RestRequestClass;

import com.SmsCallback.Model.callbackpracto;
import com.SmsCallback.Model.callbackpracto_arch;

import com.SmsCallback.Repository.callbackpractoRepository;
import com.SmsCallback.Repository.callbackpracto_archRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class SmsCallbackService {

  @Autowired
  callbackpractoRepository cbpRepository;
  
  @Autowired
  RestRequestClass requestClass;
  
  @Autowired
  callbackpracto_archRepository cbpaRepository;
  
 
  
  public void saveCallbackpractoData(Map<String, String> callBackData) {
    callbackpracto cb = new callbackpracto();
    
	DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	String partition = "p"+LocalDate.now().format(dFormatter);
        
    try {
    	this.cbpRepository.insertIntoPartition(partition, callBackData.get("corelationid"), callBackData.get("txid"), callBackData.get("to"), callBackData.get("from"),
    			callBackData.get("description"), callBackData.get("pdu"), callBackData.get("text"), callBackData.get("deliverystatus"), callBackData.get("deliverydt"), 0);
    	
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
  }
  

@Async("Async")
  public Future<ResponseEntity<String>> getcall(callbackpracto cb) {
    return (Future<ResponseEntity<String>>)new AsyncResult(this.requestClass.getRequest(cb));
  }

  @Async("Async")
  public void saveCallbackpracto_archData(callbackpracto cb, String Response) {

    callbackpracto_arch cba = new callbackpracto_arch();
    cba.setCorelationid(cb.getCorelationid());
    cba.setTxid(cb.getTxid());
    cba.setTok(cb.getTok());
    cba.setFromk(cb.getFromk());
    cba.setDescription(cb.getDescription());
    cba.setPdu(cb.getPdu());
    cba.setText(cb.getText());
    cba.setDeliverystatus(cb.getDeliverystatus());
    cba.setDeliverydt(cb.getDeliverydt());
    cba.setResponse(Response);
    cba.setCreated_date(LocalDate.now());
    System.out.println("cba" + cba);
    try {

        DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String partition = "p"+LocalDate.now().format(dFormatter);

    	
    	this.cbpRepository.insertIntoPartitionPractoArch(partition, cb.getCorelationid(), cb.getTxid(), cb.getTok(), cb.getFromk(),
    			cb.getDescription(), cb.getPdu(), cb.getText(), cb.getDeliverystatus(), cb.getDeliverydt(),
    			Response);
      
      this.cbpRepository.deleteFromPartition(partition, cb.getId());
    }
    
    catch (Exception ex) {
      System.out.println("cbsave error" + ex.getMessage());
    } 
  }
}
