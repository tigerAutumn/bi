package com.pinting.business.coreflow.repay.service.Impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.loan.model.NormalRepaySysSplitInfo;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.coreflow.core.enums.BusinessTypeEnum;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DepFixedService;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnRepayMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnPayOrders;
import com.pinting.business.model.LnRepay;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.model.LnRepeatRepayRecord;
import com.pinting.business.model.vo.RepayBillVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.JSONUtil;
import com.pinting.core.util.StringUtil;

/**
 * 代扣结果处理(账单+分账)抽象实现类 on 2018/6/25.
 * 等额等息代扣结果处理
 */
public class AbstractRepayShareProfitResultServiceImpl implements DepFixedService {
	private Logger logger = LoggerFactory.getLogger(AbstractRepayApplyServiceImpl.class);
	private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
    private TransactionTemplate transactionTemplate;

	@Autowired
 	private LnLoanMapper lnLoanMapper;
 	@Autowired
 	private LnRepayScheduleMapper lnRepayScheduleMapper;
 	@Autowired
 	private LnRepayMapper lnRepayMapper;
 	
	@Autowired
    private SpecialJnlService specialJnlService;
	@Autowired
	private DepFixedRepayPaymentService depFixedRepayPaymentService;
	
	@Override
    public ResMsg execute(FlowContext flowContext) {
		JSONObject repayBillJson  = (JSONObject) flowContext.getExtendMap().get("repayBillJson");
		LnPayOrders order = (LnPayOrders) flowContext.getExtendMap().get("lnPayOrder");
		JSONArray repayBills = null;
		
        String partnerUserId = repayBillJson.getString("partnerUserId");
        String partnerLoanId = repayBillJson.getString("partnerLoanId");
        Integer loanId = repayBillJson.getInteger("loanId");
        
        if(StringUtil.isNotEmpty(repayBillJson.getString("repayBills"))){
            repayBills = repayBillJson.getJSONArray("repayBills");
        }
        if(order.getId() == null || loanId == null || StringUtil.isEmpty(partnerUserId)
                || StringUtil.isEmpty(partnerLoanId)){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",redis中数据校验失败");
        }
        
        List<RepayBillVO> repayBillVOList = null;
        if(repayBills != null){
            repayBillVOList = (List<RepayBillVO>) JSONUtil.jsonString2BeanList(repayBills.toJSONString(), RepayBillVO.class);
        }
        
        /**
         * INIT--REPAIED:调用“B还款结果处理”还款成功处理；
         * LATE_NOT(代偿完成时会改成此状态)--REPAIED：判断是否已有相关代偿,是则拒绝还款,告警;
         * REPAIED--REPAIED:判断是否已有相关还款,是则做一笔重复还款处理;
         */
        if(CollectionUtils.isNotEmpty(repayBillVOList)){
            //REDIS账单不为空时对照REDIS中账单
            for(RepayBillVO repayBill : repayBillVOList){
                if(repayBill.getScheStatus().equals(Constants.LN_REPAY_STATUS_INIT)){
                	normalRepay (repayBill, order, flowContext);
                } else if( repayBill.getScheStatus().equals(Constants.LN_REPAY_LATE_NOT) ){
                    //判断是否已有相关代偿，是则做一笔重复还款处理
                    logger.info(">>>还款处理轮询LATE_NOT--REPAIED条件START,告警不做重复还款处理<<<");
                    specialJnlService.warn4Fail(0d,"还款处理轮询REPAIED--REPAIED条件调用还款结果处理错误partnerRepayId=" + repayBill.getPartnerRepayId(),
                            repayBill.getPartnerRepayId(), "对应redis数据错误", false);
                    logger.info(">>>还款处理轮询LATE_NOT--REPAIED条件END<<<");
                } else if( repayBill.getScheStatus().equals(Constants.LN_REPAY_REPAIED) ) {
                	repeatProcess(repayBill, order);
                } else {
                    logger.info(">>>还款处理轮询有PENDING状态未处理，请检查" + JSON.toJSONString(repayBill) + "<<<");
                    specialJnlService.warn4Fail(0d,"还款处理轮询有PENDING状态未处理，请检查partnerRepayId=" + repayBill.getPartnerRepayId(),
                            repayBill.getPartnerRepayId(), "云贷还款账单有未处理，请检查",false);
                    continue;
                }
            }
                
        }
        
        //处理结束时，借款记录设为可还标识
        if(loanId != null){
            LnLoan lnLoanUpdate = new LnLoan();
            lnLoanUpdate.setId(loanId);
            lnLoanUpdate.setIsRepaying(Constants.DEP_REPAY_AVAILABLE);
            lnLoanUpdate.setUpdateTime(new Date());
            lnLoanMapper.updateByPrimaryKeySelective(lnLoanUpdate);
        }
    
        return flowContext.getRes();
    }
	

	/**
	 * 正常还款/提前还款账单更新,分账入口
	 * */
	private void normalRepay (RepayBillVO repayBill, LnPayOrders order, FlowContext flowContext) {
		logger.info(">>>还款处理轮询INIT--REPAIED条件START<<<");
        
        int repayPlanId = repayBill.getRepayPlanId();
        LnRepay lnRepay = lnRepayMapper.selectByPrimaryKey(repayPlanId);
        
        NormalRepaySysSplitInfo result = new NormalRepaySysSplitInfo();
        result.setLnPayOrdersId(order.getId());
        result.setOrderNo(order.getOrderNo());
        result.setLoanId(repayBill.getLoanId());
        result.setRepayAmount(order.getAmount());
        result.setPartnerRepayId(repayBill.getPartnerRepayId());
        result.setPartnerEnum(flowContext.getPartnerEnum());
        result.setBusinessTypeEnum(BusinessTypeEnum.getEnumByCode(flowContext.getBusinessType()));
        try {
            logger.info(">>>还款处理轮询INIT--REPAIED条件调用还款结果处理开始<<<");
            repaySysSplit(result);
            
            logger.info(">>>还款处理轮询INIT--REPAIED条件调用还款结果处理结束<<<");
        } catch(Exception e) {
            logger.info(">>>还款处理轮询redis中数据处理错误<<<",e);
            specialJnlService.warn4Fail(0d,"还款处理轮询时处理partnerRepayId=:" + repayBill.getPartnerRepayId() + "对应redis数据错误",
                    repayBill.getPartnerRepayId(), "对应redis数据错误",false);
        } 
        //置return_flag为finish
        logger.info(">>>还款处理轮询INIT--REPAIED条件置return_flag为finish<<<");
        LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
        lnRepaySchedule.setId(repayBill.getRepayPlanId());
        lnRepaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
        lnRepaySchedule.setPayOrderNo(lnRepay.getBgwOrderNo());
        lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepaySchedule);
        logger.info(">>>还款处理轮询INIT--REPAIED条件END<<<");
	}
	
	/**
	 * 还款分账记账
	 * @author bianyatian
	 * @param result
	 */
	protected void repaySysSplit(NormalRepaySysSplitInfo result) {
		
	}


	/**
	 * 重复还款入口
	 * */
	private void repeatProcess(RepayBillVO repayBill, LnPayOrders order) {
		//同一笔账单是否存在除当前订单号的还款信息,是则做一笔重复还款
        logger.info(">>>还款处理轮询REPAIED--REPAIED条件START,调用重复还款处理<<<");
        Integer isRepeat = lnRepayMapper.isRepeatRepay(repayBill.getRepayPlanId(),order.getId());
        int repayPlanId = repayBill.getRepayPlanId();
        LnRepay lnRepay = lnRepayMapper.selectByPrimaryKey(repayPlanId);
        if(isRepeat > 0){
            LnRepeatRepayRecord repeatRepay = new LnRepeatRepayRecord();
            repeatRepay.setPartnerCode(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF);
            repeatRepay.setRepayAmount(order.getAmount());
            repeatRepay.setRepayOrderNo(order.getOrderNo());
            repeatRepay.setRepayPlanId(repayBill.getRepayPlanId());
            repeatRepay.setRepayType(Constants.REPAY_TYPE_USER_REPAY);
            repeatRepay.setReturnAmount(order.getAmount());
            repeatRepay.setSettleDate(new Date()); //结算日期
            repeatRepay.setStatus(Constants.REPEAT_REPAY_STATUS_INIT);
            repeatRepay.setCreateTime(new Date());
            repeatRepay.setUpdateTime(new Date());
            try {
            	depFixedRepayPaymentService.repeatRepayProcess(repeatRepay);
            } catch(Exception e) {
                logger.info(">>>还款处理轮询REPAIED--REPAIED条件调用还款结果处理错误<<<",e);
                specialJnlService.warn4Fail(0d,"还款处理轮询REPAIED--REPAIED条件调用还款结果处理错误partnerRepayId=" + repayBill.getPartnerRepayId(),
                        repayBill.getPartnerRepayId(), "对应redis数据错误",false);
            }
        }
        //更新return_flag为finish
        logger.info(">>>还款处理轮询REPAIED--REPAIED条件置return_flag为finish<<<");
        LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
        lnRepaySchedule.setId(repayBill.getRepayPlanId());
        lnRepaySchedule.setReturnFlag(Constants.LN_REPAY_RETURN_FLAG_FINISH);
        lnRepaySchedule.setPayOrderNo(lnRepay.getBgwOrderNo());
        lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepaySchedule);
        logger.info(">>>还款处理轮询REPAIED--REPAIED条件END<<<");
	}
	
}
