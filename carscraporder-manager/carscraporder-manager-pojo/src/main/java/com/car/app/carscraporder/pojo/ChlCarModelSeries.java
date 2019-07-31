package com.car.app.carscraporder.pojo;

import io.swagger.models.auth.In;

public class ChlCarModelSeries {
    private Integer sid;
    private String vehicleSystemName;
    private Integer bid;
    private String brname;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getVehicleSystemName() {
        return vehicleSystemName;
    }

    public void setVehicleSystemName(String vehicleSystemName) {
        this.vehicleSystemName = vehicleSystemName;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getBrname() {
        return brname;
    }

    public void setBrname(String brname) {
        this.brname = brname;
    }
}
