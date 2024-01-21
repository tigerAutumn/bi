package com.pinting.business.model.vo;

/**
 * Author:      cyb
 * Date:        2017/5/11
 * Description: 恒丰出入金对账文件汇总数据
 */
public class HFAccountTotalVO {

    /* 平台编号 */
    private String platNo;

    /* 对账日期 */
    private String checkDate;

    /* 成功笔数 */
    private Integer succNum;

    /* 成功金额 */
    private Double succAmount;

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getSuccNum() {
        return succNum;
    }

    public void setSuccNum(Integer succNum) {
        this.succNum = succNum;
    }

    public Double getSuccAmount() {
        return succAmount;
    }

    public void setSuccAmount(Double succAmount) {
        this.succAmount = succAmount;
    }
}
