package com.pinting.gateway.zsd.in.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 还款预下单服务
 * @author SHENGUOPING
 * @date  2017年8月30日 下午5:20:22
 */
public class ZsdRepayPreReq extends BaseReqModel {
	
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
    
    /** 银行卡号*/
    @NotEmpty(message = "银行卡号不能为空")
	private String bankCard;
	
	/** 银行类型*/
    @NotEmpty(message = "银行类型不能为空")
	private String bankCode;
	
	/** 身份证号*/
    @NotEmpty(message = "身份证号不能为空")
    @Pattern(regexp="^(\\d{18,18}|\\d{15,15}|\\d{17,17}x|\\d{17,17}X)$", message="身份证号格式错误")
	private String idCard;
	
	/** 姓名*/
	@NotEmpty(message = "姓名不能为空")
	private String cardHolder;
	
	/** 银行预留手机号*/
	@NotEmpty(message = "手机号不能为空")
	@Pattern(regexp="^[0-9]{11}$", message="手机号格式错误")
	private String mobile;

    /**
     * 还款总金额
     */
    @NotEmpty(message = "还款总金额不能为空")
    private Long totalAmount;

    /**
     * 借款编号
     */
    @NotEmpty(message = "借款编号不能为空")
    private String loanId;

    /**
     * 还款列表
     */
    @NotNull(message = "还款列表不能为空")
    private List<ZsdRepaymentReq> repayments;

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

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<ZsdRepaymentReq> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<ZsdRepaymentReq> repayments) {
		this.repayments = repayments;
	}
    
}
