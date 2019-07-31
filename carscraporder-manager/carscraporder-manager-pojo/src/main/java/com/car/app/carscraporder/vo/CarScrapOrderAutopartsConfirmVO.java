package com.car.app.carscraporder.vo;

import io.swagger.annotations.ApiModelProperty;



public class CarScrapOrderAutopartsConfirmVO {
	
	
    private String id;
    
    @ApiModelProperty(value = "订单id")
    private String orderId;
	
	@ApiModelProperty(value = "报价是否接受(0:接受,1:不接受)")
    private Integer amountIsaccept;

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

	public Integer getAmountIsaccept() {
		return amountIsaccept;
	}

	public void setAmountIsaccept(Integer amountIsaccept) {
		this.amountIsaccept = amountIsaccept;
	}

}
