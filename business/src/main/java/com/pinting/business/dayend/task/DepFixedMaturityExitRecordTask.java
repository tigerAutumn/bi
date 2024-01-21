package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.service.DepQuitApplyService;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedLoanRelationshipService;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsDepositionQuitApply;
import com.pinting.business.model.vo.DepFixedMaturityExitVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 
 * @project business
 * @title DepFixedMaturityExitRecordTask.java
 * @author Dragon & cat
 * @date 2017-4-5
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 固定期限产品到期退出统计退出数据（存管系统云贷自主放款、赞时贷、7贷）
 * 
 */
@Service
public class DepFixedMaturityExitRecordTask {
	private Logger log = LoggerFactory.getLogger(DepFixedMaturityExitRecordTask.class);
	
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private DepQuitApplyService depQuitApplyService; 
	@Autowired
	private  DepFixedLoanRelationshipService  depFixedLoanRelationshipService;
	@Autowired
	private SpecialJnlService specialJnlService;
	
	/**
	 * 任务执行
	 */
	public void execute() {
		log.info("==================定时任务【固定期限产品到期退出统计退出数据】开始====================");
		depFixedMaturityExit();
	}
	
	/**
	 * 1、统计自然退出的理财
	 * 2、调用申请退出记录服务，等待债转退出服务
	 */
	private void depFixedMaturityExit() {
		//1、统计自然退出的理财，投资超过最长期限并且状态为投资中的账户
		try {
			//查询存管VIP理财人，查询的列表需要排除VIP理财人(所有资产端)账户
			List<Integer> vipUserList = depFixedLoanRelationshipService.getDepVIPUserList(VIPId4PartnerEnum.YUN_DAI_SELF.getVipIdKey());
			List<Integer> vipZsdUserList = depFixedLoanRelationshipService.getDepVIPUserList(VIPId4PartnerEnum.ZSD.getVipIdKey());
			List<Integer> vip7UserList = depFixedLoanRelationshipService.getDepVIPUserList(VIPId4PartnerEnum.SEVEN_DAI_SELF.getVipIdKey());
			List<Integer> vipFreeUserList = depFixedLoanRelationshipService.getDepVIPUserList(VIPId4PartnerEnum.FREE.getVipIdKey());

			if(!CollectionUtils.isEmpty(vipUserList) && !CollectionUtils.isEmpty(vipZsdUserList) && !CollectionUtils.isEmpty(vip7UserList)){
                vipUserList.addAll(vipZsdUserList);
				vipUserList.addAll(vip7UserList);
				vipUserList.addAll(vipFreeUserList);
            } else {
				specialJnlService.warn4Fail(null,"【未配置vip理财人】固定期限产品到期退出统计退出数据",null,"未配置云贷、赞时贷、七贷vip、Free资金理财人",true);
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,",未配置vip理财人");
			}

            List<DepFixedMaturityExitVO> list =  bsSubAccountMapper.queryDepfixedMaturityExitInfo(vipUserList);

			if (!CollectionUtils.isEmpty(list)) {
				//2、调用理财申请退出记录服务，等待债转服务
				for (DepFixedMaturityExitVO depFixedMaturityExitVO : list) {
					log.info("==================定时任务【固定期限产品到期退出统计退出数据】：本金："+depFixedMaturityExitVO.getOpenBalance()+"====================");
					BsDepositionQuitApply  bsDepositionQuitApply = new BsDepositionQuitApply();
					bsDepositionQuitApply.setUserId(depFixedMaturityExitVO.getUserId());
					bsDepositionQuitApply.setSubAccountId(depFixedMaturityExitVO.getId());
					bsDepositionQuitApply.setPrincipal(depFixedMaturityExitVO.getOpenBalance());
					bsDepositionQuitApply.setFinalInterest(null);
					bsDepositionQuitApply.setExitType(Constants.DEP_QUIT_APPLY_SYS);
					bsDepositionQuitApply.setStatus(Constants.DEP_QUIT_APPLY_PASS);
					bsDepositionQuitApply.setOperateTime(new Date());
					bsDepositionQuitApply.setPlanDate(DateUtil.addDays(new Date(), 1));
					bsDepositionQuitApply.setmUserId(null);
					bsDepositionQuitApply.setCreateTime(new Date());
					bsDepositionQuitApply.setUpdateTime(new Date());
					depQuitApplyService.addDepositionQuitApply(bsDepositionQuitApply);
				}

			}else {
				log.info("==================定时任务【固定期限产品到期退出统计退出数据】：当日无自然退出数据====================");
			}
		
			
		} catch (Exception e) {
			log.error("==================定时任务【固定期限产品到期退出统计退出数据】失败====================", e);
		}
		
		
		
	}
}
