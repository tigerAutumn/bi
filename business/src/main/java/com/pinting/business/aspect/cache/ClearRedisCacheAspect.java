package com.pinting.business.aspect.cache;

import com.pinting.business.model.vo.CacheKeyVO;
import com.pinting.business.util.Constants;
import com.pinting.business.util.RedisCacheUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Aspect
@Component
public class ClearRedisCacheAspect {

    private Logger log = LoggerFactory.getLogger(ClearRedisCacheAspect.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Pointcut("@annotation(com.pinting.business.aspect.cache.ClearRedisCache)")
    public void clearRedisCache() {
    }

    /**
     * 方法正常执行成功结束后,清除redis——key缓存
     *
     * @param call
     * @param retVal
     */
    @AfterReturning(value = "clearRedisCache()", returning = "retVal")
    public void doClear(JoinPoint call, Object retVal) {
        ClearRedisCache redisCache = RedisCacheUtil.getCleareAnnotation(call);
        if (redisCache == null || redisCache.clearKey() == null) {
            // 读取缓存key失败，直接跳过缓存处理
            return;
        }

        // 待清除的redis
        ConstantsForCache.CacheKey[] clearKeys = redisCache.clearKey();
        if (retVal != null && retVal instanceof ConstantsForCache.CacheKey[]) {
            clearKeys = (ConstantsForCache.CacheKey[]) retVal;
        }

        // 去除重复的清除指令
        Set<String> redisKeySet = new HashSet<>();

        for (ConstantsForCache.CacheKey clearKey : clearKeys) {
            String redisCacheTable = RedisCacheUtil.getRedisCacheTable(clearKey.getKey());

            // 查询缓存中key值的启用状态
            Object cacheData = jsClientDaoSupport.getObj(Object.class, ConstantsForCache.REDIS_CACHE, redisCacheTable);
            if (cacheData != null && cacheData instanceof CacheKeyVO) {
                CacheKeyVO cacheKeyVO = (CacheKeyVO) cacheData;
                if (!cacheKeyVO.isStatus()) {
                    log.info("读取缓存数据启用状态：已关闭");
                    continue;
                }
            }

            if (redisKeySet.add(clearKey.name())) {
                log.info("清除缓存数据开始，cacheMethod：" + redisCacheTable);
                // 清除redis信息数据
                List<String> clearlist = jsClientDaoSupport.getListOfString(redisCacheTable);
                if (CollectionUtils.isNotEmpty(clearlist)) {
                    for (String clearRedisKey : clearlist) {
                        if (redisKeySet.add(clearRedisKey)) {
                            String[] lookUpKey = clearRedisKey.split("\\|");
                            if (lookUpKey.length >= 3 && redisCacheTable.equals(lookUpKey[1])) {
                                jsClientDaoSupport.deleteObj(String.class, redisCacheTable, lookUpKey[2]);
                                log.info("清除缓存数据成功，cacheMethod：" + redisCacheTable + "，cacheKey：" + lookUpKey[2]);
                            }
                        }
                    }
                    jsClientDaoSupport.deleteListOfObj(redisCacheTable);
                }
            }

            log.info("清除缓存数据结束，cacheMethod：" + redisCacheTable);
        }
    }
}