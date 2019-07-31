package com.car.app.carscraporder.mapper;

import com.car.app.carscraporder.pojo.TbCarParts;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbCarPartsMapper extends Mapper<TbCarParts> {

    List<TbCarParts> queryByIdPageListByWhere(@Param("paramter")TbCarParts paramter)throws Exception;
}
