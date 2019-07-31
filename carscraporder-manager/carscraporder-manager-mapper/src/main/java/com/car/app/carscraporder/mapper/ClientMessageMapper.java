package com.car.app.carscraporder.mapper;

import java.util.List;

import com.car.app.carscraporder.bo.ClientMessageBO;
import com.car.app.carscraporder.pojo.ClientMessage;
import com.github.abel533.mapper.Mapper;


public interface ClientMessageMapper extends Mapper<ClientMessage>{
	
	
	public Integer queryUnReadTotal(String clientId);

	public List<ClientMessageBO>  queryPageList();
	
	public ClientMessageBO queryBOById(String id);
	
	public int changeIsRead(String id);
}
