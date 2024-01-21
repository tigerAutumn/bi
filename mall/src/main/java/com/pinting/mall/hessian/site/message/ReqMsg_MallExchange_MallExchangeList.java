package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 积分商城商品兑换列表
 *
 * @author shh
 * @date 2018/5/16 11:25
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_MallExchange_MallExchangeList extends ReqMsg {

    private static final long serialVersionUID = 5176290528717726529L;

    private Integer userIdParam;

    private Integer pageIndex;

    private Integer pageSize;

    public Integer getUserIdParam() {
        return userIdParam;
    }

    public void setUserIdParam(Integer userIdParam) {
        this.userIdParam = userIdParam;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
