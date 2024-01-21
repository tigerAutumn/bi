package com.pinting.business.service.cache.Impl;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.site.message.ResMsg_News_CurrentNews;
import com.pinting.business.hessian.site.message.ReqMsg_News_CurrentNews;
import com.pinting.business.model.vo.BsNewsListVO;

/**
 * 查询最新的新闻|公告|动态  缓存
 * @project business
 * @author bianyatian
 * @2018-5-3 下午2:06:14
 */
@Service("newsCurrentNewsCacheImpl")
public class NewsCurrentNewsCacheImpl extends DefaultCacheImpl {

	
	@Override
    public String buildRedisCacheKey(ProceedingJoinPoint call) {
        StringBuilder redisCacheKeyObj = new StringBuilder();

        ReqMsg_News_CurrentNews req = (ReqMsg_News_CurrentNews) call.getArgs()[0];
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getReceiverType())) {
            redisCacheKeyObj.append(req.getReceiverType());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (StringUtils.isNotBlank(req.getType())) {
            redisCacheKeyObj.append(req.getType());
        }
        redisCacheKeyObj.append(ConstantsForCache.Punctuation.UNDERLINE);
        if (req.getShowPage() != null) {
            redisCacheKeyObj.append(req.getShowPage());
        }
        

        return redisCacheKeyObj.toString();
    }

    @Override
    public Object buildRedisCacheRes(Object result, ProceedingJoinPoint call) {
    	BsNewsListVO cacheObj = (BsNewsListVO) result;
    	BsNewsListVO res = (BsNewsListVO) call.getArgs()[1];
        res.setCurrentNews(cacheObj.getCurrentNews());
        res.setLatestNews(cacheObj.getLatestNews());
        return cacheObj;
    }
}
