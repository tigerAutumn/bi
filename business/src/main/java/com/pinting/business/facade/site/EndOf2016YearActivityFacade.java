package com.pinting.business.facade.site;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_GetGrandPrize;
import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_GetLuckyPrizeList;
import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_GetOtherAwards;
import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_LotteryScrollingData;
import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_NumberOfDraws;
import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_NumberOfParticipants;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_GetGrandPrize;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_GetLuckyPrizeList;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_GetOtherAwards;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_LotteryScrollingData;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_NumberOfDraws;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_NumberOfParticipants;
import com.pinting.business.model.vo.EndOf2016YearActivityVO;
import com.pinting.business.service.site.EndOf2016YearActivityService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;

/**
 * 2016客户年终抽奖活动相关
 * Created by shh on 2016/12/01 12:00.
 * @author shh
 */
@Component("EndOf2016YearActivity")
public class EndOf2016YearActivityFacade {
	
	@Autowired
	private EndOf2016YearActivityService endOf2016YearActivityService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	
	/**
	 * 抽奖滚屏数据
	 * @param req
	 * @param res
	 */
	public void lotteryScrollingData(ReqMsg_EndOf2016YearActivity_LotteryScrollingData req, ResMsg_EndOf2016YearActivity_LotteryScrollingData res) {
		//1、可以抽奖的用户
		List<EndOf2016YearActivityVO> lotteryList = endOf2016YearActivityService.quertLotteryMobileList();
		//2、滚动手机号
		List<EndOf2016YearActivityVO> scrollLlist = new ArrayList<EndOf2016YearActivityVO>();
		for(EndOf2016YearActivityVO vo : lotteryList) {
			EndOf2016YearActivityVO scrollVO = new EndOf2016YearActivityVO();
			scrollVO.setUserId(vo.getUserId());
			scrollVO.setMobile(getBlurMobile(vo.getMobile()));
			scrollLlist.add(scrollVO);
		}
		res.setValueList(BeanUtils.classToArrayList(scrollLlist));
	}
	
	/**
	 * 抽取幸运奖
	 * @param req
	 * @param res
	 */
	public void getLuckyPrizeList(ReqMsg_EndOf2016YearActivity_GetLuckyPrizeList req, ResMsg_EndOf2016YearActivity_GetLuckyPrizeList res) {
		//1、幸运奖中奖列表
		List<EndOf2016YearActivityVO> luckyLlist = endOf2016YearActivityService.drawLuckyPrizeList();
		
		//2、页面最终显示的中奖名单
		List<EndOf2016YearActivityVO> showLlist = new ArrayList<EndOf2016YearActivityVO>();
		for(EndOf2016YearActivityVO vo : luckyLlist) {
			//3、中奖的人发送短信提醒
//			smsServiceClient.sendTemplateMessage(vo.getMobile(), TemplateKey.ENDOF2016_ACTIVITY_WINNING, "幸运奖");
//			smsServiceClient.sendTemplateMessage("13757121196", TemplateKey.ENDOF2016_ACTIVITY_WINNING, "幸运奖");
			EndOf2016YearActivityVO endOf2016YearActivityVO = new EndOf2016YearActivityVO();
			endOf2016YearActivityVO.setMobile(getBlurMobile(vo.getMobile()));
			showLlist.add(endOf2016YearActivityVO);
		}
		res.setValueList(BeanUtils.classToArrayList(showLlist));
		
	}
	
	/**
	 * 抽取特等奖
	 * @param req
	 * @param res
	 */
	public void getGrandPrize(ReqMsg_EndOf2016YearActivity_GetGrandPrize req, ResMsg_EndOf2016YearActivity_GetGrandPrize res) {
		//1、页面显示 特等奖中奖名单
		List<EndOf2016YearActivityVO> grandPrizeList = endOf2016YearActivityService.grandPrize();
		List<EndOf2016YearActivityVO> showLlist = new ArrayList<EndOf2016YearActivityVO>();
		EndOf2016YearActivityVO endOf2016YearActivityVO = new EndOf2016YearActivityVO();
		endOf2016YearActivityVO.setMobile(getBlurMobile(grandPrizeList.get(0).getMobile()));
		showLlist.add(endOf2016YearActivityVO);
		res.setValueList(BeanUtils.classToArrayList(showLlist));
		//2、中奖的人发送短信提醒
//		smsServiceClient.sendTemplateMessage(grandPrizeList.get(0).getMobile(), TemplateKey.ENDOF2016_ACTIVITY_WINNING, "特等奖");
//		smsServiceClient.sendTemplateMessage("13757121196", TemplateKey.ENDOF2016_ACTIVITY_WINNING, "特等奖");
		
	}
	
	/**
	 * 抽取一、二、三等奖
	 * @param req
	 * @param res
	 */
	public void getOtherAwards(ReqMsg_EndOf2016YearActivity_GetOtherAwards req, ResMsg_EndOf2016YearActivity_GetOtherAwards res) {
		List<EndOf2016YearActivityVO> otherAwardsObj = endOf2016YearActivityService.otherAwards(req.getActivityAwardId());
		//1、页面显示 一、二、三等奖中奖名单
		List<EndOf2016YearActivityVO> showLlist = new ArrayList<EndOf2016YearActivityVO>();
		EndOf2016YearActivityVO endOf2016YearActivityVO = new EndOf2016YearActivityVO();
		endOf2016YearActivityVO.setMobile(getBlurMobile(otherAwardsObj.get(0).getMobile()));
		showLlist.add(endOf2016YearActivityVO);
		res.setValueList(BeanUtils.classToArrayList(showLlist));
	}
	
	/**
	 * 查询一二三等奖 已抽奖的人次
	 * @param req
	 * @param res
	 */
	public void numberOfDraws(ReqMsg_EndOf2016YearActivity_NumberOfDraws req, ResMsg_EndOf2016YearActivity_NumberOfDraws res) {
		int maxNumberOfDraws = endOf2016YearActivityService.queryNumberOfWinners(req.getActivityAwardId());
		res.setMaxNumberOfDraws(maxNumberOfDraws);
	}
	
	//查询参加抽奖活动的人数，人数大于等于20人时才可以抽奖
	public void numberOfParticipants(ReqMsg_EndOf2016YearActivity_NumberOfParticipants req, ResMsg_EndOf2016YearActivity_NumberOfParticipants res) {
		List<EndOf2016YearActivityVO> lotteryList = endOf2016YearActivityService.quertLotteryMobileList();
		if(lotteryList != null && lotteryList.size() > 0) {
			res.setLotteryCount(lotteryList.size());
		}
	}
	
	
	/**
	 * 手机号模糊
	 * 抽奖手机号  3 4 4展示 中间4位用币港湾表示 如：188 币港湾 1688
	 * @param mobile
	 * @return
	 */
	private String getBlurMobile(String mobile) {
		String str = "";
		if(StringUtil.isNotBlank(mobile)){
			str = mobile.substring(0, 3);
			str = str + " 币港湾  "+mobile.substring(mobile.length()-4);
		}
		return str;
	}

}
