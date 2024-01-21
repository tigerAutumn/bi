package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/3/16
 * Description: 红包列表
 */
public class ReqMsg_RedPacketInfo_QueryRedPacketList extends ReqMsg {

    private static final long serialVersionUID = 2723835064837042554L;

    private Integer userId;
    
    private String status;  // 状态

    private Integer pageNum;    // 页码 1

    private Integer numPerPage; // 每页显示条数
    
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
