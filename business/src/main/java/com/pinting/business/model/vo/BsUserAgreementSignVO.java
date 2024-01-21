package com.pinting.business.model.vo;

import com.pinting.business.model.BsUser;

/**
 * 用户协议签章对象
 */
public class BsUserAgreementSignVO extends BsUser {

    private Integer subAccountId; // 站岗户ID

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }
}
