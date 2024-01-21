package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * Author:      YED
 * Date:        2017/4/19
 * Description: 平台提现
 */
public class B2GResMsg_HFBank_PlatWithDraw extends ResMsg {

	private static final long serialVersionUID = 2158354692327324791L;

	/* 返回码，10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;
    
    public String getRecode() {
        return recode;
    }
    
    public void setRecode(String recode) {
        this.recode = recode;
    }
    
    public String getRemsg() {
        return remsg;
    }
    
    public void setRemsg(String remsg) {
        this.remsg = remsg;
    }
}