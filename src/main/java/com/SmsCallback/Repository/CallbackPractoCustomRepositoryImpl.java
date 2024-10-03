package com.SmsCallback.Repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.SmsCallback.Model.callbackpracto;

public class CallbackPractoCustomRepositoryImpl implements CallbackPractoCustomRepository{


	Logger logger = LoggerFactory.getLogger(CallbackPractoCustomRepositoryImpl.class);
	
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public int deleteFromPartition(String partition, long id) {
        String query = "DELETE FROM callbackpractos PARTITION (" + partition + ") WHERE id = :id";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("id", id);
        return nativeQuery.executeUpdate();
    }

    @Override
    @Transactional
    public int insertIntoPartition(String partition, String corelationid, String txid, String tok, String fromk,
                                   String description, String pdu, String text, String deliverystatus, 
                                   String deliverydt, int flag) {
    	
    	
    	
        String query = "INSERT INTO callbackpractos PARTITION (" + partition + ") " +
                       "(corelationid, txid, tok, fromk, description, pdu, text, deliverystatus, deliverydt, created_date, flag) " +
                       "VALUES (:corelationid, :txid, :tok, :fromk, :description, :pdu, :text, :deliverystatus, :deliverydt, :createdDate, :flag)";
        
        
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("corelationid", corelationid);
        nativeQuery.setParameter("txid", txid);
        nativeQuery.setParameter("tok", tok);
        nativeQuery.setParameter("fromk", fromk);
        nativeQuery.setParameter("description", description);
        nativeQuery.setParameter("pdu", pdu);
        nativeQuery.setParameter("text", text);
        nativeQuery.setParameter("deliverystatus", deliverystatus);
        nativeQuery.setParameter("deliverydt", deliverydt);
        nativeQuery.setParameter("createdDate", LocalDate.now());
        nativeQuery.setParameter("flag", flag);
        
        
        logger.info("corelationid : {}, txid : {}, tok : {}, fromk :{}, description: {}, pdu:{}, text: {}, deliverystatus : {}, deliverydt : {}, createdDate : {} ",
        		corelationid,txid,tok,fromk,description,pdu,text,deliverystatus,deliverydt,LocalDate.now());
        
        return nativeQuery.executeUpdate();
    }

	@Override
	@Transactional
	public int insertIntoPartitionPractoArch(String partition, String corelationid, String txid, String tok,
			String fromk, String description, String pdu, String text, String deliverystatus, String deliverydt,
			String response) {
  	
    	
    	
        String query = "INSERT INTO callbackpractos_arch PARTITION (" + partition + ") " +
                       "(corelationid, txid, tok, fromk, description, pdu, text, deliverystatus, deliverydt, created_date, response) " +
                       "VALUES (:corelationid, :txid, :tok, :fromk, :description, :pdu, :text, :deliverystatus, :deliverydt, :createdDate, :response)";
        
        
        
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("corelationid", corelationid);
        nativeQuery.setParameter("txid", txid);
        nativeQuery.setParameter("tok", tok);
        nativeQuery.setParameter("fromk", fromk);
        nativeQuery.setParameter("description", description);
        nativeQuery.setParameter("pdu", pdu);
        nativeQuery.setParameter("text", text);
        nativeQuery.setParameter("deliverystatus", deliverystatus);
        nativeQuery.setParameter("deliverydt", deliverydt);
        nativeQuery.setParameter("createdDate", LocalDate.now());
        nativeQuery.setParameter("response", response);
               
        return nativeQuery.executeUpdate();
	}

	@Override
	public List<callbackpracto> findFiftyByFlagCustom(int flag, int limit, String partition) {
		
		
		String query = "Select * from callbackpractos PARTITION ("+partition+") where flag=:flag  limit  :limit  ";
		
		Query nativeQuery = entityManager.createNativeQuery(query,callbackpracto.class);
		
		nativeQuery.setParameter("flag", flag);
		nativeQuery.setParameter("limit", limit);
		
		return nativeQuery.getResultList();
	}
	
	
}
