package com.car.app.carscraporder.app.interceptor;
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

import com.car.app.carscraporder.app.bean.UserAppBO;
import com.car.app.carscraporder.app.exception.UnloginException;
import com.car.app.carscraporder.app.service.UserService;
import com.car.app.carscraporder.app.systemparameter.SystemParameter;
import com.car.app.carscraporder.app.util.UserUtil;
import com.car.app.carscraporder.bo.UserBO;
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
public class UserHandlerInterceptor  implements HandlerInterceptor{
	
	Logger logger = LoggerFactory.getLogger(UserHandlerInterceptor.class);
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private UserService userService;

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
		
		System.out.println("请求的url为:"+request.getRequestURI());
		
		// 如何检查用户是否登录
        String ticket = CookieUtils.getCookieValue(request, SystemParameter.TICKET);
		System.out.println("nrz_demo_cookie"+ticket);
        if (StringUtils.isBlank(ticket)) {
        	 ResultBean result = new ResultBean();
        	 result.setCode(201);
        	 result.setMsg("未登陆系统");
        	 returnJson(response,result);
        	 return false;
        }

        UserAppBO user = this.userService.queryUserByTicket(ticket);
		System.out.println("nrz_demo"+user);
        if (user == null) {
            // 登录超时
        	 ResultBean result = new ResultBean();
        	 result.setCode(201);
        	 result.setMsg("未登陆系统");
        	 returnJson(response,result);
        	 return  false;
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