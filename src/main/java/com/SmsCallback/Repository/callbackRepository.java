package com.SmsCallback.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SmsCallback.Model.callback;

import java.util.List;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface callbackRepository extends JpaRepository<callback, Long> {
	@Query(value = "Select * from callback_dhani where flag=:flag order by RAND()  limit :limit " , nativeQuery = true)
	public List<callback> findFiftyByFlag(int flag, int limit);
	
	@Query(value = "Select * from callback_dhani where created_on between ?1 and ?2", nativeQuery = true)
	public Page<callback> findByCreatedOnBetween(String StartCreatedOn, String EndCreatedOn,  Pageable pageable);
	
	
}

												