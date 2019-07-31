package com.car.app.carscraporder.applet.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.AreasService;
import com.car.app.carscraporder.pojo.Areas;
import com.car.app.common.bean.ResultBean;

@Controller
@RequestMapping(value="/userarea")
@Api(value = "Area-API",tags={"用户区域管理相关接口"})
public class AreasController {
	
	@Autowired
	private AreasService areasService;
	
	
	
	
	@RequestMapping(value = "{id}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取区域详情",notes = "根据id获取区域详情")
	public ResultBean<Areas> getAreasById(@PathVariable("id")Integer id) throws Exception{
		return new ResultBean<Areas>(areasService.queryById(id));

	}
	
	/**
	 * @param carScrapType 1代表旧件模块，2代表整车模块
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取区域列表",notes = "获取区域列表")
	public ResultBean<List<Areas>> getAll(@RequestParam(value="carScrapType",required=true) String carScrapType) throws Exception{
		return new ResultBean<List<Areas>>(areasService.getAllAreas());

	}

}
