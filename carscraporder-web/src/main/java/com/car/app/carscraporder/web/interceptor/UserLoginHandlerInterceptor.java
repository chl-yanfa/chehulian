package com.car.app.carscraporder.web.interceptor;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.car.app.carscarporder.web.service.ClientUserService;
import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.pojo.User;
import com.car.app.carscraporder.web.bean.ClientUser;
import com.car.app.carscraporder.web.controller.AccoutController;
import com.car.app.carscraporder.web.threadlocal.UserUtil;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.util.CookieUtils;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * 类名称：UserHandlerInterceptor   
 * 类描述：  登录后将用户信息放入 ThreadLocal和slf4j的变量MDC中，ThreadLocal为后续service方法记录操作人。MDC记录帮助日志记录现场操作人
 * 创建人：刘子亮
 * 创建时间：2018年5月17日 上午9:59:23      
 * @version  V1.0
 *
 */
public class UserLoginHandlerInterceptor  implements HandlerInterceptor{
	 @Autowired
	 private ClientUserService clientUserService;
	 
	 private static final ObjectMapper MAPPER = new ObjectMapper();
		
	 Logger logger = LoggerFactory.getLogger(UserLoginHandlerInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// 由于tomcat线程重用，记得清空	
		   clearAllUserInfo();
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		// 如何检查用户是否登录
        String ticket = CookieUtils.getCookieValue(request, AccoutController.COOKIE_TICKET);

        if (StringUtils.isBlank(ticket)) {
         	 ResultBean result = new ResultBean();
         	 result.setCode(201);
         	 result.setMsg("未登陆系统123");
         	 returnJson(response,result);
         	 return false;
         	
         }
         ClientBO user = this.clientUserService.queryUserByTicket(ticket);
        
        if (user == null) {
            // 登录超时
        	 ResultBean result = new ResultBean();
        	 result.setCode(201);
        	 result.setMsg("未登陆系统567");
        	 returnJson(response,result);
        	 return  false;
        }
//222        
   

//333        
//    	ClientBO user = new ClientBO();
//		user.setAreaid(2);
//		user.setAreaName("北京");
//		user.setId("2193674800c24d899d405b4c64f07412");
//		user.setCustomerName("人保-北分");
//		user.setUserName("chengjiex");
//		user.setUserType("2");
//		user.setWeixincode("superadmin");
//		user.setBusinessType("3");
//444
		
        //将user保存到ThreadLocal中
        UserUtil.setUser(user);
        
       
		
		// 语言信息
		String locale = getLocaleFromCookies(request);
		
		// 放入到threadlocal，同一个线程任何地方都可以拿出来
		if (locale != null) {
			 UserUtil.setLocale(locale);
				}
        return true;
	}
	
	

	private void returnJson(HttpServletResponse response, ResultBean result) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
        	
        	
            writer = response.getWriter();
            writer.print(MAPPER.writeValueAsString(result));
 
        } catch (IOException e) {
            logger.error("response error",e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
	
	private String getLocaleFromCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return null;
		}

		for (int i = 0; i < cookies.length; i++) {
			if (UserUtil.KEY_LANG.equals(cookies[i].getName())) {
				return cookies[i].getValue();
			}
		}

		return null;
	}
	
	

	private void clearAllUserInfo() {
		UserUtil.clearAllUserInfo();
	}
	
	


}
