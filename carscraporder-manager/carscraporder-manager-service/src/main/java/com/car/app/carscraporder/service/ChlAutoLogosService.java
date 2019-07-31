package com.car.app.carscraporder.service;

import com.car.app.carscraporder.pojo.ChlAutoLogos;
import com.car.app.carscraporder.pojo.ChlCarModel;
import com.car.app.carscraporder.pojo.ChlCarModelSeries;
import com.car.app.carscraporder.pojo.TbCarSystem;

import java.util.List;

public interface ChlAutoLogosService {
    List<ChlAutoLogos> selectChlAutoLogos();
    List<ChlCarModelSeries> selectChlCarModelSeries(TbCarSystem tbCarSystem);
    List<String> selectCategory(String brandId);
    //查询车型（参数：车系id）
    List<ChlCarModel> selectCarModelSerid(Integer serid);
}
