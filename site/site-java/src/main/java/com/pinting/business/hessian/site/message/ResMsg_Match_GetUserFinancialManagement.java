package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 根据借款ID查询这一笔借款对应的理财人数据  出参
 * Created by shh on 2016/9/28 16:54.
 */
public class ResMsg_Match_GetUserFinancialManagement extends ResMsg {

    private static final long serialVersionUID = 6754827723415835122L;

    /* 理财人信息列表 */
    private ArrayList<HashMap<String, Object>> userList;

    /* 借款总本金数额 */
    private Double sumTotalAmount;

    public ArrayList<HashMap<String, Object>> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<HashMap<String, Object>> userList) {
        this.userList = userList;
    }

    public Double getSumTotalAmount() {
        return sumTotalAmount;
    }

    public void setSumTotalAmount(Double sumTotalAmount) {
        this.sumTotalAmount = sumTotalAmount;
    }
}
