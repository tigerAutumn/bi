package com.pinting.business.coreflow.core.model;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;

/**
 * 业务流程上下文件
 */
public class FlowContext {

    private ReqMsg req; // 请求对象
    private ResMsg res; // 响应对象
    private PartnerEnum partnerEnum; // 资产方标识
    private String transCode; // 交易码（业务模块）
    private String businessType; // 业务产品类型
    private HashMap<String, Object> extendMap = new HashMap<>(); //扩展字段
    private Boolean isAsync = false; // 默认同步流程, false 同步，true 异步

    /**
     * 销毁上下文
     */
    public void destroy() {
        partnerEnum = null;
        transCode = "";
        businessType = "";
        extendMap.clear();
    }

    public ReqMsg getReq() {
        return req;
    }

    public void setReq(ReqMsg req) {
        this.req = req;
    }

    public ResMsg getRes() {
        return res;
    }

    public void setRes(ResMsg res) {
        this.res = res;
    }

    public PartnerEnum getPartnerEnum() {
        return partnerEnum;
    }

    public void setPartnerEnum(PartnerEnum partnerEnum) {
        this.partnerEnum = partnerEnum;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public HashMap<String, Object> getExtendMap() {
        return extendMap;
    }

    public void setExtendMap(HashMap<String, Object> extendMap) {
        this.extendMap = extendMap;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public void setExtendData(String key, Object value) {
        this.extendMap.put(key, value);
    }

    public Object getExtendData(String key) {
        return this.extendMap.get(key);
    }

    public Boolean getAsync() {
        return isAsync;
    }

    public void setAsync(Boolean async) {
        isAsync = async;
    }
}

