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
import com.car.app.carscarporder.web.service.TbCommonMailinginfoService;
import com.car.app.carscraporder.bo.TbCommonMailinginfoBO;
import com.car.app.carscraporder.pojo.TbCommonMailinginfo;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.bean.PageResult;
import com.car.app.common.exception.NetException;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TbCommonMailinginfoServiceImpl implements
		TbCommonMailinginfoService {
	
	

	@Value("${GET_COMMONMAILING}")
	private String GET_COMMONMAILING;
	
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	
	@Autowired
	private ApiService apiService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public PageResult<TbCommonMailinginfoBO> queryPageListByWhere(Integer page,
			Integer rows, TbCommonMailinginfo paramter) throws Exception {
		PageResult<TbCommonMailinginfoBO> result = new PageResult<TbCommonMailinginfoBO>();
		 String queryCommonMailingURl = UNIFIED_EXTERNAL_URI+GET_COMMONMAILING;
		 
		 JSONObject json = (JSONObject) JSON.toJSON(paramter);
		 Map<String,Object> map = json;
		 map.put("page", page);
		 map.put("rows", rows);
		 
        HttpResult httpResult = this.apiService.doGet(queryCommonMailingURl,map);
		 
        if (httpResult.getCode() == 200) {
			  JSONObject jsonResult = (JSONObject) JSONObject.parse(httpResult.getContent());
				 if(jsonResult.containsKey("code")&&jsonResult.getInteger("code")==200){
					 JSONObject data = jsonResult.getJSONObject("data");
					 result.setTotal(data.getLong("total"));
					 List<TbCommonMailinginfoBO> rowsData = new ArrayList<TbCommonMailinginfoBO>();
					 result.setRows(rowsData);
			         JSONArray array = data.getJSONArray("rows");
			         for(int i=0;i<array.size();i++){
				         String str = JSONObject.toJSONString(array.get(i),SerializerFeature.WriteMapNullValue); 
				         TbCommonMailinginfoBO bo = JSON.parseObject(str,TbCommonMailinginfoBO.class);
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
