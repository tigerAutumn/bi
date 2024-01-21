package com.pinting.business.service.loan.impl;

import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.DailyAmount4LoanVO;
import com.pinting.business.service.loan.AuthBalanaceQueryService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class AuthBalanaceQueryServiceImpl implements AuthBalanaceQueryService {
	
	
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private SysConfigService sysConfigService;
	
	@Override
	public Double getNormalAuthBalance(Integer term,Integer day4waitLoan, List<Integer> superUserList, Integer outUserId) {
		Double normalAmount = 0d;
		
		Date now =new Date();
		//起息日在此日期之前的
		String minInterestBeginDate =DateUtil.formatYYYYMMDD( DateUtil.addDays(now, -day4waitLoan));
		BsSysConfig limit = sysConfigService.findConfigByKey(Constants.MATCH_LIMIT_AMOUNT);//债权匹配时低于该金额的不进行债权承接
//		Double limitAmount = 20d;//普通理财人债权匹配时低于该金额的不进行债权承接
		Double limitAmount = 1000d;//普通理财人债权匹配时低于该金额的不进行债权承接
		if(limit != null){
			limitAmount = Double.valueOf(limit.getConfValue());
		}
		List<DailyAmount4LoanVO> normalUserList = bsSubAccountMapper.getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				Constants.PRODUCT_TYPE_AUTH, superUserList, minInterestBeginDate, "no",limitAmount,outUserId);
		
		if(!CollectionUtils.isEmpty(normalUserList)){
			//若term为null，则查询所有非超级用户的站岗户总金额
			if(term == null){
				for (DailyAmount4LoanVO dailyAmount4LoanVO : normalUserList) {
					normalAmount = MoneyUtil.add(dailyAmount4LoanVO.getSumAmount(), normalAmount).doubleValue();
				}
			}

			//根据term查询购买产品是某个月的非超级用户的站岗户总金额
			if(term != null){
				for (DailyAmount4LoanVO dailyAmount4LoanVO : normalUserList) {
					if(dailyAmount4LoanVO.getTerm() == term){
						normalAmount = MoneyUtil.add(dailyAmount4LoanVO.getSumAmount(),normalAmount).doubleValue();
					}
				}
			}
		}	
		
		return normalAmount;
	}

	@Override
	public Double getSmallNormalAuthBalanceNew(Integer term,Integer day4waitLoan, List<Integer> superUserList) {
		Double normalAmount = 0d;

		Date now =new Date();
		//起息日在此日期之前的
		String minInterestBeginDate =DateUtil.formatYYYYMMDD( DateUtil.addDays(now, -day4waitLoan));
		BsSysConfig limit = sysConfigService.findConfigByKey(Constants.MATCH_LIMIT_AMOUNT);//债权匹配时低于该金额的不进行债权承接
		Double limitAmount = 1000d;//普通理财人债权匹配时低于该金额的不进行债权承接
		if(limit != null){
			limitAmount = Double.valueOf(limit.getConfValue());
		}
		//查询单个剩余投资金额>0且<1000的各期剩余总金额
		List<DailyAmount4LoanVO> normalUserList = bsSubAccountMapper.getSumBalanceByProductTypeSmall(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
				Constants.PRODUCT_TYPE_AUTH, superUserList, minInterestBeginDate, "no",limitAmount);

		if(!CollectionUtils.isEmpty(normalUserList)){

			//根据term查询购买产品是某个月的非超级用户的站岗户总金额
			if(term != null){
				for (DailyAmount4LoanVO dailyAmount4LoanVO : normalUserList) {
					if(dailyAmount4LoanVO.getTerm() == term){
						normalAmount = MoneyUtil.add(dailyAmount4LoanVO.getSumAmount(),normalAmount).doubleValue();
					}
				}
			}
		}

		return normalAmount;
	}

	@Override
	public Double getSuperAuthBalance(List<Integer> superUserList) {
		Double amount = 0d;
		if(!CollectionUtils.isEmpty(superUserList)){
			//超级理财人投资金额查询
			List<DailyAmount4LoanVO> superList = null;
			superList = bsSubAccountMapper.getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
					Constants.PRODUCT_TYPE_AUTH, superUserList, null,"yes",null,null);
			if(!CollectionUtils.isEmpty(superList)){
				amount = superList.get(0).getSumAmount();
			}
		}
		return amount;
	}

	@Override
	public Double getNormalAuthBalanceZDS(List<Integer> vipUserList) {
		
		return null;
	}

	@Override
	public Double getVIPAuthBalanceZDS(List<Integer> vipUserList) {
		return null;
	}
	

}
