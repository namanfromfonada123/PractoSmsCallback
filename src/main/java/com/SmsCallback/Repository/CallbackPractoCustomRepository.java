package com.SmsCallback.Repository;

import java.util.List;

import com.SmsCallback.Model.callbackpracto;
import com.SmsCallback.Model.callbackpracto_arch;

public interface CallbackPractoCustomRepository {

	   int deleteFromPartition(String partition, long id);
	   
	    int insertIntoPartition(String partition, String corelationid, String txid, String tok, String fromk, String description, String pdu, String text, String deliverystatus, String deliverydt, int flag);
	    
	    int insertIntoPartitionPractoArch(String partition, String corelationid, String txid, String tok, String fromk, String description, String pdu, String text, String deliverystatus, String deliverydt, String response);
	    
		  List<callbackpracto> findFiftyByFlagCustom( int limit,String partition);
		  
		  List<callbackpracto_arch> findBytransidAndCreatedDate(String transid, int limit, String CreatedDate);
		  List<callbackpracto_arch> findTransidinlastThreePartition(String transid, int limit);
}
