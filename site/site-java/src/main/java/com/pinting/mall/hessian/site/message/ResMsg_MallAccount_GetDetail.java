package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MallAccount_GetDetail extends ResMsg {

    private static final long serialVersionUID = 1L;

    private Integer id; // 积分商城用户余额表ID

    private Integer userId; // 用户编号ID

    private String accountType; // 账户类型 POINTS_JSH:积分余额户

    private Long balance; // 积分余额

    private Long avaliableBalance; // 可用积分余额

    private Long freezeBalance; // 冻结积分余额

    private Long totalGainPoints; // 累计赚取积分

    private Long totalUsedPoints; // 累计兑换积分

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getAvaliableBalance() {
        return avaliableBalance;
    }

    public void setAvaliableBalance(Long avaliableBalance) {
        this.avaliableBalance = avaliableBalance;
    }

    public Long getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(Long freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public Long getTotalGainPoints() {
        return totalGainPoints;
    }

    public void setTotalGainPoints(Long totalGainPoints) {
        this.totalGainPoints = totalGainPoints;
    }

    public Long getTotalUsedPoints() {
        return totalUsedPoints;
    }

    public void setTotalUsedPoints(Long totalUsedPoints) {
        this.totalUsedPoints = totalUsedPoints;
    }
}
