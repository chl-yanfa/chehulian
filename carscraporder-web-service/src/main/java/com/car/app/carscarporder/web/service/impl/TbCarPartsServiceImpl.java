package com.car.app.carscarporder.web.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.car.app.carscarporder.web.service.TbCarPartsService;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.pojo.TbCarParts;
import com.car.app.carscraporder.pojo.TbCarPartsCategory;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.bean.PageResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TbCarPartsServiceImpl implements TbCarPartsService {
	
	
	@Value("${QUERY_CARPART_URL}")
	private String QUERY_CARPART_URL;
	
	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;
	
	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Override
	public List<TbCarParts> getAllByCategory(Integer cacegoryId) throws Exception {
		 String queryOrderPageUrl = UNIFIED_EXTERNAL_URI+QUERY_CARPART_URL;
		 
		 Map<String, Object> map = new HashMap<String, Object>(); 
		 map.put("categoryId", cacegoryId+"");
		
		 HttpResult httpResult = this.apiService.doGet(queryOrderPageUrl, map);
		 
		 if (httpResult.getCode() == 200) {
			  JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
				 if(jsonResult.containsKey("code")&&jsonResult.getInteger("code")==200){
					 String jsonData = httpResult.getContent();
		             JsonNode jsonNode = MAPPER.readTree(jsonData);
		             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
		            	 List<TbCarParts> data =(List<TbCarParts>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<TbCarParts>>() {});
		                 
		            	
		                 return data;
		            	 
		                 
		             }
			         
			 }else{
				
				 throw new NetException("请求异常");
			 }
			 
			 
			 
		      
        }
		
		return null;
		
		
	}

}
