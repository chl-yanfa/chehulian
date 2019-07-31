package com.car.app.carscraporder.applet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.CarScrapInquiryPriceService;
import com.car.app.carscraporder.vo.CarScrapInquiryPriceVO;
import com.car.app.common.bean.ResultBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/inquiryprice")
@Api(value = "INQUIRYPRICE-API", description = "询价相关接口文档")
public class CarScrapInquiryPriceController {

	@Autowired
	private CarScrapInquiryPriceService carScrapInquiryPriceService;

	/**
	 * 新增询价
	 * 
	 * @param inquiryPriceVO 页面传递询价数据
	 * @return
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/addInquiryprice",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "新增询价")
	public ResultBean<Boolean> addOrder(@RequestBody CarScrapInquiryPriceVO inquiryPriceVO, BindingResult bindingResult)
			throws Exception {

//		ClientBO user = UserUtil.getUser();
//		if (user == null) {
//			throw new DataException("未登录系统");
//		}

//		inquiryPriceVO.setCreater(user.getId());
//		inquiryPriceVO.setOperator(user.getId());

		return new ResultBean<Boolean>(carScrapInquiryPriceService.addInquiryPrice(inquiryPriceVO));

	}
}
