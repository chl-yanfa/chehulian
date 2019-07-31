package com.car.app.carscraporder.mapper;

import com.car.app.carscraporder.pojo.ChlCarModelSeries;
import com.car.app.carscraporder.pojo.TbCarSystem;

import java.util.List;

public interface ChlCarModelSeriesMapper {
    List<ChlCarModelSeries> selectChlCarModelSeries(TbCarSystem tbCarSystem);
    List<String> selectCategory(String brandId);
}
