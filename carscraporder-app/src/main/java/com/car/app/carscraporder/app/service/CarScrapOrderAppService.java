package com.car.app.carscraporder.app.service;

import java.io.IOException;
import java.util.Map;

import com.car.app.carscraporder.bo.CarScrapOrderBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.CarScrapOrderQuotePageBO;
import com.car.app.carscraporder.bo.CarScrapOrderSimpleInfo;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.carscraporder.vo.CarScrapOrderQuoteVO;
import com.car.app.common.bean.PageResult;

public interface CarScrapOrderAppService {

	 CarScrapOrderSimpleInfo getSimpleOrderInfoById(String orderid)throws Exception;
	
	 CarScrapOrderBO getOrderById(String orderid)throws Exception;

	 Boolean updateOrder(String id,Map<String,String> paramMap)throws Exception;

	Boolean saveOrderAuditingRecord(String id, Integer orderStatus,String remark,Map<String,String> paramMap)throws Exception;
	
	 Boolean saveOrderAuditingRecord(String id, Integer orderStatus,String remark,String operator,Map<String,String> paramMap)throws Exception;

	 PageResult<CarScrapOrderPageBO> getOrderPageListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	 PageResult<CarScrapOrderPageBO> getAll(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	 PageResult<CarScrapOrderPageBO> queryPageHistoryListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;

	 int queryPendingOrder(String agentUserid,Integer orderStauts,String areas)throws Exception;

	 Boolean quote(CarScrapOrderQuoteVO quoteVO)throws Exception;
	
	 PageResult<CarScrapOrderQuotePageBO>  queryQuotePageListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
}
