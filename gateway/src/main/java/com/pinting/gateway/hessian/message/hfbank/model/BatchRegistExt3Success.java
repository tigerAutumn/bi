package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 批量开户（实名认证）单笔成功响应信息
 */
public class BatchRegistExt3Success implements Serializable {

    private static final long serialVersionUID = -167377677898270441L;

    /* 明细编号 */
    private String detail_no;
    /* 用户手机号 */
    private String mobile;
    /* 平台客户编号 */
    private String platcust;

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }
}
