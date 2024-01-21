package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      shh
 * Date:        2017/6/12
 * Description: 财务功能-垫付金人工调账 入参
 */
public class ReqMsg_MAccount_QueryAdvanceTransferOrder extends ReqMsg {

    private static final long serialVersionUID = -4980118106007359331L;

    private int pageNum;

    /** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
    private int numPerPage = 20;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }
}
