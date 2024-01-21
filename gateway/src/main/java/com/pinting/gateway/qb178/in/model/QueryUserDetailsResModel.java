package com.pinting.gateway.qb178.in.model;


import java.util.List;

/**
 * Author:      shh
 * Date:        2017/7/28
 * Description: 查询会员详情响应信息
 */
public class QueryUserDetailsResModel extends BaseResModel {

    /* 明细数据 */
    private List<QueryUserDetailsDataResModel> data;

    public List<QueryUserDetailsDataResModel> getData() {
        return data;
    }

    public void setData(List<QueryUserDetailsDataResModel> data) {
        this.data = data;
    }
}
