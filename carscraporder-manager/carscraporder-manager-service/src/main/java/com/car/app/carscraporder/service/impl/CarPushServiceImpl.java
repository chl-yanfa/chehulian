package com.car.app.carscraporder.service.impl;

import com.car.app.carscraporder.mapper.CarPushMapper;
import com.car.app.carscraporder.pojo.CarPush;
import com.car.app.carscraporder.pojo.CarScrapInquiryPriceAttachment;
import com.car.app.carscraporder.service.CarPushService;
import com.car.app.carscraporder.service.CarScrapInquiryPriceAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarPushServiceImpl implements CarPushService {

    @Autowired
    CarPushMapper carPushMapper;

    @Override
    public List<CarPush> getAllCarPush() {
        return carPushMapper.getAllCarPush();
    }

    @Override
    public List<CarPush> queryListByWhere(CarPush carPush) {
        return carPushMapper.getOneCarPush(carPush.getId());
    }

    @Override
    public int updateByCarPush(CarPush carPush) {
        System.out.println("业务层对象:"+carPush);
        return carPushMapper.updateByCarPush(carPush);
    }
}
