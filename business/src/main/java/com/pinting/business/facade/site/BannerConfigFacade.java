package com.pinting.business.facade.site;

import java.util.ArrayList;
import java.util.List;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsBannerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.model.vo.BsBannerConfigVO;
import com.pinting.business.service.manage.BsBannerConfigService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;

/**
 * banner轮播
 * @author bianyatian
 * @2016-3-15 下午7:02:56
 */
@Component("BannerConfig")
public class BannerConfigFacade {
	@Autowired
	private BsBannerConfigService bsBannerConfigService;
	
	/**
	 * 根据显示端查询显示的banner信息
	 * @param req
	 * @param res
	 */
	@RedisCache(serviceName = "bannerConfigGetListCacheImpl", redisCacheKey = ConstantsForCache.CacheKey.BANNERCONFIGFACADE_GETLIST)
	public ResMsg_BannerConfig_getList getList(ReqMsg_BannerConfig_getList req, ResMsg_BannerConfig_getList res) {
		List<BsBannerConfigVO> list = new ArrayList<BsBannerConfigVO>();
		list = bsBannerConfigService.getList(req.getbChannel(), null, Constants.BANNER_STATUS_SHOW,0, 5);
		if (!CollectionUtils.isEmpty(list)) {
			res.setBannerList(BeanUtils.classToArrayList(list));
		}

		return res;
	}

	/**
	 * 根据url channel查询banner信息
	 * @param req
	 * @param res
     */
	public void getBanner(ReqMsg_BannerConfig_GetBanner req, ResMsg_BannerConfig_GetBanner res) {
		BsBannerConfig bannerConfig = bsBannerConfigService.queryBannerInfo(req.getUrl(), req.getBannerChannel());
		if(bannerConfig == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else {
			res.setActivityType(bannerConfig.getActivityType());
		}
	}
	
	/**
	 * 根据url 查询banner条数
	 * @param req
	 * @param res
     */
	public void getBannerCount(ReqMsg_BannerConfig_GetBannerCount req, ResMsg_BannerConfig_GetBannerCount res) {
		int count = bsBannerConfigService.queryBannerCount(req.getUrl());
		res.setCount(count);
	}

	public void queryBanner(ReqMsg_BannerConfig_QueryBanner req, ResMsg_BannerConfig_QueryBanner res) {
		res.setResult(BeanUtils.classToArrayList(bsBannerConfigService.queryBannerByShowPage(req.getBannerChannel(), req.getShowPage())));
	}
}
