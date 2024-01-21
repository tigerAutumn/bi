package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsUserRegistView extends PageInfoObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5197171543600658226L;

	private Integer id;

    private Date registDate;

    private Integer dayRegistIncrease;

    private Integer dayCardbindIncrease;

    private Integer androidRegistNum;

    private Integer iosRegistNum;

    private Integer recommendRegistNum;

    private Integer h5RegistNum;

    private Integer pcRegistNum;

    private Integer otherRegistNum;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public Integer getDayRegistIncrease() {
        return dayRegistIncrease;
    }

    public void setDayRegistIncrease(Integer dayRegistIncrease) {
        this.dayRegistIncrease = dayRegistIncrease;
    }

    public Integer getDayCardbindIncrease() {
        return dayCardbindIncrease;
    }

    public void setDayCardbindIncrease(Integer dayCardbindIncrease) {
        this.dayCardbindIncrease = dayCardbindIncrease;
    }

    public Integer getAndroidRegistNum() {
        return androidRegistNum;
    }

    public void setAndroidRegistNum(Integer androidRegistNum) {
        this.androidRegistNum = androidRegistNum;
    }

    public Integer getIosRegistNum() {
        return iosRegistNum;
    }

    public void setIosRegistNum(Integer iosRegistNum) {
        this.iosRegistNum = iosRegistNum;
    }

    public Integer getRecommendRegistNum() {
        return recommendRegistNum;
    }

    public void setRecommendRegistNum(Integer recommendRegistNum) {
        this.recommendRegistNum = recommendRegistNum;
    }

    public Integer getH5RegistNum() {
        return h5RegistNum;
    }

    public void setH5RegistNum(Integer h5RegistNum) {
        this.h5RegistNum = h5RegistNum;
    }

    public Integer getPcRegistNum() {
        return pcRegistNum;
    }

    public void setPcRegistNum(Integer pcRegistNum) {
        this.pcRegistNum = pcRegistNum;
    }

    public Integer getOtherRegistNum() {
        return otherRegistNum;
    }

    public void setOtherRegistNum(Integer otherRegistNum) {
        this.otherRegistNum = otherRegistNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}