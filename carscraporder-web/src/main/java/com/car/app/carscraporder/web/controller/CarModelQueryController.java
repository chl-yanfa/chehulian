package com.car.app.carscraporder.web.controller;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.CarModelQueryService;
import com.car.app.common.bean.ResultBean;



@Controller
@RequestMapping(value="/carModel")
@Api(value = "CARMODEL-API",tags={"力洋接口API"})
public class CarModelQueryController {
	
	@Autowired
	private CarModelQueryService  carModelQueryService;
	

	
    @RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据车架后查询车辆信息",notes = "根据车架后查询车辆信息")
	public ResultBean<String> queryCarModelData(@RequestParam(value="vin",required = true) String vin) throws Exception{
    	
    	
    		return new ResultBean(carModelQueryService.queryCarModelData(vin));
    	
    	
		
	}

}
