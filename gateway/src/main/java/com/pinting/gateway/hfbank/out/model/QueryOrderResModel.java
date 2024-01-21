package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 订单状态查询响应信息
 */
public class QueryOrderResModel extends BaseResModel {

    /* 处理成功时，返回业务数据 */
    private QueryOrderDataResModel data;

    public QueryOrderDataResModel getData() {
        return data;
    }

    public void setData(QueryOrderDataResModel data) {
        this.data = data;
    }
}
