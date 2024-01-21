package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 欧洲杯活动-存入助力者 入参
 * @author HuanXiong
 * @version 2016-6-22 上午11:47:34
 */
public class ReqMsg_Ecup2016Activity_SaveSupportor extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 5815684975609959250L;
    /*用户id*/
    private Integer userId;
    /*助力者微信openId*/
    private String wxId;
    /*助力者微信昵称*/
    private String wxNick;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWxId() {
        return wxId;
    }
    
    public void setWxId(String wxId) {
        this.wxId = wxId;
    }
    
    public String getWxNick() {
        return wxNick;
    }
    
    public void setWxNick(String wxNick) {
        this.wxNick = wxNick;
    }
    
    
    
}
