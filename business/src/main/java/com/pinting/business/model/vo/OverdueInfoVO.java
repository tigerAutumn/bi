package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 4. 逾期及代偿数据统计
 * Created by cyb on 2018/3/12.
 */
public class OverdueInfoVO implements Serializable {

    private static final long serialVersionUID = -9223079528308924061L;

    // 出借人项目逾期率
    private String projectOverdueRate;
    // 出借人金额逾期率
    private String amtOverdueRate;
    // 借款人逾期金额
    private String overdueAmount;
    // 借款人逾期笔数
    private Integer overdueNumber;
    // 借款人逾期90天以上金额
    private String overdueNinnetyDaysAmount;
    // 借款人逾期90天以上笔数
    private Integer overdueNinnetyDaysNumber;
    // 累计代偿金额
    private String totalCompensatoryAmount;
    // 累计代偿笔数
    private Integer totalCompensatoryNumber;
    // 时间
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProjectOverdueRate() {
        return projectOverdueRate;
    }

    public void setProjectOverdueRate(String projectOverdueRate) {
        this.projectOverdueRate = projectOverdueRate;
    }

    public String getAmtOverdueRate() {
        return amtOverdueRate;
    }

    public void setAmtOverdueRate(String amtOverdueRate) {
        this.amtOverdueRate = amtOverdueRate;
    }

    public String getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(String overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public Integer getOverdueNumber() {
        return overdueNumber;
    }

    public void setOverdueNumber(Integer overdueNumber) {
        this.overdueNumber = overdueNumber;
    }

    public String getOverdueNinnetyDaysAmount() {
        return overdueNinnetyDaysAmount;
    }

    public void setOverdueNinnetyDaysAmount(String overdueNinnetyDaysAmount) {
        this.overdueNinnetyDaysAmount = overdueNinnetyDaysAmount;
    }

    public Integer getOverdueNinnetyDaysNumber() {
        return overdueNinnetyDaysNumber;
    }

    public void setOverdueNinnetyDaysNumber(Integer overdueNinnetyDaysNumber) {
        this.overdueNinnetyDaysNumber = overdueNinnetyDaysNumber;
    }

    public String getTotalCompensatoryAmount() {
        return totalCompensatoryAmount;
    }

    public void setTotalCompensatoryAmount(String totalCompensatoryAmount) {
        this.totalCompensatoryAmount = totalCompensatoryAmount;
    }

    public Integer getTotalCompensatoryNumber() {
        return totalCompensatoryNumber;
    }

    public void setTotalCompensatoryNumber(Integer totalCompensatoryNumber) {
        this.totalCompensatoryNumber = totalCompensatoryNumber;
    }
}
