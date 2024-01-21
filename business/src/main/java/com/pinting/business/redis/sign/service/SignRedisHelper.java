package com.pinting.business.redis.sign.service;

import com.pinting.business.redis.core.model.RedisContext;

/**
 * Created by zousheng on 2018/6/19.
 * 签章redis服务公共类
 */
public interface SignRedisHelper<T> {

    /**
     * 业务服务执行方法
     *
     * @param redisContext
     * @return
     */
    void execute(RedisContext<T> redisContext);
}
