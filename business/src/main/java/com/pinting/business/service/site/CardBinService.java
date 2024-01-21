package com.pinting.business.service.site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsCardBin;
import com.pinting.business.model.vo.BsBankCardVO;

public interface CardBinService {


	
	/**
	 * 根据银行卡号查询CardBin信息   暂时不用
	 * @param cardNo 卡号
	 * @return 如果找到信息，返回BsCardBin,否则返回null
	 */
	public BsCardBin findCardBinByCardNo(String cardNo);

	
}
