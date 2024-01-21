package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 订单状态查询处理成功时的业务数据
 */
public class QueryOrderData implements Serializable {

    private static final long serialVersionUID = -68972336898214116L;

    /* 平台编号 */
    private String plat_no;
    /* 查询的订单编号 */
    private String query_order_no;
    /* 订单状态（0-处理中 1-成功 2-失败 ） */
    private String status;

    /*订单确认时间（yyyyMMddHHmmss）*/
    private String trans_time;

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

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }
}
