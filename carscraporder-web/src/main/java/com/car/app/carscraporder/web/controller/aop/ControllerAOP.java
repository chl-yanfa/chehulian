package com.car.app.carscraporder.web.controller.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.car.app.common.bean.ResultBean;
/**
 * AOP配置切面后拦截所有controller请求,规范Controller返回数据格式,和异常处理机制
 * 类名称：ControllerAOP   
 * 类描述：   
 * 创建人：刘子亮
 * 创建时间：2018年5月17日 上午10:14:56      
 * @version  V1.0
 *
 */
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
		
		
		
		return result;
		
	}
	
	

}
