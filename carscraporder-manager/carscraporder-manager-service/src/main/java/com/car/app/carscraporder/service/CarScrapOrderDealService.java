package com.car.app.carscraporder.service;

import java.util.List;

import com.car.app.carscraporder.bo.CarScrapOrderDealBO;
import com.car.app.carscraporder.pojo.CarScrapOrderDeal;


public interface CarScrapOrderDealService extends BaseService<CarScrapOrderDeal> {
	public List<CarScrapOrderDealBO> queryList() throws Exception;
	public CarScrapOrderDealBO queryBOById(String id) throws Exception;
}
