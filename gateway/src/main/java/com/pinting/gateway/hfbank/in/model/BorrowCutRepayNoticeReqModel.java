package com.pinting.gateway.hfbank.in.model;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 借款人扣款还款通知请求信息
 */
public class BorrowCutRepayNoticeReqModel extends BaseReqModel {

    /* 总金额 */
    private String amt;
    /* 银行卡 */
    private String bank_no;
    /* 1-入账成功  2-入账失败 */
    private String code;
    /* 平台客户入账列表 */
    private List<BorrowCutRepayPlatcustReqModel> platcustList;

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<BorrowCutRepayPlatcustReqModel> getPlatcustList() {
        return platcustList;
    }

    public void setPlatcustList(List<BorrowCutRepayPlatcustReqModel> platcustList) {
        this.platcustList = platcustList;
    }
}
