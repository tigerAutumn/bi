package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 批量提现请求信息
 */
public class BatchWithdrawExtReqModel extends BaseReqModel {
	/* 总数量 */
	private Integer total_num;
    /* JsonArray明细数据 */
    private List<BatchWithdrawExtDataReqModel> data;

    public List<BatchWithdrawExtDataReqModel> getData() {
        return data;
    }

    public void setData(List<BatchWithdrawExtDataReqModel> data) {
        this.data = data;
    }

	public Integer getTotal_num() {
		return total_num;
	}

	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
    
}