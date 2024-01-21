package com.pinting.business.service.cache.Impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ProductDetailInfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_Product_ProductDetailInfoQuery;
import com.pinting.business.util.BeanUtils;
/**
 * 
 * @author zhangpeng
 * @date 2018/05/04
 */
@Service("productProductDetailInfoQueryCacheImpl")
public class ProductProductDetailInfoQueryCacheImpl extends DefaultCacheImpl {
	@Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();

        ReqMsg_Product_ProductDetailInfoQuery req = (ReqMsg_Product_ProductDetailInfoQuery) call.getArgs()[0];

        // 添加'_'符号是防止多个条件中，出现空数据跳过的数据
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (req.getProductId() != null) {
            redisCacheKeyObj.append(req.getProductId());
        } 
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (req.getStart() != null) {
            redisCacheKeyObj.append(req.getStart());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (req.getNumPerPage() != null) {
            redisCacheKeyObj.append(req.getNumPerPage());
        }
        return redisCacheKeyObj.toString();
    }
	
	@Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
		ResMsg_Product_ProductDetailInfoQuery cacheObj = (ResMsg_Product_ProductDetailInfoQuery) result;
		ResMsg_Product_ProductDetailInfoQuery res = (ResMsg_Product_ProductDetailInfoQuery) call.getArgs()[1];
		res.setInvestRecordList(BeanUtils.classToArrayList(cacheObj.getInvestRecordList()));
        return cacheObj;
    }
}
