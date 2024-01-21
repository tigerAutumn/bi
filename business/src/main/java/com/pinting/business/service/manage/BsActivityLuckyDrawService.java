package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.hessian.manage.message.ReqMsg_BsActivityLuckyDraw_GetList;
import com.pinting.business.hessian.manage.message.ResMsg_BsActivityLuckyDraw_GetList;
import com.pinting.business.model.BsActivityLuckyDraw;
import com.pinting.business.model.vo.ActivityScratchcardAwardVO;
import com.pinting.business.model.vo.ActivityScratchcardChanceVO;
import com.pinting.business.model.vo.ActivityScratchcardPrizeVO;

public interface BsActivityLuckyDrawService {

	void selectList(ReqMsg_BsActivityLuckyDraw_GetList req, ResMsg_BsActivityLuckyDraw_GetList res);

	/**
	 * 活动时候进行中 true-进行中；false-未开始或者已结束
	 * @param activityId
	 * @return
     */
	boolean activityStart(int activityId);
	
	/**
	 * 刮刮乐活动获奖名单
	 * @return
	 */
	List<ActivityScratchcardAwardVO> userAwardList();
	
	/**
	 * 刮刮乐活动中奖次数统计
	 * @return
	 */
	public Integer countUserAwardNum(BsActivityLuckyDraw luckyDraw);
	
	/**
	 * 刮刮乐活动用户刮奖
	 * @param userId
	 * @param activityId
	 * @return
	 */
	String userScratch(Integer userId, int activityId, Double investAmount);
	
	/**
	 * 刮刮乐活动用户是否有刮奖机会
	 * @param userId
	 * @return
	 */
	ActivityScratchcardChanceVO hasScratchChance(Integer userId);
	
	/**
	 * 刮刮乐活动我的中奖信息
	 * @param userId
	 * @return
	 */
	ActivityScratchcardPrizeVO scratchcardPrize(Integer userId);
	
}
