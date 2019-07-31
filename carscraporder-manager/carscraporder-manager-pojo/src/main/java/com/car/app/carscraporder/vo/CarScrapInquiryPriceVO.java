package com.car.app.carscraporder.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;


public class CarScrapInquiryPriceVO{
	
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
	
	@ApiModelProperty(value = "手机号码")
	private String contactNumber;
	
	@ApiModelProperty(hidden = true)
	private String creater;
	
	@ApiModelProperty(hidden = true)
    private String operator;
	
	private List<Integer> partsAttachmentIdList;

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

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<Integer> getPartsAttachmentIdList() {
		return partsAttachmentIdList;
	}

	public void setPartsAttachmentIdList(List<Integer> partsAttachmentIdList) {
		this.partsAttachmentIdList = partsAttachmentIdList;
	}
	
	
}