package com.car.app.carscraporder.app.service.imp;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.car.app.carscraporder.app.bean.UserAppBO;
import com.car.app.carscraporder.app.service.CarScrapOrderAppService;
import com.car.app.carscraporder.app.util.UserUtil;
import com.car.app.carscraporder.bo.CarScrapOrderBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.CarScrapOrderQuotePageBO;
import com.car.app.carscraporder.bo.CarScrapOrderSimpleInfo;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.carscraporder.vo.CarScrapOrderQuoteVO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.bean.PageResult;
import com.car.app.common.exception.DataException;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CarScrapOrderAppServiceImpl extends com.car.app.carscarporder.web.service.impl.CarScrapOrderServiceImpl implements CarScrapOrderAppService {
	
	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;
	
	@Value("${AUDIT_ORDERS_BYID_URI}")
	private String AUDIT_ORDERS_BYID_URI;
	
	@Value("${GET_ORDERS_SIMPLEINFO_BYID_URI}")
	private String GET_ORDERS_SIMPLEINFO_BYID_URI;
	
	@Value("${QUERY_WILL_DEALT_ORDER}")
	private String QUERY_WILL_DEALT_ORDER;
	
	@Value("${ORDER_QUOTE_URL}")
	private String ORDER_QUOTE_URL;
	
	@Value("${ORDER_QUOTELIST_URL}")
	private String ORDER_QUOTELIST_URL;

	
	private Logger logger = LoggerFactory.getLogger(CarScrapOrderAppServiceImpl.class);
	
	
	
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private ApiService apiService;
	
	public CarScrapOrderSimpleInfo getSimpleOrderInfoById(String id)throws Exception{
		 String getSimpleOrderInfoByIdUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI+GET_ORDERS_SIMPLEINFO_BYID_URI, id);
		  HttpResult httpResult = this.apiService.doGet(getSimpleOrderInfoByIdUrl);
		  if (httpResult.getCode() == 200) {
	             String jsonData = httpResult.getContent();
	             JsonNode jsonNode = MAPPER.readTree(jsonData);
	             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	                 // 订单创建成功
	            	 String data = jsonNode.path("data").toString();
	            	if(StringUtils.isNotBlank(data)){
	            		return MAPPER.readValue(jsonNode.path("data").toString(),CarScrapOrderSimpleInfo.class);
	            	}
	                  
	             }
	         }
		return null;
			 
	}
	
	

	@Override
	public CarScrapOrderBO getOrderById(String orderid) throws Exception {
		
		return super.getOrderById(orderid);
	}

	@Override
	public Boolean updateOrder(String id,Map<String,String> paramMap) throws Exception {
		
		return super.updateOrder(id,paramMap);
	}


	
	@Override
	public Boolean saveOrderAuditingRecord(String id, Integer orderStatus,
			String remark, String operator,Map<String,String> paramMap) throws Exception {
		
		  paramMap.put("auditorderStatus", orderStatus+"");
		  paramMap.put("auditRemark", remark);
		  paramMap.put("auditer", operator);
		  
		
		  String auditOrderUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI+AUDIT_ORDERS_BYID_URI, id);
		
          HttpResult httpResult = this.apiService.doPut(auditOrderUrl, paramMap);
		 
		 if (httpResult.getCode() == 200) {
             String jsonData = httpResult.getContent();
             JsonNode jsonNode = MAPPER.readTree(jsonData);
             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                 // 订单创建成功
                  String data = jsonNode.get("data").asText();
                  return  jsonNode.get("data").asBoolean();
             }
         }else{
        	 logger.error("调用订单审核出错响应结果：{},接口路径：{}", httpResult.getCode(),auditOrderUrl); 
         }
		 
		return false;
		
		
	}
	
	public PageResult<CarScrapOrderPageBO> getAll(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception{
		return super.getAll(page, rows, paramter);
	}

	
	
	@Override
	public PageResult<CarScrapOrderPageBO> getOrderPageListByKeyword(Integer page,
			Integer rows, CarScrapOrderKeywordVO paramter)
			throws Exception {
		return super.getOrderPageListByKeyword(page, rows, paramter);
	}
	
	public PageResult<CarScrapOrderPageBO> queryPageHistoryListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception{
		return super.queryPageHistoryListByKeyword(page, rows, paramter);
	}



	@Override
	public int queryPendingOrder(String agentUserid,Integer orderStauts,String areas) throws Exception {
		
		 UserAppBO user = UserUtil.getUser();
         if(user==null){
   	        throw new DataException("未登录系统");
          }
		
		 String queryWillDealtOrderCountUrl = UNIFIED_EXTERNAL_URI+QUERY_WILL_DEALT_ORDER;
		 Map<String, Object> map = new HashMap<String, Object>();
		 
		if(!user.getIsAllDataPermisssion()){
			 map.put("agentUserid", agentUserid);
			 map.put("areas", areas);
		 }
		 map.put("orderStauts", orderStauts);
		
		 
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



	@Override
	public Boolean quote(CarScrapOrderQuoteVO quoteVO) throws Exception {
		String quoteOrderUrl = UNIFIED_EXTERNAL_URI + ORDER_QUOTE_URL;
		HttpResult httpResult = this.apiService.doPutJson(quoteOrderUrl, MAPPER.writeValueAsString(quoteVO));
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				return jsonNode.get("data").asBoolean();
			}else if (jsonNode.has("code") && jsonNode.get("code").asInt() == 204){
				throw new DataException("订单状态异常");
			}
		} else {
			logger.error("调用订单审核出错响应结果：{},接口路径：{}", httpResult.getCode(), quoteOrderUrl);
		}

		return false;
	}



	@Override
	public PageResult<CarScrapOrderQuotePageBO> queryQuotePageListByKeyword(Integer page, Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception {
		PageResult<CarScrapOrderQuotePageBO> result = new PageResult<CarScrapOrderQuotePageBO>();
		String quoteListOrderUrl = UNIFIED_EXTERNAL_URI + ORDER_QUOTELIST_URL;
		JSONObject json = (JSONObject) JSON.toJSON(paramter);
		Map<String, Object> map = json;
		map.put("page", page);
		map.put("rows", rows);
		HttpResult httpResult = this.apiService.doGet(quoteListOrderUrl, map);
		if (httpResult.getCode() == 200) {
			  JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
				 if(jsonResult.containsKey("code")&&jsonResult.getInteger("code")==200){
					 JSONObject data = jsonResult.getJSONObject("data");
					 result.setTotal(data.getLong("total"));
					 List<CarScrapOrderQuotePageBO> rowsData = new ArrayList<CarScrapOrderQuotePageBO>();
					 result.setRows(rowsData);
			         JSONArray array = data.getJSONArray("rows");
			         for(int i=0;i<array.size();i++){
				         String str = JSONObject.toJSONString(array.get(i),SerializerFeature.WriteMapNullValue); 
				         CarScrapOrderQuotePageBO bo = JSON.parseObject(str,CarScrapOrderQuotePageBO.class);
						 System.out.println("带回来的bo为:"+bo);
				         rowsData.add(bo);
			          }
			         return result;
			 }else{
				 throw new NetException("请求异常");
			 }
       }
		return null;
	}

}
