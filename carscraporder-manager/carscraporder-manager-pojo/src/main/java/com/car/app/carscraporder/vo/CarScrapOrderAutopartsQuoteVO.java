package com.car.app.carscraporder.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;



public class CarScrapOrderAutopartsQuoteVO {
	
	
    private String id;
    
    @ApiModelProperty(value = "订单id")
    private String orderId;
	
    @ApiModelProperty(value = "报价金额")
    private BigDecimal amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	

}
