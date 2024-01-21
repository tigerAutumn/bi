package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsActivityAwardMapper;
import com.pinting.business.dao.BsActivityLuckyDrawMapper;
import com.pinting.business.dao.BsActivityMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.hessian.manage.message.ReqMsg_BsActivityLuckyDraw_GetList;
import com.pinting.business.hessian.manage.message.ResMsg_BsActivityLuckyDraw_GetList;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.manage.BsActivityLuckyDrawService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.LuckyDrawUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BsActivityLuckyDrawServiceImpl implements
		BsActivityLuckyDrawService {
	private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	private Logger log = LoggerFactory.getLogger(BsActivityLuckyDrawServiceImpl.class);

	@Autowired
	private BsActivityLuckyDrawMapper bsActivityLuckyDrawMapper;
	@Autowired
	private BsActivityMapper bsActivityMapper;
	@Autowired
	private BsActivityAwardMapper bsActivityAwardMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	
	@Override
	public void selectList(ReqMsg_BsActivityLuckyDraw_GetList req,
			ResMsg_BsActivityLuckyDraw_GetList res) {
		String agentIdStr = req.getAgents();
		List<Object> agentIds = null;
		if(StringUtils.isNotBlank(req.getAgents())){
			if(agentIdStr.endsWith(",")){
				agentIdStr = agentIdStr.substring(0,agentIdStr.length()-1);
			}
			
			if(agentIdStr.indexOf("-1") >=0){
				//所有用户，有普通用户又有渠道用户
				req.setAgentsFlag("-2");
				req.setAgents(agentIdStr.substring(5));
				String[] ids = req.getAgents().split(",");
				if(ids.length > 0) {
					agentIds = new ArrayList<Object>();
					for (String str : ids) {
						if(StringUtil.isNotEmpty(str)) {
							agentIds.add(Integer.valueOf(str));
						}
					}
				}
				req.setAgents(null);
			}else if(req.getAgents().startsWith("0") && req.getAgents().length()>2){
				//有普通用户又有渠道用户
				req.setAgentsFlag("-2");
				req.setAgents(agentIdStr.substring(2));
				String[] ids = req.getAgents().split(",");
				if(ids.length > 0) {
					agentIds = new ArrayList<Object>();
					for (String str : ids) {
						if(StringUtil.isNotEmpty(str)) {
							agentIds.add(Integer.valueOf(str));
						}
					}
				}
			}else if(req.getAgents().startsWith("0") && req.getAgents().length()== 2){
				//只有普通用户
				req.setAgentsFlag("-1");
			}else if(!req.getAgents().startsWith("0")){
				//只有渠道用户
				String[] ids = req.getAgents().split(",");
				if(ids.length > 0) {
					agentIds = new ArrayList<Object>();
					for (String str : ids) {
						if(StringUtil.isNotEmpty(str)) {
							agentIds.add(Integer.valueOf(str));
						}
					}
				}
			}
		}

		String mobile = req.getMobile();
		String awardContent = req.getAwardContent();
		String userName = req.getUserName();
		if(StringUtil.isNotEmpty(mobile)) {
			mobile = mobile.trim();
		}
		if(StringUtil.isNotEmpty(awardContent)) {
			awardContent = awardContent.trim();
		}
		if(StringUtil.isNotEmpty(userName)) {
			userName = userName.trim();
		}
		List<BsActivityLuckyDrawVO> list = new ArrayList<BsActivityLuckyDrawVO>();	
		int count = bsActivityLuckyDrawMapper.countUserLuckyList4Manage(agentIds, req.getAgentsFlag(), mobile,
				req.getDrawTimeStart(), req.getDrawTimeEnd(), req.getIsUserDraw(), awardContent, req.getIsWin(),
				userName, req.getActivityName());
		res.setTotalRows(count);
		
		//判断是否是加薪计划,加薪计划需用另一条sql
		if("38".equals(req.getActivityName())) {
			if(count > 0){
				list = bsActivityLuckyDrawMapper.getIncreaseSalaryInfoForExcel(agentIds, req.getAgentsFlag(), mobile,
						req.getDrawTimeStart(), req.getDrawTimeEnd(), req.getIsUserDraw(), awardContent,
						req.getIsWin(), req.getStart(), req.getNumPerPage(), userName, req.getActivityName());
				}
		}else {
			
			if(count > 0){
			list = bsActivityLuckyDrawMapper.getUserLuckyList4Manage(agentIds, req.getAgentsFlag(), mobile,
					req.getDrawTimeStart(), req.getDrawTimeEnd(), req.getIsUserDraw(), awardContent,
					req.getIsWin(), req.getStart(), req.getNumPerPage(), userName, req.getActivityName());
			}
		}
		res.setValueList(BeanUtils.classToArrayList(list));
		
	}

	@Override
	public boolean activityStart(int activityId) {
		BsActivity bsActivity = bsActivityMapper.selectByPrimaryKey(Constants.ACTIVITY_ID_DOUBLE11);
		if(null == bsActivity) {
			return false;
		} else {
			if(bsActivity.getStartTime().compareTo(new Date()) >= 0) {
				// 未开始
				return false;
			} else if(bsActivity.getEndTime().compareTo(new Date()) < 0) {
				// 已结束
				return false;
			}
		}
		return true;
	}

	@Override
	public List<ActivityScratchcardAwardVO> userAwardList() {
		// 当前时间所在周的星期一基础上加3天就是当前周的活动时间
		String startTime = com.pinting.core.util.DateUtil.format(DateUtil.getDateBegin(DateUtil.getDateByWeek(new Date(), 3)));
		String endTime = com.pinting.core.util.DateUtil.format(DateUtil.getDateEnd(DateUtil.getDateByWeek(new Date(), 3)));
		return bsActivityLuckyDrawMapper.userAwardList(Constants.ACTIVITY_ID_2017_SCRATCH_CARD, startTime, endTime);
	}
	
	@Override
	public Integer countUserAwardNum(BsActivityLuckyDraw luckyDraw) {
		BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
		example.createCriteria().andUserIdEqualTo(luckyDraw.getUserId()).andActivityIdEqualTo(luckyDraw.getActivityId())
		.andCreateTimeBetween(DateUtil.getDateBegin(DateUtil.getDateByWeek(luckyDraw.getCreateTime(), 3)), DateUtil.getDateEnd(DateUtil.getDateByWeek(luckyDraw.getCreateTime(), 3)));
		List<BsActivityLuckyDraw> list = bsActivityLuckyDrawMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(list)) {
			return 0;
		}
		return list.size();
	}

	@Override
	public String userScratch(Integer userId, int activityId, Double investAmount) {
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());
			// 刮奖数据校验
			// 1. 是否活动时间内
			// 2. 用户是否刮过奖
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
			if(week < Constants.WEEKHAY_WEEK_4) {
				return "本期尚未开始";
			} else if(week > Constants.WEEKHAY_WEEK_4) {
				return "本期已结束";
			}
			BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
			if(week == Constants.WEEKHAY_WEEK_4) {
				luckyDraw.setCreateTime(new Date());
			}
			luckyDraw.setUserId(userId);
			luckyDraw.setActivityId(Constants.ACTIVITY_ID_2017_SCRATCH_CARD);
			if (countUserAwardNum(luckyDraw) > 0) {
				return "您已参与刮奖";
			}
			// 刮奖奖品及概率实现，保存中奖信息
			String awardNote = getGuaguaLuckyAward(hasScratchChance(userId).getYearsInvestAmount());
			BsActivityLuckyDraw record = new BsActivityLuckyDraw();
			BsActivityAwardExample example = new BsActivityAwardExample();
			example.createCriteria().andActivityIdEqualTo(activityId).andContentEqualTo(awardNote);
			List<BsActivityAward> list = bsActivityAwardMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(list)) {
				log.info("userScratch method, awardId:{}", list.get(0).getId());
				record.setAwardId(list.get(0).getId());
				record.setNote(awardNote);
				record.setIsWin("Y");     //是否中奖-Y
			} else {
				record.setAwardId(null);
				record.setNote("未中奖");
				record.setIsWin("N");     //是否中奖-N
			}
			record.setUserId(userId);
			record.setActivityId(activityId);
			record.setIsBackDraw("N");//是否后台抽奖-N
			record.setIsUserDraw("Y");//用户是否抽奖-Y
			record.setUserDrawTime(new Date());
			record.setIsConfirm("N"); //是否兑奖-N
			record.setIsAutoConfirm("N"); //是否自动兑奖-N
			record.setCreateTime(new Date());
			record.setUpdateTime(new Date());
			bsActivityLuckyDrawMapper.insertSelective(record);
			return record.getNote();
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());
		}
	}

	/**
	 * 获得用户刮奖奖品
	 * @param investAmount
	 * @return
	 */
	private String getGuaguaLuckyAward(Double investAmount) {
		int luckyNum = LuckyDrawUtil.luckyNumber(); //随机获得的幸运数字[1,10000]
		log.info("getLuckyAward method, luckyNum:{}", luckyNum);
		String awardNote = "";
		if (investAmount.compareTo(1000d) < 0) {
			if(luckyNum <= 3500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_TENTH;
			} else if (luckyNum <= 6500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_NINTH;
			} else if (luckyNum <= 9000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_EIGHTH;
			} else if (luckyNum <= 9500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SEVENTH;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SIXTH;
			}
		} else if (investAmount.compareTo(10000d) < 0) {
			if(luckyNum <= 2000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_TENTH;
			} else if (luckyNum <= 4000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_NINTH;
			} else if (luckyNum <= 6500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SEVENTH;
			} else if (luckyNum <= 9000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SIXTH;
			} else if (luckyNum <= 9500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIFTH;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;
			}
		} else if (investAmount.compareTo(50000d) < 0) {
			if(luckyNum <= 1000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_NINTH;
			} else if (luckyNum <= 2500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SIXTH;
			} else if (luckyNum <= 6000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIFTH;
			} else if (luckyNum <= 9500){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_THIRD;
			}
		} else if (investAmount.compareTo(100000d) < 0) {
			if(luckyNum <= 1000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_NINTH;
			} else if (luckyNum <= 1500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SIXTH;
			} else if (luckyNum <= 2000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIFTH;
			} else if (luckyNum <= 4000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;
			} else if (luckyNum <= 7000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_THIRD;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SECOND;
			}
		} else if (investAmount.compareTo(200000d) < 0) {
			if(luckyNum <= 1000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_NINTH;
			} else if (luckyNum <= 2500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;		
			} else if (luckyNum <= 5500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_THIRD;
			} else if (luckyNum <= 9000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SECOND;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIRST;
			}
		} else {
			if(luckyNum <= 500){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;
			} else if (luckyNum <= 3000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_THIRD;			
			} else if (luckyNum <= 6500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SECOND;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIRST;
			}
		}
		log.info("getLuckyAward method, awardNote:{}", awardNote);
		return awardNote;
	}

	
	/**
	 * 获得用户刮奖奖品
	 * @param investAmount
	 * @return
	 */
	private String getLuckyAward(Double investAmount) {
		int luckyNum = LuckyDrawUtil.luckyNumber(); //随机获得的幸运数字[1,10000]
		log.info("getLuckyAward method, luckyNum:{}", luckyNum);
		String awardNote = "";
		if (investAmount.compareTo(1000d) < 0) {
			if(luckyNum <= 6000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_EIGHTH;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SEVENTH;
			}
		} else if (investAmount.compareTo(10000d) < 0) {
			if(luckyNum <= 3000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_EIGHTH;
			} else if (luckyNum <= 6000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SEVENTH;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SIXTH;
			}
		} else if (investAmount.compareTo(50000d) < 0) {
			if(luckyNum <= 1000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SEVENTH;
			} else if (luckyNum <= 3000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SIXTH;
			} else if (luckyNum <= 6000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIFTH;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;
			}
		} else if (investAmount.compareTo(100000d) < 0) {
			if(luckyNum <= 500){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIFTH;
			} else if (luckyNum <= 3000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;
			} else if (luckyNum <= 6000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_THIRD;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SECOND;
			}
		} else if (investAmount.compareTo(200000d) < 0) {
			if(luckyNum <= 2000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;
			} else if (luckyNum <= 6000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_THIRD;			
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SECOND;
			}
		} else {
			if(luckyNum <= 2000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_THIRD;
			} else if (luckyNum <= 6000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SECOND;			
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIRST;
			}
		}
		log.info("getLuckyAward method, awardNote:{}", awardNote);
		return awardNote;
	}

	@Override
	public ActivityScratchcardChanceVO hasScratchChance(Integer userId) {
		// 活动时间内用户的投资信息
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(week != Constants.WEEKHAY_WEEK_4) {
			return null;
		}
		// 当前时间所在周的星期一基础上加3天就是当前周的活动时间
		String startTime = com.pinting.core.util.DateUtil.format(DateUtil.getDateBegin(DateUtil.getDateByWeek(new Date(), 3)));
		String endTime = com.pinting.core.util.DateUtil.format(DateUtil.getDateEnd(DateUtil.getDateByWeek(new Date(), 3)));
		log.info("hasScratchChance method, startTime:{}, endTime:{}", startTime, endTime);
		BsSubAccountVO subAccountVO = bsSubAccountMapper.selectInvestInfoByActivity(userId, startTime, endTime);
		ActivityScratchcardChanceVO scratchcardChanceVO = new ActivityScratchcardChanceVO();
		if (subAccountVO != null && subAccountVO.getBalance().compareTo(0d) != 0) {
			scratchcardChanceVO.setHasScratchChance(true);
			Double yearsInvestAmount = MoneyUtil.divide(MoneyUtil.multiply(subAccountVO.getBalance(), subAccountVO.getInvestDay()).doubleValue(),
					365).doubleValue();
			log.info("hasScratchChance method, yearsInvestAmount:{}", yearsInvestAmount);
			scratchcardChanceVO.setYearsInvestAmount(yearsInvestAmount);
		} else {
			scratchcardChanceVO.setHasScratchChance(false);
			scratchcardChanceVO.setYearsInvestAmount(0d);
		}
		return scratchcardChanceVO;
	}

	@Override
	public ActivityScratchcardPrizeVO scratchcardPrize(Integer userId) {
		// 当前时间所在周的星期一基础上加3天就是当前周的活动时间
		String startTime = com.pinting.core.util.DateUtil.format(DateUtil.getDateBegin(DateUtil.getDateByWeek(new Date(), 3)));
		String endTime = com.pinting.core.util.DateUtil.format(DateUtil.getDateEnd(DateUtil.getDateByWeek(new Date(), 3)));
		return bsActivityLuckyDrawMapper.scratchcardPrize(userId, startTime, endTime);
	}
	
}
