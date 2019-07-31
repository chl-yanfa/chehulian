package com.car.app.carscraporder.app.service;

import java.util.List;

import com.car.app.carscraporder.pojo.Permission;

public interface PermissionService {
	
	
	
	public List<Permission> getPermissionByCode(String code)throws Exception;

}
