package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 存管港湾产品借款协议数据 入参
 * Created by shh on 2017/5/2 15:15.
 */
public class ReqMsg_Match_GetCustodyLoanInfo extends ReqMsg {

    private static final long serialVersionUID = -958119438219828751L;

    /* 借贷关系编号 */
    private Integer lnLoanRelationId;

    /* 子账户编号 */
    private Integer subAccountId;

    /* 协议类型 */
    private String agreementType;

    public Integer getLnLoanRelationId() {
        return lnLoanRelationId;
    }

    public void setLnLoanRelationId(Integer lnLoanRelationId) {
        this.lnLoanRelationId = lnLoanRelationId;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }
}
