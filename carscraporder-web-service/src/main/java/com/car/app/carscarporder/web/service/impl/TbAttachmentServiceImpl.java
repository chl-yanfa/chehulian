package com.car.app.carscarporder.web.service.impl;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.TbAttachmentService;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TbAttachmentServiceImpl implements TbAttachmentService {
	
	
	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;
	
	@Value("${DELETE_ATTACHMENT_BYID}")
	private String DELETE_ATTACHMENT_BYID;
	
	@Autowired
	private ApiService  apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public Boolean delete(Integer id)throws Exception{
		
		String deleteAttachmentById = MessageFormat.format(UNIFIED_EXTERNAL_URI+DELETE_ATTACHMENT_BYID, id);
		 HttpResult httpResult = this.apiService.doDelete(deleteAttachmentById);
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
