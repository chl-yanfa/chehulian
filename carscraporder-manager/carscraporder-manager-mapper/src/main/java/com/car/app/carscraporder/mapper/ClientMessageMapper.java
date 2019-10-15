package com.car.app.carscraporder.mapper;

import java.util.List;

import com.car.app.carscraporder.bo.ClientMessageBO;
import com.car.app.carscraporder.pojo.ClientMessage;
import com.github.abel533.mapper.Mapper;


public interface ClientMessageMapper extends Mapper<ClientMessage>{
	
	
	 Integer queryUnReadTotal(String clientId);

	 List<ClientMessageBO>  queryPageList();
	
	 ClientMessageBO queryBOById(String id);
	
	 int changeIsRead(String id);
}
