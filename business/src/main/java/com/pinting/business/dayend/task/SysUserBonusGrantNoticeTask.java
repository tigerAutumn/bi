package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsBonusGrantPlanMapper;
import com.pinting.business.model.BsBonusGrantPlan;
import com.pinting.business.service.site.SendWechatByRabbitService;
import com.pinting.core.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 对当日定时发放的奖励金已到账的做通知
 * @project business
 * @author bianyatian
 * @2018-3-15 上午10:20:08
 */
@Service
public class SysUserBonusGrantNoticeTask {
	private Logger log = LoggerFactory.getLogger(SysUserBonusGrantNoticeTask.class);
	@Autowired
	private BsBonusGrantPlanMapper bsBonusGrantPlanMapper;
	@Autowired
	private SendWechatByRabbitService sendWechatByRabbitService;
	
	
	
	public void execute() {
		log.info("=========定时任务{用户奖励金到账通知}开始=========");
		
		List<BsBonusGrantPlan> plans = bsBonusGrantPlanMapper.getNeedNoticeList();
		if(CollectionUtils.isNotEmpty(plans)){
			for (BsBonusGrantPlan bsBonusGrantPlan : plans) {
				try {
					sendWechatByRabbitService.bonusArrive(bsBonusGrantPlan.getUserId(), 
							bsBonusGrantPlan.getAmount().toString(), DateUtil.format(bsBonusGrantPlan.getFinishDate()));
				} catch (Exception e) {
					log.info("=========定时任务{用户奖励金到账通知}失败，BsBonusGrantPlan.id= "+bsBonusGrantPlan.getId()+"=========");
					e.printStackTrace();
				}
			}
		}
	}
}
