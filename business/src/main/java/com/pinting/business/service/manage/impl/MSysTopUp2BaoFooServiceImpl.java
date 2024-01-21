package com.pinting.business.service.manage.impl;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.service.impl.RepayPaymentServiceImpl;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.vo.BsBaoFooPreBindCardVO;
import com.pinting.business.model.vo.PreTopUpResVO;
import com.pinting.business.service.manage.MSysTopUp2BaoFooService;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_BindCard;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_BindCardConfirm;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_QuickPay;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_QuickPayConfirm;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_BindCard;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_BindCardConfirm;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_QuickPay;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_QuickPayConfirm;
import com.pinting.gateway.out.service.BaoFooTransportService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 剑钊
 *
 * @2016/10/24 17:48.
 */
@Service
public class MSysTopUp2BaoFooServiceImpl implements MSysTopUp2BaoFooService {

    private final Logger log = LoggerFactory.getLogger(MSysTopUp2BaoFooServiceImpl.class);

    @Autowired
    private BaoFooTransportService baoFooTransportService;

    @Override
    public PreTopUpResVO preTopUp(String bindId, Double amount) {

        String orderNo= Util.generateOrderNo4BaoFoo(8);

        B2GReqMsg_BaoFooQuickPay_QuickPay quickPay=new B2GReqMsg_BaoFooQuickPay_QuickPay();
        quickPay.setBindId(bindId);
        quickPay.setTxnAmt(MoneyUtil.multiply(amount,100d).toString());
        quickPay.setTrans_serial_no(Util.generateUserTransNo4BaoFoo());
        quickPay.setTransId(orderNo);

        B2GResMsg_BaoFooQuickPay_QuickPay res;
        try {
            res = baoFooTransportService.quickPay(quickPay);
            log.info(">>>>预下单响应："+ (res!=null ? JSONObject.fromObject(res).toString():"null") +"<<<<");
        } catch (Exception e) {
            e.printStackTrace();
            throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
        }

        PreTopUpResVO vo=new PreTopUpResVO();

        if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {

            vo.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
            vo.setTransId(orderNo);
        }else {
            vo.setResCode(res.getResCode());
            vo.setResMsg(res.getResMsg());
        }

        return vo;
    }

    @Override
    public PreTopUpResVO topUp(String transId, String bindId, Double amount, String smsCode) {

        B2GReqMsg_BaoFooQuickPay_QuickPayConfirm quickPayConfirm=new B2GReqMsg_BaoFooQuickPay_QuickPayConfirm();
        quickPayConfirm.setTrans_id(transId);
        quickPayConfirm.setBind_id(bindId);
        quickPayConfirm.setTrans_serial_no(Util.generateUserTransNo4BaoFoo());
        quickPayConfirm.setSms_code(smsCode);
        quickPayConfirm.setTxn_amt(MoneyUtil.multiply(amount,100d).toString());
        quickPayConfirm.setAdditional_info("系统充值");

        B2GResMsg_BaoFooQuickPay_QuickPayConfirm res;
        try {
            res = baoFooTransportService.quickPayConfirm(quickPayConfirm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
        }

        PreTopUpResVO vo=new PreTopUpResVO();

        if (!res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {

            if (res.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())){
                vo.setResCode(ConstantUtil.BSRESCODE_ING);
            }else {
                vo.setResCode(res.getResCode());
                vo.setResMsg(res.getResMsg());
            }

        }else {
            vo.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        }

        return vo;
    }

	@Override
	public String preBindCard(String bankCardNo,String mobile) {

		B2GReqMsg_BaoFooQuickPay_BindCard quickPay=new B2GReqMsg_BaoFooQuickPay_BindCard();
        
        String payBindOrderNo=Util.generateOrderNo4BaoFoo(8);
        quickPay.setTransId(payBindOrderNo); //请求宝付订单号
        quickPay.setAccNo(bankCardNo); //卡号
        quickPay.setMobile(mobile); //手机号
        quickPay.setTrans_serial_no(Util.generateUserOrderNo4Pay19(1000561));
        
        B2GResMsg_BaoFooQuickPay_BindCard res;
        res = baoFooTransportService.bindCard(quickPay);
        log.info(">>>>预下单响应："+ (res!=null ? JSONObject.fromObject(res).toString():"null") +"<<<<");
        if (!res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
        	//return payBindOrderNo;
        	throw new PTMessageException(res.getResCode(), res.getResMsg());
        }else {
        	return payBindOrderNo;
        }
        
	}

	@Override
	public void bindCardConfirm(String transId,String smsCode,String bankCardNo,String mobile,String idCard,String userName,String bankCode) {
		B2GReqMsg_BaoFooQuickPay_BindCardConfirm quickPay=new B2GReqMsg_BaoFooQuickPay_BindCardConfirm();
       
        quickPay.setTrans_id(transId); //请求宝付订单号
        quickPay.setSms_code(smsCode);
        quickPay.setAcc_no(bankCardNo);//卡号
        quickPay.setMobile(mobile); //手机号
        quickPay.setId_card(idCard);
        quickPay.setId_holder(userName);
        quickPay.setPay_code(bankCode);
        quickPay.setTrans_serial_no(Util.generateUserOrderNo4Pay19(1000561));

        B2GResMsg_BaoFooQuickPay_BindCardConfirm res;
        res = baoFooTransportService.bindCardConfirm(quickPay);
        log.info(">>>>确认下单响应："+ (res!=null ? JSONObject.fromObject(res).toString():"null") +"<<<<");
        if (!res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
        	throw new PTMessageException(res.getResCode(), res.getResMsg());
        }
	}
	
	
}
