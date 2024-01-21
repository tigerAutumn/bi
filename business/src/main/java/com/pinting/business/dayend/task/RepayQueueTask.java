package com.pinting.business.dayend.task;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.coreflow.core.service.DispatcherService;
import com.pinting.business.dayend.task.process.RepayQueueProcess;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.dto.RepayQueueDTO;
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

import java.util.concurrent.RejectedExecutionException;

/**
 * Created by zhangbao on 2017/10/11.
 */
@Service
public class RepayQueueTask {

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private Logger log = LoggerFactory.getLogger(RepayQueueTask.class);

    @Autowired
    private DepFixedRepayPaymentService depFixedRepayPaymentService;
    @Autowired
    private RepayPaymentService repayPaymentService;
    @Autowired
    private DispatcherService dispatcherService;
    
    @Autowired
    private SysConfigService sysConfigService;
    
    @Autowired
    @Qualifier("repayExecutor")
	private ThreadPoolTaskExecutor repayExecutor;

    public void execute() {
		log.info(">>>repay_queue start<<<");
		BsSysConfig configPro = sysConfigService.findConfigByKey(Constants.DK_REPAY_PROCESS_MAX_NUM);
    	Integer max_num = 15;
		if(configPro != null){
			max_num = Integer.parseInt(configPro.getConfValue());
		}
    	int n = 0;//该次定时允许开启定时数
    	try {
    		Long len = jsClientDaoSupport.llen("repay_queue");
            log.info(">>>队列大小"+ len +"<<<");
			if(len == null){
				return;
			}else if(len >= max_num){
            	n = max_num;
            }else if(len < max_num){
            	n = len.intValue();
            }
            for (int i = 0; i < n; i++) {
            	String item = jsClientDaoSupport.lpop("repay_queue");
                log.info(">>>redis单条数据："+ item +"<<<");
                if(StringUtil.isEmpty(item)){
                    log.error("pop还款队列数据为空，等待下一次定时pop");
                    return;
                }
                RepayQueueDTO repayQueueDTO = JSON.parseObject(item, RepayQueueDTO.class);
        		try {
        			RepayQueueProcess process = new RepayQueueProcess();
            		process.setDepFixedRepayPaymentService(depFixedRepayPaymentService);
            		process.setRepayPaymentService(repayPaymentService);
            		process.setDispatcherService(dispatcherService);
            		process.setRepayQueueDTO(repayQueueDTO);
            		repayExecutor.execute(process);
    			} catch (RejectedExecutionException e) {
    				log.error("ThreadPool Rejected. 重新放入还款申请队列", e);
    			    //重新放入还款申请队列
    				jsClientDaoSupport.rpush("repay_queue", JSON.toJSONString(repayQueueDTO));
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("还款队列定时执行异常", e);
		}
		log.info(">>>repay_queue end<<<");

    }
}
