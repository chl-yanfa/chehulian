package com.car.app.carscraporder.mapper;

import com.car.app.carscraporder.pojo.ChlCarModel;

import java.util.List;

public interface ChlCarModelMapper {
    //查询车型（参数：车系id）
    List<ChlCarModel> selectCarModelSerid(Integer serid);
}
