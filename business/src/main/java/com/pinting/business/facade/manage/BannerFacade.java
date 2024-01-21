package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsBannerConfigMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_BannerAddOrUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_BannerCount;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_BannerList;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_PictureAddOrUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_PictureCount;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_PictureList;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_SelectBannerConfigById;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_WebsitePictureInfo;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_BannerAddOrUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_BannerCount;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_BannerList;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_PictureAddOrUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_PictureCount;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_PictureList;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_SelectBannerConfigById;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_WebsitePictureInfo;
import com.pinting.business.model.BsBannerConfig;
import com.pinting.business.model.vo.BsBannerConfigVO;
import com.pinting.business.service.manage.BsBannerConfigService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;

/**
 * banner管理台
 * 
 * @author bianyatian
 * @2016-3-14 上午11:41:58
 */
@Component("Banner")
public class BannerFacade {
	
	@Autowired
	private BsBannerConfigService bsBannerConfigService;
	@Autowired
	private BsBannerConfigMapper bsBannerConfigMapper;
	
	
	public void bannerList(ReqMsg_Banner_BannerList req,
			ResMsg_Banner_BannerList res) {
		Integer count = bsBannerConfigService.countByBanner(req.getChannel(), req.getSubject(), req.getStatus());
		List<BsBannerConfigVO> list = new ArrayList<BsBannerConfigVO>();
		if(count == null || count != 0){
			int pageSize = 0;
			pageSize = count%req.getNumPerPage();
			if(pageSize == 0) {
				pageSize = count/req.getNumPerPage();
			} else {
				pageSize = count/req.getNumPerPage() +1;
			}
			int pageNum = (req.getPageNum() - 1) * req.getNumPerPage();
			if(req.getPageNum() > pageSize) {
				pageNum = 0;
			}
			list = bsBannerConfigService.getList(req.getChannel(), req.getSubject(), req.getStatus(),
					pageNum, req.getNumPerPage());
			res.setNumPerPage(req.getNumPerPage());
			res.setPageNum(req.getPageNum());
			if (!CollectionUtils.isEmpty(list)) {
				res.setValueList(BeanUtils.classToArrayList(list));
			}
			res.setTotalRows(count);
		}else{
			res.setTotalRows(0);
			res.setNumPerPage(req.getNumPerPage());
			res.setPageNum(req.getPageNum());
			res.setValueList(BeanUtils.classToArrayList(list));
		}
	}

	public void pictureList(ReqMsg_Banner_PictureList req, ResMsg_Banner_PictureList res) {
		List<String> showPages = null;
		if(Constants.BANNER_SHOWPAGE_LABEL_INVEST.equals(req.getShowPageLabel())) {
			showPages = Arrays.asList(new String[]{Constants.BANNER_SHOWPAGE_DEPSUBJECT, Constants.BANNER_SHOWPAGE_ZANSTAGES, Constants.BANNER_SHOWPAGE_ESTUARYPLAN});    
		} else if(Constants.BANNER_SHOWPAGE_LABEL_USERENTRY.equals(req.getShowPageLabel())){
			showPages = Arrays.asList(new String[]{Constants.BANNER_SHOWPAGE_REGISTER, Constants.BANNER_SHOWPAGE_LOGIN, Constants.BANNER_SHOWPAGE_FORGETPWD});       
		}
		Integer count = bsBannerConfigService.countPictureByBanner(showPages, req.getChannel(), req.getSubject(), req.getStatus());
		List<BsBannerConfigVO> list = new ArrayList<BsBannerConfigVO>();
		if(count == null || count != 0){
			int pageSize = 0;
			pageSize = count%req.getNumPerPage();
			if(pageSize == 0) {
				pageSize = count/req.getNumPerPage();
			} else {
				pageSize = count/req.getNumPerPage() +1;
			}
			int pageNum = (req.getPageNum() - 1) * req.getNumPerPage();
			if(req.getPageNum() > pageSize) {
				pageNum = 0;
			}
			list = bsBannerConfigService.getPictureList(showPages, req.getChannel(), req.getSubject(), req.getStatus(),
					pageNum, req.getNumPerPage());
			res.setNumPerPage(req.getNumPerPage());
			res.setPageNum(req.getPageNum());
			if (!CollectionUtils.isEmpty(list)) {
				res.setValueList(BeanUtils.classToArrayList(list));
			}
			res.setTotalRows(count);
		}else{
			res.setTotalRows(0);
			res.setNumPerPage(req.getNumPerPage());
			res.setPageNum(req.getPageNum());
			res.setValueList(BeanUtils.classToArrayList(list));
		}
	}

	public void bannerCount(ReqMsg_Banner_BannerCount req,
			ResMsg_Banner_BannerCount res) {
		Integer count = bsBannerConfigService.countByChannel(req.getChannel());
		res.setCount(count == null ? 0 : count);
	}

	public void pictureCount(ReqMsg_Banner_PictureCount req,
			ResMsg_Banner_PictureCount res) {
		Integer count = bsBannerConfigService.countByChannel(req.getChannel());
		res.setCount(count == null ? 0 : count);
	}
	
	public void selectBannerConfigById(ReqMsg_Banner_SelectBannerConfigById req,
			ResMsg_Banner_SelectBannerConfigById res) {
		BsBannerConfigVO banner = new BsBannerConfigVO();
		banner = bsBannerConfigService.selectById(req.getBannerId());
		res.setChannel(banner.getChannel());
		res.setId(banner.getId());
		res.setImgPath(banner.getImgPath());
		res.setPriority(banner.getPriority());
		res.setStatus(banner.getStatus());
		res.setSubject(banner.getSubject());
		res.setmUser(banner.getmUser());
		res.setUrl(banner.getUrl());
		res.setActivityType(banner.getActivityType());
	}
	
	public void websitePictureInfo(ReqMsg_Banner_WebsitePictureInfo req,
			ResMsg_Banner_WebsitePictureInfo res) {
		BsBannerConfigVO banner = new BsBannerConfigVO();
		banner = bsBannerConfigService.selectById(req.getBannerId());
		res.setChannel(banner.getChannel());
		res.setId(banner.getId());
		res.setImgPath(banner.getImgPath());
		res.setPriority(banner.getPriority());
		res.setStatus(banner.getStatus());
		res.setSubject(banner.getSubject());
		res.setmUser(banner.getmUser());
		res.setUrl(banner.getUrl());
		res.setShowPage(banner.getShowPage());
		res.setActivityType(banner.getActivityType());
	}

	@ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.BANNERCONFIGFACADE_GETLIST, ConstantsForCache.CacheKey.BANNERCONFIGFACADE_APPSTART})
	public void bannerAddOrUpdate(ReqMsg_Banner_BannerAddOrUpdate req,
			ResMsg_Banner_BannerAddOrUpdate res) {
		BsBannerConfig bannerConfig = new BsBannerConfig();
		try {
			Integer count = bsBannerConfigService.countByChannel(req
					.getChannel());
			count = count == null ? 0 : count;
			// 顺序选择不轮播，则不显示，其他则显示
			if(req.getPriority() != null) {
				if (req.getPriority() == -1) {
					bannerConfig.setStatus(Constants.BANNER_STATUS_HIDE);
				} else {
					bannerConfig.setStatus(Constants.BANNER_STATUS_SHOW);
				}
			}

			bannerConfig.setId(req.getId());
			bannerConfig.setChannel(req.getChannel());
			bannerConfig.setImgPath(req.getImgPath());
			bannerConfig.setPriority(req.getPriority());
			bannerConfig.setSubject(req.getSubject());
			bannerConfig.setUrl(req.getUrl());
			bannerConfig.setmUserId(req.getmUserId());
			bannerConfig.setActivityType(req.getActivityType());
			//如果新增的banner是app启动页、默认显示
			if(null != req.getChannel() && Constants.BANNER_CHANNEL_APP_START.equals(req.getChannel())) {
				bannerConfig.setStatus(Constants.BANNER_STATUS_SHOW);
			}
			if ("add".equals(req.getType())) {
				//app启动页新增
				if(null != req.getChannel() && Constants.BANNER_CHANNEL_APP_START.equals(req.getChannel())) {
					if (count >= 1) {
						res.setRetMsg("app启动页已存在！");
						res.setRetCode("999");
						return;
					}
					bsBannerConfigService.addBannerConfig(bannerConfig);
				} else {
					//新增，判断是否已经有5张显示的轮播图
					if (count >= 5 && req.getPriority() != -1) {
						res.setRetMsg("已有5张在显示的轮播图！");
						res.setRetCode("999");
						return;
					}
					if (count > 0 && req.getPriority() != -1
							&& req.getPriority() <= count) {
						//检查当前总条数a,当a>0时且序号不为-1时,比较传入的顺序b，若b>a,直接存其他不变；若b<=a,改变所以顺序号c>=b的值，都+1；
						bsBannerConfigService.updateBannersByPri(req.getPriority(),1,req.getChannel());
					}
					bsBannerConfigService.addBannerConfig(bannerConfig);
				}

			} else {
				//APP启动页删除、直接删除库中的记录
				if((null != req.getChannel() && Constants.BANNER_CHANNEL_APP_START.equals(req.getChannel())) &&
						(null != req.getDeleteFlag() && Constants.BANNER_DELETE_FLAG.equals(req.getDeleteFlag()))) {
					bsBannerConfigService.deleteBannerByChannel();
				}else {
					//修改
					if(null != req.getChannel() && Constants.BANNER_CHANNEL_APP_START.equals(req.getChannel())) {
						//APP端启动页修改
						bsBannerConfigService.updateBannerConfig(bannerConfig);
					} else {
						BsBannerConfig bannerOld = bsBannerConfigService.selectById(req.getId());
						bannerOld.setActivityType(req.getActivityType());
						if(bannerOld.getPriority() == req.getPriority()){
							//1、检查原始顺序比对当前顺序，若相同，则只修改；
							bsBannerConfigService.updateBannerConfig(bannerConfig);
						}else{
							//2、原顺序为-1不显示，首先判断是否已经有5张显示的轮播图
							if(bannerOld.getPriority() == -1 && count >= 5 && req.getPriority()!=-1){
								res.setRetMsg("已有5张在显示的轮播图！");
								res.setRetCode("999");
								return;
							}
							//3、原顺序为-1不显示，现在需要显示，判断是否需要增量改变其他的顺序,若传入的顺序b<= count,则需要修改其他的顺序
							if(bannerOld.getPriority() == -1 && req.getPriority()!=-1){
								if(req.getPriority() <= count){
									bsBannerConfigService.updateBannersByPri(req.getPriority(),1,req.getChannel());
								}
								bsBannerConfigService.updateBannerConfig(bannerConfig);
								return;
							}


							//4、原顺序为显示的轮播图，现在为不需显示，减量修改其他的顺序
							if(bannerOld.getPriority() != -1 && req.getPriority()==-1){
								bsBannerConfigService.updateBannersByPri(bannerOld.getPriority(),-1,req.getChannel());
								bsBannerConfigService.updateBannerConfig(bannerConfig);
								return;
							}

							//5、当原序号oldlPri<现传入序号newPri时，修改序号为x的值-1，x满足：oldlPri <= x <= newPri
							if(bannerOld.getPriority() < req.getPriority()){
								bsBannerConfigService.updateBannersByPris(bannerOld.getPriority(), req.getPriority(), -1, req.getChannel());
								bsBannerConfigService.updateBannerConfig(bannerConfig);
								return;
							}
							//6、当原序号oldlPri>现传入序号newPri时，修改序号为x的值+1， x满足：newPri <= x <= oldlPri
							if(bannerOld.getPriority() > req.getPriority()){
								bsBannerConfigService.updateBannersByPris(req.getPriority(), bannerOld.getPriority(), 1, req.getChannel());
								bsBannerConfigService.updateBannerConfig(bannerConfig);
								return;
							}

						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			res.setRetMsg("上传图片失败！");
			res.setRetCode("999");
			return;
		}
	}

	@ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.PRODUCTFACADE_BANNERQUERY, ConstantsForCache.CacheKey.BANNERCONFIGFACADE_APPSTART})
	public void pictureAddOrUpdate(ReqMsg_Banner_PictureAddOrUpdate req,
			ResMsg_Banner_PictureAddOrUpdate res) {
		BsBannerConfig bannerConfig = new BsBannerConfig();
		Integer count = 0;
		try {
			if(null == req.getDeleteFlag() || !Constants.BANNER_DELETE_FLAG.equals(req.getDeleteFlag())) {
				count = bsBannerConfigService.countByChannelAndShowPage(req.getChannel(), req.getShowPage());
			}
			// 顺序选择不轮播，则不显示，其他则显示
			if(req.getPriority() != null) {
				if (req.getPriority() == -1) {
					bannerConfig.setStatus(Constants.BANNER_STATUS_HIDE);
				} else {
					bannerConfig.setStatus(Constants.BANNER_STATUS_SHOW);
				}
			}
			bannerConfig.setId(req.getId());
			bannerConfig.setChannel(req.getChannel());
			bannerConfig.setImgPath(req.getImgPath());
			bannerConfig.setPriority(req.getPriority());
			bannerConfig.setSubject(req.getSubject());
			bannerConfig.setUrl(req.getUrl());
			bannerConfig.setmUserId(req.getmUserId());
			bannerConfig.setShowPage(req.getShowPage());
			bannerConfig.setActivityType(req.getActivityType());
			//如果新增的banner是app启动页、默认显示
			if(null != req.getChannel() && Constants.BANNER_CHANNEL_APP_START.equals(req.getChannel())) {
				bannerConfig.setStatus(Constants.BANNER_STATUS_SHOW);
			}
			if ("add".equals(req.getType())) {
				//新增，判断是否已经有5张显示的轮播图
				if (count >= 5 && req.getPriority() != -1) {
					res.setRetMsg("已有5张在显示的轮播图！");
					res.setRetCode("999");
					return;
				}
				// 如果对应showpage已有显示的则更新之前的为隐藏
				List<BsBannerConfig> preBanner = bsBannerConfigMapper.selectBannerConfigInfo(req.getShowPage(), req.getChannel(), 
						Constants.BANNER_STATUS_SHOW, req.getId());			
				if (!CollectionUtils.isEmpty(preBanner)) {
					bsBannerConfigMapper.updateBannerConfigStatus(req.getShowPage(), req.getChannel(), Constants.BANNER_STATUS_HIDE,
							Constants.BANNER_STATUS_SHOW, req.getId());
				}
				bsBannerConfigService.addBannerConfig(bannerConfig);
			} else {
				//删除标记，置为隐藏
				if(null != req.getDeleteFlag() && Constants.BANNER_DELETE_FLAG.equals(req.getDeleteFlag())) {
					BsBannerConfig bannerConfigTemp = new BsBannerConfig();
					bannerConfigTemp.setId(req.getId());
					bannerConfigTemp.setStatus(Constants.BANNER_STATUS_HIDE);
					bannerConfigTemp.setPriority(-1);
					bsBannerConfigMapper.updateByPrimaryKeySelective(bannerConfigTemp);
				}else {
					
					//修改
					if(null != req.getChannel() && Constants.BANNER_CHANNEL_APP_START.equals(req.getChannel())) {
						//APP端启动页修改
						bsBannerConfigService.updateBannerConfig(bannerConfig);
					} else {
						BsBannerConfig bannerOld = bsBannerConfigService.selectById(req.getId());
						bannerOld.setActivityType(req.getActivityType());
					
						//1、原顺序为-1不显示，首先判断是否已经有5张显示的轮播图
						if(bannerOld.getPriority() == -1 && count >= 5 && req.getPriority()!=-1){
							res.setRetMsg("已有5张在显示的轮播图！");
							res.setRetCode("999");
							return;
						}
						
						bsBannerConfigService.updateBannerConfig(bannerConfig);
						// 如果对应showpage已有显示的则更新之前的为隐藏
						List<BsBannerConfig> preBanner = bsBannerConfigMapper.selectBannerConfigInfo(req.getShowPage(), req.getChannel(),
								Constants.BANNER_STATUS_SHOW, req.getId());
						if (!CollectionUtils.isEmpty(preBanner)) {
							bsBannerConfigMapper.updateBannerConfigStatus(req.getShowPage(), req.getChannel(), Constants.BANNER_STATUS_HIDE,
									Constants.BANNER_STATUS_SHOW, req.getId());
						}
					}	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setRetMsg("上传图片失败！");
			res.setRetCode("999");
			return;
		}

	}
	
}
