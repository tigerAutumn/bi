package com.pinting.business.accounting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.service.CheckJnlService;
import com.pinting.business.dao.BsCheckErrorJnlMapper;
import com.pinting.business.dao.BsCheckJnlMapper;
import com.pinting.business.model.BsCheckErrorJnl;
import com.pinting.business.model.BsCheckJnl;

@Service
public class CheckJnlServiceImpl implements CheckJnlService {

	@Autowired
	private BsCheckJnlMapper bsCheckJnlMapper;
	@Autowired
	private BsCheckErrorJnlMapper bsCheckErrorJnlMapper;
	
	@Override
	public boolean addCheckJnl(BsCheckJnl bsCheckJnl) {
		return bsCheckJnlMapper.insertSelective(bsCheckJnl) == 1;
	}
	@Override
	public boolean addCheckErrorJnl(BsCheckErrorJnl bsCheckErrorJnl) {
		return bsCheckErrorJnlMapper.insertSelective(bsCheckErrorJnl) == 1;
	}

}
