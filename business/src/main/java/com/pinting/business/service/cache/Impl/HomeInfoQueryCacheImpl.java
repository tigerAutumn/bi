package com.pinting.business.service.cache.Impl;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ReqMsg_Home_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_Home_InfoQuery;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

@Service("homeInfoQueryCacheImpl")
public class HomeInfoQueryCacheImpl extends DefaultCacheImpl {

    @Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();

        ReqMsg_Home_InfoQuery req = (ReqMsg_Home_InfoQuery) call.getArgs()[0];

        // 添加'_'符号是防止多个条件中，出现空数据跳过的数据
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getProductShowTerminal())) {
            redisCacheKeyObj.append(req.getProductShowTerminal());
        }
        return redisCacheKeyObj.toString();
    }

    @Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
        ResMsg_Home_InfoQuery cacheObj = (ResMsg_Home_InfoQuery) result;
        ResMsg_Home_InfoQuery res = (ResMsg_Home_InfoQuery) call.getArgs()[1];
        res.setTotalInvestAmount(cacheObj.getTotalInvestAmount());
        res.setRecommendProductList(cacheObj.getRecommendProductList());
        res.setNoviceRecommendedProduct(cacheObj.getNoviceRecommendedProduct());
        res.setReportList(cacheObj.getReportList());
        res.setTotalIncome(cacheObj.getTotalIncome());
        res.setTotalRegUser(cacheObj.getTotalRegUser());

        return cacheObj;
    }
}
