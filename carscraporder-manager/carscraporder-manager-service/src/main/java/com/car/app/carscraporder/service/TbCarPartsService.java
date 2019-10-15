package com.car.app.carscraporder.service;

import com.car.app.carscraporder.pojo.TbCarParts;
import com.github.pagehelper.PageInfo;

public interface TbCarPartsService extends BaseService<TbCarParts>{

     PageInfo<TbCarParts> queryByIdPageListByWhere(int page, int rows, TbCarParts paramter)throws Exception;
}
