package com.pinting.business.facade.site;

import com.pinting.business.accounting.finance.service.UserBonusGrantService;
import com.pinting.business.dao.BsActivityAwardMapper;
import com.pinting.business.dao.BsActivityLuckyDrawMapper;
import com.pinting.business.dao.BsActivityMapper;
import com.pinting.business.dao.BsBonusGrantPlanMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.facade.site.enums.ActivityEnum;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsActivity;
import com.pinting.business.model.BsActivityAward;
import com.pinting.business.model.BsActivityLuckyDraw;
import com.pinting.business.model.BsBonusGrantPlan;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.BsActivityLuckyDrawVO;
import com.pinting.business.model.vo.PlayerKillingVO;
import com.pinting.business.model.vo.TrebleGiftListVO;
import com.pinting.business.service.site.ActivityLuckyDrawService;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.RedPacketService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.LuckyDrawUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 活动用户抽奖记录
 * @author bianyatian
 * @2016-6-6 下午3:27:23
 */
@Component("ActivityLuckyDraw")
public class ActivityLuckyDrawFacade {

	@Autowired
	private ActivityLuckyDrawService activityLuckyDrawService;
	@Autowired
	private UserBonusGrantService userBonusGrantService;
	@Autowired
	private BsBonusGrantPlanMapper bonusGrantPlanMapper;
	@Autowired
	private BsActivityMapper bsActivityMapper;
	@Autowired
	private RedPacketService redPacketService;
	@Autowired
	private UserService userService;
	@Autowired
	private BsActivityLuckyDrawMapper bsActivityLuckyDrawMapper;
	@Autowired
	private BsActivityAwardMapper bsActivityAwardMapper;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	 
	
	/**
	 * 根据活动id 和 条数，查询某活动中奖列表（根据时间倒序）
	 * @param req
	 * @param res
	 */
	public void luckyUserList(ReqMsg_ActivityLuckyDraw_LuckyUserList req, ResMsg_ActivityLuckyDraw_LuckyUserList res){
		
		List<BsActivityLuckyDrawVO> list = activityLuckyDrawService.getLuckyAllList(req.getActivityId(),req.getLuckyLimitNum());
		
		res.setLuckyList(BeanUtils.classToArrayList(list));
	}
	
	/**
	 * 根据活动id查询幸运出借人（根据时间倒序）
	 * @param req
	 * @param res
	 */
	public void luckyLenders(ReqMsg_ActivityLuckyDraw_LuckyLenders req, ResMsg_ActivityLuckyDraw_LuckyLenders res){
		
		List<BsActivityLuckyDrawVO> list = activityLuckyDrawService.getLuckyLenders(req.getActivityId());
		for (int i = 0; i < list.size(); i++) {
			String mobile = list.get(i).getMobile();
			String str = "";
			if(StringUtil.isNotBlank(mobile)){
				str = mobile.substring(0, 3);
				str = str + " ****  "+mobile.substring(mobile.length()-4);
			}
			list.get(i).setMobile(str);
		}
		res.setLuckyList(BeanUtils.classToArrayList(list));
	}
	
	/**
	 * 活动时间查询
	 * @param req
	 * @param res
	 */
	public void activityTime(ReqMsg_ActivityLuckyDraw_ActivityTime req, ResMsg_ActivityLuckyDraw_ActivityTime res){
		/*活动时间-是否开始*/
		BsActivity active = bsActivityMapper.selectByPrimaryKey(req.getActivityId());
		Date now = new Date();
		if(now.compareTo(active.getStartTime()) <0){
			res.setIsStart("noStart");
		}else{
			if(now.compareTo(active.getEndTime()) >0){
				res.setIsStart("end");
			}else{
				res.setIsStart("ing");
			}
		}
		String format = req.getFormat()==null?"yyyy-MM-dd HH:mm:ss":req.getFormat();
		res.setStartTime(DateUtil.formatDateTime(active.getStartTime(), format));
		res.setEndTime(DateUtil.formatDateTime(active.getEndTime(), format));
	}
	
	/**
	 * 根据活动id和用户id，查询获奖名单，用户剩余抽奖次数，活动起始时间，活动是否开始
	 * @param req
	 * @param res
	 */
	public void activityIndex(ReqMsg_ActivityLuckyDraw_ActivityIndex req, ResMsg_ActivityLuckyDraw_ActivityIndex res){
		/*所有中奖列表，最新中奖在前*/
		List<BsActivityLuckyDrawVO> list = activityLuckyDrawService.getLuckyAllList(req.getActivityId(),req.getLuckyLimitNum());
		/*查询当前已抽奖人数,去重复的userId*/
		Integer luckyNum = activityLuckyDrawService.countLuckyNum(req.getActivityId());
		/*用户可抽奖次数*/
		Integer countUserLucky = 0;
		if(req.getUserId() != null){
			countUserLucky = activityLuckyDrawService.countUserLucky(req.getActivityId(), req.getUserId(), "NO");
		}
		
		/*活动时间-是否开始*/
		BsActivity active = bsActivityMapper.selectByPrimaryKey(req.getActivityId());
		Date now = new Date();
		if(now.compareTo(active.getStartTime()) <0){
			res.setIsStart("noStart");
		}else{
			if(now.compareTo(active.getEndTime()) >0){
				res.setIsStart("end");
			}else{
				res.setIsStart("ing");
			}
		}
		
		res.setLuckyNum(luckyNum);
		res.setUserLuckyNum(countUserLucky);
		res.setLuckyList(BeanUtils.classToArrayList(list));
		res.setStartTime(DateUtil.format(active.getStartTime()));
		res.setEndTime(DateUtil.format(active.getEndTime()));
		
	}
	
	/**
	 * 活动用户抽奖
	 * @param req
	 * @param res
	 */
	public void getActiveAward(ReqMsg_ActivityLuckyDraw_GetActiveAward req, ResMsg_ActivityLuckyDraw_GetActiveAward res){
		/*1.校验活动时间-是否开始，若未开始或已结束，则beforeTimes = 0*/
		BsActivity active = bsActivityMapper.selectByPrimaryKey(req.getActivityId());
		Integer beforeTimes = activityLuckyDrawService.countUserLucky(req.getActivityId(), req.getUserId(), "NO");
		Date now = new Date();
		if(now.compareTo(active.getStartTime()) <0){
			beforeTimes = 0;
			res.setIsStart("noStart");
		}else{
			if(now.compareTo(active.getEndTime()) >0){
				beforeTimes = 0;
				res.setIsStart("end");
			}else{
				res.setIsStart("ing");
				/*活动进行中*/
				if(beforeTimes != 0){
					//按id排序查询用户已经存在但未进行抽奖操作的抽奖机会
					List<BsActivityLuckyDrawVO> getUserLuckyList = activityLuckyDrawService.getUserLuckyList(req.getActivityId(), req.getUserId(), "NO", null, null);
					if(getUserLuckyList.get(0) != null){
						BsActivityLuckyDraw activityLuckyDraw = new BsActivityLuckyDraw();
						activityLuckyDraw.setId(getUserLuckyList.get(0).getId());
						activityLuckyDraw.setAwardId(getUserLuckyList.get(0).getAwardId());
						activityLuckyDraw.setUserId(getUserLuckyList.get(0).getUserId());
						activityLuckyDraw.setIsAutoConfirm(getUserLuckyList.get(0).getIsAutoConfirm());
						switch (req.getActivityId()){
							case 1:
								break;
							case 2:
								//双十一，用户抽奖
								double11GetActiveAward(activityLuckyDraw);
								Integer afterTimes = activityLuckyDrawService.countUserLucky(activityLuckyDraw.getActivityId(), activityLuckyDraw.getUserId(), "NO");
								res.setAfterTimes(afterTimes);
								res.setAwardId(activityLuckyDraw.getAwardId());
								res.setAwardContent(getUserLuckyList.get(0).getAwardContent());
								break;
						}
					}
				}
				
			}
		}
		res.setBeforeTimes(beforeTimes);
		
	}
	
	private void double11GetActiveAward(BsActivityLuckyDraw activityLuckyDraw) {
		Double amount = 0.0;
		switch (activityLuckyDraw.getAwardId()) {
		case 14:
			amount = 5.0;
			break;
		case 15:
			amount = 10.0;
			break;
		case 16:
			amount = 20.0;
			break;
		case 17:
			amount = 30.0;
			break;
		case 18:
			amount = 50.0;
			break;

		default:  
			break;
		}
		if("Y".equals(activityLuckyDraw.getIsAutoConfirm()) && amount.compareTo(0.0) >0){
			//自动兑奖 -- 发放奖励金
			bonusAuto(amount,activityLuckyDraw.getUserId());
			//修改奖品发放状态
			activityLuckyDraw.setConfirmTime(new Date());
			activityLuckyDraw.setIsConfirm("Y");
		}
		if(activityLuckyDraw.getAwardId() == 13){
			//自动兑奖 -- 红包
			redPacketService.double11ActivityAutoRedPacketSend(activityLuckyDraw.getUserId(), "双11奖励红包");
			//修改奖品发放状态
			activityLuckyDraw.setConfirmTime(new Date());
			activityLuckyDraw.setIsConfirm("Y");
		}
		
		activityLuckyDrawService.updateUserDraw(activityLuckyDraw);
		
	}

	/**
	 * 奖励金自动发放
	 * @param amount
	 * @param userId
	 */
	private void bonusAuto(Double amount, Integer userId) {
		BsBonusGrantPlan plan = new BsBonusGrantPlan();
		plan.setAmount(amount);
		plan.setBeRecommendUserId(userId);
		plan.setCreateTime(new Date());
		plan.setGrantDate(new Date());
		plan.setGrantType(Constants.BONUS_GRANT_TYPE_BONUS_4_ACTIVITY);//活动奖励
		plan.setSerialNo(1);
		plan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
		plan.setUpdateTime(new Date());
		plan.setUserId(userId);
		bonusGrantPlanMapper.insertSelective(plan);
		//首期发放即时到账
		userBonusGrantService.grantBonus(plan);
	}

	/**
	 * 根据activityId获取所有中奖列表；活动抽奖人数
	 * userId 不为空的时候，查询用户可抽奖次数，否则传回0次
	 * @param req
	 * @param res
	 */
	public void activity618Index(ReqMsg_ActivityLuckyDraw_Activity618Index req, ResMsg_ActivityLuckyDraw_Activity618Index res){
		List<BsActivityLuckyDrawVO> list = activityLuckyDrawService.getLuckyAllList(req.getActivityId(),req.getLuckyLimitNum());
		
		Integer luckyNum = activityLuckyDrawService.countLuckyNum(req.getActivityId());
		Integer countUserLucky = 0;
		if(req.getUserId() != null){
			countUserLucky = activityLuckyDrawService.countUserLucky(req.getActivityId(), req.getUserId(), "NO");
		}
		res.setLuckyNum(luckyNum);
		res.setUserLuckyNum(countUserLucky);
		res.setLuckyList(BeanUtils.classToArrayList(list));
	}
	
	/**
	 * 618活动用户抽奖
	 * 先获得当前用户可抽奖次数，>0则抽奖
	 * @param req
	 * @param res
	 */
	public void get618Award(ReqMsg_ActivityLuckyDraw_Get618Award req, ResMsg_ActivityLuckyDraw_Get618Award res){
		Integer beforeTimes = activityLuckyDrawService.countUserLucky(req.getActivityId(), req.getUserId(), "NO");
		if(beforeTimes != 0){
			List<BsActivityLuckyDrawVO> getUserLuckyList = activityLuckyDrawService.getUserLuckyList(req.getActivityId(), req.getUserId(), "NO", null, null);
			if(getUserLuckyList.get(0) != null){
				BsActivityLuckyDraw activityLuckyDraw = new BsActivityLuckyDraw();
				activityLuckyDraw.setId(getUserLuckyList.get(0).getId());
				
				Double amount = 0.0;
				switch (getUserLuckyList.get(0).getAwardId()) {
				case 1:
					amount = 5.0;
					break;
				case 2:
					amount = 10.0;
					break;
				case 3:
					amount = 30.0;
					break;
				case 4:
					amount = 50.0;
					break;
				case 5:
					amount = 80.0;
					break;

				default:  
					break;
				}
				if("Y".equals(getUserLuckyList.get(0).getIsAutoConfirm()) && amount.compareTo(0.0) >0){
					//自动兑奖 --发放奖励金
					
					BsBonusGrantPlan plan = new BsBonusGrantPlan();
					plan.setAmount(amount);
					plan.setBeRecommendUserId(req.getUserId());
					plan.setCreateTime(new Date());
					plan.setGrantDate(new Date());
					plan.setGrantType(Constants.BONUS_GRANT_TYPE_BONUS_4_ACTIVITY);//活动奖励
					plan.setSerialNo(1);
					plan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
					plan.setUpdateTime(new Date());
					plan.setUserId(req.getUserId());
					bonusGrantPlanMapper.insertSelective(plan);
					//首期发放即时到账

					userBonusGrantService.grantBonus(plan);
					
					//修改奖品发放状态
					activityLuckyDraw.setConfirmTime(new Date());
					activityLuckyDraw.setIsConfirm("Y");
				}
				
				activityLuckyDrawService.updateUserDraw(activityLuckyDraw);
				Integer afterTimes = activityLuckyDrawService.countUserLucky(req.getActivityId(), req.getUserId(), "NO");
				res.setAfterTimes(afterTimes);
				res.setAwardId(getUserLuckyList.get(0).getAwardId());
				res.setAwardContent(getUserLuckyList.get(0).getAwardContent());
			}
		}
		res.setBeforeTimes(beforeTimes);
		
	}
	
	/**
	 * 用户中奖列表-所有活动通用
	 * @param req
	 * @param res
	 */
	public void get618UserLuckyList(ReqMsg_ActivityLuckyDraw_Get618UserLuckyList req, ResMsg_ActivityLuckyDraw_Get618UserLuckyList res){
		Integer total = activityLuckyDrawService.countUserLucky(req.getActivityId(), req.getUserId(), "YES");
		List<BsActivityLuckyDrawVO> userLuckyList = new ArrayList<BsActivityLuckyDrawVO>();
		if(total > 0){
			if(req.getPageSize() == null || req.getStartPage() == null){
				userLuckyList = activityLuckyDrawService.getUserLuckyList(req.getActivityId(), req.getUserId(), "YES", null, null);
			}else{
				userLuckyList = activityLuckyDrawService.getUserLuckyList(req.getActivityId(), req.getUserId(), "YES", req.getStartPage()*req.getPageSize(), req.getPageSize());
			}
		}

		res.setLuckyList(BeanUtils.classToArrayList(userLuckyList));
		if(req.getStartPage() != null){
			res.setTotal((int)Math.ceil((double)total/req.getPageSize()));
			res.setPageIndex(req.getStartPage());
		}
		
		
	}

	/**
	 * 根据红包名称模糊判断是否领取过某红包
	 * @param req
	 * @param res
     */
	public void judgeUserDrawed161Packet(ReqMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet req, ResMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet res) {
		boolean drawed161Packet = activityLuckyDrawService.judgeUserDrawed161Packet(req.getUserId(), req.getRedPacketName());
		res.setDrawed161Packet(drawed161Packet);
	}

	/**
	 * 拆红包
	 * 拆红包获得161红包以及免费抽奖机会接口（奖品已经定了，bs_activity_lucky_draw存库）投资红包
	 */
	public void openPacket(ReqMsg_ActivityLuckyDraw_OpenPacket req, ResMsg_ActivityLuckyDraw_OpenPacket res) {
		BsActivity bsActivity = bsActivityMapper.selectByPrimaryKey(Constants.ACTIVITY_ID_DOUBLE11);
		if(null == bsActivity) {
			throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE.getCode(), "很抱歉，活动已下线");
		} else {
			if(bsActivity.getStartTime().compareTo(new Date()) >= 0) {
				throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE.getCode(), "很抱歉，活动未开始");
			} else if(bsActivity.getEndTime().compareTo(new Date()) < 0) {
				throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE.getCode(), "很抱歉，活动已结束");
			}
		}
		if(!activityLuckyDrawService.judgeUserDrawed161Packet(req.getUserId(), "双11专享红包")) {
			activityLuckyDrawService.openPacket(req.getUserId(), "双11专享红包");
			activityLuckyDrawService.saveRedPacketActivityLuckyDraw(req.getUserId(), Constants.ACTIVITY_ID_DOUBLE11, Constants.AWARD_RED_PACKET_ID);
		} else {
			throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE.getCode(), "很抱歉，您已经拆过红包");
		}
	}
	
	/**
	 * 2017年1月23日抽奖活动，每人只限一次
	 * @param req
	 * @param res
	 */
	public void draw20170123(ReqMsg_ActivityLuckyDraw_Draw20170123 req, ResMsg_ActivityLuckyDraw_Draw20170123 res){
		//查询已有的中奖记录条数
		Integer getLucky = activityLuckyDrawService.selectUserLuckyDraws(req.getUserId(), req.getActivityId());
		if(getLucky >= 1){
			res.setBeforeTimes(0);
		}else{
			//抽奖
			BsActivityLuckyDraw activityLuckyDraw = new BsActivityLuckyDraw();
			res.setBeforeTimes(1);
			BsUser user = userService.findUserById(req.getUserId());
			BsActivity activity = bsActivityMapper.selectByPrimaryKey(req.getActivityId());
			int luckyNum = LuckyDrawUtil.luckyNumber(); //随机获得的幸运数字[1,10000]
			String awardNote = "";
			if(user.getRegisterTime().compareTo(activity.getStartTime()) > 0){
				/**
				 * 新注册用户：
				 * 40%10元奖励金---41
				 * 20%100M流量---39
				 * 40%88元投资红包---38
				 */
				if(luckyNum<= 4000){
					activityLuckyDraw.setAwardId(41);
					awardNote = "10元奖励金";
				}else if(luckyNum<= 6000){
					activityLuckyDraw.setAwardId(39);
					awardNote = "100M流量";
				}else{
					activityLuckyDraw.setAwardId(38);
					awardNote = "88元红包";
				}
			}else{
				/**
				 * 老用户：
				 * 5%10元奖励金---41
				 * 7%300M流量---40
				 * 23%100M流量---39
				 * 25%88元投资红包---38
				 * 40%58元投资红包---37
				 */
				if(luckyNum<= 500){
					activityLuckyDraw.setAwardId(41);
					awardNote = "10元奖励金";
				}else if(luckyNum<= 1200){
					activityLuckyDraw.setAwardId(40);
					awardNote = "300M流量";
				}else if(luckyNum<= 3500){
					activityLuckyDraw.setAwardId(39);
					awardNote = "100M流量";
				}else if(luckyNum<= 6000){
					activityLuckyDraw.setAwardId(38);
					awardNote = "88元红包";
				}else{
					activityLuckyDraw.setAwardId(37);
					awardNote = "58元红包";
				}
			}
			activityLuckyDraw.setActivityId(req.getActivityId());
			activityLuckyDraw.setIsAutoConfirm("N");//是否自动兑奖--N
			activityLuckyDraw.setUserId(req.getUserId());
            activityLuckyDraw.setIsBackDraw("Y");//是否后台抽奖-Y
            activityLuckyDraw.setBackDrawTime(new Date());
            activityLuckyDraw.setIsUserDraw("Y");//用户是否抽奖-Y
            activityLuckyDraw.setIsWin("Y");//是否中奖-Y
            activityLuckyDraw.setIsConfirm("N");//是否兑奖-N
            activityLuckyDraw.setUserDrawTime(new Date());
            activityLuckyDraw.setCreateTime(new Date());
            activityLuckyDraw.setUpdateTime(new Date());
            activityLuckyDraw.setNote("大转盘抽奖活动");
            bsActivityLuckyDrawMapper.insertSelective(activityLuckyDraw);
            res.setAfterTimes(0);
            BsActivityAward activityAward = bsActivityAwardMapper.selectByPrimaryKey(activityLuckyDraw.getAwardId());
            //发送短信
            smsServiceClient.sendTemplateMessage(user.getMobile(), TemplateKey.ACTIVITY_WINNING_NOTICE, awardNote);
            res.setAwardId(activityLuckyDraw.getAwardId());
            res.setAwardContent(activityAward.getContent());
		}
	
	}
	
	/**
	 * 三重好礼-二重礼的投资排行榜
	 * @param req
	 * @param res
	 */
	public void getTrebleGiftInvestList(ReqMsg_ActivityLuckyDraw_GetTrebleGiftInvestList req,
			ResMsg_ActivityLuckyDraw_GetTrebleGiftInvestList res){
		BsActivity active = bsActivityMapper.selectByPrimaryKey(req.getActivityId());
		Date now = new Date();
		if(now.compareTo(active.getStartTime()) > 0){
			//活动开始，查询起息日为活动开始后一天到当前时间后一天的前十名投资信息
			Date startDate = DateUtil.addDays(DateUtil.parseDate(DateUtil.formatYYYYMMDD(active.getStartTime())), 1);
			//当前时间的后一天
			Date tomorrowDate = DateUtil.addDays(DateUtil.parseDate(DateUtil.formatYYYYMMDD(now)), 1);
			//结束时间，活动未结束则为当前时间后一天，已结束则是活动结束时间的后一天
			Date endDate = tomorrowDate;
			if(now.compareTo(active.getEndTime()) > 0){
				//活动结束
				endDate = DateUtil.addDays(DateUtil.parseDate(DateUtil.formatYYYYMMDD(active.getEndTime())), 1);
			}
			Date selectDate = endDate;
			List<TrebleGiftListVO> historyList = new ArrayList<TrebleGiftListVO>();
			while(selectDate.compareTo(startDate) >= 0 && selectDate.compareTo(endDate) <= 0){
				//查询数据，若查询selectDate = tomorrowDate，则为当日的投资排行，其他记录到历史排行
				List<PlayerKillingVO> list = bsSubAccountMapper.queryTrebleGiftUserInvestList(selectDate,null);
				if(CollectionUtils.isNotEmpty(list)){
					if(selectDate.compareTo(endDate) == 0 && endDate.compareTo(tomorrowDate) == 0){
						res.setTodayList(BeanUtils.classToArrayList(list));
					}else{
						TrebleGiftListVO vo = new TrebleGiftListVO();
						vo.setSelectDate(DateUtil.formatDateTime(DateUtil.addDays(selectDate, -1),"yyyy年MM月dd日"));
						vo.setList(BeanUtils.classToArrayList(list));
						vo.setListSize(list.size());
						historyList.add(vo);
					}
				}
				selectDate = DateUtil.addDays(selectDate, -1);
			}
			if(CollectionUtils.isNotEmpty(historyList)){
				res.setHistoryList(BeanUtils.classToArrayList(historyList));
			}
			if(req.getUserId() != null){
				List<PlayerKillingVO> userList = bsSubAccountMapper.queryTrebleGiftUserInvestList(endDate,req.getUserId());
				if(CollectionUtils.isNotEmpty(userList)){
					res.setBuyAmount(userList.get(0).getBuyAmount());
				}else{
					res.setBuyAmount(0d);
				}
			}
			
		}
		
	}
	/**
	 * 三重好礼-活动红包
	 * @param req
	 * @param res
	 */
	public void trebleGiftOpenPacket(ReqMsg_ActivityLuckyDraw_TrebleGiftOpenPacket req, ResMsg_ActivityLuckyDraw_TrebleGiftOpenPacket res){
		BsActivity bsActivity = bsActivityMapper.selectByPrimaryKey(req.getActivityId());
		if(null == bsActivity) {
			throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE.getCode(), "很抱歉，活动已下线");
		} else {
			if(bsActivity.getStartTime().compareTo(new Date()) >= 0) {
				throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE.getCode(), "很抱歉，活动未开始");
			} else if(bsActivity.getEndTime().compareTo(new Date()) < 0) {
				throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE.getCode(), "很抱歉，活动已结束");
			}
		}
		if(!activityLuckyDrawService.judgeUserDrawed161Packet(req.getUserId(), req.getRedPacketName())) {
			activityLuckyDrawService.openPacket(req.getUserId(), req.getRedPacketName());
		} else {
			throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE.getCode(), "很抱歉，您已经拆过红包");
		}
	}


















}
