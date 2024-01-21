package com.pinting.business.service.cache.Impl;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ReqMsg_Product_FindSuggest;
import com.pinting.business.hessian.site.message.ResMsg_Product_FindSuggest;
import com.pinting.core.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

/**
 * Created by cyb on 2018/5/4.
 */
@Service("appHomeFindSuggestCacheImpl")
public class APPHomeFindSuggestCacheImpl extends DefaultCacheImpl {


    @Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();
        ReqMsg_Product_FindSuggest req = (ReqMsg_Product_FindSuggest)call.getArgs()[0];

        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if(StringUtil.isNotBlank(req.getProductShowTerminal())){
            redisCacheKeyObj.append(req.getProductShowTerminal());
        }
        return redisCacheKeyObj.toString();
    }

    @Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
        ResMsg_Product_FindSuggest cacheObj = (ResMsg_Product_FindSuggest) result;
        ResMsg_Product_FindSuggest res = (ResMsg_Product_FindSuggest) call.getArgs()[1];

        res.setProductLst(cacheObj.getProductLst());
        res.setResCode(cacheObj.getResCode());
        res.setResMsg(cacheObj.getResMsg());
        return cacheObj;
    }
}
