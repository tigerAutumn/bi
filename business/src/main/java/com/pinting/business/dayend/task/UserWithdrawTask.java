package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.service.DepUserBalanceWithdrawService;
import com.pinting.business.dao.BsHolidayMapper;
import com.pinting.business.dao.BsWithdrawCheckMapper;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.hessian.site.message.ResMsg_UserBalance_Withdraw;
import com.pinting.business.model.BsHoliday;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsWithdrawCheck;
import com.pinting.business.model.BsWithdrawCheckExample;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 针对存管出金方案，用户提现审核通过后的提现操作
 * 提现限额WITHDRAW_APPLY_LIMIT50000，若需修改，需要确保队列数据走完。
 * 限额由小改大时，会导致，原限额以上的提现（不需要清算，工作日提现）的数据变成限额以下，需要清算，因清算标识不符合，造成提现数据无法继续。
 * @author bianyatian
 * @2017-5-9 上午11:53:03
 */
@Service
public class UserWithdrawTask {

	private Logger logger = LoggerFactory.getLogger(UserWithdrawTask.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

	@Autowired
	private BsWithdrawCheckMapper bsWithdrawCheckMapper;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	@Autowired
	private BsHolidayMapper bsHolidayMapper;
	@Autowired
	private DepUserBalanceWithdrawService depUserBalanceWithdrawService;
	@Autowired
	private SpecialJnlService specialJnlService;

	public void execute() {
		/**
		 * 1、查询提现审核列表，且状态为通过审核进入队列的
		 * 2、轮询列表，根据主键查询判断状态是否WITHDRAW_PASS_QUE，是则continue；
		 * 		否则修改状态为WITHDRAW_PASS_PROCESS，进行2
		 * 3、判断是否是赞分期的代偿人，是则判断是否是工作日，且时间在(9:00, 16:30)，走大额系统
		 * 4、非赞分期代偿人，判断提现金额D，和清算标记执行对应判断，是否执行操作
		 * 		4.1 D>50000，需要T+1日（申请时间的后一日）清算，工作日，且时间区间(9:00, 16:30)，走大额系统
		 * 		4.2 D>50000，需要T日（申请时间）清算，工作日，且时间区间(9:00, 16:30)，走大额系统
		 * 		4.3 D<=50000，需要T+1日（申请时间的后一日）清算，走超级网银
		 *		4.4 D<=50000，需要T日（申请时间）清算，走超级网银
		 * 5、3和4中，不执行操作则修改状态为WITHDRAW_PASS_QUE
		 * 6、调redis中的清算标识，判断当日是否清算，若当前时间【超过9点半，并在10点30以内】还没有清算，则发送短信告警
		 */
		logger.info("=================【用户提现审核通过后的提现轮询】===================================");
		try {
			String todayDateStr = DateUtil.formatYYYYMMDD(new Date());
			String lastCheckDate = jsClientDaoSupport.getString(Constants.LIQUIDATION_FLAG); //最后一次清算的日期
			logger.info("=================【用户提现轮询】最后一次清算通知时间:"+lastCheckDate+"===================================");
			//如果清算时间小于当日，且已经超过十点零一，并在10点10分以内，则告警
			if(StringUtils.isBlank(lastCheckDate) || DateUtil.parseDate(lastCheckDate).compareTo(DateUtil.parseDate(todayDateStr)) < 0){
				Date lateTimeStart = DateUtil.parseDateTime(DateUtil.formatYYYYMMDD(new Date()) + " 10:01:00");
				Date lateTimeEnd = DateUtil.parseDateTime(DateUtil.formatYYYYMMDD(new Date()) + " 10:10:00");
				if(lateTimeStart.compareTo(new Date()) <= 0 && lateTimeEnd.compareTo(new Date()) > 0){
					//告警
					specialJnlService.warn4Fail(0d, "恒丰清算人工通知","", "【恒丰清算人工通知未获得】",false);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.info("=================【检查清算人工通知标志异常】==================================="+e.getMessage());
		}
		// 币港湾理财人提现申请金额限制（用于判断是否审核提现） 50000
		BsSysConfig withdrawApplyLimitConfig = bsSysConfigService.findKey(Constants.WITHDRAW_APPLY_LIMIT);
		Double withdrawApplyLimit = withdrawApplyLimitConfig == null ? 50000d : Double.valueOf(withdrawApplyLimitConfig.getConfValue());

		BsWithdrawCheckExample bsWithdrawCheckExample = new BsWithdrawCheckExample();
		bsWithdrawCheckExample.createCriteria().andStatusEqualTo(Constants.WITHDRAW_PASS_QUE);
		List<BsWithdrawCheck> bsWithdrawChecksList = bsWithdrawCheckMapper.selectByExample(bsWithdrawCheckExample);
		if(CollectionUtils.isNotEmpty(bsWithdrawChecksList)){
			for (BsWithdrawCheck bsWithdrawCheck : bsWithdrawChecksList) {
				if(!update2Process(bsWithdrawCheck)){
					continue;
				}
				Double applyAmount = bsWithdrawCheck.getAmount();//申请提现金额
				if(depUserBalanceWithdrawService.isCompensatoryUserZAN(bsWithdrawCheck.getUserId())) {
					// 一、赞分期代偿人提现
					if(checkWorkDayTime()){
						logger.info("=================【用户提现轮询】 checkId:"+bsWithdrawCheck.getId()+"userId:"+bsWithdrawCheck.getUserId()+",在工作时间，提现===================================");
						ReqMsg_UserBalance_Withdraw reqMsg = new ReqMsg_UserBalance_Withdraw();
						ResMsg_UserBalance_Withdraw resMsg = new ResMsg_UserBalance_Withdraw();
						reqMsg.setUserId(bsWithdrawCheck.getUserId());
						reqMsg.setManageId(bsWithdrawCheck.getmUserId());
						reqMsg.setCheckId(String.valueOf(bsWithdrawCheck.getId()));
						reqMsg.setAmount(bsWithdrawCheck.getAmount());
						reqMsg.setWithdrawType(Constants.HFBANK_WITHDRAW_TYPE_FINANCE);
						try {
							depUserBalanceWithdrawService.compensatorApply(reqMsg, resMsg);
						} catch (Exception e) {
							logger.info("=================【用户提现轮询】checkId:"+bsWithdrawCheck.getId()+"调用depUserBalanceWithdrawService.compensatorApply异常===================================");
							update2PassQue(bsWithdrawCheck.getId());
							e.printStackTrace();
						}
					}else{
						//修改状态为WITHDRAW_PASS_QUE
						logger.info("=================【用户提现轮询】 checkId:"+bsWithdrawCheck.getId()+"userId:"+bsWithdrawCheck.getUserId()+",非工作时间，不可提现===================================");
						update2PassQue(bsWithdrawCheck.getId());

					}
				} else {
					boolean meetCondition = false;
					// 二、普通理财人提现
					if(applyAmount.compareTo(withdrawApplyLimit) > 0
							&& (Constants.WITHDRAW_CLEAR_MARK_YES_T_ADD_1.equals(bsWithdrawCheck.getClearingMark())
							|| Constants.WITHDRAW_CLEAR_MARK_YES_T.equals(bsWithdrawCheck.getClearingMark()))) {
						meetCondition = true;
						// 4.1 D>50000，需要T+1日（申请时间的后一日）清算，工作日，且时间区间(9:00, 16:30)，走大额系统
						// 4.2 D>50000，需要T  日（申请时间）       清算，工作日，且时间区间(9:00, 16:30)，走大额系统
						Date withdrawApplyDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(bsWithdrawCheck.getCreateTime()));
						boolean clearFlag = getClearFlagByDate(withdrawApplyDate, bsWithdrawCheck.getClearingMark());//申请当日的出入金已清算
						if(clearFlag && checkWorkDayTime()) {
							logger.info("=================【用户提现轮询】 checkId:"+bsWithdrawCheck.getId()+"userId:"+bsWithdrawCheck.getUserId()+",在工作时间且已清算，提现===================================");
							ReqMsg_UserBalance_Withdraw reqMsg = new ReqMsg_UserBalance_Withdraw();
							ResMsg_UserBalance_Withdraw resMsg = new ResMsg_UserBalance_Withdraw();
							reqMsg.setUserId(bsWithdrawCheck.getUserId());
							reqMsg.setManageId(bsWithdrawCheck.getmUserId());
							reqMsg.setCheckId(String.valueOf(bsWithdrawCheck.getId()));
							reqMsg.setAmount(bsWithdrawCheck.getAmount());
							try {
								depUserBalanceWithdrawService.checkPass(reqMsg, resMsg, Constants.WITHDRAW_PASS_QUE_SUCC);
							} catch (Exception e) {
								logger.info("=================【用户提现轮询】checkId:"+bsWithdrawCheck.getId()+"调用depUserBalanceWithdrawService.checkPass异常===================================");
								update2PassQue(bsWithdrawCheck.getId());
								e.printStackTrace();
							}
						} else {
							//修改状态为WITHDRAW_PASS_QUE
							logger.info("=================【用户提现轮询】 checkId:"+bsWithdrawCheck.getId()+"userId:"+bsWithdrawCheck.getUserId()+",非工作时间或者未清算，不可提现===================================");
							update2PassQue(bsWithdrawCheck.getId());
						}
					}
					if(applyAmount.compareTo(withdrawApplyLimit) <= 0
							&& (Constants.WITHDRAW_CLEAR_MARK_YES_T_ADD_1.equals(bsWithdrawCheck.getClearingMark())
							|| Constants.WITHDRAW_CLEAR_MARK_YES_T.equals(bsWithdrawCheck.getClearingMark()))) {
						meetCondition = true;
						// 4.3 D<=50000，需要T+1日（申请时间的后一日）清算，走超级网银
						// 4.4 D<=50000，需要T  日（申请时间）		  清算，走超级网银
						Date withdrawApplyDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(bsWithdrawCheck.getCreateTime()));
						boolean clearFlag = getClearFlagByDate(withdrawApplyDate, bsWithdrawCheck.getClearingMark());
						if(clearFlag) {
							logger.info("=================【用户提现轮询】 checkId:"+bsWithdrawCheck.getId()+"userId:"+bsWithdrawCheck.getUserId()+",已清算，提现===================================");
							ReqMsg_UserBalance_Withdraw reqMsg = new ReqMsg_UserBalance_Withdraw();
							ResMsg_UserBalance_Withdraw resMsg = new ResMsg_UserBalance_Withdraw();
							reqMsg.setUserId(bsWithdrawCheck.getUserId());
							reqMsg.setManageId(bsWithdrawCheck.getmUserId());
							reqMsg.setCheckId(String.valueOf(bsWithdrawCheck.getId()));
							reqMsg.setAmount(bsWithdrawCheck.getAmount());
							try {
								depUserBalanceWithdrawService.checkPass(reqMsg, resMsg, Constants.WITHDRAW_PASS_QUE_SUCC);
							} catch (Exception e) {
								logger.info("=================【用户提现轮询】checkId:"+bsWithdrawCheck.getId()+"调用depUserBalanceWithdrawService.checkPass异常===================================");
								update2PassQue(bsWithdrawCheck.getId());
								e.printStackTrace();
							}

						}else{
							//修改状态为WITHDRAW_PASS_QUE
							logger.info("=================【用户提现轮询】checkId:"+bsWithdrawCheck.getId()+" userId:"+bsWithdrawCheck.getUserId()+",清算标识："+clearFlag+"不可提现===================================");
							update2PassQue(bsWithdrawCheck.getId());
						}
					}
					if(!meetCondition) {
						logger.info("=================【用户提现轮询】审核数据不满足条件：{}", JSONObject.fromObject(bsWithdrawCheck));
					}
				}
			}
		}

	}
	private boolean update2Process(BsWithdrawCheck bsWithdrawCheck) {
		logger.info("将审核数据 {} 的状态更新成PASS_PROCESS开始", bsWithdrawCheck.getId());
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_PASS_QUE.getKey());
			BsWithdrawCheck thisWithdrawCheck = bsWithdrawCheckMapper.selectByPrimaryKey(bsWithdrawCheck.getId());
			logger.info("审核数据 {} list时的状态：{}，根据ID查出来的状态：{}", bsWithdrawCheck.getId(), bsWithdrawCheck.getStatus(), thisWithdrawCheck.getStatus());
			if(!Constants.WITHDRAW_PASS_QUE.equals(thisWithdrawCheck.getStatus())){
				logger.info("审核数据 {} 已经被改成了PASS_PROCESS开始，无需再次更新，返回false", bsWithdrawCheck.getId());
				return false;
			}
			//修改状态为WITHDRAW_PASS_PROCESS
			BsWithdrawCheck widtCheckTemp_process = new BsWithdrawCheck();
			widtCheckTemp_process.setId(bsWithdrawCheck.getId());
			widtCheckTemp_process.setStatus(Constants.WITHDRAW_PASS_PROCESS);
			bsWithdrawCheckMapper.updateByPrimaryKeySelective(widtCheckTemp_process);
			logger.info("将审核数据 {} 的状态更新成PASS_PROCESS成功", bsWithdrawCheck.getId());
			return true;
		}finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_PASS_QUE.getKey());
		}
	}

	/**
	 * 判断申请当日的出入金已清算
	 * @param date		提现的申请时间
	 * @param clearType	清算类型（T+1日还是T日）
	 * @return
	 */
	private boolean getClearFlagByDate(Date date, String clearType) {
		String todayDateStr = DateUtil.formatYYYYMMDD(new Date());
		String selDateStr = null;
		if(Constants.WITHDRAW_CLEAR_MARK_YES_T_ADD_1.equals(clearType)) {
			// 提交日后一日（T+1日）
			selDateStr = DateUtil.formatYYYYMMDD(DateUtil.addDays(date, 1));
		} else if(Constants.WITHDRAW_CLEAR_MARK_YES_T.equals(clearType)) {
			// 提交日（T日）
			selDateStr = DateUtil.formatYYYYMMDD(date);
		}

		//判断提交日后一日是否早于今日，是则直接返回已清算
		if(DateUtil.parseDate(selDateStr).compareTo(DateUtil.parseDate(todayDateStr)) < 0) {
			logger.info("是否已清算：{}", true);
			return true;
		}
		String lastCheckDate = "";
		try {
			lastCheckDate = jsClientDaoSupport.getString(Constants.LIQUIDATION_FLAG); //最后一次清算的日期
			logger.info("=================【用户提现轮询】最后一次清算通知时间:"+lastCheckDate+"===================================");
			//如果清算时间小于当日，且已经超过9点半，并在10点30以内，则告警
			if(StringUtils.isBlank(lastCheckDate) || DateUtil.parseDate(lastCheckDate).compareTo(DateUtil.parseDate(todayDateStr)) < 0){
				Date lateTimeStart = DateUtil.parseDateTime(DateUtil.formatYYYYMMDD(new Date()) + " 9:30:00");
				Date lateTimeEnd = DateUtil.parseDateTime(DateUtil.formatYYYYMMDD(new Date()) + " 10:30:00");
				if(lateTimeStart.compareTo(new Date()) <= 0 && lateTimeEnd.compareTo(new Date()) > 0){
					//告警
					//specialJnlService.warn4FailNoSMS(0d, "清算结果未取得", "", "【清算状态通知未获得】");
					logger.warn("【用户提现轮询】清算结果未取得");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(selDateStr.equals(lastCheckDate)) {
			logger.info("是否已清算：{}", true);
			return true;
		} else {
			logger.info("是否已清算：{}", false);
			return false;
		}
	}

	//修改状态为WITHDRAW_PASS_QUE
	private void update2PassQue(Integer id) {
		logger.info("将审核数据 {} 的状态更新成PASS_QUE", id);
		BsWithdrawCheck widtCheckTemp_que = new BsWithdrawCheck();
		widtCheckTemp_que.setId(id);
		widtCheckTemp_que.setStatus(Constants.WITHDRAW_PASS_QUE);
		bsWithdrawCheckMapper.updateByPrimaryKeySelective(widtCheckTemp_que);

	}

	//判断是否是工作日且时间在9:00--16:30之间
	private boolean checkWorkDayTime() {
		logger.info("判断是否是工作日且时间在9:00--16:30之间");
		BsHoliday holiday = bsHolidayMapper.todayIsHolidayOrNot();
		if(holiday == null){
			Date nowtime = new Date();
			String nowDateStr = DateUtil.formatYYYYMMDD(nowtime);
			Date startTime = DateUtil.parseDateTime(nowDateStr + " 9:00:00");
			Date endTime = DateUtil.parseDateTime(nowDateStr + " 16:30:00");

			// 测试 需要还原
			// Date endTime = DateUtil.parseDateTime(nowDateStr + " 17:30:00");
			if(nowtime.compareTo(startTime) > 0 && nowtime.compareTo(endTime) < 0){
				logger.info("是否在工作日：{}，且时间在 {} 和 {} 之间", true, startTime, endTime);
				return true;
			}else{
				logger.info("是否在工作日：{}，且时间在：{} 和 {} 之间", false, startTime, endTime);
				return false;
			}
		}else{
			logger.info("是否在工作日：{}", false);
			return false;
		}
	}
}
