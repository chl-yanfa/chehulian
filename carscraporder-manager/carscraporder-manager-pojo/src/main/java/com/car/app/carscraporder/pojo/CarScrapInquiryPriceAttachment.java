package com.car.app.carscraporder.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Table(name="car_scrap_inquiry_price_attachment")
public class CarScrapInquiryPriceAttachment extends BasePojo{
	@Id
    private Integer id;
	
	@ApiModelProperty(value = "附件存储id")
    private Integer attachmentId;
	
	@ApiModelProperty(value = "关联id")
	private String inquiryPriceId;

	@ApiModelProperty(value = "是否删除")
	private Integer isremove;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getInquiryPriceId() {
		return inquiryPriceId;
	}

	public void setInquiryPriceId(String inquiryPriceId) {
		this.inquiryPriceId = inquiryPriceId;
	}

	public Integer getIsremove() {
		return isremove;
	}

	public void setIsremove(Integer isremove) {
		this.isremove = isremove;
	}
	
}