package com.pinting.business.service.cache.Impl;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ReqMsg_BannerConfig_getList;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ListQuery;
import com.pinting.business.hessian.site.message.ResMsg_BannerConfig_getList;
import com.pinting.business.hessian.site.message.ResMsg_Product_ListQuery;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

@Service("bannerConfigGetListCacheImpl")
public class BannerConfigGetListCacheImpl extends DefaultCacheImpl {

    @Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();

        ReqMsg_BannerConfig_getList req = (ReqMsg_BannerConfig_getList) call.getArgs()[0];

        // 添加'_'符号是防止多个条件中，出现空数据跳过的数据
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getbChannel())) {
            redisCacheKeyObj.append(req.getbChannel());
        }
        return redisCacheKeyObj.toString();
    }

    @Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
        ResMsg_BannerConfig_getList cacheObj = (ResMsg_BannerConfig_getList) result;
        ResMsg_BannerConfig_getList res = (ResMsg_BannerConfig_getList) call.getArgs()[1];
        res.setBannerList(cacheObj.getBannerList());
        return cacheObj;
    }
}
