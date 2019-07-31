package com.car.app.carscraporder.service;

import com.car.app.carscraporder.pojo.TbCarPartsCategory;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TbCarPartsCategoryService extends BaseService<TbCarPartsCategory> {

    public PageInfo<TbCarPartsCategory> queryMyPageListByWhere(int page, int rows, TbCarPartsCategory paramter)throws Exception;

}
