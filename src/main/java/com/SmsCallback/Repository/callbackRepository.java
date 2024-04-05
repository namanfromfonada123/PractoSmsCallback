package com.SmsCallback.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SmsCallback.model.callback;


import java.util.List;

import javax.security.auth.callback.Callback;

import org.springframework.data.jpa.repository.Query;


@Repository
public interface callbackRepository extends JpaRepository<callback, Long> {

	@Query(value = "Select * from callbackril where flag=:flag  limit  :limit " , nativeQuery = true)
	public List<callback> findFiftyByFlag(int flag, int limit);
	
	public List<callback> findByDeliverydt(String deliverydt);
}

