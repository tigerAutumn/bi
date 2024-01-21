package com.pinting.business.accounting.service;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import com.pinting.business.model.BsProduct;

@SuppressWarnings("rawtypes")
public interface ProductPlanStatutsScheduleService {
	
	/**
	 * 待发布理财计划，根据开始时间前5分钟时，自动发布（schedule服务）
	 * @param product
	 * @return
	 */
	public ScheduledFuture autoPublishSchedule(BsProduct product);
	
	/**
	 * 已发布理财计划，如果尚未开始，根据开始时间，自动开始（schedule服务）
	 * @param product
	 * @return
	 */
	public ScheduledFuture autoOpeningSchedule(BsProduct product);
	
	/**
	 * 已发布理财计划，根据结束时间，自动结束（schedule服务）
	 * @param product
	 * @return
	 */
	public ScheduledFuture autoFinishSchedule(BsProduct product);
	
	/**
	 * 取消某定时任务（schedule服务）
	 * @param productSchedules
	 */
	public boolean cancelProductSchedule(ScheduledFuture schedule);
	
	/**
	 * 删除某理财计划的所有定时任务（schedule服务）
	 * @param product
	 */
	public void scheduleRegistDelete(BsProduct product);
	
	/**
	 * 针对理财计划状态（待发布、已发布但未开始、已开始），进行定时任务注册（schedule服务）
	 * @param product
	 * @return 返回定时任务注册列表
	 */
	public List<ScheduledFuture> scheduleRegist4Status(BsProduct product);
	
}
