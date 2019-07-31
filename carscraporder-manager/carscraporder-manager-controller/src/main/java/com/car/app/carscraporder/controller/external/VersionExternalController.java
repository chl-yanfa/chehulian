package com.car.app.carscraporder.controller.external;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.bo.VersionBO;
import com.car.app.carscraporder.pojo.Version;
import com.car.app.carscraporder.service.VersionService;
import com.car.app.common.bean.ResultBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/external/version")
@Api(value = "External-problem-API", tags = { "提供外部调用的常见问题相关接口,(跳过登录校验,按照自定义鉴权逻辑)" })
public class VersionExternalController {

	@Autowired
	private VersionService versionService;

	
	/**
	 * 根据消息id获取消息详情
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getInfo", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取版本信息根据来源", notes = "获取版本信息根据来源")
	public ResultBean<VersionBO> get(@RequestParam(value="oc",required = true)Integer oc) throws Exception {
		if(oc!=null) {
			Version param =new Version();
			param.setOc(oc);
			Version queryOne = versionService.queryOne(param);
			if(queryOne != null) {
				VersionBO bo =new VersionBO();
				BeanUtils.copyProperties(queryOne, bo);
				return new ResultBean<VersionBO>(bo);							
			}
			return new ResultBean<VersionBO>();
		}
		return new ResultBean<VersionBO>();

	}
	
}
