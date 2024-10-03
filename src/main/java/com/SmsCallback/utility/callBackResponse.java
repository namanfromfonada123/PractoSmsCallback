package com.SmsCallback.utility;

import java.util.List;

import lombok.Data;

@Data
public class callBackResponse {

	private List<callBackRes> callBackResList;
	private long totalCallbacks;
	
}
