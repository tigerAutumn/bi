package com.pinting.business.service.site.impl;

import com.pinting.business.accounting.finance.service.UserBonusGrantService;
import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.facade.site.enums.ActivityEnum;
import com.pinting.business.hessian.site.message.ReqMsg_Activity_SalaryIncreasePlan;
import com.pinting.business.hessian.site.message.ResMsg_Activity_SalaryIncreasePlan;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.RedPacketService;
import com.pinting.business.service.site.TicketInterestService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.LuckyDrawUtil;
import com.pinting.business.util.MobileCheckUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.*;

/**
 * Author:      cyb
 * Date:        2017/1/24
 * Description: 活动服务
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    private final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private BsActivity2017anniversaryUserBenisonMapper bsActivity2017anniversaryUserBenisonMapper;
    @Autowired
    private BsActivity2017anniversaryWxSurpportMapper bsActivity2017anniversaryWxSurpportMapper;
    @Autowired
    private BsWxInfoMapper bsWxInfoMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsActivityMapper bsActivityMapper;
    @Autowired
    private BsActivityLuckyDrawMapper bsActivityLuckyDrawMapper;
    @Autowired
    private BsActivityAwardMapper bsActivityAwardMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsBonusGrantPlanMapper bonusGrantPlanMapper;
    @Autowired
    private UserBonusGrantService userBonusGrantService;
    @Autowired
    private RedPacketService redPacketService;
    @Autowired
    private BsUserReceiveAddressMapper bsUserReceiveAddressMapper;
    @Autowired
    private SMSServiceClient smsServiceClient;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsActivityJoinTeamMapper bsActivityJoinTeamMapper;
    @Autowired
    private BsActivityTeamMapper bsActivityTeamMapper;
    @Autowired
    private BsRedPacketCheckMapper bsRedPacketCheckMapper;
    @Autowired
    private BsProductMapper bsProductMapper;
    @Autowired
    private Bs2016CheckInUserMapper bs2016CheckInUserMapper;
    @Autowired
    private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
    private Bs2016AnnualMeetingEmployeeMapper bs2016AnnualMeetingEmployeeMapper;
	@Autowired
    private TicketInterestService ticketInterestService;

    // ==================================================== 活动公共方法开始 =========================================================
    @Override
    public String duringActivity(Integer activityId) {
        BsActivity activity = bsActivityMapper.selectByPrimaryKey(activityId);
        return this.duringActivity(activity);
    }

    private String duringActivity(BsActivity activity) {
        if(activity.getStartTime().after(new Date())) {
            log.info("{} 未开始。开始时间：{}；结束时间：{}", activity.getName(), DateUtil.format(activity.getStartTime()), DateUtil.format(activity.getEndTime()));
            return Constants.ACTIVITY_IS_NOT_START;
        }
        if(activity.getEndTime().before(new Date())) {
            log.info("{} 已结束。开始时间：{}；结束时间：{}", activity.getName(), DateUtil.format(activity.getStartTime()), DateUtil.format(activity.getEndTime()));
            return Constants.ACTIVITY_IS_END;
        }
        return Constants.ACTIVITY_IS_START;
    }

    @Override
    public String duringActivity(Integer activityId, Integer preDays, Integer toEndDays) {
        Date now = new Date();
        BsActivity activity = bsActivityMapper.selectByPrimaryKey(activityId);
        if(activity.getStartTime().after(now)) {
            if(null != preDays) {
                if(DateUtil.addDays(now, preDays).after(activity.getStartTime())) {
                    log.info("{} 预热中。开始时间：{}；结束时间：{}", activity.getName(), DateUtil.format(activity.getStartTime()), DateUtil.format(activity.getEndTime()));
                    return Constants.ACTIVITY_IS_NOT_START_PRE;
                } else {
                    log.info("{} 未开始。开始时间：{}；结束时间：{}", activity.getName(), DateUtil.format(activity.getStartTime()), DateUtil.format(activity.getEndTime()));
                    return Constants.ACTIVITY_IS_NOT_START;
                }
            } else {
                log.info("{} 未开始。开始时间：{}；结束时间：{}", activity.getName(), DateUtil.format(activity.getStartTime()), DateUtil.format(activity.getEndTime()));
                return Constants.ACTIVITY_IS_NOT_START;
            }
        }
        if(activity.getEndTime().before(now)) {
            log.info("{} 已结束。开始时间：{}；结束时间：{}", activity.getName(), DateUtil.format(activity.getStartTime()), DateUtil.format(activity.getEndTime()));
            return Constants.ACTIVITY_IS_END;
        }
        if(null != toEndDays) {
            if(DateUtil.addDays(now, toEndDays).after(activity.getEndTime())) {
                log.info("{} 即将结束。开始时间：{}；结束时间：{}", activity.getName(), DateUtil.format(activity.getStartTime()), DateUtil.format(activity.getEndTime()));
                return Constants.ACTIVITY_IS_WILL_END;
            }
        }
        return Constants.ACTIVITY_IS_START;
    }

    @Override
    public String duringActivity(Date startTime, Date endTime, Integer preDays, Integer toEndDays) {
        Date now = new Date();
        if(startTime.after(now)) {
            if(null != preDays) {
                if(DateUtil.addDays(now, preDays).after(startTime)) {
                    log.info("活动预热中。开始时间：{}；结束时间：{}", startTime, endTime);
                    return Constants.ACTIVITY_IS_NOT_START_PRE;
                } else {
                    log.info("活动未开始。开始时间：{}；结束时间：{}", startTime, endTime);
                    return Constants.ACTIVITY_IS_NOT_START;
                }
            } else {
                log.info("活动未开始。开始时间：{}；结束时间：{}", startTime, endTime);
                return Constants.ACTIVITY_IS_NOT_START;
            }
        }
        if(endTime.before(now)) {
            log.info("活动已结束。开始时间：{}；结束时间：{}", startTime, endTime);
            return Constants.ACTIVITY_IS_END;
        }
        if(null != toEndDays) {
            if(DateUtil.addDays(now, toEndDays).after(endTime)) {
                log.info("活动即将结束。开始时间：{}；结束时间：{}", startTime, endTime);
                return Constants.ACTIVITY_IS_WILL_END;
            }
        }
        return Constants.ACTIVITY_IS_START;
    }

    @Override
    public String duringActivity(String startTime, String endTime) {
        Date start = DateUtil.parse(startTime, "yyyy-MM-dd HH:mm:ss");
        Date end = DateUtil.parse(endTime, "yyyy-MM-dd HH:mm:ss");
        if(start.after(new Date())) {
            log.info("{} 未开始。开始时间：{}；结束时间：{}", startTime, endTime);
            return Constants.ACTIVITY_IS_NOT_START;
        }
        if(end.before(new Date())) {
            log.info("{} 已结束。开始时间：{}；结束时间：{}", startTime, endTime);
            return Constants.ACTIVITY_IS_END;
        }
        return Constants.ACTIVITY_IS_START;
    }

    @Override
    public BsActivityVO queryActivity(Integer activityId) {
        BsActivity activity = bsActivityMapper.selectByPrimaryKey(activityId);
        BsActivityVO result = new BsActivityVO();
        result.setStartTime(DateUtil.format(activity.getStartTime()));
        result.setEndTime(DateUtil.format(activity.getEndTime()));
        log.info("{} 的开始时间：{}；结束时间：{}", activity.getName(), result.getStartTime(), result.getEndTime());
        return result;
    }

    @Override
    public ActivityBaseVO queryBaseActivity(Integer activityId, Integer userId) {
        BsActivity activity = bsActivityMapper.selectByPrimaryKey(activityId);
        ActivityBaseVO result = new ActivityBaseVO();
        result.setStartTime(DateUtil.format(activity.getStartTime()));
        result.setEndTime(DateUtil.format(activity.getEndTime()));
        result.setIsStart(this.duringActivity(activity));
        result.setIsLogin(null == userId ? Constants.IS_LOGIN_NO : Constants.IS_LOGIN_YES);
        if(null != userId) {
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            if(null == bsUser) {
                result.setIsLogin(Constants.IS_LOGIN_NO);
            }
        }
        log.info("{} 的开始时间：{}；结束时间：{}", activity.getName(), result.getStartTime(), result.getEndTime());
        return result;
    }

    @Override
    public int compareDate(String time1, String time2) {
        Date date1 = DateUtil.parse(time1, "yyyy-MM-dd HH:mm:ss");
        Date date2 = DateUtil.parse(time2, "yyyy-MM-dd HH:mm:ss");
        return date1.compareTo(date2);
    }

    @Override
    public void revertLuckyDraw(int activityId) {
        switch (activityId) {
            case Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE:
                BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
                example.createCriteria().andActivityIdEqualTo(activityId);
                bsActivityLuckyDrawMapper.deleteByExample(example);
                break;
            case Constants.ACTIVITY_ID_2018_YEAR_END:
                bs2016AnnualMeetingEmployeeMapper.revertWin();
                break;
            default:
                break;
        }
    }

    @Override
    public String duringActivity(Date date, int activityId) {
        BsActivity activity = bsActivityMapper.selectByPrimaryKey(activityId);
        if(activity.getStartTime().after(date)) {
            log.info("比较时间：{}；开始时间：{}；结束时间：{}。开始时间大于比较时间", DateUtil.format(date), DateUtil.format(activity.getStartTime()), DateUtil.format(activity.getEndTime()));
            return Constants.ACTIVITY_IS_NOT_START;
        }
        if(activity.getEndTime().before(date)) {
            log.info("比较时间：{}；开始时间：{}；结束时间：{}。结束时间小于比较时间", DateUtil.format(date), DateUtil.format(activity.getStartTime()), DateUtil.format(activity.getEndTime()));
            return Constants.ACTIVITY_IS_END;
        }
        return Constants.ACTIVITY_IS_START;
    }

    @Override
    public NewYear2018FirstVO newYear2018FirstInfo(Integer userId) {
        ActivityBaseVO activityBaseVO = this.queryBaseActivity(Constants.ACTIVITY_ID_2018_NEW_YEAR_FIRST, userId);
        NewYear2018FirstVO result = new NewYear2018FirstVO();
        BeanUtils.copyProperties(activityBaseVO, result);
        if(null != userId) {
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                    .andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_FIRST);
            int count = bsActivityLuckyDrawMapper.countByExample(example);
            result.setIsJoined(count > 0 ? Constants.IS_YES : Constants.IS_NO);
        }
        return result;
    }

    @Override
    public NewYear2018SecondVO newYear2018SecondInfo(Integer userId) {
        ActivityBaseVO activityBaseVO = this.queryBaseActivity(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND, userId);
        NewYear2018SecondVO result = new NewYear2018SecondVO();
        BeanUtils.copyProperties(activityBaseVO, result);
        List<ActivityBaseVO> activityBaseVOList = new ArrayList<>();

        String giftNumber1 = "100";
        String giftNumber2 = "100";
        String giftNumber3 = "100";
        String giftNumber4 = "100";
        BsSysConfig giftConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.SYS_CONFIG_NEW_YEAR_2018_GIFTS_NUMBER);
        if(giftConfig != null && StringUtil.isNotBlank(giftConfig.getConfValue())) {
            String[] numbers = StringUtil.split(giftConfig.getConfValue(), ",");
            if(numbers.length >= 4) {
                giftNumber1 = numbers[0];giftNumber2 = numbers[1];giftNumber3 = numbers[2];giftNumber4 = numbers[3];
            } else if(numbers.length == 3) {
                giftNumber1 = numbers[0];giftNumber2 = numbers[1];giftNumber3 = numbers[2];
            } else if(numbers.length == 2) {
                giftNumber1 = numbers[0];giftNumber2 = numbers[1];
            } else if(numbers.length == 1) {
                giftNumber1 = numbers[0];
            }
        }

        String time1 = "2018-02-15 18:00:00";
        String time2 = "2018-02-15 20:00:00";
        String time3 = "2018-02-15 22:00:00";
        String time4 = "2018-02-16 00:00:00";
        BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.SYS_CONFIG_KEYNEW_YEAR_2018_TIMES);
        if(config != null && StringUtil.isNotBlank(config.getConfValue())) {
            String[] times = StringUtil.split(config.getConfValue(), ",");
            if(times.length >= 4) {
                time1 = times[0];time2 = times[1];time3 = times[2];time4 = times[3];
            } else if(times.length == 3) {
                time1 = times[0];time2 = times[1];time3 = times[2];
            } else if(times.length == 2) {
                time1 = times[0];time2 = times[1];
            } else if(times.length == 1) {
                time1 = times[0];
            }
        }

        ActivityBaseVO activityBaseVO18 = new ActivityAnswerInfoVO();
        activityBaseVO18.setStartTime(time1);
        activityBaseVO18.setEndTime(DateUtil.format(DateUtil.addSeconds(DateUtil.parse(time2, "yyyy-MM-dd HH:mm:ss"), -1)));
        activityBaseVO18.setIsStart(this.duringActivity(activityBaseVO18.getStartTime(), activityBaseVO18.getEndTime()));
        List<Integer> awardIdList18 = new ArrayList<>();
        awardIdList18.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_88);
        awardIdList18.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_188);
        awardIdList18.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_888);
        BsActivityLuckyDrawExample example18 = new BsActivityLuckyDrawExample();
        example18.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                .andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND)
                .andAwardIdIn(awardIdList18);
        int count18 = bsActivityLuckyDrawMapper.countByExample(example18);
        activityBaseVO18.setNote(String.valueOf(Integer.parseInt(giftNumber1) - count18));
        if(null != userId) {
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                    .andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND)
                    .andAwardIdIn(awardIdList18);
            int count = bsActivityLuckyDrawMapper.countByExample(example);
            activityBaseVO18.setIsJoined(count > 0 ? Constants.IS_YES : Constants.IS_NO);
        }
        activityBaseVOList.add(activityBaseVO18);

        ActivityBaseVO activityBaseVO20 = new ActivityAnswerInfoVO();
        activityBaseVO20.setStartTime(time2);
        activityBaseVO20.setEndTime(DateUtil.format(DateUtil.addSeconds(DateUtil.parse(time3, "yyyy-MM-dd HH:mm:ss"), -1)));
        activityBaseVO20.setIsStart(this.duringActivity(activityBaseVO20.getStartTime(), activityBaseVO20.getEndTime()));
        List<Integer> awardIdList20 = new ArrayList<>();
        awardIdList20.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_88);
        awardIdList20.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_188);
        awardIdList20.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_888);
        BsActivityLuckyDrawExample example20 = new BsActivityLuckyDrawExample();
        example20.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                .andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND)
                .andAwardIdIn(awardIdList20);
        int count20 = bsActivityLuckyDrawMapper.countByExample(example20);
        activityBaseVO20.setNote(String.valueOf(Integer.parseInt(giftNumber2) - count20));
        if(null != userId) {
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                    .andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND)
                    .andAwardIdIn(awardIdList20);
            int count = bsActivityLuckyDrawMapper.countByExample(example);
            activityBaseVO20.setIsJoined(count > 0 ? Constants.IS_YES : Constants.IS_NO);
        }
        activityBaseVOList.add(activityBaseVO20);

        ActivityBaseVO activityBaseVO22 = new ActivityAnswerInfoVO();
        activityBaseVO22.setStartTime(time3);
        activityBaseVO22.setEndTime(DateUtil.format(DateUtil.addSeconds(DateUtil.parse(time4, "yyyy-MM-dd HH:mm:ss"), -1)));
        activityBaseVO22.setIsStart(this.duringActivity(activityBaseVO22.getStartTime(), activityBaseVO22.getEndTime()));
        List<Integer> awardIdList22 = new ArrayList<>();
        awardIdList22.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_88);
        awardIdList22.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_188);
        awardIdList22.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_888);
        BsActivityLuckyDrawExample example22 = new BsActivityLuckyDrawExample();
        example22.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                .andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND)
                .andAwardIdIn(awardIdList22);
        int count22 = bsActivityLuckyDrawMapper.countByExample(example22);
        activityBaseVO22.setNote(String.valueOf(Integer.parseInt(giftNumber3) - count22));
        if(null != userId) {
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                    .andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND)
                    .andAwardIdIn(awardIdList22);
            int count = bsActivityLuckyDrawMapper.countByExample(example);
            activityBaseVO22.setIsJoined(count > 0 ? Constants.IS_YES : Constants.IS_NO);
        }
        activityBaseVOList.add(activityBaseVO22);

        ActivityBaseVO activityBaseVO24 = new ActivityAnswerInfoVO();
        activityBaseVO24.setStartTime(time4);
        Date endTime = DateUtil.addHours(DateUtil.parse(time4, "yyyy-MM-dd HH:mm:ss"), 1);
        activityBaseVO24.setEndTime(DateUtil.format(DateUtil.addSeconds(endTime, -1)));
        activityBaseVO24.setIsStart(this.duringActivity(activityBaseVO24.getStartTime(), activityBaseVO24.getEndTime()));
        List<Integer> awardIdList24 = new ArrayList<>();
        awardIdList24.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_88);
        awardIdList24.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_188);
        awardIdList24.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_888);
        BsActivityLuckyDrawExample example24 = new BsActivityLuckyDrawExample();
        example24.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                .andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND)
                .andAwardIdIn(awardIdList24);
        int count24 = bsActivityLuckyDrawMapper.countByExample(example24);
        activityBaseVO24.setNote(String.valueOf(Integer.parseInt(giftNumber4) - count24));
        if(null != userId) {
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                    .andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND)
                    .andAwardIdIn(awardIdList24);
            int count = bsActivityLuckyDrawMapper.countByExample(example);
            activityBaseVO24.setIsJoined(count > 0 ? Constants.IS_YES : Constants.IS_NO);
        }
        activityBaseVOList.add(activityBaseVO24);
        result.setActivityBaseVOList(activityBaseVOList);
        return result;
    }

    @Override
    public NewYear2018ThirdVO newYear2018ThirdInfo(Integer userId) {
        ActivityBaseVO activityBaseVO = this.queryBaseActivity(Constants.ACTIVITY_ID_2018_NEW_YEAR_THIRD, userId);
        NewYear2018ThirdVO result = new NewYear2018ThirdVO();
        BeanUtils.copyProperties(activityBaseVO, result);
        Double loanAmount = bsSubAccountMapper.sumAnnualAmount(activityBaseVO.getStartTime(), activityBaseVO.getEndTime(), null);
        if(null != userId) {
            Double myLanAmount = bsSubAccountMapper.sumAnnualAmount(activityBaseVO.getStartTime(), activityBaseVO.getEndTime(), userId);
            result.setMyLoanAmount(myLanAmount);
        }
        result.setLoanAmount(loanAmount);
        return result;
    }

    @Override
    public void newYear2018GetRedPacket(Integer userId) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());
            String isStart = this.duringActivity(Constants.ACTIVITY_ID_2018_NEW_YEAR_FIRST);
            if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
            } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
            }
            if(null == userId) {
                throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
            } else {
                BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
                if(null == bsUser) {
                    throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
                }
            }
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                    .andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_FIRST);
            int count = bsActivityLuckyDrawMapper.countByExample(example);
            if(count > 0) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_LEFT_TIMES_ZERO.getCode(), "您已领取");
            }

            // 新春礼包
            BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
            this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_2018_NEW_YEAR_FIRST, Constants.ACTIVITY_ID_2018_NEW_YEAR_FIRST,
                    Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "新年红包", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
            try {
                redPacketService.autoRedPacketSendByName(userId, "新年红包");
            } catch (Exception e) {
                log.error("给用户 {} 发放 {} 异常：{}", userId, "新年红包", e.getMessage());
                luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                luckyDraw.setNote("新年红包发放失败：" + e.getMessage());
                luckyDraw.setUpdateTime(new Date());
                bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
            }
            luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
            luckyDraw.setConfirmTime(new Date());
            luckyDraw.setUpdateTime(new Date());
            bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());
        }
    }

    @Override
    public void newYear2018RobRedPacket(Integer userId, String time) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());

            String isStart = this.duringActivity(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND);
            if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
            } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
            }
            if(null == userId) {
                throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
            } else {
                BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
                if(null == bsUser) {
                    throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
                }
            }

            Integer giftNumber1 = 100;
            Integer giftNumber2 = 100;
            Integer giftNumber3 = 100;
            Integer giftNumber4 = 100;
            BsSysConfig giftConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.SYS_CONFIG_NEW_YEAR_2018_GIFTS_NUMBER);
            if(giftConfig != null && StringUtil.isNotBlank(giftConfig.getConfValue())) {
                String[] numbers = StringUtil.split(giftConfig.getConfValue(), ",");
                if(numbers.length >= 4) {
                    giftNumber1 = Integer.valueOf(numbers[0]);giftNumber2 = Integer.valueOf(numbers[1]);giftNumber3 = Integer.valueOf(numbers[2]);giftNumber4 = Integer.valueOf(numbers[3]);
                } else if(numbers.length == 3) {
                    giftNumber1 = Integer.valueOf(numbers[0]);giftNumber2 = Integer.valueOf(numbers[1]);giftNumber3 = Integer.valueOf(numbers[2]);
                } else if(numbers.length == 2) {
                    giftNumber1 = Integer.valueOf(numbers[0]);giftNumber2 = Integer.valueOf(numbers[1]);
                } else if(numbers.length == 1) {
                    giftNumber1 = Integer.valueOf(numbers[0]);
                }
            }

            String time1 = "2018-02-15 18:00:00";
            String time2 = "2018-02-15 20:00:00";
            String time3 = "2018-02-15 22:00:00";
            String time4 = "2018-02-16 00:00:00";
            BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.SYS_CONFIG_KEYNEW_YEAR_2018_TIMES);
            if(config != null && StringUtil.isNotBlank(config.getConfValue())) {
                String[] times = StringUtil.split(config.getConfValue(), ",");
                if(times.length >= 4) {
                    time1 = times[0];time2 = times[1];time3 = times[2];time4 = times[3];
                } else if(times.length == 3) {
                    time1 = times[0];time2 = times[1];time3 = times[2];
                } else if(times.length == 2) {
                    time1 = times[0];time2 = times[1];
                } else if(times.length == 1) {
                    time1 = times[0];
                }
            }

            List<Integer> awardIdList = new ArrayList<>();
            int luckyNumber = 0;

            Integer awardId = null;
            int giftNumber = 0;
            if(time1.equals(time)) {
                String endTime1 = DateUtil.format(DateUtil.addSeconds(DateUtil.parse(time2, "yyyy-MM-dd HH:mm:ss"), -1));
                isStart = this.duringActivity(time1, endTime1);
                if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
                    throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START.getCode(), time1 + "档的活动未开始，请刷新页面");
                } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
                    throw new PTMessageException(PTMessageEnum.ACTIVITY_END.getCode(), time1 + "档的活动已结束，刷新页面，进入下个整点送红包");
                }

                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_88);
                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_188);
                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_888);
                BsActivityLuckyDrawExample example88 = new BsActivityLuckyDrawExample();
                example88.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_88).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count88 = bsActivityLuckyDrawMapper.countByExample(example88);
                BsActivityLuckyDrawExample example188 = new BsActivityLuckyDrawExample();
                example188.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_188).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count188 = bsActivityLuckyDrawMapper.countByExample(example188);
                BsActivityLuckyDrawExample example888 = new BsActivityLuckyDrawExample();
                example888.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_888).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count888 = bsActivityLuckyDrawMapper.countByExample(example888);
                int leftTimes = giftNumber1 - count88 - count188 - count888;
                if(leftTimes > 0) {
                    luckyNumber = LuckyDrawUtil.luckyNumber(1, leftTimes);
                }
                log.info("用户 {} 在环节二抢红包的幸运数字：{}，剩余次数分别是：{}，{}，{}", userId, luckyNumber, (30-count88), (35-count188), (35-count888));
                if(luckyNumber <= (30-count88)) {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_88;
                } else if(luckyNumber <= (30-count88 + 35-count188)) {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_188;
                } else {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_18_888;
                }
                giftNumber = giftNumber1;
            } else if(time2.equals(time)) {
                String endTime2 = DateUtil.format(DateUtil.addSeconds(DateUtil.parse(time3, "yyyy-MM-dd HH:mm:ss"), -1));
                isStart = this.duringActivity(time2, endTime2);
                if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
                    throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START.getCode(), time2 + "档的活动未开始，请刷新页面");
                } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
                    throw new PTMessageException(PTMessageEnum.ACTIVITY_END.getCode(), time2 + "档的活动已结束，刷新页面，进入下个整点送红包");
                }

                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_88);
                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_188);
                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_888);

                BsActivityLuckyDrawExample example88 = new BsActivityLuckyDrawExample();
                example88.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_88).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count88 = bsActivityLuckyDrawMapper.countByExample(example88);
                BsActivityLuckyDrawExample example188 = new BsActivityLuckyDrawExample();
                example188.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_188).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count188 = bsActivityLuckyDrawMapper.countByExample(example188);
                BsActivityLuckyDrawExample example888 = new BsActivityLuckyDrawExample();
                example888.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_888).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count888 = bsActivityLuckyDrawMapper.countByExample(example888);
                int leftTimes = giftNumber1 - count88 - count188 - count888;
                if(leftTimes > 0) {
                    luckyNumber = LuckyDrawUtil.luckyNumber(1, leftTimes);
                }
                log.info("用户 {} 在环节二抢红包的幸运数字：{}，剩余次数分别是：{}，{}，{}", userId, luckyNumber, (30-count88), (35-count188), (35-count888));
                if(luckyNumber <= (30-count88)) {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_88;
                } else if(luckyNumber <= (30-count88 + 35-count188)) {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_188;
                } else {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_20_888;
                }
                giftNumber = giftNumber2;
            } else if(time3.equals(time)) {
                String endTime3 = DateUtil.format(DateUtil.addSeconds(DateUtil.parse(time4, "yyyy-MM-dd HH:mm:ss"), -1));
                isStart = this.duringActivity(time3, endTime3);
                if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
                    throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START.getCode(), time3 + "档的活动未开始，请刷新页面");
                } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
                    throw new PTMessageException(PTMessageEnum.ACTIVITY_END.getCode(), time3 + "档的活动已结束，刷新页面，进入下个整点送红包");
                }

                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_88);
                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_188);
                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_888);

                BsActivityLuckyDrawExample example88 = new BsActivityLuckyDrawExample();
                example88.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_88).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count88 = bsActivityLuckyDrawMapper.countByExample(example88);
                BsActivityLuckyDrawExample example188 = new BsActivityLuckyDrawExample();
                example188.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_188).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count188 = bsActivityLuckyDrawMapper.countByExample(example188);
                BsActivityLuckyDrawExample example888 = new BsActivityLuckyDrawExample();
                example888.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_888).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count888 = bsActivityLuckyDrawMapper.countByExample(example888);
                int leftTimes = giftNumber1 - count88 - count188 - count888;
                if(leftTimes > 0) {
                    luckyNumber = LuckyDrawUtil.luckyNumber(1, leftTimes);
                }
                log.info("用户 {} 在环节二抢红包的幸运数字：{}，剩余次数分别是：{}，{}，{}", userId, luckyNumber, (30-count88), (35-count188), (35-count888));

                if(luckyNumber <= (30-count88)) {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_88;
                } else if(luckyNumber <= (30-count88 + 35-count188)) {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_188;
                } else {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_22_888;
                }
                giftNumber = giftNumber3;
            } else if(time4.equals(time)) {
                Date endTime = DateUtil.addHours(DateUtil.parse(time4, "yyyy-MM-dd HH:mm:ss"), 1);
                String endTime4 = DateUtil.formatDateTime(endTime, "yyyy-MM-dd HH:mm:ss");
                isStart = this.duringActivity(time4, endTime4);
                if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
                    throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
                } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
                    throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
                }

                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_88);
                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_188);
                awardIdList.add(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_888);

                BsActivityLuckyDrawExample example88 = new BsActivityLuckyDrawExample();
                example88.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_88).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count88 = bsActivityLuckyDrawMapper.countByExample(example88);
                BsActivityLuckyDrawExample example188 = new BsActivityLuckyDrawExample();
                example188.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_188).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count188 = bsActivityLuckyDrawMapper.countByExample(example188);
                BsActivityLuckyDrawExample example888 = new BsActivityLuckyDrawExample();
                example888.createCriteria().andAwardIdEqualTo(Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_888).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
                int count888 = bsActivityLuckyDrawMapper.countByExample(example888);
                int leftTimes = giftNumber1 - count88 - count188 - count888;
                if(leftTimes > 0) {
                    luckyNumber = LuckyDrawUtil.luckyNumber(1, leftTimes);
                }
                log.info("用户 {} 在环节二抢红包的幸运数字：{}，剩余次数分别是：{}，{}，{}", userId, luckyNumber, (30-count88), (35-count188), (35-count888));

                if(luckyNumber <= (30-count88)) {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_88;
                } else if(luckyNumber <= (30-count88 + 35-count188)) {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_188;
                } else {
                    awardId = Constants.AWARD_ID_2018_NEW_YEAR_SECOND_24_888;
                }
                giftNumber = giftNumber4;
            }
            BsActivityAward award = bsActivityAwardMapper.selectByPrimaryKey(awardId);

            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                    .andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND).andAwardIdIn(awardIdList);
            int count = bsActivityLuckyDrawMapper.countByExample(example);
            if(count >= giftNumber) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_LEFT_TIMES_ZERO.getCode(), "本轮已抢完");
            }

            BsActivityLuckyDrawExample userExample = new BsActivityLuckyDrawExample();
            userExample.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                    .andActivityIdEqualTo(Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND).andAwardIdIn(awardIdList).andUserIdEqualTo(userId);
            int userCount = bsActivityLuckyDrawMapper.countByExample(userExample);
            if(userCount > 0) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_LEFT_TIMES_ZERO.getCode(), "本轮已抢");
            }

            // 超值红包
            BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
            this.initLuckyDraw(luckyDraw, userId, awardId, Constants.ACTIVITY_ID_2018_NEW_YEAR_SECOND,
                    Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, award.getContent(), Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
            try {
                redPacketService.autoRedPacketSendByName(userId, award.getContent());
            } catch (Exception e) {
                log.error("给用户 {} 发放 {} 异常：{}", userId, award.getContent(), e.getMessage());
                luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                luckyDraw.setNote("超值红包发放失败：" + e.getMessage());
                luckyDraw.setUpdateTime(new Date());
                bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
            }
            luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
            luckyDraw.setConfirmTime(new Date());
            luckyDraw.setUpdateTime(new Date());
            bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());
        }
    }

	 @Override
    public ChristmasEveVO yearEndLuckyUserList(Integer type) {
        ChristmasEveVO result = new ChristmasEveVO();
        List<ChristmasEveResultVO> drawnList = new ArrayList<>();
        List<ChristmasEveResultVO> list = new ArrayList<>();

        // 判断活动是否开始
        String isStart = this.duringActivity(Constants.ACTIVITY_ID_2018_YEAR_END);
        BsActivityVO activity = this.queryActivity(Constants.ACTIVITY_ID_2018_YEAR_END);
        result.setIsStart(isStart);
        result.setStartTime(activity.getStartTime());
        result.setEndTime(activity.getEndTime());

        Map<String, Integer> map = new HashMap<>();
        map.put("first", 0);
        map.put("second", 0);
        map.put("third", 0);
        map.put("lucky", 0);
        // 已中奖用户
        List<EndOf2016YearActivityVO> drawnUserList = bs2016CheckInUserMapper.selectDrawedUser(Constants.ACTIVITY_ID_2018_YEAR_END, type);
        if(!CollectionUtils.isEmpty(drawnUserList)) {
            for (EndOf2016YearActivityVO data: drawnUserList) {
                ChristmasEveResultVO user = new ChristmasEveResultVO();
                user.setMobile(data.getMobile());
                user.setType(data.getType());
                drawnList.add(user);
                if("1".equals(data.getType())) {
                    map.put("first", map.get("first") + 1);
                }
                if("2".equals(data.getType())) {
                    map.put("second", map.get("second") + 1);
                }
                if("3".equals(data.getType())) {
                    map.put("third", map.get("third") + 1);
                }
                if("0".equals(data.getType())) {
                    map.put("lucky", map.get("lucky") + 1);
                }
            }
        }
        result.setDrawnList(drawnList);
        result.setMap(map);
        // 未中奖用户
        List<EndOf2016YearActivityVO> userList = bs2016CheckInUserMapper.selectNoDrawUserAmount(Constants.ACTIVITY_ID_2018_YEAR_END);
        if(!CollectionUtils.isEmpty(userList)) {
            for (EndOf2016YearActivityVO data: userList) {
                ChristmasEveResultVO user = new ChristmasEveResultVO();
                user.setMobile(data.getMobile());
                user.setType(data.getType());
                user.setUserId(data.getUserId());
                list.add(user);
            }
        }
        result.setList(list);
        return result;
    }

    @Override
    public ChristmasEveVO yearEndLuckyDraw(final int type) {
        ChristmasEveVO result = new ChristmasEveVO();
        String isStart = this.duringActivity(Constants.ACTIVITY_ID_2018_YEAR_END);
        if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }

        final List<ChristmasEveResultVO> list = new ArrayList<>();
        List<Integer> luckyNumberList = new ArrayList<>();
        Integer awardId = null;
        int luckyDrawTimes = 0; // 单次抽奖次数
        int totalDrawTimes = 0; // 总抽奖次数
        switch (type) {
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_FIRST:
                // 一等奖一次抽取1人（内定为嘉宾名单累计年化投资金额最高用户）
                awardId = Constants.AWARD_ID_2018_YEAR_END_FIRST;
                luckyDrawTimes = 1;
                totalDrawTimes = 1;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_SECOND:
                // 2. 二等奖一次抽取1人，随机中奖
                awardId = Constants.AWARD_ID_2018_YEAR_END_SECOND;
                luckyDrawTimes = 10;
                totalDrawTimes = 10;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_THIRD:
                // 三等奖一次抽取15人，随机中奖
                awardId = Constants.AWARD_ID_2018_YEAR_END_THIRD;
                luckyDrawTimes = 10;
                totalDrawTimes = 30;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_LUCKY:
                // 幸运奖一次抽取15人，随机中奖
                awardId = Constants.AWARD_ID_2018_YEAR_END_LUCKY;
                luckyDrawTimes = 10;
                totalDrawTimes = 50;
                break;
        }
        List<EndOf2016YearActivityVO> userList = bs2016CheckInUserMapper.selectNoDrawUserAmount(Constants.ACTIVITY_ID_2018_YEAR_END);
        if(!CollectionUtils.isEmpty(userList)) {
            Bs2016AnnualMeetingEmployeeExample employeeExample = new Bs2016AnnualMeetingEmployeeExample();
            employeeExample.createCriteria().andAwardIdEqualTo(awardId).andIsWinEqualTo(Constants.IS_WIN_YES);
            int count = bs2016AnnualMeetingEmployeeMapper.countByExample(employeeExample);
            if(count >= totalDrawTimes) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_LEFT_TIMES_ZERO.getCode(), "已抽过奖");
            }

            luckyDrawTimes = userList.size() > luckyDrawTimes ? luckyDrawTimes : userList.size();
            List<Integer> initDataList = new ArrayList<>();
            for (int j = 0; j < userList.size(); j++) {
                initDataList.add(j);
            }
            LuckyDrawUtil.luckyNumberList(initDataList, luckyDrawTimes, luckyNumberList);
            for (Integer number : luckyNumberList) {
                EndOf2016YearActivityVO vo = userList.get(number);
                ChristmasEveResultVO eveRsultVO = new ChristmasEveResultVO();
                eveRsultVO.setMobile(vo.getMobile());
                eveRsultVO.setUserId(vo.getUserId());
                list.add(eveRsultVO);
            }
        } else {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "无抽奖用户");
        }
        final Integer finalAwardId = awardId;
        List<Integer> idList = new ArrayList<Integer>();
        for(ChristmasEveResultVO vo: list) {
            idList.add(vo.getUserId());
        }
        // 已中奖用户列表
        Bs2016AnnualMeetingEmployeeExample bs2016AnnualMeetingEmployeeExample = new Bs2016AnnualMeetingEmployeeExample();
        bs2016AnnualMeetingEmployeeExample.createCriteria().andIdIn(idList);
        Bs2016AnnualMeetingEmployee employee = new Bs2016AnnualMeetingEmployee();
        employee.setAwardId(finalAwardId);
        employee.setIsWin(Constants.IS_WIN_YES);
        employee.setUpdateTime(new Date());
        employee.setWinTime(new Date());
        bs2016AnnualMeetingEmployeeMapper.updateByExampleSelective(employee, bs2016AnnualMeetingEmployeeExample);

        result.setDrawnList(list);

        List<EndOf2016YearActivityVO> drawnUserList = bs2016CheckInUserMapper.selectDrawedUser(Constants.ACTIVITY_ID_2018_YEAR_END, type);
        if(!CollectionUtils.isEmpty(drawnUserList)) {
            result.setLeftTimes(totalDrawTimes - drawnUserList.size());
        } else {
            result.setLeftTimes(totalDrawTimes);
        }
        return result;
    }

    @Override
    public ChristmasEveResultVO yearEndMakeupDraw(int type) {
        // 补抽时如果检测到第二已经中了其他奖项，就跳过到第三，依次类推

        String isStart = this.duringActivity(Constants.ACTIVITY_ID_2018_YEAR_END);
        if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }

        ChristmasEveResultVO christmasEveResult = new ChristmasEveResultVO();
        Integer awardId = null;
        Integer totalDrawTimes = null;
        switch (type) {
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_FIRST:
                // 一等奖一次抽取1人（内定为嘉宾名单累计年化投资金额最高用户）
                awardId = Constants.AWARD_ID_2018_YEAR_END_FIRST;
                totalDrawTimes = 1;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_SECOND:
                // 2. 二等奖一次抽取1人，随机中奖
                awardId = Constants.AWARD_ID_2018_YEAR_END_SECOND;
                totalDrawTimes = 10;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_THIRD:
                // 三等奖一次抽取15人，随机中奖
                awardId = Constants.AWARD_ID_2018_YEAR_END_THIRD;
                totalDrawTimes = 30;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_LUCKY:
                // 幸运奖一次抽取15人，随机中奖
                awardId = Constants.AWARD_ID_2018_YEAR_END_LUCKY;
                totalDrawTimes = 50;
                break;
        }
        // 未中奖用户列表
        List<EndOf2016YearActivityVO> userList = bs2016CheckInUserMapper.selectNoDrawUserAmount(Constants.ACTIVITY_ID_2018_YEAR_END);

        EndOf2016YearActivityVO result = new EndOf2016YearActivityVO();
        if(!CollectionUtils.isEmpty(userList)) {
            int luckyNumber = LuckyDrawUtil.luckyNumber(1, userList.size());
            result = userList.get(luckyNumber - 1);
            christmasEveResult.setMobile(result.getMobile());
            christmasEveResult.setUserId(result.getUserId());
        } else {
            throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR.getCode(), "无抽奖用户");
        }

        // 已中奖用户列表
        Bs2016AnnualMeetingEmployee employee = new Bs2016AnnualMeetingEmployee();
        employee.setId(christmasEveResult.getUserId());
        employee.setAwardId(awardId);
        employee.setIsWin(Constants.IS_WIN_YES);
        employee.setUpdateTime(new Date());
        employee.setWinTime(new Date());
        bs2016AnnualMeetingEmployeeMapper.updateByPrimaryKeySelective(employee);

        List<EndOf2016YearActivityVO> drawnUserList = bs2016CheckInUserMapper.selectDrawedUser(Constants.ACTIVITY_ID_2018_YEAR_END, type);
        if(!CollectionUtils.isEmpty(drawnUserList)) {
            christmasEveResult.setLeftTimes(totalDrawTimes - drawnUserList.size());
        } else {
            christmasEveResult.setLeftTimes(totalDrawTimes);
        }
        return christmasEveResult;
    }

	// ==================================================== 2017年平安夜湾粉抽奖活动结束 ======================================================
    @Override
    public ChristmasEveVO christmasEveLuckyUserList(Integer type) {
        ChristmasEveVO result = new ChristmasEveVO();
        List<ChristmasEveResultVO> drawnList = new ArrayList<>();
        List<ChristmasEveResultVO> list = new ArrayList<>();

        // 判断活动是否开始
        String isStart = this.duringActivity(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE);
        BsActivityVO activity = this.queryActivity(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE);
        result.setIsStart(isStart);
        result.setStartTime(activity.getStartTime());
        result.setEndTime(activity.getEndTime());

        Map<String, Integer> map = new HashMap<>();
        map.put("first", 0);
        map.put("second", 0);
        map.put("third", 0);
        map.put("lucky", 0);
        // 已中奖用户
        List<EndOf2016YearActivityVO> drawnUserList = bs2016CheckInUserMapper.selectDrawedUser(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE, type);
        if(!CollectionUtils.isEmpty(drawnUserList)) {
            for (EndOf2016YearActivityVO data: drawnUserList) {
                ChristmasEveResultVO user = new ChristmasEveResultVO();
                user.setMobile(data.getMobile());
                user.setType(data.getType());
                drawnList.add(user);
                if("1".equals(data.getType())) {
                    map.put("first", map.get("first") + 1);
                }
                if("2".equals(data.getType())) {
                    map.put("second", map.get("second") + 1);
                }
                if("3".equals(data.getType())) {
                    map.put("third", map.get("third") + 1);
                }
                if("0".equals(data.getType())) {
                    map.put("lucky", map.get("lucky") + 1);
                }
            }
        }
        result.setDrawnList(drawnList);
        result.setMap(map);
        // 未中奖用户
        List<EndOf2016YearActivityVO> userList = bs2016CheckInUserMapper.selectNoDrawUserAmount(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE);
        if(!CollectionUtils.isEmpty(userList)) {
            for (EndOf2016YearActivityVO data: userList) {
                ChristmasEveResultVO user = new ChristmasEveResultVO();
                user.setMobile(data.getMobile());
                user.setType(data.getType());
                list.add(user);
            }
        }
        result.setList(list);
        return result;
    }

    @Override
    public ChristmasEveVO christmasEveLuckyDraw(final int type) {
//        一等奖-1人（内定）
//        二等奖-3人
//        三等奖-15人
//        幸运奖-30人
//        1. 一等奖一次抽取1人（内定为嘉宾名单累计年化投资金额最高用户）
//        2. 二等奖一次抽取1人，随机中奖
//        3. 三等奖一次抽取15人，随机中奖
//        4. 幸运奖一次抽取15人，随机中奖
//        其余奖项不得抽中嘉宾名单中累计年化投资金额最高用户
        ChristmasEveVO result = new ChristmasEveVO();
        String isStart = this.duringActivity(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE);
        if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }

        final List<ChristmasEveResultVO> list = new ArrayList<>();
        List<Integer> luckyNumberList = new ArrayList<>();
        Integer awardId = null;
        int luckyDrawTimes = 0; // 单次抽奖次数
        int totalDrawTimes = 0; // 总抽奖次数
        switch (type) {
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_FIRST:
                // 一等奖一次抽取1人（内定为嘉宾名单累计年化投资金额最高用户）
                awardId = Constants.AWARD_ID_2017_CHRISTMAS_EVE_FIRST;
                luckyDrawTimes = 1;
                totalDrawTimes = 1;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_SECOND:
                // 2. 二等奖一次抽取1人，随机中奖
                awardId = Constants.AWARD_ID_2017_CHRISTMAS_EVE_SECOND;
                luckyDrawTimes = 1;
                totalDrawTimes = 3;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_THIRD:
                // 三等奖一次抽取15人，随机中奖
                awardId = Constants.AWARD_ID_2017_CHRISTMAS_EVE_THIRD;
                luckyDrawTimes = 15;
                totalDrawTimes = 15;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_LUCKY:
                // 幸运奖一次抽取15人，随机中奖
                awardId = Constants.AWARD_ID_2017_CHRISTMAS_EVE_LUCKY;
                luckyDrawTimes = 15;
                totalDrawTimes = 30;
                break;
        }
        ChristmasEveResultVO resultVO = new ChristmasEveResultVO();
        List<EndOf2016YearActivityVO> amountList = bs2016CheckInUserMapper.selectNoDrawUserAmount(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE);
        if(!CollectionUtils.isEmpty(amountList)) {
            EndOf2016YearActivityVO maxAmount = bs2016CheckInUserMapper.selectAnnualizedAmountMax().get(0);
            List<EndOf2016YearActivityVO> userList = new ArrayList<>();
            for (EndOf2016YearActivityVO user: amountList) {
                // 最高年化的用户拎出来
                if(maxAmount.getUserId().equals(user.getUserId())) {
                    resultVO.setAmount(user.getAnnualizedInvestment());
                    resultVO.setMobile(user.getMobile());
                    resultVO.setUserId(user.getUserId());
                } else {
                    userList.add(user);
                }
            }

            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE).andAwardIdEqualTo(awardId);
            int count = bsActivityLuckyDrawMapper.countByExample(example);
            if(count >= totalDrawTimes) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_LEFT_TIMES_ZERO.getCode(), "已抽过奖");
            }

            if(awardId.equals(Constants.AWARD_ID_2017_CHRISTMAS_EVE_FIRST)) {
                // 一等奖
                list.add(resultVO);
            } else {
                if(CollectionUtils.isEmpty(userList)) {
                    throw new PTMessageException(PTMessageEnum.ACTIVITY_LEFT_TIMES_ZERO.getCode(), "无抽奖用户");
                }
                luckyDrawTimes = userList.size() > luckyDrawTimes ? luckyDrawTimes : userList.size();
                List<Integer> initDataList = new ArrayList<>();
                for (int j = 0; j < userList.size(); j++) {
                    initDataList.add(j);
                }
                LuckyDrawUtil.luckyNumberList(initDataList, luckyDrawTimes, luckyNumberList);
                for (Integer number : luckyNumberList) {
                    EndOf2016YearActivityVO vo = userList.get(number);
                    ChristmasEveResultVO eveRsultVO = new ChristmasEveResultVO();
                    eveRsultVO.setMobile(vo.getMobile());
                    eveRsultVO.setAmount(vo.getAnnualizedInvestment());
                    eveRsultVO.setUserId(vo.getUserId());
                    list.add(eveRsultVO);
                }
            }
        } else {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "无抽奖用户");
        }
        final Integer finalAwardId = awardId;
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    for(ChristmasEveResultVO result: list) {
                        // 已中奖用户列表
                        BsActivityLuckyDraw activityLuckyDraw = new BsActivityLuckyDraw();
                        activityLuckyDraw.setAwardId(finalAwardId);
                        activityLuckyDraw.setUserId(result.getUserId());
                        activityLuckyDraw.setActivityId(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE);
                        activityLuckyDraw.setIsWin(Constants.IS_WIN_YES);//是否中奖：中奖-Y；未中奖-N
                        activityLuckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);//是否兑奖：已兑奖-Y；未兑奖-N
                        activityLuckyDraw.setIsAutoConfirm(Constants.IS_AUTO_CONFIRM_NO);
                        activityLuckyDraw.setIsBackDraw(Constants.IS_BACK_DRAW_YES);
                        activityLuckyDraw.setIsUserDraw(Constants.IS_USER_DRAW_NO);
                        activityLuckyDraw.setCreateTime(new Date());
                        activityLuckyDraw.setUpdateTime(new Date());
                        activityLuckyDraw.setBackDrawTime(new Date());
                        activityLuckyDraw.setNote(type == 0 ? "幸运奖" : type + "等奖");
                        bsActivityLuckyDrawMapper.insertSelective(activityLuckyDraw);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "抽奖失败");
                }
                return null;
            }
        });
        result.setDrawnList(list);

        List<EndOf2016YearActivityVO> drawnUserList = bs2016CheckInUserMapper.selectDrawedUser(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE, type);
        if(!CollectionUtils.isEmpty(drawnUserList)) {
            result.setLeftTimes(totalDrawTimes - drawnUserList.size());
        } else {
            result.setLeftTimes(totalDrawTimes);
        }
        return result;
    }


    @Override
    public ChristmasEveResultVO christmasEveMakeupDraw(int type) {
        // 补抽时如果检测到第二已经中了其他奖项，就跳过到第三，依次类推

        String isStart = this.duringActivity(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE);
        if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }

        ChristmasEveResultVO christmasEveResult = new ChristmasEveResultVO();
        Integer awardId = null;
        Integer totalDrawTimes = null;
        switch (type) {
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_FIRST:
                // 一等奖一次抽取1人（内定为嘉宾名单累计年化投资金额最高用户）
                awardId = Constants.AWARD_ID_2017_CHRISTMAS_EVE_FIRST;
                totalDrawTimes = 1;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_SECOND:
                // 2. 二等奖一次抽取1人，随机中奖
                awardId = Constants.AWARD_ID_2017_CHRISTMAS_EVE_SECOND;
                totalDrawTimes = 3;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_THIRD:
                // 三等奖一次抽取15人，随机中奖
                awardId = Constants.AWARD_ID_2017_CHRISTMAS_EVE_THIRD;
                totalDrawTimes = 15;
                break;
            case Constants.AWARD_TYPE_2017_CHRISTMAS_EVE_LUCKY:
                // 幸运奖一次抽取15人，随机中奖
                awardId = Constants.AWARD_ID_2017_CHRISTMAS_EVE_LUCKY;
                totalDrawTimes = 30;
                break;
        }
        // 未中奖用户列表
        List<EndOf2016YearActivityVO> amountList = bs2016CheckInUserMapper.selectNoDrawUserAmount(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE);
        EndOf2016YearActivityVO maxAmount = bs2016CheckInUserMapper.selectAnnualizedAmountMax().get(0);
        List<EndOf2016YearActivityVO> userList = new ArrayList<>();
        for (EndOf2016YearActivityVO amount: amountList) {
            if(!amount.getUserId().equals(maxAmount.getUserId())) {
                userList.add(amount);
            }
        }
        EndOf2016YearActivityVO result = new EndOf2016YearActivityVO();
        if(Constants.AWARD_ID_2017_CHRISTMAS_EVE_FIRST == awardId) {
            // 一等奖
            if(!CollectionUtils.isEmpty(amountList)) {
                result = amountList.get(0);
                christmasEveResult.setMobile(result.getMobile());
                christmasEveResult.setAmount(result.getAnnualizedInvestment());
                christmasEveResult.setUserId(result.getUserId());
            } else {
                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR.getCode(), "无抽奖用户");
            }
        } else {
            if(!CollectionUtils.isEmpty(userList)) {
                int luckyNumber = LuckyDrawUtil.luckyNumber(1, userList.size());
                result = userList.get(luckyNumber - 1);
                christmasEveResult.setMobile(result.getMobile());
                christmasEveResult.setAmount(result.getAnnualizedInvestment());
                christmasEveResult.setUserId(result.getUserId());
            } else {
                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR.getCode(), "无抽奖用户");
            }
        }

        // 已中奖用户列表
        BsActivityLuckyDraw activityLuckyDraw = new BsActivityLuckyDraw();
        this.initLuckyDraw(activityLuckyDraw, result.getUserId(), awardId, Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE, Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO,
                type == 0 ? "补抽：幸运奖" : "补抽：" + type + "等奖", Constants.IS_BACK_DRAW_YES, Constants.IS_USER_DRAW_NO, Constants.IS_AUTO_CONFIRM_NO);

        List<EndOf2016YearActivityVO> drawnUserList = bs2016CheckInUserMapper.selectDrawedUser(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE, type);
        if(!CollectionUtils.isEmpty(drawnUserList)) {
            christmasEveResult.setLeftTimes(totalDrawTimes - drawnUserList.size());
        } else {
            christmasEveResult.setLeftTimes(totalDrawTimes);
        }
        return christmasEveResult;
    }
    // ==================================================== 2017年平安夜湾粉抽奖活动结束 ======================================================


    // ==================================================== 活动公共方法结束 ========================================================


    // ==================================================== 2017年318周年庆活动开始 ======================================================

    @Override
    public AnniversaryFirstHomePageInfoVO anniversaryFirstHomePageInfo(Integer userId) {
        // a. 是否登录
        // b. 一重礼活动时间
        // c. 二重礼活动时间
        // d. 一重礼是否开始标识
        // e. 二重礼是否开始标识
        // f. 元宝个数
        // g. 元宝是否可开启

        AnniversaryFirstHomePageInfoVO result = new AnniversaryFirstHomePageInfoVO();
        // a. 是否登录
        result.setIsLogin(null == userId ? Constants.IS_LOGIN_NO : Constants.IS_LOGIN_YES);
        if(null != userId) {
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            if(null == bsUser) {
                result.setIsLogin(Constants.IS_LOGIN_NO);
            }
        }
        // b. 一重礼活动时间
        BsActivityVO activityFirst = this.queryActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FIRST);
        result.setStartTimeFirst(activityFirst.getStartTime());
        result.setEndTimeFirst(activityFirst.getEndTime());
        // c. 二重礼活动时间
        BsActivityVO activitySecond = this.queryActivity(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND);
        result.setStartTimeSecond(activitySecond.getStartTime());
        result.setEndTimeSecond(activitySecond.getEndTime());
        // d. 一重礼是否开始标识
        result.setIsStartFirst(this.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FIRST, 1, null));
        // e. 二重礼是否开始标识
        result.setIsStartSecond(this.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND, 1, null));

        if(Constants.IS_LOGIN_YES.equals(result.getIsLogin())) {
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND)
                    .andAwardIdEqualTo(Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT).andUserIdEqualTo(userId);
            int count = bsActivityLuckyDrawMapper.countByExample(example);
            if(count < Constants.ACTIVITY_ANNIVERSARY_GOLD_INIT_NUMBER) {
                BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
                luckyDraw.setAwardId(Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT);
                luckyDraw.setActivityId(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND);
                luckyDraw.setIsWin(Constants.IS_WIN_YES);//是否中奖：中奖-Y；未中奖-N
                luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);//是否兑奖：已兑奖-Y；未兑奖-N
                luckyDraw.setIsAutoConfirm(Constants.IS_AUTO_CONFIRM_YES);
                luckyDraw.setIsBackDraw(Constants.IS_BACK_DRAW_NO);
                luckyDraw.setIsUserDraw(Constants.IS_USER_DRAW_YES);
                luckyDraw.setCreateTime(new Date());
                luckyDraw.setUpdateTime(new Date());
                luckyDraw.setUserDrawTime(new Date());
                luckyDraw.setUserId(userId);
                luckyDraw.setNote("1");
                bsActivityLuckyDrawMapper.insertSelective(luckyDraw);
            }

            // f. 元宝个数
            result.setGoldIngotNum(bsActivityLuckyDrawMapper.countGoldIngotByUserId(userId, Constants.ACTIVITY_ID_ANNIVERSARY_SECOND, Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT, null));
            // g. 元宝是否可开启
            String today = DateUtil.formatYYYYMMDD(new Date());
            result.setCanUnlock(Constants.GOLD_INGOT_UNLOCK_TIME.equals(today) ? Constants.GOLD_INGOT_UNLOCK_YES : Constants.GOLD_INGOT_UNLOCK_NO);

            // c. 已解锁元宝个数
            Integer unlocked = bsActivityLuckyDrawMapper.countGoldIngotByUserId(userId, Constants.ACTIVITY_ID_ANNIVERSARY_SECOND, Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT, Constants.IS_CONFIRM_YES);
            result.setUnlockedNum(unlocked == null ? 0 : unlocked);
        }
        return result;
    }

    @Override
    public AnniversarySharePageInfoVO anniversarySharePageInfo(Integer shareUserId, String openId, Integer pageNum, Integer numPerPage) {
        // a. 分享者元宝个数
        // b. 助力好友列表（昵称、头像、元宝）

        AnniversarySharePageInfoVO result = new AnniversarySharePageInfoVO();
        BsWxInfoExample wxInfoExample = new BsWxInfoExample();
        wxInfoExample.createCriteria().andOpenIdEqualTo(openId);
        List<BsWxInfo> wxInfoList = bsWxInfoMapper.selectByExample(wxInfoExample);
        if(!CollectionUtils.isEmpty(wxInfoList)) {
            BsWxInfo wxInfo = wxInfoList.get(0);
            BsActivity2017anniversaryWxSurpportExample example = new BsActivity2017anniversaryWxSurpportExample();
            example.createCriteria().andPartnerIdEqualTo(shareUserId).andWxInfoIdEqualTo(wxInfo.getId()).andActivityIdEqualTo(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND);
            long joinedCount = bsActivity2017anniversaryWxSurpportMapper.countByExample(example);
            result.setIsJoined(joinedCount > 0 ? Constants.IS_YES : Constants.IS_NO);
        } else {
            result.setIsJoined(Constants.IS_NO);
        }
        // a. 分享者元宝个数
        result.setGoldIngotNum(bsActivityLuckyDrawMapper.countGoldIngotByUserId(shareUserId, Constants.ACTIVITY_ID_ANNIVERSARY_SECOND, Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT, null));

        // b. 助力好友列表（昵称、头像、元宝）
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage); // mysql的分页
        List<AnniversaryHelpFriendVO> friendList = bsActivity2017anniversaryWxSurpportMapper.selectByPartnerId(shareUserId, Constants.ACTIVITY_ID_ANNIVERSARY_SECOND, start, numPerPage);
        if(!CollectionUtils.isEmpty(friendList)) {
            for (AnniversaryHelpFriendVO vo : friendList) {
                try {
                    String nick = URLDecoder.decode(vo.getWxNick(), "utf-8");
                    vo.setWxNick(nick);
                } catch (UnsupportedEncodingException e) {
                    log.error("助理好友昵称解码：{}", vo.getWxNick());
                }
            }
        }
        result.setFriendList(CollectionUtils.isEmpty(friendList) ? new ArrayList<AnniversaryHelpFriendVO>() : friendList);

        // c. 已解锁元宝个数
        Integer unlocked = bsActivityLuckyDrawMapper.countGoldIngotByUserId(shareUserId, Constants.ACTIVITY_ID_ANNIVERSARY_SECOND, Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT, Constants.IS_CONFIRM_YES);
        result.setUnlockedNum(unlocked == null ? 0 : unlocked);
        return result;
    }

    @Override
    public void anniversaryHelpFriend(final AnniversaryHelpFriendVO wxFriend) {
        // a. 判断活动是否开始（未开始不做任何处理）
        // b. 判断活动是否结束（结束了的助力，被助力者添加0个元宝）
        // c. 判断是否已经助力
        // d. 判断被助力者是否超过60个元宝（超过60，被助力者添加0个元宝）
        // e. 判断被助力者是否超过60个元宝（不超过60，被助力者添加2-6个元宝）
        // f. 新增或更新助力者微信信息
        // g. 新增抽奖记录
        // h. 新增助力记录

        final String isStart = this.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND);
        if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        }
        // b. 判断用户是否授权
        if(StringUtil.isBlank(wxFriend.getOpenId())) {
            throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
        }
        if(wxFriend.getShareUserId() == null) {
            throw new PTMessageException(PTMessageEnum.SHARE_USER_IS_NOT_EXIST);
        }
        // c. 判断用户是否已经助力
        AnniversaryHelpFriendVO friend = bsActivity2017anniversaryWxSurpportMapper.selectByOpenId(wxFriend.getOpenId(), wxFriend.getShareUserId(), Constants.ACTIVITY_ID_ANNIVERSARY_SECOND);
        if(null != friend) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_LEFT_TIMES_ZERO);
        }
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                Integer luckyNumber = null;
                if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
                    // a. 判断活动是否开始（未开始不做任何处理）
                } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
                    // b. 判断活动是否结束（结束了的助力，被助力者添加0个元宝）
                    AnniversaryHelpFriendVO friend = bsActivity2017anniversaryWxSurpportMapper.selectByOpenId(wxFriend.getOpenId(), wxFriend.getShareUserId(), Constants.ACTIVITY_ID_ANNIVERSARY_SECOND);
                    if(null == friend) {
                        // c. 判断是否已经助力，已经助力不做任何处理
                        luckyNumber = 0;
                    }
                } else {
                    // c. 判断是否已经助力，已经助力不做任何处理
                    AnniversaryHelpFriendVO friend = bsActivity2017anniversaryWxSurpportMapper.selectByOpenId(wxFriend.getOpenId(), wxFriend.getShareUserId(), Constants.ACTIVITY_ID_ANNIVERSARY_SECOND);
                    if(null == friend) {
                        int goldNum = bsActivityLuckyDrawMapper.countGoldIngotByUserId(wxFriend.getShareUserId(), Constants.ACTIVITY_ID_ANNIVERSARY_SECOND, Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT, null);
                        if(goldNum >= Constants.HELP_TOTAL_NUM_MAX) {
                            // d. 判断被助力者是否超过60个元宝（超过60，被助力者添加0个元宝）
                            luckyNumber = 0;
                        } else {
                            // e. 判断被助力者是否超过60个元宝（不超过60，被助力者添加2-6个元宝）
                            luckyNumber = LuckyDrawUtil.luckyNumber(2, 6);
                            if(goldNum + luckyNumber > Constants.HELP_TOTAL_NUM_MAX) {
                                luckyNumber = Constants.HELP_TOTAL_NUM_MAX - goldNum;
                            }
                        }
                    }
                }

                if(null != luckyNumber) {
                    // f. 新增或更新助力者微信信息
                    BsWxInfoExample wxInfoExample = new BsWxInfoExample();
                    wxInfoExample.createCriteria().andOpenIdEqualTo(wxFriend.getOpenId());
                    List<BsWxInfo> wxInfoList = bsWxInfoMapper.selectByExample(wxInfoExample);
                    BsWxInfo bsWxInfo;
                    if(!CollectionUtils.isEmpty(wxInfoList)) {
                        // 更新
                        bsWxInfo = wxInfoList.get(0);
                        bsWxInfo.setNick(wxFriend.getWxNick());
                        bsWxInfo.setHeadImgUrl(wxFriend.getWxHeadImg());
                        bsWxInfo.setUpdateTime(new Date());
                        bsWxInfoMapper.updateByPrimaryKeySelective(bsWxInfo);
                    } else {
                        // 新增
                        bsWxInfo = new BsWxInfo();
                        bsWxInfo.setOpenId(wxFriend.getOpenId());
                        bsWxInfo.setNick(wxFriend.getWxNick());
                        bsWxInfo.setHeadImgUrl(wxFriend.getWxHeadImg());
                        bsWxInfo.setCreateTime(new Date());
                        bsWxInfo.setUpdateTime(new Date());
                        bsWxInfoMapper.insertSelective(bsWxInfo);
                    }

                    // g. 新增抽奖记录
                    if(luckyNumber > 0) {
                        for (int i = 0; i < luckyNumber; i++) {
                            BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
                            luckyDraw.setAwardId(Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT);
                            luckyDraw.setUserId(wxFriend.getShareUserId());
                            luckyDraw.setActivityId(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND);
                            luckyDraw.setIsWin(Constants.IS_WIN_YES);//是否中奖：中奖-Y；未中奖-N
                            luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);//是否兑奖：已兑奖-Y；未兑奖-N
                            luckyDraw.setIsAutoConfirm(Constants.IS_AUTO_CONFIRM_YES);
                            luckyDraw.setIsBackDraw(Constants.IS_BACK_DRAW_NO);
                            luckyDraw.setIsUserDraw(Constants.IS_USER_DRAW_YES);
                            luckyDraw.setCreateTime(new Date());
                            luckyDraw.setUpdateTime(new Date());
                            luckyDraw.setUserDrawTime(new Date());
                            luckyDraw.setNote("1");
                            bsActivityLuckyDrawMapper.insertSelective(luckyDraw);
                        }
                    }
                    // h. 新增助力记录
                    BsActivity2017anniversaryWxSurpport surpport = new BsActivity2017anniversaryWxSurpport();
                    surpport.setActivityId(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND);
                    surpport.setPartnerId(wxFriend.getShareUserId());
                    surpport.setWxInfoId(bsWxInfo.getId());
                    surpport.setContent(luckyNumber.toString());
                    surpport.setCreateTime(new Date());
                    surpport.setUpdateTime(new Date());
                    bsActivity2017anniversaryWxSurpportMapper.insertSelective(surpport);
                }
                return true;
            }
        });
    }

    @Override
    public void anniversaryUnlockGoldIngot(final Integer userId) {
        log.info("购买之后，解锁元宝开始");
        // 公式：剩余可解锁元宝的投资额 = 用户3月18日累计投资金额 - 已经解锁的元宝 * 5000
        // a. 判断用户3月18日当天投资额满5000，解锁一个元宝（5元奖励金）
        // b. 投资完成时立即发放奖励金

        // a. 判断用户3月18日当天投资额满5000，解锁一个元宝（5元奖励金）
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                String today = DateUtil.formatYYYYMMDD(new Date());
                if(Constants.GOLD_INGOT_UNLOCK_TIME.equals(today)) {
                    log.info("购买之后，解锁元宝开始，正式开始解锁");
                    // 本次解锁元宝个数
                    Integer currentGoldNum = 0;
                    // 1. 查询当前用户在3月18日当天投资的总金额（大于等于30天的产品）
                    Double investAmount = bsSubAccountMapper.sumBuyAmountByUserIdOneDay(userId, Constants.GOLD_INGOT_UNLOCK_TIME);
                    // 已经解锁的元宝个数
                    Integer unlockedGoldIngotNum = bsActivityLuckyDrawMapper.countGoldIngotByUserId(userId, Constants.ACTIVITY_ID_ANNIVERSARY_SECOND, Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT, Constants.IS_CONFIRM_YES);
                    // 未解锁元宝个数
                    Integer notUnlockGoldIngotNum = bsActivityLuckyDrawMapper.countGoldIngotByUserId(userId, Constants.ACTIVITY_ID_ANNIVERSARY_SECOND, Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT, Constants.IS_CONFIRM_NO);
                    // 剩余可解锁元宝的投资额
                    Double leftAmount = investAmount - (unlockedGoldIngotNum * Constants.GOLD_INGOT_UNLOCK_AMOUNT);
                    Double leftTimes = leftAmount / Constants.GOLD_INGOT_UNLOCK_AMOUNT;
                    // 剩余可解锁的元宝的个数
                    int leftTimeInt = MoneyUtil.round(new BigDecimal(leftTimes), 0, BigDecimal.ROUND_DOWN).intValue();

                    if(unlockedGoldIngotNum + notUnlockGoldIngotNum < Constants.ACTIVITY_ANNIVERSARY_GOLD_INIT_NUMBER) {
                        BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
                        luckyDraw.setAwardId(Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT);
                        luckyDraw.setActivityId(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND);
                        luckyDraw.setIsWin(Constants.IS_WIN_YES);//是否中奖：中奖-Y；未中奖-N
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);//是否兑奖：已兑奖-Y；未兑奖-N
                        luckyDraw.setIsAutoConfirm(Constants.IS_AUTO_CONFIRM_YES);
                        luckyDraw.setIsBackDraw(Constants.IS_BACK_DRAW_NO);
                        luckyDraw.setIsUserDraw(Constants.IS_USER_DRAW_YES);
                        luckyDraw.setUserId(userId);
                        luckyDraw.setCreateTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                        luckyDraw.setUserDrawTime(new Date());
                        luckyDraw.setNote("1");
                        bsActivityLuckyDrawMapper.insertSelective(luckyDraw);
                        notUnlockGoldIngotNum = 1;
                    }
                    if(leftTimeInt <= 0) {
                        // 投资额不足，无法解锁元宝
                    } else if(leftTimeInt >= notUnlockGoldIngotNum) {
                        currentGoldNum = notUnlockGoldIngotNum;
                        // 解锁所有剩余的元宝
                        BsActivityLuckyDrawExample luckyDrawExample = new BsActivityLuckyDrawExample();
                        luckyDrawExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND).andUserIdEqualTo(userId)
                                .andAwardIdEqualTo(Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT).andIsConfirmEqualTo(Constants.IS_CONFIRM_NO);
                        BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                        bsActivityLuckyDrawMapper.updateByExampleSelective(luckyDraw, luckyDrawExample);
                    } else {
                        currentGoldNum = leftTimeInt;
                        // 解锁部分剩余的元宝
                        BsActivityLuckyDrawExample luckyDrawExample = new BsActivityLuckyDrawExample();
                        luckyDrawExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND).andUserIdEqualTo(userId)
                                .andAwardIdEqualTo(Constants.AWARD_ID_ANNIVERSARY_GOLD_INGOT).andIsConfirmEqualTo(Constants.IS_CONFIRM_NO);
                        List<BsActivityLuckyDraw> luckyDrawList = bsActivityLuckyDrawMapper.selectByExample(luckyDrawExample);
                        for (int i = 0; i < leftTimeInt; i ++) {
                            BsActivityLuckyDraw luckyDraw = luckyDrawList.get(i);
                            luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                            luckyDraw.setConfirmTime(new Date());
                            luckyDraw.setUpdateTime(new Date());
                            bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                        }
                    }

                    for (int i = 0; i < currentGoldNum; i ++) {
                        // b. 投资完成时立即发放奖励金
                        BsBonusGrantPlan plan = new BsBonusGrantPlan();
                        plan.setUserId(userId);
                        plan.setAmount(Constants.GOLD_INGOT_AMOUNT);
                        plan.setGrantType(Constants.BONUS_GRANT_TYPE_BONUS_4_ACTIVITY);//活动奖励
                        plan.setSerialNo(1);
                        plan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
                        plan.setCreateTime(new Date());
                        plan.setUpdateTime(new Date());
                        plan.setGrantDate(new Date());
                        bonusGrantPlanMapper.insertSelective(plan);
                        userBonusGrantService.grantBonus(plan);
                    }
                } else {
                    log.info("购买之后，解锁元宝开始，但未到解锁时间：{}", Constants.GOLD_INGOT_UNLOCK_TIME);
                }
                return true;
            }
        });
    }

    @Override
    public AnniversarySecondHomePageInfoVO anniversarySecondHomePageInfo(Integer userId) {
        // a. 三重礼活动时间
        // b. 四重礼活动时间
        // c. 三重礼是否开始标识
        // d. 四重礼是否开始标识
        // e. 是否登录
        // f. 祝福语列表数据（取1000条数据，按用户祝福时间倒叙）
        // g. 年化投资额实时排行榜
        // g.1. 公式：
        // g.2. 公式：

        AnniversarySecondHomePageInfoVO result = new AnniversarySecondHomePageInfoVO();
        // a. 三重礼活动时间
        BsActivityVO activityThird = this.queryActivity(Constants.ACTIVITY_ID_ANNIVERSARY_THIRD);
        result.setStartTimeThird(activityThird.getStartTime());
        result.setEndTimeThird(activityThird.getEndTime());
        // b. 四重礼活动时间
        final BsActivityVO activityFourth = this.queryActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FOURTH);
        result.setStartTimeFourth(activityFourth.getStartTime());
        result.setEndTimeFourth(activityFourth.getEndTime());
        // c. 三重礼是否开始标识
        result.setIsStartThird(this.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_THIRD, 1, null));
        // d. 四重礼是否开始标识
        result.setIsStartFourth(this.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FOURTH, 1, null));
        // e. 是否登录
        result.setIsLogin(null == userId ? Constants.IS_LOGIN_NO : Constants.IS_LOGIN_YES);
        if(null != userId) {
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            if(null == bsUser) {
                result.setIsLogin(Constants.IS_LOGIN_NO);
            }
        }
        // f. 祝福语列表数据（取1000条数据，按用户祝福时间倒叙）
        List<BsActivity2017anniversaryUserBenison> benisonList = bsActivity2017anniversaryUserBenisonMapper.selectByPage(userId, 0, 1000, null);
        result.setBenisonList(CollectionUtils.isEmpty(benisonList) ? new ArrayList<BsActivity2017anniversaryUserBenison>() : benisonList);
        // g. 累计年化投资额实时排行榜（四重礼）
        if(Constants.ACTIVITY_IS_NOT_START.equals(result.getIsStartFourth())) {
            result.setInvestUserList(new ArrayList<AnniversaryInvestUserInfoVO>());
        } else {
            List<AnniversaryInvestUserInfoVO> investUserList = bsSubAccountMapper.selectAnniversaryList(activityFourth.getStartTime(), activityFourth.getEndTime(), 0, 15);
            // 如果年化相同，则最先到达这个投资额的用户排在前面
            if(!CollectionUtils.isEmpty(investUserList)) {
                Collections.sort(investUserList, new Comparator<AnniversaryInvestUserInfoVO>() {
                    @Override
                    public int compare(AnniversaryInvestUserInfoVO o1, AnniversaryInvestUserInfoVO o2) {
                        if(o1.getAmount().equals(o2.getAmount())) {
                            BsSubAccount a = bsSubAccountMapper.selectClosest(o1.getUserId(), activityFourth.getEndTime());
                            BsSubAccount b = bsSubAccountMapper.selectClosest(o2.getUserId(), activityFourth.getEndTime());
                            return a.getOpenTime().compareTo(b.getOpenTime());
                        } else if(o1.getAmount().compareTo(o2.getAmount()) > 0) {
                            return -1;
                        } else if(o1.getAmount().compareTo(o2.getAmount()) < 0) {
                            return 1;
                        }
                        return 0;
                    }
                });
                for (int i = 0; i < investUserList.size(); i ++) {
                    investUserList.get(i).setRank(i + 1);
                }
            }
            result.setInvestUserList(CollectionUtils.isEmpty(investUserList) ? new ArrayList<AnniversaryInvestUserInfoVO>() : investUserList);
        }
        return result;
    }

    @Override
    public ActivityBaseVO anniversaryBenison(BsActivity2017anniversaryUserBenison benison) {
        // a. 活动是否开始
        // b. 是否已经登录
        // c. 当天是否已经祝福过了（一天只能祝福一次，无论是否审核）
        // d. 存库

        ActivityBaseVO result = new ActivityBaseVO();
        // a. 活动是否开始
        result.setIsStart(this.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_THIRD, 1, null));
        if(!Constants.ACTIVITY_IS_START.equals(result.getIsStart())) {
            return result;
        }
        // b. 是否已经登录
        result.setIsLogin(null == benison.getUserId() ? Constants.IS_LOGIN_NO : Constants.IS_LOGIN_YES);
        if(Constants.IS_LOGIN_YES.equals(result.getIsLogin())) {
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(benison.getUserId());
            if(null == bsUser) {
                result.setIsLogin(Constants.IS_LOGIN_NO);
            }
        }
        if(Constants.IS_LOGIN_NO.equals(result.getIsLogin())) {
            return result;
        }

        // c. 当天是否已经祝福过了（一天只能祝福一次，无论是否审核）
        Date startDate = DateUtil.parse(DateUtil.formatYYYYMMDD(new Date()) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date endDate = DateUtil.parse(DateUtil.formatYYYYMMDD(new Date()) + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        BsActivity2017anniversaryUserBenisonExample example = new BsActivity2017anniversaryUserBenisonExample();
        example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_ANNIVERSARY_THIRD).andUserIdEqualTo(benison.getUserId()).andCreateTimeBetween(startDate, endDate);
        long count = bsActivity2017anniversaryUserBenisonMapper.countByExample(example);
        result.setIsJoined(count > 0 ? Constants.IS_YES : Constants.IS_NO);
        if(Constants.IS_YES.equals(result.getIsJoined())) {
            return result;
        }
        // d. 存库
        benison.setActivityId(Constants.ACTIVITY_ID_ANNIVERSARY_THIRD);
        benison.setCreateTime(new Date());
        benison.setUpdateTime(new Date());
        benison.setCheckStatus(Constants.BENISON_CHECK_STATUS_INIT);
        bsActivity2017anniversaryUserBenisonMapper.insertSelective(benison);

        return result;
    }

    @Override
    public void anniversaryCheckBenison(BsActivity2017anniversaryUserBenison benison) {
        BsActivity2017anniversaryUserBenison checkBenison = bsActivity2017anniversaryUserBenisonMapper.selectByPrimaryKey(benison.getId());
        if(!Constants.BENISON_CHECK_STATUS_INIT.equals(checkBenison.getCheckStatus())) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_BENISON_HAVE_CHECKED);
        }
        benison.setUpdateTime(new Date());
        benison.setCheckTime(new Date());
        bsActivity2017anniversaryUserBenisonMapper.updateByPrimaryKeySelective(benison);
    }

    @Override
    public List<BsActivity2017anniversaryUserBenison> anniversaryBenisonList(Integer pageNum, Integer numPerPage) {
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        List<BsActivity2017anniversaryUserBenison> benisonList = bsActivity2017anniversaryUserBenisonMapper.selectByPage(null, start, numPerPage, "manage");
        return CollectionUtils.isEmpty(benisonList) ? new ArrayList<BsActivity2017anniversaryUserBenison>() : benisonList;
    }

    @Override
    public long anniversaryBenisonListCount() {
        BsActivity2017anniversaryUserBenisonExample example = new BsActivity2017anniversaryUserBenisonExample();
        example.createCriteria().andCheckStatusNotEqualTo(Constants.BENISON_CHECK_STATUS_REMOVE);
        long count = bsActivity2017anniversaryUserBenisonMapper.countByExample(example);
        return count;
    }

    @Override
    public AnniversaryThirdHomePageInfoVO anniversaryThirdHomePageInfo(Integer userId) {
        // a. 五重礼活动时间
        // b. 六重礼活动时间
        // c. 五重礼是否开始标识
        // d. 六重礼是否开始标识
        // e. 是否登录状态
        // f. 五重礼当前用户今日年化投资额
        // g. 五重礼当前用户预计可瓜分奖金
        // 公式：用户当日奖励=当日总奖金*用户当日年化投资额/当日总年化投资额；
        // h. 五重礼今日总年化投资额
        // i. 五重礼下一个升级奖金额度的年化投资额
        // j. 五重礼今日年化投资额排行榜
        // k. 五重礼我瓜分到的奖金列表
        // l. 五重礼获奖名单
        // m. 六重礼是否抽中过奖品
        // n. 六重礼抽中的奖品

        final AnniversaryThirdHomePageInfoVO result = new AnniversaryThirdHomePageInfoVO();
        // a. 五重礼活动时间
        final BsActivityVO activityFifth = this.queryActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FIFTH);
        result.setStartTimeFifth(activityFifth.getStartTime());
        result.setEndTimeFifth(activityFifth.getEndTime());
        // b. 六重礼活动时间
        BsActivityVO activitySixth = this.queryActivity(Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH);
        result.setStartTimeSixth(activitySixth.getStartTime());
        result.setEndTimeSixth(activitySixth.getEndTime());
        // c. 五重礼是否开始标识
        result.setIsStartFifth(this.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FIFTH, 1, null));
        // d. 六重礼是否开始标识
        result.setIsStartSixth(this.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH, 1, null));
        // e. 是否登录状态
        result.setIsLogin(null == userId ? Constants.IS_LOGIN_NO : Constants.IS_LOGIN_YES);
        if(null != userId) {
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            if(null == bsUser) {
                result.setIsLogin(Constants.IS_LOGIN_NO);
            }
        }
        Double anniversaryAmountToday = 0d;
        if(Constants.ACTIVITY_IS_END.equals(result.getIsStartFifth())) {
            anniversaryAmountToday = bsSubAccountMapper.sumAnniversaryAmountOneDay(activityFifth.getEndTime());
        } else {
            anniversaryAmountToday = bsSubAccountMapper.sumAnniversaryAmountOneDay(DateUtil.formatYYYYMMDD(new Date()));
        }
        if(Constants.IS_LOGIN_YES.equals(result.getIsLogin())) {
            // f. 五重礼当前用户今日年化投资额（活动已结束，显示活动结束那一天的数据）
            if(Constants.ACTIVITY_IS_END.equals(result.getIsStartFifth())) {
                result.setSelfAnnualizedInvestment(bsSubAccountMapper.sumBuyAmountByUserIdBetweenDays(userId, activityFifth.getEndTime(), activityFifth.getEndTime()));
            } else {
                result.setSelfAnnualizedInvestment(bsSubAccountMapper.sumAnniversaryOnePerson(userId));
            }
            // g. 五重礼当前用户预计可瓜分奖金（活动已结束，显示活动结束那一天的数据）
            result.setTodayUserAwardAmount(this.calUserAward(result.getSelfAnnualizedInvestment(), anniversaryAmountToday));
        }
        // h. 五重礼今日总年化投资额
        result.setTodayTotalAmount(anniversaryAmountToday);
        // i. 五重礼今日可瓜分总奖金
        result.setTodayAwardAmount(this.calThatDayAwardAmount(anniversaryAmountToday));
        // i.1 五重礼下一个升级投资额
        if(anniversaryAmountToday.compareTo(6000000d) >= 0) {
            result.setNextUpAmount(0d);
        } else if(anniversaryAmountToday.compareTo(5000000d) >= 0){
            result.setNextUpAmount(600d);
        } else if(anniversaryAmountToday.compareTo(3500000d) >= 0){
            result.setNextUpAmount(500d);
        } else if(anniversaryAmountToday.compareTo(2500000d) >= 0){
            result.setNextUpAmount(350d);
        } else {
            result.setNextUpAmount(250d);
        }

        // j. 五重礼今日年化投资额排行榜
        if(Constants.ACTIVITY_IS_NOT_START.equals(result.getIsStartFifth()) || Constants.ACTIVITY_IS_NOT_START_PRE.equals(result.getIsStartFifth())) {
            // 五重礼活动未开始
            result.setTodayInvestUserList(new ArrayList<AnniversaryInvestUserInfoVO>());
        } else {
            // 五重礼活动已经开始
            List<AnniversaryInvestUserInfoVO> todayInvestUserList = new ArrayList<>();
            if(Constants.ACTIVITY_IS_END.equals(result.getIsStartFifth())) {
                todayInvestUserList = bsSubAccountMapper.selectAnniversaryTodayList(activityFifth.getEndTime(), 0, 10);
            } else {
                todayInvestUserList = bsSubAccountMapper.selectAnniversaryTodayList(DateUtil.formatYYYYMMDD(new Date()), 0, 10);
            }
            // 如果年化相同，则最先到达这个投资额的用户排在前面
            if(!CollectionUtils.isEmpty(todayInvestUserList)) {
                Collections.sort(todayInvestUserList, new Comparator<AnniversaryInvestUserInfoVO>() {
                    @Override
                    public int compare(AnniversaryInvestUserInfoVO o1, AnniversaryInvestUserInfoVO o2) {
                        if(o1.getAmount().equals(o2.getAmount())) {
                            BsSubAccount a = bsSubAccountMapper.selectClosest(o1.getUserId(), activityFifth.getEndTime());
                            BsSubAccount b = bsSubAccountMapper.selectClosest(o2.getUserId(), activityFifth.getEndTime());
                            return a.getOpenTime().compareTo(b.getOpenTime());
                        } else if(o1.getAmount().compareTo(o2.getAmount()) > 0) {
                            return -1;
                        } else if(o1.getAmount().compareTo(o2.getAmount()) < 0) {
                            return 1;
                        }
                        return 0;
                    }
                });
                for (int i = 0; i < todayInvestUserList.size(); i ++) {
                    todayInvestUserList.get(i).setRank(i + 1);
                }
            }
            result.setTodayInvestUserList(todayInvestUserList);
        }

        if(Constants.IS_LOGIN_YES.equals(result.getIsLogin())) {
            // m. 六重礼是否抽中过奖品
            BsActivityLuckyDrawExample activityLuckyDrawExample = new BsActivityLuckyDrawExample();
            activityLuckyDrawExample.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH)
                    .andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES).andIsAutoConfirmEqualTo(Constants.IS_AUTO_CONFIRM_YES);
            List<BsActivityLuckyDraw> luckyDrawListAuto = bsActivityLuckyDrawMapper.selectByExample(activityLuckyDrawExample);
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH)
                    .andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_NO).andIsAutoConfirmEqualTo(Constants.IS_AUTO_CONFIRM_NO);
            List<BsActivityLuckyDraw> luckyDrawListAutoNO = bsActivityLuckyDrawMapper.selectByExample(example);
            luckyDrawListAuto.addAll(luckyDrawListAutoNO);
            result.setIsWin(CollectionUtils.isEmpty(luckyDrawListAuto) ? Constants.IS_NO : Constants.IS_YES);
            // n. 六重礼抽中的奖品
            result.setAwardId(CollectionUtils.isEmpty(luckyDrawListAuto) ? null : luckyDrawListAuto.get(0).getAwardId());
        }
        return result;
    }

    @Override
    public List<AnniversaryAwardInfoVO> userAwardList(Integer userId) {
        // k. 五重礼我瓜分到的奖金列表（3月18日-3月24日）
        // 用户当日年化投资额列表
        BsActivityVO activity = this.queryActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FIFTH);
        Date startTime = DateUtil.parse(activity.getStartTime(), "yyyy-MM-dd");
        Date endTime = DateUtil.parse(activity.getEndTime(), "yyyy-MM-dd");
        List<AnniversaryAwardInfoVO> userAwardInfoList = bsSubAccountMapper.selectAnniversaryThatDayList(userId, activity.getStartTime(), activity.getEndTime());
        List<AnniversaryAwardInfoVO> realUserAwardList = new ArrayList<>();
        // 当日总的年化投资额列表
        List<AnniversaryAwardInfoVO> totalAwardInfoList = bsSubAccountMapper.selectAnniversaryThatDayList(null, activity.getStartTime(), activity.getEndTime());
        while (true) {
            Double userAnniAmountThatDay = 0d;// 用户年化投资
            Double anniAmountThatDay = 0d;  // 总年化投资额
            for (AnniversaryAwardInfoVO info : userAwardInfoList) {
                if(info.getTime().equals(startTime)) {
                    userAnniAmountThatDay = info.getAmount();
                    break;
                }
            }
            for (AnniversaryAwardInfoVO info : totalAwardInfoList) {
                if(info.getTime().equals(startTime)) {
                    anniAmountThatDay = info.getAmount();
                    break;
                }
            }
            AnniversaryAwardInfoVO realUserAward = new AnniversaryAwardInfoVO();
            realUserAward.setTimeStr(DateUtil.formatDateTime(startTime, "MM.dd"));
            realUserAward.setAmount(userAnniAmountThatDay);
            realUserAward.setAward(this.calUserAward(userAnniAmountThatDay, anniAmountThatDay));
            realUserAward.setAnniAmountThatDay(anniAmountThatDay);
            realUserAward.setTodayAwardAmount(this.calThatDayAwardAmount(anniAmountThatDay));
            realUserAwardList.add(realUserAward);

            // l. 五重礼获奖名单（每一天年化投资额前十的用户列表）
            startTime = DateUtil.addDays(startTime, 1);
            if(startTime.after(endTime)) {
                break;
            }
        }
        return realUserAwardList;
    }

    @Override
    public Map<String, List<HashMap<String, Object>>> winnerList() {
        Map<String, List<HashMap<String, Object>>> map = new HashMap<>();
        final BsActivityVO activity = this.queryActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FIFTH);
        Date startTime = DateUtil.parse(activity.getStartTime(), "yyyy-MM-dd HH:mm:ss");
        Date endTime = DateUtil.parse(activity.getEndTime(), "yyyy-MM-dd HH:mm:ss");
        int i = 1;
        while (true) {
            if(startTime.after(new Date())) {
                break;
            }
            if(DateUtil.isSameDay(startTime, new Date())) {
                break;
            }
            // l. 五重礼获奖名单（每一天年化投资额前十的用户列表）
            List<AnniversaryAwardInfoVO> allAwardList = bsSubAccountMapper.selectAllAwardList(DateUtil.formatYYYYMMDD(startTime), 0, 10);
            // 如果年化相同，则最先到达这个投资额的用户排在前面
            if(!CollectionUtils.isEmpty(allAwardList)) {
                final Date finalStartTime = startTime;
                Collections.sort(allAwardList, new Comparator<AnniversaryAwardInfoVO>() {
                    @Override
                    public int compare(AnniversaryAwardInfoVO o1, AnniversaryAwardInfoVO o2) {
                        Date time = finalStartTime;
                        if(o1.getAmount().equals(o2.getAmount())) {
                            BsSubAccount a = bsSubAccountMapper.selectClosest(o1.getUserId(), DateUtil.formatYYYYMMDD(time) + " 23:59:59");
                            BsSubAccount b = bsSubAccountMapper.selectClosest(o2.getUserId(), DateUtil.formatYYYYMMDD(time) + " 23:59:59");
                            return a.getOpenTime().compareTo(b.getOpenTime());
                        } else if(o1.getAmount().compareTo(o2.getAmount()) > 0) {
                            return -1;
                        } else if(o1.getAmount().compareTo(o2.getAmount()) < 0) {
                            return 1;
                        }
                        return 0;
                    }
                });
                for (int j = 0; j < allAwardList.size();j ++) {
                    allAwardList.get(j).setRank(j + 1);
                }
            }

            List<HashMap<String, Object>> result = new ArrayList<>();
            for (AnniversaryAwardInfoVO vo: allAwardList) {
                HashMap<String, Object> m = new HashMap<>();
                vo.setAward(this.calUserAward(vo.getAmount(), vo.getAnniAmountThatDay()));
                vo.setTodayAwardAmount(this.calThatDayAwardAmount(vo.getAnniAmountThatDay()));
                vo.setTimeStr(DateUtil.formatDateTime(vo.getTime(), "MM.dd"));
                m.put("time", vo.getTimeStr());
                switch (vo.getRank()) {
                    case 1 :
                    case 2 :
                    case 3 : vo.setAward(MoneyUtil.add(vo.getAward(), 200d).doubleValue());break;
                    case 4 :
                    case 5 :
                    case 6 :
                    case 7 :
                    case 8 :
                    case 9 :
                    case 10 : vo.setAward(MoneyUtil.add(vo.getAward(), 50d).doubleValue());break;
                }
                m.put("amount", vo.getAmount() == null ? 0d : vo.getAmount());
                m.put("anniAmountThatDay", vo.getAnniAmountThatDay() == null ? 0d: vo.getAnniAmountThatDay());
                m.put("todayAwardAmount", vo.getTodayAwardAmount());
                m.put("rank", vo.getRank());
                m.put("userName", vo.getUserName().substring(0, 1) + "**");
                m.put("award", vo.getAward() == null ? 0d : vo.getAward());
                result.add(m);
            }
            for(int index = result.size(); index < 10; index ++) {
                HashMap<String, Object> m = new HashMap<>();
                m.put("amount", "----");
                m.put("anniAmountThatDay", "----");
                m.put("todayAwardAmount", "----");
                m.put("rank", index);
                m.put("userName", "----");
                m.put("award", "----");
                result.add(m);
            }
            map.put(String.valueOf(i), result);
            i++;
            startTime = DateUtil.addDays(startTime, 1);
            if(startTime.after(endTime)) {
                break;
            }
            if(startTime.after(new Date())) {
                break;
            }
        }
        return map;
    }

    @Override
    public Integer anniversaryLuckyDraw(Integer userId) {
        // a. 判断活动是否开始
        // b. 判断用户是否登录
        // c. 判断用户是否已经抽过奖
        // d. 抽奖开始

        // a. 判断活动是否开始
        String isStart = this.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH);
        if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }
        // b. 判断用户是否登录
        if(null == userId) {
            throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
        } else {
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            if(null == bsUser) {
                throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
            }
        }
        BsActivityVO activity = this.queryActivity(Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH);
        // 1. 查询当前用户在3月18日当天投资的总金额（大于等于30天的产品）
        Double anniversaryAmountToday = bsSubAccountMapper.sumBuyAmountByUserIdBetweenDays(userId, activity.getStartTime(), activity.getEndTime());
        if(anniversaryAmountToday.compareTo(0d) <= 0) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_USER_NOT_INVEST);
        }
        // c. 判断用户是否已经抽过奖
        BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
        example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH)
                .andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES).andIsAutoConfirmEqualTo(Constants.IS_AUTO_CONFIRM_YES);
        int autoCount = bsActivityLuckyDrawMapper.countByExample(example);
        BsActivityLuckyDrawExample example2 = new BsActivityLuckyDrawExample();
        example2.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH)
                .andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_NO).andIsAutoConfirmEqualTo(Constants.IS_AUTO_CONFIRM_NO);
        int autoNoCount = bsActivityLuckyDrawMapper.countByExample(example2);
        int count = autoNoCount + autoCount;
        if(count > 0) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_LEFT_TIMES_ZERO);
        }
        // d. 抽奖开始
//        Double anniversaryAmountToday = bsSubAccountMapper.sumAnniversaryAmountOneDay(DateUtil.formatYYYYMMDD(new Date()));
        BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
        Integer luckyNumber = 0;
        if(anniversaryAmountToday.compareTo(1200000d) >= 0) {
            // 京东卡500/1000元
            luckyNumber = LuckyDrawUtil.luckyNumber(2);
            if(luckyNumber.equals(1)) {
                // 京东卡1000元
                this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_15, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                        Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "京东卡1000元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_NO);
            } else {
                // 京东卡500元
                this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_14, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                        Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "京东卡500元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_NO);
            }
        } else if(anniversaryAmountToday.compareTo(600000d) >= 0) {
            // 奖励金50元,京东卡100/200/500元
            luckyNumber = LuckyDrawUtil.luckyNumber(4);
            switch (luckyNumber) {
                case 1 :
                    // 奖励金50元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_11, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金50元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 50d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金50元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 2 :
                    // 京东卡100元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_12, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "京东卡100元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_NO);
                    break;
                case 3 :
                    // 京东卡200元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_13, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "京东卡200元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_NO);
                    break;
                case 4 :
                    // 京东卡500元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_14, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "京东卡500元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_NO);
                    break;
            }
        } else if(anniversaryAmountToday.compareTo(300000d) >= 0) {
            // 奖励金30/50元,京东卡100/200元
            luckyNumber = LuckyDrawUtil.luckyNumber(4);
            switch (luckyNumber) {
                case 1:
                    // 奖励金30元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_10, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金30元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 30d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金30元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 2:
                    // 奖励金50元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_11, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金50元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 50d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金50元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 3:
                    // 京东卡100元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_12, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "京东卡100元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_NO);
                    break;
                case 4:
                    // 京东卡200元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_13, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "京东卡200元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_NO);
                    break;
            }
        } else if(anniversaryAmountToday.compareTo(100000d) >= 0) {
            // 奖励金15/20/30/50元
            luckyNumber = LuckyDrawUtil.luckyNumber(4);
            switch (luckyNumber) {
                case 1:
                    // 奖励金15元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_8, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金15元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 15d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金15元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 2:
                    // 奖励金20元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_9, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金20元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 20d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金20元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 3:
                    // 奖励金30元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_10, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金30元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 30d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金30元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 4:
                    // 奖励金50元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_11, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金50元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 50d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金50元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
            }
        } else if(anniversaryAmountToday.compareTo(50000d) >= 0) {
            // 奖励金10/15/20/30元
            luckyNumber = LuckyDrawUtil.luckyNumber(4);
            switch (luckyNumber) {
                case 1:
                    // 奖励金10元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_7, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金10元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 10d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金10元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 2:
                    // 奖励金15元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_8, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金15元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 15d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金15元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 3:
                    // 奖励金20元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_9, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金20元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 20d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金20元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 4:
                    // 奖励金30元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_10, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金30元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 30d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金30元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
            }
        } else if(anniversaryAmountToday.compareTo(15000d) >= 0) {
            // 奖励金3/5/10/15/20元
            luckyNumber = LuckyDrawUtil.luckyNumber(5);
            switch (luckyNumber) {
                case 1:
                    // 奖励金3元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_5, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金3元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 3d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金3元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 2:
                    // 奖励金5元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_6, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金5元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 5d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金5元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 3:
                    // 奖励金10元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_7, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金10元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 10d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金10元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 4:
                    // 奖励金15元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_8, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金15元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 15d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金15元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 5:
                    // 奖励金20元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_9, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金20元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 20d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金20元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
            }
        } else if(anniversaryAmountToday.compareTo(3000d) >= 0) {
            // 投资红包20元，流量5M/10M/30M
            luckyNumber = LuckyDrawUtil.luckyNumber(4);
            switch (luckyNumber) {
                case 1:
                    // 投资红包20元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_3, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "投资红包20元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    try {
                        redPacketService.autoRedPacketSendByName(userId, Constants.ANNIVERSARY_RED_PACKET_SERIAL_NAME_20);
                    } catch (Exception e) {
                        log.error("给用户 {} 发放 {} 异常：{}", userId, "投资红包20元", e.getMessage());
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("投资红包20元发放失败：" + e.getMessage());
                        luckyDraw.setUpdateTime(new Date());
                        bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                        throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
                    }
                    luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                    luckyDraw.setConfirmTime(new Date());
                    luckyDraw.setUpdateTime(new Date());
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 2:
                    // 奖励金1元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_4, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金1元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 1d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金1元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 3:
                    // 奖励金3元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_5, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金3元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 3d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金3元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 4:
                    // 奖励金5元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_6, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金5元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 5d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金5元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
            }
        } else {
            // 投资红包5/10/20元，流量5M
            luckyNumber = LuckyDrawUtil.luckyNumber(4);
            switch (luckyNumber) {
                case 1:
                    // 投资红包5元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_1, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "投资红包5元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    try {
                        redPacketService.autoRedPacketSendByName(userId, Constants.ANNIVERSARY_RED_PACKET_SERIAL_NAME_5);
                    } catch (Exception e) {
                        log.error("给用户 {} 发放 {} 异常：{}", userId, "投资红包5元", e.getMessage());
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("投资红包5元发放失败：" + e.getMessage());
                        luckyDraw.setUpdateTime(new Date());
                        bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                        throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
                    }
                    luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                    luckyDraw.setConfirmTime(new Date());
                    luckyDraw.setUpdateTime(new Date());
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 2:
                    // 投资红包10元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_2, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "投资红包10元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    try {
                        redPacketService.autoRedPacketSendByName(userId, Constants.ANNIVERSARY_RED_PACKET_SERIAL_NAME_10);
                    } catch (Exception e) {
                        log.error("给用户 {} 发放 {} 异常：{}", userId, "投资红包10元", e.getMessage());
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("投资红包10元发放失败：" + e.getMessage());
                        luckyDraw.setUpdateTime(new Date());
                        bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                        throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
                    }
                    luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                    luckyDraw.setConfirmTime(new Date());
                    luckyDraw.setUpdateTime(new Date());
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 3:
                    // 投资红包20元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_3, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "投资红包20元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    try {
                        redPacketService.autoRedPacketSendByName(userId, Constants.ANNIVERSARY_RED_PACKET_SERIAL_NAME_20);
                    } catch (Exception e) {
                        log.error("给用户 {} 发放 {} 异常：{}", userId, "投资红包20元", e.getMessage());
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("投资红包20元发放失败：" + e.getMessage());
                        luckyDraw.setUpdateTime(new Date());
                        bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                        throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
                    }
                    luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                    luckyDraw.setConfirmTime(new Date());
                    luckyDraw.setUpdateTime(new Date());
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
                case 4:
                    // 奖励金1元
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_ANNIVERSARY_OF_SIX_4, Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH,
                            Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, "奖励金1元", Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                    if(this.grantBonus(luckyDraw, 1d)) {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                        luckyDraw.setConfirmTime(new Date());
                        luckyDraw.setUpdateTime(new Date());
                    } else {
                        luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                        luckyDraw.setNote("奖励金1元发放失败。");
                        luckyDraw.setUpdateTime(new Date());
                    }
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    break;
            }
        }

        return luckyDraw.getAwardId();
    }


    /**
     * 计算当天总奖金（五重礼）
     * @param anniversaryAmountThatDay  当天年化总金额
     * @return
     */
    private double calThatDayAwardAmount(Double anniversaryAmountThatDay) {
        // 当日总奖金根据当日年化投资额计算 投资额>=250W- 奖金5K; >=350W- 10K; >=500W- 20K; >=600W- 30K
        double thatDayAwardAmount = 0d;
        if(anniversaryAmountThatDay.compareTo(6000000d) >= 0) {
            thatDayAwardAmount = 30000d;
        } else if(anniversaryAmountThatDay.compareTo(5000000d) >= 0){
            thatDayAwardAmount = 20000d;
        } else if(anniversaryAmountThatDay.compareTo(3500000d) >= 0){
            thatDayAwardAmount = 10000d;
        } else if(anniversaryAmountThatDay.compareTo(2500000d) >= 0){
            thatDayAwardAmount = 5000d;
        }
        return thatDayAwardAmount;
    }

    /**
     * 计算用户应瓜分的奖金（五重礼）
     * @param userAnniAmountThatDay 当日用户年化投资额
     * @param anniAmountThatDay     当日总的年化投资额
     * @return
     */
    private double calUserAward(Double userAnniAmountThatDay, Double anniAmountThatDay) {
        // 公式：用户当日奖励=当日总奖金*用户当日年化投资额/当日总年化投资额；
        Double todayAwardAmount = this.calThatDayAwardAmount(anniAmountThatDay);
        Double todayUserAwardAmount = 0d;
        if(null != anniAmountThatDay) {
            if(anniAmountThatDay > 0 && userAnniAmountThatDay.compareTo(1000d) >= 0) {
                todayUserAwardAmount = MoneyUtil.defaultRound(MoneyUtil.divide(MoneyUtil.multiply(todayAwardAmount, userAnniAmountThatDay).doubleValue(), anniAmountThatDay)).doubleValue();
            }
        }
        return todayUserAwardAmount;
    }

    // ==================================================== 2017年318周年庆活动结束 ======================================================

    // ==================================================== 2017年女神节活动开始 ======================================================

    @Override
    public ActivityForGirl2017VO activityForGirl2017PageInfo() {
        ActivityForGirl2017VO result = new ActivityForGirl2017VO();
        BsActivity bsActivity = bsActivityMapper.selectByPrimaryKey(ActivityEnum.ACTIVITY_FOR_GIRL_2017.getId());
        BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
        example.createCriteria().andActivityIdEqualTo(ActivityEnum.ACTIVITY_FOR_GIRL_2017.getId());

        result.setStartTime(DateUtil.format(bsActivity.getStartTime()));
        result.setEndTime(DateUtil.format(bsActivity.getEndTime()));
        result.setIsStart(duringActivity(ActivityEnum.ACTIVITY_FOR_GIRL_2017.getId()));
        result.setNum(bsActivityLuckyDrawMapper.countByExample(example));
        return result;
    }

    @Override
    public ActivityForGirl2017ReceiveVO checkForGirl2017Receive(Integer userId, Integer agentId) {
        log.info("校验用户 {} 在 {} 渠道的领取资格，开始", userId, agentId);
        ActivityForGirl2017ReceiveVO result = new ActivityForGirl2017ReceiveVO();
        // 0. 活动是否开始
        result.setIsStart(duringActivity(ActivityEnum.ACTIVITY_FOR_GIRL_2017.getId()));
        // 1. 是否登录
        if(null == userId) {
            result.setIsLogin(Constants.IS_LOGIN_NO);
            return result;
        } else {
            result.setIsLogin(Constants.IS_LOGIN_YES);
        }
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
        if(null == bsUser) {
            result.setIsLogin(Constants.IS_LOGIN_NO);
            return result;
        }
        // 2. 是否指定渠道用户 -> 钱报、柯桥、瑞安、海宁
        if(null != bsUser.getAgentId()) {
            switch (bsUser.getAgentId()) {
                case Constants.AGENT_QIANBAO_ID:
                case Constants.AGENT_KQ_ID:
                case Constants.AGENT_HN_ID:
                case Constants.AGENT_RA_ID:
                    if(bsUser.getAgentId() == agentId) {
                        result.setIsSpecifyUser("yes");
                    } else {
                        result.setIsSpecifyUser("no");
                    }
                    break;
                default:
                    result.setIsSpecifyUser("no");
                    break;
            }
        } else {
            result.setIsSpecifyUser("no");
        }

        // 3. 是否已经绑卡
        if(Constants.IS_BIND_BANK_YES == bsUser.getIsBindBank()) {
            result.setIsBind("yes");
        } else {
            result.setIsBind("no");
        }
        // 4. 是否已领取
        BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
        example.createCriteria().andActivityIdEqualTo(ActivityEnum.ACTIVITY_FOR_GIRL_2017.getId()).andUserIdEqualTo(userId);
        int count = bsActivityLuckyDrawMapper.countByExample(example);
        result.setEverWin(count > 0 ? "yes" : "no");
        return result;
    }

    @Override
    @Transactional
    public ActivityForGirl2017ReceiveVO activityForGirl2017Receive(Integer userId, Integer agentId, String receiveType, String consignee, String tel, String address, String note) {
        log.info("用户 {} 在 {} 渠道开始领取奖品，领取类型是 {}", userId, agentId, receiveType);
        // 1. 校验领取资格
        ActivityForGirl2017ReceiveVO result = checkForGirl2017Receive(userId, agentId);
        if(Constants.ACTIVITY_AWARD_FOR_GIRL_TYPE_EXPRESS.equals(receiveType)) {
            if(StringUtil.isBlank(consignee)) {
                throw new PTMessageException("user_name_blank", "用户姓名不能为空");
            }
            if(StringUtil.isBlank(tel)) {
                throw new PTMessageException("tel_blank", "联系电话不能为空");
            }
            if(StringUtil.isBlank(address)) {
                throw new PTMessageException("address_blank", "联系地址不能为空");
            }
        }
        // 2. 领取
        if("yes".equals(result.getIsSpecifyUser()) && Constants.ACTIVITY_IS_START.equals(result.getIsStart()) && "yes".equals(result.getIsBind()) && "no".equals(result.getEverWin())) {
            log.info("用户 {} 符合领取资格", userId);
            BsActivityLuckyDraw bsActivityLuckyDraw = new BsActivityLuckyDraw();
            BsActivityAward award = null;
            if(Constants.ACTIVITY_AWARD_FOR_GIRL_TYPE_TO_STORE.equals(receiveType)) {
                award = bsActivityAwardMapper.selectByPrimaryKey(Constants.ACTIVITY_AWARD_FOR_GIRL_TO_STORE);
            } else if (Constants.ACTIVITY_AWARD_FOR_GIRL_TYPE_EXPRESS.equals(receiveType)) {
                award = bsActivityAwardMapper.selectByPrimaryKey(Constants.ACTIVITY_AWARD_FOR_GIRL_EXPRESS);
            }
            if(null == award) {
                throw new PTMessageException("award_not_exist", "抱歉，奖品已被领取完了");
            }
            this.initLuckyDraw(bsActivityLuckyDraw, userId, award.getId(), ActivityEnum.ACTIVITY_FOR_GIRL_2017.getId(), Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, award.getNote(), "N", "Y", "N");

            if(Constants.ACTIVITY_AWARD_FOR_GIRL_TYPE_EXPRESS.equals(receiveType)) {
                BsUserReceiveAddress bsUserReceiveAddress = new BsUserReceiveAddress();
                bsUserReceiveAddress.setUserId(userId);
                bsUserReceiveAddress.setLuckyDrawId(bsActivityLuckyDraw.getId());
                bsUserReceiveAddress.setAddress(address);
                bsUserReceiveAddress.setConsignee(consignee);
                bsUserReceiveAddress.setNote(note);
                bsUserReceiveAddress.setTel(tel);
                bsUserReceiveAddress.setCreateTime(new Date());
                bsUserReceiveAddress.setUpdateTime(new Date());
                bsUserReceiveAddressMapper.insertSelective(bsUserReceiveAddress);
            }
            log.info("用户 {} 在 {} 渠道，领取了 {}", userId, agentId, award.getContent());
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            try {
                if(Constants.ACTIVITY_AWARD_FOR_GIRL_TYPE_TO_STORE.equals(receiveType)) {
                    smsServiceClient.sendTemplateMessage(bsUser.getMobile(), TemplateKey.ACTIVITY_FOR_GIRL_TO_THE_STORE, award.getContent());
                } else {
                    smsServiceClient.sendTemplateMessage(bsUser.getMobile(), TemplateKey.ACTIVITY_FOR_GIRL_EXPRESS, award.getContent());
                }
            } catch (Exception e) {
                log.error("2017年女神节活动，用户 {} 领取奖品成功，发送短信通知异常 {}", userId, e.getMessage());
            }
            result.setIsWin("yes");
        } else {
            log.info("用户 {} 在 {} 渠道内不符合领取资格", userId, agentId);
            result.setIsWin("no");
        }
        return result;
    }
    // ==================================================== 2017年女神节活动结束 =========================================================


    // ==================================================== 2017年元宵节活动开始 =========================================================
    @Override
    public LanternFestival2017DrawResultVO lanternFestival2017LuckyDraw(Integer userId, Integer activityId) {
        LanternFestival2017DrawResultVO result = new LanternFestival2017DrawResultVO();
        result.setIsStart(this.duringActivity(activityId));
        result.setCode("000000");
        if(Constants.ACTIVITY_IS_START.equals(result.getIsStart())) {
            Date now = new Date();
            // 统计当天该用户中奖个数（由于抽奖成功，但兑奖失败记为未抽奖，故而加上is_confirm的查询条件）
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andActivityIdEqualTo(activityId).andUserIdEqualTo(userId).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES)
                    .andConfirmTimeBetween(DateUtil.parseDateTime(DateUtil.formatYYYYMMDD(now) + " 00:00:00"), DateUtil.parseDateTime(DateUtil.formatYYYYMMDD(now) + " 23:59:59"));
            int toDayCount = bsActivityLuckyDrawMapper.countByExample(example);
            BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
            if(toDayCount > 0) {
                // 当天已经摇过的用户，没有机会抽奖，但做一笔存库动作，记录用户操作
                log.info("2017年元宵活动一天只能摇一摇一次，用户 {} 今日已经摇中过 {} 次。", userId, toDayCount);
                result.setCode(PTMessageEnum.ACTIVITY_ONE_LIMIT.getCode());
                result.setMessage(PTMessageEnum.ACTIVITY_ONE_LIMIT.getMessage());
                initLuckyDraw(luckyDraw, userId, null, activityId, Constants.IS_WIN_NO, Constants.IS_CONFIRM_NO, null, null, null, null);
                result.setIsWin(luckyDraw.getIsWin());
            } else {
                // ========================================== 抽奖 ==========================================
                luckyDraw = this.luckyDraw(userId, activityId);
                // ========================================== 兑奖 ==========================================
                Map<String, String> confirmResult = null;
                if(null != luckyDraw.getAwardId()) {
                    // 奖品没有都领过，兑奖
                    confirmResult = this.luckyDrawConfirm(luckyDraw);
                }
                if(null == confirmResult) {
                    // 奖品都领过
                    result.setCode("999999");
                    result.setMessage("人气大爆发，请稍后再试");
                } else {
                    // 封装响应数据
                    if(!"000000".equals(confirmResult.get("code"))) {
                        // 红包被领用完了||其他发放红包或发放奖励金报错
                        result.setCode("999999");
                        result.setMessage("人气大爆发，请稍后再试");
                    }
                    BsActivityAward award = bsActivityAwardMapper.selectByPrimaryKey(luckyDraw.getAwardId());
                    result.setAmount(award.getContent());
                    switch (award.getId()) {
                        // 1元摇一摇奖励金
                        case Constants.YUAN_XIAO_ACTIVITY_SHAKE_1:
                            // 5元摇一摇奖励金
                        case Constants.YUAN_XIAO_ACTIVITY_SHAKE_2:
                            result.setAwardType("bonus");
                            break;
                        // 5元摇一摇红包
                        case Constants.YUAN_XIAO_ACTIVITY_SHAKE_3:
                            // 35元摇一摇红包
                        case Constants.YUAN_XIAO_ACTIVITY_SHAKE_4:
                            // 85元摇一摇红包
                        case Constants.YUAN_XIAO_ACTIVITY_SHAKE_5:
                            result.setAwardType("red_packet");
                            break;
                        default:break;
                    }
                }
                result.setIsWin(luckyDraw.getIsWin());
            }
        }
        return result;
    }

    @Override
    public void userBuyGrantBonus(Integer userId, Double amount, Integer subAccountId) {
        log.info("用户 {} 购买币港湾理财平台产品 {} 元，购买子账户编号：{}", userId, amount, subAccountId);
        String isStart = this.duringActivity(ActivityEnum.LANTERN_FESTIVAL_2017_BUY.getId());
        if(Constants.ACTIVITY_IS_START.equals(isStart)) {
            // 2017年元宵活动进行中
            int count = this.investNumInActivity();
            String countStr = String.valueOf(count);
            String lastDigitsNum = countStr.substring(countStr.length()-1, countStr.length());
            if(Constants.ACTIVITY_LAST_DIGITS_NUMBER.equals(lastDigitsNum) && Constants.ACTIVITY_INVEST_AMOUNT_LIMIT.compareTo(amount) <= 0) {
                // 灯笼尾号逢8且投资金额≥5000元，即可获得5元奖励金
                BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
                BsActivityAward award = bsActivityAwardMapper.selectByPrimaryKey(Constants.YUAN_XIAO_ACTIVITY_BUY);
                initLuckyDraw(luckyDraw, userId, award.getId(), award.getActivityId(), Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, count + "," + subAccountId, null, null, null);
                boolean isSuccess = this.grantBonus(luckyDraw);
                if(isSuccess) {
                    luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);//是否兑奖：已兑奖-Y；未兑奖-N
                    luckyDraw.setConfirmTime(new Date());
                    luckyDraw.setUpdateTime(new Date());
                } else {
                    luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);//是否兑奖：已兑奖-Y；未兑奖-N
                    luckyDraw.setNote(count + "," + subAccountId + "," + "投资成功且满足条件但发放奖励金失败");
                    luckyDraw.setUpdateTime(new Date());
                }
                bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
            }
        }
    }

    @Override
    public LanternFestival2017DrawIndexVO luckyDrawPageInfo() {
        LanternFestival2017DrawIndexVO result = new LanternFestival2017DrawIndexVO();

        // 1. 活动开始截止时间
        BsActivity activity = bsActivityMapper.selectByPrimaryKey(ActivityEnum.LANTERN_FESTIVAL_2017_SHAKE.getId());
        result.setStartTime(DateUtil.format(activity.getStartTime()));
        result.setEndTime(DateUtil.format(activity.getEndTime()));

        // 2. 摇一摇累计参与人次
        int shakeNum = bsActivityLuckyDrawMapper.countLuckyNumToDay(ActivityEnum.LANTERN_FESTIVAL_2017_SHAKE.getId());
        result.setShakeNum(shakeNum);

        // 3. 总发出奖金
        Double amount = bsActivityLuckyDrawMapper.sumAwardAmount(ActivityEnum.LANTERN_FESTIVAL_2017_SHAKE.getId());
        result.setAmount(amount == null ? 0d : amount);

        // 4. 活动期间投资人数（灯笼总数）
        result.setInvestNum(this.investNumInActivity());

        // 5. 下一个中奖号码（灯笼号）
        int initNum = result.getInvestNum();
        String lastDigitsNum;
        do {
            initNum++;
            String countStr = String.valueOf(initNum);
            lastDigitsNum = countStr.substring(countStr.length()-1, countStr.length());
        } while (!Constants.ACTIVITY_LAST_DIGITS_NUMBER.equals(lastDigitsNum));
        result.setNextLuckInvestNum(String.valueOf(initNum));

        return result;
    }

    @Override
    public LanternFestival2017LanternResultVO getList(Integer pageNum, Integer numPerPage) {
        LanternFestival2017LanternResultVO result = new LanternFestival2017LanternResultVO();
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        List<LanternFestival2017LanternDetailVO> list = bsActivityLuckyDrawMapper.selectByPage(ActivityEnum.LANTERN_FESTIVAL_2017_BUY.getId(), start, numPerPage);
        BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
        example.createCriteria().andIsWinEqualTo(Constants.IS_WIN_YES).andActivityIdEqualTo(ActivityEnum.LANTERN_FESTIVAL_2017_BUY.getId());
        int totalRows = bsActivityLuckyDrawMapper.countByExample(example);
        int totalPages = (int) Math.ceil((double) totalRows / numPerPage);
        result.setTotalPages(totalPages);
        result.setList(list);
        result.setTotalRows(totalRows);
        return result;
    }

    /**
     * 抽奖-中奖-存库
     * @param userId        用户ID
     * @param activityId    活动ID
     * @return
     */
    private BsActivityLuckyDraw luckyDraw(Integer userId, Integer activityId) {
        BsActivityLuckyDrawExample luckyDrawExample = new BsActivityLuckyDrawExample();
        luckyDrawExample.createCriteria().andActivityIdEqualTo(activityId).andUserIdEqualTo(userId).andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_YES);
        // 该用户当前活动中奖列表
        List<BsActivityLuckyDraw> luckyDrawList = bsActivityLuckyDrawMapper.selectByExample(luckyDrawExample);

        BsActivityAward award = null;
        BsActivityLuckyDraw winAward = new BsActivityLuckyDraw();
        if (CollectionUtils.isEmpty(luckyDrawList)) {
            // 1. 未中过奖品，按照指定概率获取奖品
            log.info("用户 {} 未摇中过奖品", userId);

            // 产生一个[1, 10000]的随机数
            Integer luckyNumber = LuckyDrawUtil.luckyNumber();
            if(luckyNumber <= 1500) {
                award = bsActivityAwardMapper.selectByPrimaryKey(Constants.YUAN_XIAO_ACTIVITY_SHAKE_1);
                log.info("用户 {} 摇到幸运数字 {} ，获得奖品 {}", userId, luckyNumber, award.getNote());
            } else if(luckyNumber <= 2000) {
                award = bsActivityAwardMapper.selectByPrimaryKey(Constants.YUAN_XIAO_ACTIVITY_SHAKE_2);
                log.info("用户 {} 摇到幸运数字 {} ，获得奖品 {}", userId, luckyNumber, award.getNote());
            } else if(luckyNumber <= 4000) {
                award = bsActivityAwardMapper.selectByPrimaryKey(Constants.YUAN_XIAO_ACTIVITY_SHAKE_3);
                log.info("用户 {} 摇到幸运数字 {} ，获得奖品 {}", userId, luckyNumber, award.getNote());
            } else if(luckyNumber <= 6500) {
                award = bsActivityAwardMapper.selectByPrimaryKey(Constants.YUAN_XIAO_ACTIVITY_SHAKE_4);
                log.info("用户 {} 摇到幸运数字 {} ，获得奖品 {}", userId, luckyNumber, award.getNote());
            } else if(luckyNumber <= 10000) {
                award = bsActivityAwardMapper.selectByPrimaryKey(Constants.YUAN_XIAO_ACTIVITY_SHAKE_5);
                log.info("用户 {} 摇到幸运数字 {} ，获得奖品 {}", userId, luckyNumber, award.getNote());
            }
            // 抽中奖品存库记录
            initLuckyDraw(winAward, userId, award.getId(), activityId, Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, award.getNote(), null, null, null);
        } else {
            // 2. 中过奖品，排除中奖奖品，奖品在其他奖品中随机产生
            log.info("用户 {} 总共摇中过 {} 次奖品", userId, luckyDrawList.size());
            List<Integer> awardIdList = new ArrayList<>();
            for (BsActivityLuckyDraw luckyDraw : luckyDrawList) {
                awardIdList.add(luckyDraw.getAwardId());
            }
            BsActivityAwardExample example = new BsActivityAwardExample();
            example.createCriteria().andActivityIdEqualTo(activityId).andIdNotIn(awardIdList);
            example.setOrderByClause("id asc");
            List<BsActivityAward> bsActivityAwardList = bsActivityAwardMapper.selectByExample(example);

            // 产生一个[1, bsActivityAwardList.size()]的随机数
            if(bsActivityAwardList.size() != 0) {
                Integer luckyNumber = LuckyDrawUtil.luckyNumber(bsActivityAwardList.size());
                for (int i = 1; i <= bsActivityAwardList.size(); i++) {
                    if(luckyNumber.equals(i)) {
                        award = bsActivityAwardList.get(i-1);
                        break;
                    }
                }
                // 抽中奖品存库记录
                initLuckyDraw(winAward, userId, award.getId(), activityId, Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, award.getNote(), null, null, null);
            } else {
                // 抽中奖品存库记录
                initLuckyDraw(winAward, userId, null, activityId, Constants.IS_WIN_NO, Constants.IS_CONFIRM_NO, null, null, null, null);
            }
        }
        return winAward;
    }

    /**
     * 兑奖
     * @param luckyDraw
     */
    private Map<String, String> luckyDrawConfirm(final BsActivityLuckyDraw luckyDraw) {
        return transactionTemplate.execute(new TransactionCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInTransaction(TransactionStatus status) {
                Map<String, String> result = new HashMap<>();
                result.put("code", "000000");
                // 已中奖，开始兑奖，判断是红包还是奖励金
                switch (luckyDraw.getAwardId()) {
                    // 1元摇一摇奖励金
                    case Constants.YUAN_XIAO_ACTIVITY_SHAKE_1:
                    // 5元摇一摇奖励金
                    case Constants.YUAN_XIAO_ACTIVITY_SHAKE_2:
                        boolean isConfirm = grantBonus(luckyDraw);
                        result.put("code", isConfirm ? "000000" : "999999");
                        break;
                    // 5元摇一摇红包
                    case Constants.YUAN_XIAO_ACTIVITY_SHAKE_3:
                    // 35元摇一摇红包
                    case Constants.YUAN_XIAO_ACTIVITY_SHAKE_4:
                    // 85元摇一摇红包
                    case Constants.YUAN_XIAO_ACTIVITY_SHAKE_5:
                        result = sendRedPacket(luckyDraw);
                        break;
                    default:break;
                }

                if("000000".equals(result.get("code"))) {
                    luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);//是否兑奖：已兑奖-Y；未兑奖-N
                    luckyDraw.setConfirmTime(new Date());
                    luckyDraw.setUpdateTime(new Date());
                } else {
                    luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);//是否兑奖：已兑奖-Y；未兑奖-N
                    luckyDraw.setUpdateTime(new Date());
                    luckyDraw.setNote("抽奖成功，兑奖失败，此次抽奖默认为未抽奖：" + result.get("message"));
                }
                bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                return result;
            }
        });
    }

    /**
     * 自动发放红包
     * @param luckyDraw 中奖信息
     * @return
     */
    private Map<String, String> sendRedPacket(BsActivityLuckyDraw luckyDraw) {
        // 上线之前先配置红包，获得红包批次号之后，修改此处代码，将批次号改掉
        Map<String, String> result = new HashMap<>();
        result.put("code", "000000");
        try {
            switch (luckyDraw.getAwardId()) {
                case Constants.YUAN_XIAO_ACTIVITY_SHAKE_3:
                    redPacketService.autoRedPacketSendBySeriaNo(luckyDraw.getUserId(), Constants.YUAN_XIAO_RED_PACKET_SERIAL_NO_32);
                    break;
                case Constants.YUAN_XIAO_ACTIVITY_SHAKE_4:
                    redPacketService.autoRedPacketSendBySeriaNo(luckyDraw.getUserId(), Constants.YUAN_XIAO_RED_PACKET_SERIAL_NO_33);
                    break;
                case Constants.YUAN_XIAO_ACTIVITY_SHAKE_5:
                    redPacketService.autoRedPacketSendBySeriaNo(luckyDraw.getUserId(), Constants.YUAN_XIAO_RED_PACKET_SERIAL_NO_34);
                    break;
                default:break;
            }
        } catch (PTMessageException e) {
            log.error("给用户 {} 发放红包 {} 异常：{}", luckyDraw.getUserId(), luckyDraw.getAwardId(), e.getMessage());
            result.put("code", e.getErrCode());
            result.put("message", e.getMessage());
        } catch (Exception e) {
            log.error("给用户 {} 发放红包 {} 异常：{}", luckyDraw.getUserId(), luckyDraw.getAwardId(), e.getMessage());
            result.put("code", "999999");
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * 自动发放奖励金
     * @param luckyDraw 中奖信息
     * @return
     */
    private boolean grantBonus(BsActivityLuckyDraw luckyDraw) {
        BsActivityAward award = bsActivityAwardMapper.selectByPrimaryKey(luckyDraw.getAwardId());
        BsBonusGrantPlan plan = new BsBonusGrantPlan();
        plan.setUserId(luckyDraw.getUserId());
        plan.setAmount(Double.valueOf(award.getContent()));
        plan.setGrantType(Constants.BONUS_GRANT_TYPE_BONUS_4_ACTIVITY);//活动奖励
        plan.setSerialNo(1);
        plan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
        plan.setCreateTime(new Date());
        plan.setUpdateTime(new Date());
        plan.setGrantDate(new Date());
        bonusGrantPlanMapper.insertSelective(plan);
        return userBonusGrantService.grantBonus(plan);
    }

    /**
     * 自动发放奖励金
     * @param luckyDraw 中奖信息
     * @return
     */
    private boolean grantBonus(BsActivityLuckyDraw luckyDraw, Double awardAmount) {
        BsBonusGrantPlan plan = new BsBonusGrantPlan();
        plan.setUserId(luckyDraw.getUserId());
        plan.setAmount(awardAmount);
        plan.setGrantType(Constants.BONUS_GRANT_TYPE_BONUS_4_ACTIVITY);//活动奖励
        plan.setSerialNo(1);
        plan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
        plan.setCreateTime(new Date());
        plan.setUpdateTime(new Date());
        plan.setGrantDate(new Date());
        bonusGrantPlanMapper.insertSelective(plan);
        return userBonusGrantService.grantBonus(plan);
    }

    /**
     * 活动期间内投资个数
     * @return
     */
    private Integer investNumInActivity() {
        BsActivity bsActivity = bsActivityMapper.selectByPrimaryKey(ActivityEnum.LANTERN_FESTIVAL_2017_BUY.getId());
        int count = bsSubAccountMapper.countLanternActivityBuy(bsActivity.getStartTime(), bsActivity.getEndTime());
//        BsSubAccountExample example = new BsSubAccountExample();
//        example.createCriteria().andOpenTimeBetween(bsActivity.getStartTime(), bsActivity.getEndTime());
//        int count = bsSubAccountMapper.countByExample(example);
        return count;
    }

    // ==================================================== 2017年元宵节活动结束 =========================================================

    private void initWxSurpport(BsActivity2017anniversaryWxSurpport surpport, Integer activityId, Integer luckyDrawId, Integer shareUserId, Integer wxInfoId, String content) {
        surpport.setActivityId(activityId);
        surpport.setLuckyDrawId(luckyDrawId);
        surpport.setPartnerId(shareUserId);
        surpport.setWxInfoId(wxInfoId);
        surpport.setContent(content);
        surpport.setCreateTime(new Date());
        surpport.setUpdateTime(new Date());
        bsActivity2017anniversaryWxSurpportMapper.insertSelective(surpport);
    }

    private void initLuckyDraw(BsActivityLuckyDraw activityLuckyDraw, Integer userId, Integer awardId, Integer activityId, String isWin, String isConfirm, String note, String isBackDraw, String isUserDraw, String isAutoConfirm) {
        activityLuckyDraw.setAwardId(awardId);
        activityLuckyDraw.setUserId(userId);
        activityLuckyDraw.setActivityId(activityId);
        activityLuckyDraw.setIsWin(isWin);//是否中奖：中奖-Y；未中奖-N
        activityLuckyDraw.setIsConfirm(isConfirm);//是否兑奖：已兑奖-Y；未兑奖-N
        if(null == isAutoConfirm) {
            activityLuckyDraw.setIsAutoConfirm("Y");//是否自动兑奖-Y
        } else {
            activityLuckyDraw.setIsAutoConfirm(isAutoConfirm);
        }
        if(null == isBackDraw) {
            activityLuckyDraw.setIsBackDraw("N");//是否后台抽奖-N
        } else {
            activityLuckyDraw.setIsBackDraw(isBackDraw);
            if(Constants.IS_BACK_DRAW_YES.equals(isBackDraw)) {
                activityLuckyDraw.setBackDrawTime(new Date());
            }
        }
        if(null == isUserDraw) {
            activityLuckyDraw.setIsUserDraw("Y");//是否用户抽奖-Y
            activityLuckyDraw.setUserDrawTime(new Date());
        } else {
            activityLuckyDraw.setIsUserDraw(isUserDraw);
            if(Constants.IS_USER_DRAW_YES.equals(isUserDraw)) {
                activityLuckyDraw.setUserDrawTime(new Date());
            }
        }
        activityLuckyDraw.setCreateTime(new Date());
        activityLuckyDraw.setUpdateTime(new Date());
        activityLuckyDraw.setNote(note);
        bsActivityLuckyDrawMapper.insertSelective(activityLuckyDraw);
    }

	@Override
	public List<PlayerKillingVO> springInvestRankingList() {
		List<PlayerKillingVO> list = bsSubAccountMapper.springInvestRankingList();
		return list;
	}

	@Override
	public Double springUserInvestAmount(Integer userId) {
		Double amount = bsSubAccountMapper.springUserInvestAmount(userId);
		return amount == null ? 0d : amount;
	}

	@Override
	public List<PlayerKillingVO> springUserInvitedList(Integer userId) {
		List<PlayerKillingVO> list = bsSubAccountMapper.springUserInvitedList(userId);
		return list;
	}

    @Override
    public void joinTeam(Integer userId, Integer teamId, Integer serial) {
        BsActivityTeam team = bsActivityTeamMapper.selectByPrimaryKey(teamId);
        log.info("用户 {} 开始抱团，团队号是：{} - {}", userId, team.getName(), serial);
        // 0. 活动开始与否，用户登录与否
        String isStart = this.duringActivity(Constants.ACTIVITY_ID_2017_TEAM);
        if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }
        //  判断用户是否登录
        if(null == userId) {
            throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
        } else {
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            if(null == bsUser) {
                throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
            }
        }

        BsActivityJoinTeamExample example = new BsActivityJoinTeamExample();
        example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_TEAM);
        int joinedTimes = bsActivityJoinTeamMapper.countByExample(example);
        if(Constants.ACTIVITY_JOIN_TEAM_TIMES <= joinedTimes) {
            throw new PTMessageException(PTMessageEnum.JOIN_TEAM_COUNT_LIMIT.getCode(), "用户最多可抱团" + Constants.ACTIVITY_JOIN_TEAM_TIMES + "次");
        }
        BsActivityJoinTeamExample sameSerialExample = new BsActivityJoinTeamExample();
        sameSerialExample.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_TEAM)
                .andTeamIdEqualTo(teamId).andSerialEqualTo(serial);
        int sameSerialJoinedTimes = bsActivityJoinTeamMapper.countByExample(sameSerialExample);
        if(sameSerialJoinedTimes > 0) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "您已加入此团");
        }

        BsActivityJoinTeamExample sameTeamExample = new BsActivityJoinTeamExample();
        sameTeamExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_TEAM)
                .andTeamIdEqualTo(teamId).andSerialEqualTo(serial);
        int sameTeamJoinedTimes = bsActivityJoinTeamMapper.countByExample(sameTeamExample);
        if(sameTeamJoinedTimes >= team.getFullNumber()) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "您加入的团已满");
        }

        // 插入抱团记录
        BsActivityJoinTeam joinTeam = new BsActivityJoinTeam();
        joinTeam.setUserId(userId);
        joinTeam.setActivityId(Constants.ACTIVITY_ID_2017_TEAM);
        joinTeam.setAwardSendStatus(Constants.NO);
        joinTeam.setTeamId(teamId);
        joinTeam.setSerial(serial);
        joinTeam.setCreateTime(new Date());
        joinTeam.setUpdateTime(new Date());
        bsActivityJoinTeamMapper.insertSelective(joinTeam);

        BsActivityJoinTeamExample sendAwardExample = new BsActivityJoinTeamExample();
        sendAwardExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_TEAM).andTeamIdEqualTo(teamId).andSerialEqualTo(serial);
        List<BsActivityJoinTeam> sendAwardList = bsActivityJoinTeamMapper.selectByExample(sendAwardExample);
        if(!CollectionUtils.isEmpty(sendAwardList)) {
            if(sendAwardList.size() == team.getFullNumber()) {
                String policyType = new String();
                if(Constants.ACTIVITY_TEAM_A.equals(team.getName())) {
                    policyType = "存管抱团奖励红包01";
                } else if(Constants.ACTIVITY_TEAM_B.equals(team.getName())) {
                    policyType = "存管抱团奖励红包02";
                } else if(Constants.ACTIVITY_TEAM_C.equals(team.getName())) {
                    policyType = "存管抱团奖励红包03";
                } else if(Constants.ACTIVITY_TEAM_D.equals(team.getName())) {
                    policyType = "存管抱团奖励红包04";
                } else if(Constants.ACTIVITY_TEAM_E.equals(team.getName())) {
                    policyType = "存管抱团奖励红包05";
                }
                // 满员触发发放奖励红包
                for (BsActivityJoinTeam activityJoinTeam : sendAwardList) {
                    boolean sendSuccess = true;
                    try {
                        redPacketService.autoRedPacketPolicyType(activityJoinTeam.getUserId(), policyType);
                    } catch (Exception e) {
                        log.info("用户：{} 存管抱团奖励红包发放失败，失败原因：{}", activityJoinTeam.getUserId(), e.getMessage());
                        activityJoinTeam.setAwardSendStatus(Constants.FAIL);
                        activityJoinTeam.setNote(policyType + "发放失败，失败原因：" + e.getMessage());
                        activityJoinTeam.setUpdateTime(new Date());
                        bsActivityJoinTeamMapper.updateByPrimaryKeySelective(activityJoinTeam);
                        sendSuccess = false;
                    }
                    if(sendSuccess) {
                        activityJoinTeam.setAwardSendStatus(Constants.YES);
                        activityJoinTeam.setNote(policyType + "发放成功");
                        activityJoinTeam.setUpdateTime(new Date());
                        bsActivityJoinTeamMapper.updateByPrimaryKeySelective(activityJoinTeam);
                    }
                }
            }
        }

    }

    @Override
    public DepActivityInfoVO depActivityInfo(Integer userId) {

        DepActivityInfoVO result = new DepActivityInfoVO();
        String isStartAnswer = this.duringActivity(Constants.ACTIVITY_ID_2017_ANSWER);
        BsActivityVO activityAnswer = this.queryActivity(Constants.ACTIVITY_ID_2017_ANSWER);

        String isStartTeam = this.duringActivity(Constants.ACTIVITY_ID_2017_TEAM);
        BsActivityVO activityTeam = this.queryActivity(Constants.ACTIVITY_ID_2017_TEAM);

        String isStartLight = this.duringActivity(Constants.ACTIVITY_ID_2017_LIGHT);
        BsActivityVO activityLight = this.queryActivity(Constants.ACTIVITY_ID_2017_LIGHT);

        //  判断用户是否登录
        String isLogin = Constants.IS_LOGIN_YES;
        if(null == userId) {
            isLogin = Constants.IS_LOGIN_NO;
        } else {
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            if(null == bsUser) {
                isLogin = Constants.IS_LOGIN_NO;
            }
        }
        result.setIsLogin(isLogin);

        // 1. 答题活动页面信息
        ActivityAnswerInfoVO answer = new ActivityAnswerInfoVO();
        answer.setIsStart(isStartAnswer);
        answer.setIsLogin(isLogin);
        answer.setStartTime(activityAnswer.getStartTime());
        answer.setEndTime(activityAnswer.getEndTime());
        if(Constants.IS_LOGIN_YES.equals(isLogin)) {
            BsActivityLuckyDrawExample answerExample = new BsActivityLuckyDrawExample();
            answerExample.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_ANSWER);
            List<BsActivityLuckyDraw> list = bsActivityLuckyDrawMapper.selectByExample(answerExample);
            if(!CollectionUtils.isEmpty(list)) {
                if (Constants.IS_CONFIRM_NO.equals(list.get(0).getIsConfirm())) {
                    answer.setNote("no_red_packet");
                }
                answer.setIsJoined(Constants.YES);
                String note = list.get(0).getNote();
                String[] info = note.split(",");
                answer.setCorrectAnswer(Integer.parseInt(info[0]));
                answer.setRedAmount(Double.parseDouble(info[1]));
            } else {
                answer.setIsJoined(Constants.NO);
            }
        } else {
            answer.setIsJoined(Constants.NO);
        }
        result.setAnswer(answer);

        // 2. 抱团活动页面信息
        List<ActivityTeamInfoVO> teamInfo =  new ArrayList<>();
        List<BsActivityTeam> teams = bsActivityTeamMapper.selectLatestTeamNumber();
        for (BsActivityTeam team: teams) {
            ActivityTeamInfoVO data = new ActivityTeamInfoVO();
            String[] names = team.getName().split("-");
            Integer serial = Integer.parseInt(names[names.length-1]);
            BsActivityJoinTeamExample joinTeamExample = new BsActivityJoinTeamExample();
            joinTeamExample.createCriteria().andTeamIdEqualTo(team.getId()).andSerialEqualTo(serial);
            // 当前团员
            int sameSerialCount = bsActivityJoinTeamMapper.countByExample(joinTeamExample);

            // 当前用户已参与次数
            if(Constants.IS_LOGIN_YES.equals(isLogin)) {
                BsActivityJoinTeamExample example = new BsActivityJoinTeamExample();
                example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_TEAM);
                int joinedTimes = bsActivityJoinTeamMapper.countByExample(example);
                if(Constants.ACTIVITY_JOIN_TEAM_TIMES <= joinedTimes) {
                    // 参团次数已满
                    data.setStatus(Constants.JOIN_TEAM_STATUS_FULL);
                } else {
                    data.setStatus(Constants.JOIN_TEAM_STATUS_JOIN);
                }

                BsActivityJoinTeamExample sameSerialExample = new BsActivityJoinTeamExample();
                sameSerialExample.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_TEAM)
                        .andTeamIdEqualTo(team.getId()).andSerialEqualTo(serial);
                int sameSerialJoinedTimes = bsActivityJoinTeamMapper.countByExample(sameSerialExample);
                if(sameSerialJoinedTimes > 0) {
                    data.setIsJoined(Constants.YES);
                } else {
                    data.setIsJoined(Constants.NO);
                }
            }

            data.setIsStart(isStartTeam);
            data.setIsLogin(isLogin);
            data.setTeamName(Constants.ACTIVITY_IS_NOT_START.equals(isStartTeam) ? (names[0] + "-1") : team.getName());
            data.setSerial(serial);
            data.setFullNumber(team.getFullNumber());
            data.setTeamId(team.getId());
            data.setCurrentNumber(Constants.ACTIVITY_IS_NOT_START.equals(isStartTeam) ? 0 : sameSerialCount);
            data.setStartTime(activityTeam.getStartTime());
            data.setEndTime(activityTeam.getEndTime());
            teamInfo.add(data);
        }
        result.setTeamList(teamInfo);

        // 3. 点亮活动页面信息
        ActivityBaseVO light = new ActivityBaseVO();
        light.setIsLogin(isLogin);
        light.setIsStart(isStartLight);
        light.setStartTime(activityLight.getStartTime());
        light.setEndTime(activityLight.getEndTime());
        result.setLight(light);
        return result;
    }

    @Override
    public ActivityForLightUp2017VO activityForLightUp2017VOPageInfo(Integer userId) {
        ActivityForLightUp2017VO result = new ActivityForLightUp2017VO();
        BsActivity bsActivity = bsActivityMapper.selectByPrimaryKey(Constants.ACTIVITY_ID_2017_LIGHT);
        //统计点亮存管图标的人数 / 初始值300
        int count = 0;
        BsActivityLuckyDrawExample luckyDrawExample = new BsActivityLuckyDrawExample();
        luckyDrawExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_LIGHT);
        count = bsActivityLuckyDrawMapper.countByExample(luckyDrawExample) + 300;

        result.setStartTime(DateUtil.format(bsActivity.getStartTime()));
        result.setEndTime(DateUtil.format(bsActivity.getEndTime()));
        result.setIsStart(duringActivity(Constants.ACTIVITY_ID_2017_LIGHT));
        result.setNum(count);

        //查询用户是否已经点亮存管图标
        BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
        example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_LIGHT);
        List<BsActivityLuckyDraw> list = bsActivityLuckyDrawMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(list)) {
            result.setIsLightUp(Constants.ACTIVITY_IS_LIGHT_UP);
        }else {
            result.setIsLightUp(Constants.ACTIVITY_IS_NOT_LIGHT_UP);
        }
        return result;
    }

    @Override
    public int addLightUpDepLogo(Integer userId) {
        BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
        example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_LIGHT);
        List<BsActivityLuckyDraw> list = bsActivityLuckyDrawMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(list)) { //该用户已点亮图标
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "您已点亮存管图标");
        }else {
            BsActivityLuckyDraw activityLuckyDraw = new BsActivityLuckyDraw();
            activityLuckyDraw.setUserId(userId);
            activityLuckyDraw.setActivityId(Constants.ACTIVITY_ID_2017_LIGHT);
            activityLuckyDraw.setIsBackDraw(Constants.IS_BACK_DRAW_NO); //是否后台抽奖
            activityLuckyDraw.setIsUserDraw(Constants.IS_USER_DRAW_YES); //用户是否抽奖-Y
            activityLuckyDraw.setUserDrawTime(new Date()); //用户抽奖时间
            activityLuckyDraw.setIsWin(Constants.IS_WIN_YES); //是否中奖
            activityLuckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO); //是否兑奖
            activityLuckyDraw.setIsAutoConfirm(Constants.IS_AUTO_CONFIRM_NO); //是否自动兑奖
            activityLuckyDraw.setCreateTime(new Date());
            activityLuckyDraw.setUpdateTime(new Date());
            return bsActivityLuckyDrawMapper.insertSelective(activityLuckyDraw);
        }
    }

    @Override
    public int queryCountLightUpDepLogo() {
        int count = 0;
        BsActivityLuckyDrawExample luckyDrawExample = new BsActivityLuckyDrawExample();
        luckyDrawExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_LIGHT);
        count = bsActivityLuckyDrawMapper.countByExample(luckyDrawExample) + 300;
        return count;
    }
    
    @Override
	public Integer depAnswer(Integer userId, Integer subject) {
    	// 0. 活动开始与否，用户登录与否
        String isStart = this.duringActivity(Constants.ACTIVITY_ID_2017_ANSWER);
        if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }
        //  判断用户是否登录
        if(null == userId) {
            throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
        } else {
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            if(null == bsUser) {
                throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
            }
        }
        
        // 一个用户只能参加一次
        BsActivityLuckyDrawExample activityLuckyDrawExample = new BsActivityLuckyDrawExample();
        activityLuckyDrawExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_ANSWER).andUserIdEqualTo(userId);
        int activityLuckyDrawTimes = bsActivityLuckyDrawMapper.countByExample(activityLuckyDrawExample);
        if (activityLuckyDrawTimes > 0) {
        	throw new PTMessageException(PTMessageEnum.JOIN_ANSWER_ACTIVITY.getCode(), "您已参加答题活动");
        }
        
        // 插入活动记录
		BsActivityLuckyDraw activityLuckyDraw = new BsActivityLuckyDraw();
		activityLuckyDraw.setUserId(userId);
		activityLuckyDraw.setActivityId(Constants.ACTIVITY_ID_2017_ANSWER);
		activityLuckyDraw.setIsWin(Constants.IS_WIN_YES);//是否中奖：中奖-Y；未中奖-N
		activityLuckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);//是否兑奖：已兑奖-Y；未兑奖-N
		activityLuckyDraw.setIsAutoConfirm(Constants.IS_AUTO_CONFIRM_YES);
		activityLuckyDraw.setIsBackDraw(Constants.IS_BACK_DRAW_NO);
		activityLuckyDraw.setIsUserDraw(Constants.IS_USER_DRAW_YES);
		activityLuckyDraw.setCreateTime(new Date());
		activityLuckyDraw.setUpdateTime(new Date());
		activityLuckyDraw.setUserDrawTime(new Date());
		activityLuckyDraw.setNote(subject+","+getAnswerRedPacket(subject));
		bsActivityLuckyDrawMapper.insertSelective(activityLuckyDraw);
		String policyType = "";
		if(Constants.ACTIVITY_ANSWER_0 == subject || Constants.ACTIVITY_ANSWER_1 == subject) {
            policyType = "存管答题奖励红包01";
        } else if(Constants.ACTIVITY_ANSWER_2 == subject) {
            policyType = "存管答题奖励红包02";
        } else if(Constants.ACTIVITY_ANSWER_3 == subject) {
            policyType = "存管答题奖励红包03";
        } else if(Constants.ACTIVITY_ANSWER_4 == subject) {
            policyType = "存管答题奖励红包04";
        }

        boolean sendSuccess = true;
		try {
			redPacketService.autoRedPacketPolicyType(userId, policyType);
	    } catch (Exception e) {
	        log.error("给用户 {} 发放 {} 异常：{}", userId, "存管答题奖励红包", e.getMessage());
	        activityLuckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
            // 后期补发红包的依据，不能覆盖掉分数和红包金额
	        activityLuckyDraw.setNote(activityLuckyDraw.getNote() + ",存管答题奖励红包发放失败：" + e.getMessage());
	        activityLuckyDraw.setUpdateTime(new Date());
	        bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(activityLuckyDraw);
            sendSuccess = false;
	    }
        if(sendSuccess) {
            activityLuckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
            activityLuckyDraw.setConfirmTime(new Date());
            activityLuckyDraw.setUpdateTime(new Date());
            bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(activityLuckyDraw);
        }
		return getAnswerRedPacket(subject);
	}

    @Override
    public List<ActivityYouFuRankInfoVO> youFuRankInfo() {
        return bsActivityMapper.selectYouFuRankList();
    }

    @Override
    public ActivityYouFuSelfInfoVO youFuSelfInfo(Integer userId) {
        checkUserId(userId);
        ActivityYouFuSelfInfoVO result = bsActivityMapper.selectYouFuSelfInfo(userId);
        result = result == null ? new ActivityYouFuSelfInfoVO() : result;
        result.setIsStart(this.duringActivity(Constants.ACTIVITY_ID_2017_YOUFU_SHARE));
        return result;
    }

    @Override
    public void share(final Integer userId) {
        final String isStart = this.duringActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_SHARE);
        if(Constants.ACTIVITY_IS_START.equals(isStart)) {
            // 1. 数据校验
            checkUserId(userId);

            // 2. 当天没分享过，新增记录，并随机获得一个感恩红包
            Date now = new Date();
            String YYYYMMDD = DateUtil.formatYYYYMMDD(now);
            Date startTime = DateUtil.parse(YYYYMMDD + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            Date endTime = DateUtil.parse(YYYYMMDD + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_SHARE)
                    .andIsConfirmEqualTo(Constants.IS_CONFIRM_YES).andConfirmTimeBetween(startTime, endTime);
            List<BsActivityLuckyDraw> list = bsActivityLuckyDrawMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(list)) {
                BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
                Integer awardId = null;
                String serialName = null;
                int luckyNumber = LuckyDrawUtil.luckyNumber(4);
                switch (luckyNumber) {
                    case 1:
                        awardId = Constants.AWARD_ID_2017_SHARE_RED_5;
                        serialName = "感恩5元红包";
                        break;
                    case 2:
                        awardId = Constants.AWARD_ID_2017_SHARE_RED_30;
                        serialName = "感恩30元红包";
                        break;
                    case 3:
                        awardId = Constants.AWARD_ID_2017_SHARE_RED_75;
                        serialName = "感恩75元红包";
                        break;
                    case 4:
                        awardId = Constants.AWARD_ID_2017_SHARE_RED_350;
                        serialName = "感恩350元红包";
                        break;
                }
                this.initLuckyDraw(luckyDraw, userId, awardId, Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_SHARE,
                        Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, serialName, Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
                try {
                    redPacketService.autoRedPacketSendByName(userId, serialName);
                } catch (Exception e) {
                    log.error("用户每天首次分享活动至微信（朋友圈/朋友），给用户 {} 发放 {} 异常：{}", userId, serialName, e.getMessage());
                    luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
                    luckyDraw.setNote(serialName + "发放失败：" + e.getMessage());
                    luckyDraw.setUpdateTime(new Date());
                    bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
                    throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER.getCode(), "红包发放失败，请重新分享");
                }
                luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
                luckyDraw.setConfirmTime(new Date());
                luckyDraw.setUpdateTime(new Date());
                bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
            }
        }
    }

    @Override
    public ActivityBaseVO sharePageInfo(Integer userId) {
        ActivityBaseVO result = new ActivityBaseVO();

        if(Constants.IS_LOGIN_YES.equals(isLogin(userId))) {
            Date now = new Date();
            String YYYYMMDD = DateUtil.formatYYYYMMDD(now);
            Date startTime = DateUtil.parse(YYYYMMDD + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            Date endTime = DateUtil.parse(YYYYMMDD + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_SHARE)
                    .andIsConfirmEqualTo(Constants.IS_CONFIRM_YES).andConfirmTimeBetween(startTime, endTime);
            List<BsActivityLuckyDraw> list = bsActivityLuckyDrawMapper.selectByExample(example);
            if(!CollectionUtils.isEmpty(list)) {
                result.setIsJoined(Constants.YES);
            } else {
                result.setIsJoined(Constants.NO);
            }
        } else {
            result.setIsJoined(Constants.NO);

        }
        return result;
    }

    @Override
    public Integer investExchange(Integer userId, Integer term) {
        jsClientDaoSupport.lock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());
        try {
            // 1. 活动是否开始
            String isStart = this.duringActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST);
            if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
            } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
            }

            // 2. 用户是否登录
            checkUserId(userId);

            int giftTotalNumber = 0;
            double investLimit = 0d;
            int awardId = 0;
            switch (term) {
                case 1:
                    giftTotalNumber = Constants.TERM_1_GIFT_NUMBER;
                    investLimit = Constants.TERM_1_INVEST_LIMIT;
                    awardId = Constants.AWARD_ID_2017_INVEST_TERM_1_MAOJIN;
                    break;
                case 3:
                    giftTotalNumber = Constants.TERM_3_GIFT_NUMBER;
                    investLimit = Constants.TERM_3_INVEST_LIMIT;
                    awardId = Constants.AWARD_ID_2017_INVEST_TERM_3_YANGSHENG;
                    break;
                case 6:
                    giftTotalNumber = Constants.TERM_6_GIFT_NUMBER;
                    investLimit = Constants.TERM_6_INVEST_LIMIT;
                    awardId = Constants.AWARD_ID_2017_INVEST_TERM_6_TIEPI;
                    break;
                case 12:
                    giftTotalNumber = Constants.TERM_12_GIFT_NUMBER;
                    investLimit = Constants.TERM_12_INVEST_LIMIT;
                    awardId = Constants.AWARD_ID_2017_INVEST_TERM_12_YANWO;
                    break;
            }
            BsActivityLuckyDrawExample luckyDrawExample = new BsActivityLuckyDrawExample();
            luckyDrawExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST)
                    .andAwardIdEqualTo(awardId).andIsWinEqualTo(Constants.IS_WIN_YES);
            int count = bsActivityLuckyDrawMapper.countByExample(luckyDrawExample);
            // 3. 当前期限港湾计划产品对应礼品是否已经兑换完
            if(count >= giftTotalNumber) {
                throw new PTMessageException(PTMessageEnum.GIFTS_FINISHED);
            }
            // 4. 当前用户是否已经兑换过当前产品对应礼品了
            BsActivityLuckyDrawExample userLuckyDrawExample = new BsActivityLuckyDrawExample();
            userLuckyDrawExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST)
                    .andAwardIdEqualTo(awardId).andIsWinEqualTo(Constants.IS_WIN_YES).andUserIdEqualTo(userId);
            count = bsActivityLuckyDrawMapper.countByExample(userLuckyDrawExample);
            if(count > 0) {
                throw new PTMessageException(PTMessageEnum.GIFTS_HAVE_BEEN_FINISHED);
            }
            // 5. 当前用户是否兑换过两份礼品
            BsActivityLuckyDrawExample twoLuckyDrawExample = new BsActivityLuckyDrawExample();
            twoLuckyDrawExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST)
                    .andIsWinEqualTo(Constants.IS_WIN_YES).andUserIdEqualTo(userId);
            count = bsActivityLuckyDrawMapper.countByExample(twoLuckyDrawExample);
            if(count >= 2) {
                throw new PTMessageException(PTMessageEnum.GIFTS_ONLY_TWO_TIMES);
            }
            // 6. 当前用户未兑换过礼品，且满足兑换条件
            BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
            Double totalBalance = bsActivityMapper.sumThanksGiving(userId, term);
            if(totalBalance >= investLimit) {
                BsActivityAward award = bsActivityAwardMapper.selectByPrimaryKey(awardId);
                this.initLuckyDraw(luckyDraw, userId, awardId, Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST,
                        Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, award.getContent(), Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_NO);
                return luckyDraw.getId();
            } else {
                throw new PTMessageException(PTMessageEnum.NON_CONFORMITY);
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());
        }
    }

    @Override
    public void addAddress(Integer userId, Integer luckyDrawId, String mobile, String userName, String address) {
        // 1. 活动是否开始
        String isStart = this.duringActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST);
        if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }

        // 2. 用户是否登录
        checkUserId(userId);

        if(StringUtil.isBlank(userName)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(), "未填写收货人");
        }
        if(StringUtil.isBlank(mobile)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(), "未填写手机号");
        }
        if(!MobileCheckUtil.isMobile(mobile)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(), "手机格式不正确");
        }
        if(StringUtil.isBlank(address)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(), "未填写详细地址");
        }

        int count = bsActivityLuckyDrawMapper.countThanksGiving(userId, luckyDrawId);
        if(count > 0) {
            throw new PTMessageException(PTMessageEnum.ADDRESS_HAVE_WRITE);
        }
        BsActivityLuckyDraw luckyDraw = bsActivityLuckyDrawMapper.selectByPrimaryKey(luckyDrawId);
        if(!luckyDraw.getUserId().equals(userId)) {
            throw new PTMessageException(PTMessageEnum.USER_INFO_ERROR);
        }
        BsUserReceiveAddress receiveAddress = new BsUserReceiveAddress();
        receiveAddress.setUserId(userId);
        receiveAddress.setAddress(address);
        receiveAddress.setConsignee(userName);
        receiveAddress.setLuckyDrawId(luckyDrawId);
        receiveAddress.setTel(mobile);
        receiveAddress.setCreateTime(new Date());
        receiveAddress.setUpdateTime(new Date());
        bsUserReceiveAddressMapper.insertSelective(receiveAddress);
    }

    @Override
    public void generateInvestNumber(Integer userId, Integer authSubId) {
        jsClientDaoSupport.lock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());
        try {
            log.info("2017感恩节活动产生投资号，用户ID：{}，站岗户ID：{}", userId, authSubId);
            String isStart = this.duringActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER);
            if(Constants.ACTIVITY_IS_START.equals(isStart)) {
                BsSubAccount firstBuy = bsSubAccountMapper.selectFirstInvest(userId);
                if(authSubId.equals(firstBuy.getId())) {
                    BsProductExample productExample = new BsProductExample();
                    productExample.createCriteria().andIdEqualTo(firstBuy.getProductId()).andActivityTypeEqualTo(Constants.PRODUCT_ACTIVITY_TYPE_NORMAL).andSerialIdIsNotNull();
                    List<BsProduct> productList = bsProductMapper.selectByExample(productExample);
                    if(CollectionUtils.isEmpty(productList)) {
                        return;
                    }
                    BsProduct product = productList.get(0);
                    String[] showTerminalArray = product.getShowTerminal().split(",");
                    List terminalList = CollectionUtils.arrayToList(showTerminalArray);
                    List<String> showTerminalList = new ArrayList<>();
                    showTerminalList.add(Constants.PRODUCT_SHOW_TERMINAL_PC_178);
                    showTerminalList.add(Constants.PRODUCT_SHOW_TERMINAL_H5_178);
                    showTerminalList.add(Constants.PRODUCT_SHOW_TERMINAL_PC_KQ);
                    showTerminalList.add(Constants.PRODUCT_SHOW_TERMINAL_H5_KQ);
                    showTerminalList.add(Constants.PRODUCT_SHOW_TERMINAL_PC_HN);
                    showTerminalList.add(Constants.PRODUCT_SHOW_TERMINAL_H5_HN);
                    showTerminalList.add(Constants.PRODUCT_SHOW_TERMINAL_PC_RUIAN);
                    showTerminalList.add(Constants.PRODUCT_SHOW_TERMINAL_H5_RUIAN);
                    if(CollectionUtils.containsAny(terminalList, showTerminalList)) {
                        return;
                    }

                    BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
                    example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER);
                    int count = bsActivityLuckyDrawMapper.countByExample(example);
                    String investNumber = String.valueOf(count + 1);
                    BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
                    this.initLuckyDraw(luckyDraw, userId, Constants.AWARD_ID_2017_LUCKY_NUMBER, Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER,
                            Constants.IS_WIN_NO, Constants.IS_CONFIRM_NO, investNumber, Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_NO);
                }
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());
        }
    }

    @Override
    public void luckyNumber() {
        BsActivity activity = bsActivityMapper.selectByPrimaryKey(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER);
        Date now = new Date();
        log.info("产生幸运号日期：{}", DateUtil.format(now));
        Date startTimeAdd1 = DateUtil.addDays(activity.getStartTime(), 1);
        Date endTimeAdd1 = DateUtil.addDays(activity.getEndTime(), 1);
        if((startTimeAdd1.before(now) || DateUtil.isSameDay(now, startTimeAdd1))
                && (endTimeAdd1.after(now) || DateUtil.isSameDay(now, endTimeAdd1))) {
            log.info("开始产生幸运号日期：{}", DateUtil.format(now));
            String yesterday = DateUtil.formatDateTime(DateUtil.addDays(now, -1), "yyyy-MM-dd");
            Date startTime = DateUtil.parse(yesterday + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            Date endTime = DateUtil.parse(yesterday + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER).andCreateTimeBetween(startTime, endTime);
            List<BsActivityLuckyDraw> list = bsActivityLuckyDrawMapper.selectByExample(example);
            if(!CollectionUtils.isEmpty(list)) {
                BsActivityLuckyDrawExample isWinExample = new BsActivityLuckyDrawExample();
                isWinExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER).andCreateTimeBetween(startTime, endTime).andIsWinEqualTo(Constants.IS_WIN_YES);
                List<BsActivityLuckyDraw> isWinList = bsActivityLuckyDrawMapper.selectByExample(isWinExample);
                if(!CollectionUtils.isEmpty(isWinList)) {
                    throw new PTMessageException(PTMessageEnum.HAVE_LUCKY_DRAW);
                }
                int size = list.size();
                int luckyNumber = LuckyDrawUtil.luckyNumber(size);
                log.info("幸运号随机数：{}", luckyNumber);
                BsActivityLuckyDraw luckyDraw = list.get(luckyNumber - 1);
                luckyDraw.setIsWin(Constants.IS_WIN_YES);
                luckyDraw.setUpdateTime(new Date());
                bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);

                log.info("开始产生幸运号日期：{}，幸运号：{}", DateUtil.format(now), luckyDraw.getNote());
            }
        }
    }

    @Override
    public List<ActivityGiftNumberVO> giftNumber(Integer userId) {
        String isStart = this.duringActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST);
        String isLogin = this.isLogin(userId);

        // 毛巾
        ActivityGiftNumberVO maojin = new ActivityGiftNumberVO();
        maojin.setIsStart(isStart);
        maojin.setIsLogin(isLogin);
        BsActivityLuckyDrawExample maojinExamlple = new BsActivityLuckyDrawExample();
        maojinExamlple.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST)
                .andAwardIdEqualTo(Constants.AWARD_ID_2017_INVEST_TERM_1_MAOJIN).andIsWinEqualTo(Constants.IS_WIN_YES);
        int maojinCount = bsActivityLuckyDrawMapper.countByExample(maojinExamlple);
        maojin.setNumber((Constants.TERM_1_GIFT_NUMBER - maojinCount) < 0 ? 0 : Constants.TERM_1_GIFT_NUMBER - maojinCount);
        if(Constants.IS_LOGIN_YES.equals(isLogin)) {
            int count = bsActivityLuckyDrawMapper.countThanksGiving(userId, null);
            maojin.setHaveAddress(count > 0 ? true : false);

            BsActivityLuckyDrawExample myMaojinExamlple = new BsActivityLuckyDrawExample();
            myMaojinExamlple.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST)
                    .andAwardIdEqualTo(Constants.AWARD_ID_2017_INVEST_TERM_1_MAOJIN).andIsWinEqualTo(Constants.IS_WIN_YES).andUserIdEqualTo(userId);
            List<BsActivityLuckyDraw> luckyDrawList = bsActivityLuckyDrawMapper.selectByExample(myMaojinExamlple);
            maojin.setStatus(!CollectionUtils.isEmpty(luckyDrawList) ? Constants.YES : Constants.NO);
            maojin.setLuckyDrawId(!CollectionUtils.isEmpty(luckyDrawList) ? luckyDrawList.get(0).getId() : null);

            // 是否满足投资条件
            if(Constants.NO.equals(maojin.getStatus())) {
                Double totalBalance = bsActivityMapper.sumThanksGiving(userId, 1);
                if (totalBalance < Constants.TERM_1_INVEST_LIMIT) {
                    maojin.setStatus(Constants.GO_INVEST);
                }
            }
        }

        // 养生壶
        ActivityGiftNumberVO yangsheng = new ActivityGiftNumberVO();
        yangsheng.setIsStart(isStart);
        yangsheng.setIsLogin(isLogin);
        BsActivityLuckyDrawExample yangshengExamlple = new BsActivityLuckyDrawExample();
        yangshengExamlple.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST)
                .andAwardIdEqualTo(Constants.AWARD_ID_2017_INVEST_TERM_3_YANGSHENG).andIsWinEqualTo(Constants.IS_WIN_YES);
        int yangshengCount = bsActivityLuckyDrawMapper.countByExample(yangshengExamlple);
        yangsheng.setNumber((Constants.TERM_3_GIFT_NUMBER - yangshengCount) < 0 ? 0 : Constants.TERM_3_GIFT_NUMBER - yangshengCount);
        if(Constants.IS_LOGIN_YES.equals(isLogin)) {
            int count = bsActivityLuckyDrawMapper.countThanksGiving(userId, null);
            yangsheng.setHaveAddress(count > 0 ? true : false);

            BsActivityLuckyDrawExample myYanshengExamlple = new BsActivityLuckyDrawExample();
            myYanshengExamlple.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST)
                    .andAwardIdEqualTo(Constants.AWARD_ID_2017_INVEST_TERM_3_YANGSHENG).andIsWinEqualTo(Constants.IS_WIN_YES).andUserIdEqualTo(userId);
            List<BsActivityLuckyDraw> luckyDrawList = bsActivityLuckyDrawMapper.selectByExample(myYanshengExamlple);
            yangsheng.setStatus(!CollectionUtils.isEmpty(luckyDrawList) ? Constants.YES : Constants.NO);
            yangsheng.setLuckyDrawId(!CollectionUtils.isEmpty(luckyDrawList) ? luckyDrawList.get(0).getId() : null);

            // 是否满足投资条件
            if(Constants.NO.equals(yangsheng.getStatus())) {
                Double totalBalance = bsActivityMapper.sumThanksGiving(userId, 3);
                if (totalBalance < Constants.TERM_3_INVEST_LIMIT) {
                    yangsheng.setStatus(Constants.GO_INVEST);
                }
            }
        }

        // 铁皮石斛礼盒
        ActivityGiftNumberVO tiepi = new ActivityGiftNumberVO();
        tiepi.setIsStart(isStart);
        tiepi.setIsLogin(isLogin);
        BsActivityLuckyDrawExample tiepiExamlple = new BsActivityLuckyDrawExample();
        tiepiExamlple.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST)
                .andAwardIdEqualTo(Constants.AWARD_ID_2017_INVEST_TERM_6_TIEPI).andIsWinEqualTo(Constants.IS_WIN_YES);
        int tiepiCount = bsActivityLuckyDrawMapper.countByExample(tiepiExamlple);
        tiepi.setNumber((Constants.TERM_6_GIFT_NUMBER - tiepiCount) < 0 ? 0 : Constants.TERM_6_GIFT_NUMBER - tiepiCount);
        if(Constants.IS_LOGIN_YES.equals(isLogin)) {
            int count = bsActivityLuckyDrawMapper.countThanksGiving(userId, null);
            tiepi.setHaveAddress(count > 0 ? true : false);

            BsActivityLuckyDrawExample myTiepiExamlple = new BsActivityLuckyDrawExample();
            myTiepiExamlple.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST)
                    .andAwardIdEqualTo(Constants.AWARD_ID_2017_INVEST_TERM_6_TIEPI).andIsWinEqualTo(Constants.IS_WIN_YES).andUserIdEqualTo(userId);
            List<BsActivityLuckyDraw> luckyDrawList = bsActivityLuckyDrawMapper.selectByExample(myTiepiExamlple);
            tiepi.setStatus(!CollectionUtils.isEmpty(luckyDrawList) ? Constants.YES : Constants.NO);
            tiepi.setLuckyDrawId(!CollectionUtils.isEmpty(luckyDrawList) ? luckyDrawList.get(0).getId() : null);

            // 是否满足投资条件
            if(Constants.NO.equals(tiepi.getStatus())) {
                Double totalBalance = bsActivityMapper.sumThanksGiving(userId, 6);
                if (totalBalance < Constants.TERM_6_INVEST_LIMIT) {
                    tiepi.setStatus(Constants.GO_INVEST);
                }
            }
        }

        // 燕窝礼盒
        ActivityGiftNumberVO yanwo = new ActivityGiftNumberVO();
        yanwo.setIsStart(isStart);
        yanwo.setIsLogin(isLogin);
        BsActivityLuckyDrawExample yanwoExamlple = new BsActivityLuckyDrawExample();
        yanwoExamlple.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST)
                .andAwardIdEqualTo(Constants.AWARD_ID_2017_INVEST_TERM_12_YANWO).andIsWinEqualTo(Constants.IS_WIN_YES);
        int yanwoCount = bsActivityLuckyDrawMapper.countByExample(yanwoExamlple);
        yanwo.setNumber((Constants.TERM_12_GIFT_NUMBER - yanwoCount) < 0 ? 0 : Constants.TERM_12_GIFT_NUMBER - yanwoCount);
        if(Constants.IS_LOGIN_YES.equals(isLogin)) {
            int count = bsActivityLuckyDrawMapper.countThanksGiving(userId, null);
            yanwo.setHaveAddress(count > 0 ? true : false);

            BsActivityLuckyDrawExample myYanwoExamlple = new BsActivityLuckyDrawExample();
            myYanwoExamlple.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST)
                    .andAwardIdEqualTo(Constants.AWARD_ID_2017_INVEST_TERM_12_YANWO).andIsWinEqualTo(Constants.IS_WIN_YES).andUserIdEqualTo(userId);
            List<BsActivityLuckyDraw> luckyDrawList = bsActivityLuckyDrawMapper.selectByExample(myYanwoExamlple);
            yanwo.setStatus(!CollectionUtils.isEmpty(luckyDrawList) ? Constants.YES : Constants.NO);
            yanwo.setLuckyDrawId(!CollectionUtils.isEmpty(luckyDrawList) ? luckyDrawList.get(0).getId() : null);

            // 是否满足投资条件
            if(Constants.NO.equals(yanwo.getStatus())) {
                Double totalBalance = bsActivityMapper.sumThanksGiving(userId, 12);
                if (totalBalance < Constants.TERM_12_INVEST_LIMIT) {
                    yanwo.setStatus(Constants.GO_INVEST);
                }
            }
        }

        if(Constants.IS_LOGIN_YES.equals(isLogin)) {
            if(maojin.isHaveAddress() || yangsheng.isHaveAddress() || tiepi.isHaveAddress() || yanwo.isHaveAddress()) {
            } else if(maojin.getLuckyDrawId() == null && yangsheng.getLuckyDrawId() == null && tiepi.getLuckyDrawId() == null && yanwo.getLuckyDrawId() == null) {
                // 此处目的是为了前端页面展示
                maojin.setHaveAddress(true);
                yangsheng.setHaveAddress(true);
                tiepi.setHaveAddress(true);
                yanwo.setHaveAddress(true);
            }
        }
        List<ActivityGiftNumberVO> result = new ArrayList<>();
        result.add(maojin);
        result.add(yangsheng);
        result.add(tiepi);
        result.add(yanwo);

        int exchangeNumber = 0;
        for (ActivityGiftNumberVO vo: result) {
            if(Constants.YES.equals(vo.getStatus())) {
                exchangeNumber ++;
            }
            if(exchangeNumber >= 2) {
                for (ActivityGiftNumberVO giftNumber: result) {
                    if(Constants.NO.equals(giftNumber.getStatus()) || Constants.GO_INVEST.equals(giftNumber.getStatus())) {
                        giftNumber.setStatus(Constants.CAN_NOT_JOIN);
                    }
                }
                break;
            }
        }
        return result;
    }

    @Override
    public ActivityLuckyNumberVO luckyNumber(Integer userId) {
        String isStart = this.duringActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER);
        String isLogin = this.isLogin(userId);
        BsActivity activity = bsActivityMapper.selectByPrimaryKey(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER);

        ActivityLuckyNumberVO result = new ActivityLuckyNumberVO();
        result.setIsStart(isStart);
        result.setIsLogin(isLogin);

        Date startTime = activity.getStartTime();
        Date endTime = activity.getEndTime();
        Date now = new Date();
        Date today = DateUtil.parse(DateUtil.formatYYYYMMDD(now) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date yesterday = DateUtil.addDays(today, -1);

        List<KeyValue> luckyNumberList = new ArrayList<>();
        if (Constants.ACTIVITY_IS_START.equals(isStart)) {
            if (DateUtil.formatYYYYMMDD(startTime).equals(DateUtil.formatYYYYMMDD(now))) {
                // 14号无幸运号
                KeyValue todayLuckyNumber = new KeyValue();
                todayLuckyNumber.setKey(DateUtil.formatYYYYMMDD(today));
                luckyNumberList.add(todayLuckyNumber);
            } else {
                // 15号-23号，存在幸运号，都是前一天的
                Date yesterdayEnd = DateUtil.parse(DateUtil.formatYYYYMMDD(yesterday) + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
                BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
                example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER).andIsWinEqualTo(Constants.IS_WIN_YES)
                        .andCreateTimeBetween(yesterday, yesterdayEnd);
                List<BsActivityLuckyDraw> list = bsActivityLuckyDrawMapper.selectByExample(example);
                KeyValue yesterdayLuckyNumber = new KeyValue();
                yesterdayLuckyNumber.setKey(DateUtil.formatYYYYMMDD(yesterday));
                if(!CollectionUtils.isEmpty(list)) {
                    yesterdayLuckyNumber.setValue(list.get(0).getNote());
                } else {
                    yesterdayLuckyNumber.setValue("未产生");
                }
                luckyNumberList.add(yesterdayLuckyNumber);
                KeyValue todayLuckyNumber = new KeyValue();
                todayLuckyNumber.setKey(DateUtil.formatYYYYMMDD(today));
                luckyNumberList.add(todayLuckyNumber);
            }
        } else if (DateUtil.formatYYYYMMDD(DateUtil.addDays(endTime, 1)).equals(DateUtil.formatYYYYMMDD(now))) {
            // 24号，虽然活动已经结束，但存在幸运号，是23号的
            Date yesterdayEnd = DateUtil.parse(DateUtil.formatYYYYMMDD(yesterday) + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER).andIsWinEqualTo(Constants.IS_WIN_YES)
                    .andCreateTimeBetween(yesterday, yesterdayEnd);
            List<BsActivityLuckyDraw> list = bsActivityLuckyDrawMapper.selectByExample(example);
            KeyValue yesterdayLuckyNumber = new KeyValue();
            yesterdayLuckyNumber.setKey(DateUtil.formatYYYYMMDD(yesterday));
            if(!CollectionUtils.isEmpty(list)) {
                yesterdayLuckyNumber.setValue(list.get(0).getNote());
            } else {
                yesterdayLuckyNumber.setValue("未产生");
            }
            luckyNumberList.add(yesterdayLuckyNumber);
        }

        // 每日幸运号
        result.setLuckyNumber(luckyNumberList);

        if (Constants.IS_LOGIN_YES.equals(isLogin)) {
            List<ActivityLuckyNumber> luckyList = new ArrayList<>();
            // 每日投资幸运号列表
            List<ActivityLuckyNumber> investNumberList = bsActivityLuckyDrawMapper.selectInvestNumber(userId);
            List<ActivityLuckyNumber> luckyNumList = bsActivityLuckyDrawMapper.selectLuckyNumber();
            result.setWaitPublish(Constants.NO);
            for (Date time = startTime; time.compareTo(endTime) < 1; time = DateUtil.addDays(time, 1)) {
                ActivityLuckyNumber activityLuckyNumber = new ActivityLuckyNumber();
                activityLuckyNumber.setInvestDate(time);
                for (ActivityLuckyNumber investNumber : investNumberList) {
                    if (DateUtil.formatYYYYMMDD(time).equals(DateUtil.formatYYYYMMDD(investNumber.getInvestDate()))) {
                        activityLuckyNumber.setInvestNumber(investNumber.getInvestNumber());
                        break;
                    }
                }

                if(DateUtil.formatYYYYMMDD(now).equals(DateUtil.formatYYYYMMDD(time))
                        && StringUtil.isNotBlank(activityLuckyNumber.getInvestNumber())) {
                    // 今天存在投资，证明已经具备获得幸运号的资格，今天无需继续投资了
                    result.setWaitPublish(Constants.YES);
                }

                if(time.compareTo(new Date()) < 0 && StringUtil.isBlank(activityLuckyNumber.getInvestNumber())) {
                    activityLuckyNumber.setInvestNumber("未投资");
                }
                for (ActivityLuckyNumber luckyNumber : luckyNumList) {
                    if (DateUtil.formatYYYYMMDD(time).equals(DateUtil.formatYYYYMMDD(luckyNumber.getInvestDate()))) {
                        activityLuckyNumber.setLuckyNumber(luckyNumber.getLuckyNumber());
                        activityLuckyNumber.setLuckyUser(luckyNumber.getLuckyUser());
                        break;
                    }
                }
                if(StringUtil.isNotBlank(activityLuckyNumber.getInvestNumber())
                        && StringUtil.isBlank(activityLuckyNumber.getLuckyNumber())) {
                    activityLuckyNumber.setLuckyNumber("明日公布");
                    activityLuckyNumber.setLuckyUser("明日公布");
                    if(time.compareTo(today) < 0) {
                        activityLuckyNumber.setLuckyNumber("未产生");
                        activityLuckyNumber.setLuckyUser("未产生");
                    }
                }
                luckyList.add(activityLuckyNumber);
            }
            result.setLuckyNumberList(luckyList);
        }

        return result;
    }

    @Override
    public boolean haveAddress(Integer userId, Integer luckyDrawId) {
        int count = bsActivityLuckyDrawMapper.countThanksGiving(userId, luckyDrawId);
        if(count > 0) {
            return true;
        }
        return false;
    }

    private BsUser checkUserId(Integer userId) {
        if(null == userId) {
            throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
        } else {
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            if(null == bsUser) {
                throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
            }
            return bsUser;
        }
    }

    private String isLogin(Integer userId) {
        String result = null == userId ? Constants.IS_LOGIN_NO : Constants.IS_LOGIN_YES;
        if(null != userId) {
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            if(null == bsUser) {
                result = Constants.IS_LOGIN_NO;
            }
        }
        return result;
    }

    private int getAnswerRedPacket(int subject) {
    	int answerRedPacket = 0;
    	if (Constants.ACTIVITY_ANSWER_0 == subject || Constants.ACTIVITY_ANSWER_1 == subject) {
    		answerRedPacket = Constants.ACTIVITY_ANSWER_RED2;
    	} else if(Constants.ACTIVITY_ANSWER_2 == subject) {
    		answerRedPacket = Constants.ACTIVITY_ANSWER_RED4;
    	} else if(Constants.ACTIVITY_ANSWER_3 == subject) {
    		answerRedPacket = Constants.ACTIVITY_ANSWER_RED6;
    	} else if(Constants.ACTIVITY_ANSWER_4 == subject) {
    		answerRedPacket = Constants.ACTIVITY_ANSWER_RED8;
    	} 
    	return answerRedPacket;
    }
    
    @Override
    public WeChatLuckyTurningInfoVO weChatLuckyTurningInfo(Integer userId) {
    	WeChatLuckyTurningInfoVO result = new WeChatLuckyTurningInfoVO();
        String isLogin = this.isLogin(userId);
        ActivityBaseVO activityBaseVO = this.queryBaseActivity(Constants.ACTIVITY_ID_WECHAT_LUCKY_TURNING, userId);
        result.setIsLogin(isLogin);
        result.setIsStart(activityBaseVO.getIsStart());
        result.setStartTime(activityBaseVO.getStartTime());
        result.setEndTime(activityBaseVO.getEndTime());
        return result;
    }
    
    @Override
    public WeChatLuckyTurningInfoVO weChatLuckyTurningDataInfo(Integer userId) {
    	WeChatLuckyTurningInfoVO result = new WeChatLuckyTurningInfoVO();
        String isLogin = this.isLogin(userId);
        if(Constants.IS_LOGIN_YES.equals(isLogin)) {
            Date now = new Date();
            Date todayBegin = null;
            try {
                todayBegin = DateUtil.getDateBegin(now);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // 抽奖机会
            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_WECHAT_LUCKY_TURNING)
            	.andAwardIdEqualTo(Constants.WECHAT_LUCKY_TURNING_CHANCE_TO_DRAW).andUserIdEqualTo(userId)
            	.andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_NO).andCreateTimeBetween(todayBegin, now);
            int chanceToDraw = bsActivityLuckyDrawMapper.countByExample(example);
            result.setNumberOfChance(chanceToDraw);
            
            BsActivityLuckyDrawExample shareExample = new BsActivityLuckyDrawExample();
            shareExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_WECHAT_LUCKY_TURNING).andUserIdEqualTo(userId)
        		.andAwardIdEqualTo(Constants.WECHAT_LUCKY_TURNING_CHANCE_TO_DRAW).andNoteLike(Constants.WECHAT_CHANCE_TO_DRAW_TYPE_SHARE + "%")
        		.andIsWinEqualTo(Constants.IS_WIN_YES).andCreateTimeBetween(todayBegin, now);
            int sharedCount = bsActivityLuckyDrawMapper.countByExample(shareExample);
            if (sharedCount == 0) {
				result.setShared("no");
			} else {
				result.setShared("yes");
			}
        }
        return result;
    }
    
    @Override
    public WeChatAwardListInfoVO weChatAwardListInfo(Integer userId) {
    	WeChatAwardListInfoVO result = new WeChatAwardListInfoVO();
    	String isLogin = this.isLogin(userId);
    	if(Constants.IS_LOGIN_YES.equals(isLogin)) {
            // 我的奖品列表
            List<BsActivityLuckyDraw> luckyDraws = bsActivityLuckyDrawMapper.selectAwardListByUserId(userId, Constants.ACTIVITY_ID_WECHAT_LUCKY_TURNING);
            List<HashMap<String, Object>> awardList = new ArrayList<>();
            for(BsActivityLuckyDraw luckyDraw : luckyDraws) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("award", luckyDraw.getNote());
                map.put("date", DateUtil.formatDateTime(luckyDraw.getUserDrawTime(), "YYYY-MM-dd"));
                map.put("time", DateUtil.formatDateTime(luckyDraw.getUserDrawTime(), "HH:mm:ss"));
                awardList.add(map);
            }
            result.setAwardList(awardList);
        }
        return result;
    }
    
    @Override
    public WeChatStartTheLotteryVO weChatStartTheLottery(Integer userId) {
    	WeChatStartTheLotteryVO result = new WeChatStartTheLotteryVO();
    	Date now = new Date();
        Date todayBegin = null;
        try {
            todayBegin = DateUtil.getDateBegin(now);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 活动开始与否
        String isStart = this.duringActivity(Constants.ACTIVITY_ID_WECHAT_LUCKY_TURNING);
        if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }
        // 用户登录与否
        checkUserId(userId);
        // 是否符合抽奖资格
        BsActivityLuckyDrawExample drawExample = new BsActivityLuckyDrawExample();
        drawExample.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_WECHAT_LUCKY_TURNING)
                .andAwardIdEqualTo(Constants.WECHAT_LUCKY_TURNING_CHANCE_TO_DRAW).andUserIdEqualTo(userId)
                .andIsWinEqualTo(Constants.IS_WIN_YES).andCreateTimeBetween(todayBegin, now);
        List<BsActivityLuckyDraw> drawList = bsActivityLuckyDrawMapper.selectByExample(drawExample);
        if(CollectionUtils.isEmpty(drawList)) {
            throw new PTMessageException(PTMessageEnum.ACTIVITY_LEFT_TIMES_ZERO);
        }

        // 未被使用的机会
        BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
        example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_WECHAT_LUCKY_TURNING)
                .andAwardIdEqualTo(Constants.WECHAT_LUCKY_TURNING_CHANCE_TO_DRAW).andUserIdEqualTo(userId)
                .andIsWinEqualTo(Constants.IS_WIN_YES).andIsConfirmEqualTo(Constants.IS_CONFIRM_NO).andCreateTimeBetween(todayBegin, now);
        List<BsActivityLuckyDraw> chanceToDrawList = bsActivityLuckyDrawMapper.selectByExample(example);
        int awardId = 0;
        boolean draw = false;
        if(drawList.size() == 1) {
            if(drawList.get(0).getIsConfirm().equals(Constants.IS_CONFIRM_YES)) {
                // 只获得了一次抽奖机会，如果该机会已经用掉，则返回您可进行分享或出借获得抽奖机会的哦~
                throw new PTMessageException(PTMessageEnum.ACTIVITY_LEFT_TIMES_ZERO);
            } else {
                // 只获得了一次抽奖机会，如果该机会没有用掉，则抽奖并返回对应奖品，和下一个类型的type
                draw = true;
                result.setType(Constants.WECHAT_CHANCE_TO_DRAW_TYPE_SHARE.equals(drawList.get(0).getNote())? Constants.WECHAT_CHANCE_TO_DRAW_TYPE_LOGIN : drawList.get(0).getNote());
            }
        }
        if(drawList.size() > 1 && CollectionUtils.isEmpty(chanceToDrawList)) {
            // 今日有抽奖次数，但都被用掉了
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NO_LUCKY_TIMES);
        }
        if(drawList.size() > 1) {
            // 获得了两次抽奖机会，如果该机会已经用掉，则返回对应奖品
            draw = true;
        }
        if(draw) {
            // 今日有抽奖次数
            BsActivityLuckyDraw chanceToDraw = chanceToDrawList.get(0);
            int luckyNumber = LuckyDrawUtil.luckyNumber(1, 10000);
            if(Constants.WECHAT_CHANCE_TO_DRAW_TYPE_LOGIN.equals(chanceToDraw.getNote())) {
                // 登录获奖概率
                if(luckyNumber <= 1000) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_3;
                } else if(luckyNumber <= 3000) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_5;
                } else if(luckyNumber <= 3500) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_10;
                } else if(luckyNumber <= 5000) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_30;
                } else if(luckyNumber <= 5500) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_50;
                } else if(luckyNumber <= 6500) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_80;
                } else if(luckyNumber <= 7500) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_INTEREST_TICKET_1;
                } else if(luckyNumber <= 8000) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_INTEREST_TICKET_2;
                }

            } else {
                // 分享获奖概率
            	if(luckyNumber <= 1000) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_3;
                } else if(luckyNumber <= 2500) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_5;
                } else if(luckyNumber <= 3500) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_10;
                } else if(luckyNumber <= 5000) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_30;
                } else if(luckyNumber <= 6000) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_50;
                } else if(luckyNumber <= 7000) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_RED_PACKET_80;
                } else if(luckyNumber <= 7500) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_INTEREST_TICKET_1;
                } else if(luckyNumber <= 8000) {
                    awardId = Constants.WECHAT_LUCKY_TURNING_INTEREST_TICKET_2;
                }
            }
            if (awardId != 0) {
		        BsActivityAward award = bsActivityAwardMapper.selectByPrimaryKey(awardId);
		        BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDraw();
		        this.initLuckyDraw(luckyDraw, userId, awardId, Constants.ACTIVITY_ID_WECHAT_LUCKY_TURNING,
		                Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, award.getContent(), Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
		    	try {
		    		if (awardId == Constants.WECHAT_LUCKY_TURNING_INTEREST_TICKET_1
		    				|| awardId == Constants.WECHAT_LUCKY_TURNING_INTEREST_TICKET_2) {
		    			//加息券发放
		    			ticketInterestService.weChatAutoTicketSendByName(userId, award.getContent());
					} else {
						// 红包发放
						redPacketService.weChatAutoRedPacketSendByName(userId, award.getContent());
					}
		
		            luckyDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
		            luckyDraw.setConfirmTime(new Date());
		            luckyDraw.setUpdateTime(new Date());
		            bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
		        } catch (Exception e) {
		            log.error("给用户 {} 发放 {} 异常：{}", userId, award.getContent(), e.getMessage());
		            luckyDraw.setIsConfirm(Constants.IS_CONFIRM_NO);
		            luckyDraw.setUpdateTime(new Date());
		            bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(luckyDraw);
		        }
			}
            // 此次机会已经用掉
            chanceToDraw.setIsConfirm(Constants.IS_CONFIRM_YES);
            chanceToDraw.setConfirmTime(new Date());
            chanceToDraw.setUpdateTime(new Date());
            bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(chanceToDraw);
        }
        result.setAward(awardId+"");
        return result;
    }
    
    @Override
    public String wechatChanceToDraw(Integer userId, String type) {
    	String getChance = "no";
        Date now = new Date();
        Date todayBegin = null;
        try {
            todayBegin = DateUtil.getDateBegin(now);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(Constants.ACTIVITY_IS_START.equals(duringActivity(Constants.ACTIVITY_ID_WECHAT_LUCKY_TURNING))) {
            checkUserId(userId);

            BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
            example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_WECHAT_LUCKY_TURNING).andUserIdEqualTo(userId)
            	.andAwardIdEqualTo(Constants.WECHAT_LUCKY_TURNING_CHANCE_TO_DRAW).andNoteLike(type + "%")
            	.andIsWinEqualTo(Constants.IS_WIN_YES).andCreateTimeBetween(todayBegin, now);
            int count = bsActivityLuckyDrawMapper.countByExample(example);
            if(count > 0) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_ONE_LIMIT);
            }

            BsActivityLuckyDraw luckyDraw = new BsActivityLuckyDrawVO();
            this.initLuckyDraw(luckyDraw, userId, Constants.WECHAT_LUCKY_TURNING_CHANCE_TO_DRAW, Constants.ACTIVITY_ID_WECHAT_LUCKY_TURNING,
                    Constants.IS_WIN_YES, Constants.IS_CONFIRM_NO, type, Constants.IS_BACK_DRAW_NO, Constants.IS_USER_DRAW_YES, Constants.IS_AUTO_CONFIRM_YES);
            getChance = "yes";
        }
        return getChance;
    }
    
    @Override
    @RedisCache(serviceName = "salaryIncreasePlanCacheImpl", redisCacheKey = ConstantsForCache.CacheKey.ACTIVITYFACADE_SALARYINCREASEPLAN)
    public ResMsg_Activity_SalaryIncreasePlan salaryIncreasePlan(ReqMsg_Activity_SalaryIncreasePlan req, ResMsg_Activity_SalaryIncreasePlan res) {
    	
    	List<BsActivityLuckyDrawVO> moreThan10000List = new ArrayList<BsActivityLuckyDrawVO>();
    	List<BsActivityLuckyDrawVO> moreThan50000List = new ArrayList<BsActivityLuckyDrawVO>();
    	List<BsActivityLuckyDrawVO> moreThan100000List = new ArrayList<BsActivityLuckyDrawVO>();
    	List<BsActivityLuckyDrawVO> moreThan500000List = new ArrayList<BsActivityLuckyDrawVO>();
    	
    	//加薪计划年化在10000~50000的中奖数和中奖列表
    	String userDrawTime = DateUtil.formatDateTime(new Date(), "yyyy-MM");
    	int moreThan10000count = bsActivityLuckyDrawMapper.countSalaryIncreasePlanList(userDrawTime,
    			Constants.SALARY_INCREASE_PLAN_BONUS_1, Constants.ACTIVITY_ID_SALARY_INCREASE_PLAN);
    	if(moreThan10000count > 0){
    		moreThan10000List = bsActivityLuckyDrawMapper.getSalaryIncreasePlanList(userDrawTime,
	    			Constants.SALARY_INCREASE_PLAN_BONUS_1, Constants.ACTIVITY_ID_SALARY_INCREASE_PLAN);
		}
    	
    	//加薪计划年化在50000~100000的中奖数和中奖列表
    	int moreThan50000count = bsActivityLuckyDrawMapper.countSalaryIncreasePlanList(userDrawTime,
    			Constants.SALARY_INCREASE_PLAN_BONUS_2, Constants.ACTIVITY_ID_SALARY_INCREASE_PLAN);
    	if(moreThan50000count > 0){
    		moreThan50000List = bsActivityLuckyDrawMapper.getSalaryIncreasePlanList(userDrawTime,
	    			Constants.SALARY_INCREASE_PLAN_BONUS_2, Constants.ACTIVITY_ID_SALARY_INCREASE_PLAN);
		}
    	
    	//加薪计划年化在100000~500000的中奖数和中奖列表
    	int moreThan100000count = bsActivityLuckyDrawMapper.countSalaryIncreasePlanList(userDrawTime,
    			Constants.SALARY_INCREASE_PLAN_BONUS_3, Constants.ACTIVITY_ID_SALARY_INCREASE_PLAN);
    	if(moreThan100000count > 0){
    		moreThan100000List = bsActivityLuckyDrawMapper.getSalaryIncreasePlanList(userDrawTime,
	    			Constants.SALARY_INCREASE_PLAN_BONUS_3, Constants.ACTIVITY_ID_SALARY_INCREASE_PLAN);
		}
    	
    	//加薪计划年化在50000以上的中奖数和中奖列表
    	int moreThan500000count = bsActivityLuckyDrawMapper.countSalaryIncreasePlanList(userDrawTime,
    			Constants.SALARY_INCREASE_PLAN_BONUS_4, Constants.ACTIVITY_ID_SALARY_INCREASE_PLAN);
    	if(moreThan500000count > 0){
    		moreThan500000List = bsActivityLuckyDrawMapper.getSalaryIncreasePlanList(userDrawTime,
	    			Constants.SALARY_INCREASE_PLAN_BONUS_4, Constants.ACTIVITY_ID_SALARY_INCREASE_PLAN);
		}
    	
    	res.setMoreThan10000List(com.pinting.business.util.BeanUtils.classToArrayList(moreThan10000List));
    	res.setMoreThan50000List(com.pinting.business.util.BeanUtils.classToArrayList(moreThan50000List));
    	res.setMoreThan100000List(com.pinting.business.util.BeanUtils.classToArrayList(moreThan100000List));
    	res.setMoreThan500000List(com.pinting.business.util.BeanUtils.classToArrayList(moreThan500000List));
    	
    	res.setMoreThan10000Quota(30-moreThan10000count);
    	res.setMoreThan50000Quota(15-moreThan50000count);
    	res.setMoreThan100000Quota(10-moreThan100000count);
    	res.setMoreThan500000Quota(5-moreThan500000count);
    	
    	return res;
    }
    
    @Override
    @ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.ACTIVITYFACADE_SALARYINCREASEPLAN})
    public int insertSelective(BsActivityLuckyDraw record) {
    	return bsActivityLuckyDrawMapper.insertSelective(record);
    }
}
