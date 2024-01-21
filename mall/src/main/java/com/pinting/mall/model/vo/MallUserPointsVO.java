package com.pinting.mall.model.vo;

/**
 * 积分用户管理VO
 *
 * @author shh
 * @date 2018/5/15 20:00
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class MallUserPointsVO {

    /* 用户积分余额表编号id */
    private Integer id;

    /* 用户手机号 */
    private String mobile;

    /* 累计赚取积分 */
    private Long totalGainPoints;

    /* 可用积分余额 */
    private Long avaliableBalance;

    /* 查询手机号 */
    private String searchMobile;

    /* 账户累计积分起始值 */
    private String startTotalGainPoints;

    /* 账户累计积分结束值 */
    private String endTotalGainPoints;

    /* 剩余积分起始值 */
    private String startAvaliableBalance;

    /* 剩余积分结束值 */
    private String endAvaliableBalance;

    /* 当前页码 */
    private int pageNum;

    /* 每页记录数 */
    private int numPerPage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getTotalGainPoints() {
        return totalGainPoints;
    }

    public void setTotalGainPoints(Long totalGainPoints) {
        this.totalGainPoints = totalGainPoints;
    }

    public Long getAvaliableBalance() {
        return avaliableBalance;
    }

    public void setAvaliableBalance(Long avaliableBalance) {
        this.avaliableBalance = avaliableBalance;
    }

    public String getSearchMobile() {
        return searchMobile;
    }

    public void setSearchMobile(String searchMobile) {
        this.searchMobile = searchMobile;
    }

    public String getStartTotalGainPoints() {
        return startTotalGainPoints;
    }

    public void setStartTotalGainPoints(String startTotalGainPoints) {
        this.startTotalGainPoints = startTotalGainPoints;
    }

    public String getEndTotalGainPoints() {
        return endTotalGainPoints;
    }

    public void setEndTotalGainPoints(String endTotalGainPoints) {
        this.endTotalGainPoints = endTotalGainPoints;
    }

    public String getStartAvaliableBalance() {
        return startAvaliableBalance;
    }

    public void setStartAvaliableBalance(String startAvaliableBalance) {
        this.startAvaliableBalance = startAvaliableBalance;
    }

    public String getEndAvaliableBalance() {
        return endAvaliableBalance;
    }

    public void setEndAvaliableBalance(String endAvaliableBalance) {
        this.endAvaliableBalance = endAvaliableBalance;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }
}
