package com.car.app.carscarporder.web.service;

import java.util.List;

import com.car.app.carscraporder.bo.CarScrapOrderDealBO;


public interface CarScrapOrderDealService {
	public List<CarScrapOrderDealBO> queryList() throws Exception;
	public CarScrapOrderDealBO queryBOById(String id) throws Exception;
}
