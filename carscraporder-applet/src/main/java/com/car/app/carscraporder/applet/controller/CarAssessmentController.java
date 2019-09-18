package com.car.app.carscraporder.applet.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.CarAssessmentService;
import com.car.app.common.bean.ResultBean;

@Controller
@RequestMapping(value="/carAssessment")
@Api(value = "carAssessment-API",tags={"车辆估值相关"})
public class CarAssessmentController {
	@Autowired
	private CarAssessmentService  carAssessmentService;
	
	    @RequestMapping(value="getCarPrice",method = RequestMethod.GET)
		@ResponseBody
		@ApiOperation(value = "根据车架后查询车辆估值",notes = "根据车架后查询车辆估值")
		public ResultBean<String> getCarPrice(@RequestParam(name="vin",required=true)String vin,@RequestParam(name="areaName",required=false)String areaName) throws Exception{
	    	return new ResultBean(carAssessmentService.getPrice(vin, areaName));
	 }
}