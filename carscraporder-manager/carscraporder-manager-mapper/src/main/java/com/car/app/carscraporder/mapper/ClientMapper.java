package com.car.app.carscraporder.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.bo.ClientCertificationBO;
import com.car.app.carscraporder.pojo.Client;
import com.car.app.carscraporder.vo.ClientVO;
import com.github.abel533.mapper.Mapper;

public interface ClientMapper extends Mapper<Client>{
	
	public List<ClientBO> queryClientBOByName(String loginname);
	
	public int insertVerificationCode(Map<String,Object> map);
	
	public String getValidateCode(Map<String, Object> params);
	
	public List<ClientBO> queryClientBOByNameOrPhone(@Param("nameOrPhone") String nameOrPhone);
	
	public ClientBO getClientById(String id);

	public List<ClientBO> getClientByPhone(String phone);
	
	public List<ClientCertificationBO> queryPageUserCertification(ClientVO clientVO);
	
	public ClientCertificationBO queryUserCertification(String id);

}
