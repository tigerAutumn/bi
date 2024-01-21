package com.pinting.business.service.cache.Impl;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ReqMsg_Product_BannerQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Product_BannerQuery;
import com.pinting.business.hessian.site.message.ResMsg_Product_ListQuery;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

@Service("productBannerQueryCacheImpl")
public class ProductBannerQueryCacheImpl extends DefaultCacheImpl {

    @Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();
        // 设置缓存key值
        ReqMsg_Product_BannerQuery req = (ReqMsg_Product_BannerQuery) call.getArgs()[0];

        // 添加'_'符号是防止多个条件中，出现空数据跳过的数据
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getReturnType())) {
            redisCacheKeyObj.append(req.getReturnType());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getChannel())) {
            redisCacheKeyObj.append(req.getChannel());
        }

        return redisCacheKeyObj.toString();
    }

    @Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
        ResMsg_Product_BannerQuery cacheObj = (ResMsg_Product_BannerQuery) result;
        ResMsg_Product_BannerQuery res = (ResMsg_Product_BannerQuery) call.getArgs()[1];
        res.setBannerUrl(cacheObj.getBannerUrl());
        res.setImgPath(cacheObj.getImgPath());
        return cacheObj;
    }
}
