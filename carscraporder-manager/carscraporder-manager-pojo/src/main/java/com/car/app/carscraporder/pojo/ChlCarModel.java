package com.car.app.carscraporder.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ChlCarModel {
    private Integer id;

    private Integer brandId;

    private String vinCode;

    private String modelName;

    private Integer seriesId;

    private Integer subSeriesId;

    private String displacement;

    private String modelYear;

    private String driveType;

    private String engine;

    private String bodyStructure;

    private BigDecimal purchasePrice;

    private String partNum;

    private String rangeOfService;

    private String rangeOfPickup;

    private Integer status;

    private Date createdTime;

    private String createdBy;

    private String updatedBy;

    private Date updatedTime;

    private Integer deleteState;

    private String called;//车型俗称

    private String carAlias;//车型别名

    private String carYear;//年款

    private String dynamic;//动类类型

    private String drives;//驱动形式

    private String seatNum;//座位数

    private String emissionStandard;//排放标准

    private String configureLevel;//配置等级

    private String cylinder;//气缸排列形式

    private String engineValveNum;//发动机气门数

    private String transmission;//变速器挡数

    private String wheelbase;//轴距

    private String cardoorNum;//车门数

    private String supply;//供油方式

    private String inlet;//进气形式

    private String power;//功率

    private String remarks;//备注

    private int carTypeId;//汽车类别id

    private String aliasId;//原厂家车型id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public Integer getSubSeriesId() {
        return subSeriesId;
    }

    public void setSubSeriesId(Integer subSeriesId) {
        this.subSeriesId = subSeriesId;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getBodyStructure() {
        return bodyStructure;
    }

    public void setBodyStructure(String bodyStructure) {
        this.bodyStructure = bodyStructure;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getPartNum() {
        return partNum;
    }

    public void setPartNum(String partNum) {
        this.partNum = partNum;
    }

    public String getRangeOfService() {
        return rangeOfService;
    }

    public void setRangeOfService(String rangeOfService) {
        this.rangeOfService = rangeOfService;
    }

    public String getRangeOfPickup() {
        return rangeOfPickup;
    }

    public void setRangeOfPickup(String rangeOfPickup) {
        this.rangeOfPickup = rangeOfPickup;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public String getCarAlias() {
        return carAlias;
    }

    public void setCarAlias(String carAlias) {
        this.carAlias = carAlias;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public String getDrives() {
        return drives;
    }

    public void setDrives(String drives) {
        this.drives = drives;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public String getEmissionStandard() {
        return emissionStandard;
    }

    public void setEmissionStandard(String emissionStandard) {
        this.emissionStandard = emissionStandard;
    }

    public String getConfigureLevel() {
        return configureLevel;
    }

    public void setConfigureLevel(String configureLevel) {
        this.configureLevel = configureLevel;
    }

    public String getCylinder() {
        return cylinder;
    }

    public void setCylinder(String cylinder) {
        this.cylinder = cylinder;
    }

    public String getEngineValveNum() {
        return engineValveNum;
    }

    public void setEngineValveNum(String engineValveNum) {
        this.engineValveNum = engineValveNum;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getWheelbase() {
        return wheelbase;
    }

    public void setWheelbase(String wheelbase) {
        this.wheelbase = wheelbase;
    }

    public String getCardoorNum() {
        return cardoorNum;
    }

    public void setCardoorNum(String cardoorNum) {
        this.cardoorNum = cardoorNum;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public String getInlet() {
        return inlet;
    }

    public void setInlet(String inlet) {
        this.inlet = inlet;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(int carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getAliasId() {
        return aliasId;
    }

    public void setAliasId(String aliasId) {
        this.aliasId = aliasId;
    }

    @Override
    public String toString() {
        return "ChlCarModel{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", vinCode='" + vinCode + '\'' +
                ", modelName='" + modelName + '\'' +
                ", seriesId=" + seriesId +
                ", subSeriesId=" + subSeriesId +
                ", displacement='" + displacement + '\'' +
                ", modelYear='" + modelYear + '\'' +
                ", driveType='" + driveType + '\'' +
                ", engine='" + engine + '\'' +
                ", bodyStructure='" + bodyStructure + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", partNum='" + partNum + '\'' +
                ", rangeOfService='" + rangeOfService + '\'' +
                ", rangeOfPickup='" + rangeOfPickup + '\'' +
                ", status=" + status +
                ", createdTime=" + createdTime +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedTime=" + updatedTime +
                ", deleteState=" + deleteState +
                ", called='" + called + '\'' +
                ", carAlias='" + carAlias + '\'' +
                ", carYear='" + carYear + '\'' +
                ", dynamic='" + dynamic + '\'' +
                ", drives='" + drives + '\'' +
                ", seatNum='" + seatNum + '\'' +
                ", emissionStandard='" + emissionStandard + '\'' +
                ", configureLevel='" + configureLevel + '\'' +
                ", cylinder='" + cylinder + '\'' +
                ", engineValveNum='" + engineValveNum + '\'' +
                ", transmission='" + transmission + '\'' +
                ", wheelbase='" + wheelbase + '\'' +
                ", cardoorNum='" + cardoorNum + '\'' +
                ", supply='" + supply + '\'' +
                ", inlet='" + inlet + '\'' +
                ", power='" + power + '\'' +
                ", remarks='" + remarks + '\'' +
                ", carTypeId=" + carTypeId +
                ", aliasId='" + aliasId + '\'' +
                '}';
    }
}
