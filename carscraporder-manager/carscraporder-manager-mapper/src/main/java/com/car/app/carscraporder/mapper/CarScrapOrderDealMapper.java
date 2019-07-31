package com.car.app.carscraporder.mapper;

import java.util.List;

import com.car.app.carscraporder.bo.CarScrapOrderDealBO;
import com.car.app.carscraporder.pojo.CarScrapOrderDeal;
import com.github.abel533.mapper.Mapper;


public interface CarScrapOrderDealMapper extends Mapper<CarScrapOrderDeal>{
	
	public List<CarScrapOrderDealBO> queryList();
	public CarScrapOrderDealBO queryBOById(String id);
	
}
