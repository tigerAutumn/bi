package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsTermProductCode;
import com.pinting.business.model.BsTermProductCodeExample;

public interface MTermProductCodeService {
	/**
	 * 查询期限产品编码列表
	 * @param example
	 * @return List<BsTermProductCode>
	 * @since JDK 1.7
	 */
	public List<BsTermProductCode> findBsTermProductCodes(BsTermProductCodeExample example);
}
