package com.car.app.carscraporder.app.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.app.service.TbCarPartsAppService;
import com.car.app.carscraporder.pojo.TbCarParts;
import com.car.app.common.bean.ResultBean;

@Controller
@RequestMapping(value="/carParts")
@Api(value = "Cityes-API",tags={"汽车配件名称"})
public class TbCarPartsController {
	
	
	@Autowired
	private TbCarPartsAppService tbCarPartsAppService;
	
	
	@RequestMapping(value="getByCategoryId",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取配件下拉框数据",notes = "退出系统")
	public ResultBean<List<TbCarParts>> getCarPartsByCategoryId(@RequestParam(value="categoryId",required = true)Integer categoryId) throws Exception{
		
	
		List<TbCarParts> carPartsList = tbCarPartsAppService.getAllByCategory(categoryId);
		return new ResultBean(carPartsList);
		
	}

}
