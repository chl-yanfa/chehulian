package com.car.app.carscarporder.web.service.impl;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.AdvertisementService;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.pojo.Advertisement;
import com.car.app.carscraporder.pojo.TbCarPartsCategory;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
	
	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;
	
	@Value("${GET_ALL_ADVERTISEMENT}")
	private String GET_ALL_ADVERTISEMENT;
	
	@Autowired
	private ApiService  apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	

	public List<Advertisement> getAll() throws Exception{
		
		String getAllAdvertisement = UNIFIED_EXTERNAL_URI+GET_ALL_ADVERTISEMENT;
		
		 HttpResult httpResult = this.apiService.doGet(getAllAdvertisement);
		 if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
            	 List<Advertisement> data = (List<Advertisement>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<Advertisement>>() {});
                 
            	 
                 return data;
            }
        }
		
		return null;
		
	}


}
