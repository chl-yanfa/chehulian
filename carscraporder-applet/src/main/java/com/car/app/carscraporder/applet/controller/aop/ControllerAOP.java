package com.car.app.carscraporder.applet.controller.aop;

import javax.security.auth.login.AccountException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.car.app.carscraporder.web.bean.CheckException;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.exception.DataException;
import com.car.app.common.exception.DataExistException;
import com.car.app.common.exception.UnloginException;
import com.car.app.common.exception.UserNamePasswordException;

public class ControllerAOP {
	private static final Logger logger = LoggerFactory.getLogger(ControllerAOP.class);
	
	/**
	 * �?  com.jbr365.app.controller下所用的类的�?有public方法都做拦截 
	 * @param point
	 * @return
	 */
	public Object handlerControllerMethod(ProceedingJoinPoint point){
		long startTime = System.currentTimeMillis();
		 ResultBean<?>  result = null;
		 try{
			 result = (ResultBean<?>)point.proceed();
			 logger.info(point.getSignature()+"use time"+(System.currentTimeMillis()-startTime));
			 
		 }catch(Throwable e){
			 e.printStackTrace();
			 result =handlerException(point,e);
		 }
		return result;
	}
	/**
	 * 拦截 目标抛出异常统一处理
	 * @param point
	 * @param e
	 * @return
	 */
	public ResultBean<?> handlerException(ProceedingJoinPoint point,Throwable e){
		logger.error(e.getMessage());
		ResultBean<?>  result = new ResultBean(e);
		
		   //密码错误
		  if (e instanceof UnloginException) {
			  result.setCode(201);
			  result.setMsg("未登录系统");
            //账号不存在
           }
		  else if(e instanceof CheckException) {
			  result.setCode(202);
			  result.setMsg("请求参数错误");
            //账号不存在
           } else if(e instanceof AccountException) {
 			  result.setCode(203);
 			  result.setMsg("账号错误");
             //账号不存在
            } else if(e instanceof DataException) {
  			  result.setCode(204);
  			  result.setMsg("数据错误 "+e.getMessage());
              //账号不存在
            }else if(e instanceof UserNamePasswordException) {
     			  result.setCode(205);
      			  result.setMsg("用户名密码错误");
                  //账号不存在
             }else if(e instanceof DataExistException) {
     			  result.setCode(209);
      			  result.setMsg("数据已存在 "+e.getMessage());
                  //账号不存在
             }else {
        	  result.setCode(500);
        	  result.setMsg("未知异常");
          }
		return result;
	}
}
	