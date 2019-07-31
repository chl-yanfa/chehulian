package com.car.app.carscraporder.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ningrz
 * @version 1.0
 * @date 2019/7/8 16:05
 */
@Table(name="car_push")
public class CarPush extends BasePojo{
    @Id
    private Integer id;

    @NotBlank
    private String pushType;

    @NotBlank
    private String notificationTitle;

    @NotBlank
    private String msgTitle;

    @NotBlank
    private String msgContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CarPush{" +
                "id=" + id +
                ", pushType='" + pushType + '\'' +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", msgTitle='" + msgTitle + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
