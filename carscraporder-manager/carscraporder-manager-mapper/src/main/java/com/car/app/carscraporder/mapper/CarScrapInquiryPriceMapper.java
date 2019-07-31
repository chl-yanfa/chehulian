package com.car.app.carscraporder.mapper;

import java.util.List;

import com.car.app.carscraporder.bo.CarScrapInquiryPriceBO;
import com.car.app.carscraporder.pojo.CarScrapInquiryPrice;
import com.car.app.carscraporder.vo.CarScrapInquiryPricePageVO;
import com.github.abel533.mapper.Mapper;

public interface CarScrapInquiryPriceMapper extends Mapper<CarScrapInquiryPrice> {
	public List<CarScrapInquiryPriceBO> queryPageListByWhere(CarScrapInquiryPricePageVO vo);

}
