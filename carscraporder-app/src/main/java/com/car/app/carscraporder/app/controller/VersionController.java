package com.car.app.carscraporder.app.controller;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.VersionService;
import com.car.app.carscraporder.bo.VersionBO;
import com.car.app.common.bean.ResultBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping(value="/version")
@Api(value = "Order-API",description = "版本号相关接口文档")
public class VersionController {
	
	
	private Logger logger = LoggerFactory.getLogger(VersionController.class);
	
	
	@Autowired
	private  VersionService  versionService;
	
	/**
	 * 根据OC获取版本详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getVersion",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据OC获取版本详情",notes = "根据OC获取版本详情")
	public ResultBean<VersionBO> getVersion(@RequestParam(value = "oc", required = true) Integer oc) throws Exception{
		return new ResultBean<VersionBO>(versionService.getVersionInfo(oc));
		
	}
	
}
