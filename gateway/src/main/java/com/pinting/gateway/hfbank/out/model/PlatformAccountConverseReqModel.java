package com.pinting.gateway.hfbank.out.model;

/**
 * 平台不同账户转账请求信息
 * Created by shh on 2017/4/1.
 */
public class PlatformAccountConverseReqModel extends BaseReqModel {

    /* 来源账户(平台子账户：1-自有子账户；3手续费子账户；4-体验金子账户；5-抵用金子账户；6-加息金子账户；7-保证金子账户) */
    private String source_account;
    /* 转账金额 */
    private String amt;
    /* 目标账户(平台子账户：1-自有子账户；3手续费子账户；4-体验金子账户；5-抵用金子账户；6-加息金子账户；7-保证金子账户) */
    private String dest_account;
    /* 备注 */
    private String remark;

    public String getSource_account() {
        return source_account;
    }

    public void setSource_account(String source_account) {
        this.source_account = source_account;
    }
    
    public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
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
