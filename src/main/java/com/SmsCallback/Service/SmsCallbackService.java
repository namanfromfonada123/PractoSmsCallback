package com.SmsCallback.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.SmsCallback.InternalRestRequest.RestRequestClass;
import com.SmsCallback.model.callback;
import com.SmsCallback.model.callback_arch;
import com.SmsCallback.utility.DateOrTimeFormat;
import com.SmsCallback.utility.callBackResponse;
import com.SmsCallback.Repository.callbackRepository;
import com.SmsCallback.Repository.callback_archRepository;

@Service
public class SmsCallbackService {
	Logger logger = LoggerFactory.getLogger(SmsCallbackService.class);

	@Autowired
	callbackRepository cbRepository;
	
	@Autowired
	RestRequestClass requestClass;
	
	@Autowired
	callback_archRepository  cbaRepository;

	
	public callback saveCallbackData(Map<String, String> callBackData){
		callback cb = new callback();
		cb.setCorelationid(callBackData.get("corelationid" ));
		cb.setTxid(callBackData.get("txid" ));
		cb.setTok(callBackData.get("to"));
		cb.setFromk(callBackData.get("from"));
		cb.setDescription(callBackData.get("description"));
		cb.setPdu(callBackData.get("pdu"));
		cb.setText(callBackData.get("text"));
		cb.setDeliverystatus(callBackData.get("deliverystatus"));
		cb.setDeliverydt(callBackData.get("deliverydt"));
		cb.setFlag(0);
		try {
			 return cbRepository.save(cb);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
		
	}
	
	@Async("Async")
	public Future<ResponseEntity<String>> getcall(callback cb) throws ParseException 
	{return	 new AsyncResult<>(requestClass.getRequest(cb)) ;}
	
   @Async("Async")
	public void saveCallback_archData(callback cb, String Response) {
		
		cb.setFlag(1);
		cbRepository.save(cb);
		   
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
		
		  cbaRepository.save(cba);
	}
   
   public List<callBackResponse> getCallbacks(String date) {
	
	   
//	   String dateString= DateOrTimeFormat.convertDateToString(DateOrTimeFormat.convertStringToDate(date));
	   
	   List<callback> callbacks = cbRepository.findByDeliverydt(date);
	   List<callBackResponse> cbrList = new ArrayList<>();
	   for(callback cb: callbacks)
	   {
		   callBackResponse cbr = new callBackResponse();
		   cbr.setCorelationid(cb.getCorelationid());
		   cbr.setDeliverydt(cb.getDeliverydt());
		   cbr.setDeliverystatus(cb.getDeliverystatus());
		   cbr.setDescription(cb.getDescription());
		   cbr.setId(cb.getId());
		   cbr.setPdu(cb.getPdu());
		   cbr.setTok(cb.getTok());
		   cbr.setTxid(cb.getTxid());
		   
		   cbrList.add(cbr);
	   }
	   
	   return cbrList;
	   
}
	

}