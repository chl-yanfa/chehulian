package com.car.app.carscarporder.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.car.app.carscarporder.web.service.TbCarPartsCategoryService;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.bo.CarScrapOrderBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
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
public class TbCarPartsCategoryServiceImpl implements TbCarPartsCategoryService {
	
	@Value("${QUERY_CARPARTCATEGORY_URL}")
	private String QUERY_CARPARTCATEGORY_URL;
	
	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;
	
	@Autowired
	private ApiService apiService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	

	@Override
	public List<TbCarPartsCategory> getAll() throws Exception{
		
		 String queryOrderPageUrl = UNIFIED_EXTERNAL_URI+QUERY_CARPARTCATEGORY_URL;
		 
		 HttpResult httpResult = this.apiService.doGet(queryOrderPageUrl);
		 
		 if (httpResult.getCode() == 200) {
			  JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
				 if(jsonResult.containsKey("code")&&jsonResult.getInteger("code")==200){
					 String jsonData = httpResult.getContent();
		             JsonNode jsonNode = MAPPER.readTree(jsonData);
		             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
		            	 List<TbCarPartsCategory> data =(List<TbCarPartsCategory>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<TbCarPartsCategory>>() {});
		                 return data;
		            	 
		                 
		             }
				 }
			 }else{
				
				 throw new NetException("请求异常");
			 }
			 
			 
			 
		 
		return null;
	}

}
