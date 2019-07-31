package com.car.app.carscraporder.app.service;

import java.util.List;

import com.car.app.carscraporder.bo.TbAppBannerSimpleBO;

public interface TbAppBannerService {
	
	public List<TbAppBannerSimpleBO> getAll(String type)throws Exception;

}
