package com.car.app.carscraporder.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.app.bean.PermissionBO;
import com.car.app.carscraporder.app.bean.UserAppBO;
import com.car.app.carscraporder.app.exception.DataException;
import com.car.app.carscraporder.app.exception.UnloginException;
import com.car.app.carscraporder.app.service.CarScrapOrderAppService;
import com.car.app.carscraporder.app.service.CarScrapOrderAutopartsAppService;
import com.car.app.carscraporder.app.service.PermissionService;
import com.car.app.carscraporder.app.service.UserService;
import com.car.app.carscraporder.app.systemparameter.SystemParameter;
import com.car.app.carscraporder.app.util.UserUtil;
import com.car.app.carscraporder.pojo.Permission;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.service.RedisService;
import com.car.app.common.util.CookieUtils;


@Controller
@RequestMapping(value="/permisssion")
@Api(value = "permisssion-API",description = "点击整车或旧件加载权限" )
public class PermissionController {
	
	
	@Autowired
	private PermissionService  permissionService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CarScrapOrderAppService carScrapOrderAppService;
	@Autowired
	private CarScrapOrderAutopartsAppService carScrapOrderAutopartsAppService;
	
	

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据编码获取权限",notes = "根据编码获取权限")
	public ResultBean<List<PermissionBO>>  getCarScrapOrderPermission(
			@ApiParam(name = "functionCode", value = "功能编码,整车编码：wholecar。旧件：parts",required = true)
	        @RequestParam(value="functionCode",required = true)String functionCode,
	        HttpServletRequest request,
            HttpServletResponse response
	        ) throws Exception{
		
		
		 List<PermissionBO> result = null;
		
		UserAppBO user = (UserAppBO) UserUtil.getUser();
		if(user==null){
			throw new UnloginException("用户未登陆系统");
		}
	    String code = null;
		
		
		if(StringUtils.equals(functionCode, SystemParameter.FUNCTION_ORDER_CAR_WHOLE)){
			code = "order_car_whole";
		}else if(StringUtils.equals(functionCode, SystemParameter.FUNCTION_ORDER_CAR_PARTS)){
		
			code = "order_car_parts";
			
		}else{
		
			throw new DataException("请求参数错误");
			
		}
		
		Map permission = user.getPermission();
		if(permission.containsKey(functionCode)){

			Map<String,List<Integer>> permissionCache = user.getPermission();
			
			List<Integer> permissionListCache = permissionCache.get(functionCode);
			
			result = queryRemindNum(permissionListCache,functionCode);
			
		    return new ResultBean(result);
		}else{
			List<Permission>  permisssionList = permissionService.getPermissionByCode(code);
			if(permisssionList!=null&&permisssionList.size()>0){
				List<Integer> funPermissionCode = new ArrayList<Integer>();
				
				if(StringUtils.equals(functionCode, SystemParameter.FUNCTION_ORDER_CAR_WHOLE)){
					for(Permission permissions :permisssionList){       
						if(StringUtils.equals(permissions.getPercode(), "order_car_whole:accept")){
							funPermissionCode.add(1);
						}else if (StringUtils.equals(permissions.getPercode(), "order_car_whole:storage")){
							funPermissionCode.add(2);
						}else if (StringUtils.equals(permissions.getPercode(), "order_car_whole:scrap")){
							funPermissionCode.add(3);
						}else if (StringUtils.equals(permissions.getPercode(), "order_car_whole:query")){
							funPermissionCode.add(4);
						}else if (StringUtils.equals(permissions.getPercode(), "order_car_whole:jiedan")){
							funPermissionCode.add(5);
						}
						
					}
					//将权限存储到user缓存与Map集合
					if(funPermissionCode.size()>0){
						user.getPermission().put(functionCode, funPermissionCode);
						String appSessionId = CookieUtils.getCookieValue(request, SystemParameter.TICKET);
						redisService.set(SystemParameter.TICKET+appSessionId, SystemParameter.MAPPER.writeValueAsString(user), 30*60);
						
						result = queryRemindNum(funPermissionCode,functionCode);
					    return new ResultBean(result);
					}
					
				}else{
					for(Permission permissions :permisssionList){
						if(StringUtils.equals(permissions.getPercode(), "order_car_parts:accept")){
							funPermissionCode.add(1);
						}else if (StringUtils.equals(permissions.getPercode(), "order_car_parts:intostorage")){
							funPermissionCode.add(2);
						}else if (StringUtils.equals(permissions.getPercode(), "order_car_parts:outstorage")){
							funPermissionCode.add(3);
						}else if (StringUtils.equals(permissions.getPercode(), "order_car_parts:query")){
							funPermissionCode.add(4);
						}else if (StringUtils.equals(permissions.getPercode(), "order_car_parts:sorting")){
							funPermissionCode.add(5);
						}
						
					}
					//将权限存储到user缓存
					if(funPermissionCode.size()>0) {
						user.getPermission().put(functionCode, funPermissionCode);
						String appSessionId = CookieUtils.getCookieValue(request, SystemParameter.TICKET);
						redisService.set(SystemParameter.TICKET+appSessionId, SystemParameter.MAPPER.writeValueAsString(user), 30*60);
						
						result = queryRemindNum(funPermissionCode,functionCode);
						
					    return new ResultBean(result);
					}
				}
					
			
			}
			
		}
		return new ResultBean<List<PermissionBO>> (new ArrayList());
		
		
	}
	
	
	private List<PermissionBO> queryRemindNum(List<Integer> permissionListCache,String functionCode) throws Exception{
		
		UserAppBO user = (UserAppBO) UserUtil.getUser();
		if(user==null){
			throw new UnloginException("用户未登陆系统");
		}
		
		List<PermissionBO> bos = new ArrayList<PermissionBO>();
		
		if(StringUtils.equals(functionCode, SystemParameter.FUNCTION_ORDER_CAR_WHOLE)){
			for(Integer permissioncode:permissionListCache){
				if(permissioncode==1){
					PermissionBO bo = new PermissionBO();
					//待接收数量
					int count = carScrapOrderAppService.queryPendingOrder(user.getId(), 2, user.getAreasids());
					bo.setRemindNum(count);
					bo.setFunctionCode(1);
					bos.add(bo);
				}else if(permissioncode==2){
					//待入场数量
					PermissionBO bo = new PermissionBO();
					int count =carScrapOrderAppService.queryPendingOrder(null, 3, user.getAreasids());
					bo.setRemindNum(count);
					bo.setFunctionCode(2);
					bos.add(bo);
				}else if(permissioncode==3){
					//待报废数量
					PermissionBO bo = new PermissionBO();
					int count = carScrapOrderAppService.queryPendingOrder(null, 4, user.getAreasids());
					bo.setRemindNum(count);
					bo.setFunctionCode(3);
					bos.add(bo);
				}else if(permissioncode==4){
					PermissionBO bo = new PermissionBO();
					bo.setFunctionCode(4);
					bos.add(bo);
				}else if(permissioncode==5){
					//接单管理
					PermissionBO bo = new PermissionBO();
					int count = carScrapOrderAppService.queryPendingOrder(user.getId(), 33, user.getAreasids());
					bo.setRemindNum(count);
					bo.setFunctionCode(5);
					bos.add(bo);
				}
			}
		}else{
			for(Integer permissioncode:permissionListCache){
				if(permissioncode==1){
					//旧件待接收数量
					PermissionBO bo = new PermissionBO();
					int count = carScrapOrderAutopartsAppService.queryPendingOrder(user.getId(), 2, user.getAreasids(),null);
					bo.setRemindNum(count);
					bo.setFunctionCode(1);
					bos.add(bo);
				}else if(permissioncode==2){
					//旧件待入库数量
					PermissionBO bo = new PermissionBO();
					int count = carScrapOrderAutopartsAppService.queryPendingOrder(null, 3, user.getAreasids(),null);
					bo.setRemindNum(count);
					bo.setFunctionCode(2);
					bos.add(bo);
				}else if(permissioncode==3){
					//旧件待出库库数量
					PermissionBO bo = new PermissionBO();
					int count = carScrapOrderAutopartsAppService.queryPendingOrder(null, 5, user.getAreasids(),true);
					bo.setRemindNum(count);
					bo.setFunctionCode(3);
					bos.add(bo);
				}else if(permissioncode==4){
					PermissionBO bo = new PermissionBO();
					bo.setFunctionCode(4);
					bos.add(bo);
				}else if(permissioncode==5){
					
					PermissionBO bo = new PermissionBO();
					int count = carScrapOrderAutopartsAppService.queryPendingOrder(null, 6, user.getAreasids(),false);
					bo.setRemindNum(count);
					bo.setFunctionCode(5);
					bos.add(bo);
				}
			}
		}
		return bos;
	}

}
