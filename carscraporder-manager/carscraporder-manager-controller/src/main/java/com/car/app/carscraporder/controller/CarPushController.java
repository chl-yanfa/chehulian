package com.car.app.carscraporder.controller;


import com.car.app.carscraporder.pojo.CarPush;
import com.car.app.carscraporder.service.CarPushService;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.exception.DataException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 *  宁润泽
 */
@Controller
@RequestMapping(value="/getmsg")
@Api(value = "Push-API",tags={"推送消息相关接口"} )
public class CarPushController {

	@Autowired
	CarPushService carPushService;

	/**
	 * 查询所有推送信息
	 * @return  ResultBean
	 * @throws Exception
	 */
	@RequestMapping(value="getPushMsg",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询推送消息",notes = "查询推送消息")
	public ResultBean<Boolean> getPushMsg() throws Exception{
		List<CarPush> carPushList = carPushService.getAllCarPush();
		return new ResultBean(ResultBean.SUCCESS,carPushList,"查询成功");
	}

	@RequestMapping(value = "{id}",method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "修改管理区域",notes = "修改管理区域")
	public ResultBean<Boolean> updateCarPush(@PathVariable("id")Integer id, @Validated CarPush push, BindingResult bindingResult) throws Exception{
		System.out.println("id为"+id);
//		User u = UserUtil.getUser();
//		if(u==null){
//			throw new UnloginException();
//		}
		CarPush carPush = new CarPush();
		carPush.setId(id);

		List<CarPush> carPushList = carPushService.queryListByWhere(carPush);
		System.out.println(carPushList.get(0)+"：宁润泽");
		if(carPushList==null){
			throw new DataException("数据不存在");
		}
		CarPush carPush2 = carPushList.get(0);
		push.setCreateTime(new Date());

		carPush2.setPushType(push.getPushType());
		carPush2.setNotificationTitle(push.getNotificationTitle());
		carPush2.setMsgTitle(push.getMsgTitle());
		carPush2.setMsgContent(push.getMsgContent());
		carPush2.setCreateTime(push.getCreateTime());

		return new ResultBean<>(carPushService.updateByCarPush(carPush2)==1);
//		areas.setOperator(u.getId());
//		areas.setCreater(data.getCreater());
//		areas.setCreatetime(data.getCreatetime());
//		areas.setStatus(data.getStatus());
//		areas.setIsVirtualField(data.getIsVirtualField());
//		return new ResultBean<>(areasService.update(areas)==1);
	}
}
