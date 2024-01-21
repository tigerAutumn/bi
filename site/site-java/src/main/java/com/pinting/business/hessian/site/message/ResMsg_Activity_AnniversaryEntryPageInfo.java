package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 周年庆入口页面信息
 */
public class ResMsg_Activity_AnniversaryEntryPageInfo extends ResMsg {

    private static final long serialVersionUID = -5160473264962380863L;

    private String statusFirst;

    private String statusSecond;

    private String statusThird;

    public String getStatusFirst() {
        return statusFirst;
    }

    public void setStatusFirst(String statusFirst) {
        this.statusFirst = statusFirst;
    }

    public String getStatusSecond() {
        return statusSecond;
    }

    public void setStatusSecond(String statusSecond) {
        this.statusSecond = statusSecond;
    }

    public String getStatusThird() {
        return statusThird;
    }

    public void setStatusThird(String statusThird) {
        this.statusThird = statusThird;
    }

}
