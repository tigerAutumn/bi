package com.pinting.business.service.cache.Impl;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_Pay19BankList;
import com.pinting.business.hessian.site.message.ResMsg_Bank_Pay19BankList;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

/**
 * Created by cyb on 2018/5/4.
 */
@Service("bankPay19BankListCacheImpl")
public class BankPay19BankListCacheImpl extends DefaultCacheImpl {

    @Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();
        ReqMsg_Bank_Pay19BankList req = (ReqMsg_Bank_Pay19BankList)call.getArgs()[0];

        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        return redisCacheKeyObj.toString();
    }

    @Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
        ResMsg_Bank_Pay19BankList cacheObj = (ResMsg_Bank_Pay19BankList) result;
        ResMsg_Bank_Pay19BankList res = (ResMsg_Bank_Pay19BankList) call.getArgs()[1];

        res.setBankList(cacheObj.getBankList());
        res.setResCode(cacheObj.getResCode());
        res.setResMsg(cacheObj.getResMsg());

        return cacheObj;
    }
}
