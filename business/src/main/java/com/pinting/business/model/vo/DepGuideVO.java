package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/12
 * Description: 存管引导相关（前端相关）
 */
public class DepGuideVO implements Serializable {

    private static final long serialVersionUID = -8542911180414506511L;

    /* 账户类型：DEP-存管账户；DOUBLE-双帐户；SIMPLE-普通账户 */
    private String accountType;

    /* 是否恒丰开户：NO_BIND_CARD-未绑卡；FAILED_BIND_HF-恒丰批量开户失败；WAIT_ACTIVATE-待激活；OPEN-已激活； */
    private String isOpened;

    /* 是否实名：YES-已实名；NO-未实名 */
    private String isBindName;

    public String getIsBindName() {
        return isBindName;
    }

    public void setIsBindName(String isBindName) {
        this.isBindName = isBindName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getIsOpened() {
        return isOpened;
    }

    public void setIsOpened(String isOpened) {
        this.isOpened = isOpened;
    }
}
