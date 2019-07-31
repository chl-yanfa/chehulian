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
import com.car.app.carscarporder.web.service.CarScrapOrderService;
import com.car.app.carscraporder.bo.CarScrapOrderBO;
import com.car.app.carscraporder.bo.CarScrapOrderMyTradeBO;
import com.car.app.carscraporder.bo.CarScrapOrderMyTradeInfoBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.OrderAduitBO;
import com.car.app.carscraporder.vo.CarScrapOrderConfirmAmountVO;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.carscraporder.vo.CarScrapOrderVO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.bean.PageResult;
import com.car.app.common.exception.DataException;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CarScrapOrderServiceImpl implements CarScrapOrderService {
	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Value("${CREATE_ORDER_URI}")
    private String CREATE_ORDER_URL;
	
	@Value("${UPDATE_ORDER_URI}")
    private String UPDATE_ORDER_URL;
	  
	@Value("${DELETE_ORDER_URI}")
	private String DELETE_ORDER_URL;
	
	@Value("${CANCEL_ORDER_URL}")
	private String CANCEL_ORDER_URL;
	  
	@Value("${QUERY_ORDERS_PAGE_URI}")
	private String QUERY_ORDERS_PAGE_URL;
	  
	@Value("${QUERY_ORDER_URI}")
    private String QUERY_ORDER_URL;
	
	@Value("${QUERY_ORDER_HISTORY_AUDIT_URL}")
	private String QUERY_ORDER_HISTORY_AUDIT_URL;
	
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	@Value("${GETORDERAUDITINFO}")
	private String GETORDERAUDITINFO;
	
	@Value("${QUERY_ALL_ORDER_URL}")
	private String QUERY_ALL_ORDER_URL;

	@Value("${ORDER_CONFIRMAMOUNT_URL}")
	private String ORDER_CONFIRMAMOUNT_URL;

	@Value("${ORDER_MYTRADE_URL}")
	private String ORDER_MYTRADE_URL;
	
	@Value("${ORDER_MYTRADEINFO_URL}")
	private String ORDER_MYTRADEINFO_URL;
	
	
	
	
	
	
	public PageResult<CarScrapOrderPageBO> getAll(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception{
		
		PageResult result = new PageResult();
		 String queryOrderPageUrl = UNIFIED_EXTERNAL_URI+QUERY_ALL_ORDER_URL;
		 JSONObject json = (JSONObject) JSON.toJSON(paramter);
		 Map map = json; 
		 map.put("page", page);
		 map.put("rows", rows);
		 HttpResult httpResult = this.apiService.doGet(queryOrderPageUrl, map);
		 
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
	public PageResult<CarScrapOrderPageBO>  getOrderPageListByKeyword(Integer page,
			Integer rows, CarScrapOrderKeywordVO paramter)
			throws Exception {
		
		PageResult<CarScrapOrderPageBO> result = new PageResult<CarScrapOrderPageBO>();
		 String queryOrderPageUrl = UNIFIED_EXTERNAL_URI+QUERY_ORDERS_PAGE_URL;
		 JSONObject json = (JSONObject) JSON.toJSON(paramter);
		 Map map = json; 
		 map.put("page", page);
		 map.put("rows", rows);
		 HttpResult httpResult = this.apiService.doGet(queryOrderPageUrl, map);
		 
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
	public PageResult<CarScrapOrderPageBO>  queryPageHistoryListByKeyword(Integer page,
			Integer rows, CarScrapOrderKeywordVO paramter)
			throws Exception {
		
		PageResult result = new PageResult();
		 String queryOrderPageUrl = UNIFIED_EXTERNAL_URI+QUERY_ORDER_HISTORY_AUDIT_URL;
		 JSONObject json = (JSONObject) JSON.toJSON(paramter);
		 Map map = json; 
		 map.put("page", page);
		 map.put("rows", rows);
		 HttpResult httpResult = this.apiService.doGet(queryOrderPageUrl, map);
		 
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
	public Boolean addOrder(CarScrapOrderVO order) throws Exception {
		 String createOrderUrl = UNIFIED_EXTERNAL_URI+CREATE_ORDER_URL;
		 HttpResult httpResult = this.apiService.doPostJson(createOrderUrl, MAPPER.writeValueAsString(order));
		 
		 if (httpResult.getCode() == 200) {
             String jsonData = httpResult.getContent();
             JsonNode jsonNode = MAPPER.readTree(jsonData);
             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                 // 订单创建成功
                 return jsonNode.get("data").asBoolean();
             }
         }else{
        	 throw new NetException("http请求响应编码"+httpResult.getCode());
         }
		return false;
	}

	@Override
	public Boolean deleteOrderById(String orderid,String operator)throws Exception {
           
            String OrderDeleteUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI+DELETE_ORDER_URL, orderid);
		 
         Map<String,Object> paramter = new HashMap<>();
         paramter.put("id", orderid);
         paramter.put("operator", operator);
		 HttpResult httpResult = this.apiService.doDelete(OrderDeleteUrl,paramter);
		 
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

	@Override
	public CarScrapOrderBO getOrderById(String orderid)throws Exception  {
		  String OrderQueryByIdUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI+QUERY_ORDER_URL, orderid);
			 
			 HttpResult httpResult = this.apiService.doGet(OrderQueryByIdUrl);
			 
			 if (httpResult.getCode() == 200) {
	             String jsonData = httpResult.getContent();
	             JsonNode jsonNode = MAPPER.readTree(jsonData);
	             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	                 // 订单创建成功
	            	 
	                  return MAPPER.readValue(jsonNode.path("data").toString(),CarScrapOrderBO.class);
	             }
	         }
			 
			 
			 
			return null;
	}

	@Override
	public Boolean updateOrder(String id,Map<String,String> paramMap) throws Exception  { 
		String OrderUpdateByIdUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI+UPDATE_ORDER_URL, id);
		 
		
		 HttpResult httpResult = this.apiService.doPut(OrderUpdateByIdUrl, paramMap);
		 
		 if (httpResult.getCode() == 200) {
             String jsonData = httpResult.getContent();
             JsonNode jsonNode = MAPPER.readTree(jsonData);
             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                 // 订单创建成功
                  String data = jsonNode.get("data").asText();
                  return  (1==jsonNode.get("data").asInt());
             }
         }
		 
		return false;
	}
	
	public OrderAduitBO getOrderAuditInfo(String orderId) throws Exception{
		
		String OrderUpdateByIdUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI+GETORDERAUDITINFO, orderId);
		 
		
		 HttpResult httpResult = this.apiService.doGet(OrderUpdateByIdUrl);
		 
		 if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                // 订单创建成功
            	  return MAPPER.readValue(jsonNode.path("data").toString(),OrderAduitBO.class);
            }
        }
		 
		return null;
		
	}


	@Override
	public Boolean cancelOrderById(Map<String, String> paramMap) throws Exception {
		String OrderCancelUrl = UNIFIED_EXTERNAL_URI + CANCEL_ORDER_URL;
		HttpResult httpResult = this.apiService.doPut(OrderCancelUrl, paramMap);
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				return jsonNode.get("data").asBoolean();
			}
		}
		return false;
	}


	@Override
	public Boolean confirmAmount(CarScrapOrderConfirmAmountVO confirmAmountVO) throws Exception {
		String OrderConfirmAmountUrl = UNIFIED_EXTERNAL_URI + ORDER_CONFIRMAMOUNT_URL;
		String writeValueAsString = MAPPER.writeValueAsString(confirmAmountVO);
		HttpResult httpResult = this.apiService.doPutJson(OrderConfirmAmountUrl, writeValueAsString);
		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				return jsonNode.get("data").asBoolean();
			}else if(jsonNode.has("code") && jsonNode.get("code").asInt() == 204){
				throw new DataException("订单状态异常");
            }
		}
		return false;
	}


	@Override
	public CarScrapOrderMyTradeBO myTrade(String clientId) throws Exception {
		String OrderMyTradeUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI + ORDER_MYTRADE_URL, clientId);
		HttpResult httpResult = this.apiService.doGet(OrderMyTradeUrl);

		if (httpResult.getCode() == 200) {
			String jsonData = httpResult.getContent();
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
				return MAPPER.readValue(jsonNode.path("data").toString(), CarScrapOrderMyTradeBO.class);
			}
		}

		return null;
	}


	@Override
	public PageResult<CarScrapOrderMyTradeInfoBO> myTradeInfo(Map<String,Object> map) throws Exception {
		PageResult<CarScrapOrderMyTradeInfoBO> result = new PageResult<CarScrapOrderMyTradeInfoBO>();
		String myTradeInfoUrl = UNIFIED_EXTERNAL_URI + ORDER_MYTRADEINFO_URL;
		HttpResult httpResult = this.apiService.doGet(myTradeInfoUrl, map);

		if (httpResult.getCode() == 200) {
			JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
			if (jsonResult.containsKey("code") && jsonResult.getInteger("code") == 200) {
				JSONObject data = jsonResult.getJSONObject("data");
				result.setTotal(data.getLong("total"));
				List<CarScrapOrderMyTradeInfoBO> rowsData = new ArrayList<CarScrapOrderMyTradeInfoBO>();
				result.setRows(rowsData);
				JSONArray array = data.getJSONArray("rows");
				for (int i = 0; i < array.size(); i++) {
					String str = JSONObject.toJSONString(array.get(i), SerializerFeature.WriteMapNullValue);
					CarScrapOrderMyTradeInfoBO bo = JSON.parseObject(str, CarScrapOrderMyTradeInfoBO.class);
					rowsData.add(bo);
				}
				return result;
			} else {
				throw new NetException("请求异常");
			}

		}

		return null;
	}

	
	
	
	
	
	

}
