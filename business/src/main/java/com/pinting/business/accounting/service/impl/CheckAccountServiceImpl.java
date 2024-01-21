
package com.pinting.business.accounting.service.impl;

import com.pinting.business.accounting.finance.model.QuickPayResultInfo;
import com.pinting.business.accounting.finance.service.UserBalanceWithdrawService;
import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.service.CheckAccountService;
import com.pinting.business.dao.Bs19payCheckErrorMapper;
import com.pinting.business.dao.Bs19payCheckMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.enums.BAOFOOTransTypeEnum;
import com.pinting.business.enums.BaoFooCheckBalanceTypeEnum;
import com.pinting.business.enums.HFBankCheckBalanceTypeEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.PayPlatformEnum;
import com.pinting.business.model.Bs19payCheck;
import com.pinting.business.model.Bs19payCheckError;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersExample;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.LnPayOrders;
import com.pinting.business.model.LnPayOrdersExample;
import com.pinting.business.model.dto.OrderResultInfo;
import com.pinting.business.service.common.OrderResultDispatchService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.exception.PTException;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.hessian.message.pay19.model.AccountDetail;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_QueryOrderResult;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_QueryOrderResult;
import com.pinting.gateway.hessian.message.reapal.G2BReqMsg_ReapalQuickPay_ReapalNotify;
import com.pinting.gateway.hessian.message.reapal.G2BResMsg_ReapalQuickPay_ReapalNotify;
import com.pinting.gateway.out.service.ReapalTransportService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CheckAccountServiceImpl implements CheckAccountService {
	private Logger log = LoggerFactory.getLogger(CheckAccountServiceImpl.class);
	@Autowired
	private UserTopUpService userTopUpService;
	@Autowired
	private UserBalanceWithdrawService userBalanceWithdrawService;
	@Autowired
	private SpecialJnlService specialJnlService;
    @Autowired
    private Bs19payCheckMapper bs19payCheckMapper;
    @Autowired
    private Bs19payCheckErrorMapper bs19payCheckErrorMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private ReapalTransportService reapalTransportService;
	@Autowired
	private OrderResultDispatchService orderResultDispatchService;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
	
	@Override
	public void checkAccount4Paying(BsPayOrders order, AccountDetail detail,
			String status, String note) {
		if(detail == null){
			detail = new AccountDetail();
		}

		OrderResultInfo orderResultInfo = new OrderResultInfo();
		if (OrderStatus.SUCCESS.getCode().equals(status)) {
			orderResultInfo.setSuccess(true);
		} else {
			orderResultInfo.setSuccess(false);
		}
		orderResultInfo.setAmount(order.getAmount());
		orderResultInfo.setOrderNo(order.getOrderNo());
		orderResultInfo.setPayOrderNo(detail.getMpOrderNo());
		if(OrderStatus.FAIL.getCode().equals(status)){
			orderResultInfo.setReturnCode(PTMessageEnum.TRANS_ERROR.getCode());
			orderResultInfo.setReturnMsg(StringUtil.isEmpty(note)?"对账确认为失败":note);
		}else{
			orderResultInfo.setReturnCode(PTMessageEnum.TRANS_SUCCESS.getCode());
			orderResultInfo.setReturnMsg("对账确认为成功");
		}
		orderResultInfo.setFinishTime(detail.getFinishTime());

		try {
			//调用边的分发服务
			orderResultDispatchService.dispatch(orderResultInfo);
			// 对账设为一致
			String info = "【" + order.getOrderNo() + "|"
					+ detail.getTradeType() + "|交易成功】对账一致：处理中转"
					+ status + "处理成功";
			checkAccountIsMatch(order.getOrderNo(), order.getAmount(),
					order.getAmount(),
					Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT, info,
					order.getSubAccountId());
		} catch (Exception e) {
			log.info("==================定时任务【对账】处理中转" + status
					+ "处理失败====================");
			// 对账不一致
			String info = "【" + order.getOrderNo() + "|"
					+ detail.getTradeType() + "|交易成功】对账不一致：本地订单处理中，三方"
					+ status + "（处理中转" + status + "处理失败）";
			checkAccountIsUnMatch(order.getOrderNo(),
					order.getAmount(), detail.getAmount(),
					Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
					"订单状态：" + String.valueOf(order.getStatus()),
					status, order.getSubAccountId(), "对账本地处理中", order.getTransType(), Constants.TRANSTERMINAL_FINANCE);
			// 告警
			specialJnlService.warn4Fail(order.getAmount(),
					"定时任务【对账】处理中转" + status + "处理失败",
					order.getOrderNo(), "对账处理中转换",true);
		}
	}

	@Override
	public void checkAccountHf4Paying(BsPayOrders order, AccountDetail detail, String status, String note,
			String channel, String partnerCode, String transTerminal) {
		if(detail == null){
			detail = new AccountDetail();
		}

		OrderResultInfo orderResultInfo = new OrderResultInfo();
		if (OrderStatus.SUCCESS.getCode().equals(status)) {
			orderResultInfo.setSuccess(true);
		} else {
			orderResultInfo.setSuccess(false);
		}
		orderResultInfo.setAmount(order.getAmount());
		orderResultInfo.setOrderNo(order.getOrderNo());
		orderResultInfo.setPayOrderNo(detail.getMpOrderNo());
		if(OrderStatus.FAIL.getCode().equals(status)){
			orderResultInfo.setReturnCode(PTMessageEnum.TRANS_ERROR.getCode());
			orderResultInfo.setReturnMsg(StringUtil.isEmpty(note)?"对账确认为失败":note);
		}else{
			orderResultInfo.setReturnCode(PTMessageEnum.TRANS_SUCCESS.getCode());
			orderResultInfo.setReturnMsg("对账确认为成功");
		}
		orderResultInfo.setFinishTime(detail.getFinishTime());

		try {
			//调用边的分发服务
			orderResultDispatchService.dispatch(orderResultInfo);
			// 对账设为一致
			String info = "【" + order.getOrderNo() + "|"
					+ detail.getTradeType() + "|交易成功】对账一致：处理中转"
					+ status + "处理成功";
			checkAccountIsMatch(order.getOrderNo(), order.getAmount(),
					order.getAmount(),
					Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT, info,
					order.getSubAccountId());
		} catch (Exception e) {
			log.info("==================定时任务【对账】处理中转" + status
					+ "处理失败====================");
			// 对账不一致
			String info = "【" + order.getOrderNo() + "|"
					+ detail.getTradeType() + "|交易成功】对账不一致：本地订单状态或金额与三方不一致，三方"
					+ status + "（处理中转" + status + "处理失败）";
			
			// 对账不一致
			String specialJnlInfo = "【" + order.getOrderNo() + "|"
					+ detail.getTradeType() + "|交易成功】对账不一致：本地订单处理中，三方"
					+ status + "（处理中转" + status + "处理失败）";

			checkHfbankAccountIsUnMatch(order.getOrderNo(),
					order.getAmount(), detail.getAmount(),
					Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
					"订单状态：" + String.valueOf(order.getStatus()),
					status, order.getSubAccountId(), "对账本地处理中", order.getTransType(), transTerminal,
					channel, partnerCode, detail.getOrderNo(), Constants.ORDER_TRANS_CODE_SUCCESS, specialJnlInfo);
			
			// 告警
			specialJnlService.warn4Fail(order.getAmount(),
					"定时任务【对账】处理中转" + status + "处理失败",
					order.getOrderNo(), "对账处理中转换",true);
		}
		
	}
	
	@Override
	public void checkAccount4Paying(BsPayOrders order, AccountDetail detail, String status, String note,
			String merchantNo, String hostSysStatus) {
		if(detail == null){
			detail = new AccountDetail();
		}

		OrderResultInfo orderResultInfo = new OrderResultInfo();
		if (OrderStatus.SUCCESS.getCode().equals(status)) {
			orderResultInfo.setSuccess(true);
		} else {
			orderResultInfo.setSuccess(false);
		}
		orderResultInfo.setAmount(order.getAmount());
		orderResultInfo.setOrderNo(order.getOrderNo());
		orderResultInfo.setPayOrderNo(detail.getMpOrderNo());
		if(OrderStatus.FAIL.getCode().equals(status)){
			orderResultInfo.setReturnCode(PTMessageEnum.TRANS_ERROR.getCode());
			orderResultInfo.setReturnMsg(StringUtil.isEmpty(note)?"对账确认为失败":note);
		}else{
			orderResultInfo.setReturnCode(PTMessageEnum.TRANS_SUCCESS.getCode());
			orderResultInfo.setReturnMsg("对账确认为成功");
		}
		orderResultInfo.setFinishTime(detail.getFinishTime());

		try {
			//调用边的分发服务
			orderResultDispatchService.dispatch(orderResultInfo);
			// 对账设为不一致
			String info = "【" + order.getOrderNo() + "|"
					+ detail.getTradeType() + "|交易成功】对账一不致：本地订单状态或金额与三方不一致(处理中转"
					+ status + "处理成功)";
			// 对账不一致
			String specialJnlInfo = "【" + order.getOrderNo() + "|"
					+ detail.getTradeType() + "|交易成功】对账一致：处理中转"
					+ status + "处理成功";
			checkAccountIsUnMatch(order.getOrderNo(),
					order.getAmount(), detail.getAmount(),
					Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
					"订单状态：" + String.valueOf(order.getStatus()),
					status, order.getSubAccountId(), "对账本地处理中", order.getTransType(), Constants.TRANSTERMINAL_FINANCE,
					merchantNo, null, detail.getOrderNo(), hostSysStatus, specialJnlInfo);
		} catch (Exception e) {
			log.info("==================定时任务【对账】处理中转" + status
					+ "处理失败====================");
			// 对账不一致
			String info = "【" + order.getOrderNo() + "|"
					+ detail.getTradeType() + "|交易成功】对账不一致：本地订单状态或金额与三方不一致，三方"
					+ status + "（处理中转" + status + "处理失败）";
			String specialJnlInfo = "【" + order.getOrderNo() + "|"
					+ detail.getTradeType() + "|交易成功】对账不一致：本地订单处理中，三方"
					+ status + "（处理中转" + status + "处理失败）";
			checkAccountIsUnMatch(order.getOrderNo(),
					order.getAmount(), detail.getAmount(),
					Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
					"订单状态：" + String.valueOf(order.getStatus()),
					status, order.getSubAccountId(), "对账本地处理中", order.getTransType(), Constants.TRANSTERMINAL_FINANCE,
					merchantNo, null, detail.getOrderNo(), hostSysStatus, specialJnlInfo);
			// 告警
			specialJnlService.warn4Fail(order.getAmount(),
					"定时任务【对账】处理中转" + status + "处理失败",
					order.getOrderNo(), "对账处理中转换",true);
		}
	}
	
	@Override
	public void checkAccountIsMatch(String orderNo, Double sysAmount,
			Double doneAmount, String result, String info, Integer subAccountId) {
		Bs19payCheck check = new Bs19payCheck();
        check.setOrderNo(orderNo);
        check.setCheckTime(new Date());
        check.setSysAmount(sysAmount);
        check.setDoneAmount(doneAmount);
        check.setResult(result);
        check.setInfo(info);
        bs19payCheckMapper.insertSelective(check);
        //更新子账户表投资产品对账标志
        if(subAccountId != null){
        	BsSubAccount bsSubAccount = bsSubAccountMapper.selectByPrimaryKey(subAccountId);
			if(isCheckProductType(bsSubAccount)) {
				String checkStatus = bsSubAccount.getCheckStatus();
				BsSubAccount subAcTemp = new BsSubAccount();
				subAcTemp.setId(subAccountId);
				if(!StringUtil.isEmpty(checkStatus)
						&& Constants.CHECK_ACCOUNT_STATUS_FAIL.equals(checkStatus)){
					subAcTemp.setCheckStatus(Constants.CHECK_ACCOUNT_STATUS_FAIL_2_SUCCESS);
				}else{
					subAcTemp.setCheckStatus(Constants.CHECK_ACCOUNT_STATUS_SUCCESS);
				}
				bsSubAccountMapper.updateByPrimaryKeySelective(subAcTemp);
			}
        }

	}

	@Override
	public void checkAccountIsUnMatch(String orderNo, Double sysAmount,
			Double doneAmount, String result, String info, String sysStatus,
			String doneStatus, Integer subAccountId, String smsKeywords) {
		Bs19payCheck check = new Bs19payCheck();
        check.setOrderNo(orderNo);
        check.setCheckTime(new Date());
        check.setSysAmount(sysAmount);
        check.setDoneAmount(doneAmount);
        check.setResult(result);
        check.setInfo(info);
        bs19payCheckMapper.insertSelective(check);

        Bs19payCheckError error = new Bs19payCheckError();
        error.setOrderNo(orderNo);
        error.setSysStatus(sysStatus);
        error.setCheckFileStatus(doneStatus);
        error.setSysAmount(sysAmount);
        error.setDoneAmount(doneAmount);
        error.setCheckFileName(DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd"));
        error.setCreateTime(new Date());
        error.setInfo(info);
        error.setIsDeal(Constants.CHECK_ERROR_JNL_NOT_DEAL);
        bs19payCheckErrorMapper.insertSelective(error);
        //更新对账标志
        if(subAccountId != null){
			BsSubAccount subAc = bsSubAccountMapper.selectByPrimaryKey(subAccountId);
			if(isCheckProductType(subAc)) {
				subAc.setCheckStatus(Constants.CHECK_ACCOUNT_STATUS_FAIL);
				bsSubAccountMapper.updateByPrimaryKeySelective(subAc);
			}
        }
        if (sysAmount == null) {
        	sysAmount = 0d;
        }
        if (doneAmount == null) {
        	doneAmount = 0d;
        }
        //告警
        specialJnlService.warn4Fail(sysAmount == 0 ? doneAmount : sysAmount, info, orderNo, StringUtil.left(smsKeywords, 15), true);

	}
	
	@Override
	public void reapalQuickPayCheckAccount4Paying(BsPayOrders order,
			B2GResMsg_ReapalQuickPay_QueryOrderResult result, String status,
			String note) {
		if(result == null){
			result = new B2GResMsg_ReapalQuickPay_QueryOrderResult();
			result.setAmount(0d);
		}

		if (Constants.TRANS_CARD_BUY_PRODUCT.equals(order.getTransType())
				|| Constants.TRANS_TOP_UP.equals(order.getTransType())) {
			// 快捷卡购买/充值
			if (Constants.CHANNEL_TRS_QUICKPAY.equals(order
					.getChannelTransType())) {

				G2BReqMsg_ReapalQuickPay_ReapalNotify resultReq = new G2BReqMsg_ReapalQuickPay_ReapalNotify();
				G2BResMsg_ReapalQuickPay_ReapalNotify resultResp = new G2BResMsg_ReapalQuickPay_ReapalNotify();
				resultReq.setAmount(order.getAmount());
				resultReq.setOrderNo(order.getOrderNo());
				resultReq.setTradeNo(result.getTradeNo());
				resultReq.setStatus(status);
				
				if(OrderStatus.FAIL.getCode().equals(status)){
					resultReq.setResultMsg(StringUtil.isEmpty(note)?"对账确认为失败":note);
				}else{
					resultReq.setResultMsg("对账确认为成功");
				}
				try {
					QuickPayResultInfo tempReq = new QuickPayResultInfo();
					tempReq.setStatus(resultReq.getStatus());
					tempReq.setErrorCode(resultReq.getResultCode());
					tempReq.setErrorMsg(resultReq.getResultMsg());
					tempReq.setMpOrderId(resultReq.getTradeNo());
					tempReq.setOrderId(resultReq.getOrderNo());
					userTopUpService.notify(tempReq);
					// 对账设为一致
					String info = "【" + order.getOrderNo() + "|融宝快键支付|交易成功】对账一致：处理中转"
							+ status + "处理成功";
					checkAccountIsMatch(order.getOrderNo(), order.getAmount(),
							order.getAmount(),
							Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT, info,
							order.getSubAccountId());
				} catch (Exception e) {
					log.info("==================定时任务【融宝对账】快捷卡购买处理中转" + status
							+ "处理失败====================");
					// 对账不一致
					String info = "【" + order.getOrderNo() + "|融宝快捷支付|交易成功】对账不一致：本地订单处理中，三方"
							+ status + "（处理中转" + status + "处理失败）";
					
					checkAccountIsUnMatch(order.getOrderNo(),
							order.getAmount(), MoneyUtil.divide(result.getAmount(), 100).doubleValue(),
							Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
							"订单状态：" + String.valueOf(order.getStatus()),
							status, order.getSubAccountId(), "融宝对账本地处理中", order.getTransType(), Constants.TRANSTERMINAL_FINANCE);
					// 告警
					specialJnlService.warn4Fail(order.getAmount(),
							"定时任务【融宝对账】快捷卡购买处理中转" + status + "处理失败",
							order.getOrderNo(), "融宝对账处理中转换",true);
				}

			}

		} else {
			// 对账不一致
			String info = "【" + order.getOrderNo() + "|融宝快捷支付|交易成功】对账不一致：本地订单处理中，三方" + status;
			checkAccountIsUnMatch(order.getOrderNo(), order.getAmount(),
					MoneyUtil.divide(result.getAmount(), 100).doubleValue(),
					Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态："
							+ String.valueOf(order.getStatus()), status,
					order.getSubAccountId(), "融宝对账本地处理中", order.getTransType(), Constants.TRANSTERMINAL_FINANCE);
		}
	}

	@Override
	public void checkReapalSingle(BsPayOrders order) {
		//调用快捷支付订单查询接口，判断金额、状态是否一致
		B2GReqMsg_ReapalQuickPay_QueryOrderResult req = new B2GReqMsg_ReapalQuickPay_QueryOrderResult();
		req.setOrderNo(order.getOrderNo());
		B2GResMsg_ReapalQuickPay_QueryOrderResult resp = reapalTransportService.queryOrderResult(req);
		if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
			String reapalStatus = resp.getStatus();
			//融宝快捷查询到的金额是分，所以此处除以100
			Double reapalAmount = MoneyUtil.divide(resp.getAmount(), 100).doubleValue();
			Integer localStatus = order.getStatus();
			Double localAmount = order.getAmount();
			if(localStatus == Constants.ORDER_STATUS_PAYING){
				//对账不一致：本地处理中，三方已成功，本地需置为成功
				if("completed".equals(reapalStatus) && reapalAmount.compareTo(localAmount) == 0){
					reapalQuickPayCheckAccount4Paying(order, resp, OrderStatus.SUCCESS.getCode(), null);
				}
				//对账不一致：本地处理中，三方失败或已关闭，本地需置为失败
				else if("failed".equals(reapalStatus) || "closed".equals(reapalStatus)){
					reapalQuickPayCheckAccount4Paying(order, resp, OrderStatus.FAIL.getCode(), null);
				}
				//对账一致：本地处理中，三方处理中，但需告警
				else{
					//对账一致
					String info = "{" + order.getOrderNo() + "|融宝快捷支付|交易处理中}对账一致";
					checkAccountIsMatch(order.getOrderNo(), localAmount, reapalAmount, 
							Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT, info, order.getSubAccountId());
					//告警
		            specialJnlService.warn4Fail(null, "定时任务{融宝处理中订单对账}单笔订单"+order.getOrderNo()+"交易处理中", order.getOrderNo(), "融宝处理中订单对账",true);
				}
			}
		}
		//查询异常
		else{
			throw new PTException(resp.getResCode(), 
					StringUtil.isEmpty(resp.getResMsg())?"订单结果查询失败":resp.getResMsg());
		}
		
	}

	@Override
	public void checkAccountIsUnMatch(String orderNo, Double sysAmount, Double doneAmount, String result, String info,
			String sysStatus, String doneStatus, Integer subAccountId, String smsKeywords, String transType, String transTerminal) {
		Bs19payCheck check = new Bs19payCheck();
        check.setOrderNo(orderNo);
        check.setCheckTime(new Date());
        check.setSysAmount(sysAmount);
        check.setDoneAmount(doneAmount);
        check.setResult(result);
        check.setInfo(info);
        bs19payCheckMapper.insertSelective(check);

        Bs19payCheckError error = new Bs19payCheckError();
        error.setOrderNo(orderNo);
        error.setSysStatus(sysStatus);
        error.setCheckFileStatus(doneStatus);
        error.setSysAmount(sysAmount);
        error.setDoneAmount(doneAmount);
        error.setCheckFileName(DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd"));
        error.setCreateTime(new Date());
        error.setInfo(info);
        error.setIsDeal(Constants.CHECK_ERROR_JNL_NOT_DEAL);
        error.setNote(transType);
        bs19payCheckErrorMapper.insertSelective(error);
        //更新对账标志
        if(subAccountId != null){
			BsSubAccount subAc = bsSubAccountMapper.selectByPrimaryKey(subAccountId);
			if(isCheckProductType(subAc)) {
				subAc.setCheckStatus(Constants.CHECK_ACCOUNT_STATUS_FAIL);
				bsSubAccountMapper.updateByPrimaryKeySelective(subAc);
			}
        }
        if (sysAmount == null) {
        	sysAmount = 0d;
        }
        if (doneAmount == null) {
        	doneAmount = 0d;
        }
        //告警
        String warnTransType = Constants.TRANS_TYPE_UN_CLEAR;
        specialJnlService.warn4Fail(sysAmount == 0 ? doneAmount : sysAmount, 
        		info+"--(业务类型:"+ warnTransType +")", orderNo, StringUtil.left(smsKeywords, 15), true);
	}
	
	
	/**
	 * 是否对应对账类型
	 * @param subAc
	 * @return
	 */
	private boolean isCheckProductType(BsSubAccount subAc) {
		if (Constants.PRODUCT_TYPE_AUTH_YUN.equals(subAc.getProductType()) ||
				Constants.PRODUCT_TYPE_RED.equals(subAc.getProductType()) ||
				Constants.PRODUCT_TYPE_REG_D.equals(subAc.getProductType()) ||
				Constants.PRODUCT_TYPE_AUTH.equals(subAc.getProductType()) ||
				Constants.PRODUCT_TYPE_REG.equals(subAc.getProductType())  ||
				Constants.PRODUCT_TYPE_AUTH_ZSD.equals(subAc.getProductType()) ||
				Constants.PRODUCT_TYPE_RED_ZSD.equals(subAc.getProductType()) ||
				Constants.PRODUCT_TYPE_AUTH_7.equals(subAc.getProductType()) ||
				Constants.PRODUCT_TYPE_RED_7.equals(subAc.getProductType())) {
			return true;	
		}
		return false;
	}
		
	/**
	 * 告警信息添加业务类型
	 * @param orderNo
	 * @param transTerminal
	 * @param transType
	 * @return
	 */
	private String getWarnTransType(String orderNo, String transTerminal, String transType) {
		String warnTransType = "";
		String channel = "";
		if (!Constants.TRANSTERMINAL_FINANCE.equals(transTerminal) && !Constants.TRANSTERMINAL_LOAN.equals(transTerminal)) {
			warnTransType = transTerminal;
		}
		if (Constants.TRANSTERMINAL_FINANCE.equals(transTerminal)) {
        	BsPayOrdersExample bsPayOrdersExample = new BsPayOrdersExample();
        	bsPayOrdersExample.createCriteria().andOrderNoEqualTo(orderNo);
        	List<BsPayOrders> bsList = bsPayOrdersMapper.selectByExample(bsPayOrdersExample);
        	if (!CollectionUtils.isEmpty(bsList)) {
        		channel = bsList.get(0).getChannel();
        	}
        } else if (Constants.TRANSTERMINAL_LOAN.equals(transTerminal)) {
        	LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
        	lnPayOrdersExample.createCriteria().andOrderNoEqualTo(orderNo);
        	List<LnPayOrders> lnList = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
        	if (!CollectionUtils.isEmpty(lnList)) {
        		channel = lnList.get(0).getChannel();
        	}
        }
        BAOFOOTransTypeEnum	transTypeEnum = BAOFOOTransTypeEnum.getEnumByCode(transType);
        if (transTypeEnum != null && Constants.CHANNEL_BAOFOO.equals(channel)) {
        	warnTransType = transTypeEnum.getDescription();
        }
		return warnTransType;
	}
	
	@Override
	public void checkAccountIsUnMatch(String orderNo, Double sysAmount, Double doneAmount, String result, String info, String sysStatus,
			String doneStatus, Integer subAccountId, String smsKeywords, String transType, String transTerminal,
			String merchantNo, String partnerCode, String bfOrderNo, String hostSysStatus, String specialJnlInfo) {
		Bs19payCheck check = new Bs19payCheck();
        check.setOrderNo(orderNo);
        check.setCheckTime(new Date());
        check.setSysAmount(sysAmount);
        check.setDoneAmount(doneAmount);
        check.setResult(result);
        check.setInfo(specialJnlInfo);
        bs19payCheckMapper.insertSelective(check);

        Bs19payCheckError error = new Bs19payCheckError();
        error.setOrderNo(orderNo);
        error.setSysStatus(sysStatus);
        error.setCheckFileStatus(doneStatus);
        error.setSysAmount(sysAmount);
        error.setDoneAmount(doneAmount);
        error.setCheckFileName(DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd"));
        error.setCreateTime(new Date());
        error.setInfo(info);
        error.setIsDeal(Constants.CHECK_ERROR_JNL_NOT_DEAL);
        error.setNote(transType);
        error.setMerchantNo(merchantNo);
        error.setPartnerCode(getPartnerCode(transTerminal, transType, partnerCode));
        error.setBusinessType(getBusinessType(transTerminal, transType));
        error.setBfOrderNo(bfOrderNo);
        error.setHostSysStatus(hostSysStatus);
        bs19payCheckErrorMapper.insertSelective(error);
        //更新对账标志
        if(subAccountId != null){
			BsSubAccount subAc = bsSubAccountMapper.selectByPrimaryKey(subAccountId);
			if(isCheckProductType(subAc)) {
				subAc.setCheckStatus(Constants.CHECK_ACCOUNT_STATUS_FAIL);
				bsSubAccountMapper.updateByPrimaryKeySelective(subAc);
			}
        }
        
        //告警
        String warnTransType = getWarnTransType(orderNo, transTerminal, transType);
        if (sysAmount == null) {
        	sysAmount = 0d;
        }
        if (doneAmount == null) {
        	doneAmount = 0d;
        }
        specialJnlService.warn4Fail(sysAmount == 0 ? doneAmount : sysAmount, 
        		specialJnlInfo+"--(业务类型:"+ warnTransType +")", orderNo, StringUtil.left(smsKeywords, 15), true);
	}

	/**
	 * 出错账务信息添加资产合作方
	 * @param transTerminal
	 * @param transType
	 * @return
	 */
	private String getPartnerCode(String transTerminal, String transType, String partnerCodeIn) {
		String partnerCode = "";
		if (Constants.TRANSTERMINAL_FINANCE.equals(transTerminal)) {
			if (Constants.TRANS_BALANCE_WITHDRAW.equals(transType) || Constants.TRANS_BONUS_WITHDRAW.equals(transType)) {
				partnerCode = PartnerEnum.BGW.getCode();
			} else {
				if (transType.contains("YUN")) {
					partnerCode = PartnerEnum.YUN_DAI_SELF.getCode();
				} else if (transType.contains("SEVEN")) {
					partnerCode = PartnerEnum.SEVEN_DAI_SELF.getCode();
				} else if (transType.contains("ZSD")) {
					partnerCode = PartnerEnum.ZSD.getCode();
				} else {
					partnerCode = PartnerEnum.ZAN.getCode();
				}
			}
			// 借款端 或者 代扣还款 ， 线下还款，老产品回款，辅助通道转主通道，代偿业务类型取传入的资产方
        } else if (Constants.TRANSTERMINAL_LOAN.equals(transTerminal) || 
        		BAOFOOTransTypeEnum.LN_COMPENSATE.getDescription().equals(transTerminal) ||
        		BAOFOOTransTypeEnum.SYS_RECEIVE_MONEY.getDescription().equals(transTerminal) ||
        		BAOFOOTransTypeEnum.PARTNER_OFFLINE_REPAY.getDescription().equals(transTerminal) ||
        		BAOFOOTransTypeEnum.DEPREPAY_MAIN_WITHHOLD_REPAY.getDescription().equals(transTerminal) ||
        		BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getDescription().equals(transTerminal) ||
        		BAOFOOTransTypeEnum.DEPREPAY_ASSIST_WITHHOLD_REPAY.getDescription().equals(transTerminal)) {
        	partnerCode = partnerCodeIn;
        }
		return partnerCode;
	}
	
	/**
	 * 出错账务信息添加业务类型
	 * @param transTerminal
	 * @param transType
	 * @return
	 */
	private String getBusinessType(String transTerminal, String transType) {
		String businessType = "";
		// 代付(余额提现)
		if (BAOFOOTransTypeEnum.BALANCE_WITHDRAW.getCode().equals(transType)) {
			businessType = BaoFooCheckBalanceTypeEnum.PAID_BALANCE_WITHDRAW.getCode();
		// 代付(奖励金提现)
		} else if (BAOFOOTransTypeEnum.BONUS_WITHDRAW.getCode().equals(transType)) {
			businessType = BaoFooCheckBalanceTypeEnum.PAID_BONUS_WITHDRAW.getCode();
		// 代付(归集户)
		} else if (BAOFOOTransTypeEnum.WITHDRAW_2_DEP_REPAY_CARD.getCode().equals(transType)) {
			businessType = BaoFooCheckBalanceTypeEnum.PAID_2_DEP_REPAY_CARD.getCode();
		// 代扣(还款)
		} else if (BAOFOOTransTypeEnum.DEPREPAY_MAIN_WITHHOLD_REPAY.getCode().equals(transType) ||
				BAOFOOTransTypeEnum.DEPREPAY_ASSIST_WITHHOLD_REPAY.getCode().equals(transType)) {
			businessType = BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode();
		// 钱包转账(线下还款)
		} else if (BAOFOOTransTypeEnum.PARTNER_OFFLINE_REPAY.getCode().equals(transType)) {
			businessType = BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_OFFLINE_REPAY.getCode();
		// 钱包转账(代偿)
		} else if (BAOFOOTransTypeEnum.LN_COMPENSATE.getCode().equals(transType)) {
			businessType = BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_LN_COMPENSATE.getCode();
		// 钱包转账(辅转主)	
		} else if (BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getCode().equals(transType)) {
			businessType = BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_ASSIST_2_MAIN.getCode();
		// 钱包转账(结算合作方)
		} else if (BAOFOOTransTypeEnum.SYS_PARTNER_REVENUE.getCode().equals(transType) ||
				BAOFOOTransTypeEnum.SYS_YUN_REPAY_REVENUE.getCode().equals(transType) ||
				BAOFOOTransTypeEnum.SYS_SEVEN_REPAY_REVENUE.getCode().equals(transType) ||
				BAOFOOTransTypeEnum.SYS_ZSD_REPAY_REVENUE.getCode().equals(transType) ||
				BAOFOOTransTypeEnum.SYS_YUN_REPEAT.getCode().equals(transType) ||
				BAOFOOTransTypeEnum.SYS_ZSD_REPEAT.getCode().equals(transType) ||
				BAOFOOTransTypeEnum.SYS_SEVEN_REPEAT.getCode().equals(transType)) {
			businessType = BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_SETTLE.getCode();
		// 钱包转账(老产品回款)
		} else if (BAOFOOTransTypeEnum.SYS_RECEIVE_MONEY.getCode().equals(transType)) {
			businessType = BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_SYS_RECEIVE_MONEY.getCode();
		}
		return businessType;
	}
	
	@Override
	public void checkHfbankAccountIsUnMatch(String orderNo, Double sysAmount, Double doneAmount, String result,
			String info, String sysStatus, String doneStatus, Integer subAccountId, String smsKeywords,
			String transType, String transTerminal, String channel, String partnerCode, String bfOrderNo,
			String hostSysStatus, String specialJnlInfo) {
		Bs19payCheck check = new Bs19payCheck();
        check.setOrderNo(orderNo);
        check.setCheckTime(new Date());
        check.setSysAmount(sysAmount);
        check.setDoneAmount(doneAmount);
        check.setResult(result);
        check.setInfo(specialJnlInfo);
        bs19payCheckMapper.insertSelective(check);

        Bs19payCheckError error = new Bs19payCheckError();
        String businessType = getHfbankBusinessType(transTerminal, transType, partnerCode);
        error.setOrderNo(orderNo);
        error.setSysStatus(sysStatus);
        error.setCheckFileStatus(doneStatus);
        error.setSysAmount(sysAmount);
        error.setDoneAmount(doneAmount);
        error.setCheckFileName(DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd"));
        error.setCreateTime(new Date());
        error.setInfo(info);
        error.setIsDeal(Constants.CHECK_ERROR_JNL_NOT_DEAL);
        error.setNote(transType);
        error.setMerchantNo(null);
        error.setPartnerCode(partnerCode);
        error.setBusinessType(businessType);
        error.setBfOrderNo(bfOrderNo);
        error.setHostSysStatus(hostSysStatus);
        error.setChannel(PayPlatformEnum.HFBANK.getCode());
        bs19payCheckErrorMapper.insertSelective(error);
        //更新对账标志
        if(subAccountId != null){
			BsSubAccount subAc = bsSubAccountMapper.selectByPrimaryKey(subAccountId);
			if(isCheckProductType(subAc)) {
				subAc.setCheckStatus(Constants.CHECK_ACCOUNT_STATUS_FAIL);
				bsSubAccountMapper.updateByPrimaryKeySelective(subAc);
			}
        }
        
        //告警
        String warnTransType = HFBankCheckBalanceTypeEnum.getEnumByCode(businessType) == null? "":HFBankCheckBalanceTypeEnum.getEnumByCode(businessType).getDescription();
        if (sysAmount == null) {
        	sysAmount = 0d;
        }
        if (doneAmount == null) {
        	doneAmount = 0d;
        }
        specialJnlService.warn4Fail(sysAmount == 0 ? doneAmount : sysAmount, 
        		specialJnlInfo+"--(业务类型:"+ warnTransType +")", orderNo, StringUtil.left(smsKeywords, 15), true);
	}
	
	
	/**
	 * 恒丰对账，出错账务信息添加业务类型
	 * @param transTerminal
	 * @param transType
	 * @return
	 */
	private String getHfbankBusinessType(String transTerminal, String transType, String partnerCode) {
		String businessType = "";
		if (StringUtil.isEmpty(transType)) {
			return businessType;
		}
		if (Constants.TRANSTERMINAL_FINANCE.equals(transTerminal)) {
			// 入金(理财人充值)
			if (BAOFOOTransTypeEnum.HFBANK_TOP_UP.getCode().equals(transType)) {
				businessType = HFBankCheckBalanceTypeEnum.HFBANK_FINANCIAL_TOP_UP.getCode();
			// 入金(平台充值)
			} else if (BAOFOOTransTypeEnum.HFBANK_SYS_TOP_UP.getCode().equals(transType)) {
				businessType = HFBankCheckBalanceTypeEnum.HFBANK_PLATFORM_TOP_UP.getCode();
			// 出金(理财人提现)	
			} else if (BAOFOOTransTypeEnum.HFBANK_BALANCE_WITHDRAW.getCode().equals(transType)) {
				businessType = HFBankCheckBalanceTypeEnum.HFBANK_FINANCIAL_WITHDRAW.getCode();
			// 出金(平台提现)	
			} else if (BAOFOOTransTypeEnum.HFBANK_SYS_WITHDRAW.getCode().equals(transType)) {
				businessType = HFBankCheckBalanceTypeEnum.HFBANK_PLATFORM_WITHDRAW.getCode();
			}
		} else if (Constants.TRANSTERMINAL_LOAN.equals(transTerminal)) {
			// 云贷放款
			if (BAOFOOTransTypeEnum.HFBANK_YUN_LOAN.getCode().equals(transType) && PartnerEnum.YUN_DAI_SELF.getCode().equals(partnerCode)) {
				businessType = HFBankCheckBalanceTypeEnum.HFBANK_YUN_LOAN.getCode();
			// 7贷放款
			} else if (BAOFOOTransTypeEnum.HFBANK_SEVEN_LOAN.getCode().equals(transType) && PartnerEnum.SEVEN_DAI_SELF.getCode().equals(partnerCode)) {
				businessType = HFBankCheckBalanceTypeEnum.HFBANK_SEVEN_LOAN.getCode();
			} 
		}
		return businessType;
	}
	
}
