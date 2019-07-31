package com.car.app.carscraporder.mapper;

import com.car.app.carscraporder.pojo.TbCarPartsCategory;

import java.util.List;
import java.util.Map;

/**
 * @author ningrz
 * @version 1.0
 * @date 2019/7/15 16:27
 */
public interface TbCarPartsCategoryByChlMapper {

    List<TbCarPartsCategory> queryPageListByWhere(Map<String,Object> data);
}
