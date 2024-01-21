package com.pinting.gateway.hfbank.out.model;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 订单状态查询处理成功时的业务数据
 */
public class QueryOrderDataResModel implements Serializable {

    private static final long serialVersionUID = -68972336898214116L;

    /* 平台编号 */
    private String plat_no;
    /* 查询的订单编号 */
    private String query_order_no;
    /* 订单状态（0-处理中 1-成功 2-失败 ） */
    private String status;

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public String getQuery_order_no() {
        return query_order_no;
    }

    public void setQuery_order_no(String query_order_no) {
        this.query_order_no = query_order_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
