package com.pinting.business.accounting.loan.model;

import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanRelation;

import java.util.List;

/**
 * Created by cyb on 2018/3/2.
 */
public class ProtocolSealVO {

    /* 协议编号 */
    private String agreementNo;

    /* 借款 */
    private LnLoan lnLoan;

    /* 借贷关系 */
    private List<LnLoanRelation> loanRelations;
    
    public ProtocolSealVO(String agreementNo, LnLoan lnLoan) {
        this.agreementNo = agreementNo;
        this.lnLoan = lnLoan;
    }

    public ProtocolSealVO(String agreementNo, LnLoan lnLoan, List<LnLoanRelation> loanRelations) {
        this.agreementNo = agreementNo;
        this.lnLoan = lnLoan;
        this.loanRelations = loanRelations;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public LnLoan getLnLoan() {
        return lnLoan;
    }

    public void setLnLoan(LnLoan lnLoan) {
        this.lnLoan = lnLoan;
    }

    public List<LnLoanRelation> getLoanRelations() {
        return loanRelations;
    }

    public void setLoanRelations(List<LnLoanRelation> loanRelations) {
        this.loanRelations = loanRelations;
    }
}
