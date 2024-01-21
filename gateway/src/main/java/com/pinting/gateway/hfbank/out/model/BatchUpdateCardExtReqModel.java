package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 批量换卡请求信息
 */
public class BatchUpdateCardExtReqModel extends BaseReqModel {
	/* 总数量 */
    private Integer total_num;
    /* JsonArray批量数据 */
    private List<BatchUpdateCardExtDetailReqModel> data;
    
    public Integer getTotal_num() {
		return total_num;
	}

	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}

	public List<BatchUpdateCardExtDetailReqModel> getData() {
        return data;
    }

    public void setData(List<BatchUpdateCardExtDetailReqModel> data) {
        this.data = data;
    }
}
