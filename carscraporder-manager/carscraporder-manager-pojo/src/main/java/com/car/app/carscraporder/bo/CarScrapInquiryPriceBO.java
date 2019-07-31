package com.car.app.carscraporder.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CarScrapInquiryPriceBO{
    private String id;
    private String scrapType;
	private String carModel;
	private String carAge;
	private String drivingMileage;
	private String partsName;
	private String contactNumber;
	private Integer status;
	private Date createtime;
	private List<String> partsUrls = new ArrayList<String>();
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public List<String> getPartsUrls() {
		return partsUrls;
	}
	public void setPartsUrls(List<String> partsUrls) {
		this.partsUrls = partsUrls;
	}
	
}