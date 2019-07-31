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

import com.car.app.carscraporder.app.service.TbAppBannerService;
import com.car.app.carscraporder.bo.TbAppBannerSimpleBO;
import com.car.app.carscraporder.pojo.TbAppBanner;
import com.car.app.common.bean.ResultBean;


@Controller
@RequestMapping(value="/banner")
@Api(value = "External-AppBanner-API",tags={"app的banner相关"} )
public class TbAppBannerController {
	
	@Autowired
	private TbAppBannerService  tbAppBannerService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取用户分页列表",notes = "获取用户分页列表")
	public ResultBean<List<TbAppBannerSimpleBO>> getAll(@RequestParam(value="type",required = true)String type) throws Exception{
		
		      List<TbAppBannerSimpleBO> result =  tbAppBannerService.getAll(type);
		
		
		     return new ResultBean<List<TbAppBannerSimpleBO>>(result);
		
		
	}

}
