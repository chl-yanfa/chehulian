package com.car.app.carscraporder.pojo;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 类名称：Version 
 * 类描述：版本号表
 * 创建人：张婉欣
 *  
 * @version  V1.0
 *
 */
@Table(name="tb_version")
public class Version extends BasePojo{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@ApiModelProperty(value = "来源 1:用户IOS   2:用户Android    3:员工IOS   4:员工Android")
    private Integer oc;

	@ApiModelProperty(value = "项目名")
	private String projectName;

	@ApiModelProperty(value = "url")
	private String url;

	@ApiModelProperty(value = "版本号")
	private String versionCode;

	@ApiModelProperty(value = "备注")
	private String remark;

	@JsonIgnore
	@ApiModelProperty(value = "时间戳")
    private Date ts;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOc() {
		return oc;
	}

	public void setOc(Integer oc) {
		this.oc = oc;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}


    
}