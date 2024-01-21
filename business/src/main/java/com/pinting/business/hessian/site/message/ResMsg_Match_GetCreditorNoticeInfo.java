package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 代偿协议债权转让通知书 出参
 * Created by zousheng on 2018/7/05 14:39.
 */
public class ResMsg_Match_GetCreditorNoticeInfo extends ResMsg {

    private static final long serialVersionUID = 5166098952968431673L;

    private String yunDaiSelfUserName; //云贷自主放款债权受让人名字

    private String yunDaiSelfIdCard; //云贷自主放款债权受让人身份证号

    private String loanAgreementNumber; //借款协议编号

    private String lateRepayDate; //代偿成功的时间

    private String loanUserName; //借款人姓名

    private String loanIdCard; //借款人身份证号

    private ArrayList<HashMap<String, Object>> dataGrid; // 债权转让通知书信息

    public String getYunDaiSelfUserName() {
        return yunDaiSelfUserName;
    }

    public void setYunDaiSelfUserName(String yunDaiSelfUserName) {
        this.yunDaiSelfUserName = yunDaiSelfUserName;
    }

    public String getYunDaiSelfIdCard() {
        return yunDaiSelfIdCard;
    }

    public void setYunDaiSelfIdCard(String yunDaiSelfIdCard) {
        this.yunDaiSelfIdCard = yunDaiSelfIdCard;
    }

    public String getLoanAgreementNumber() {
        return loanAgreementNumber;
    }

    public void setLoanAgreementNumber(String loanAgreementNumber) {
        this.loanAgreementNumber = loanAgreementNumber;
    }

    public String getLateRepayDate() {
        return lateRepayDate;
    }

    public void setLateRepayDate(String lateRepayDate) {
        this.lateRepayDate = lateRepayDate;
    }

    public String getLoanUserName() {
        return loanUserName;
    }

    public void setLoanUserName(String loanUserName) {
        this.loanUserName = loanUserName;
    }

    public String getLoanIdCard() {
        return loanIdCard;
    }

    public void setLoanIdCard(String loanIdCard) {
        this.loanIdCard = loanIdCard;
    }

    public ArrayList<HashMap<String, Object>> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(ArrayList<HashMap<String, Object>> dataGrid) {
        this.dataGrid = dataGrid;
    }
}
