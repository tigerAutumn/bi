package com.pinting.business.model.vo;

import com.pinting.business.model.BsTicketPreDetail;

import java.util.Date;

public class BsTicketPreDetailVO extends BsTicketPreDetail {
    // 查询条件
    private String serialNoSearch; // 发放计划批次号

    private Integer pageNum = 1;

    private Integer numPerPage = 20;

    private Integer start = 1;

    // 响应数据
    private String mobile; // 用户手机号

    private Date registerTime; // 注册时间

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getSerialNoSearch() {
        return serialNoSearch;
    }

    public void setSerialNoSearch(String serialNoSearch) {
        this.serialNoSearch = serialNoSearch;
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
}