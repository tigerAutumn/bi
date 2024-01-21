package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 查询回款卡信息 出参
 * @author bianyatian
 * @2015-11-19 下午7:20:01
 */

public class ResMsg_Bank_QueryFirstCard extends ResMsg {
	/**
	 *
	 */
	private static final long serialVersionUID = -8380270405134508740L;
	/*用户可免费提现次数*/
	private Integer can_withdraw_times;
	/*银行id*/
	private Integer bankId;
	/*卡号*/
	private String cardNo;
	/*银行名称*/
	private String bankName;
	/*JSH子账户可提现余额*/
	private Double can_withdraw;
	/*小图标*/
	private String smallLogo;
	/*大图标*/
	private String largeLogo;
	/*银行卡ID*/
	private Integer cardId;
	/*最小提现金额*/
	private String withdrawLimit;
	/*查询最小充值金额*/
	private String rechageLimit;
	/**
	 * 每个月可以免费提现的总次数
	 */
	private Integer eachMonthWithdrawTimes;
	/**
	 * 用户提现手续费
	 */
	private Double withdrawCounterFee;

	/* 单笔提现上限 */
	private Double singleWithdrawUpperLimit;
	/* 单日提现上限 */
	private Double dayWithdrawUpperLimit;
	/* 用户已提现总金额*/
	private Double userWithdrawAmount;
	/* 恒丰存管可提现余额 */
	private Double depCanWithdraw;

	/* 提现金额限制值 */
	private Double withdrawLimitAmount;

	public Double getDepCanWithdraw() {
		return depCanWithdraw;
	}

	public void setDepCanWithdraw(Double depCanWithdraw) {
		this.depCanWithdraw = depCanWithdraw;
	}

	public Double getDayWithdrawUpperLimit() {
		return dayWithdrawUpperLimit;
	}

	public void setDayWithdrawUpperLimit(Double dayWithdrawUpperLimit) {
		this.dayWithdrawUpperLimit = dayWithdrawUpperLimit;
	}

	public Double getSingleWithdrawUpperLimit() {
		return singleWithdrawUpperLimit;
	}

	public void setSingleWithdrawUpperLimit(Double singleWithdrawUpperLimit) {
		this.singleWithdrawUpperLimit = singleWithdrawUpperLimit;
	}

	public Integer getEachMonthWithdrawTimes() {
		return eachMonthWithdrawTimes;
	}

	public void setEachMonthWithdrawTimes(Integer eachMonthWithdrawTimes) {
		this.eachMonthWithdrawTimes = eachMonthWithdrawTimes;
	}

	public Double getWithdrawCounterFee() {
		return withdrawCounterFee;
	}

	public void setWithdrawCounterFee(Double withdrawCounterFee) {
		this.withdrawCounterFee = withdrawCounterFee;
	}

	public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getCan_withdraw_times() {
		return can_withdraw_times;
	}

	public void setCan_withdraw_times(Integer can_withdraw_times) {
		this.can_withdraw_times = can_withdraw_times;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Double getCan_withdraw() {
		return can_withdraw;
	}

	public void setCan_withdraw(Double can_withdraw) {
		this.can_withdraw = can_withdraw;
	}

    public String getSmallLogo() {
        return smallLogo;
    }

    public void setSmallLogo(String smallLogo) {
        this.smallLogo = smallLogo;
    }

    public String getLargeLogo() {
        return largeLogo;
    }

    public void setLargeLogo(String largeLogo) {
        this.largeLogo = largeLogo;
    }

	public String getWithdrawLimit() {
		return withdrawLimit;
	}

	public void setWithdrawLimit(String withdrawLimit) {
		this.withdrawLimit = withdrawLimit;
	}

	public String getRechageLimit() {
		return rechageLimit;
	}

	public void setRechageLimit(String rechageLimit) {
		this.rechageLimit = rechageLimit;
	}

	public Double getUserWithdrawAmount() {
		return userWithdrawAmount;
	}

	public void setUserWithdrawAmount(Double userWithdrawAmount) {
		this.userWithdrawAmount = userWithdrawAmount;
	}

	public Double getWithdrawLimitAmount() {
		return withdrawLimitAmount;
	}

	public void setWithdrawLimitAmount(Double withdrawLimitAmount) {
		this.withdrawLimitAmount = withdrawLimitAmount;
	}
}
