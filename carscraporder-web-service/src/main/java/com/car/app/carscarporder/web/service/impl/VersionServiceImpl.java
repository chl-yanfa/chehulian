package com.car.app.carscarporder.web.service.impl;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.VersionService;
import com.car.app.carscraporder.bo.VersionBO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class VersionServiceImpl implements VersionService {
	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	  
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	
	@Value("${QUERY_VERSION_INFO_URI}")
	private String QUERY_VERSION_INFO_URI;
	  
	
	@Override
	public VersionBO getVersionInfo(Integer oc) throws Exception {
		String getVersionInfoUrl = UNIFIED_EXTERNAL_URI+QUERY_VERSION_INFO_URI;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("oc", oc);
		HttpResult httpResult = this.apiService.doGet(getVersionInfoUrl, params);
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				VersionBO byDb = MAPPER.readValue(jsonNode.path("data").toString(), VersionBO.class);
				// 查询消息成功
				return byDb;
			}
		}
		return null;
	}
	


}
