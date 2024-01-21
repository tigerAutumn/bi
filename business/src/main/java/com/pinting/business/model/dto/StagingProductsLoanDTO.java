package com.pinting.business.model.dto;

import com.pinting.business.model.vo.PageInfoObject;

import java.util.Date;

/**
 * Created by 剑钊 on 2016/11/9.
 */
public class StagingProductsLoanDTO extends PageInfoObject{

    private Date loanTimeStart;

    private Date loanTimeEnd;


    public Date getLoanTimeEnd() {
        return loanTimeEnd;
    }

    public void setLoanTimeEnd(Date loanTimeEnd) {
        this.loanTimeEnd = loanTimeEnd;
    }

    public Date getLoanTimeStart() {
        return loanTimeStart;
    }

    public void setLoanTimeStart(Date loanTimeStart) {
        this.loanTimeStart = loanTimeStart;
    }

}
