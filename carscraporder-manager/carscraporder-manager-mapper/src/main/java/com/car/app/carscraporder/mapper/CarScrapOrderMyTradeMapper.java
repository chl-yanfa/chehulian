package com.car.app.carscraporder.mapper;

import java.util.List;

import com.car.app.carscraporder.bo.CarScrapOrderMyTradeBO;
import com.car.app.carscraporder.bo.CarScrapOrderMyTradeInfoBO;


public interface CarScrapOrderMyTradeMapper{
	
	public CarScrapOrderMyTradeBO queryMyTrade(String clientId);
	
	public List<CarScrapOrderMyTradeInfoBO> queryPageMyTradeInfo(String clientId);

}
