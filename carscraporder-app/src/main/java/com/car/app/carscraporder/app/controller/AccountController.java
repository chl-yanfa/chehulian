package com.car.app.carscraporder.app.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.service.RedisService;
import com.car.app.common.util.CookieUtils;
import com.car.app.carscraporder.app.bean.UserAppBO;
import com.car.app.carscraporder.app.exception.UnloginException;
import com.car.app.carscraporder.app.service.UserService;
import com.car.app.carscraporder.app.systemparameter.SystemParameter;
import com.car.app.carscraporder.app.util.UserUtil;
import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.pojo.User;

/**
 * 
 * 类名称：AccountController   
 * 类描述：账号相关的Controller   
 * 创建人：刘子亮
 * 创建时间：2018年5月17日 上午9:48:20      
 * @version  V1.0
 *
 */
@Controller
@RequestMapping(value="/account")
@Api(value = "account-API",description="登录登出相关接口" )
public class AccountController {

	 @Autowired
	 private RedisService redisService;
	 
	 private static final ObjectMapper MAPPER = new ObjectMapper();
	 
	 @Autowired
	 private UserService userService;

	 /**
	     * 根据用户名密码登录
	     * @param username
	     * @param password
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception 
	     */
	    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
	    @ResponseBody
	    public ResultBean<Map> doLogin(@RequestParam("userName") String username,
	            @RequestParam("password") String password, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
	      
	    	UserAppBO user = this.userService.doLogin(username, password);
			System.out.println("登陆跳转返回后，得到的user为："+user);
	    	 Map<String,Object> result = new HashMap<String,Object>();
	        if (user != null) {
				System.out.println("用户的sessionid为:"+user.getSessionid());
	        	 // 登录成功
	            // 将ticket写入到cookie中
	            CookieUtils.setCookie(request, response, SystemParameter.TICKET, user.getSessionid());

				String ticket = CookieUtils.getCookieValue(request, SystemParameter.TICKET);
				System.out.println("登陆跳转返回后,得出ticket为:"+ticket);
	            result.put("sessionid", user.getSessionid());
				result.put("userid", user.getId());
	            result.put("realName", user.getRealName());
	            result.put("error", 0);
	            result.put("areaName", user.getAreaName());
	            result.put("areasids", user.getAreasids());
	            return new ResultBean<Map>(result);
	        } else {
	        	 return new ResultBean(500,null,"用户名密码错误");
	        }
	    }
	
	
	
	
	
	/**
	 * 登出系统
	 * @return  ResultBean 登出方法返回包装信息
	 * @throws Exception 
	 */
	@RequestMapping(value="logout",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "退出系统",notes = "退出系统")
	public ResultBean<Boolean> logout(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
		
		//获取redis存储session
		 String ticket = CookieUtils.getCookieValue(request, SystemParameter.TICKET);
		 if(ticket!=null){
			 redisService.del(ticket);
		 }
		 
		 
		
		//退出系统
		return new ResultBean(ResultBean.SUCCESS,null,"登出成功");
	}
	
	
	/**
	 * 
	 * @param password
	 * @param newpassword
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "setPwd",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "退出系统",notes = "退出系统")
	public ResultBean<Boolean> resetpw(
			@ApiParam(name = "password", value = "密码",required = true)
	        @RequestParam(value="password",required = true)String password,
	        @ApiParam(name = "newPassword", value = "新密密码",required = true)
	        @RequestParam(value="newPassword",required = true)String newpassword,
	        HttpServletRequest request
			) throws Exception{
		
		User user = UserUtil.getUser();
		
		if(user==null){
			throw new UnloginException("没有登录系统");
		}
		Boolean success = this.userService.resetpw(user.getLoginName(), password, newpassword,user.getId());
	
		if(success){
			String ticket = CookieUtils.getCookieValue(request, SystemParameter.TICKET);
			 if(ticket!=null){
				 redisService.del(ticket);
			 }
			
			return new ResultBean(ResultBean.SUCCESS,success,"密码修改成功");
		}else{
			return new ResultBean(ResultBean.SUCCESS,success,"密码修改失败");
		}
		
		
	}
	
	
	

	
	
	
	
	
	
	
	
	

}
