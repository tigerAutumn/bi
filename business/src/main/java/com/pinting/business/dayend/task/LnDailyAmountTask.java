package com.pinting.business.dayend.task;

import com.pinting.business.dao.LnDailyAmountMapper;
import com.pinting.business.model.LnDailyAmount;
import com.pinting.business.model.LnDailyAmountExample;
import com.pinting.business.service.loan.LoanQueryService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户站岗资金日终统计
 * @author bianyatian
 * @2016-8-23 下午7:50:49
 */
@Service
public class LnDailyAmountTask {

	private Logger log = LoggerFactory.getLogger(LnDailyAmountTask.class);
	@Autowired
	private LnDailyAmountMapper lnDailyAmountMapper;
	@Autowired
	private LoanQueryService loanQueryService;
	
	public void execute() {
		/**
		 * 1.查询bs_sub_account,bs_product,bs_property_info, 资产方为蜂鸟，且bs_sub_account表中product_type为AUTH的各个周期的数据（普通用户和超级理财人分开查）
		 * 2.超级理财人编号在bs_sys_config中配置
		 * 3.记录ln_daily_amount（一天一条）
		 */
		log.info("==================【用户站岗资金日终统计】 开始 =================");
		//校验是否已存在当日数据
		Date now =new Date();
		Date useDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(now));
		LnDailyAmountExample example = new LnDailyAmountExample();
		example.createCriteria().andUseDateEqualTo(useDate).andPartnerCodeEqualTo(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
		List<LnDailyAmount> list = lnDailyAmountMapper.selectByExample(example); 
		if(list!= null && list.size()>0){
			log.info("==================【用户站岗资金日终统计】 已存在 =================");
		}else{
			LnDailyAmount lnDailyAmount = loanQueryService.queryDailyAmount();
			if(lnDailyAmount != null ){
				log.info("==================【用户站岗资金日终统计】1个月："+lnDailyAmount.getTerm1Amount()+",2个月："+lnDailyAmount.getTerm2Amount() 
						+",3个月："+lnDailyAmount.getTerm3Amount()+",4个月："+lnDailyAmount.getTerm4Amount()
						+",5个月："+lnDailyAmount.getTerm4Amount()+",6个月："+lnDailyAmount.getTerm6Amount()
						+",9个月："+lnDailyAmount.getTerm9Amount()+",12个月： "+lnDailyAmount.getTerm12Amount()
						+",超级理财人： "+lnDailyAmount.getTermxAmount()+"=================");
				
				lnDailyAmountMapper.insertSelective(lnDailyAmount);
				
			}
			log.info("==================【用户站岗资金日终统计】 结束 =================");
		}	
	}
}
