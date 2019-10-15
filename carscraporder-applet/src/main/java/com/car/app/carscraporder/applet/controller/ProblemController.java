package com.car.app.carscraporder.applet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.ProblemService;
import com.car.app.carscraporder.applet.threadlocal.UserUtil;
import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.bo.ClientProblemBO;
import com.car.app.common.bean.PageResult;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.exception.DataException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Controller
@RequestMapping(value="/problem")
@Api(value = "Order-API",description = "常见问题相关接口文档")
public class ProblemController {
	
	@Autowired
	private ProblemService  problemService;
	
	
	/**
	 * 消息分页查询
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取常见问题分页列表", notes = "获取常见问题分页列表")
	public ResultBean<PageResult<ClientProblemBO>> getProblemPageList(
			@ApiParam(name = "page", value = "分页参数页码", required = true) @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数", required = true) @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows)
			throws Exception {

		ClientBO user = UserUtil.getUser();
		if (user == null) {
			throw new DataException("未登录系统");
		}
		PageResult<ClientProblemBO> pageData = problemService.getProblemPageList(page, rows);
		return new ResultBean<>(pageData);
	}

	/**
	 * 根据id获取常见问题详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "{id}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取常见问题详情",notes = "根据id获取常见问题详情")
	public ResultBean<ClientProblemBO> getProblemById(@PathVariable("id")String id) throws Exception{
		return new ResultBean<ClientProblemBO>(problemService.getProblemById(id));
		
	}
	
	
	


}
