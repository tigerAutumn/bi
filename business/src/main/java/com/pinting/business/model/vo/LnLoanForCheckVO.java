package com.pinting.business.model.vo;

import com.pinting.business.model.LnLoan;

/**
 * Author:      cyb
 * Date:        2016/9/24
 * Description:
 */
public class LnLoanForCheckVO extends LnLoan {

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
