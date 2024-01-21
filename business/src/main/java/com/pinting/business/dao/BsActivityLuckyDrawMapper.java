package com.pinting.business.dao;

import com.pinting.business.model.BsActivityLuckyDraw;
import com.pinting.business.model.BsActivityLuckyDrawExample;
import com.pinting.business.model.vo.*;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BsActivityLuckyDrawMapper {

	int countByExample(BsActivityLuckyDrawExample example);

    int deleteByExample(BsActivityLuckyDrawExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsActivityLuckyDraw record);

    int insertSelective(BsActivityLuckyDraw record);

    List<BsActivityLuckyDraw> selectByExample(BsActivityLuckyDrawExample example);

    BsActivityLuckyDraw selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsActivityLuckyDraw record, @Param("example") BsActivityLuckyDrawExample example);

    int updateByExample(@Param("record") BsActivityLuckyDraw record, @Param("example") BsActivityLuckyDrawExample example);

    int updateByPrimaryKeySelective(BsActivityLuckyDraw record);

    int updateByPrimaryKey(BsActivityLuckyDraw record);
    
    /**
     * 所有中奖列表，最新中奖在前
     * 根据activityId查询所有中奖信息,已中奖,已抽奖,用户抽奖时间倒序
     * is_win = 'Y',is_user_draw = 'Y',user_draw_time desc
     * @param activityId
     * @param limitNum
     * @return
     */
    List<BsActivityLuckyDrawVO> getLuckyAllList(@Param("activityId")Integer activityId,@Param("limitNum") Integer limitNum);
    
    /**
     * 所有幸运出借人的中奖列表
     * 根据activityId查询所有中奖信息
     * is_win = 'Y',is_user_draw = 'N'
     * @param activityId
     * @return
     */
    List<BsActivityLuckyDrawVO> getLuckyLenders(@Param("activityId")Integer activityId);
    
    /**
     * 幸运出借人的中奖列表
     * 根据activityId和backDrawTime查询所有中奖信息
     * is_win = 'Y',is_user_draw = 'N'
     * @param activityId
     * @param backDrawTime
     * @return
     */
    List<BsActivityLuckyDrawVO> getLuckyLendersByDate(@Param("activityId")Integer activityId, @Param("backDrawTime")String backDrawTime);
    
    /**
     * 查询当前已抽奖人数,去重复的userId
     * is_win = 'Y',is_user_draw = 'Y'
     * @param activityId
     * @return
     */
    int countLuckyNum(@Param("activityId")Integer activityId);
    
    /**
     * luckyFlagS：Y查询用户已中奖品：is_win = 'Y',is_user_draw = 'Y',user_draw_time desc
     * luckyFlagS：N查询用户未抽奖列表
     * @param activityId
     * @param userId
     * @param luckyFlagS
     * @return
     */
    List<BsActivityLuckyDrawVO> getUserLuckyList(@Param("activityId")Integer activityId,
    		@Param("userId")Integer userId,@Param("luckyFlagS") String luckyFlagS,
    		@Param("start") Integer start,@Param("page") Integer page);
    
    /**
     * luckyFlagS：Y查询用户已中奖品：is_win = 'Y',is_user_draw = 'Y',user_draw_time desc
     * luckyFlagS：N查询用户未抽奖列表
     * @param activityId
     * @param userId
     * @param luckyFlagS
     * @return
     */
    int countUserLucky(@Param("activityId")Integer activityId,
    		@Param("userId")Integer userId,@Param("luckyFlagS") String luckyFlagSS);

    /**
     * 根据金额 查询符合的条数 --activity_id=1 
     * @param minAmount
     * @param maxAmount
     * @return
     */
    int getCountByAmount(@Param("minAmount")Integer minAmount, @Param("maxAmount")Integer maxAmount);
    
    
    /**
     * 管理台获取列表
     * @param agentIds
     * @param agentsFlag --0,有普通用户且有其他渠道；1--仅有普通用户
     * @param mobile
     * @param drawTimeStart
     * @param drawTimeEnd
     * @param isUserDraw
     * @param awardContent
     * @param isWin
     * @param start
     * @param numPerPage
     * @param userName
     * @parm activityName
     * @return
     */
    List<BsActivityLuckyDrawVO> getUserLuckyList4Manage(@Param("agentIds")List<Object> agentIds,
    		@Param("agentsFlag")String agentsFlag,
    		@Param("mobile")String mobile,
    		@Param("drawTimeStart")String drawTimeStart,
    		@Param("drawTimeEnd")String drawTimeEnd,
    		@Param("isUserDraw")String isUserDraw,
    		@Param("awardContent")String awardContent,
    		@Param("isWin")String isWin,
    		@Param("start") Integer start,
            @Param("numPerPage") Integer numPerPage,
            @Param("userName") String userName,
            @Param("activityName") String activityName);
    
    /**
     * 加薪计划数据导出
     * @param agentIds
     * @param agentsFlag --0,有普通用户且有其他渠道；1--仅有普通用户
     * @param mobile
     * @param drawTimeStart
     * @param drawTimeEnd
     * @param isUserDraw
     * @param awardContent
     * @param isWin
     * @param start
     * @param numPerPage
     * @param userName
     * @parm activityName
     * @return
     */
    List<BsActivityLuckyDrawVO> getIncreaseSalaryInfoForExcel(@Param("agentIds")List<Object> agentIds,
    		@Param("agentsFlag")String agentsFlag,
    		@Param("mobile")String mobile,
    		@Param("drawTimeStart")String drawTimeStart,
    		@Param("drawTimeEnd")String drawTimeEnd,
    		@Param("isUserDraw")String isUserDraw,
    		@Param("awardContent")String awardContent,
    		@Param("isWin")String isWin,
    		@Param("start") Integer start,
            @Param("numPerPage") Integer numPerPage,
            @Param("userName") String userName,
            @Param("activityName") String activityName);
    
    /**
     * 管理台获取列表条数
     * @param agentIds
     * @param mobile
     * @param agentsFlag --0,有普通用户且有其他渠道；1--仅有普通用户
     * @param drawTimeStart
     * @param drawTimeEnd
     * @param isUserDraw
     * @param awardContent
     * @param isWin
     * @param userName
     * @parm activityName
     * @return
     */
    int countUserLuckyList4Manage(@Param("agentIds")List<Object> agentIds,
    		@Param("agentsFlag")String agentsFlag,
    		@Param("mobile")String mobile,
    		@Param("drawTimeStart")String drawTimeStart,
    		@Param("drawTimeEnd")String drawTimeEnd,
    		@Param("isUserDraw")String isUserDraw,
    		@Param("awardContent")String awardContent,
    		@Param("isWin")String isWin,
            @Param("userName") String userName,
            @Param("activityName") String activityName);

    /**
     * 加薪计划活动中奖列表条数
     * @param userDrawTime
     * @param awardId
     * @param activityId
     * @return
     */
    int countSalaryIncreasePlanList(
    		@Param("userDrawTime")String userDrawTime,
    		@Param("awardId")int awardId,
            @Param("activityId")int activityId);
    
    /**
     * 加薪计划活动中奖列表
     * @param userDrawTime
     * @param awardId
     * @param activityId
     * @return
     */
    List<BsActivityLuckyDrawVO> getSalaryIncreasePlanList(
    		@Param("userDrawTime")String userDrawTime,
    		@Param("awardId")int awardId,
            @Param("activityId")int activityId);
    
    
    /**
     * 统计中奖总金额
     * @param activityId    活动ID
     * @return
     */
    Double sumAwardAmount(@Param("activityId") Integer activityId);

    /**
     * 根据活动ID分页查询获奖名单
     * @param activityId    活动ID
     * @param start         分页信息-第几页 0开始
     * @param numPerPage    分页信息-每页显示条数
     * @return
     */
    List<LanternFestival2017LanternDetailVO> selectByPage(@Param("activityId") Integer activityId, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 查询当天已抽奖人数,去重复的userId
     * @param activityId
     * @return
     */
    int countLuckyNumToDay(@Param("activityId") Integer activityId);

    /**
     * 统计用户元宝个数
     * @param userId        用户ID
     * @param activityId    活动编号
     * @param awardId       奖品ID
     * @param isConfirm     是否已经兑奖
     * @return
     */
    Integer countGoldIngotByUserId(@Param("userId") Integer userId, @Param("activityId") Integer activityId, @Param("awardId") Integer awardId, @Param("isConfirm") String isConfirm);

    /**
     * 根据活动ID查询刮刮乐活动获奖名单
     * @param activityId    活动ID
     * @return
     */
    List<ActivityScratchcardAwardVO> userAwardList(@Param("activityId") Integer activityId, @Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 刮刮乐活动用户中奖信息
     * @param userId
     * @return
     */
    ActivityScratchcardPrizeVO scratchcardPrize(@Param("userId") Integer userId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 统计感恩节活动投资兑换好礼次数
     * @param userId        用户ID
     * @param luckyDrawId   抽奖ID
     * @return
     */
    int countThanksGiving(@Param("userId") Integer userId, @Param("luckyDrawId") Integer luckyDrawId);

    /**
     * 查询投资号
     * @param userId
     * @return
     */
    List<ActivityLuckyNumber> selectInvestNumber(Integer userId);

    /**
     * 查询幸运号
     * @return
     */
    List<ActivityLuckyNumber> selectLuckyNumber();
    
    /**
     * 查询微信小程序财运大转盘我的奖品
     * @param userId
     * @param activityId
     * @return
     */
    List<BsActivityLuckyDraw> selectAwardListByUserId(@Param("userId") Integer userId, @Param("activityId") Integer activityId);
}