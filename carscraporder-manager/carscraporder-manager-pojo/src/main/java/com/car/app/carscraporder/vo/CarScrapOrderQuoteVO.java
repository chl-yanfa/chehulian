package com.car.app.carscraporder.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class CarScrapOrderQuoteVO{
	
	private String id;

	@ApiModelProperty(value = "订单类型（1:整车，2:旧件）")
	private String orderType;
	
	@ApiModelProperty(value = "报价金额")
    private BigDecimal amount;

	@ApiModelProperty(hidden = true)
    private String operator;

	//业务员现场调整价格原因
	private String adjustWhy;
	
	List<CarScrapOrderAutopartsQuoteVO> carScrapOrderAutopartsList = new ArrayList<CarScrapOrderAutopartsQuoteVO>();

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<CarScrapOrderAutopartsQuoteVO> getCarScrapOrderAutopartsList() {
		return carScrapOrderAutopartsList;
	}

	public void setCarScrapOrderAutopartsList(List<CarScrapOrderAutopartsQuoteVO> carScrapOrderAutopartsList) {
		this.carScrapOrderAutopartsList = carScrapOrderAutopartsList;
	}

	public String getAdjustWhy() {
		return adjustWhy;
	}

	public void setAdjustWhy(String adjustWhy) {
		this.adjustWhy = adjustWhy;
	}
}
