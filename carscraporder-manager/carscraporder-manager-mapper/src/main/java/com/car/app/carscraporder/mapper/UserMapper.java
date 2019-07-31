package com.car.app.carscraporder.mapper;

import java.util.List;

import com.car.app.carscraporder.bo.FactoryQueryBO;
import org.apache.ibatis.annotations.Param;

import com.car.app.carscraporder.bo.UserBO;
import com.car.app.carscraporder.bo.UserSimpleBO;
import com.car.app.carscraporder.pojo.User;
import com.car.app.carscraporder.vo.UserVO;
import com.github.abel533.mapper.Mapper;

public interface UserMapper extends Mapper<User> {

	UserBO queryUserBOById(String id);
	
	List<UserBO> queryPageUser(UserVO paramter);

	UserBO queryUserBOByNameAndPassword(String loginname);
	
	List<UserSimpleBO> getReceiveOrderPermissionUser(@Param("permisscode")String permisscode,@Param("areid")String areid);

	List<UserSimpleBO> getAllBussinessPeople(@Param("permisscode")String permisscode);
}
