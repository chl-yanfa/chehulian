package com.car.app.carscraporder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.bo.CarScrapInquiryPriceBO;
import com.car.app.carscraporder.exception.UnloginException;
import com.car.app.carscraporder.pojo.CarScrapInquiryPrice;
import com.car.app.carscraporder.pojo.User;
import com.car.app.carscraporder.service.CarScrapInquiryPriceService;
import com.car.app.carscraporder.util.UserUtil;
import com.car.app.carscraporder.vo.CarScrapInquiryPricePageVO;
import com.car.app.common.bean.ResultBean;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/inquiryprice")
@Api(value = "Inquiryprice-API", tags = { "询价操作相关接口" })
public class CarScrapInquiryPriceController {

	@Autowired
	private CarScrapInquiryPriceService carScrapInquiryPriceService;

	
	@RequestMapping(value = "list",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取分页列表",notes = "获取分页列表" )
	public ResultBean<PageInfo<CarScrapInquiryPriceBO>> getList(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapInquiryPricePageVO paramter) throws Exception{
		return new ResultBean<PageInfo<CarScrapInquiryPriceBO>>(carScrapInquiryPriceService.queryPageListByWhere(page, rows, paramter));
	}
	@RequestMapping(value = "read/{id}",method = RequestMethod.PUT)
	@ResponseBody
	public ResultBean<Boolean> read(@PathVariable("id")String id) throws Exception{
		User user = UserUtil.getUser();
		if(user==null){
			throw new UnloginException();
		}
		CarScrapInquiryPrice record = new CarScrapInquiryPrice();
		record.setId(id);
		CarScrapInquiryPrice queryOne = carScrapInquiryPriceService.queryOne(record);
		if(queryOne!=null && queryOne.getStatus()==0) {
			queryOne.setOperator(user.getId());
			queryOne.setStatus(1);
			carScrapInquiryPriceService.updateSelective(queryOne);
		}
		return new ResultBean<Boolean>(true);
	}

}
