package com.pinting.business.service.cache.Impl;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Product_ListQuery;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

@Service("productListQueryCacheImpl")
public class ProductListQueryCacheImpl extends DefaultCacheImpl {

    @Override
    public boolean useCache(ProceedingJoinPoint call) {

        // 带分页条件，大于3页,不查询缓存
        ReqMsg_Product_ListQuery req = (ReqMsg_Product_ListQuery) call.getArgs()[0];
        if (req.getPageNum() != null && req.getPageNum().compareTo(ConstantsForCache.CACHE_MAX_PAGE) > 0) {
            return false;
        }

        return true;
    }

    @Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();
        // 设置缓存key值，带分页条件
        ReqMsg_Product_ListQuery req = (ReqMsg_Product_ListQuery) call.getArgs()[0];

        // 添加'_'符号是防止多个条件中，出现空数据跳过的数据
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (req.getPageNum() != null) {
            redisCacheKeyObj.append(req.getPageNum());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (req.getNumPerPage() != null) {
            redisCacheKeyObj.append(req.getNumPerPage());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getUserType())) {
            redisCacheKeyObj.append(req.getUserType());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getProductShowTerminal())) {
            redisCacheKeyObj.append(req.getProductShowTerminal());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (req.getStatus() != null) {
            redisCacheKeyObj.append(req.getStatus());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getTerm())) {
            redisCacheKeyObj.append(req.getTerm());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getIsSuggest())) {
            redisCacheKeyObj.append(req.getIsSuggest());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getActivityType())) {
            redisCacheKeyObj.append(req.getActivityType());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getReturnType())) {
            redisCacheKeyObj.append(req.getReturnType());
        }

        return redisCacheKeyObj.toString();
    }

    @Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
        ResMsg_Product_ListQuery cacheObj = (ResMsg_Product_ListQuery) result;
        ResMsg_Product_ListQuery res = (ResMsg_Product_ListQuery) call.getArgs()[1];
        res.setProductLst(cacheObj.getProductLst());
        res.setCount(cacheObj.getCount());
        return cacheObj;
    }
}
