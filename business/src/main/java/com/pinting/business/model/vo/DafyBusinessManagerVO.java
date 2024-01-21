package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 达飞业管
 * @author HuanXiong
 * @version 2016-6-2 下午1:42:07
 */
public class DafyBusinessManagerVO extends PageInfoObject {
    
    /**  */
    private static final long serialVersionUID = 2339256303136879238L;

    // 请求条件参数
    private String successTimeStart;
    
    private String successTimeEnd;
    
    private String balanceStart;
    
    private String balanceEnd;
    
    private String paymentStart;
    
    private String paymentEnd;
    
    // 返回结果
    
    private Integer id;
    
    private Integer subAccountId;   // 子账户ID
    
    private String userName;    //理财人姓名
    
    private String mobile;
    
    private String idCard;
    
    private String productName;
    
    private String managerName; // 客户经理
    
    private String deptName;    // 营业部
    
    private Long deptCode; //营业部编号
    
    private Integer status; // 理财意向状态
    
    private String statusStr; // 理财意向状态(分期产品)
    
    private Double baseRate;    // 预期年化利率
    
    private Integer term;    // 理财周期
    
    private Double balance;  // 投资金额（赞分期产品的匹配成功金额）
    
    private Double openBalance; //购买金额（赞分期产品的购买金额）
    
    private Double buyAmount;  // 实际金额(用户实际支付金额)（赞分期产品的匹配成功实际支付金额）
    
    private Double totalIncome;    // 累计收益
    
    private Date applyTime;    // 申请时间
    
    private Date successTime;  // 购买成功时间
    
    private Date interestBeginDate; // 计息开始时间（封闭开始日期）
    
    private Date actualPaymentDate;    // 实际赎回时间（回款）
    
    private Date planPaymentDate; //计划赎回时间（回款）
    
    private Double payment; //回款本息

	public Long getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(Long deptCode) {
		this.deptCode = deptCode;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getInterestBeginDate() {
        return interestBeginDate;
    }

    public void setInterestBeginDate(Date interestBeginDate) {
        this.interestBeginDate = interestBeginDate;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

	public String getSuccessTimeStart() {
		return successTimeStart;
	}

	public void setSuccessTimeStart(String successTimeStart) {
		this.successTimeStart = successTimeStart;
	}

	public String getSuccessTimeEnd() {
		return successTimeEnd;
	}

	public void setSuccessTimeEnd(String successTimeEnd) {
		this.successTimeEnd = successTimeEnd;
	}

	public String getBalanceStart() {
		return balanceStart;
	}

	public void setBalanceStart(String balanceStart) {
		this.balanceStart = balanceStart;
	}

	public String getBalanceEnd() {
		return balanceEnd;
	}

	public void setBalanceEnd(String balanceEnd) {
		this.balanceEnd = balanceEnd;
	}

	public String getPaymentStart() {
		return paymentStart;
	}

	public void setPaymentStart(String paymentStart) {
		this.paymentStart = paymentStart;
	}

	public String getPaymentEnd() {
		return paymentEnd;
	}

	public void setPaymentEnd(String paymentEnd) {
		this.paymentEnd = paymentEnd;
	}

	public Double getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(Double buyAmount) {
		this.buyAmount = buyAmount;
	}

	public Double getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = totalIncome;
	}

	public Date getActualPaymentDate() {
		return actualPaymentDate;
	}

	public void setActualPaymentDate(Date actualPaymentDate) {
		this.actualPaymentDate = actualPaymentDate;
	}

	public Date getPlanPaymentDate() {
		return planPaymentDate;
	}

	public void setPlanPaymentDate(Date planPaymentDate) {
		this.planPaymentDate = planPaymentDate;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Double getOpenBalance() {
		return openBalance;
	}

	public void setOpenBalance(Double openBalance) {
		this.openBalance = openBalance;
	}

}
