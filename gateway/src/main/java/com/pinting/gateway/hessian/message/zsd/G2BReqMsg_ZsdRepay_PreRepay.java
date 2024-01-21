package com.pinting.gateway.hessian.message.zsd;

import java.util.List;
import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.loan.model.Repayment;

/**
 * 还款预下单服务
 * @author SHENGUOPING
 * @date  2017年8月30日 下午5:52:04
 */
public class G2BReqMsg_ZsdRepay_PreRepay extends ReqMsg {
	
	private static final long serialVersionUID = -6509572014572845362L;

	/**
     * 还款订单号
     */
    private String orderNo;

    /**
     * 用户IP地址
     */
    private String ip;

    /**
     * 用户编号
     */
    private String userId;
    
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
     * 还款总金额
     */
    private String totalAmount;

    /**
     * 借款编号
     */
    private String loanId;

    /**
     * 还款列表
     */
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

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<Repayment> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<Repayment> repayments) {
		this.repayments = repayments;
	}
    
}
