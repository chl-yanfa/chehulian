package com.car.app.carscraporder.mapper;

import com.car.app.carscraporder.pojo.TbCarPartsCategory;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbCarPartsCategoryMapper extends Mapper<TbCarPartsCategory> {

     List<TbCarPartsCategory> queryMyPageListByWhere(@Param("paramter")TbCarPartsCategory paramter)throws Exception;
}
