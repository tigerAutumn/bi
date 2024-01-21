package com.pinting.business.util;

/**
 * Created by 剑钊 on 2016/7/12.
 */
public class TransferEnv {

    /**
     * 转出方账号（邮箱）
     */
    private String accountFrom;

    /**
     * 转入方账号（邮箱）
     */
    private String accountTo;

    /**
     * 转入方会员号（用于宝付转账）
     */
    private String memberIdTo;

    /**
     * 转账异步通知地址
     */
    private String notifyUrl;

    /**
     * 转入方公司名称
     */
    private String toAcctName;

    /**
     * 转入账户的账户类型 （19付暂仅支持为ENTPRISE）
     */
    private String toAcctType="ENTPRISE";

    /**
     * 交易类型 （19付暂仅支持为TRANSFER）
     */
    private String tradeType="TRANSFER";

    /**
     * 商户备注信息
     */
    private String remarks;

    /**
     * 交易描述
     */
    private String tradeDesc;
    
    /**
     * 转账交易转入方名称
     */
    private String transTarget;
    
    /**
     * 系统子账户产品户名称
     * 云贷：REG_YUN
     * 七贷：REG_7
     */
    private String sysSubAccountREG;
    
    /**
     * 系统子账户回款户名称
     * 云贷：REG_YUN
     * 七贷：RETURN_7
     */
    private String sysSubAccountRETURN;

    public String getMemberIdTo() {
        return memberIdTo;
    }

    public void setMemberIdTo(String memberIdTo) {
        this.memberIdTo = memberIdTo;
    }

    public String getTransTarget() {
		return transTarget;
	}

	public void setTransTarget(String transTarget) {
		this.transTarget = transTarget;
	}

	public String getSysSubAccountREG() {
		return sysSubAccountREG;
	}

	public void setSysSubAccountREG(String sysSubAccountREG) {
		this.sysSubAccountREG = sysSubAccountREG;
	}

	public String getSysSubAccountRETURN() {
		return sysSubAccountRETURN;
	}

	public void setSysSubAccountRETURN(String sysSubAccountRETURN) {
		this.sysSubAccountRETURN = sysSubAccountRETURN;
	}

	public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getToAcctName() {
        return toAcctName;
    }

    public void setToAcctName(String toAcctName) {
        this.toAcctName = toAcctName;
    }

    public String getToAcctType() {
        return toAcctType;
    }

    public void setToAcctType(String toAcctType) {
        this.toAcctType = toAcctType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTradeDesc() {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }
}
