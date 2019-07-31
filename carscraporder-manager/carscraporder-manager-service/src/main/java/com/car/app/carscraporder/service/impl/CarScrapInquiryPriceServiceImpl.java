package com.car.app.carscraporder.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.app.carscraporder.bo.CarScrapInquiryPriceBO;
import com.car.app.carscraporder.exception.DataException;
import com.car.app.carscraporder.mapper.CarScrapInquiryPriceMapper;
import com.car.app.carscraporder.pojo.CarScrapInquiryPrice;
import com.car.app.carscraporder.pojo.CarScrapInquiryPriceAttachment;
import com.car.app.carscraporder.service.CarScrapInquiryPriceAttachmentService;
import com.car.app.carscraporder.service.CarScrapInquiryPriceService;
import com.car.app.carscraporder.util.CheckUtil;
import com.car.app.carscraporder.util.UUIDUtil;
import com.car.app.carscraporder.vo.CarScrapInquiryPricePageVO;
import com.car.app.carscraporder.vo.CarScrapInquiryPriceVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CarScrapInquiryPriceServiceImpl extends BaseServiceImpl<CarScrapInquiryPrice> implements CarScrapInquiryPriceService {
	@Autowired
	private CarScrapInquiryPriceMapper carScrapInquiryPriceMapper;
	@Autowired
	private CarScrapInquiryPriceAttachmentService carScrapInquiryPriceAttachmentService;
	
	@Override
	public Integer save(CarScrapInquiryPriceVO inquiryPriceVO) throws Exception {
		CheckUtil.notNull(inquiryPriceVO, "param.is.null");
		CarScrapInquiryPrice record = new CarScrapInquiryPrice();
		BeanUtils.copyProperties(inquiryPriceVO, record);
		//校验订单
		boolean issuccuss = valid(record);
		if(issuccuss) {
			String id = UUIDUtil.getUUID();
			record.setId(id);
			record.setStatus(0);
			List<Integer> partsAttachmentIdList = inquiryPriceVO.getPartsAttachmentIdList();
			if(partsAttachmentIdList!=null && partsAttachmentIdList.size()>0) {
				for (Integer partsAttachmentId : partsAttachmentIdList) {
					CarScrapInquiryPriceAttachment  attachment = new CarScrapInquiryPriceAttachment();
					attachment.setAttachmentId(partsAttachmentId);
					attachment.setInquiryPriceId(id);
					attachment.setIsremove(0);
					carScrapInquiryPriceAttachmentService.save(attachment);
				}
			}
		}else {
			throw new DataException("询价数据异常");
		}
		return super.save(record);
	}
	private boolean valid(CarScrapInquiryPrice record){
		CheckUtil.notNull(record, "param.is.null");
		CheckUtil.notNull(record.getScrapType(), "param.is.null");
		CheckUtil.check((Integer.parseInt(record.getScrapType())>0), "param.is.null");
		CheckUtil.check((Integer.parseInt(record.getScrapType())<3), "param.is.null");
		return true;
	}
	@Override
	public PageInfo<CarScrapInquiryPriceBO> queryPageListByWhere(Integer page, Integer rows,
			CarScrapInquiryPricePageVO paramter) throws Exception {
		PageHelper.startPage(page, rows);
		List<CarScrapInquiryPriceBO> queryPageList = carScrapInquiryPriceMapper.queryPageListByWhere(paramter);
		return new PageInfo<CarScrapInquiryPriceBO>(queryPageList);
	}
	
	

	
}
