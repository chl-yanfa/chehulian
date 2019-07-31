package com.car.app.carscraporder.app.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.app.service.TbAppBannerService;
import com.car.app.carscraporder.bo.TbAppBannerSimpleBO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TbAppBannerServiceImpl implements TbAppBannerService {
	
	@Value("${QUERY_ALL_BANNER}")
	private String QUERY_ALL_BANNER;
	
	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;
	
	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public List<TbAppBannerSimpleBO> getAll(String type)throws Exception{
		String queryAllBannerUrl = UNIFIED_EXTERNAL_URI+QUERY_ALL_BANNER;
		
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("type", type);
		  HttpResult httpResult = this.apiService.doGet(queryAllBannerUrl,params);
		  
		  if (httpResult.getCode() == 200) {
	             String jsonData = httpResult.getContent();
	             JsonNode jsonNode = MAPPER.readTree(jsonData);
	             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	                 // 订单创建成功
	            	 MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//忽略对象中没有的属性
	            	 List<TbAppBannerSimpleBO> data =(List<TbAppBannerSimpleBO>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<TbAppBannerSimpleBO>>() {});
	                 
	            	 
	                  return data;
	             }
	         }
		return null;
		
	}

}
