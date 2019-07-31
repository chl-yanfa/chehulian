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

import com.car.app.carscraporder.app.service.TbDataDicService;
import com.car.app.carscraporder.pojo.TbDataDic;
import com.car.app.common.bean.ResultBean;



@Controller
@RequestMapping(value="/dic")
@Api(value = "dic",tags={"字典"})
public class DataDicController {
	
	
	@Autowired
	private TbDataDicService  tbDataDicService;
	
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据类型获取字典列表",notes = "根据类型获取字典列表")
	public ResultBean<List<TbDataDic>> getCompanyList(
			@RequestParam(value="dataType",required = true)String dataType) throws Exception{
		
		
		
		
		
		        
     return new ResultBean(tbDataDicService.getDicByDataType(dataType));
		
		
	}

}
