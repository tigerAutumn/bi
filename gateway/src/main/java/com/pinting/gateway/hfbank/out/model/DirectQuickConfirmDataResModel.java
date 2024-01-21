package com.pinting.gateway.hfbank.out.model;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 快捷充值确认响应信息中的业务数据
 */
public class DirectQuickConfirmDataResModel implements Serializable {

    private static final long serialVersionUID = -689374900439784401L;
    /* 商户请求订单号 */
    private String order_no;
    /* 订单状态0:已接受, 1:处理中,2:处理成功,3:处理失败 */
    private String order_status;
    /* 系统处理日期(yyyyMMddHHmmss)) */
    private String process_date;
    /* 平台流水号 */
    private String query_id;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getProcess_date() {
        return process_date;
    }

    public void setProcess_date(String process_date) {
        this.process_date = process_date;
    }

    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }
}
