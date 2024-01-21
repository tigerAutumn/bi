package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 业务概览-北京财务 出参
 * Created by shh on 2017/2/16 14:27.
 */
public class ResMsg_FinancialAccount_AccountDataList extends ResMsg {

    private static final long serialVersionUID = 3242665581555598901L;

    /* 当前云贷存量总额 */
    private Double yundaiTotalAmount;

    /* 当前七贷存量总额 */
    private Double totalAmount7Dai;

    /* 当前赞分期存量总额 */
    private Double zanTotalAmount;

    /* 累计理财总额 */
    private Double totalAmount;

    /* 累计云贷融资总额 */
    private Double yundaiFinancingAmount;

    /* 累计七贷融资总额 */
    private Double financingAmount7Dai;

    /* 累计赞分期融资总额 */
    private Double zanFinancingAmount;

    /* 累计融资总额 */
    private Double financingAmount;

    private Double totalBuyAmount;

    private Double buyAmountForYunDai;

    private Double buyAmountFor7Dai;

    private Double buyAmountForZan;

    private Double totalReturnAmount;

    private Double returnAmountForYunDai;

    private Double returnAmountFor7Dai;

    private Double returnAmountForZan;

    private Double totalFinanceAmount;

    private Double financeAmountToYunDai;

    private Double financeAmountTo7Dai;

    private Double financeAmountToZan;

    private Integer totalRows;

    private Integer pageNum;

    private Integer numPerPage;

    private ArrayList<HashMap<String,Object>> valueList;

    public Double getYundaiTotalAmount() {
        return yundaiTotalAmount;
    }

    public void setYundaiTotalAmount(Double yundaiTotalAmount) {
        this.yundaiTotalAmount = yundaiTotalAmount;
    }

    public Double getTotalAmount7Dai() {
        return totalAmount7Dai;
    }

    public void setTotalAmount7Dai(Double totalAmount7Dai) {
        this.totalAmount7Dai = totalAmount7Dai;
    }

    public Double getZanTotalAmount() {
        return zanTotalAmount;
    }

    public void setZanTotalAmount(Double zanTotalAmount) {
        this.zanTotalAmount = zanTotalAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getYundaiFinancingAmount() {
        return yundaiFinancingAmount;
    }

    public void setYundaiFinancingAmount(Double yundaiFinancingAmount) {
        this.yundaiFinancingAmount = yundaiFinancingAmount;
    }

    public Double getFinancingAmount7Dai() {
        return financingAmount7Dai;
    }

    public void setFinancingAmount7Dai(Double financingAmount7Dai) {
        this.financingAmount7Dai = financingAmount7Dai;
    }

    public Double getZanFinancingAmount() {
        return zanFinancingAmount;
    }

    public void setZanFinancingAmount(Double zanFinancingAmount) {
        this.zanFinancingAmount = zanFinancingAmount;
    }

    public Double getFinancingAmount() {
        return financingAmount;
    }

    public void setFinancingAmount(Double financingAmount) {
        this.financingAmount = financingAmount;
    }

    public Double getTotalBuyAmount() {
        return totalBuyAmount;
    }

    public void setTotalBuyAmount(Double totalBuyAmount) {
        this.totalBuyAmount = totalBuyAmount;
    }

    public Double getBuyAmountForYunDai() {
        return buyAmountForYunDai;
    }

    public void setBuyAmountForYunDai(Double buyAmountForYunDai) {
        this.buyAmountForYunDai = buyAmountForYunDai;
    }

    public Double getBuyAmountFor7Dai() {
        return buyAmountFor7Dai;
    }

    public void setBuyAmountFor7Dai(Double buyAmountFor7Dai) {
        this.buyAmountFor7Dai = buyAmountFor7Dai;
    }

    public Double getBuyAmountForZan() {
        return buyAmountForZan;
    }

    public void setBuyAmountForZan(Double buyAmountForZan) {
        this.buyAmountForZan = buyAmountForZan;
    }

    public Double getTotalReturnAmount() {
        return totalReturnAmount;
    }

    public void setTotalReturnAmount(Double totalReturnAmount) {
        this.totalReturnAmount = totalReturnAmount;
    }

    public Double getReturnAmountForYunDai() {
        return returnAmountForYunDai;
    }

    public void setReturnAmountForYunDai(Double returnAmountForYunDai) {
        this.returnAmountForYunDai = returnAmountForYunDai;
    }

    public Double getReturnAmountFor7Dai() {
        return returnAmountFor7Dai;
    }

    public void setReturnAmountFor7Dai(Double returnAmountFor7Dai) {
        this.returnAmountFor7Dai = returnAmountFor7Dai;
    }

    public Double getReturnAmountForZan() {
        return returnAmountForZan;
    }

    public void setReturnAmountForZan(Double returnAmountForZan) {
        this.returnAmountForZan = returnAmountForZan;
    }

    public Double getTotalFinanceAmount() {
        return totalFinanceAmount;
    }

    public void setTotalFinanceAmount(Double totalFinanceAmount) {
        this.totalFinanceAmount = totalFinanceAmount;
    }

    public Double getFinanceAmountToYunDai() {
        return financeAmountToYunDai;
    }

    public void setFinanceAmountToYunDai(Double financeAmountToYunDai) {
        this.financeAmountToYunDai = financeAmountToYunDai;
    }

    public Double getFinanceAmountTo7Dai() {
        return financeAmountTo7Dai;
    }

    public void setFinanceAmountTo7Dai(Double financeAmountTo7Dai) {
        this.financeAmountTo7Dai = financeAmountTo7Dai;
    }

    public Double getFinanceAmountToZan() {
        return financeAmountToZan;
    }

    public void setFinanceAmountToZan(Double financeAmountToZan) {
        this.financeAmountToZan = financeAmountToZan;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public ArrayList<HashMap<String, Object>> getValueList() {
        return valueList;
    }

    public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
        this.valueList = valueList;
    }
}
