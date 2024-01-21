package com.pinting.business.service.manage.impl;

import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.vo.CacheKeyVO;
import com.pinting.business.service.manage.CacheService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.RedisCacheUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CacheServiceImpl implements CacheService {

    private Logger log = LoggerFactory.getLogger(CacheServiceImpl.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Override
    public CacheKeyVO findCacheKey(String key) {

        ConstantsForCache.CacheKey cacheKey = ConstantsForCache.CacheKey.getEnum(key);
        if (cacheKey != null) {
            String redisCacheKey = RedisCacheUtil.getRedisCacheTable(cacheKey.getKey());
            // 查询缓存中key值的启用状态
            Object cacheData = jsClientDaoSupport.getObj(Object.class, ConstantsForCache.REDIS_CACHE, redisCacheKey);
            if (cacheData != null && cacheData instanceof CacheKeyVO) {
                return  (CacheKeyVO) cacheData;
            }
        }

        return null;
    }

    @Override
    public List<CacheKeyVO> findCacheKeyList() {

        List<CacheKeyVO> cacheKeyVOS = new ArrayList<>();
        CacheKeyVO cacheKeyVO = null;
        for (ConstantsForCache.CacheKey cacheKey : ConstantsForCache.CacheKey.values()) {
            String redisCacheKey = RedisCacheUtil.getRedisCacheTable(cacheKey.getKey());
            // 查询缓存中key值的启用状态
            Object cacheData = jsClientDaoSupport.getObj(Object.class, ConstantsForCache.REDIS_CACHE, redisCacheKey);
            if (cacheData != null && cacheData instanceof CacheKeyVO) {
                cacheKeyVO = (CacheKeyVO) cacheData;
            } else {
                cacheKeyVO = new CacheKeyVO();
                cacheKeyVO.setCache(redisCacheKey);
                cacheKeyVO.setCacheKey(cacheKey.getKey());
                cacheKeyVO.setDesc(cacheKey.getDesc());
                cacheKeyVO.setStatus(true);
                cacheKeyVO.setExpire(ConstantsForCache.DEFAULT_EXPIRE);
                jsClientDaoSupport.addOrUpdateObj(cacheKeyVO, ConstantsForCache.REDIS_CACHE, redisCacheKey, -1);
            }
            cacheKeyVOS.add(cacheKeyVO);
        }

        return cacheKeyVOS;
    }

    @ClearRedisCache(clearKey = {})
    @Override
    public ConstantsForCache.CacheKey[] refreshCacheKey(String... cacheKeys) {
        ConstantsForCache.CacheKey[] cacheKeyList = new ConstantsForCache.CacheKey[cacheKeys.length];
        int clearNo = 0;
        for (int i = 0; i < cacheKeys.length; i++) {
            ConstantsForCache.CacheKey clearKey = ConstantsForCache.CacheKey.getEnum(cacheKeys[i]);
            if (clearKey != null) {
                cacheKeyList[clearNo] = clearKey;
                clearNo++;
            }
        }
        return cacheKeyList;
    }

    @Override
    public boolean openCachekey(String... cacheKeys) {
        for (String key : cacheKeys) {
            ConstantsForCache.CacheKey cacheKey = ConstantsForCache.CacheKey.getEnum(key);
            if (cacheKey != null) {
                String redisCacheKey = RedisCacheUtil.getRedisCacheTable(cacheKey.getKey());
                log.info("启用缓存key:" + redisCacheKey);

                // 查询缓存中key值的启用状态
                CacheKeyVO cacheData = jsClientDaoSupport.getObj(CacheKeyVO.class, ConstantsForCache.REDIS_CACHE, redisCacheKey);
                if (cacheData != null) {
                    cacheData.setStatus(true);
                    jsClientDaoSupport.addOrUpdateObj(cacheData, ConstantsForCache.REDIS_CACHE, redisCacheKey, -1);
                } else {
                    log.info("缓存key数据未找到:" + redisCacheKey);
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "缓存配置数据未找到,请刷新页面");
                }
            }
        }
        return true;
    }

    @Override
    public boolean closeCachekey(String... cacheKeys) {
        for (String key : cacheKeys) {
            ConstantsForCache.CacheKey cacheKey = ConstantsForCache.CacheKey.getEnum(key);
            if (cacheKey != null) {
                String redisCacheKey = RedisCacheUtil.getRedisCacheTable(cacheKey.getKey());
                log.info("关闭缓存key:" + redisCacheKey);

                // 查询缓存中key值的启用状态
                CacheKeyVO cacheData = jsClientDaoSupport.getObj(CacheKeyVO.class, ConstantsForCache.REDIS_CACHE, redisCacheKey);
                if (cacheData != null) {
                    cacheData.setStatus(false);
                    jsClientDaoSupport.addOrUpdateObj(cacheData, ConstantsForCache.REDIS_CACHE, redisCacheKey, -1);
                } else {
                    log.info("缓存key数据未找到:" + redisCacheKey);
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "缓存配置数据未找到,请刷新页面");
                }
            }
        }
        return true;
    }

    @Override
    public boolean refreshCachekeyExpire(String cacheKey, int expire) {

        ConstantsForCache.CacheKey cacheKeyEnum = ConstantsForCache.CacheKey.getEnum(cacheKey);
        if (cacheKeyEnum != null) {
            String redisCacheKey = RedisCacheUtil.getRedisCacheTable(cacheKeyEnum.getKey());
            log.info("设置缓存key失效时间:" + redisCacheKey + "，expire" + expire);

            // 查询缓存中key值的启用状态
            CacheKeyVO cacheData = jsClientDaoSupport.getObj(CacheKeyVO.class, ConstantsForCache.REDIS_CACHE, redisCacheKey);
            if (cacheData != null) {
                cacheData.setExpire(expire);
                jsClientDaoSupport.addOrUpdateObj(cacheData, ConstantsForCache.REDIS_CACHE, redisCacheKey, -1);
            } else {
                log.info("缓存key数据未找到:" + redisCacheKey);
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "缓存配置数据未找到,请刷新页面");
            }
        }

        return true;
    }
}
