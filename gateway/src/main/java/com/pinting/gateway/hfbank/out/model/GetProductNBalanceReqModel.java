package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 标的账户余额查询请求信息
 */
public class GetProductNBalanceReqModel extends BaseReqModel {

    /* 账户编号 */
    private String prod_id;
    /* 现金01   在途02 */
    private String type;

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
