package com.car.app.carscarporder.web.service.impl;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.CarScrapOrderDealService;
import com.car.app.carscraporder.bo.CarScrapOrderDealBO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CarScrapOrderDealServiceImpl implements CarScrapOrderDealService {
	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	  
	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;
	
	@Value("${QUERY_ALL_ORDER_DEAL_URL}")
	private String QUERY_ALL_ORDER_DEAL_URL;
	  
	@Value("${QUERY_ORDER_DEAL_URL}")
	private String QUERY_ORDER_DEAL_URL;
	
	@Override
	public List<CarScrapOrderDealBO> queryList() throws Exception {
		String queryListUrl = UNIFIED_EXTERNAL_URI+QUERY_ALL_ORDER_DEAL_URL;
		HttpResult httpResult = this.apiService.doGet(queryListUrl);
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				List<CarScrapOrderDealBO> data = (List<CarScrapOrderDealBO>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<CarScrapOrderDealBO>>() {});
				return data;
			}
		}else{
			throw new NetException("请求异常");
		}
		return null;
	}


	@Override
	public CarScrapOrderDealBO queryBOById(String id) throws Exception {
		String queryByIdUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI + QUERY_ORDER_DEAL_URL, id);
		HttpResult httpResult = this.apiService.doGet(queryByIdUrl);
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				return MAPPER.readValue(jsonNode.path("data").toString(), CarScrapOrderDealBO.class);
			}
		}

		return null;
	}

	
	

}
