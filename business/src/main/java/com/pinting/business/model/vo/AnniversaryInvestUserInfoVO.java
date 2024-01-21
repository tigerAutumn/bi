package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/2/23
 * Description: 2017周年庆期间投资用户信息
 */
public class AnniversaryInvestUserInfoVO implements Serializable {

    private static final long serialVersionUID = -1140926306840634134L;

    /* 投资用户ID */
    private Integer userId;

    /* 投资用户姓名 */
    private String userName;

    /* 年化投资额 */
    private Double amount;

    /* 排名 */
    private Integer rank;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
