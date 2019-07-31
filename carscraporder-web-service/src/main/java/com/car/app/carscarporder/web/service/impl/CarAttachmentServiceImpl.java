package com.car.app.carscarporder.web.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.CarAttachmentService;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CarAttachmentServiceImpl implements CarAttachmentService {
	
	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;
	
	@Value("${DELETE_CAR_ATTACHMENT_BYID}")
	private String DELETE_CAR_ATTACHMENT_BYID;
	
	@Autowired
	private ApiService  apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	
	public Boolean delete(Integer id) throws Exception{
		
		String deleteCarAttachmentById = MessageFormat.format(UNIFIED_EXTERNAL_URI+DELETE_CAR_ATTACHMENT_BYID, id.toString());
		
		 HttpResult httpResult = this.apiService.doDelete(deleteCarAttachmentById);
		 if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                // 订单创建成功
            	 boolean data = jsonNode.get("data").asBoolean();
                 return data;
            }
        }
		
		return false;
		
	}

}
