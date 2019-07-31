package com.car.app.carscarporder.web.service;

import java.util.List;

import com.car.app.carscraporder.pojo.Areas;

public interface AreasService {

	
	
	public Areas queryById(Integer id) throws Exception;
	
	public List<Areas> getAllAreas() throws Exception;
	
}
