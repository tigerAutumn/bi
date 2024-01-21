package com.pinting.business.service.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_BuyMessageDepListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_BuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_BuyMessageDepListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_BuyMessageListQuery;
import com.pinting.business.model.BsCashSchedule30;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsStatistics;
import com.pinting.business.model.vo.AgentUserVo;
import com.pinting.business.model.vo.BsQhdStatisticsInvestVO;
import com.pinting.business.model.vo.BsStatisticsVO;
import com.pinting.business.model.vo.DepCash30VO;

public interface MStatisticsService {

	/**
	 * 根据类型查询统计结果
	 * @param type
	 * @return 时间和值的列表
	 */
	public List<BsStatistics> findMStatisticsByType(String type);
	
	
	/**
	 * 查询当前购买的信息的条数
	 * @param orderNo       订单号
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param investBeginTime 结算起始日期
	 * @param investEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param productCode   产品类型code
	 * @param agentName     渠道名称
	 * @param startRate     开始利率
	 * @param endRate       结束利率
	 * @param agentIds		渠道Id
	 * @param nonAgentId    非渠道Id
	 * @return 当前购买信息的条数，
	 */
	public int CountBuyMessage(String orderNo,Double beginAmount, Double endAmount,
			Date beginTime, Date endTime,Date investBeginTime, Date investEndTime, String bindBankCard,
			String buyBankCard, String idCard, Integer accountStatus,
			String mobile, String userName, Integer term,String productCode,String agentName,
			String buyBankType,Integer agentId,Double rate,Double startRate,Double endRate,
			String agentIds,String nonAgentId,String propertySymbol);

	/**
	 * 查询当前购买的信息的条数（包含赞分期）
	 * @param orderNo       订单号
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param investBeginTime 结算起始日期
	 * @param investEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param productCode   产品类型code
	 * @param agentName     渠道名称
	 * @param startRate     开始利率
	 * @param endRate       结束利率
	 * @param agentIds		渠道Id
	 * @param nonAgentId    非渠道Id
	 * @return 当前购买信息的条数，
	 */
	public int countBuyMessageForZan(String orderNo,Double beginAmount, Double endAmount,
							   Date beginTime, Date endTime,Date investBeginTime, Date investEndTime, String bindBankCard,
							   String buyBankCard, String idCard, Integer accountStatus,
							   String mobile, String userName, Integer term,String productCode,String agentName,
							   String buyBankType,Integer agentId,Double rate,Double startRate,Double endRate,
							   String agentIds,String nonAgentId,String propertySymbol);
	
	/**
	 * 查询购买信息列表
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param investBeginTime 结算起始日期
	 * @param investEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param productCode   产品类型code
	 * @param agentName     渠道名称
	 * @param pageNum		当前页
	 * @param numPerPage	每页的页数
	 * @param startRate     开始利率
	 * @param endRate       结束利率
	 * @param agentIds		渠道Id
	 * @param nonAgentId    非渠道Id
	 * @return 返回购买信息的列表，
	 */
	public List<BsStatisticsVO> findUserBuyMessageList(String orderNo,Double beginAmount,
			Double endAmount, Date beginTime, Date endTime,Date investBeginTime, Date investEndTime,
			String bindBankCard, String buyBankCard, String idCard,String productCode,String agentName,
			Integer accountStatus, String mobile, String userName, Integer term,int pageNum,int numPerPage,
			String orderDirection,String orderField,String buyBankType,Integer agentId,Double rate,
			Double startRate,Double endRate,String agentIds,String nonAgentId,String propertySymbol);

	/**
	 * 查询购买信息列表
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param investBeginTime 结算起始日期
	 * @param investEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param productCode   产品类型code
	 * @param agentName     渠道名称
	 * @param pageNum		当前页
	 * @param numPerPage	每页的页数
	 * @param startRate     开始利率
	 * @param endRate       结束利率
	 * @param agentIds		渠道Id
	 * @param nonAgentId    非渠道Id
	 * @return 返回购买信息的列表
     */
	List<BsStatisticsVO> findUserBuyMessageListForZan(String orderNo,Double beginAmount,
												Double endAmount, Date beginTime, Date endTime,Date investBeginTime, Date investEndTime,
												String bindBankCard, String buyBankCard, String idCard,String productCode,String agentName,
												Integer accountStatus, String mobile, String userName, Integer term,int pageNum,int numPerPage,
												String orderDirection,String orderField,String buyBankType,Integer agentId,Double rate,
												Double startRate,Double endRate,String agentIds,String nonAgentId,String propertySymbol);

	/**
	 * 计算当前的交易总和（排除了赞分期）
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param investBeginTime 结算起始日期
	 * @param investEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param startRate     开始利率
	 * @param endRate       结束利率
	 * @param agentIds		渠道Id
	 * @param nonAgentId    非渠道Id
	 * @return 返回购买信息的列表，
	 */
	public double findUserBuySumBalance(Double beginAmount,
			Double endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
			String bindBankCard, String buyBankCard, String idCard,Integer accountStatus, String mobile, 
			String userName, Integer term,String ProductCode,String buyBankType,Integer agentId,Double rate,
			Double startRate,Double endRate,String agentIds,String nonAgentId,String propertySymbol);

	/**
	 * 计算当前的交易总和（排除了赞分期）
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param investBeginTime 结算起始日期
	 * @param investEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param startRate     开始利率
	 * @param endRate       结束利率
	 * @param agentIds		渠道Id
	 * @param nonAgentId    非渠道Id
	 * @return 返回购买信息的列表，
	 */
	double findUserBuySumBalanceForZan(Double beginAmount,
									   Double endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
									   String bindBankCard, String buyBankCard, String idCard,Integer accountStatus, String mobile,
									   String userName, Integer term,String ProductCode,String buyBankType,Integer agentId,Double rate,
									   Double startRate,Double endRate,String agentIds,String nonAgentId,String propertySymbol);


	/**
	 * 批量插入统计表数据
	 * @param valueList
	 */
	public void insertValueList(ArrayList<BsStatistics> valueList);


	/**
	 * 查询未来365天内的投资收益
	 * @param today 当天数时间
	 * @param future30 未来365天时间
	 * @return
	 */
	public ArrayList<BsCashSchedule30> selectBsCashScheduleList(Date today,
			Date future30);

	/**
	 * 删除当天以及当天以后的数据
	 */
	public boolean deleteBsCashSchedule();


	/**
	 * 批量插入未来三十天内的投资收益
	 * @param valueList
	 * @return
	 */
	public void insertCashScheduleList(ArrayList<BsCashSchedule30> valueList);
	
	/**
	 * 查询产品列表（全部）
	 * @return
	 */
	public List<BsProduct> findProductList();
	
	/**
	 * 投资回款
	 * 
	 * @param beginAmount 起始金额
	 * @param endAmount 结束金额
	 * @param beginTime 起始时间
	 * @param endTime 结束时间
	 * @param bindBankCard 回款银行卡号
	 * @param bindBankName 回款银行卡类型
	 * @param settleAccountsBeginTime 结算起始时间
	 * @param settleAccountsEndTime 结算结束时间
	 * @param accountStatus 产品状态
	 * @param orderNo 订单
	 * @param productId 产品购买类别
	 * @param agentName 渠道来源名称
	 * @param beginSettlementAmount 起始结算金额
	 * @param endSettlementAmount 结束结算金额
	 * @param idCard 身份证
	 * @param mobile 手机号码
	 * @param userName 用户名
	 * @param pageNum 当前页
	 * @param numPerPage 记录数
	 * @param orderDirection 排序
	 * @param orderField
	 * @param agentId 渠道编号
	 * @param term 期限
	 * @param rate 利率
	 * @param buyBankType 购买银行类别
	 * @return 返回投资回款列表
	 */
	public List<BsStatisticsVO> findUserInvestment(Double beginAmount, Double endAmount, 
			Date beginTime, Date endTime, String bindBankCard, String bindBankName, Date settleAccountsBeginTime, 
			Date settleAccountsEndTime, Integer accountStatus, String orderNo, String productId, String agentName, 
			Double beginSettlementAmount, Double endSettlementAmount, String idCard, String mobile, String userName, 
			int pageNum,int numPerPage,String orderDirection,String orderField, Integer orderStatus, Integer agentId,Integer term,String rate, String buyBankType);
	
	
	/**
	 * 计算当前的投资回款总额
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param investBeginTime 结算起始日期
	 * @param investEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param bindBankName 回款银行卡类型
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param beginSettlementAmount 起始结算金额
	 * @param endSettlementAmount 结束结算金额
	 * @param orderNo 订单
	 * @param agentId 渠道编号
	 * @param term 期限
	 * @param rate 利率
	 * @param buyBankType 购买银行类别
	 * @return 返回投资回款总额
	 */
	public double selectUserSumInvestment(Double beginAmount, 
			Double endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
			String bindBankCard, String buyBankCard, String bindBankName, String idCard,
			Integer accountStatus, String mobile, String userName, Integer term,String productId, 
			Double beginSettlementAmount, Double endSettlementAmount, String orderNo, Integer agentId,String rate, String buyBankType);
	
	/**
	 * 计算当前的投资回款条数
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param investBeginTime 结算起始日期
	 * @param investEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param bindBankName 回款银行卡类型
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param beginSettlementAmount 起始结算金额
	 * @param endSettlementAmount 结束结算金额
	 * @param orderNo 订单
	 * @param agentId 渠道编号
	 * @param term 期限
	 * @param rate 利率
	 * @param buyBankType 购买银行类别
	 * @return 返回投资回款条数
	 */
	public int selectCountSumInvestment(Double beginAmount,
			Double endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
			String bindBankCard, String buyBankCard, String bindBankName, String idCard,
			Integer accountStatus, String mobile, String userName, Integer term,String productId, 
			Double beginSettlementAmount, Double endSettlementAmount, String orderNo, Integer agentId,String rate, String buyBankType);
	
	/**
	 * 查询购买信息列表
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param investBeginTime 结算起始日期
	 * @param investEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param pageNum		当前页
	 * @param numPerPage	每页的页数
	 * @return 返回购买信息的列表，
	 */
	public List<BsStatisticsVO> findAgentUserMessageList(String orderNo,Double beginAmount,
			Double endAmount, Date beginTime, Date endTime,Date investBeginTime, Date investEndTime,
			String bindBankCard, String buyBankCard, String idCard,String productCode,
			Integer accountStatus, String mobile, String userName, Integer term,
			int pageNum,int numPerPage,String orderDirection,String orderField,
			Integer agentId,String productName, Date interestBeginTime,
			Date interestEndTime, String distributionChannel, Integer terminalType);

	/**
	 * 查询购买信息列表 钱报系统用户
	 * @param orderNo
	 * @param beginAmount
	 * @param endAmount
	 * @param beginTime
	 * @param endTime
	 * @param investBeginTime
	 * @param investEndTime
	 * @param bindBankCard
	 * @param buyBankCard
	 * @param idCard
	 * @param productCode
	 * @param accountStatus
	 * @param mobile
	 * @param userName
	 * @param term
	 * @param pageNum
	 * @param numPerPage
	 * @param orderDirection
	 * @param orderField
	 * @param distributionChannel 分销渠道
     * @param productName
     * @param interestBeginTime
     * @param interestEndTime
     * @return
     */
	public List<BsStatisticsVO> findAgentUserMessageListAgent(String orderNo,Double beginAmount,
			  Double endAmount, Date beginTime, Date endTime,Date investBeginTime, Date investEndTime,
			  String bindBankCard, String buyBankCard, String idCard,String productCode,
			  Integer accountStatus, String mobile, String userName, Integer term,
			  int pageNum,int numPerPage,String orderDirection,String orderField,
			  String distributionChannel,String productName, Date interestBeginTime, Date interestEndTime, Integer terminalType);
	
	
	/**
	 * 查询当前购买的信息的条数
	 * @param orderNo       订单号
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param investBeginTime 结算起始日期
	 * @param investEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @return 当前购买信息的条数，
	 */
	public int countAgentBuyMessage(String orderNo,Double beginAmount, Double endAmount,
			Date beginTime, Date endTime,Date investBeginTime, Date investEndTime, String bindBankCard,
			String buyBankCard, String idCard, Integer accountStatus,
			String mobile, String userName, Integer term,String productCode,Integer agentId,String productName,
			Date interestBeginTime, Date interestEndTime);

	/**
	 * 查询当前购买的信息的条数 钱报系统用户
	 * @param orderNo
	 * @param beginAmount
	 * @param endAmount
	 * @param beginTime
	 * @param endTime
	 * @param investBeginTime
	 * @param investEndTime
	 * @param bindBankCard
	 * @param buyBankCard
	 * @param idCard
	 * @param accountStatus
	 * @param mobile
	 * @param userName
	 * @param term
	 * @param productCode
	 * @param distributionChannel 分销渠道
	 * @param productName
     * @param interestBeginTime
     * @param interestEndTime
     * @return
     */
	public int countAgentBuyMessageAgent(String orderNo,Double beginAmount, Double endAmount,
			Date beginTime, Date endTime,Date investBeginTime, Date investEndTime, String bindBankCard,
			String buyBankCard, String idCard, Integer accountStatus,
			String mobile, String userName, Integer term,String productCode,String distributionChannel,String productName,
			Date interestBeginTime, Date interestEndTime, Integer terminalType);
	
	/**
	 * 计算当前的交易总和
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param investBeginTime 结算起始日期
	 * @param investEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @return 返回购买信息的列表，
	 */
	public double findAgentUserSumBalance(Double beginAmount,
			Double endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
			String bindBankCard, String buyBankCard, String idCard,
			Integer accountStatus, String mobile, String userName, 
			Integer term,String ProductCode,Integer agentId,String productName, 
			Date interestBeginTime, Date interestEndTime);

	/**
	 * 计算当前的交易总和 钱报系统用户
	 * @param beginAmount
	 * @param endAmount
	 * @param beginTime
	 * @param endTime
	 * @param settleAccountsBeginTime
	 * @param settleAccountsEndTime
	 * @param bindBankCard
	 * @param buyBankCard
	 * @param idCard
	 * @param accountStatus
	 * @param mobile
	 * @param userName
	 * @param term
	 * @param ProductCode
	 * @param distributionChannel 分销渠道
	 * @param productName
     * @param interestBeginTime
     * @param interestEndTime
     * @return
     */
	public double findAgentUserSumBalanceAgent(Double beginAmount,
		   Double endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
		   String bindBankCard, String buyBankCard, String idCard,
		   Integer accountStatus, String mobile, String userName,
		   Integer term,String ProductCode,String distributionChannel,String productName,
		   Date interestBeginTime, Date interestEndTime, Integer terminalType);


	/**
	 * 赞分期投资购买查询
	 * @param req
	 * @param res
     */
	void buyMessageListQuery(ReqMsg_Statistics_BuyMessageListQuery req,
							 ResMsg_Statistics_BuyMessageListQuery res);

	/**
	 * 存管后-出借匹配查询
	 * @param req
	 * @param res
     */
	void depBuyMessageListQuery(ReqMsg_Statistics_BuyMessageDepListQuery req,
								ResMsg_Statistics_BuyMessageDepListQuery res);
	
	/******************投资购买查询-存管后*******************/
	/**
	 * 投资购买查询-存管后
	 * @param req
	 * @param res
     */
	void depInvestmentBuyMessageListQuery(ReqMsg_Statistics_BuyMessageDepListQuery req,
								ResMsg_Statistics_BuyMessageDepListQuery res);
	/**
	 * 投资购买查询存管后 计数
	 * @param mobile
	 * @param userName
	 * @param orderNo
	 * @param beginTime
	 * @param endTime
	 * @param settleAccountsBeginTime
	 * @param settleAccountsEndTime
	 * @param accountStatus
	 * @param agentName
	 * @param agentId
	 * @param sAgentIds
	 * @param nonAgentId
	 * @param propertySymbol
	 * @return
	 */
	public int countInvestmentBuyMessageForDep(String mobile, String userName, String orderNo, String beginTime, String endTime, 
			String settleAccountsBeginTime, String settleAccountsEndTime,
			Integer accountStatus, String agentName, Integer agentId, 
			String sAgentIds, String nonAgentId, String propertySymbol);
	
	/**
	 * 投资购买查询存管后 记录列表
	 * @param mobile
	 * @param userName
	 * @param orderNo
	 * @param beginTime
	 * @param endTime
	 * @param settleAccountsBeginTime
	 * @param settleAccountsEndTime
	 * @param accountStatus
	 * @param agentName
	 * @param agentId
	 * @param sAgentIds
	 * @param nonAgentId
	 * @param propertySymbol
	 * @return
	 */
	List<BsStatisticsVO> findInvestmentBuyMessageListForDep(String mobile, String userName, String orderNo, String beginTime, String endTime, 
			String settleAccountsBeginTime, String settleAccountsEndTime,
			Integer accountStatus, String agentName, Integer agentId, 
			String sAgentIds, String nonAgentId, String propertySymbol, int pageNum,int numPerPage);
	
	/**
	 * 投资购买查询存管后 统计购买总金额
	 * @param mobile
	 * @param userName
	 * @param orderNo
	 * @param beginTime
	 * @param endTime
	 * @param settleAccountsBeginTime
	 * @param settleAccountsEndTime
	 * @param accountStatus
	 * @param agentName
	 * @param agentId
	 * @param sAgentIds
	 * @param nonAgentId
	 * @param propertySymbol
	 * @return
	 */
	double findInvestmentBuySumBalanceForDep(String mobile, String userName, String orderNo, String beginTime, String endTime, 
			String settleAccountsBeginTime, String settleAccountsEndTime,
			Integer accountStatus, String agentName, Integer agentId, 
			String sAgentIds, String nonAgentId, String propertySymbol);

	/******************投资购买查询-存管后*******************/
	
	/**
	 * 查询当前购买的信息的条数（云贷存管）
	 * @param orderNo       订单号
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param settleAccountsBeginTime 结算起始日期
	 * @param settleAccountsEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param productCode   产品类型code
	 * @param agentName     渠道名称
	 * @param startRate     开始利率
	 * @param endRate       结束利率
	 * @param sAgentIds		渠道Id
	 * @param nonAgentId    非渠道Id
	 * @return 当前购买信息的条数，
	 */
	public int countBuyMessageForDep(String orderNo,String beginAmount, String endAmount,
									 Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard,
									 String buyBankCard, String idCard, Integer accountStatus,String mobile, String userName, Integer term,
									 String productCode,String agentName,String buyBankType,Integer agentId,Double rate,Double startRate,Double endRate,
									 String sAgentIds,String nonAgentId,String propertySymbol, String productType);

	/**
	 * 计算当前的交易总和（云贷存管）匹配成功的
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param startRate     开始利率
	 * @param endRate       结束利率
	 * @param agentIds		渠道Id
	 * @param nonAgentId    非渠道Id
	 * @return 返回购买信息的列表，
	 */
	double findUserBuySumBalanceForDep(String beginAmount,
									   String endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
									   String bindBankCard, String buyBankCard, String idCard,Integer accountStatus, String mobile,
									   String userName, Integer term,String ProductCode,String buyBankType,Integer agentId,Double rate,
									   Double startRate,Double endRate,String agentIds,String nonAgentId,String propertySymbol, String productType);

	/**
	 * 查询购买信息列表（云贷存管）
	 * @param beginAmount  	起始金额
	 * @param endAmount		结束金额
	 * @param beginTime		起始时间
	 * @param endTime		结束时间
	 * @param settleAccountsBeginTime 结算起始日期
	 * @param settleAccountsEndTime 结算结束日期
	 * @param bindBankCard	绑定银行卡号
	 * @param buyBankCard	购买银行卡号
	 * @param idCard		身份证号码
	 * @param accountStatus	账户状态
	 * @param mobile		手机号码
	 * @param userName		用户名
	 * @param term			投资期限
	 * @param productCode   产品类型code
	 * @param agentName     渠道名称
	 * @param pageNum		当前页
	 * @param numPerPage	每页的页数
	 * @param startRate     开始利率
	 * @param endRate       结束利率
	 * @param sAgentIds		渠道Id
	 * @param nonAgentId    非渠道Id
	 * @return 返回购买信息的列表
	 */
	List<BsStatisticsVO> findUserBuyMessageListForDep(String orderNo,String beginAmount,
													  String endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
													  String bindBankCard, String buyBankCard, String idCard,String productCode,String agentName,
													  Integer accountStatus, String mobile, String userName, Integer term,int pageNum,int numPerPage,
													  String orderDirection,String orderField,String buyBankType,Integer agentId,Double rate,Double startRate,Double endRate,
													  String sAgentIds,String nonAgentId,String propertySymbol, String productType);
	
	
	/**
	 * 查询存管产品列表
	 * @return
	 */
	public List<BsProduct> findProductListDep();
	
	/**
	 * 查询非存管产品列表
	 * @return
	 */
	public List<BsProduct> findProductListNoDep();
	
	/**
	 * 云贷存管未来30天兑付统计-删除数据
	 * @return
	 */
	public boolean deleteBsDepCash30(String partnerCode);

	/**
	 * 云贷存管未来30天兑付统计-保存统计数据
	 * @param valueList
	 */
	public void insertDepCashScheduleList(List<DepCash30VO> valueList);

	/**
	 * 渠道业绩查询-渠道用户查询优化-查询债转金额
	 * @return
     */
	public List<AgentUserVo> queryRealAmountTransList();

	/**
	 * 渠道业绩查询-渠道用户查询优化-查询理财人回款计划表记录数
	 * @return
	 */
	public List<AgentUserVo> queryRepayedPeriodCountList();

	/**
	 * 渠道用户投资查询计数（秦皇岛）
	 * @param userName
	 * @param mobile
	 * @param term
	 * @param accountStatus
	 * @param distributionChannel
	 * @param terminalType
	 * @param beginBuyAmount
	 * @param endBuyAmount
	 * @param investFinishBeginTime
	 * @param investFinsishEndTime
	 * @param buyBeginTime
	 * @param buyEndTime
	 * @return
	 */
	public int countQhdAgentBuyMessage(String userName, String mobile, Integer term, Integer accountStatus,
			String distributionChannel, String terminalType, Double beginBuyAmount, Double endBuyAmount,
			Date investFinishBeginTime, Date investFinsishEndTime, Date buyBeginTime, Date buyEndTime);
	
	/**
	 * 渠道用户投资查询列表（秦皇岛）
	 * @param userName
	 * @param mobile
	 * @param term
	 * @param accountStatus
	 * @param distributionChannel
	 * @param terminalType
	 * @param beginBuyAmount
	 * @param endBuyAmount
	 * @param investFinishBeginTime
	 * @param investFinsishEndTime
	 * @param buyBeginTime
	 * @param buyEndTime
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	public List<BsQhdStatisticsInvestVO> findQhdAgentUserMessageList(String userName, String mobile, Integer term, Integer accountStatus,
			String distributionChannel, String terminalType, Double beginBuyAmount, Double endBuyAmount,
			Date investFinishBeginTime, Date investFinsishEndTime, Date buyBeginTime, Date buyEndTime, int pageNum, int numPerPage);
	
	/**
	 * 渠道用户投资查询投资总金额（秦皇岛）
	 * @param userName
	 * @param mobile
	 * @param term
	 * @param accountStatus
	 * @param distributionChannel
	 * @param terminalType
	 * @param beginBuyAmount
	 * @param endBuyAmount
	 * @param investFinishBeginTime
	 * @param investFinsishEndTime
	 * @param buyBeginTime
	 * @param buyEndTime
	 * @return
	 */
	Double findQhdAgentUserSumBalance(String userName, String mobile, Integer term, Integer accountStatus,
			String distributionChannel, String terminalType, Double beginBuyAmount, Double endBuyAmount,
			Date investFinishBeginTime, Date investFinsishEndTime, Date buyBeginTime, Date buyEndTime);
	
}
