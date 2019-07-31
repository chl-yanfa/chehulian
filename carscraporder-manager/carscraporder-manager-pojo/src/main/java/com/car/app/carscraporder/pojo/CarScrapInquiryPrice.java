package com.car.app.carscraporder.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Table(name="car_scrap_inquiry_price")
public class CarScrapInquiryPrice extends BasePojo {
	@Id
    private String id;
	
	@ApiModelProperty(value = "类型（1:整车，2:旧件）")
    private String scrapType;
	
	@ApiModelProperty(value = "车辆型号(数据来源车型号数据字典)")
	private String carModel;
	
	@ApiModelProperty(value = "车龄")
	private String carAge;

	@ApiModelProperty(value = "行驶里程")
	private String drivingMileage;

	@ApiModelProperty(value = "配件名称")
	private String partsName;
	
	@ApiModelProperty(value = "配件图片存储id")
	private Integer partsAttachmentId;

	@ApiModelProperty(value = "手机号码")
	private String contactNumber;

	@ApiModelProperty(value = "状态 0:未联系,1:已联系,2:忽略")
	private Integer status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScrapType() {
		return scrapType;
	}

	public void setScrapType(String scrapType) {
		this.scrapType = scrapType;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCarAge() {
		return carAge;
	}

	public void setCarAge(String carAge) {
		this.carAge = carAge;
	}

	public String getDrivingMileage() {
		return drivingMileage;
	}

	public void setDrivingMileage(String drivingMileage) {
		this.drivingMileage = drivingMileage;
	}

	public String getPartsName() {
		return partsName;
	}

	public void setPartsName(String partsName) {
		this.partsName = partsName;
	}

	public Integer getPartsAttachmentId() {
		return partsAttachmentId;
	}

	public void setPartsAttachmentId(Integer partsAttachmentId) {
		this.partsAttachmentId = partsAttachmentId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}