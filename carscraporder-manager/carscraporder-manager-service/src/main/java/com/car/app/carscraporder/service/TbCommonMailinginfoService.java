package com.car.app.carscraporder.service;

import com.car.app.carscraporder.bo.TbCommonMailinginfoBO;
import com.car.app.carscraporder.pojo.TbCommonMailinginfo;
import com.github.pagehelper.PageInfo;

public interface TbCommonMailinginfoService extends BaseService<TbCommonMailinginfo> {
	
	
	
	public PageInfo<TbCommonMailinginfoBO> queryListByWhere(Integer page,Integer rows,TbCommonMailinginfo paramter);

}
