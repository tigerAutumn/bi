package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      shh
 * Date:        2018/2/1
 * Description: 赞时贷资金迁移协议 查询借款人的资产方
 */
public class ResMsg_Match_CheckLnUserPartnerCode extends ResMsg {

    private static final long serialVersionUID = -7014381656214581041L;

    /* 借款人的资产方 */
    private String partnerCode;

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
}
