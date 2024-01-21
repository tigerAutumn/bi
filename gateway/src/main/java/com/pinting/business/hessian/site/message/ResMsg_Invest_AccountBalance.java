package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2016/8/27
 * Description: 账户余额响应信息
 */
public class ResMsg_Invest_AccountBalance extends ResMsg {

    private static final long serialVersionUID = -3456761867714924298L;

    /* 可用余额 */
    private String availableBalance;

    /* 存管户账户余额 */
    private String depAvailableBalance;

    /* 处理中提现金额 */
    private String payingWithdrawBalance;

    /* 绑卡状态（YES-已绑卡；NO-未绑卡） */
    private String status;
    
    /* 是否设置交易密码（YES-已设置；NO-未设置） */
    private String havePayPassword;

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getPayingWithdrawBalance() {
        return payingWithdrawBalance;
    }

    public void setPayingWithdrawBalance(String payingWithdrawBalance) {
        this.payingWithdrawBalance = payingWithdrawBalance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getHavePayPassword() {
		return havePayPassword;
	}

	public void setHavePayPassword(String havePayPassword) {
		this.havePayPassword = havePayPassword;
	}

    public String getDepAvailableBalance() {
        return depAvailableBalance;
    }

    public void setDepAvailableBalance(String depAvailableBalance) {
        this.depAvailableBalance = depAvailableBalance;
    }
}
