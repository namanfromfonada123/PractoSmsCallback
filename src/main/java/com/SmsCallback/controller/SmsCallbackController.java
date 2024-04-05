package com.SmsCallback.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SmsCallback.model.callback;
import com.SmsCallback.utility.DateOrTimeFormat;
import com.SmsCallback.utility.callBackResponse;
import com.SmsCallback.Repository.callbackRepository;
import com.SmsCallback.Service.SmsCallbackService;


@RestController
public class SmsCallbackController {

	Logger logger = LoggerFactory.getLogger(SmsCallbackController.class);
	
	@Autowired
	SmsCallbackService smscbService;
	
	@Autowired 
	 callbackRepository cbRepository;
	
	@GetMapping("/")
	public String CallBack(@RequestParam Map<String, String> requestdata) {		
			callback cb =	smscbService.saveCallbackData(requestdata);
			if(cb!=null) {
				
		logger.info(cb+" Save Successfully");		
				return "Save Successfully";
			}
			else {
		logger.info(cb +" Unable to save successfully!!");		
				return "Save Unsuccessfull";
				}
	}
	
	@GetMapping("/save")
	public void saveMultipleData() {
		for (int i = 0; i <10000; i++) {
			callback cb = new callback();
			
			cb.setCorelationid("abc");
			cb.setDeliverydt("19-01-2024");
			cb.setDeliverystatus("sdsdfdfdf");
			cb.setDescription("sadasdwq jncinein");
			cb.setFlag(0);
			cb.setFromk("wyeuwyeuwy");
			cb.setPdu(23+"");
			cb.setText("asdf");
			cb.setTok("werer");
			cb.setTxid(UUID.randomUUID().getMostSignificantBits()+"");
			
			logger.info(" Save Successfully :"+ cb);		
			cbRepository.save(cb);
		}
	}
	
	@GetMapping("/getCallByDate")
	public List<callBackResponse> getCallBackByDate(@RequestParam("DeliveryDate") String DeliveryDate) {
		return smscbService.getCallbacks(DeliveryDate);
	}
	
	@GetMapping("/get")
	public List<callback> getCallback()
	{
		return cbRepository.findByDeliverydt("2024-01-19");
	}
	
//	@GetMapping("/getDate")
//	public void printDate() {
//		try {
//			System.out.println(DateOrTimeFormat.changeDateFormate("2024-01-19"));
//		} catch (ParseException e) {
//			System.out.println(e.getMessage());
//		}
//	}
	
}

