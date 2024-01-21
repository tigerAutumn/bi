package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * Created by cyb on 2018/3/12.
 */
public class AgeOrGenderProportion implements Serializable {
    private static final long serialVersionUID = 8226099902770532489L;
    // 18-28岁出借人年龄分布占比%
    private String youngLenderProportion;
    private Integer youngInvestorTypeNumber;
    // 29-38岁出借人年龄分布占比%
    private String middleLenderProportion;
    private Integer middleInvestorTypeNumber;
    // 40-50岁出借人年龄分布占比%
    private String fortyAgeLenderProportion;
    private Integer fortyAgeInvestorTypeNumber;
    // 50岁出借人年龄分布占比%
    private String oldLenderProportion;
    private Integer oldInvestorTypeNumber;

    // 男性出借人占比%
    private String maleLender;
    private Integer maleNumber;
    // 女性出借人占比%
    private String femaleLender;
    private Integer femaleNumber;

    public Integer getYoungInvestorTypeNumber() {
        return youngInvestorTypeNumber;
    }

    public void setYoungInvestorTypeNumber(Integer youngInvestorTypeNumber) {
        this.youngInvestorTypeNumber = youngInvestorTypeNumber;
    }

    public Integer getMiddleInvestorTypeNumber() {
        return middleInvestorTypeNumber;
    }

    public void setMiddleInvestorTypeNumber(Integer middleInvestorTypeNumber) {
        this.middleInvestorTypeNumber = middleInvestorTypeNumber;
    }

    public Integer getFortyAgeInvestorTypeNumber() {
        return fortyAgeInvestorTypeNumber;
    }

    public void setFortyAgeInvestorTypeNumber(Integer fortyAgeInvestorTypeNumber) {
        this.fortyAgeInvestorTypeNumber = fortyAgeInvestorTypeNumber;
    }

    public Integer getOldInvestorTypeNumber() {
        return oldInvestorTypeNumber;
    }

    public void setOldInvestorTypeNumber(Integer oldInvestorTypeNumber) {
        this.oldInvestorTypeNumber = oldInvestorTypeNumber;
    }

    public Integer getMaleNumber() {
        return maleNumber;
    }

    public void setMaleNumber(Integer maleNumber) {
        this.maleNumber = maleNumber;
    }

    public Integer getFemaleNumber() {
        return femaleNumber;
    }

    public void setFemaleNumber(Integer femaleNumber) {
        this.femaleNumber = femaleNumber;
    }

    public String getYoungLenderProportion() {
        return youngLenderProportion;
    }

    public void setYoungLenderProportion(String youngLenderProportion) {
        this.youngLenderProportion = youngLenderProportion;
    }

    public String getMiddleLenderProportion() {
        return middleLenderProportion;
    }

    public void setMiddleLenderProportion(String middleLenderProportion) {
        this.middleLenderProportion = middleLenderProportion;
    }

    public String getFortyAgeLenderProportion() {
        return fortyAgeLenderProportion;
    }

    public void setFortyAgeLenderProportion(String fortyAgeLenderProportion) {
        this.fortyAgeLenderProportion = fortyAgeLenderProportion;
    }

    public String getOldLenderProportion() {
        return oldLenderProportion;
    }

    public void setOldLenderProportion(String oldLenderProportion) {
        this.oldLenderProportion = oldLenderProportion;
    }

    public String getMaleLender() {
        return maleLender;
    }

    public void setMaleLender(String maleLender) {
        this.maleLender = maleLender;
    }

    public String getFemaleLender() {
        return femaleLender;
    }

    public void setFemaleLender(String femaleLender) {
        this.femaleLender = femaleLender;
    }
}
