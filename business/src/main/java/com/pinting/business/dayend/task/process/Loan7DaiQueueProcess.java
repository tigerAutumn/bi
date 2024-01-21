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
 * 借款队列任务Loan7DaiQueueTask调用的线程
 * @author bianyatian
 * @2018-1-24 上午11:20:46
 */
public class Loan7DaiQueueProcess implements Runnable{

	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	private Logger log = LoggerFactory.getLogger(Loan7DaiQueueProcess.class);
	
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
		Map<String, String> map = jsClientDaoSupport.getHashMapFromObj("loan_7dai_process");
		if (MapUtils.isEmpty(map) || map.size() == 0) {
			map = new HashMap<>();
		}
		map.put(loanQueueDTO.getLnLoan().getId().toString(), loanQueueDTO.getLnLoan().getId().toString());
		jsClientDaoSupport.addObjOfHashMap(map, "loan_7dai_process", -1);
		try {
			if(loanQueueDTO != null){
                //调用借款业务
                if (PartnerEnum.SEVEN_DAI_SELF.getCode().equals(loanQueueDTO.getChannel())) {
                    depFixedLoanPaymentService.matchAndLoanPay(loanQueueDTO.getLnLoan(),loanQueueDTO.getLnBindCard(),loanQueueDTO.getChannel());
                }else{
                	//不是7贷的
                	log.error(loanQueueDTO.getLnLoan().getId()+"7贷借款队列中，数据合作方不为7贷");
                }
            }else{
                log.info(">>>7贷借款队列lpop为空<<<");
            }
		} catch (Exception e) {
			log.error("7贷借款队列线程执行异常", e);
		}finally{
			//删除redis数据
			jsClientDaoSupport.deleteObjOfHashMap(loanQueueDTO.getLnLoan().getId().toString(),"loan_7dai_process");
		}
	}

}
