package com.pinting.business.coreflow.repay.service.Impl;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DepFixedService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.RepayQueueDTO;
import com.pinting.business.model.vo.Pay19BankVO;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_CutRepayConfirm;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * 还款申请抽象实现类
 * Created by Gemma on 2018/6/25.
 */
public abstract class AbstractRepayApplyServiceImpl implements DepFixedService {

    private Logger logger = LoggerFactory.getLogger(AbstractRepayApplyServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private Bs19payBankMapper bs19payBankMapper;
    @Autowired
    private LnRepayMapper lnRepayMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private LnRepayDetailMapper lnRepayDetailMapper;
    @Autowired
    private LnRepeatRepayRecordMapper lnRepeatRepayRecordMapper;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private LnPayOrdersJnlMapper lnPayOrdersJnlMapper;
    
    @Override
    public ResMsg execute(FlowContext flowContext) {
    	G2BReqMsg_DafyLoan_CutRepayConfirm req = (G2BReqMsg_DafyLoan_CutRepayConfirm) flowContext.getReq();
    	try { 
    		//1.业务数据校验
    		verifyBusinessBefore(req, flowContext);
    		if (CollectionUtils.isNotEmpty(req.getLoans())) {
                 //2.记录还款信息
                 insertRepayApplyRecord(req, flowContext);
                 //3.记录订单信息(入Redis)
                 insertPayOrdersRecords(req, flowContext);
             }
             return flowContext.getRes();
         } finally {
        	 
         }
    }
    
    /**
     * 业务数据前置校验
     * */
    protected void verifyBusinessBefore(G2BReqMsg_DafyLoan_CutRepayConfirm req, FlowContext flowContext) {
    	//订单是否重复 
        LnRepayExample repayExample = new LnRepayExample();
        repayExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
        List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
        if (CollectionUtils.isNotEmpty(repayList) && repayList.size() > 0) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号重复");
        }
        //借款用户是否存在
        LnUserExample lnUserExample = new LnUserExample();
        lnUserExample.createCriteria().andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode())
                .andPartnerUserIdEqualTo(req.getUserId());
        List<LnUser> lnUsers = lnUserMapper.selectByExample(lnUserExample);
        if (CollectionUtils.isEmpty(lnUsers)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款用户信息");
        }
        //该银行是否支持代扣
        List<Pay19BankVO> pay19BankVOs = bs19payBankMapper.selectByChanelPayTypeBankCode(Constants.CHANNEL_BAOFOO, Constants.PAY_TYPE_HOLD, req.getBankCode());
        if (CollectionUtils.isEmpty(pay19BankVOs)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "银行编码[" + req.getBankCode() + "]不存在或该银行不支持代扣");
        }

        flowContext.getExtendMap().put("lnUser", 		lnUsers.get(0));
        flowContext.getExtendMap().put("pay19BankVOs", 	pay19BankVOs.get(0));
        flowContext.getExtendMap().put("orderNo", 		Util.generateOrderNo4BaoFoo(8));
        flowContext.getExtendMap().put("bgwOrderNo", 	Util.generateSysOrderNo(Constants.BGW_ORDER_NUMBER_GENERATE_WITHHOLD));
    }
    
    /**
     * 记录还款申请信息
     * */
    @Transactional
	protected void insertRepayApplyRecord(final G2BReqMsg_DafyLoan_CutRepayConfirm req,
    		FlowContext flowContext) {

    	LnUser lnUser =  (LnUser) flowContext.getExtendMap().get("lnUser");
    	String orderNo = (String) flowContext.getExtendMap().get("orderNo");
    	String bgwOrderNo = (String) flowContext.getExtendMap().get("bgwOrderNo");
    	 
        ArrayList<HashMap<String, Object>> loans = req.getLoans();
        for (HashMap<String, Object> loan : loans) {
        	String loanId = (String) loan.get("loanId");
            ArrayList<HashMap<String, Object>> repayments = (ArrayList<HashMap<String, Object>>) loan.get("repayments");
            //每笔借款是否存在
            LnLoanExample loanExample = new LnLoanExample();
            loanExample.createCriteria().andPartnerLoanIdEqualTo(loanId)
                    .andLnUserIdEqualTo(lnUser.getId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
            List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);
            if (CollectionUtils.isEmpty(loanList)) {
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "找不到借款[" + loanId + "]信息");
             }
             final LnLoan lnLoan = loanList.get(0);
             //该笔借款是否有还款正在进行中
             if (lnLoan.getIsRepaying().equals(Constants.DEP_REPAY_REPAYING)) {
                 throw new PTMessageException(PTMessageEnum.DEP_REPAY_REPAYING);
             }

             if (CollectionUtils.isEmpty(repayments)) {
                 throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "代扣还款云贷传入账单为空");
             }
             for (HashMap<String, Object> repay : repayments) {
                 //是否已还款/是否需要账单同步
                 String repayId = (String) repay.get("repayId");
                 Double total = (Double) repay.get("total");
                 Double principal = (Double) repay.get("principal");
                 Double interest = (Double) repay.get("interest");
                 Double principalOverdue = (Double) repay.get("principalOverdue");
                 Double interestOverdue = (Double) repay.get("interestOverdue");
                 //预留字段
                 //String reservedField1 = (String) repay.get("reservedField1");

                 LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                 lnRepayScheduleExample.createCriteria().andPartnerRepayIdEqualTo(repayId).andLoanIdEqualTo(lnLoan.getId());
                 List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);

                 //记录还款信息表和还款信息明细表
                 LnRepay lnRepay = new LnRepay();
                 if (CollectionUtils.isNotEmpty(lnRepaySchedules)) {
                     LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);
                     if (lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PENDING) ||
                             lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PROCESS)) {
                         throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + lnRepaySchedule.getPartnerRepayId() + "]正在处理中");
                     }
                     lnRepay.setRepayPlanId(lnRepaySchedule.getId());
                     String scheduleStatus = lnRepaySchedule.getStatus();
                     if (LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) ||
                             LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(scheduleStatus) ||
                             LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus)) {
                         throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repayId + "]已存在还款或已废除");
                     }

                     //是否已存在重复还款记录
                     repeatRepayJudge(lnRepaySchedule.getId(), repayId);
                 }
                 lnRepay.setStatus(LoanStatus.REPAY_STATUS_PAYING.getCode());
                 lnRepay.setUpdateTime(new Date());
                 lnRepay.setBgwOrderNo(bgwOrderNo);
                 lnRepay.setCreateTime(new Date());
                 lnRepay.setDoneTotal(total);
                 lnRepay.setLnUserId(lnUser.getId());
                 lnRepay.setPayOrderNo(orderNo);
                 lnRepay.setLoanId(lnLoan.getId());
                 lnRepay.setPartnerOrderNo(req.getOrderNo());
                 lnRepay.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                 lnRepayMapper.insertSelective(lnRepay);

                 LnRepayDetail lnRepayDetail = new LnRepayDetail();
                 lnRepayDetail.setUpdateTime(new Date());
                 lnRepayDetail.setCreateTime(new Date());
                 lnRepayDetail.setRepayId(lnRepay.getId());
                 lnRepayDetail.setDoneAmount(principal != null ? principal : 0d);
                 lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                 lnRepayDetailMapper.insertSelective(lnRepayDetail);

                 lnRepayDetail.setDoneAmount(interest != null ? interest : 0d);
                 lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                 lnRepayDetailMapper.insertSelective(lnRepayDetail);

                 lnRepayDetail.setDoneAmount(principalOverdue != null ? principalOverdue : 0d);
                 lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
                 lnRepayDetailMapper.insertSelective(lnRepayDetail);

                 lnRepayDetail.setDoneAmount(interestOverdue != null ? interestOverdue : 0d);
                 lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
                 lnRepayDetailMapper.insertSelective(lnRepayDetail);
             }

         }
    }
    /**
     * 主动还款，代扣是否已存在重复还款
     * @param billId
     * @param parnterBillId
     */
    public void repeatRepayJudge(Integer billId,String parnterBillId){
        LnRepeatRepayRecordExample example = new LnRepeatRepayRecordExample();
        example.createCriteria().andRepayPlanIdEqualTo(billId);
        List<LnRepeatRepayRecord> repeatRepayRecords = lnRepeatRepayRecordMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(repeatRepayRecords)){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + parnterBillId + "]已存在重复还款");
        }
    }
    /**
     * 记录还款订单信息
     * */
    public void insertPayOrdersRecords(G2BReqMsg_DafyLoan_CutRepayConfirm req,
    		FlowContext flowContext) {
    	
    	LnUser lnUser =  (LnUser) flowContext.getExtendMap().get("lnUser");
    	Pay19BankVO pay19BankVO =   (Pay19BankVO) flowContext.getExtendMap().get("pay19BankVOs");
    	String orderNo = (String) flowContext.getExtendMap().get("orderNo");
    	
    	 //记录ln_pay_orders,ln_pay_orders_jnl表
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setAmount(req.getTotalAmount());
        lnPayOrders.setBankCardNo(req.getBankCard());
        lnPayOrders.setBankId(pay19BankVO.getBankId());
        lnPayOrders.setBankName(pay19BankVO.getBankName());
        lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
        lnPayOrders.setIdCard(req.getIdCard());
        lnPayOrders.setLnUserId(lnUser.getId());
        lnPayOrders.setMobile(req.getMobile());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(orderNo);
        lnPayOrders.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setSubAccountId(null);
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(req.getName());
        lnPayOrdersMapper.insertSelective(lnPayOrders);

        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setSubAccountId(null);
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setUserId(lnUser.getId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
        //入还款队列
        LnBindCard lnBindCard = new LnBindCard();
        lnBindCard.setLnUserId(lnUser.getId());
        lnBindCard.setBankCard(req.getBankCard());
        lnBindCard.setIdCard(req.getIdCard());
        lnBindCard.setUserName(req.getName());
        lnBindCard.setBankCode(req.getBankCode());
        lnBindCard.setMobile(req.getMobile());
        //入redis
        try {
            RepayQueueDTO repayQueueDTO = new RepayQueueDTO();
            repayQueueDTO.setLnBindCard(lnBindCard);
            repayQueueDTO.setLnPayOrder(lnPayOrders);
            repayQueueDTO.setChannel(flowContext.getPartnerEnum().getCode());
            if (!StringUtil.isEmpty(req.getUserSignNo())) {
            	repayQueueDTO.setUserSignNo(req.getUserSignNo());
			}
            if (!StringUtil.isEmpty(req.getPayIP())) {
            	repayQueueDTO.setPayIP(req.getPayIP());
			}
            jsClientDaoSupport.rpush("repay_queue", JSON.toJSONString(repayQueueDTO));
            logger.info(">>>入还款队列数据:" + JSON.toJSONString(repayQueueDTO) + "<<<");
        } catch (Exception e){
            logger.error("入还款队列异常", e);
        }
    }
}