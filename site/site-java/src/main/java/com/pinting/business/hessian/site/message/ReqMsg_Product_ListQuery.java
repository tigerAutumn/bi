package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Product_ListQuery extends ReqMsg {

    private static final long serialVersionUID = 7077895019443776386L;
    
    private String userType;   // 默认NORMAL
    
    private String productShowTerminal;    // 显示端口
    
    private Integer page;
    
    private Integer status; // 状态 null（6进行中|4已发放未进行|7已完成）（6可投资）

    private String term; // 期限  0-60天;61-200天;200天以上
    
    private Integer pageNum = 1;
    
    private Integer numPerPage = 10;
    
    private Integer start = 1;
    
    private Integer totalPages; // 总页数
    
    private Integer totolRows = 0;  // 总数据数
    
    private String isSuggest;   // 是否可推荐
    
    private String activityType;    // 是否新手推荐
    
    private String returnType; //回款方式
    
    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Integer getTotalPages() {
//        totalPages = (totolRows - totolRows % numPerPage) / nu`mPerPage;
//        if(totolRows % numPerPage > 0)
//            totalPages ++;
        totalPages = (int) Math.ceil((double) totolRows / numPerPage);
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotolRows() {
        return totolRows;
    }

    public void setTotolRows(Integer totolRows) {
        this.totolRows = totolRows;
    }
    
    public Integer getStart() {
        start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
    
    public Integer getPageNum() {
        if (pageNum < 1) 
            this.pageNum = 1;
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getProductShowTerminal() {
        return productShowTerminal;
    }

    public void setProductShowTerminal(String productShowTerminal) {
        this.productShowTerminal = productShowTerminal;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getIsSuggest() {
        return isSuggest;
    }

    public void setIsSuggest(String isSuggest) {
        this.isSuggest = isSuggest;
    }

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
    
}
