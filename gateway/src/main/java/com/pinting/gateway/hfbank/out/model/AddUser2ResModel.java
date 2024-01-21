package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 短验绑卡确认响应信息
 */
public class AddUser2ResModel extends BaseResModel {

    /* 确认成功时，必填 */
    private String platcust;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }
}
