package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author	:	yed 
 * Date		:	2017/6/12 
 * Description	:	绑卡响应信息 
 */
public class B2GResMsg_HFBank_UserAddCard extends ResMsg {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8715926681729704200L;

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
