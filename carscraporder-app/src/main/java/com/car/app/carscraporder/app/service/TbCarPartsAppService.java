package com.car.app.carscraporder.app.service;

import java.util.List;

import com.car.app.carscraporder.pojo.TbCarParts;
import com.car.app.carscraporder.pojo.TbCarPartsCategory;

public interface TbCarPartsAppService {
	
	public List<TbCarParts> getAllByCategory(Integer cacegoryId)throws Exception;

}
