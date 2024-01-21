package com.pinting.business.model.vo;

import com.pinting.business.model.BsTicketGrantPlanCheck;

import java.util.Date;

public class BsTicketGrantPlanCheckVO extends BsTicketGrantPlanCheck {

    // 查询条件
    private String distributeTypeSearch; // 发放模式：AUTO 自动 MANUAL 手动

    private String serialNameSearch; // 发放计划名称

    private String checkStatusSearch; // UNCHECKED 待审核 PASS 已通过 REFUSE 不通过

    private String grantStatusSearch; // 发放状态: INIT 未发放 PROCESS 发放中 FINISH 发放结束 CLOSE 已停用

    private Integer pageNum = 1;

    private Integer numPerPage = 20;

    private Integer start = 1;

    private String orderByClause;

    // 响应数据
    private Double ticketApr; // 加息幅度(基于本金的加息率%)

    private Integer grantTotal; // 发放加息券总数

    private String validTermType; // 加息券发放有效期类型:FIXED 固定时间段有效 AFTER_RECEIVE 发放后有效天数

    private Date useTimeStart; // FIXED 固定时间段：使用有效期开始

    private Date useTimeEnd; // FIXED 固定时间段：使用有效期结束

    private Integer availableDays; // AFTER_RECEIVE 发放后有效天数：使用

    private String notifyChannel; // WECHAT 微信 SMS 短信 APP app通知 可以多个，以逗号隔开

    private Double investLimit; // 达到投资金额加息券才能使用(单笔投资满多少元)

    private String productLimit; // BIGANGWAN_SERIAL（港湾系列） YONGJIN_SERIAL（涌金系列） KUAHONG_SERIAL（跨虹系列） BAOXIN_SERIAL（保信系列） 多个产品限制用逗号隔开

    private String termLimit; // 30,90,180,365（天） 多个产品期限限制用逗号隔开

    private String applicantName; // 申请人姓名

    private String checkerName; // 审核人姓名

    public Double getTicketApr() {
        return ticketApr;
    }

    public void setTicketApr(Double ticketApr) {
        this.ticketApr = ticketApr;
    }

    public Integer getGrantTotal() {
        return grantTotal;
    }

    public void setGrantTotal(Integer grantTotal) {
        this.grantTotal = grantTotal;
    }

    public String getValidTermType() {
        return validTermType;
    }

    public void setValidTermType(String validTermType) {
        this.validTermType = validTermType;
    }

    public Date getUseTimeStart() {
        return useTimeStart;
    }

    public void setUseTimeStart(Date useTimeStart) {
        this.useTimeStart = useTimeStart;
    }

    public Date getUseTimeEnd() {
        return useTimeEnd;
    }

    public void setUseTimeEnd(Date useTimeEnd) {
        this.useTimeEnd = useTimeEnd;
    }

    public Integer getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Integer availableDays) {
        this.availableDays = availableDays;
    }

    public String getNotifyChannel() {
        return notifyChannel;
    }

    public void setNotifyChannel(String notifyChannel) {
        this.notifyChannel = notifyChannel;
    }

    public Double getInvestLimit() {
        return investLimit;
    }

    public void setInvestLimit(Double investLimit) {
        this.investLimit = investLimit;
    }

    public String getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(String productLimit) {
        this.productLimit = productLimit;
    }

    public String getTermLimit() {
        return termLimit;
    }

    public void setTermLimit(String termLimit) {
        this.termLimit = termLimit;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public Integer getStart() {
        start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getDistributeTypeSearch() {
        return distributeTypeSearch;
    }

    public void setDistributeTypeSearch(String distributeTypeSearch) {
        this.distributeTypeSearch = distributeTypeSearch == null ? null : distributeTypeSearch.trim();
    }

    public String getSerialNameSearch() {
        return serialNameSearch;
    }

    public void setSerialNameSearch(String serialNameSearch) {
        this.serialNameSearch = serialNameSearch == null ? null : serialNameSearch.trim();
    }

    public String getCheckStatusSearch() {
        return checkStatusSearch;
    }

    public void setCheckStatusSearch(String checkStatusSearch) {
        this.checkStatusSearch = checkStatusSearch == null ? null : checkStatusSearch.trim();
    }

    public String getGrantStatusSearch() {
        return grantStatusSearch;
    }

    public void setGrantStatusSearch(String grantStatusSearch) {
        this.grantStatusSearch = grantStatusSearch == null ? null : grantStatusSearch.trim();
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
}