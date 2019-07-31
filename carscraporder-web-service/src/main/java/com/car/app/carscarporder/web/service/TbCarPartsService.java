package com.car.app.carscarporder.web.service;

import java.util.List;

import com.car.app.carscraporder.pojo.TbCarParts;

public interface TbCarPartsService {
	
	public List<TbCarParts> getAllByCategory(Integer cacegoryId)throws Exception;

}
