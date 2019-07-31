package com.car.app.carscraporder.bo;

import java.math.BigDecimal;

import com.car.app.carscraporder.pojo.Client;

public class ClientBO extends Client{
	
	private Integer areaid;
	private String areaName;
	private String customerName;
	private String bankAccount;
	private String bank;
	private String openingBank;
	private BigDecimal walletPledge;		//保证金
	private BigDecimal walletPledgeFreeze;	//冻结保证金
	private Integer isScrapOrde;		//整车:0:暂无订单,1:有订单
	private Integer isPartsOrde;		//旧件:0:暂无订单,1:有订单
	private String phone;
	
	
	
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public Integer getAreaid() {
		return areaid;
	}
	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	public BigDecimal getWalletPledge() {
		return walletPledge;
	}
	public void setWalletPledge(BigDecimal walletPledge) {
		this.walletPledge = walletPledge;
	}
	public BigDecimal getWalletPledgeFreeze() {
		return walletPledgeFreeze;
	}
	public void setWalletPledgeFreeze(BigDecimal walletPledgeFreeze) {
		this.walletPledgeFreeze = walletPledgeFreeze;
	}
	public Integer getIsScrapOrde() {
		return isScrapOrde;
	}
	public void setIsScrapOrde(Integer isScrapOrde) {
		this.isScrapOrde = isScrapOrde;
	}
	public Integer getIsPartsOrde() {
		return isPartsOrde;
	}
	public void setIsPartsOrde(Integer isPartsOrde) {
		this.isPartsOrde = isPartsOrde;
	}

	@Override
	public String getPhone() {
		return phone;
	}

	@Override
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "ClientBO{" +
				"areaid=" + areaid +
				", areaName='" + areaName + '\'' +
				", customerName='" + customerName + '\'' +
				", bankAccount='" + bankAccount + '\'' +
				", bank='" + bank + '\'' +
				", openingBank='" + openingBank + '\'' +
				", walletPledge=" + walletPledge +
				", walletPledgeFreeze=" + walletPledgeFreeze +
				", isScrapOrde=" + isScrapOrde +
				", isPartsOrde=" + isPartsOrde +
				", phone='" + phone + '\'' +
				'}';
	}
}
