package com.pinting.business.dayend.task;

import com.pinting.business.accounting.service.CheckJnlService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.accounting.service.ReconciliationService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsCheckErrorJnl;
import com.pinting.business.model.BsCheckJnl;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.manage.MAccountService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.ExcelUtil;
import com.pinting.business.util.FtpClientUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.model.InvestmentAmounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 金额汇总对账 以及金额明细对账
 * @Project: business
 * @Title: CheckAccountTask.java
 * @author dingpf
 * @date 2015-3-4 上午11:11:53
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class CheckAccountTask {
	private Logger log = LoggerFactory.getLogger(CheckAccountTask.class);
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	@Autowired
	private ReconciliationService reconciliationService; 
	@Autowired
	private PayOrdersService payOrdersService;
	@Autowired
	private SMSService smsService;
	@Autowired
	private CheckJnlService checkJnlService;
	@Autowired
	private MAccountService mAccountService;
	
	/**
	 * 任务执行
	 */
	public void execute() {
		//日终【到期未正常兑付告警】
		receiveMoneyWarnTask();
		// 日终【达飞理财资金对账】
		//dafyCheckAccountTask();
		// 日终【达飞理财资金明细对账】
		//dafyCheckAccountDetailTask();
	}
	
	private void receiveMoneyWarnTask() {
		log.info("==================日终【到期未正常兑付告警】开始====================");
		try {
			
			Date time = new Date();
			int count = mAccountService.countInvestMatureWarm(DateUtil.parseDate(DateUtil.formatYYYYMMDD(time)));
			if(count > 0){
				String type = "日终【到期未正常兑付告警】";
				String detail = "【" + DateUtil.format(new Date()) + "】日终：有未正常兑付回款，请检查“到期未正常兑付告警”结果列表";
				bsSpecialJnlService.addSpecialJnl(type, detail);
				smsService.sendSysManagerMobiles("到期未正常兑付告警金额有异常",true);
			}
			
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【到期未正常兑付告警】失败，【特殊交易流水表】新增失败记录====================", e);
			String type = "日终【到期未正常兑付告警】";
			String detail = "【" + DateUtil.format(new Date()) + "】日终：到期未正常兑付告警跑批失败";
			bsSpecialJnlService.addSpecialJnl(type, detail);
			smsService.sendSysManagerMobiles("到期未正常兑付告警跑批失败",true);
		}
		log.info("==================日终【到期未正常兑付告警】结束====================");
		
	}

	/**
	 * 通过ftp请求达飞获得文件，并进行明细对账
	 */
	private void dafyCheckAccountDetailTask() {
		log.info("==================日终【达飞理财资金明细对账】开始====================");
		try {
			//ftp获得文件，并保存
			String ftpHost = GlobEnvUtil.get("dafy.ftp.host");
			String ftpPort = GlobEnvUtil.get("dafy.ftp.port");
			String username = GlobEnvUtil.get("dafy.ftp.username");
			String password = GlobEnvUtil.get("dafy.ftp.password");
			String localDir = GlobEnvUtil.get("dafy.checkAccount.download.file");
			String sourceFileName = GlobEnvUtil.get("dafy.checkAccount.ftp.file.addr") + 
					DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".xls";
			FtpClientUtil ftpClient = new FtpClientUtil(username, password, ftpHost, Integer.valueOf(ftpPort));
			boolean flag = ftpClient.download(sourceFileName, localDir);
			if(!flag){
				throw new PTMessageException(PTMessageEnum.DAFY_FTP_DOWNLOAD_FAIL);
			}
			//解析excel文件
			File localFile = new File(localDir + DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".xls");
			int ignoreRows = 1;// 忽略第一行
			String[][] result = ExcelUtil.getData(localFile, ignoreRows);
			if(result == null){
				throw new PTMessageException(PTMessageEnum.DAFY_READ_EXCEL_FAIL);
			}else if(result.length == 0){
				log.info("==================日终【达飞理财资金明细对账】对账文件数据为空====================");
			}else{
				//进行明细对账
				Date beginDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)));
				Date endDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
				List<BsPayOrders> orders = payOrdersService.findOrdersByDate(beginDate, endDate);
				Map<String, Object> isCheckedMap = new HashMap<>();
				int rowLength = result.length;
				for (BsPayOrders order : orders) {
					String localOrderNo = order.getOrderNo();
					Integer localStatus = order.getStatus();
					Double localAmount = order.getAmount();
					boolean isExist = false;
					for (int i = 0; i < rowLength; i++) {
						String[] row = result[i];
						String orderNo = row[0];
						if(!localOrderNo.equals(orderNo)){
							continue;
						}else{
							isExist = true;
							String status = row[2];
							Double amount = StringUtil.isEmpty(row[4])?0d:Double.valueOf(row[4]);
							//若对账文件订单记录 只有订单号，其他字段都为空，并且本地此条订单为未成功，则跳过此条记录
							if(amount == 0 && localStatus != Constants.ORDER_STATUS_SUCCESS ){
								isCheckedMap.put(localOrderNo, localStatus);
								break;
							}else if(amount == 0 && localStatus == Constants.ORDER_STATUS_SUCCESS) {
								//error
								checkAccountNotMatch(order,localAmount, Constants.CHECK_ACCOUNT_DETAIL_NOT_MATCH, "【"+localOrderNo + "|" + localAmount + ":空"  + "|" + (localStatus==Constants.ORDER_STATUS_SUCCESS?"支付成功":"支付未成功") +"】对账状态不一致！！！");
								isCheckedMap.put(localOrderNo, localStatus);
								break;
							}
							//对账一致(双方都成功 或 双方都未成功)
							if((localStatus == Constants.ORDER_STATUS_SUCCESS && 
									Constants.DAFY_ORDER_STATUS_SUCCESS.equals(status)) ||
									(localStatus != Constants.ORDER_STATUS_SUCCESS && 
									!Constants.DAFY_ORDER_STATUS_SUCCESS.equals(status))){
								//金额是否一致
								if(localAmount.compareTo(Double.valueOf(amount)) == 0){
									//sucess
									checkAccountIsMatch(order, Double.valueOf(amount), Constants.CHECK_ACCOUNT_DETAIL_IS_MATCH, "【"+localOrderNo + "|" + localAmount + "|" + (localStatus==Constants.ORDER_STATUS_SUCCESS?"支付成功":"支付未成功") +"】对账一致");
								}else{
									//error
									checkAccountNotMatch(order, Double.valueOf(amount), Constants.CHECK_ACCOUNT_DETAIL_NOT_MATCH, "【"+localOrderNo + "|" + localAmount + ":" + amount + "|" + (localStatus==Constants.ORDER_STATUS_SUCCESS?"支付成功":"支付未成功") +"】对账金额不一致！！！");
								}
							}
							//单方面成功
							else if((localStatus == Constants.ORDER_STATUS_SUCCESS && 
									!Constants.DAFY_ORDER_STATUS_SUCCESS.equals(status)) ||
									(localStatus != Constants.ORDER_STATUS_SUCCESS && 
									Constants.DAFY_ORDER_STATUS_SUCCESS.equals(status))){
								//error
								checkAccountNotMatch(order, Double.valueOf(amount), Constants.CHECK_ACCOUNT_DETAIL_NOT_MATCH, "【"+localOrderNo + "|" + localAmount + ":" + amount + "|" + (Constants.DAFY_ORDER_STATUS_SUCCESS.equals(status)?"支付成功":"支付未成功") + ":" + (localStatus==Constants.ORDER_STATUS_SUCCESS?"支付成功":"支付未成功") +"】对账支付结果不一致！！！");
							}
							
						}
						break;
					}
					//不存在于对账文件，但本地已成功！(第三方没有此账务)
					if(!isExist && localStatus == Constants.ORDER_STATUS_SUCCESS){
						//error
						checkAccountNotMatch(order, 0d, Constants.CHECK_ACCOUNT_DETAIL_THIRD_NOT_FOUND, "【"+localOrderNo + "|" + localAmount + "|" + (localStatus==Constants.ORDER_STATUS_SUCCESS?"支付成功":"支付未成功") +"】第三方没有此账务！！！");
					}
					isCheckedMap.put(localOrderNo, localStatus);
				}
				log.info("==================日终【达飞理财资金明细对账】开始查找本地系统未记录的账务====================");
				for (int i = 0; i < rowLength; i++) {
					String[] row = result[i];
					String orderNo = row[0];
					if(isCheckedMap.get(orderNo) == null){
						log.info("==================日终【达飞理财资金明细对账】发现未对账账务，针对订单号【"+ orderNo +"】进行本地订单账务查询====================");
						BsPayOrders order = payOrdersService.findOrderByOrderNo(orderNo.trim());
						
						String status = row[2];
						Double amount = StringUtil.isEmpty(row[4])?0d:Double.valueOf(row[4]);
						//第三方有，本系统未记录此账务
						if(order == null){
							//error
							BsPayOrders tempOrder = new BsPayOrders();
							tempOrder.setAmount(Double.valueOf(amount));
							tempOrder.setOrderNo(orderNo);
							checkAccountNotMatch(tempOrder, Double.valueOf(amount), Constants.CHECK_ACCOUNT_DETAIL_LOCAL_NOT_FOUND, "【"+orderNo + "|" + amount + "|" + (Constants.DAFY_ORDER_STATUS_SUCCESS.equals(status)?"支付成功":"支付未成功") +"】该天系统未记录此账务！！！");
						//第三方有，本系统也有记录此账务（发生此种情况可能是对账文件中包含 其他日期的 账务）
						}else{
							if(Constants.DAFY_ORDER_STATUS_SUCCESS.equals(status)){
								if(order.getStatus() == Constants.ORDER_STATUS_SUCCESS){
									//金额是否一致
									if(order.getAmount().compareTo(Double.valueOf(amount)) == 0){
										//sucess
										checkAccountIsMatch(order, Double.valueOf(amount), Constants.CHECK_ACCOUNT_DETAIL_IS_MATCH, "【"+ order.getOrderNo() + "|" + order.getAmount() + "|" + (order.getStatus()==Constants.ORDER_STATUS_SUCCESS?"支付成功":"支付未成功") +"】对账一致");
									}else{
										//error
										checkAccountNotMatch(order, Double.valueOf(amount), Constants.CHECK_ACCOUNT_DETAIL_NOT_MATCH, "【"+order.getOrderNo() + "|" + order.getAmount() + ":" + amount + "|" + (order.getStatus()==Constants.ORDER_STATUS_SUCCESS?"支付成功":"支付未成功") +"】对账金额不一致！！！");
									}
								}else{
									//error
									checkAccountNotMatch(order, Double.valueOf(amount), Constants.CHECK_ACCOUNT_DETAIL_NOT_MATCH, "【"+ order.getOrderNo() + "|" + order.getAmount() + "|" + (order.getStatus()==Constants.ORDER_STATUS_SUCCESS?"支付成功":"支付未成功") +"】对账支付结果不一致！！！");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【达飞理财资金明细对账】失败，【特殊交易流水表】新增失败记录====================", e);
			String type = "日终【达飞理财资金明细对账】";
			String detail = "【" + DateUtil.format(new Date()) + "】日终：达飞理财资金明细对账跑批失败：" + e.getMessage();
			bsSpecialJnlService.addSpecialJnl(type, detail);
			smsService.sendSysManagerMobiles("达飞理财资金明细对账跑批失败",true);
		}
		log.info("==================日终【达飞理财资金明细对账】结束====================");
	}

	
	private void checkAccountIsMatch(BsPayOrders order, Double amount, int result, String message) {
		BsCheckJnl checkJnl = new BsCheckJnl();
		checkJnl.setDoneAmount(amount);
		checkJnl.setInfo(message);
		checkJnl.setResult(Constants.CHECK_ACCOUNT_DETAIL_IS_MATCH);
		checkJnl.setSysAmount(order.getAmount());
		checkJnl.setTime(new Date());
		checkJnl.setTransJnlId(order.getStartJnlNo());
		checkJnl.setUserId(order.getUserId());
		checkJnlService.addCheckJnl(checkJnl);
	}

	private void checkAccountNotMatch(BsPayOrders order, Double amount, int result, String message) {
		log.error(message);
		BsCheckJnl checkJnl = new BsCheckJnl();
		checkJnl.setDoneAmount(amount);
		checkJnl.setInfo(message);
		checkJnl.setResult(result);
		checkJnl.setSysAmount(order.getAmount());
		checkJnl.setTime(new Date());
		checkJnl.setTransJnlId(order.getStartJnlNo());
		checkJnl.setUserId(order.getUserId());
		checkJnlService.addCheckJnl(checkJnl);
		
		BsCheckErrorJnl checkErrorJnl = new BsCheckErrorJnl();
		checkErrorJnl.setCheckFileId(0);
		checkErrorJnl.setCheckFileStatus(0);
		checkErrorJnl.setCheckJnlId(checkJnl.getId());
		checkErrorJnl.setCreateTime(new Date());
		checkErrorJnl.setInfo(message);
		checkErrorJnl.setIsDeal(Constants.CHECK_ERROR_JNL_NOT_DEAL);
		checkErrorJnl.setSysStatus(order.getStatus());
		checkErrorJnl.setTransJnlId(order.getStartJnlNo());
		checkErrorJnl.setUserId(order.getUserId());
		checkJnlService.addCheckErrorJnl(checkErrorJnl);
		
		String type = "日终【达飞理财资金明细对账】";
		String detail = "【" + DateUtil.format(new Date()) + "】日终：达飞理财资金明细对账：" + message;
		bsSpecialJnlService.addSpecialJnl(type, detail);
		smsService.sendSysManagerMobiles("达飞理财资金明细对账",true);
	}

	private void dafyCheckAccountTask() {
		log.info("==================日终【达飞理财资金对账】开始====================");
		try {
			// 发起上一天达飞理财资金对账接口，获得 理财产品汇总列表
			List<InvestmentAmounts> amounts = 
					reconciliationService.checkAccount2Dafy(DateUtil.addDays(new Date(), -1));
			log.info("==================日终【达飞理财资金对账】理财产品汇总列表对账情况如下：====================");
			if(amounts == null || amounts.size() < 0){
				throw new Exception("达飞返回理财资金汇总列表为空！");
			}
			// 理财资金汇总列表 循环对账
			for (InvestmentAmounts investmentAmount : amounts) {
				checkAmount(investmentAmount);
			}
			
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【达飞理财资金对账】失败，【特殊交易流水表】新增失败记录====================", e);
			String type = "日终【达飞理财资金对账】";
			String detail = "【" + DateUtil.format(new Date()) + "】日终：达飞理财资金对账跑批失败：" + e.getMessage();
			bsSpecialJnlService.addSpecialJnl(type, detail);
			smsService.sendSysManagerMobiles("达飞理财资金对账跑批失败",true);
		}
		log.info("==================日终【达飞理财资金对账】结束====================");
		
	}

	private void checkAmount(InvestmentAmounts investmentAmount) {
		// 根据产品代码统计本地系统该理财产品购买总额
		String productCode = investmentAmount.getProductCode();
		String channel = Constants.CHANNEL_DAFY;
		Date queryDate = DateUtil.addDays(new Date(), -1);
		InvestmentAmounts localAmount = 
				payOrdersService.queryInvestmentAmount(channel, productCode, queryDate);
		// 进行对账
		if(investmentAmount.getAmount().compareTo(localAmount.getAmount()) == 0){
			log.info("==================日终【达飞理财资金对账】"+ investmentAmount + "对账结果：金额一致====================");
		}else{
			log.error("==================日终【达飞理财资金对账】金额有异常情况，" +
					investmentAmount.getAmount() + "!=" + localAmount.getAmount() + 
					"！【特殊交易流水表】新增异常记录====================");
			String type = "日终【达飞理财资金对账】";
			String detail = "【" + DateUtil.format(new Date()) + "】日终：达飞理财资金对账：【"+ investmentAmount + "】对账结果：金额异常，"+
					investmentAmount.getAmount() + "!=" + localAmount.getAmount();
			bsSpecialJnlService.addSpecialJnl(type, detail);
			smsService.sendSysManagerMobiles("达飞理财资金对账金额有异常",true);
		}
	}
}
