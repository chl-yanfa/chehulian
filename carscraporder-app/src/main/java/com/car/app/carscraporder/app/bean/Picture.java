package com.car.app.carscraporder.app.bean;

public class Picture {
	
	private int id;
	private String defaultUrl;
	private String carPictureName;
	private Boolean isVerify=true;
	
	
	
	
	public Picture(String defaultUrl, String carPictureName) {
		super();
		this.defaultUrl = defaultUrl;
		this.carPictureName = carPictureName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDefaultUrl() {
		return defaultUrl;
	}
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
	public String getCarPictureName() {
		return carPictureName;
	}
	public void setCarPictureName(String carPictureName) {
		this.carPictureName = carPictureName;
	}
	public Boolean getIsVerify() {
		return isVerify;
	}
	public void setIsVerify(Boolean isVerify) {
		this.isVerify = isVerify;
	}
	
	

	

}
