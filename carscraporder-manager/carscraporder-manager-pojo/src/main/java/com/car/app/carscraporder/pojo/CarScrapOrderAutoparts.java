package com.car.app.carscraporder.pojo;

import java.math.BigDecimal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
/**
 * 
 * 类名称：CarScrapOrderAutoparts   
 * 类描述：配件实体类   
 * 创建人：刘子亮
 * 创建时间：2018年10月9日 上午11:51:24      
 * @version  V1.0
 *
 */
@Table(name="car_scrap_order_autoparts")
public class CarScrapOrderAutoparts extends BasePojo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
	
	@ApiModelProperty(value = "订单id")
    private String orderId;
	
	@ApiModelProperty(value = "配件类型")
    private Integer partsType;

	@ApiModelProperty(value = "配件名称")
    private String partsName;
	
	@ApiModelProperty(value = "配件编号")
    private String partsNum;
	
	@ApiModelProperty(value = "配件数量")
    private Integer partsCount;
	
	@ApiModelProperty(value = "纯拆")
    private String isDismantle;
	
	@ApiModelProperty(value = "碰撞")
    private String isCollision;
	
	@ApiModelProperty(value = "自然磨损")
    private String isWear;
	
	@ApiModelProperty(value = "水淹")
    private String isFlood;
	
	@ApiModelProperty(value = "配件备注")
    private String remark;
	
	@ApiModelProperty(value = "分总报价金额")
    private BigDecimal subQuote;
	
	@ApiModelProperty(value = "配件金额")
    private BigDecimal amount;
	
	@ApiModelProperty(value = "配件状态")
	private Integer orderAutopartsStatus;
	
	@ApiModelProperty(value = "分拣状态")
	private Integer sortingState;
	
	@ApiModelProperty(value = "无法接收原因")
	private Integer unableReceiveReason;
	
	private String qrPic;
	
	private Boolean isValid=true;
	

   

  

	public String getQrPic() {
		return qrPic;
	}

	public void setQrPic(String qrPic) {
		this.qrPic = qrPic;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getPartsType() {
        return partsType;
    }

    public void setPartsType(Integer partsType) {
        this.partsType = partsType;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName == null ? null : partsName.trim();
    }

    public String getPartsNum() {
        return partsNum;
    }

    public void setPartsNum(String partsNum) {
        this.partsNum = partsNum == null ? null : partsNum.trim();
    }

    public Integer getPartsCount() {
		return partsCount;
	}

	public void setPartsCount(Integer partsCount) {
		this.partsCount = partsCount;
	}

	public String getIsDismantle() {
		return isDismantle;
	}

	public void setIsDismantle(String isDismantle) {
		this.isDismantle = isDismantle;
	}

	public String getIsCollision() {
		return isCollision;
	}

	public void setIsCollision(String isCollision) {
		this.isCollision = isCollision;
	}

	public String getIsWear() {
		return isWear;
	}

	public void setIsWear(String isWear) {
		this.isWear = isWear;
	}

	public String getIsFlood() {
		return isFlood;
	}

	public void setIsFlood(String isFlood) {
		this.isFlood = isFlood;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getSubQuote() {
		return subQuote;
	}

	public void setSubQuote(BigDecimal subQuote) {
		this.subQuote = subQuote;
	}

	public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

	

	public Integer getOrderAutopartsStatus() {
		return orderAutopartsStatus;
	}

	public void setOrderAutopartsStatus(Integer orderAutopartsStatus) {
		this.orderAutopartsStatus = orderAutopartsStatus;
	}
	
	



	public Integer getSortingState() {
		return sortingState;
	}

	public void setSortingState(Integer sortingState) {
		this.sortingState = sortingState;
	}

	public Integer getUnableReceiveReason() {
		return unableReceiveReason;
	}

	public void setUnableReceiveReason(Integer unableReceiveReason) {
		this.unableReceiveReason = unableReceiveReason;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

   
 
}