package com.pinting.business.util;

import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * reids緩存工具类
 */
public class RedisCacheUtil {
    /**
     * 读取缓存注解信息
     *
     * @param call
     * @return
     */
    public static RedisCache getCacheAnnotation(JoinPoint call) {
        try {
            String targetName = call.getTarget().getClass().getName();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();

            MethodSignature methodSignature = (MethodSignature) call.getSignature();
            Method method = methodSignature.getMethod();

            for (Method m : methods) {
                if (method.getName().equals(m.getName())) {
                    RedisCache annotation = m.getAnnotation(RedisCache.class);
                    return annotation;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 读取清除缓存注解信息
     *
     * @param call
     * @return
     */
    public static ClearRedisCache getCleareAnnotation(JoinPoint call) {
        try {
            String targetName = call.getTarget().getClass().getName();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();

            MethodSignature methodSignature = (MethodSignature) call.getSignature();
            Method method = methodSignature.getMethod();

            for (Method m : methods) {
                if (method.getName().equals(m.getName())) {
                    ClearRedisCache annotation = m.getAnnotation(ClearRedisCache.class);
                    return annotation;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 构建一次缓存table_key
     *
     * @param redisCacheKey
     * @return
     */
    public static String getRedisCacheTable(String redisCacheKey) {
        return (ConstantsForCache.REDIS_CACHE + ConstantsForCache.Punctuation.UNDERLINE + redisCacheKey).toLowerCase();
    }
}
