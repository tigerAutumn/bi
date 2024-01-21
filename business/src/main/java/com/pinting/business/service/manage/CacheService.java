package com.pinting.business.service.manage;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.model.vo.CacheKeyVO;

import java.util.List;

/**
 * 管理台缓存处理
 */
public interface CacheService {

	/**
	 * 查询缓存key配置信息
	 * @param key
	 * @return
	 */
	CacheKeyVO findCacheKey(String key);

	/**
	 * 查询缓存key配置管理列表
	 * @return
	 */
	List<CacheKeyVO> findCacheKeyList();

	/**
	 * 刷新缓存key
	 * @param cacheKey
	 * @return
	 */
	ConstantsForCache.CacheKey[] refreshCacheKey(String... cacheKey);

	/**
	 * 启用缓存key
	 * @param cacheKey
	 * @return
	 */
	boolean openCachekey(String... cacheKey);

	/**
	 * 关闭缓存key
	 * @param cacheKey
	 * @return
	 */
	boolean closeCachekey(String... cacheKey);

	/**
	 * 修改缓存配置失效时间
	 * @param cacheKey
	 * @param expire
	 * @return
	 */
	boolean refreshCachekeyExpire(String cacheKey, int expire);
}
