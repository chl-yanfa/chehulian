package com.car.app.carscraporder.controller.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.bo.ClientMessageBO;
import com.car.app.carscraporder.service.ClientMessageService;
import com.car.app.common.bean.PageResult;
import com.car.app.common.bean.ResultBean;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/external/message")
@Api(value = "External-message-API", tags = { "提供外部调用的消息相关接口,(跳过登录校验,按照自定义鉴权逻辑)" })
public class ClientMessageExternalController {

	@Autowired
	private ClientMessageService clientMessageService;

	/**
	 * 根据客户id获取消息未读总条数
	 * 
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMessageUnReadTotal/{clientId}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据客户id获取消息未读总条数", notes = "根据客户id获取消息未读总条数")
	public ResultBean<Integer> getMessageUnReadTotal(@PathVariable("clientId") String clientId) throws Exception {
		return new ResultBean<Integer>(clientMessageService.queryUnReadTotal(clientId));

	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取消息分页列表", notes = "获取消息分页列表")
	public ResultBean<PageResult<ClientMessageBO>> getMessageList(
			@ApiParam(name = "page", value = "分页参数页码", required = true) @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数", required = true) @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows)
			throws Exception {

		PageResult<ClientMessageBO> pageResult = null;
		PageInfo<ClientMessageBO> pageInfo = clientMessageService.queryPageList(page, rows);
		if (pageInfo != null) {
			pageResult = new PageResult<ClientMessageBO>();
			pageResult.setRows(pageInfo.getList());
			pageResult.setTotal(pageInfo.getTotal());
		}

		return new ResultBean<PageResult<ClientMessageBO>>(pageResult);

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
	public ResultBean<ClientMessageBO> Message(@PathVariable("id") String id) throws Exception {
		return new ResultBean<ClientMessageBO>(clientMessageService.queryBOById(id));

	}
	/**
	 * 更新消息为已读状态
	 * @param id
	 * @param u
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/changeIsread",method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "修改消息",notes = "根据id修改消息信息")
	public ResultBean<Integer> changeIsread(@RequestParam(required = true)String id) throws Exception{
		 return new ResultBean<Integer>(clientMessageService.changeIsread(id));
		
	}

}
