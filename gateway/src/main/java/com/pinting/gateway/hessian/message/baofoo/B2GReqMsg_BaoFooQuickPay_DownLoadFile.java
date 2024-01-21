package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GReqMsg_BaoFooQuickPay_DownLoadFile extends ReqMsg {


    /**
     * 对账日期
     * 格式yyyy-MM-dd
     */
    private String date;

    /**
     * 类型
     * 收单：fi  出款：fo
     */
    private String type;

    /**
     * 对账方编码
     */
    private String partner;
    
    /**
     * 商户号
     */
    private String merchantNo;
    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
    
    
}
