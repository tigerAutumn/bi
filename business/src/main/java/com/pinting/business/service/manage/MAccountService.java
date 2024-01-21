package com.pinting.business.service.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsAssetsList;
import com.pinting.business.model.BsCashSchedule30;
import com.pinting.business.model.BsCheckErrorJnl;
import com.pinting.business.model.BsCheckJnl;
import com.pinting.business.model.BsProfitLoss;
import com.pinting.business.model.BsSysWithdraw;
import com.pinting.business.model.BsUserWithdraw;
import com.pinting.business.model.vo.*;

public interface MAccountService {
	
	/**
	 * 根据当前页和一页的页数，进行分页查询
	 * @param pageNum
	 * @param numPerPage
	 * @return 返回差错列表记录
	 */
	public ArrayList<BsCheckErrorJnlVO> findCheckErrorJnlListByPage(Date beginTime,Date overTime,int pageNum,
			int numPerPage);
	
	/**
	 * 查询当前差错表中的总条数
	 * @return
	 */
	public int countCheckErrorList();
	/**
	 * 查询未来30日可兑付中的总条数
	 * @return
	 */
	public int countCashScheduleList(Date beginTime, Date overTime);
	/**
	 * 查询当前损益表中的总条数
	 * @return
	 */
	public int countProfitLossList();
	
	/**
	 * 根据id号更新差错处理表
	 * @param checkError
	 * @return 更新成功返回true，否则返回false
	 */
	public boolean updateCheckErrorById(BsCheckErrorJnl checkError);

	/**
	 * 根据条件查询进行分页查询，条件可以为空
	 * @param pageNum
	 * @param numPerPage
	 * @return 返回流水表所有记录
	 */
	public ArrayList<BsAccountJnlVO> findAccountListQueryByPage(Integer status,Date beginTime,Date overTime,int pageNum,
			int numPerPage);
	
	/**
	 * 订单分页查询 条件可以为空 -- 超管
	 * @param userMobile 用户手机号
	 * @param userName 用户名
	 * @param orderNo 订单号
	 * @param accountType 账户类型（系统、用户）
	 * @param transType 交易类型（充值、购买产品、钱包提现、奖励金提现、回款提现）
	 * @param channelTransType 渠道类型（快捷、代收、代付、网银、钱包转账）
	 * @param bankName 银行类型
	 * @param bankCardNo 银行卡号
	 * @param beginTime 交易开始时间
	 * @param overTime 交易结束时间
	 * @param pageNum 当前页
	 * @param numPerPage 每页记录数
	 * @param orderDirection
	 * @param orderField
	 * @param status 订单状态
	 * @param buyBankType 购买银行类别
	 * @param agentId 渠道id
	 * @param mobile 预留手机号
	 * @param idCard 身份证
	 * @param payChannel 订单渠道
	 * @param returnMsg 返回信息
	 * @param agentIds 渠道编号
	 * @param nonAgentId 非渠道编号
	 * @param 用户ID
	 * @return
	 */
	public ArrayList<BsPayOrdersVO> findOrdersListQueryByPage(String userMobile, String userName, String orderNo, 
			Integer accountType, String transType, String channelTransType, String bankName, String bankCardNo, 
			Date beginTime, Date overTime, int pageNum, int numPerPage, String orderDirection, String orderField, 
			Integer status, String buyBankType, Integer agentId, String mobile, String idCard, String payChannel, String returnMsg,
			String agentIds,String nonAgentId, Integer userId,String terminalType);
	
	/**
	 * 订单分页查询 条件可以为空 --订单跟踪
	 * @param userMobile 用户手机号 
	 * @param userName 用户名
	 * @param transType 交易类型（充值、购买产品、钱包提现、奖励金提现、回款提现）
	 * @param channelTransType 渠道类型（快捷、代收、代付、网银、钱包转账）
	 * @param beginTime 交易开始时间
	 * @param overTime 交易结束时间
	 * @param pageNum 当前页
	 * @param numPerPage 每页记录数
	 * @param status 订单状态
	 * @param buyBankType 购买银行类别
	 * @param agentId 渠道id
	 * @param payChannel 订单渠道
	 * @param returnMsg 返回信息
	 * @param agentIds 渠道编号
	 * @param nonAgentId 非渠道编号
	 * @return
	 */
	public ArrayList<BsPayOrdersVO> findOrdersListQueryByPage4Normal(String userMobile, String userName, 
			String transType, String channelTransType, Date beginTime, Date overTime, int pageNum, int numPerPage,
			Integer status, String buyBankType, Integer agentId, String payChannel, String returnMsg,
			String agentIds,String nonAgentId,String orderDirection, String orderField);
	/**
	 * 根据条件查询进行分页查询，条件可以为空
	 * @param pageNum
	 * @param numPerPage
	 * @return 返回流水表所有记录
	 */
	public ArrayList<BsUserWithdraw> findCustomerWithdrawByPage(String applyNo,Integer status,Date withdrawTime,Date finishTime,int pageNum,
			int numPerPage);
	/**
	* 根据条件查询进行分页查询，条件可以为空
	* @param pageNum
	* @param numPerPage
	* @return 返回流水表所有记录reqMsg.getStatus(),reqMsg.getCreateTime(),reqMsg.getPageNum(),reqMsg.getNumPerPage()
	*/
	public ArrayList<BsAssetsList> findAssetsListByPage(Integer status,Date createTime,int pageNum,
					int numPerPage);
	/**
	 * 根据条件查询进行分页查询，条件可以为空
	 * @param pageNum
	 * @param numPerPage
	 * @return 返回流水表所有记录
	 */
	public ArrayList<BsProfitLoss> findProfitLossListQueryByPage(Date beginTime,Date overTime,int pageNum,
			int numPerPage);
	/**
	 * 根据条件查询进行分页查询，条件可以为空
	 * @param pageNum
	 * @param numPerPage
	 * @return 返回流水表所有记录
	 */
	public ArrayList<BsCashSchedule30> findCashScheduleListQueryByPage(Date beginTime,Date overTime,int pageNum,
			int numPerPage);

	/**
	 * 查询当前流水表所有记录
	 * @return 返回流水表中总条数
	 */
	public int countTransferList(Integer status,Date beginTime,Date overTime);
	
	/**
	 * 未来30天应付本金合计
	 * @param beginTime
	 * @param overTime
	 * @return
	 */
	public double sumCashBaseAmount(Date beginTime,Date overTime);
	
	/**
	 * 未来30天应付利息合计
	 * @param beginTime
	 * @param overTime
	 * @return
	 */
	public double sumBashInterestAmount(Date beginTime,Date overTime);

	/**
	 * 查询当前订单表所有记录
	 * @return 返回流水表中总条数reqMsg.getStatus(),reqMsg.getBeginTime(),reqMsg.getOverTime()
	 */
	public int countOrdersList(String orderNo,Integer Status,Date BeginTime,Date OverTime);
	/**
	 * 查询当前订单表所有记录
	 * @return 返回流水表中总条数reqMsg.getStatus(),reqMsg.getBeginTime(),reqMsg.getOverTime()
	 */
	public int countBsUserWithdraw(String orderNo,Integer Status,Date withdrawTime,Date finishTime);

	/**
	 * 根据id号查询流水表详细信息
	 * @param id
	 * @return 返回流水表详细信息记录，没有则返回为null
	 */
	public BsAccountJnl findAccountJnlById(Integer id);

	/**
	 * 根据id号对账登记表详情
	 * @param id
	 * @return 对账登记表记录，没有返回null
	 */
	public BsCheckJnl findCheckJnlById(Integer id);

	/**
	 * 查询到期未支付警示
	 * @param startTime
	 * @param endTime
	 * @param userName
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	public ArrayList<BsStatisticsVO> findInvestMatureWarmList(Date settleAccountsBeginTime,
			Date settleAccountsEndTime, String userName, int pageNum, int numPerPage);

	/**
	 * 到期后还未支付的数量
	 * @param startTime
	 * @param endTime
	 * @param userName
	 * @return
	 */
	public int countInvestMatureWarmList(Date settleAccountsBeginTime, Date settleAccountsEndTime,
			String userName);
	
	/**
	 * 到期后还未支付的跑批告警
	 * @param time
	 * @return
	 */
	public int countInvestMatureWarm(Date time);
	
	/**
	 * 分页查询当前系统提现明细
	 * @param finishTimeBegin 到账开始时间
	 * @param finishTimeEnd	  到账结束时间
	 * @param withdrawTimeBegin 提现开始时间
	 * @param withdrawTimeEnd  到账结束时间
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	public ArrayList<BsSysWithdraw> findSysWithdrawDetailListByPage(
			Date finishTimeBegin,Date finishTimeEnd,Date withdrawTimeBegin,Date withdrawTimeEnd,Integer status, int pageNum, int numPerPage);
	
	/**
	 * 计算当前总系统明细
	 * @param finishTimeBegin 到账开始时间
	 * @param finishTimeEnd	  到账结束时间
	 * @param withdrawTimeBegin 提现开始时间
	 * @param withdrawTimeEnd  到账结束时间
	 * @return
	 */
	public int countSysWithdrawList(Date finishTimeBegin,Date finishTimeEnd,Date withdrawTimeBegin,Date withdrawTimeEnd,Integer status);
	/**
	 * 资管计划合同查询
	 * @param status
	 * @param createTime
	 * @return
	 */
	public int countBsAssetsList(Integer status, Date createTime);
	
	/**
	 * 新增资管计划记录
	 * @param bsAssetsList
	 */
	public void addBsAssetsPlan(BsAssetsList bsAssetsList);
	
	/**
	 * 订单数量统计--超管
	 * @param userMobile 用户手机号
	 * @param userName 用户名
	 * @param orderNo 订单号
	 * @param accountType 账户类型（系统、用户）
	 * @param transType 交易类型（充值、购买产品、钱包提现、奖励金提现、回款提现）
	 * @param channelTransType 渠道类型（快捷、代收、代付、网银、钱包转账）
	 * @param bankName 银行类型
	 * @param bankCardNo 银行卡号
	 * @param beginTime 交易开始时间
	 * @param overTime 交易结束时间
	 * @param pageNum 当前页
	 * @param numPerPage 每页记录数
	 * @param orderDirection
	 * @param orderField
	 * @param status 订单状态
	 * @param buyBankType 购买银行类别
	 * @param agentId 渠道id
	 * @param mobile 预留手机号
	 * @param idCard 身份证
	 * @param payChannel 订单渠道
	 * @param agentIds 渠道编号
	 * @param nonAgentId 非渠道编号
	 * @param userId 用户ID
	 * @return
	 */
	public int selectCountPayOrder(String userMobile, String userName, String orderNo, 
			Integer accountType, String transType, String channelTransType, String bankName, 
			String bankCardNo, Date beginTime, Date overTime, Integer status, String buyBankType, 
			Integer agentId, String mobile, String idCard, String payChannel, String returnMsg,
			String agentIds, String nonAgentId, Integer userId,String sShowTerminal);
	
	/**
	 * 订单数量统计 --订单跟踪
	 * @param userMobile 用户手机号 
	 * @param userName 用户名
	 * @param transType 交易类型（充值、购买产品、钱包提现、奖励金提现、回款提现）
	 * @param channelTransType 渠道类型（快捷、代收、代付、网银、钱包转账）
	 * @param beginTime 交易开始时间
	 * @param overTime 交易结束时间
	 * @param pageNum 当前页
	 * @param numPerPage 每页记录数
	 * @param status 订单状态
	 * @param buyBankType 购买银行类别
	 * @param agentId 渠道id
	 * @param payChannel 订单渠道
	 * @param returnMsg 返回信息
	 * @param agentIds 渠道编号
	 * @param nonAgentId 非渠道编号
	 * @return
	 */
	public int selectCountPayOrder4Normal(String userMobile, String userName, 
			String transType, String channelTransType, Date beginTime, Date overTime, int pageNum, int numPerPage,
			Integer status, String buyBankType, Integer agentId, String payChannel, String returnMsg,
			String agentIds,String nonAgentId);
	/**
	 * 用户提现查询
	 * @param mobile 手机号
	 * @param userName 用户名
	 * @param status 订单状态
	 * @param beginTime 提现时间
	 * @param overTime
	 * @param pageNum 当前页
	 * @param numPerPage 每页记录数
	 * @param orderDirection
	 * @param orderField
	 * @return
	 */
	public List<BsPayOrdersVO> findUserWithdrawal(String mobile, String userName, 
			Integer status, Date beginTime, Date overTime, 
			int pageNum, int numPerPage, String orderDirection, String orderField);
	
	/**
	 * 用户提现金额统计
	 * @param mobile 手机号
	 * @param userName 用户名
	 * @param status 订单状态
	 * @param beginTime 提现时间
	 * @param overTime
	 * @return
	 */
	public double sumUserWithdrawal(String mobile, String userName, 
			Integer status, Date beginTime, Date overTime);
	
	/**
	 * 用户提现金额记录统计
	 * @param mobile 手机号
	 * @param userName 用户名
	 * @param status 订单状态
	 * @param beginTime 提现时间
	 * @param overTime
	 * @return
	 */
	public int countUserWithdrawal(String mobile, String userName, 
		    Integer status, Date beginTime, Date overTime);
	
	/**
	 * 奖励金查询列表
	 * @param mobile
	 * @param userName
	 * @param transCode 操作类型：奖励金转余额，获得推荐奖励
	 * @param beginTime
	 * @param overTime
	 * @param pageNum
	 * @param numPerPage
	 * @param orderDirection
	 * @param orderField
	 * @return
	 */
	public List<BsAccountJnlVO> findUserBonus(String mobile, String userName, String transCodes, 
			Date beginTime, Date overTime, 
			int pageNum, int numPerPage, 
			String orderDirection, String orderField);
	
	/**
	 * 奖励金查询金额合计
	 * @param mobile
	 * @param userName
	 * @param transCode
	 * @param beginTime
	 * @param overTime
	 * @return
	 */
	public double sumUserBonus(String mobile, String userName, String transCodes, 
			Date beginTime, Date overTime);
	
	/**
	 * 奖励金查询记录统计
	 * @param mobile
	 * @param userName
	 * @param transCode 操作类型：奖励金转余额，获得推荐奖励
	 * @param beginTime
	 * @param overTime
	 * @return
	 */
	public int countUserBonus(String mobile, String userName, String transCodes, 
			Date beginTime, Date overTime);
	
	/**
	 * 用户记账流水查询
	 * @param userName
	 * @param moobile
	 * @param beginTime
	 * @param overTime
	 * @param startTransTime 账务时间
	 * @param endTransTim
	 * @param pageNum
	 * @param numPerPage
	 * @param orderDirection
	 * @param orderField
	 * @return
	 */
	public List<BsAccountJnlVO> userChargeAccount(String userName, String mobile, 
			Date beginTime, Date overTime, 
			Date startTransTime, Date endTransTime, 
			int pageNum, int numPerPage, 
			String orderDirection, String orderField);
	
	/**
	 * 用户记账流水记录统计
	 * @param userName
	 * @param mobile
	 * @param beginTime
	 * @param overTime
	 * @param startTransTime 账务时间
	 * @param endTransTim
	 * @return
	 */
	public int userCountChargeAccount(String userName, String mobile, 
			Date beginTime, Date overTime, 
			Date startTransTime, Date endTransTime);
	
	/**
	 * 系统记账流水列表查询
	 * @param transType 交易类型
	 * @param status 状态
	 * @param startTransAmount 交易金额
	 * @param endTransAmount
	 * @param beginTime 系统时间
	 * @param overTime
	 * @param startTransTime 账务日期
	 * @param endTransTime
	 * @param pageNum
	 * @param numPerPage
	 * @param orderDirection
	 * @param orderField
	 * @return
	 */
	public List<BsSysAccountJnlVO> findSysAccountList(String transOtherCode, Integer status, 
			Double startTransAmount, Double endTransAmount, 
			Date beginTime, Date overTime, 
			Date startTransTime, Date endTransTime, 
			int pageNum, int numPerPage, 
			String orderDirection, String orderField);
	
	/**
	 * 系统记账流水记录统计
	 * @param transType 交易类型
	 * @param status 状态
	 * @param startTransAmount 交易金额
	 * @param endTransAmount
	 * @param beginTime 系统时间
	 * @param overTime
	 * @param startTransTime 账务日期
	 * @param endTransTime
	 * @return
	 */
	public int findCountSysAccount(String transOtherCode, Integer status, 
			Double startTransAmount, Double endTransAmount, 
			Date beginTime, Date overTime, 
			Date startTransTime, Date endTransTime);

	/**
	 * 财务功能-恒丰系统充值
	 * @param record
	 * @return
     */
	public List<HFBankPayOrderVO> queryHFBankSysTopUp(HFBankPayOrderVO record);

	/**
	 * 财务功能-恒丰系统提现
	 * @param record
	 * @return
     */
	public List<HFBankPayOrderVO> queryHFBankSysWithdraw(HFBankPayOrderVO record);

	/**
	 * 财务功能-恒丰系统账户划转
	 * @param record
	 * @return
     */
	public List<HFBankPayOrderVO> queryHFBankSysAccountTransfer(HFBankPayOrderVO record);

	/**
	 * 财务功能-恒丰系统充值-统计
	 * @return
     */
	public int queryHFBankSysTopUpCount();

	/**
	 * 财务功能-恒丰系统提现-统计
	 * @return
     */
	public int queryHFBankSysWithdrawCount();

	/**
	 * 财务功能-恒丰系统账户划转-统计
	 * @return
     */
	public int queryHFBankSysAccountTransferCount();
	
	/**
	 * 财务功能-云贷砍头息划转--统计
	 * @return
     */
	public int queryYunHeadFeeTransferCount();
	
	/**
	 * 财务功能-云贷砍头息划转--列表
	 * @return
     */
	public List<HFBankHeadFeeTransferVO> queryYunHeadFeeTransfer(HFBankHeadFeeTransferVO vo);
	
	/**
	 * 赞分期代偿人充值-列表
	 * @param record
	 * @return
     */
	public List<HFBankPayOrderVO> queryZanCompensateList(HFBankPayOrderVO record);
		
	/**
	 * 赞分期代偿人充值-统计
	 * @return
     */
	public int queryZanCompensateCount();

	/**
	 * 根据userId查询赞分期VIP退出申请-站岗户余额
	 * @param userId
	 * @return
     */
	public double queryZanAuthAmount(Integer userId);

	/**
	 * 财务功能-垫付金人工调账-统计
	 * @return
	 */
	public int queryHFBankAdvanceTransferCount();

	/**
	 * 借款人提现-统计
	 * @return
     */
	public int querySysLoanerWithdrawCount();
	
	/**
	 * 财务功能-垫付金人工调账列表
	 * @param record
	 * @return
	 */
	public List<HFBankPayOrderVO> queryHFBankAdvanceTransfer(HFBankPayOrderVO record);
	
	/** 
	 * 借款人提现-统计
	 * @return
	 * */
	public List<HFBANKLoanWithdrawOrderVO> querySysLoanerWithdraw(HFBANKLoanWithdrawOrderVO record);

	/**
	 * 资产方线下还款对账
	 * @param partnerCode
	 * @param startTime
	 * @param endTime
	 * @param pageNum
	 * @param numPerPage
     * @return
     */
	public Map<String, Object> queryPartnerOfflineRepaymentInfo(String partnerCode, String startTime,
																String endTime, Integer pageNum,
																Integer numPerPage);

	
	/**
	 * 钱报信息服务费查询计数
	 * @return
	 */
	public int countQbDepServiceFee(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime);
	
	/**
	 * 钱报信息服务费查询列表
	 * @return
	 */
	public List<DepServiceFeeVO> queryQbDepServiceFeeList(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime,
			int pageNum, int numPerPage);
	
	/**
	 * 杭商信息服务费查询计数
	 * @return
	 */
	public int countHsDepServiceFee(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime, Integer accountStatus, 
			String agentIds, String nonAgentId, Integer agentId);
	
	/**
	 * 杭商信息服务费查询列表
	 * @return
	 */
	public List<DepServiceFeeVO> queryHsDepServiceFeeList(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime,
			Integer accountStatus, String agentIds, String nonAgentId, Integer agentId, int pageNum, int numPerPage);
	
	/**
	 * 退票数据批量订单号查询计数
	 * @return
	 */
	public int countRebateOrder(Date beginTime, Date endTime);
	
	/**
	 * 退票数据批量订单号查询列表
	 * @return
	 */
	public List<LnDepositionRepayScheduleVO> queryRebateOrderList(Date beginTime, Date endTime, Integer pageNum, Integer numPerPage);
	
	/**
	 * 品听信息服务费查询计数
	 * @return
	 */
	public int countPtDepServiceFee(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime,
			String agentIds, String nonAgentId, Integer agentId);
	
	/**
	 * 品听信息服务费查询列表
	 * @return
	 */
	public List<DepServiceFeeVO> queryPtDepServiceFeeList(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime,
			String agentIds, String nonAgentId, Integer agentId, int pageNum, int numPerPage);
	
	/**
	 * 统计购买总金额
	 * @param productName
	 * @param productTerm
	 * @param buyBeginTime
	 * @param buyEndTime
	 * @return
	 */
	Double sumQbBuyAmount(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime); 
	
	/**
	 * 统计信息服务费
	 * @param productName
	 * @param productTerm
	 * @param buyBeginTime
	 * @param buyEndTime
	 * @return
	 */
	Double sumQbDepServiceFee(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime); 
	
	/**
	 * 统计购买总金额
	 * @param productName
	 * @param productTerm
	 * @param buyBeginTime
	 * @param buyEndTime
	 * @return
	 */
	Double sumBuyAmount(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime,
			String agentIds, String nonAgentId, Integer agentId); 
	
	/**
	 * 杭商统计购买总金额
	 * @param productName
	 * @param productTerm
	 * @param buyBeginTime
	 * @param buyEndTime
	 * @return
	 */
	Double sumBuyAmount(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime, Integer accountStatus,
			String agentIds, String nonAgentId, Integer agentId); 
	
	/**
	 * 统计信息服务费
	 * @param productName
	 * @param productTerm
	 * @param buyBeginTime
	 * @param buyEndTime
	 * @return
	 */
	Double sumDepServiceFee(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime,
			String agentIds, String nonAgentId, Integer agentId); 
	
	/**
	 * 统计杭商信息服务费
	 * @param productName
	 * @param productTerm
	 * @param buyBeginTime
	 * @param buyEndTime
	 * @return
	 */
	Double sumHsDepServiceFee(String productName, Integer productTerm, Date buyBeginTime, Date buyEndTime,
			Integer accountStatus, String agentIds, String nonAgentId, Integer agentId); 
	
}
