package com.pinting.business.model.vo;

/**
 * Created by cyb on 2017/10/17.
 */
public class ActivityYouFuRankInfoVO extends ActivityBaseVO {

    // 排名
    private Integer rank;

    // 推荐人
    private String userName;

    // 推荐人数
    private Integer recommendedNum;

    // 累计年化投资金额
    private Double balance;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRecommendedNum() {
        return recommendedNum;
    }

    public void setRecommendedNum(Integer recommendedNum) {
        this.recommendedNum = recommendedNum;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
