package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 回款计划详情APP端 出参
 * Created by shh on 2017/3/31.
 */
public class ResMsg_RepaySchedule_RepayScheduleDetails extends ResMsg {

    private static final long serialVersionUID = -32260620253428344L;

    private ArrayList<HashMap<String, Object>> repayScheduleDetailsList;

    public ArrayList<HashMap<String, Object>> getRepayScheduleDetailsList() {
        return repayScheduleDetailsList;
    }

    public void setRepayScheduleDetailsList(ArrayList<HashMap<String, Object>> repayScheduleDetailsList) {
        this.repayScheduleDetailsList = repayScheduleDetailsList;
    }
}
