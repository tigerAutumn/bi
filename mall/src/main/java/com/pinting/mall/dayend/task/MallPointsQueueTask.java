package com.pinting.mall.dayend.task;

import java.util.concurrent.RejectedExecutionException;

import com.alibaba.fastjson.JSON;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.dayend.task.process.MallPointsQueueMoreProcess;
import com.pinting.mall.dayend.task.process.MallPointsQueueOneProcess;
import com.pinting.mall.model.MallBsSysConfig;
import com.pinting.mall.model.dto.MallPointsIncomeQueueDTO;
import com.pinting.mall.service.MallPointsService;
import com.pinting.mall.service.MallSysConfigService;
import com.pinting.mall.util.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
/**
 * 积分发放队列处理定时
 * @author Gemma
 * @date 2018年5月9日 
 */
@Service
public class MallPointsQueueTask {

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private Logger log = LoggerFactory.getLogger(MallPointsQueueTask.class);

    @Autowired
    private MallPointsService mallAccountService;
    @Autowired
    private MallSysConfigService sysConfigService;
    
    @Autowired
    @Qualifier("mallTaskExecutor")
	private ThreadPoolTaskExecutor mallTaskExecutor;
    /**
     * 积分发放  -- 风测,开通存管,注册
     * */
    public void executeOne() {

        log.info(">>>>>start积分发放定时任务start<<<<<");
        try {
        	MallBsSysConfig configPro = sysConfigService.findConfigValueByKey(Constants.TASK_MAX_RECORDS_ONE);
         	Integer max_num = 15;
     		if(configPro != null){
     			max_num = Integer.parseInt(configPro.getConfValue());
     		}
         	int n = 0;//该次定时允许开启定时数
        	
         	Long len = jsClientDaoSupport.llen("mall_points_queue");
            log.info(">>>积分发放队列大小"+ len +"<<<");
            if(len == null){
				return;
			}else if(len >= max_num){
            	n = max_num;
            }else if(len < max_num){
            	n = len.intValue();
            }
            for (int i = 0; i < n; i++) {
         	
	        	String item = jsClientDaoSupport.lpop("mall_points_queue");
	        	log.info(">>>redis单条数据："+ item +"<<<");
	        	if(StringUtil.isEmpty(item)){
	        		log.error("pop积分任务队列为空，等待下一次定时pop");
	        		return;
	        	}
	        	
				MallPointsIncomeQueueDTO mallPointsDTO = JSON.parseObject(item, MallPointsIncomeQueueDTO.class);
	            if( mallPointsDTO != null ) {
	            	try {
	            		MallPointsQueueOneProcess process = new MallPointsQueueOneProcess();
	                	process.setMallAccountService(mallAccountService);
	                	process.setMallPointsDTO(mallPointsDTO);
	                	mallTaskExecutor.execute(process);
					} catch (RejectedExecutionException e) {
						log.error("ThreadPool Rejected. 重新放入积分发放队列", e);
						//重新放入借款队列
	    				jsClientDaoSupport.rpush("mall_points_queue", JSON.toJSONString(mallPointsDTO));
					}
	            }else{
	                log.info(">>>>>积分发放队列为空<<<<<");
	            }
            }
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("积分发放队列定时执行异常", e.getMessage());
        }
        
        log.info(">>>>>end积分发放定时任务end<<<<<");
    }
    
    /**
     * 积分发放 -- 购买 
     * */
    public void executeMore() {

        log.info(">>>>>start投资积分发放定时任务start<<<<<");
        try {
        	MallBsSysConfig configPro = sysConfigService.findConfigValueByKey(Constants.TASK_MAX_RECORDS_MORE);
         	Integer max_num = 15;
     		if(configPro != null){
     			max_num = Integer.parseInt(configPro.getConfValue());
     		}
         	int n = 0;//该次定时允许开启定时数
        	
         	Long len = jsClientDaoSupport.llen("mall_points_invest_queue");
            log.info(">>>投资积分发放队列大小"+ len +"<<<");
            if(len == null){
				return;
			}else if(len >= max_num){
            	n = max_num;
            }else if(len < max_num){
            	n = len.intValue();
            }
            for (int i = 0; i < n; i++) {
	            	
	        	String item = jsClientDaoSupport.lpop("mall_points_invest_queue");
	        	log.info(">>>redis单条数据："+ item +"<<<");
	        	if(StringUtil.isEmpty(item)){
	        		log.error("pop投资积分任务队列为空，等待下一次定时pop");
	        		return;
	        	}
	        	
				MallPointsIncomeQueueDTO mallPointsDTO = JSON.parseObject(item, MallPointsIncomeQueueDTO.class);
	            if( mallPointsDTO != null ) {
	            	try {
	            		MallPointsQueueMoreProcess process = new MallPointsQueueMoreProcess();
	                	process.setMallAccountService(mallAccountService);
	                	process.setMallPointsDTO(mallPointsDTO);
	                	mallTaskExecutor.execute(process);
					} catch (RejectedExecutionException e) {
						log.error("ThreadPool Rejected. 重新放入投资积分发放队列", e);
						//重新放入积分发放队列
	    				jsClientDaoSupport.rpush("mall_points_invest_queue", JSON.toJSONString(mallPointsDTO));
					}
	            }else{
	                log.info(">>>>>投资积分发放队列为空<<<<<");
	            }
        	}
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("投资积分发放队列定时执行异常", e.getMessage());
        }
        
        log.info(">>>>>end投资积分发放定时任务end<<<<<");
    }
}
