package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 订单状态查询请求信息
 */
public class QueryOrderReqModel extends BaseReqModel {

    /* 查询的订单号（针对批量接口，对应detail_no） */
    private String query_order_no;

    public String getQuery_order_no() {
        return query_order_no;
    }

    public void setQuery_order_no(String query_order_no) {
        this.query_order_no = query_order_no;
    }
}
