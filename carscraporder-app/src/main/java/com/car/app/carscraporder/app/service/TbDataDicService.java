package com.car.app.carscraporder.app.service;

import java.util.List;

import com.car.app.carscraporder.pojo.TbDataDic;

public interface TbDataDicService {
	
	public List<TbDataDic> getDicByDataType(String dataType)throws Exception;

}
