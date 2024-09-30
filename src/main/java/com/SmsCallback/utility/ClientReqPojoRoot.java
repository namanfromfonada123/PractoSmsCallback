package com.SmsCallback.utility;

import java.util.List;

import lombok.Data;

@Data
public class ClientReqPojoRoot {

	String event;
	List<ClientReqPojodata> data;
}


//curl --location 'https://in1.cb.wzrkt.com/sms/generic?account=447-95K-996Z' \
//--header 'Content-Type: application/json' \
//--data '[{
//        "event": "delivered",
//        "data": [{
//            "ts": 1724224872,
//            "meta": "9999999890.1200000000.1654002362.20220531.0.wzrk_default.-1",
//            "description": "Delivered"
//        }]
//    }]'
