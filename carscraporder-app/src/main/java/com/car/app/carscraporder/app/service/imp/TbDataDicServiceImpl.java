package com.car.app.carscraporder.app.service.imp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.car.app.carscraporder.app.service.TbDataDicService;
import com.car.app.carscraporder.pojo.TbDataDic;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TbDataDicServiceImpl implements TbDataDicService {
	
	
	@Value("${UNIFIED_EXTERNAL_URI}")
	private String UNIFIED_EXTERNAL_URI;
	
	
	@Value("${GET_DIC_BY_DATATYPE}")
	private String GET_DIC_BY_DATATYPE;
	
	@Autowired
	private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public List<TbDataDic> getDicByDataType(String dataType) throws Exception {
		String getDicByDataType = UNIFIED_EXTERNAL_URI+GET_DIC_BY_DATATYPE;
		
		 Map<String, Object> params = new HashMap<String, Object>();
		 params.put("dataType", dataType);
		  HttpResult httpResult = this.apiService.doGet(getDicByDataType,params);
		  
		  if (httpResult.getCode() == 200) {
	             String jsonData = httpResult.getContent();
	             JsonNode jsonNode = MAPPER.readTree(jsonData);
	             if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
	                 // 订单创建成功
	            	 MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//忽略对象中没有的属性
	            	 List<TbDataDic> data =(List<TbDataDic>) MAPPER.readValue(jsonNode.path("data").toString(),new TypeReference<List<TbDataDic>>() {});
	                 
	                  return data;
	             }
	         }
		  
		return null;
	}

}
