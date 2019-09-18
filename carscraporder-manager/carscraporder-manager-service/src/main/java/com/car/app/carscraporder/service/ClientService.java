package com.car.app.carscraporder.service;

import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.bo.ClientBankCardBO;
import com.car.app.carscraporder.bo.ClientCertificationBO;
import com.car.app.carscraporder.pojo.Client;
import com.car.app.carscraporder.vo.ClientAddBankVO;
import com.car.app.carscraporder.vo.ClientCertificationVO;
import com.car.app.carscraporder.vo.ClientVO;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 类名称：ClientService   
 * 类描述：   
 * 创建人：刘子亮
 * 创建时间：2018年10月9日 下午4:23:31      
 * @version  V1.0
 *
 */
public interface ClientService extends BaseService<Client>{
	
	 ClientBO queryClientBOByName(String loginname)throws Exception;
	
	 Boolean resetPassword(String id,String oldpassword,String newpassword,String operator)throws Exception;
	
	 Boolean validateCode(String phone) throws Exception;
	
	 ClientBO queryClientBOByNameOrPhone(String whereParam)throws Exception;
	
	 ClientBO getClientById(String id)throws Exception;
	
	 ClientBO getClientByPhone(String phone)throws Exception;
	
	 Boolean doRegister(ClientVO clientVO)throws Exception;
	
	 Boolean certification(ClientCertificationVO vo)throws Exception;
	
	 ClientBankCardBO queryUserBankCard(String clientid)throws Exception;
	
	 Boolean addBankCard(ClientAddBankVO vo)throws Exception;
	
	 String verifyValidateCode(String phone)throws Exception;
	
	 Boolean forgetPwd(String id,String newpassword,String operator)throws Exception;
	
	 PageInfo<ClientCertificationBO> queryUserCertificationList(Integer page, Integer rows,ClientVO vo) throws Exception;
	
	 ClientCertificationBO queryUserCertification(String id) throws Exception;
	
	 Boolean auditCertification(ClientVO clientVO)throws Exception;
	
}
