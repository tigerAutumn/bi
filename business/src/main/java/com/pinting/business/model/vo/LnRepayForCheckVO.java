package com.pinting.business.model.vo;

import com.pinting.business.model.LnRepay;

/**
 * Author:      cyb
 * Date:        2016/9/24
 * Description:
 */
public class LnRepayForCheckVO extends LnRepay {

    private String partnerLoanId;

    private String partnerUserId;

    private String errorMsg;

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId;
    }

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
