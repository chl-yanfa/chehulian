package com.car.app.carscarporder.web.service;



import com.car.app.carscraporder.bo.ClientMessageBO;
import com.car.app.common.bean.PageResult;

public interface MessageService {
	
	public PageResult<ClientMessageBO> getMessagePageList(Integer page,Integer rows,String clientId)throws Exception;
	
	public ClientMessageBO getMessageById(String messageId)throws Exception;

	public Integer getMessageUnReadTotal(String clientId)throws Exception;
 
}
