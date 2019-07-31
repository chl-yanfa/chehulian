package com.car.app.carscraporder.vo;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

public class ClientVO {
	
	private String id;
	
	@NotEmpty(message="用户名不能为空")
	@Pattern(regexp = "[0-9a-zA-Z]{4,23}", message = "用户名只能输入字母或数字")
    private String loginName;
	
	@ApiModelProperty(value = "客户姓名")
    private String userName;
	
	@NotEmpty(message="手机号不能为空")
	@ApiModelProperty(value = "手机号")
	private String phone;
	
	@ApiModelProperty(value = "密码")
	private String password;
	
	@ApiModelProperty(value = "验证码")
	private String validateCode;
	
	private Integer certificationState;
	
	private String certificationMemo;
	
	private String operator;
	
	private String creater;
	
	private String keyWord;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public Integer getCertificationState() {
		return certificationState;
	}

	public void setCertificationState(Integer certificationState) {
		this.certificationState = certificationState;
	}

	public String getCertificationMemo() {
		return certificationMemo;
	}

	public void setCertificationMemo(String certificationMemo) {
		this.certificationMemo = certificationMemo;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
}
