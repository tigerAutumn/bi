package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 查询相关来源的最新报道 入参
 * @author HuanXiong
 * @version 2016-6-3 下午2:11:04
 */
public class ReqMsg_News_QueryBySource extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -6407228901187552091L;

    /*来源*/
    private String source;
    /*接收对象类型*/
    private String receiverType;
    
    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    
}
