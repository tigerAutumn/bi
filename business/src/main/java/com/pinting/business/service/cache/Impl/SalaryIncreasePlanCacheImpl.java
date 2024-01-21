package com.pinting.business.service.cache.Impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.pinting.business.hessian.site.message.ReqMsg_Activity_SalaryIncreasePlan;
import com.pinting.business.hessian.site.message.ResMsg_Activity_SalaryIncreasePlan;

@Service("salaryIncreasePlanCacheImpl")
public class SalaryIncreasePlanCacheImpl extends DefaultCacheImpl {

	@Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();
        // 设置缓存key值
        ReqMsg_Activity_SalaryIncreasePlan req = (ReqMsg_Activity_SalaryIncreasePlan) call.getArgs()[0];

        return redisCacheKeyObj.toString();
    }

    @Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
    	ResMsg_Activity_SalaryIncreasePlan cacheObj = (ResMsg_Activity_SalaryIncreasePlan) result;
    	ResMsg_Activity_SalaryIncreasePlan res = (ResMsg_Activity_SalaryIncreasePlan) call.getArgs()[1];
    	res.setMoreThan10000List(cacheObj.getMoreThan10000List());
    	res.setMoreThan10000Quota(cacheObj.getMoreThan10000Quota());
    	res.setMoreThan50000List(cacheObj.getMoreThan50000List());
    	res.setMoreThan50000Quota(cacheObj.getMoreThan50000Quota());
    	res.setMoreThan100000List(cacheObj.getMoreThan100000List());
    	res.setMoreThan100000Quota(cacheObj.getMoreThan100000Quota());
    	res.setMoreThan500000List(cacheObj.getMoreThan500000List());
    	res.setMoreThan500000Quota(cacheObj.getMoreThan500000Quota());
        return cacheObj;
    }
}
