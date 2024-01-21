package com.pinting.business.service.cache;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 缓存助手接口
 */
public interface CacheHelper {

    /**
     * 判断是否使用缓存
     * @param call
     * @return
     */
    boolean useCache(ProceedingJoinPoint call);

    /**
     * 构建对应数据的缓存key值
     * @param call
     * @return
     */
    String buildRedisCacheKey(ProceedingJoinPoint call);

    /**
     * 构建缓存响应数据
     * @param result
     * @param call
     * @return
     */
    Object buildRedisCacheRes(Object result, ProceedingJoinPoint call);
}
