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
	
	

	
	 PageResult<CarScrapOrderPageBO> getOrderPageListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	 PageResult<CarScrapOrderPageBO>  queryPageHistoryListByKeyword(Integer page,
		Integer rows, CarScrapOrderKeywordVO paramter)
		throws Exception ;
	
	 PageResult<CarScrapOrderPageBO> getAll(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	
	 Boolean addOrder(CarScrapOrderVO order)throws Exception;
	
	 Boolean deleteOrderById(String orderid,String operator)throws Exception;
	
	 Boolean cancelOrderById(Map<String,String> paramMap)throws Exception;
	
	 Boolean confirmAmount(CarScrapOrderConfirmAmountVO confirmAmountVO)throws Exception;
	
	 CarScrapOrderBO getOrderById(String orderid)throws Exception;
	
	 Boolean updateOrder(String id,Map<String,String> paramMap)throws Exception;
	
	 OrderAduitBO getOrderAuditInfo(String orderId) throws Exception;
	
	 CarScrapOrderMyTradeBO myTrade(String clientId)throws Exception;
	
	 PageResult<CarScrapOrderMyTradeInfoBO> myTradeInfo(Map<String,Object> map)throws Exception;
	
 
}
