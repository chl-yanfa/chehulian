package com.car.app.carscraporder.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
@Table(name="car_scrap_order_deal")
public class CarScrapOrderDeal extends BasePojo{
	
	@Id
	private String id;

	@ApiModelProperty(value = "订单号")
    private String orderNo;

	@ApiModelProperty(value = "订单类型（1:整车，2:旧件）")
    private String orderType;

	@ApiModelProperty(value = "OE号")
    private String oe;

	@ApiModelProperty(value = "车标")
    private String carBrand;

	@ApiModelProperty(value = "车辆型号")
    private String carModelNumber;

	@ApiModelProperty(value = "OE号")
    private String carFrameNumber;

	@ApiModelProperty(value = "车牌号")
    private String carNumber;

	@ApiModelProperty(value = "配件名称")
    private String partsName;

	@ApiModelProperty(value = "标准名称")
    private String partsStandardName;

	@ApiModelProperty(value = "品质")
    private String quality;

	@ApiModelProperty(value = "品相")
    private String condition;

	@ApiModelProperty(value = "车主")
    private String carOwner;

	@ApiModelProperty(value = "地址")
    private String address;

	@ApiModelProperty(value = "里程")
    private String drivingMileage;

    @ApiModelProperty(value = "车龄")
    private String carAge;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "头图")
    private Integer headImgId;

    @ApiModelProperty(value = "是否删除")
    private Integer isremove;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOe() {
		return oe;
	}

	public void setOe(String oe) {
		this.oe = oe;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarModelNumber() {
		return carModelNumber;
	}

	public void setCarModelNumber(String carModelNumber) {
		this.carModelNumber = carModelNumber;
	}

	public String getCarFrameNumber() {
		return carFrameNumber;
	}

	public void setCarFrameNumber(String carFrameNumber) {
		this.carFrameNumber = carFrameNumber;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getPartsName() {
		return partsName;
	}

	public void setPartsName(String partsName) {
		this.partsName = partsName;
	}

	public String getPartsStandardName() {
		return partsStandardName;
	}

	public void setPartsStandardName(String partsStandardName) {
		this.partsStandardName = partsStandardName;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getCarOwner() {
		return carOwner;
	}

	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDrivingMileage() {
		return drivingMileage;
	}

	public void setDrivingMileage(String drivingMileage) {
		this.drivingMileage = drivingMileage;
	}

	public String getCarAge() {
		return carAge;
	}

	public void setCarAge(String carAge) {
		this.carAge = carAge;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getHeadImgId() {
		return headImgId;
	}

	public void setHeadImgId(Integer headImgId) {
		this.headImgId = headImgId;
	}

	public Integer getIsremove() {
		return isremove;
	}

	public void setIsremove(Integer isremove) {
		this.isremove = isremove;
	}

}