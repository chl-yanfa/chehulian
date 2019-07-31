package com.car.app.carscarporder.web.service;



import java.util.Map;

import com.car.app.carscraporder.bo.CarScrapOrderBO;
import com.car.app.carscraporder.bo.CarScrapOrderMyTradeBO;
import com.car.app.carscraporder.bo.CarScrapOrderMyTradeInfoBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.OrderAduitBO;
import com.car.app.carscraporder.vo.CarScrapOrderConfirmAmountVO;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.carscraporder.vo.CarScrapOrderVO;
import com.car.app.common.bean.PageResult;

public interface CarScrapOrderService {
	
	

	
	public PageResult<CarScrapOrderPageBO> getOrderPageListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	public PageResult<CarScrapOrderPageBO>  queryPageHistoryListByKeyword(Integer page,
			Integer rows, CarScrapOrderKeywordVO paramter)
			throws Exception ;
	
	public PageResult<CarScrapOrderPageBO> getAll(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	
	public Boolean addOrder(CarScrapOrderVO order)throws Exception;
	
	public Boolean deleteOrderById(String orderid,String operator)throws Exception;
	
	public Boolean cancelOrderById(Map<String,String> paramMap)throws Exception;
	
	public Boolean confirmAmount(CarScrapOrderConfirmAmountVO confirmAmountVO)throws Exception;
	
	public CarScrapOrderBO getOrderById(String orderid)throws Exception;
	
	public Boolean updateOrder(String id,Map<String,String> paramMap)throws Exception;
	
	public OrderAduitBO getOrderAuditInfo(String orderId) throws Exception;
	
	public CarScrapOrderMyTradeBO myTrade(String clientId)throws Exception;
	
	public PageResult<CarScrapOrderMyTradeInfoBO> myTradeInfo(Map<String,Object> map)throws Exception;
	
 
}
