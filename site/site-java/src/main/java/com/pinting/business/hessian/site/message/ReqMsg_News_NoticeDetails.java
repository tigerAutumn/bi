package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      shh
 * Date:        2017/7/29
 * Description: 查询某一个平台公告详情 入参
 */
public class ReqMsg_News_NoticeDetails extends ReqMsg {

    private static final long serialVersionUID = 3360713088600397371L;

    /* 公告id */
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
