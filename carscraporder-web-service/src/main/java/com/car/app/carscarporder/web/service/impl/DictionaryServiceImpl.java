package com.car.app.carscarporder.web.service.impl;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.DictionaryService;
import com.car.app.carscraporder.bo.DictionaryBO;
import com.car.app.carscraporder.bo.DictionarySystemBO;
import com.car.app.carscraporder.pojo.TbAreas;
import com.car.app.carscraporder.pojo.TbCityes;
import com.car.app.carscraporder.pojo.TbProvinces;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DictionaryServiceImpl implements DictionaryService {
	
	
	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;
	
	@Value("${GET_ALL_PROVINCE}")
	private String GET_ALL_PROVINCE;
	
	@Value("${GET_ALL_CITY_BY_PROVINCEID}")
	private String GET_ALL_CITY_BY_PROVINCEID;
	
	@Value("${GET_ALL_AREAS_BY_CITYID}")
	private String  GET_ALL_AREAS_BY_CITYID;
	
	
	@Value("${GET_PROVINCE_ID}")
	private String GET_PROVINCE_ID;
	
	@Value("${GET_CITY_BY_ID}")
	private String GET_CITY_BY_ID;
	
	@Value("${GET_AREAS_BY_ID}")
	private String GET_AREAS_BY_ID;
	
	@Value("${GET_ALL_CAR_BRAND}")
	private String GET_ALL_CAR_BRAND;
	
	@Value("${GET_CAR_SYSTEM_BY_ID}")
	private String GET_CAR_SYSTEM_BY_ID;
	
	@Value("${GET_CAR_MODEL_BY_ID}")
	private String GET_CAR_MODEL_BY_ID;
	
	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	
	
	

	@Override
	public List<DictionaryBO> getAllProvince()throws Exception {
		String queryAllProvince = UNIFIED_EXTERNAL_URI+GET_ALL_PROVINCE;
		
		 HttpResult httpResult = this.apiService.doGet(queryAllProvince);
		 if (httpResult.getCode() == 200) {
          String jsonData = httpResult.getContent();
          JsonNode jsonNode = MAPPER.readTree(jsonData);
          if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
              // 订单创建成功
        	 List<DictionaryBO> data =(List<DictionaryBO>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<DictionaryBO>>() {});
             return data;
           }
         }else{
			 throw new NetException("请求异常");
		 }
		 
		
		return null;
	}

	@Override
	public List<DictionaryBO> getAllCityByProvinceId(String provinceId)
			throws Exception {
		
		   String getAllCityByProvinceIdUrl  = MessageFormat.format(UNIFIED_EXTERNAL_URI+GET_ALL_CITY_BY_PROVINCEID, provinceId);
		   
		   HttpResult httpResult = this.apiService.doGet(getAllCityByProvinceIdUrl);
			 if (httpResult.getCode() == 200) {
	          String jsonData = httpResult.getContent();
	          JsonNode jsonNode = MAPPER.readTree(jsonData);
	          if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	              // 订单创建成功
	        	 List<DictionaryBO> data =(List<DictionaryBO>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<DictionaryBO>>() {});
	             return data;
	           }
	         }else{
				 throw new NetException("请求异常");
			 }
			 
			
			return null;
		   
	}

	@Override
	public List<DictionaryBO> getAllAreaByCityId(String cityId) throws Exception {
		String getAllCityByProvinceIdUrl  = MessageFormat.format(UNIFIED_EXTERNAL_URI+GET_ALL_AREAS_BY_CITYID, cityId);
		   
		   HttpResult httpResult = this.apiService.doGet(getAllCityByProvinceIdUrl);
			 if (httpResult.getCode() == 200) {
	          String jsonData = httpResult.getContent();
	          JsonNode jsonNode = MAPPER.readTree(jsonData);
	          if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	              // 订单创建成功
	        	 List<DictionaryBO> data =(List<DictionaryBO>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<DictionaryBO>>() {});
	             return data;
	           }
	         }else{
				 throw new NetException("请求异常");
			 }
			 
			
			return null;
	}

	@Override
	public TbProvinces getProvince(String provinceid) throws Exception {
		  String getAllCityByProvinceIdUrl  = MessageFormat.format(UNIFIED_EXTERNAL_URI+GET_PROVINCE_ID, provinceid);
		   
		   HttpResult httpResult = this.apiService.doGet(getAllCityByProvinceIdUrl);
			 if (httpResult.getCode() == 200) {
	          String jsonData = httpResult.getContent();
	          JsonNode jsonNode = MAPPER.readTree(jsonData);
	          if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	              // 订单创建成功
	        	  TbProvinces data = MAPPER.readValue(jsonNode.path("data").toString(),TbProvinces.class);
	             return data;
	           }
	         }else{
				 throw new NetException("请求异常");
			 }
			 
			
			return null;
	}

	@Override
	public TbCityes getCity(String cityid) throws Exception {
		String getAllCityByProvinceIdUrl  = MessageFormat.format(UNIFIED_EXTERNAL_URI+GET_CITY_BY_ID, cityid);
		   
		   HttpResult httpResult = this.apiService.doGet(getAllCityByProvinceIdUrl);
			 if (httpResult.getCode() == 200) {
	          String jsonData = httpResult.getContent();
	          JsonNode jsonNode = MAPPER.readTree(jsonData);
	          if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	              // 订单创建成功
	        	  TbCityes data = MAPPER.readValue(jsonNode.path("data").toString(),TbCityes.class);
	             return data;
	           }
	         }else{
				 throw new NetException("请求异常");
			 }
			 
			
			return null;
	}

	@Override
	public TbAreas getTbAreas(String areaid) throws Exception {
		String getAllCityByProvinceIdUrl  = MessageFormat.format(UNIFIED_EXTERNAL_URI+GET_AREAS_BY_ID, areaid);
		   
		   HttpResult httpResult = this.apiService.doGet(getAllCityByProvinceIdUrl);
			 if (httpResult.getCode() == 200) {
	          String jsonData = httpResult.getContent();
	          JsonNode jsonNode = MAPPER.readTree(jsonData);
	          if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	              // 订单创建成功
	        	  TbAreas data = MAPPER.readValue(jsonNode.path("data").toString(),TbAreas.class);
	             return data;
	           }
	         }else{
				 throw new NetException("请求异常");
			 }
			 
			
			return null;
	}

	@Override
	public List<DictionarySystemBO> getAllCarBrand() throws Exception {
		String queryAllBrand = UNIFIED_EXTERNAL_URI + GET_ALL_CAR_BRAND;
		HttpResult httpResult = this.apiService.doGet(queryAllBrand);
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				List<DictionarySystemBO> data = (List<DictionarySystemBO>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<DictionarySystemBO>>() {});
				return data;
			}
		} else {
			throw new NetException("请求异常");
		}
		return null;
	}

	@Override
	public List<DictionarySystemBO> getCarSystem(String brandId) throws Exception {
		String getCarSystemByBrandIdUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI + GET_CAR_SYSTEM_BY_ID, brandId);
		HttpResult httpResult = this.apiService.doGet(getCarSystemByBrandIdUrl);
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				List<DictionarySystemBO> data = (List<DictionarySystemBO>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<DictionarySystemBO>>() {});
				return data;
			}
		} else {
			throw new NetException("请求异常");
		}
		return null;
	}

	@Override
	public List<DictionaryBO> getCarModel(String systemId) throws Exception {
		String getCarModelBySystemIdUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI + GET_CAR_MODEL_BY_ID, systemId);
		HttpResult httpResult = this.apiService.doGet(getCarModelBySystemIdUrl);
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				List<DictionaryBO> data = (List<DictionaryBO>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<DictionaryBO>>() {});
				return data;
			}
		} else {
			throw new NetException("请求异常");
		}
		return null;
	}

}
