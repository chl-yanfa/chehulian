package com.car.app.carscarporder.web.service;



import com.car.app.carscraporder.bo.ClientProblemBO;
import com.car.app.common.bean.PageResult;

public interface ProblemService {
	
	 PageResult<ClientProblemBO> getProblemPageList(Integer page,Integer rows)throws Exception;
	
	 ClientProblemBO getProblemById(String problemId)throws Exception;
 
}
