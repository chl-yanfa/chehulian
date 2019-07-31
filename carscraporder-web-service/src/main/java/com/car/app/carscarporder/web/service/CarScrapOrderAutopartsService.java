package com.car.app.carscarporder.web.service;
        
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
import com.github.pagehelper.PageInfo;

public interface CarScrapOrderAutopartsService {
	
   public CarScrapOrderAutopartsBO  queryBOById(String id)throws Exception;
	
	public CarScrapOrderAutopartsDetailBO  getOrderAutopartsDetailById(String id)throws Exception;
	
	
	public  List<CarScrapOrderAutopartsBO> queryListByOrderId(String orderid)throws Exception;
	
	
	public PageResult<CarScrapOrderPageBO>  queryBOPageListByWhere(Integer page,Integer rows, CarScrapOrderKeywordVO paramter)throws Exception;
	
	
	public PageResult<CarScrapOrderPageBO>  queryHistoryBOPageListByWhere(Integer page,Integer rows, CarScrapOrderKeywordVO paramter)throws Exception;


	
	public Boolean update(Map<String,String> param,String id)throws Exception;
	
	public Boolean delete(Integer id,  String operator)throws Exception;

}
