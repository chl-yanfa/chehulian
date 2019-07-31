package com.car.app.carscraporder.mapper;

import com.car.app.carscraporder.pojo.CarPush;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarPushMapper extends Mapper<CarPush>{

    List<CarPush> getAllCarPush();

    List<CarPush> getOneCarPush(Integer id);

    int updateByCarPush(CarPush carPush);
}
