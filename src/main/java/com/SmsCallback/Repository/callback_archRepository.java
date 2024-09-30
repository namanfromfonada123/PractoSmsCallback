package com.SmsCallback.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SmsCallback.Model.callback_arch;

@Repository
public interface callback_archRepository extends JpaRepository<callback_arch, Long> {

	
	
}