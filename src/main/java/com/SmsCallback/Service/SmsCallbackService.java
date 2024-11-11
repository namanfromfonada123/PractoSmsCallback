package com.SmsCallback.Service;

import com.SmsCallback.Config.QueueProducer;

import com.SmsCallback.utility.CallbackPojo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class SmsCallbackService {

	@Autowired
	QueueProducer queueProducer;

	public void saveCallbackpractoData(Map<String, String> callBackData) {
		try {

			CallbackPojo cb = new CallbackPojo();

			String DeliveryDate = changeDatetoUTCandISO(callBackData.get("deliverydt"));

			cb.setCorelationid(callBackData.get("corelationid"));
			cb.setDeliverydt(DeliveryDate);
			cb.setDeliverystatus(callBackData.get("deliverystatus"));
			cb.setDescription(callBackData.get("description"));
			cb.setFromk(callBackData.get("from"));
			cb.setPdu(callBackData.get("pdu"));
			cb.setText(callBackData.get("text"));
			cb.setTok(callBackData.get("to"));
			cb.setTxid(callBackData.get("txid"));

			queueProducer.sendMessage(cb);

		} catch (Exception e) {
			System.out.println("Exception While Sending TO RabbitMq : " + e.getMessage());
		}

	}

	public String changeDatetoUTCandISO(String deliverydt) {

		// Define the formatter for the input date format
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// Parse the input string to a LocalDateTime object
		LocalDateTime localDateTime = LocalDateTime.parse(deliverydt, inputFormatter);

		// Assume the input date is in UTC (interpreted without changes)
		ZonedDateTime utcDateTime = localDateTime.atZone(ZoneId.of("UTC"));

		// Now convert this UTC date to UTC-05:30 time zone (it will subtract 5 hours
		// and 30 minutes)
		ZonedDateTime zonedDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("UTC-05:30"));

		// Define the ISO date formatter
		DateTimeFormatter isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		// Format the ZonedDateTime to the ISO string
		String isoDate = zonedDateTime.format(isoFormatter);

		return isoDate;

	}

}
