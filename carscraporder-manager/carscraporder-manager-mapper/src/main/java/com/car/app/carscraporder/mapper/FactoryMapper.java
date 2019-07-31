package com.car.app.carscraporder.mapper;

import com.car.app.carscraporder.bo.FactoryQueryBO;
import com.car.app.carscraporder.pojo.CarPush;
import com.car.app.carscraporder.pojo.Factory;
import com.github.abel533.mapper.Mapper;

import java.util.List;

public interface FactoryMapper extends Mapper<Factory>{
    List<FactoryQueryBO> getAllFactory();
}
