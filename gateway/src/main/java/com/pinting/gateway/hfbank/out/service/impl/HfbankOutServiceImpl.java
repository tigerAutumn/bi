package com.pinting.gateway.hfbank.out.service.impl;

import java.io.File;

import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.dafy.out.util.DafyOutConstant;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hfbank.out.enums.NoticeUrl;
import com.pinting.gateway.hfbank.out.model.*;
import com.pinting.gateway.hfbank.out.service.HfbankOutService;
import com.pinting.gateway.hfbank.out.util.HfbankOutConstant;
import com.pinting.gateway.util.CommunicateUtil;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.FileUtil;

/**
 * 恒丰银行存管相关业务，向恒丰银行发起请求 实现类接口
 * @author Dragon & cat
 * @date 2016-11-24
 */
@Service
public class HfbankOutServiceImpl implements HfbankOutService {

	private static Logger logger = LoggerFactory.getLogger(HfbankOutServiceImpl.class);



	/**
	 * 账单同步
	 */
	@Override
	public QueryBillResModel queryBill(
			QueryBillReqModel reqModel) {
		String requestSeq = String.valueOf(System.currentTimeMillis());
		reqModel.setRequestSeq(requestSeq);

		logger.info("恒丰银行账单同步>>>model:"+reqModel.toString());
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,"h");
		QueryBillResModel resModel = JSON.parseObject(result,QueryBillResModel.class);
		//QueryBillResModel resModel =  new QueryBillResModel();
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.TRANSCODE_NOT_FOUND);
		}
		return resModel;
	}

	/* ============================== 浣熊写的接口开始 =============================== */
	// TODO 请求参数均在business处理。调用恒丰接口，获得返回值，直接在当前方法内进行判断（此步骤还没有做，等待通讯类方法实现，之后再做处理）

	/**
	 * 1. 批量开户（四要素绑卡）
	 * 2. 批量开户（实名认证），针对借款人
	 * 3. 批量换卡
	 * 4. 短验绑卡申请
	 * 5. 短验绑卡确认
	 * 6. 网关充值请求
	 * 7. 充值回调通知 异步通知，不在此类内
	 * 		@see com.pinting.gateway.hfbank.in.service.HfbankInService
	 * 8. 快捷充值请求
	 * 9. 快捷充值确认
	 * 10. 借款人扣款还款（代扣）
	 * 11. 借款人扣款还款（代扣）通知 异步通知，不在此类内
	 * 		@see com.pinting.gateway.hfbank.in.service.HfbankInService
	 * 12. 批量提现
	 * 13. 提现通知 异步通知，不在此类内
	 * 		@see com.pinting.gateway.hfbank.in.service.HfbankInService
	 * 14. 订单状态查询
	 * 15. 资金余额查询
	 * 16. 账户余额明细查询
	 * 17. 标的账户余额查询
	 * 18. 平台提现 
	 * 19. 平台充值
	 */

	@Override
	public PlatChargeResModel platCharge(PlatChargeReqModel req) {
		logger.info("恒丰银行平台充值请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.PLAT_CHARGE.getCode());
		PlatChargeResModel resModel = JSON.parseObject(result, PlatChargeResModel.class);
		logger.info("恒丰银行平台充值响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}
	
	@Override
	public PlatWithDrawResModel platWithdraw(PlatWithDrawReqModel req) {
		logger.info("恒丰银行平台提现请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.PLAT_WITHDRAW.getCode());
		PlatWithDrawResModel resModel = JSON.parseObject(result, PlatWithDrawResModel.class);
		logger.info("恒丰银行平台提现响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}
		
	@Override
	public BatchRegistExtResModel batchBindCard4Elements(BatchRegistExtReqModel req) {
		logger.info("恒丰银行批量绑卡（四要素认证）请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.BATCH_REGIST_EXT.getCode());
		BatchRegistExtResModel resModel = JSON.parseObject(result,BatchRegistExtResModel.class);
		logger.info("恒丰银行批量绑卡（四要素认证）响应信息：{}", JSONObject.fromObject(resModel));

		return resModel;
	}

	@Override
	public BatchRegistExt3ResModel batchBindCardRealNameAuth(BatchRegistExt3ReqModel req) {
		logger.info("恒丰银行批量开户（实名认证）请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.BATCH_REGIST_EXT3.getCode());
		BatchRegistExt3ResModel resModel = JSON.parseObject(result,BatchRegistExt3ResModel.class);
		logger.info("恒丰银行批量开户（实名认证）响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public BatchUpdateCardExtResModel batchChangeCard(BatchUpdateCardExtReqModel req) {
		logger.info("恒丰银行批量换卡请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.BATCH_UPDATE_CARD_EXT.getCode());
		BatchUpdateCardExtResModel resModel = JSON.parseObject(result,BatchUpdateCardExtResModel.class);
		logger.info("恒丰银行批量换卡响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public GetBinkCardCode2ResModel userPreBindCard(GetBinkCardCode2ReqModel req) {
		logger.info("恒丰银行短验绑卡申请请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.GET_BINK_CARD_CODE2.getCode());
		GetBinkCardCode2ResModel resModel = JSON.parseObject(result,GetBinkCardCode2ResModel.class);
		logger.info("恒丰银行短验绑卡申请响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public AddUser2ResModel userBindCard(AddUser2ReqModel req) {
		logger.info("恒丰银行短验绑卡确认请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.ADD_USER2.getCode());
		AddUser2ResModel resModel = JSON.parseObject(result,AddUser2ResModel.class);
		logger.info("恒丰银行短验绑卡确认响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public GatewayRechargeRequestResModel userGatewayRechargeRequest(GatewayRechargeRequestReqModel req) {
		logger.info("恒丰银行网关充值请求请求信息：{doCommunicate2Hf}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2HfEbank(req,HfbankOutConstant.hfbankUrl +NoticeUrl.GATEWAY_RECHARGE_REQUEST.getCode());
		GatewayRechargeRequestResModel resModel = new GatewayRechargeRequestResModel();
		if (result != null && result.contains("recode")) {
			resModel = JSON.parseObject(result,GatewayRechargeRequestResModel.class);
			logger.info("恒丰银行网关充值请求响应信息：{recode}", JSONObject.fromObject(result));
		}else {
			logger.info("恒丰银行网关充值请求响应信息：{html}", result);
			resModel.setHtml(result);
			resModel.setRecode("10000");
		}
		return resModel;
	}

	@Override
	public DirectQuickRequestResModel userQuickPayPreRecharge(DirectQuickRequestReqModel req) {
		logger.info("恒丰银行快捷充值请求请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.DIRECT_QUICK_REQUEST.getCode());
		DirectQuickRequestResModel resModel = JSON.parseObject(result,DirectQuickRequestResModel.class);
		logger.info("恒丰银行快捷充值请求响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public DirectQuickConfirmResModel userQuickPayConfirmRecharge(DirectQuickConfirmReqModel req) {
		logger.info("恒丰银行快捷充值确认请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.DIRECT_QUICK_CONFIRM.getCode());
		DirectQuickConfirmResModel resModel = JSON.parseObject(result,DirectQuickConfirmResModel.class);
		logger.info("恒丰银行快捷充值确认响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public BorrowCutRepayResModel borrowCutRepayDK(BorrowCutRepayReqModel req) {
		logger.info("恒丰银行借款人扣款还款（代扣）请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.BORROW_GUT_REPAY.getCode());
		BorrowCutRepayResModel resModel = JSON.parseObject(result,BorrowCutRepayResModel.class);
		logger.info("恒丰银行借款人扣款还款（代扣）响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public BatchWithdrawExtResModel userBatchWithdraw(BatchWithdrawExtReqModel req) {
		logger.info("恒丰银行批量提现请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.BATCH_WITHDRAW_EXT.getCode());
		BatchWithdrawExtResModel resModel = JSON.parseObject(result,BatchWithdrawExtResModel.class);
		logger.info("恒丰银行批量提现响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public QueryOrderResModel queryOrder(QueryOrderReqModel req) {
		logger.info("恒丰银行订单状态查询请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.QUERY_ORDER.getCode());
		QueryOrderResModel resModel = JSON.parseObject(result,QueryOrderResModel.class);
		logger.info("恒丰银行订单状态查询响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public GetAccountInfoResModel queryAccountInfo(GetAccountInfoReqModel req) {
		logger.info("恒丰银行资金余额查询请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.GET_ACCOUNT_INFO.getCode());
		GetAccountInfoResModel resModel = JSON.parseObject(result,GetAccountInfoResModel.class);
		logger.info("恒丰银行资金余额查询响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public GetAccountNBalaceResModel queryAccountLeftAmountInfo(GetAccountNBalaceReqModel req) {
		logger.info("恒丰银行账户余额明细查询请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.GET_ACCOUNTN_BALACE.getCode());
		GetAccountNBalaceResModel resModel = JSON.parseObject(result,GetAccountNBalaceResModel.class);
		logger.info("恒丰银行账户余额明细查询响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public GetProductNBalanceResModel queryProductBalance(GetProductNBalanceReqModel req) {
		logger.info("恒丰银行标的账户余额查询请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.GET_PRODUCTN_BALACE.getCode());
		GetProductNBalanceResModel resModel = JSON.parseObject(result,GetProductNBalanceResModel.class);
		logger.info("恒丰银行标的账户余额查询响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}




	/* ============================== 浣熊写的接口结束 =============================== */



	/* ============================== 龙猫写的接口开始 =============================== */
	@Override
	public PublishResModel publish(PublishReqModel reqModel) {
		logger.info("恒丰银行标的发布请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.PUBLISH.getCode());
		PublishResModel resModel = JSON.parseObject(result,PublishResModel.class);
		logger.info("恒丰银行标的发布响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public EstablishOrAbandonResModel establishOrAbandon(
			EstablishOrAbandonReqModel reqModel) {
		logger.info("恒丰银行标的成废请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.ESTABLISH_OR_ABANDON.getCode());
		EstablishOrAbandonResModel resModel = JSON.parseObject(result,EstablishOrAbandonResModel.class);
		logger.info("恒丰银行标的成废响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public BatchExtBuyResModel batchExtBuy(BatchExtBuyReqModel reqModel) {
		logger.info("恒丰银行批量投标请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.BATCH_EXT.getCode());
		BatchExtBuyResModel resModel = JSON.parseObject(result,BatchExtBuyResModel.class);
		logger.info("恒丰银行批量投标响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public ChargeOffResModel chargeOff(ChargeOffReqModel reqModel) {
		logger.info("恒丰银行标的出账请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.CHANGE_OFF.getCode());
		ChargeOffResModel resModel = JSON.parseObject(result,ChargeOffResModel.class);
		logger.info("恒丰银行标的出账响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public ModifyPayOutAcctResModel modifyPayOutAcct(
			ModifyPayOutAcctReqModel reqModel) {
		logger.info("恒丰银行标的出账信息修改请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.MODIFY_PAY_OUT_ACCT.getCode());
		ModifyPayOutAcctResModel resModel = JSON.parseObject(result,ModifyPayOutAcctResModel.class);
		logger.info("恒丰银行标的出账信息修改响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public TransferDebtResModel transferDebt(TransferDebtReqModel reqModel) {
		logger.info("恒丰银行标的转让请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.TRANSFER_DEBT.getCode());
		TransferDebtResModel resModel = JSON.parseObject(result,TransferDebtResModel.class);
		logger.info("恒丰银行标的转让响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public BatchExtRepayResModel batchExtRepay(BatchExtRepayReqModel reqModel) {
		logger.info("恒丰银行借款人批量还款请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.REPAYACTION_BATCH_EXT.getCode());
		BatchExtRepayResModel resModel = JSON.parseObject(result,BatchExtRepayResModel.class);
		logger.info("恒丰银行借款人批量还款响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public ProdRepayResModel prodRepay(ProdRepayReqModel reqModel) {
		logger.info("恒丰银行标的还款请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.PROD_REPAY.getCode());
		ProdRepayResModel resModel = JSON.parseObject(result,ProdRepayResModel.class);
		logger.info("恒丰银行标的还款响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public PlatformTransferResModel platformTransfer(
			PlatformTransferReqModel reqModel) {
		logger.info("恒丰银行平台转账个人请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.PLAT_TRANS.getCode());
		PlatformTransferResModel resModel = JSON.parseObject(result,PlatformTransferResModel.class);
		logger.info("恒丰银行平台转账个人响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public PlatformAccountConverseResModel platformAccountConverse(
			PlatformAccountConverseReqModel reqModel) {
		logger.info("恒丰银行平台不同账户转账请求信息：{}", JSONObject.fromObject(reqModel));

		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.PLATFORM_ACCOUNT_CONVERSE.getCode());
		PlatformAccountConverseResModel resModel = JSON.parseObject(result,PlatformAccountConverseResModel.class);
		logger.info("恒丰银行平台不同账户转账响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public CompensateRepayResModel compensateRepay(
			CompensateRepayReqModel reqModel) {
		logger.info("恒丰银行平台标的代偿（委托）还款请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.COMPENSATE_REPAY.getCode());
		CompensateRepayResModel resModel = JSON.parseObject(result,CompensateRepayResModel.class);
		logger.info("恒丰银行平台标的代偿（委托）还款响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public RepayCompensateResModel repayCompensate(
			RepayCompensateReqModel reqModel) {
		logger.info("恒丰银行平台借款人还款代偿（委托）请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel,HfbankOutConstant.hfbankUrl +NoticeUrl.REPAY_COMPENSATE.getCode());
		RepayCompensateResModel resModel = JSON.parseObject(result,RepayCompensateResModel.class);
		logger.info("恒丰银行平台借款人还款代偿（委托）响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	/* ============================== 龙猫写的接口结束=============================== */


	@Override
	public FundDataCheckResModel fundDataCheck(FundDataCheckReqModel reqModel) {
		logger.info("恒丰银行对账文件资金进出数据请求信息：{}", JSONObject.fromObject(reqModel));
		String result = CommunicateUtil.doCommunicate2Hf(reqModel, HfbankOutConstant.hfbankUrl + NoticeUrl.FUNDDATA_CHECK.getCode());
		FundDataCheckResModel resModel;
		if (StringUtil.isNotEmpty(result) && result.contains("recode")) {
			resModel = JSON.parseObject(result, FundDataCheckResModel.class);
			resModel.setDestFileName(Constants.HF_FILE_NOT_EXIST);
		} else {			
			String resultPlat = StringUtil.substringBefore(result, "|");
			if (!GlobEnvUtil.get("hfbank.platNo").equals(resultPlat)) {
				throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "对账文件下载失败");
			}
			try {
				String filePath = GlobEnvUtil.get("baofoo.account.fileFiPath");
				String fileNamePrefix = Constants.HF_FUNDDTACHECK_FILENAME_PREFIX;
				if(filePath.lastIndexOf(File.separator) != filePath.length()-1){
					filePath = filePath+File.separator;
				} 
				String fileName = filePath + fileNamePrefix + "_"+reqModel.getPaycheck_date()+".txt";
				String destFileName = filePath + fileNamePrefix + "_"+reqModel.getPaycheck_date()+".zip";
				if(FileUtil.createFile(fileName)) {
					FileUtil.writeToFile(fileName, result, false);
					FileUtil.zipFiles(FilenameUtils.getFullPath(fileName), FilenameUtils.getName(fileName), destFileName);
					FileUtil.deleteFile(fileName);
					logger.info("fileName:{},destFileName:{}",fileName,destFileName);
					resModel = new FundDataCheckResModel();
					resModel.setDestFileName(destFileName);
					resModel.setResResult(result);
				} else {
					resModel = new FundDataCheckResModel();
					resModel.setDestFileName(Constants.HF_FILE_CREATE_FAIL);
				}
				resModel.setRecode(Constants.BSRESCODE_DEP_SUCCESS);
				resModel.setRemsg(Constants.HFRESCODE_DEP_NOTICE_SUCCESS);
			} catch (Exception e) {
				logger.error("恒丰银行对账文件资金进出数据异常："+e.getMessage(), e);
				throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "对账文件参数校验失败");
			}
		}
		logger.info("恒丰银行对账文件资金进出数据响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;	
	}

	@Override
	public BalanceInfoResModel balanceInfo(BalanceInfoReqModel req) {
		logger.info("恒丰银行对账文件账户余额数据请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.BALANCE_INFO.getCode());
		BalanceInfoResModel resModel;
		if (StringUtil.isNotEmpty(result) && result.contains("recode")) {
			resModel = JSON.parseObject(result, BalanceInfoResModel.class);
			resModel.setDestFileName(Constants.HF_FILE_NOT_EXIST);
		} else {
			String resultPlat = StringUtil.substringBefore(result, "|");
			if(!GlobEnvUtil.get("hfbank.platNo").equals(resultPlat)) {
				throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "对账文件下载失败");
			}
			try {
				String filePath = GlobEnvUtil.get("baofoo.account.fileFiPath");
				String fileNamePrefix = Constants.HF_BALANCEINFO_FILENAME_PREFIX;
				if(filePath.lastIndexOf(File.separator) != filePath.length()-1) {
					filePath = filePath+File.separator;
				}
				String fileName = filePath + fileNamePrefix + "_" + req.getPaycheck_date() + ".txt";
				String destFileName = filePath + fileNamePrefix + "_" + req.getPaycheck_date() + ".zip";
				if(FileUtil.createFile(fileName)) {
					FileUtil.writeToFile(fileName, StringUtils.substringBeforeLast(result, "|")+"|", false);
					FileUtil.zipFiles(FilenameUtils.getFullPath(fileName), FilenameUtils.getName(fileName), destFileName);
					FileUtil.deleteFile(fileName);
					logger.info("destFileName:{}",destFileName);
					resModel = new BalanceInfoResModel();
					resModel.setDestFileName(destFileName);
					resModel.setResResult(result);
				} else {
					resModel = new BalanceInfoResModel();
					resModel.setDestFileName(Constants.HF_FILE_CREATE_FAIL);
				}
				resModel.setRecode(Constants.BSRESCODE_DEP_SUCCESS);
				resModel.setRemsg(Constants.HFRESCODE_DEP_NOTICE_SUCCESS);
			} catch (Exception e) {
				logger.error("恒丰银行对账文件账户余额数据异常："+e.getMessage(), e);
				throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "对账文件参数校验失败");
			}
		}
		logger.info("恒丰银行对账文件账户余额数据响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public LiquidationInfoResModel queryLiquidationInfo(LiquidationInfoReqModel req) {
		logger.info("恒丰银行清算状态查询数据请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req, HfbankOutConstant.hfbankUrl +NoticeUrl.QUERY_LIQUIDATION_INFO.getCode());
		LiquidationInfoResModel resModel = JSON.parseObject(result, LiquidationInfoResModel.class);
		logger.info("恒丰银行清算状态查询数据响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}
	
	@Override
	public GetFundListResModel getFundList(GetFundListReqModel req) {
		logger.info("资金变动明细查询数据请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req, HfbankOutConstant.hfbankUrl +NoticeUrl.GET_FUNDLIST_INFO.getCode());
		GetFundListResModel resModel = JSON.parseObject(result, GetFundListResModel.class);
		logger.info("资金变动明细查询数据响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public GetBinkCardInfoResModel addBankCard(GetBinkCardInfoReqModel req) {
		logger.info("绑卡数据请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req, HfbankOutConstant.hfbankUrl +NoticeUrl.USER_ADD_CARD.getCode());
		GetBinkCardInfoResModel resModel = JSON.parseObject(result, GetBinkCardInfoResModel.class);
		logger.info("绑卡数据响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

	@Override
	public UpdatePlatAcctResModel updatePlatAcct(UpdatePlatAcctReqModel req) {
		logger.info("客户信息变更请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req, HfbankOutConstant.hfbankUrl +NoticeUrl.USER_ACTION_UPDATEPLATACCT.getCode());
		UpdatePlatAcctResModel resModel = JSON.parseObject(result, UpdatePlatAcctResModel.class);
		logger.info("客户信息变更响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}
	@Override
	public QueryFundOrderResModel queryFundOrderInfo(QueryFundOrderReqModel req) {
		logger.info("恒丰银行充值订单状态查询请求信息：{}", JSONObject.fromObject(req));
		String result = CommunicateUtil.doCommunicate2Hf(req,HfbankOutConstant.hfbankUrl +NoticeUrl.QUERY_FUND_ORDER.getCode());
		QueryFundOrderResModel resModel = JSON.parseObject(result,QueryFundOrderResModel.class);
		logger.info("恒丰银行充值订单状态查询响应信息：{}", JSONObject.fromObject(resModel));
		return resModel;
	}

}
