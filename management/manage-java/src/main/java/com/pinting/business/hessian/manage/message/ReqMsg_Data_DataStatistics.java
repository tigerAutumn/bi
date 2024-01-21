/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_Data_DataStatistics.java, v 0.1 2015-12-14 下午2:10:41 HuanXiong Exp $
 */
public class ReqMsg_Data_DataStatistics extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 3095657373916485209L;

    /**
     * 查询类别{
     *  NEW_USER_NUM:新注册人数,
     *  NEW_USER_BALANCE: 新用户购买金额,
     *  OLD_USER_BALANCE: 老用户购买金额
     * }
     */
    private String            queryType;

    /**
     * 时间维度 {
     *  day: 按天查询
     *  hour: 按小时查询
     * }
     */
    private String            timeType;

    // 最近30天；15天；7天。
    private String            currentDays;

    // 截止时间，若currentDays不为空，则以下置为空；若按小时查询，默认是startTime
    private String            startTime;
    private String            endTime;

    // 渠道
    private String            agent;
    
    
    
    
    
    
    
    

    /**
    * 每页显示的记录数(默认为20条,可以通过set改变其数量)
    */
    private int               numPerPage       = 20;

    /**
     * 总记录数
     */
    private int               totalRows;

    /**
     * 当前页码
     */
    private int               pageNum;
    /**
     * 当前开始下标
     */
    private int               start            = 1;

    /**
     * 当前结束下标
     */
    private int               end;

    /**
     * 排列方式
     */
    private String            sord;

    /**
     * 总页数
     */
    private int               totalPages;
    /**
     * 升序降序
     */
    private String            orderDirection;
    /**
     * 排序的列名称
     */
    private String            orderField;
    
    private String agentIds;
	
    private String nonAgentId;

    /**
     * 返回当前页面数
     * 
     * @return
     */
    public int getPageNum() {

        if (pageNum < 1) {

            this.pageNum = 1;
        }

        return pageNum;
    }

    /**
     * 返回总页数
     * 
     * @return
     */
    public int getTotalPages() {

        totalPages = (int) Math.ceil((double) totalRows / numPerPage);
        return totalPages;
    }

    /**
     * 返回总行数
     * 
     * @return
     */
    public int getTotalRows() {

        return totalRows;
    }

    /**
     * 第一页
     * 
     * @return
     */
    public int firstPage() {

        return 1;
    }

    /**
     * 最后一页
     * 
     * @return
     */
    public int lastPage() {

        return getTotalPages();
    }

    /**
     * 上一页
     * 
     * @return
     */
    public int previousPage() {

        return (pageNum - 1 > getTotalPages()) ? getTotalPages() : pageNum - 1;
    }

    /**
     * 下一页
     * 
     * @return
     */
    public int nextPage() {

        return (pageNum + 1 > getTotalPages()) ? getTotalPages() : pageNum + 1;
    }

    /**
     * 是否是第一页
     * 
     * @return
     */
    public boolean isFirstPage() {

        return (pageNum == 1) ? true : false;
    }

    /**
     * 是否是最后一页
     * 
     * @return
     */
    public boolean isLastPage() {

        return (pageNum == getTotalPages()) ? true : false;
    }

    /**
     * 当前开始下标
     */
    public int getStart() {
        // start = (curPage <= 1) ? 0 : ((curPage - 1) * pageSize) + 1;
        // oracle的分页

        start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage); // mysql的分页

        return start;
    }

    /**
     * 当前结束下标
     */
    public int getEnd() {
        end = getStart() + numPerPage - 1;

        return end;
    }

    /**
     * 每页显示的记录数(默认为10条,可以通过set改变其数量)
     * 
     * @return
     */
    public int getNumPerPage() {

        return numPerPage;
    }

    public void setPageNum(int pageNum) {

        this.pageNum = pageNum;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getCurrentDays() {
        return currentDays;
    }

    public void setCurrentDays(String currentDays) {
        this.currentDays = currentDays;
    }

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

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

	public String getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(String agentIds) {
		this.agentIds = agentIds;
	}

	public String getNonAgentId() {
		return nonAgentId;
	}

	public void setNonAgentId(String nonAgentId) {
		this.nonAgentId = nonAgentId;
	}
}
