package com.pinting.gateway.hfbank.in.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_HFBank_BorrowCutRepayNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_HFBank_GatewayRechargeNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_HFBank_LiquidationNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_HFBank_OutOfAccount;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_HFBank_PlatTopUpNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_HFBank_PlatWithdrawNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_HFBank_UserBatchWithdrawNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BResMsg_HFBank_BorrowCutRepayNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BResMsg_HFBank_GatewayRechargeNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BResMsg_HFBank_LiquidationNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BResMsg_HFBank_OutOfAccount;
import com.pinting.gateway.hessian.message.hfbank.G2BResMsg_HFBank_PlatTopUpNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BResMsg_HFBank_PlatWithdrawNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BResMsg_HFBank_UserBatchWithdrawNotice;
import com.pinting.gateway.hessian.message.hfbank.model.BorrowCutRepayPlatcust;
import com.pinting.gateway.hfbank.in.model.BatchWithdrawExtNoticeReqModel;
import com.pinting.gateway.hfbank.in.model.BatchWithdrawExtNoticeResModel;
import com.pinting.gateway.hfbank.in.model.BorrowCutRepayNoticeReqModel;
import com.pinting.gateway.hfbank.in.model.BorrowCutRepayNoticeResModel;
import com.pinting.gateway.hfbank.in.model.BorrowCutRepayPlatcustReqModel;
import com.pinting.gateway.hfbank.in.model.GatewayRechargeNoticeReqModel;
import com.pinting.gateway.hfbank.in.model.GatewayRechargeNoticeResModel;
import com.pinting.gateway.hfbank.in.model.LiquidationNoticeReqModel;
import com.pinting.gateway.hfbank.in.model.LiquidationNoticeResModel;
import com.pinting.gateway.hfbank.in.model.OutOfAccountReqModel;
import com.pinting.gateway.hfbank.in.model.OutOfAccountResModel;
import com.pinting.gateway.hfbank.in.model.PlatTopUpNoticeReqModel;
import com.pinting.gateway.hfbank.in.model.PlatTopUpNoticeResModel;
import com.pinting.gateway.hfbank.in.model.PlatWithdrawNoticeReqModel;
import com.pinting.gateway.hfbank.in.model.PlatWithdrawNoticeResModel;
import com.pinting.gateway.hfbank.in.service.HfbankInService;
import com.pinting.gateway.util.Constants;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 恒丰银行资金存管，异步通知回来的服务
 */
@Service
public class HfbankInServiceImpl implements HfbankInService {
	private final Logger logger = LoggerFactory.getLogger(HfbankInServiceImpl.class);
	
	
    @Autowired
    @Qualifier("gatewayHFBankService")
    private HessianService gatewayHFBankService;



    @Override
    public GatewayRechargeNoticeResModel gatewayRechargeNotice(GatewayRechargeNoticeReqModel req) {
    	
    	G2BReqMsg_HFBank_GatewayRechargeNotice reqHessian = new G2BReqMsg_HFBank_GatewayRechargeNotice();
        reqHessian.setPlat_no(req.getPlat_no());
        reqHessian.setOrder_no(req.getOrder_no());
        reqHessian.setPlat_no(req.getPlat_no());
        reqHessian.setType(req.getType());
        reqHessian.setOrder_amt(Double.parseDouble(req.getOrder_amt()));
        reqHessian.setTrans_date(req.getTrans_date());
        reqHessian.setTrans_time(req.getTrans_time());
        reqHessian.setPay_order_no(req.getPay_order_no());
        reqHessian.setPay_finish_date(req.getPay_finish_date());
        reqHessian.setPay_finish_time(req.getPay_finish_time());
        reqHessian.setOrder_status(req.getOrder_status());
        reqHessian.setPay_amt(Double.parseDouble(req.getPay_amt()));
        reqHessian.setError_info(req.getError_info());
        reqHessian.setError_no(req.getError_no());
        reqHessian.setHost_req_serial_no(req.getHost_req_serial_no());
        reqHessian.setPlatcust(req.getPlatcust());
        G2BResMsg_HFBank_GatewayRechargeNotice resHessian = (G2BResMsg_HFBank_GatewayRechargeNotice) gatewayHFBankService.handleMsg(reqHessian);
        GatewayRechargeNoticeResModel res = new GatewayRechargeNoticeResModel();
        
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resHessian.getResCode())) {
			logger.error("恒丰银行（存管）网关充值回调通知异常："+resHessian.getResMsg());
			res.setRecode(Constants.HFRESCODE_DEP_NOTICE_FAIL);
			return res;
		}
		
        res.setRecode(Constants.HFRESCODE_DEP_NOTICE_SUCCESS);
        return res;
    }

    @Override
    public BorrowCutRepayNoticeResModel borrowCutRepayNotice(BorrowCutRepayNoticeReqModel req) {
        G2BReqMsg_HFBank_BorrowCutRepayNotice reqHessian = new G2BReqMsg_HFBank_BorrowCutRepayNotice();
        reqHessian.setOrder_no(req.getOrder_no());
        reqHessian.setAmt(Double.parseDouble(req.getAmt()));
        reqHessian.setBank_no(req.getBank_no());
        reqHessian.setCode(req.getCode());
        reqHessian.setPlat_no(req.getPlat_no());
        List<BorrowCutRepayPlatcust> platcusts = new ArrayList<>();
        if(!CollectionUtils.isEmpty(req.getPlatcustList())){
            for (BorrowCutRepayPlatcustReqModel platcustTemp : req.getPlatcustList()){
                BorrowCutRepayPlatcust platcust = new BorrowCutRepayPlatcust();
                platcust.setPlatcust(platcustTemp.getPlatcust());
                platcust.setAmt(Double.parseDouble(platcustTemp.getAmt()));
                platcusts.add(platcust);
            }
        }
        reqHessian.setPlatcustList(platcusts);
        G2BResMsg_HFBank_BorrowCutRepayNotice resHessian = (G2BResMsg_HFBank_BorrowCutRepayNotice) gatewayHFBankService.handleMsg(reqHessian);
        BorrowCutRepayNoticeResModel res = new BorrowCutRepayNoticeResModel();
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resHessian.getResCode())) {
			logger.error("恒丰银行（存管）借款人扣款还款回调通知异常："+resHessian.getResMsg());
			res.setRecode(Constants.HFRESCODE_DEP_NOTICE_FAIL);
			return res;
		}
        res.setRecode(Constants.HFRESCODE_DEP_NOTICE_SUCCESS);
        return res;
    }

    @Override
    public BatchWithdrawExtNoticeResModel userBatchWithdrawNotice(BatchWithdrawExtNoticeReqModel req) {
        G2BReqMsg_HFBank_UserBatchWithdrawNotice reqHessian = new G2BReqMsg_HFBank_UserBatchWithdrawNotice();

        reqHessian.setPlat_no(req.getPlat_no());
        reqHessian.setPlatcust(req.getPlatcust());
        reqHessian.setOrder_no(req.getOrder_no());
        reqHessian.setOrder_amt(Double.parseDouble(req.getOrder_amt()));
        reqHessian.setTrans_date(req.getTrans_date());
        reqHessian.setTrans_time(req.getTrans_time());
        reqHessian.setPay_order_no(req.getPay_order_no());
        reqHessian.setPay_finish_date(req.getPay_finish_date());
        reqHessian.setPay_finish_time(req.getPay_finish_time());
        reqHessian.setOrder_status(req.getOrder_status());
        reqHessian.setPay_amt(Double.parseDouble(req.getPay_amt()));
        reqHessian.setError_info(req.getError_info());
        reqHessian.setError_no(req.getError_no());
        reqHessian.setHost_req_serial_no(req.getHost_req_serial_no());
        reqHessian.setPay_path(req.getPay_path());
        
        G2BResMsg_HFBank_UserBatchWithdrawNotice resHessian = (G2BResMsg_HFBank_UserBatchWithdrawNotice) gatewayHFBankService.handleMsg(reqHessian);
        BatchWithdrawExtNoticeResModel res = new BatchWithdrawExtNoticeResModel();
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resHessian.getResCode())) {
			logger.error("恒丰银行（存管）批量提现回调通知异常："+resHessian.getResMsg());
			res.setRecode(Constants.HFRESCODE_DEP_NOTICE_FAIL);
			return res;
		}
        res.setRecode(Constants.HFRESCODE_DEP_NOTICE_SUCCESS);
        return res;
    }

	@Override
	public OutOfAccountResModel outOfAccount(OutOfAccountReqModel req) {
		 OutOfAccountResModel res = new OutOfAccountResModel();
		if (StringUtil.isBlank(req.getPay_finish_date())) {
			logger.error("恒丰银行（存管）标的出账回调通知参数缺失：pay_finish_date为空");
			res.setRecode("param empty | pay_finish_date");
			return res;
		}
		if (StringUtil.isBlank(req.getPay_finish_time())) {
			logger.error("恒丰银行（存管）标的出账回调通知参数缺失：pay_finish_time为空");
			res.setRecode("param empty | pay_finish_time");
			return res;
		}
		G2BReqMsg_HFBank_OutOfAccount reqHessian = new G2BReqMsg_HFBank_OutOfAccount();
        reqHessian.setPlat_no(req.getPlat_no());
        reqHessian.setOrder_no(req.getOrder_no());
        reqHessian.setOut_amt(Double.parseDouble(req.getOut_amt()));
        reqHessian.setPlatucst(req.getPlatcust());
        reqHessian.setOpen_branch(req.getOpen_branch());
        reqHessian.setWithdraw_account(req.getWithdraw_account());
        reqHessian.setPayee_name(req.getPayee_name());
        reqHessian.setPay_finish_date(DateUtil.parse(req.getPay_finish_date() + req.getPay_finish_time(), "YYYYMMDDHHmmSS"));
        reqHessian.setPay_finish_time(DateUtil.parse(req.getPay_finish_date() + req.getPay_finish_time(), "YYYYMMDDHHmmSS"));
        reqHessian.setOrder_status(req.getOrder_status());
        reqHessian.setError_info(req.getError_info());
        reqHessian.setError_no(req.getError_no());
        reqHessian.setHost_req_serial_no(req.getHost_req_serial_no());
        reqHessian.setPay_path(req.getPay_path()); 
        
         G2BResMsg_HFBank_OutOfAccount resHessian = (G2BResMsg_HFBank_OutOfAccount) gatewayHFBankService.handleMsg(reqHessian);
         res.setOrder_no(req.getOrder_no());
 		 if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resHessian.getResCode())) {
			logger.error("恒丰银行（存管）标的出账回调通知异常："+resHessian.getResMsg());
			res.setRecode(Constants.HFRESCODE_DEP_NOTICE_FAIL);
			return res;
		 }
         res.setRecode(Constants.HFRESCODE_DEP_NOTICE_SUCCESS);
         return res;
	}

	@Override
	public PlatTopUpNoticeResModel platTopUpNotice(PlatTopUpNoticeReqModel req) {
		
		G2BReqMsg_HFBank_PlatTopUpNotice reqHessian = new G2BReqMsg_HFBank_PlatTopUpNotice();
		reqHessian.setPlat_no(req.getPlat_no());
		reqHessian.setOrder_no(req.getOrder_no());
		reqHessian.setAmt(Double.parseDouble(req.getAmt()));
		reqHessian.setCode(req.getCode());

        G2BResMsg_HFBank_PlatTopUpNotice resHessian = (G2BResMsg_HFBank_PlatTopUpNotice) gatewayHFBankService.handleMsg(reqHessian);
        PlatTopUpNoticeResModel res = new PlatTopUpNoticeResModel();
        res.setOrder_no(req.getOrder_no());
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resHessian.getResCode())) {
			logger.error("恒丰银行（存管）平台充值回调通知异常："+resHessian.getResMsg());
			res.setRecode(Constants.HFRESCODE_DEP_NOTICE_FAIL);
			return res;
		}
        res.setRecode(Constants.HFRESCODE_DEP_NOTICE_SUCCESS);
        return res;
	}

	@Override
	public PlatWithdrawNoticeResModel platWithdrawNotice(
			PlatWithdrawNoticeReqModel req) {
		G2BReqMsg_HFBank_PlatWithdrawNotice reqHessian = new G2BReqMsg_HFBank_PlatWithdrawNotice();
		reqHessian.setPlat_no(req.getPlat_no());
		reqHessian.setOrder_no(req.getOrder_no());
		reqHessian.setAmt(Double.parseDouble(req.getAmt()));
		reqHessian.setCode(req.getCode());
        G2BResMsg_HFBank_PlatWithdrawNotice resHessian = (G2BResMsg_HFBank_PlatWithdrawNotice) gatewayHFBankService.handleMsg(reqHessian);
        PlatWithdrawNoticeResModel res = new PlatWithdrawNoticeResModel();
        res.setOrder_no(req.getOrder_no());
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resHessian.getResCode())) {
			logger.error("恒丰银行（存管）平台提现回调通知异常："+resHessian.getResMsg());
			res.setRecode(Constants.HFRESCODE_DEP_NOTICE_FAIL);
			return res;
		}
        res.setRecode(Constants.HFRESCODE_DEP_NOTICE_SUCCESS);
        return res;
	}

	@Override
	public LiquidationNoticeResModel liquidationNotice(
			LiquidationNoticeReqModel req) {
		G2BReqMsg_HFBank_LiquidationNotice reqHessian = new G2BReqMsg_HFBank_LiquidationNotice();
		reqHessian.setPlat_no(req.getPlat_no());
		reqHessian.setLiquidation_flag(req.getLiquidation_flag());
        G2BResMsg_HFBank_LiquidationNotice resHessian = (G2BResMsg_HFBank_LiquidationNotice) gatewayHFBankService.handleMsg(reqHessian);
        LiquidationNoticeResModel res = new LiquidationNoticeResModel();
        res.setOrder_no(req.getOrder_no());
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resHessian.getResCode())) {
			logger.error("恒丰银行（存管）清算成功通知异常："+resHessian.getResMsg());
			res.setRecode(Constants.HFRESCODE_DEP_NOTICE_FAIL);
			return res;
		}
        res.setRecode(Constants.HFRESCODE_DEP_NOTICE_SUCCESS);
        return res;
	}


}
