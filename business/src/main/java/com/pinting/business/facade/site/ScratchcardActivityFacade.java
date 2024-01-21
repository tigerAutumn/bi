package com.pinting.business.facade.site;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_ScratchcardActivity_CountUserAward;
import com.pinting.business.hessian.site.message.ReqMsg_ScratchcardActivity_HasScratchChance;
import com.pinting.business.hessian.site.message.ReqMsg_ScratchcardActivity_ScratchcardPrize;
import com.pinting.business.hessian.site.message.ReqMsg_ScratchcardActivity_UserAwardList;
import com.pinting.business.hessian.site.message.ReqMsg_ScratchcardActivity_UserScratch;
import com.pinting.business.hessian.site.message.ResMsg_ScratchcardActivity_CountUserAward;
import com.pinting.business.hessian.site.message.ResMsg_ScratchcardActivity_HasScratchChance;
import com.pinting.business.hessian.site.message.ResMsg_ScratchcardActivity_ScratchcardPrize;
import com.pinting.business.hessian.site.message.ResMsg_ScratchcardActivity_UserAwardList;
import com.pinting.business.hessian.site.message.ResMsg_ScratchcardActivity_UserScratch;
import com.pinting.business.model.BsActivityLuckyDraw;
import com.pinting.business.model.vo.ActivityScratchcardAwardVO;
import com.pinting.business.model.vo.ActivityScratchcardChanceVO;
import com.pinting.business.model.vo.ActivityScratchcardPrizeVO;
import com.pinting.business.service.manage.BsActivityLuckyDrawService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;

/**
 * 刮刮乐活动
 * @author SHENGUOPING
 * @date  2017年8月17日 下午8:17:47
 */
@Component("ScratchcardActivity")
public class ScratchcardActivityFacade {

	@Autowired
	private BsActivityLuckyDrawService bsActivityLuckyDrawService;
	
	
	/**
	 * 刮刮乐用户中奖记录
	 * @param req
	 * @param res
	 */
	public void userAwardList(ReqMsg_ScratchcardActivity_UserAwardList req, ResMsg_ScratchcardActivity_UserAwardList res) {
		List<ActivityScratchcardAwardVO> userAward = bsActivityLuckyDrawService.userAwardList();
		ArrayList<HashMap<String, Object>> bean = BeanUtils.classToArrayList(userAward);
		if (bean != null) {
			res.setAwardList(bean);
		}else {
			res.setAwardList(null);
		}
	}
	
	/**
	 * 刮刮乐活动中奖次数统计
	 * @param req
	 * @param res
	 */
	public void countUserAward(ReqMsg_ScratchcardActivity_CountUserAward req, ResMsg_ScratchcardActivity_CountUserAward res) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(week != Constants.WEEKHAY_WEEK_4){
			res.setResCode(ConstantUtil.RESCODE_FAIL);
			return;
		}
		BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
		if(week == Constants.WEEKHAY_WEEK_4) {
			luckyDraw.setCreateTime(new Date());
		}
		luckyDraw.setUserId(req.getUserId());
		luckyDraw.setActivityId(Constants.ACTIVITY_ID_2017_SCRATCH_CARD);
		Integer userAwardNum = bsActivityLuckyDrawService.countUserAwardNum(luckyDraw);
		res.setUserAwardCount(userAwardNum);

		//中奖的奖品
		ActivityScratchcardPrizeVO activityScratchcardPrizeVO = bsActivityLuckyDrawService.scratchcardPrize(req.getUserId());
		if(activityScratchcardPrizeVO != null) {
			res.setPrizeContent(activityScratchcardPrizeVO.getPrizeContent());
		}
	}
	
	/**
	 * 刮刮乐活动用户刮奖
	 * @param req
	 * @param res
	 */
	public void userScratch(ReqMsg_ScratchcardActivity_UserScratch req, ResMsg_ScratchcardActivity_UserScratch res) {
		String prize = bsActivityLuckyDrawService.userScratch(req.getUserId(), Constants.ACTIVITY_ID_2017_SCRATCH_CARD, req.getInvestAmount());
		if (StringUtil.isBlank(prize)) {
			res.setResCode(ConstantUtil.RESCODE_FAIL);
			res.setResMsg(prize);
		} else {
			res.setPrizeContent(prize);
		}
	}
	
	/**
	 * 刮刮乐活动用户是否有刮奖机会
	 * @param req
	 * @param resp
	 */
	public void hasScratchChance(ReqMsg_ScratchcardActivity_HasScratchChance req, ResMsg_ScratchcardActivity_HasScratchChance resp) {
		ActivityScratchcardChanceVO scratchcardChanceVO = bsActivityLuckyDrawService.hasScratchChance(req.getUserId()); 
		if (scratchcardChanceVO != null) {
    		resp.setHasScratchChance(scratchcardChanceVO.isHasScratchChance());
    		resp.setYearsInvestAmount(scratchcardChanceVO.getYearsInvestAmount());
    	} else {
            resp.setHasScratchChance(false);
        }
	}
	
	/**
	 * 刮刮乐活动我的中奖信息
	 * @param req
	 * @param resp
	 */
	public void scratchcardPrize(ReqMsg_ScratchcardActivity_ScratchcardPrize req, ResMsg_ScratchcardActivity_ScratchcardPrize resp) {
		ActivityScratchcardPrizeVO scratchcardPrizeVO = bsActivityLuckyDrawService.scratchcardPrize(req.getUserId());
		if (scratchcardPrizeVO != null) {
    		resp.setIsScratch(scratchcardPrizeVO.getIsScratch());
    		resp.setPrizeContent(scratchcardPrizeVO.getPrizeContent());
    	} else {
            resp.setIsScratch(Constants.ACTIVITY_SCRATCH_CARD_IS_SCRATCH_NO);
        }
	}
	 
}
