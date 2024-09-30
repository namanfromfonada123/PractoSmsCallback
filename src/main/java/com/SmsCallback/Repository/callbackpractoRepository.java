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
public interface callbackpractoRepository extends JpaRepository<callbackpracto, Long> {
	  callbackpracto findFirstByFlag(int paramInt);
	  
	  callbackpracto findAllBytxid(String paramString);
	  
	  @Query(value = "Select * from callbackpracto where flag=:flag  limit  :limit  ", nativeQuery = true)
	  List<callbackpracto> findFiftyByFlag(int flag, int limit);
	  
	  @Query(value = "SELECT * FROM callbackpracto WHERE flag = :flag AND deliverydt >= :date limit :limit", nativeQuery = true)
	  List<callbackpracto> findFiftyByCurrentDateFlag(@Param("flag") int flag, @Param("limit") int limit, @Param("date") String date);

	  
	  @Query(value = "SELECT * FROM callbackpracto WHERE flag = :flag AND deliverydt <= :date limit :limit", nativeQuery = true)
	  List<callbackpracto> findFiftyByLessCurrentDateFlag(@Param("flag") int flag, @Param("limit") int limit, @Param("date") String date);

	  
	  @Modifying
	  @Transactional
	  @Query(value = "update callbackpracto set flag=1 where id=:id limit 1", nativeQuery = true)
	 Integer updateCallBackFlagForPracto(@Param("id")long id);
	  
	  @Modifying
	  @Transactional
	  @Query(value = "delete from callbackpracto where id=:id", nativeQuery = true)
	 Integer deleteCallBackFlagForPracto(@Param("id")long id);
	  
	  
	  
}


							