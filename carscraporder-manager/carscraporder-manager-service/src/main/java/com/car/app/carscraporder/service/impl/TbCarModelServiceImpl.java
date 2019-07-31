package com.car.app.carscraporder.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.mapper.TbCarSystemMapper;
import com.car.app.carscraporder.pojo.TbCarModel;
import com.car.app.carscraporder.service.TbCarModelService;

@Service
public class TbCarModelServiceImpl extends BaseServiceImpl<TbCarModel> implements TbCarModelService {

	@Autowired
	private TbCarSystemMapper tbCarSystemMapper;
	@Override
	public List<String> getSystemCategory(String brandId) throws Exception {
		return tbCarSystemMapper.getSystemCategory(brandId);
	}
	
}
