package com.car.app.carscraporder.app.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.app.service.TbCarPartsCategoryAppService;
import com.car.app.carscraporder.pojo.TbCarPartsCategory;
import com.car.app.common.bean.ResultBean;

@Controller
@RequestMapping(value="/carPartsCategory")
@Api(value = "Cityes-API",tags={"汽车配件大类"})
public class TbCarPartsCategoryController {
	
	@Autowired
	private TbCarPartsCategoryAppService tbCarPartsCategoryAppService;
	
	
	@RequestMapping(value="/getAll",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取配件下拉框数据",notes = "退出系统")
	public ResultBean<List<TbCarPartsCategory>> getCarPartsCategory() throws Exception{
		
		List<TbCarPartsCategory> carPartsCategoryList = tbCarPartsCategoryAppService.getAll();
		
		
	
		return new ResultBean(carPartsCategoryList);
	}
	

}
