package com.pinting.business.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pinting.business.model.vo.*;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersExample;
import com.pinting.gateway.hessian.message.dafy.model.InvestmentAmounts;
import com.pinting.gateway.hessian.message.qb178.model.OrderListDataResModel;


public interface BsPayOrdersMapper {
    int countByExample(BsPayOrdersExample example);

    int deleteByExample(BsPayOrdersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsPayOrders record);

    int insertSelective(BsPayOrders record);

    List<BsPayOrders> selectByExample(BsPayOrdersExample example);

    BsPayOrders selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsPayOrders record, @Param("example") BsPayOrdersExample example);

    int updateByExample(@Param("record") BsPayOrders record, @Param("example") BsPayOrdersExample example);

    int updateByPrimaryKeySelective(BsPayOrders record);

	int updateByPrimaryKey(BsPayOrders record);

	/**
	 * 根据渠道、产品代码和日期 统计该渠道该日期区间该产品被成功购买的总金额
	 *
	 * @param map
	 *            参数：channel、productCode、beginDate、endDate
	 * @return 成功则返回该日期区间某理财产品购买额
	 */
	InvestmentAmounts selectInvestmentAmount(Map<String, Object> map);

	/**
	 * 管理台订单查询-超级管理员
	 * @param bsPayOrdersVO
	 * @return
	 */
	ArrayList<BsPayOrdersVO> selectPayOrdersListPageInfo(BsPayOrdersVO bsPayOrdersVO);
	/**
	 * 管理台订单查询-订单跟踪，手机号和姓名全匹配
	 * @param bsPayOrdersVO
	 * @return
	 */
	ArrayList<BsPayOrdersVO> selectPayOrdersListPageInfo4Normal(BsPayOrdersVO bsPayOrdersVO);

	Double sumIncarnateBonus();

	int cardRecordCount(BsPayOrdersVO record);

	List<BsPayOrdersVO> findCardRecordByPage(BsPayOrdersVO record);

	List<BsPayOrders> findBuyBankTypeList();
	/**
	 * 管理台订单查询总条数-超管
	 * @param bsPayOrdersVO
	 * @return
	 */
	int selectCountPayOrders(BsPayOrdersVO bsPayOrdersVO);
	/**
	 * 管理台订单查询总条数-订单跟踪，手机号和姓名全匹配
	 * @param bsPayOrdersVO
	 * @return
	 */
	int selectCountPayOrders4Normal(BsPayOrdersVO bsPayOrdersVO);

	/** 用户提现查询 */
	List<BsPayOrdersVO> selectUserWithdrawalInfo(BsPayOrdersVO bsPayOrdersVO);

	/** 用户提现金额统计 */
	double selectSumUserWithdrawal(BsPayOrdersVO bsPayOrdersVO);

	/** 用户提现记录数 */
	int selectCountUserWithdrawal(BsPayOrdersVO bsPayOrdersVO);

	/** 银行通道支出总额 */
	Double calculateTotal(@Param("transType") String transType, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("bankId") Integer bankId, @Param("userId") Integer userId);

	/** 渠道收入总额 */
	Double calculateChannelTotal(@Param("transType") String transType, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("channel") String channel);

	/** 理财产品购买总额 */
	Double calculateBuyProductTotal(@Param("startTime") String startTime, @Param("endTime") String endTime);

	List<BsPayOrders> selectBsPayOrders(BsPayOrdersVO record);

	int selectUserCountTopUp(BsPayOrdersVO bsPayOrdersVO);

	ArrayList<BsPayOrdersVO> selectUserTopUpList(BsPayOrdersVO bsPayOrdersVO);

	double selectUserSumTopUp(BsPayOrdersVO bsPayOrdersVO);

	/** 订单查询（客服使用） **/
	List<BsPayOrdersVO> payOrdersPage(BsPayOrdersVO record);

	int countPayOrders(BsPayOrdersVO record);

	/** 某时间断内充值成功总金额 */
	Double topUpTotal(@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("userId") Integer userId);

	/**
	 * 查询首页金额信息 昨日充值金额 rechargeAmount 昨日购买金额 buyAmount 当日提现已完成金额
	 * withdrawOverAmount 当日提现处理中金额 withdrawProcessAmount
	 *
	 * @param yesterday
	 * @param channelType
	 * @return
	 */
	Map<String, Object> selectFinanceWithdrawAmount(@Param("yesterday") String yesterday, @Param("today") String today,
			@Param("channelType") String channelType);

	/**
	 * 查询用户首次购买设备信息 return Map<String,Object>
	 */
	Map<String, Object> selectFirstInvestDevice(Map<String, Object> map);

	/** 根据用户查询成功或处理中的订单。排序：成功，处理中；修改时间倒序 */
	List<BsPayOrders> selectBuySuccessPayOrders(@Param("userId") Integer userId);

	/**
	 * 根据订单号查询该笔订单购买产品的基本信息
	 *
	 * @param orderNo
	 *            购买订单号
	 * @return 无信息则返回null
	 */
	BsPayOrderProductVO selectPayOrderProductByOrderNo(String orderNo);

	/**
	 * 用户充值记录
	 *
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<UserRechanageStatisticsVO> userRechangeStatistics(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("position") Integer position, @Param("offset") Integer offset);

	/**
	 * 用户充值记录总数
	 *
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer userRechangeStatisticsCount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime);


	/**
	 * 用户充值总金额
	 *
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double userTotalRechangeAmountStatistics(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime);
	/**
	 * 本金购买-用户购买记录
	 * @param userType
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<CorpusBuyStatisticsVO> corpusBuyStatistics(@Param("userType") String userType,@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("userIdList") String[] userIdList,@Param("position") Integer position, @Param("offset") Integer offset);
	/**
	 * 本金购买-用户购买记录总数
	 * @param userType
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer corpusBuyStatisticsCount(@Param("userType") String userType,@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime,@Param("userIdList") String[] userIdList);
	/**
	 * 本金购买-用户本金购买总金额
	 * @param userType
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	CorpusBuyStatisticsVO corpusBuyTotalAmount(@Param("userType") String userType,@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime,@Param("userIdList") String[] userIdList);


	/**
	 * 查询支付融资客户数据
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param position
	 * @param offset
	 * @return
	 */
	List<PayFinanceStatisticsVO> payFinanceStatistics(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("position") Integer position, @Param("offset") Integer offset);

	/**
	 * 查询云贷/赞时贷支付融资客户数据
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @param partnerBusinessFlag 借款产品标识
	 * @param position
	 * @param offset
	 * @return
	 */
	List<PayFinanceStatisticsVO> payFinanceYunZSDStatistics(@Param("userType") String userType, @Param("mobile") String mobile,
													  @Param("userName") String userName, @Param("startTime") String startTime,
													  @Param("endTime") String endTime, @Param("partnerCode")String partnerCode,
													  @Param("partnerBusinessFlag")String partnerBusinessFlag,
													  @Param("position") Integer position, @Param("offset") Integer offset);


	/**
	 * 查询支付融资客户数据的记录数
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * 
	 * @return
	 */
	Integer payFinanceStatisticsCount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 查询云贷/赞时贷支付融资客户数据的记录数
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @param partnerBusinessFlag 借款产品标识
	 * @return
	 */
	Integer payFinanceYunZSDStatisticsCount(@Param("userType") String userType, @Param("mobile") String mobile,
									  @Param("userName") String userName, @Param("startTime") String startTime,
									  @Param("endTime") String endTime, @Param("partnerCode")String partnerCode,
									  @Param("partnerBusinessFlag")String partnerBusinessFlag);

	/**
	 * 查询支付融资客户金额总计
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	PayFinanceStatisticsVO payFinanceStatisticsTotalAmount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime);


	/**
	 * 计提奖励金
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param position
	 * @param offset
	 * @return
	 */
	List<CalculateRewardsStatisticsVO> calculateRewardsStatistics(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("position") Integer position, @Param("offset") Integer offset);
	/**
	 * 计提奖励金数据条数
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer calculateRewardsStatisticsCount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime);
	/**
	 * 计提奖励金金额总计
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double calculateRewardsStatisticsTotalAmount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime);


	/**
     * 客户支取-用户支取记录
     *
     * @param mobile
     * @param userName
     * @param startTime
     * @param endTime
     * @return
     */
    List<UserDrawBonusStatisticsVO> userDrawBonusStatistics(@Param("customerName") String customerName,
            @Param("startTime") String startTime, @Param("endTime") String endTime,
            @Param("position") Integer position, @Param("offset") Integer offset);

    /**
     * 客户支取-用户支取记录总数
     *
     * @param mobile
     * @param userName
     * @param startTime
     * @param endTime
     * @return
     */
    Integer userDrawBonusStatisticsCount(@Param("customerName") String customerName, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 客户支取-用户支取总金额
     * @param customerName
     * @param startTime
     * @param endTime
     * @return
     */
	Double userDrawBonusStatisticsTotalAmount(@Param("customerName") String customerName, @Param("startTime") String startTime, @Param("endTime") String endTime);


    /**
     * 奖励金发放记录
     * @param mobile
     * @param userName
     * @param startTime
     * @param endTime
     * @param position
     * @param offset
     * @return
     */
	List<GrantRewardsStatisticsVO> grantRewardsStatistics(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("type") String type,@Param("position") Integer position, @Param("offset") Integer offset);
	/**
	 * 奖励金发放记录总数
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer grantRewardsStatisticsCount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime,@Param("type") String type);
	/**
	 * 奖励金发放金额总计
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double grantRewardsStatisticsTotalAmount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime,@Param("type") String type);


    /**
     * 发放本息记录
     * @param customerName
     * @param startTime
     * @param endTime
     * @param position
     * @param offset
     * @return
     */
    List<UserGrantInterestStatisticsVO> userGrantInterestStatistics(@Param("customerName") String customerName,
        @Param("startTime") String startTime, @Param("endTime") String endTime,
        @Param("propertySymbol") String propertySymbol,
        @Param("position") Integer position, @Param("offset") Integer offset);

    /**
     * 发放本息记录总数
     * @param customerName
     * @param startTime
     * @param endTime
     * @return
     */
    Integer userGrantInterestStatisticsCount(@Param("customerName") String customerName,
                                             @Param("startTime") String startTime,
                                             @Param("endTime") String endTime,
                                             @Param("propertySymbol") String propertySymbol);
    /**
     * 发放本息金额合计
     * @param customerName
     * @param startTime
     * @param endTime
     * @return
     */
	UserGrantInterestStatisticsVO userGrantInterestStatisticsTotalAmount(@Param("customerName") String customerName,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("propertySymbol") String propertySymbol);


    /**
     * 融资客户结算
     * @param mobile
     * @param userName
     * @param startTime
     * @param endTime
     * @param position
     * @param offset
     * @return
     */
	List<BalanceFinanceStatisticsVO> balanceFinanceStatistics(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("note") String note,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("position") Integer position, @Param("offset") Integer offset);

	/**
	 * 融资客户结算数据条数
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer balanceFinanceStatisticsCount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("note") String note,
			@Param("startTime") String startTime,
			@Param("endTime") String endTime);
	/**
	 * 融资客户结算金额总计
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	BalanceFinanceStatisticsVO balanceFinanceStatisticsTotalAmount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime);
	
	
    /**
     * 融资客户结算 -赞分期（存管前）
     * @param mobile
     * @param userName
     * @param startTime
     * @param endTime
     * @param position
     * @param offset
     * @return
     */
	List<BalanceFinanceStatisticsVO> balanceFinanceStatisticsZan(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("note") String note,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("position") Integer position, @Param("offset") Integer offset);

	/**
	 * 融资客户结算数据条数 -赞分期（存管前）
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer balanceFinanceStatisticsZanCount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("note") String note,
			@Param("startTime") String startTime,
			@Param("endTime") String endTime);
	/**
	 * 融资客户结算金额总计 -赞分期（存管前）
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	BalanceFinanceStatisticsVO balanceFinanceStatisticsZanTotalAmount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime);
	
	/**
	 * 7贷投资利息线下结算（4%）
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param position
	 * @param offset
	 * @return
	 */
	List<FinanceInterestOffline7DaiVO> financeInterestOffline7Dai(
			@Param("mobile") String mobile, @Param("userName")String userName, @Param("startTime")String startTime,@Param("endTime")  String endTime,
			@Param("position") Integer position,@Param("offset")  Integer offset);
	/**
	 * 7贷投资利息线下结算（4%）列表数量
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer financeInterestOffline7DaiCount(@Param("mobile")String mobile, @Param("userName")String userName,
			@Param("startTime")String startTime,@Param("endTime")  String endTime);
	/**
	 * 7贷投资利息线下结算（4%）总计
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	FinanceInterestOffline7DaiVO financeInterestOffline7DaiTotalAmount(@Param("mobile")String mobile, @Param("userName")String userName,
			@Param("startTime")String startTime,@Param("endTime")  String endTime);

	/**
	 * 财务确认处理查看详情中，根据userId查询用户订单信息
	 * @param userId
	 * @return
	 */
	List<BsPayOrdersVO> selectPayOrdersByUserId(@Param("userId") Integer userId);

	/**
	 * 单日回款到卡金额（包含已回款+回款中+提现中+审核中）
	 * @param userId	用户ID
	 * @return
     */
	Double sumWithdrawUpperLimit(@Param("userId") int userId);

	/**
	 * 当日已提现+目前审核中+目前提现中 的金额
	 * @param userId
	 * @return
	 */
	Double sumWithdrawCheckAmount(@Param("userId") int userId);

	/**
	 * 今天充值成功-今天余额购买
	 * @param userId
	 * @return
	 */
	Double topUpSubBuyBalanceToday(@Param("userId") int userId);



	/**
	 * 赞分期营销代收代付列表数量
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer revenueCollectionRepayCount( @Param("userName")String userName,@Param("mobile")String mobile,
			@Param("type")String type,@Param("startTime")String startTime,@Param("endTime")  String endTime);
	/**
	 * 赞分期营销代收代付
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @param position
	 * @param offset
	 * @return
	 */
	List<RevenueCollectionRepayVO> revenueCollectionRepayList(@Param("userName")String userName,@Param("mobile")String mobile,
			@Param("type")String type,@Param("startTime")String startTime,@Param("endTime")  String endTime,
			@Param("position") Integer position,@Param("offset")  Integer offset);


	/**
	 * 赞分期营销代收代付总金额
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @param revenueType
	 * @return
	 */
	Double revenueCollectionRepaySum(@Param("userName")String userName,@Param("mobile")String mobile,
			@Param("type")String type,@Param("startTime")String startTime,@Param("endTime")  String endTime,
			@Param("revenueType") String revenueType);

	/**
	 * 查询该笔订单并加锁
	 * @param id
	 * @return
     */
	BsPayOrders selectByPKForLock(@Param("id") Integer id);
	
	

	/**
	 * 财务功能-恒丰系统充值-列表
	 * @param record
	 * @return
     */
	List<HFBankPayOrderVO> selectHFBankSysTopUp(HFBankPayOrderVO record);

	/**
	 * 财务功能-恒丰系统充值-统计
	 * @return
     */
	Integer selectHFBankSysTopUpCount();

	/**
	 * 财务功能-恒丰系统提现-列表
	 * @param record
	 * @return
     */
	List<HFBankPayOrderVO> selectHFBankSysWithdraw(HFBankPayOrderVO record);

	/**
	 * 财务功能-恒丰系统提现-统计
	 * @return
     */
	Integer selectHFBankSysWithdrawCount();

	/**
	 * 财务功能-恒丰系统账户划转-列表
	 * @param record
	 * @return
     */
	List<HFBankPayOrderVO> selectHFBankSysAccountTransfer(HFBankPayOrderVO record);

	/**
	 * 财务功能-恒丰系统账户划转-统计
	 * @return
     */
	Integer selectHFBankSysAccountTransferCount();

	/**
	 * 财务功能-云贷砍头息划转-列表
	 * @param record
	 * @return
     */
	List<HFBankHeadFeeTransferVO> selectYunHeadFeeTransfer(HFBankHeadFeeTransferVO record);

	/**
	 * 财务功能-云贷砍头息划转-统计
	 * @return
	 */
	Integer selectYunHeadFeeTransferCount();
	
	/**
	 * 赞分期代偿人充值-列表
	 * @param record
	 * @return
     */
	List<HFBankPayOrderVO> selectZanCompensateList(HFBankPayOrderVO record);

	/**
	 * 赞分期代偿人充值-统计
	 * @return
     */
	Integer selectZanCompensateCount();
	
	/**
	 * 查询所有购买成功但是 红包抵用转账失败的数据
	 * @return
     */
	List<BsPayOrders> selectFailRedPacketTransfer();

	/**
	 * 财务功能-垫付金人工调账-统计
	 * @return
	 */
	Integer selectHFBankAdvanceTransferCount();

	/**
	 * 财务功能-垫付金人工调账-列表
	 * @param record
	 * @return
	 */
	List<HFBankPayOrderVO> selectHFBankAdvanceTransfer(HFBankPayOrderVO record);

	/**
	 * 云贷/赞时贷 营销代收代付列表数量
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @return
	 */
	Integer revenueCollectionRepayYunZSDCount( @Param("userName")String userName,@Param("mobile")String mobile,
			@Param("type")String type,@Param("startTime")String startTime,@Param("endTime")  String endTime,@Param("partnerCode") String partnerCode);
	/**
	 * 云贷/赞时贷 营销代收代付
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @param position
	 * @param offset
	 * @return
	 */
	List<RevenueCollectionRepayVO> revenueCollectionRepayYunZSDList(@Param("userName")String userName,@Param("mobile")String mobile,
			@Param("type")String type,@Param("startTime")String startTime,@Param("endTime")  String endTime,@Param("partnerCode")  String partnerCode,
			@Param("position") Integer position,@Param("offset")  Integer offset);


	/**
	 * 云贷/赞时贷 营销代收代付总金额
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @param revenueType
	 * @param partnerCode
	 * @return
	 */
	Double revenueCollectionRepayYunZSDSum(@Param("userName")String userName,@Param("mobile")String mobile,
			@Param("type")String type,@Param("startTime")String startTime,@Param("endTime")  String endTime,
			@Param("revenueType") String revenueType,@Param("partnerCode") String partnerCode);
	
	/**
	 * 归集户代收代付计数
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer influxCollectionRepayCount(@Param("userName")String userName, @Param("mobile")String mobile,
			@Param("type")String type, @Param("startTime")String startTime, @Param("endTime") String endTime);
	
	/**
	 * 归集户代收代付求和
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @param influxType
	 * @return
	 */
	Double influxCollectionRepaySum(@Param("userName")String userName, @Param("mobile")String mobile,
			@Param("type")String type, @Param("startTime")String startTime, @Param("endTime") String endTime,
			@Param("influxType") String influxType);
	
	/**
	 * 归集户代收代付记录
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @param position
	 * @param offset
	 * @return
	 */
	List<InfluxCollectionRepayVO> influxCollectionRepayList(@Param("userName")String userName, @Param("mobile")String mobile,
			@Param("type")String type, @Param("startTime")String startTime, @Param("endTime") String endTime,
			@Param("position") Integer position, @Param("offset") Integer offset);
	/**
	 * 云贷自主放款未来30天待兑付查询
	 * @param cashDateString
	 * @return
	 */
	List<Cash30YunVO> cash30Yun(@Param("cashDateString") String cashDateString);
	
	/**
	 * 云贷自主放款未来30天待兑付查询--云贷VIP理财人当前持有债权
	 * @return
	 */
	Double debtVipYun();
	
	/**
	 * 云贷自主放款未来30天待兑付查询--云贷VIP理财人当前站岗余额
	 * @return
	 */
	Double standVipYun();
	/*==========================钱报178 APP平台接入S======================================*/

    List<OrderListDataResModel> queryOrderListApp178(@Param("productCode") String productCode, 
        @Param("userAccount") String userAccount, @Param("createTimeBegin") String createTimeBegin,@Param("createTimeEnd") String createTimeEnd,
        @Param("position") Integer position,@Param("offset")  Integer offset);
    
    Integer countOrderListApp178(@Param("productCode") String productCode, 
            @Param("userAccount") String userAccount, @Param("createTimeBegin") String createTimeBegin,@Param("createTimeEnd") String createTimeEnd);
    
    
    /*==========================钱报178 APP平台接入E======================================*/
    
    
    
    /**
	 * 存管-云贷-本金购买统计
	 * @param userType VIP/NORMAL
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @param position
	 * @param offset
	 * @return
	 */
	List<CorpusBuyStatisticsVO> yunZSDCorpusBuyStatistics(@Param("userType") String userType,@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime, 
			@Param("partnerCode")String partnerCode, @Param("position") Integer position, @Param("offset") Integer offset);
    /**
     * 根据时间段查询对应交易类型的总金额
     * @param transType
     * @param beginTime
     * @param endTime
     * @return
     */
    Double sumBalanceByTransType(@Param("transType") String transType,@Param("beginTime") String beginTime,@Param("endTime") String endTime);
/**
	 * 存管-云贷-本金购买统计-记录总数
	 * @param userType VIP/NORMAL
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @return
	 */
	Integer yunZSDCorpusBuyStatisticsCount(@Param("userType") String userType,@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("partnerCode")String partnerCode);
	
	/**
	 * 存管-云贷-本金购买统计-本金购买总金额
	 * @param userType VIP/NORMAL
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @return
	 */
	Double yunZSDCorpusBuyStatisticsSumBalance(@Param("userType") String userType,@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("partnerCode")String partnerCode);

	/**
	 * 恒丰客户支取统计列表
	 * @param userName
	 * @param startTime
	 * @param endTime
     * @return
     */
	List<HfbankCustomerWithdrawalVO> selectHfbankWithdrawalList(@Param("userName") String userName, @Param("startTime") String startTime,
															  @Param("endTime") String endTime, @Param("position") Integer position,
															  @Param("offset") Integer offset);

	/**
	 * 恒丰客户支取统计记录数统计
	 * @param userName
	 * @param startTime
	 * @param endTime
     * @return
     */
	Integer selectHfbankWithdrawalCount(@Param("userName") String userName, @Param("startTime") String startTime,
									  @Param("endTime") String endTime);

	/**
	 * 恒丰客户支取总金额合计
	 * @param userName
	 * @param startTime
	 * @param endTime
     * @return
     */
	Double selectHfbankWithdrawalSumAmount(@Param("userName") String userName, @Param("startTime") String startTime,
										 @Param("endTime") String endTime);

	/**
	 * 恒丰客户支取手续费合计
	 * @param userName
	 * @param startTime
	 * @param endTime
     * @return
     */
	Double selectHfbankWithdrawalSumFee(@Param("userName") String userName, @Param("startTime") String startTime,
									  @Param("endTime") String endTime);
	
	/**
	 * 融资客户结算数据条数
	 * @param mobile
	 * @param userName
	 * @param custType 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<DepBalanceFinanceStatisticsVO> depBalanceFinanceStatisticsCount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("note") String note,
			@Param("custType") String custType, @Param("partnerCode") String partnerCode,
			@Param("partnerBusinessFlag")String partnerBusinessFlag,
			@Param("startTime") String startTime,
			@Param("endTime") String endTime);
	/**
     * 融资客户结算
     * @param mobile
     * @param userName
     * @param custType 
     * @param startTime
     * @param endTime
     * @param position
     * @param offset
     * @return
     */
	List<DepBalanceFinanceStatisticsVO> depBalanceFinanceStatistics(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("note") String note,
			@Param("custType") String custType, @Param("partnerCode") String partnerCode,
			@Param("partnerBusinessFlag")String partnerBusinessFlag,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("position") Integer position, @Param("offset") Integer offset);
	/**
	 * 币港湾-恒丰-宝付对账数据条数
	 * @param mobile
	 * @param userName
	 * @param custType 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer depBaoFooBalanceStatisticsCount(
			@Param("startTime") String startTime,
			@Param("endTime") String endTime);
	/**
     * 币港湾-恒丰-宝付对账数据
     * @param mobile
     * @param userName
     * @param custType 
     * @param startTime
     * @param endTime
     * @param position
     * @param offset
     * @return
     */
	List<DepBaoFooBalanceStatisticsVO> depBaoFooBalanceStatistics(
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("position") Integer position, @Param("offset") Integer offset);

	/**
	 * 宝付客户支取统计列表
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<HfbankCustomerWithdrawalVO> selectBaofooWithdrawalList(@Param("userName") String userName, @Param("startTime") String startTime,
																@Param("endTime") String endTime,@Param("transType") String transType, 
																@Param("position") Integer position,@Param("offset") Integer offset);

	/**
	 * 宝付客户支取统计记录数统计
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer selectBaofooWithdrawalCount(@Param("userName") String userName, @Param("startTime") String startTime,
										@Param("endTime") String endTime,@Param("transType") String transType);

	/**
	 * 宝付客户支取总金额合计
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double selectBaofooWithdrawalSumAmount(@Param("userName") String userName, @Param("startTime") String startTime,
										   @Param("endTime") String endTime,@Param("transType") String transType);

	/**
	 * 宝付客户支取手续费合计
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double selectBaofooWithdrawalSumFee(@Param("userName") String userName, @Param("startTime") String startTime,
										@Param("endTime") String endTime,@Param("transType") String transType);
	/**
	 * 财务统计-出借匹配统计列表-赞分期出借
	 * @param userType
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param userIdList
	 * @param position
	 * @param offset
	 * @return
	 */
	List<CorpusBuyStatisticsVO> selectLendingMatchForZan(@Param("userType") String userType, @Param("mobile") String mobile,
														 @Param("userName") String userName, @Param("startTime") String startTime,
														 @Param("endTime") String endTime, @Param("userIdList") String[] userIdList,
														 @Param("position") Integer position, @Param("offset") Integer offset);

	/**
	 * 财务统计-出借匹配统计记录统计-赞分期出借
	 * @param userType
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param userIdList
	 * @return
	 */
	Integer selectLendingMatchForZanCount(@Param("userType") String userType, @Param("mobile") String mobile,
										  @Param("userName") String userName, @Param("startTime") String startTime,
										  @Param("endTime") String endTime, @Param("userIdList") String[] userIdList);

	/**
	 * 财务统计-出借匹配统计-统计用户本金购买总金额-赞分期出借
	 * @param userType
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param userIdList
	 * @return
	 */
	CorpusBuyStatisticsVO selectLendingMatchForZanTotalAmount(@Param("userType") String userType, @Param("mobile") String mobile,
															  @Param("userName") String userName, @Param("startTime") String startTime,
															  @Param("endTime") String endTime,@Param("userIdList") String[] userIdList);


	/**
	 * 返还投资人手续费明细列表
	 * @param partnerCode
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param position
	 * @param offset
	 * @return
	 */
    List<ReturnFeeToInvestorVO> returnFeeToInvestor(@Param("partnerCode") String partnerCode,
													@Param("mobile") String mobile,
													@Param("userName") String userName,
													@Param("startTime") String startTime,
													@Param("endTime") String endTime,
													@Param("position") Integer position,
													@Param("offset") Integer offset);

	/**
	 * 返还投资人手续费总额
	 * @param partnerCode
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double returnFeeToInvestorTotalAmount(@Param("partnerCode") String partnerCode,
										  @Param("mobile") String mobile,
										  @Param("userName") String userName,
										  @Param("startTime") String startTime,
										  @Param("endTime") String endTime);

	/**
	 * 财务统计-支付融资客户-赞分期出借-列表查询
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param position
	 * @param offset
	 * @return
	 */
	List<PayFinanceStatisticsVO> payFinanceStatisticsForZan(@Param("mobile") String mobile, @Param("userName") String userName,
															@Param("startTime") String startTime, @Param("endTime") String endTime,
															@Param("position") Integer position, @Param("offset") Integer offset);

	/**
	 * 财务统计-支付融资客户-赞分期出借-记录统计
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer payFinanceStatisticsCountForZan(@Param("mobile") String mobile, @Param("userName") String userName,
											@Param("startTime") String startTime, @Param("endTime") String endTime);

	/**
	 * 财务统计-支付融资客户-赞分期出借-金额统计
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	PayFinanceStatisticsVO payFinanceStatisticsTotalAmountForZan(@Param("mobile") String mobile, @Param("userName") String userName,
																 @Param("startTime") String startTime, @Param("endTime") String endTime);


	/**
	 * 返还投资人手续费明细记录数
	 * @param partnerCode
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer returnFeeToInvestorCount(@Param("partnerCode") String partnerCode,
									 @Param("mobile") String mobile,
									 @Param("userName") String userName,
									 @Param("startTime") String startTime,
									 @Param("endTime") String endTime);

    /**
     * 赞分期产品统计-融资客户结算
     * @param mobile
     * @param userName
     * @param startTime
     * @param endTime
     * @param position
     * @param offset
     * @return
     */
	List<BalanceFinanceStatisticsVO> depZanBalanceFinanceStatistics(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("note") String note,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("position") Integer position, @Param("offset") Integer offset);

	/**
	 * 赞分期产品统计-融资客户结算数据条数
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer depZanBalanceFinanceStatisticsCount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("note") String note,
			@Param("startTime") String startTime,
			@Param("endTime") String endTime);

	/**
	 * 赞分期产品统计-融资客户结算金额总计
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	BalanceFinanceStatisticsVO depZanBalanceFinanceStatisticsTotalAmount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime,
			@Param("endTime") String endTime);


	/**
	 * 支付融资客户出借金额总计（云贷，赞时贷）
	 * @param userType
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @param partnerBusinessFlag 借款产品标识
	 * @return
	 */
    Double payFinanceYunZSDTotalAmount(@Param("userType") String userType,
									   @Param("mobile") String mobile,
									   @Param("userName") String userName,
									   @Param("startTime") String startTime,
									   @Param("endTime") String endTime,
									   @Param("partnerCode") String partnerCode,
									   @Param("partnerBusinessFlag")String partnerBusinessFlag);


	/**
	 * 查询订单对应商户号信息
	 * @param payOrderNo
	 * @return
	 */
	LnChannelVO queryBsMerchantInfoOfOrder(@Param("payOrderNo") String payOrderNo);
	/**
	 * 查询宝付对账理财端订单
	 * @return
	 */
	List<BsPayOrders> baoFooCheckAccountOrders();

	/**
	 * 对账完成后， 对应交易类型计数
	 * @param channel
	 * @param transType
	 * @return
	 */
	int countCheckAccountOrders(@Param("channel")String channel, @Param("transType")String transType,
			@Param("status")int status, @Param("checkDate")String checkDate);

	/**
	 * 对账完成后， 对应交易类型统计金额
	 * @param channel
	 * @param transType
	 * @return
	 */
	Double sumCheckAccountOrders(@Param("channel")String channel, @Param("transType")String transType,
			@Param("status")int status, @Param("checkDate")String checkDate);
	
	/**
	 * 管理台 还款日常管理计数
	 * @param record
	 * @return
	 */
	Integer queryGachaCheckDailyCount(DailyCheckGachaVO record);
	/**
	 * 管理台 还款日常管理列表
	 * @param record
	 * @return
	 */
	List<DailyCheckGachaVO> queryGachaCheckDailyInfo(DailyCheckGachaVO record);

	/**
	 * 管理台 还款日常管理还款金额统计
	 * @param record
	 * @return
     */
	Double selectGachaCheckDailySum(DailyCheckGachaVO record);
	
	/**
	 * 七店账单信息同步通知统计数据(理财购买)</br>
	 * &nbsp&nbsp 1、统计七店用户投资购买订单数据</br>
	 * &nbsp&nbsp 2、startTime时间之前两小时到startTime区间内产生的数据</br>
	 * @param startTime 统计开始时间
	 * @param queryStartTime null时，返回startTime时间之前两小时到startTime区间内产生的数，否则返回queryStartTime~startTime区间数据
	 * @return
	 */
	List<OrderInfoSyncVO> orderInfoSyncBuy(@Param("startTime") Date startTime, @Param("queryStartTime") Date queryStartTime);
	/**
	 * 七店账单信息同步通知统计数据(到期回款)</br>
	 * &nbsp&nbsp 1、统计七店用户到期回款订单数据</br>
	 * &nbsp&nbsp 2、startTime时间之前两小时到startTime区间内产生的数据</br>
	 * @param startTime 统计开始时间
	 * @param queryStartTime null时，返回startTime时间之前两小时到startTime区间内产生的数，否则返回queryStartTime~startTime区间数据
	 * @return
	 */
	List<OrderInfoSyncVO> orderInfoSyncReturn(@Param("startTime") Date startTime, @Param("queryStartTime") Date queryStartTime);
	
}