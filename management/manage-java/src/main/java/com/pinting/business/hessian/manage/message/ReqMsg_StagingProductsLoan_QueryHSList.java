package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * 杭商-分期产品出借查询 入参
 * Created by shh on 2016/11/7 20:07.
 */
public class ReqMsg_StagingProductsLoan_QueryHSList extends ReqMsg {

    private static final long serialVersionUID = -1725661138507978743L;

    private Date loanTimeStart;

    private Date loanTimeEnd;

    private Integer totalRows;

    private int pageNum;

    /** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
    private int numPerPage = 20;

    /** 排序 */
    private String orderField;

    private String orderDirection;

    public Date getLoanTimeStart() {
        return loanTimeStart;
    }

    public void setLoanTimeStart(Date loanTimeStart) {
        this.loanTimeStart = loanTimeStart;
    }

    public Date getLoanTimeEnd() {
        return loanTimeEnd;
    }

    public void setLoanTimeEnd(Date loanTimeEnd) {
        this.loanTimeEnd = loanTimeEnd;
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
}
