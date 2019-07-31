package com.car.app.carscraporder.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.CarScrapOrderService;
import com.car.app.carscraporder.bo.CarScrapOrderBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.bo.OrderAduitBO;
import com.car.app.carscraporder.pojo.CarScrapOrder;
import com.car.app.carscraporder.vo.CarScrapOrderConfirmAmountVO;
import com.car.app.carscraporder.vo.CarScrapOrderExternalVO;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.carscraporder.vo.CarScrapOrderVO;
import com.car.app.carscraporder.web.bean.ClientUser;
import com.car.app.carscraporder.web.threadlocal.UserUtil;
import com.car.app.carscraporder.web.util.HttpRequestParamConConverter;
import com.car.app.common.bean.PageResult;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.exception.DataException;


@Controller
@RequestMapping(value="/order")
@Api(value = "Order-API",description = "订单相关接口文档")
public class CarScrapOrderController {
	
	
	private Logger logger = LoggerFactory.getLogger(CarScrapOrderController.class);
	
	
	@Autowired
	private CarScrapOrderService  carScrapOrderService;
	
	


	
	/**
	 * 订单分页查询
	 * @param page
	 * @param rows
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取订单分页列表",notes = "获取用户分页列表")
	public ResultBean<PageResult<CarScrapOrderPageBO>> getOrderList(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception{
		
		      ClientBO user = UserUtil.getUser();
	          if(user==null){
	    	        throw new DataException("未登录系统");
	           }
		     
	         paramter.setClientId(user.getId());
		     PageResult<CarScrapOrderPageBO> pageData = carScrapOrderService.getOrderPageListByKeyword(page, rows, paramter);
		     
		     return new ResultBean<PageResult<CarScrapOrderPageBO>>(pageData);
		
		
	}
	
	
	
	/**
	 * 新增订单
	 * @param carScrapOrderVO 页面传递订单数据
	 * @return
	 * @throws Exception 
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "新增订单")
	public ResultBean<Boolean> addOrder(@RequestBody CarScrapOrderExternalVO carScrapOrderVO,BindingResult bindingResult) throws Exception{
		
		ClientBO user = UserUtil.getUser();
	     if(user==null){
	    	 throw new DataException("未登录系统");
	     }
	     
	     if(user.getUserType()=="2"){
	    	 if(user.getAreaid()!=null){
	    		 carScrapOrderVO.setOrderAreasId(user.getAreaid());
	    	 }
	    	
	     }
	    
	     carScrapOrderVO.setCreater(user.getId());
	     carScrapOrderVO.setClientId(user.getId());
	     
	     //判断登录人是个人用户还是大客户	 
	     carScrapOrderVO.setClientType(user.getUserType());
	    
	     
	     
		return new ResultBean<Boolean>(carScrapOrderService.addOrder(carScrapOrderVO));
		
	}
	
	
	/**
	 * 根据订单id删除订单
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "{id}",method = RequestMethod.DELETE)
	@ResponseBody
	@ApiOperation(value = "根据ids删除订单",notes = "根据ids删除订单")
	public ResultBean<Boolean> deleteOrderById(
			@ApiParam(name = "id", value = "订单id",required = true)
			@PathVariable("id")String id,
			HttpServletRequest request) throws Exception{

		ClientBO user = UserUtil.getUser();
	     if(user == null){
	    	 throw new DataException("未登录系统");
	     }
	     
	     
		return new ResultBean<Boolean>(carScrapOrderService.deleteOrderById(id,user.getId()));

		
	}

	
	/**
	 * 根据订单id获取订单详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "{id}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取订单详情",notes = "根据id获取User详情")
	public ResultBean<CarScrapOrderBO> getOrderById(@PathVariable("id")String id) throws Exception{
		return new ResultBean<CarScrapOrderBO>(carScrapOrderService.getOrderById(id));
		
	}
	
	/**
	 * 根据id更新订单信息
	 * @param id
	 * @param u
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "{id}",method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "修改订单",notes = "根据id修改订单信息")
	public ResultBean<Boolean> updateOrder(@PathVariable("id")String id,@Validated CarScrapOrderVO carScrapOrderVo,BindingResult bindingResult,HttpServletRequest request) throws Exception{
		ClientBO user = UserUtil.getUser();
	     if(user == null){
	    	 throw new DataException("未登录系统");
	     }
		
	     Map<String,String> paramMap = HttpRequestParamConConverter.getParameterMap(request);
	     
	    
	     paramMap.put("operator", user.getId());
	     
		return new ResultBean<Boolean>(carScrapOrderService.updateOrder(id,paramMap));
		
	}
	
	@RequestMapping(value = "getOrderAuditInfo/{id}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取订单审核进度详情",notes = "根据id获取订单审核进度详情")
	public ResultBean<OrderAduitBO> getOrderAuditInfo(@PathVariable("id")String id) throws Exception{
		return new ResultBean<OrderAduitBO>(carScrapOrderService.getOrderAuditInfo(id));
		
	}
	/**
	 * 根据订单id取消订单
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "cancelOrder", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "根据订单id取消订单", notes = "根据订单id取消订单")
	public ResultBean<Boolean> cancelOrder(
			@ApiParam(name = "id", value = "订单id", required = true) @RequestParam(required = true) String id,
			@RequestParam(required = false) String cancelMemo) throws Exception {

		ClientBO user = UserUtil.getUser();
		if (user == null) {
			throw new DataException("未登录系统");
		}
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("id", id);
		paramMap.put("cancelMemo", cancelMemo);
		paramMap.put("operator", user.getId());
		return new ResultBean<Boolean>(carScrapOrderService.cancelOrderById(paramMap));

	}
	
	/**
	 * confirmAmount 确认报价
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "confirmAmount", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "根据订单id确认报价", notes = "根据订单id确认报价")
	public ResultBean<Boolean> confirmAmount(@RequestBody CarScrapOrderConfirmAmountVO confirmAmountVO,BindingResult bindingResult) throws Exception {

		ClientBO user = UserUtil.getUser();
		if (user == null) {
			throw new DataException("未登录系统");
		}
		confirmAmountVO.setOperator(user.getId());
		return new ResultBean<Boolean>(carScrapOrderService.confirmAmount(confirmAmountVO));
	}
	
	
	


}
