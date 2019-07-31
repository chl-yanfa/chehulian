package com.car.app.carscraporder.bo;

/**
 * @author ningrz
 * @version 1.0
 * @date 2019/7/30 10:44
 * @Remark 解体厂信息类
 */
public class FactoryQueryBO {

    private Integer id;

    private String factoryName;

    private String factoryPhone;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
