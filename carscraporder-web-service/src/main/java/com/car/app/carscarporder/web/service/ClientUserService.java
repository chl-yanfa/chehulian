package com.car.app.carscarporder.web.service;


import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.bo.ClientBankCardBO;
import com.car.app.carscraporder.vo.ClientAddBankVO;
import com.car.app.carscraporder.vo.ClientCertificationVO;
import com.car.app.carscraporder.vo.ClientVO;

public interface ClientUserService {
	
	 ClientBO doLogin(String loginName,String password)throws Exception;

	 ClientBO queryUserByTicket(String ticket) throws Exception;

	 Boolean setPwd(String id,String oldPassword, String newPassword,String operator,String loginName)throws Exception;
	
	 Boolean getValidateCode(String phone) throws Exception;
	
	 ClientBO queryUserById(String id) throws Exception;
	
	 ClientBO queryUserByPhone(String phone) throws Exception;
	
	 Boolean doRegister(ClientVO clientVO)throws Exception;
	
	 Boolean certification(ClientCertificationVO clientVO)throws Exception;
	
	 ClientBankCardBO queryUserBankCard(String clientid) throws Exception;
	
	 Boolean addBankCard(ClientAddBankVO clientVO)throws Exception;
	
	 Boolean verifyValidateCode(String phone,String validateCode)throws Exception;
	
	 Boolean forgetPwd(String id, String newPwd,String operator)throws Exception;
	
}
