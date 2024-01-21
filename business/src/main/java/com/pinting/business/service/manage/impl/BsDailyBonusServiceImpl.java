package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsDailyBonusMapper;
import com.pinting.business.model.BsDailyBonus;
import com.pinting.business.model.vo.BsDailyBonusVO;
import com.pinting.business.service.manage.BsDailyBonusService;

@Service
public class BsDailyBonusServiceImpl implements BsDailyBonusService{

	@Autowired
	private BsDailyBonusMapper bsDailyBonusMapper;
	
	@Override
	public List<BsDailyBonusVO> bsDailyBonusPage(BsDailyBonusVO record) {
		return bsDailyBonusMapper.bsDailyBonusPage(record);
	}

	@Override
	public int bsDailyBonusCount(BsDailyBonusVO record) {
		return bsDailyBonusMapper.bsDailyBonusCount(record);
	}

	@Override
	public Double bonusSum(BsDailyBonusVO record) {
		return bsDailyBonusMapper.bonusSum(record);
	}

	@Override
	public BsDailyBonusVO subAccountKeySumBonus(Integer subAccountId) {
		BsDailyBonusVO bsDailyBonusVO = new BsDailyBonusVO();
		bsDailyBonusVO.setSubAccountId(subAccountId);
		return bsDailyBonusMapper.subAccountKeySumBonus(bsDailyBonusVO);
	}

	@Override
	public List<BsDailyBonusVO> bsDailyBonus4ServiceDetail(BsDailyBonusVO record) {
		return bsDailyBonusMapper.bsDailyBonus4ServiceDetail(record);
	}

	@Override
	public Double bonusSum4ServiceDetail(BsDailyBonusVO record) {
		return bsDailyBonusMapper.bonusSum4ServiceDetail(record);
	}

}
