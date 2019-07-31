package com.car.app.carscraporder.app.service;

import java.util.List;
import java.util.Map;

import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsDetailBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsPageBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.pojo.CarScrapOrderAutoparts;
import com.car.app.carscraporder.vo.CarScrapOrderAutopartsWhereParamter;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.common.bean.PageResult;

public interface CarScrapOrderAutopartsAppService {
	
	
	public CarScrapOrderAutopartsBO  queryBOById(String id)throws Exception;

	
    public CarScrapOrderAutopartsDetailBO  getOrderAutopartsDetailById(String id)throws Exception;
	
	
	public  List<CarScrapOrderAutopartsBO> queryListByOrderId(String orderid)throws Exception;
	
	
	public PageResult<CarScrapOrderPageBO>  queryBOPageListByWhere(Integer page,Integer rows, CarScrapOrderKeywordVO paramter)throws Exception;
	
	public PageResult<CarScrapOrderPageBO>  getSortingParts(Integer page,Integer rows, CarScrapOrderKeywordVO paramter)throws Exception;
	
	
	public PageResult<CarScrapOrderPageBO>  queryHistoryBOPageListByWhere(Integer page,Integer rows,
			CarScrapOrderKeywordVO paramter)throws Exception;
	/**
	 * 
	 * @param id  配件主键
	 * @param orderStatus 审核状态
	 * @param remark 审核备注
	 * @param operator 审核人
	 * @return
	 * @throws Exception
	 */
	public Boolean saveOrderAutopartsAuditingRecord(String id,Integer orderStatus, String remark, String operator,Map<String,String> paramMap)throws Exception;
	
	public Boolean update(Map<String,String> param,String id)throws Exception;
	
	public Boolean delete(Integer id,  String operator)throws Exception;
	
	
	public int queryPendingOrder(String agentUserid,Integer autoPartsStauts,String areas,Boolean isSorting)throws Exception;
	
	public Boolean sortingParts(String id,Integer sortingStatus, String operator)throws Exception;
	
	

}
