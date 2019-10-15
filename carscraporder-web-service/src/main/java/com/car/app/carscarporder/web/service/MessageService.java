package com.car.app.carscarporder.web.service;



import com.car.app.carscraporder.bo.ClientMessageBO;
import com.car.app.common.bean.PageResult;

public interface MessageService {
	
	 PageResult<ClientMessageBO> getMessagePageList(Integer page,Integer rows,String clientId)throws Exception;
	
	 ClientMessageBO getMessageById(String messageId)throws Exception;

	 Integer getMessageUnReadTotal(String clientId)throws Exception;
 
}
