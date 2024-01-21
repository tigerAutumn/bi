package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsUserInvestView extends PageInfoObject{
    /**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 4332747969888556454L;

	private Integer id;

    private Integer todayUserNum;

    private Integer todayInvestNum;

    private Double todayInvestAmount;

    private Double todayAnnualAmount;

    private Integer totalUserNum;

    private Integer totalInvestNum;

    private Double totalInvestAmount;

    private Double totalAnnualAmount;

    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTodayUserNum() {
        return todayUserNum;
    }

    public void setTodayUserNum(Integer todayUserNum) {
        this.todayUserNum = todayUserNum;
    }

    public Integer getTodayInvestNum() {
        return todayInvestNum;
    }

    public void setTodayInvestNum(Integer todayInvestNum) {
        this.todayInvestNum = todayInvestNum;
    }

    public Double getTodayInvestAmount() {
        return todayInvestAmount;
    }

    public void setTodayInvestAmount(Double todayInvestAmount) {
        this.todayInvestAmount = todayInvestAmount;
    }

    public Double getTodayAnnualAmount() {
        return todayAnnualAmount;
    }

    public void setTodayAnnualAmount(Double todayAnnualAmount) {
        this.todayAnnualAmount = todayAnnualAmount;
    }

    public Integer getTotalUserNum() {
        return totalUserNum;
    }

    public void setTotalUserNum(Integer totalUserNum) {
        this.totalUserNum = totalUserNum;
    }

    public Integer getTotalInvestNum() {
        return totalInvestNum;
    }

    public void setTotalInvestNum(Integer totalInvestNum) {
        this.totalInvestNum = totalInvestNum;
    }

    public Double getTotalInvestAmount() {
        return totalInvestAmount;
    }

    public void setTotalInvestAmount(Double totalInvestAmount) {
        this.totalInvestAmount = totalInvestAmount;
    }

    public Double getTotalAnnualAmount() {
        return totalAnnualAmount;
    }

    public void setTotalAnnualAmount(Double totalAnnualAmount) {
        this.totalAnnualAmount = totalAnnualAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}