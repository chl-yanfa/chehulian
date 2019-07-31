package com.car.app.carscraporder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.bo.ClientMessageBO;
import com.car.app.carscraporder.mapper.ClientMessageMapper;
import com.car.app.carscraporder.pojo.ClientMessage;
import com.car.app.carscraporder.service.ClientMessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ClientMessageServiceImpl extends BaseServiceImpl<ClientMessage> 
		implements ClientMessageService {
	
	@Autowired
	private ClientMessageMapper clientMessageMapper;
	
	
	/**
	 * 根据客户id获取未读消息条数
	 * @param clientId
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Integer queryUnReadTotal(String clientId) throws Exception{
		//查询订单信息
		return clientMessageMapper.queryUnReadTotal(clientId);
		
	}
	@Override
	public PageInfo<ClientMessageBO>  queryPageList(Integer page,Integer rows)throws Exception{
		PageHelper.startPage(page, rows);
		
		List<ClientMessageBO> data = clientMessageMapper.queryPageList();
		return new PageInfo<ClientMessageBO>(data);
		
	}
	
	/**
	 * 根据消息id获取消息详情
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@Override
	public ClientMessageBO queryBOById(String id) throws Exception{
		//查询订单信息
		return clientMessageMapper.queryBOById(id);
		
	}

	@Override
	public int changeIsread(String id) throws Exception {
		return clientMessageMapper.changeIsRead(id);
	}
	
	
}
