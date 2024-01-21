package com.pinting.manage.model;

/**
 * 查询恒丰币港湾营收（赞分期）请求参数
 * Created by cyb on 2018/4/20.
 */
public class HFBGWRevenueOfZanReq {

    private String startTime;

    private String endTime;

    private Integer pageNum;

    private Integer numPerPage = 20;

    // 是否展示数据：yes-展示；其他-不展示
    private String showData;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getShowData() {
        return showData;
    }

    public void setShowData(String showData) {
        this.showData = showData;
    }
}
