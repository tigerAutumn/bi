package com.pinting.business.accounting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.accounting.service.SysReceiveMoneyService;
import com.pinting.business.dao.BsSysReceiveMoneyMapper;
import com.pinting.business.model.BsSysReceiveMoney;
import com.pinting.business.model.BsSysReceiveMoneyExample;

@Service
public class SysReceiveMoneyServiceImpl implements SysReceiveMoneyService {
	
	@Autowired
	private BsSysReceiveMoneyMapper bsSysReceiveMoneyMapper;
	
	@Override
	@Transactional
	public void batchAddSysReceiveMoneys(
			List<BsSysReceiveMoney> bsSysReceiveMoneys, String trsType) {
		// 清空对应类型的数据
		BsSysReceiveMoneyExample example = new BsSysReceiveMoneyExample();
		example.createCriteria().andTypeEqualTo(trsType);
		bsSysReceiveMoneyMapper.deleteByExample(example);
		
		// 批量插入
		if(bsSysReceiveMoneys!=null && bsSysReceiveMoneys.size()>0){
			for (BsSysReceiveMoney bsSysReceiveMoney : bsSysReceiveMoneys) {
				bsSysReceiveMoneyMapper.insertSelective(bsSysReceiveMoney);
			}
		}

	}

}
