package com.pinting.business.accounting.finance.service;

import com.pinting.business.accounting.finance.model.EBankResultInfo;
import com.pinting.business.accounting.finance.model.QuickPayResultInfo;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_EBankBuy;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_EBankBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_Order;

/**
 * Created by babyshark on 2016/9/9.
 */
public interface UserTopUpService {

    /**
     * 充值预下单（快捷）
     * @param req 用户ID、充值金额、银行卡号、预留手机号、身份证、持卡人姓名、银行名称、银行ID、终端类型@1手机端,2PC端
     * @param res 用于结果返回
     */
    void pre(ReqMsg_RegularBuy_Order req, ResMsg_RegularBuy_Order res);
    /**
     * 充值正式下单（快捷）
     * @param req 请求信息
     * @param res 用于结果返回
     */
    void confirm(ReqMsg_RegularBuy_Order req, ResMsg_RegularBuy_Order res);

    /**
     * 充值通知（快捷）
     * @param req 请求信息
     */
    void notify(QuickPayResultInfo req);

    /**
     * 网银充值
     * @param req 请求信息
     * @param res 用于结果返回
     */
    void eBank(ReqMsg_RegularBuy_EBankBuy req, ResMsg_RegularBuy_EBankBuy res);

    /**
     * 网银充值通知
     * @param req 请求信息
     */
    void notifyEBank(EBankResultInfo req);


}
