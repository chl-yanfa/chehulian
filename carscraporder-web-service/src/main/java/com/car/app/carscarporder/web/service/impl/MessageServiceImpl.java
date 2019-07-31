package com.car.app.carscarporder.web.service.impl;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.car.app.carscarporder.web.service.MessageService;
import com.car.app.carscraporder.bo.ClientMessageBO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.bean.PageResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	  
	@Value("${QUERY_MESSAGE_PAGE_URI}")
	private String QUERY_MESSAGE_PAGE_URI;
	  
	@Value("${QUERY_MESSAGE_URI}")
    private String QUERY_MESSAGE_URL;
	
	@Value("${QUERY_MESSAGE_UNREAD_TOTAL_URI}")
    private String QUERY_MESSAGE_UNREAD_TOTAL_URI;
	
	@Value("${QUERY_MESSAGE_CHANGE_ISREAD_URI}")
    private String QUERY_MESSAGE_CHANGE_ISREAD_URI;
	
	
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	
	

	@Override
	public Integer getMessageUnReadTotal(String clientId) throws Exception {
		String UnReadQueryUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI + QUERY_MESSAGE_UNREAD_TOTAL_URI, clientId);
		HttpResult httpResult = this.apiService.doGet(UnReadQueryUrl);

		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				// 查询消息成功
				return MAPPER.readValue(jsonNode.path("data").toString(), Integer.class);
			}
		}
		return 0;
	}
	
	@Override
	public PageResult<ClientMessageBO> getMessagePageList(Integer page,Integer rows,String clientId) throws Exception {
		
		PageResult<ClientMessageBO> result = new PageResult<ClientMessageBO>();
		 String queryMessagePageUrl = UNIFIED_EXTERNAL_URI+QUERY_MESSAGE_PAGE_URI;
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("page", page);
		 map.put("rows", rows);
		 map.put("clientId", clientId);
		 HttpResult httpResult = this.apiService.doGet(queryMessagePageUrl, map);
		 
		 if (httpResult.getCode() == 200) {
			  JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
				 if(jsonResult.containsKey("code")&&jsonResult.getInteger("code")==200){
					 JSONObject data = jsonResult.getJSONObject("data");
					 result.setTotal(data.getLong("total"));
					 List<ClientMessageBO> rowsData = new ArrayList<ClientMessageBO>();
					 result.setRows(rowsData);
			         JSONArray array = data.getJSONArray("rows");
			         for(int i=0;i<array.size();i++){
				         String str = JSONObject.toJSONString(array.get(i),SerializerFeature.WriteMapNullValue); 
				         ClientMessageBO bo = JSON.parseObject(str,ClientMessageBO.class);
				         rowsData.add(bo);
			          }
			
			         return result;
			 }else{
				 throw new NetException("请求异常");
			 }
		      
         }
		
		return null;
	}
	
	@Override
	public ClientMessageBO getMessageById(String messageId) throws Exception {
		String MessageQueryByIdUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI + QUERY_MESSAGE_URL, messageId);
		HttpResult httpResult = this.apiService.doGet(MessageQueryByIdUrl);
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				ClientMessageBO byDb = MAPPER.readValue(jsonNode.path("data").toString(), ClientMessageBO.class);
				if(null != byDb && byDb.getIsread()!=null && byDb.getIsread() == 0) {
					//将消息置为已读
					String MessageChangeUrl = UNIFIED_EXTERNAL_URI + QUERY_MESSAGE_CHANGE_ISREAD_URI;
					Map<String,String> map = new HashMap<String,String>();
					map.put("id", byDb.getId());
					this.apiService.doPut(MessageChangeUrl, map);
					byDb.setIsread(1);
				}
				// 查询消息成功
				return byDb;
			}
		}
		return null;
	}
	


}
