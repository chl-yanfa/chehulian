package com.car.app.carscraporder.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;



public class CarScrapOrderAutopartsVO {
	
	
    private String id;
    
    @ApiModelProperty(value = "订单id")
    private String orderId;
	
	@ApiModelProperty(value = "配件类型")
    private Integer partsType;

	@ApiModelProperty(value = "配件名称")
    private String partsName;
	
	@ApiModelProperty(value = "配件编号")
    private String partsNum;
	
	@ApiModelProperty(value = "配件数量")
    private Integer partsCount;
	
	@ApiModelProperty(value = "纯拆")
    private String isDismantle;
	
	@ApiModelProperty(value = "碰撞")
    private String isCollision;
	
	@ApiModelProperty(value = "自然磨损")
    private String isWear;
	
	@ApiModelProperty(value = "水淹")
    private String isFlood;
	
	@ApiModelProperty(value = "配件备注")
    private String remark;
	
	private String qrPic;
	
	@ApiModelProperty(value = "无法接收原因")
	private Integer unableReceiveReason;
	
	@ApiModelProperty(value = "配件金额")
    private BigDecimal amount;
	
	@ApiModelProperty(value = "数据操作人记录")
	private String operator;
	private String creater;
	
	//"申请订单客户类型（1：各人 2：大客户）")
	@ApiModelProperty(hidden = true)
	private String clientType;
	
	
	private List<CarScrapOrderPictureVO> pictures = new ArrayList<CarScrapOrderPictureVO>();
	
	
	

	public Integer getUnableReceiveReason() {
		return unableReceiveReason;
	}

	public void setUnableReceiveReason(Integer unableReceiveReason) {
		this.unableReceiveReason = unableReceiveReason;
	}

	public String getQrPic() {
		return qrPic;
	}

	public void setQrPic(String qrPic) {
		this.qrPic = qrPic;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<CarScrapOrderPictureVO> getPictures() {
		return pictures;
	}

	public void setPictures(List<CarScrapOrderPictureVO> pictures) {
		this.pictures = pictures;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Integer getPartsType() {
		return partsType;
	}

	public void setPartsType(Integer partsType) {
		this.partsType = partsType;
	}

	public String getPartsName() {
		return partsName;
	}

	public void setPartsName(String partsName) {
		this.partsName = partsName;
	}

	public String getPartsNum() {
		return partsNum;
	}

	public void setPartsNum(String partsNum) {
		this.partsNum = partsNum;
	}

	public Integer getPartsCount() {
		return partsCount;
	}

	public void setPartsCount(Integer partsCount) {
		this.partsCount = partsCount;
	}

	public String getIsDismantle() {
		return isDismantle;
	}

	public void setIsDismantle(String isDismantle) {
		this.isDismantle = isDismantle;
	}

	public String getIsCollision() {
		return isCollision;
	}

	public void setIsCollision(String isCollision) {
		this.isCollision = isCollision;
	}

	public String getIsWear() {
		return isWear;
	}

	public void setIsWear(String isWear) {
		this.isWear = isWear;
	}

	public String getIsFlood() {
		return isFlood;
	}

	public void setIsFlood(String isFlood) {
		this.isFlood = isFlood;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}


}
