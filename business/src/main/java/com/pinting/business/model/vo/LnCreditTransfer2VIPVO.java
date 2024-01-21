package com.pinting.business.model.vo;

import java.util.List;

/**
 * 赞分期-债转支付VIP
 * 云贷，赞时贷-债转支付
 * @author bianyatian
 * @2016-12-5 上午9:36:20
 */
public class LnCreditTransfer2VIPVO {

	private List<LnCreditTransferMVO> list;
	
	private Integer count;
	
	private Double transSumAmount;
	
	private LnCreditTransferMVO lnCreditTransferMVO;//存管后统计数据

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getTransSumAmount() {
		return transSumAmount;
	}

	public void setTransSumAmount(Double transSumAmount) {
		this.transSumAmount = transSumAmount;
	}

	public List<LnCreditTransferMVO> getList() {
		return list;
	}

	public void setList(List<LnCreditTransferMVO> list) {
		this.list = list;
	}

	public LnCreditTransferMVO getLnCreditTransferMVO() {
		return lnCreditTransferMVO;
	}

	public void setLnCreditTransferMVO(LnCreditTransferMVO lnCreditTransferMVO) {
		this.lnCreditTransferMVO = lnCreditTransferMVO;
	}
	
}
