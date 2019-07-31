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
import com.car.app.carscarporder.web.service.ProblemService;
import com.car.app.carscraporder.bo.ClientProblemBO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.bean.PageResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProblemServiceImpl implements ProblemService {
	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	  
	@Value("${QUERY_PROBLEM_PAGE_URI}")
	private String QUERY_PROBLEM_PAGE_URI;
	  
	@Value("${QUERY_PROBLEM_URI}")
    private String QUERY_PROBLEM_URL;
	
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	
	
	@Override
	public PageResult<ClientProblemBO> getProblemPageList(Integer page,Integer rows) throws Exception {
		
		PageResult<ClientProblemBO> result = new PageResult<ClientProblemBO>();
		 String queryMessagePageUrl = UNIFIED_EXTERNAL_URI+QUERY_PROBLEM_PAGE_URI;
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("page", page);
		 map.put("rows", rows);
		 HttpResult httpResult = this.apiService.doGet(queryMessagePageUrl, map);
		 
		 if (httpResult.getCode() == 200) {
			  JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
				 if(jsonResult.containsKey("code")&&jsonResult.getInteger("code")==200){
					 JSONObject data = jsonResult.getJSONObject("data");
					 result.setTotal(data.getLong("total"));
					 List<ClientProblemBO> rowsData = new ArrayList<ClientProblemBO>();
					 result.setRows(rowsData);
			         JSONArray array = data.getJSONArray("rows");
			         for(int i=0;i<array.size();i++){
				         String str = JSONObject.toJSONString(array.get(i),SerializerFeature.WriteMapNullValue); 
				         ClientProblemBO bo = JSON.parseObject(str,ClientProblemBO.class);
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
	public ClientProblemBO getProblemById(String problemId) throws Exception {
		String MessageQueryByIdUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI + QUERY_PROBLEM_URL, problemId);
		HttpResult httpResult = this.apiService.doGet(MessageQueryByIdUrl);
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				ClientProblemBO byDb = MAPPER.readValue(jsonNode.path("data").toString(), ClientProblemBO.class);
				// 查询消息成功
				return byDb;
			}
		}
		return null;
	}
	


}
