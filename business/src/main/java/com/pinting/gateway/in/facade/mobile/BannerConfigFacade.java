package com.pinting.gateway.in.facade.mobile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import com.pinting.business.hessian.site.message.ReqMsg_BannerConfig_AppStart;
import com.pinting.business.hessian.site.message.ReqMsg_BannerConfig_getList;
import com.pinting.business.hessian.site.message.ResMsg_BannerConfig_AppStart;
import com.pinting.business.hessian.site.message.ResMsg_BannerConfig_getList;
import com.pinting.business.model.vo.BsBannerConfigVO;
import com.pinting.business.service.manage.BsBannerConfigService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.gateway.in.util.InterfaceVersion;

@Component("appBannerConfig")
public class BannerConfigFacade {
	@Autowired
	private BsBannerConfigService bsBannerConfigService;
	
	@InterfaceVersion("getList/1.0.0")
	@RedisCache(serviceName = "bannerConfigGetListCacheImpl" , redisCacheKey = ConstantsForCache.CacheKey.BANNERCONFIGFACADE_GETLIST)
	public ResMsg_BannerConfig_getList getList(ReqMsg_BannerConfig_getList req, ResMsg_BannerConfig_getList res){
		List<BsBannerConfigVO> list = new ArrayList<BsBannerConfigVO>();
		list = bsBannerConfigService.getList(req.getbChannel(), null, Constants.BANNER_STATUS_SHOW,0, 5);
		if (!CollectionUtils.isEmpty(list)) {
			res.setBannerList(BeanUtils.classToArrayList(list));
		}
		return res;
	}
	
	@InterfaceVersion("AppStart/1.0.0")
	@RedisCache(serviceName = "bannerConfigAppStartCacheImpl" , redisCacheKey = ConstantsForCache.CacheKey.BANNERCONFIGFACADE_APPSTART)
	public ResMsg_BannerConfig_AppStart appStart(ReqMsg_BannerConfig_AppStart req, ResMsg_BannerConfig_AppStart res){
		
		List<BsBannerConfigVO> list = new ArrayList<BsBannerConfigVO>();
		list = bsBannerConfigService.getList(Constants.BANNER_CHANNEL_APP_START, null, Constants.BANNER_STATUS_SHOW,0, 1);
		if (!CollectionUtils.isEmpty(list)) {
			res.setImage(list.get(0).getImgPath());
			res.setUrl(list.get(0).getUrl());
		}else {
			res.setImage(null);
			res.setUrl(null);
		}
		return res;
	}
	
	
}
