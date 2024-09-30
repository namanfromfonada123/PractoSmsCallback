package com.SmsCallback.InternalRestRequest;

import com.SmsCallback.InternalRestRequest.Properties;
import com.SmsCallback.Model.callback;
import com.SmsCallback.Model.callbackpracto;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RestRequestClass {
  Logger logger = LoggerFactory.getLogger(com.SmsCallback.InternalRestRequest.RestRequestClass.class);
  
  @Autowired
  Properties properties;
  
  RestTemplate restTemplate = new RestTemplate();
  
  @Value("${urlString}")
  private String ClientUrl;
  
  public ResponseEntity<String> getRequest(callback cb) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.ClientUrl);
    setpathVariables(builder);
    Map<String, String> queryParam = this.properties.getQuery();
    builder.queryParam(queryParam.get("corelationid"), new Object[] { cb.getCorelationid() });
    builder.queryParam(queryParam.get("txid"), new Object[] { cb.getTxid() });
    builder.queryParam(queryParam.get("to"), new Object[] { cb.getTok() });
    builder.queryParam(queryParam.get("from"), new Object[] { cb.getFromk() });
    builder.queryParam(queryParam.get("deliverystatus"), new Object[] { cb.getDeliverystatus() });
    builder.queryParam(queryParam.get("deliverydt"), new Object[] { cb.getDeliverydt() });
    String finalUrlString = builder.build().toUriString();
    System.out.println(finalUrlString);
    try {
      ResponseEntity<String> responseEntity = this.restTemplate.exchange(finalUrlString, HttpMethod.GET, getHttpEntity(setHeaders()), String.class, new Object[0]);
      finalUrlString = "";
      return responseEntity;
    } catch (Exception e) {
      this.logger.warn(e.getMessage());
      return null;
    } 
  }
  
  public ResponseEntity<String> getRequest(callbackpracto cb) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.ClientUrl);
    setpathVariables(builder);
    Map<String, String> queryParam = this.properties.getQuery();
    builder.queryParam(queryParam.get("corelationid"), new Object[] { cb.getCorelationid() });
    builder.queryParam(queryParam.get("txid"), new Object[] { cb.getTxid() });
    builder.queryParam(queryParam.get("to"), new Object[] { cb.getTok() });
    builder.queryParam(queryParam.get("from"), new Object[] { cb.getFromk() });
    builder.queryParam(queryParam.get("pdu"), new Object[] { cb.getPdu() });
    builder.queryParam(queryParam.get("deliverystatus"), new Object[] { cb.getDeliverystatus() });
    builder.queryParam(queryParam.get("deliverydt"), new Object[] { cb.getDeliverydt() });
    String finalUrlString = builder.build().toUriString();
    System.out.println(finalUrlString);
    try {
      ResponseEntity<String> responseEntity = this.restTemplate.exchange(finalUrlString, HttpMethod.GET, getHttpEntity(setHeaders()), String.class, new Object[0]);
      finalUrlString = "";
      return responseEntity;
    } catch (Exception e) {
      this.logger.warn(e.getMessage());
      return null;
    } 
  }
  
  public HttpHeaders setHeaders() {
    HttpHeaders headers = new HttpHeaders();
    if (this.properties.getHeader() != null)
      for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)this.properties.getHeader().entrySet()) {
        if (!((String)entry.getValue()).isEmpty())
          headers.add(entry.getKey(), entry.getValue()); 
      }  
    return headers;
  }
  
  public void setpathVariables(UriComponentsBuilder builder) {
    if (this.properties.getPathVariable() != null)
      for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)this.properties.getPathVariable().entrySet()) {
        if (!((String)entry.getValue()).isEmpty())
          builder.path("/" + (String)entry.getValue()); 
      }  
  }
  
  public HttpEntity<Object> getHttpEntity(HttpHeaders headers) {
    return new HttpEntity((MultiValueMap)headers);
  }
}
