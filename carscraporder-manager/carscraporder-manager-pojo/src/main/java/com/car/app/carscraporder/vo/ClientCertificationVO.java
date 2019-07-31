package com.car.app.carscraporder.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.car.app.carscraporder.pojo.Client;

import io.swagger.annotations.ApiModelProperty;

public class ClientCertificationVO extends Client{
	
	@NotEmpty(message="用户名不能为空")
	@ApiModelProperty(value = "用户真实姓名")
    private String userName;
	
	@NotEmpty(message="身份证号不能为空")
	@ApiModelProperty(value = "身份证号")
    private String idCard;
	
	@NotEmpty(message="身份证正面图片不能为空")
	@ApiModelProperty(value = "身份证正面图片ID")
    private Integer frontIdCard;
	
	@NotEmpty(message="身份证背面图片不能为空")
	@ApiModelProperty(value = "身份证背面图片ID")
    private Integer backIdCard;
	
	@NotEmpty(message="手持身份证图片不能为空")
	@ApiModelProperty(value = "手持身份证图片ID")
	private Integer handIdCard;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getFrontIdCard() {
		return frontIdCard;
	}

	public void setFrontIdCard(Integer frontIdCard) {
		this.frontIdCard = frontIdCard;
	}

	public Integer getBackIdCard() {
		return backIdCard;
	}

	public void setBackIdCard(Integer backIdCard) {
		this.backIdCard = backIdCard;
	}

	public Integer getHandIdCard() {
		return handIdCard;
	}

	public void setHandIdCard(Integer handIdCard) {
		this.handIdCard = handIdCard;
	}
}
