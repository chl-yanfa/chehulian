package com.car.app.carscraporder.applet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.CarScrapOrderDealService;
import com.car.app.carscraporder.bo.CarScrapOrderDealBO;
import com.car.app.common.bean.ResultBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/orderdeal")
@Api(value = "OrderDeal-API",description = "订单相关接口文档")
public class CarScrapOrderDealController {

	@Autowired
	private CarScrapOrderDealService carScrapOrderDealService;

	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取列表", notes = "获取列表")
	public ResultBean<List<CarScrapOrderDealBO>> getList() throws Exception {
		List<CarScrapOrderDealBO> queryList = carScrapOrderDealService.queryList();
		return new ResultBean<List<CarScrapOrderDealBO>>(queryList);

	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取订单详情", notes = "根据id获取User详情")
	public ResultBean<CarScrapOrderDealBO> getOrderDealById(@PathVariable("id") String id) throws Exception {
		return new ResultBean<CarScrapOrderDealBO>(carScrapOrderDealService.queryBOById(id));

	}

}
