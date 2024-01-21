package com.pinting.business.model.vo;

/**
 * Created by cyb on 2017/10/17.
 */
public class ActivityYouFuSelfInfoVO extends ActivityBaseVO {

    // 我的排名
    private Integer rank;

    // 邀请人数
    private Integer recommendedNum;

    // 累计年化投资金额
    private Double balance;

    // 是否达到进入排行榜资格（YES ：达到（>=10万）；NO：未达到（< 10万））
    private String qualify;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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

    public String getQualify() {
        return qualify;
    }

    public void setQualify(String qualify) {
        this.qualify = qualify;
    }
}
