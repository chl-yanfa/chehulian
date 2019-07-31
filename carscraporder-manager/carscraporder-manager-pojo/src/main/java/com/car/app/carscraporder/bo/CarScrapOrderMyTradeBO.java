package com.car.app.carscraporder.bo;

import java.math.BigDecimal;

public class CarScrapOrderMyTradeBO{
	
	private Integer partTotalCount;
	private BigDecimal partTotalAmount;
	private Integer scrapCarTotalCount;
	private BigDecimal scrapCarTotalAmount;
	
	
	public Integer getPartTotalCount() {
		return partTotalCount;
	}
	public void setPartTotalCount(Integer partTotalCount) {
		this.partTotalCount = partTotalCount;
	}
	public BigDecimal getPartTotalAmount() {
		return partTotalAmount;
	}
	public void setPartTotalAmount(BigDecimal partTotalAmount) {
		this.partTotalAmount = partTotalAmount;
	}
	public Integer getScrapCarTotalCount() {
		return scrapCarTotalCount;
	}
	public void setScrapCarTotalCount(Integer scrapCarTotalCount) {
		this.scrapCarTotalCount = scrapCarTotalCount;
	}
	public BigDecimal getScrapCarTotalAmount() {
		return scrapCarTotalAmount;
	}
	public void setScrapCarTotalAmount(BigDecimal scrapCarTotalAmount) {
		this.scrapCarTotalAmount = scrapCarTotalAmount;
	}
	
	

}
