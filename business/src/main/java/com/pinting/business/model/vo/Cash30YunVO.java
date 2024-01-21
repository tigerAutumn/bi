package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 云贷自主放款未来30天待兑付查询
 * @project business
 * @title Cash30YunVO.java
 * @author Dragon & cat
 * @date 2017-6-20
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class Cash30YunVO {
	/*兑付日*/
	private  	Date		finishDate;
	/*预计退出债权总额*/
	private		Double		totalAmount;		
	/*预计预计前日还款资金*/
	private		Double		returnedAmount;	
	/*预计预计前日还款资金*/
	private		Double		prepareAmount;
	
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getReturnedAmount() {
		return returnedAmount;
	}
	public void setReturnedAmount(Double returnedAmount) {
		this.returnedAmount = returnedAmount;
	}
	public Double getPrepareAmount() {
		return prepareAmount;
	}
	public void setPrepareAmount(Double prepareAmount) {
		this.prepareAmount = prepareAmount;
	}	
}
