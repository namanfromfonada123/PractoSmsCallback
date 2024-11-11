package com.SmsCallback.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SmsCallback.Model.PractoMessageData;

@Repository
public interface PractoMesssageDataRepository extends JpaRepository<PractoMessageData, Long> {

}
