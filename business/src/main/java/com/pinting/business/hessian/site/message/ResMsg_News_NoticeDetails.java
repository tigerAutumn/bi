package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;

/**
 * Author:      shh
 * Date:        2017/7/29
 * Description: 查询某一个平台公告详情 出参
 */
public class ResMsg_News_NoticeDetails extends ResMsg {

    private static final long serialVersionUID = -278282681125003840L;

    /* 结果列表 */
    private HashMap<String, Object> details;

    public HashMap<String, Object> getDetails() {
        return details;
    }

    public void setDetails(HashMap<String, Object> details) {
        this.details = details;
    }
}
