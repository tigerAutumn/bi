package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 还款通知
 * @author SHENGUOPING
 * @date  2017年8月30日 下午8:34:04
 */
public class B2GResMsg_ZsdNotice_NoticeRepay extends ResMsg {

	private static final long serialVersionUID = 7934591696037425706L;
	
	private String responseTime;

    private String errorCode;

    private String errorMsg;

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
}
