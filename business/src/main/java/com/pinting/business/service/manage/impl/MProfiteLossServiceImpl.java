package com.pinting.business.service.manage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsProfitLossMapper;
import com.pinting.business.model.BsProfitLoss;
import com.pinting.business.model.BsProfitLossExample;
import com.pinting.business.service.manage.MProfiteLossService;

@Service
public class MProfiteLossServiceImpl implements MProfiteLossService {
	
	@Autowired
	private BsProfitLossMapper profitLossMapper;
	
	@Override
	public boolean insertProfitLoss(BsProfitLoss bsProfitLoss) {
		return profitLossMapper.insertSelective(bsProfitLoss) >0;
	}

	@Override
	public boolean updateProfitLossById(BsProfitLoss bsProfitLoss) {
		
		return profitLossMapper.updateByPrimaryKeySelective(bsProfitLoss)>0;
	
	}

	@Override
	public BsProfitLoss findProfitByClearDateMonth() {
		return profitLossMapper.selectProfitLossByClearDateMonth();
	}

	@Override
	public double sumProfite() {
		Double sumProfite = profitLossMapper.sumProfite();
		return sumProfite==null?0:sumProfite;
	}

}
