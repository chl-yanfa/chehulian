package com.car.app.carscraporder.app.service.imp;

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
import com.car.app.carscarporder.web.service.impl.CarScrapOrderAutopartsServiceImpl;
import com.car.app.carscraporder.app.service.CarScrapOrderAutopartsAppService;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsDetailBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsPageBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.pojo.CarScrapOrderAutoparts;
import com.car.app.carscraporder.vo.CarScrapOrderAutopartsWhereParamter;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.bean.PageResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CarScrapOrderAutopartsAppServiceImpl extends CarScrapOrderAutopartsServiceImpl implements
		CarScrapOrderAutopartsAppService {
	
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	
	@Value("${AUTOPARTS_AUDITING}")
    private String AUTOPARTS_AUDITING;
	
	@Value("${QUERY_WILL_DEALT_AUTOPARTS}")
	private String QUERY_WILL_DEALT_AUTOPARTS;
	
	@Value("${SORTING_AUTOPARTS_BYID}")
	private String SORTING_AUTOPARTS_BYID;
	
	@Value("${GET_SORTING_AUTOPARTS}")
	private String GET_SORTING_AUTOPARTS;
	
	
	@Autowired
	 private ApiService apiService;

	@Override
	public CarScrapOrderAutopartsDetailBO getOrderAutopartsDetailById(String id)
			throws Exception {
		
		return super.getOrderAutopartsDetailById(id);
	}

	@Override
	public List<CarScrapOrderAutopartsBO> queryListByOrderId(String orderid)
			throws Exception {
		
		return  super.queryListByOrderId(orderid);
	}

	@Override
	public PageResult<CarScrapOrderPageBO> queryBOPageListByWhere(
			Integer page, Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception {
		
		return super.queryBOPageListByWhere(page,rows,paramter);
	}

	public PageResult<CarScrapOrderPageBO>  queryHistoryBOPageListByWhere(Integer page,Integer rows,
			CarScrapOrderKeywordVO paramter)throws Exception{
		return super.queryHistoryBOPageListByWhere(page,rows,paramter);
	}
	
	@Override
	public Boolean saveOrderAutopartsAuditingRecord(String id,
			Integer auditorderStatus, String auditRemark, String operator,Map<String,String> paramMap)
			throws Exception {
		
		String orderAuditingUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI+AUTOPARTS_AUDITING, id);
		
		 paramMap.put("auditorderStatus", auditorderStatus+"");
		 paramMap.put("auditRemark", auditRemark);
		 paramMap.put("auditer", operator);
		 HttpResult httpResult = this.apiService.doPut(orderAuditingUrl, paramMap);
		 
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
	
	
	public PageResult<CarScrapOrderPageBO>  getSortingParts(Integer page,Integer rows, CarScrapOrderKeywordVO paramter)throws Exception{
		
		
		PageResult<CarScrapOrderPageBO> result = new PageResult<CarScrapOrderPageBO>();
		 String queryAutoPartsPageUrl = UNIFIED_EXTERNAL_URI+GET_SORTING_AUTOPARTS;
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
	public Boolean update(Map<String,String> param,String id)
			throws Exception {
		
		return super.update(param,id);
	}

	@Override
	public Boolean delete(Integer id, String operator) throws Exception {
		
		return super.delete(id, operator);
	}

	@Override
	public CarScrapOrderAutopartsBO queryBOById(String id) throws Exception {
		
		return super.queryBOById(id);
	}
	
	public int queryPendingOrder(String agentUserid,Integer autoPartsStauts,String areas,Boolean isSorting)throws Exception{
		
		 String queryWillDealtOrderCountUrl = UNIFIED_EXTERNAL_URI+QUERY_WILL_DEALT_AUTOPARTS;
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("agentUserid", agentUserid);
		 map.put("partsStauts", autoPartsStauts);
		 map.put("areas", areas);
		 if(isSorting!=null)
		    map.put("isSorting", isSorting);
		 
		  HttpResult httpResult = this.apiService.doGet(queryWillDealtOrderCountUrl,map);
		  if (httpResult.getCode() == 200) {
	             String jsonData = httpResult.getContent();
	             JsonNode jsonNode = MAPPER.readTree(jsonData);
	             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	                 // 订单创建成功
	            	 return jsonNode.get("data").asInt();
	             }
	         }
		return 0;
	
	}
	
	public Boolean sortingParts(String id,Integer sortingStatus, String operator)throws Exception{
		
		String sortingAutopartsById = MessageFormat.format(UNIFIED_EXTERNAL_URI+SORTING_AUTOPARTS_BYID, id);
		
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("sortingStatus", sortingStatus);
		 map.put("operator", operator);
		 HttpResult httpResult = this.apiService.doPost(sortingAutopartsById, map);
		 if (httpResult.getCode() == 200) {
             String jsonData = httpResult.getContent();
             JsonNode jsonNode = MAPPER.readTree(jsonData);
             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                 // 订单创建成功
            	 return jsonNode.get("data").asBoolean();
             }
         }
		 
		
		return false;
		
	}


}
