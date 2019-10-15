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
	
	List<ClientBO> queryClientBOByName(String loginname);
	
	int insertVerificationCode(Map<String,Object> map);
	
	String getValidateCode(Map<String, Object> params);
	
	List<ClientBO> queryClientBOByNameOrPhone(@Param("nameOrPhone") String nameOrPhone);
	
	ClientBO getClientById(String id);

	List<ClientBO> getClientByPhone(String phone);
	
	List<ClientCertificationBO> queryPageUserCertification(ClientVO clientVO);
	
	ClientCertificationBO queryUserCertification(String id);

}
