package com.car.app.carscraporder.applet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.PaimaiService;
import com.car.app.carscraporder.bo.PaimaiBO;
import com.car.app.common.bean.ResultBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/paimai")
@Api(value = "Paimai-API",description = "拍卖会相关接口文档")
public class PaimaiController {

	@Autowired
	private PaimaiService paimaiService;

	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取列表", notes = "获取列表")
	public ResultBean<List<PaimaiBO>> getList() throws Exception {
		List<PaimaiBO> queryList = paimaiService.queryList();
		return new ResultBean<>(queryList);
	}
}
