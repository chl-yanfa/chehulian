package com.car.app.carscraporder.bo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CarScrapOrderMyTradeInfoBO{
	
	private String id;
	private String orderType; 
	private String orderTypeS;
	private BigDecimal orderAmount;
	private String carNumber;
	private Integer partsNum;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date orderTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderTypeS() {
		return orderTypeS;
	}
	public void setOrderTypeS(String orderTypeS) {
		this.orderTypeS = orderTypeS;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public Integer getPartsNum() {
		return partsNum;
	}
	public void setPartsNum(Integer partsNum) {
		this.partsNum = partsNum;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	
}
