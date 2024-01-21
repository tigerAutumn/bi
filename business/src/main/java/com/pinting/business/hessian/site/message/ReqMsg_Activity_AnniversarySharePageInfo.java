package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 2017周年庆分享页面信息
 */
public class ReqMsg_Activity_AnniversarySharePageInfo extends ReqMsg {

    private static final long serialVersionUID = 7940458222328949886L;

    /* 分享者用户ID */
    private Integer shareUserId;

    /* 微信OPENID */
    private String openId;

    /* 当前页码 */
    private Integer pageNum;

    /* 每页显示条数 */
    private Integer numPerPage;

    public Integer getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Integer shareUserId) {
        this.shareUserId = shareUserId;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
