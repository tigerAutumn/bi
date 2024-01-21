package com.pinting.business.service.site;

import com.pinting.business.model.DafyWapBankExt;

public interface DafyWapBankService {
	
	/**
	 * 根据系统银行卡Id,查询达飞银行卡代码
	 * @param bankId
	 * @return
	 */
	public DafyWapBankExt findDafyBankCodeByBankId(String bankId);
}
