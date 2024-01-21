package com.pinting.business.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pinting.business.model.vo.*;
import com.pinting.gateway.hessian.message.qb178.model.QueryUserDetailsDataResModel;
import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserExample;

public interface BsUserMapper {
    int countByExample(BsUserExample example);
    
    int countByUserinfo(BsUserAssetVO example);
    int deleteByExample(BsUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUser record);

    int insertSelective(BsUser record);

    List<BsUser> selectByExample(BsUserExample example);
    
    List<BsUserAssetVO> selectAllUser(BsUser bsUser);

    BsUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUser record, @Param("example") BsUserExample example);

    int updateByExample(@Param("record") BsUser record, @Param("example") BsUserExample example);

    int updateByPrimaryKeySelective(BsUser record);

    int updateByPrimaryKey(BsUser record);
	
	/**
	 * 用户资产信息查询
	 * @param userId
	 * @return
	 */
	BsUserAssetVO selectUserAssetByUserId(Integer userId);
	/**
	 * 查询所有被推荐人，以及各自当前有效投资产品资金总额
	 * @return
	 */
	List<BsUserBonusVO> selectRecommendedUserAndInvestAmount();
	/**
	 * 根据用户id增量修改累计奖励金收益、当前奖励金、可提现金额
	 * @param record
	 * @return
	 */
	int updateBonusById(BsUser record);
	
	/**
	 * 根据用户id增量修改累计奖励金收益、当前奖励金、可提现金额、当前收益、累计收益
	 * @param record
	 * @return
	 */
	int updateUserAmountInfoById(BsUser record);

	
	/**
	 * 根据查询条件查询购买信息
	 * @param statisticsVo
	 * @return 返回查询到的购买信息
	 * @author HuangMengJian
	 */
	List<BsStatisticsVO> selectUserBuyMessageList(BsStatisticsVO statisticsVo);

	/**
	 * 计算用户购买信息的条数
	 * @param statisticsVo
	 * @return
	 */
	int countUserBuyMessageList(BsStatisticsVO statisticsVo);

	/**
	 * 计算当前用户购买金额总和
	 * @param statisticsVo
	 * @return
	 */
	double selectUserBuySumBalance(BsStatisticsVO statisticsVo);

	/**
	 * 查询到期未支付警示
	 * @param statisticsVo
	 * @return
	 */
	ArrayList<BsStatisticsVO> selectInvestMatureWarmList(
			BsStatisticsVO statisticsVo);

	/**
	 * 到期后还未支付的数量
	 * @param statisticsVo	
	 * @return
	 */
	int countInvestMatureWarmList(BsStatisticsVO statisticsVo);
	
	/**
	 * 到期后还未支付跑批告警
	 * @param statisticsVo	
	 * @return
	 */
	int countInvestMatureWarm(BsStatisticsVO statisticsVo);
	
	/**
	 * 查询当前用户数量，管理台设置条件查询
	 * @param bsUser
	 * @return
	 */
	int countAllUserCount(BsUserAssetVO bsUser);

	/**
	 * 查询卡绑定超时的用户信息
	 * @param createTime 已超时时间
	 * @return
	 */
	List<BsUser> selectUserForBindCardTimeout(Date createTime);
	
	/**
	 * 计算可提现金额
	 * @return
	 */
	Double sumCanWithdraw();

	/**
	 * 根据userId查询下线
	 * @param data
	 * @return
	 */
	List<BsUser> selectSubUserByUserId(Map<String, Object> data);

	BsUserVO selectByDafyUserId(String customerId);
	
	/**
	 * 根据用户id增量修改当前投资收益字段
	 * @param record
	 * @return
	 */
	int updateCurrInterestById(BsUser record);
	
	/**
	 * 根据渠道查询用户信息
	 * @param record
	 * @return
	 */
	List<AgentUserInfoVO> selectAgentUserList(  @Param("record") BsUser record, 
												@Param("investFlag") String investFlag, 
												@Param("sregistTime")String sregistTime, 
												@Param("eregistTime")String eregistTime);
	/**
	 * 根据渠道查询用户总数
	 * @param record
	 * @return
	 */
	int countAgentUserList(	@Param("record") BsUser record, 
							@Param("investFlag") String investFlag, 
							@Param("sregistTime")String sregistTime, 
							@Param("eregistTime")String eregistTime);
	
	/**
	 * 根据条件查询投资回款数据
	 * @param statisticsVo
	 * @return
	 */
	
	List<BsStatisticsVO> selectUserInvestmentPaybackList(BsStatisticsVO statisticsVo);
	
	/**
	 * 计算当前用户投资回款金额总和
	 * @param statisticsVo
	 * @return
	 */
	double selectUserSumInvestment(BsStatisticsVO statisticsVo);
	
	/**
	 * 计算用户投资回款的条数
	 * @param statisticsVo
	 * @return
	 */
	int countUserInvestmentList(BsStatisticsVO statisticsVo);
	
	
	/**
	 * 统计用户的总收入
	 * @return 总收入
	 */
	double countUserIncome();
	
	/**
	 * 
	 * @Title: selectByPKForLock 
	 * @Description: 根据主键锁记录
	 * @param userId
	 * @return
	 * @throws
	 */
	BsUser selectByPKForLock(Integer userId);
	
	
	/**
	 * 查询当前登陆渠道用户数量，管理台设置条件查询
	 * @param bsUser
	 * @return
	 */
	int countAgentUserCount(BsUserAssetVO bsUser);
	List<BsUserAssetVO> selectAgentUser(BsUser bsUser);
	
	/**
	 * 计算当前登入管理员渠道下用户购买信息
	 * @param statisticsVo
	 * @return
	 */
	int countAgentBuyMessage(BsStatisticsVO statisticsVo);
	List<BsStatisticsVO> findAgentUserMessageList(BsStatisticsVO statisticsVo);
	
	/**
	 * 计算当前登入渠道用户购买金额总和
	 * @param statisticsVo
	 * @return
	 */
	double selectAgentSumBalance(BsStatisticsVO statisticsVo);
	
	/**
	 * 统计注册用户总数
	 * @param map 查询条件
	 * @return map
	 */
	Map<String,Object> countResgisterUser(Map<String,Object> map);
	
	/**
	 * 公众号按时间统计注册用户总数
	 * @param map 查询条件
	 * @return map
	 */
	int countResgisterUserByTime(Date time);
	
	/**
	 * 分页查询:客户信息查询
	 * @param bsUser
	 * @return
	 */
	List<BsUserVO> bsUserPage(BsUser bsUser);
	int countBsUser(BsUser bsUser);
	
	/**
	 * 查询用户基础信息
	 * @return List<BsBaseUserVO>
	 */
	List<BsBaseUserVO> selectBaseUserInfo();
	
	/**
	 * 通过手机号查询用户
	 * @param bsUser
	 * @return
	 */
	BsUser mobileSelect(BsUser bsUser);
	
	/**
	 * 分页查询用户运营信息
	 * @param recode
	 * @return
	 */
	List<MUserOperateVO> selectUserOperatePage(MUserOperateVO recode);
	
	/**
	 * 查询用户运营信息的总数
	 * @param recode
	 * @return
	 */
	int countUserOperate(MUserOperateVO recode);
	
	/**
	 * 查询用户总数
	 * @param map
	 * @return
	 */
	int countBsUserByMap(Map<String, Object> map);
	
	/**
     * 
     * @Title: countRecommendUser 
     * @Description: 通过条件查询某用户的推荐人数
     * @param map
     * @return
     * @throws
     */
    int countRecommendUser(Map<String,Object> map);
	
	/**
	 * 注册用户查询列表  
	 * @param bsUser
	 * @return
	 */
	List<BsUserAssetVO> selectRegisterUser(BsUserAssetVO bsUser);
	
	/**
	 * 注册用户统计
	 * @param bsUser
	 * @return
	 */
	int selectRegisterUserCount(BsUserAssetVO bsUser);
	
	/**
	 * 用户综合查询 
	 * @param record
	 * @return
	 */
	List<BsUserAssetVO> selectUserComplex(BsUserAssetVO record);
	
	/**
	 * 用户综合查询 数量统计
	 * @param record
	 * @return
	 */
	int countUserComplexCount(BsUserAssetVO record);
	
	/**
	 * 用户注册查询 数量统计
	 * @param record
	 * @return
	 */
	int countUserRecordCount(BsRecordListVo record);
	
	/**
	 * 用户综合查询 回访记录查询
	 * @param record
	 * @return
	 */
	List<BsRecordListVo> selectUserRecord(BsRecordListVo record);
	
	/**
	 * 用户综合查询 回访记录新增
	 * @param record
	 * @return
	 */
	void addUserRecord(BsRecordListVo record);
	
	/**
	 * 用户标签管理列表
	 * @param bsUser
	 * @return
	 */
	List<BsUserAssetVO> selectAllUserTag(BsUser bsUser);
	
	/**
	 * 用户标签管理统计
	 * @param bsUser
	 * @return
	 */
	int countAllUserTagCount(BsUserAssetVO bsUser);
	
	/**
	 * user_id新增标签查询列表
	 * @param bsUser
	 * @return
	 */
	List<BsUserAssetVO> selectUserIdTag(BsUser bsUser);
	
	/**
	 * user_id新增标签查询统计
	 * @param bsUser
	 * @return
	 */
	int countUserIdTag(BsUserAssetVO bsUser);
	
	/**
	 * 希财用户信息统计
	 * @param startdate 开始时间(用户注册时间),为空则不限制(格式为 2015-1-20)
	 * @param enddate 结束时间(用户注册时间),为空则不限制(格式为 2015-1-20)
	 * @param page 页码,默认为1
	 * @param pagesize 返回记录条数,默认为10
	 * @param agentId 希财渠道
	 * @return
	 */
	 List<UserInfoXiCaiVO> selectXiCaiUserList(@Param("startdate") String startdate, @Param("enddate") String enddate, 
				@Param("page") Integer page, @Param("pagesize") Integer pagesize, @Param("agentId") Integer agentId);
	
	/**
	 * 希财用户数据统计
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	int selectXiCaiUserCount(@Param("startdate") String startdate, @Param("enddate") String enddate, @Param("agentId") Integer agentId);
	
	/**
	 * 希财用户投资信息统计
	 * @param startdate
	 * @param enddate
	 * @param page
	 * @param pagesize
	 * @return
	 */
	List<InvestInfoXiCaiVO> selectXiCaiInvestInfo(@Param("startdate") String startdate, @Param("enddate") String enddate, 
			@Param("page") Integer page, @Param("pagesize") Integer pagesize, @Param("agentId") Integer agentId);
	
	/**
	 * 希财用户投资产品数量统计
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	int selectXiCaiInvestCount(@Param("startdate") String startdate, @Param("enddate") String enddate, @Param("agentId") Integer agentId);
	
	/**
	 * 
	 * @Title: selectAllUserForUserId 
	 * @Description: 用户标签查询--按查询条件查询用户，得到userId列表
	 * @param bsUser
	 * @return
	 * @throws
	 */
	List<BsUser> selectAllUserForUserId(BsUser bsUser);
	
	/**
	 * 
	 * @Title: selectUsersByIdsOrMobiles 
	 * @Description: 根据userId或者mobile查询用户
	 * @param map
	 * @return List<BsUser>
	 */
	List<BsUser> selectUsersByIdsOrMobiles(Map<String, Object> map);
	
	/**
	 * 用户复投查询
	 * @param record
	 * @return
	 */
	List<BsUserAssetVO> selectUserComplexVote(BsUserAssetVO record);
	
	/**
	 * 用户复投统计
	 * @param record
	 * @return
	 */
	int selectUserComplexVoteCount(BsUserAssetVO record);

    /**
     * @param req
     * @return
     */
    List<BsUser> selectBaseUserGrid(@Param("userName") String userName, 
        @Param("mobile") String mobile, 
        @Param("idCard") String idCard, 
        @Param("start") Integer start, 
        @Param("numPerPage") Integer numPerPage);

	/**
	 * 查询用户归属信息
	 * @param req
	 * @return
     */
	AscriptionChangeUserInfoVO selectOwnershipGrid(@Param("record") AscriptionChangeUserInfoVO req);

	AscriptionChangeDetailVO selectOwnershipDetail(@Param("userId")Integer userId);

	AscriptionChangeDetailVO selectResultsDetail(@Param("userId")Integer userId,@Param("resultsTime")Date resultsTime);

	/**
	 * 用户利息回款查询
	 * @param record
	 * @return
     */
	List<InterestRepaymentVO> selectUserInterestRepayment(InterestRepaymentVO record);

	/**
	 * 用户利息回款统计
	 * @param record
	 * @return
     */
	int selectUserInterestRepaymentCount(InterestRepaymentVO record);

	Double selectAmountTrans(@Param("userId") Integer userId);
	
	/**
	 * 财务确认处理查看详情中，根据userId查询用户基本信息
	 * @param userId
	 * @return
	 */
	BsUserAssetVO selectUserById(@Param("userId") Integer userId);

	/**
	 * 财务确认处理查看详情中，根据userId查询用户基本信息查询绑卡表中离系统时间最近的一条记录
	 * @param userId
	 * @return
     */
	BsUserAssetVO selectRecentUserById(@Param("userId") Integer userId);
	
	
	/**
	 * 根据查询条件查询委托计划投资购买信息
	 * @param UserBuyMessageEntrustVO
	 * @return 委托计划投资购买信息
	 */
	List<UserBuyMessageEntrustVO> selectUserBuyMessageEntrustList(UserBuyMessageEntrustVO userBuyMessageEntrustVO);
	
	
	/**
	 * 根据查询条件查询委托计划投资购买条数
	 * @param UserBuyMessageEntrustVO
	 * @return
	 */
	Integer countUserBuyMessageEntrustList(UserBuyMessageEntrustVO userBuyMessageEntrustVO);
	
	
	/**
	 * 根据查询条件查询委托计划投资购买总金额
	 * @param UserBuyMessageEntrustVO
	 * @return
	 */
	Double sumUserBuyMessageEntrustList(UserBuyMessageEntrustVO userBuyMessageEntrustVO);

	/**
	 * 根据查询条件查询购买信息(ZAN)
	 * @param statisticsVo
	 * @return 返回查询到的购买信息
     */
	List<BsStatisticsVO> selectUserBuyMessageListForZan(BsStatisticsVO statisticsVo);

	/**
	 * 计算用户购买信息的条数（ZAN）
	 * @param statisticsVo
	 * @return
	 */
	int countUserBuyMessageListForZan(BsStatisticsVO statisticsVo);

	/**
	 * 计算当前用户购买金额总和（ZAN）
	 * @param statisticsVo
	 * @return
     */
	double selectUserBuySumBalanceForZan(BsStatisticsVO statisticsVo);

	/**
	 * 计算每一个用户的出借受让金额
	 * @return
     */
	List<Map<String, Object>> selectAmountTransGroup();
	/**
	 * 查询云贷自主放款债权受让人信息
	 * @return
	 */
	@Deprecated
	BsUser selectYunDaiSelfCreditor();

	/**
	 * 查询恒丰银行待激活信息
	 * @param userId	用户ID
	 * @return
     */
	WaiteActivateInfoVO selectWaiteActivateInfo(Integer userId);

	/**
	 * 根据查询条件查询购买信息（YUN_DAI_SELF）-存管
	 * @param statisticsVo
	 * @return 返回查询到的购买信息
	 */
	List<BsStatisticsVO> selectUserBuyMessageListForDep(BsStatisticsDepVO statisticsVo);

	/**
	 * 计算用户购买信息的条数（YUN_DAI_SELF）-存管
	 * @param statisticsVo
	 * @return
	 */
	int countUserBuyMessageListForDep(BsStatisticsDepVO statisticsVo);

	/**
	 * 计算当前用户购买金额总和（YUN_DAI_SELF）-存管 匹配成功的
	 * @param statisticsVo
	 * @return
	 */
	Double selectUserBuySumBalanceForDep(BsStatisticsDepVO statisticsVo);

	
	/**
	 * 根据查询条件 投资购买查询（存管后）
	 * @param statisticsVo
	 * @return 返回查询到的购买信息
	 */
	List<BsStatisticsVO> selectInvestmentBuyMessageListForDep(BsStatisticsDepVO statisticsVo);

	/**
	 * 投资购买查询（存管后）  计数
	 * @param statisticsVo
	 * @return
	 */
	int countInvestmentBuyMessageListForDep(BsStatisticsDepVO statisticsVo);
	
	/**
	 * 投资购买查询（存管后） 统计购买总金额
	 * @param statisticsVo
	 * @return
	 */
	Double selectInvestmentBuySumBalanceForDep(BsStatisticsDepVO statisticsVo);
	
	
	/**
	 * 计算当前用户购买金额总和（YUN_DAI_SELF,ZSD）-存管
	 * @param buyBeginTime
	 * @param buyEndTime
	 * @param propertySymbol
	 * @param productType
	 * @return
	 */
	Double selectUserTotalBalanceForDep(@Param("buyBeginTime") Date buyBeginTime,
										@Param("buyEndTime") Date buyEndTime,
										@Param("propertySymbol") String propertySymbol,
										@Param("productType") String productType);
	
	/**
	 * 钱报信息服务费查询计数
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	int countQbDepServiceFee(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 钱报信息服务费查询列表
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	List<DepServiceFeeVO> queryQbDepServiceFeeList(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 杭商信息服务费查询计数
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	int countHsDepServiceFee(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 杭商钱报178信息服务费查询计数
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	int countHsDep178ServiceFee(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 杭商信息服务费查询列表
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	List<DepServiceFeeVO> queryHsDepServiceFeeList(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 杭商信息服务费查询钱报178列表
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	List<DepServiceFeeVO> queryHsDepServiceFee178List(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 杭商信息服务费查询总列表
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	List<DepServiceFeeVO> queryHsDepServiceFeeAllList(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 品听信息服务费查询计数
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	int countPtDepServiceFee(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 品听信息服务费查询列表
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	List<DepServiceFeeVO> queryPtDepServiceFeeList(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 品听统计购买总金额
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	Double sumPtBuyAmount(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 统计购买总金额
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	Double sumBuyAmount(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 统计购买178钱报总金额
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	Double sumBuy178Amount(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 统计信息服务费
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	Double sumDepServiceFee(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 统计杭商信息服务费
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	Double sumHsDepServiceFee(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 统计杭商钱报178信息服务费
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	Double sumHsDep178ServiceFee(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 统计购买总金额
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	Double sumQbBuyAmount(DepServiceFeeSelectVO depServiceFeeSelectVO);
	
	/**
	 * 统计信息服务费
	 * @param depServiceFeeSelectVO
	 * @return
	 */
	Double sumQbDepServiceFee(DepServiceFeeSelectVO depServiceFeeSelectVO);
	

	/* ============================  钱报178 APP平台接入 Start ============================ */

	/**
	 * 钱报app统计会员详情记录条数
	 * @param userAccount 用户手机号
	 * @param createTimeBegin
	 * @param createTimeEnd
	 * @return
	 */
	Integer count178UserDetails(@Param("userAccount") String userAccount,
								@Param("createTimeBegin") String createTimeBegin,
								@Param("createTimeEnd") String createTimeEnd);

	/**
	 * 钱报app分页查询会员详情
	 * @param userAccount 用户手机号
	 * @param createTimeBegin
	 * @param createTimeEnd
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	List<QueryUserDetailsDataResModel> select178UserDetailsByPage(@Param("userAccount") String userAccount,
																  @Param("createTimeBegin") String createTimeBegin,
																  @Param("createTimeEnd") String createTimeEnd,
																  @Param("start")Integer start,
																  @Param("numPerPage")Integer numPerPage);

	/* ============================  钱报178 APP平台接入 End ============================ */

	// 恒丰用户信息更新（申请列表查询）
	List<UpdateHFUserApplyVO> selectUpdateHFUserInfo(@Param("userName") String userName,
													 @Param("mobile") String mobile,
													 @Param("cardMobile") String cardMobile,
													 @Param("startTime") String startTime,
													 @Param("endTime") String endTime,
													 @Param("mUser") String mUser,
													 @Param("start") Integer start,
													 @Param("numPerPage") Integer numPerPage);

	int countUpdateHFUserInfo(@Param("userName") String userName,
												@Param("mobile") String mobile,
										  	    @Param("cardMobile") String cardMobile,
							  				  	@Param("startTime") String startTime,
												@Param("endTime") String endTime,
												@Param("mUser") String mUser);

	// 恒丰用户信息更新（审核列表查询）
	List<UpdateHFUserApplyVO> selectUpdateHFUserApplyInfo(@Param("userName") String userName,
													 @Param("mobile") String mobile,
													 @Param("cardMobile") String cardMobile,
													 @Param("startTime") String startTime,
													 @Param("endTime") String endTime,
													 @Param("mUser") String mUser,
													 @Param("start") Integer start,
													 @Param("numPerPage") Integer numPerPage);

	int countUpdateHFUserApplyInfo(@Param("userName") String userName,
									 @Param("mobile") String mobile,
									 @Param("cardMobile") String cardMobile,
									 @Param("startTime") String startTime,
									 @Param("endTime") String endTime,
									 @Param("mUser") String mUser);

	/**
	 * 查询7贷自主放款债权受让人信息-7贷代偿协议数据准备
	 * @return
	 */
	@Deprecated
	BsUser select7DaiSelfCreditor();

	/**
	 * 发手动红包筛选用户-查询用户运营信息的总数
	 * @param recode
	 * @return
	 */
	int countUserOperateManualPacket(MUserOperateVO recode);
	/**
	 * 发手动红包筛选用户-查询用户运营信息
	 * @param recode
	 * @return
	 */
	List<MUserOperateVO> selectUserOperateManualPacketPage(MUserOperateVO recode);

	int modifyUserAmountIncrement(BsUser record);

	/**
	 * 分页查询生日当天没有获得加息券的用户
	 * @param serialNo		加息券序列号
	 * @param birthday		生日
	 * @param start			开始页（0）
	 * @param numPerPage	每页显示条数
     */
	List<BsUser> selectHaveNoTicketUserList(@Param("serialNo") String serialNo, @Param("birthday") String birthday, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

	/**
	 * 根据用户ID集合，分页查询展示用户信息
	 * @param userIds
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	List<BsUser> findUserByIds(@Param("userIds") List<Integer> userIds, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

	/**
	 * 查询一段时间范围内，过生日的用户数量
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int findCountBirthdayByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

	/**
	 * 七店用户信息同步通知统计数据(注册新增)</br>
	 * &nbsp&nbsp 1、统计七店新增注册用户数据</br>
	 * &nbsp&nbsp 2、startTime时间之前两小时到startTime区间内产生的数据</br>
	 * @param startTime 统计开始时间
	 * @param queryStartTime null时，返回startTime时间之前两小时到startTime区间内产生的数，否则返回queryStartTime~startTime区间数据
	 * @return
	 */
	List<CustomerInfoSyncVO> customerInfoSyncRegister(@Param("startTime") Date startTime,  @Param("queryStartTime") Date queryStartTime);

	/**
	 * 七店用户信息同步通知统计数据(绑卡实名新增)</br>
	 * &nbsp&nbsp 1、统计七店新增绑卡实名用户数据</br>
	 * &nbsp&nbsp 2、startTime时间之前两小时到startTime区间内产生的数据</br>
	 * @param startTime 统计开始时间
	 * @param queryStartTime null时，返回startTime时间之前两小时到startTime区间内产生的数，否则返回queryStartTime~startTime区间数据
	 * @return
	 */
	List<CustomerInfoSyncVO> customerInfoSyncRealName(@Param("startTime") Date startTime,  @Param("queryStartTime") Date queryStartTime);

	/**
	 * 七店用户信息同步通知统计数据(登录)</br>
	 * &nbsp&nbsp 1、统计七店最近登录用户数据</br>
	 * &nbsp&nbsp 2、startTime时间之前两小时到startTime区间内产生的数据</br>
	 * @param startTime 统计开始时间
	 * @param queryStartTime null时，返回startTime时间之前两小时到startTime区间内产生的数，否则返回queryStartTime~startTime区间数据
	 * @return
	 */
	List<CustomerInfoSyncVO> customerInfoSyncLogin(@Param("startTime") Date startTime,  @Param("queryStartTime") Date queryStartTime);
	
	/**
	 * 管理台更换用户安全卡计数
	 * @param record
	 * @return
	 */
	int countUserChangeBindCardInfo(BsChangeBindCardVO record);
	
	/**
	 * 管理台更换用户安全卡列表
	 * @param record
	 * @return
	 */
	List<BsChangeBindCardVO> selectUserChangeBindCardInfo(BsChangeBindCardVO record);

	/**
	 * 渠道用户查询（秦皇岛）
	 * @param bsUser
	 * @return
	 */
	List<BsQhdUserAgentVO> selectQhdAgentUserList(BsQhdUserAgentVO bsUser);
	
	/**
	 * 查询当前登陆渠道用户数量，管理台设置条件查询
	 * @param bsUser
	 * @return
	 */
	int countQhdAgentUser(BsQhdUserAgentVO bsUser);
	
	/**
	 * 渠道用户投资查询计数（秦皇岛）
	 * @param statisticsVo
	 * @return
	 */
	int countQhdAgentBuyMessage(BsQhdStatisticsInvestVO statisticsVo);
	
	/**
	 * 渠道用户投资查询列表（秦皇岛）
	 * @param statisticsVo
	 * @return
	 */
	List<BsQhdStatisticsInvestVO> findQhdAgentUserMessageList(BsQhdStatisticsInvestVO statisticsVo);
	
	/**
	 * 渠道用户投资投资金额总和（秦皇岛）
	 * @param statisticsVo
	 * @return
	 */
	Double findQhdAgentUserSumBalance(BsQhdStatisticsInvestVO statisticsVo);

	/**
	 * 查找身份证号符合对应日期的用户
	 * 例如时间05月21日，身份证号对应月日是0521的用户
	 * @param idCard
	 * @return
	 */
	List<BsUser> findBirthdayBenefitUserList(@Param("birthday") String birthday, @Param("serialNo") String serialNo);
	
}