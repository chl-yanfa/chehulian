package com.car.app.carscraporder.service;

import com.car.app.carscraporder.bo.CarScrapInquiryPriceBO;
import com.car.app.carscraporder.pojo.CarScrapInquiryPrice;
import com.car.app.carscraporder.vo.CarScrapInquiryPricePageVO;
import com.car.app.carscraporder.vo.CarScrapInquiryPriceVO;
import com.github.pagehelper.PageInfo;

public interface CarScrapInquiryPriceService extends BaseService<CarScrapInquiryPrice>{
	
	public Integer save(CarScrapInquiryPriceVO record)throws Exception ;
	public PageInfo<CarScrapInquiryPriceBO> queryPageListByWhere(Integer page,Integer rows,CarScrapInquiryPricePageVO paramter)throws Exception;

}
