package com.car.app.carscraporder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.bo.CarScrapOrderDealBO;
import com.car.app.carscraporder.mapper.CarScrapOrderDealMapper;
import com.car.app.carscraporder.pojo.CarScrapOrderDeal;
import com.car.app.carscraporder.service.CarScrapOrderDealService;

@Service
public class CarScrapOrderDealServiceImpl extends BaseServiceImpl<CarScrapOrderDeal> implements CarScrapOrderDealService {
	
	@Autowired
	private CarScrapOrderDealMapper carScrapOrderDealMapper;

	@Override
	public List<CarScrapOrderDealBO> queryList() throws Exception {
		return carScrapOrderDealMapper.queryList();
	}

	@Override
	public CarScrapOrderDealBO queryBOById(String id) throws Exception {
		return carScrapOrderDealMapper.queryBOById(id);
	}
	
	

	
}
