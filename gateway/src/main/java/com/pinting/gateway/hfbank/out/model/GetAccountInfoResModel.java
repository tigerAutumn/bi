package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 资金余额查询响应信息
 */
public class GetAccountInfoResModel extends BaseResModel {

    private GetAccountInfoDataResModel data;

    public GetAccountInfoDataResModel getData() {
        return data;
    }

    public void setData(GetAccountInfoDataResModel data) {
        this.data = data;
    }
}
