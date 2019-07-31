package com.car.app.carscraporder.app.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.impl.TbCarPartsCategoryServiceImpl;
import com.car.app.carscraporder.app.service.TbCarPartsCategoryAppService;
import com.car.app.carscraporder.pojo.TbCarPartsCategory;

@Service
public class TbCarPartsCategoryAppServiceImpl extends TbCarPartsCategoryServiceImpl implements TbCarPartsCategoryAppService {

	@Override
	public List<TbCarPartsCategory> getAll() throws Exception {
		// TODO Auto-generated method stub
		return super.getAll();
	}

}
