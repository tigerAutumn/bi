package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;


/**
 * Author:      shh
 * Date:        2018/1/15
 * Description: 查询协议版本信息
 */
public class ReqMsg_AgreementVersion_QueryAgreementVersionInfo extends ReqMsg{

    private static final long serialVersionUID = -1423356999186713489L;

    /* 协议类型 */
    private String agreementType;

    /* 协议生效时间 */
    private String agreementEffectiveStartTime;

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }

    public String getAgreementEffectiveStartTime() {
        return agreementEffectiveStartTime;
    }

    public void setAgreementEffectiveStartTime(String agreementEffectiveStartTime) {
        this.agreementEffectiveStartTime = agreementEffectiveStartTime;
    }
}
