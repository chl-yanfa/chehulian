package com.car.app.carscraporder.controller.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.service.CarScrapInquiryPriceService;
import com.car.app.carscraporder.vo.CarScrapInquiryPriceVO;
import com.car.app.common.bean.ResultBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/external/inquiryprice")
@Api(value = "External-problem-API", tags = { "提供外部调用的询价操作相关接口,(跳过登录校验,按照自定义鉴权逻辑)" })
public class CarScrapInquiryPriceExternalController {

	@Autowired
	private CarScrapInquiryPriceService carScrapInquiryPriceService;

	
	@RequestMapping(value = "/addInquiryprice",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "新增询价",notes = "新增询价")
	public ResultBean<Boolean> addInquiryprice(@RequestBody CarScrapInquiryPriceVO inquiryPriceVO,BindingResult bindingResult) throws Exception{
		return new ResultBean<Boolean>(carScrapInquiryPriceService.save(inquiryPriceVO)==1);
	}

}
