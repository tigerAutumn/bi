/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_FinancialAccount_SaleReceivable.java, v 0.1 2016-2-18 下午1:55:53 HuanXiong Exp $
 */
public class ResMsg_FinancialAccount_SaleReceivable extends ResMsg {

    /**  */
    private static final long serialVersionUID = -3682312070188547738L;

    private ArrayList<HashMap<String, Object>> saleReceivableList;
    
    private Integer count;

    public ArrayList<HashMap<String, Object>> getSaleReceivableList() {
        return saleReceivableList;
    }

    public void setSaleReceivableList(ArrayList<HashMap<String, Object>> saleReceivableList) {
        this.saleReceivableList = saleReceivableList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
