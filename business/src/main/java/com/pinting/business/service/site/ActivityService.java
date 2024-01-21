package com.pinting.business.service.site;

import com.pinting.business.hessian.site.message.ReqMsg_Activity_SalaryIncreasePlan;
import com.pinting.business.hessian.site.message.ResMsg_Activity_SalaryIncreasePlan;
import com.pinting.business.model.BsActivity2017anniversaryUserBenison;
import com.pinting.business.model.BsActivityLuckyDraw;
import com.pinting.business.model.vo.*;
import com.pinting.core.util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2017/1/24
 * Description: 活动服务
 */
public interface ActivityService {

    // ==================================================== 活动公共方法开始 ======================================================
    /**
     * 是否在活动期间之内
     * @param activityId  活动ID
     * @return
     */
    String duringActivity(Integer activityId);

    /**
     * 是否在活动期间之内
     * @param activityId    活动ID
     * @param preDays       预热天数
     * @param toEndDays     即将结束的天数
     * @return              not_start-未开始；pre-预热期；start-已开始；will_end-即将结束；end-已结束
     */
    String duringActivity(Integer activityId, Integer preDays, Integer toEndDays);

    /**
     * 是否在活动期间之内
     * @param startTime     活动开始时间
     * @param endTime       活动结束时间
     * @param preDays       预热天数
     * @param toEndDays     即将结束的天数
     * @return              not_start-未开始；pre-预热期；start-已开始；will_end-即将结束；end-已结束
     */
    String duringActivity(Date startTime, Date endTime, Integer preDays, Integer toEndDays);

    /**
     * 是否在活动期间之内
     * @param startTime YYYY-MM-DD HH:mm:ss
     * @param endTime   YYYY-MM-DD HH:mm:ss
     * @return
     */
    String duringActivity(String startTime, String endTime);

    /**
     * 根据ID查询对应活动的开始时间和结束时间
     * @param activityId  活动ID
     * @return
     */
    BsActivityVO queryActivity(Integer activityId);

    /**
     * 根据ID查询对应活动的开始时间和结束时间
     * @param activityId    活动ID
     * @param userId        用户ID
     * @return
     */
    ActivityBaseVO queryBaseActivity(Integer activityId, Integer userId);

    /**
     * 比较两个时间的大小
     * @param time1
     * @param time2
     * @return 0-相同；1-time1>time2；-1-time1<time2
     */
    int compareDate(String time1, String time2);

    /**
     * 清空活动数据
     */
    void revertLuckyDraw(int activityId);

    /**
     * 指定日期是否在活动期间
     * @param date
     * @param activityId
     * @return
     */
    String duringActivity(Date date, int activityId);


    // ==================================================== 活动公共方法结束 ======================================================

    
/**
     * 环节一基础页面信息
     * @return
     */
    NewYear2018FirstVO newYear2018FirstInfo(Integer userId);

    /**
     * 环节二基础页面信息
     * @return
     */
    NewYear2018SecondVO newYear2018SecondInfo(Integer userId);

    /**
     * 环节三基础页面信息
     * @param userId    用户ID
     * @return
     */
    NewYear2018ThirdVO newYear2018ThirdInfo(Integer userId);

    /**
     * 环节一-领取红包
     * @param userId    用户ID
     */
    void newYear2018GetRedPacket(Integer userId);

    /**
     * 环节二-抢红包
     * @param userId    用户ID
     */
    void newYear2018RobRedPacket(Integer userId, String time);



    // ==================================================== 2018年新年活动结束 ======================================================

	// ==================================================== 2018年年会抽奖开始 ======================================================
    /**
     * 查询中奖列表
     * @param type  奖品类型（1-一等奖；2-二等奖；3-三等奖；0-幸运奖）
     * @return
     */
    ChristmasEveVO yearEndLuckyUserList(Integer type);


    /**
     * 抽奖
     * @param type  奖品类型（1-一等奖；2-二等奖；3-三等奖；0-幸运奖）
     * @return
     */
    ChristmasEveVO yearEndLuckyDraw(int type);

    /**
     * 补抽
     * @param type  奖品类型（1-一等奖；2-二等奖；3-三等奖；0-幸运奖）
     * @return
     */
    ChristmasEveResultVO yearEndMakeupDraw(int type);
    // ==================================================== 2018年年会抽奖结束 ======================================================

    // ==================================================== 2017年平安夜湾粉抽奖活动开始 ======================================================

    /**
     * 查询中奖列表
     * @param type  奖品类型（1-一等奖；2-二等奖；3-三等奖；0-幸运奖）
     * @return
     */
    ChristmasEveVO christmasEveLuckyUserList(Integer type);

    /**
     * 抽奖
     * @param type  奖品类型（1-一等奖；2-二等奖；3-三等奖；0-幸运奖）
     * @return
     */
    ChristmasEveVO christmasEveLuckyDraw(int type);

    /**
     * 补抽
     * @param type  奖品类型（1-一等奖；2-二等奖；3-三等奖；0-幸运奖）
     * @return
     */
    ChristmasEveResultVO christmasEveMakeupDraw(int type);

    // ==================================================== 2017年平安夜湾粉抽奖活动结束 ======================================================


    // ==================================================== 2017年318周年庆活动开始 ======================================================

    /**
     * 1. 一重礼，二重礼主页面信息
     * @param userId    用户ID
     * @return
     */
    AnniversaryFirstHomePageInfoVO anniversaryFirstHomePageInfo(Integer userId);

    /**
     * 2. 二重礼分享页面信息
     * @param shareUserId   分享者用户ID
     * @param openId        微信openID
     * @param pageNum       当前页码
     * @param numPerPage    每页显示条数
     * @return
     */
    AnniversarySharePageInfoVO anniversarySharePageInfo(Integer shareUserId, String openId, Integer pageNum, Integer numPerPage);

    /**
     * 3. 二重礼助力服务
     * @param wxFriend  助理好友微信信息
     */
    void anniversaryHelpFriend(AnniversaryHelpFriendVO wxFriend);

    /**
     * 4. 二重礼3月18日解锁元宝服务
     * @param userId    投资者用户ID
     */
    void anniversaryUnlockGoldIngot(Integer userId);

    /**
     * 5. 三重礼，四重礼主页面信息
     * @param userId    用户ID
     * @return
     */
    AnniversarySecondHomePageInfoVO anniversarySecondHomePageInfo(Integer userId);

    /**
     * 6. 三重礼祝福语存库服务
     * @param benison   祝福语
     */
    ActivityBaseVO anniversaryBenison(BsActivity2017anniversaryUserBenison benison);

    /**
     * 7. 三重礼管理台祝福语审核服务
     * @param benison   祝福语
     */
    void anniversaryCheckBenison(BsActivity2017anniversaryUserBenison benison);

    /**
     * 7.1 三重礼管理台祝福语查询列表
     * @param pageNum
     * @param numPerPage
     * @return
     */
    List<BsActivity2017anniversaryUserBenison> anniversaryBenisonList(Integer pageNum, Integer numPerPage);

    /**
     * 7.2 三重礼管理台祝福语查询列表总个数
     * @return
     */
    long anniversaryBenisonListCount();

    /**
     * 8. 五重礼、六重礼页面信息
     * @param userId    用户ID
     * @return
     */
    AnniversaryThirdHomePageInfoVO anniversaryThirdHomePageInfo(Integer userId);

    /**
     * 8.1 五重礼-用户获奖列表
     * @param userId
     * @return
     */
    List<AnniversaryAwardInfoVO> userAwardList(Integer userId);

    /**
     * 8.2 五重礼-获奖名单
     * @return
     */
    Map<String, List<HashMap<String, Object>>> winnerList();

    /**
     * 9. 六重礼抽奖服务
     * @param userId    用户ID
     */
    Integer anniversaryLuckyDraw(Integer userId);


    /**
     * 0. 入口页面（this.queryActivity && this.duringActivity）
     *  a. 每一期的活动时间
     *  b. 每一期是否开始的标识
     * 1. 一重礼，二重礼主页面信息
     *  a. 是否登录
     *  b. 一重礼活动时间
     *  c. 二重礼活动时间
     *  d. 一重礼是否开始标识
     *  e. 二重礼是否开始标识
     *  f. 元宝个数
     *  g. 元宝是否可开启
     * 2. 二重礼分享页面信息
     *  a. 分享者元宝个数
     *  b. 助力好友列表（昵称、头像、元宝）
     * 3. 二重礼助力服务
     *  a. 判断活动是否开始（未开始不做任何处理）
     *  b. 判断活动是否结束（结束了的助力，被助力者添加0个元宝）
     *  c. 判断是否已经助力
     *  d. 判断被助力者是否超过60个元宝（超过60，被助力者添加0个元宝）
     *  e. 保存或更新助力者微信信息
     *  f. 被助力者添加2-6个元宝（超过60或者活动已经结束了的助力，被助力者添加0个元宝）
     * 4. 二重礼3月18日解锁元宝服务
     *  a. 判断用户3月18日当天投资额满5000，解锁一个元宝（5元奖励金）
     *  b. 投资完成时立即发放奖励金
     * 5. 三重礼，四重礼主页面信息
     *  a. 三重礼活动时间
     *  b. 三重礼活动时间
     *  c. 四重礼活动时间
     *  d. 四重礼活动时间
     *  e. 三重礼是否开始标识
     *  f. 四重礼是否开始标识
     *  g. 是否登录
     *  h. 祝福语列表数据（取1000条数据，按用户祝福时间倒叙）
     *  i. 年化投资额实时排行榜
     * 6. 三重礼祝福语存库服务
     * 7. 三重礼管理台祝福语审核服务
     * 8. 五重礼、六重礼页面信息
     *  a. 五重礼活动时间
     *  b. 六重礼活动时间
     *  c. 五重礼是否开始标识
     *  d. 六重礼是否开始标识
     *  e. 是否登录状态
     *  f. 五重礼当前用户今日年化投资额
     *  g. 五重礼当前用户预计可瓜分奖金
     *  h. 五重礼今日总年化投资额
     *  i. 五重礼下一个升级奖金额度的年化投资额
     *  j. 五重礼今日年化投资额排行榜
     *  k. 五重礼获奖名单
     *  l. 六重礼是否抽中过奖品
     *  m. 六重礼抽中的奖品
     * 9. 六重礼抽奖服务
     */
    // ==================================================== 2017年318周年庆活动结束 ======================================================

    // ==================================================== 2017年女神节活动开始 =========================================================

    /**
     * 2017年女神节活动页面信息
     * @return
     */
    ActivityForGirl2017VO activityForGirl2017PageInfo();

    /**
     * 2017年女神节活动校验领取资格
     * @param userId    用户ID
     * @param agentId   当前入口渠道ID
     * @return
     */
    ActivityForGirl2017ReceiveVO checkForGirl2017Receive(Integer userId, Integer agentId);

    /**
     * 2017年女神节活动领取奖品
     * @param userId        用户ID
     * @param agentId       当前入口渠道ID
     * @param receiveType   奖品类型：express-快递；to_store-到店
     * @param consignee     收货人
     * @param tel           收货人联系电话
     * @param address       收货地址
     * @param note          备注
     * @return
     */
    ActivityForGirl2017ReceiveVO activityForGirl2017Receive(Integer userId, Integer agentId, String receiveType, String consignee, String tel, String address, String note);

    // ==================================================== 2017年女神节活动结束 =========================================================

    // ==================================================== 2017年元宵节活动开始 =========================================================
    /**
     * 抽奖页面信息
     * @return
     */
    LanternFestival2017DrawIndexVO luckyDrawPageInfo();

    /**
     * 获得获奖用户列表
     * @return
     */
    LanternFestival2017LanternResultVO getList(Integer pageNum, Integer numPerPage);

    /**
     * 元宵节摇一摇活动-抽奖结果
     * @param userId        用户ID
     * @param activityId    活动ID
     * @return              抽奖结果
     */
    LanternFestival2017DrawResultVO lanternFestival2017LuckyDraw(Integer userId, Integer activityId);

    /**
     * 元宵节理财活动-用户购买获得奖励金
     * @param userId        用户ID
     * @param amount        购买金额
     * @param subAccountId  用户子账户ID
     */
    void userBuyGrantBonus(Integer userId, Double amount, Integer subAccountId);

    // ==================================================== 2017年元宵节活动结束 =========================================================

    //====================================================== 2017踏春活动开始 =======================================================
    /**
     * 2017踏春活动投资排行榜前10
     * @return
     */
    List<PlayerKillingVO> springInvestRankingList();
    
    /**
     * 2017踏春活动 用户投资金额
     * @param userId
     * @return
     */
    Double springUserInvestAmount(Integer userId);
    
    /**
     * 2017踏春活动 用户邀请列表
     * @param userId
     * @return
     */
    List<PlayerKillingVO> springUserInvitedList(Integer userId);
    
    //====================================================== 2017踏春活动结束 =======================================================

    //====================================================== 2017存管后活动开始 =======================================================

    /**
     * 抱团服务
     * @param userId    用户ID
     * @param teamId    团队ID
     * @param serial    序号
     */
    void joinTeam(Integer userId, Integer teamId, Integer serial);

    /**
     * 存管答题抱团活动页面信息
     * @param userId    用户ID
     */
    DepActivityInfoVO depActivityInfo(Integer userId);


    /**
     * 微信点亮存管图标瓜分百万红包首页信息
     * @param userId 用户ID
     * @return
     */
    public ActivityForLightUp2017VO activityForLightUp2017VOPageInfo(Integer userId);

    /**
     * 点亮存管图标
     * @param userId 用户ID
     * @return
     */
    public int addLightUpDepLogo(Integer userId);

    /**
     * 统计点亮存管图标的用户数量
     * @return
     */
    public int queryCountLightUpDepLogo();
    
    /**
     * 存管答题
     * @param userId	用户ID
     * @param subject	答对题数目	
     * @return
     */
    Integer depAnswer(Integer userId, Integer subject);
    
    //====================================================== 2017存管后活动结束 =======================================================

    //====================================================== 2017友富同享邀请活动开始 =======================================================

    /**
     * 邀请金牌排行榜
     * @return
     */
    List<ActivityYouFuRankInfoVO> youFuRankInfo();

    /**
     * 我的战绩
     * @param userId    用户ID
     * @return
     */
    ActivityYouFuSelfInfoVO youFuSelfInfo(Integer userId);
    //====================================================== 2017友富同享邀请活动结束 =======================================================


    //====================================================== 2017感恩节活动结束 =======================================================

    /**
     * 【2017感恩节活动】用戶分享活动获得红包
     * 用户每天首次分享活动至微信（朋友圈/朋友），可随机获得一个感恩红包；
     * @param userId    用户ID
     */
    void share(Integer userId);

    /**
     * 分享页面信息
     * @param userId    用户ID
     * @return
     */
    ActivityBaseVO sharePageInfo(Integer userId);

    /**
     * 【2017感恩节活动】用户累计购买指定标的达到指定金额兑换礼品
     * 每位用户仅可兑换两份礼品，每个品类礼品仅可兑换一次
     * @param userId        用户ID
     * @param term          兑换哪个期限的奖品
     */
    Integer investExchange(Integer userId, Integer term);

    /**
     * 【2017感恩节活动】添加地址
     * @param userId        用户ID
     * @param luckyDrawId   中奖ID
     * @param mobile        手机号
     * @param userName      用户姓名
     * @param address       收货地址
     */
    void addAddress(Integer userId, Integer luckyDrawId, String mobile, String userName, String address);

    /**
     * （购买切面）
     * 【2017感恩节活动】生成投资号，存库
     * @param userId    用户ID
     * @param authSubId 站岗户ID
     */
    void generateInvestNumber(Integer userId, Integer authSubId);

    /**
     * （0点定时）
     * 【2017感恩节活动】从昨日投资号码中产生一个幸运号码，存库
     */
    void luckyNumber();

    /**
     * 【2017感恩节活动】兑奖个数以及状态
     * @param userId
     * @return
     */
    List<ActivityGiftNumberVO> giftNumber(Integer userId);

    /**
     * 【2017感恩节活动】每日幸运号码以及我的投资号查询
     * @param userId
     * @return
     */
    ActivityLuckyNumberVO luckyNumber(Integer userId);

    /**
     * 是否已经存在地址
     * @param userId
     * @return
     */
    boolean haveAddress(Integer userId, Integer luckyDrawId);

    //====================================================== 2017感恩节活动结束 =======================================================

    // ==================================================== 微信小程序财运大转盘 开始 =======================================================
    /**
     * 微信小程序财运大转盘抽奖数据（是否登录，是否开始）
     * @param userId    用户ID
     * @return
     */
    WeChatLuckyTurningInfoVO weChatLuckyTurningInfo(Integer userId);
    
    /**
     * 微信小程序财运大转盘抽奖异步数据（抽奖机会，是否分享）
     * @param userId    用户ID
     * @return
     */
    WeChatLuckyTurningInfoVO weChatLuckyTurningDataInfo(Integer userId);
    
    /**
     * 微信小程序财运大转盘我的奖品列表
     * @param userId    用户ID
     * @return
     */
    WeChatAwardListInfoVO weChatAwardListInfo(Integer userId);
    
    /**
     * 微信小程序财运大转盘抽奖结果
     * @param userId    用户ID
     * @return
     */
    WeChatStartTheLotteryVO weChatStartTheLottery(Integer userId);
    
    /**
     * 微信小程序财运大转盘获得抽奖机会
     * @param userId    用户ID
     * @param type      获得类型：share-分享
     * @return
     */
    String wechatChanceToDraw(Integer userId, String type);
    
    /**
     * 加薪计划活动查询中奖信息
     */
    ResMsg_Activity_SalaryIncreasePlan salaryIncreasePlan(ReqMsg_Activity_SalaryIncreasePlan req, ResMsg_Activity_SalaryIncreasePlan res);
    
    /**
     * 加薪计划活动插入中奖信息
     */
    int insertSelective(BsActivityLuckyDraw record);
}
