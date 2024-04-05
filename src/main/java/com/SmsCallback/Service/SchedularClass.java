package com.SmsCallback.Service;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.SmsCallback.model.callback;
import com.SmsCallback.Repository.callbackRepository;

@Component
public class SchedularClass {
	
	Logger logger = LoggerFactory.getLogger(SchedularClass.class);
	
	@Autowired
	callbackRepository cbRepository;

	@Autowired
	SmsCallbackService smscbService;
	
	@Scheduled(fixedDelay =  1000)
	public void Scheduler() {
		boolean schedularStart=true;
		List<callback> cblist = cbRepository.findFiftyByFlag(0,5000);
		if(schedularStart) {
		
				if(!cblist.isEmpty()) {
					for (callback cb : cblist) {
						try {
							logger.info("Calling Get Request !!");
							Future<ResponseEntity<String>> response=smscbService.getcall(cb);
							smscbService.saveCallback_archData(cb, response.get().getBody());
							logger.info("successfully calling Get Request !!");
							
						}catch (HttpClientErrorException | HttpServerErrorException e) {
							logger.error("Getting Exception for : " + cb + " !! " + e.getStatusCode());
							smscbService.saveCallback_archData(cb,e.getResponseBodyAsString());
						} catch (InterruptedException |ExecutionException|ParseException e) {
							logger.error("Getting Exception for : " + cb + " !! " + e.getMessage());

						} 
					}
					}
				else {
					logger.info("No More Callback Left to call");
					schedularStart=false;
				}
			
		}
	}

}
// change the position of try inside for loop to handle result for each