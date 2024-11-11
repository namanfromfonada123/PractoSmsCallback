package com.SmsCallback.Repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.SmsCallback.Model.callbackpracto;
import com.SmsCallback.Model.callbackpracto_arch;

public interface callbackpracto_archRepository extends JpaRepository<callbackpracto_arch, Long> {
	
	
	callbackpracto_arch findAllBytxid(String paramString);
	
//	  @Modifying
//	  @Transactional
//	  @Query(value = "INSERT INTO callbackpractos_arch PARTITION(:partition) (corelationid, txid, tok, fromk, description, pdu, text, deliverystatus, deliverydt, created_date, flag ,response) " +
//	                 "VALUES (:corelationid, :txid, :tok, :fromk, :description, :pdu, :text, :deliverystatus, :deliverydt, :createdDate, :flag, :response)", nativeQuery = true)
//	  int insertIntoPartition(@Param("partition") String partition,
//	                          @Param("corelationid") String corelationid,
//	                          @Param("txid") String txid,
//	                          @Param("tok") String tok,
//	                          @Param("fromk") String fromk,
//	                          @Param("description") String description,
//	                          @Param("pdu") String pdu,
//	                          @Param("text") String text,
//	                          @Param("deliverystatus") String deliverystatus,
//	                          @Param("deliverydt") String deliverydt,
//	                          @Param("createdDate") LocalDate createdDate,
//	                          @Param("flag") int flag,
//	                          @Param("response") String response
//			  );
	  
	
}
