package com.car.app.carscraporder.controller.external;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.bo.PaimaiBO;
import com.car.app.carscraporder.service.PaimaiService;
import com.car.app.common.bean.ResultBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/external/paimai")
@Api(value = "External-Paimai-API", tags = { "提供外部调用的拍卖相关接口,(跳过登录校验,按照自定义鉴权逻辑)" })
public class PaimaiExternalController {

	@Autowired
	private PaimaiService paimaiService;

	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取列表", notes = "获取列表")
	public ResultBean<List<PaimaiBO>> getList() throws Exception {
		List<PaimaiBO> queryList = paimaiService.queryList();
		return new ResultBean<List<PaimaiBO>>(queryList);

	}


}
