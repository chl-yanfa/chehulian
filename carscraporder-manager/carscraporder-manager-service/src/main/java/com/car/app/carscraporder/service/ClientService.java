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
	
	public ClientBO queryClientBOByName(String loginname)throws Exception;
	
	public Boolean resetPassword(String id,String oldpassword,String newpassword,String operator)throws Exception;
	
	public Boolean validateCode(String phone) throws Exception;
	
	public ClientBO queryClientBOByNameOrPhone(String whereParam)throws Exception;
	
	public ClientBO getClientById(String id)throws Exception;
	
	public ClientBO getClientByPhone(String phone)throws Exception;
	
	public Boolean doRegister(ClientVO clientVO)throws Exception;
	
	public Boolean certification(ClientCertificationVO vo)throws Exception;
	
	public ClientBankCardBO queryUserBankCard(String clientid)throws Exception;
	
	public Boolean addBankCard(ClientAddBankVO vo)throws Exception;
	
	public String verifyValidateCode(String phone)throws Exception;
	
	public Boolean forgetPwd(String id,String newpassword,String operator)throws Exception;
	
	public PageInfo<ClientCertificationBO> queryUserCertificationList(Integer page, Integer rows,ClientVO vo) throws Exception;
	
	public ClientCertificationBO queryUserCertification(String id) throws Exception;
	
	public Boolean auditCertification(ClientVO clientVO)throws Exception;
	
}
