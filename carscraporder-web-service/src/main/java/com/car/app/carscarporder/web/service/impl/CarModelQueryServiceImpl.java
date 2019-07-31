package com.car.app.carscarporder.web.service.impl;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.CarModelQueryService;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CarModelQueryServiceImpl implements CarModelQueryService {
	
	@Value("${QUERY_CARMODE_URL}")
	private String QUERY_CARMODE_URL;
	
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	
	@Autowired
	 private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public String queryCarModelData(String vid) throws Exception {
		
		String queryCarModelUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI+QUERY_CARMODE_URL, vid);
		
		 HttpResult httpResult = this.apiService.doGet(queryCarModelUrl);
		 if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                // 订单创建成功
            	 String data = jsonNode.get("data").asText();
                 return data;
            }
        }
		 
		
		return null;
	}

}
