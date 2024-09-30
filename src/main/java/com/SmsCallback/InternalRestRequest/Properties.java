package com.SmsCallback.InternalRestRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("")
public class Properties {
  private Map<String, String> header = new HashMap<>();
  
  private TreeMap<String, String> pathVariable = new TreeMap<>();
  
  private Map<String, String> query = new HashMap<>();
  
  public Map<String, String> getHeader() {
    return this.header;
  }
  
  public void setHeader(Map<String, String> header) {
    this.header = header;
  }
  
  public TreeMap<String, String> getPathVariable() {
    return this.pathVariable;
  }
  
  public void setPathVariable(TreeMap<String, String> pathVariable) {
    this.pathVariable = pathVariable;
  }
  
  public Map<String, String> getQuery() {
    return this.query;
  }
  
  public void setQuery(Map<String, String> query) {
    this.query = query;
  }
}
