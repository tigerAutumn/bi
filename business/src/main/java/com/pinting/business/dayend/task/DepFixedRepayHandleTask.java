package com.pinting.business.dayend.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.coreflow.core.service.DispatcherService;
import com.pinting.business.dao.*;
import com.pinting.business.dayend.task.process.RepayHandleProcess;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class DepFixedRepayHandleTask {

	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	private Logger log = LoggerFactory.getLogger(DepFixedRepayHandleTask.class);

	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private DepFixedRepayPaymentService depFixedRepayPaymentService;
	@Autowired
	private DispatcherService dispatchService;

	@Autowired
	@Qualifier("repayHandleExecutor")
	private ThreadPoolTaskExecutor repayHandleExecutor;

	public void execute() {
		try {
			checkRepayBillStatus();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("还款处理轮询定时执行异常", e);
		}
	}

	private void checkRepayBillStatus() {
		log.info(">>>还款处理轮询定时开始<<<");
		Long len = jsClientDaoSupport.llen("repayBill");
		BsSysConfig configPro = sysConfigService.findConfigByKey(Constants.DK_REPAY_HANDLE_MAX_NUM);
		Integer max_num = 15;
		if(configPro != null){
			max_num = Integer.parseInt(configPro.getConfValue());
		}
		int n = 0;//该次定时允许开启定时数
		log.info(">>>redis正在处理中的还款账单数量" + len + "<<<");
		if(len == null){
			return;
		}else if(len >= max_num){
			n = max_num;
		}else if(len < max_num){
			n = len.intValue();
		}
		try {
			for (int i = 0; i < n; i++) {
				String repayBillItem = jsClientDaoSupport.lpop("repayBill");
				if(StringUtil.isEmpty(repayBillItem)){
					log.warn("redis数据为空");
					return;
				}
				JSONObject json = JSON.parseObject(repayBillItem);
				log.info(">>>正在处理redis数据" + json.toJSONString() + "<<<");
				
				Integer lnPayOrderId = json.getInteger("lnPayOrderId");
				Integer loanId = json.getInteger("loanId");
				if( lnPayOrderId == null || loanId == null ) {
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",redis中数据校验失败");
				}
				LnPayOrders lnPayOrder = lnPayOrdersMapper.selectByPrimaryKey(lnPayOrderId);
				LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(loanId);
				PartnerEnum partner = PartnerEnum.getEnumByCode(lnPayOrder.getPartnerCode());
				
				try {
					RepayHandleProcess repayHandleProcess = new RepayHandleProcess();
					repayHandleProcess.setPartnerEnum(partner);
					repayHandleProcess.setRepayBillJson(json);
					repayHandleProcess.setLnPayOrder(lnPayOrder);
					repayHandleProcess.setLnLoan(lnLoan);
					repayHandleProcess.setDepFixedRepayPaymentService(depFixedRepayPaymentService);
					repayHandleProcess.setDispatchService(dispatchService);
					repayHandleExecutor.execute(repayHandleProcess);
				} catch (Exception e) {
					log.info(">>>还款处理轮询redis中数据处理错误：{}，并重新放入redis：{}<<<", e.getMessage(), JSON.toJSONString(json));
					specialJnlService.warn4Fail(0d,"还款处理轮询时处理partnerLoanId=:" + json.getString("partnerLoanId") + "对应redis数据错误",
							json.getString("partnerLoanId"), "对应redis数据错误",false);
					jsClientDaoSupport.rpush("repayBill", JSON.toJSONString(json));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(">>>还款处理轮询定时执行异常：{}<<<", e.getMessage());
		}
		log.info(">>>还款处理轮询定时结束<<<");
	}}
