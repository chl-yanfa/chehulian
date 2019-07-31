package com.car.app.carscraporder.controller.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.bo.ClientProblemBO;
import com.car.app.carscraporder.service.ClientProblemService;
import com.car.app.common.bean.PageResult;
import com.car.app.common.bean.ResultBean;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/external/problem")
@Api(value = "External-problem-API", tags = { "提供外部调用的常见问题相关接口,(跳过登录校验,按照自定义鉴权逻辑)" })
public class ClientProblemExternalController {

	@Autowired
	private ClientProblemService clientProblemService;

	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取常见问题分页列表", notes = "获取常见问题分页列表")
	public ResultBean<PageResult<ClientProblemBO>> getMessageList(
			@ApiParam(name = "page", value = "分页参数页码", required = true) @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数", required = true) @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows)
			throws Exception {

		PageResult<ClientProblemBO> pageResult = null;
		PageInfo<ClientProblemBO> pageInfo = clientProblemService.queryPageList(page, rows);
		if (pageInfo != null) {
			pageResult = new PageResult<ClientProblemBO>();
			pageResult.setRows(pageInfo.getList());
			pageResult.setTotal(pageInfo.getTotal());
		}

		return new ResultBean<PageResult<ClientProblemBO>>(pageResult);

	}

	/**
	 * 根据消息id获取消息详情
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取消息详情", notes = "根据id获取消息详情")
	public ResultBean<ClientProblemBO> Message(@PathVariable("id") String id) throws Exception {
		return new ResultBean<ClientProblemBO>(clientProblemService.queryBOById(id));

	}

}
