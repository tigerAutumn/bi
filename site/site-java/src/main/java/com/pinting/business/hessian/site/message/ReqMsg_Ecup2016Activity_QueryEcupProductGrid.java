package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 欧洲杯活动-查询欧洲杯活动标 入参
 * @author HuanXiong
 * @version 2016-6-22 上午11:47:05
 */
public class ReqMsg_Ecup2016Activity_QueryEcupProductGrid extends ReqMsg {
    
    /**  */
    private static final long serialVersionUID = 4465493289155780295L;
    /*展示端口*/
    private String showTerminal;
    /*类型（NORMAL：欧洲杯产品；NEW_USER：新用户产品）*/
    private String type;

    public String getShowTerminal() {
        return showTerminal;
    }

    public void setShowTerminal(String showTerminal) {
        this.showTerminal = showTerminal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
}
