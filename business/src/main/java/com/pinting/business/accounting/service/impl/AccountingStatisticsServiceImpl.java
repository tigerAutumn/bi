package com.pinting.business.accounting.service.impl;

import com.pinting.business.accounting.service.AccountingStatisticsService;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSubAccountExample;
import com.pinting.business.model.vo.BsDailyInterestVO;
import com.pinting.business.model.vo.BsSubAc4InterestVO;
import com.pinting.business.model.vo.BsUserBonusVO;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 账务统计实现类
 * 
 * @Project: business
 * @Title: AccountingStatisticsServiceImpl.java
 * @author dingpf
 * @date 2015-2-4 下午3:58:50
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class AccountingStatisticsServiceImpl implements
		AccountingStatisticsService {

	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsUserMapper bsUserMapper;

	@Override
	public List<BsSubAc4InterestVO> findInterestForProduct(Date interestDate) {
		List<BsSubAc4InterestVO> list = bsSubAccountMapper
				.selectInterestForProduct(DateUtil.addDays(interestDate, 0));
		return list.size() > 0 ? list : null;
	}

	@Override
	public List<BsDailyInterestVO> findUserDailyInterest(Date interestDate) {
		List<BsDailyInterestVO> list = bsSubAccountMapper
				.selectUserDailyInterest(DateUtil.addDays(interestDate, 0));
		return list.size() > 0 ? list : null;
	}

	@Override
	public List<BsUserBonusVO> findRecommendedUserAndInvestAmount() {
		List<BsUserBonusVO> list = bsUserMapper
				.selectRecommendedUserAndInvestAmount();
		return list.size() > 0 ? list : null;
	}

	@Override
	public void modifyExpireInvestInterest(Date currentDate) {
		BsSubAccountExample example = new BsSubAccountExample();
		List<Integer> statuss = new ArrayList<Integer>();
		statuss.add(2);
		statuss.add(3);
		List<String> productTypes = new ArrayList<>();
		productTypes.add(Constants.PRODUCT_TYPE_REG);
		productTypes.add(Constants.PRODUCT_TYPE_AUTH_YUN);
		productTypes.add(Constants.PRODUCT_TYPE_AUTH_ZSD);
		productTypes.add(Constants.PRODUCT_TYPE_AUTH_7);
		productTypes.add(Constants.PRODUCT_TYPE_AUTH_FREE);
		example.createCriteria()
				.andStatusIn(statuss)
				.andProductTypeIn(productTypes)
				.andLastFinishInterestDateEqualTo(
						DateUtil.parseDate(DateUtil.formatYYYYMMDD(currentDate)));
		BsSubAccount bsSubAccount = new BsSubAccount();
		bsSubAccount.setStatus(Constants.SUBACCOUNT_STATUS_SETTLEING);
		bsSubAccountMapper.updateByExampleSelective(bsSubAccount, example);
	}

}
