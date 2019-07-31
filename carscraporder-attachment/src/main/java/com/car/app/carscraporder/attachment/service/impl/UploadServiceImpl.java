package com.car.app.carscraporder.attachment.service.impl;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.attachment.service.UploadService;
import com.car.app.carscraporder.bo.AttachmentBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.vo.AttachmentVO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UploadServiceImpl implements UploadService {
	
	@Autowired
	private ApiService apiService;
	
	

	@Value("${UPLOAD_ATTACHMENT_URL}")
	private String UPLOAD_ATTACHMENT_URL;
	
	@Value("${UPLOAD_CARATTACHMENT_URL}")
	private String UPLOAD_CARATTACHMENT_URL;
	
	
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	
	
	
	public Integer  saveAttachmentBuniessData(AttachmentVO vo)throws Exception{
		
		String uplodadUrl = UPLOAD_ATTACHMENT_URL;
		String json = MAPPER.writeValueAsString(vo);
		HttpResult httpResult = this.apiService.doPutJson(uplodadUrl, json);
		 if (httpResult.getCode() == 200) {
	            String jsonData = httpResult.getContent();
	            JsonNode jsonNode = MAPPER.readTree(jsonData);
	            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	                // 订单创建成功
	            	AttachmentBO bo =	MAPPER.readValue(jsonNode.path("data").toString(),AttachmentBO.class);
	            	return bo.getId();
	            }
	        }
		return null;
	}
	
	public Integer  saveCarAttachmentBuniessData(String buniessid,AttachmentVO vo)throws Exception{
		String uploadCarAttachmentUrl = MessageFormat.format(UPLOAD_CARATTACHMENT_URL, buniessid);

		String json = MAPPER.writeValueAsString(vo);
		HttpResult httpResult = this.apiService.doPutJson(uploadCarAttachmentUrl, json);
		 if (httpResult.getCode() == 200) {
	            String jsonData = httpResult.getContent();
	            JsonNode jsonNode = MAPPER.readTree(jsonData);
	            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	                // 订单创建成功
	            	AttachmentBO bo =	MAPPER.readValue(jsonNode.path("data").toString(),AttachmentBO.class);

	            	  return bo.getBusinessId();
	            }
	        }
		return null;
	}
	
	
	
	
	

}
