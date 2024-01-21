package com.pinting.business.accounting.loan.service;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.loan.model.CompensateRepaySysSplitInfo;
import com.pinting.business.accounting.loan.model.DepFixedRepayPayResultInfo;
import com.pinting.business.accounting.loan.model.NormalRepaySysSplitInfo;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsUserCompensateVO;
import com.pinting.gateway.hessian.message.dafy.*;
import com.pinting.gateway.hessian.message.loan7.*;import com.pinting.gateway.hessian.message.zsd.*;

import java.util.Date;
import java.util.List;

/**
 * 
 * @project business
 * @title DepFixedRepayPaymentService.java
 * @author Dragon & cat
 * @date 2017-4-4
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 存管业务固定期限还款服务（主动还款、代扣还款、代偿还款）
 */ 
public interface DepFixedRepayPaymentService {

	/**
	 * 主动还款预下单
	 *
	 * @param req
	 * @return 支付单号
	 */
	String doPreRepay(G2BReqMsg_DafyLoan_ActiveRepayPre req);

	/**
	 * 主动还款预下单重发短信验证码
	 * @param partnerOrderNo
	 * @return
     */
	String doPreRepayRepeat(String partnerOrderNo);

	/**
	 * 确认还款
	 * @param req
	 * @throws Exception
	 */
	void doRepayConfirm(G2BReqMsg_DafyLoan_ActiveRepayConfirm req) throws Exception;

	/**
	 * 前置校验，入还款队列
	 * @param req
	 */
	void repayApply(G2BReqMsg_DafyLoan_CutRepayConfirm req);

	/**
	 * 前置校验，入还款队列-before
	 * */
	void dispatcherRepayApply(G2BReqMsg_DafyLoan_CutRepayConfirm req);
	/**
	 * 发起代扣还款请求,结果处理 (云贷、7贷)
	 * @param lnPayOrder
	 * @param lnBindCard
	 */
	void withholdingRepayAsync(LnPayOrders lnPayOrder, LnBindCard lnBindCard, String userSignNo, String payId);

	/**
	 * 还款结果支付业务处理（主动还款、代扣还款）
	 * @param payResult
	 */
	void doRepayResultPayProcess(DepFixedRepayPayResultInfo payResult);

	/**
	 * 还款结果通知云贷
	 * @param lnPayOrder
	 * @param errorMsg
	 */
	void notifyPartner(LnPayOrders lnPayOrder, String errorMsg);

	/**
	 * 重复还款处理   
	 * @param repeatRepay
	 * @param marginAmout
	 * @return
	 */
	void repeatRepayProcess(LnRepeatRepayRecord repeatRepay, Double marginAmout);
	/**
	 * 重复还款处理   
	 * @param repeatRepay
	 * @return
	 */
	void repeatRepayProcess(LnRepeatRepayRecord repeatRepay);
	/**
	 * 主动(代扣)还款系统分账-云贷、赞时贷
	 * @param info
	 */
	void normalRepaySysSplit(NormalRepaySysSplitInfo info);
	
	/**
	 * 代偿通知接收，云贷，七贷
	 * @param lnCompensate
	 * @param detailList
	 */
	void lateRepayNotice(LnCompensate lnCompensate, List<LnCompensateDetail> detailList);

	/**
	 * 代偿系统分账【云贷，赞时贷】
	 * @param info
	 */
	void compensateRepaySysSplit(CompensateRepaySysSplitInfo info);
	
	/**
	 * 还款结果查询
	 * @param req
	 */
	void repayResultQuery(G2BReqMsg_DafyLoan_QueryRepayResult req,
						  G2BResMsg_DafyLoan_QueryRepayResult res);
	
	/**
	 * 根据合作方还款id   查询   某账单还款利息天数
	 * @param partnerRepayId
	 * @return
	 */
	Integer queryInterestDays(String partnerRepayId);
	
	/**
	 * 主动还款预下单-赞时贷
	 *
	 * @param req
	 * @return 支付单号
	 */
	String doZsdPreRepay(G2BReqMsg_ZsdRepay_PreRepay req);
	
	/**
	 * 主动还款预下单重发短信验证码-赞时贷
	 * @param partnerOrderNo
	 * @return
     */
	@Deprecated
	String doZsdPreRepayRepeat(String partnerOrderNo);
	
	/**
	 * 确认还款
	 * @param req
	 * @throws Exception
	 */
	void doZsdRepayConfirm(G2BReqMsg_ZsdRepay_RepayConfirm req) throws Exception;


    /**
     * 前置校验，入还款队列（赞时贷-代扣还款入口）
     * @param req
     */
    void repayApplyZsd(G2BReqMsg_ZsdRepay_CutpaymentRepay req);

    /**
     * 发起代扣还款请求,结果处理
     * @param lnPayOrder
     * @param lnBindCard
     */
    void withholdingRepayZsdAsync(LnPayOrders lnPayOrder, LnBindCard lnBindCard);

	/**
	 * 发起代扣还款（线下还款，逻辑同线上代扣还款，但不发起宝付请求）
	 * @param req
	 */
	void repayOfflineZsd(G2BReqMsg_ZsdRepay_CutpaymentRepay req);

	/**	
	 * 还款结果 通知赞时贷
	 * @param lnPayOrder
	 * @param repayResultMsg
     */
	void noticePartner2Dsd(final LnPayOrders lnPayOrder, final String repayResultMsg);

	/**
	 * 赞时贷查询还款结果
	 * @param req
	 * @param res
     */
	void zsdRepayResultQuery(G2BReqMsg_ZsdRepay_QueryRepayResult req,
							 G2BResMsg_ZsdRepay_QueryRepayResult res);


    /**
     * 赞时贷代偿通知
     * @param req
     */
	void lateRepayZsdNotice(G2BReqMsg_ZsdRepay_LateRepayNotice req);

	
	/**
	 * 逾期还款分账
	 * @param lnLoan
	 * @param repaySchedule
	 * @param repayAmount
	 * @param relationList
	 */
	void overdueRepaySplit4ZSD(LnLoan lnLoan, LnRepaySchedule repaySchedule, 
			Double repayAmount, List<LnLoanRelation> relationList);
	/**
	 * 云贷还款业务结果处理
	 * @param json
	 * @param order
     */
	void yunDaiBillSync(JSONObject json, LnPayOrders order);


	/******************************************* 存管七贷  *****************************************************/

	/** * @param json
	 * @param order
	 */
	void zsdBillSync(JSONObject json, LnPayOrders order);

	/**
	 * 7贷还款业务结果处理
	 * INIT账单：调用正常代扣分账
	 * REPAIED、LATE_NOT、LATE_REPAIED：做重复还款处理
	 * @param json
	 * @param order
	 */
	void sevenDaiBillSync(JSONObject json, LnPayOrders order);
    /**
     * 前置校验，入还款队列（七贷-代扣还款入口）
     * @param req
     */
    void depSevenRepayApply(G2BReqMsg_DepLoan7_CutRepayConfirm req);

	/**
	 * 发起代扣还款（线下还款，逻辑同线上代扣还款，但不发起宝付请求）
	 * @param req
	 */
	void repayOfflineDepSeven(G2BReqMsg_DepLoan7_CutRepayConfirm req);

	/**
	 * 还款结果 通知7贷
	 * @param lnPayOrder
	 * @param repayResultMsg
	 */
	void noticePartner2Seven(final LnPayOrders lnPayOrder, final String repayResultMsg);
	/**
	 * 7贷查询还款结果
	 * @param req
	 * @param res
	 */
	void sevenRepayResultQuery(G2BReqMsg_DepLoan7_QueryRepayResult req,
							   G2BResMsg_DepLoan7_QueryRepayResult res);
	
	
	/**
	 * 代偿系统分账【七贷】
	 * @param info
	 */
	void compRepaySysSplit4Seven(CompensateRepaySysSplitInfo info);
	
	/**
	 * 代扣还款系统分账【七贷】
	 * */
	void normalRepaySysSplit4Seven(NormalRepaySysSplitInfo info);
	/**
	 * <p>根据借款时间和资产方查询代偿人信息，查询代偿人信息的统一方法</p>
	 * <p>目前支持的资产方：云贷、七贷</p>
	 * <p>1.根据key(compensate_user_info)查询redis中保存的代偿人信息，查询到的代偿人信息是保存于HashMap<String, String>中</p>
	 * 	   <ul>
	 * 	   <li>如果代偿人信息存在于redis，并且没有失效，那么进行步骤2</li>
	 *     <li>如果代偿人信息存在于redis，那么需要根据COMPENSATE_USER_INFO到配置表中查询代偿人配置，
	 *     将代偿人信息逐一查出来保存到map中，并且将map存入redis（设置失效时间为1天）</li>
	 *     </ul>
	 * <p>2.根据loanTime和partnerCode查询返回对应的代偿人信息</p>
	 *     <ul>
	 *     <li>loanTime在separateDate之前返回对应合作方的老代偿人信息</li>
	 *     <li>loanTime在separateDate之后返回对应合作方的新代偿人信息</li>
	 *     </ul>
	 * @param loanTime 借款日期
	 * @param partnerCode 合作方编码       目前只支持云贷和七贷
	 * @return BsUserCompensateVO 代偿人信息
	 */
	BsUserCompensateVO compensaterInfo(Date loanTime, String partnerCode);
	
	
	/**
	 * 云贷-等额本息-还款系统分账记账
	 * @author bianyatian
	 * @param info
	 */
	void repaySysSplit4YunFixedInstallment(NormalRepaySysSplitInfo info);

	/**
	 * 云贷-等本等息-还款系统分账记账
	 * */
	void repaySysSplit4YunFixedPrincipalInterest(NormalRepaySysSplitInfo info);
}
