package com.pinting.mall.dayend.task.process;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.mall.enums.MallRuleEnum;
import com.pinting.mall.model.dto.MallPointsIncomeQueueDTO;
import com.pinting.mall.service.MallPointsService;
import com.pinting.mall.util.Constants;

/**
 * 积分发放任务MallPointsQueueTask调用的线程
 * @author Gemma
 * @date 2018-5-9 20:24:47
 */
public class MallPointsQueueMoreProcess implements Runnable{

	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	private Logger log = LoggerFactory.getLogger(MallPointsQueueMoreProcess.class);
	
	private MallPointsService mallAccountService;
	private MallPointsIncomeQueueDTO mallPointsDTO;
	
	public MallPointsService getMallAccountService() {
		return mallAccountService;
	}

	public void setMallAccountService(MallPointsService mallAccountService) {
		this.mallAccountService = mallAccountService;
	}
	
	public MallPointsIncomeQueueDTO getMallPointsDTO() {
		return mallPointsDTO;
	}

	public void setMallPointsDTO(MallPointsIncomeQueueDTO mallPointsDTO) {
		this.mallPointsDTO = mallPointsDTO;
	}

	@Override
	public void run() {
		
		//线程开始，存放redis数据
		Map<String, String> map = jsClientDaoSupport.getHashMapFromObj("mall_points_process");
		
		if (MapUtils.isEmpty(map) || map.size() == 0) {
			map = new HashMap<>();
		}
		map.put(mallPointsDTO.getId().toString(), mallPointsDTO.getId().toString());
		
		jsClientDaoSupport.addObjOfHashMap(map, "mall_points_process", -1);
		
		try {
			if( mallPointsDTO != null ) {
                //调用积分发放业务
                if(MallRuleEnum.MALL_INVEST.getCode().equals(mallPointsDTO.getTransType()) 
                		|| MallRuleEnum.MALL_FIRST_INVEST.getCode().equals(mallPointsDTO.getTransType())
                		|| MallRuleEnum.MALL_TOTAL_INVEST.getCode().equals(mallPointsDTO.getTransType())) {
                	mallAccountService.investPointsIn(mallPointsDTO);
                }
            } else {
                log.info(">>>积分发放队列lpop为空<<<");
            }
		} catch (Exception e) {
			log.error("积分发放队列线程执行异常", e);
		} finally {
			//删除redis数据
			jsClientDaoSupport.deleteObjOfHashMap(mallPointsDTO.getId().toString(),	"mall_points_process");
		}
	}

}
