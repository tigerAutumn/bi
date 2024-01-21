package com.pinting.gateway.in.facade.loan;

import java.util.Date;
import java.util.List;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanExample;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.model.LnRepayScheduleExample;
import com.pinting.business.service.loan.RepayQueryService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.loan.*;
import com.pinting.gateway.hessian.message.loan.model.Repayment;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 剑钊 on 2016/8/19.
 */
@Component("Repay")
public class RepayFacade {

    @Autowired
    private RepayPaymentService repayPaymentService;
    @Autowired
    private RepayQueryService repayQueryService;
    @Autowired
    private LnLoanMapper loanMapper;
    @Autowired
    private LnRepayScheduleMapper scheduleMapper;
    @Autowired
    private BsSysConfigService bsSysConfigService;

    /**
     * 预还款
     * @param req
     * @param res
     * @throws Exception
     */
    public void preRepay(G2BReqMsg_Repay_PreRepay req,G2BResMsg_Repay_PreRepay res) throws Exception {

        res.setBgwOrderNo(repayPaymentService.preRepay(req));
    }

    /**
     * 代扣还款
     * @param req
     * @param res
     * @throws Exception
     */
    public void withholdingRepay(G2BReqMsg_Repay_WithholdingRepay req,G2BResMsg_Repay_WithholdingRepay res)throws Exception{
    	int initNum = 0;//初始化状态为init，且计划还款时间在赞分期赎回时间之后的账单笔数
        if (Constants.REQ_IS_OFFLINE_Y.equals(req.getIsOffline())) {
        	//查询借款信息
            LnLoanExample loanExample = new LnLoanExample();
            loanExample.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
            List<LnLoan> loanList = loanMapper.selectByExample(loanExample);
            if (CollectionUtils.isEmpty(loanList) || loanList.size() == 0) {
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款信息");
            }
        	for (Repayment repayment : req.getRepayments()) {
        		Date finishDate = DateUtil.parseDate("2018-03-15");
                BsSysConfig config = bsSysConfigService.findKey( Constants.ZAN_CALLED_AWAY_DATE );
                if(config != null){
                	finishDate = DateUtil.parseDate(config.getConfValue());
                }
        		//查询对应的还款计划表      
                LnRepayScheduleExample scheduleExample = new LnRepayScheduleExample();
                scheduleExample.createCriteria().andLoanIdEqualTo(loanList.get(0).getId()).andPartnerRepayIdEqualTo(repayment.getRepayId());
                List<LnRepaySchedule> repayScheduleList = scheduleMapper.selectByExample(scheduleExample);
                LnRepaySchedule repaySchedule = repayScheduleList.get(0);
                //状态为init，且计划还款时间在赞分期赎回时间之后，则应调用正常还款
                if(LoanStatus.REPAY_SCHEDULE_STATUS_INIT.getCode().equals(repaySchedule.getStatus()) && repaySchedule.getPlanDate().compareTo(finishDate)>0){
                	initNum++;
                }
        	}
        	
		}
        if(Constants.REQ_IS_OFFLINE_Y.equals(req.getIsOffline()) && initNum != req.getRepayments().size()){
        	/*
        	 * 线下还款，存在部分【赞分期提前赎回】的处理数据，调用线下还款，线下还款中会进行对应的报错处理
        	 * 线下还款，都不是【赞分期提前赎回】的处理数据，调用线下还款
        	 */
        	res.setBgwOrderNo(repayPaymentService.withholdingRepayOffLine(req));
        }else if(Constants.REQ_IS_OFFLINE_Y.equals(req.getIsOffline()) && initNum == req.getRepayments().size()){
        	//线下还款，若还款数据都为【赞分期提前赎回】的处理数据，则调用新的线下还款业务，原来的调用逾期还款改成线下还款
        	res.setBgwOrderNo(repayPaymentService.withholdingRepayOffLineNew(req));
        }else if(!Constants.REQ_IS_OFFLINE_Y.equals(req.getIsOffline())){
        	//非线下还款业务，走原来的代扣
			res.setBgwOrderNo(repayPaymentService.withholdingRepay(req));
		}
    	
    }

    /**
     * 确认还款
     * @param req
     * @param res
     */
    public void repayConfirm(G2BReqMsg_Repay_RepayConfirm req, G2BResMsg_Repay_RepayConfirm res) throws Exception {

        repayPaymentService.repayConfirm(req);
    }
    
    /**
     * 还款结果查询
     * @param req
     * @param res
     * @throws Exception
     */
    public void queryRepayResult(G2BReqMsg_Repay_QueryRepayResult req, G2BResMsg_Repay_QueryRepayResult res) throws Exception{
    	if(StringUtil.isBlank(req.getOrderNo())){
    		throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_NOT_ENOUGH);
    	}

        repayQueryService.queryRepayResult(req, res);
    }
    
    
    /**
     * 坏账处理
     * @param req
     * @param res
     * @throws Exception
     */
    public void badDebt(G2BReqMsg_Repay_BadDebt req,G2BResMsg_Repay_BadDebt res) throws Exception {
    	repayPaymentService.badDebt(req);
    }
}
