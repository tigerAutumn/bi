package com.pinting.business.dayend.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.model.Loan7BillInfo;
import com.pinting.business.accounting.loan.service.DepFixedBillSyncService;
import com.pinting.business.dao.BsSpecialJnlMapper;
import com.pinting.business.dao.LnRepayScheduleDetailMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.model.LnRepayScheduleDetail;
import com.pinting.business.model.LnRepayScheduleExample;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.vo.RepaySchedule4DetailVO;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.loan7.model.Loan7QueryBillRepayment;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 七贷-随借随还-同步账单
 * @project business
 * @author bianyatian
 * @2018-3-22 上午11:51:13
 */
@Service
public class SyncBill4SevenRepayAnyTimeTask {
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	private Logger log = LoggerFactory.getLogger(SyncBill4SevenRepayAnyTimeTask.class);
	@Autowired
	private LnRepayScheduleMapper lnRepayScheduleMapper;
	@Autowired
	private LnUserMapper lnUserMapper;
	@Autowired 
	private DepFixedBillSyncService depFixedBillSyncService;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private LnRepayScheduleDetailMapper lnRepayScheduleDetailMapper;
	@Autowired
    private BsSpecialJnlMapper bsSpecialJnlMapper;
    @Autowired
    private SMSService smsService;
	
	public void execute() {
		
		log.info(">>>七贷-随借随还-同步账单定时开始<<<");
		Long len = jsClientDaoSupport.llen("sevenFreePayBill");
        log.info(">>>七贷-随借随还-同步账单队列大小"+ len +"<<<");
        int num = 100;
        int reTimes = 1;
        if(len == null){
			return;
		}else {
			reTimes =len.intValue()/num+1;
		}
        for (int i = 1; i <= reTimes; i++) {
			if(i == reTimes){ //最后一次获得循环个数
				num = len.intValue() - num*(reTimes-1);
			}
			for (int j = 1; j <= num; j++) {
             	String sevenFreePayBill = jsClientDaoSupport.lpop("sevenFreePayBill");
     			if(StringUtil.isEmpty(sevenFreePayBill)){
     				log.warn("redis数据为空");
     				break;
     			}
     			JSONObject json = JSON.parseObject(sevenFreePayBill);
     			log.info(">>>七贷-随借随还-同步账单定时，第"+i+"次循环第"+j+"条，数据:" + sevenFreePayBill + "<<<");
     			String timeStr = json.getString("time");
     			Date time = DateUtil.parseDateTime(timeStr);
     			Date now = new Date();
     			if(time == null){
     				//首次拉取账单，存入时间
     				json.put("time", DateUtil.format(now));
     				jsClientDaoSupport.rpush("sevenFreePayBill", JSON.toJSONString(json));
     				continue;
     			}else if(DateUtil.getDiffeMinute(now, time) <= 5){
     				jsClientDaoSupport.rpush("sevenFreePayBill", JSON.toJSONString(json));
     				continue;
     			}
     			syncBill(json,now);
     		}
		}
        
       
			
	}

	private void syncBill(JSONObject json,Date now) {
		try {
			Integer noRePushMinute = 30;//几分钟后只告警，不入redis
			String partnerLoanId = json.getString("partnerLoanId");
			Integer userId = json.getInteger("userId");
			Integer loanId = json.getInteger("loanId");
			Double loanAmount = json.getDouble("loanAmount");
			Date time = json.getDate("time");
			//根据借款id查询已还本金('LATE_NOT','LATE_REPAIED','REPAIED')
			Double repaiedAmount = lnRepayScheduleMapper.sumRepaiedByLoanId(loanId);
			log.info(">>>七贷-随借随还-同步账单repaiedAmount:"+repaiedAmount+"loanAmount"+loanAmount+","+repaiedAmount.compareTo(loanAmount));
			if(repaiedAmount.compareTo(loanAmount) == 0){
				//本金已还完 - 修改该笔借款所有未还账单的状态为废除
				LnRepaySchedule repayScheduleUpd = new LnRepaySchedule();
                repayScheduleUpd.setStatus(Constants.LN_REPAY_CANCELLED);
                repayScheduleUpd.setUpdateTime(now);
                LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
                repayScheduleExample.createCriteria().andLoanIdEqualTo(loanId).andStatusEqualTo(Constants.LN_REPAY_STATUS_INIT);
                lnRepayScheduleMapper.updateByExampleSelective(repayScheduleUpd, repayScheduleExample);
                
			}else if(repaiedAmount.compareTo(loanAmount)<0){
				boolean isRePush=false;//是否重新入redis标识
				//已还本金小于借款本金，说明未全部还款，同步账单
				//查询借款用户新
				LnUser lnUser = lnUserMapper.selectByPrimaryKey(userId);
				//拉取账单
				Loan7BillInfo loan7BillInfo = new Loan7BillInfo();

				log.info(">>>七贷-随借随还-同步账单，正在拉取账单，loanId:"+loanId);
				try {
					loan7BillInfo = depFixedBillSyncService.getSevenNewBills(lnUser.getPartnerUserId(), partnerLoanId);  
				} catch (Exception e) {
					log.info(">>>七贷-随借随还-同步账单，拉取账单异常，loanId:"+loanId);
					e.printStackTrace();
				}
				String specialJnlNot = "";//告警短信内容
				boolean sendSmsFlag = false;//发送短信告警标识
				if(loan7BillInfo == null || CollectionUtils.isEmpty(loan7BillInfo.getRepayments())){
					//未拉取到账单，重新入redis，短信告警（半小时后），告警后不入redis；
					if(DateUtil.getDiffeMinute(now, time) >= noRePushMinute){
						//短信告警，不入redis
						specialJnlNot = specialJnlNot + "本地账单尚未还完,拉取七贷无账单;";
						sendSmsFlag = true;
					}else{
						if(!isRePush){
							jsClientDaoSupport.rpush("sevenFreePayBill", JSON.toJSONString(json));
							isRePush = true;
						}
					}
				}else{
					//已拉取到七贷账单，查询该笔借款对应的所有有效账单
					List<RepaySchedule4DetailVO> scheduleList = lnRepayScheduleMapper.selectAllSchedule(loanId);
					for (Loan7QueryBillRepayment repayment : loan7BillInfo.getRepayments()) {
						RepaySchedule4DetailVO scheduleTemp = null;
						Double total = repayment.getTotal();
						Double principal = repayment.getPrincipal();
						Double interest = repayment.getInterest();
						Double principalOverdue = repayment.getPrincipalOverdue();
						Double interestOverdue = repayment.getInterestOverdue();
						if(CollectionUtils.isNotEmpty(scheduleList)){
							for (RepaySchedule4DetailVO repaySchedule4DetailVO : scheduleList) {
								//对比账单编号是否一致，或则是非0的期数是否一致，若一致视为同笔账单
								/*log.info(">>>>>>>>repayment.getRepayId()="+repayment.getRepayId());
								log.info(">>>>>>>>repaySchedule4DetailVO.getPartnerRepayId()="+repaySchedule4DetailVO.getPartnerRepayId());
								log.info(">>>>>>>>repayment.getRepaySerial()="+repayment.getRepaySerial());
								log.info(">>>>>>>>repaySchedule4DetailVO.getSerialId()="+repaySchedule4DetailVO.getSerialId());*/
								if(repayment.getRepayId().equals(repaySchedule4DetailVO.getPartnerRepayId())
										|| (repayment.getRepaySerial()==repaySchedule4DetailVO.getSerialId()
												&& repaySchedule4DetailVO.getSerialId()!=0 )){
									scheduleTemp = repaySchedule4DetailVO;
									break;
								}
							}
						}
						log.info(">>>七贷-随借随还-同步账单：loanId:"+loanId+"parRepayId:"+repayment.getRepayId()
								+"本地状态："+(scheduleTemp == null ?"无":scheduleTemp.getStatus())
								+"七贷状态："+repayment.getStatus());
						if(Constants.LN_REPAY_STATUS_INIT.equals(repayment.getStatus())){
							if(null == scheduleTemp){
								//七贷INIT，本地无账单，新增，告警；
								addRepaySchedule(repayment,loanId);
								specialJnlNot = specialJnlNot + "七贷有INIT账单，本地无账单，合作方还款id："+repayment.getRepayId()+";";
							}else if(Constants.LN_REPAY_STATUS_INIT.equals(scheduleTemp.getStatus())){
								//七贷INIT，本地INIT,若金额不一致，修改账单及明细
								boolean isSame = true;
								if(scheduleTemp.getPrincipal() == 0){
									isSame=false;//等于0时，认为不需要修改
								}
								if(scheduleTemp.getPlanTotal().compareTo(total) != 0){
									updateSchedule(scheduleTemp.getId(),total);
									isSame=false;
								}
								if(scheduleTemp.getPrincipal().compareTo(principal) != 0){
									updateScheduleDetail(scheduleTemp.getPrincipalId(),principal);
									isSame=false;
								}
								if(scheduleTemp.getInterest().compareTo(interest) != 0){
									updateScheduleDetail(scheduleTemp.getInterestId(),interest);
									isSame=false;
								}
								if(scheduleTemp.getPrincipalOverdue().compareTo(principalOverdue) != 0){
									updateScheduleDetail(scheduleTemp.getPrincipalOverdueId(),principalOverdue);
									isSame=false;
								}
								if(scheduleTemp.getInterestOverdue().compareTo(interestOverdue) != 0){
									updateScheduleDetail(scheduleTemp.getInterestOverdueId(),interestOverdue);
									isSame=false;
								}
								if(isSame){
									//七贷INIT，本地INIT，金额一致，重新入redis，告警（半小时后），告警后不入redis；
									log.info(">>>七贷-随借随还-同步账单,七贷INIT，本地INIT，金额一致，入redis时间："+time+",当前时间："+now +"间隔"+DateUtil.getDiffeMinute(now, time));
									if(DateUtil.getDiffeMinute(now, time) >= noRePushMinute){
				    					//告警，不入redis
										specialJnlNot = specialJnlNot + "还款后七贷INIT，本地INIT，金额一致，"+scheduleTemp.getId()+";";
				    				}else{
				    					if(!isRePush){
											jsClientDaoSupport.rpush("sevenFreePayBill", JSON.toJSONString(json));
											isRePush = true;
										}
				    				}
								}
							}else if(Constants.LN_REPAY_REPAIED.equals(scheduleTemp.getStatus()) || Constants.LN_REPAY_LATE_NOT.equals(scheduleTemp.getStatus())
									|| Constants.LN_REPAY_LATE_REPAIED.equals(scheduleTemp.getStatus())){
								//七贷INIT，本地已还（包括代偿和逾期），重新入redis，短信告警（半小时后），告警后不入redis
								log.info(">>>七贷-随借随还-同步账单,七贷INIT，本地已还（包括代偿和逾期），入redis时间："+time+",当前时间："+now +"间隔"+DateUtil.getDiffeMinute(now, time));
								if(DateUtil.getDiffeMinute(now, time) >= noRePushMinute){
			    					//短信告警，不入redis
			    					specialJnlNot = specialJnlNot + "七贷INIT，本地已还（包括代偿和逾期），planId"+scheduleTemp.getId()+";";
									sendSmsFlag = true;
			    				}else{
			    					if(!isRePush){
										jsClientDaoSupport.rpush("sevenFreePayBill", JSON.toJSONString(json));
										isRePush = true;
									}
			    				}
							}
							
						}else if(!Constants.LN_REPAY_CANCELLED.equals(repayment.getStatus())){
							if(scheduleTemp == null){
								//七贷已还，本地无账单，短信告警；
								specialJnlNot = specialJnlNot + "七贷已还（包括代偿和逾期），本地无账单，合作方还款id："+repayment.getRepayId()+";";
								sendSmsFlag = true;
							}else if(Constants.LN_REPAY_STATUS_INIT.equals(scheduleTemp.getStatus())){
								//七贷已还（包括代偿和逾期），本地INIT，短信告警；
								specialJnlNot = specialJnlNot + "七贷已还（包括代偿和逾期），本地INIT，planId："+scheduleTemp.getId()+";";
								sendSmsFlag = true;
							}else if(Constants.LN_REPAY_REPAIED.equals(scheduleTemp.getStatus())){
								//七贷已还（包括代偿和逾期），本地正常还款，校验金额，金额不一致，告警；
								if(scheduleTemp.getPlanTotal().compareTo(total) != 0 || scheduleTemp.getPrincipal().compareTo(principal) != 0
										|| scheduleTemp.getInterest().compareTo(interest) != 0 || scheduleTemp.getPrincipalOverdue().compareTo(principalOverdue) !=0
										|| scheduleTemp.getInterestOverdue().compareTo(interestOverdue) != 0){
									specialJnlNot = specialJnlNot + "七贷已还（包括代偿和逾期），本地已还（正常还款），金额不一致，"+scheduleTemp.getId()+";";
								}
							}else if(Constants.LN_REPAY_LATE_NOT.equals(scheduleTemp.getStatus())
									|| Constants.LN_REPAY_LATE_REPAIED.equals(scheduleTemp.getStatus())){
								//七贷已还（包括代偿和逾期），本地代偿和逾期，本金不一致，告警
								if(scheduleTemp.getPrincipal().compareTo(principal) != 0){
									specialJnlNot = specialJnlNot + "七贷已还（包括代偿和逾期），本地已还（代偿和逾期），本金不一致，"+scheduleTemp.getId()+";";
								}
							}
						}
					
					}

					// 七贷有账单，本地有账单，（本地有效账单比七贷多）。告警
					if(CollectionUtils.isNotEmpty(scheduleList) && CollectionUtils.isNotEmpty(loan7BillInfo.getRepayments())) {
						for (RepaySchedule4DetailVO repaySchedule4DetailVO : scheduleList) {
							if(Constants.LN_REPAY_REPAIED.equals(repaySchedule4DetailVO.getStatus()) || Constants.LN_REPAY_LATE_NOT.equals(repaySchedule4DetailVO.getStatus())
									|| Constants.LN_REPAY_LATE_REPAIED.equals(repaySchedule4DetailVO.getStatus())) {
								// 标识本地账单信息，七贷账单也存在，默认七贷账单不存在。
								boolean isNotExist = true;
								for (Loan7QueryBillRepayment repayment : loan7BillInfo.getRepayments()) {
									//对比账单编号是否一致，或则是非0的期数是否一致，若一致视为同笔账单
									if(repayment.getRepayId().equals(repaySchedule4DetailVO.getPartnerRepayId())
											|| (repayment.getRepaySerial().equals(repaySchedule4DetailVO.getSerialId()) 
												&& repaySchedule4DetailVO.getSerialId()!=0 ) ){
										// 标识七贷账单存在
										isNotExist = false;
                                        break;
									}
								}

								// 本地有效账单在七贷有缺失的情况，告警
								if (isNotExist) {
									// 本地有账单，七贷无账单，告警
									log.info(">>>七贷-随借随还-同步账单，本地有效账单比对，本地有账单，七贷无账单，入redis时间："+time+",告警时间："+now +"间隔"+DateUtil.getDiffeMinute(now, time));
									//告警，不入redis
									specialJnlNot = specialJnlNot + "本地有效账单比对，本地有账单，七贷无账单;";
									break;
								}
							}
						}
					}
				}
				if(!isRePush && StringUtil.isNotBlank(specialJnlNot)){
					//在没有重入redis的情况下，判断是否是短信告警标识，发送告警短信或只告警
					if(sendSmsFlag){
						warnSendSms(specialJnlNot, loanId);
					}else{
						warnNoSms(specialJnlNot, loanId);
					}
				}
				
				
			}
			
			log.info(">>>七贷-随借随还-同步账单定时结束<<<");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("还款处理轮询定时执行异常", e);
		}
		
	}

	/**
	 * 更新还款账单
	 * @author bianyatian
	 * @param id
	 * @param total
	 */
	private void updateSchedule(Integer id, Double total) {
		LnRepaySchedule schedule = new LnRepaySchedule();
		schedule.setId(id);
		schedule.setPlanTotal(total);
		schedule.setUpdateTime(new Date());
		lnRepayScheduleMapper.updateByPrimaryKeySelective(schedule);
	}
	
	/**
	 * 更新还款账单明细
	 * @author bianyatian
	 * @param id
	 * @param planAmount
	 */
	private void updateScheduleDetail(Integer id, Double planAmount) {
		LnRepayScheduleDetail scheduleDetail = new LnRepayScheduleDetail();
		scheduleDetail.setId(id);
		scheduleDetail.setPlanAmount(planAmount);
		scheduleDetail.setUpdateTime(new Date());
		lnRepayScheduleDetailMapper.updateByPrimaryKeySelective(scheduleDetail);
		
	}

	/**
	 * 新增账单
	 * @author bianyatian
	 * @param repayment
	 */
	private void addRepaySchedule(Loan7QueryBillRepayment repayment, Integer loanId) {
		LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
		lnRepaySchedule.setUpdateTime(new Date());
		lnRepaySchedule.setCreateTime(new Date());
		lnRepaySchedule.setLoanId(loanId);
		lnRepaySchedule.setPartnerRepayId(repayment.getRepayId());
		lnRepaySchedule.setPlanDate(repayment.getRepayDate());
		lnRepaySchedule.setPlanTotal(repayment.getTotal());
		lnRepaySchedule.setStatus(LoanStatus.REPAY_SCHEDULE_STATUS_INIT.getCode());
		lnRepaySchedule.setSerialId(repayment.getRepaySerial());

		lnRepayScheduleMapper.insertSelective(lnRepaySchedule);

		LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
		lnRepayScheduleDetail.setUpdateTime(new Date());
		lnRepayScheduleDetail.setCreateTime(new Date());
		lnRepayScheduleDetail.setPlanId(lnRepaySchedule.getId());
		lnRepayScheduleDetail.setPlanAmount(repayment.getPrincipal() != null ? repayment.getPrincipal() : 0d);
		lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
		lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

		lnRepayScheduleDetail.setPlanAmount(repayment.getInterest() != null ? repayment.getInterest() : 0d);
		lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
		lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

		lnRepayScheduleDetail.setPlanAmount(repayment.getPrincipalOverdue() != null ? repayment.getPrincipalOverdue() : 0d);
		lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
		lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

		lnRepayScheduleDetail.setPlanAmount(repayment.getInterestOverdue() != null ? repayment.getInterestOverdue() : 0d);
		lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
		lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
	}

	/**
	 * 发送告警短信
	 * @author bianyatian
	 * @param detail
	 * @param loanId
	 */
	private void warnSendSms(String detail, Integer loanId) {
		detail = "七贷账单同步："+detail;
		String type ="七贷账单同步，借款id:"+loanId;
		
        // 告警表插入
        BsSpecialJnl specialJnl = new BsSpecialJnl();
        specialJnl.setAmount(null);
        specialJnl.setDetail(detail);
        specialJnl.setOrderNo(null);
        specialJnl.setStatus(Constants.SPECIAL_JNL_STATUS_CREATE);
        specialJnl.setType(type);
        specialJnl.setOpTime(new Date());
        specialJnl.setUpdateTime(new Date());
        bsSpecialJnlMapper.insertSelective(specialJnl);
        // 短信
        if(detail.length()>100){
        	detail = detail.substring(0,100);
        }
        String message = detail; //+ "失败或异常，详情请检查告警表";
        smsService.sendWarnMobiles(message, false, Constants.EMERGENCY_MOBILE);
		//specialJnlService.warnDevelop4Fail(null, "七贷账单同步："+detail, null, type, false);
	}
	
	/**
	 * 无短信告警
	 * @author bianyatian
	 * @param detail
	 * @param loanId
	 */
	private void warnNoSms(String detail, Integer loanId) {
		specialJnlService.warn4FailNoSMS(null, "七贷账单同步："+detail, null, "七贷账单同步，借款id:"+loanId);
	}
}
