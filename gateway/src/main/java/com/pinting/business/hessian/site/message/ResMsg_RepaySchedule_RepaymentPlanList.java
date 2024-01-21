package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 回款计划APP端 出参
 * Created by shh on 2017/3/31.
 */
public class ResMsg_RepaySchedule_RepaymentPlanList extends ResMsg {

    private static final long serialVersionUID = -2630412343459989038L;

    private ArrayList<HashMap<String, Object>> repaymentPlanList;

    public ArrayList<HashMap<String, Object>> getRepaymentPlanList() {
        return repaymentPlanList;
    }

    public void setRepaymentPlanList(ArrayList<HashMap<String, Object>> repaymentPlanList) {
        this.repaymentPlanList = repaymentPlanList;
    }
}
