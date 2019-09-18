package com.car.app.carscraporder.applet.interceptor;

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
import com.car.app.carscraporder.applet.controller.AccoutController;
import com.car.app.carscraporder.applet.threadlocal.UserUtil;
import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.util.CookieUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserLoginHandlerInterceptor implements HandlerInterceptor{
	 @Autowired
	 private ClientUserService clientUserService;
	 
	 public static final String COOKIE_TICKET = "TT_TICKET_APPLET";
	 
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
       String ticket = CookieUtils.getCookieValue(request, AccoutController.COOKIE_TICKET,false);

       if (StringUtils.isBlank(ticket)) {
      	 ResultBean result = new ResultBean();
      	 result.setCode(201);
      	 result.setMsg("未登陆系统1");
      	 returnJson(response,result);
      	 return false;
      }

       ClientBO user = this.clientUserService.queryUserByTicket(ticket);
       
       if (user == null) {
           // 登录超时
       	 ResultBean result = new ResultBean();
       	 result.setCode(201);
       	 result.setMsg("未登陆系统2");
       	 returnJson(response,result);
       	 return false;
       }
       
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
