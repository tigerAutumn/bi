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
 * @version $Id: ResMsg_FinancialAccount_SaleAgentData.java, v 0.1 2016-2-19 上午10:06:04 HuanXiong Exp $
 */
public class ResMsg_FinancialAccount_SaleAgentData extends ResMsg {

    /**  */
    private static final long serialVersionUID = -6290780944947841993L;
    
    private ArrayList<HashMap<String, Object>> saleAgentData;
    
    private ArrayList<HashMap<String, Object>> agents;
    
    private Integer count;
    
    private Double sumCpAgentData;

    public ArrayList<HashMap<String, Object>> getSaleAgentData() {
        return saleAgentData;
    }

    public void setSaleAgentData(ArrayList<HashMap<String, Object>> saleAgentData) {
        this.saleAgentData = saleAgentData;
    }
    
    public ArrayList<HashMap<String, Object>> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<HashMap<String, Object>> agents) {
        this.agents = agents;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

	public Double getSumCpAgentData() {
		return sumCpAgentData;
	}

	public void setSumCpAgentData(Double sumCpAgentData) {
		this.sumCpAgentData = sumCpAgentData;
	}
    
}
