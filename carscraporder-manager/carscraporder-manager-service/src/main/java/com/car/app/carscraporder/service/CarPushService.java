package com.car.app.carscraporder.service;

import com.car.app.carscraporder.pojo.CarPush;


import java.util.List;

public interface CarPushService {

	List<CarPush> getAllCarPush();
	List<CarPush> queryListByWhere(CarPush carPush);
	int updateByCarPush(CarPush carPush);
}
