package com.pinting.business.accounting.loan.service;

import java.util.List;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.RepayResultInfo;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.CommissionVO;
import com.pinting.business.model.vo.LnLoanRepayScheduleVO;
import com.pinting.business.model.vo.RepayScheduleVO;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Repay_BadDebt;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Repay_PreRepay;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Repay_RepayConfirm;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Repay_WithholdingRepay;

public interface RepayPaymentService {

    /**
     * 预还款
     * @param req
     * @return
     * @throws Exception
     */
    String preRepay(G2BReqMsg_Repay_PreRepay req) throws Exception;

    /**
     * 确认还款
     * @param req
     * @throws Exception
     */
    void repayConfirm(G2BReqMsg_Repay_RepayConfirm req) throws Exception;


    /**
     * 代扣还款，对应信息入redis
     * @param req
     * @return
     * @throws Exception
     */
    String withholdingRepay(G2BReqMsg_Repay_WithholdingRepay req)throws Exception;

	/**
	 * 取redis数据，发起代扣请求以及对应操作
	 * @param order			订单
	 * @param lnBindCard	银行卡
	 */
	void withholdingRepaySendBaoFoo(LnPayOrders order, LnBindCard lnBindCard);

    /**
     * 还款通知
     * @param req
     */
    void notifyRepay(RepayResultInfo req);

    /**
     * 通知合作方
     * @param repay
     */
    void notifyPartner(LnRepay repay,String errorMsg);
    /**
     * 坏账处理
     * 赞分期已经记为坏账，通知币港湾做坏账处理
     * 币港湾只做记账，之前应付的资金已经从备付金中提取
     * @param req G2BReqMsg_Repay_BadDebt
     */
	void badDebt(G2BReqMsg_Repay_BadDebt req);
	
	/**
	 * 正常还款、提前还款系统分账
	 * @param lnLoan
	 * @param repaySchedule
	 * @param repayAmount
	 * @param payOrderNo
	 * @param relationList 
	 */
	void normalRepaySysSplit(LnLoan lnLoan, LnRepaySchedule repaySchedule, 
			Double repayAmount, String payOrderNo, List<LnLoanRelation> relationList);
	
	/**
	 * 赞分期进行理财人提前赎回后，赞分期借款的还款账单计息正常还款或提前还款
	 * @author bianyatian
	 * @param lnLoan
	 * @param repaySchedule
	 * @param repayAmount
	 * @param payOrderNo
	 * @param relationList
	 */
	void normalRepaySysSplit4ZANNew(LnLoan lnLoan, LnRepaySchedule repaySchedule, 
			Double repayAmount, String payOrderNo, List<LnLoanRelation> relationList);
	
	/**
	 * 赞分期进行理财人提前赎回，进恒丰前分账
	 * @author bianyatian
	 * @param lnLoan
	 * @param loanRepaySchedule
	 * @param relationList
	 */
	void normalRepaySysSplit4ZANHF(LnLoan lnLoan,LnLoanRepayScheduleVO loanRepaySchedule,List<LnLoanRelation> relationList);
	
	/**
	 * 逾期垫付系统分账
	 * 1、记录还款营收收入
	 * 2、生成存管账单
	 * 3、调用系统记账
	 * 4、ln_repay_schedule更新状态为逾期未还
	 * @param lnLoan
	 * @param repaySchedule
	 * @param relationList
	 */
	void overdueSysSplit(LnLoan lnLoan, RepayScheduleVO repaySchedule, List<LnLoanRelation> relationList);
	
	/**
	 * 1、记录合作方的还款营收收入
	 * 2、ln_repay_schedule更新状态为已还
	 * 4、新增存管还款计划表及明细
	 * @param lnLoan
	 * @param repaySchedule
	 * @param lnRepayAmount
	 * @param payOrderNo
	 * @param partnerRevenue
	 * @param lnSubAccountId
	 * @param depRepayScheduleTotal
	 * @param loanServiceAmount  P_amount计息本金*计天数息(inDays)*借款服务费利率/365
	 * @param partnerEnum
	 */
	void doNormalRepayDetail(LnLoan lnLoan, LnRepaySchedule repaySchedule, Double lnRepayAmount,String payOrderNo, Double partnerRevenue,
			Integer lnSubAccountId, Double depRepayScheduleTotal,PartnerEnum partnerEnum, Integer maxSerialId, Double loanServiceAmount);


	/**
	 * 逾期还款赞分期
	 * @param lnLoan
	 * @param repaySchedule
	 * @param repayAmount
	 * @param payOrderNo
	 * @param relationList
	 * @param isOffLine 是否是线下还款
	 */
	void overdueRepay(LnLoan lnLoan, LnRepaySchedule repaySchedule, 
			Double repayAmount, String payOrderNo, List<LnLoanRelation> relationList, boolean isOffLine);
	
	
	
	/**
	 * 借款人还款到代偿人处理
	 * @param depRepaySchedule
	 * @param lnHfUserId 平台客户编号（借款人）
	 * @param compensateHfUserId 代偿人平台客户编号
	 * @param lnDEPJSHId 借款人子账户DEP_JSH id
	 * @param compensateDEPJSHId 代偿人子账户DEP_JSH id
	 */
	void overdueRepay2Compensate(LnDepositionRepaySchedule depRepaySchedule, 
			String lnHfUserId, String compensateHfUserId,Integer lnDEPJSHId, 
			Integer compensateDEPJSHId);
	/**
	 * 	线下还款-代扣还款 <br>
	 * 	为了不影响原先代扣流程，代码原则不嵌入代扣代码中，根据是否线下还款，开始走线下还款流程 <br>
	 *	走代扣接口流程，发起宝付代扣动作不做，默认代扣成功 <br>
	 *	线下还款流程 <br>
	 *	<p>
	 *	 <br>
	 *	1、还款流程开始，校验数据：校验用户/白名单、校验还款表、订单状态、绑卡状态 <br>
	 *	2、循环账单（比对账单）: <br>
	 *	a.根据账单还款ID查询还款计划表，查询还款明细，比对账单数据和还款明细是否一致； <br>
	 *	b.记录ln_repay表，状态为还款中，设置RepayType为线下还款；记录还款明细ln_repay_detail； <br>
	 *	 <br>
	 *	3、记录ln_pay_orders,ln_pay_orders_jnl表； <br>
	 *	4、此步认为代扣直接成功，更新 订单表为处理中;放到redis中“process_order”，并插入到消息队列表中<br>
	 *	5、轮询处理“process_order“ <br>
	 *	</p>
	 * @param req
	 * @return
	 * @throws Exception
	 */
	String withholdingRepayOffLine(G2BReqMsg_Repay_WithholdingRepay req) throws Exception;
	/**
	 * 1、记录合作方的还款营收收入
	 * 2、ln_repay_schedule更新状态为已还(确认终态)
	 * 4、新增存管还款计划表及明细
	 * @param lnLoan
	 * @param repaySchedule
	 * @param lnRepayAmount
	 * @param payOrderNo
	 * @param partnerRevenue
	 * @param lnSubAccountId
	 * @param depRepayScheduleTotal 
	 * @param partnerEnum
	 * @param maxSerialId
	 * @param loanServiceAmount  P_amount计息本金*计天数息(inDays)*借款服务费利率/365
	 * @param isLastPeriodRepay  最后一期还款标志 
	 * @param realRepayPrincipal 最后一期还款计息本金
	 * @param diffRepayPrincipal 最后一次还款本金差额
	 */
	void doNormalRepayDetail4Seven(LnLoan lnLoan, LnRepaySchedule repaySchedule, Double lnRepayAmount,String payOrderNo, Double partnerRevenue,
			Integer lnSubAccountId, Double agreementAmount,PartnerEnum partnerEnum, Integer maxSerialId, Double loanServiceAmount, 
			boolean isLastPeriodRepay, Double realRepayPrincipal, Double diffRepayPrincipal);

	/**
	 * 	线下还款-赞分期提前赎回后的线下还款
	 *
	 *	1、还款流程开始，校验数据：校验用户/白名单、校验还款表、订单状态、绑卡状态
	 *	2、循环账单（比对账单）: 
	 *	a.根据账单还款ID查询还款计划表，查询还款明细，比对账单数据和还款明细是否一致；
	 *	b.记录ln_repay表，状态为还款中，设置RepayType为线下还款；记录还款明细ln_repay_detail；
	 *	
	 *	3、记录ln_pay_orders,ln_pay_orders_jnl表；
	 *	4、此步认为代扣直接成功，更新 订单表为处理中;调用新的征程还款
	 *	5、起线程通知RepayProcess 
	 *	
	 * @param req
	 * @return
	 * @throws Exception
	 */
	String withholdingRepayOffLineNew(G2BReqMsg_Repay_WithholdingRepay req)throws Exception;

}
