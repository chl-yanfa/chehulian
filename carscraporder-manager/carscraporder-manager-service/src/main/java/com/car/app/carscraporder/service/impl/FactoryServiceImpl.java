package com.car.app.carscraporder.service.impl;

import com.car.app.carscraporder.bo.FactoryQueryBO;
import com.car.app.carscraporder.mapper.CarPushMapper;
import com.car.app.carscraporder.mapper.FactoryMapper;
import com.car.app.carscraporder.pojo.CarPush;
import com.car.app.carscraporder.service.CarPushService;
import com.car.app.carscraporder.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    FactoryMapper factoryMapper;

    @Override
    public List<FactoryQueryBO> getAllFactory() {
        return factoryMapper.getAllFactory();
    }

}
