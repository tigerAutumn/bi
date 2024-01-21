package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * 管理台缓存对象
 */
public class CacheKeyVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cache; // 缓存实际key值

    private String cacheKey; // 业务key值

    private String desc; // 缓存描述

    private int expire; // 缓存失效时间

    private boolean status; // 缓存启用状态

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
