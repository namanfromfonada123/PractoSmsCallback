package com.SmsCallback.Service;

import com.SmsCallback.Model.callbackpracto;
import com.SmsCallback.Repository.callbackpractoRepository;
import java.util.List;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.SchedulerBeanDefinitionParser;
import org.springframework.stereotype.Component;

@Component
public class SchedularClass {
  Logger logger = LoggerFactory.getLogger(com.SmsCallback.Service.SchedularClass.class);
  
  @Autowired
  callbackpractoRepository cbRepository;
  
  @Autowired
  SmsCallbackService smscbService;
  
//  @Scheduled(fixedRate = 1000L)
  public void makeCall() {
	  
    boolean schedularStart = true;
    List<callbackpracto> cblist = this.cbRepository.findFiftyByFlag(0, 5000);
    if (schedularStart)
      if (!cblist.isEmpty()) {
        for (callbackpracto cb : cblist) {
          try {
        	  
        	  System.out.println("cb1 : "+cb);
            Future<ResponseEntity<String>> response = this.smscbService.getcall(cb);        	  
            this.smscbService.saveCallbackpracto_archData(cb, (String)((ResponseEntity)response.get()).getBody());
        	          	  
          } catch (Exception e) {
            this.logger.error(e.getMessage());
            this.logger.error("Error --------");
            this.logger.error("Error --------" + cb);
            this.smscbService.saveCallbackpracto_archData(cb, e.getMessage());
          } 
        } 
      } else {
        this.logger.info("No More Callback Left to call");
        schedularStart = false;
      }  
  }
  
// @Scheduled(fixedDelay = 1000L)
  public void makeCall1() {
    boolean schedularStart = true;
    
    List<callbackpracto> cblist = this.cbRepository.findFiftyByCurrentDateFlag(0, 5000,"2024-09-27 00:00:00");
    if (schedularStart)
      if (!cblist.isEmpty()) {
        for (callbackpracto cb : cblist) {
          try {
        	  
        	  System.out.println("cb2 : "+cb);
            Future<ResponseEntity<String>> response = this.smscbService.getcall(cb);
            this.smscbService.saveCallbackpracto_archData(cb, (String)((ResponseEntity)response.get()).getBody());
          } catch (Exception e) {
            this.logger.error(e.getMessage());
            this.logger.error("Error --------");
            this.logger.error("Error --------" + cb);
            this.smscbService.saveCallbackpracto_archData(cb, e.getMessage());
          } 
        } 
        
        
        System.out.println("*************************************************************");
      } else {
        this.logger.info("No More Callback Left to call");
        schedularStart = false;
      }  
  }
  
  
 @Scheduled(fixedDelay = 1000L)
 public void makeCall2() {
   boolean schedularStart = true;
   
   List<callbackpracto> cblist = this.cbRepository.findFiftyByLessCurrentDateFlag(0, 5000,"2024-09-27 00:00:00");
   if (schedularStart)
     if (!cblist.isEmpty()) {
       for (callbackpracto cb : cblist) {
         try {
       	  
       	  System.out.println("cb2 : "+cb);
           Future<ResponseEntity<String>> response = this.smscbService.getcall(cb);
           this.smscbService.saveCallbackpracto_archData(cb, (String)((ResponseEntity)response.get()).getBody());
         } catch (Exception e) {
           this.logger.error(e.getMessage());
           this.logger.error("Error --------");
           this.logger.error("Error --------" + cb);
           this.smscbService.saveCallbackpracto_archData(cb, e.getMessage());
         } 
       } 
       
       
       System.out.println("*************************************************************");
     } else {
       this.logger.info("No More Callback Left to call");
       schedularStart = false;
     }  
 }
 
}
