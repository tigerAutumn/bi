package com.pinting.business.model.vo;

import com.pinting.business.model.BsVipQuit;

/**
 * 赞分期VIP退出申请vo
 * Created by shh on 2017/4/24.
 */
public class VipQuitVO extends BsVipQuit {

    /* 操作人 */
    private String quitUserName;

    /* 审核人 */
    private String checkUserName;

    public String getQuitUserName() {
        return quitUserName;
    }

    public void setQuitUserName(String quitUserName) {
        this.quitUserName = quitUserName;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }
}
