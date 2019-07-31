package com.car.app.carscraporder.pojo;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;


/**
 * 
 * 类名称：ClientProblem 
 * 类描述：常见问题类
 * 创建人：张婉欣
 * 创建时间：2019-01-21 19:10:05    
 * @version  V1.0
 *
 */
@Table(name="tb_client_problem")
public class ClientProblem extends BasePojo{
	
	@Id
    private String id;

	@ApiModelProperty(value = "常见问题标题")
    private String title;

	@ApiModelProperty(value = "常见问题内容")
    private String content;

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