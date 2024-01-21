package com.pinting.business.service.cache.Impl;

import com.pinting.business.service.cache.CacheHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

@Service("defaultCacheImpl")
public class DefaultCacheImpl implements CacheHelper {

    @Override
    public boolean useCache(ProceedingJoinPoint call) {
        return true;
    }

    @Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        return "";
    }

    @Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
        return result;
    }
}
