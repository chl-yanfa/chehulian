package com.car.app.carscraporder.service;
import java.util.List;

import com.car.app.carscraporder.bo.FactoryQueryBO;
import com.car.app.carscraporder.bo.UserBO;
import com.car.app.carscraporder.bo.UserSimpleBO;
import com.car.app.carscraporder.pojo.User;
import com.car.app.carscraporder.vo.UserVO;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

public interface UserService extends BaseService<User> {
	
	 Integer saveUserAndRoleAndAreas(UserVO uservo)throws Exception;
	
	 Integer updateUserAndRoleAndAreas(UserVO uservo)throws Exception;

	 Integer deleteById(String id)throws Exception;

	 UserBO queryUserBOById(String id)throws Exception;
	
	 User queryById(String id)throws Exception;
	
	 PageInfo<UserBO> queryPageListUser(Integer page, Integer rows,UserVO user) throws Exception;
	
	 UserBO queryUserBOByName(String loginname)throws Exception;

	 List<UserSimpleBO> getReceiveOrderPermissionUser(String permisscode,String areid);

	 List<UserSimpleBO> getAllMoneyPeople(Integer roleNum);

	 List<UserSimpleBO> getAllBussinessPeople(@Param("permisscode")String permisscode);
}
