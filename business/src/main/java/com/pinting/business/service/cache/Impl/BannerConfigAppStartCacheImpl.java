package com.pinting.business.service.cache.Impl;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ResMsg_BannerConfig_AppStart;
import com.pinting.business.util.Constants;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

@Service("bannerConfigAppStartCacheImpl")
public class BannerConfigAppStartCacheImpl extends DefaultCacheImpl {

    @Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();
        //ReqMsg_BannerConfig_AppStart req = (ReqMsg_BannerConfig_AppStart) call.getArgs()[0];
        // 添加'_'符号是防止多个条件中，出现空数据跳过的数据
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        redisCacheKeyObj.append(Constants.BANNER_CHANNEL_APP_START);
        return redisCacheKeyObj.toString();
    }

    @Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
        ResMsg_BannerConfig_AppStart cacheObj= (ResMsg_BannerConfig_AppStart) result;
        ResMsg_BannerConfig_AppStart res = (ResMsg_BannerConfig_AppStart) call.getArgs()[1];
        res.setUrl(cacheObj.getUrl());
        res.setImage(cacheObj.getImage());
        return cacheObj;
    }
}
