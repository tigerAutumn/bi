package com.pinting.business.service.cache.Impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.model.vo.ProductDetailVO;

@Service("productSelectProductDetailListCacheImpl")
public class ProductSelectProductDetailListCacheImpl extends DefaultCacheImpl {

	@Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();

        Integer productId = (Integer) call.getArgs()[0];
        Integer start = (Integer) call.getArgs()[1];
        Integer numPerPage = (Integer) call.getArgs()[2];

        // 添加'_'符号是防止多个条件中，出现空数据跳过的数据
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (productId != null) {
            redisCacheKeyObj.append(productId);
        } 
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (start != null) {
            redisCacheKeyObj.append(start);
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (numPerPage != null) {
            redisCacheKeyObj.append(numPerPage);
        }
        return redisCacheKeyObj.toString();
    }
	
	@Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
		ProductDetailVO cacheObj = (ProductDetailVO) result;
        return cacheObj;
    }
}
