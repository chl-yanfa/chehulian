package com.car.app.carscraporder.vo;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class CarScrapOrderConfirmAmountVO{
	
	private String id;
	
	@ApiModelProperty(value = "订单类型（1:整车，2:旧件）")
	private String orderType;
	
	@ApiModelProperty(value = "报价是否接受(0:接受,1:不接受)")
    private Integer amountIsaccept;
	
	@ApiModelProperty(hidden = true)
    private String operator;
	
	List<CarScrapOrderAutopartsConfirmVO> carScrapOrderAutopartsList = new ArrayList<CarScrapOrderAutopartsConfirmVO>();

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

	public Integer getAmountIsaccept() {
		return amountIsaccept;
	}

	public void setAmountIsaccept(Integer amountIsaccept) {
		this.amountIsaccept = amountIsaccept;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<CarScrapOrderAutopartsConfirmVO> getCarScrapOrderAutopartsList() {
		return carScrapOrderAutopartsList;
	}

	public void setCarScrapOrderAutopartsList(List<CarScrapOrderAutopartsConfirmVO> carScrapOrderAutopartsList) {
		this.carScrapOrderAutopartsList = carScrapOrderAutopartsList;
	}
	
	


}
