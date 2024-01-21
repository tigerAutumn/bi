/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 财务对账数据：结算2.0（12.1-1.31）
 * @author HuanXiong
 * @version $Id: ResMsg_FinancialAccount_ListQuery2.java, v 0.1 2016-2-15 下午12:09:58 HuanXiong Exp $
 */
public class ResMsg_FinancialAccount_ListQuery2 extends ResMsg {

    /**  */
    private static final long serialVersionUID = -4070947333983044290L;

    private ArrayList<HashMap<String, Object>> financialAccountList;
    
    private String totalInvestAmount;
    
    private String totalReturnInterestAmount;
    
    private Integer count;

    public ArrayList<HashMap<String, Object>> getFinancialAccountList() {
        return financialAccountList;
    }

    public void setFinancialAccountList(ArrayList<HashMap<String, Object>> financialAccountList) {
        this.financialAccountList = financialAccountList;
    }

    public String getTotalInvestAmount() {
        return totalInvestAmount;
    }

    public void setTotalInvestAmount(String totalInvestAmount) {
        this.totalInvestAmount = totalInvestAmount;
    }

    public String getTotalReturnInterestAmount() {
        return totalReturnInterestAmount;
    }

    public void setTotalReturnInterestAmount(String totalReturnInterestAmount) {
        this.totalReturnInterestAmount = totalReturnInterestAmount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
}
