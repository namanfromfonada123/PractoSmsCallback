package com.SmsCallback.Service;

import com.SmsCallback.InternalRestRequest.RestRequestClass;
import com.SmsCallback.Model.callback;
import com.SmsCallback.Model.callback_arch;
import com.SmsCallback.Model.callbackpracto;
import com.SmsCallback.Model.callbackpracto_arch;
import com.SmsCallback.Repository.callbackRepository;
import com.SmsCallback.Repository.callback_archRepository;
import com.SmsCallback.Repository.callbackpractoRepository;
import com.SmsCallback.Repository.callbackpracto_archRepository;

import java.time.LocalDate;
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
  callbackRepository cbRepository;
  
  @Autowired
  callbackpractoRepository cbpRepository;
  
  @Autowired
  RestRequestClass requestClass;
  
  @Autowired
  callback_archRepository cbaRepository;
  
  @Autowired
  callbackpracto_archRepository cbpaRepository;
  
  public callback saveCallbackData(Map<String, String> callBackData) {
    callback cb = new callback();
    cb.setCorelationid(callBackData.get("corelationid"));
    cb.setTxid(callBackData.get("txid"));
    cb.setTok(callBackData.get("to"));
    cb.setFromk(callBackData.get("from"));
    cb.setDescription(callBackData.get("description"));
    cb.setPdu(callBackData.get("pdu"));
    cb.setText(callBackData.get("text"));
    cb.setDeliverystatus(callBackData.get("deliverystatus"));
    cb.setDeliverydt(callBackData.get("deliverydt"));
    cb.setFlag(0);
    try {
      return (callback)this.cbRepository.save(cb);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    } 
  }
  
  public callbackpracto saveCallbackpractoData(Map<String, String> callBackData) {
    callbackpracto cb = new callbackpracto();
    
    cb.setCreated_date(String.valueOf(LocalDate.now()));
    cb.setCorelationid(callBackData.get("corelationid"));
    cb.setTxid(callBackData.get("txid"));
    cb.setTok(callBackData.get("to"));
    cb.setFromk(callBackData.get("from"));
    cb.setDescription(callBackData.get("description"));
    cb.setPdu(callBackData.get("pdu"));
    cb.setText(callBackData.get("text"));
    cb.setDeliverystatus(callBackData.get("deliverystatus"));
    cb.setDeliverydt(callBackData.get("deliverydt"));
    cb.setCreated_date(String.valueOf(LocalDate.now()));
    cb.setFlag(0);
    
    try {
      return (callbackpracto)this.cbpRepository.save(cb);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    } 
  }
  
  @Async("Async")
  public Future<ResponseEntity<String>> getcall(callback cb) {
    return (Future<ResponseEntity<String>>)new AsyncResult(this.requestClass.getRequest(cb));
  }
  
  @Async("Async")
  public Future<ResponseEntity<String>> getcall(callbackpracto cb) {
    return (Future<ResponseEntity<String>>)new AsyncResult(this.requestClass.getRequest(cb));
  }
  
  public callback_arch saveCallback_archData(callback cb, String Response) {
    callback_arch cba = new callback_arch();
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
    
    return (callback_arch)this.cbaRepository.save(cba);
  }
  
  @Async("Async")
  public void saveCallbackpracto_archData(callbackpracto cb, String Response) {
//    cb.setFlag(1);
//    this.cbpRepository.save(cb);
//	  this.cbpRepository.updateCallBackFlagForPracto(cb.getId());
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
    cba.setCreated_date(String.valueOf(LocalDate.now()));
    System.out.println("cba" + cba);
    try {
      this.cbpaRepository.save(cba);
      this.cbpRepository.deleteCallBackFlagForPracto(cb.getId());

      
    } catch (Exception ex) {
      System.out.println("cbsave error" + ex.getMessage());
    } 
  }
}
