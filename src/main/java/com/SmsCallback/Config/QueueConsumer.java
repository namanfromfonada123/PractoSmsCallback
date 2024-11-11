package com.SmsCallback.Config;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SmsCallback.Model.callbackpracto;
import com.SmsCallback.Service.ConsumerService;
import com.SmsCallback.utility.CallbackPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Component
public class QueueConsumer {

	
	@Autowired
	ConsumerService consumerService;
	
	@RabbitListener(queues = "practo.callback.queue" , containerFactory = "prefetchContainerFactory" )
	public void recieveMessage(CallbackPojo message){
        
		System.out.println("Consuming practo data from Queue : "+ message);
			consumerService.callToClientAndSaveToDatabase(message);

		
	}	
	
}
