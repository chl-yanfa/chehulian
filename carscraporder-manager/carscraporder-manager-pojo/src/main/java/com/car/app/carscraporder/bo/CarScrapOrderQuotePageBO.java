package com.car.app.carscraporder.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 类名称：CarScrapOrderQuotePageBO 
 * 类描述：报价列表
 * 创建人：张婉欣
 * 创建时间：2019-01-28 14:21:24    
 * @version  V1.0
 *
 */
public class CarScrapOrderQuotePageBO {

	 private String id;
	 
	 @ApiModelProperty(value = "订单编号")
	 private String orderNo;
	 
	 @ApiModelProperty(value = "订单时间")
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	 private Date orderTime;;
	 
	 @ApiModelProperty(value = "车架号")
	 private String carFrameNumber;
	 
	 @ApiModelProperty(value = "报价类型")
	 private String orderType;
	 
	 @ApiModelProperty(value = "报价类型:整车|配件")
	 private String orderTypeS;
	 
	 @ApiModelProperty(value = "整车:车牌号|旧件:配件数量")
	 private String carNumberOrPartsNum;
	 
	 @ApiModelProperty(value = "车型")
	 private String carModelNumberName;
	 
	 @ApiModelProperty(value = "委托方")
	 private String custormName;
	 
	 @ApiModelProperty(value = "订单状态")
	 private Integer orderStatus;

	 @ApiModelProperty(value = "取车人联系人")
	 private String takeCarContacts;

	 @ApiModelProperty(value = "取车联系电话")
	 private String takeCarContactNumber;

	 private String  branchOffice;

	 private String  headOffice;

	 //列表显示图片
	 private String listPicture;

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

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getCarFrameNumber() {
		return carFrameNumber;
	}

	public void setCarFrameNumber(String carFrameNumber) {
		this.carFrameNumber = carFrameNumber;
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

	public String getCarNumberOrPartsNum() {
		return carNumberOrPartsNum;
	}

	public void setCarNumberOrPartsNum(String carNumberOrPartsNum) {
		this.carNumberOrPartsNum = carNumberOrPartsNum;
	}

	public String getCarModelNumberName() {
		return carModelNumberName;
	}

	public void setCarModelNumberName(String carModelNumberName) {
		this.carModelNumberName = carModelNumberName;
	}

	public String getCustormName() {
		return custormName;
	}

	public void setCustormName(String custormName) {
		this.custormName = custormName;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTakeCarContacts() {
		return takeCarContacts;
	}

	public void setTakeCarContacts(String takeCarContacts) {
		this.takeCarContacts = takeCarContacts;
	}

	public String getTakeCarContactNumber() {
		return takeCarContactNumber;
	}

	public void setTakeCarContactNumber(String takeCarContactNumber) {
		this.takeCarContactNumber = takeCarContactNumber;
	}

	public String getBranchOffice() {
		return branchOffice;
	}

	public void setBranchOffice(String branchOffice) {
		this.branchOffice = branchOffice;
	}

	public String getHeadOffice() {
		return headOffice;
	}

	public void setHeadOffice(String headOffice) {
		this.headOffice = headOffice;
	}

	public String getListPicture() {
		return listPicture;
	}

	public void setListPicture(String listPicture) {
		this.listPicture = listPicture;
	}

	@Override
	public String toString() {
		return "CarScrapOrderQuotePageBO{" +
				"id='" + id + '\'' +
				", orderNo='" + orderNo + '\'' +
				", orderTime=" + orderTime +
				", carFrameNumber='" + carFrameNumber + '\'' +
				", orderType='" + orderType + '\'' +
				", orderTypeS='" + orderTypeS + '\'' +
				", carNumberOrPartsNum='" + carNumberOrPartsNum + '\'' +
				", carModelNumberName='" + carModelNumberName + '\'' +
				", custormName='" + custormName + '\'' +
				", orderStatus=" + orderStatus +
				", takeCarContacts='" + takeCarContacts + '\'' +
				", takeCarContactNumber='" + takeCarContactNumber + '\'' +
				", branchOffice='" + branchOffice + '\'' +
				", headOffice='" + headOffice + '\'' +
				", listPicture='" + listPicture + '\'' +
				'}';
	}
}
