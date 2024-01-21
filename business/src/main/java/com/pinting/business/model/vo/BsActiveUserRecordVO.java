package com.pinting.business.model.vo;

import com.pinting.business.model.BsActiveUserRecord;

public class BsActiveUserRecordVO extends BsActiveUserRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4951864344855871556L;

	private Integer sum;
	private Integer h5num;
	private Integer appnum;
	private Integer pcnum;
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	public Integer getH5num() {
		return h5num;
	}
	public void setH5num(Integer h5num) {
		this.h5num = h5num;
	}
	public Integer getAppnum() {
		return appnum;
	}
	public void setAppnum(Integer appnum) {
		this.appnum = appnum;
	}
	public Integer getPcnum() {
		return pcnum;
	}
	public void setPcnum(Integer pcnum) {
		this.pcnum = pcnum;
	}
}
