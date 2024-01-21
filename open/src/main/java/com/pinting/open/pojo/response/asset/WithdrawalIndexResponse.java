/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

/**
 * 
 * @author HuanXiong
 * @version $Id: WithdrawalIndexResponse.java, v 0.1 2015-12-24 上午10:38:23 HuanXiong Exp $
 */
public class WithdrawalIndexResponse extends Response {
    
    private Integer cardId;
    
    private String cardNo;
    
    private String bankName;
    
    private Integer withdrawTimes;  // 用户当月剩余免费提现次数
    
    private String startTime;   // 开始时间
    
    private String endTime;
    
    private Double canWithdraw; // 最多可提现金额
    
    private Integer eachMonthWithdrawTimes; //每个月可以免费提现的总次数

    private Double withdrawCounterFee; //用户提现手续费
    
	private String  smallLogo;   //小图标

    private String  largeLogo;   //大图标
    
	/* 单笔提现上限 */
	private Double singleWithdrawUpperLimit;
	/* 单日提现上限 */
	private Double dayWithdrawUpperLimit;
	/* 用户已提现总金额*/
	private Double userWithdrawAmount;
	
	
	private Double depBalance; //存管账户余额
	
	private String depAccountStatus; //存管户可用余额(OLD 只有存管前账户   、DEP  只有存管户 、 DOUBLE  双账户并存)
	/* 5万 */
	private String withdrawApplyLimit;

	public String getWithdrawApplyLimit() {
		return withdrawApplyLimit;
	}

	public void setWithdrawApplyLimit(String withdrawApplyLimit) {
		this.withdrawApplyLimit = withdrawApplyLimit;
	}

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getWithdrawTimes() {
        return withdrawTimes;
    }

    public void setWithdrawTimes(Integer withdrawTimes) {
        this.withdrawTimes = withdrawTimes;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(Double canWithdraw) {
        this.canWithdraw = canWithdraw;
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

	public Double getSingleWithdrawUpperLimit() {
		return singleWithdrawUpperLimit;
	}

	public void setSingleWithdrawUpperLimit(Double singleWithdrawUpperLimit) {
		this.singleWithdrawUpperLimit = singleWithdrawUpperLimit;
	}

	public Double getDayWithdrawUpperLimit() {
		return dayWithdrawUpperLimit;
	}

	public void setDayWithdrawUpperLimit(Double dayWithdrawUpperLimit) {
		this.dayWithdrawUpperLimit = dayWithdrawUpperLimit;
	}

	public Double getUserWithdrawAmount() {
		return userWithdrawAmount;
	}

	public void setUserWithdrawAmount(Double userWithdrawAmount) {
		this.userWithdrawAmount = userWithdrawAmount;
	}

	public Double getDepBalance() {
		return depBalance;
	}

	public void setDepBalance(Double depBalance) {
		this.depBalance = depBalance;
	}

	public String getDepAccountStatus() {
		return depAccountStatus;
	}

	public void setDepAccountStatus(String depAccountStatus) {
		this.depAccountStatus = depAccountStatus;
	}
	
	
}
