package com.pinting.gateway.bird.in.model;

import com.pinting.gateway.hessian.message.loan.model.Repayment;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @title 还款代扣请求
 * Created by 剑钊 on 2016/12/29.
 */
public class WithholdingRepayReq extends BaseReqModel{


    /**
     * 还款订单号
     */
    @NotEmpty(message = "订单号不能为空")
    private String orderNo;

    /**
     * 用户编号
     */
    @NotNull(message = "用户编号不能为空")
    private String userId;

    /**
     * 代扣目标
     */
    private String cutTarget;

    /**
     * 绑定id
     */
    private String bindId;
    
	/** 银行卡号*/
	private String bankCard;
	
	/** 身份证号*/
	private String idCard;
	
	/** 姓名*/
	private String cardHolder;
	
	/** 银行预留手机号*/
	private String mobile;

    /**
     * 当前还款用户姓名
     */
    private String payUserName;

    /**
     * 当前还款用户身份证
     */
    private String payUserIdCard;

    /**
     * 当前还款用户卡号
     */
    private String payUserCardNo;

    /**
     * 当前还款用户手机
     */
    private String payUserMobile;

    /**
     * 银行编码
     */
    private String bankCode;
    
    /**
     * 是否线下还款
     */
    private String isOffline;

    /**
     * 线下还款支付单号(宝付支付单号)
     */
    private String offPayOrderNo;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCutTarget() {
        return cutTarget;
    }

    public void setCutTarget(String cutTarget) {
        this.cutTarget = cutTarget;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getPayUserName() {
        return payUserName;
    }

    public void setPayUserName(String payUserName) {
        this.payUserName = payUserName;
    }

    public String getPayUserIdCard() {
        return payUserIdCard;
    }

    public void setPayUserIdCard(String payUserIdCard) {
        this.payUserIdCard = payUserIdCard;
    }

    public String getPayUserCardNo() {
        return payUserCardNo;
    }

    public void setPayUserCardNo(String payUserCardNo) {
        this.payUserCardNo = payUserCardNo;
    }

    public String getPayUserMobile() {
        return payUserMobile;
    }

    public void setPayUserMobile(String payUserMobile) {
        this.payUserMobile = payUserMobile;
    }
    

    public String getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}

	public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
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

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
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

    public String getOffPayOrderNo() {
        return offPayOrderNo;
    }

    public void setOffPayOrderNo(String offPayOrderNo) {
        this.offPayOrderNo = offPayOrderNo;
    }
}
