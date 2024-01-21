package com.pinting.gateway.loan7.in.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.DepLoan7BusinessService;
import com.pinting.gateway.loan7.in.model.ActiveRepayConfirmReqModel;
import com.pinting.gateway.loan7.in.model.ActiveRepayConfirmResModel;
import com.pinting.gateway.loan7.in.model.ActiveRepayPreReqModel;
import com.pinting.gateway.loan7.in.model.ActiveRepayPreResModel;
import com.pinting.gateway.loan7.in.model.ActiveRepaySmsCodeRepeatReqModel;
import com.pinting.gateway.loan7.in.model.ActiveRepaySmsCodeRepeatResModel;
import com.pinting.gateway.loan7.in.model.AgreementNoticeReqModel;
import com.pinting.gateway.loan7.in.model.AgreementNoticeResModel;
import com.pinting.gateway.loan7.in.model.ApplyLoanReqModel;
import com.pinting.gateway.loan7.in.model.ApplyLoanResModel;
import com.pinting.gateway.loan7.in.model.CutRepayConfirmLoans;
import com.pinting.gateway.loan7.in.model.CutRepayConfirmRepayments;
import com.pinting.gateway.loan7.in.model.CutRepayConfirmReqModel;
import com.pinting.gateway.loan7.in.model.CutRepayConfirmResModel;
import com.pinting.gateway.loan7.in.model.FillFinishReqModel;
import com.pinting.gateway.loan7.in.model.FillFinishResModel;
import com.pinting.gateway.loan7.in.model.LateRepay;
import com.pinting.gateway.loan7.in.model.LateRepayReqModel;
import com.pinting.gateway.loan7.in.model.LateRepayResModel;
import com.pinting.gateway.loan7.in.model.LoginReqModel;
import com.pinting.gateway.loan7.in.model.LoginResModel;
import com.pinting.gateway.loan7.in.model.PushBillReqModel;
import com.pinting.gateway.loan7.in.model.PushBillResModel;
import com.pinting.gateway.loan7.in.model.QueryDailyAmountReqModel;
import com.pinting.gateway.loan7.in.model.QueryDailyAmountResModel;
import com.pinting.gateway.loan7.in.model.QueryLoanResultReqModel;
import com.pinting.gateway.loan7.in.model.QueryLoanResultResModel;
import com.pinting.gateway.loan7.in.model.QueryRepayResultReqModel;
import com.pinting.gateway.loan7.in.model.QueryRepayResultResModel;
import com.pinting.gateway.loan7.in.model.QuerySignResultReqModel;
import com.pinting.gateway.loan7.in.model.QuerySignResultResModel;
import com.pinting.gateway.loan7.in.model.Repayment;
import com.pinting.gateway.dafy.in.service.impl.DafyLnLoanServiceImpl;
import com.pinting.gateway.dafy.in.util.DafyInConstant;
import com.pinting.gateway.loan7.in.util.DepLoan7InConstant;
import com.pinting.gateway.loan7.in.util.DepLoan7InMsgUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.dafy.model.QueryBillRepayment;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_ActiveRepayConfirm;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_ActiveRepayPre;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_ActiveRepaySmsCodeRepeat;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_ApplyLoan;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_CutRepayConfirm;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_DailyAvailableAmountLimit;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_FillFinishNotice;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_LateRepayNotice;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_PushBill;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_QueryLoanResult;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_QueryRepayResult;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_QuerySignResult;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_ActiveRepayConfirm;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_ActiveRepayPre;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_ActiveRepaySmsCodeRepeat;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_ApplyLoan;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_CutRepayConfirm;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_DailyAvailableAmountLimit;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_FillFinishNotice;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_LateRepayNotice;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_PushBill;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_QueryLoanResult;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_QueryRepayResult;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_QuerySignResult;
import com.pinting.gateway.hessian.message.loan7.model.Lenders;
import com.pinting.gateway.loan7.in.service.DepLoan7Service;
import com.pinting.gateway.util.Constants;
@Service
public class DepLoan7ServiceImpl implements DepLoan7Service{
	
	
	private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);


	private  Logger log = LoggerFactory.getLogger(DafyLnLoanServiceImpl.class);
	@Autowired
	private DepLoan7BusinessService depLoan7BusinessService;
	
	
	
	@Override
	public LoginResModel login(LoginReqModel reqModel){

		String clientKey = reqModel.getClientKey();
		String clientSecret = reqModel.getClientSecret();

		// 登录数据校验
		LoginResModel resModel = new LoginResModel();
		resModel.setResponseTime(new Date());
		
		log.info("7贷token获取入参:" + clientSecret + "/" + clientKey);
		
		if (StringUtil.isEmpty(clientKey)){
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(DepLoan7InConstant.REUTRN_MSG_LOGIN_CLIENTKEY_EMPTY_FAIL);
			resModel.setResponseTime(new Date());
			return resModel;
        }
		
		
        if (StringUtil.isEmpty(clientSecret)){
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(DepLoan7InConstant.REUTRN_MSG_LOGIN_CLIENTSECRET_EMPTY_FAIL);
			resModel.setResponseTime(new Date());
			return resModel;
        }

        
        if (DepLoan7InMsgUtil.depLoan7InClientKey.equals(clientKey) && DepLoan7InMsgUtil.depLoan7InClientSecret.equals(clientSecret)) {
          
            resModel.setToken(UUID.randomUUID().toString());
            resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_SUCCESS);
			resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_LOGIN_SUCCESS);
			resModel.setResponseTime(new Date());
            try {
				jsClientDaoSupport.setString(resModel.getToken(), "dep_7dai_access_token", 60 * 30);
			} catch (Exception e) {
				// 登录失败
				resModel.setToken(null);
				resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
				resModel.setRespMsg(DepLoan7InConstant.REUTRN_MSG_LOGIN_TOKEN_SET_FAIL);
				resModel.setResponseTime(new Date());
			}
        } else {
			// 登录失败
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(DepLoan7InConstant.REUTRN_MSG_LOGIN_FAIL);
			resModel.setResponseTime(new Date());
        }

		return resModel;
		
	}

	/**
	 * 每日可借额度查询
	 */
	@Override
	public QueryDailyAmountResModel dailyAvailableAmountLimit(
			QueryDailyAmountReqModel reqModel) {
		
		QueryDailyAmountResModel resModel = new QueryDailyAmountResModel();
		
		// 组装报文，向business发送
		G2BReqMsg_DepLoan7_DailyAvailableAmountLimit req = new G2BReqMsg_DepLoan7_DailyAvailableAmountLimit();
		req.setQueryDate(reqModel.getQueryDate());
		// 向business发起请求通知，告知结果
		G2BResMsg_DepLoan7_DailyAvailableAmountLimit res = depLoan7BusinessService
				.sendBsDailyAvailableAmountLimit(req);
		// 获取business响应吗，并装成返回达飞的响应码
		
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DepLoan7InConstant.RETURN_CODE_SUCCESS;
		resModel.setAmount(res.getAmount() != null ? MoneyUtil.multiply(res.getAmount(), 100).longValue():null);
		resModel.setLoanDate(DateUtil.parseDate(DateUtil.format(res.getLoanDate())));
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		return resModel;
	}
	/**
	 * 放款
	 */
	@Override
	public ApplyLoanResModel applyLoan(ApplyLoanReqModel reqModel) {
		
		//校验 userId 、name 、idCard、mobile 、bankCard、bankCode、orderNo、applyTime、businessType、loanId、loanAmount、loanFee、loanTerm、loanRate 
		ApplyLoanResModel resModel = new ApplyLoanResModel();

		// 组装报文，向business发送
		G2BReqMsg_DepLoan7_ApplyLoan req = new G2BReqMsg_DepLoan7_ApplyLoan();
		req.setUserId(reqModel.getUserId());
		req.setName(reqModel.getName());
		req.setIdCard(reqModel.getIdCard());
		req.setMobile(reqModel.getMobile());
		req.setBankCard(reqModel.getBankCard());
		req.setBankCode(reqModel.getBankCode());
		req.setOrderNo(reqModel.getOrderNo());
		req.setApplyTime(reqModel.getApplyTime());
		req.setBusinessType(reqModel.getBusinessType());
		req.setLoanId(reqModel.getLoanId());
		req.setLoanAmount(reqModel.getLoanAmount() == null ? null :  MoneyUtil.divide(reqModel.getLoanAmount(), 100).doubleValue());
		req.setLoanFee(reqModel.getLoanFee() == null ? null :  MoneyUtil.divide(reqModel.getLoanFee(), 100).doubleValue());
		req.setLoanTerm(reqModel.getLoanTerm());
		req.setLoanRate(reqModel.getLoanRate());
		req.setSubjectName(reqModel.getSubjectName());
		req.setPurpose(reqModel.getPurpose());
		req.setCreditAmount(reqModel.getCreditAmount() == null ? null :  MoneyUtil.divide(reqModel.getCreditAmount(), 100).doubleValue());
		req.setLoanedAmount(reqModel.getLoanedAmount() == null ? null :  MoneyUtil.divide(reqModel.getLoanedAmount(), 100).doubleValue());
		req.setCreditLevel(reqModel.getCreditLevel());
		req.setCreditScore(reqModel.getCreditScore());
		req.setLoanTimes(reqModel.getLoanTimes());
		req.setBreakTimes(reqModel.getBreakTimes());
		req.setBreakMaxDays(reqModel.getBreakMaxDays());
		req.setWorkUnit(reqModel.getWorkUnit());
		req.setEducation(reqModel.getEducation());
		req.setMarriage(reqModel.getMarriage());
		req.setMonthlyIncome(reqModel.getMonthlyIncome() == null ? null :  MoneyUtil.divide(reqModel.getMonthlyIncome(), 100).doubleValue());
		
		// 向business发起请求通知，告知结果
		G2BResMsg_DepLoan7_ApplyLoan res = depLoan7BusinessService.applyLoan(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DepLoan7InConstant.RETURN_CODE_SUCCESS;
		
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		return resModel;
	}
	/**
	 * 放款结果查询
	 */
	@Override
	public QueryLoanResultResModel queryLoanResult(QueryLoanResultReqModel reqModel) {
		//校验
		QueryLoanResultResModel resModel = new QueryLoanResultResModel();

		// 组装报文，向business发送
		G2BReqMsg_DepLoan7_QueryLoanResult req = new G2BReqMsg_DepLoan7_QueryLoanResult();
		req.setOrderNo(reqModel.getOrderNo());
		req.setApplyDate(reqModel.getApplyDate());	
		// 向business发起请求通知，告知结果
		G2BResMsg_DepLoan7_QueryLoanResult res = depLoan7BusinessService.queryLoanResult(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DepLoan7InConstant.RETURN_CODE_SUCCESS;
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		resModel.setOrderNo(res.getOrderNo());
		resModel.setBgwOrderNo(res.getBgwOrderNo());
		resModel.setLoanServiceRate(res.getLoanServiceRate());
		resModel.setAgreementNo(res.getAgreementNo());
		resModel.setLoanId(res.getLoanId());
		resModel.setChannel(res.getChannel());
		resModel.setResultCode(res.getResultCode());
		resModel.setResultMsg(res.getResultMsg());
		resModel.setFinishTime(res.getFinishTime());
		
		if(CollectionUtils.isNotEmpty(res.getLenders())){
			List<Lenders> lenderList = new ArrayList<Lenders>();
			for (HashMap<String, Object> lenderMap : res.getLenders()) {
				Lenders lenders = new Lenders();
				lenders.setInvestAmount(MoneyUtil.multiply((Double)lenderMap.get("investAmount"), 100).longValue());
				lenders.setLenderName((String)lenderMap.get("lenderName"));
				lenders.setLenderIdcard((String)lenderMap.get("lenderIdcard"));
				lenders.setMobile((String)lenderMap.get("mobile"));
				lenderList.add(lenders);
			}
			resModel.setLenders(lenderList);
		}		
		return resModel;
	}
	
	
	@Override
	public PushBillResModel pushBill(PushBillReqModel reqModel) {
		//校验
		PushBillResModel resModel = new PushBillResModel();
		List<Repayment> repaymentList = reqModel.getRepayments();
		if (CollectionUtils.isEmpty(repaymentList) && StringUtil.isEmpty(reqModel.getAgreementNo())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR);
			resModel.setResponseTime(new Date());
			return resModel; 
		}
		
		if (CollectionUtils.isNotEmpty(repaymentList)) {
			for (Repayment repayment : repaymentList) {
				if (StringUtil.isBlank(repayment.getRepayId())) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayId为空");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (StringUtil.isBlank(repayment.getStatus())) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":status为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getRepayDate()) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayDate为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getRepaySerial()) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repaySerial为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getTotal()) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":total为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getPrincipal()) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":principal为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getInterest()) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":interest为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
			}
		}
		
		// 组装报文，向business发送
		G2BReqMsg_DepLoan7_PushBill req = new G2BReqMsg_DepLoan7_PushBill();
		req.setUserId(reqModel.getUserId());
		req.setLoanId(reqModel.getLoanId());
		req.setAgreementNo(reqModel.getAgreementNo());
		req.setAgreementUrl(reqModel.getAgreementUrl());
		ArrayList<HashMap<String, Object>>  listMap = new ArrayList<HashMap<String, Object>>();
		for (Repayment repayment : reqModel.getRepayments()) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("repayId", repayment.getRepayId());
			map.put("status", repayment.getStatus());
			map.put("repayDate", repayment.getRepayDate());
			map.put("repaySerial", repayment.getRepaySerial());
			map.put("total", repayment.getTotal() == null ? null :  MoneyUtil.divide(repayment.getTotal(), 100).doubleValue());
			map.put("principal", repayment.getPrincipal() == null ? null :  MoneyUtil.divide(repayment.getPrincipal(), 100).doubleValue());
			map.put("interest", repayment.getInterest() == null ? null :  MoneyUtil.divide(repayment.getInterest(), 100).doubleValue());
			map.put("principalOverdue", repayment.getPrincipalOverdue() == null ? null :  MoneyUtil.divide(repayment.getPrincipalOverdue(), 100).doubleValue());
			map.put("interestOverdue", repayment.getInterestOverdue() == null ? null :  MoneyUtil.divide(repayment.getInterestOverdue(), 100).doubleValue());
			map.put("reservedField1", repayment.getReservedField1());
			map.put("bgwOrderNo", repayment.getBgwOrderNo());
			listMap.add(map);
		}
		req.setRepayments(listMap);

		// 向business发起请求通知，告知结果
		G2BResMsg_DepLoan7_PushBill res = depLoan7BusinessService.pushBill(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DepLoan7InConstant.RETURN_CODE_SUCCESS;
		
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		return resModel;
	}
	
	
	@Override
	public QuerySignResultResModel querySignResult(QuerySignResultReqModel reqModel) {
		//校验
		QuerySignResultResModel resModel = new QuerySignResultResModel();
		
		// 组装报文，向business发送
		G2BReqMsg_DepLoan7_QuerySignResult req = new G2BReqMsg_DepLoan7_QuerySignResult();
		req.setAgreementNo(reqModel.getAgreementNo());
		// 向business发起请求通知，告知结果
		G2BResMsg_DepLoan7_QuerySignResult res = depLoan7BusinessService.querySignResult(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DepLoan7InConstant.RETURN_CODE_SUCCESS;
		
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		resModel.setSignResult(res.getSignResult());
		resModel.setLoanId(res.getLoanId());
		resModel.setAgreementUrl(res.getAgreementUrl());
		
		/*List<Lenders> lenderList = new ArrayList<Lenders>();
		for (HashMap<String, Object> lenderMap : res.getLenders()) {
			Lenders lenders = new Lenders();
			lenders.setInvestAmount(MoneyUtil.multiply((Double)lenderMap.get("investAmount"), 100).longValue());
			lenders.setLenderName((String)lenderMap.get("lenderName"));
			lenders.setLenderIdcard((String)lenderMap.get("lenderIdcard"));
			lenderList.add(lenders);
		}
		resModel.setLenders(lenderList);*/
		return resModel;
	}
	
	@Override
	public ActiveRepayPreResModel activeRepayPre(ActiveRepayPreReqModel reqModel) {
		ActiveRepayPreResModel resModel = new ActiveRepayPreResModel();
		
		resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
		resModel.setRespMsg("接口不可用");
		resModel.setResponseTime(new Date());
		return resModel;
	}
	
	/**
	 * 主动还款确认下单
	 */
	@Override
	public ActiveRepayConfirmResModel activeRepayConfirm(
			ActiveRepayConfirmReqModel reqModel) {
		ActiveRepayConfirmResModel resModel = new ActiveRepayConfirmResModel();

		resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
		resModel.setRespMsg("接口不可用");
		resModel.setResponseTime(new Date());
		return resModel;
	}

	
	/**
	 * 补账完成通知
	 */
	@Override
	public FillFinishResModel fillFinish(FillFinishReqModel reqModel) {
		
		FillFinishResModel resModel = new FillFinishResModel();
		
		// 组装报文，向business发送
		G2BReqMsg_DepLoan7_FillFinishNotice req = new G2BReqMsg_DepLoan7_FillFinishNotice();
		req.setAmount(reqModel.getAmount() == null ? null :  MoneyUtil.divide(reqModel.getAmount(), 100).doubleValue());
		req.setApplyTime(reqModel.getApplyTime());
		req.setFinishTime(reqModel.getFinishTime());
		req.setOrderNo(reqModel.getOrderNo());
		req.setPayOrderNo(reqModel.getPayOrderNo());
		req.setFillType(reqModel.getFillType());
		// 向business发起请求通知，告知结果
		G2BResMsg_DepLoan7_FillFinishNotice res = depLoan7BusinessService.fillFinishNotic(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DepLoan7InConstant.RETURN_CODE_SUCCESS;
		
		
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		return resModel;
	}
	
	/**
	 * 代偿通知
	 */
	@Override
	public LateRepayResModel lateRepay(LateRepayReqModel reqModel) {
		//校验
		LateRepayResModel resModel = new LateRepayResModel();
		
		List<LateRepay> repaymentList = reqModel.getRepayments();
		
		if (CollectionUtils.isNotEmpty(repaymentList)) {
			for (LateRepay repayment : repaymentList) {
				
				if (StringUtil.isBlank(repayment.getRepayId())) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayId为空");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (StringUtil.isBlank(repayment.getUserId())) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":userId为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (StringUtil.isBlank(repayment.getLoanId())) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":loanId为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (StringUtil.isBlank(repayment.getRepayType())) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayType为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (null == repayment.getRepaySerial()) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repaySerial为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getTotal()) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":total为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getPrincipal()) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":principal为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getInterest()) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":interest为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
			}
		}
		
		// 组装报文，向business发送
		G2BReqMsg_DepLoan7_LateRepayNotice req = new G2BReqMsg_DepLoan7_LateRepayNotice();
		req.setOrderNo(reqModel.getOrderNo());
		req.setApplyTime(reqModel.getApplyTime());
		req.setFinishTime(reqModel.getFinishTime());
		req.setTotalAmount(reqModel.getTotalAmount() == null ? null :MoneyUtil.divide(reqModel.getTotalAmount(), 100).doubleValue());
		req.setPayOrderNo(reqModel.getPayOrderNo());
		req.setOrderNo(reqModel.getOrderNo());
		ArrayList<HashMap<String, Object>>  list = new ArrayList<HashMap<String, Object>>();
		for (LateRepay lateRepays : reqModel.getRepayments()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("userId", lateRepays.getUserId());
			map.put("loanId", lateRepays.getLoanId());
			map.put("repayId", lateRepays.getRepayId());
			map.put("repaySerial", lateRepays.getRepaySerial());
			map.put("repayType", lateRepays.getRepayType());
			map.put("total",  lateRepays.getTotal() == null ? null : MoneyUtil.divide(lateRepays.getTotal(), 100).doubleValue());
			map.put("principal", lateRepays.getPrincipal() == null ? null : MoneyUtil.divide(lateRepays.getPrincipal(), 100).doubleValue());
			map.put("interest", lateRepays.getInterest() == null ? null : MoneyUtil.divide(lateRepays.getInterest(), 100).doubleValue());
			map.put("principalOverdue",  lateRepays.getPrincipalOverdue() == null ? null : MoneyUtil.divide(lateRepays.getPrincipalOverdue(), 100).doubleValue());
			map.put("interestOverdue", lateRepays.getInterestOverdue()== null ? null : MoneyUtil.divide(lateRepays.getInterestOverdue(), 100).doubleValue());
			map.put("reservedField1", lateRepays.getReservedField1());
			list.add(map);
		}
		req.setRepayments(list);
		
		
		// 向business发起请求通知，告知结果
		G2BResMsg_DepLoan7_LateRepayNotice res = depLoan7BusinessService.lateRepayNotice(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DepLoan7InConstant.RETURN_CODE_SUCCESS;
				
		
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		return resModel;
	}
	
	/**
	 * 还款结果查询
	 */
	@Override
	public QueryRepayResultResModel queryRepayResul(
			QueryRepayResultReqModel reqModel) {
		
		QueryRepayResultResModel resModel = new QueryRepayResultResModel();
		// 组装报文，向business发送
		G2BReqMsg_DepLoan7_QueryRepayResult req = new G2BReqMsg_DepLoan7_QueryRepayResult();
		req.setApplyDate(reqModel.getApplyDate());
		req.setOrderNo(reqModel.getOrderNo());
		// 向business发起请求通知，告知结果
		G2BResMsg_DepLoan7_QueryRepayResult res = depLoan7BusinessService.queryRepayResult(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DepLoan7InConstant.RETURN_CODE_SUCCESS;
		
		resModel.setBgwOrderNo(res.getBgwOrderNo());
		resModel.setFinishTime(res.getFinishTime());
		resModel.setOrderNo(res.getOrderNo());
		resModel.setResultCode(res.getResultCode());
		resModel.setResultMsg(res.getResultMsg());
		resModel.setChannel(res.getChannel());
		
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		return resModel;
	}
	
	/**
	 * 代扣还款
	 */
	@Override
	public CutRepayConfirmResModel cutRepayConfirm(
			CutRepayConfirmReqModel reqModel) {
		//校验
		CutRepayConfirmResModel resModel = new CutRepayConfirmResModel();

		if(Constants.REPAY_OffLINE_FLAG_Y.equals(reqModel.getIsOffline()) && StringUtil.isEmpty(reqModel.getOffPayOrderNo())){
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
			resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":线下还款时offPayOrderNo必输");
			resModel.setResponseTime(new Date());
			return resModel;
		}

		List<CutRepayConfirmLoans>  cutRepayConfirmLoanList = reqModel.getLoans();
		if (CollectionUtils.isNotEmpty(cutRepayConfirmLoanList)) {
			for (CutRepayConfirmLoans cutRepayConfirmLoans: cutRepayConfirmLoanList) {
				
				if (StringUtil.isBlank(cutRepayConfirmLoans.getLoanId())) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":loanId为空");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (null == cutRepayConfirmLoans.getSingleTotalAmount()) {
					resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":singleTotalAmount为空("+cutRepayConfirmLoans.getLoanId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}

				List<MorphDynaBean> morphDynaBeanList =   cutRepayConfirmLoans.getRepayments();
				//将json对象转换为java对象

				if (CollectionUtils.isNotEmpty(morphDynaBeanList)) {
					for (MorphDynaBean morphDynaBean : morphDynaBeanList) {
						JSONObject obj = new JSONObject().fromObject(morphDynaBean);
						CutRepayConfirmRepayments repayments = (CutRepayConfirmRepayments)JSONObject.toBean(obj,CutRepayConfirmRepayments.class);

						
						if (StringUtil.isBlank(repayments.getRepayId())) {
							resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayId为空");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						if (StringUtil.isBlank(repayments.getStatus())) {
							resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":status为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						
						if (StringUtil.isBlank(repayments.getRepayType())) {
							resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayType为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						
						if (null == repayments.getRepaySerial()) {
							resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repaySerial为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						if (null == repayments.getTotal()) {
							resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":total为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						if (null == repayments.getPrincipal()) {
							resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":principal为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						if (null == repayments.getInterest()) {
							resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DepLoan7InConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":interest为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						
						
					}
				}
				
			}
		}
		
		// 组装报文，向business发送
		G2BReqMsg_DepLoan7_CutRepayConfirm req = new G2BReqMsg_DepLoan7_CutRepayConfirm();
		if(!Constants.REQ_IS_OFFLINE_Y.equals(reqModel.getIsOffline())){
			if (!StringUtil.isEmpty(reqModel.getUserSignNo())) {
				if (StringUtil.isEmpty(reqModel.getPayIP())) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR+":协作代扣失败payIP不存在");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				req.setUserSignNo(reqModel.getUserSignNo());
				req.setPayIP(reqModel.getPayIP());
			}
		}
		req.setOrderNo(reqModel.getOrderNo());
		req.setOverdueFlag(reqModel.getOverdueFlag());
		req.setUserId(reqModel.getUserId());
		req.setName(reqModel.getName());
		req.setIdCard(reqModel.getIdCard());
		req.setMobile(reqModel.getMobile());
		req.setBankCard(reqModel.getBankCard());
		req.setBankCode(reqModel.getBankCode());
		req.setTotalAmount(reqModel.getTotalAmount() == null ? null : MoneyUtil.divide(reqModel.getTotalAmount(), 100).doubleValue());
		req.setIsOffline(reqModel.getIsOffline());
		req.setOffPayOrderNo(reqModel.getOffPayOrderNo());
		req.setApplyTime(reqModel.getRequestTime());
		
		ArrayList<HashMap<String, Object>>  loansMap = new ArrayList<HashMap<String, Object>>();
		for (CutRepayConfirmLoans loans : reqModel.getLoans()) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("loanId", loans.getLoanId());
			map.put("singleTotalAmount", MoneyUtil.divide(loans.getSingleTotalAmount(), 100).doubleValue());
			ArrayList<HashMap<String, Object>> repaymentsMap = new ArrayList<HashMap<String, Object>>();
			List<MorphDynaBean> morph = loans.getRepayments();
			
			for(MorphDynaBean repayment : morph){
				JSONObject obj = new JSONObject().fromObject(repayment);
				CutRepayConfirmRepayments repayments = (CutRepayConfirmRepayments)JSONObject.toBean(obj,CutRepayConfirmRepayments.class);
	         	
				HashMap<String, Object> map1 = new HashMap<>();
				map1.put("repayId", repayments.getRepayId());
				map1.put("status", repayments.getStatus());
				map1.put("repayType", repayments.getRepayType());
				map1.put("repaySerial", repayments.getRepaySerial());
				map1.put("total", repayments.getTotal() == null ? null : MoneyUtil.divide(repayments.getTotal(), 100).doubleValue());
				map1.put("principal",repayments.getPrincipal() == null ? null :  MoneyUtil.divide(repayments.getPrincipal(), 100).doubleValue());
				map1.put("interest", repayments.getInterest() == null ? null : MoneyUtil.divide(repayments.getInterest(), 100).doubleValue());
				map1.put("principalOverdue",repayments.getPrincipalOverdue() == null ? null : MoneyUtil.divide(repayments.getPrincipalOverdue(), 100).doubleValue());
				map1.put("interestOverdue", repayments.getInterestOverdue() == null ? null : MoneyUtil.divide(repayments.getInterestOverdue(), 100).doubleValue());
				map1.put("penalty", repayments.getPenalty() == null ? null : MoneyUtil.divide(repayments.getPenalty(), 100).doubleValue());
				map1.put("reservedField1", repayments.getReservedField1());
				repaymentsMap.add(map1);
			}
			map.put("repayments", repaymentsMap);
			
			loansMap.add(map);
		}
		req.setLoans(loansMap);
		
		// 向business发起请求通知，告知结果
		G2BResMsg_DepLoan7_CutRepayConfirm res = depLoan7BusinessService.cutRepayConfirm(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DepLoan7InConstant.RETURN_CODE_ACCEPT;
		
		
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		return resModel;
	}
	/**
	 * 协议下载地址查询
	 */
	@Override
	public AgreementNoticeResModel agreementNotice(
			AgreementNoticeReqModel reqModel) {
		
		AgreementNoticeResModel resModel = new AgreementNoticeResModel();
		
		// 合规化，7贷协议不再生成，协议下载地址查询接口不再传输内容
		// 组装报文，向business发送
		G2BReqMsg_DepLoan7_AgreementNotice req = new G2BReqMsg_DepLoan7_AgreementNotice();
		req.setLoanId(reqModel.getLoanId());
		// 向business发起请求通知，告知结果
		G2BResMsg_DepLoan7_AgreementNotice res = depLoan7BusinessService.agreementNotice(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DepLoan7InConstant.RETURN_CODE_SUCCESS;
		
		
		resModel.setLoanId(res.getLoanId());
		resModel.setServiceFeeConfirmUrl(res.getServiceFeeConfirmUrl());
		resModel.setDebtTransConfirmUrl(res.getDebtTransConfirmUrl());
		resModel.setDebtTransNoticesUrl(res.getDebtTransNoticesUrl());
		resModel.setBorrowerName(res.getBorrowerName());
		resModel.setBorrowerIdCard(res.getBorrowerIdCard());
		resModel.setDebtTransferInfo(res.getDebtTransferInfo());
		resModel.setDebtTransfers(res.getDebtTransfers());
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());		
		resModel.setResponseTime(new Date());
		return resModel;
	}

	@Override
	public ActiveRepaySmsCodeRepeatResModel activeRepaySmsCodeRepeat(
			ActiveRepaySmsCodeRepeatReqModel reqModel) {
		
		
		ActiveRepaySmsCodeRepeatResModel resModel = new ActiveRepaySmsCodeRepeatResModel();

		// 组装报文，向business发送
		G2BReqMsg_DepLoan7_ActiveRepaySmsCodeRepeat req = new G2BReqMsg_DepLoan7_ActiveRepaySmsCodeRepeat();
		req.setOrderNo(reqModel.getOrderNo());
		// 向business发起请求通知，告知结果
		G2BResMsg_DepLoan7_ActiveRepaySmsCodeRepeat res = depLoan7BusinessService.activeRepaySmsCodeRepeat(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DepLoan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DepLoan7InConstant.RETURN_CODE_ACCEPT;
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		resModel.setBgwOrderNo(res.getBgwOrderNo());
		return resModel;
	}


}
