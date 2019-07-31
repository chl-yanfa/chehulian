package com.car.app.carscraporder.app.service.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.impl.TbCarPartsServiceImpl;
import com.car.app.carscraporder.app.service.TbCarPartsAppService;
import com.car.app.carscraporder.pojo.TbCarParts;
import com.car.app.carscraporder.pojo.TbCarPartsCategory;

@Service
public class TbCarPartsAppServiceImpl extends TbCarPartsServiceImpl implements TbCarPartsAppService {

	@Override
	public List<TbCarParts> getAllByCategory(Integer cacegoryId) throws Exception{
		
		return super.getAllByCategory(cacegoryId);
	}

}
