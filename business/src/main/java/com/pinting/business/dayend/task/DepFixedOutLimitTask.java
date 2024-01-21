package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.LnDailyAmountMapper;
import com.pinting.business.model.LnDailyAmount;
import com.pinting.business.model.LnDailyAmountExample;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.MoneyUtil;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangbao on 2017/4/1.
 * @author yangende 2018-6-1 13:46:24
 * @deprecated 自由资金计划-放款额度统计改造
 */
@Service
public class DepFixedOutLimitTask {
    private Logger logger = LoggerFactory.getLogger(DepFixedOutLimitTask.class);

    @Autowired
    private LnDailyAmountMapper lnDailyAmountMapper;

    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;

    @Autowired
    private SpecialJnlService specialJnlService;
    
    // 定时任务执行方法
    public void execute() {
    	//自由资金户
    	Double dailyFreeCashTotal = bsSubAccountMapper.dailyMoneyCalculate(DateUtil.getDate(new Date()));
    	logger.info(DateUtil.getDate(new Date())+"自由资金计划总量:"+dailyFreeCashTotal);
    	//云贷资金站岗额度
    	yunOutLimitTask(dailyFreeCashTotal);
    	//七贷资金站岗额度
    	sevenDaiSelfOutLimitTask(dailyFreeCashTotal);
    }
    
    private void yunOutLimitTask( Double dailyFreeCashTotal) {
    	logger.info("============定时任务：云贷自主放款"+DateUtil.getDate(new Date())+"放款限额统计定时开始执行============");
        try {
	        /**
	         * 统计云贷每日放款限额=
	         * 除VIP外的云贷站岗户和红包户可用金额（注意起息日）- 退出申请中当日需要的承接金额 - VIP债权转让需要的承接金额 
	         */
	        Double dailyTotal = bsSubAccountMapper.dailyMoneyCount();
	        LnDailyAmount apply = new LnDailyAmount();
	        apply.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
	        if(dailyTotal == null){
	            dailyTotal = 0d;
	        }
	        List<String> sttList = new ArrayList<String>();
	        sttList.add(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE);
	        sttList.add(Constants.LN_DAILY_AMOUNT_STATUS_INIT);
	        //查询运营最近一次有效的额度配置
	        LnDailyAmountExample example = new LnDailyAmountExample();
	        example.createCriteria().andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode())
	        	.andStatusIn(sttList);
	        example.setOrderByClause("update_time desc");
	        List<LnDailyAmount> lnDailyAmountList = lnDailyAmountMapper.selectByExample(example);
	        
	        if( CollectionUtils.isNotEmpty(lnDailyAmountList) ) {
	        	int lnDailyAmtId = lnDailyAmountList.get(0).getId();
	        	double freeRate  = lnDailyAmountList.get(0).getFreeRate();
	        	String lnDailyStatus= lnDailyAmountList.get(0).getStatus(); //INIT , AVALIABLE , CANCELLED 
	        	Date latestDate = lnDailyAmountList.get(0).getUseDate();
	        	
	        	if( DateUtil.getBetweenDays(new Date(), latestDate) == 1 ) {
	        		//如果额度记录已存在INIT,则直接更新为AVALIABLE 
		        	if( Constants.LN_DAILY_AMOUNT_STATUS_INIT.equals(lnDailyStatus) ) {
		        		LnDailyAmount dailyAmountTmp = new LnDailyAmount();
		            	dailyAmountTmp.setId(lnDailyAmtId);	
		            	dailyAmountTmp.setTermxAmount( MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue() ); 
		            	dailyAmountTmp.setTermxLeftAmount( MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue() );
		            	dailyAmountTmp.setFreeAmount(MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue());
		            	dailyAmountTmp.setPartnerAmount(dailyTotal); 
		            	dailyAmountTmp.setStatus(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE); 
		            	dailyAmountTmp.setUpdateTime(new Date()); 
		            	lnDailyAmountMapper.updateByPrimaryKeySelective(dailyAmountTmp); 
		        	} else if( Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE.equals(lnDailyStatus) ) {
		        		//更新原额度记录,重新插入一条新的可放款额度记录 
		            	LnDailyAmount dailyAmountTmp = new LnDailyAmount();
		            	dailyAmountTmp.setId(lnDailyAmtId);
		            	dailyAmountTmp.setStatus(Constants.LN_DAILY_AMOUNT_STATUS_CANCELLED);
		            	dailyAmountTmp.setUpdateTime(new Date());
		            	lnDailyAmountMapper.updateByPrimaryKeySelective(dailyAmountTmp);
		            	
		            	LnDailyAmount dailyAmountNew = new LnDailyAmount();
		            	dailyAmountNew.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
		            	dailyAmountNew.setFreeRate(freeRate); 
		            	dailyAmountNew.setTermxAmount( MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue() );
		            	dailyAmountNew.setTermxLeftAmount( MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue() );
		            	dailyAmountNew.setFreeAmount( MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() );
		        		dailyAmountNew.setPartnerAmount(dailyTotal);
		        		dailyAmountNew.setStatus(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE);
		        		dailyAmountNew.setUseDate(new Date());
		        		dailyAmountNew.setAvailableEndTime( DateUtil.getDateEnd2S(new Date()) );
		        		dailyAmountNew.setCreateTime(new Date());
		        		dailyAmountNew.setUpdateTime(new Date());
		            	lnDailyAmountMapper.insertSelective(dailyAmountNew);
		        	} 
	        	} else {
	        		LnDailyAmount dailyAmountTmp = new LnDailyAmount();
	        		dailyAmountTmp.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
	        		dailyAmountTmp.setFreeRate(freeRate); 
	        		dailyAmountTmp.setTermxAmount( MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue() );
	            	dailyAmountTmp.setTermxLeftAmount( MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue() );
	            	dailyAmountTmp.setFreeAmount(MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue());
	            	dailyAmountTmp.setPartnerAmount(dailyTotal);
	            	dailyAmountTmp.setStatus(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE);
	            	dailyAmountTmp.setUseDate(new Date());
	            	dailyAmountTmp.setAvailableEndTime( DateUtil.getDateEnd2S(new Date()) );
	            	dailyAmountTmp.setCreateTime(new Date());
	            	dailyAmountTmp.setUpdateTime(new Date());
	            	lnDailyAmountMapper.insertSelective(dailyAmountTmp);
	        	}
	        } else {
	        	logger.info("云贷每日放款额度统计异常,未设定自有资金配比!");
	        	specialJnlService.warnDevelop4Fail(0d, "云贷每日放款额度统计异常,未设定自有资金配比", "", "云贷放款额度统计异常", true);
	        }
        } catch (Exception e) {
        	logger.error("每日放款额度统计异常", e);
		} finally {
			logger.info("============定时任务：云贷自主放款"+DateUtil.getDate(new Date())+"放款限额统计定时执行结束============");
		}
    }
//    private void zsdOutLimitTask() {
//    	logger.info("============定时任务：赞时贷每日放款限额统计定时开始执行============");
//        LnDailyAmountExample example = new LnDailyAmountExample();
//        example.createCriteria().andUseDateEqualTo(new Date()).andPartnerCodeEqualTo(PartnerEnum.ZSD.getCode());
//        List<LnDailyAmount> lnDailyAmountList = lnDailyAmountMapper.selectByExample(example);
//        if(CollectionUtils.isNotEmpty(lnDailyAmountList)){
//            logger.error("今日放款限额统计定时重复执行");
//            return;
//        }
//        Double dailyTotal = bsSubAccountMapper.zsdDailyMoneyCount();
//        LnDailyAmount apply = new LnDailyAmount();
//        apply.setPartnerCode(PartnerEnum.ZSD.getCode());
//        if(dailyTotal == null){
//            dailyTotal = 0d;
//        }
//        apply.setTermxAmount(dailyTotal);
//        apply.setUseDate(new Date());
//        apply.setCreateTime(new Date());
//        apply.setUpdateTime(new Date());
//        lnDailyAmountMapper.insertSelective(apply);
//        logger.info("============定时任务：赞时贷每日放款限额统计定时执行结束============");
//    }
    private void sevenDaiSelfOutLimitTask(Double dailyFreeCashTotal) {
    	logger.info("============定时任务：7贷自主放款"+DateUtil.getDate(new Date())+"放款限额统计定时开始执行============");
        try {
	        /**
	         * 统计7贷每日放款限额=
	         * 除VIP外的7贷站岗户和红包户可用金额（注意起息日）- 退出申请中当日需要的承接金额 - VIP债权转让需要的承接金额 - 7贷站岗户可用金额低于10元（可配）的金额
	         */
	        Double dailyTotal = bsSubAccountMapper.sevenDaiSelfDailyMoneyCount();
	        LnDailyAmount apply = new LnDailyAmount();
	        apply.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
	        if(dailyTotal == null){
	            dailyTotal = 0d;
	        }
	        List<String> sttList = new ArrayList<String>();
	        sttList.add(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE);
	        sttList.add(Constants.LN_DAILY_AMOUNT_STATUS_INIT);
	        //查询运营最近一次有效的额度配置
	        LnDailyAmountExample example = new LnDailyAmountExample();
	        example.createCriteria().andPartnerCodeEqualTo(PartnerEnum.SEVEN_DAI_SELF.getCode())
	        	.andStatusIn(sttList);
	        example.setOrderByClause("update_time desc");
	        List<LnDailyAmount> lnDailyAmountList = lnDailyAmountMapper.selectByExample(example);
	        
	        if( CollectionUtils.isNotEmpty(lnDailyAmountList) ) {
	        	int lnDailyAmtId = lnDailyAmountList.get(0).getId();
	        	double freeRate  = lnDailyAmountList.get(0).getFreeRate();
	        	String lnDailyStatus= lnDailyAmountList.get(0).getStatus(); //INIT , AVALIABLE , CANCELLED 
	        	Date latestDate = lnDailyAmountList.get(0).getUseDate();
	        	
	        	if( DateUtil.getBetweenDays(new Date(), latestDate) == 1 ) {
	        		//如果额度记录已存在INIT,则直接更新为AVALIABLE 
		        	if( Constants.LN_DAILY_AMOUNT_STATUS_INIT.equals(lnDailyStatus) ) {
		        		LnDailyAmount dailyAmountTmp = new LnDailyAmount();
		            	dailyAmountTmp.setId(lnDailyAmtId);	
		            	dailyAmountTmp.setTermxAmount( MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue() ); 
		            	dailyAmountTmp.setTermxLeftAmount(  MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue()  );
		            	dailyAmountTmp.setFreeAmount(MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue());
		            	dailyAmountTmp.setPartnerAmount(dailyTotal); 
		            	dailyAmountTmp.setStatus(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE); 
		            	dailyAmountTmp.setUpdateTime(new Date()); 
		            	lnDailyAmountMapper.updateByPrimaryKeySelective(dailyAmountTmp); 
		        	} else if( Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE.equals(lnDailyStatus) ) {
		        		//更新原额度记录,重新插入一条新的可放款额度记录 
		            	LnDailyAmount dailyAmountTmp = new LnDailyAmount();
		            	dailyAmountTmp.setId(lnDailyAmtId);
		            	dailyAmountTmp.setStatus(Constants.LN_DAILY_AMOUNT_STATUS_CANCELLED);
		            	dailyAmountTmp.setUpdateTime(new Date());
		            	lnDailyAmountMapper.updateByPrimaryKeySelective(dailyAmountTmp);
		            	
		            	LnDailyAmount dailyAmountNew = new LnDailyAmount();
		            	dailyAmountNew.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
		            	dailyAmountNew.setFreeRate(freeRate); 
		            	dailyAmountNew.setTermxAmount( MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue() );
		            	dailyAmountNew.setTermxLeftAmount( MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue() );
		            	dailyAmountNew.setFreeAmount( MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() );
		        		dailyAmountNew.setPartnerAmount(dailyTotal);
		        		dailyAmountNew.setStatus(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE);
		        		dailyAmountNew.setUseDate(new Date());
		        		dailyAmountNew.setAvailableEndTime( DateUtil.getDateEnd2S(new Date()) );
		        		dailyAmountNew.setCreateTime(new Date());
		        		dailyAmountNew.setUpdateTime(new Date());
		            	lnDailyAmountMapper.insertSelective(dailyAmountNew);
		        	} 
	        	} else {
	        		LnDailyAmount dailyAmountTmp = new LnDailyAmount();
	        		dailyAmountTmp.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
	        		dailyAmountTmp.setFreeRate(freeRate); 
	        		dailyAmountTmp.setTermxAmount( MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue() );
	            	dailyAmountTmp.setTermxLeftAmount( MoneyUtil.add( dailyTotal, MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue() ).doubleValue() );
	            	dailyAmountTmp.setFreeAmount(MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRate).doubleValue(),100,2).doubleValue());
	            	dailyAmountTmp.setPartnerAmount(dailyTotal);
	            	dailyAmountTmp.setStatus(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE);
	            	dailyAmountTmp.setUseDate(new Date());
	            	dailyAmountTmp.setAvailableEndTime( DateUtil.getDateEnd2S(new Date()) );
	            	dailyAmountTmp.setCreateTime(new Date());
	            	dailyAmountTmp.setUpdateTime(new Date());
	            	lnDailyAmountMapper.insertSelective(dailyAmountTmp);
	        	}
	        } else {
	        	logger.info("云贷每日放款额度统计异常,未设定自有资金配比!");
	        	specialJnlService.warnDevelop4Fail(0d, "云贷每日放款额度统计异常,未设定自有资金配比", "", "云贷放款额度统计异常", true);
	        }
	        
        } catch (Exception e) {
        	logger.error("每日放款额度统计异常", e);
		} finally {
			logger.info("============定时任务：7贷自主放款"+DateUtil.getDate(new Date())+"放款限额统计定时执行结束============");
		}
        
    }
}
