package com.car.app.carscraporder.app.service.imp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommonVariablesServiceImpl {
	
	@Value("${PICURL}")
	public  String PICURL;
	
	

}
