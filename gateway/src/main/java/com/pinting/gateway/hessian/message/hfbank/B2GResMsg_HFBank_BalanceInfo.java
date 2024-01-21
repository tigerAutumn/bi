package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      shh
 * Date:        2017/5/9
 * Description: 对账文件账户余额数据响应信息
 */
public class B2GResMsg_HFBank_BalanceInfo extends ResMsg {

    private static final long serialVersionUID = 5960674215942137099L;

    /* 返回码，10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;

    /* 目标文件地址 */
    private String destFileName;

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

    public String getDestFileName() {
        return destFileName;
    }

    public void setDestFileName(String destFileName) {
        this.destFileName = destFileName;
    }
}
