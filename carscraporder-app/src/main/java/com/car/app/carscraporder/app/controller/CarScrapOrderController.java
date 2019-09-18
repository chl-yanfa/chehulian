package com.car.app.carscraporder.app.controller;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.app.bean.CarScrapOrderSimpleBaseVO;
import com.car.app.carscraporder.app.bean.UserAppBO;
import com.car.app.carscraporder.app.service.CarScrapOrderAppService;
import com.car.app.carscraporder.app.service.CarScrapOrderAutopartsAppService;
import com.car.app.carscraporder.app.service.imp.CommonVariablesServiceImpl;
import com.car.app.carscraporder.app.util.CheckUtil;
import com.car.app.carscraporder.app.util.HttpRequestParamConConverter;
import com.car.app.carscraporder.app.util.UserUtil;
import com.car.app.carscraporder.bo.CarScrapOrderBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.CarScrapOrderQuotePageBO;
import com.car.app.carscraporder.bo.CarScrapOrderSimpleInfo;
import com.car.app.carscraporder.bo.OrderAttachmentBO;
import com.car.app.carscraporder.bo.UserBO;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.carscraporder.vo.CarScrapOrderQuoteVO;
import com.car.app.carscraporder.vo.CarScrapOrderVO;
import com.car.app.common.bean.PageResult;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.exception.DataException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Controller
@RequestMapping(value="/order")
@Api(value = "Order-API",description = "订单相关接口文档")
public class CarScrapOrderController {
	
	
	private Logger logger = LoggerFactory.getLogger(CarScrapOrderController.class);
	
	
	@Autowired
	private CarScrapOrderAppService  carScrapOrderAppService;

	@Autowired
	private  CarScrapOrderAutopartsAppService carScrapOrderAutopartsAppService;
	
	@Autowired
	private  CommonVariablesServiceImpl  commonVariablesServiceImpl;
	
	
	
	
	
	
	/**
	 * 订单分页查询
	 * @param page
	 * @param rows
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取订单分页列表",notes = "获取用户分页列表")
	public ResultBean<PageResult<CarScrapOrderPageBO>> getOrderList(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			@ApiParam(name = "queryType", value = "查询类型1.订单待接收 2.订单已接收 3订单待入场 4.订单已入库 5.订单待出库 6.订单已出库 7订单查询",required = true)
			@RequestParam(value="queryType",required = true,defaultValue="1")Integer querytype,
			CarScrapOrderKeywordVO paramter) throws Exception{
		      UserAppBO user = UserUtil.getUser();
	          if(user==null){
	    	        throw new DataException("未登录系统");
	           }
	        
	          CheckUtil.notNull(paramter, "param.is.null");//参数错误
	          CheckUtil.notNull(paramter.getOrderType(), "param.is.null");//参数错误
	          //TO_DO 校验订单类型参数是否正确
	          String businessType = paramter.getOrderType();
	          
	          
	          //当前登录用户管理区域ids
	         String areaesids = null;
	         if(!user.getIsAllDataPermisssion()){
	        	  areaesids = user.getAreasids();
	         }
	          
	         PageResult<CarScrapOrderPageBO> pageData = null;
	           if(StringUtils.equals(businessType, "1")){
	        	   pageData = handleWholeCar(paramter,querytype,user,areaesids,page,rows);
	           }else if(StringUtils.equals(businessType, "2")){
	        	   pageData = handleParts(paramter,querytype,user,areaesids,page,rows);
	           }else{
	        	   throw new DataException("请求参数错误!");
	           }
	           
		     return new ResultBean<PageResult<CarScrapOrderPageBO>>(pageData);
		
		
	}
	
	
	private PageResult<CarScrapOrderPageBO> handleParts(CarScrapOrderKeywordVO paramter,Integer querytype,UserAppBO user,String areaesids,
			Integer page,Integer rows) throws Exception{
		PageResult<CarScrapOrderPageBO> pageData = null;
		if(querytype==8){
   		 //1.旧件待接收
		  paramter.setOrderStatus("2");
		  paramter.setUserid(user.getId());
		  paramter.setAraesids(areaesids);
		  pageData = carScrapOrderAppService.getOrderPageListByKeyword(page, rows, paramter);
   	  }else if(querytype==9){
		  //2.旧件已接收
		  paramter.setOrderStatus("3");
		  paramter.setUserid(user.getId());
		  pageData = carScrapOrderAppService.queryPageHistoryListByKeyword(page, rows, paramter);
   	  }else if(querytype==10){
   		  //3.旧件待入库
		 paramter.setOrderStatus("3");
		 paramter.setAraesids(areaesids);
		 paramter.setUserid(user.getId());
		 pageData = carScrapOrderAutopartsAppService.queryBOPageListByWhere(page, rows, paramter);
   	  }else if(querytype==11){
   		  //4.旧件已入库
		  paramter.setOrderStatus("5");
		  paramter.setUserid(user.getId());
		  paramter.setAraesids(areaesids);
		  pageData = carScrapOrderAutopartsAppService.queryHistoryBOPageListByWhere(page, rows, paramter);
   	  }else if(querytype==12){
   		  //5.旧件待出库
		   paramter.setOrderStatus("5");
		   paramter.setUserid(user.getId());
		   paramter.setIsSorting(true);
		   paramter.setAraesids(areaesids);
		   pageData = carScrapOrderAutopartsAppService.getSortingParts(page, rows, paramter);
   	  }else if(querytype==13){
   		  //3.旧件已出库
		  paramter.setOrderStatus("6");
		  paramter.setUserid(user.getId());
		  paramter.setAraesids(areaesids);
		  pageData = carScrapOrderAutopartsAppService.queryHistoryBOPageListByWhere(page, rows, paramter);
   	  }else if(querytype==14){
   		  //3.车辆查询
		  paramter.setAraesids(areaesids);
		  paramter.setOrderType("2");
		  paramter.setUserid(user.getId());
		  pageData = carScrapOrderAutopartsAppService.queryBOPageListByWhere(page, rows, paramter);
   	  }else if(querytype==15){
   		  //3.待分拣
		   paramter.setOrderStatus("5");
		   paramter.setIsSorting(false);
		   paramter.setAraesids(areaesids);
		   paramter.setUserid(user.getId());
		   pageData = carScrapOrderAutopartsAppService.getSortingParts(page, rows, paramter);
	  }else if(querytype==16){
   		  //3.配件在制造
		      paramter.setOrderStatus("51");
	          paramter.setUserid(user.getId());
	          paramter.setAraesids(areaesids);
	          pageData = carScrapOrderAutopartsAppService.queryHistoryBOPageListByWhere(page, rows, paramter);
      }else if(querytype==17){
   		  //3.配件报废
    	   paramter.setOrderStatus("52");
           paramter.setUserid(user.getId());
           paramter.setAraesids(areaesids);
	       pageData = carScrapOrderAutopartsAppService.queryHistoryBOPageListByWhere(page, rows, paramter);
    }
		return pageData;
 }
	

	private PageResult<CarScrapOrderPageBO> handleWholeCar(CarScrapOrderKeywordVO paramter,Integer querytype,UserAppBO user,String areaesids,
			Integer page,Integer rows) throws Exception{
		PageResult<CarScrapOrderPageBO> pageData = null;
		if(querytype==1){
   		 //1.车辆未接收
			  paramter.setOrderStatus("2");
			  paramter.setUserid(user.getId());
			  paramter.setAraesids(areaesids);
			  pageData = carScrapOrderAppService.getOrderPageListByKeyword(page, rows, paramter);
	        		  
   	  }else if(querytype==2){
			 //2.车辆已接收
			  paramter.setOrderStatus("3");
			  paramter.setUserid(user.getId());
			  pageData = carScrapOrderAppService.queryPageHistoryListByKeyword(page, rows, paramter);
	        		  
   	  }else if(querytype==3){
   		  //3.整车待入场
   		        paramter.setOrderStatus("3");
   		        paramter.setAraesids(areaesids);
   		        pageData = carScrapOrderAppService.getOrderPageListByKeyword(page, rows, paramter);
   		  
   	  }else if(querytype==4){
   		  //4.整车已入场
	        		  paramter.setOrderStatus("4");
		   	          //paramter.setUserid(user.getId());
		   	          paramter.setAraesids(areaesids);
		   	          pageData = carScrapOrderAppService.queryPageHistoryListByKeyword(page, rows, paramter);
   	  }else if(querytype==5){
   		  //5.整车待报废
   		           paramter.setOrderStatus("4");
   		           paramter.setAraesids(areaesids);
		           pageData = carScrapOrderAppService.getOrderPageListByKeyword(page, rows, paramter);
   		  
   	  }else if(querytype==6){
   		  //6.整车已报废
	        		  paramter.setOrderStatus("5");
		   	          //paramter.setUserid(user.getId());
		   	          paramter.setAraesids(areaesids);
		   	          pageData = carScrapOrderAppService.queryPageHistoryListByKeyword(page, rows, paramter);
	        		  
   	  }else if(querytype==7){
   		  //7.车辆查询
   		        paramter.setAraesids(areaesids);
   		        paramter.setOrderType("1");
   		        pageData = carScrapOrderAppService.getAll(page, rows, paramter);
   		          
   	  }else if(querytype==8){
			//8.整车待接单
			paramter.setOrderStatus("33");
			paramter.setUserid(user.getId());
			paramter.setAraesids(areaesids);
			pageData = carScrapOrderAppService.getOrderPageListByKeyword(page, rows, paramter);
	  }else if(querytype==9){
			//8.整车已接单
			paramter.setOrderStatus("2");
			paramter.setUserid(user.getId());
			paramter.setAraesids(areaesids);
			pageData = carScrapOrderAppService.getOrderPageListByKeyword(page, rows, paramter);
		}else if(querytype==10){
			//8.整车待审核
			paramter.setOrderStatus("96");
			paramter.setUserid(user.getId());
			paramter.setAraesids(areaesids);
			pageData = carScrapOrderAppService.getOrderPageListByKeyword(page, rows, paramter);
		}else if(querytype==11){
			//8.整车已审核
			paramter.setOrderStatus("2");
			paramter.setUserid(user.getId());
			paramter.setAraesids(areaesids);
			paramter.setIsWhere(1);
			pageData = carScrapOrderAppService.getOrderPageListByKeyword(page, rows, paramter);
		}else if(querytype==12){
            //8.整车已取消
            paramter.setOrderStatus("-1");
            paramter.setAraesids(areaesids);
            pageData = carScrapOrderAppService.getOrderPageListByKeyword(page, rows, paramter);
        }
		return pageData;
	}
	
	
	
	
	
	
	
	/**
	 * 根据订单id获取订单详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/details/{id}",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据id获取订单详情",notes = "根据id获取User详情")
	public ResultBean<CarScrapOrderBO> getOrderById(@PathVariable("id")String id) throws Exception{
		
		CarScrapOrderBO bo = carScrapOrderAppService.getOrderById(id);
		if(bo!=null){
			//填充默认图片
			if(StringUtils.equals(bo.getOrderType(), "1"))
			    fillDeafultPicture(bo);
		}
		return new ResultBean<CarScrapOrderBO>(bo);
	}
	
	
	
     private List<OrderAttachmentBO> deafulePricture(Integer prictureType,List<OrderAttachmentBO> sourceData){
		  if(prictureType==1){  //整车
			  Map<String,OrderAttachmentBO> relation = new HashMap<String,OrderAttachmentBO>();
			  List<OrderAttachmentBO> prictures = new ArrayList<OrderAttachmentBO>();
			  OrderAttachmentBO bo = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/1.png","1",true);
			  prictures.add(bo);
			  relation.put("1", bo);
			  OrderAttachmentBO bo2 = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/2.png","2",false);
			  prictures.add(bo2);
			  relation.put("2", bo2);
			  OrderAttachmentBO bo3 = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/3.png","3",false);
			  prictures.add(bo3);
			  relation.put("3", bo3);
			  OrderAttachmentBO bo4 = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/4.png","4",false);
			  prictures.add(bo4);
			  relation.put("4", bo4);
			  OrderAttachmentBO bo5 = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/5.png","5",false);
			  prictures.add(bo5);
			  relation.put("5", bo5);
			  OrderAttachmentBO bo6 = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/6.png","6",false);
			  prictures.add(bo6);
			  relation.put("6", bo6);
			  OrderAttachmentBO bo7 = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/7.png","7",false);
			  prictures.add(bo7);
			  relation.put("7", bo7);
			  OrderAttachmentBO bo8 = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/8.png","8",false);
			  prictures.add(bo8);
			  relation.put("8", bo8);
			  OrderAttachmentBO bo9 = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/9.png","9",false);
			  prictures.add(bo9);
			  relation.put("9", bo9);

			  for(OrderAttachmentBO data:sourceData){
				  if(relation.containsKey(data.getCarPictureType())){
					  relation.get(data.getCarPictureType()).setUrl(data.getUrl());
					  relation.get(data.getCarPictureType()).setId(data.getId());
				  }
			  }
			  System.out.println("prictures"+prictures);
			  return prictures;
		  }else if (prictureType==2){
			  Map<String,OrderAttachmentBO> relation = new HashMap<String,OrderAttachmentBO>();
			  List<OrderAttachmentBO> prictures = new ArrayList<OrderAttachmentBO>();
			  OrderAttachmentBO bo = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/10.png","10",true);
			  prictures.add(bo);
			  relation.put("10", bo);
			  OrderAttachmentBO bo2 = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/11.png","11",true);
			  prictures.add(bo2);
			  relation.put("11", bo2);
			  OrderAttachmentBO bo3 = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/12.png","12",true);
			  prictures.add(bo3);
			  relation.put("12", bo3);
			  OrderAttachmentBO bo4 = new OrderAttachmentBO(commonVariablesServiceImpl.PICURL+"/carscraporder-app/android/image/13.png","13",true);
			  prictures.add(bo4);
			  relation.put("13", bo4);
			  
			  for(OrderAttachmentBO data:sourceData){
				  if(relation.containsKey(data.getCarPictureType())){
					  relation.get(data.getCarPictureType()).setUrl(data.getUrl());
					  relation.get(data.getCarPictureType()).setId(data.getId());
				  }
			  }
			  return prictures;
		  }
		return sourceData;
	}
	
	
	private void fillDeafultPicture(CarScrapOrderBO bo){
		
		if(bo!=null){
				List<OrderAttachmentBO> vehiclePictures = deafulePricture(1,bo.getVehiclePictures());
				bo.setVehiclePictures(vehiclePictures);

	    	   List<OrderAttachmentBO> formalitiesPictures = deafulePricture(2,bo.getFormalitiesPictures());
	    	   bo.setFormalitiesPictures(formalitiesPictures);
			}
	}
	
	
	
	@RequestMapping(value = "/get/{id}",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据id获取订单简单信息情",notes = "根据id获取User详情")
	public ResultBean<CarScrapOrderSimpleInfo> getSimpleOrderInfoById(@PathVariable("id")String id) throws Exception{
		return new ResultBean<CarScrapOrderSimpleInfo>(carScrapOrderAppService.getSimpleOrderInfoById(id));
		
	}
	
	
	
	/**
	 * 根据id更新订单信息
	 * @param id
	 * @param u
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "修改订单",notes = "根据id修改订单信息")
	public ResultBean<Boolean> updateOrder(@PathVariable("id")String id,@Validated CarScrapOrderVO carScrapOrderVo,HttpServletRequest request) throws Exception{
		UserBO user = (UserBO) UserUtil.getUser();
	     if(user == null){
	    	 throw new DataException("未登录系统");
	     }
		
	     
	     
	     Map<String,String> map = HttpRequestParamConConverter.getParameterMap(request);
	     //记录修改人信息
	     map.put("operator",user.getId());
	     
		return new ResultBean<Boolean>(carScrapOrderAppService.updateOrder(id,map));
		
	}
	
	/**
	 * 审核订单
	 * @param id
	 * @param u
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/audit/{id}",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "审核订单-App跳板接口",notes = "审核订单")
	public ResultBean<Boolean> saveOrderAuditingRecord(@PathVariable("id")String id,
			@RequestParam(required = true)Integer auditorderStatus,
			String auditRemark,String isGetOrder,CarScrapOrderSimpleBaseVO vo,HttpServletRequest request) throws Exception{
		UserBO user = (UserBO) UserUtil.getUser();
	     if(user == null){
	    	 throw new DataException("未登录系统");
	     }
		Map<String,String> map = HttpRequestParamConConverter.getParameterMap(request);
	     if(isGetOrder!=null){ //接单操作
			 return new ResultBean<Boolean>(carScrapOrderAppService.saveOrderAuditingRecord(id,auditorderStatus,auditRemark,map));
		 }else{
			 return new ResultBean<Boolean>(carScrapOrderAppService.saveOrderAuditingRecord(id,auditorderStatus,auditRemark,user.getId(),map));
		 }
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
		UserAppBO user = UserUtil.getUser();
		if (user == null) {
			throw new DataException("未登录系统");
		}
		quoteVO.setOperator(user.getId());
		return new ResultBean<Boolean>(carScrapOrderAppService.quote(quoteVO));

	}
	
	@RequestMapping(value = "quoteList",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取报价分页列表",notes = "获取报价分页列表" )
	public ResultBean<PageResult<CarScrapOrderQuotePageBO>> quoteList(@ApiParam(name = "page", value = "分页参数页码",required = true)@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception{
		UserAppBO user = UserUtil.getUser();
		if (user == null) {
			throw new DataException("未登录系统");
		}
		// 查询是否有查询全区域数据的权限
		if (!user.getIsAllDataPermisssion())paramter.setAraesids(user.getAreasids());
		PageResult<CarScrapOrderQuotePageBO> pageData = carScrapOrderAppService.queryQuotePageListByKeyword(page, rows, paramter);

		return new ResultBean<PageResult<CarScrapOrderQuotePageBO>>(pageData);
	}

	
	


}
