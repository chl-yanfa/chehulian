package com.car.app.carscraporder.service;

import com.car.app.carscraporder.bo.ClientProblemBO;
import com.car.app.carscraporder.pojo.ClientProblem;
import com.github.pagehelper.PageInfo;



public interface ClientProblemService extends BaseService<ClientProblem> {
	
	 PageInfo<ClientProblemBO>  queryPageList(Integer page,Integer rows)throws Exception;

	 ClientProblemBO queryBOById(String id)throws Exception;
	
}
