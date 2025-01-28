package com.SmsCallback.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.SmsCallback.InternalRestRequest.RestRequestClass;
import com.SmsCallback.Model.callbackpracto;
import com.SmsCallback.Repository.callbackpractoRepository;
import com.SmsCallback.utility.CallbackPojo;


@Service
public class ConsumerService {

	@Autowired
	  callbackpractoRepository cbpRepository;
	  
	  @Autowired
	  RestRequestClass requestClass;
	  
	  Logger logger = LoggerFactory.getLogger(ConsumerService.class);
	
	public void callToClientAndSaveToDatabase(CallbackPojo cbp)
	{
		
		callbackpracto cb = new callbackpracto();
		cb.setCorelationid(cbp.getCorelationid());
		cb.setCreated_date(LocalDate.now());
		cb.setDeliverydt(cbp.getDeliverydt());
		cb.setDeliverystatus(cbp.getDeliverystatus());
		cb.setDescription(cbp.getDescription());
		cb.setFromk(cbp.getFromk());
		cb.setPdu(cbp.getPdu());
		cb.setText(cbp.getText());
		cb.setTok(cbp.getTok());
		cb.setTxid(cbp.getTxid());
		
		
		
		try {
		    // getcall returns a Future, convert it to CompletableFuture for chaining
		    CompletableFuture<ResponseEntity<String>> responseFuture = CompletableFuture.supplyAsync(() -> {
		        try {
		            return getcall(cb).get();  // Ensure getcall returns a Future
		        } catch (InterruptedException | ExecutionException e) {
			    	logger.info("Exception during ClientApi Call " + e.getLocalizedMessage());
		            throw new RuntimeException(e);
		        }
		    });

		    // Chain the saveCallbackpracto_archData to run after getcall completes
		    responseFuture.thenAccept(response -> {
		        saveCallbackpracto_archData(cb, response.getBody());
		    }).exceptionally(ex -> {
		    	
		        // Handle exceptions asynchronously and call saveCallbackpracto_archData with error message
		    	logger.info("Exception during saving to arch table " + ex.getLocalizedMessage());
		        saveCallbackpracto_archData(cb, ex.getLocalizedMessage());
		        
		        return null;
		    });

		} catch (Exception e) {
			logger.info("Exception occur : " + e.getLocalizedMessage());
		}
	}
	
	
	
	// @Async("apiCall")
	//   public Future<ResponseEntity<String>> getcall(callbackpracto cb) {
	//     return (Future<ResponseEntity<String>>)new AsyncResult(this.requestClass.getRequest(cb));
	//   }
	@Async("apiCall")
	  public Future<ResponseEntity<String>> getcall(callback_arch cb, String errorCode, String ErrorDescription) throws Exception {
	    return (Future<ResponseEntity<String>>)new AsyncResult(this.requestClass.getRequest(cb,errorCode,ErrorDescription));
	  }
	

	  @Async("callbackInsert")
	  public void saveCallbackpracto_archData(callbackpracto cb, String Response) {

	    try {	    	

	        DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	        String partition = "p"+LocalDate.now().format(dFormatter);

	        System.out.println(partition);
	    	
	    	this.cbpRepository.insertIntoPartitionPractoArch(partition, cb.getCorelationid(), cb.getTxid(), cb.getTok(), cb.getFromk(),
	    			cb.getDescription(), cb.getPdu(), cb.getText(), cb.getDeliverystatus(), cb.getDeliverydt(),
	    			Response);
	    	
	    	logger.info("Insert to arch txid : {}, Response:{}, System.currentTimeMillisEnd : {} ",cb.getTxid(),Response,System.currentTimeMillis());


	    }
	    
	    catch (Exception ex) {
	      System.out.println("cbsave error" + ex.getMessage());
	    }
	    
	  }
}
