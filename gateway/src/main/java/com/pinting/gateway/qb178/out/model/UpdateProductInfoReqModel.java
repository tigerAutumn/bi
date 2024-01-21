package com.pinting.gateway.qb178.out.model;

/**
 * Author:      shh
 * Date:        2017/7/28
 * Description: 更新产品信息 请求信息
 */
public class UpdateProductInfoReqModel extends BaseReqModel {

    /* 产品编号 */
    private String product_code;

    /* 产品状态, 申购中（buying），已确权（confirmed）， 赎回完成（redeemed），发行失败（failure）*/
    private String product_status;

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_status() {
        return product_status;
    }

    public void setProduct_status(String product_status) {
        this.product_status = product_status;
    }
}
