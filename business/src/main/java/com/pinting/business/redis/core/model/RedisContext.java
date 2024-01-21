package com.pinting.business.redis.core.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * redis公共服务对象上下文
 */
public class RedisContext<T> implements Serializable {

    private static final long serialVersionUID = -1416647430645211970L;

    private String redisKey; // rediskey值

    private Date inRedisTime; // 入redis时间

    private Date latestExecuteTime; // 最近执行时间

    private int executeCount; // 已执行次数

    private List<String> afterProcess = new ArrayList<>(); // 后处理serviceName列表

    private List<T> redisVOList = new ArrayList<>(); // redis存储对象列表

    private HashMap<String, Object> extendMap = new HashMap<>(); // 扩展属性

    public RedisContext() {
    }

    public RedisContext(String redisKey) {
        this.redisKey = redisKey;
        this.inRedisTime = new Date();
        this.executeCount = 0;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public Date getInRedisTime() {
        return inRedisTime;
    }

    public void setInRedisTime(Date inRedisTime) {
        this.inRedisTime = inRedisTime;
    }

    public Date getLatestExecuteTime() {
        return latestExecuteTime;
    }

    public void setLatestExecuteTime(Date latestExecuteTime) {
        this.latestExecuteTime = latestExecuteTime;
    }

    public int getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(int executeCount) {
        this.executeCount = executeCount;
    }

    public List<T> getRedisVOList() {
        return redisVOList;
    }

    public void setRedisVOList(List<T> redisVOList) {
        this.redisVOList = redisVOList;
    }

    public HashMap<String, Object> getExtendMap() {
        return extendMap;
    }

    public void setExtendMap(HashMap<String, Object> extendMap) {
        this.extendMap = extendMap;
    }

    public void setExtendData(String key, Object value) {
        this.extendMap.put(key, value);
    }

    public Object getExtendData(String key) {
        return this.extendMap.get(key);
    }

    public List<String> getAfterProcess() {
        return afterProcess;
    }

    public void setAfterProcess(List<String> afterProcess) {
        this.afterProcess = afterProcess;
    }
}
