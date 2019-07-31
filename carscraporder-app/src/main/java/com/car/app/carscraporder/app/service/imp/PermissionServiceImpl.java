package com.car.app.carscraporder.app.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.app.bean.UserAppBO;
import com.car.app.carscraporder.app.exception.DataException;
import com.car.app.carscraporder.app.exception.UnloginException;
import com.car.app.carscraporder.app.service.PermissionService;
import com.car.app.carscraporder.app.systemparameter.SystemParameter;
import com.car.app.carscraporder.app.util.UserUtil;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsDetailBO;
import com.car.app.carscraporder.pojo.Permission;
import com.car.app.carscraporder.pojo.User;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	
	
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	
	@Value("${QUERY_PERMISSION_BYCODE}")
	private String QUERY_PERMISSION_BYCODE;
	
    @Autowired
	private  ApiService apiService;

	@Override
	public List<Permission> getPermissionByCode(String functionCode) throws Exception{
		
		UserAppBO user = (UserAppBO) UserUtil.getUser();
		
		if(user==null){
			throw new UnloginException("未登录系统");
		}
		
	   String queryPermissionByCodeUrl = UNIFIED_EXTERNAL_URI+QUERY_PERMISSION_BYCODE;
			
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("userid", user.getId());
			params.put("functionCode", functionCode);
			   //根据功能编码获取权限
			HttpResult httpResult = apiService.doGet(queryPermissionByCodeUrl, params);
			
			
			 if (httpResult.getCode() == 200) {
		            String jsonData = httpResult.getContent();
		            JsonNode jsonNode = SystemParameter.MAPPER.readTree(jsonData);
		            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
		            	
		            	List<Permission> permissionresult =  (List<Permission>) SystemParameter.MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<Permission>>() {});
		            	
		           	   return permissionresult;
		            }
		        }
			
			
		
		
		
		
		
		
		
		
		
		return null;
	}

}
