package com.car.app.carscarporder.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.CarScrapInquiryPriceService;
import com.car.app.carscraporder.vo.CarScrapInquiryPriceVO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CarScrapInquiryPriceServiceImpl implements CarScrapInquiryPriceService {
	@Autowired
	private ApiService apiService;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;

	@Value("${CREATE_INQUIRY_PRICE_URL}")
	private String CREATE_INQUIRY_PRICE_URL;

	@Override
	public Boolean addInquiryPrice(CarScrapInquiryPriceVO inquiryPriceVO) throws Exception {
		String createUrl = UNIFIED_EXTERNAL_URI + CREATE_INQUIRY_PRICE_URL;
		HttpResult httpResult = this.apiService.doPostJson(createUrl, MAPPER.writeValueAsString(inquiryPriceVO));
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				return jsonNode.get("data").asBoolean();
			}
		} else {
			throw new NetException("http请求响应编码" + httpResult.getCode());
		}
		return null;
	}

}
