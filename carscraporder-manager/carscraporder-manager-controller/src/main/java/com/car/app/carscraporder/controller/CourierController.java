package com.car.app.carscraporder.controller;

import com.car.app.carscraporder.pojo.TbCarCourier;
import com.car.app.carscraporder.service.CourierService;
import com.car.app.common.bean.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *  宁润泽
 */
@Controller
@RequestMapping(value="/getCourier")
@Api(value = "Courier-API",tags={"快递信息接口"} )
public class CourierController {

	@Autowired
	CourierService courierService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取所有快递公司",notes = "获取所有快递公司")
	public ResultBean<List<TbCarCourier>> getAllCourier() throws Exception{
		return new ResultBean<List<TbCarCourier>>(courierService.getAllCourier());
	}
}
