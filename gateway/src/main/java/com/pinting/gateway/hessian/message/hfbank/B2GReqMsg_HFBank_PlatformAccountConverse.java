package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

public class B2GReqMsg_HFBank_PlatformAccountConverse extends ReqMsg {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4812665449306921526L;
	/* 来源账户(平台子账户：1-自有子账户；3手续费子账户；4-体验金子账户；5-抵用金子账户；6-加息金子账户；7-保证金子账户) */
    private String source_account;
    /* 转账金额 */
    private Double amt;
    /* 目标账户(平台子账户：1-自有子账户；3手续费子账户；4-体验金子账户；5-抵用金子账户；6-加息金子账户；7-保证金子账户) */
    private String dest_account;
    /* 备注 */
    private String remark;
    
    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

    public Date getPartner_trans_date() {
        return partner_trans_date;
    }

    public void setPartner_trans_date(Date partner_trans_date) {
        this.partner_trans_date = partner_trans_date;
    }

    public Date getPartner_trans_time() {
        return partner_trans_time;
    }

    public void setPartner_trans_time(Date partner_trans_time) {
        this.partner_trans_time = partner_trans_time;
    }

    public String getSource_account() {
        return source_account;
    }

    public void setSource_account(String source_account) {
        this.source_account = source_account;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getDest_account() {
        return dest_account;
    }

    public void setDest_account(String dest_account) {
        this.dest_account = dest_account;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
