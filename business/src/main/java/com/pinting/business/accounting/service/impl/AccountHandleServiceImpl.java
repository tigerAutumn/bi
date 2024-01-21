package com.pinting.business.accounting.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.service.AccountHandleService;
import com.pinting.business.dao.BsProductSubAccountPrefixMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsProductSubAccountPrefixExample;
import com.pinting.business.model.BsProductSubAccountPrefixKey;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.DateUtil;

@Service
public class AccountHandleServiceImpl implements AccountHandleService{
	
	@Autowired
	private BsProductSubAccountPrefixMapper bsProductSubAccountPrefixMapper;

	@Override
	/**
	 * 账户编码规则：
            code+uer_id+yyyyMMddHHmmss+4位随机数
                                    其中code可以为：
            1001：主账户
            2001：结算户
            2002：产品户-3个月
            2003：产品户-6个月
            2004：产品户-12个月
            2005：产品户-1个月
            ...
	 */
	public String generateAccount(Integer accountPrefix, Integer userId) {
		String accountCode = generateAccountCode(accountPrefix, userId);
		if(null == accountCode){
			throw new PTMessageException(PTMessageEnum.ACCOUNT_GENERATE_ERROR_TYPE);
		}
		return accountCode;
	}
	
	@Override
	public String generateProductAccount(Integer productId, Integer userId) {
		
		BsProductSubAccountPrefixExample example = new BsProductSubAccountPrefixExample();
		example.createCriteria().andProductIdEqualTo(productId);
		List<BsProductSubAccountPrefixKey> prefixs = bsProductSubAccountPrefixMapper.selectByExample(example);
		String code = generateAccount(Integer.valueOf(prefixs.get(0).getSubAccountPrefix()), userId);
		/*if(Constants.PRODUCT_CODE_3_80.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_THREE_80, userId);
		}else if(Constants.PRODUCT_CODE_6_90.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_SIX_90, userId);			
		}else if(Constants.PRODUCT_CODE_12_132.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_YEAR_132, userId);
		}else if(Constants.PRODUCT_CODE_1_70.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_MONTH_70, userId);
		}else if(Constants.PRODUCT_CODE_1_120.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_MONTH_120, userId);
		}else if(Constants.PRODUCT_CODE_1_75.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_MONTH_75, userId);
		}else if(Constants.PRODUCT_CODE_3_85.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_THREE_85, userId);
		}else if(Constants.PRODUCT_CODE_6_105.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_SIX_105, userId);
		}else if(Constants.PRODUCT_CODE_9_115.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_NINE_115, userId);
		}else if(Constants.PRODUCT_CODE_1_132.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_MONTH_132, userId);
		}
		
		else if(Constants.PRODUCT_CODE_1_90.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_MONTH_90, userId);
		}
		else if(Constants.PRODUCT_CODE_3_100.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_THREE_100, userId);
		}
		else if(Constants.PRODUCT_CODE_6_120.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_SIX_120, userId);
		}
		else if(Constants.PRODUCT_CODE_12_140.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_YEAR_140, userId);
		}
		else if(Constants.PRODUCT_CODE_1_100.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_MONTH_100, userId);
		}
		else if(Constants.PRODUCT_CODE_3_110.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_THREE_110, userId);
		}
		else if(Constants.PRODUCT_CODE_6_130.equals(productCode)){
			code = generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_SIX_130, userId);
		}*/
		return code;
	}

	/**
	 * 对应账户编号生成
	 * @param accountPrefix
	 * @param userId
	 * @return
	 */
	private String generateAccountCode(int accountPrefix, Integer userId) {
		StringBuffer accountCode = new StringBuffer(String.valueOf(accountPrefix));
		accountCode.append(userId)
			.append(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"))
			.append(Util.generateAssignLengthNo(4));
		
		return accountCode.toString();
	}

	

}
