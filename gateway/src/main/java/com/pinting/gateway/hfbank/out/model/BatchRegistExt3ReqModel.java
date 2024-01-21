package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 批量开户（实名认证）针对借款人请求信息
 */
public class BatchRegistExt3ReqModel extends BaseReqModel {

    /* 总数量 */
    private Integer total_num;
    /* JsonArray，批量数据 */
    private List<BatchRegistExt3DetailReqModel> data;

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public List<BatchRegistExt3DetailReqModel> getData() {
        return data;
    }

    public void setData(List<BatchRegistExt3DetailReqModel> data) {
        this.data = data;
    }

}
