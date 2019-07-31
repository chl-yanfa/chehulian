package com.car.app.carscarporder.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.car.app.carscarporder.web.service.CarAssessmentService;
import com.car.app.carscraporder.pojo.Areas;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CarAssessmentServiceImpl implements CarAssessmentService {
	
	
   @Value("${UNIFIED_EXTERNAL_URI}")
   private String UNIFIED_EXTERNAL_URI;
	
   @Value("${GET_CAR_PRICE}")
	private String GET_CAR_PRICE;
   
    @Autowired
	private ApiService apiService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

   
   
	@Override
	public String getPrice(String vin,String areaName)throws Exception{
           String getCarPrice = UNIFIED_EXTERNAL_URI+GET_CAR_PRICE;
		 
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("vin", vin);
        map.put("areaName", areaName);
           
		 HttpResult httpResult = this.apiService.doGet(getCarPrice,map);
		 
		 if (httpResult.getCode() == 200) {
			  JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
				 if(jsonResult.containsKey("code")&&jsonResult.getInteger("code")==200){
					 String jsonData = httpResult.getContent();
		             JsonNode jsonNode = MAPPER.readTree(jsonData);
		             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
		            	 String data = jsonNode.get("data").asText();
		                 return data;
		            	 
		                 
		             }
				 }
			 }else{
				 throw new NetException("请求异常");
			 }
			 
			 
			 
		 
		return null;
	}

}
