package com.pinting.gateway.in.facade.zsd;

import com.pinting.business.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdRepay_BadDebt;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdRepay_CutpaymentRepay;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdRepay_LateRepayNotice;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdRepay_PreRepay;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdRepay_QueryRepayResult;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdRepay_RepayConfirm;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdRepay_BadDebt;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdRepay_CutpaymentRepay;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdRepay_LateRepayNotice;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdRepay_PreRepay;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdRepay_QueryRepayResult;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdRepay_RepayConfirm;

/**
 * Created by zhangbao on 2017/8/30.
 */

@Component("ZsdRepay")
public class ZsdRepayFacade {

    private Logger logger = LoggerFactory.getLogger(ZsdRepayFacade.class);
    
    @Autowired
	private DepFixedRepayPaymentService  depFixedRepayPaymentService;

    /**
     * 还款预下单
     * @param req
     * @param res
     */
    public void preRepay(G2BReqMsg_ZsdRepay_PreRepay req, G2BResMsg_ZsdRepay_PreRepay res){
    	logger.info("收到还款预下单请求");
    	if(StringUtil.isEmpty(req.getOrderNo())) {
    		throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_NOT_ENOUGH,"订单号不允许为空");
    	}
    	if(StringUtil.isEmpty(req.getUserId())) {
    		throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_NOT_ENOUGH,"用户编号不允许为空");
    	}
    	if(StringUtil.isEmpty(req.getLoanId())) {
    		throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_NOT_ENOUGH,"借款编号不允许为空");
    	}
    	if(StringUtil.isEmpty(req.getTotalAmount())) {
    		throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_NOT_ENOUGH,"还款总额不允许为空");
    	}
    	String bgwOrderNo = depFixedRepayPaymentService.doZsdPreRepay(req);
    	res.setBgwOrderNo(bgwOrderNo);
    }
    
    /**
     * 代扣还款
     * @param req
     * @param res
     * @throws Exception 
     */
    public void cutpaymentRepay(G2BReqMsg_ZsdRepay_CutpaymentRepay req, G2BResMsg_ZsdRepay_CutpaymentRepay res) throws Exception {
        logger.info("收到代扣还款请求");
        if(Constants.REQ_IS_OFFLINE_Y.equals(req.getIsOffline())){
            depFixedRepayPaymentService.repayOfflineZsd(req);
        }else{
            depFixedRepayPaymentService.repayApplyZsd(req);
        }

    }
    
    /**
     * 赞时贷代偿通知
     * @param req
     * @param res
     */
    public void lateRepayNotice(G2BReqMsg_ZsdRepay_LateRepayNotice req, G2BResMsg_ZsdRepay_LateRepayNotice res) {
        logger.info("收到代偿通知请求");
        depFixedRepayPaymentService.lateRepayZsdNotice(req);
    }

    /**
     * 确认还款
     * @param req
     * @param res
     */
    public void repayConfirm(G2BReqMsg_ZsdRepay_RepayConfirm req, G2BResMsg_ZsdRepay_RepayConfirm res) throws Exception {
        logger.info("收到确认还款请求");
        depFixedRepayPaymentService.doZsdRepayConfirm(req);
    }

    /**
     * 还款结果查询
     * @param req
     * @param res
     * @throws Exception
     */
    public void queryRepayResult(G2BReqMsg_ZsdRepay_QueryRepayResult req, G2BResMsg_ZsdRepay_QueryRepayResult res) throws Exception{
        if(StringUtil.isBlank(req.getOrderNo())){
            throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_NOT_ENOUGH);
        }
        logger.info("收到赞时贷还款结果查询请求");
        depFixedRepayPaymentService.zsdRepayResultQuery(req, res);
    }

    /**
     * 坏账处理
     * @param req
     * @param res
     * @throws Exception
     */
    public void badDebt(G2BReqMsg_ZsdRepay_BadDebt req, G2BResMsg_ZsdRepay_BadDebt res) throws Exception {
        logger.info("收到坏账处理请求");
    }
}
