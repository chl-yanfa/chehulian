package com.car.app.carscraporder.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.mapper.TbCarPartsMapper;
import com.car.app.carscraporder.pojo.TbCarParts;
import com.car.app.carscraporder.service.TbCarPartsService;

import java.util.List;

@Service
public class TbCarPartsServiceImpl extends BaseServiceImpl<TbCarParts> implements
		TbCarPartsService {

	@Autowired
	private TbCarPartsMapper tbCarPartsMapper;


	@Override
	public PageInfo<TbCarParts> queryByIdPageListByWhere(int page, int rows, TbCarParts paramter) throws Exception {
		PageHelper.startPage(page, rows);
		List<TbCarParts> data = tbCarPartsMapper.queryByIdPageListByWhere(paramter);
		return new PageInfo<TbCarParts>(data);
	}
}
