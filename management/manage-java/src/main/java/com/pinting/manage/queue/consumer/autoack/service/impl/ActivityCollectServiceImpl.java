package com.pinting.manage.queue.consumer.autoack.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.dao.BsBannerConfigMapper;
import com.pinting.business.hessian.site.message.ReqMsg_Activity_SalaryIncreasePlan;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ResMsg_Activity_SalaryIncreasePlan;
import com.pinting.business.model.BsActivityLuckyDraw;
import com.pinting.business.model.BsProduct;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.StringUtil;
import com.pinting.rabbit.queue.core.model.RabbitFlowContext;
import com.pinting.rabbit.queue.core.model.RabbitQueuesVO;
import com.pinting.rabbit.queue.core.service.RabbitServiceExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

@Service("activityCollectServiceImpl")
public class ActivityCollectServiceImpl implements RabbitServiceExecutor {

	private Logger logger = LoggerFactory.getLogger(ActivityCollectServiceImpl.class);
	
	@Autowired
	BsBannerConfigMapper bsBannerConfigMapper;
	@Autowired
    private ActivityService activityService;
	@Autowired
	private SpecialJnlService specialJnlService;
	
	@Override
	public Object execute(RabbitFlowContext flowContext) {
		logger.info("==============运营活动数据采集，队列处理开始============");
		String order_no = null;
		Integer sub_account_id = null;
		try {
			
			RabbitQueuesVO<JSONObject> rabbitQueueVo = flowContext.getQueuesVO();
			
			JSONObject json = rabbitQueueVo.getBodyVO();
			logger.info("运营日常活动数据采集出MQ=["+json.toJSONString()+"]");
			
			ReqMsg_RegularBuy_BalanceBuy req = JSONObject.parseObject(json.getJSONObject("RegularBuyBalanceReq").toJSONString(), ReqMsg_RegularBuy_BalanceBuy.class) ;
			BsProduct product = JSONObject.parseObject(json.getJSONObject("BsProduct").toJSONString(), BsProduct.class) ;
			sub_account_id = (Integer) json.get("sub_account_id");
			order_no = (String) json.get("order_no");
			
			//=====================================加薪计划活动====================================================
            //banner存在且为15号
            Calendar today = Calendar.getInstance();
            int day = today.get(Calendar.DAY_OF_MONTH);
            //每月15号为加薪活动日
            if (day == 15) {
            	int bannerCount = bsBannerConfigMapper.selectBannerCount
            			(Constants.BANNER_URL_SALARY_INCREASE);
            	if (bannerCount == 3) {
            		logger.info("============= 【加薪计划】日期和banner满足要求，进入加薪计划  ==============");
            		salaryIncreaseRank( req, product, sub_account_id, order_no );//新起线程执行加薪计划
            	} else {
            		logger.info("============= 【加薪计划】三端banner不同时存在，不进入加薪计划  ==============");
            	}
            }
            //=====================================加薪计划活动=======================================================
            //=====================================其他活动=======================================================
            
        } catch (Exception e) {
            logger.info("【加薪计划】购买统计失败{}", e);
            //告警
            specialJnlService.warnDevelop4Fail(0d, "日常活动数据采集出MQ:sub_account_id={"+sub_account_id+"}", order_no, "【加薪计划】", false);
        }
		logger.info("==============运营活动数据采集，队列处理结束============");
		return null;
	}
	
	private void salaryIncreaseRank(final ReqMsg_RegularBuy_BalanceBuy req, final BsProduct product, final Integer sub_account_id, final String order_no) {
            //用户购买港湾计划系列单笔年化出借额10000及以上
            String name = product.getName();
            String activityType = product.getActivityType();
            String showTerminal = product.getShowTerminal();
            Integer term = product.getTerm();
            Double sumAmount = 0d;
            switch (term) {
                case -7:
                    sumAmount = req.getAmount()/365*7;
                    break;
                case 1:
                    sumAmount = req.getAmount()/365*30;
                    break;
                case 3:
                    sumAmount = req.getAmount()/365*90;
                    break;
                case 6:
                    sumAmount = req.getAmount()/365*180;
                    break;
                case 12:
                    sumAmount = req.getAmount();
                    break;
            }
            logger.info("============= 【加薪计划】年化额："+sumAmount+"  ==============");
            if (StringUtil.contains(name, "港湾计划") &&
                    !"NEW_BUYER".equals(activityType) &&
                    !StringUtil.contains(showTerminal, "H5_QD") &&
                    sumAmount >= Constants.SALARY_INCREASE_FIRST_RANK_AMOUNT
                    ) {
                logger.info("============= 【加薪计划】购买满足中奖条件  ==============");
                ReqMsg_Activity_SalaryIncreasePlan planReq = new ReqMsg_Activity_SalaryIncreasePlan();
                ResMsg_Activity_SalaryIncreasePlan planRes = new ResMsg_Activity_SalaryIncreasePlan();
                ResMsg_Activity_SalaryIncreasePlan resultRes = activityService.salaryIncreasePlan(planReq, planRes);

                BsActivityLuckyDraw record = new BsActivityLuckyDraw();
                record.setUserId(req.getUserId());
                record.setActivityId(Constants.ACTIVITY_ID_SALARY_INCREASE_PLAN);
                record.setIsBackDraw("N");//不是后台抽奖
                record.setIsUserDraw("Y");//是用户抽奖
                record.setUserDrawTime(new Date());//用户抽奖时间
                record.setIsWin("Y");//用户是否中奖
                record.setIsConfirm("N");//用户是否确认
                record.setConfirmTime(new Date());//确认时间
                record.setIsAutoConfirm("Y");//是否自动确认
                record.setCreateTime(new Date());
                record.setUpdateTime(new Date());
                
                record.setSubAccountId(sub_account_id);
                record.setOrderNo(order_no);
                record.setYearInterest(sumAmount);
                record.setCouponType(req.getTicketType());
                record.setCouponId(req.getTicketId());
                
                
                DecimalFormat dFormat = new DecimalFormat("#.00");
                if (sumAmount <= Constants.SALARY_INCREASE_SECOND_RANK_AMOUNT
                        && resultRes.getMoreThan10000Quota() > 0) {
                    //年化出借额10000~50000，且榜单未满
                    logger.info("============= 【加薪计划】进入第一档   ==============");
                    record.setAwardId(Constants.SALARY_INCREASE_PLAN_BONUS_1);
                    String amountString = dFormat.format(sumAmount*Constants.SALARY_INCREASE_FIRST_RANK_BONUS);
                    record.setNote("奖励金金额："+Double.valueOf(amountString));
                    activityService.insertSelective(record);
                } else if (sumAmount > Constants.SALARY_INCREASE_SECOND_RANK_AMOUNT
                        && sumAmount <= Constants.SALARY_INCREASE_THIRD_RANK_AMOUNT
                        && resultRes.getMoreThan50000Quota() > 0) {
                    //年化出借额50001~100000，且榜单未满
                    logger.info("============= 【加薪计划】进入第二档   ==============");
                    record.setAwardId(Constants.SALARY_INCREASE_PLAN_BONUS_2);
                    String amountString = dFormat.format(sumAmount*Constants.SALARY_INCREASE_SECOND_RANK_BONUS);
                    record.setNote("奖励金金额："+Double.valueOf(amountString));
                    activityService.insertSelective(record);
                } else if (sumAmount > Constants.SALARY_INCREASE_THIRD_RANK_AMOUNT
                        && sumAmount <= Constants.SALARY_INCREASE_FOURTH_RANK_AMOUNT
                        && resultRes.getMoreThan100000Quota() > 0) {
                    //年化出借额100001~500000，且榜单未满
                    logger.info("============= 【加薪计划】进入第三档   ==============");
                    record.setAwardId(Constants.SALARY_INCREASE_PLAN_BONUS_3);
                    String amountString = dFormat.format(sumAmount*Constants.SALARY_INCREASE_THIRD_RANK_BONUS);
                    record.setNote("奖励金金额："+Double.valueOf(amountString));
                    activityService.insertSelective(record);
                } else if (sumAmount > Constants.SALARY_INCREASE_FOURTH_RANK_AMOUNT
                        && resultRes.getMoreThan500000Quota() > 0) {
                    //年化出借额500001以上，且榜单未满
                    logger.info("============= 【加薪计划】进入第四档   ==============");
                    record.setAwardId(Constants.SALARY_INCREASE_PLAN_BONUS_4);
                    String amountString = dFormat.format(sumAmount*Constants.SALARY_INCREASE_FOURTH_RANK_BONUS);
                    record.setNote("奖励金金额："+Double.valueOf(amountString));
                    activityService.insertSelective(record);
                }
            } else {
                logger.info("============= 【加薪计划】购买不满足中奖条件  ==============");
            }
        }
	
}
