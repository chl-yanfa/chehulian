package com.car.app.carscraporder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.bo.ClientProblemBO;
import com.car.app.carscraporder.mapper.ClientProblemMapper;
import com.car.app.carscraporder.pojo.ClientProblem;
import com.car.app.carscraporder.service.ClientProblemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ClientProblemServiceImpl extends BaseServiceImpl<ClientProblem> 
		implements ClientProblemService {
	
	@Autowired
	private ClientProblemMapper clientProblemMapper;
	
	@Override
	public PageInfo<ClientProblemBO>  queryPageList(Integer page,Integer rows)throws Exception{
		PageHelper.startPage(page, rows);
		List<ClientProblemBO> data = clientProblemMapper.queryPageList();
		return new PageInfo<ClientProblemBO>(data);
	}
	
	@Override
	public ClientProblemBO queryBOById(String id) throws Exception{
		//查询订单信息
		return clientProblemMapper.queryBOById(id);
		
	}
	
	
}
