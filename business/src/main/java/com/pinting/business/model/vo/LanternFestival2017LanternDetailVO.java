package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/2/3
 * Description: 获奖用户信息
 */
public class LanternFestival2017LanternDetailVO implements Serializable {

    private static final long serialVersionUID = 7601351525720265529L;

    /* 灯笼号 */
    private String lanternNum;

    /* 获奖时间 */
    private Date createTime;

    /* 用户名 */
    private String userName;

    /* 手机号 */
    private String mobile;

    public String getLanternNum() {
        return lanternNum;
    }

    public void setLanternNum(String lanternNum) {
        this.lanternNum = lanternNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
