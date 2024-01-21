package com.pinting.business.service.manage.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.model.BillInfo;
import com.pinting.business.accounting.loan.service.DepFixedBillSyncService;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanExample;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.model.LnRepayScheduleExample;
import com.pinting.business.service.manage.YunDaiSelfBillService;

@Service
public class YunDaiSelfBillServiceImpl implements YunDaiSelfBillService {
	private Logger log = LoggerFactory.getLogger(YunDaiSelfBillServiceImpl.class);
	
	@Autowired
	private DepFixedBillSyncService depFixedBillSyncService;
	@Autowired
	private LnRepayScheduleMapper lnRepayScheduleMapper;
	@Autowired
	private LnLoanMapper lnLoanMapper;

	@Override
	public void syncBill(String partnerLoanId, String partnerUserId) {
			log.info("管理台同步账单-获取云贷账单开始 partnerUserId="+partnerUserId+ ";partnerLoanId=" +partnerLoanId);
			//获取云贷账单
			BillInfo billInfo = null;
			billInfo = depFixedBillSyncService.getNewestBill(partnerUserId, partnerLoanId);
			if (billInfo == null ) {
				throw new PTMessageException(PTMessageEnum.BILL_SYNC_INFO_EMPTY_ERROR);
			}
			//再次确认数据库中不存在账单
			LnLoanExample lnLoanExample = new LnLoanExample();
			lnLoanExample.createCriteria().andPartnerLoanIdEqualTo(partnerLoanId);
			List<LnLoan> list = lnLoanMapper.selectByExample(lnLoanExample);
			
			LnRepayScheduleExample example = new LnRepayScheduleExample();
			example.createCriteria().andLoanIdEqualTo(list.get(0).getId());
			List<LnRepaySchedule> repaySchedules = lnRepayScheduleMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(repaySchedules)) {
				throw new PTMessageException(PTMessageEnum.BILL_SYNC_INFO_EMPTY_ERROR);
			}
			log.info("管理台同步账单-获取云贷账单成功，保存账单");
			//云贷账单保存
			depFixedBillSyncService.manualLoanSyncBill(billInfo);
			log.info("管理台同步账单-账单保存成功");

	}

}
