package com.pinting.business.model.vo;

import com.pinting.business.model.BsUser;

import java.util.List;

public class BsTicketInterestManualVO extends BsInterestTicketGrantAttrVO {

    // 查询条件
    private String userIds; // 用户编号集合字符串(以,拼接)

    private Integer checkId; // 卡券发放计划ID，克隆使用

    private Integer pageNum = 1;

    private Integer numPerPage = 20;

    private Integer start = 1;

    private Integer totalRows;

    // 响应数据
    private Integer receiveNum; // 接收处理用户数量

    private List<BsUser> userOperateList; // 用户信息

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
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

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public List<BsUser> getUserOperateList() {
        return userOperateList;
    }

    public void setUserOperateList(List<BsUser> userOperateList) {
        this.userOperateList = userOperateList;
    }

    @Override
    public Integer getCheckId() {
        return checkId;
    }

    @Override
    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }
}