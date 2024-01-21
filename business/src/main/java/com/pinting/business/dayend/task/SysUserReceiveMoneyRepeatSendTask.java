package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.service.UserReturnMoneyService;
import com.pinting.business.dao.BsBatchBuyDetailMapper;
import com.pinting.business.dao.BsBatchReturnDetailMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.LnFinanceRepayScheduleMapper;
import com.pinting.business.model.*;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.StringUtil;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 扫描用户待回款产品明细表，失败的需重新发起回款
 * @Project: business
 * @Title: SysUserReceiveMoneyRepeatSendTask.java
 * @author dingpf
 * @date 2015-11-23 下午1:21:42
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class SysUserReceiveMoneyRepeatSendTask {
	private Logger log = LoggerFactory.getLogger(SysUserReceiveMoneyRepeatSendTask.class);
	@Autowired
	private BsBatchBuyDetailMapper bsBatchBuyDetailMapper;
	@Autowired
	private BsBatchReturnDetailMapper bsBatchReturnDetailMapper;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private UserReturnMoneyService userReturnMoneyService;
	@Autowired
	private UserService userService;
	@Autowired
	private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;

	/**
	 * 任务执行
	 */
	public void execute() {
		// 定时任务{用户回款重发}
		//userReceiveMoneyRepeatSend();
		userReceiveMoneyRepeatSendNew();
	}
	
	private void userReceiveMoneyRepeatSendNew() {
		log.info("=========定时任务{用户回款3.10之前}开始=========");
		try {
			//根据回款计划表，对回款失败记录进行重发
			log.info("========={用户回款重发}根据回款结算计划表，对回款失败记录进行重发=========");
			BsBatchReturnDetailExample returnDetailExample = new BsBatchReturnDetailExample();
			returnDetailExample.createCriteria().andReturnStatusEqualTo(Constants.RETURN_STATUS_NOT);
			List<BsBatchReturnDetail> returnDetails = bsBatchReturnDetailMapper.selectByExample(returnDetailExample);
			if(returnDetails!=null && returnDetails.size()>0){
				Date now = new Date();
				log.info("========={用户回款3.10之前}找到["+returnDetails.size()+"]笔回款失败记录，进行回款（3.10之前）=========");
				for (BsBatchReturnDetail batchReturnDetail : returnDetails) {
					BsSubAccount subAccount = bsSubAccountMapper.selectByPrimaryKey(batchReturnDetail.getSubAccountId());
					if(subAccount.getLastFinishInterestDate().compareTo(now) <= 0){
						//理财回款日已过
						//确认标的还款是否成功，LnFinanceRepaySchedule的状态为已还，则回款到余额
						List<LnFinanceRepaySchedule> finList = lnFinanceRepayScheduleMapper.selectBySubAccount(batchReturnDetail.getSubAccountId());
						if(CollectionUtils.isNotEmpty(finList) 
								&& Constants.FINANCE_REPAY_SATAE_REPAIED.equals(finList.get(0).getStatus())){
							userReturnMoneyService.return2Balance4Before(batchReturnDetail);
						}
					}
					
				}
			}else{
				log.info("=========定时任务{用户回款重发}无需重发=========");
			}
			log.info("=========定时任务{用户回款重发}结束=========");
		} catch (Exception e) {
			log.error("=========定时任务{用户回款重发}失败=========", e);
			//告警
			specialJnlService.warn4Fail(null, "定时任务{用户回款重发}用户回款重发异常："+StringUtil.left(e.getMessage(), 20),
					null,"用户回款重发",true);
		}
	}

	private void userReceiveMoneyRepeatSend(){
		log.info("=========定时任务{用户回款重发}开始=========");
		try {
			//查询需同步到回款结算计划表的用户回款
			BsBatchBuyDetailExample example = new BsBatchBuyDetailExample();
			example.createCriteria().andReturnStatusEqualTo(Constants.RETURN_STATUS_FAIL);
			final List<BsBatchBuyDetail> batchBuyDetails = bsBatchBuyDetailMapper.selectByExample(example);
			if(batchBuyDetails!=null && batchBuyDetails.size()>0){
				log.info("========={用户回款重发}找到["+batchBuyDetails.size()+"]笔产品未同步到回款结算计划表=========");
				userReturnMoneyService.generatePlans(batchBuyDetails);
			}
			
			//根据回款计划表，对回款失败记录进行重发
			log.info("========={用户回款重发}根据回款结算计划表，对回款失败记录进行重发=========");
			BsBatchReturnDetailExample returnDetailExample = new BsBatchReturnDetailExample();
			returnDetailExample.createCriteria().andReturnStatusEqualTo(Constants.RETURN_STATUS_FAIL);
			List<BsBatchReturnDetail> returnDetails = bsBatchReturnDetailMapper.selectByExample(returnDetailExample);
			if(returnDetails!=null && returnDetails.size()>0){
				log.info("========={用户回款重发}找到["+returnDetails.size()+"]笔回款失败记录，进行回款重发=========");
				for (BsBatchReturnDetail batchReturnDetail : returnDetails) {
					BsUser fnUser = userService.findUserById(batchReturnDetail.getUserId());
					Integer returnPath = fnUser.getReturnPath();
					if(Constants.RETURN_PATH_BALANCE == returnPath){
						userReturnMoneyService.return2Balance(batchReturnDetail);
					}else if(Constants.RETURN_PATH_BANKCARD == returnPath){
						userReturnMoneyService.return2Card(batchReturnDetail);
					}
				}
			}else{
				log.info("=========定时任务{用户回款重发}无需重发=========");
			}
			log.info("=========定时任务{用户回款重发}结束=========");
		} catch (Exception e) {
			log.error("=========定时任务{用户回款重发}失败=========", e);
			//告警
			specialJnlService.warn4Fail(null, "定时任务{用户回款重发}用户回款重发异常："+StringUtil.left(e.getMessage(), 20),
					null,"用户回款重发",true);
		}
	}

}
