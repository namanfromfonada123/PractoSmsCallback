package com.SmsCallback.Repository;


import com.SmsCallback.Model.callbackpracto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface callbackpractoRepository extends JpaRepository<callbackpracto, Long>,CallbackPractoCustomRepository {
	  callbackpracto findFirstByFlag(int paramInt);
	  
	  callbackpracto findAllBytxid(String paramString);
	  
	  @Query(value = "Select * from callbackpracto PARTITION(:partition) where flag=:flag  limit  :limit  ", nativeQuery = true)
	  List<callbackpracto> findFiftyByFlag(int flag, int limit, String partition);
	  	  
}


							