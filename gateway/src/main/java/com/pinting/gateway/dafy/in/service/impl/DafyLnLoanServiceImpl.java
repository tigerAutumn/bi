package com.pinting.gateway.dafy.in.service.impl;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.SendBusinessService;
import com.pinting.gateway.dafy.in.model.*;
import com.pinting.gateway.dafy.in.service.DafyInLoanService;
import com.pinting.gateway.dafy.in.util.DafyInConstant;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.hessian.message.dafy.*;
import com.pinting.gateway.hessian.message.dafy.model.Lenders;
import com.pinting.gateway.util.Constants;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DafyLnLoanServiceImpl implements DafyInLoanService {
	private static final String dafyClientKey = "channeldafykey1230";
	private static final String dafyClientSecret = "dafy7987!&Ke6!3";

	
	private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);

	private  Logger log = LoggerFactory.getLogger(DafyLnLoanServiceImpl.class);
	@Autowired
	private SendBusinessService sendBusinessService;
	
	
	
	@Override
	public LoginResModel login(LoginReqModel reqModel){

		String clientKey = reqModel.getClientKey();
		String clientSecret = reqModel.getClientSecret();

		// 登录数据校验
		LoginResModel resModel = new LoginResModel();
		resModel.setResponseTime(new Date());
		
		log.info("自主放款token获取入参:" + clientSecret + "/" + clientKey);
		
		if (StringUtil.isEmpty(clientKey)){
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(DafyInConstant.REUTRN_MSG_LOGIN_CLIENTKEY_EMPTY_FAIL);
			resModel.setResponseTime(new Date());
			return resModel;
        }
		
		
        if (StringUtil.isEmpty(clientSecret)){
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(DafyInConstant.REUTRN_MSG_LOGIN_CLIENTSECRET_EMPTY_FAIL);
			resModel.setResponseTime(new Date());
			return resModel;
        }

        
        if (dafyClientKey.equals(clientKey) && dafyClientSecret.equals(clientSecret)) {
          
            resModel.setToken(UUID.randomUUID().toString());
            resModel.setRespCode(DafyInConstant.RETURN_CODE_SUCCESS);
			resModel.setRespMsg(DafyInConstant.RETURN_MSG_LOGIN_SUCCESS);
			resModel.setResponseTime(new Date());
            try {
				jsClientDaoSupport.setString(resModel.getToken(), "dafy_loan_access_token", 60 * 30);
			} catch (Exception e) {
				// 登录失败
				resModel.setToken(null);
				resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
				resModel.setRespMsg(DafyInConstant.REUTRN_MSG_LOGIN_TOKEN_SET_FAIL);
				resModel.setResponseTime(new Date());
			}
        } else {
			// 登录失败
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(DafyInConstant.REUTRN_MSG_LOGIN_FAIL);
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
		G2BReqMsg_DafyLoan_DailyAvailableAmountLimit req = new G2BReqMsg_DafyLoan_DailyAvailableAmountLimit();
		req.setQueryDate(reqModel.getQueryDate());
		// 向business发起请求通知，告知结果
		G2BResMsg_DafyLoan_DailyAvailableAmountLimit res = sendBusinessService
				.sendBsDailyAvailableAmountLimit(req);
		// 获取business响应吗，并装成返回达飞的响应码
		
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_SUCCESS;
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
		G2BReqMsg_DafyLoan_ApplyLoan req = new G2BReqMsg_DafyLoan_ApplyLoan();
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
		req.setAddress(reqModel.getAddress());
		req.setEmail(reqModel.getEmail());
		
		// 向business发起请求通知，告知结果
		G2BResMsg_DafyLoan_ApplyLoan res = sendBusinessService.applyLoan(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_SUCCESS;
		
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
		G2BReqMsg_DafyLoan_QueryLoanResult req = new G2BReqMsg_DafyLoan_QueryLoanResult();
		req.setOrderNo(reqModel.getOrderNo());
		req.setApplyDate(reqModel.getApplyDate());	
		// 向business发起请求通知，告知结果
		G2BResMsg_DafyLoan_QueryLoanResult res = sendBusinessService.queryLoanResult(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_SUCCESS;
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		resModel.setOrderNo(res.getOrderNo());
		resModel.setBgwOrderNo(res.getBgwOrderNo());
		resModel.setAgreementNo(res.getAgreementNo());
		resModel.setLoanServiceRate(res.getLoanServiceRate());
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
		if (CollectionUtils.isNotEmpty(repaymentList)) {
			for (Repayment repayment : repaymentList) {
				if (StringUtil.isBlank(repayment.getRepayId())) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayId为空");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (StringUtil.isBlank(repayment.getStatus())) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":status为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getRepayDate()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayDate为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getRepaySerial()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repaySerial为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getTotal()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":total为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getPrincipal()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":principal为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getInterest()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":interest为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
			}
		}
		
		// 组装报文，向business发送
		G2BReqMsg_DafyLoan_PushBill req = new G2BReqMsg_DafyLoan_PushBill();
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
		G2BResMsg_DafyLoan_PushBill res = sendBusinessService.pushBill(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_SUCCESS;
		
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
		G2BReqMsg_DafyLoan_QuerySignResult req = new G2BReqMsg_DafyLoan_QuerySignResult();
		req.setAgreementNo(reqModel.getAgreementNo());
		// 向business发起请求通知，告知结果
		G2BResMsg_DafyLoan_QuerySignResult res = sendBusinessService.querySignResult(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_SUCCESS;
		
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
		
		//校验
		ActiveRepayPreResModel resModel = new ActiveRepayPreResModel();
		
		List<Repayment> repaymentList = reqModel.getRepayments();
		
		if (CollectionUtils.isNotEmpty(repaymentList)) {
			for (Repayment repayment : repaymentList) {
				if (StringUtil.isBlank(repayment.getRepayId())) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayId为空");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (StringUtil.isBlank(repayment.getStatus())) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":status为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (StringUtil.isBlank(repayment.getRepayType())) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayType为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (null == repayment.getRepaySerial()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repaySerial为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getTotal()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":total为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getPrincipal()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":principal为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getInterest()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":interest为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
			}
		}
		
		
		// 组装报文，向business发送
		G2BReqMsg_DafyLoan_ActiveRepayPre req = new G2BReqMsg_DafyLoan_ActiveRepayPre();
		req.setOrderNo(reqModel.getOrderNo());
		req.setUserId(reqModel.getUserId());
		req.setName(reqModel.getName());
		req.setIdCard(reqModel.getIdCard());
		req.setMobile(reqModel.getMobile());
		req.setBankCard(reqModel.getBankCard());
		req.setBankCode(reqModel.getBankCode());
		req.setLoanId(reqModel.getLoanId());
		req.setTotalAmount(reqModel.getTotalAmount() == null ? null : MoneyUtil.divide(reqModel.getTotalAmount(), 100).doubleValue());
		ArrayList<HashMap<String, Object>>  listMap = new ArrayList<HashMap<String, Object>>();
		for (Repayment repayment : reqModel.getRepayments()) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("repayId", repayment.getRepayId());
			map.put("status", repayment.getStatus());
			map.put("repayType", repayment.getRepayType());
			map.put("repaySerial", repayment.getRepaySerial());
			map.put("total", repayment.getTotal() == null ? null :MoneyUtil.divide(repayment.getTotal(), 100).doubleValue());
			map.put("principal", repayment.getPrincipal() == null ? null :MoneyUtil.divide(repayment.getPrincipal(), 100).doubleValue());
			map.put("interest", repayment.getInterest() == null ? null :MoneyUtil.divide(repayment.getInterest(), 100).doubleValue());
			map.put("principalOverdue", repayment.getPrincipalOverdue() == null ? null :MoneyUtil.divide(repayment.getPrincipalOverdue(), 100).doubleValue());
			map.put("interestOverdue", repayment.getInterestOverdue() == null ? null : MoneyUtil.divide(repayment.getInterestOverdue(), 100).doubleValue());
			map.put("reservedField1", repayment.getReservedField1());
			
			listMap.add(map);
		}
		req.setRepayments(listMap);

		// 向business发起请求通知，告知结果
		G2BResMsg_DafyLoan_ActiveRepayPre res = sendBusinessService.activeRepayPre(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_SUCCESS;
		
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		resModel.setBgwOrderNo(res.getBgwOrderNo());
		return resModel;
	}
	
	/**
	 * 主动还款确认下单
	 */
	@Override
	public ActiveRepayConfirmResModel activeRepayConfirm(
			ActiveRepayConfirmReqModel reqModel) {
		ActiveRepayConfirmResModel resModel = new ActiveRepayConfirmResModel();

		// 组装报文，向business发送
		G2BReqMsg_DafyLoan_ActiveRepayConfirm req = new G2BReqMsg_DafyLoan_ActiveRepayConfirm();
		req.setBgwOrderNo(reqModel.getBgwOrderNo());
		req.setSmsCode(reqModel.getSmsCode());
		// 向business发起请求通知，告知结果
		G2BResMsg_DafyLoan_ActiveRepayConfirm res = sendBusinessService.activeRepayConfirm(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if(PTMessageEnum.MOBILE_CODE_WRONG_ERROR.getCode().equals(res.getResCode())){
			resModel.setRespCode(DafyInConstant.RETURN_CODE_MOBILE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}else if(!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_ACCEPT;
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
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
		G2BReqMsg_DafyLoan_FillFinishNotice req = new G2BReqMsg_DafyLoan_FillFinishNotice();
		req.setAmount(reqModel.getAmount() == null ? null :  MoneyUtil.divide(reqModel.getAmount(), 100).doubleValue());
		req.setApplyTime(reqModel.getApplyTime());
		req.setFinishTime(reqModel.getFinishTime());
		req.setOrderNo(reqModel.getOrderNo());
		req.setPayOrderNo(reqModel.getPayOrderNo());
		req.setFillType(reqModel.getFillType());
		// 向business发起请求通知，告知结果
		G2BResMsg_DafyLoan_FillFinishNotice res = sendBusinessService.fillFinishNotic(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_SUCCESS;
		
		
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
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayId为空");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (StringUtil.isBlank(repayment.getUserId())) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":userId为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (StringUtil.isBlank(repayment.getLoanId())) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":loanId为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (StringUtil.isBlank(repayment.getRepayType())) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayType为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (null == repayment.getRepaySerial()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repaySerial为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getTotal()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":total为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getPrincipal()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":principal为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				if (null == repayment.getInterest()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":interest为空("+repayment.getRepayId()+")");
					resModel.setResponseTime(new Date());
					return resModel;
				}
			}
		}
		
		// 组装报文，向business发送
		G2BReqMsg_DafyLoan_LateRepayNotice req = new G2BReqMsg_DafyLoan_LateRepayNotice();
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
		G2BResMsg_DafyLoan_LateRepayNotice res = sendBusinessService.lateRepayNotice(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_SUCCESS;
				
		
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
		G2BReqMsg_DafyLoan_QueryRepayResult req = new G2BReqMsg_DafyLoan_QueryRepayResult();
		req.setApplyDate(reqModel.getApplyDate());
		req.setOrderNo(reqModel.getOrderNo());
		// 向business发起请求通知，告知结果
		G2BResMsg_DafyLoan_QueryRepayResult res = sendBusinessService.queryRepayResult(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_SUCCESS;
		
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
		
		List<CutRepayConfirmLoans>  cutRepayConfirmLoanList = reqModel.getLoans();
		if (CollectionUtils.isNotEmpty(cutRepayConfirmLoanList)) {
			for (CutRepayConfirmLoans cutRepayConfirmLoans: cutRepayConfirmLoanList) {
				
				if (StringUtil.isBlank(cutRepayConfirmLoans.getLoanId())) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":loanId为空");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (null == cutRepayConfirmLoans.getSingleTotalAmount()) {
					resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":singleTotalAmount为空("+cutRepayConfirmLoans.getLoanId()+")");
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
							resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayId为空");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						if (StringUtil.isBlank(repayments.getStatus())) {
							resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":status为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						
						if (StringUtil.isBlank(repayments.getRepayType())) {
							resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayType为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						
						if (null == repayments.getRepaySerial()) {
							resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repaySerial为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						if (null == repayments.getTotal()) {
							resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":total为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						if (null == repayments.getPrincipal()) {
							resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":principal为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						if (null == repayments.getInterest()) {
							resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
							resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":interest为空("+cutRepayConfirmLoans.getLoanId()+"-"+repayments.getRepayId()+")");
							resModel.setResponseTime(new Date());
							return resModel;
						}
						
						
					}
				}
				
			}
		}
		
		// 组装报文，向business发送
		G2BReqMsg_DafyLoan_CutRepayConfirm req = new G2BReqMsg_DafyLoan_CutRepayConfirm();
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
		req.setOrderNo(reqModel.getOrderNo());
		req.setOverdueFlag(reqModel.getOverdueFlag());
		req.setUserId(reqModel.getUserId());
		req.setName(reqModel.getName());
		req.setIdCard(reqModel.getIdCard());
		req.setMobile(reqModel.getMobile());
		req.setBankCard(reqModel.getBankCard());
		req.setBankCode(reqModel.getBankCode());
		req.setTotalAmount(reqModel.getTotalAmount() == null ? null : MoneyUtil.divide(reqModel.getTotalAmount(), 100).doubleValue());
		
		ArrayList<HashMap<String, Object>>  loansMap = new ArrayList<HashMap<String, Object>>();
		for (CutRepayConfirmLoans loans : reqModel.getLoans()) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("loanId", loans.getLoanId());
			map.put("singleTotalAmount", MoneyUtil.divide(loans.getSingleTotalAmount(), 100).doubleValue());
			ArrayList<HashMap<String, Object>> repaymentsMap = new ArrayList<HashMap<String, Object>>();
			List<MorphDynaBean> morph = loans.getRepayments();
			
			for(MorphDynaBean repayment : loans.getRepayments()){
				HashMap<String, Object> map1 = new HashMap<>();
				
				//将json对象转换为java对象
				JSONObject obj = new JSONObject().fromObject(repayment);
				CutRepayConfirmRepayments repayments = (CutRepayConfirmRepayments)JSONObject.toBean(obj,CutRepayConfirmRepayments.class);
				
				map1.put("repayId", repayments.getRepayId());
				map1.put("status", repayments.getStatus());
				map1.put("repayType", repayments.getRepayType());
				map1.put("repaySerial", repayments.getRepaySerial());
				map1.put("total", repayments.getTotal() == null ? null : MoneyUtil.divide(repayments.getTotal(), 100).doubleValue());
				map1.put("principal",repayments.getPrincipal() == null ? null :  MoneyUtil.divide(repayments.getPrincipal(), 100).doubleValue());
				map1.put("interest", repayments.getInterest() == null ? null : MoneyUtil.divide(repayments.getInterest(), 100).doubleValue());
				map1.put("principalOverdue",repayments.getPrincipalOverdue() == null ? null : MoneyUtil.divide(repayments.getPrincipalOverdue(), 100).doubleValue());
				map1.put("interestOverdue", repayments.getInterestOverdue() == null ? null : MoneyUtil.divide(repayments.getInterestOverdue(), 100).doubleValue());
				map1.put("reservedField1", repayments.getReservedField1());
				repaymentsMap.add(map1);
			}
			map.put("repayments", repaymentsMap);
			
			loansMap.add(map);
		}
		req.setLoans(loansMap);
		
		// 向business发起请求通知，告知结果
		G2BResMsg_DafyLoan_CutRepayConfirm res = sendBusinessService.cutRepayConfirm(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_ACCEPT;
		
		
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
		
		// 组装报文，向business发送
		G2BReqMsg_DafyLoan_AgreementNotice req = new G2BReqMsg_DafyLoan_AgreementNotice();
		req.setLoanId(reqModel.getLoanId());
		// 向business发起请求通知，告知结果
		G2BResMsg_DafyLoan_AgreementNotice res = sendBusinessService.agreementNotice(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_SUCCESS;
		resModel.setAgreements(res.getAgreements());
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
		G2BReqMsg_DafyLoan_ActiveRepaySmsCodeRepeat req = new G2BReqMsg_DafyLoan_ActiveRepaySmsCodeRepeat();
		req.setOrderNo(reqModel.getOrderNo());
		// 向business发起请求通知，告知结果
		G2BResMsg_DafyLoan_ActiveRepaySmsCodeRepeat res = sendBusinessService.activeRepaySmsCodeRepeat(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		
		String respCode = DafyInConstant.RETURN_CODE_ACCEPT;
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		resModel.setBgwOrderNo(res.getBgwOrderNo());
		return resModel;
	}

}
