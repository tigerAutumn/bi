package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 快捷充值预下单响应信息
 */
public class DirectQuickRequestResModel extends BaseResModel {

    /* 返回业务数据 */
    private DirectQuickRequestDataResModel data;

    public DirectQuickRequestDataResModel getData() {
        return data;
    }

    public void setData(DirectQuickRequestDataResModel data) {
        this.data = data;
    }
}
