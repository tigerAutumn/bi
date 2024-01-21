package com.pinting.business.model.vo;

import java.util.List;

/**
 * 管理台存量提前赎回，返回对象
 * @author bianyatian
 * @2018-1-22 上午10:03:38
 */
public class SysReturnManageResVO {
	
	private List<SysReturnManageInfoVO> detailList; //返回页面列表
	
	private Double sumTotal; //总计总金额
	
	private Double sumPrincipal; //总计本金

	public List<SysReturnManageInfoVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<SysReturnManageInfoVO> detailList) {
		this.detailList = detailList;
	}

	public Double getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(Double sumTotal) {
		this.sumTotal = sumTotal;
	}

	public Double getSumPrincipal() {
		return sumPrincipal;
	}

	public void setSumPrincipal(Double sumPrincipal) {
		this.sumPrincipal = sumPrincipal;
	}
	
	
}
