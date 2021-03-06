package com.car.app.carscraporder.bo;

public class UserSimpleBO {
	
	 private String id;
	
	 private String realName;

	 private String contactPhone;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Override
	public String toString() {
		return "UserSimpleBO{" +
				"id='" + id + '\'' +
				", realName='" + realName + '\'' +
				", contactPhone='" + contactPhone + '\'' +
				'}';
	}
}
