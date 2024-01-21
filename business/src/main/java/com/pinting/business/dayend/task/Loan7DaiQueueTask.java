package com.pinting.business.dayend.task;

import java.util.concurrent.RejectedExecutionException;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.accounting.loan.service.LoanPaymentService;
import com.pinting.business.dayend.task.process.LoanQueueProcess;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.dto.LoanQueueDTO;
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
/**
 * 七贷借款队列处理定时
 * @author bianyatian
 * @2018-1-24 上午11:18:29
 */
@Service
public class Loan7DaiQueueTask {

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private Logger log = LoggerFactory.getLogger(Loan7DaiQueueTask.class);

    @Autowired
    private LoanPaymentService loanPaymentService;

    @Autowired
    private DepFixedLoanPaymentService depFixedLoanPaymentService;
    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    @Qualifier("loan7DaiExecutor")
	private ThreadPoolTaskExecutor loan7DaiExecutor;
    
    public void execute() {

        log.info(">>>loan_7dai_queue start<<<");
        BsSysConfig configPro = sysConfigService.findConfigByKey(Constants.LOAN_7DAI_PROCESS_MAX_NUM);
    	Integer max_num = 15;
		if(configPro != null){
			max_num = Integer.parseInt(configPro.getConfValue());
		}
    	int n = 0;//该次定时允许开启定时数
        
        try {
            Long len = jsClientDaoSupport.llen("loan_7dai_queue");
            log.info(">>>7贷借款队列大小"+ len +"<<<");
            if(len == null){
				return;
			}else if(len >= max_num){
            	n = max_num;
            }else if(len < max_num){
            	n = len.intValue();
            }
            for (int i = 0; i < n; i++) {
            	String item = jsClientDaoSupport.lpop("loan_7dai_queue");
                log.info(">>>redis单条数据："+ item +"<<<");
                if(StringUtil.isEmpty(item)){
                    log.error("pop7贷借款队列数据为空，等待下一次定时pop");
                    return;
                }
                LoanQueueDTO loanQueueDTO = JSON.parseObject(item, LoanQueueDTO.class);
                if(loanQueueDTO != null){
                	try {
                		LoanQueueProcess process = new LoanQueueProcess();
                    	process.setDepFixedLoanPaymentService(depFixedLoanPaymentService);
                    	process.setLoanPaymentService(loanPaymentService);
                    	process.setLoanQueueDTO(loanQueueDTO);
                    	loan7DaiExecutor.execute(process);
					} catch (RejectedExecutionException e) {
						log.error("ThreadPool Rejected. 重新放入7贷借款队列", e);
						//重新放入借款队列
	    				jsClientDaoSupport.rpush("loan_7dai_queue", JSON.toJSONString(loanQueueDTO));
					}
                }else{
                    log.info(">>>7贷借款队列lpop为空<<<");
                }
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("借款队列定时执行异常", e);
        }

        log.info(">>>loan_7dai_queue end<<<");
    }
}
