package com.SmsCallback.Repository;

import java.time.LocalDate;
import java.util.List;

import com.SmsCallback.Model.callbackpracto;

public interface CallbackPractoCustomRepository {

	   int deleteFromPartition(String partition, long id);
	   
	    int insertIntoPartition(String partition, String corelationid, String txid, String tok, String fromk, String description, String pdu, String text, String deliverystatus, String deliverydt, int flag);
	    
	    int insertIntoPartitionPractoArch(String partition, String corelationid, String txid, String tok, String fromk, String description, String pdu, String text, String deliverystatus, String deliverydt, String response);
	    
		  List<callbackpracto> findFiftyByFlagCustom(int flag, int limit,String partition);
}
