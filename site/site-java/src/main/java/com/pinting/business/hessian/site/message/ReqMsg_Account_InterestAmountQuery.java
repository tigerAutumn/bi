package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 根据子账户编号，查询产品购买成功后的加息收益金额 入参
 *
 * @author shh
 * @date 2018/8/2 15:04
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Account_InterestAmountQuery extends ReqMsg {

    private static final long serialVersionUID = 407686316220075570L;

    /* 子账户主键id */
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
