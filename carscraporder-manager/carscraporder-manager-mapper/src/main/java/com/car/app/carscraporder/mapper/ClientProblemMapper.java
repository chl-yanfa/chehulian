package com.car.app.carscraporder.mapper;

import java.util.List;

import com.car.app.carscraporder.bo.ClientProblemBO;
import com.car.app.carscraporder.pojo.ClientProblem;
import com.github.abel533.mapper.Mapper;


public interface ClientProblemMapper extends Mapper<ClientProblem>{
	

	public List<ClientProblemBO>  queryPageList();
	
	public ClientProblemBO queryBOById(String id);
	
}
