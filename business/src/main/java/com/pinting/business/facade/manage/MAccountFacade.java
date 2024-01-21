package com.pinting.business.facade.manage;

import com.pinting.business.accounting.finance.model.SysActTransSendInfo;
import com.pinting.business.accounting.finance.service.SysProductBuyService;
import com.pinting.business.accounting.service.CheckAccountService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.accounting.service.SysWithdrawService;
import com.pinting.business.dao.BsBankCardMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.manage.*;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Withdraw_SysWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Withdraw_SysWithdraw;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.out.service.DafyTransportService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @Project: business
 * @Title: AccountFacade.java
 * @author Huang MengJian
 * @date 2015-2-1 下午3:34:07
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("MAccount")
public class MAccountFacade{
	@Autowired
	private MAccountService accountService;
	
	@Autowired
	private DafyTransportService dafyTransportService;
	
	@Autowired
	private SysWithdrawService sysWithdrawService;
	
	@Autowired
	private PayOrdersService payOrdersService;
	
	@Autowired
	private SysProductBuyService sysProductBuyService;
	
	@Autowired
	private MUserTransDetailService transDetailService;
	
	@Autowired
	private CheckAccountService checkAccountService;
	
	@Autowired
	private AgentService agentService;

	@Autowired
	private BsSubAccountService subAccountService;
	
	@Autowired
	private BsSysBalanceDailyFileService bsSysBalanceDailyFileService;
	
	@Autowired
	private BsUserService bsUserService;
	
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private BsBankCardMapper bsBankCardMapper;
	
	
	private Logger log = LoggerFactory.getLogger(MAccountFacade.class);
	
	public void checkErrorListQuery(ReqMsg_MAccount_CheckErrorListQuery reqMsg,ResMsg_MAccount_CheckErrorListQuery resMsg){
		
		ArrayList<BsCheckErrorJnlVO> valueList = (ArrayList<BsCheckErrorJnlVO>) accountService.findCheckErrorJnlListByPage(reqMsg.getBeginTime(),reqMsg.getOverTime(),reqMsg.getPageNum(),reqMsg.getNumPerPage());
		
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList); 
		
		resMsg.setValueList(hashList);
		
		int total = accountService.countCheckErrorList();
		
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
		resMsg.setTotalRows(total);
		
	}
	
	/**
	 * 我要处理
	 * @param reqMsg
	 * @param resMsg
	 */
	public void checkErrorSave(ReqMsg_MAccount_CheckErrorSave reqMsg,ResMsg_MAccount_CheckErrorSave resMsg)throws PTMessageException{
		
		BsCheckErrorJnl checkError = new BsCheckErrorJnl();
		checkError.setIsDeal(Constants.CHECKERROR_STATUS_DEAL);
		checkError.setId(reqMsg.getId());
		checkError.setDealUserId(reqMsg.getmUserid());
		
		boolean flag = accountService.updateCheckErrorById(checkError);
		
		if(!flag){
			throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
		}
		
		
	}
	/**
	 * 币港湾投资收益统计查询
	 * @param reqMsg
	 * @param resMsg
	 */
	public void profitLossListQuery(ReqMsg_MAccount_ProfitLossListQuery reqMsg, ResMsg_MAccount_ProfitLossListQuery resMsg) {
		ArrayList<BsProfitLoss> valueList = (ArrayList<BsProfitLoss>) accountService.findProfitLossListQueryByPage(reqMsg.getBeginTime(),reqMsg.getOverTime(),reqMsg.getPageNum(),reqMsg.getNumPerPage());
		
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList); 
		
		resMsg.setValueList(hashList);
		
		int total = accountService.countProfitLossList();
		
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
		resMsg.setTotalRows(total);
	}
	/**
	 * 未来30日因兑付查询
	 * @param reqMsg
	 * @param resMsg
	 */
	public void cashScheduleListQuery(ReqMsg_MAccount_CashScheduleListQuery reqMsg, ResMsg_MAccount_CashScheduleListQuery resMsg) {
		ArrayList<BsCashSchedule30> valueList = (ArrayList<BsCashSchedule30>) accountService.findCashScheduleListQueryByPage(reqMsg.getBeginTime(),reqMsg.getOverTime(),reqMsg.getPageNum(),reqMsg.getNumPerPage());
		
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList); 
		
		resMsg.setValueList(hashList);
		
		int total = accountService.countCashScheduleList(reqMsg.getBeginTime(),reqMsg.getOverTime());
		
		/** 未来30天应付本金合计 */
		double totalCashBaseAmount30 = accountService.sumCashBaseAmount(reqMsg.getBeginTime(), reqMsg.getOverTime());
		
		/** 未来30天应付利息合计 */
		double totalBashInterestAmount30 = accountService.sumBashInterestAmount(reqMsg.getBeginTime(), reqMsg.getOverTime());
		
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
		resMsg.setTotalRows(total);
		resMsg.setTotalCashBaseAmount30(totalCashBaseAmount30);
		resMsg.setTotalBashInterestAmount30(totalBashInterestAmount30);
	}
	
	public void transferListQuery(ReqMsg_MAccount_TransferListQuery reqMsg, ResMsg_MAccount_TransferListQuery resMsg) {
		ArrayList<BsAccountJnlVO> valueList = (ArrayList<BsAccountJnlVO>) accountService.findAccountListQueryByPage(reqMsg.getStatus(),reqMsg.getBeginTime(),reqMsg.getOverTime(),reqMsg.getPageNum(),reqMsg.getNumPerPage());
		
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList); 
		
		resMsg.setValueList(hashList);
		
		int total = accountService.countTransferList(reqMsg.getStatus(),reqMsg.getBeginTime(),reqMsg.getOverTime());
		
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
		resMsg.setTotalRows(total);
	}
	/**
	 * 订单查询-管理员查询，导出
	 * @param reqMsg
	 * @param resMsg
	 */
	public void orderListQuery(ReqMsg_MAccount_OrderListQuery reqMsg, ResMsg_MAccount_OrderListQuery resMsg) {

		log.error("service data req time" + new Date().getTime());
		ArrayList<BsPayOrdersVO> valueList = (ArrayList<BsPayOrdersVO>) accountService.findOrdersListQueryByPage(
				reqMsg.getUserMobile(),reqMsg.getUserName(),
				reqMsg.getOrderNo(),reqMsg.getAccountType(),
				reqMsg.getTransType(),reqMsg.getChannelTransType(),
				reqMsg.getBankName(),reqMsg.getBankCardNo(),
				reqMsg.getBeginTime(),reqMsg.getOverTime(),
				reqMsg.getPageNum(),reqMsg.getNumPerPage(),
				reqMsg.getOrderDirection(),reqMsg.getOrderField(),
				reqMsg.getStatus(),reqMsg.getBuyBankType(),
				reqMsg.getAgentId(),reqMsg.getMobile(),
				reqMsg.getIdCard(),reqMsg.getPayChannel(), reqMsg.getReturnMsg(),
				reqMsg.getAgentIds(),reqMsg.getNonAgentId(),reqMsg.getUserId(),reqMsg.getsShowTerminal());
		
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList);
		log.error("service data res time" + new Date().getTime());
		resMsg.setValueList(hashList);
		log.error("service count req time" + new Date().getTime());
		int total = accountService.selectCountPayOrder(reqMsg.getUserMobile(),reqMsg.getUserName(),
				reqMsg.getOrderNo(),reqMsg.getAccountType(),
				reqMsg.getTransType(),reqMsg.getChannelTransType(),
				reqMsg.getBankName(),reqMsg.getBankCardNo(),
				reqMsg.getBeginTime(),reqMsg.getOverTime(),
				reqMsg.getStatus(),reqMsg.getBuyBankType(),
				reqMsg.getAgentId(),reqMsg.getMobile(),
				reqMsg.getIdCard(),reqMsg.getPayChannel(), reqMsg.getReturnMsg(),
				reqMsg.getAgentIds(),reqMsg.getNonAgentId(),reqMsg.getUserId(),reqMsg.getsShowTerminal());
		log.error("service count res time" + new Date().getTime());
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
		resMsg.setTotalRows(total);
		List<BsPayOrders> buyBankTypeList = payOrdersService.findBuyBankTypeList();
		resMsg.setBuyBankTypeList(BeanUtils.classToArrayList(buyBankTypeList));

		log.error("service req time" + new Date().getTime());
		/*List<BsAgent> agents = agentService.agentNameGroupByList(new BsAgent());
		resMsg.setAgentList(BeanUtils.classToArrayList(agents));*/
	}
	
	
	public void orderListQuery4Normal(ReqMsg_MAccount_OrderListQuery4Normal reqMsg, ResMsg_MAccount_OrderListQuery4Normal resMsg) {

		Date days90 = DateUtil.parseDateTime(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -89))+" 00:00:00");
		if(reqMsg.getOverTime() != null && reqMsg.getOverTime().compareTo(days90) < 0) {
			resMsg.setValueList(null);
			resMsg.setPageNum(reqMsg.getPageNum());
			resMsg.setNumPerPage(reqMsg.getNumPerPage());
			resMsg.setTotalRows(0);
			List<BsPayOrders> buyBankTypeList = payOrdersService.findBuyBankTypeList();
			resMsg.setBuyBankTypeList(BeanUtils.classToArrayList(buyBankTypeList));
		}else{
			if(reqMsg.getBeginTime() != null && reqMsg.getBeginTime().compareTo(days90) < 0){
				reqMsg.setBeginTime(days90);
			}
			log.error("service data req time" + new Date().getTime());
			ArrayList<BsPayOrdersVO> valueList = (ArrayList<BsPayOrdersVO>) accountService.findOrdersListQueryByPage4Normal(
					reqMsg.getUserMobile(), reqMsg.getUserName(), reqMsg.getTransType(), reqMsg.getChannelTransType(), 
					reqMsg.getBeginTime(),reqMsg.getOverTime(), reqMsg.getPageNum(),reqMsg.getNumPerPage(), reqMsg.getStatus(),
					reqMsg.getBuyBankType(), reqMsg.getAgentId(), reqMsg.getPayChannel(), reqMsg.getReturnMsg(), 
					reqMsg.getAgentIds(),reqMsg.getNonAgentId(),reqMsg.getOrderDirection(),reqMsg.getOrderField());
			
			ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList);
			log.error("service data res time" + new Date().getTime());
			resMsg.setValueList(hashList);
			log.error("service count req time" + new Date().getTime());
			int total = accountService.selectCountPayOrder4Normal(reqMsg.getUserMobile(), reqMsg.getUserName(), 
					reqMsg.getTransType(), reqMsg.getChannelTransType(), reqMsg.getBeginTime(),reqMsg.getOverTime(), 
					reqMsg.getPageNum(),reqMsg.getNumPerPage(), reqMsg.getStatus(),reqMsg.getBuyBankType(), reqMsg.getAgentId(), 
					reqMsg.getPayChannel(), reqMsg.getReturnMsg(), reqMsg.getAgentIds(),reqMsg.getNonAgentId());
			log.error("service count res time" + new Date().getTime());
			resMsg.setPageNum(reqMsg.getPageNum());
			resMsg.setNumPerPage(reqMsg.getNumPerPage());
			resMsg.setTotalRows(total);
			List<BsPayOrders> buyBankTypeList = payOrdersService.findBuyBankTypeList();
			resMsg.setBuyBankTypeList(BeanUtils.classToArrayList(buyBankTypeList));

			log.error("service req time" + new Date().getTime());
		}
		
		/*List<BsAgent> agents = agentService.agentNameGroupByList(new BsAgent());
		resMsg.setAgentList(BeanUtils.classToArrayList(agents));*/
	}
	
	/**
	 * 订单处理中转成功或失败（业务）
	 * @param req
	 * @param res
	 */
	public void orderTrocessing(ReqMsg_MAccount_OrderTrocessing req, ResMsg_MAccount_OrderTrocessing res) {
		BsPayOrders order = payOrdersService.findOrderByOrderNo(req.getOrderNo());
		if(order != null){
			checkAccountService.checkAccount4Paying(order, null, OrderStatus.FAIL.getCode(), req.getErrorText());
		}else{
			throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
		}
	}
	
	/**
	 * 客户提现查询
	 * @param reqMsg
	 * @param resMsg
	 */
	public void customerWithdraw(ReqMsg_MAccount_CustomerWithdraw reqMsg, ResMsg_MAccount_CustomerWithdraw resMsg) {
		ArrayList<BsUserWithdraw> valueList = (ArrayList<BsUserWithdraw>) accountService.findCustomerWithdrawByPage(reqMsg.getApplyNo(),reqMsg.getStatus(),reqMsg.getWithdrawTime(),reqMsg.getFinishTime(),reqMsg.getPageNum(),reqMsg.getNumPerPage());
		
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList); 
		
		resMsg.setValueList(hashList);
		
		int total = accountService.countBsUserWithdraw(reqMsg.getApplyNo(),reqMsg.getStatus(),reqMsg.getWithdrawTime(),reqMsg.getFinishTime());
		
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
		resMsg.setTotalRows(total);
	}
	/**
	 * 资管计划合同查询
	 * @param reqMsg
	 * @param resMsg
	 */
	public void assetsList(ReqMsg_MAccount_AssetsList reqMsg, ResMsg_MAccount_AssetsList resMsg) {
		ArrayList<BsAssetsList> valueList = (ArrayList<BsAssetsList>) accountService.findAssetsListByPage(reqMsg.getStatus(),reqMsg.getCreateTime(),reqMsg.getPageNum(),reqMsg.getNumPerPage());
		
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList); 
		
		resMsg.setValueList(hashList);
		
		int total = accountService.countBsAssetsList(reqMsg.getStatus(),reqMsg.getCreateTime());
		
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
		resMsg.setTotalRows(total);
	}
	
	public void transferDetail(ReqMsg_MAccount_TransferDetail reqMsg,ResMsg_MAccount_TransferDetail resMsg){
		
		BsAccountJnl accountJnl = accountService.findAccountJnlById(reqMsg.getId());
		
		resMsg.setAccountId1(accountJnl.getAccountId1());
		resMsg.setAccountId2(accountJnl.getAccountId2());
		resMsg.setAccountName1(accountJnl.getAccountName1());
		resMsg.setAccountName2(accountJnl.getAccountName2());
		resMsg.setAfterAvialableBalance1(accountJnl.getAfterAvialableBalance1());
		resMsg.setAfterAvialableBalance2(accountJnl.getAfterAvialableBalance2());
		resMsg.setAfterBalance1(accountJnl.getAfterBalance1());
		resMsg.setAfterBalance2(accountJnl.getAfterBalance2());
		resMsg.setAfterFreezeBalance1(accountJnl.getAfterFreezeBalance1());
		resMsg.setAfterFreezeBalance2(accountJnl.getAfterFreezeBalance2());
		resMsg.setBeforeAvialableBalance1(accountJnl.getBeforeAvialableBalance1());
		resMsg.setBeforeAvialableBalance2(accountJnl.getBeforeAvialableBalance2());
		resMsg.setBeforeBalance1(accountJnl.getBeforeBalance1());
		resMsg.setBeforeBalance2(accountJnl.getBeforeBalance2());
		resMsg.setBeforeFreezeBalance1(accountJnl.getBeforeFreezeBalance1());
		resMsg.setBeforeFreezeBalance2(accountJnl.getBeforeFreezeBalance2());
		resMsg.setCdFlag1(accountJnl.getCdFlag1());
		resMsg.setCdFlag2(accountJnl.getCdFlag2());
		resMsg.setChannelJnlNo(accountJnl.getChannelJnlNo());
		resMsg.setChannelTime(accountJnl.getChannelTime());
		resMsg.setCheckStatus(accountJnl.getCheckStatus());
		resMsg.setFee(accountJnl.getFee());
		resMsg.setId(accountJnl.getId());
		resMsg.setIsCheck(accountJnl.getIsCheck());
		resMsg.setNote(accountJnl.getNote());
		resMsg.setStatus(accountJnl.getStatus());
		resMsg.setSubAccountCode1(accountJnl.getSubAccountCode1());
		resMsg.setSubAccountCode2(accountJnl.getSubAccountCode2());
		resMsg.setSubAccountId1(accountJnl.getSubAccountId1());
		resMsg.setSubAccountId2(accountJnl.getSubAccountId2());
		resMsg.setSysTime(accountJnl.getSysTime());
		resMsg.setTransAmount(accountJnl.getTransAmount());
		resMsg.setTransCode(accountJnl.getTransCode());
		resMsg.setTransName(accountJnl.getTransName());
		resMsg.setTransTime(accountJnl.getTransTime());
		resMsg.setTransType(accountJnl.getTransType());
		resMsg.setUserId1(accountJnl.getUserId1());
		resMsg.setUserId2(accountJnl.getUserId2());
		resMsg.setRespCode(accountJnl.getRespCode());
		resMsg.setRespMsg(accountJnl.getRespMsg());
	}
	
	public void checkJnlDetail(ReqMsg_MAccount_CheckJnlDetail reqMsg,ResMsg_MAccount_CheckJnlDetail resMsg){
		
		BsCheckJnl checkJnl = accountService.findCheckJnlById(reqMsg.getId());
		resMsg.setId(checkJnl.getId());
		resMsg.setTransfJnlId(checkJnl.getTransJnlId());
		resMsg.setTime(checkJnl.getTime());
		resMsg.setSysAmount(checkJnl.getSysAmount());
		resMsg.setDoneAmount(checkJnl.getDoneAmount());
		resMsg.setResult(checkJnl.getResult());
		resMsg.setUserId(checkJnl.getUserId());
		resMsg.setInfo(checkJnl.getInfo());
	}
	
	
	public void investMatureWarm(ReqMsg_MAccount_InvestMatureWarm req, ResMsg_MAccount_InvestMatureWarm res){
		
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = accountService.countInvestMatureWarmList(req.getSettleBeginTime(),req.getSettleEndTime(),req.getUserName());
		if(totalRows > 0){
			ArrayList<BsStatisticsVO> investMatureWarmList = accountService.findInvestMatureWarmList(req.getSettleBeginTime(),req.getSettleEndTime(),req.getUserName(),req.getPageNum(),req.getNumPerPage());
			
			ArrayList<HashMap<String, Object>> warmList = BeanUtils.classToArrayList(investMatureWarmList);
			res.setValueList(warmList);
		}
		res.setTotalRows(totalRows);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
	}
	
	
	public void sysWithdraw(ReqMsg_MAccount_SysWithdraw reqMsg,ResMsg_MAccount_SysWithdraw resMsg){
		
		BsSysWithdraw bsSysWithdraw = new BsSysWithdraw();
		bsSysWithdraw.setAmount(reqMsg.getAmount());
		bsSysWithdraw.setStatus(Constants.WX_SYS_WITHDRAW_APPLY);
		bsSysWithdraw.setWithdrawTime(new Date());
		sysWithdrawService.addSysWithdrawReturnId(bsSysWithdraw);
		int applyId = bsSysWithdraw.getId();
		String applyNo = Util.generateApplyNo(String.valueOf(applyId), Constants.SYS_WITHDRAW_APPLY_NO);
		
		// 更新提现信息表 申请编号
		BsSysWithdraw modifyWithdraw4ApplyNo = new BsSysWithdraw();
		modifyWithdraw4ApplyNo.setId(applyId);
		modifyWithdraw4ApplyNo.setApplyNo(applyNo);
		sysWithdrawService.modifySysWithdrawById(modifyWithdraw4ApplyNo);
		
		//开始向达飞发起提现申请
		B2GReqMsg_Withdraw_SysWithdraw req = new B2GReqMsg_Withdraw_SysWithdraw();
		
		req.setAmount(reqMsg.getAmount());
		req.setApplyNo(applyNo);
		
		B2GResMsg_Withdraw_SysWithdraw res = dafyTransportService.sysWithdraw(req);
		//更新
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			BsSysWithdraw resSysWithdraw = new BsSysWithdraw();
			resSysWithdraw.setId(applyId);
			resSysWithdraw.setStatus(Constants.WX_SYS_WITHDRAW_FAIL);
			resSysWithdraw.setFinishTime(new Date());
			boolean flag = sysWithdrawService.modifySysWithdrawById(resSysWithdraw);
			if(!flag){
				throw new PTMessageException(PTMessageEnum.DB_CUD_NO_EFFECT);
			}
			throw new PTMessageException(PTMessageEnum.WITHDRAW_FAIL, res.getResMsg());
		}
	}
	
	
	public void sysWithdrawDetailListQuery(ReqMsg_MAccount_SysWithdrawDetailListQuery reqMsg,ResMsg_MAccount_SysWithdrawDetailListQuery resMsg){
		
		ArrayList<BsSysWithdraw> valueList = (ArrayList<BsSysWithdraw>) accountService.findSysWithdrawDetailListByPage(reqMsg.getFinishTimeBegin(),reqMsg.getFinishTimeEnd(),reqMsg.getWithdrawTimeBegin(),reqMsg.getWithdrawTimeEnd(),reqMsg.getStatus(),reqMsg.getPageNum(),reqMsg.getNumPerPage());
		
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList); 
		
		resMsg.setValueList(hashList);
		
		int total = accountService.countSysWithdrawList(reqMsg.getFinishTimeBegin(),reqMsg.getFinishTimeEnd(),reqMsg.getWithdrawTimeBegin(),reqMsg.getWithdrawTimeEnd(),reqMsg.getStatus());
		
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
		resMsg.setTotalRows(total);
		
	}
	
	/*
	 * 用户交易明细列表查询
	 */
	public void tradeDetailListQuery(ReqMsg_MAccount_TradeDetailListQuery req,ResMsg_MAccount_TradeDetailListQuery resp) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = transDetailService.countUserTransDetail(req.getsMobile(), req.getsUserName(), req.getsTransType());
		if(totalRows > 0) {
			ArrayList<BsUserTransDetailVO>  valuesList = (ArrayList<BsUserTransDetailVO>)transDetailService.findUserTransDetailListQueryByPage(req.getsMobile(), req.getsUserName(), req.getsTransType(), pageNum, numPerPage);
			resp.setUserTransDetail(BeanUtils.classToArrayList(valuesList));
		}
		resp.setPageNum(pageNum);
		resp.setNumPerPage(numPerPage);
		resp.setTotalRows(totalRows > 0?totalRows:0);
		resp.setsMobile(req.getsMobile());
		resp.setsUserName(req.getsUserName());
		resp.setsTransType(req.getsTransType());
	}
	
	/**
	 * 用户提现查询
	 * @param req
	 * @param res
	 */
	public void userWithdraw(ReqMsg_MAccount_UserWithdraw req, ResMsg_MAccount_UserWithdraw res) {
		int totalRows = accountService.countUserWithdrawal(req.getMobile(), req.getUserName(), 
				req.getStatus(), req.getBeginTime(), req.getOverTime());
		if(totalRows > 0) {
			double sumUserAmount = accountService.sumUserWithdrawal(req.getMobile(), req.getUserName(), 
					req.getStatus(), req.getBeginTime(), req.getOverTime());
			res.setSumUserAmount(sumUserAmount);
			ArrayList<BsPayOrdersVO> valueList = (ArrayList<BsPayOrdersVO>) accountService.findUserWithdrawal(
					req.getMobile(), req.getUserName(), 
					req.getStatus(), req.getBeginTime(), 
					req.getOverTime(), req.getPageNum(), 
					req.getNumPerPage(), req.getOrderDirection(), 
					req.getOrderField());
			ArrayList<HashMap<String, Object>> hashList = BeanUtils.classToArrayList(valueList);
			res.setValueList(hashList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 用户奖励金查询
	 * @param req
	 * @param res
	 */
	public void bonusListQuery(ReqMsg_MAccount_BonusListQuery req, ResMsg_MAccount_BonusListQuery res) {
		int totalRows = accountService.countUserBonus(req.getMobile(), req.getUserName(), req.getTransCodes(), 
				req.getBeginTime(), req.getOverTime());
		if(totalRows > 0) {
			double sumBonusAmount = accountService.sumUserBonus(req.getMobile(), req.getUserName(), req.getTransCodes(), 
				req.getBeginTime(), req.getOverTime());
			res.setSumBonusAmount(sumBonusAmount);
			ArrayList<BsAccountJnlVO> valueList = (ArrayList<BsAccountJnlVO>) accountService.findUserBonus(req.getMobile(), req.getUserName(), req.getTransCodes(), 
					req.getBeginTime(), req.getOverTime(), 
					req.getPageNum(), req.getNumPerPage(), 
					req.getOrderDirection(), req.getOrderField());
			ArrayList<HashMap<String, Object>> hashList = BeanUtils.classToArrayList(valueList);
			res.setValueList(hashList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 用户充值查询
	 * @param req
	 * @param res
	 */
	public void topUpListQuery(ReqMsg_MAccount_TopUpListQuery req, ResMsg_MAccount_TopUpListQuery res) {
		int totalRows = payOrdersService.userCountTopUp(req.getMobile(), req.getUserName(), 
				 req.getStatus(), req.getBeginTime(), req.getOverTime());
		if(totalRows > 0) {
			double sumTopUpAmount = payOrdersService.userSumTopUp(req.getMobile(), req.getUserName(), 
				req.getStatus(),req.getBeginTime(), req.getOverTime());
			res.setSumTopUpAmount(sumTopUpAmount);
			ArrayList<BsPayOrdersVO> valueList = (ArrayList<BsPayOrdersVO>) payOrdersService.findUserTopUp(req.getMobile(), req.getUserName(),req.getStatus(), 
					req.getBeginTime(), req.getOverTime(),  
					req.getPageNum(), req.getNumPerPage(), 
					req.getOrderDirection(), req.getOrderField());
			ArrayList<HashMap<String, Object>> hashList = BeanUtils.classToArrayList(valueList);
			res.setValueList(hashList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 用户记账流水查询
	 * @param req
	 * @param res
	 */
	public void userChargeAccountQuery(ReqMsg_MAccount_UserChargeAccountQuery req, ResMsg_MAccount_UserChargeAccountQuery res) {
		int totalRows = accountService.userCountChargeAccount(req.getUserName(), req.getMobile(), 
				req.getBeginTime(), req.getOverTime(), 
				req.getStartTransTime(), req.getEndTransTime());
		if(totalRows > 0) {
			ArrayList<BsAccountJnlVO> valueList = (ArrayList<BsAccountJnlVO>) accountService.userChargeAccount(req.getUserName(), req.getMobile(), 
					req.getBeginTime(), req.getOverTime(), 
					req.getStartTransTime(), req.getEndTransTime(), 
					req.getPageNum(), req.getNumPerPage(), 
					req.getOrderDirection(), req.getOrderField());
			ArrayList<HashMap<String, Object>> hashList = BeanUtils.classToArrayList(valueList);
			res.setValueList(hashList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 订单明细流水查询
	 * @param req
	 * @param res
	 */
	public void orderDetailQuery(ReqMsg_MAccount_OrderDetailQuery req, ResMsg_MAccount_OrderDetailQuery res) {
		int totalRows = payOrdersService.findCountOrderDetail(req.getUserName(), req.getMobile(), req.getBeginTime(), 
				req.getOverTime(), req.getStatus(), 
				req.getStartUpdateTime(), req.getEndUpdateTime(), 
				req.getOrderNo(), req.getTransType());
		if(totalRows > 0) {
			ArrayList<BsPayOrdersVO> valueList = (ArrayList<BsPayOrdersVO>) payOrdersService.findOrderDetailList(req.getUserName(), req.getMobile(), req.getBeginTime(), 
					req.getOverTime(), req.getStatus(), 
					req.getStartUpdateTime(), req.getEndUpdateTime(), 
					req.getOrderNo(), req.getTransType(), 
					req.getPageNum(), req.getNumPerPage(), 
					req.getOrderDirection(), req.getOrderField());
			ArrayList<HashMap<String, Object>> hashList = BeanUtils.classToArrayList(valueList);
			res.setValueList(hashList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 系统记账流水查询
	 * @param req
	 * @param res
	 */
	public void sysAccountQuery(ReqMsg_MAccount_SysAccountQuery req, ResMsg_MAccount_SysAccountQuery res) {
		int totalRows = accountService.findCountSysAccount(req.getTransOtherCode(), req.getStatus(), req.getStartTransAmount(), req.getEndTransAmount(), 
				req.getBeginTime(), req.getOverTime(), req.getStartTransTime(), req.getEndTransTime());
		if(totalRows > 0) {
			ArrayList<BsSysAccountJnlVO> valueList = (ArrayList<BsSysAccountJnlVO>) accountService.findSysAccountList(req.getTransOtherCode(), req.getStatus(), req.getStartTransAmount(), req.getEndTransAmount(), 
					req.getBeginTime(), req.getOverTime(), req.getStartTransTime(), req.getEndTransTime(), 
					req.getPageNum(), req.getNumPerPage(), req.getOrderDirection(), req.getOrderField());
			ArrayList<HashMap<String, Object>> hashList = BeanUtils.classToArrayList(valueList);
			res.setValueList(hashList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 人工发起划款购买理财，只支持单个用户产品户
	 * @param req
	 * @param res
	 */
	public void manualWorkAcctTrans4Buy(ReqMsg_MAccount_ManualWorkAcctTrans4Buy req, ResMsg_MAccount_ManualWorkAcctTrans4Buy res){
		SysActTransSendInfo transInfo = sysProductBuyService.prepareSingleProduct(req.getSubAccountId());
		sysProductBuyService.trans2Partner(transInfo);
	}
	
	/**
	 * 人工发起划款购买理财，前一天的所有成功对账的产品户都会发起
	 * @param req
	 * @param res
	 */
	public void manualWorkAcctTrans4BuyAll(ReqMsg_MAccount_ManualWorkAcctTrans4BuyAll req, ResMsg_MAccount_ManualWorkAcctTrans4BuyAll res){
		sysProductBuyService.preparePartnerDailyProduct(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
		sysProductBuyService.preparePartnerDailyProduct(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI);
	}

	/**
	 * 财务功能-恒丰系统充值
	 * @param req
	 * @param res
     */
	public void queryHFTopUpOrder(ReqMsg_MAccount_QueryHFTopUpOrder req, ResMsg_MAccount_QueryHFTopUpOrder res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		HFBankPayOrderVO vo = new HFBankPayOrderVO();
		vo.setPageNum(pageNum);
		vo.setNumPerPage(numPerPage);
		int totalRows = accountService.queryHFBankSysTopUpCount();
		if(totalRows > 0) {
			List<HFBankPayOrderVO> resultList =  accountService.queryHFBankSysTopUp(vo);
			if(resultList != null && resultList.size() > 0) {
				//恒丰系统账户划转-存入订单表bs_pay_orders表时，note字段，会同时存入操作人/转出账户/转入账户，已“;”拼接的一个字符串
				List<HFBankPayOrderVO> objList = new ArrayList<HFBankPayOrderVO>();
				for(HFBankPayOrderVO obj : resultList) {
					HFBankPayOrderVO payOrderVO = new HFBankPayOrderVO();
					payOrderVO.setOrderNo(obj.getOrderNo());
					payOrderVO.setCreateTime(obj.getCreateTime());
					payOrderVO.setUpdateTime(obj.getUpdateTime());
					payOrderVO.setAmount(obj.getAmount());
					payOrderVO.setTransType(obj.getTransType());
					payOrderVO.setStatus(obj.getStatus());

					String note = obj.getNote();
					if(StringUtil.isNotBlank(note)) {
						String[] array = note.split(";");
						if(StringUtil.isNotBlank(array[0])){
							payOrderVO.setNote(array[0]);
						}
						payOrderVO.setUserName(array[1]);
					}
					objList.add(payOrderVO);
				}
				res.setValueList(BeanUtils.classToArrayList(objList));
			}
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}

	/**
	 * 财务功能-恒丰系统提现
	 * @param req
	 * @param res
	 */
	public void queryHFWithdrawOrder(ReqMsg_MAccount_QueryHFWithdrawOrder req, ResMsg_MAccount_QueryHFWithdrawOrder res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		HFBankPayOrderVO vo = new HFBankPayOrderVO();
		vo.setPageNum(pageNum);
		vo.setNumPerPage(numPerPage);
		int totalRows = accountService.queryHFBankSysWithdrawCount();
		if(totalRows > 0) {
			List<HFBankPayOrderVO> resultList =  accountService.queryHFBankSysWithdraw(vo);
			if(resultList != null && resultList.size() > 0) {
				//恒丰系统账户划转-存入订单表bs_pay_orders表时，note字段，会同时存入操作人/转出账户/转入账户，已“;”拼接的一个字符串
				List<HFBankPayOrderVO> objList = new ArrayList<HFBankPayOrderVO>();
				for(HFBankPayOrderVO obj : resultList) {
					HFBankPayOrderVO payOrderVO = new HFBankPayOrderVO();
					payOrderVO.setOrderNo(obj.getOrderNo());
					payOrderVO.setCreateTime(obj.getCreateTime());
					payOrderVO.setUpdateTime(obj.getUpdateTime());
					payOrderVO.setAmount(obj.getAmount());
					payOrderVO.setTransType(obj.getTransType());
					payOrderVO.setStatus(obj.getStatus());

					String note = obj.getNote();
					if(StringUtil.isNotBlank(note)) {
						String[] array = note.split(";");
						if(StringUtil.isNotBlank(array[0])){
							payOrderVO.setNote(array[0]);
						}
						payOrderVO.setUserName(array[1]);
					}
					objList.add(payOrderVO);
				}
				res.setValueList(BeanUtils.classToArrayList(objList));
			}
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}

	/**
	 * 财务功能-恒丰系统账户划转
	 * @param req
	 * @param res
     */
	public void queryHFBankSysAccountTransfer(ReqMsg_MAccount_QueryHFBankSysAccountTransfer req, ResMsg_MAccount_QueryHFBankSysAccountTransfer res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		HFBankPayOrderVO vo = new HFBankPayOrderVO();
		vo.setPageNum(pageNum);
		vo.setNumPerPage(numPerPage);
		int totalRows = accountService.queryHFBankSysAccountTransferCount();
		if(totalRows > 0) {
			List<HFBankPayOrderVO> resultList =  accountService.queryHFBankSysAccountTransfer(vo);
			if(resultList != null && resultList.size() > 0) {
				//恒丰系统账户划转-存入订单表bs_pay_orders表时，note字段，会同时存入操作人/转出账户/转入账户，已“;”拼接的一个字符串
				List<HFBankPayOrderVO> objList = new ArrayList<HFBankPayOrderVO>();
				for(HFBankPayOrderVO obj : resultList) {
					HFBankPayOrderVO payOrderVO = new HFBankPayOrderVO();
					payOrderVO.setOrderNo(obj.getOrderNo());
					payOrderVO.setCreateTime(obj.getCreateTime());
					payOrderVO.setUpdateTime(obj.getUpdateTime());
					payOrderVO.setAmount(obj.getAmount());
					payOrderVO.setTransType(obj.getTransType());
					payOrderVO.setStatus(obj.getStatus());

					String note = obj.getNote();
					if(StringUtil.isNotBlank(note)) {
						String[] array = note.split(";");
						if(array.length > 3) {
							if(StringUtil.isNotBlank(array[0])){
								payOrderVO.setNote(array[0]);
							}
							payOrderVO.setUserName(array[1]);
							payOrderVO.setTransferOutAccount(array[2]);
							payOrderVO.setTransferInAccount(array[3]);
						}else {
							if(StringUtil.isNotBlank(array[0])){
								payOrderVO.setNote(array[0]);
							}
							payOrderVO.setUserName(array[1]);
							payOrderVO.setTransferOutAccount(array[2]);
						}

					}
					objList.add(payOrderVO);
				}
				res.setValueList(BeanUtils.classToArrayList(objList));
			}
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}

	/**
	 * 财务功能-云贷砍头息划转
	 * @param req
	 * @param res
     */
	public void queryYunHeadFeeTransferList(ReqMsg_MAccount_QueryYunHeadFeeTransferList req, ResMsg_MAccount_QueryYunHeadFeeTransferList res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		HFBankHeadFeeTransferVO vo = new HFBankHeadFeeTransferVO();
		vo.setPageNum(pageNum);
		vo.setNumPerPage(numPerPage);
		int totalRows = accountService.queryYunHeadFeeTransferCount();
		if(totalRows > 0) {
			//云贷
			BsSysConfig yunHeadFee2User = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.YUN_HEAD_FEE_2_USER);
			if(yunHeadFee2User == null) {
				log.info("云贷砍头息划转目标用户配置数据不存在");
				return;
			}
			BsUser user = bsUserService.findUserByUserId(Integer.parseInt(yunHeadFee2User.getConfValue()));
			if(user == null) {
				log.info("云贷砍头息划转目标用户数据在bs_user不存在");
				return;
			}
			//赞时贷
			BsSysConfig zsdHeadFee2User = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.ZSD_HEAD_FEE_2_USER);
			if(zsdHeadFee2User == null) {
				log.info("赞时贷砍头息划转目标用户配置数据不存在");
			}
			BsUser zsdUser = bsUserService.findUserByUserId(Integer.parseInt(zsdHeadFee2User.getConfValue()));
			if(zsdUser == null) {
				log.info("赞时贷砍头息划转目标用户数据在bs_user不存在");
				return;
			}
			List<HFBankHeadFeeTransferVO> resultList =  accountService.queryYunHeadFeeTransfer(vo);
			if(resultList != null && resultList.size() > 0) {
				//云贷砍头息划转-存入订单表bs_pay_orders表时，note字段，会同时存入操作人/转出账户/转入账户，已“;”拼接的一个字符串
				List<HFBankHeadFeeTransferVO> objList = new ArrayList<HFBankHeadFeeTransferVO>();
				for(HFBankHeadFeeTransferVO obj : resultList) {
					HFBankHeadFeeTransferVO payOrderVO = new HFBankHeadFeeTransferVO();
					payOrderVO.setOrderNo(obj.getOrderNo());
					payOrderVO.setCreateTime(obj.getCreateTime());
					payOrderVO.setUpdateTime(obj.getUpdateTime());
					payOrderVO.setAmount(obj.getAmount());
					payOrderVO.setTransType(obj.getTransType());
					payOrderVO.setStatus(obj.getStatus());
					payOrderVO.setTransferOutAccount(obj.getUserName());

					String note = obj.getNote();
					if(StringUtil.isNotBlank(note)) {
						String[] array = note.split(";");
						if(StringUtil.isNotBlank(array[0])){
							payOrderVO.setNote(array[0]);
						}
						payOrderVO.setUserName(array[1]);
					}
					objList.add(payOrderVO);
				}
				res.setValueList(BeanUtils.classToArrayList(objList));
			}
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 财务功能-赞分期代偿人充值、提现
	 * @param req
	 * @param res
     */
	public void queryZanCompensateList(ReqMsg_MAccount_QueryZanCompensateList req, ResMsg_MAccount_QueryZanCompensateList res) {

		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		HFBankPayOrderVO vo = new HFBankPayOrderVO();
		vo.setPageNum(pageNum);
		vo.setNumPerPage(numPerPage);
		int totalRows = accountService.queryZanCompensateCount();
		if(totalRows > 0) {
			List<HFBankPayOrderVO> resultList =  accountService.queryZanCompensateList(vo);
			res.setValueList(BeanUtils.classToArrayList(resultList));
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);

		//代偿人账户余额
		double zanCompensateAmount = 0d;
		BsSubAccount bsSubAccount = subAccountService.queryDEPJSHSubAccountByUserId();
		if(bsSubAccount == null) {
			zanCompensateAmount = 0d;
		}else {
			zanCompensateAmount = bsSubAccount.getBalance();
		}
		res.setZanCompensateAmount(zanCompensateAmount);

		//代偿人信息
		List<BsUser> userList = bsUserService.queryZanCompensateUserInfo();
		res.setZanUserList(BeanUtils.classToArrayList(userList));
	}
	
	/**
	 * 财务功能-借款人提现
	 * @param req
	 * @param res
     */
	public void queryLoanList(ReqMsg_MAccount_QueryLoanList req, ResMsg_MAccount_QueryLoanList res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		HFBANKLoanWithdrawOrderVO vo = new HFBANKLoanWithdrawOrderVO();
		vo.setPageNum(pageNum);
		vo.setNumPerPage(numPerPage);
		int totalRows = accountService.querySysLoanerWithdrawCount();
		if(totalRows > 0) { 														
			List<HFBANKLoanWithdrawOrderVO> resultList =  accountService.querySysLoanerWithdraw(vo);
			res.setValueList(BeanUtils.classToArrayList(resultList));
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}

	
	/**
	 * 财务功能-垫付金人工调账
	 * @param req
	 * @param res
	 */
	public void queryAdvanceTransferOrder(ReqMsg_MAccount_QueryAdvanceTransferOrder req, ResMsg_MAccount_QueryAdvanceTransferOrder res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		HFBankPayOrderVO vo = new HFBankPayOrderVO();
		vo.setPageNum(pageNum);
		vo.setNumPerPage(numPerPage);
		int totalRows = accountService.queryHFBankAdvanceTransferCount();
		if(totalRows > 0) {
			List<HFBankPayOrderVO> resultList =  accountService.queryHFBankAdvanceTransfer(vo);
			if(resultList != null && resultList.size() > 0) {
				//恒丰系统垫付金人工调账-存入订单表bs_pay_orders表时，note字段，会同时存入操作人/转出账户/转入账户，已“;”拼接的一个字符串
				List<HFBankPayOrderVO> objList = new ArrayList<HFBankPayOrderVO>();
				for(HFBankPayOrderVO obj : resultList) {

					String note = obj.getNote();
					if(StringUtil.isNotBlank(note) && note.contains(";")) { //人工调账
						HFBankPayOrderVO payOrderVO = new HFBankPayOrderVO();
						payOrderVO.setOrderNo(obj.getOrderNo());
						payOrderVO.setCreateTime(obj.getCreateTime());
						payOrderVO.setUpdateTime(obj.getUpdateTime());
						payOrderVO.setAmount(obj.getAmount());
						payOrderVO.setTransType(obj.getTransType());
						payOrderVO.setStatus(obj.getStatus());

						if(StringUtil.isNotBlank(note)) {
							String[] array = note.split(";");
							if(StringUtil.isNotBlank(array[0])){
								payOrderVO.setNote(array[0]);
							}
							payOrderVO.setUserName(array[1]);
							payOrderVO.setTransferOutAccount(array[2]);
							payOrderVO.setTransferInAccount(array[3]);
						}
						objList.add(payOrderVO);
					}else { // 自动调账
						HFBankPayOrderVO payOrderVO = new HFBankPayOrderVO();
						payOrderVO.setOrderNo(obj.getOrderNo());
						payOrderVO.setCreateTime(obj.getCreateTime());
						payOrderVO.setUpdateTime(obj.getUpdateTime());
						payOrderVO.setAmount(obj.getAmount());
						payOrderVO.setStatus(obj.getStatus());
						payOrderVO.setNote(note);
						payOrderVO.setTransferOutAccount("自有子账户");
						payOrderVO.setTransferInAccount("垫付金子账户");
						objList.add(payOrderVO);
					}
				}
				res.setValueList(BeanUtils.classToArrayList(objList));
			}
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 财务功能-钱报信息服务费查询
	 * @param req
	 * @param res
     */
	public void queryQbDepServiceFeeList(ReqMsg_MAccount_QueryQbDepServiceFeeList req, ResMsg_MAccount_QueryQbDepServiceFeeList res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = accountService.countQbDepServiceFee(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime());
		if (totalRows > 0) {
			List<DepServiceFeeVO> valueList = accountService.queryQbDepServiceFeeList(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime(),
						pageNum, numPerPage);
			ArrayList<HashMap<String, Object>> depServiceFeeList = BeanUtils.classToArrayList(valueList);
			if (CollectionUtils.isNotEmpty(depServiceFeeList)) {
				for (HashMap<String, Object> hashMap : depServiceFeeList) {
					Integer userId = (Integer)hashMap.get("userId");
					BsBankCardExample bsBankCardExample = new BsBankCardExample();
					bsBankCardExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.DAFY_BINDING_STAUTS_NORMAL);
			        List<BsBankCard> bankList = bsBankCardMapper.selectByExample(bsBankCardExample);
					if (CollectionUtils.isNotEmpty(bankList)) {					
						hashMap.put("bankName", bankList.get(0).getBankName());
					}
				}
				double sumBuyAmount = accountService.sumQbBuyAmount(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime());
				double sumDepServiceFee = accountService.sumQbDepServiceFee(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime());
				res.setSumBuyAmount(sumBuyAmount);
				res.setSumQbServiceFee(sumDepServiceFee);
			}
			res.setValueList(depServiceFeeList);
		}
		res.setTotalRows(totalRows);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
	}
	
	/**
	 * 财务功能-杭商信息服务费查询
	 * @param req
	 * @param res
     */
	public void queryHsDepServiceFeeList(ReqMsg_MAccount_QueryHsDepServiceFeeList req, ResMsg_MAccount_QueryHsDepServiceFeeList res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = accountService.countHsDepServiceFee(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime(),
				req.getAccountStatus(), req.getAgentIds(), req.getNonAgentId(), req.getAgentId());
		if (totalRows > 0) {
			List<DepServiceFeeVO> valueList = accountService.queryHsDepServiceFeeList(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime(),
					req.getAccountStatus(), req.getAgentIds(), req.getNonAgentId(), req.getAgentId(), pageNum, numPerPage);
			ArrayList<HashMap<String, Object>> depServiceFeeList = BeanUtils.classToArrayList(valueList);
			if (CollectionUtils.isNotEmpty(depServiceFeeList)) {
				for (HashMap<String, Object> hashMap : depServiceFeeList) {
					Integer userId = (Integer)hashMap.get("userId");
					BsBankCardExample bsBankCardExample = new BsBankCardExample();
					bsBankCardExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.DAFY_BINDING_STAUTS_NORMAL);
			        List<BsBankCard> bankList = bsBankCardMapper.selectByExample(bsBankCardExample);
					if (CollectionUtils.isNotEmpty(bankList)) {					
						hashMap.put("bankName", bankList.get(0).getBankName());
					}
				}
				double sumBuyAmount = accountService.sumBuyAmount(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime(),
						req.getAccountStatus(), req.getAgentIds(), req.getNonAgentId(), req.getAgentId());
				double sumDepServiceFee = accountService.sumHsDepServiceFee(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime(),
						req.getAccountStatus(), req.getAgentIds(), req.getNonAgentId(), req.getAgentId());
				res.setSumBuyAmount(sumBuyAmount);
				res.setSumHsServiceFee(sumDepServiceFee);
			}	
			res.setValueList(depServiceFeeList);
		}
		res.setTotalRows(totalRows);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
	}
	
	/**
	 * 财务功能-品听信息服务费查询
	 * @param req
	 * @param res
     */
	public void queryPtDepServiceFeeList(ReqMsg_MAccount_QueryPtDepServiceFeeList req, ResMsg_MAccount_QueryPtDepServiceFeeList res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = accountService.countPtDepServiceFee(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime(),
				req.getAgentIds(), req.getNonAgentId(), req.getAgentId());
		if (totalRows > 0) {
			List<DepServiceFeeVO> valueList = accountService.queryPtDepServiceFeeList(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime(),
					req.getAgentIds(), req.getNonAgentId(), req.getAgentId(), pageNum, numPerPage);
			ArrayList<HashMap<String, Object>> depServiceFeeList = BeanUtils.classToArrayList(valueList);
			if (CollectionUtils.isNotEmpty(depServiceFeeList)) {
				double sumBuyAmount = accountService.sumBuyAmount(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime(),
						req.getAgentIds(), req.getNonAgentId(), req.getAgentId());
				double sumDepServiceFee = accountService.sumDepServiceFee(req.getProductName(), req.getProductTerm(), req.getBuyBeginTime(), req.getBuyEndTime(),
						req.getAgentIds(), req.getNonAgentId(), req.getAgentId());
				res.setSumBuyAmount(sumBuyAmount);
				res.setSumPtServiceFee(sumDepServiceFee);
			}	
			res.setValueList(depServiceFeeList);
		}
		res.setTotalRows(totalRows);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
	}
	
	/**
	 * 财务功能-日终账务查询
	 * @param req
	 * @param res
     */
	public void querySysBalanceDailyList(ReqMsg_MAccount_QuerySysBalanceDailyList req, ResMsg_MAccount_QuerySysBalanceDailyList res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = bsSysBalanceDailyFileService.countSysBalanceDailyFile(req.getSnapBeginTime(), req.getSnapEndTime());
		if(totalRows > 0) {
			List<BsSysBalanceDailyFile> balanceDailyFileList =  bsSysBalanceDailyFileService.querySysBalanceDailyFileList(req.getSnapBeginTime(), req.getSnapEndTime(), pageNum, numPerPage);
			if(CollectionUtils.isNotEmpty(balanceDailyFileList)) {
				res.setValueList(BeanUtils.classToArrayList(balanceDailyFileList));
			}
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(totalRows);
	}
	
	/**
	 * 退票数据批量订单号查询
	 * @param req
	 * @param res
     */
	public void queryRebateOrder(ReqMsg_MAccount_QueryRebateOrder req, ResMsg_MAccount_QueryRebateOrder res) {
		int totalRows = accountService.countRebateOrder(req.getBeginTime(), req.getEndTime());
		if (totalRows > 0) {
			List<LnDepositionRepayScheduleVO> valueList = accountService.queryRebateOrderList(req.getBeginTime(), req.getEndTime(), req.getPageNum(), req.getNumPerPage());
			ArrayList<HashMap<String, Object>> rebateOrderList = BeanUtils.classToArrayList(valueList);
			res.setValueList(rebateOrderList);
		}
		res.setTotalRows(totalRows);
	}
	
}
