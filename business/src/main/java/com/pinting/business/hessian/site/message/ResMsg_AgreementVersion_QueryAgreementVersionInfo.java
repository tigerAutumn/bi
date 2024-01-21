package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      shh
 * Date:        2018/1/15
 * Description:
 */
public class ResMsg_AgreementVersion_QueryAgreementVersionInfo extends ResMsg {

    private static final long serialVersionUID = 4633303255672397906L;

    /* 协议版本号 */
    private String agreementVersion;

    public String getAgreementVersion() {
        return agreementVersion;
    }

    public void setAgreementVersion(String agreementVersion) {
        this.agreementVersion = agreementVersion;
    }
}
