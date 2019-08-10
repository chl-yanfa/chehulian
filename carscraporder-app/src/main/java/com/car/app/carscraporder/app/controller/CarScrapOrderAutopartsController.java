package com.car.app.carscraporder.app.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.app.bean.CarScrapOrderSimpleBaseVO;
import com.car.app.carscraporder.app.service.CarScrapOrderAutopartsAppService;
import com.car.app.carscraporder.app.util.HttpRequestParamConConverter;
import com.car.app.carscraporder.app.util.UserUtil;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsDetailBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.UserBO;
import com.car.app.carscraporder.pojo.CarScrapOrderAutoparts;
import com.car.app.carscraporder.pojo.User;
import com.car.app.carscraporder.vo.CarScrapOrderAutopartsVO;
import com.car.app.carscraporder.vo.CarScrapOrderAutopartsWhereParamter;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.common.bean.PageResult;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.exception.DataException;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value="/autoparts")
@Api(value = "AUTOPARTS-API",description="配件操作接口")
public class CarScrapOrderAutopartsController {
	
	@Autowired
	private CarScrapOrderAutopartsAppService carScrapOrderAutopartsAppService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取用户分页列表",notes = "获取用户分页列表")
	public ResultBean<PageResult<CarScrapOrderPageBO>> getUserList(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception{
		    
			    
			    PageResult<CarScrapOrderPageBO> pageInfo =  carScrapOrderAutopartsAppService.queryBOPageListByWhere(page, rows, paramter);
			   
			    
		     return new ResultBean<PageResult<CarScrapOrderPageBO>>(pageInfo);
		
		
	}
	
	/**
	 * 根据配件id获取配件简单信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "get/{id}",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据配件id获取配件详情",notes = "根据配件id获取配件详情")
	public ResultBean<CarScrapOrderAutopartsBO> getOrderAutopartsBOById(@PathVariable("id")String id) throws Exception{
		
		return  new ResultBean<CarScrapOrderAutopartsBO>(carScrapOrderAutopartsAppService.queryBOById(id));
		
	}
	
	/**
	 * 根据订单id获取配件详情
	 * @param id
	 * @param carScrapOrderVo
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "{id}/detail",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<CarScrapOrderAutopartsDetailBO> getOrderAutopartsDetailById(@PathVariable("id")String id) throws Exception{
		
		return  new ResultBean<CarScrapOrderAutopartsDetailBO>(carScrapOrderAutopartsAppService.getOrderAutopartsDetailById(id));
		
	}
	
	
	/**
	 * 根据id更新配件信息
	 * @param id
	 * @param u
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "update/{id}",method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Boolean> updateOrderAutoparts(@PathVariable("id")String id,@Validated CarScrapOrderAutopartsVO carScrapOrderVo,HttpServletRequest request) throws Exception{
		
		User user = UserUtil.getUser();
        if(user==null){
  	        throw new DataException("未登录系统");
         }
        
        Map<String,String> map = HttpRequestParamConConverter.getParameterMap(request);
         //填充数据操作人
        map.put("Operator", user.getId());
		
		
		return new ResultBean<Boolean>(carScrapOrderAutopartsAppService.update(map,id));
		
	}
	
	
	@RequestMapping(value = "/audit/{id}",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "审核订单",notes = "审核订单")
	public ResultBean<Boolean> saveOrderAuditingRecord(@PathVariable("id")String id,
			@RequestParam(required = true)Integer auditorderStatus,
			@RequestParam(value="auditRemark",required = false)String auditRemark,CarScrapOrderSimpleBaseVO vo,HttpServletRequest request) throws Exception{
		UserBO user = (UserBO) UserUtil.getUser();
	     if(user == null){
	    	 throw new DataException("未登录系统");
	     }
	     Map<String,String> map = HttpRequestParamConConverter.getParameterMap(request);
		 return new ResultBean<Boolean>(carScrapOrderAutopartsAppService.saveOrderAutopartsAuditingRecord(id,auditorderStatus,auditRemark,user.getId(),map));
	}
	
	@RequestMapping(value = "/sorting/{id}",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "配件分拣",notes = "配件分拣")
	public ResultBean<Boolean> sortingParts(@PathVariable("id")String id,
			@RequestParam(required = true)Integer sortingStatus,
			HttpServletRequest request) throws Exception{
		UserBO user = (UserBO) UserUtil.getUser();
	     if(user == null){
	    	 throw new DataException("未登录系统");
	     }

	     
		return new ResultBean<Boolean>(carScrapOrderAutopartsAppService.sortingParts(id,sortingStatus,user.getId()));
		
	}
	
	
	
	
	
	
	
	
	
	

}
