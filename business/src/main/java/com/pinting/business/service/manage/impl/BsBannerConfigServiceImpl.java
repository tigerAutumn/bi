package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsBannerConfigMapper;
import com.pinting.business.model.BsBannerConfig;
import com.pinting.business.model.BsBannerConfigExample;
import com.pinting.business.model.vo.BsBannerConfigVO;
import com.pinting.business.service.manage.BsBannerConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.in.util.MethodRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class BsBannerConfigServiceImpl implements BsBannerConfigService {

	private static final Logger log = LoggerFactory.getLogger(BsBannerConfigServiceImpl.class);
	
	@Autowired
	private BsBannerConfigMapper bsBannerConfigMapper;

	@Override
	@MethodRole("APP")
	public List<BsBannerConfigVO> getList(String channel, String subject,
			String status, Integer pageNum, Integer numPerPage) {
		
		List<BsBannerConfigVO> list = bsBannerConfigMapper.getListByBannerConfig(
				channel, subject, status, pageNum, numPerPage);
		return list;
	}

	@Override
	public int countByChannel(String channel) {
		BsBannerConfigExample example = new BsBannerConfigExample();
		example.createCriteria().andChannelEqualTo(channel).andShowPageEqualTo(Constants.BANNER_SHOWPAGE_INDEX)
				.andStatusEqualTo(Constants.BANNER_STATUS_SHOW);
		int count = bsBannerConfigMapper.countByExample(example);
		return count;
	}
	
	@Override
	public int countByChannelAndShowPage(String channel, String showPage) {
		BsBannerConfigExample example = new BsBannerConfigExample();
		example.createCriteria().andChannelEqualTo(channel).andShowPageEqualTo(showPage)
				.andStatusEqualTo(Constants.BANNER_STATUS_SHOW);
		int count = bsBannerConfigMapper.countByExample(example);
		return count;
	}
	
	@Override
	public void updateBannersByPri(Integer priority, Integer addNum, String channel) {
		bsBannerConfigMapper.updateBannersByPri(priority, addNum, channel);

	}

	@Override
	public void updateBannerConfig(BsBannerConfig bannerConfig) {
		bannerConfig.setUpdateTime(new Date());
		bsBannerConfigMapper.updateByPrimaryKeySelective(bannerConfig);

	}

	@Override
	public void addBannerConfig(BsBannerConfig bannerConfig) {
		bannerConfig.setCreateTime(new Date());
		bannerConfig.setUpdateTime(new Date());
		if(StringUtil.isBlank(bannerConfig.getShowPage())) {
			bannerConfig.setShowPage(Constants.SHOW_PAGE_INDEX);
		}
		bsBannerConfigMapper.insertSelective(bannerConfig);
	}

	@Override
	public int countByBanner(String channel, String subject, String status) {
		int count = bsBannerConfigMapper.countByBannerConfig(channel, subject, status);
		return count;
	}

	@Override
	public BsBannerConfigVO selectById(Integer id) {
		BsBannerConfigVO vo = bsBannerConfigMapper.selectById(id);
		return vo;
	}

	@Override
	public void updateBannersByPris(Integer smallPri, Integer bigPri,
			Integer addNum, String channel) {
		bsBannerConfigMapper.updateBannersByPris(smallPri, bigPri, addNum, channel);
		
	}

	@Override
	public int deleteBannerByChannel() {
		BsBannerConfigExample bannerConfigExample = new BsBannerConfigExample();
		bannerConfigExample.createCriteria().andChannelEqualTo(Constants.BANNER_CHANNEL_APP_START);//APP端启动页
		int returnCount = bsBannerConfigMapper.deleteByExample(bannerConfigExample);
		return returnCount;
	}

	@Override
	public List<BsBannerConfigVO> getPictureList(List<String> showPage, String channel,
			String subject, String status, Integer pageNum, Integer numPerPage) {
		List<BsBannerConfigVO> list = bsBannerConfigMapper.getPictureListByBannerConfig(
				showPage, channel, subject, status, pageNum, numPerPage);
		return list;
	}

	@Override
	public int countPictureByBanner(List<String> showPage, String channel, String subject,
			String status) {
		int count = bsBannerConfigMapper.countPictureByBannerConfig(showPage, channel, subject, status);
		return count;
	}

	@Override
	public BsBannerConfig selectBannerConfigByType(String returnType, String channel) {
		String showPage = Constants.BANNER_SHOWPAGE_DEPSUBJECT;
		if(Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST.equals(returnType)) {
			showPage = Constants.BANNER_SHOWPAGE_ZANSTAGES;
		} else if(Constants.PRODUCT_RETURN_TYPE_FINISH_RETURN_ALL.equals(returnType)) {
			showPage = Constants.BANNER_SHOWPAGE_ESTUARYPLAN;
		}
		BsBannerConfigExample bannerConfigExample = new BsBannerConfigExample();
		bannerConfigExample.createCriteria().andShowPageEqualTo(showPage).
			andChannelEqualTo(convertChannelInfo(channel)).andStatusEqualTo(Constants.BANNER_STATUS_SHOW);
		List<BsBannerConfig> list = bsBannerConfigMapper.selectByExample(bannerConfigExample);
		if(!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		} else {
			// 只配置了1个或2个banner
			BsBannerConfigExample randBannerConfigExample = new BsBannerConfigExample();
			randBannerConfigExample.createCriteria().andStatusEqualTo(Constants.BANNER_STATUS_SHOW).andChannelEqualTo(convertChannelInfo(channel)).andShowPageIn(Arrays.asList(
					new String[]{Constants.BANNER_SHOWPAGE_DEPSUBJECT, Constants.BANNER_SHOWPAGE_ZANSTAGES, Constants.BANNER_SHOWPAGE_ESTUARYPLAN}));
			List<BsBannerConfig> randList = bsBannerConfigMapper.selectByExample(randBannerConfigExample);
			if (!CollectionUtils.isEmpty(randList)) {
				return randList.get(0);
			}
		}
		return null;
	}
	
	/**
	 * 转化渠道信息
	 * @return
	 */
	private String convertChannelInfo(String channel) {
		String convertChannel = "";
		if ("gen2.0".equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_GEN;
		} else if ("micro2.0".equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_MICRO;
		} else if (Constants.PRODUCT_SHOW_TERMINAL_PC_178.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_GEN178;
		} else if (Constants.PRODUCT_SHOW_TERMINAL_PC_KQ.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_GENKQ;
		} else if (Constants.PRODUCT_SHOW_TERMINAL_PC_HN.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_GENHN;
		} else if (Constants.PRODUCT_SHOW_TERMINAL_PC_RUIAN.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_GENRUIAN;
		} else if (Constants.PRODUCT_SHOW_TERMINAL_H5_178.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_MICRO178;
		} else if (Constants.PRODUCT_SHOW_TERMINAL_H5_KQ.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_MICROKQ;
		} else if (Constants.PRODUCT_SHOW_TERMINAL_H5_HN.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_MICROHN;
		} else if (Constants.PRODUCT_SHOW_TERMINAL_H5_RUIAN.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_MICRORUIAN;
		}else if (Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_JT.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_MICROJT;
		}else if (Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_XW.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_MICROXW;
		}else if (Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_TV.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_MICROTV;
		}else if (Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_ST.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_MICROSTZY;
		}else if (Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_PC_QHD_ST.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_GENSTZY;
		}else if (Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_SJC.equals(channel)) {
			convertChannel = Constants.BANNER_CHANNEL_MICROSJC;
		}
		return convertChannel;
	}
	
	@Override
	public BsBannerConfig queryBannerInfo(String url, String channel) {
		BsBannerConfig bannerConfig = bsBannerConfigMapper.selectBannerInfo(url, channel);
		return bannerConfig;
	}
	
	@Override
	public int queryBannerCount(String url) {
		int count = bsBannerConfigMapper.selectBannerCount(url);
		return count;
	}

	@Override
	public List<BsBannerConfig> queryBannerByShowPage(String channel, String showPage) {
		BsBannerConfigExample bannerConfigExample = new BsBannerConfigExample();
		bannerConfigExample.createCriteria().andShowPageEqualTo(showPage).andChannelEqualTo(channel).andStatusEqualTo(Constants.BANNER_STATUS_SHOW);
		bannerConfigExample.setOrderByClause("priority asc");
		List<BsBannerConfig> list = bsBannerConfigMapper.selectByExample(bannerConfigExample);
		return CollectionUtils.isEmpty(list) ? new ArrayList<BsBannerConfig>() : list;
	}

	@Override
	public void updateBannersByShowPage(Integer priority, Integer addNum,
			String channel, String showPage) {
		bsBannerConfigMapper.updateBannersByShowPage(priority, addNum, channel, showPage);
	}

	@Override
	public void updateBannersByShowPages(Integer smallPri, Integer bigPri,
			Integer addNum, String channel, String showPage) {
		bsBannerConfigMapper.updateBannersByShowPages(smallPri, bigPri, addNum, channel, showPage);
	}

}
