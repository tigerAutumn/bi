package com.pinting.gateway.loan7.in.model;

import java.util.List;

import net.sf.ezmorph.bean.MorphDynaBean;

/**
 * 自主放款-代扣还款-一级循环
 * @author bianyatian
 * @2016-11-29 上午9:59:41
 */
public class CutRepayConfirmLoans {
	
	private String loanId; // 借款编号
	
	private Long singleTotalAmount; // 还款金额
	
	private List<MorphDynaBean> repayments; //还款信息；二级循环

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public Long getSingleTotalAmount() {
		return singleTotalAmount;
	}

	public void setSingleTotalAmount(Long singleTotalAmount) {
		this.singleTotalAmount = singleTotalAmount;
	}

	public List<MorphDynaBean> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<MorphDynaBean> repayments) {
		this.repayments = repayments;
	}
	
	

}
