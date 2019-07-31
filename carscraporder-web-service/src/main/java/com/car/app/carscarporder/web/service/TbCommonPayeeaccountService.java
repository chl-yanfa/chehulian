package com.car.app.carscarporder.web.service;


import com.car.app.carscraporder.pojo.TbCommonPayeeaccount;
import com.car.app.common.bean.PageResult;

public interface TbCommonPayeeaccountService {
	
	
	public PageResult<TbCommonPayeeaccount> queryPageListByWhere(Integer page, Integer rows,TbCommonPayeeaccount paramter)throws Exception;

}
