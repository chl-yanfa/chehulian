package com.car.app.carscraporder.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CarScrapInquiryPricePageVO{
	
    private String scrapType;
	private String carModel;
	private String keywords;
	private String drivingMileage;
	private String partsName;
	private String contactNumber;
	private Integer status;
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
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
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}