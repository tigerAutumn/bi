package com.pinting.business.service.cache.Impl;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ListIndexInfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_Product_ListIndexInfoQuery;

@Service("appProductListIndexInfoQueryImpl")
public class APPProductListIndexInfoQueryImpl extends DefaultCacheImpl {

	@Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
		StringBuilder redisCacheKeyObj = new StringBuilder();
		ReqMsg_Product_ListIndexInfoQuery req = (ReqMsg_Product_ListIndexInfoQuery)call.getArgs()[0];
		
		redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
		if (StringUtils.isNotBlank(req.getProductShowTerminal())) {
            redisCacheKeyObj.append(req.getProductShowTerminal());
        }
		return redisCacheKeyObj.toString();
	}
	
	 @Override
	 public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
		 ResMsg_Product_ListIndexInfoQuery cacheObj = (ResMsg_Product_ListIndexInfoQuery) result;
		 ResMsg_Product_ListIndexInfoQuery res = (ResMsg_Product_ListIndexInfoQuery) call.getArgs()[1];
		
		 res.setTimeLimitActivityStatus(cacheObj.getTimeLimitActivityStatus());
		 res.setTimeLimitActivity(cacheObj.getTimeLimitActivity());
		 res.setNoviceActivityStatus(cacheObj.getNoviceActivityStatus());
		 res.setNoviceActivity(cacheObj.getNoviceActivity());
		 res.setFinishReturnAllProductStatus(cacheObj.getFinishReturnAllProductStatus());
		 res.setFinishReturnAllProduct(cacheObj.getFinishReturnAllProduct());
		 res.setAverageCapitalPlusInterestProductStatus(cacheObj.getAverageCapitalPlusInterestProductStatus());
		 res.setAverageCapitalPlusInterestProduct(cacheObj.getAverageCapitalPlusInterestProduct());
		 
		 res.setResCode(cacheObj.getResCode());
		 res.setResMsg(cacheObj.getResMsg());
		 
		 return cacheObj;
	 }
}
