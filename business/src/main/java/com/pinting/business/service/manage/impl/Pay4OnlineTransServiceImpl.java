package com.pinting.business.service.manage.impl;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.dto.Pay4OnlineTransDTO;
import com.pinting.business.service.manage.Pay4OnlineTransService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.TransferEnv;
import com.pinting.business.util.TransferEnvUtil;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.out.service.BaoFooTransportService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by 剑钊 on 2016/11/16.
 */
@Service
public class Pay4OnlineTransServiceImpl implements Pay4OnlineTransService {

    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private SpecialJnlService specialJnlService;

    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Override
    public void pay4OnlineTrans(Pay4OnlineTransDTO pay4OnlineTransDTO) {

        try {

            jsClientDaoSupport.lock(RedisLockEnum.LOCK_TRANS4PARTNER_TOKEN.getKey());

            String token=jsClientDaoSupport.getString("marketingToken");

            if(StringUtils.isNotBlank(token) && pay4OnlineTransDTO.getToken().equals(token)) {

                B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans req = new B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans();
                req.setTrans_no(Util.generateOrderNo4BaoFoo(8));
                req.setTrans_money(String.valueOf(MoneyUtil.defaultRound(pay4OnlineTransDTO.getAmount())));
                req.setTransSummary("管理台宝付账户间划账");
                req.setPropertySymbol(pay4OnlineTransDTO.getPartner());

                B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans res = null;
                try {
                    res = baoFooTransportService.pay4OnlineTrans(req);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
                }

                //转账成功
                if (StringUtils.isNotBlank(res.getState())) {
                    if (res.getState().equals("1")) {
                        specialJnlService.warn4FailNoSMS(MoneyUtil.defaultRound(pay4OnlineTransDTO.getAmount()).doubleValue(), "管理台宝付账户间金额划拨成功", req.getTrans_no(), "管理台宝付账户间金额划拨");
                    } else if (res.getState().equals("-1")) {
                        specialJnlService.warn4FailNoSMS(MoneyUtil.defaultRound(pay4OnlineTransDTO.getAmount()).doubleValue(), "管理台宝付账户间金额划拨失败：失败编码：" + res.getResCode() + ",失败原因：" + res.getResMsg(), req.getTrans_no(), "管理台宝付账户间金额划拨");
                    } else if (res.getState().equals("0")) {
                        specialJnlService.warn4FailNoSMS(MoneyUtil.defaultRound(pay4OnlineTransDTO.getAmount()).doubleValue(), "管理台宝付账户间金额划拨正在处理中", req.getTrans_no(), "管理台宝付账户间金额划拨");
                    }
                } else {
                    specialJnlService.warn4FailNoSMS(MoneyUtil.defaultRound(pay4OnlineTransDTO.getAmount()).doubleValue(), "管理台宝付账户间金额划拨失败：失败编码：" + res.getResCode() + ",失败原因：" + res.getResMsg(), req.getTrans_no(), "管理台宝付账户间金额划拨");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            specialJnlService.warn4FailNoSMS(MoneyUtil.defaultRound(pay4OnlineTransDTO.getAmount()).doubleValue(), "管理台宝付账户间金额划拨失败：失败原因：" + e.getMessage(), null, "管理台宝付账户间金额划拨");

            throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_TRANS4PARTNER_TOKEN.getKey());
        }
    }

    @Override
    public String createMarketingToken() {

        try {

            String token=UUID.randomUUID().toString();
            jsClientDaoSupport.setString(token,"marketingToken");
            return token;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
