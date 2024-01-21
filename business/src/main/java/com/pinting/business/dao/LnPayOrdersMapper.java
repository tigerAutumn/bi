package com.pinting.business.dao;

import java.util.List;
import java.util.Map;

import com.pinting.business.model.vo.AgreementFeeDetailVO;
import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.LnPayOrders;
import com.pinting.business.model.LnPayOrdersExample;
import com.pinting.business.model.vo.HFBANKLoanWithdrawOrderVO;
import com.pinting.business.model.vo.LnChannelVO;
import com.pinting.business.model.vo.LnPayOrdersVO;

public interface LnPayOrdersMapper {
    int countByExample(LnPayOrdersExample example);

    int deleteByExample(LnPayOrdersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnPayOrders record);

    int insertSelective(LnPayOrders record);

    List<LnPayOrders> selectByExample(LnPayOrdersExample example);

    LnPayOrders selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnPayOrders record, @Param("example") LnPayOrdersExample example);

    int updateByExample(@Param("record") LnPayOrders record, @Param("example") LnPayOrdersExample example);

    int updateByPrimaryKeySelective(LnPayOrders record);

    int updateByPrimaryKey(LnPayOrders record);


	/**
	 * 管理台分期订单查询
	 * @param lnPayOrdersVO
	 * @return
	 */
    List<LnPayOrdersVO> selectStagePayOrdersListPageInfo(LnPayOrdersVO lnPayOrdersVO);


	/**
	 * 管理台分期订单查询总条数
	 * @param lnPayOrdersVO
	 * @return
	 */
	Integer selectStagePayOrdersListCount(LnPayOrdersVO lnPayOrdersVO);

    /**
     * 查询该笔订单并加锁
     * @param id
     * @return
     */
    LnPayOrders selectByPKForLock(@Param("id") Integer id);
    
	/**
	 * 借款人提现-统计
	 * @return
	 */
	Integer selectSysLoanerWithdrawCount();
	
	/**
	 * 借款人提现-列表
	 * @param record
	 * @return
     */
	List<HFBANKLoanWithdrawOrderVO> selectLoanerList(HFBANKLoanWithdrawOrderVO record);
	/**
	 * 查询宝付辅助通道转账交易订单
	 * @return
	 */
	List<LnPayOrders> selectBaofooAssistTransOrders();

	/**
	 * 查询订单对应商户号信息
	 * @param payOrderNo
	 * @return
	 */
	LnChannelVO queryLnMerchantInfoOfOrder(@Param("payOrderNo") String payOrderNo);
	/**
	 * 查询主通道对账借款端订单
	 * @return
	 */
	List<LnPayOrders> baoFooCheckAccountOrders();
	
	/**
	 * 对账完成后， 对应交易类型计数
	 * @param channel
	 * @param transType
	 * @return
	 */
	int countCheckAccountOrders(@Param("channel")String channel, @Param("transTypes")List<String> transTypes,
			@Param("status")int status, @Param("channelTransType")String channelTransType, @Param("partnerCode") String partnerCode);
	
	/**
	 * 对账完成后， 对应交易类型统计金额
	 * @param channel
	 * @param transType
	 * @return
	 */
	Double sumCheckAccountOrders(@Param("channel")String channel, @Param("transTypes")List<String> transTypes,
			@Param("status")int status, @Param("channelTransType")String channelTransType, @Param("partnerCode") String partnerCode);
	
	/**
	 * 对账完成后，辅助通道转账到主通道计数
	 * @return
	 */
	int countBaofooAssist2MainTransOrders(@Param("channel")String channel, @Param("transType") String transType,
			@Param("status")int status, @Param("channelTransType")String channelTransType,
			@Param("paymentChannelId") Integer paymentChannelId, @Param("checkDate")String checkDate);
	
	/**
	 * 对账完成后，辅助通道转账到主通道统计金额
	 * @return
	 */
	Double sumBaofooAssist2MainTransOrders(@Param("channel")String channel, @Param("transType") String transType,
			@Param("status")int status, @Param("channelTransType")String channelTransType,
			@Param("paymentChannelId") Integer paymentChannelId, @Param("checkDate")String checkDate);
	
	/**
	 * 对账完成后，线下还款计数
	 * @return
	 */
	int countPartnerOfflineRepay(@Param("channel")String channel, @Param("transType") String transType,
			@Param("channelTransType") String channelTransType, @Param("status")int status, @Param("partnerCode")String partnerCode);
	
	/**
	 * 对账完成后，线下还款统计金额
	 * @return
	 */
	Double sumPartnerOfflineRepay(@Param("channel")String channel, @Param("transType") String transType,
			@Param("channelTransType") String channelTransType, @Param("status")int status, @Param("partnerCode")String partnerCode);
	
	/**
	 * 对账完成后，辅助通道线下还款计数
	 * @return
	 */
	int countPartnerOfflineRepayForAssist(@Param("channel")String channel, @Param("transType") String transType,
			@Param("channelTransType") String channelTransType, @Param("status")int status, 
			@Param("partnerCode")String partnerCode, @Param("paymentChannelId") Integer paymentChannelId);
	
	/**
	 * 对账完成后，辅助通道线下还款统计金额
	 * @return
	 */
	Double sumPartnerOfflineRepayForAssist(@Param("channel")String channel, @Param("transType") String transType,
			@Param("channelTransType") String channelTransType, @Param("status")int status, 
			@Param("partnerCode")String partnerCode, @Param("paymentChannelId") Integer paymentChannelId);
	
	/**
	 * 查询借款订单信息，根据订单号和渠道
	 * @param orderNo
	 * @param channel
	 * @return
	 */
	LnPayOrders selectByOrderNoAndChannel(@Param("orderNo") String orderNo, @Param("channel") String channel, @Param("status") Integer status);
	
	
	/**
	 * 同银行卡上次协议支付成功时间
	 * @param bankCardNo
	 * @param expId 排除该次
	 * @return
	 */
	LnPayOrders selectLastByCard(@Param("bankCardNo") String bankCardNo,@Param("expId") Integer expId);
	

	/**
	 * 统计短信条数，结算日期内发送短信成功的条数（还款预下单验证码短信及重发）
	 * @param partnerCode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer countPartnerSmsRecord(@Param("partnerCode") String partnerCode, @Param("startTime") String startTime, @Param("endTime") String endTime);
	
	/**
	 * 统计鉴权笔数，结算日期内恒丰绑卡笔数（不管成功未成功）
	 * @param partnerCode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer countPartnerAuthRecord(@Param("partnerCode") String partnerCode, @Param("startTime") String startTime, @Param("endTime") String endTime);
	
	/**
	 * 统计放款代发笔数，结算日期内放款成功的借款笔数，去除退票笔数（结算日期内退票）
	 * @param partnerCode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer countPartnerLoanRecord(@Param("partnerCode") String partnerCode, @Param("startTime") String startTime, @Param("endTime") String endTime);
	
	/**
	 * 统计还款代扣笔数，结算日期内还款代扣成功的笔数
	 * @param partnerCode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer countPartnerRepayRecord(@Param("partnerCode") String partnerCode, @Param("startTime") String startTime, @Param("endTime") String endTime);
	
	/**
	 * 统计放款金额，结算日期内放款成功的借款金额（包含砍头息），去除退票金额（结算日期内退票）
	 * @param partnerCode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double sumPartnerLoanAmount(@Param("partnerCode") String partnerCode, @Param("startTime") String startTime, @Param("endTime") String endTime);

	/**
	 * 对账完成后， 对应交易类型计数
	 * 业务类型：主商户代扣还款
	 * @param channel
	 * @param transType
	 * @return
	 */
	int countCheckAccountOrdersForDK(@Param("channel")String channel, @Param("transType")String transType,
			@Param("status")int status, @Param("channelTransType")String channelTransType, 
			@Param("partnerCode") String partnerCode, @Param("merchantNo") String merchantNo);
	
	/**
	 * 对账完成后， 对应交易类型统计金额
	 * 业务类型：主商户代扣还款
	 * @param channel
	 * @param transType
	 * @return
	 */
	Double sumCheckAccountOrdersForDK(@Param("channel")String channel, @Param("transType")String transType,
			@Param("status")int status, @Param("channelTransType")String channelTransType, 
			@Param("partnerCode") String partnerCode, @Param("merchantNo") String merchantNo);
	
	/**
	 * 对账完成后， 对应交易类型计数
	 * 业务类型：辅商户代扣还款
	 * @param channel
	 * @param transType
	 * @return
	 */
	int countCheckAccountOrdersForAssistDK(@Param("channel")String channel, @Param("transType")String transType,
			@Param("status")int status, @Param("channelTransType")String channelTransType, 
			@Param("partnerCode") String partnerCode, @Param("paymentChannelId") Integer paymentChannelId,
			@Param("checkDate") String checkDate);
	
	/**
	 * 对账完成后， 对应交易类型统计金额
	 * 业务类型：辅商户代扣还款
	 * @param channel
	 * @param transType
	 * @return
	 */
	Double sumCheckAccountOrdersForAssistDK(@Param("channel")String channel, @Param("transType")String transType,
			@Param("status")int status, @Param("channelTransType")String channelTransType, 
			@Param("partnerCode") String partnerCode, @Param("paymentChannelId") Integer paymentChannelId,
			@Param("checkDate") String checkDate);
	
	/**
	 * 查询代扣还款，排除线下还款
	 * @param transTypes
	 * @param channelTransType
	 * @return
	 */
	List<LnPayOrders> selectLnPayOrdersWithNoOffline(@Param("transType") String transType, 
			@Param("channelTransType") String channelTransType, @Param("orderNo") String orderNo);
	
	/**
	 * 公众号 还款日常管理还款笔数、金额统计
	 * @return
     */
	Map<String,Object> queryDailyCountAndSum(String partnerCode);

	/**
	 * 资产方费用结算查询-协议支付手续费总和查询
	 * @param partnerCode
	 * @param startTime
	 * @param endTime
     * @return
     */
	Double sumAgreementFeeAmount(@Param("partnerCode") String partnerCode,
								 @Param("startTime") String startTime,
								 @Param("endTime") String endTime);

	/**
	 * 资产方费用结算查询，协议支付手续费明细，默认显示100条记录
	 * @param partnerCode
	 * @param startTime
	 * @param endTime
     * @return
     */
	List<AgreementFeeDetailVO> selectAgreementFeeDetailList(@Param("partnerCode") String partnerCode,
															@Param("startTime") String startTime,
															@Param("endTime") String endTime);

	/**
	 * 资产方费用结算查询，协议支付手续费明细，导出excel查询sql
	 * @param partnerCode
	 * @param startTime
	 * @param endTime
     * @return
     */
	List<AgreementFeeDetailVO> selectAgreementFeeDetailListExportXls(@Param("partnerCode") String partnerCode,
															@Param("startTime") String startTime,
															@Param("endTime") String endTime);
}