package com.car.app.carscraporder.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.mapper.TbCarPartsCategoryMapper;
import com.car.app.carscraporder.pojo.TbCarPartsCategory;
import com.car.app.carscraporder.service.TbCarPartsCategoryService;

import java.util.List;

@Service
public class TbCarPartsCategoryServiceImpl extends BaseServiceImpl<TbCarPartsCategory> implements
		TbCarPartsCategoryService {
	
	@Autowired
	private TbCarPartsCategoryMapper tbCarPartsCategoryMapper;


	@Override
	public PageInfo<TbCarPartsCategory> queryMyPageListByWhere(int page, int rows, TbCarPartsCategory paramter) throws Exception {
		PageHelper.startPage(page, rows);
		List<TbCarPartsCategory> data = tbCarPartsCategoryMapper.queryMyPageListByWhere(paramter);
		return new PageInfo<TbCarPartsCategory>(data);
	}
}
