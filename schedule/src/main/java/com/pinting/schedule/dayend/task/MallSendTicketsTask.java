package com.pinting.schedule.dayend.task;

import java.util.concurrent.RejectedExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pinting.business.service.site.RedPacketService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.TicketInterestService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.dao.MallSendCommodityMapper;
import com.pinting.mall.model.MallBsSysConfig;
import com.pinting.mall.service.MallExchangeOrdersService;
import com.pinting.mall.service.MallSysConfigService;
import com.pinting.schedule.dayend.task.process.MallSendTicketsQueueProcess;

/**
 * 红包、优惠券-积分商城兑换成功后发放
 * @project schedule
 * @author bianyatian
 * @2018-5-14 上午10:39:49
 */
@Service
public class MallSendTicketsTask {

	private Logger log = LoggerFactory.getLogger(MallSendTicketsTask.class);
	
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
    @Qualifier("mallSendTicketsTaskExecutor")
	private ThreadPoolTaskExecutor mallSendTicketsTaskExecutor;
	
	@Autowired
	private MallExchangeOrdersService mallExchangeOrdersService;
	
	@Autowired
	private MallSysConfigService sysConfigService;
	@Autowired
	private RedPacketService redPacketService;
	@Autowired
	private TicketInterestService ticketInterestService;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private MallSendCommodityMapper mallSendCommodityMapper;
	
	/**
	 * 1、根据redis中的id查询积分商城订单表数据，关联查询商品信息和商品类别信息；
	 * 2、校验订单状态
	 * 3、根据商品类别发放红包和加息券，若不是这两种类型，则抛异常
	 * @author bianyatian
	 */
	public void execute(){
		log.info(">>>>>start【红包和加息券】发放定时任务 start<<<<<");
		MallBsSysConfig configPro = sysConfigService.findConfigValueByKey(Constants.MALL_SEND_TICKET_PROCESS_MAX_NUM);
     	Integer max_num = 15;
 		if(configPro != null){
 			max_num = Integer.parseInt(configPro.getConfValue());
 		}
     	int n = 0;//该次定时允许开启定时数
     	try {
     		Long len = jsClientDaoSupport.llen(Constants.MALL_SEND_TICKET_QUEUE_KEY);
            log.info(">>>【红包和加息券】发放队列大小"+ len +"<<<");
            if(len == null){
    			return;
    		}else if(len >= max_num){
            	n = max_num;
            }else if(len < max_num){
            	n = len.intValue();
            }
            for (int i = 0; i < n; i++) {
            	String item = jsClientDaoSupport.lpop(Constants.MALL_SEND_TICKET_QUEUE_KEY);
	        	log.info(">>>redis单条数据："+ item +"<<<");
	        	if(StringUtil.isEmpty(item)){
	        		log.error("pop【红包和加息券】发放队列为空，等待下一次定时pop");
	        		return;
	        	}
	        	Integer orderId = JSON.parseObject(item,Integer.class);
	        	if(orderId != null){
	        		try {
	        			MallSendTicketsQueueProcess process = new MallSendTicketsQueueProcess();
		        		process.setOrderId(orderId);
		        		process.setMallExchangeOrdersService(mallExchangeOrdersService);
		        		process.setRedPacketService(redPacketService);
		        		process.setTicketInterestService(ticketInterestService);
		        		process.setSpecialJnlService(specialJnlService);
		        		process.setMallSendCommodityMapper(mallSendCommodityMapper);
		        		mallSendTicketsTaskExecutor.execute(process);
					} catch (RejectedExecutionException e) {
						log.error("ThreadPool Rejected. 重新【红包和加息券】发放队列", e);
						//重新放入积分发放队列
	    				jsClientDaoSupport.rpush(Constants.MALL_SEND_TICKET_QUEUE_KEY, JSON.toJSONString(orderId));
					}
	        	}else{
	        		log.info(">>>【红包和加息券】发放队列为空");
	        	}
	        	
            }
		} catch (Exception e) {
			e.printStackTrace();
            log.error("【红包和加息券】发放定时 执行异常", e);
		}
     	
		
		log.info(">>>>>end【红包和加息券】发放定时任务 end<<<<<");
	}
}
