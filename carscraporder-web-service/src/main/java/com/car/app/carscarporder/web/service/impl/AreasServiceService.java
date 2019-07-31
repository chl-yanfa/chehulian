package com.car.app.carscarporder.web.service.impl;

import java.text.MessageFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.car.app.carscarporder.web.service.AreasService;
import com.car.app.carscraporder.pojo.Areas;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class AreasServiceService implements AreasService {
	
	
	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;
	
	@Value("${GET_ALL_AREAS}")
	private String GET_ALL_AREAS;
	
	@Value("${GET_AREAS_BYID}")
	private String  GET_AREAS_BYID;
	
	@Autowired
	private ApiService apiService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public Areas queryById(Integer id) throws Exception {
		String queryAreasByid = MessageFormat.format(UNIFIED_EXTERNAL_URI+GET_AREAS_BYID, id);
		
		 HttpResult httpResult = this.apiService.doGet(queryAreasByid);
		 if (httpResult.getCode() == 200) {
           String jsonData = httpResult.getContent();
           JsonNode jsonNode = MAPPER.readTree(jsonData);
           if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
               // 订单创建成功
        	   Areas data =(Areas) MAPPER.readValue(jsonNode.path("data").toString(),Areas.class);
              return data;
            }
          }else{
			 throw new NetException("请求异常");
		 }
		 
		
		return null;
	}

	@Override
	public List<Areas> getAllAreas() throws Exception {
            String queryAllAreas = UNIFIED_EXTERNAL_URI+GET_ALL_AREAS;
		 
		 HttpResult httpResult = this.apiService.doGet(queryAllAreas);
		 
		 if (httpResult.getCode() == 200) {
			  JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
				 if(jsonResult.containsKey("code")&&jsonResult.getInteger("code")==200){
					 String jsonData = httpResult.getContent();
		             JsonNode jsonNode = MAPPER.readTree(jsonData);
		             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
		            	 List<Areas> data =(List<Areas>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<Areas>>() {});
		                 return data;
		            	 
		                 
		             }
				 }
			 }else{
				 throw new NetException("请求异常");
			 }
			 
			 
			 
		 
		return null;
	}

}
