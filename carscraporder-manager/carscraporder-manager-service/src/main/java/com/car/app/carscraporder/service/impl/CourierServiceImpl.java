package com.car.app.carscraporder.service.impl;


import com.car.app.carscraporder.mapper.CourierMapper;
import com.car.app.carscraporder.pojo.TbCarCourier;
import com.car.app.carscraporder.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    CourierMapper courierMapper;

    @Override
    public List<TbCarCourier> getAllCourier() {
        return courierMapper.getAllCourier();
    }
}
