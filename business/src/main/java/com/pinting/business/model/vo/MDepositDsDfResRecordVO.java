package com.pinting.business.model.vo;

import java.util.List;

public class MDepositDsDfResRecordVO {
	
	private List<MDepositDsDfResVO> list; //返回列表
	
	private Integer count; //列表条数
	
	private  MDepositDsDfResVO sumAmount; //总金额返回

	public List<MDepositDsDfResVO> getList() {
		return list;
	}

	public void setList(List<MDepositDsDfResVO> list) {
		this.list = list;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public MDepositDsDfResVO getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(MDepositDsDfResVO sumAmount) {
		this.sumAmount = sumAmount;
	}

}
