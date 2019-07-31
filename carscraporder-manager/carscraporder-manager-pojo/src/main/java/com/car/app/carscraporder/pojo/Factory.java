package com.car.app.carscraporder.pojo;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ningrz
 * @version 1.0
 * @date 2019/7/30 10:56
 */
@Table(name="car_scrap_factory")
public class Factory {
    @Id
    private Integer id;

    @ApiModelProperty(value = "解体厂名称")
    private String factoryName;

    @ApiModelProperty(value = "解体厂联系电话")
    private String factoryPhone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryPhone() {
        return factoryPhone;
    }

    public void setFactoryPhone(String factoryPhone) {
        this.factoryPhone = factoryPhone;
    }
}
