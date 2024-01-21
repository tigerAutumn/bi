package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.core.util.DateUtil;

public class YunRepayPlanVO {

	private int  serialId; // 期数
    private int days;   // 本期计息天数
    private Date planDate;   // 还款日
    
    public int getSerialId() {
		return serialId;
	}
	public void setSerialId(int serialId) {
		this.serialId = serialId;
	} 
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
    
	@Override
    public String toString() {
        return "YunRepayPlanVO[serialId=" + serialId + ",days=" + days 
                +",planDate="
                + DateUtil.formatYYYYMMDD(planDate) + "]";
    }
	
}
