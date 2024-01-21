package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * 用户利息回款查询 入参
 * Created by shh on 2016/10/11 21:57.
 */
public class ReqMsg_BsUser_BsUserInterestRepayment extends ReqMsg {

    private static final long serialVersionUID = -8749529645415987572L;

    /* 购买开始时间 */
    private Date openTimeStart;

    /* 购买结束时间 */
    private Date openTimeEnd;

    private Integer totalRows;

    private int pageNum;

    /** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
    private int numPerPage = 20;

    /** 排序 */
    private String orderField;

    private String orderDirection;

    private String queryDateFlag;

    public Date getOpenTimeStart() {
        return openTimeStart;
    }

    public void setOpenTimeStart(Date openTimeStart) {
        this.openTimeStart = openTimeStart;
    }

    public Date getOpenTimeEnd() {
        return openTimeEnd;
    }

    public void setOpenTimeEnd(Date openTimeEnd) {
        this.openTimeEnd = openTimeEnd;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
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

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getQueryDateFlag() {
        return queryDateFlag;
    }

    public void setQueryDateFlag(String queryDateFlag) {
        this.queryDateFlag = queryDateFlag;
    }
}
