package com.car.app.carscraporder.app.bean;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CarScrapOrderSimpleBaseVO extends CarScrapOrderBaseVO {
	
	@ApiModelProperty(value = "车辆归属(1:个人,2:客户)")
    private String carAscription="1";
	
	@ApiModelProperty(value = "车主姓名")
    private String carOwner;
	
	@ApiModelProperty(value = "能否行驶(1:否,2:是 3:严重事故)")
    private String isdrive;
	
	@ApiModelProperty(value = "车辆证明日期")
    private String carScrapDate;
	
	@ApiModelProperty(value = "取车人联系人")
    private String takeCarContacts;

	@ApiModelProperty(value = "取车联系电话")
    private String takeCarContactNumber;
	
	@ApiModelProperty(value = "取车时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date takeCarTime;

	@ApiModelProperty(value = "取车地址")
    private String takeCarAddress;

}
