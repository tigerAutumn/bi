package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 借款人扣款还款请求信息
 */
public class BorrowCutRepayReqModel extends BaseReqModel {

    /* 总金额 */
    private String amt;
    /* 异步通知地址 */
    private String notify_url;
    /* 备注 */
    private String remark;
    /* 平台客户入账列表 */
    private List<BorrowCutRepayPlatcustReqModel> platcustList;

    public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getNotify_url() {
        return notify_url;
    }
    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public List<BorrowCutRepayPlatcustReqModel> getPlatcustList() {
        return platcustList;
    }
    public void setPlatcustList(List<BorrowCutRepayPlatcustReqModel> platcustList) {
        this.platcustList = platcustList;
    }
}