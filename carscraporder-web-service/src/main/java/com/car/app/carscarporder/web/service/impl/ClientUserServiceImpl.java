package com.car.app.carscarporder.web.service.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.car.app.carscarporder.web.util.DemoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.car.app.carscarporder.web.service.ClientUserService;
import com.car.app.carscarporder.web.systemparameter.SystemParameter;
import com.car.app.carscraporder.bo.ClientBO;
import com.car.app.carscraporder.bo.ClientBankCardBO;
import com.car.app.carscraporder.vo.ClientAddBankVO;
import com.car.app.carscraporder.vo.ClientCertificationVO;
import com.car.app.carscraporder.vo.ClientVO;
import com.car.app.common.bean.HttpResult;
import com.car.app.common.bean.ResultBean;
import com.car.app.common.exception.DataException;
import com.car.app.common.exception.DataExistException;
import com.car.app.common.exception.NetException;
import com.car.app.common.exception.UserNamePasswordException;
import com.car.app.common.service.ApiService;
import com.car.app.common.service.RedisService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClientUserServiceImpl implements ClientUserService {

    @Autowired
    private ApiService apiService;
    @Autowired
    private RedisService redisService;

    @Value("${UNIFIED_EXTERNAL_URI}")
    private String UNIFIED_EXTERNAL_URI;

    @Value("${CLIENT_DO_LOGON}")
    private String CLIENT_DO_LOGON;

    @Value("${USER_RESET_PASSWORD}")
    private String USER_RESET_PASSWORD;

    @Value("${GET_VALIDATE_CODE}")
    private String GET_VALIDATE_CODE;

    @Value("${CLIENT_DO_REGISTER_URL}")
    private String CLIENT_DO_REGISTER_URL;

    @Value("${CLIENT_CERTIFICATION_URL}")
    private String CLIENT_CERTIFICATION_URL;

    @Value("${QUERUY_CLIENT_BANKCARD_URL}")
    private String QUERUY_CLIENT_BANKCARD_URL;

    @Value("${CLIENT_ADDBANKCARD_URL}")
    private String CLIENT_ADDBANKCARD_URL;

    @Value("${USER_VERIFY_VALIDATECODE_URL}")
    private String USER_VERIFY_VALIDATECODE_URL;

    @Value("${USER_FORGET_PASSWORD}")
    private String USER_FORGET_PASSWORD;

    @Value("${CLIENT_GETUSER_BYPHONE_URL}")
    private String CLIENT_GETUSER_BYPHONE_URL;

    @Value("${CLIENT_GETUSER_BYID_URL}")
    private String CLIENT_GETUSER_BYID_URL;


    private static final ObjectMapper MAPPER = new ObjectMapper();

    public ClientBO doLogin(String loginName, String password) throws Exception {

        Map<String, Object> paramter = new HashMap<String, Object>();
        paramter.put("name", loginName);
        paramter.put("password", password);
        HttpResult httpResult = this.apiService.doPost(UNIFIED_EXTERNAL_URI + CLIENT_DO_LOGON, paramter);

        if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = SystemParameter.MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                String userinfo = jsonNode.path("data").toString();

                // 获取查询信息
                ClientBO user = SystemParameter.MAPPER.readValue(jsonNode.path("data").toString(), ClientBO.class);

                return user;
            } else {

                ResultBean bean = SystemParameter.MAPPER.readValue(jsonData, ResultBean.class);
            }
        }

        return null;

    }

    @Override
    public ClientBO queryUserByTicket(String ticket) throws Exception {
        //查询缓存中是否存在ticket的数�?
        String key = SystemParameter.TICKET + ticket;
        String value = this.redisService.get(key);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        ClientBO user = SystemParameter.MAPPER.readValue(value, ClientBO.class);

        // 刷新redis中的数据的生存时间
        this.redisService.expire(key, 3600*24*30);
//		ClientBO user = new ClientBO();
//		user.setAreaid(2);
//		user.setAreaName("北京");
//		user.setId("2193674800c24d899d405b4c64f07412");
//		user.setCustomerName("人保-北分");
//		user.setUserName("chengjiex");
//		user.setUserType("2");
//		user.setWeixincode("superadmin");
//		user.setBusinessType("3");
        return user;

    }

    @Override
    public Boolean setPwd(String id, String oldPassword,
                          String newPassword, String operator, String loginName) throws Exception {

        String resetPasswordUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI + USER_RESET_PASSWORD, id);

        Map<String, String> paramter = new HashMap<>();

        paramter.put("oldPassword", oldPassword);
        paramter.put("newPassword", newPassword);
        paramter.put("operator", operator);

        HttpResult httpResult = this.apiService.doPut(resetPasswordUrl, paramter);

        if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                //顺带更新件交易密码
                DemoUtil.TestResetPwd(loginName,newPassword);
                // 订单创建成功
                return jsonNode.get("data").asBoolean();
            } else if (jsonNode.has("code") && jsonNode.get("code").asInt() == 204) {
                throw new DataException("数据异常");
            } else if (jsonNode.has("code") && jsonNode.get("code").asInt() == 205) {
                throw new UserNamePasswordException("密码输入错误");
            } else {
                throw new Exception();
            }
        }
        return false;
    }

    @Override
    public Boolean getValidateCode(String phone) throws Exception {
        Map<String, Object> paramter = new HashMap<>();
        paramter.put("phone", phone);
        HttpResult httpResult = this.apiService.doPost(UNIFIED_EXTERNAL_URI + GET_VALIDATE_CODE, paramter);
        if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                return jsonNode.get("data").asBoolean();
            } else {
                return false;
            }
        }
        return false;
    }

    //-----------------------------------
    @Override
    public ClientBO queryUserById(String id) throws Exception {
        Map<String, Object> paramter = new HashMap<String, Object>();
        paramter.put("id", id);
        HttpResult httpResult = this.apiService.doPost(UNIFIED_EXTERNAL_URI + CLIENT_GETUSER_BYID_URL, paramter);
        if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = SystemParameter.MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                // 获取查询信息
                ClientBO user = SystemParameter.MAPPER.readValue(jsonNode.path("data").toString(), ClientBO.class);

                return user;
            }
        }
        return null;
    }

    @Override
    public ClientBO queryUserByPhone(String phone) throws Exception {
        Map<String, Object> paramter = new HashMap<String, Object>();
        paramter.put("phone", phone);
        HttpResult httpResult = this.apiService.doPost(UNIFIED_EXTERNAL_URI + CLIENT_GETUSER_BYPHONE_URL, paramter);
        if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = SystemParameter.MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                // 获取查询信息
                ClientBO user = SystemParameter.MAPPER.readValue(jsonNode.path("data").toString(), ClientBO.class);

                return user;
            }
        }
        return null;
    }

    @Override
    public Boolean doRegister(ClientVO clientVO) throws Exception {
        JSONObject json = (JSONObject) JSON.toJSON(clientVO);
        Map<String, Object> paramter = json;
        HttpResult httpResult = this.apiService.doPost(UNIFIED_EXTERNAL_URI + CLIENT_DO_REGISTER_URL, paramter);
        if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                return jsonNode.get("data").asBoolean();
            } else if (jsonNode.has("code") && jsonNode.get("code").asInt() == 202) {
                throw new DataException("用户名只能输入字母或数字");
            } else if (jsonNode.has("code") && jsonNode.get("code").asInt() == 204) {
                throw new DataException("验证码错误");
            } else if (jsonNode.has("code") && jsonNode.get("code").asInt() == 209) {
                throw new DataExistException("用户已存在");
            } else {
                throw new Exception();
            }
        } else {
            throw new NetException("http请求响应编码" + httpResult.getCode());
        }
    }

    @Override
    public Boolean certification(ClientCertificationVO clientVO) throws Exception {
        String certificationUrl = UNIFIED_EXTERNAL_URI + CLIENT_CERTIFICATION_URL;
        HttpResult httpResult = this.apiService.doPutJson(certificationUrl, MAPPER.writeValueAsString(clientVO));
        if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                return jsonNode.get("data").asBoolean();
            }
        } else {
            throw new NetException("http请求响应编码" + httpResult.getCode());
        }
        return false;
    }

    @Override
    public ClientBankCardBO queryUserBankCard(String clientid) throws Exception {
        String queryBankCardUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI + QUERUY_CLIENT_BANKCARD_URL, clientid);
        HttpResult httpResult = this.apiService.doGet(queryBankCardUrl);

        if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                return MAPPER.readValue(jsonNode.path("data").toString(), ClientBankCardBO.class);
            }
        }
        return null;
    }

    @Override
    public Boolean addBankCard(ClientAddBankVO clientVO) throws Exception {
        String addBankCardUrl = UNIFIED_EXTERNAL_URI + CLIENT_ADDBANKCARD_URL;
        HttpResult httpResult = this.apiService.doPutJson(addBankCardUrl, MAPPER.writeValueAsString(clientVO));
        if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                return jsonNode.get("data").asBoolean();
            } else if (jsonNode.has("code") && jsonNode.get("code").asInt() == 204) {
                throw new DataException("数据异常:数据不存在/用户名不一致");
            } else {
                throw new Exception();
            }
        } else {
            throw new NetException("http请求响应编码" + httpResult.getCode());
        }
    }

    @Override
    public Boolean verifyValidateCode(String phone, String validateCode) throws Exception {
        String myTradeInfoUrl = UNIFIED_EXTERNAL_URI + USER_VERIFY_VALIDATECODE_URL;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("phone", phone);
        HttpResult httpResult = this.apiService.doGet(myTradeInfoUrl, params);
        if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                if (jsonNode.get("data") != null) {
                    String code = jsonNode.get("data").asText();
                    if (StringUtils.equals(validateCode, code)) {
                        return true;
                    }
                }
                return false;
            }
        }
        throw new DataException("验证码错误");
    }

    @Override
    public Boolean forgetPwd(String id, String newPwd, String operator) throws Exception {
        String resetPasswordUrl = MessageFormat.format(UNIFIED_EXTERNAL_URI + USER_FORGET_PASSWORD, id);

        Map<String, String> paramter = new HashMap<String, String>();
        paramter.put("newPassword", newPwd);
        paramter.put("operator", operator);
        HttpResult httpResult = this.apiService.doPut(resetPasswordUrl, paramter);
        ClientBO clientBO = queryUserById(id);
        if (httpResult.getCode() == 200) {
            String jsonData = httpResult.getContent();
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            if (jsonNode.has("code") && jsonNode.get("code").asInt() == 200) {
                //顺带更新件交易密码
                DemoUtil.TestResetPwd(clientBO.getLoginName(),newPwd);
                return jsonNode.get("data").asBoolean();
            } else if (jsonNode.has("code") && jsonNode.get("code").asInt() == 204) {
                throw new DataException("数据异常");
            } else {
                throw new Exception();
            }
        } else {
            throw new NetException("http请求响应编码" + httpResult.getCode());
        }
    }
}