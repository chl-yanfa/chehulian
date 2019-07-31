package com.car.app.carscraporder.app.bean;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.car.app.carscraporder.bo.UserBO;

public class UserAppBO extends UserBO {

	private Map<String,List<Integer>> permission = new HashMap<String,List<Integer>>();

	private String sessionid;

	public Map<String, List<Integer>> getPermission() {
		return permission;
	}

	public void setPermission(Map<String, List<Integer>> permission) {
		this.permission = permission;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	@Override
	public String toString() {
		return "UserAppBO{" +
				"permission=" + permission +
				", sessionid='" + sessionid + '\'' +
				'}';
	}
}
