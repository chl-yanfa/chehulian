package com.car.app.carscraporder.controller.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.bo.ClientBankCardBO;
import com.car.app.carscraporder.exception.DataException;
import com.car.app.carscraporder.exception.UserNamePasswordException;
import com.car.app.carscraporder.service.ClientService;
import com.car.app.carscraporder.util.StringUtils;
import com.car.app.carscraporder.vo.ClientAddBankVO;
import com.car.app.carscraporder.vo.ClientCertificationVO;
import com.car.app.carscraporder.vo.ClientVO;
import com.car.app.common.bean.ResultBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value="/external/client")
@Api(value = "External-user-API",tags={"提供外部调用的订单相关接口,(跳过登录校验,按照自定义鉴权逻辑)"} )
public class ClientUserExternalController {
	
	@Autowired
	private ClientService  clientService;
	
	
	@RequestMapping(value="doLogin",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据用户名密码查找用户",notes = "根据用户名密码查找用户")
	public ResultBean<ClientBO> getClient(
			@ApiParam(name = "name", value = "用户名",required = true)
			@RequestParam(value="name",required = true)String  name,
			@ApiParam(name = "password", value = "用户密码",required = true)
			@RequestParam(value="password",required = true)String password) throws Exception{
		    
		    //根据用户名查询用户
			ClientBO user = clientService.queryClientBOByNameOrPhone(name);
		    if(user==null){
		    	  throw new DataException("用户名密码错误");
		      }else {
		    	  String validpassword = StringUtils.createPassword(password, user.getPasswordSalt(), 2);
		    	  String orgiPassword = user.getPassword();
		    	  if(StringUtils.equals(validpassword, orgiPassword)){
		    		  return new ResultBean<ClientBO>(user);
		            }else{
		            	throw new UserNamePasswordException("用户名密码错误");
		            }
		    
		      }
		
	}
	
	    @RequestMapping(value = "setPwd/{id}",method = RequestMethod.PUT)
		@ResponseBody
		@ApiOperation(value = "重置密码",notes = "重置密码")
		public ResultBean<Boolean> setPwd(@PathVariable("id")String id,
				@RequestParam(value="oldPassword" ,required=true) String oldPassword,
				@RequestParam(value="newPassword" ,required=true) String newPassword
				,String operator) throws Exception{
	    	
	    	
					return new ResultBean(clientService.resetPassword(id,oldPassword,newPassword,operator));
	    	
	    	
		 
	 }
	
    @RequestMapping(value = "getClientById", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据手机号查询用户", notes = "根据手机号查询用户")
    public ResultBean<ClientBO> getClientById(@RequestParam(value = "id", required = true) String id)throws Exception {
    	return new ResultBean<ClientBO>(clientService.getClientById(id));
    	
    }
    @RequestMapping(value = "getClientByPhone", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据手机号查询用户", notes = "根据手机号查询用户")
    public ResultBean<ClientBO> getClientByPhone(@RequestParam(value = "phone", required = true) String phone)throws Exception {
    	return new ResultBean<ClientBO>(clientService.getClientByPhone( phone));
    	
    }
	@RequestMapping(value = "doRegister", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "用户注册", notes = "用户注册")
	public ResultBean<Boolean> doRegister(ClientVO clientVO)throws Exception {

		return new ResultBean<Boolean>(clientService.doRegister(clientVO));

	}
	
    @RequestMapping(value = "getValidateCode",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取验证码",notes = "获取验证码")
    public ResultBean<Boolean> getValidateCode(@RequestParam(value="phone",required=true) String phone)throws Exception{
    	     return new ResultBean(clientService.validateCode(phone)); 
    }
	        

	@RequestMapping(value = "certification", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "实名认证", notes = "实名认证")
	public ResultBean<Boolean> certification(@RequestBody ClientCertificationVO vo)throws Exception {
		return new ResultBean<Boolean>(clientService.certification(vo));
	}
	
	@RequestMapping(value = "queryUserBankCard/{clientid}",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据id获取订单详情",notes = "根据id获取User详情")
	public ResultBean<ClientBankCardBO> queryUserBankCard(@PathVariable("clientid")String clientid) throws Exception{
		return new ResultBean<ClientBankCardBO>(clientService.queryUserBankCard(clientid));
		
	}

	@RequestMapping(value = "addBankCard", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "添加银行卡", notes = "添加银行卡")
	public ResultBean<Boolean> addBankCard(@RequestBody ClientAddBankVO vo)throws Exception {
		return new ResultBean<Boolean>(clientService.addBankCard(vo));
	}
	
	@RequestMapping(value = "verifyValidateCode", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询验证码", notes = "查询验证码")
	public ResultBean<String> verifyValidateCode(@RequestParam(value = "phone", required = true) String phone) throws Exception {
		return new ResultBean<String>(clientService.verifyValidateCode(phone));
	}

	@RequestMapping(value = "forgetPwd/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "忘记密码-更新密码", notes = "忘记密码-更新密码")
	public ResultBean<Boolean> forgetPwd(@PathVariable("id") String id,
			@RequestParam(value = "newPassword", required = true) String newPassword, 
			String operator)throws Exception {
		return new ResultBean<Boolean>(clientService.forgetPwd(id, newPassword, operator));
	}
	
	

}
