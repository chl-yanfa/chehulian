package com.car.app.carscraporder.applet.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.car.app.carscarporder.web.service.CarScrapOrderAutopartsService;
import com.car.app.carscraporder.applet.threadlocal.UserUtil;
import com.car.app.carscraporder.applet.util.HttpRequestParamConConverter;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsBO;
import com.car.app.carscraporder.bo.CarScrapOrderAutopartsDetailBO;
import com.car.app.carscraporder.bo.CarScrapOrderPageBO;
import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.vo.CarScrapOrderAutopartsVO;
import com.car.app.carscraporder.vo.CarScrapOrderKeywordVO;
import com.car.app.common.bean.PageResult;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.exception.DataException;

@Controller
@RequestMapping(value="/autoparts")
@Api(value = "AUTOPARTS-API",tags={"配件操作接口"})
public class CarScrapOrderAutopartsController {
	
	@Autowired
	private CarScrapOrderAutopartsService carScrapOrderAutopartsService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取用户分页列表",notes = "获取用户分页列表")
	public ResultBean<PageResult<CarScrapOrderPageBO>> getUserList(
			@ApiParam(name = "page", value = "分页参数页码",required = true)
			@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)
			@RequestParam(value="rows",required = true,defaultValue="10")Integer rows,
			CarScrapOrderKeywordVO paramter) throws Exception{
		    
			    
			    PageResult<CarScrapOrderPageBO> pageInfo =  carScrapOrderAutopartsService.queryBOPageListByWhere(page, rows, paramter);
			   
			    
		     return new ResultBean<PageResult<CarScrapOrderPageBO>>(pageInfo);
		
		
	}
	
	/**
	 * 根据配件id获取配件简单信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "{id}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据配件id获取配件详情",notes = "根据配件id获取配件详情")
	public ResultBean<CarScrapOrderAutopartsBO> getOrderAutopartsBOById(@PathVariable("id")String id) throws Exception{
		
		return  new ResultBean<CarScrapOrderAutopartsBO>(carScrapOrderAutopartsService.queryBOById(id));
		
	}
	
	/**
	 * 根据订单id获取配件详情
	 * @param id
	 * @param carScrapOrderVo
	 * @param bindingResult
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "{id}/detail",method = RequestMethod.GET)
	@ResponseBody
	public ResultBean<CarScrapOrderAutopartsDetailBO> getOrderAutopartsDetailById(@PathVariable("id")String id) throws Exception{
		
		return  new ResultBean<CarScrapOrderAutopartsDetailBO>(carScrapOrderAutopartsService.getOrderAutopartsDetailById(id));
		
	}
	
	
	/**
	 * 根据id更新配件信息
	 * @param id
	 * @param u
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "{id}",method = RequestMethod.PUT)
	@ResponseBody
	public ResultBean<Boolean> updateOrderAutoparts(@PathVariable("id")String id,@Validated CarScrapOrderAutopartsVO carScrapOrderVo,BindingResult bindingResult,
			HttpServletRequest request) throws Exception{
		
		ClientBO user = UserUtil.getUser();
        if(user==null){
  	        throw new DataException("未登录系统");
         }
        Map<String,String> map = HttpRequestParamConConverter.getParameterMap(request);
        //填充数据操作人
         map.put("Operator", user.getId());
		return new ResultBean<Boolean>(carScrapOrderAutopartsService.update(map,id));
		
	}
	
	@RequestMapping(value = "{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public ResultBean<Boolean> deleteOrderAutoparts(@PathVariable("id")Integer id) throws Exception{
		
		ClientBO user = UserUtil.getUser();
        if(user==null){
  	        throw new DataException("未登录系统");
         }
	
		return new ResultBean<Boolean>(carScrapOrderAutopartsService.delete(id,user.getId()));
		
	}
	
	
	
	
	
	
	
	
	
	

}
