package com.pinting.business.aspect.cache;

import java.lang.annotation.*;

/**
 * 查询redis缓存
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisCache {

	/**
	 * 缓存数据查询加载实现类bean
	 * @return
	 */
	String serviceName() default "defaultCacheImpl";

	/**
	 * 缓存table_key值
	 * @return
	 */
	ConstantsForCache.CacheKey redisCacheKey();

	/**
	 * 缓存数据类型：Object, List
	 * @return
	 */
	ConstantsForCache.CacheType cacheType() default ConstantsForCache.CacheType.OBJECT;

	/**
	 * 缓存失效时间
	 * @return
	 */
	int expire() default ConstantsForCache.DEFAULT_EXPIRE;
}