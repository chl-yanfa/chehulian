package com.car.app.carscraporder.service;

import java.util.List;

import com.car.app.carscraporder.pojo.TbCarModel;

public interface TbCarModelService extends BaseService<TbCarModel>{
	List<String> getSystemCategory(String brandId) throws Exception;
}
