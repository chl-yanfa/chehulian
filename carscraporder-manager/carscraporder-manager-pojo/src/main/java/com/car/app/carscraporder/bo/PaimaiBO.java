package com.car.app.carscraporder.bo;

import java.util.Date;

public class PaimaiBO {
	private String id;
    private String pmhId;				//拍卖会ID
    private Integer auctionType;		//拍品类型(0:全车配件,1:高价值配件,2:大宗物资)
    private String paimaiName;			//拍卖会名称
    private Date paimaiStartTime;		//拍卖开始时间
    private Date paimaiFinishTime;		//拍卖结束时间
    private Integer paimaiState;		//拍卖状态：0-待发布；1-进行中；2-已结束
    private Date systemTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPmhId() {
		return pmhId;
	}
	public void setPmhId(String pmhId) {
		this.pmhId = pmhId;
	}
	public Integer getAuctionType() {
		return auctionType;
	}
	public void setAuctionType(Integer auctionType) {
		this.auctionType = auctionType;
	}
	public String getPaimaiName() {
		return paimaiName;
	}
	public void setPaimaiName(String paimaiName) {
		this.paimaiName = paimaiName;
	}
	public Date getPaimaiStartTime() {
		return paimaiStartTime;
	}
	public void setPaimaiStartTime(Date paimaiStartTime) {
		this.paimaiStartTime = paimaiStartTime;
	}
	public Date getPaimaiFinishTime() {
		return paimaiFinishTime;
	}
	public void setPaimaiFinishTime(Date paimaiFinishTime) {
		this.paimaiFinishTime = paimaiFinishTime;
	}
	public Integer getPaimaiState() {
		return paimaiState;
	}
	public void setPaimaiState(Integer paimaiState) {
		this.paimaiState = paimaiState;
	}
	public Date getSystemTime() {
		return systemTime;
	}
	public void setSystemTime(Date systemTime) {
		this.systemTime = systemTime;
	}
    
}
