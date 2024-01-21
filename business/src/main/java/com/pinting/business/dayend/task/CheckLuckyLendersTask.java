package com.pinting.business.dayend.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsBannerConfig;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.BsActivityLuckyDrawVO;
import com.pinting.business.service.manage.BsBannerConfigService;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.service.site.ActivityLuckyDrawService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;

/**
 * 查询周周乐幸运借款人，存库
 * Created by zhangpeng on 2018/05/08.
 */
@Service
public class CheckLuckyLendersTask {
	
	private Logger log = LoggerFactory.getLogger(CheckLuckyLendersTask.class);

	@Autowired
	private BsSubAccountService bsSubAccountService;
	@Autowired
	private ActivityLuckyDrawService activityLuckyDrawService;
	@Autowired
	private BsBannerConfigService bsBannerConfigService;
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
     * 任务执行
     */
    public void execute() {
    	//查询本周是否进行幸运出借人活动
    	BsBannerConfig pcBannerConfig = bsBannerConfigService.queryBannerInfo(Constants.BANNER_URL_WEEKHAY_BGW_PC, Constants.BANNER_CHANNEL_GEN);
    	BsBannerConfig h5BannerConfig = bsBannerConfigService.queryBannerInfo(Constants.BANNER_URL_WEEKHAY_BGW_H5, Constants.BANNER_CHANNEL_MICRO);
    	BsBannerConfig appBannerConfig = bsBannerConfigService.queryBannerInfo(Constants.BANNER_URL_WEEKHAY_APP, Constants.BANNER_CHANNEL_APP);
		if(pcBannerConfig == null || h5BannerConfig == null || appBannerConfig == null ) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else {
			if (Constants.WEEKHAY_LUCKY_LENDERS.equals(pcBannerConfig.getActivityType()) && 
				Constants.WEEKHAY_LUCKY_LENDERS.equals(h5BannerConfig.getActivityType()) &&
				Constants.WEEKHAY_LUCKY_LENDERS.equals(appBannerConfig.getActivityType())) {
				//获取当前星期几
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
				//获取活动日星期几
				BsSysConfig activityStartTimeConfig = sysConfigService.findConfigByKey(Constants.LUCKY_LENDERS_ACTIVITY_START_TIME);
	    		Integer activityStartTime = Integer.valueOf(activityStartTimeConfig.getConfValue());
	    		//判断统计日为活动日的第二天
	    		if (week - activityStartTime == 1) {
	    			//查询今天的幸运出借人列表
	    			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); 
	    			List<BsActivityLuckyDrawVO> list = activityLuckyDrawService.getLuckyLendersByDate(Constants.LUCKY_LENDERS_ACTIVITY_ID, df.format(cal.getTime()));
	    			if (list.size() == 0) {
	    				checkLuckyLendersTask();
					} else {
						log.info("==================【周周乐】幸运出借人已统计，请勿重复统计====================");
					}
				} else {
					log.info("==================【周周乐】幸运出借人统计日错误，不是活动日的第二天 ====================");
				}
			} else {
				log.info("==================本周【周周乐】活动不为幸运出借人，所以不需要统计出借人列表====================");
			}
		}
    }
    
    private void checkLuckyLendersTask(){
    	log.info("==================筛选【周周乐  幸运出借人】开始====================");
    	//统计用户在币活动日当天的总年化投资额
    	Double sumAmount = bsSubAccountService.selectWeekhaySumAmount();
    	if (sumAmount < 2000000) {
    		log.info("==================【周周乐  幸运出借人】当日总年化投资额小于200万====================");
			//统计单个用户年化投资额达到5万的列表
    		List<BsActivityLuckyDrawVO> bigInvestorsList = bsSubAccountService.selectWeekhayBigInvestors(50000d);
    		if (bigInvestorsList.size() >= 3) {
    			log.info("==================【周周乐  幸运出借人】当日单个用户投资额达到5万的大于等于3人====================");
				//达到5万的用户中获取3名入库
    			getLuckyList(bigInvestorsList, 3, Constants.LUCKY_LENDERS_BOUNS_50_ID, "50元奖励金");
			} else {
				log.info("==================【周周乐  幸运出借人】当日单个用户投资额达到5万的小于3人====================");
				//当日投资用户列表
				List<BsActivityLuckyDrawVO> allInvestorsList = bsSubAccountService.selectWeekhayAllInvestors();
				//当日投资用户中获取3名
				getLuckyList(allInvestorsList, 3, Constants.LUCKY_LENDERS_BOUNS_10_ID, "10元奖励金");
			}
		} else if (2000000 <= sumAmount && sumAmount < 3000000) {
			log.info("==================【周周乐  幸运出借人】当日总年化投资额达到200万，小于300万====================");
			//统计单个用户年化投资额达到10万的列表
    		List<BsActivityLuckyDrawVO> bigInvestorsList = bsSubAccountService.selectWeekhayBigInvestors(100000d);
    		if (bigInvestorsList.size() >= 4) {
    			log.info("==================【周周乐  幸运出借人】当日单个用户投资额达到10万的大于等于4人====================");
				//达到10万的用户中获取4名
    			getLuckyList(bigInvestorsList, 4, Constants.LUCKY_LENDERS_BOUNS_100_ID, "100元奖励金");
			} else {
				log.info("==================【周周乐  幸运出借人】当日单个用户投资额达到10万的小于4人====================");
				//当日投资用户列表
				List<BsActivityLuckyDrawVO> allInvestorsList = bsSubAccountService.selectWeekhayAllInvestors();
				//当日投资用户中获取4名
				getLuckyList(allInvestorsList, 4, Constants.LUCKY_LENDERS_BOUNS_15_ID, "15元奖励金");
			}
		} else {
			log.info("==================【周周乐  幸运出借人】当日总年化投资额达到300万====================");
			//统计单个用户年化投资额达到10万的列表
    		List<BsActivityLuckyDrawVO> bigInvestorsList = bsSubAccountService.selectWeekhayBigInvestors(100000d);
    		if (bigInvestorsList.size() >= 5) {
    			log.info("==================【周周乐  幸运出借人】当日单个用户投资额达到10万的大于等于5人====================");
				//达到10万的用户中获取5名
    			getLuckyList(bigInvestorsList, 5, Constants.LUCKY_LENDERS_BOUNS_150_ID, "150元奖励金");
			} else {
				log.info("==================【周周乐  幸运出借人】当日单个用户投资额达到10万的小于5人====================");
				//当日投资用户列表
				List<BsActivityLuckyDrawVO> allInvestorsList = bsSubAccountService.selectWeekhayAllInvestors();
				//当日投资用户中获取5名
				getLuckyList(allInvestorsList, 5, Constants.LUCKY_LENDERS_BOUNS_20_ID, "20元奖励金");
			}
		}
        log.info("==================筛选【周周乐  幸运出借人】结束====================");
    }
    
    //将幸运出借人入库
    private void getLuckyList(List<BsActivityLuckyDrawVO> investorsList, Integer listSize, Integer bonusId, String note) {
    	if (investorsList.size() < listSize) {
			log.info("==================【周周乐  幸运出借人】投资人数不足，无法统计出幸运出借人列表 ====================");
		} else {
	    	Random rand = new Random();
	    	for (int i = 0; i < listSize; i++) {  
	            int myRand = rand.nextInt(investorsList.size());  
	            activityLuckyDrawService.addLuckyLenders(investorsList.get(myRand).getUserId(), bonusId, note);
	            investorsList.remove(myRand);
	        }
		}
    }
}
