package com.car.app.carscraporder.mapper;

import java.util.List;

import com.car.app.carscraporder.bo.ClientProblemBO;
import com.car.app.carscraporder.pojo.ClientProblem;
import com.github.abel533.mapper.Mapper;


public interface ClientProblemMapper extends Mapper<ClientProblem>{
	

	 List<ClientProblemBO>  queryPageList();
	
	 ClientProblemBO queryBOById(String id);
	
}
