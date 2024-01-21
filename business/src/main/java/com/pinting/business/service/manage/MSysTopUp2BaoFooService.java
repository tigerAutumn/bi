package com.pinting.business.service.manage;

import com.pinting.business.model.vo.BsBaoFooPreBindCardVO;
import com.pinting.business.model.vo.PreTopUpResVO;

/**
 * Created by 剑钊
 * @title 系统充值到宝付
 * @2016/10/24 17:44.
 */
public interface MSysTopUp2BaoFooService {

    /**
     * 预充值
     * @param bindId 绑卡编号
     * @param amount 充值金额 单位 ：元
     */
    PreTopUpResVO preTopUp(String bindId, Double amount);


    /**
     * 确认充值
     * @param transId 订单号
     * @param bindId 绑卡编号
     * @param amount 充值金额
     * @param smsCode 短信验证码
     */
    PreTopUpResVO topUp(String transId,String bindId,Double amount,String smsCode);
    

    /**
     * 预绑卡
     */
    String preBindCard(String bankCardNo,String mobile);
    
    /**
     * 确认绑卡
     */
    void bindCardConfirm(String transId,String smsCode,String bankCardNo,String mobile,String idCard,String userName,String bankCode);
}
