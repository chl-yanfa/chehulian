package com.car.app.carscraporder.web.controller;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.TbCommonContactsService;
import com.car.app.carscarporder.web.service.TbCommonPayeeaccountService;
import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.pojo.TbCommonContacts;
import com.car.app.carscraporder.pojo.TbCommonPayeeaccount;
import com.car.app.carscraporder.web.threadlocal.UserUtil;
import com.car.app.common.bean.PageResult;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.exception.DataException;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value="/commonPayee")
@Api(value = "commonPayee-API",tags={"常用收款账户"})
public class TbCommonPayeeaccountController {
	
	
	@Autowired
	private TbCommonPayeeaccountService TbCommonPayeeaccountService;
	
	
	/**
	 * 
	 * @param page
	 * @param rows
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取大客户分页列表",notes = "获取用户分页列表")
	public ResultBean<PageResult> getCompanyList(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			TbCommonPayeeaccount paramter, HttpServletRequest request) throws Exception{
		     
		    ClientBO user = UserUtil.getUser();
            if(user==null){
   	            throw new DataException("未登录系统");
             } 
            paramter.setClientid(user.getId());
		     return new ResultBean(TbCommonPayeeaccountService.queryPageListByWhere(page, rows, paramter));
		
		
	}

}
