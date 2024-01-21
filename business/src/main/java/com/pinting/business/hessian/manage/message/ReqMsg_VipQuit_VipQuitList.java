package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**VipQuitList
 * 赞分期VIP退出申请 入参
 * Created by shh on 2017/4/19.
 */
public class ReqMsg_VipQuit_VipQuitList extends ReqMsg {

    private static final long serialVersionUID = 2242037557109754539L;

    private int pageNum;

    /** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
    private int numPerPage = 20;

    private Integer userId;

    private Integer id;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
