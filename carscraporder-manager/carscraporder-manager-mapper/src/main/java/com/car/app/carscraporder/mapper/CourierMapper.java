package com.car.app.carscraporder.mapper;

import com.car.app.carscraporder.pojo.TbCarCourier;
import com.github.abel533.mapper.Mapper;

import java.util.List;

public interface CourierMapper extends Mapper<TbCarCourier>{
    List<TbCarCourier> getAllCourier();
}
