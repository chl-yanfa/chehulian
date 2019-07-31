package com.car.app.carscraporder.app.service;

import com.car.app.carscraporder.app.bean.UserAppBO;
import com.car.app.carscraporder.bo.UserBO;
import com.car.app.carscraporder.pojo.User;



public interface UserService{
	
	public UserAppBO doLogin(String loginName,String password)throws Exception;
	
	public UserAppBO queryUserByTicket(String ticket) throws Exception;
	
	
	public Boolean resetpw(String loginName,String password,String newpassword,String operator)throws Exception;

}
