package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;

/**
 * Created by cyb on 2018/1/8.
 */
public class ResMsg_Match_QueryBorrowerInfo extends ResMsg {

    private static final long serialVersionUID = 743256492527206579L;

    private HashMap<String, Object> borrowerInfo;

    public HashMap<String, Object> getBorrowerInfo() {
        return borrowerInfo;
    }

    public void setBorrowerInfo(HashMap<String, Object> borrowerInfo) {
        this.borrowerInfo = borrowerInfo;
    }
}
