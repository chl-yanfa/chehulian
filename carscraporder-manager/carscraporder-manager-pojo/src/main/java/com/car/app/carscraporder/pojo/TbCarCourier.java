package com.car.app.carscraporder.pojo;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ningrz
 * @version 1.0
 * @date 2019/8/5 9:42
 */
@Table(name="tb_car_courier")
public class TbCarCourier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "快递公司编码")
    private String carScrapNum;

    @ApiModelProperty(value = "快递公司姓名")
    private String carScrapName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarScrapNum() {
        return carScrapNum;
    }

    public void setCarScrapNum(String carScrapNum) {
        this.carScrapNum = carScrapNum;
    }

    public String getCarScrapName() {
        return carScrapName;
    }

    public void setCarScrapName(String carScrapName) {
        this.carScrapName = carScrapName;
    }
}
