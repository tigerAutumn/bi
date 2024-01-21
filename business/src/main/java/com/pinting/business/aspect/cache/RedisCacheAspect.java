package com.pinting.business.aspect.cache;

import com.pinting.business.model.vo.CacheKeyVO;
import com.pinting.business.service.cache.CacheHelper;
import com.pinting.business.util.Constants;
import com.pinting.business.util.RedisCacheUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class RedisCacheAspect implements ApplicationContextAware {

    private Logger log = LoggerFactory.getLogger(RedisCacheAspect.class);
    protected static ApplicationContext applicationContext;
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Pointcut("@annotation(com.pinting.business.aspect.cache.RedisCache)")
    public void findRedisCache() {
    }

    @Around(value = "findRedisCache()")
    public Object doAround(ProceedingJoinPoint call) throws Throwable {
        RedisCache redisCache = RedisCacheUtil.getCacheAnnotation(call);
        if (redisCache == null || redisCache.redisCacheKey() == null) {
            // 读取缓存key失败，直接跳过缓存处理
            return call.proceed();
        }

        String redisCacheTable = RedisCacheUtil.getRedisCacheTable(redisCache.redisCacheKey().getKey());
        log.info("读取缓存数据，cacheMethod：" + redisCacheTable);

        // 查询缓存中key值的启用状态
        Object cacheData = jsClientDaoSupport.getObj(Object.class, ConstantsForCache.REDIS_CACHE, redisCacheTable);
        CacheKeyVO cacheKeyVO = null;
        if (cacheData != null && cacheData instanceof CacheKeyVO) {
            cacheKeyVO = (CacheKeyVO) cacheData;
            if (!cacheKeyVO.isStatus()) {
                log.info("读取缓存数据启用状态：已关闭");
                return call.proceed();
            }
        }

        Object service = applicationContext.getBean(redisCache.serviceName());
        if (service != null && service instanceof CacheHelper) {
            CacheHelper cacheHelper = (CacheHelper) service;
            if (!cacheHelper.useCache(call)) {
                log.info("是否读取缓存数据：false");
                return call.proceed();
            }
        }

        StringBuilder redisCacheKeyObj = new StringBuilder()
                .append(redisCache.redisCacheKey())
                .append(ConstantsForCache.Punctuation.UNDERLINE)
                .append(redisCache.cacheType().name());

        if (service != null && service instanceof CacheHelper) {
            CacheHelper cacheHelper = (CacheHelper) service;
            String redisCacheKey = cacheHelper.buildRedisCacheKey(call);
            if (StringUtils.isNotBlank(redisCacheKey)) {
                redisCacheKeyObj.append(redisCacheKey);
            }
        }

        String redisCacheKey = redisCacheKeyObj.toString().toLowerCase();
        log.info("读取缓存数据开始，cachekey：" + redisCacheKey);
        Object cacheObj = null;
        try {
            cacheObj = jsClientDaoSupport.getObj(Object.class, redisCacheTable, redisCacheKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cacheObj != null) {
            log.info("读取缓存数据成功，cachekey：" + redisCacheKey);
            if (service != null && service instanceof CacheHelper) {
                CacheHelper cacheHelper = (CacheHelper) service;
                Object obj = cacheHelper.buildRedisCacheRes(cacheObj, call);
                if (obj != null) {
                    cacheObj = obj;
                }
            }
            // 中断正常方法，直接返回
            return cacheObj;
        } else {
            log.info("读取缓存数据——没有缓存数据，cachekey：" + redisCacheKey);
            Object result = call.proceed();
            if (result != null) {
                int expire = cacheKeyVO != null ? cacheKeyVO.getExpire() : redisCache.expire();
                log.info("添加缓存redisCacheKey数据，cacheMethod：" + redisCacheTable + "，cachekey：" + redisCacheKey + "，expire：" + expire);
                jsClientDaoSupport.addOrUpdateObj(result, redisCacheTable, redisCacheKey, expire);
                jsClientDaoSupport.addOrUpdateObj(redisCacheKey, redisCacheTable);
            }
            return result;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}