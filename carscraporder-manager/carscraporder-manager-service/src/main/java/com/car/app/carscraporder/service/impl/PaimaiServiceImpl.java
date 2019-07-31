package com.car.app.carscraporder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.bo.PaimaiBO;
import com.car.app.carscraporder.mapper.PaimaiMapper;
import com.car.app.carscraporder.service.PaimaiService;

@Service
public class PaimaiServiceImpl implements PaimaiService {
	
	@Autowired
	private PaimaiMapper paimaiMapper;

	@Override
	public List<PaimaiBO> queryList() throws Exception {
		return paimaiMapper.queryList();
	}
}
