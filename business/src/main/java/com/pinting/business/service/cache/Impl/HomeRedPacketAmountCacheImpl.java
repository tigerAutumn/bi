package com.pinting.business.service.cache.Impl;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ReqMsg_Home_RedPacketAmount;
import com.pinting.business.hessian.site.message.ResMsg_Home_RedPacketAmount;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

/**
 * Created by cyb on 2018/5/3.
 */
@Service("homeRedPacketAmountCacheImpl")
public class HomeRedPacketAmountCacheImpl extends DefaultCacheImpl {

    @Override
    public boolean useCache(ProceedingJoinPoint call) {
        return super.useCache(call);
    }

    @Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();
        ReqMsg_Home_RedPacketAmount req = (ReqMsg_Home_RedPacketAmount) call.getArgs()[0];
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getProductShowTerminal())) {
            redisCacheKeyObj.append(req.getProductShowTerminal());
        }
        return redisCacheKeyObj.toString();
    }

    @Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
        ResMsg_Home_RedPacketAmount cacheObj = (ResMsg_Home_RedPacketAmount) result;
        ResMsg_Home_RedPacketAmount res = (ResMsg_Home_RedPacketAmount) call.getArgs()[1];
        res.setTotalRedPacketSubtract(cacheObj.getTotalRedPacketSubtract());
        return cacheObj;
    }
}
