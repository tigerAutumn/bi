package com.pinting.business.facade.site;

import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.facade.site.enums.ActivityEnum;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsActivity2017anniversaryUserBenison;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;

import com.pinting.core.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2017/2/4
 * Description: 活动
 */
@Component("Activity")
public class ActivityFacade {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SysConfigService sysConfigService;

    // ==================================================== 2017年活动公共方法开始 ======================================================
    /**
     * 2017元宵活动-是否在活动期间
     * @param req
     * @param res
     */
    public void duringActivity(ReqMsg_Activity_DuringActivity req, ResMsg_Activity_DuringActivity res) {
        if("shake".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(ActivityEnum.LANTERN_FESTIVAL_2017_SHAKE.getId()));
        } else if("buy".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(ActivityEnum.LANTERN_FESTIVAL_2017_BUY.getId()));
        } else if("girl".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(ActivityEnum.ACTIVITY_FOR_GIRL_2017.getId()));
        } else if("anniversary_first".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FIRST, 1, null));
        } else if("anniversary_second".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_SECOND, 1, null));
        } else if("anniversary_third".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_THIRD, 1, null));
        } else if("anniversary_fourth".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FOURTH, 1, null));
        } else if("anniversary_fifth".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FIFTH, 1, null));
        } else if("anniversary_sixth".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_SIXTH, 1, null));
        } else if("anniversary_first_period".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_FIRST_PERIOD_START_TIME),
                    DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_FIRST_PERIOD_END_TIME), 1, null));
        } else if("anniversary_second_period".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_SECOND_PERIOD_START_TIME),
                    DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_SECOND_PERIOD_END_TIME), 1, null));
        } else if("anniversary_third_period".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_THIRD_PERIOD_START_TIME),
                    DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_THIRD_PERIOD_END_TIME), 1, null));
        } else if("water".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_WATER_SIGN_UP));
        } else if("water_vote".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_WATER_VOTE));
        } else if("2017_618".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_2017_618));
        } else if("scratch_card".equals(req.getActivityType())) {
        	res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_2017_SCRATCH_CARD));
        } else if("2017_answer".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_2017_ANSWER));
        } else if("2017_team".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_2017_TEAM));
        } else if("2017_light".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_2017_LIGHT));
        } else if("youfu".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_2017_YOUFU_SHARE));
        } else if("2017_christmas".equals(req.getActivityType())) {
            res.setIsDuringActivity(activityService.duringActivity(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE));
        } else {
            res.setIsDuringActivity(activityService.duringActivity(Integer.parseInt(req.getActivityType())));
        }
    }

    /**
     * 查询活动基本信息（开始结束时间、是否开始）
     * @param req
     * @param res
     */
    public void baseData(ReqMsg_Activity_BaseData req, ResMsg_Activity_BaseData res) {
        if("2017_618".equals(req.getActivityType())) {
            res.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_2017_618));
            BsActivityVO base = activityService.queryActivity(Constants.ACTIVITY_ID_2017_618);
            res.setStartTime(base.getStartTime());
            res.setEndTime(base.getEndTime());
        } else if("2017_answer".equals(req.getActivityType())) {
            res.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_2017_ANSWER));
            BsActivityVO base = activityService.queryActivity(Constants.ACTIVITY_ID_2017_ANSWER);
            res.setStartTime(base.getStartTime());
            res.setEndTime(base.getEndTime());
        } else if("2017_team".equals(req.getActivityType())) {
            res.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_2017_TEAM));
            BsActivityVO base = activityService.queryActivity(Constants.ACTIVITY_ID_2017_TEAM);
            res.setStartTime(base.getStartTime());
            res.setEndTime(base.getEndTime());
        } else if("2017_light".equals(req.getActivityType())) {
            res.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_2017_LIGHT));
            BsActivityVO base = activityService.queryActivity(Constants.ACTIVITY_ID_2017_LIGHT);
            res.setStartTime(base.getStartTime());
            res.setEndTime(base.getEndTime());
        } else if("youfu".equals(req.getActivityType())) {
            res.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_2017_YOUFU_SHARE));
            BsActivityVO base = activityService.queryActivity(Constants.ACTIVITY_ID_2017_YOUFU_SHARE);
            res.setStartTime(base.getStartTime());
            res.setEndTime(base.getEndTime());
        } else if("thanksgiving1".equals(req.getActivityType())) {
            res.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_SHARE));
            BsActivityVO base = activityService.queryActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_SHARE);
            res.setStartTime(base.getStartTime());
            res.setEndTime(base.getEndTime());
        } else if("thanksgiving2".equals(req.getActivityType())) {
            res.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST));
            BsActivityVO base = activityService.queryActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST);
            res.setStartTime(base.getStartTime());
            res.setEndTime(base.getEndTime());
        } else if("thanksgiving3".equals(req.getActivityType())) {
            res.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER));
            BsActivityVO base = activityService.queryActivity(Constants.ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER);
            res.setStartTime(base.getStartTime());
            res.setEndTime(base.getEndTime());
        } else if("2017_christmas".equals(req.getActivityType())) {
            res.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE));
            BsActivityVO base = activityService.queryActivity(Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE);
            res.setStartTime(base.getStartTime());
            res.setEndTime(base.getEndTime());
        } else if("2018_recommend".equals(req.getActivityType())) {
            res.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_2017_RECOMMEND));
            BsActivityVO base = activityService.queryActivity(Constants.ACTIVITY_ID_2017_RECOMMEND);
            res.setStartTime(base.getStartTime());
            res.setEndTime(base.getEndTime());
            BsSysConfig zan_rate = sysConfigService.findConfigByKey(Constants.BONUS_RATE_4_SELF_INSTALLMENT_NEW);
            BsSysConfig one_month = sysConfigService.findConfigByKey(Constants.BONUS_RATE_4_SELF_1MONTH_NEW);
            BsSysConfig three_month = sysConfigService.findConfigByKey(Constants.BONUS_RATE_4_SELF_3MONTH_NEW);
            BsSysConfig six_month = sysConfigService.findConfigByKey(Constants.BONUS_RATE_4_SELF_6MONTH_NEW);
            BsSysConfig one_year = sysConfigService.findConfigByKey(Constants.BONUS_RATE_4_SELF_1YEAR_NEW);
            HashMap<String, Object> extend = new HashMap<>();
            extend.put("zan_rate", zan_rate.getConfValue());
            extend.put("one_month", one_month.getConfValue());
            extend.put("three_month", three_month.getConfValue());
            extend.put("six_month", six_month.getConfValue());
            extend.put("one_year", one_year.getConfValue());
            res.setExtendInfo(extend);
        } else {
            res.setIsStart(activityService.duringActivity(Integer.parseInt(req.getActivityType())));
            BsActivityVO base = activityService.queryActivity(Integer.parseInt(req.getActivityType()));
            res.setStartTime(base.getStartTime());
            res.setEndTime(base.getEndTime());
        }
    }
    // ==================================================== 2017年活动公共方法结束 ======================================================

    // ==================================================== 2018年年会抽奖开始 ======================================================
    public void yearEndQueryList(ReqMsg_Activity_YearEndQueryList req, ResMsg_Activity_YearEndQueryList res) {
        ChristmasEveVO result = activityService.yearEndLuckyUserList(req.getType());
        res.setEndTime(result.getEndTime());
        res.setIsStart(result.getIsStart());
        res.setStartTime(result.getStartTime());
        res.setDrawnTimes(result.getMap());
        List<ChristmasEveResultVO> list = result.getList();
        List<HashMap<String, Object>> data = new ArrayList<>();
        for (ChristmasEveResultVO vo: list) {
            HashMap<String, Object> map = BeanUtils.transBeanMap(vo);
            data.add(map);
        }

        List<HashMap<String, Object>> drawnData = new ArrayList<>();
        List<ChristmasEveResultVO> drawnList = result.getDrawnList();
        for (ChristmasEveResultVO vo: drawnList) {
            HashMap<String, Object> map = BeanUtils.transBeanMap(vo);
            drawnData.add(map);
        }
        res.setList(data);
        res.setDrawnList(drawnData);
    }
    public void yearEndLuckyDraw(ReqMsg_Activity_YearEndLuckyDraw req, ResMsg_Activity_YearEndLuckyDraw res) {
        ChristmasEveVO result = activityService.yearEndLuckyDraw(req.getType());
        List<HashMap<String, Object>> data = new ArrayList<>();
        for (ChristmasEveResultVO vo: result.getDrawnList()) {
            HashMap<String, Object> map = BeanUtils.transBeanMap(vo);
            data.add(map);
        }
        res.setList(data);
        res.setLeftTimes(result.getLeftTimes());
    }
    public void yearEndMakeupDraw(ReqMsg_Activity_YearEndMakeupDraw req, ResMsg_Activity_YearEndMakeupDraw res) {
        ChristmasEveResultVO result = activityService.yearEndMakeupDraw(req.getType());
        res.setType(result.getType());
        res.setAmount(result.getAmount());
        res.setUserId(result.getUserId());
        res.setMobile(result.getMobile());
        res.setLeftTimes(result.getLeftTimes());
    }
    // ==================================================== 2018年年会抽奖结束 ======================================================


    // ==================================================== 2017年平安夜活动开始 ======================================================
    public void chrismasQueryList(ReqMsg_Activity_ChrismasQueryList req, ResMsg_Activity_ChrismasQueryList res) {
        ChristmasEveVO result = activityService.christmasEveLuckyUserList(req.getType());
        res.setEndTime(result.getEndTime());
        res.setIsStart(result.getIsStart());
        res.setStartTime(result.getStartTime());
        res.setDrawnTimes(result.getMap());
        List<ChristmasEveResultVO> list = result.getList();
        List<HashMap<String, Object>> data = new ArrayList<>();
        for (ChristmasEveResultVO vo: list) {
            HashMap<String, Object> map = BeanUtils.transBeanMap(vo);
            data.add(map);
        }

        List<HashMap<String, Object>> drawnData = new ArrayList<>();
        List<ChristmasEveResultVO> drawnList = result.getDrawnList();
        for (ChristmasEveResultVO vo: drawnList) {
            HashMap<String, Object> map = BeanUtils.transBeanMap(vo);
            drawnData.add(map);
        }
        res.setList(data);
        res.setDrawnList(drawnData);
    }
    public void chrismasEveLuckyDraw(ReqMsg_Activity_ChrismasEveLuckyDraw req, ResMsg_Activity_ChrismasEveLuckyDraw res) {
        ChristmasEveVO result = activityService.christmasEveLuckyDraw(req.getType());
        List<HashMap<String, Object>> data = new ArrayList<>();
        for (ChristmasEveResultVO vo: result.getDrawnList()) {
            HashMap<String, Object> map = BeanUtils.transBeanMap(vo);
            data.add(map);
        }
        res.setList(data);
        res.setLeftTimes(result.getLeftTimes());
    }
    public void chrismasEveMakeupDraw(ReqMsg_Activity_ChrismasEveMakeupDraw req, ResMsg_Activity_ChrismasEveMakeupDraw res) {
        ChristmasEveResultVO result = activityService.christmasEveMakeupDraw(req.getType());
        res.setType(result.getType());
        res.setAmount(result.getAmount());
        res.setUserId(result.getUserId());
        res.setMobile(result.getMobile());
        res.setLeftTimes(result.getLeftTimes());
    }
    // ==================================================== 2017年平安夜活动结束 ======================================================


    // ==================================================== 2017年318周年庆活动开始 ======================================================

    /**
     * 0. 周年庆活动入口页面
     */
    public void anniversaryEntryPageInfo(ReqMsg_Activity_AnniversaryEntryPageInfo req, ResMsg_Activity_AnniversaryEntryPageInfo res) {
        res.setStatusFirst(activityService.duringActivity(DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_FIRST_PERIOD_START_TIME),
                DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_FIRST_PERIOD_END_TIME), 1, null));
        res.setStatusSecond(activityService.duringActivity(DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_SECOND_PERIOD_START_TIME),
                DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_SECOND_PERIOD_END_TIME), 1, null));
        res.setStatusThird(activityService.duringActivity(DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_THIRD_PERIOD_START_TIME),
                DateUtil.parseDateTime(Constants.ACTIVITY_ANNIVERSARY_THIRD_PERIOD_END_TIME), 1, null));
    }

    /**
     * 1. 一重礼，二重礼主页面信息
     */
     public void anniversaryFirstHomePageInfo(ReqMsg_Activity_AnniversaryFirstHomePageInfo req, ResMsg_Activity_AnniversaryFirstHomePageInfo res) {
         AnniversaryFirstHomePageInfoVO result = activityService.anniversaryFirstHomePageInfo(req.getUserId());
         org.springframework.beans.BeanUtils.copyProperties(result, res);
     }

    /**
     * 2. 二重礼分享页面信息
     */
    public void anniversarySharePageInfo(ReqMsg_Activity_AnniversarySharePageInfo req, ResMsg_Activity_AnniversarySharePageInfo res) {
        AnniversarySharePageInfoVO result = activityService.anniversarySharePageInfo(req.getShareUserId(), req.getOpenId(), req.getPageNum(), req.getNumPerPage());
        org.springframework.beans.BeanUtils.copyProperties(result, res);
    }

    /**
     * 3. 二重礼助力服务
     */
    public void anniversaryHelpFriend(ReqMsg_Activity_AnniversaryHelpFriend req, ResMsg_Activity_AnniversaryHelpFriend res) {
        AnniversaryHelpFriendVO wxFriend = new AnniversaryHelpFriendVO();
        org.springframework.beans.BeanUtils.copyProperties(req, wxFriend);
        activityService.anniversaryHelpFriend(wxFriend);
    }

    /**
     * 4. 二重礼3月18日解锁元宝服务
     */
    public void anniversaryUnlockGoldIngot(ReqMsg_Activity_AnniversaryUnlockGoldIngot req, ResMsg_Activity_AnniversaryUnlockGoldIngot res) {
        activityService.anniversaryUnlockGoldIngot(req.getUserId());
    }

    /**
     * 5. 三重礼，四重礼主页面信息
     */
    public void anniversarySecondHomePageInfo(ReqMsg_Activity_AnniversarySecondHomePageInfo req, ResMsg_Activity_AnniversarySecondHomePageInfo res) {
        AnniversarySecondHomePageInfoVO result = activityService.anniversarySecondHomePageInfo(req.getUserId());
        org.springframework.beans.BeanUtils.copyProperties(result, res);
        res.setBenisonList(BeanUtils.classToArrayList(result.getBenisonList()));
        res.setInvestUserList(BeanUtils.classToArrayList(result.getInvestUserList()));
    }

    /**
     * 6. 三重礼祝福语存库服务
     */
    public void anniversaryBenison(ReqMsg_Activity_AnniversaryBenison req, ResMsg_Activity_AnniversaryBenison res) {
        BsActivity2017anniversaryUserBenison benison = new BsActivity2017anniversaryUserBenison();
        org.springframework.beans.BeanUtils.copyProperties(req, benison);
        ActivityBaseVO vo = activityService.anniversaryBenison(benison);
        org.springframework.beans.BeanUtils.copyProperties(vo, res);
    }

    /**
     * 8. 五重礼、六重礼页面信息
     */
    public void anniversaryThirdHomePageInfo(ReqMsg_Activity_AnniversaryThirdHomePageInfo req, ResMsg_Activity_AnniversaryThirdHomePageInfo res) {
        AnniversaryThirdHomePageInfoVO result = activityService.anniversaryThirdHomePageInfo(req.getUserId());
        org.springframework.beans.BeanUtils.copyProperties(result, res);
        res.setTodayInvestUserList(BeanUtils.classToArrayList(result.getTodayInvestUserList()));
    }

    /**
     * 8.1 五重礼-用户获奖列表
     */
    public void anniversaryThirdUserInvestList(ReqMsg_Activity_AnniversaryThirdUserInvestList req, ResMsg_Activity_AnniversaryThirdUserInvestList res) {
        res.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_ANNIVERSARY_FIFTH, 1, null));
        res.setIsLogin(req.getUserId() == null ? Constants.IS_LOGIN_NO : Constants.IS_LOGIN_YES);
        List<HashMap<String, Object>> userAwardList = new ArrayList<>();
        List<AnniversaryAwardInfoVO> list = activityService.userAwardList(req.getUserId());
        for (AnniversaryAwardInfoVO vo : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("time", vo.getTimeStr());
            map.put("amount", vo.getAmount());
            map.put("award", vo.getAward());
            map.put("userName", vo.getUserName());
            map.put("anniAmountThatDay", vo.getAnniAmountThatDay());
            map.put("todayAwardAmount", vo.getTodayAwardAmount());
            map.put("rank", vo.getRank());
            userAwardList.add(map);
        }
        res.setUserAwardList(userAwardList);
    }

    /**
     * 8.2 五重礼-获奖名单
     */
    public void anniversaryThirdWinnerList(ReqMsg_Activity_AnniversaryThirdWinnerList req, ResMsg_Activity_AnniversaryThirdWinnerList res) {
        Map<String, List<HashMap<String, Object>>> winnerList = activityService.winnerList();
        res.setWinnerList(winnerList);
    }

    /**
     * 9. 六重礼抽奖服务
     */
    public void anniversaryLuckyDraw(ReqMsg_Activity_AnniversaryLuckyDraw req, ResMsg_Activity_AnniversaryLuckyDraw res) {
        res.setAwardId(activityService.anniversaryLuckyDraw(req.getUserId()));
    }

    // ==================================================== 2017年318周年庆活动结束 ======================================================

    // ==================================================== 2017年女神节活动开始 =========================================================
    /**
     * 2017年女神节活动页面信息
     * @return
     */
    public void activityForGirl2017PageInfo(ReqMsg_Activity_ActivityForGirl2017PageInfo req, ResMsg_Activity_ActivityForGirl2017PageInfo res) {
        ActivityForGirl2017VO result = activityService.activityForGirl2017PageInfo();
        org.springframework.beans.BeanUtils.copyProperties(result, res);
    }

    /**
     * 2017年女神节活动校验领取资格
     * @return
     */
    public void checkForGirl2017Receive(ReqMsg_Activity_CheckForGirl2017Receive req, ResMsg_Activity_CheckForGirl2017Receive res) {
        ActivityForGirl2017ReceiveVO result = activityService.checkForGirl2017Receive(req.getUserId(), req.getAgentId());
        org.springframework.beans.BeanUtils.copyProperties(result, res);
    }

    /**
     * 2017年女神节活动领取奖品
     * @return
     */
    public void activityForGirl2017Receive(ReqMsg_Activity_ActivityForGirl2017Receive req, ResMsg_Activity_ActivityForGirl2017Receive res) {
        ActivityForGirl2017ReceiveVO result = activityService.activityForGirl2017Receive(req.getUserId(), req.getAgentId(), req.getAwardType(), req.getConsignee(), req.getTel(), req.getAddress(), req.getNote());
        org.springframework.beans.BeanUtils.copyProperties(result, res);
    }


    // ==================================================== 2017年女神节活动结束 =========================================================

    // ==================================================== 2017年元宵节活动开始 =========================================================

    /**
     * 2017元宵活动-活动首页信息
     * @param req
     * @param res
     */
    public void luckyDrawPageInfo(ReqMsg_Activity_LuckyDrawPageInfo req, ResMsg_Activity_LuckyDrawPageInfo res) {
        LanternFestival2017DrawIndexVO result = activityService.luckyDrawPageInfo();
        org.springframework.beans.BeanUtils.copyProperties(result, res);
    }

    /**
     * 2017元宵活动-活动中奖名单
     * @param req
     * @param res
     */
    public void luckyDrawList(ReqMsg_Activity_LuckyDrawList req, ResMsg_Activity_LuckyDrawList res) {
        LanternFestival2017LanternResultVO vo = activityService.getList(req.getPageNum(), req.getNumPerPage());
        res.setTotalRows(vo.getTotalRows());
        List<HashMap<String, Object>> list = new ArrayList<>();
        for (LanternFestival2017LanternDetailVO detail : vo.getList()) {
            HashMap<String, Object> map = BeanUtils.transBeanMap(detail);
            map.put("createTime", DateUtil.format(detail.getCreateTime()));
            list.add(map);
        }
        res.setList(list);
        res.setTotalPages(vo.getTotalPages());
    }

    /**
     * 2017元宵活动-抽奖
     * @param req
     * @param res
     */
    public void luckyDrawResult(ReqMsg_Activity_LuckyDrawResult req, ResMsg_Activity_LuckyDrawResult res) {
        LanternFestival2017DrawResultVO vo = activityService.lanternFestival2017LuckyDraw(req.getUserId(), ActivityEnum.LANTERN_FESTIVAL_2017_SHAKE.getId());
        res.setAmount(vo.getAmount());
        res.setAwardType(vo.getAwardType());
        res.setCode(vo.getCode());
        res.setMessage(vo.getMessage());
        res.setIsStart(vo.getIsStart());
        res.setIsWin(vo.getIsWin());
    }

    // ==================================================== 2017年元宵节活动结束 =========================================================
    
    
    // ================================= 2017年踏春活动开始 ==================================

    /**
     * 2017踏春活动首页不需登录查询的信息
     * @param req
     * @param res
     */
    public void springIndex(ReqMsg_Activity_SpringIndex req, ResMsg_Activity_SpringIndex res){
    	//查询推荐的标的2个
    	List<ProductDetailVO> productList = productService.suggestProductList(req.getTerminal(), Constants.ACTIVITY_SPRING_PRODUCT_NAME);
    	res.setProductList(CollectionUtils.isNotEmpty(productList) ?
    			 BeanUtils.classToArrayList(productList) : null);
    	
    	//投资排行榜列表
    	List<PlayerKillingVO> investRankingList = activityService.springInvestRankingList();
    	res.setInvestRankingList(CollectionUtils.isNotEmpty(investRankingList) ?
    			BeanUtils.classToArrayList(investRankingList) : null);
    	
    }
    
    /**
     * 2017踏春活动--用户邀请列表
     * @param req
     * @param res
     */
    public void springUserInvitedList(ReqMsg_Activity_SpringUserInvitedList req, ResMsg_Activity_SpringUserInvitedList res){
    	List<PlayerKillingVO> springUserInvitedList = activityService.springUserInvitedList(req.getUserId());
    	res.setInvitedList(CollectionUtils.isNotEmpty(springUserInvitedList) ?
    			BeanUtils.classToArrayList(springUserInvitedList) : null);
    }
    
    /**
     * 2017踏春活动 用户登录后可查数据
     * @param req
     * @param res
     */
    public void springUsersVO(ReqMsg_Activity_SpringUsersVO req, ResMsg_Activity_SpringUsersVO res){
    	//2017踏春活动--用户邀请列表
    	List<PlayerKillingVO> springUserInvitedList = activityService.springUserInvitedList(req.getUserId());
    	res.setUserInvitedList(CollectionUtils.isNotEmpty(springUserInvitedList) ?
    			BeanUtils.classToArrayList(springUserInvitedList) : null);
    	//用户活动期间投资额
    	Double amount = activityService.springUserInvestAmount(req.getUserId());
    	res.setUserInvestAmount(amount);
    	//用户在排行榜中名次
    	//投资排行榜列表
    	List<PlayerKillingVO> investRankingList = activityService.springInvestRankingList();
    	PlayerKillingVO min = investRankingList.get(investRankingList.size()-1);
    	if(amount >= min.getBuyAmount()){
    		for (PlayerKillingVO playerKillingVO : investRankingList) {
				if(playerKillingVO.getUserId().equals(req.getUserId())){
					res.setRankingNo(playerKillingVO.getRowno());
				}
			}
    	}
    	
    }



    // ==================================================== 2017年存管抱团活动开始 =========================================================

    /**
     * 存管后答题抱团活动页面信息
     * @param req
     * @param res
     */
    public void depActivityInfo(ReqMsg_Activity_DepActivityInfo req, ResMsg_Activity_DepActivityInfo res) {
        DepActivityInfoVO info = activityService.depActivityInfo(req.getUserId());
        res.setIsLogin(info.getIsLogin());
        res.setAnswer(BeanUtils.transBeanMap(info.getAnswer()));
        res.setTeamList(BeanUtils.classToArrayList(info.getTeamList()));
        res.setLight(BeanUtils.transBeanMap(info.getLight()));
    }

    /**
     * 抱团服务
     * @param req
     * @param res
     */
    public void joinTeam(ReqMsg_Activity_JoinTeam req, ResMsg_Activity_JoinTeam res) {
        activityService.joinTeam(req.getUserId(), req.getTeamId(), req.getSerial());
    }

    /**
     * 答题服务
     * @param req
     * @param res
     */
    public void answer(ReqMsg_Activity_Answer req, ResMsg_Activity_Answer res) {
        activityService.depAnswer(req.getUserId(), req.getScore());
    }
    // ==================================================== 2017年存管抱团活动结束 =========================================================

    // ==================================================== 2017年微信点亮存管图标瓜分百万红包开始 ======================================================

    /**
     * 微信点亮存管图标瓜分百万红包首页信息
     * @param req
     * @param res
     */
    public void activityForLightUp2017PageInfo(ReqMsg_Activity_ActivityForLightUp2017PageInfo req, ResMsg_Activity_ActivityForLightUp2017PageInfo res) {
        int userId = 0;
        if(req.getUserId() == 0) { //用户未登陆
            userId = 0;
        }else { //用户已登陆
            userId = req.getUserId();
        }
        ActivityForLightUp2017VO result = activityService.activityForLightUp2017VOPageInfo(userId);
        org.springframework.beans.BeanUtils.copyProperties(result, res);
    }

    /**
     * 点亮操作
     */
    public void activityLightUpOperate(ReqMsg_Activity_ActivityLightUpOperate req, ResMsg_Activity_ActivityLightUpOperate res) {
        int row = activityService.addLightUpDepLogo(req.getUserId());
        if(row > 0) { //点亮成功
            res.setIsLightUp(Constants.ACTIVITY_IS_LIGHT_UP);
        }else { //点亮失败
            res.setIsLightUp(Constants.ACTIVITY_IS_NOT_LIGHT_UP);
        }
        //统计点亮存管图标的用户数量
        int num = activityService.queryCountLightUpDepLogo();
        res.setNum(num);
    }

    // ==================================================== 2017年微信点亮存管图标瓜分百万红包结束 ======================================================

    // ==================================================== 2017 友富同享邀请活动 开始======================================================
    public void youFuRankInfo(ReqMsg_Activity_YouFuRankInfo req, ResMsg_Activity_YouFuRankInfo res) {
        List<ActivityYouFuRankInfoVO> result = activityService.youFuRankInfo();
        res.setRankList(BeanUtils.classToArrayList(result));
    }

    public void youFuSelfInfo(ReqMsg_Activity_YouFuSelfInfo req, ResMsg_Activity_YouFuSelfInfo res) {
        ActivityYouFuSelfInfoVO result = activityService.youFuSelfInfo(req.getUserId());
        org.springframework.beans.BeanUtils.copyProperties(result, res);
    }
    // ==================================================== 2017 友富同享邀请活动 结束======================================================

    // ==================================================== 2017 感恩节活动 结束======================================================
    public void share(ReqMsg_Activity_Share req, ResMsg_Activity_Share res) {
        activityService.share(req.getUserId());
    }

    public void sharePageInfo(ReqMsg_Activity_SharePageInfo req, ResMsg_Activity_SharePageInfo res) {
        res.setIsJoined(activityService.sharePageInfo(req.getUserId()).getIsJoined());
    }

    public void addAddress(ReqMsg_Activity_AddAddress req, ResMsg_Activity_AddAddress res) {
        activityService.addAddress(req.getUserId(), req.getLuckyDrawId(), req.getMobile(), req.getUserName(), req.getAddress());
    }

    public void exchangeGift(ReqMsg_Activity_ExchangeGift req, ResMsg_Activity_ExchangeGift res) {
        res.setLuckyDrawId(activityService.investExchange(req.getUserId(), req.getTerm()));
        res.setHaveAddress(activityService.haveAddress(req.getUserId(), res.getLuckyDrawId()));
    }

    public void giftNumber(ReqMsg_Activity_GiftNumber req, ResMsg_Activity_GiftNumber res) {
        List<ActivityGiftNumberVO> result = activityService.giftNumber(req.getUserId());
        res.setGiftNumber(BeanUtils.classToArrayList(result));
    }

    public void luckyNumber(ReqMsg_Activity_LuckyNumber req, ResMsg_Activity_LuckyNumber res) {
        ActivityLuckyNumberVO result = activityService.luckyNumber(req.getUserId());
        res.setLuckyNumber(BeanUtils.classToArrayList(result.getLuckyNumber()));
        res.setLuckyNumberList(BeanUtils.classToArrayList(result.getLuckyNumberList()));
        res.setIsStart(result.getIsStart());
        res.setIsLogin(result.getIsLogin());
        res.setWaitPublish(result.getWaitPublish());
    }

    // ==================================================== 2017 感恩节活动 结束======================================================




    // ==================================================== 2018 感恩节活动 开始======================================================
    public void newYear2018First(ReqMsg_Activity_NewYear2018First req, ResMsg_Activity_NewYear2018First res) {
        NewYear2018FirstVO result = activityService.newYear2018FirstInfo(req.getUserId());
        org.springframework.beans.BeanUtils.copyProperties(result, res);
    }

    public void newYear2018Second(ReqMsg_Activity_NewYear2018Second req, ResMsg_Activity_NewYear2018Second res) {
        NewYear2018SecondVO result = activityService.newYear2018SecondInfo(req.getUserId());
        List<ActivityBaseVO> activityBaseVOList = result.getActivityBaseVOList();
        ArrayList<HashMap<String, Object>> list =  BeanUtils.classToArrayList(activityBaseVOList);
        org.springframework.beans.BeanUtils.copyProperties(result, res);
        res.setResult(list);
    }

    public void newYear2018Third(ReqMsg_Activity_NewYear2018Third req, ResMsg_Activity_NewYear2018Third res) {
        NewYear2018ThirdVO result = activityService.newYear2018ThirdInfo(req.getUserId());
        org.springframework.beans.BeanUtils.copyProperties(result, res);
    }

    public void newYear2018GetRedPacket(ReqMsg_Activity_NewYear2018GetRedPacket req, ResMsg_Activity_NewYear2018GetRedPacket res) {
        if (StringUtil.isNotBlank(req.getTime())) {
            activityService.newYear2018RobRedPacket(req.getUserId(), req.getTime());
        } else {
            activityService.newYear2018GetRedPacket(req.getUserId());
        }
    }
    // ==================================================== 2018 感恩节活动 结束======================================================

    // ==================================================== 微信小程序财运大转盘 开始 =======================================================
    public void weChatLuckyTurningInfo(ReqMsg_Activity_WeChatLuckyTurningInfo req, ResMsg_Activity_WeChatLuckyTurningInfo res) {
    	try {
    		activityService.wechatChanceToDraw(req.getUserId(), Constants.WECHAT_CHANCE_TO_DRAW_TYPE_LOGIN);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	WeChatLuckyTurningInfoVO result = activityService.weChatLuckyTurningInfo(req.getUserId());
        res.setEndTime(result.getEndTime());
        res.setIsStart(result.getIsStart());
        res.setStartTime(result.getStartTime());
        res.setIsLogin(result.getIsLogin());
    }
    
    public void weChatLuckyTurningDataInfo(ReqMsg_Activity_WeChatLuckyTurningDataInfo req, ResMsg_Activity_WeChatLuckyTurningDataInfo res) {
    	WeChatLuckyTurningInfoVO result = activityService.weChatLuckyTurningDataInfo(req.getUserId());
        res.setNumberOfChance(result.getNumberOfChance());
        res.setShared(result.getShared());
    }
    
    public void weChatAwardListInfo(ReqMsg_Activity_WeChatAwardListInfo req, ResMsg_Activity_WeChatAwardListInfo res) {
    	WeChatAwardListInfoVO result = activityService.weChatAwardListInfo(req.getUserId());
    	res.setAwardList(result.getAwardList());
    }

    public void weChatStartTheLottery(ReqMsg_Activity_WeChatStartTheLottery req, ResMsg_Activity_WeChatStartTheLottery res) {
    	WeChatStartTheLotteryVO result = activityService.weChatStartTheLottery(req.getUserId());
    	res.setAward(result.getAward());
    }

    public void wechatShareChanceToDraw(ReqMsg_Activity_WechatShareChanceToDraw req, ResMsg_Activity_WechatShareChanceToDraw res) {
    	String getChance = activityService.wechatChanceToDraw(req.getUserId(), Constants.WECHAT_CHANCE_TO_DRAW_TYPE_SHARE);
    	res.setGetChance(getChance);
    }
    
    // ==================================================== 加薪计划活动查询中奖信息 开始 =======================================================
    public ResMsg_Activity_SalaryIncreasePlan salaryIncreasePlan(ReqMsg_Activity_SalaryIncreasePlan req, ResMsg_Activity_SalaryIncreasePlan res) {
    	return activityService.salaryIncreasePlan(req, res);
    }
    // ==================================================== 加薪计划活动查询中奖信息 开始 =======================================================
}
