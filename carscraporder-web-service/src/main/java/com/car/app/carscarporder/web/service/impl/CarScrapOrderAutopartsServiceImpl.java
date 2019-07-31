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
import com.car.app.carscarporder.web.service.CarScrapOrderAutopartsService;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsDetailBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsPageBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.pojo.Advertisement;
import com.car.app.carscraporder.pojo.CarScrapOrderAutoparts;
import com.car.app.carscraporder.vo.CarScrapOrderAutopartsWhereParamter;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.bean.PageResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.car.app.common.util.ObjectUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CarScrapOrderAutopartsServiceImpl implements
		CarScrapOrderAutopartsService {
	
	
	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	
	@Value("${QUERY_AUTOPARTS_PAGE_URI}")
	private String QUERY_AUTOPARTS_PAGE_URI;
	
	@Value("${QUERY_AUTOPARTS_BYID_URI}")
	private String QUERY_AUTOPARTS_BYID_URI;
	
	@Value("${QUERY_AUTOPARTS_INFO_BYID_URI}")
	private String QUERY_AUTOPARTS_INFO_BYID_URI;
	
	@Value("${UPDATE_AUTOPARTS_BYID}")
	private String UPDATE_AUTOPARTS_BYID;
	
	@Value("${DELETE_AUTOPARTS_BYID}")
	private String DELETE_AUTOPARTS_BYID;
	
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	
	
	
	@Value("${QUERY_AUTOPARTS_BYORDERID_URI}")
    private String QUERY_AUTOPARTS_BYORDERID_URI;
	
	@Value("${QUERY_AUTOPARTS_HISTORY_AUDIT_URL}")
    private String QUERY_AUTOPARTS_HISTORY_AUDIT_URL;
	
	

	@Override
	public CarScrapOrderAutopartsBO queryBOById(String id) throws Exception {
		String queryAutoPartsUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI+QUERY_AUTOPARTS_BYID_URI, id);
		 HttpResult httpResult = this.apiService.doGet(queryAutoPartsUrl);
		 if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                // 订单创建成功
           	 
                 return MAPPER.readValue(jsonNode.path("data").toString(),CarScrapOrderAutopartsBO.class);
            }
        }
		 
		 
		return null;
	}

	@Override
	public CarScrapOrderAutopartsDetailBO getOrderAutopartsDetailById(String id)
			throws Exception {
		String queryAutoPartsInfoUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI+QUERY_AUTOPARTS_INFO_BYID_URI, id);
		 HttpResult httpResult = this.apiService.doGet(queryAutoPartsInfoUrl);
		 if (httpResult.getCode() == 200) {
           String jsonData = httpResult.getContent();
           JsonNode jsonNode = MAPPER.readTree(jsonData);
           if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
               // 订单创建成功
          	 
                return MAPPER.readValue(jsonNode.path("data").toString(),CarScrapOrderAutopartsDetailBO.class);
           }
       }
		return null;
	}

	@Override
	public List<CarScrapOrderAutopartsBO> queryListByOrderId(String orderid)
			throws Exception {
		
		String queryAutoPartsInfoUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI+QUERY_AUTOPARTS_BYORDERID_URI, orderid);
		 HttpResult httpResult = this.apiService.doGet(queryAutoPartsInfoUrl);
		 if (httpResult.getCode() == 200) {
          String jsonData = httpResult.getContent();
          JsonNode jsonNode = MAPPER.readTree(jsonData);
          if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
              // 订单创建成功
        	  List<CarScrapOrderAutopartsBO> data = (List<CarScrapOrderAutopartsBO>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<CarScrapOrderAutopartsBO>>() {});
               return data;
          }
      }
		
		
		return null;
	}

	@Override
	public PageResult<CarScrapOrderPageBO> queryBOPageListByWhere(
			Integer page, Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception {
		
		
		PageResult<CarScrapOrderPageBO> result = new PageResult<CarScrapOrderPageBO>();
		 String queryAutoPartsPageUrl = UNIFIED_EXTERNAL_URI+QUERY_AUTOPARTS_PAGE_URI;
		 JSONObject json = (JSONObject) JSON.toJSON(paramter);
		 Map<String,Object> map = json;
		 map.put("page", page);
		 map.put("rows", rows);
		 HttpResult httpResult = this.apiService.doGet(queryAutoPartsPageUrl, map);
		 
		 if (httpResult.getCode() == 200) {
			  JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
				 if(jsonResult.containsKey("code")&&jsonResult.getInteger("code")==200){
					 JSONObject data = jsonResult.getJSONObject("data");
					 result.setTotal(data.getLong("total"));
					 List<CarScrapOrderPageBO> rowsData = new ArrayList<CarScrapOrderPageBO>();
					 result.setRows(rowsData);
			         JSONArray array = data.getJSONArray("rows");
			         for(int i=0;i<array.size();i++){
				         String str = JSONObject.toJSONString(array.get(i),SerializerFeature.WriteMapNullValue); 
				         CarScrapOrderPageBO bo = JSON.parseObject(str,CarScrapOrderPageBO.class);
				         rowsData.add(bo);
				        
			          }
			
			         return result;
			 }else{
				
				 throw new NetException("请求异常");
			 }
			 
			 
			 
		      
        }
		
		return null;
	}
	
	public PageResult<CarScrapOrderPageBO>  queryHistoryBOPageListByWhere(Integer page,Integer rows,
			CarScrapOrderKeywordVO paramter)throws Exception{
		
		PageResult<CarScrapOrderPageBO> result = new PageResult<CarScrapOrderPageBO>();
		 String queryAutoPartsPageUrl = UNIFIED_EXTERNAL_URI+QUERY_AUTOPARTS_HISTORY_AUDIT_URL;
		 JSONObject json = (JSONObject) JSON.toJSON(paramter);
		 Map<String,Object> map = json;
		 map.put("page", page);
		 map.put("rows", rows);
		 HttpResult httpResult = this.apiService.doGet(queryAutoPartsPageUrl, map);
		 
		 if (httpResult.getCode() == 200) {
			  JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
				 if(jsonResult.containsKey("code")&&jsonResult.getInteger("code")==200){
					 JSONObject data = jsonResult.getJSONObject("data");
					 result.setTotal(data.getLong("total"));
					 List<CarScrapOrderPageBO> rowsData = new ArrayList<CarScrapOrderPageBO>();
					 result.setRows(rowsData);
			         JSONArray array = data.getJSONArray("rows");
			         for(int i=0;i<array.size();i++){
				         String str = JSONObject.toJSONString(array.get(i),SerializerFeature.WriteMapNullValue); 
				         CarScrapOrderPageBO bo = JSON.parseObject(str,CarScrapOrderPageBO.class);
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
	public Boolean update(Map<String,String> param,String id) throws Exception{
		String updateAutoParts = MessageFormat.format(UNIFIED_EXTERNAL_URI+UPDATE_AUTOPARTS_BYID, id);
		
		// HttpResult httpResult = this.apiService.doPutJson(updateAutoParts, MAPPER.writeValueAsString(carScrapOrderAutoparts));
		
		
		 HttpResult httpResult = this.apiService.doPut(updateAutoParts, param);
		 
		 if (httpResult.getCode() == 200) {
             String jsonData = httpResult.getContent();
             JsonNode jsonNode = MAPPER.readTree(jsonData);
             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                 // 订单创建成功
                  String data = jsonNode.get("data").asText();
                  return  jsonNode.get("data").asBoolean();
             }
         }
		 
		
		return false;
		
		
	}
	

	@Override
	public Boolean delete(Integer id, String operator) throws Exception {
		String updateAutoParts = MessageFormat.format(UNIFIED_EXTERNAL_URI+DELETE_AUTOPARTS_BYID, id);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("operator", operator);
		 HttpResult httpResult = this.apiService.doDelete(updateAutoParts, params);
		 if (httpResult.getCode() == 200) {
			 String jsonData = httpResult.getContent();
             JsonNode jsonNode = MAPPER.readTree(jsonData);
             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                 // 订单创建成功
                  String data = jsonNode.get("data").asText();
                  return  jsonNode.get("data").asBoolean();
             }
		 }
		return false;
	}

}
