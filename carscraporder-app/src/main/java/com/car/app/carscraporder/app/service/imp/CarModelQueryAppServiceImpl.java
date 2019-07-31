package com.car.app.carscraporder.app.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.impl.CarModelQueryServiceImpl;
import com.car.app.carscraporder.app.service.CarModelQueryAppService;
import com.car.app.common.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CarModelQueryAppServiceImpl extends CarModelQueryServiceImpl  implements CarModelQueryAppService {
	
	@Value("${QUERY_CARMODE_URL}")
	private String QUERY_CARMODE_URL;
	
	@Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;
	
	@Autowired
	 private ApiService apiService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public String queryCarModelData(String vid) throws Exception {
		
		return super.queryCarModelData(vid);
	}

}
