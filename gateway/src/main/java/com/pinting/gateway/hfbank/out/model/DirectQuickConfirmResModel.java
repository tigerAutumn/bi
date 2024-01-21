package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 快捷充值确认响应信息
 */
public class DirectQuickConfirmResModel extends BaseResModel {

    /* 返回业务数据 */
    private DirectQuickConfirmDataResModel data;

    public DirectQuickConfirmDataResModel getData() {
        return data;
    }

    public void setData(DirectQuickConfirmDataResModel data) {
        this.data = data;
    }
}
