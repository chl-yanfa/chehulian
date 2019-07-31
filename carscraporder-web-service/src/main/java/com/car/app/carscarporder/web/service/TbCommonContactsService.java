package com.car.app.carscarporder.web.service;

import com.car.app.carscraporder.pojo.TbCommonContacts;
import com.car.app.common.bean.PageResult;

public interface TbCommonContactsService {
	
	
	public PageResult<TbCommonContacts> queryPageListByWhere(Integer page, Integer rows,TbCommonContacts paramter)throws Exception;

}
