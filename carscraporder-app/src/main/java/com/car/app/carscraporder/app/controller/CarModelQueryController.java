package com.car.app.carscraporder.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.app.service.CarModelQueryAppService;
import com.car.app.common.bean.ResultBean;

@Controller
@RequestMapping(value="/carModel")
@Api(value = "CARMODEL-API",tags={"力洋接口API"})
public class CarModelQueryController {

	
	@Autowired
	private CarModelQueryAppService  carModelQueryAppService;
	

	
    @RequestMapping(value="{vin}",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据车架后查询车辆信息",notes = "根据车架后查询车辆信息")
	public ResultBean<String> queryCarModelData(@PathVariable("vin")String vin) throws Exception{
    	
    	
    		return new ResultBean(carModelQueryAppService.queryCarModelData(vin));
    	
    	
		
	}
}
