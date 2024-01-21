package com.pinting.business.accounting.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.service.RecordAccountingService;
import com.pinting.business.accounting.service.impl.process.BaseAccountingProcess;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.util.Util;

/**
 * 记账服务实现类（待定）
 * @Project: business
 * @Title: RecordAccountingServiceImpl.java
 * @author dingpf
 * @date 2015-1-21 下午6:34:03
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class RecordAccountingServiceImpl implements RecordAccountingService,ApplicationContextAware {
	protected static ApplicationContext applicationContext;
	
	@Override
	/**
	 * 根据交易码，进行对应交易前置记账
	 */
	public Integer preRecordAccounting(BsAccountJnl accountJnl) {
		//传入数据校验
//		preRecordAccountingDataCheck(accountJnl);
		//根据交易码查找对应process进行前置记账
		BaseAccountingProcess process = (BaseAccountingProcess) applicationContext.getBean(Util.changeTransCode(accountJnl.getTransCode()) + "RecordAccountingProcess");
		Integer jnlId = process.preRecordAccountingExecute(accountJnl);
		
		return jnlId;
	}
	
	@Override
	/**
	 * 根据原流水号，进行对应交易后置记账
	 */
	public void postfRecordAccounting(BsAccountJnl accountJnl) {
		//传入数据校验
//		postfRecordAccountingDataCheck(accountJnl);
		//根据交易码查找对应process进行前置记账
		BaseAccountingProcess process = (BaseAccountingProcess) applicationContext.getBean(Util.changeTransCode(accountJnl.getTransCode()) + "RecordAccountingProcess");
		process.postfRecordAccountingExecute(accountJnl);
	}

	private void preRecordAccountingDataCheck(BsAccountJnl accountJnl) {
		if(accountJnl.getTransTime() == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "transTime");
		}
		if(accountJnl.getTransCode() == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "transCode");
		}
		if(accountJnl.getTransAmount() == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "transAmount");
		}
		if(accountJnl.getUserId1() == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "userId1");
		}
		/*if(subAccountCode2 == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "subAccountCode2");
		}*/
		
	}
	
	private void postfRecordAccountingDataCheck(BsAccountJnl accountJnl) {
		if(accountJnl.getRelativeJnl() == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "relativeJnlId");
		}
		if(accountJnl.getTransTime() == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "transTime");
		}
		if(accountJnl.getTransCode() == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "transCode");
		}
		if(accountJnl.getStatus() == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "status");
		}
		if(accountJnl.getTransAmount() == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "transAmount");
		}
		if(accountJnl.getUserId1() == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "userId1");
		}
		if(accountJnl.getRespCode() == null){
			throw new PTMessageException(PTMessageEnum.RECORD_ACCOUNTING_DATA_ERROR, "respCode");
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		applicationContext = context;
	}

}
