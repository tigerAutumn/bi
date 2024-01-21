package com.pinting.business.service.loan.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.LnLoanWhiteMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.LnLoanWhiteExample;
import com.pinting.business.service.loan.LoanUserMobileWhiteListCheckService;
import com.pinting.core.util.StringUtil;

@Service
public class LoanUserMobileWhiteListCheckServiceImpl implements
		LoanUserMobileWhiteListCheckService {
	@Autowired
	private LnLoanWhiteMapper lnLoanWhiteMapper;

	@Override
	public boolean lnMobileWhiteListCheck(String mobile) {
		if(StringUtil.isBlank(mobile)){
			throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_NOT_ENOUGH);
		}
		LnLoanWhiteExample countExample = new LnLoanWhiteExample();
		countExample.createCriteria().andMobileIsNotNull();
		long count = lnLoanWhiteMapper.countByExample(countExample);
		if(count == 0){
			return true;
		}else{
			LnLoanWhiteExample checkExample = new LnLoanWhiteExample();
			checkExample.createCriteria().andMobileEqualTo(mobile);
			long countCheck = lnLoanWhiteMapper.countByExample(checkExample);
			if(countCheck == 0){
				return false;
			}else{
				return true;
			}
		}
		
	}

}
