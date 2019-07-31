package com.car.app.carscraporder.service.impl;

import com.car.app.carscraporder.mapper.ChlAutoLogosMapper;
import com.car.app.carscraporder.mapper.ChlCarModelMapper;
import com.car.app.carscraporder.mapper.ChlCarModelSeriesMapper;
import com.car.app.carscraporder.pojo.ChlAutoLogos;
import com.car.app.carscraporder.pojo.ChlCarModel;
import com.car.app.carscraporder.pojo.ChlCarModelSeries;
import com.car.app.carscraporder.pojo.TbCarSystem;
import com.car.app.carscraporder.service.ChlAutoLogosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChlAutoLogosServiceImpl implements ChlAutoLogosService {
    @Autowired
    private ChlAutoLogosMapper chlAutoLogosMapper;
    @Autowired
    private ChlCarModelSeriesMapper chlCarModelSeriesMapper;
    @Autowired
    private ChlCarModelMapper chlCarModelMapper;
    @Override
    public List<ChlAutoLogos> selectChlAutoLogos() {
        return chlAutoLogosMapper.selectChlAutoLogos();
    }

    @Override
    public List<ChlCarModelSeries> selectChlCarModelSeries(TbCarSystem tbCarSystem) {
        return chlCarModelSeriesMapper.selectChlCarModelSeries(tbCarSystem);
    }

    @Override
    public List<String> selectCategory(String brandId) {
        return chlCarModelSeriesMapper.selectCategory(brandId);
    }

    @Override
    public List<ChlCarModel> selectCarModelSerid(Integer serid) {
        return chlCarModelMapper.selectCarModelSerid(serid);
    }


}
