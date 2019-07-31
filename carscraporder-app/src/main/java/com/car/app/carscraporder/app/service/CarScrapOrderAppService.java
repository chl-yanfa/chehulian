package com.car.app.carscraporder.app.service;

import java.util.Map;

import com.car.app.carscraporder.bo.CarScrapOrderBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.CarScrapOrderQuotePageBO;
import com.car.app.carscraporder.bo.CarScrapOrderSimpleInfo;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.carscraporder.vo.CarScrapOrderQuoteVO;
import com.car.app.common.bean.PageResult;

public interface CarScrapOrderAppService {
	
	
	
	
	public CarScrapOrderSimpleInfo getSimpleOrderInfoById(String orderid)throws Exception;
	
	public CarScrapOrderBO getOrderById(String orderid)throws Exception;
	public Boolean updateOrder(String id,Map<String,String> paramMap)throws Exception;
	
	public Boolean saveOrderAuditingRecord(String id, Integer orderStatus,String remark,String operator,Map<String,String> paramMap)throws Exception;
	
	
	
	
	public PageResult<CarScrapOrderPageBO> getOrderPageListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	public PageResult<CarScrapOrderPageBO> getAll(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	public PageResult<CarScrapOrderPageBO> queryPageHistoryListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;
	
	
	public int queryPendingOrder(String agentUserid,Integer orderStauts,String areas)throws Exception;
	
	
	public Boolean quote(CarScrapOrderQuoteVO quoteVO)throws Exception;
	
	
	public PageResult<CarScrapOrderQuotePageBO>  queryQuotePageListByKeyword(Integer page,Integer rows,CarScrapOrderKeywordVO paramter)throws Exception;

}
