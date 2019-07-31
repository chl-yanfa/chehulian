package com.car.app.carscraporder.app.service.imp;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.app.bean.UserAppBO;
import com.car.app.carscraporder.app.service.UserService;
import com.car.app.carscraporder.app.systemparameter.SystemParameter;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.bo.UserBO;
import com.car.app.carscraporder.pojo.User;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.service.ApiService;
import com.car.app.common.service.RedisService;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private RedisService redisService;
	
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	
	@Value("${USER_LOGIN}")
	private String QUERY_USER_BYNAMEANDPAW;
	
	@Value("${USER_RESETPWD}")
	private String UPDATE_USER_PWD;
	
	@Autowired
	private ApiService apiService;
	

	@Override
	public UserAppBO doLogin(String loginName, String password) throws Exception {
		
		 Map<String,Object> paramter = new HashMap<String,Object>();
		 paramter.put("name", loginName);
		 paramter.put("password", password);
		 HttpResult httpResult = this.apiService.doPost(UNIFIED_EXTERNAL_URI+QUERY_USER_BYNAMEANDPAW,paramter);
		 
		 if (httpResult.getCode() == 200) {
	            String jsonData = httpResult.getContent();
	            JsonNode jsonNode = SystemParameter.MAPPER.readTree(jsonData);
	            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	            	String userinfo = jsonNode.path("data").toString();
	            	
	                // 获取查询信息
	            	UserAppBO user = SystemParameter.MAPPER.readValue(jsonNode.path("data").toString(),UserAppBO.class);
	           	    String ticket = DigestUtils.md5Hex(user.getLoginName() + System.currentTimeMillis());
	           	    
	           	    user.setSessionid(ticket);
	           	    //将session缓存到redis
	           	     redisService.set(SystemParameter.TICKET+ticket, userinfo,60 * 30);
	           	   
	           	   return user;
	            }else{
	            	
	            	ResultBean bean = SystemParameter.MAPPER.readValue(jsonData,ResultBean.class);
	            }
	        }
		 
		 
		
		
		
		return null;
	}

	@Override
	public UserAppBO queryUserByTicket(String ticket) throws Exception {
		 //查询缓存中是否存在ticket的数据
	    String key = SystemParameter.TICKET + ticket;
        String value = this.redisService.get(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        UserAppBO user = SystemParameter.MAPPER.readValue(value, UserAppBO.class);

        // 刷新reds中的数据的生存时间
        this.redisService.expire(key, 60 * 30);

        return user;
	}

	@Override
	public Boolean resetpw(String loginName,String password,String newpassword,String operator)throws Exception{
		
		String updateUserPwdUrl = UNIFIED_EXTERNAL_URI+UPDATE_USER_PWD;
		
		Map<String,String> paramter = new HashMap<String,String>();
		 paramter.put("loginName", loginName);
		 paramter.put("password", password);
		 paramter.put("newpassword", newpassword);
		 paramter.put("operator", operator);
		 HttpResult httpResult = this.apiService.doPut(updateUserPwdUrl,paramter);
		 
		 if (httpResult.getCode() == 200) {
	            String jsonData = httpResult.getContent();
	            JsonNode jsonNode = SystemParameter.MAPPER.readTree(jsonData);
	            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	            	Boolean success = jsonNode.get("data").asBoolean();
	           	   
	           	   return success;
	            }
	        }
		
		return false;
	}

}
