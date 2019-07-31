package com.car.app.carscraporder.service;

import com.car.app.carscraporder.bo.ClientMessageBO;
import com.car.app.carscraporder.pojo.ClientMessage;
import com.github.pagehelper.PageInfo;


/**
 * 
 * 类名称：ClientMessageService 
 * 类描述：客户消息通知
 * 创建人：张婉欣
 * 创建时间：2019-01-21 15:10:42    
 * @version  V1.0
 *
 */
public interface ClientMessageService extends BaseService<ClientMessage> {
	
	public Integer queryUnReadTotal(String clientId)throws Exception;
	public PageInfo<ClientMessageBO>  queryPageList(Integer page,Integer rows)throws Exception;
	public ClientMessageBO queryBOById(String id)throws Exception;
	public int changeIsread(String id)throws Exception;
	
}
