package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 批量换卡每一笔成功的响应信息
 */
public class BatchUpdateCardExtSuccess implements Serializable {

    private static final long serialVersionUID = -8011763264259972114L;

    /* 明细编号 */
    private String detail_no;
    /* 平台客户编号 */
    private String platcust;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }
}
