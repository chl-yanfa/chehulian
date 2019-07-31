package com.car.app.carscraporder.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.car.app.carscraporder.pojo.ClientBankcard;

import io.swagger.annotations.ApiModelProperty;

public class ClientAddBankVO extends ClientBankcard{
	
	@NotEmpty(message="用户名不能为空")
	@ApiModelProperty(value = "用户真实姓名")
	private String payeeName;
    
	@NotEmpty(message="银行卡号不能为空")
	@ApiModelProperty(value = "银行卡号")
    private String bankAccount;

	@NotEmpty(message="所属银行不能为空")
	@ApiModelProperty(value = "所属银行")
    private String bank;

	@NotEmpty(message="开户行不能为空")
	@ApiModelProperty(value = "开户行")
    private String openingBank;

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

}
