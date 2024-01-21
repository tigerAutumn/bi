package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 3. 用户数据统计
 * Created by cyb on 2018/3/12.
 */
public class UserDataVO implements Serializable {

    private static final long serialVersionUID = -9028210959302225306L;
    // 出借人借款数据统计
    private LenderOrBorrowerData lenderData;
    // 借款人借款数据统计
    private LenderOrBorrowerData borrowerData;
    // 年龄||性别分布
    private AgeOrGenderProportion ageOrGenderProportion;
    // 时间
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public LenderOrBorrowerData getLenderData() {
        return lenderData;
    }

    public void setLenderData(LenderOrBorrowerData lenderData) {
        this.lenderData = lenderData;
    }

    public LenderOrBorrowerData getBorrowerData() {
        return borrowerData;
    }

    public void setBorrowerData(LenderOrBorrowerData borrowerData) {
        this.borrowerData = borrowerData;
    }

    public AgeOrGenderProportion getAgeOrGenderProportion() {
        return ageOrGenderProportion;
    }

    public void setAgeOrGenderProportion(AgeOrGenderProportion ageOrGenderProportion) {
        this.ageOrGenderProportion = ageOrGenderProportion;
    }

}
