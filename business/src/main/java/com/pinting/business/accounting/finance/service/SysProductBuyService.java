package com.pinting.business.accounting.finance.service;

import com.pinting.business.accounting.finance.model.SysActTransResultInfo;
import com.pinting.business.accounting.finance.model.SysActTransSendInfo;
import com.pinting.business.model.vo.BsSubAc4BatchBuyVO;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_SysBatchBuyProduct;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_SysBuyProductNotice;

import java.util.List;

/**
 * Created by babyshark on 2016/9/9.
 */
public interface SysProductBuyService {

    /**
     * 准备并记录某合作方昨天所有待转账的REG产品转账数据
     * @param propertySymbol 资金接收方
     * @return 返回需要系统转账购买的列表，无则返回null
     */
    List<BsSubAc4BatchBuyVO> preparePartnerDailyProduct(String propertySymbol);

    /**
     * 准备并记录单笔REG产品转账数据
     * @param subAccountId 待转账的 产品户编号
     * @return 返回转账对象，无数据或发生异常则 throw PTMessageException
     */
    SysActTransSendInfo prepareSingleProduct(Integer subAccountId);

    /**
     * 转账给合作方
     * @param req
     */
    void trans2Partner(SysActTransSendInfo req);

    /**
     * 转账结果通知处理
     *
     * @param req
     */
    void notifyTrans2PartnerResult(SysActTransResultInfo req);

    /**
     * 向合作方购买理财
     * @param req
     */
    void buyProduct(B2GReqMsg_Payment_SysBatchBuyProduct req);

    /**
     * 购买结果通知处理
     * @param req
     */
    void notifyBuyProductResult(G2BReqMsg_DafyPayment_SysBuyProductNotice req);
}
