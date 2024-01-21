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
 * @version $Id: ResMsg_ChannelWithdraw_QueryWithdrawCheck.java, v 0.1 2016-1-8 上午10:57:27 HuanXiong Exp $
 */
public class ResMsg_ChannelWithdraw_QueryWithdrawCheck extends ResMsg {

    /**  */
    private static final long serialVersionUID = -8377594069694731898L;

    //    手机号、用户姓名、金额、状态、创建时间、审核时间、操作员
    ArrayList<HashMap<String, Object>> queryWithdrawCheckList;
    
    private Integer count;

    public ArrayList<HashMap<String, Object>> getQueryWithdrawCheckList() {
        return queryWithdrawCheckList;
    }

    public void setQueryWithdrawCheckList(ArrayList<HashMap<String, Object>> arrayList) {
        this.queryWithdrawCheckList = arrayList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
}
