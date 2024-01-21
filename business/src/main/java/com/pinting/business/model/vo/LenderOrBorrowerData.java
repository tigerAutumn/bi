package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * 出借人||借款人借款数据统计
 * Created by cyb on 2018/3/12.
 */
public class LenderOrBorrowerData implements Serializable {
    private static final long serialVersionUID = 4579364187937940428L;
    // 累计出借/借款人数
    private Integer totalNumber;
    // 当期出借/借款人数
    private Integer currentNumber;
    // 人均累计出借/借款金额
    private String eachTotalAmount;
    // 前十大出借人出借/借款人借款金额占比%
    private String topTenAmtProportion;
    // 最大单一出借人出借/借款人借款余额占比%
    private String topAmtProportion;

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String getEachTotalAmount() {
        return eachTotalAmount;
    }

    public void setEachTotalAmount(String eachTotalAmount) {
        this.eachTotalAmount = eachTotalAmount;
    }

    public String getTopTenAmtProportion() {
        return topTenAmtProportion;
    }

    public void setTopTenAmtProportion(String topTenAmtProportion) {
        this.topTenAmtProportion = topTenAmtProportion;
    }

    public String getTopAmtProportion() {
        return topAmtProportion;
    }

    public void setTopAmtProportion(String topAmtProportion) {
        this.topAmtProportion = topAmtProportion;
    }
}
