package com.car.app.carscraporder.pojo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 类名称：ClientCertificationRecord 
 * 类描述：用户实名认证记录类
 * 创建人：张婉欣
 * 创建时间：2019-01-21 14:02:00    
 * @version  V1.0
 *
 */
@Table(name="tb_client_certification_record")
public class ClientCertificationRecord{
	
	@Id
    private String id;

	@ApiModelProperty(value = "用户id")
    private String clientid;

	@ApiModelProperty(value = "审核时间")
    private Date auditTime;

	@ApiModelProperty(value = "审核人")
    private String auditUser;

	@ApiModelProperty(value = "0：审核通过；-1：审核驳回")
	private Integer state;

	@ApiModelProperty(value = "备注")
    private String memo;
	
	@JsonIgnore
	private String creater;
	@JsonIgnore
    private Date createtime;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
   

    
}