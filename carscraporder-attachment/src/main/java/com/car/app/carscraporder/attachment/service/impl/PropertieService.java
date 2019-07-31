package com.car.app.carscraporder.attachment.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertieService {
	
	@Value("${REPOSITORY_PATH}")
	public  String REPOSITORY_PATH;
	
	
	
	@Value("${PICURL}")
	public  String PICURL;

}
