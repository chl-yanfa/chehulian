package com.car.app.carscarporder.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.car.app.carscarporder.web.service.TbCommonPayeeaccountService;
import com.car.app.carscraporder.pojo.TbCommonMailinginfo;
import com.car.app.carscraporder.pojo.TbCommonPayeeaccount;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.bean.PageResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TbCommonPayeeaccountServiceImpl implements
		TbCommonPayeeaccountService {
	

	@Value("${GET_COMMONPAYEE}")
	private String GET_COMMONPAYEE;
	
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	
	@Autowired
	private ApiService apiService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public PageResult<TbCommonPayeeaccount> queryPageListByWhere(Integer page,
			Integer rows, TbCommonPayeeaccount paramter) throws Exception {
		PageResult<TbCommonPayeeaccount> result = new PageResult<TbCommonPayeeaccount>();
		 String queryCommonPayeeURl = UNIFIED_EXTERNAL_URI+GET_COMMONPAYEE;
		 
		 JSONObject json = (JSONObject) JSON.toJSON(paramter);
		 Map<String,Object> map = json;
		 map.put("page", page);
		 map.put("rows", rows);
		 
       HttpResult httpResult = this.apiService.doGet(queryCommonPayeeURl,map);
		 
       if (httpResult.getCode() == 200) {
			  JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
				 if(jsonResult.containsKey("code")&&jsonResult.getInteger("code")==200){
					 JSONObject data = jsonResult.getJSONObject("data");
					 result.setTotal(data.getLong("total"));
					 List<TbCommonPayeeaccount> rowsData = new ArrayList<TbCommonPayeeaccount>();
					 result.setRows(rowsData);
			         JSONArray array = data.getJSONArray("rows");
			         for(int i=0;i<array.size();i++){
				         String str = JSONObject.toJSONString(array.get(i),SerializerFeature.WriteMapNullValue); 
				         TbCommonPayeeaccount bo = JSON.parseObject(str,TbCommonPayeeaccount.class);
				         rowsData.add(bo);
				        
			          }
			
			         return result;
			 }else{
				
				 throw new NetException("请求异常");
			 }
			 
			 
			 
		 
	
       }
		return result;
	}

}
