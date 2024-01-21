package com.pinting.gateway.bird.in.model;

import com.pinting.gateway.hessian.message.loan.model.Repayment;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by 剑钊 on 2016/8/10.
 * 还款预下单
 */
public class RepayPreReq extends BaseReqModel {

    /**
     * 还款订单号
     */
    @NotEmpty(message = "订单号不能为空")
    private String orderNo;

    /**
     * 用户IP地址
     */
    private String ip;

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private String userId;

    /**
     * 绑定id
     */
    private String bindId;

    /**
     * 还款总金额
     */
    @NotEmpty(message = "还款总金额不能为空")
    private String totalAmount;

    /**
     * 借款编号
     */
    @NotEmpty(message = "借款编号不能为空")
    private String loanId;
    
    
    
    /** 银行卡号*/
	private String bankCard;
	
	/** 银行类型*/
	private String bankCode;
	
	/** 身份证号*/
	private String idCard;
	
	/** 姓名*/
	private String cardHolder;
	
	/** 银行预留手机号*/
	private String mobile;

    /**
     * 还款列表
     */
    @NotNull(message = "还款列表不能为空")
    private List<Repayment> repayments;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public List<Repayment> getRepayments() {
        return repayments;
    }

    public void setRepayments(List<Repayment> repayments) {
        this.repayments = repayments;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
    
    
}
