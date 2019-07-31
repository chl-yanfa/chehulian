package com.car.app.carscraporder.applet.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.app.carscarporder.web.service.CarScrapOrderService;
import com.car.app.carscarporder.web.service.ClientUserService;
import com.car.app.carscarporder.web.systemparameter.SystemParameter;
import com.car.app.carscraporder.applet.threadlocal.UserUtil;
import com.car.app.carscraporder.bo.CarScrapOrderMyTradeBO;
import com.car.app.carscraporder.bo.CarScrapOrderMyTradeInfoBO;
import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.bo.ClientBankCardBO;
import com.car.app.carscraporder.vo.ClientAddBankVO;
import com.car.app.carscraporder.vo.ClientCertificationVO;
import com.car.app.carscraporder.vo.ClientVO;
import com.car.app.common.bean.PageResult;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.exception.DataException;
import com.car.app.common.service.RedisService;
import com.car.app.common.util.CookieUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import shaded.org.apache.commons.lang3.StringUtils;

@Controller
@RequestMapping(value="/account")
@Api(value = "Accout-API",description = "账户相关接口文档")
public class AccoutController {
	
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private CarScrapOrderService carScrapOrderService;

	 public static final String COOKIE_TICKET = "TT_TICKET_APPLET";
	 @Autowired
     private RedisService redisService;
	
	@RequestMapping(value = "doLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<Map<String,Object>> doLogin(@RequestParam("username") String username,
            @RequestParam("password") String password, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
      
    	ClientBO user = this.clientUserService.doLogin(username, password);
        if (user != null) {
        	// 登录成功
        	  String ticket = DigestUtils.md5Hex(user.getLoginName() + System.currentTimeMillis());
        	  String userstr = SystemParameter.MAPPER.writeValueAsString(user);
        	  //放入缓存
           	  redisService.set(SystemParameter.TICKET+ticket, userstr,60 * 30);
            
               // 将ticket写入到cookie中
              CookieUtils.setCookie(request, response, COOKIE_TICKET, ticket);
              
              Map<String,Object> map = new HashMap<String,Object>();
              map.put("state", true);
              map.put("session", ticket);
              map.put("areaid", user.getAreaid());
              map.put("realName", user.getUserName());
              map.put("userType", user.getUserType());
              map.put("phone", user.getPhone());
              map.put("businessType", user.getBusinessType());
              map.put("userId", user.getId());
              map.put("isScrapOrde", user.getIsScrapOrde());
              map.put("isPartsOrde", user.getIsPartsOrde());
            return new ResultBean<Map<String,Object>>(map);
        } else {
        	 Map<String,Object> map = new HashMap<String,Object>();
             map.put("state", false);
             map.put("session", "");
             map.put("areaid", "");
             map.put("realName", "");
             map.put("userType", "");
             map.put("phone", "");
             map.put("businessType", "");
        	 return new ResultBean<Map<String,Object>>(map);
        }
        

    }
	
	
	
	 @RequestMapping(value = "setPwd",method = RequestMethod.PUT)
		@ResponseBody
		@ApiOperation(value = "重置密码",notes = "重置密码")
		public ResultBean<Boolean> setPwd(@RequestParam(value="oldPassword" ,required=true) String oldPassword,
				@RequestParam(value="newPassword" ,required=true) String newPassword) throws Exception{
			
	    	ClientBO user = UserUtil.getUser();
	        if(user==null){
	  	        throw new DataException("未登录系统");
	         }
	       Boolean data = clientUserService.setPwd(user.getId(),oldPassword,newPassword,user.getId(),user.getLoginName());
		   return new ResultBean<Boolean>(data);
		}
	    
	    @RequestMapping(value = "getInfo",method = RequestMethod.GET)
		@ResponseBody
		@ApiOperation(value = "获取详情",notes = "获取详情")
		public ResultBean<ClientBO> getInfo() throws Exception{
			
			ClientBO user = UserUtil.getUser();
			if (user == null) {
				throw new DataException("未登录系统");
			}
			ClientBO bo = clientUserService.queryUserById(user.getId());
			return new ResultBean<ClientBO>(bo);
			
		}
	    
	    
	    @RequestMapping(value="/getValidateCode", method = RequestMethod.POST)
		@ResponseBody
		@ApiOperation(value = "获取验证码",notes = "获取验证码")
	    public ResultBean<Boolean> getVerificationCode(@RequestParam(value="phone" ,required=true) String phone) throws Exception{
	    	Boolean data = clientUserService.getValidateCode(phone);
	    	return new ResultBean<Boolean>(data); 
		}
	       
	//----------------------------
	/**
	 * doRegister 个人用户注册
	 * 
	 * @param loginName
	 * @param phone
	 * @param validateCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "doRegister", method = RequestMethod.POST)
	@ResponseBody
	public ResultBean<Boolean> doRegister(ClientVO clientVO)throws Exception {
		return new ResultBean<Boolean>(this.clientUserService.doRegister(clientVO));
	}
	
	@RequestMapping(value = "certification", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "实名认证", notes = "实名认证")
	public ResultBean<Boolean> certification(ClientCertificationVO clientVO) throws Exception {

		ClientBO user = UserUtil.getUser();
		if (user == null) {
			throw new DataException("未登录系统");
		}
		clientVO.setId(user.getId());
		clientVO.setOperator(user.getId());
		return new ResultBean<Boolean>(clientUserService.certification(clientVO));
	}
	
	@RequestMapping(value = "getBankCard", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取当前用户银行卡信息", notes = "获取当前用户银行卡信息")
	public ResultBean<ClientBankCardBO> getBankCard() throws Exception {

		ClientBO user = UserUtil.getUser();
		if (user == null) {
			throw new DataException("未登录系统");
		}
		return new ResultBean<ClientBankCardBO>(clientUserService.queryUserBankCard(user.getId()));

	}
	
	@RequestMapping(value = "addBankCard", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "添加银行卡", notes = "添加银行卡")
	public ResultBean<Boolean> addBankCard(ClientAddBankVO clientVO) throws Exception {

		ClientBO user = UserUtil.getUser();
		if (user == null) {
			throw new DataException("未登录系统");
		}
		clientVO.setClientid(user.getId());
		return new ResultBean<Boolean>(clientUserService.addBankCard(clientVO));
	}
	
	@RequestMapping(value = "myTrade", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "我的交易", notes = "我的交易")
	public ResultBean<CarScrapOrderMyTradeBO> myTrade() throws Exception {

		ClientBO user = UserUtil.getUser();
		if (user == null) {
			throw new DataException("未登录系统");
		}

		return new ResultBean<CarScrapOrderMyTradeBO>(carScrapOrderService.myTrade(user.getId()));

	}
	
	@RequestMapping(value = "myTradeInfo", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "我的交易详情", notes = "我的交易详情")
	public ResultBean<PageResult<CarScrapOrderMyTradeInfoBO>> myTradeInfo(@ApiParam(name = "page", value = "分页参数页码",required = true)@RequestParam(value="page",required = true,defaultValue="1")Integer page,
			@ApiParam(name = "rows", value = "分页参数每页数据条数",required = true)@RequestParam(value="rows",required = true,defaultValue="10")Integer rows) throws Exception {

		ClientBO user = UserUtil.getUser();
		if (user == null) {
			throw new DataException("未登录系统");
		}
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("page", page);
		map.put("rows", rows);
		map.put("clientId", user.getId());
		return new ResultBean<PageResult<CarScrapOrderMyTradeInfoBO>>(carScrapOrderService.myTradeInfo(map));

	}
	
	@RequestMapping(value = "verifyValidateCode", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "忘记密码-校验验证码", notes = "忘记密码-校验验证码")
	public ResultBean<Boolean> verifyValidateCode(@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "validateCode", required = true) String validateCode) throws Exception {

		ClientBO user = clientUserService.queryUserByPhone(phone);
		if (user==null || StringUtils.isBlank(user.getPhone())) {
			throw new DataException("无此用户");
		}
		Boolean data = clientUserService.verifyValidateCode(user.getPhone(), validateCode);
		return new ResultBean<Boolean>(data);
	}
	
	@RequestMapping(value = "forgetPwd", method = RequestMethod.PUT)
	@ResponseBody
	@ApiOperation(value = "忘记密码-更新密码", notes = "忘记密码-更新密码")
	public ResultBean<Boolean> forgetPwd(@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "newPwd", required = true) String newPwd) throws Exception {

		ClientBO user = clientUserService.queryUserByPhone(phone);
		if (user==null || StringUtils.isBlank(user.getPhone())) {
			throw new DataException("无此用户");
		}
		Boolean data = clientUserService.forgetPwd(user.getId(), newPwd, user.getId());
		return new ResultBean<Boolean>(data);
	}
}
