package com.pinting.business.accounting.finance.service;

import com.pinting.business.accounting.finance.model.QuickPayResultInfo;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_EBankBuy;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_EBankBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_Order;

import java.util.Map;

/**
 * Author:      cyb
 * Date:        2017/4/4
 * Description: 存管充值服务
 */
public interface DepUserTopUpService {

    /**
     * 恒丰-充值预下单（快捷）
     * @param req 用户ID、充值金额、银行卡号、预留手机号、身份证、持卡人姓名、银行名称、银行ID、终端类型@1手机端,2PC端、账户类型
     * @param res 用于结果返回
     */
    void hfPre(ReqMsg_RegularBuy_Order req, ResMsg_RegularBuy_Order res);

    /**
     * 恒丰-充值正式下单（快捷）
     * @param req 请求信息
     * @param res 用于结果返回
     */
    void hfConfirm(ReqMsg_RegularBuy_Order req, ResMsg_RegularBuy_Order res);

    /**
     * 恒丰-充值通知
     * @param req 请求信息
     */
    void hfNotify(QuickPayResultInfo req);

    /**
     * 恒丰-网银充值
     * @param req 请求信息
     * @param res 用于结果返回
     */
    void hfEBank(ReqMsg_RegularBuy_EBankBuy req, ResMsg_RegularBuy_EBankBuy res);

}
