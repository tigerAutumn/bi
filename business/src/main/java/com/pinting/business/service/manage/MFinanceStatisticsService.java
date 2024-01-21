package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.vo.*;

public interface MFinanceStatisticsService {

	/**
	 * 用户充值记录
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<UserRechanageStatisticsVO> userRechangeStatistics(String userName, String mobile, String startTime,
			String endTime, Integer page, Integer offset);
	
	/**
	 * 用户充值记录总数
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer userRechangeStatisticsCount(String userName, String mobile, String startTime,
			String endTime);
	/**
	 * 用户充值总金额
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double userTotalRechangeAmountStatistics(String userName, String mobile, String startTime,
			String endTime);
	
	
	
	/**
	 *  本金购买-查询用户购买记录
	 * @param userName 用户名
	 * @param mobile 用户手机
	 * @param startTime 起始时间
	 * @param endTime 结束时间
	 * @param page 
	 * @param offset
	 * @return
	 */
	public List<CorpusBuyStatisticsVO> corpusBuyStatistics(String userType,String userName,
			String mobile, String startTime, String endTime, Integer page,
			Integer offset);
	/**
	 *  本金购买-查询用户购买记录总数
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer corpusBuyStatisticsCount(String userType,String userName, String mobile,
			String startTime, String endTime);
	
	/**
	 * 本金购买-统计用户本金购买总金额
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public CorpusBuyStatisticsVO corpusBuyTotalAmount(String userType,String userName,
			String mobile, String startTime, String endTime);
	/**
	 * 支付融资客户数据
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
	public List<PayFinanceStatisticsVO> payFinanceStatistics(String userName,
			String mobile, String startTime, String endTime, Integer page,
			Integer offset);
	/**
	 * 支付融资客户数据总条数
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer payFinanceStatisticsCount(String userName, String mobile,
			String startTime, String endTime);
	
	
	/**
	 * 支付融资客户金额总计
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public PayFinanceStatisticsVO payFinanceStatisticsTotalAmount(
			String userName, String mobile, String startTime, String endTime);
	
	/**
	 * 计提奖励金数据
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
	public List<CalculateRewardsStatisticsVO> calculateRewardsStatistics(
			String userName, String mobile, String startTime, String endTime, Integer page,
			Integer offset);
	/**
	 * 计提奖励金数据条数
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer calculateRewardsStatisticsCount(String userName,
			String mobile, String startTime, String endTime);
	
	/**
	 * 计提奖励金金额总计
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double calculateRewardsStatisticsTotalAmount(String userName,
			String mobile, String startTime, String endTime);
	
	/**
     * 客户支取-用户支取记录
     * @param customerName
     * @param mobile
     * @param startTime
     * @param endTime
     * @param page
     * @param offset
     * @return
     */
    public List<UserDrawBonusStatisticsVO> userDrawBonusStatistics(String customerName, String mobile, String startTime,
        String endTime, Integer page, Integer offset);
    
    /**
     * 客户支取-用户支取记录总数
     * @param userName
     * @param mobile
     * @param startTime
     * @param endTime
     * @return
     */
    public Integer userDrawBonusStatisticsCount(String customerName, String mobile, String startTime,
            String endTime);
    
    /**
     * 客户支取-用户支取总金额
     * @param customerName
     * @param mobile
     * @param startTime
     * @param endTime
     * @return
     */
	public Double userDrawBonusStatisticsTotalAmount(String customerName, String mobile, String startTime,
            String endTime);

    
	/**
	 * 用户红包实发记录
	 * @param customerName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
	public List<UserRedPublishStatisticsVO> userRedPacketPublishStatistics(String customerName, String mobile, String startTime,
        String endTime, Integer page, Integer offset);
	
	/**
     * 用户红包实发记录总数
     * @param userName
     * @param mobile
     * @param startTime
     * @param endTime
     * @return
     */
    public Integer userRedPacketPublishStatisticsCount(String customerName, String mobile, String startTime,
            String endTime);
    /**
     * 红包发放--红包实际使用总金额
     * @param customerName
     * @param mobile
     * @param startTime
     * @param endTime
     * @return
     */
	public Double userRedPacketPublishStatisticsTotalAmount(String customerName, String mobile, String startTime,
            String endTime);
    
    /**
     * 奖励金发放记录
     * @param userName
     * @param mobile
     * @param startTime
     * @param endTime
     * @param page
     * @param offset
     * @return
     */
	public List<GrantRewardsStatisticsVO> grantRewardsStatistics(
			String userName, String mobile, String startTime, String endTime, String type, Integer page, Integer offset);
	/**
	 * 奖励金发放记录条数
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer grantRewardsStatisticsCount(String userName, String mobile,
			String startTime, String endTime, String type);
	
	/**
	 * 奖励金发放金额总计
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double grantRewardsStatisticsTotalAmount(String userName,
			String mobile, String startTime, String endTime, String type);
    
    /**
     * 发放本息记录
     * @param customerName
     * @param startTime
     * @param endTime
     * @param position
     * @param offset
     * @return
     */
    public List<UserGrantInterestStatisticsVO> userGrantInterestStatistics(String customerName, String startTime, String endTime, 
        String propertySymbol,Integer page, Integer offset);
    
    /**
     * 发放本息记录总数
     * @param customerName
     * @param startTime
     * @param endTime
     * @return
     */
    public Integer userGrantInterestStatisticsCount(String customerName, String startTime, String endTime,String propertySymbol);
    
    /**
     * 发放本息金额合计
     * @param customerName
     * @param startTime
     * @param endTime
     * @return
     */
	public UserGrantInterestStatisticsVO userGrantInterestStatisticsTotalAmount(
			String customerName, String startTime, String endTime,String propertySymbol);
    /**
     * 融资客户结算
     * @param userName
     * @param mobile
     * @param note
     * @param startTime
     * @param endTime
     * @param page
     * @param offset
     * @return
     */
	public List<BalanceFinanceStatisticsVO> balanceFinanceStatistics(String partnerCode,String userName,
			String mobile, String note, String startTime, String endTime, Integer page,
			Integer offset);
	/**
	 * 融资客户结算数据条数
	 * @param userName
	 * @param mobile
	 * @param note
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer balanceFinanceStatisticsCount(String partnerCode,String userName,
			String mobile, String note, String startTime, String endTime);
	/**
	 * 融资客户结算金额总计
	 * @param userName
	 * @param mobile
	 * @param note
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public BalanceFinanceStatisticsVO balanceFinanceStatisticsTotalAmount(
			String partnerCode,String userName, String mobile, String note, String startTime, String endTime, Integer count);
	/**
	 * 7贷投资利息线下结算（4%）
	 * 查询列表信息
	 * @param userName 用户名字
	 * @param mobile 手机号
	 * @param startTime 开始时间
	 * @param endTime  结束时间
	 * @param page
	 * @param offset
	 * @return
	 */
	public List<FinanceInterestOffline7DaiVO> financeInterestOffline7Dai(
			String userName, String mobile, String startTime, String endTime,
			Integer page, Integer offset);
	/**
	 * 7贷投资利息线下结算（4%）
	 * 查询列表信息总数
	 * @param userName  用户名字
	 * @param mobile  手机号
	 * @param startTime  开始时间
	 * @param endTime  结束时间
	 * @return
	 */
	public Integer financeInterestOffline7DaiCount(String userName,
			String mobile, String startTime, String endTime);
	/**
	 * 7贷投资利息线下结算（4%）
	 * @param userName  用户名字
	 * @param mobile  手机号
	 * @param startTime  开始时间
	 * @param endTime  结束时间
	 * @return
	 */
	public FinanceInterestOffline7DaiVO financeInterestOffline7DaiTotalAmount(
			String userName, String mobile, String startTime, String endTime);
	
	
	
	
	/**
	 * 赞分期营销代收代付
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
	public List<RevenueCollectionRepayVO> revenueCollectionRepayList(String userName,
			String mobile, String type,String startTime, String endTime, Integer page,
			Integer offset);
	/**
	 * 赞分期营销代收代付
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer revenueCollectionRepayCount(String userName, String mobile,String type,
			String startTime, String endTime);
	
	/**
	 * 赞分期营销代收代付
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double revenueCollectionRepaySum(String userName, String mobile,String type,
			String startTime, String endTime,String revenueType);


	/**
	 * 管理台保证金代收代付查询-赞分期
	 * @param queryVo
	 * @return
	 */
	public MDepositDsDfResRecordVO mDepositDsDfRecord(MDepositDsDfQueryVO queryVo);
	
	
	/**
	 * 管理台保证金代收代付查询-赞时贷
	 * @param queryVo
	 * @return
	 */
	public MDepositDsDfResRecordVO depositDsDfRecord4ZSD(MDepositDsDfQueryVO queryVo);
	
	/**
	 * 云贷/赞时贷营销代收代付
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @param partnerCode
	 * @return
	 */
	public List<RevenueCollectionRepayVO> revenueCollectionRepayYunZSDList(String userName,
			String mobile, String type,String startTime, String endTime, String partnerCode,Integer page,
			Integer offset);
	/**
	 * 云贷/赞时贷营销代收代付
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer revenueCollectionRepayYunZSDCount(String userName, String mobile,String type,
			String startTime, String endTime, String partnerCode);
	
	/**
	 * 云贷/赞时贷营销代收代付
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double revenueCollectionRepayYunZSDSum(String userName, String mobile,String type,
			String startTime, String endTime,String revenueType, String partnerCode);
	
	/**
	 * 归集户代收代付计数
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer influxCollectionRepayCount(String userName, String mobile, String type,
			String startTime, String endTime);
	
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
	public Double influxCollectionRepaySum(String userName, String mobile, String type,
			String startTime, String endTime, String influxType);
	
	/**
	 * 归集户代收代付记录
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
	public List<InfluxCollectionRepayVO> influxCollectionRepayList(String userName,
			String mobile, String type, String startTime, String endTime, Integer page, Integer offset);
	/**
	 * 云贷自主放款未来30天待兑付查询
	 * @param valueOf
	 * @param valueOf2
	 * @return
	 */
	public List<Cash30YunVO> cash30Yun(Integer page, Integer offset);
	/**
	 * 云贷VIP当前持有债权
	 * @return
	 */
	public Double debtVipYun();
	/**
	 * 云贷VIP当前站岗资金
	 * @return
	 */
	public Double standVipYun();
	/**
	 * （云贷、赞时贷）砍头息代收代付
	 * @param partnerCode
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
	List<HeadFeeCollectPayVO> headFeeCollectPayList(String partnerCode, String userName, String mobile, String type, String startTime, String endTime, Integer page, Integer offset);

	/**
	 * （云贷、赞时贷）砍头息代收代付计数
	 * @param partnerCode
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer headFeeCollectPayCount(String partnerCode, String userName, String mobile, String type, String startTime, String endTime);

	/**
	 * （云贷、赞时贷）砍头息代收代付总额
	 * @param partnerCode
	 * @param userName
	 * @param mobile
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public HeadFeeCollectPayVO headFeeCollectPayTotalAmount(String partnerCode, String userName, String mobile, String type, String startTime, String endTime);

	/**
	 * 融资客户结算数据条数
	 * @param userName
	 * @param mobile
	 * @param note
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<DepBalanceFinanceStatisticsVO> depBalanceFinanceStatisticsCount(String userName,
			String mobile, String note, String custType, String partnerCode, String partnerBusinessFlag, String startTime, String endTime);
	/**
     * 融资客户结算
     * @param userName
     * @param mobile
     * @param note
     * @param startTime
     * @param endTime
     * @param page
     * @param offset
     * @return
     */
	public List<DepBalanceFinanceStatisticsVO> depBalanceFinanceStatistics(String userName,
			String mobile, String note, String custType, String partnerCode, String partnerBusinessFlag, String startTime, String endTime, Integer page,
			Integer offset);

	/**
	 * 财务统计-出借匹配统计列表-赞分期出借
	 * @param userType
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
	public List<CorpusBuyStatisticsVO> queryLendingMatchForZan(String userType,String userName,
															   String mobile, String startTime,
															   String endTime, Integer page,
															   Integer offset);

	/**
	 * 财务统计-出借匹配统计记录统计-赞分期出借
	 * @param userType
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer queryLendingMatchForZanCount(String userType,String userName,
												String mobile, String startTime,
												String endTime);

	/**
	 * 财务统计-出借匹配统计-统计用户本金购买总金额-赞分期出借
	 * @param userType
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public CorpusBuyStatisticsVO queryLendingMatchForZanTotalAmount(String userType,String userName,
																	String mobile, String startTime,
																	String endTime);


	/**
	 * 返还投资人手续费明细列表
	 * @param partnerCode
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
    List<ReturnFeeToInvestorVO> returnFeeToInvestor(String partnerCode, String userName, String mobile, String startTime, String endTime, Integer page, Integer offset);

	/**
	 * 返还投资人手续费记录数
	 * @param partnerCode
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    Integer returnFeeToInvestorCount(String partnerCode, String userName, String mobile, String startTime, String endTime);

	/**
	 * 返还投资人手续费总计
	 * @param partnerCode
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double returnFeeToInvestorTotalAmount(String partnerCode, String userName, String mobile, String startTime, String endTime);

    /**
	 * 财务统计-支付融资客户-赞分期出借-列表
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param page
     * @param offset
     * @return
     */
	public List<PayFinanceStatisticsVO> payFinanceStatisticsForZan(String userName, String mobile,
																   String startTime, String endTime,
																   Integer page, Integer offset);

	/**
	 * 财务统计-支付融资客户-赞分期出借-记录统计
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
     * @return
     */
	public Integer payFinanceStatisticsCountForZan(String userName, String mobile,
												   String startTime, String endTime);

	/**
	 * 财务统计-支付融资客户-赞分期出借-金额统计
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
     * @return
     */
	public PayFinanceStatisticsVO payFinanceStatisticsTotalAmountForZan(String userName, String mobile,
																		String startTime, String endTime);

	/**
	 * 投资到期统计
	 * @param type
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
    List<InvestExpireVO> investExpire(String partnerCode, String type, String userName, String mobile, String startTime, String endTime, Integer page, Integer offset);

	/**
	 * 投资到期记录数
	 * @param type
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer investExpireCount(String partnerCode, String type, String userName, String mobile, String startTime, String endTime);

	/**
	 * 投资到期总计
	 * @param partnerCode
	 * @param type
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double investExpireTotalAmount(String partnerCode, String type, String userName, String mobile, String startTime, String endTime);

	/**
     * 赞分期产品统计-融资客户结算
     * @param userName
     * @param mobile
     * @param note
     * @param startTime
     * @param endTime
     * @param page
     * @param offset
     * @return
     */
	public List<BalanceFinanceStatisticsVO> depZanBalanceFinanceStatistics(String userName,
			String mobile, String note, String startTime, String endTime, Integer page,
			Integer offset);
	/**
	 * 赞分期产品统计-融资客户结算数据条数
	 * @param userName
	 * @param mobile
	 * @param note
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer depZanBalanceFinanceStatisticsCount(String userName,
			String mobile, String note, String startTime, String endTime);
	/**
	 * 赞分期产品统计-融资客户结算金额总计
	 * @param userName
	 * @param mobile
	 * @param note
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public BalanceFinanceStatisticsVO depZanBalanceFinanceStatisticsTotalAmount(
			String userName, String mobile, String note, String startTime, String endTime, Integer count);

	/**
	 * 币港湾-宝付-恒丰对账数据条数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer depBaoFooBalanceStatisticsCount(String startTime, String endTime);
	/**
     * 币港湾-宝付-恒丰对账数据 
     * @param startTime
     * @param endTime
     * @param page
     * @param offset
     * @return
     */
	public List<DepBaoFooBalanceStatisticsVO> depBaoFooBalanceStatistics( String startTime, String endTime, Integer page,
			Integer offset);

	/**
	 * 对账 -可疑记录列表
	 * @param orderNo 订单号
	 * @param startTime 对账开始时间
	 * @param endTime 对账结束时间
	 * @param page 分页参数
	 * @param offset 分页参数
	 * @return
	 */
	public List<BsPayCheckErrorVO> findCheckErrorList(String orderNo, String partnerCode, String businessType, String paymentChannelId, 
			String channel, String startTime, String endTime, Integer page, Integer offset);

	/**
	 * 对账 -可疑记录列表计数
	 * @param orderNo 订单号
	 * @param startTime 对账开始时间
	 * @param endTime 对账结束时间
	 * @return
	 */
	public int countCheckError(String orderNo, String partnerCode, String businessType, String paymentChannelId,
			String channel, String startTime, String endTime);
	
	/**
	 * 对账 -恒丰对账结果列表
	 * @param checkDate 对账日期
	 * @return
	 */
	public List<BsSysDailyCheckGachaVO> findHfbankDailyCheckGachaList(String checkDate);

	/**
	 * 对账 -对账结果列表
	 * @param checkDate 对账日期
	 * @return
	 */
	public List<BsSysDailyCheckGachaVO> findSysDailyCheckGachaList(String merchantNo, String checkDate);
	
	/**
	 * 对账 -异常订单处理
	 * @param handleType 异常处理类型
	 * @param orderNo 订单号
	 * @return
	 */
	String checkGachaErrorHandle(String handleType, String orderNo);

}
