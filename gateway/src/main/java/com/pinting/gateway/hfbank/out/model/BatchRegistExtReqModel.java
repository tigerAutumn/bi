package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 批量开户(四要素绑卡)请求数据
 */
public class BatchRegistExtReqModel extends BaseReqModel {

    /* 总数量 */
    private Integer total_num;

    /* JsonArray，批量明细数据 */
    private List<BatchRegistExtDetailReqModel> data;

    public List<BatchRegistExtDetailReqModel> getData() {
        return data;
    }

    public void setData(List<BatchRegistExtDetailReqModel> data) {
        this.data = data;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }
}
