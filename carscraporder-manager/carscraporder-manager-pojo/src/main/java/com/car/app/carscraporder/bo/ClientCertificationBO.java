package com.car.app.carscraporder.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ClientCertificationBO {
	
	private String id;
	
    private String userName;
	
	private String phone;
	
	private String idCard;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date certificationSubmitTime;
	
	private String certificationState;
	
    private String frontIdCard;
	
    private String backIdCard;
	
	private String handIdCard;
	
	private String certificationMemo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getCertificationSubmitTime() {
		return certificationSubmitTime;
	}

	public void setCertificationSubmitTime(Date certificationSubmitTime) {
		this.certificationSubmitTime = certificationSubmitTime;
	}

	public String getCertificationState() {
		return certificationState;
	}

	public void setCertificationState(String certificationState) {
		this.certificationState = certificationState;
	}

	public String getFrontIdCard() {
		return frontIdCard;
	}

	public void setFrontIdCard(String frontIdCard) {
		this.frontIdCard = frontIdCard;
	}

	public String getBackIdCard() {
		return backIdCard;
	}

	public void setBackIdCard(String backIdCard) {
		this.backIdCard = backIdCard;
	}

	public String getHandIdCard() {
		return handIdCard;
	}

	public void setHandIdCard(String handIdCard) {
		this.handIdCard = handIdCard;
	}

	public String getCertificationMemo() {
		return certificationMemo;
	}

	public void setCertificationMemo(String certificationMemo) {
		this.certificationMemo = certificationMemo;
	}
	
}
