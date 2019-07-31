package com.car.app.carscraporder.controller;


import com.car.app.carscraporder.bo.FactoryQueryBO;
import com.car.app.carscraporder.pojo.CarPush;
import com.car.app.carscraporder.service.CarPushService;
import com.car.app.carscraporder.service.FactoryService;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.exception.DataException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 *  宁润泽
 */
@Controller
@RequestMapping(value="/getFactory")
@Api(value = "Factory-API",tags={"解体厂信息接口"} )
public class FactoryController {

	@Autowired
	FactoryService factoryService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取所有解体厂",notes = "获取所有解体厂")
	public ResultBean<List<FactoryQueryBO>> getAllFactory() throws Exception{
		return new ResultBean<List<FactoryQueryBO>>(factoryService.getAllFactory());
	}
}
