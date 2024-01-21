package com.pinting.business.dayend.task.process;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.accounting.loan.service.LoanPaymentService;
import com.pinting.business.model.dto.LoanQueueDTO;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;

/**
 * 借款队列任务LoanQueueTask调用的线程
 * @author bianyatian
 * @2017-12-12 下午5:14:22
 */
public class LoanQueueProcess implements Runnable{

	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	private Logger log = LoggerFactory.getLogger(LoanQueueProcess.class);
	
	private LoanPaymentService loanPaymentService;
	private DepFixedLoanPaymentService depFixedLoanPaymentService;
	private LoanQueueDTO loanQueueDTO;
	
	public void setLoanPaymentService(LoanPaymentService loanPaymentService) {
		this.loanPaymentService = loanPaymentService;
	}

	public void setDepFixedLoanPaymentService(
			DepFixedLoanPaymentService depFixedLoanPaymentService) {
		this.depFixedLoanPaymentService = depFixedLoanPaymentService;
	}

	public void setLoanQueueDTO(LoanQueueDTO loanQueueDTO) {
		this.loanQueueDTO = loanQueueDTO;
	}

	@Override
	public void run() {
		//线程开始，存放redis数据
		Map<String, String> map = jsClientDaoSupport.getHashMapFromObj("loan_process");
		if (MapUtils.isEmpty(map) || map.size() == 0) {
			map = new HashMap<>();
		}
		map.put(loanQueueDTO.getLnLoan().getId().toString(), loanQueueDTO.getLnLoan().getId().toString());
		jsClientDaoSupport.addObjOfHashMap(map, "loan_process", -1);
		try {
			if(loanQueueDTO != null){
                //调用借款业务
                if (PartnerEnum.YUN_DAI_SELF.getCode().equals(loanQueueDTO.getChannel()) || 
                		PartnerEnum.SEVEN_DAI_SELF.getCode().equals(loanQueueDTO.getChannel())) {
                    depFixedLoanPaymentService.matchAndLoanPay(loanQueueDTO.getLnLoan(),loanQueueDTO.getLnBindCard(),loanQueueDTO.getChannel());
                } else if(PartnerEnum.ZSD.getCode().equals(loanQueueDTO.getChannel())) {
                    depFixedLoanPaymentService.matchAndLoanPay(loanQueueDTO.getLnLoan(),loanQueueDTO.getLnBindCard(),loanQueueDTO.getChannel());
                } else {
                    loanPaymentService.matchAndLoanPay(loanQueueDTO.getLnLoan(), loanQueueDTO.getLnBindCard(), loanQueueDTO.getChannel());
                }
            }else{
                log.info(">>>借款队列lpop为空<<<");
            }
		} catch (Exception e) {
			log.error("借款队列线程执行异常", e);
		}finally{
			//删除redis数据
			jsClientDaoSupport.deleteObjOfHashMap(loanQueueDTO.getLnLoan().getId().toString(),"loan_process");
		}
	}

}
