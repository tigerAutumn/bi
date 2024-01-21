package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_Activity_LuckyDrawResult.java, v 0.1 2016-1-21 下午6:09:41 HuanXiong Exp $
 */
public class ResMsg_Activity_LuckyDrawResult extends ResMsg {

    /**  */
    private static final long serialVersionUID = -8662763324182961292L;

    /* 是否中奖：yes-中奖；no-未中奖 */
    private String isWin;

    /* 中奖类型：bonus-奖励金；red_packet-红包 */
    private String awardType;

    /* 中奖金额 */
    private String amount;

    /* 活动是否开始 */
    private String isStart;

    private String code;

    private String message;

    public String getIsWin() {
        return isWin;
    }

    public void setIsWin(String isWin) {
        this.isWin = isWin;
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }
}
