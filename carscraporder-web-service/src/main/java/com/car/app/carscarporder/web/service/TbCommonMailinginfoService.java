package com.car.app.carscarporder.web.service;

import com.car.app.carscraporder.bo.TbCommonMailinginfoBO;
import com.car.app.carscraporder.pojo.TbCommonMailinginfo;
import com.car.app.common.bean.PageResult;

public interface TbCommonMailinginfoService {
	
	
	public PageResult<TbCommonMailinginfoBO> queryPageListByWhere(Integer page, Integer rows,TbCommonMailinginfo paramter)throws Exception;

}
