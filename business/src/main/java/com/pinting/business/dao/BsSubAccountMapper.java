package com.pinting.business.dao;

import com.pinting.business.accounting.loan.model.LoanMatchInvestorRangeVO;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSubAccountExample;
import com.pinting.business.model.vo.*;
import com.pinting.gateway.hessian.message.qb178.model.PositionProduct4UserInfo;
import com.pinting.gateway.hessian.message.qb178.model.QueryBalanceInfo;
import com.pinting.gateway.hessian.message.qb178.model.RepayPlanInfo;
import org.apache.ibatis.annotations.Param;

import java.util.*;

public interface BsSubAccountMapper {
    int countByExample(BsSubAccountExample example);

    int deleteByExample(BsSubAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSubAccount record);

    int insertSelective(BsSubAccount record);

    List<BsSubAccount> selectByExample(BsSubAccountExample example);

    BsSubAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSubAccount record, @Param("example") BsSubAccountExample example);

    int updateByExample(@Param("record") BsSubAccount record, @Param("example") BsSubAccountExample example);

    int updateByPrimaryKeySelective(BsSubAccount record);

    int updateByPrimaryKey(BsSubAccount record);
    /**
     * 根据用户编号查询结算户
     * @param userId
     * @return
     */
    BsSubAccount selectJSHSubAccountByUserId(Integer userId);

	/**
	 * 根据用户编号查询结算户
	 * @param userId
	 * @return
	 */
	BsSubAccount selectDepJSHSubAccountByUserId(Integer userId);
    
    /**
     * 根据用户编号查询奖励金
     * @param userId
     * @return
     */
    BsSubAccount selectJLJSubAccountByUserId(Integer userId);

    /**
	 * 根据userId查询我的投资记录
	 * @param data 用户编号
	 * @return 如果查询成功返回列表，否则返回null
	 */
    ArrayList<BsSubAccountVO> selectByExamplePage(Map<String, Object> data);

	Integer countMyInvestCount(Map<String, Object> data);

	BsSubAccountVO selectByExampleByUserIdAndId(Map<String, Object> paramMap);
	
	/**
	 * 根据计息日期查询所有有效子产品户，并直接计算各产品户的利息
	 * @param interestDate 计息日期
	 * @return
	 */
	List<BsSubAc4InterestVO> selectInterestForProduct(Date interestDate);
	/**
	 * 根据计息日期查询所有用户的该天利息（根据用户分组查询，并统计用户利息）
	 * @param interestDate
	 * @return
	 */
	List<BsDailyInterestVO> selectUserDailyInterest(Date interestDate);
	/**
	 * 增量修改结算户余额信息
	 * @param bsSubAccount
	 * @return
	 */
	int updateBalancesByIncrement(BsSubAccount bsSubAccount);
	
	/**
	 * 根据产品码查询购买人数
	 * @param bsSubAccount 产品代码
	 * @return 返回当前产品购买的人数
	 * @author HuangMengJian
	 */
	int countProductNumByProductCode(BsSubAccount bsSubAccount);

	/**
	 * 查询当前时间购买总金额（投资中 + 转让中 + 已结算 ）
	 * @param subAccount
	 * @return 该产品的购买总金额
	 * @author HuangMengJian
	 */
	Double countProductAmountByProductCode(BsSubAccount subAccount);

	/**
	 * 查询当日购买的总金额    sum(投资中) - sum(已转让)
	 * @param subAccount
	 * @return 当日购买的总金额
	 * @author HuangMengJian
	 */
	Double countProductDayByProductCode(BsSubAccountVO subAccount);

	/**
	 * 根据产品码查询当日购买人数
	 * @param subAccount 产品代码
	 * @return 返回当前产品购买的人数
	 * @author HuangMengJian
	 */
	int countProductNumDayByProductCode(BsSubAccountVO subAccount);

	/**
	 * 投资本金
	 * @return
	 */
	Double sumPeriodCaptial();

	/**
	 * 投资中产品的利息(不是本月购买的利息)
	 * @param productCode
	 * @return
	 */
	ArrayList<BsSubAccountVO> selectCaptialAndInvestInvesting(String productCode);

	/**
	 * 投资中产品的利息(本月刚购买的利息)
	 * @param productCode
	 * @return
	 */
	ArrayList<BsSubAccountVO> selectCaptialAndInvestInvesting2(String productCode);

	/**
	 * 已转让的产品利息
	 * @param productCode
	 * @return
	 */
	ArrayList<BsSubAccountVO> selectCaptialAndInvestTransfered(String productCode);

	/** 
	 * 已结算的产品利息
	 * @param productCode
	 * @return
	 */
	ArrayList<BsSubAccountVO> selectCaptialAndInvestSettled(String productCode);
	
	/**
	 * 根据产品id号查询购买的次数
	 * @param bsSubAccount
	 * @return
	 */
	Integer countProductBuyNumByProductId(BsSubAccountVO bsSubAccount);
	
	/**	
	 * 根据产品id号查询当前还能购买的产品金额
	 * @param bsSubAccount
	 * @return
	 */
	Double countProductSurplusAmountByProductId(BsSubAccountVO bsSubAccount);
	
	/**
	 * 计算当月营销费用
	 * @return
	 */
	Double sumMarkingCosts();

	/**
	 * 计算当天营销费用
	 * @return
	 */
	Double sumTodayMarketingCosts();
	
	/**
	 * 
	 * @Title: selectSubAccount 
	 * @Description: 查询用户子账户信息
	 * @param userId
	 * @param productType
	 * @param status
	 * @return
	 * @throws
	 */
	List<BsSubAccount> selectSubAccount(@Param("userId")Integer userId,
								  @Param("productType")String productType,
								  @Param("status")Integer status);

	/**
	 * 统计当天 各产品的 购买总额
	 * @param currentDay
	 * @return
	 */
	List<BsSubAc4BatchBuyVO> selectBsSubAc4BatchBuy(@Param("currentDay")Date currentDay,
													@Param("propertySymbol")String propertySymbol);

	/**
	 * 获得当天某编码的购买明细
	 * @param currentDay
	 * @param propertySymbol
     * @return
     */
	List<BsSubAc4BatchBuyVO> selectBsSubAcDetail4BatchBuy(@Param("currentDay")Date currentDay,
														  @Param("propertySymbol")String propertySymbol,
														  @Param("productCode")String productCode);

	/**
	 * 根据产品类型，查询当天投资中的产品
	 * @param interestBeginDate
	 * @return
	 */
	List<BsSubAc4InterestVO> selectBsSubAc4BatchBuyDetail(@Param("interestBeginDate")Date interestBeginDate,
														@Param("productCode")String productCode);
	
	/**
	 * 根据产品户编号，查询当天该笔投资中的产品
	 * @param subAccountId
	 * @return
	 */
	BsSubAc4InterestVO selectBsSubAc4BatchBuyDetailSingle(@Param("subAccountId")Integer subAccountId);
	
    /**
     * 查询可用余额
     * @param userId    用户ID
     * @return
     */
    Double selectAvailableBalanceByUserId(Integer userId);
    
    /**
     * 
     * @Description: 根据userId查询子账户信息表
     * @param userId
	 * @param productType
     * @return
     */
    BsSubAccount selectSingleSubActByUserAndType(@Param("userId")Integer userId, @Param("productType")String productType);
    
    /**
     * 根据子账户编号查询（加锁）
     * @param subActId
     * @return
     */
    BsSubAccount selectSubAccountByIdForLock(@Param("subActId")Integer subActId);
    
    /**
	 * 统计投资用户、投资金额、成交笔数
	 * @param map
	 * @return map
	 */
	Map<String,Object> countSubAccount(Map<String,Object> map);
	
	/**
	 * 公众号统计投资用户、投资金额、成交笔数
	 * @param map
	 * @return map
	 */
	Map<String,Object> countSubAccountByTime(Map<String,Object> map);
	
	/**
	 * 公众号统计产品购买查询
	 * @param map
	 * @return map
	 */
	Map<String,Object> countSubAccountByPartner(Map<String,Object> map);
	
	/**
	 * 统计奖励金户余额
	 * @return double
	 */
	public double countBonusAccBalance();

	/**
	 * 统计新增用户投资笔数
	 * @param startTime
	 * @param endTime
	 * @return
     */
    List<Map<String, Object>> countCurrentNewInvestNumber(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
	 * 新增用户投资额
	 * @param startTime
	 * @param endTime
	 * @return
     */
    List<Map<String, Object>> sumCurrentNewInvestBalance(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 当日老用户（第二次及以上投资的）投资笔数
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> countCurrentOldInvestTimes(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 当日老用户（第二次及以上投资的）投资金额 
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> sumCurrentOldInvestBalance(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 当日全部投资金额
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> sumCurrentTotalInvestBalance(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 累计投资人数
     * 
     * @return
     */
    public List<Map<String, Object>> countTotalInvestNumber(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 参与复投人数
     */
    public List<Map<String, Object>> countTotalRepeatNumber(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 累计投资金额
     */
    public List<Map<String, Object>> sumTotalBalance(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 当日全部投资笔数  
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> currentTotalInvestTimes(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 按时对比：新用户购买金额
     * 
     * @param startTime
     * @param agentIds
     * @return
     */
    List<BsSubAccount> selectDailyNewUserInvestAmount(@Param("startTime") String startTime, @Param("agentIds")List<Object> agentIds,@Param("nonAgentId")String nonAgentId);

    /**
     * 按时对比：当日老用户购买金额
     * @param startTime
     * @param agentIds
     * @return
     */
    List<BsSubAccount> selectDailyOldUserInvestAmount(@Param("startTime") String startTime, @Param("agentIds")List<Object> agentIds,@Param("nonAgentId")String nonAgentId);

    /**
     * 累计投资用户数
     * @return
     */
    Integer countTotalInvestUser();

    /**
     * 累计投资金额
     * @return
     */
    Integer countTotalReInvestUser();

    /**
     * 累计投资金额
     * @return
     */
    Double sumTotalInvestAmount();

    /**
     * 新增用户当日投资笔数
     * @param startTime
     * @return
     */
    Integer countdailyNewInvestNumbers(@Param("startTime") String startTime);

    /**
     * 新增用户当日投资额
     * @param startTime
     * @return
     */
    Double sumDailyNewInvestBalance(@Param("startTime") String startTime);

    /**
     * 当日老用户（第二次及以上投资的）投资笔数
     * @param startTime
     * @return
     */
    Integer countDailyOldInvestTimes(@Param("startTime") String startTime);

    /**
     * 当日老用户（第二次及以上投资的）投资金额
     * @param startTime
     * @return
     */
    Double sumDailyOldInvestBalance(@Param("startTime") String startTime);

    /**
     * 当日全部投资笔数  
     * @param startTime
     * @return
     */
    Integer countDailyTotalInvestTimes(@Param("startTime") String startTime);

    /**
     * 当日全部投资金额
     * @param startTime
     * @return
     */
    Double sumDailyTotalInvestBalance(@Param("startTime") String startTime);

    /**
     * 历史数据：日新增用户投资笔数
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> dailyNewInvestNum(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 历史数据：新增用户（没有投资过的）当日投资金额
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> dailyNewInvestAmount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 历史数据：老用户（当日之前又投资过）当日投资笔数
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> dailyOldInvestNum(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 历史数据：老用户（当日之前又投资过）当日投资金额
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> dailyOldInvestAmount(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 历史数据：当日总投资笔数
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> dailyTotalInvestNum(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 历史数据：当日总投资金额
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> dailyTotalInvestAmount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 历史数据：累计投资人数
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> totalInvestUser(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 历史数据：累计复投人数
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> totalReInvestUser(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 历史数据：累计投资金额
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> totalInvestAmount(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 获取起息日在2015-12-1之后，且status为5-已结算或7-结算中,且product_type='REG'的数据 - 按金额倒序排
     * @return
     */
    List<BsSubAccount> getNewSubAccountREGReturnList(@Param("start")Integer start,@Param("pageSize")Integer pageSize);
    
    /**
     * 获取起息日在2015-12-1之后，且status为5-已结算或7-结算中,且product_type='REG'的数据 - 总条数
     * @return
     */
    Integer countNewSubAccountREGReturnList();

    /**
	 * 查询用户的加权投资期限
	 * @return Map<String,Object>
	 */
    Map<String,Object> selectWeightInvestTrem(Map<String,Object> map);

    /**
     * 统计当前用户在活动时间之前投资的数量
     * @param userId
     * @param startTime
     * @return
     */
    Integer countOpenTimeLessThenActivityTime(@Param("userId") Integer userId, 
                                              @Param("startTime") Date startTime);

    /**
     * 统计当前用户在活动时间之内投资的数量
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countOpenTimeBetweenActivityTime(@Param("userId") Integer userId, 
                                             @Param("startTime") Date startTime, 
                                             @Param("endTime") Date endTime,
                                             @Param("percent") Integer percent);

    /**
     * 统计当前用户的在活动期间邀请的用户投资的人数
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countRecommendInvitedUsers(@Param("userId") Integer userId, 
                                       @Param("startTime") Date startTime, 
                                       @Param("endTime") Date endTime);

    /**
     * 单次投资1个月1万（及以上）的个数
     * 
     * @param userId
     * @param startTime
     * @param endTime
     * @param term
     * @param amount
     * @return
     */
    Integer countGreaterThanOrEqualToAmount(@Param("userId") Integer userId, 
                                       @Param("startTime") Date startTime, 
                                       @Param("endTime") Date endTime,
                                       @Param("term") Integer term,
                                       @Param("amount") Double amount);

    /**
     * （活动期间）单次投资12个月1万-5万获得1次“镶钻金蛋”抽奖机会
     * @param userId
     * @param startTime
     * @param endTime
     * @param term
     * @param bottomAmount
     * @param topAmount
     * @return
     */
    Integer countBetweenInvestAmount(@Param("userId") Integer userId, 
                                       @Param("startTime") Date startTime, 
                                       @Param("endTime") Date endTime,
                                       @Param("term") Integer term,
                                       @Param("bottomAmount") Double bottomAmount,
                                       @Param("topAmount") Double topAmount);

    /**
     * 1、活动期间购买[100,10000)1个月+12个月的投资数量或已投资老用户的投资数量（25%）
     * 2、活动期间购买1万元1个月及以上的用户的投资数量（100%）
     * @param userId
     * @param startTime
     * @param endTime
     * @param percent
     * @return
     */
    Integer countNormalEggBetweenActivityTime(@Param("userId") Integer userId, 
                                              @Param("startTime") Date startTime,
                                              @Param("endTime") Date endTime, 
                                              @Param("percent") Integer percent);
    
    /**
     * 获取起息日在2015-12-1及之前，且status为2-投资中,且product_type='REG'的数据
     * @return
     */
    List<BsSubAccount> getOldSubAccountREGList(@Param("start")Integer start,@Param("pageSize")Integer pageSize);
    
    /**
     * 获取起息日在2015-12-1及之前，且status为2-投资中,且product_type='REG'的数据-总条数
     * @return
     */
    Integer countOldSubAccountREGList();
    
    /**
     * 获取起息日在某日(如：2015-12-1)之后，且status为2-投资中,且product_type='REG'的数据 - 按金额倒序排
     * 
     * BsSubAccount 中的note用于暂时存放agent_id
     * @return
     */
    List<BsSubAccount> getNewSubAccountREGList(@Param("start")Integer start,
    		@Param("pageSize")Integer pageSize,@Param("interestBeginDate") Date interestBeginDate,
    		@Param("today") Date today, @Param("propertySymbol") String propertySymbol);
    
    /**
     * 获取起息日在某日(如：2015-12-1)之后，且status为2-投资中,且product_type='REG'的数据-总条数
     * @return
     */
    Integer countNewSubAccountREGList(@Param("interestBeginDate") Date interestBeginDate,
    		@Param("today") Date today, @Param("propertySymbol") String propertySymbol);

    /**
     * 至尊
     * @param userId
     * @param startTime
     * @param endTime
     * @param lessAmount
     * @param moreAmount
     * @return
     */
    List<BsSubAccount> selectSuperMeInvest(@Param("userId") Integer userId,
                                           @Param("startTime") Date startTime,
                                           @Param("endTime") Date endTime,
                                           @Param("lessAmount") Double lessAmount,
                                           @Param("moreAmount") Double moreAmount);

	/**
	 * 财务对账数据：结算2.0（12.1-1.31）
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @param start
	 * @param numPerPage
     * @return
     */
    List<FinancialAccountVO> selectFinancialAccount2(@Param("type") String type,
                                                     @Param("startTime") String startTime,
                                                     @Param("endTime") String endTime,
                                                     @Param("start") Integer start,
                                                     @Param("numPerPage") Integer numPerPage);

	/**
	 * 财务对账数据：结算2.0（12.1-1.31）
	 * @param type
	 * @param startTime
	 * @param endTime
     * @return
     */
    Integer countFinancialAccount2(@Param("type") String type,
                                   @Param("startTime") String startTime,
                                   @Param("endTime") String endTime);

    /**
     * 
     * @return
     */
    Double sumCurrentMonthInterestReturnAmount();

    /**
     * 
     * @return
     */
    Double sumTotalInvestAmountFinancialAccount2();


    /**
	 * 财务对账数据：结算1.0（12.1之前）
	 * @param startTime
	 * @param endTime
	 * @param start
	 * @param numPerPage
	 * @return
     */
    List<FinancialAccountVO> selectFinancialAccount1(@Param("startTime") String startTime,
                                                     @Param("endTime") String endTime,
                                                     @Param("start") Integer start,
                                                     @Param("numPerPage") Integer numPerPage);

	/**
	 * 财务对账数据：结算1.0（12.1之前）
	 * @param startTime
	 * @param endTime
	 * @return
     */
    Integer countFinancialAccount1(@Param("startTime") String startTime,
                                   @Param("endTime") String endTime);
    
    /**
     * 财务对账数据：结算1.0（12.1之前）投资总金额
     * @return
     */
    Double sumTotalInvestAmountFinancialAccount1();
    
    
    /**
	 * 财务对账数据：结算3.0（2.1后)->删选条件精确到日
	 * @param endTime
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<FinancialAccountVO> selectFinancialAccount3Day(@Param("endTime") String endTime,
			@Param("start") Integer start, @Param("pageSize") Integer pageSize);
	/**
	 * 财务对账数据：结算3.0（2.1后)->删选条件精确到日-->投资总额
	 * @param endTime
	 * @return
	 */
	Double sumInvestAmountDay3(@Param("endTime") String endTime);
	
	/**
	 * 财务对账数据：结算3.0（2.1后)->删选条件精确到日-->回款总额
	 * @param endTime
	 * @return
	 */
	Double sumReturnAmountDay3(@Param("endTime") String endTime);
	
	/**
	 * 财务对账数据：结算3.0（2.1后)->删选条件精确到日
	 * @param endTime
	 * @return
	 */
	Integer countFinancialAccount3Day(@Param("endTime") String endTime);
	
	/**
	 * 销售应收查询
	 * @param startTime
	 * @param endTime
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	List<SaleReceivableVO> selectSaleReceivables(@Param("startTime") String startTime,
                                                 @Param("endTime") String endTime,
                                                 @Param("start") Integer start,
                                                 @Param("numPerPage") Integer numPerPage);

    /**
     * 销售应收查询
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countSaleReceivables(@Param("type") String type,
                                 @Param("startTime") String startTime,
                                 @Param("endTime") String endTime);
	
	
	/**
	 * 财务对账数据：结算3.0（2.1后)->删选条件精确到月-->输入时间为某月第一天
	 * @param endTime
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<FinancialAccountVO> selectFinancialAccount3Month(@Param("endTime") String endTime,
			@Param("start") Integer start, @Param("pageSize") Integer pageSize);
	/**
	 * 财务对账数据：结算3.0（2.1后)->删选条件精确到月-->投资总额
	 * @param endTime
	 * @return
	 */
	Double sumInvestAmountMonth3(@Param("endTime") String endTime);
	
	/**
	 * 财务对账数据：结算3.0（2.1后)->删选条件精确到月-->回款总额
	 * @param endTime
	 * @return
	 */
	Double sumReturnAmountMonth3(@Param("endTime") String endTime);
	
	/**
	 * 财务对账数据：结算3.0（2.1后)->删选条件精确到月
	 * @param endTime
	 * @return
	 */
	Integer countFinancialAccount3Month(@Param("endTime") String endTime);
	
	/**
	 * 销售渠道结算查询
	 * @param agentId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<SaleAgentDataVO> selectSaleAgentData(@Param("agentId") Integer agentId, 
	                                          @Param("startTime") String startTime, 
	                                          @Param("endTime") String endTime,
	                                          @Param("start") Integer start,
                                              @Param("numPerPage") Integer numPerPage);
	
	/**
	 * 销售渠道CP总计查询
	 * @param agentId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double sumCPSaleAgentData(@Param("agentId") Integer agentId, 
	                                          @Param("startTime") String startTime, 
	                                          @Param("endTime") String endTime,
	                                          @Param("start") Integer start,
                                              @Param("numPerPage") Integer numPerPage);
	
	/**
	 * 统计销售渠道结算查询总数
	 * @param agentId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer countSaleAgentData(@Param("agentId") Integer agentId, 
                               @Param("startTime") String startTime, 
                               @Param("endTime") String endTime);
	
	/**
	 * 查询希财渠道某一产品用户投资总金额
	 * @param agentId
	 * @return 希财渠道用户投资总金额
	 */
	Double countCsaiUserBuyAccount(@Param("agentId")Integer agentId,@Param("productId")Integer productId);

    /**
	 * 查询希财渠道某一产品投资的用户总数
	 * @param agentId
	 * @param productId
	 * @return
     */
    Integer countCsaiBuyUser(@Param("agentId")Integer agentId,@Param("productId")Integer productId);
	
	
    /**
	 * 统计投资用户、投资金额、成交笔数、年化金额
	 * @param map
	 * @return map
	 */
	Map<String,Object> statisticSubAccount(Map<String,Object> map);
	
	
	
	/**
	 * 按月查询今年每个月用户投资情况
	 * @return
	 */
	List<TotalInvestGroupByMonthVO> selectTotalInvestGroupByMonth();
	
	/**
	 * 查询用户待获取的收益
	 * @return
	 */
	Double investInterestWill();
	
	/**
	 * 按照产品类型查新投资总金额
	 * @return
	 */
	List<InvestTotalGroupByProductVO> investTotalGroupByProduct();
	
	/**
	 * 我的投资列表
	 * @param userId
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<BsSubAccountVO> selectMyInvestment(@Param("userId")Integer userId,@Param("start")Integer start,@Param("pageSize")Integer pageSize, @Param("status")Integer status);
	
	
	/**
	 * 查询当月累计投资总金额
	 * @return
	 */
	Double investMentOverDateMonth(@Param("dateTime") String dateTime );
	
	/**
	 * 按照产品投资期限长短类型查投资总金额
	 * @return
	 */
	List<InvestTotalGroupByProductVO> investTotalGroupByProductTerm();
	
	/**
	 * 查询投资人性别比例
	 * @return
	 */
	List<InvestorTypeVO> investorTypeSex();
	
	/**
	 * 查询投资人年龄段比例
	 * @return
	 */
	List<InvestorTypeVO> investorTypeAge();
	/**
	 *  理财意向--查看收益
	 * @param subAccountId 子账户ID
	 * @return
	 */
	FinancialUserInvestDetailVO selectFinancialUserInvestDetail(@Param("subAccountId")
			Integer subAccountId);
	/**
	 * 理财意向--充值记录
	 * @param subAccountId 子账户ID
	 * @return
	 */
	FinancialRechargeRecordVO financialRechargeRecord(@Param("subAccountId")Integer subAccountId);
	
	
	List<BsSubAccount> selectSubAccountByUserIdAndOpenTime(@Param("userId")Integer userId,@Param("resultsTime")Date resultsTime);

    /**
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> countNewInvestUser(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     *
	 * @param startTime
	 * @param endTime
     * @return
     */
    List<Map<String, Object>> countInvestUser(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 
     * @param startTime
     * @param agentIds
     * @param nonAgentId
     * @return
     */
    List<BsSubAccount> selectDailyNewInvestUser(@Param("startTime") String startTime, @Param("agentIds") List<Object> agentIds, @Param("nonAgentId") String nonAgentId);

    /**
     * 
     * @param startTime
     * @param agentIds
     * @param nonAgentId
     * @return
     */
    List<BsSubAccount> selectDailyInvestUser(@Param("startTime") String startTime, @Param("agentIds") List<Object> agentIds, @Param("nonAgentId") String nonAgentId);

    /**
     * 
     * @param startTime
     * @param agentIds
     * @param nonAgentId
     * @return
     */
    List<BsSubAccount> selectInvestAnnual(@Param("startTime") String startTime, @Param("agentIds") List<Object> agentIds, @Param("nonAgentId") String nonAgentId);
    
    
    /**
     * 根据interestBeginDate查询改日之后的匹配额和投资额相差100元以上的列表，按时间分组查询
     * @param interestBeginDate
     * @return
     */
    List<Map<String, Object>> getNoMatchList(@Param("interestBeginDate")Date interestBeginDate, @Param("propertySymbol")String propertySymbol);


    /**
     * 查询起息时间是某日的投资中的总投资金额
     * @param interestBeginDate
     * @return
     */
    Double sumInvestBalanceByDate(@Param("interestBeginDate")Date interestBeginDate, @Param("propertySymbol")String propertySymbol);
    
    /**
     * 根据起息时间查询已经完全匹配的和匹配金额超过一定比例的列表
     * @param interestBeginDate
     * @param percent
     * @param topBalance
     * @return
     */
    List<Map<String, Object>> getMatchedList(@Param("interestBeginDate")Date interestBeginDate,
    		@Param("percent")Double percent,@Param("topBalance")Double topBalance, @Param("propertySymbol")String propertySymbol);
    
    /**
     * 根据起息时间查询需要再次匹配的列表，主要查询金额和subAccountId
     * @param interestBeginDate
     * @param minPercent 投资金额<=5000时需要匹配金额(未匹配金额)/投资金额 >percent，满足条件
     * @param maxPercent 投资金额>5000时需要匹配金额(未匹配金额)/投资金额 >percent，满足条件
     * @return
     */
    List<Map<String, Object>>  getNeedMatchList(@Param("interestBeginDate")Date interestBeginDate,
    		@Param("minPercent")Double minPercent, @Param("maxPercent")Double maxPercent, @Param("propertySymbol")String propertySymbol);
    
    
    /**
     * 根据起息时间查询需要再次匹配的列表条数，主要查询金额和subAccountId
     * @param interestBeginDate
     * @param minPercent 投资金额<=5000时需要匹配金额(未匹配金额)/投资金额 >percent，满足条件
     * @param maxPercent 投资金额>5000时需要匹配金额(未匹配金额)/投资金额 >percent，满足条件
     * @return
     */
    Integer  getNeedMatchCount(@Param("interestBeginDate")Date interestBeginDate,
    		@Param("minPercent")Double minPercent, @Param("maxPercent")Double maxPercent, @Param("propertySymbol")String propertySymbol);
    

    /**
     * 查询起息日在规定时间内，投资成功的投资总金额（站岗）
     * @param propertySymbol 
     * @param productType 
     * @param userIdList
     * @param minInterestBeginDat      minInterestBeginDate < interest_begin_date < now
     * @param isSuper yes - 表示是超级用户，no表示普通用户
     * @param matchLimitAmount 普通理财人债权匹配时低于该金额的不进行债权承接
     * @param outUserId 出让人用户id，需要排除
     * @return
     */
    List<DailyAmount4LoanVO> getSumBalanceByProductType(@Param("propertySymbol")String propertySymbol,@Param("productType")String productType,
    		@Param("userIdList")List<Integer> userIdList,@Param("minInterestBeginDate")String minInterestBeginDat,
    		@Param("isSuper") String isSuper,@Param("matchLimitAmount")Double matchLimitAmount,@Param("outUserId") Integer outUserId);

	/**
	 * 查询起息日在规定时间内，投资成功的小额投资总金额（站岗）
	 * @param propertySymbol
	 * @param productType
	 * @param userIdList
	 * @param minInterestBeginDat      minInterestBeginDate < interest_begin_date < now
	 * @param isSuper yes - 表示是超级用户，no表示普通用户
	 * @param matchLimitAmount 普通理财人债权匹配时低于该金额的不进行债权承接
	 * @return
	 */
	List<DailyAmount4LoanVO> getSumBalanceByProductTypeSmall(@Param("propertySymbol")String propertySymbol,@Param("productType")String productType,
														@Param("userIdList")List<Integer> userIdList,@Param("minInterestBeginDate")String minInterestBeginDat,@Param("isSuper") String isSuper,@Param("matchLimitAmount")Double matchLimitAmount);
    /**
     * 债权匹配时查询可以匹配的AUTH户列表
     * @param propertySymbol
     * @param productType
     * @param userIdList
     * @param interestBeginDate 起息日
     * @param term 投资期限
     * @param isSuper yes - 表示是超级用户，no表示普通用户
     * @param matchLimitAmount 普通理财人债权匹配时低于该金额的不进行债权承接
     * @return
     */
    List<BsCanMatch4ZanSubAccountVO> canMatch4ZanList(@Param("propertySymbol")String propertySymbol, @Param("productType")String productType,
													  @Param("userIdList")List<Integer> userIdList, @Param("interestBeginDate")String interestBeginDate,
													  @Param("term")Integer term, @Param("isSuper") String isSuper, @Param("matchLimitAmount")Double matchLimitAmount);

	/**
	 * 债权匹配时查询可以匹配的小额AUTH户列表
	 * @param propertySymbol
	 * @param productType
	 * @param userIdList
	 * @param interestBeginDate 起息日
	 * @param term 投资期限
	 * @param isSuper yes - 表示是超级用户，no表示普通用户
	 * @param matchLimitAmount 普通理财人债权匹配时低于该金额的不进行债权承接
	 * @return
	 */
	List<BsCanMatch4ZanSubAccountVO> canSmallMatch4ZanList(@Param("propertySymbol")String propertySymbol, @Param("productType")String productType,
													  @Param("userIdList")List<Integer> userIdList, @Param("interestBeginDate")String interestBeginDate,
													  @Param("term")Integer term, @Param("isSuper") String isSuper, @Param("matchLimitAmount")Double matchLimitAmount);
    
    /**
     * 债权转让时查询可以接收债权的AUTH户金额>=某值的最小值
     * 或 >=某值的最大值
     * @param propertySymbol
     * @param productType
     * @param userIdList
     * @param interestBeginDate 起息日
     * @param term 投资期限
     * @param matchLimitAmount 普通理财人债权匹配时低于该金额的不进行债权承接
     * @param orderByType 排序规则
     * @return
     */
    BsCanMatch4ZanSubAccountVO getCanMatch4Zan2Transfer(@Param("propertySymbol")String propertySymbol, @Param("productType")String productType,
			  @Param("userIdList")List<Integer> userIdList, @Param("interestBeginDate")String interestBeginDate,
			  @Param("term")Integer term, @Param("matchLimitAmount")Double matchLimitAmount,@Param("orderByType")String orderByType);


	/**
	 * 债权转让时查询可以接收债权的AUTH户金额>=某值的最小值
	 * 或 >=某值的最大值
	 * @param propertySymbol
	 * @param productType
	 * @param userIdList
	 * @param interestBeginDate 起息日
	 * @param term 投资期限
	 * @param matchLimitAmount 普通理财人债权匹配时低于该金额的不进行债权承接
	 * @param orderByType 排序规则
	 * @param outUserId 出让用户id(需要排除)
	 * @return
	 */
	BsCanMatch4ZanSubAccountVO getCanMatch4Zan2TransferNew(@Param("propertySymbol")String propertySymbol, @Param("productType")String productType,
			@Param("userIdList")List<Integer> userIdList, @Param("interestBeginDate")String interestBeginDate,@Param("term")Integer term, 
			@Param("matchLimitAmount")Double matchLimitAmount,@Param("orderByType")String orderByType,@Param("outUserId") Integer outUserId);

    /**
     * 查询用户某日购买成功金额，按投资期限统计总投资金额
     * @param propertySymbol
     * @param productType
     * @param userIdList vip用户列表
     * @param interestBeginDate 起息日
     * @return
     */
    List<DailyAmount4LoanVO> getSumOpenBalanceByDate(@Param("propertySymbol")String propertySymbol, 
    		@Param("productType")String productType,@Param("userIdList")List<Integer> userIdList, 
    		@Param("interestBeginDate")String interestBeginDate);
    
	/**
	 * 分页查询委托计划列表
	 * @param userId		用户ID
	 * @param start			分页开始
	 * @param numPerPage	每页显示数据条数
     * @return
     */
	List<BsSubAccountVO> selectCommissionPlanList(@Param("userId") int userId, @Param("start") int start, @Param("numPerPage") int numPerPage);

	/**
	 * 统计委托计划总数
	 * @param userId	用户ID
	 * @return			总数
     */
	int countCommissionPlanList(@Param("userId") int userId);
	
	
	/**
	 * 查询超级理财人的JSH列表
	 * @param userIdList
	 * @return
	 */
	List<BsSubAccountVO> selectJSHBySuperUser( @Param("userIdList")List<Integer> userIdList);
	
	/**
	 * 刮刮乐活动用户投资信息
	 * @param userId
	 * @return
	 */
	BsSubAccountVO selectInvestInfoByActivity(@Param("userId") Integer userId,
			 					@Param("startTime") String startTime, @Param("endTime") String endTime);
	
	/**
	 * 根据userId查询港湾计划投资条数
	 * @param userId
	 * @return 返回我投资的记录的条数
	 */
	int countMyInvestBGWByUserId(Integer userId);
	
	
	/**
	 * 根据站岗户ID（auth）查询此比委托计划状态
	 * @param authSubAccountId	站岗子账户ID
	 * @return
     */
	String queryEntrustStatusBySubAccountId(@Param("authSubAccountId") int authSubAccountId);

	/**
	 * 根据用户ID查询投资中的项目总数（港湾计划+委托计划，港湾计划原先的统计规则不变，委托计划统计规则（委托结束与回款结束状态）不在统计范围内）
	 * @param userId	用户ID
	 * @return			项目总数（港湾计划+委托计划，港湾计划原先的统计规则不变，委托计划统计规则（委托结束与回款结束状态）不在统计范围内）
	 */
	int countInTheInvestmentByUserId(@Param("userId") Integer userId);
	/**
	 * 根据VIP理财人用户ID列表查询对应子账户中站岗户ID列表
	 * @return
	 */
	List<Integer> getSuperUserSubAccountIdList();
	
	
	
	/**
	 * 活动需要查询使用的红包名称和投资的金额，投资的期限，以及投资的产品名称
	 * @param userId
	 * @param subAccountId
	 * @return
	 */
	BsSubAc4ActivityVO select4ActivityBuy(@Param("userId") Integer userId,@Param("subAccountId") int subAccountId);
	
	/**
	 * 根据子账户ID查询对应用户ID
	 * @param subAccountId 子账户ID
	 * @return
	 */
	Integer selectUserIdBySubAccountId(@Param("subAccountId") Integer subAccountId);
	
	/**
	 * 根据用户id查询历史投资open_balance总额
	 * @param userId
	 * @return
	 */
	Double sumAmount4HisBuy(@Param("userId") Integer userId);
	
	/**
	 * 根据产品ID查询产品投资排行榜（前20名）
	 * @param productId 产品ID
	 * @return
	 */
	List<PlayerKillingVO> queryUserInvestRankingList(@Param("productId") Integer productId);

	/**
	 * 三重礼活动，每日排行榜（前10名）
	 * @param selectDate
	 * @param userId  查询单个用户的某日年化投资金额时，传参
	 * @return
	 */
	List<PlayerKillingVO> queryTrebleGiftUserInvestList(@Param("selectDate") Date selectDate,
			@Param("userId") Integer userId);

	/**
	 * 元宵活动，币港湾产品投资个数
	 * @param startTime
	 * @param endTime
     * @return
     */
	int countLanternActivityBuy(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

	/**
	 * 业务概览-北京财务
	 * @return
     */
	Map<String,Object> selectAccountData();


	List<FinanceOverviewDetailForBeijingVO> selectBuyAmountForYunDai(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
	List<FinanceOverviewDetailForBeijingVO> selectBuyAmountFor7Dai(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
	List<FinanceOverviewDetailForBeijingVO> selectBuyAmountForZan(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
	List<FinanceOverviewDetailForBeijingVO> selectReturnAmountForYunDai(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
	List<FinanceOverviewDetailForBeijingVO> selectReturnAmountFor7Dai(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
	List<FinanceOverviewDetailForBeijingVO> selectReturnAmountForZan(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
	List<FinanceOverviewDetailForBeijingVO> selectFinanceAmountToYunDai(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
	List<FinanceOverviewDetailForBeijingVO> selectFinanceAmountTo7Dai(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
	List<FinanceOverviewDetailForBeijingVO> selectFinanceAmountToZan(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

	Integer countBuyAmountForYunDai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Integer countBuyAmountFor7Dai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Integer countBuyAmountForZan(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Integer countReturnAmountForYunDai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Integer countReturnAmountFor7Dai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Integer countReturnAmountForZan(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Integer countFinanceAmountToYunDai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Integer countFinanceAmountTo7Dai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Integer countFinanceAmountToZan(@Param("startTime") String startTime, @Param("endTime") String endTime);

	Double sumBuyAmountForYunDai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Double sumBuyAmountFor7Dai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Double sumBuyAmountForZan(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Double sumReturnAmountForYunDai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Double sumReturnAmountFor7Dai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Double sumReturnAmountForZan(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Double sumFinanceAmountToYunDai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Double sumFinanceAmountTo7Dai(@Param("startTime") String startTime, @Param("endTime") String endTime);
	Double sumFinanceAmountToZan(@Param("startTime") String startTime, @Param("endTime") String endTime);
	
	//累计理财总额
	Double sumTotalAmountForYunDai();
	Double sumTotalAmountFor7Dai();
	Double sumTotalAmountForZan();

	/**
	 * 统计某人一天之内的购买金额
	 * @param userId	用户ID
	 * @param time		yyyy-MM-dd
     * @return
     */
	Double sumBuyAmountByUserIdOneDay(@Param("userId") Integer userId, @Param("time") String time);

	/**
	 * 统计某人购买金额（年化）
	 * @param userId	用户ID
	 * @return
	 */
	Double sumBuyAmountByUserIdBetweenDays(@Param("userId") Integer userId, @Param("startTime") String startTime, @Param("endTime") String endTime);

	/**
	 * （活动期间）累计年化投资额实时排行榜
	 * @param startTime		开始时间
	 * @param endTime		结束时间
	 * @param start			mysql开始页码 0
	 * @param numPerPage	每页显示条数
     * @return
     */
	List<AnniversaryInvestUserInfoVO> selectAnniversaryList(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

	/**
	 * 当日用户年化投资额
	 * @param userId	用户ID
	 * @return
     */
	Double sumAnniversaryOnePerson(@Param("userId") Integer userId);

	/**
	 * 统计当日年化投资额
	 * @param time	时间YYYY-MM-dd
	 * @return
     */
	Double sumAnniversaryAmountOneDay(@Param("time") String time);

	/**
	 * 某一天的年化投资额排行榜
	 * @param time			某一天 YYYY-MM-DD
	 * @param start			mysql开始页码 0
	 * @param numPerPage	每页显示条数
	 * @return
	 */
	List<AnniversaryInvestUserInfoVO> selectAnniversaryTodayList(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

	/**
	 * 当前用户瓜分到的奖金
	 * @param userId		用户编号
	 * @param startTime		开始时间
	 * @param endTime		结束时间
     * @return
     */
	List<AnniversaryAwardInfoVO> selectAnniversaryThatDayList(@Param("userId") Integer userId, @Param("startTime") String startTime, @Param("endTime") String endTime);

	/**
	 * 每天的年化投资排行榜
	 * @param time
	 * @param start
	 * @param numPerPage
     * @return
     */
	List<AnniversaryAwardInfoVO> selectAllAwardList(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

	/**
	 * 获取用户距离指定时间最近的一次投资
	 * @param userId
	 * @param time
     * @return
     */
	BsSubAccount selectClosest(@Param("userId") Integer userId, @Param("time") String time);
	/**
	 * 查询用户投资分布列表
	 * @param userId
	 * @return
	 */
	List<BsInvestProportionVO> selectInvestProportionList(@Param("userId") Integer userId);
	/**
	 * 根据产品投资状态统计产品投资数量
	 * @param userId  用户ID
	 * @param investStatus  产品投资状态
	 * @return
	 */
	Integer countMyInvestByStatus(@Param("userId") Integer userId,@Param("investStatus") String investStatus);
	/**
	 * 
	 * @param data
	 * @return
	 */
	ArrayList<BsSubAccountVO> bgwMyInvestByUserId(Map<String, Object> data);
	
	/**
	 * 
	 * @param userId  用户ID
	 * @param start
	 * @param numPerPage
	 * @param investStatus
	 * @return
	 */
	List<BsSubAccountVO> zanMyInvestByUserId(@Param("userId") Integer userId,@Param("start") Integer start,
			@Param("numPerPage") Integer numPerPage,@Param("investStatus") String investStatus);
	/**
	 * 根据产品投资状态统计产品投资数量 --赞分期
	 * @param userId  用户ID
	 * @param investStatus  产品投资状态
	 * @return
	 */
	Integer countMyInvestZanByStatus(@Param("userId") Integer userId,@Param("investStatus") String investStatus);
	/**
	 * 根据产品userID查询赞分期产品当前持有金额
	 * @param userId
	 * @return
	 */
	Double zanMyInvestCurrentHoldByUserId(Integer userId);
	
	/**
	 * 根据用户id查询已购买成功的笔数
	 * @param userId
	 * @return
	 */
	Integer countInvestedNum(@Param("userId") Integer userId);
	
	/**
	 * 2017踏春活动投资排行榜前10
	 * @return
	 */
	List<PlayerKillingVO> springInvestRankingList();
	
	/**
	 * 2017踏春活动 用户投资金额
	 * @return
	 */
	Double springUserInvestAmount(@Param("userId") Integer userId);
	
	/**
	 * 2017踏春活动 用户邀请列表
	 * @param userId 
	 * @return
	 */
	List<PlayerKillingVO> springUserInvitedList(@Param("userId") Integer userId);
	
	//===========================存管匹配相关S===================================
	/**
	 * 存管定期，待匹配总金额,排除vip列表
	 * @param vipUserIdList
	 * @param productType auth户type
	 * @param redProType red户type
	 * @param invstAmt 起投金额
	 * @return
	 */
	Double depFixedNormalSumAmountWait4Match(@Param("vipUserIdList")List<Integer> vipUserIdList, 
			@Param("productType")String productType, @Param("redProType")String redProType, @Param("invstAmt")Double invstAmt );
	

	/**
	 * 存管定期，待匹配列表,排除vip列表
	 * @param vipUserIdList
	 * @param productType auth户type
	 * @param redProType red户type
	 * @param invstAmt 起投金额 
	 * @return
	 */
	List<BsSubAccountVO4DepFixedMatch> depFixedNormalWait4MatchList(@Param("vipUserIdList")List<Integer> vipUserIdList, 
			@Param("productType")String productType, @Param("redProType")String redProType, @Param("invstAmt")Double invstAmt );

	/**
	 * 最优匹配记录，排除vip列表
	 * @param vipUserIdList 过滤的VIP理财人
	 * @param productType 站岗户
	 * @param redProType 红包户
	 * @param loanMatchRange 借款金额区间配置
	 * @return
	 */
	List<BsSubAccountVO4DepFixedMatch> depFixedNormalWait4Match(@Param("vipUserIdList") List<Integer> vipUserIdList, @Param("productType") String productType,
																@Param("redProType") String redProType, @Param("loanMatchRange") LoanMatchInvestorRangeVO loanMatchRange,
																@Param("oneLevelAuthFlag") Boolean oneLevelAuthFlag, @Param("twoLevelAuthFlag") Boolean twoLevelAuthFlag,
																@Param("threeLevelAuthFlag") Boolean threeLevelAuthFlag);

	/**
	 * 查询匹配站岗户+红包户可用余额
	 *
	 * @param authAccountId 站岗户ID
	 * @param redAccountId 红包户ID
	 * @return
	 */
	BsSubAccountVO4Match depFixedAvailableBalance4MatchById(@Param("authAccountId") Integer authAccountId, @Param("redAccountId") Integer redAccountId);

	/**
	 * 存管分期，债权匹配时查询可以匹配的小额AUTH户列表
	 * @param propertySymbol
	 * @param productType
	 * @param userIdList
	 * @param interestBeginDate 起息日
	 * @param term 投资期限
	 * @param isSuper yes - 表示是超级用户，no表示普通用户
	 * @param matchLimitAmount 普通理财人债权匹配时低于该金额的不进行债权承接
	 * @return
	 */
	List<BsCanMatch4ZanSubAccountVO> canSmallMatch4ZanListDepStage(@Param("propertySymbol")String propertySymbol, @Param("productType")String productType,
													  @Param("userIdList")List<Integer> userIdList, @Param("interestBeginDate")String interestBeginDate,
													  @Param("term")Integer term, @Param("isSuper") String isSuper, @Param("matchLimitAmount")Double matchLimitAmount);
	
	
	/**
     * 存管分期，债权匹配时查询可以匹配的AUTH户列表
     * @param propertySymbol
     * @param productType
     * @param userIdList
     * @param interestBeginDate 起息日
     * @param term 投资期限
     * @param isSuper yes - 表示是超级用户，no表示普通用户
     * @param matchLimitAmount 普通理财人债权匹配时低于该金额的不进行债权承接
     * @return
     */
    List<BsCanMatch4ZanSubAccountVO> canMatch4ZanListDepStage(@Param("propertySymbol")String propertySymbol, @Param("productType")String productType,
													  @Param("userIdList")List<Integer> userIdList, @Param("interestBeginDate")String interestBeginDate,
													  @Param("term")Integer term, @Param("isSuper") String isSuper, @Param("matchLimitAmount")Double matchLimitAmount);
	
    /**
	 * 存管定期(云贷)，转让时，查询不在退出审核表（状态除去REFU拒绝）中
	 * 起息日在今日之前,当红包金额超过承接本金时，红包金额取承接本金为红包金额
	 * @param amount 最低可用余额（承接人应付本息）
	 * @param inAgentViewConfig 是否是钱报系客户，是传yes,不是传no
	 * @param isSuper yes - 表示是超级用户，no表示理财用户
	 * @param vipIdList
	 * @param principal 承接本金
	 * @param outUserId 出让用户id(需要排除)
	 * @param productType 产品类型
	 * @param redProType red户type
	 * @return
	 */
    BsSubAccountVO4DepFixedMatch query4TransferCommon(@Param("amount") Double amount,
    		@Param("inAgentViewConfig") String inAgentViewConfig,@Param("isSuper") String isSuper,
    		@Param("vipIdList")List<Integer> vipIdList,@Param("principal") Double principal,
    		@Param("outUserId") Integer outUserId, @Param("productType") String productType, @Param("redProType")String redProType);
    
    
    /**
	 * 存管定期（赞时贷），转让时，查询不在退出审核表（状态除去REFU拒绝）中
	 * 起息日在今日之前,当红包金额超过承接本金时，红包金额取承接本金为红包金额
	 * @param amount 最低可用余额（承接人应付本息）
	 * @param inAgentViewConfig 是否是钱报系客户，是传yes,不是传no
	 * @param isSuper yes - 表示是超级用户，no表示理财用户
	 * @param vipIdList
	 * @param principal 承接本金
	 * @param outUserId 出让用户id(需要排除)
	 * @return
	 */
    BsSubAccountVO4DepFixedMatch query4TransferZsd(@Param("amount") Double amount,
    		@Param("inAgentViewConfig") String inAgentViewConfig,@Param("isSuper") String isSuper,
    		@Param("vipIdList")List<Integer> vipIdList,@Param("principal") Double principal,
    		@Param("outUserId") Integer outUserId);
	
	//===========================存管匹配相关E===================================
	
	/**
	 * 存管定期  统计到期退出数据退出
	 * @param vipUserList 
	 * @return
	 */
	List<DepFixedMaturityExitVO> queryDepfixedMaturityExitInfo(@Param("vipUserList") List<Integer> vipUserList);

	/**
	 * 统计云贷自助放款当日可用额度
	 * @return
	 */
	Double dailyMoneyCount();

	/**
	 * 统计云贷自助放款指定日期可用额度
	 * @return
	 */
	Double dailyMoneyCountFixedDay(@Param("PlanDate") String planDate);

    /**
	 * 统计赞时贷当日可用额度
	 * @return
	 */
    Double zsdDailyMoneyCount();
    /**
	 * 统计云贷自助放款当日可用额度
	 * @return
	 */
    Double dailyMoneyCalculate(@Param("PlanDate") String planDate);

	/**
	 * 统计云贷自助放款指定日期可用额度
	 * @return
	 */
	Double dailyMoneyCalculateFixedDay(@Param("PlanDate") String planDate);

	/**
	 * 查询资产方站岗资金当前可用余额
	 * @param productType 站岗户
	 * @param redProType 红包户
	 * @return
	 */
	Double dailyAvailableBalanceMoneyCalculate(@Param("productType")String productType,
										@Param("redProType")String redProType);

	/**
	 * 查询资产方站岗资金当前可用余额
	 * @param productTypes 站岗户列表
	 * @return
	 */
	Double dailyAvailableBalanceMoneyCalculateForSevenDian(@Param("productTypes")List<String> productTypes);

	/**
	 * 查询站岗户次日退出本息总计
	 * @param productType 站岗户
	 * @param redProType 红包户
	 */
	Double dailyQuitAuthMoneyCalculate(@Param("productType")String productType, @Param("redProType")String redProType);

	/**
	 * 每日补息
	 * @return
	 */
	List<DailyDiffVO> dailyDiffMoney();

	/**
	 * 根据用户编号查询结算户
	 * @param userId
	 * @return
	 */
	BsSubAccount selectDEPJSHSubAccountByUserId(Integer userId);
	/**
	 * 查询投资本金
	 * @param subAccountId 子账户ID
	 * @return
	 */
	Double selectTotalPrincipal(@Param("subAccountId") Integer subAccountId);

	/**
	 * 根据userId查询赞分期VIP退出申请-站岗户余额
	 * @param userId
	 * @return
	 */
	Double selectZanAuthAmount(@Param("userId") Integer userId);

	/**
	 * 根据用户查询该用户站岗户-分期产品
	 * @param userId 用户编号唯一确定站岗户
	 * @return 该用户站岗户
	 */
	List<BsSubAccount> selectAuthAccountByUserId(Integer userId);
	
	/**
	 * 统计产品站岗红包金额
	 * @return
	 */
	Double countRedAccBalance();
	
	/**
	 * 统计7贷产品站岗红包金额
	 * @return
	 */
	Double countRed7AccBalance();
	
	/**
	 * 统计产品站岗红包冻结金额
	 * @return
	 */
	Double countRedAccFreezeBalance();
	
	/**
	 * 统计产品站岗红包冻结金额
	 * @return
	 */
	Double countRedAccFreezeBalanceByType(String productType);
	
	/**
	 * 统计产品站岗红包金额
	 * @return
	 */
	Double countRedAccBalanceByType(String productType);
	
	/**
	 * 根据id查询该用户子账户数据-存管产品
	 * @param authAccountId
	 * @return
     */
	BsSubAccount selectAuthAccountById(Integer authAccountId);
	
	/**
	 * 查询赞分期可转债权量
	 * @return
	 */
	List<AvailableClaimVO> availableClaim(@Param("userIds") List<Integer> userIds,
			@Param("matchLimitAmount") Double matchLimitAmount);
	
	/**
	 * 查询赞时贷可转债权量
	 * @return
	 */
	List<AvailableClaimVO> zsdAvailableClaim(@Param("userIds") List<Integer> userIds);
	
	/* ============================  钱报178 APP平台接入 Start ============================ */
	
	/**
	 * 钱报app,持仓余额列表查询条数
	 * @param productId
	 * @param agentId
	 * @param createTimeBegin
	 * @param createTimeEnd
	 * @return
	 */
	Integer CountQbUserPosition(@Param("productId") Integer productId,@Param("agentId") Integer agentId,
			@Param("createTimeBegin") Date createTimeBegin,@Param("createTimeEnd") Date createTimeEnd);
	
	/**
	 * 钱报app,持仓余额列表查询
	 * @param productId
	 * @param agentId
	 * @param createTimeBegin
	 * @param createTimeEnd
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	List<PositionProduct4UserInfo> queryQbUserPositionPageList(@Param("productId") Integer productId,@Param("agentId") Integer agentId,
			@Param("createTimeBegin") Date createTimeBegin,@Param("createTimeEnd") Date createTimeEnd,
			@Param("start")Integer start,@Param("numPerPage")Integer numPerPage);


	/**
	 * 钱报app，查询还款计划列表
	 * @param productId
	 * @param userMobile
	 * @param agentId
	 * @param createTimeBegin
	 * @param createTimeEnd
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	List<RepayPlanInfo> queryQbRepayPlanList(@Param("productId") Integer productId,@Param("userMobile") String userMobile,
			@Param("agentId") Integer agentId,@Param("createTimeBegin") Date createTimeBegin,@Param("createTimeEnd") Date createTimeEnd,
			@Param("start")Integer start,@Param("numPerPage")Integer numPerPage);
	
	/**
	 * 钱报app，查询还款计划列表条数
	 * @param productId
	 * @param userMobile
	 * @param agentId
	 * @param createTimeBegin
	 * @param createTimeEnd
	 * @return
	 */
	Integer countQbRepayPlan(@Param("productId") Integer productId,@Param("userMobile") String userMobile,
			@Param("agentId") Integer agentId,@Param("createTimeBegin") Date createTimeBegin,@Param("createTimeEnd") Date createTimeEnd);

	/**
	 * 钱报app查询会员资金余额记录条数
	 * @param userAccountAry 会员账号集合
	 * @param createTimeBegin
	 * @param createTimeEnd
	 * @return
	 */
	Integer count178QueryBalance(@Param("userAccountAry") List<String> userAccountAry,
								 @Param("createTimeBegin") String createTimeBegin,
								 @Param("createTimeEnd") String createTimeEnd);

	/**
	 * 钱报app分页查询会员资金余额
	 * @param userAccountAry
	 * @param createTimeBegin
	 * @param createTimeEnd
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	List<QueryBalanceInfo> select178QueryBalanceByPage(@Param("userAccountAry") List<String> userAccountAry,
													   @Param("createTimeBegin") String createTimeBegin,
													   @Param("createTimeEnd") String createTimeEnd,
													   @Param("start")Integer start,
													   @Param("numPerPage")Integer numPerPage);

	/* ============================  钱报178 APP平台接入 End ============================ */

	
	
	/**
	 * 赞时贷:根据产品类型查询所有待匹配的总额
	 * (需排除在退出申请表中的投资数据)
	 * @param productType
	 * @return
	 */
	Double balanceWait4Match4ZSD(@Param("productType")String productType);

	/**
	 * 赞时贷:待匹配列表,排除vip列表
	 * 大额匹配： minBalance<=投资金额 ，maxBalance传null
	 * 小额匹配：0<投资金额  <maxBalance，minBalance传null
	 * @param productType
	 * @param vipUserIdList
	 * @param minBalance
	 * @param maxBalance
	 * @return
	 */
	List<BsSubAccountVO4DepFixedMatch> zsdNormalWait4MatchList(@Param("productType") String productType, 
			@Param("vipUserIdList")List<Integer> vipUserIdList,
			@Param("minBalance")Double minBalance, @Param("maxBalance")Double maxBalance);
	
	/**
	 * 赞时贷:待匹配列表,仅vip列表
	 * @param productType
	 * @param vipUserIdList
	 * @return
	 */
	List<BsSubAccountVO4DepFixedMatch> zsdVIPWait4MatchList(@Param("productType") String productType, @Param("vipUserIdList")List<Integer> vipUserIdList);

	/**
	 * 查询首笔投资
	 * @param userId
     */
	BsSubAccount selectFirstInvest(@Param("userId") Integer userId);
	
	/**
	 * 赞分期可转债权查询，匹配限制金额
	 * @return
	 */
	Double getMatchLimitAmount(); 
	
	/**
	 * 统计产品站岗红包金额
	 * @param productType
	 * @return
	 */
	Double sumRedAccBalanceByType(@Param("type") String productType);
	
	/**
	 * 统计周周乐活动日总年化投资额
	 * @param 
	 * @return
	 */
	Double selectWeekhaySumAmount();
	
	/**
	 * 统计周周乐活动日年化投资达到额度的用户列表
	 * @param 
	 * @return 
	 */
	List<BsActivityLuckyDrawVO> selectWeekhayBigInvestors(@Param("investAmount") Double investAmount);
	
	/**
	 * 统计周周乐活动投资的用户列表
	 * @param 
	 * @return 
	 */
	List<BsActivityLuckyDrawVO> selectWeekhayAllInvestors();

	/**
	 * 统计所有产品站岗红包金额
	 * @param productType
	 * @return
	 */
	Double sumRedAccBalancee();
	
	List<InvestAmountVO> selectInvestAmountEachMonth();
	/**
	 * 统计7贷自主放款当日可用额度
	 * @return
	 */
	Double sevenDaiSelfDailyMoneyCount();

	/**
	 * 统计7贷自主放款指定日期可用额度
	 * @return
	 */
	Double sevenDaiSelfDailyMoneyCountFixedDay(@Param("PlanDate") String planDate);

	/**
	 * 2018新年活动年化投资额
	 * @return
	 */
	Double sumAnnualAmount(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("userId") Integer userId);

	/* 2018平台数据添加字段 start */
	/* 2. 累计出借额（平台自成立以来出借的金额总和，仅统计本金） */
	Double sumLoanAmount();

	/* 3. 自成立以来累计借贷金额（平台成立以来出借的金额总和，仅统计本金） */
	Double sumBorrowerAmount();
	/* 4. 自成立以来累计借贷笔数（平台成立以来出借的总笔数） */
	Integer countLoanTimes();

	/* 5. 当前待还借贷金额（借款人未还款的出借金额总和，仅统计本金） */
	Double sumLeftAmount();

	/* 6. 当前待还借贷笔数（借款人未还款的出借总笔数） */
	Integer countLeftAmountTimes();

	/* 9. 累计出借人数（平台成立以来，累计的出借总人数，排除VIP理财人） */
	Integer countLoanUserTimes(@Param("userIdList") List<Integer> userIdList);

	/* 10. 当期出借人数（目前有待回款的出借人数，排除VIP理财人） */
	Integer countCurrentLoanUserTimes(@Param("userIdList") List<Integer> userIdList);

	/* 12.  前十大出借人出借余额占比（按待回款金额排序，前十大出借人出借余额总和/当前待还借贷金额，仅统计本金，排除VIP理财人） */
	Double sumTenLargestLeftAmount(@Param("userIdList") List<Integer> userIdList);

	/* 13. 最大单一出借人出借余额占比（按待回款金额排序，最大单一出借人出借余额总和/当前待还借贷金额，仅统计本金，排除VIP理财人） */
	Double sumLargestLeftAmount(@Param("userIdList") List<Integer> userIdList);

	/* 14. 累计借款人数（平台成立以来，累计的借款人数） */
	Integer countBorrowerUserTimes();

	/* 15. 当期借款人数（目前处于借款状态的借款人数） */
	Integer countCurrentBorrowerUserTimes();

	/* 17. 前十大借款人待还金额占比（按当前借款金额排序，前十大借款人待还金额总和/当前待还借贷金额，仅统计本金） */
	Double sumTenBorrowerLargestLeftAmount();

	/* 18. 最大单一借款人待还金额占比（按当前借款金额排序，最大单一借款人待还金额总和/当前待还借贷金额，仅统计本金） */
	Double sumBorrowerLargestLeftAmount();

	/* 21. 借款人逾期金额（当前对投资人已经处于逾期状态的所有借款的金额总和，仅统计本金） */
	Double sumLateAmount();

	/* 22. 借款人逾期笔数（当前对投资人处于逾期状态的所有借款的笔数之和） */
	Integer countLateAmount();

	/* 23. 借款人逾期90天以上金额（当前对投资人逾期＞90天的借款金额总和，仅统计本金） */
	Double sum90LateAmount();

	/* 24. 借款人逾期90天以上笔数（当前对投资人逾期＞90天的借款笔数之和） */
	Integer count90LateAmount();

	/* 25. 累计代偿金额（平台自成立以来，累计的代偿金额，包括本金和利息） */
	Double sumLateNotAmount();

	/* 26. 累计代偿笔数（平台自成立以来，累计代偿的笔数之和） */
	Integer countLateNotAmount();
	
	/* 27. 本月成交额（元） 本月出借的金额总和，仅统计本金*/
	Double monthBuyAmount(@Param("year")String year,@Param("month") String month);
	
	/* 28. 本月成交人数（人）本月出借的人数总和*/
	Integer monthBuyUserNumber(@Param("year")String year,@Param("month") String month);
	
	/* 29. 本月成交笔数（笔）本月出借的笔数总和*/
	Integer monthBuyNumber(@Param("year")String year,@Param("month") String month);
	
	/* 30. 本月用户收益（元） 本月累计帮用户赚取*/
	Double monthIncomeAmount(@Param("year")String year,@Param("month") String month);
	
	/* 31. 本月借贷金额（元）借款人本月借贷金额总和*/
	Double monthLoanAmount(@Param("year")String year,@Param("month") String month);
	
	/* 32. 本月借贷笔数（笔） 借款人本月借贷的总笔数*/
	Integer monthLoanNumber(@Param("year")String year,@Param("month") String month);
	
	/* 33. 本月各期限计划成交额*/
	List<Platform4ManageProductVO> buyGroupList(@Param("year")String year,@Param("month") String month);
	
	/* 34. 网页版端口占比（%）订单端口，无端口加至pc*/
	Double pcProportion();
	
	/* 35. H5端口占比（%）*/
	Double h5Proportion();
	
	/* 36. 单日最高成交额（元）*/
	Double mostDayBuyAmount();
	
	/* 37. 单笔最高成交额（元）*/
    Double mostOneBuyAmount();
    
    /* 38. 最快满标时间（秒）*/
    Double fastestSecond();
    
    /* 39. 成交次数最多（次）*/
    Double mostBuyTimes();
    
    /* 40. 富豪榜*/
    List<String> richerList();
	
	/* 2018平台数据添加字段 end */

	/* 2018财务管理-财务总账查询-站岗户 start */
	/* 1、云贷站岗户余额 */
	Double selectSumBgwAuthYunBalance();

	/* 2、7贷站岗户余额 */
	Double selectSumBgwAuthSevenBalance();

	/* 3、赞时贷站岗户余额 */
	Double selectSumBgwAuthZsdBalance();

	/* 4、赞分期站岗户余额 */
	Double selectSumBgwAuthZanBalance();
	/* 2018财务管理-财务总账查询-站岗户 end */

	/**
	 * 查询老云贷，老七贷提前赎回的数据进行补签委托协议
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	List<BsUserAgreementSignVO> selectOldYUNOrSevenSignedSupplementInfo(@Param("start") int start, @Param("numPerPage") int numPerPage);
	
	/**
	 * 根据站岗户id查询某产品预计收益（不包括加息券）
	 * @author bianyatian
	 * @param subAccountId
	 * @return
	 */
	Double subAccountInterest(@Param("subAccountId") Integer subAccountId);
	
	/**
	 * 根据用户id查询用户累计年化投资额（排除赞分期）
	 * @author bianyatian
	 * @param userId
	 * @return
	 */
	Double sumYearInvestByUserId(@Param("userId") Integer userId);

	/**
	 * 根据用户id查询用户首次投资产品信息
	 * @author bianyatian
	 * @param userId
	 * @return
	 */
	BsSubAccount firstInvestByUserId(@Param("userId") Integer userId);

	/* 2018财务管理-财务总账查询-新增自由站岗户业务数据 start */
	/**
	 * 1、统计自由站岗产品站岗红包金额
	 * @return
	 */
	Double sumRedFreeAccBalance();

	/**
	 * 2、统计自由站岗户余额
	 * @return
     */
	Double selectSumBgwAuthFreeBalance();
	/* 2018财务管理-财务总账查询-新增自由站岗户业务数据 end */

	/**
	 * 平台存量数据-投资余额本金
	 * @param endTime
	 * @return
	 */
	Double sumFinancesAuthBalance(@Param("endTime") String endTime, @Param("productType") String productType);

	/* 2018财务管理-每日日终账务查询-系统余额快照记录表站岗户金额查询 start */

	// 1、云贷站岗户余额
	SysBalanceDailySnapVO selectSumAuthYunBalance();

	// 2、7贷站岗户余额
	SysBalanceDailySnapVO selectSumAuthSevenBalance();

	// 3、赞时贷站岗户余额
	SysBalanceDailySnapVO selectSumAuthZsdBalance();

	// 4、赞分期站岗户余额
	SysBalanceDailySnapVO selectBgwAuthZanBalance();

	/* 2018财务管理-每日日终账务查询-系统余额快照记录表站岗户金额查询 end */

}
