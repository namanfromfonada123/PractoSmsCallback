package com.SmsCallback.controller;

import com.SmsCallback.Model.callback;
import com.SmsCallback.Model.callbackpracto;
import com.SmsCallback.Repository.callbackRepository;
import com.SmsCallback.Repository.callbackpractoRepository;
import com.SmsCallback.Service.SmsCallbackService;
import java.util.Map;
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
  callbackRepository cbRepository;
  
  @Autowired
  callbackpractoRepository cbpRepository;
  
//  @GetMapping({"/"})
  public void CallBack(@RequestParam Map<String, String> requestdata) {
    this.smscbService.saveCallbackData(requestdata);
  }
  
  //@GetMapping({"/practo"})
  public void CallBackpracto(@RequestParam Map<String, String> requestdata) {
    this.smscbService.saveCallbackpractoData(requestdata);
  }
  
//  @GetMapping({"/get"})
//  public callback getbyzero() {
//    callback cb = this.cbRepository.findFirstByFlag(0);
//    System.out.println(cb);
//    return cb;
//  }
  
  //@GetMapping({"/get/transid"})
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
}