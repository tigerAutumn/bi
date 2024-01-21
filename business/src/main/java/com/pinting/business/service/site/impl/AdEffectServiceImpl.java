package com.pinting.business.service.site.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsAdEffectMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_AdEffect_SelectAdEffectInfo;
import com.pinting.business.hessian.manage.message.ResMsg_AdEffect_SelectAdEffectInfo;
import com.pinting.business.hessian.site.message.ReqMsg_AdEffect_AdEffectSaveInfo;
import com.pinting.business.hessian.site.message.ResMsg_AdEffect_AdEffectSaveInfo;
import com.pinting.business.model.BsAdEffect;
import com.pinting.business.model.vo.BsAdEffectVO;
import com.pinting.business.model.vo.RedPacketStatisticsVO;
import com.pinting.business.service.site.AdEffectService;
import com.pinting.business.util.BeanUtils;

@Service
public class AdEffectServiceImpl implements AdEffectService {
    @Autowired
    private BsAdEffectMapper bsAdEffectMapper;
    
	@Override
	public void adEffectSaveInfo(ReqMsg_AdEffect_AdEffectSaveInfo req,
			ResMsg_AdEffect_AdEffectSaveInfo res) {
		BsAdEffect bsAdEffect = new BsAdEffect();
		bsAdEffect.setUrl(req.getUrl());
		bsAdEffect.setMonitorType(req.getMonitorType());
		bsAdEffect.setVisitTime(req.getVisitTime());
		bsAdEffect.setRegMobile(req.getRegMobile());
		bsAdEffect.setUtmSource(req.getUtmSource());
		bsAdEffect.setUtmMedium(req.getUtmMedium());
		bsAdEffect.setUtmTerm(req.getUtmTerm());
		bsAdEffect.setUtmContent(req.getUtmContent());
		bsAdEffect.setUtmCampaign(req.getUtmCampaign());
		bsAdEffect.setCreateTime(req.getCreateTime());
		bsAdEffectMapper.insertSelective(bsAdEffect);

	}

	@Override
	public void selectAdEffectInfo(ReqMsg_AdEffect_SelectAdEffectInfo req,
			ResMsg_AdEffect_SelectAdEffectInfo res) {
		List<BsAdEffectVO>  bsAdEffectVOs = bsAdEffectMapper.selectAdEffectInfoData(req.getVisitTimeStart(), req.getVisitTimeEnd(), 
		    req.getMonitorType(),req.getUtmSource(),req.getUtmMedium(),req.getUtmCampaign(), req.getStart(), req.getNumPerPage());
		Integer  count = bsAdEffectMapper.selectAdEffectInfoCount(req.getVisitTimeStart(), req.getVisitTimeEnd(), req.getMonitorType(),
		    req.getUtmSource(),req.getUtmMedium(),req.getUtmCampaign());
		res.setDataGrid(BeanUtils.classToArrayList(bsAdEffectVOs));
		res.setCount(count);
	}

}
