package com.car.app.carscraporder.pojo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 类名称：ClientMessage 
 * 类描述：用户消息通知类
 * 创建人：张婉欣
 * 创建时间：2019-01-21 14:02:00    
 * @version  V1.0
 *
 */
@Table(name="tb_client_message")
public class ClientMessage extends BasePojo{
	
	@Id
    private String id;

	@ApiModelProperty(value = "客户id")
    private String clientId;

	@ApiModelProperty(value = "消息标题")
    private String title;

	@ApiModelProperty(value = "消息内容")
    private String content;

	@ApiModelProperty(value = "消息类型:0-实名认证 1-报价确认")
    private String type;

	@ApiModelProperty(value = "是否已读(0:未读，1:已读)")
	private Integer isread;
	
	@ApiModelProperty(value = "是否删除(0:有效，1:删除)")
    private Integer isremove;
	
	@JsonIgnore
	private String creater;
	@JsonIgnore
    private Date createtime;
   

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId == null ? null : clientId.trim();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}
	public Integer getIsread() {
		return isread;
	}
	public void setIsread(Integer isread) {
		this.isread = isread;
	}
	public Integer getIsremove() {
		return isremove;
	}
	public void setIsremove(Integer isremove) {
		this.isremove = isremove;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater == null ? null : creater.trim();
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
   
}