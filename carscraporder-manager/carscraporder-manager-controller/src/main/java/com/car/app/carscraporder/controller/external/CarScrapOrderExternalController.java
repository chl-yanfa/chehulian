package com.car.app.carscraporder.controller.external;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.car.app.carscraporder.bo.*;
import com.car.app.carscraporder.exception.UnloginException;
import com.car.app.carscraporder.mapper.UserMapper;
import com.car.app.carscraporder.pojo.CarPush;
import com.car.app.carscraporder.pojo.User;
import com.car.app.carscraporder.service.CarPushService;
import com.car.app.carscraporder.util.*;
import com.car.app.carscraporder.vo.*;
import com.car.app.common.exception.DataException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.http.DataConverter;
import com.car.app.carscraporder.pojo.CarScrapOrder;
import com.car.app.carscraporder.service.CarScrapOrderService;
import com.car.app.carscraporder.systemparameter.CommonSystemParamter;
import com.car.app.common.bean.PageResult;
import com.car.app.common.bean.ResultBean;
import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value="/external/order")
@Api(value = "External-Order-API",tags={"提供外部调用的订单相关接口,(跳过登录校验,按照自定义鉴权逻辑)"} )
public class CarScrapOrderExternalController {
	
	@Autowired
	private CarScrapOrderService  carScrapOrderService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	CarPushService carPushService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取用户分页列表",notes = "获取用户分页列表")
	public ResultBean<PageResult<CarScrapOrderPageBO>> getOrderList(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception{
			    PageResult<CarScrapOrderPageBO> pageResult = null;
				PageInfo<CarScrapOrderPageBO> pageInfo = null;
			    if((paramter.getOrderStatus().equals("1")||paramter.getOrderStatus()=="1")){
					 pageInfo =  carScrapOrderService.queryPageListByKeywordByChl(page, rows, paramter);
				}else{
					 pageInfo =  carScrapOrderService.queryPageListByKeyword(page, rows, paramter);
				}
				if(pageInfo!=null){
					pageResult  = new PageResult<CarScrapOrderPageBO>();
					pageResult.setRows(pageInfo.getList());
					pageResult.setTotal(pageInfo.getTotal());
				}
		     return new ResultBean<PageResult<CarScrapOrderPageBO>>(pageResult);
	}



	@RequestMapping(value="/examine",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取二次报价待审核",notes = "获取二次报价待审核")
	public ResultBean<PageResult<CarScrapOrderPageBO>> getOrderListByExamine(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception{

		PageResult<CarScrapOrderPageBO> pageResult = null;
		PageInfo<CarScrapOrderPageBO> pageInfo =  carScrapOrderService.queryPageListByKeywordByExamine(page, rows, paramter);
		if(pageInfo!=null){
			pageResult  = new PageResult<CarScrapOrderPageBO>();
			pageResult.setRows(pageInfo.getList());
			pageResult.setTotal(pageInfo.getTotal());
		}
		return new ResultBean<PageResult<CarScrapOrderPageBO>>(pageResult);
	}

	@RequestMapping(value="/examineok",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取二次报价已审核",notes = "获取二次报价已审核")
	public ResultBean<PageResult<CarScrapOrderPageBO>> getOrderListByExamineOK(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception{

		PageResult<CarScrapOrderPageBO> pageResult = null;
		PageInfo<CarScrapOrderPageBO> pageInfo =  carScrapOrderService.queryPageListByKeywordByExamineOK(page, rows, paramter);
		if(pageInfo!=null){
			pageResult  = new PageResult<CarScrapOrderPageBO>();
			pageResult.setRows(pageInfo.getList());
			pageResult.setTotal(pageInfo.getTotal());
		}
		return new ResultBean<PageResult<CarScrapOrderPageBO>>(pageResult);
	}


	@RequestMapping(value="/cancel",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取二次报价已取消",notes = "获取二次报价已取消")
	public ResultBean<PageResult<CarScrapOrderPageBO>> getOrderListByCancel(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception{

		PageResult<CarScrapOrderPageBO> pageResult = null;
		PageInfo<CarScrapOrderPageBO> pageInfo =  carScrapOrderService.queryPageListByKeywordByCancel(page, rows, paramter);
		if(pageInfo!=null){
			pageResult  = new PageResult<CarScrapOrderPageBO>();
			pageResult.setRows(pageInfo.getList());
			pageResult.setTotal(pageInfo.getTotal());
		}
		return new ResultBean<PageResult<CarScrapOrderPageBO>>(pageResult);
	}


	
	@RequestMapping(value="/history",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取订单历史分页列表",notes = "获取订单历史分页列表")
	public ResultBean<PageResult> getOrderHistoryList(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception{
		         
			    PageResult pageResult  = new PageResult();
			    if(paramter.getOrderStatus().equals("2")||paramter.getOrderStatus()=="2"){
					PageInfo<CarScrapOrderPageBO> pageInfo =  carScrapOrderService.queryPageHistoryListByKeywordByChl(page, rows, paramter);
					pageResult.setRows(pageInfo.getList());
					pageResult.setTotal(pageInfo.getTotal());
				}else{
					PageInfo<CarScrapOrderPageBO> pageInfo =  carScrapOrderService.queryPageHistoryListByKeyword(page, rows, paramter);
					pageResult.setRows(pageInfo.getList());
					pageResult.setTotal(pageInfo.getTotal());
				}
		     	return new ResultBean(pageResult);
	}
	
	@RequestMapping(value="/getAll",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取所有操作的订单和待处理的订单",notes = "获取所有操作的订单和待处理的订单")
	public ResultBean<PageResult> getAll(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception{

		    System.out.println("zyybz_"+paramter);

			PageResult pageResult  = new PageResult();
			System.out.println("订单状态为1:"+paramter.getOrderStatus());
			if((paramter.getOrderStatus()==null || paramter.getOrderStatus().equals("")) || (paramter.getOrderStatus().equals("1")||paramter.getOrderStatus()=="1") || (paramter.getOrderStatus().equals("2")||paramter.getOrderStatus()=="2")){
				PageInfo<CarScrapOrderPageBO> pageInfo =  carScrapOrderService.queryAllByChl(page, rows, paramter);
				pageResult.setRows(pageInfo.getList());
				pageResult.setTotal(pageInfo.getTotal());
			}else{
				System.out.println("订单状态为2:"+paramter.getOrderStatus());
				PageInfo<CarScrapOrderPageBO> pageInfo =  carScrapOrderService.queryAll(page, rows, paramter);
				pageResult.setRows(pageInfo.getList());
				pageResult.setTotal(pageInfo.getTotal());
			}
		 return new ResultBean(pageResult);
	}

	//获得所有的推送消息
	public List<CarPush> getCarPush(){
		List<CarPush> carPushList = carPushService.getAllCarPush();
		return carPushList;
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
		//return new ResultBean<Boolean>(carScrapOrderService.save(carScrapOrderVO)==1);
		int result = carScrapOrderService.save(carScrapOrderVO);
		if(result==1){
			//订单新增完毕后通知所有的业务员
			UserVO userParam = new UserVO();
			List<UserBO> listData = userMapper.queryPageUser(userParam);
			List<CarPush> carPushlist = getCarPush();
			CarPush carPush = carPushlist.get(0);
			System.out.println("执行业务员通知_APP");
			for (int i=0;i<listData.size();i++){
				System.out.println("循环通知_APP!");
				JYyPushUtil.sendToRegistrationId(listData.get(i).getId(),carPush.getNotificationTitle(),carPush.getMsgTitle(),carPush.getMsgContent(),"1");
			}
			return new ResultBean<>(true);
		}else{
			return new ResultBean<>(false);
		}
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
	public ResultBean<Integer> deleteOrderById(
			@ApiParam(name = "id", value = "订单id",required = true)
			@RequestParam(required=true) String id,@RequestParam(value="operator")String operator,
			HttpServletRequest request) throws Exception{

		return new ResultBean(carScrapOrderService.deleteByPrimaryKey(id,operator));
		
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
		CarScrapOrderBO cbo = carScrapOrderService.queryBOById(id);
//		System.out.println("cbo:"+cbo);

		//获取数据操作人
//		UserBO userbo = userMapper.queryUserBOById(cbo.getOperator());
//		System.out.println("userbo"+userbo);
//		if(userbo!=null){cbo.setOperator(userbo.getRealName());}
		//若订单为12【12为待总部报价】& 总部报价不为空 & 总部最初报价>业务报价【说明一开始报多了】,得出该单为二次报价【待审核】
		if(cbo.getOrderStatus()==12 && cbo.getOrderAmount()!=null && cbo.getSinceQuote()!=null && (cbo.getOrderAmount().compareTo(cbo.getSinceQuote())==1) ){
			cbo.setOrderStatusCN("未审核");  //车辆接收-订单详情-二次报价-'待审核'小字
		}//总部已审核，待用户再次确认报价,13 且 总部价< || =业务价
		else if(cbo.getOrderStatus()==13 && cbo.getOrderAmount()!=null && cbo.getSinceQuote()!=null
				&& ((cbo.getOrderAmount().compareTo(cbo.getSinceQuote())==-1) || (cbo.getOrderAmount().compareTo(cbo.getSinceQuote())==0))){
			cbo.setOrderStatusCN("已审核");
		}else if(cbo.getOrderStatus()==12 && cbo.getSinceQuote()==null){
			cbo.setOrderStatusCN("总部待报价");
		}else if(cbo.getOrderStatus()==13 && cbo.getSinceQuote()==null){
			cbo.setOrderStatusCN("已报价");
		}
		switch (cbo.getOrderStatus()){
			case 1:
				cbo.setOrderStatusCN("待派单");
				break;
			case 2:
				cbo.setOrderStatusCN("待接收");
				break;
			case 3:
				cbo.setOrderStatusCN("待入场"); //已接收=待入场
				break;
			case 4:
				cbo.setOrderStatusCN("待报废");
				break;
			case 5:
				cbo.setOrderStatusCN("已完成");
				break;
			case 11:
				cbo.setOrderStatusCN("分部待报价");
				break;
			case 33:
				cbo.setOrderStatusCN("待接单");
				break;
			case 96:
				cbo.setOrderStatusCN("待审核");
				break;
		}
		return new ResultBean(cbo);
	}
	
	
	
	/**
	 * 根据订单获取订单的简单信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getSimpleInfo/{id}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取订单详情",notes = "根据id获取User详情")
	public ResultBean<CarScrapOrderSimpleInfo> getCarScrapOrderSimpleInfo(@PathVariable("id")String id) throws Exception{
		CarScrapOrderSimpleInfo simpleInfo = new CarScrapOrderSimpleInfo();
		CarScrapOrderBO bo = carScrapOrderService.queryBOById(id);
		if(bo!=null){
			BeanUtils.copyProperties(bo, simpleInfo);
		}else{
			return new ResultBean(bo);
		}
		return new ResultBean(simpleInfo);
	}
	
	/**
	 * 根据id更新订单
	 * @param id
	 * @param u
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "{id}",method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "修改订单",notes = "根据id修改订单信息")
	public ResultBean<Integer> updateOrder(@PathVariable("id")String id,HttpServletRequest request) throws Exception{
         CarScrapOrder carScrapOrder = carScrapOrderService.queryById(id);
	     carScrapOrder = DataConverter.copyRequestParamToOrdermedel(carScrapOrder,request);
		 return new ResultBean(carScrapOrderService.update(carScrapOrder));
	}
	
	/**
	 * 订单审核
	 * @param id
	 * @param orderStatus
	 * @param remark
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/audit/{id}",method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "审核订单App-最终落地接口",notes = "审核订单")
	public ResultBean<Boolean> saveOrderAuditingRecord(@PathVariable("id")String id,
			@RequestParam(value="auditorderStatus",required = true)Integer auditorderStatus,
			@RequestParam(value="auditRemark",required = false)String auditRemark,
		    @RequestParam(value="auditer")String auditer,
		    @RequestParam(value="agentUserName",required = false)String username,
		    @RequestParam(value="agentUserid",required = false)String userId,
			@RequestParam(value="isClient",required = false)String isClient,
		    CarScrapOrder order, HttpServletRequest request) throws Exception{

		 System.out.println("测试接单的auditer:"+auditer);

		CarScrapOrder carScrapOrder = carScrapOrderService.queryById(id);
		if(carScrapOrder!=null){
			if(order.getAdjustWhy()!=null && order.getSinceQuote()!=null){
				carScrapOrder.setAdjustWhy(order.getAdjustWhy());
				carScrapOrder.setSinceQuote(order.getSinceQuote());
			}
			int result;
			carScrapOrder = DataConverter.copyRequestParamToOrdermedel(carScrapOrder,request);
			carScrapOrder.setOperator(auditer);
			if(username!=null||userId!=null){
				result = carScrapOrderService.saveOrderAuditingRecord(id,auditorderStatus,auditRemark,auditer,carScrapOrder,username,userId,isClient);
			}else if(username==null && userId==null && isClient!=null){
				result = carScrapOrderService.saveOrderAuditingRecord(id,auditorderStatus,auditRemark,auditer,carScrapOrder,username,userId,isClient);
			} else{
				result = carScrapOrderService.saveOrderAuditingRecord(id,auditorderStatus,auditRemark,auditer,carScrapOrder);
			}
			return new ResultBean<Boolean>(result==1);
		}
		return new ResultBean<Boolean>(false);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getOrderAuditInfo/{id}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取订单审核进度详情",notes = "根据id获取订单审核进度详情")
	public ResultBean<OrderAduitBO> getOrderAuditInfo(@PathVariable("id")String id) throws Exception{
		return new ResultBean<OrderAduitBO>(carScrapOrderService.getOrderAuditInfo(id));
	}
	
	

	
	/**
	 * 
	 * @param agentUserid
	 * @param orderStauts
	 * @param areas
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryToDoHndleOrder",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询待处理的订单数",notes = "审核订单")
	public ResultBean<Integer> queryPendingOrder(String agentUserid,Integer orderStauts,String areas)throws Exception{
		List<Object> areasArray = new ArrayList<Object>();
		if(StringUtils.isNotBlank(areas)){
			String[] areasids = StringUtils.split(areas, ",");
			areasArray = Arrays.asList(areasids);
		}
		Example example = new Example(CarScrapOrder.class);
		Criteria criteria  = example.createCriteria();
		
		
		if(CommonSystemParamter.ORDER_DISTRIBUTION_STATUS==orderStauts){
			CheckUtil.notEmpty(agentUserid, "param is null");
			criteria.andEqualTo("orderStatus", CommonSystemParamter.ORDER_DISTRIBUTION_STATUS);
			criteria.andEqualTo("agentUserid", agentUserid);
		}else if(CommonSystemParamter.ORDER_RECEIVE_STATUS==orderStauts){
			criteria.andEqualTo("orderStatus", CommonSystemParamter.ORDER_RECEIVE_STATUS);
		}else if(CommonSystemParamter.ORDER_STORAGE_STATUS==orderStauts){
			criteria.andEqualTo("orderStatus", CommonSystemParamter.ORDER_STORAGE_STATUS);
		}else if(CommonSystemParamter.ORDER_NOOK_STATUS==orderStauts){
			criteria.andEqualTo("orderStatus", CommonSystemParamter.ORDER_NOOK_STATUS);
			criteria.andEqualTo("agentUserid", agentUserid);
		}
		criteria.andEqualTo("isremove", false);
		criteria.andEqualTo("orderType", "1");
		if(areasArray!=null&&areasArray.size()>0){
			criteria.andIn("orderAreasId", areasArray);
		}
		
		
		return new ResultBean<Integer>(carScrapOrderService.selectCountByExample(example));
		
	}
	
	/**
	 * cancelOrder	取消订单
	 * @param id
	 * @param cancelMemo
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "取消订单", notes = "根据id取消订单")
	public ResultBean<Integer> cancelOrder(
			@ApiParam(name = "id", value = "订单id", required = true) @RequestParam(required = true) String id,
			@RequestParam(required = false) String cancelMemo,
			@RequestParam(required = true) String operator) throws Exception {
		
		return new ResultBean<Integer>(carScrapOrderService.cancelOrder(id, cancelMemo, operator));

	}
	
	
	/**
	 * confirmAmount	确认报价
	 * @param order
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmAmount", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "确认报价", notes = "根据id确认报价")
	public ResultBean<Boolean> confirmAmount(@RequestBody CarScrapOrderConfirmAmountVO confirmAmountVO,BindingResult bindingResult) throws Exception {
		
		return new ResultBean<Boolean>(carScrapOrderService.confirmAmount(confirmAmountVO));

	}
	
	@RequestMapping(value = "/myTrade/{clientId}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "我的交易",notes = "根据id获取订单审核进度详情")
	public ResultBean<CarScrapOrderMyTradeBO> myTrade(@PathVariable("clientId")String clientId) throws Exception{
		return new ResultBean<CarScrapOrderMyTradeBO>(carScrapOrderService.myTrade(clientId));
		
	}
	
	@RequestMapping(value = "/myTradeInfo",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取我的交易详情列表", notes = "获取我的交易详情列表")
	public ResultBean<PageResult<CarScrapOrderMyTradeInfoBO>> myTradeInfo(
			@ApiParam(name = "page", value = "分页参数页码", required = true) @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数", required = true) @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows,
			@RequestParam(value = "clientId", required = true) String clientId)
			throws Exception {

		PageResult<CarScrapOrderMyTradeInfoBO> pageResult = null;
		PageInfo<CarScrapOrderMyTradeInfoBO> pageInfo = carScrapOrderService.myTradeInfo(page, rows, clientId);
		if (pageInfo != null) {
			pageResult = new PageResult<CarScrapOrderMyTradeInfoBO>();
			pageResult.setRows(pageInfo.getList());
			pageResult.setTotal(pageInfo.getTotal());
		}
		return new ResultBean<PageResult<CarScrapOrderMyTradeInfoBO>>(pageResult);
	}
	/**
	 * quote 报价(分总,总部)
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "quote", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "报价", notes = "报价")
	public ResultBean<Boolean> quote(@RequestBody CarScrapOrderQuoteVO quoteVO) throws Exception {
		//return new ResultBean<Boolean>(carScrapOrderService.quote(quoteVO));
		return new ResultBean<Boolean>(carScrapOrderService.quoteByPush(quoteVO));

	}
	
	@RequestMapping(value = "quoteList",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取报价分页列表",notes = "获取报价分页列表" )
	public ResultBean<PageResult<CarScrapOrderQuotePageBO>> quoteList(@ApiParam(name = "page", value = "分页参数页码",required = true)@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception{
		// 查询是否有查询全区域数据的权限
		PageResult<CarScrapOrderQuotePageBO> pageResult = null;
		PageInfo<CarScrapOrderQuotePageBO> pageInfo = carScrapOrderService.queryQuotePageListByKeyword(page, rows, paramter);
		if (pageInfo != null) {
			pageResult = new PageResult<CarScrapOrderQuotePageBO>();
			pageResult.setRows(pageInfo.getList());
			pageResult.setTotal(pageInfo.getTotal());
			return new ResultBean<PageResult<CarScrapOrderQuotePageBO>>(pageResult);
		}
		return new ResultBean<PageResult<CarScrapOrderQuotePageBO>>(pageResult);

	}
	
}
