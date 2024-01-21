package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by houjf on 2017/3/30.
 */
public class ResMsg_RepaySchedule_PaymentPlant extends ResMsg {

    private static final long serialVersionUID = 2104064560167780266L;

    /*查询待收的条数记录*/
    private Integer totalRecord;

    /*查询往期回款记录条数*/
    private Integer totalRecordPast;

    /*往期回款列表*/
    private ArrayList<HashMap<String,Object>> paymentPlantPastList;

    /*待收款列表*/
    private ArrayList<HashMap<String,Object>> paymentPastList;

    /*回款计划详情条数*/
    private Integer count;

    /*回款计划详情分页list*/
    private ArrayList<HashMap<String,Object>> paymentPlantDetailsList;

    private Double totalRepayScheduleTotalAmount;

    private Double totalPlanTotalRepaied;

    private Double totalReceivableAmount;
    
	private Integer totalCount;
	private Integer pageIndex;
	
    public Double getTotalRepayScheduleTotalAmount() {
        return totalRepayScheduleTotalAmount;
    }

    public void setTotalRepayScheduleTotalAmount(Double totalRepayScheduleTotalAmount) {
        this.totalRepayScheduleTotalAmount = totalRepayScheduleTotalAmount;
    }

    public Double getTotalPlanTotalRepaied() {
        return totalPlanTotalRepaied;
    }

    public void setTotalPlanTotalRepaied(Double totalPlanTotalRepaied) {
        this.totalPlanTotalRepaied = totalPlanTotalRepaied;
    }

    public Double getTotalReceivableAmount() {
        return totalReceivableAmount;
    }

    public void setTotalReceivableAmount(Double totalReceivableAmount) {
        this.totalReceivableAmount = totalReceivableAmount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<HashMap<String, Object>> getPaymentPlantDetailsList() {
        return paymentPlantDetailsList;
    }

    public void setPaymentPlantDetailsList(ArrayList<HashMap<String, Object>> paymentPlantDetailsList) {
        this.paymentPlantDetailsList = paymentPlantDetailsList;
    }

    public Integer getTotalRecordPast() {
        return totalRecordPast;
    }

    public void setTotalRecordPast(Integer totalRecordPast) {
        this.totalRecordPast = totalRecordPast;
    }

    public ArrayList<HashMap<String, Object>> getPaymentPlantPastList() {
        return paymentPlantPastList;
    }

    public void setPaymentPlantPastList(ArrayList<HashMap<String, Object>> paymentPlantPastList) {
        this.paymentPlantPastList = paymentPlantPastList;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public ArrayList<HashMap<String, Object>> getPaymentPastList() {
        return paymentPastList;
    }

    public void setPaymentPastList(ArrayList<HashMap<String, Object>> paymentPastList) {
        this.paymentPastList = paymentPastList;
    }

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
    
}
