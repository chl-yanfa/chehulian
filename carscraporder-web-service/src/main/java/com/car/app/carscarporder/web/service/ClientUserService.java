package com.car.app.carscarporder.web.service;


import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.bo.ClientBankCardBO;
import com.car.app.carscraporder.vo.ClientAddBankVO;
import com.car.app.carscraporder.vo.ClientCertificationVO;
import com.car.app.carscraporder.vo.ClientVO;

public interface ClientUserService {
	
	public ClientBO doLogin(String loginName,String password)throws Exception;
	
	
	public ClientBO queryUserByTicket(String ticket) throws Exception;

	public Boolean setPwd(String id,String oldPassword, String newPassword,String operator,String loginName)throws Exception;
	
	public Boolean getValidateCode(String phone) throws Exception;
	
	
	//--------------------------
	
	public ClientBO queryUserById(String id) throws Exception;
	
	public ClientBO queryUserByPhone(String phone) throws Exception;
	
	public Boolean doRegister(ClientVO clientVO)throws Exception;
	
	public Boolean certification(ClientCertificationVO clientVO)throws Exception;
	
	public ClientBankCardBO queryUserBankCard(String clientid) throws Exception;
	
	public Boolean addBankCard(ClientAddBankVO clientVO)throws Exception;
	
	public Boolean verifyValidateCode(String phone,String validateCode)throws Exception;
	
	public Boolean forgetPwd(String id, String newPwd,String operator)throws Exception;
	
}
