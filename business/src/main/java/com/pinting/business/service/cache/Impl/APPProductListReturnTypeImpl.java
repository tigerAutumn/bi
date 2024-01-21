package com.pinting.business.service.cache.Impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ProductListReturnType;
import com.pinting.business.hessian.site.message.ResMsg_Product_ProductListReturnType;
import com.pinting.core.util.StringUtil;

@Service("appProductListReturnTypeImpl")
public class APPProductListReturnTypeImpl extends DefaultCacheImpl {

	@Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
		StringBuilder redisCacheKeyObj = new StringBuilder();
		ReqMsg_Product_ProductListReturnType req = (ReqMsg_Product_ProductListReturnType)call.getArgs()[0];
		
		redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
		if(StringUtil.isNotBlank(req.getReturnType())){
			redisCacheKeyObj.append(req.getReturnType());
		}
		return redisCacheKeyObj.toString();
	}
	
	
	@Override
	public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
		ResMsg_Product_ProductListReturnType cacheObj = (ResMsg_Product_ProductListReturnType) result;
		ResMsg_Product_ProductListReturnType res = (ResMsg_Product_ProductListReturnType) call.getArgs()[1];
		
		res.setProductList(cacheObj.getProductList());
		res.setResCode(cacheObj.getResCode());
		res.setResMsg(cacheObj.getResMsg());
		
		return cacheObj;
	}
}
