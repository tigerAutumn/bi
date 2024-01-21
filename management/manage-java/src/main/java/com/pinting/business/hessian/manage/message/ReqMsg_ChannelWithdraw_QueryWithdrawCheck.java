/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_ChannelWithdraw_QueryWithdrawCheck.java, v 0.1 2016-1-8 上午10:57:18 HuanXiong Exp $
 */
public class ReqMsg_ChannelWithdraw_QueryWithdrawCheck extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -7081455412995011274L;

    //    手机号、用户姓名、状态列表
    private String mobile;
    
    private String userName;
    
    private String status;
    
    private Integer pageNum = 0;
    
    private Integer numPerPage = 20;
    
    private Integer start = 1;
    
    public Integer getStart() {
        start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public int getPageNum() {

        if (pageNum < 1) {

            this.pageNum = 1;
        }

        return pageNum;
    }
    
    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    
}
