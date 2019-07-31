package com.car.app.carscraporder.app.service.imp;

import org.springframework.stereotype.Service;

import com.car.app.carscarporder.web.service.impl.CarAttachmentServiceImpl;
import com.car.app.carscraporder.app.service.CarAttachmentAppService;

@Service
public class CarAttachmentAppServiceImpl extends CarAttachmentServiceImpl implements CarAttachmentAppService {
	
	

	public Boolean delete(Integer id) throws Exception{
		return super.delete(id);
	}

}
