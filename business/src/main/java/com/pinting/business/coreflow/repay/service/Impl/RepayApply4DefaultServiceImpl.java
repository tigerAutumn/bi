package com.pinting.business.coreflow.repay.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.dao.Bs19payBankMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.dao.LnRepayDetailMapper;
import com.pinting.business.dao.LnRepayMapper;
import com.pinting.business.dao.LnRepayScheduleDetailMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanExample;
import com.pinting.business.model.LnRepay;
import com.pinting.business.model.LnRepayDetail;
import com.pinting.business.model.LnRepayExample;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.model.LnRepayScheduleDetail;
import com.pinting.business.model.LnRepayScheduleDetailExample;
import com.pinting.business.model.LnRepayScheduleExample;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.LnUserExample;
import com.pinting.business.model.vo.Pay19BankVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_CutRepayConfirm;

/**
 * Created by Gemma on 2018/6/25.
 */
@Service("repayApply4DefaultServiceImpl")
public class RepayApply4DefaultServiceImpl extends AbstractRepayApplyServiceImpl {
	
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private LnRepayScheduleMapper lnRepayScheduleMapper;
	@Autowired
	private LnRepayMapper lnRepayMapper;
	@Autowired
	private LnRepayDetailMapper lnRepayDetailMapper;
	@Autowired
	private LnUserMapper lnUserMapper;
	@Autowired
	private Bs19payBankMapper bs19payBankMapper;
	@Autowired
	private LnRepayScheduleDetailMapper lnRepayScheduleDetailMapper;
	@Autowired
    private SpecialJnlService specialJnlService;
	
	
	/**
     * 业务数据前置校验
     * */
	@Override
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
        
    	
        ArrayList<HashMap<String, Object>> loans = req.getLoans();
        for (HashMap<String, Object> loan : loans) {
        	String loanId = (String) loan.get("loanId");
            ArrayList<HashMap<String, Object>> repayments = (ArrayList<HashMap<String, Object>>) loan.get("repayments");
            
            //每笔借款是否存在
            LnLoanExample loanExample = new LnLoanExample();
            loanExample.createCriteria().andPartnerLoanIdEqualTo(loanId)
                    .andLnUserIdEqualTo(lnUsers.get(0).getId()).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
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
             
//          	boolean billsPushFlag = false;//账单是否推送标志
            int minSerialId = 360;		 //还款期次
           	for (HashMap<String, Object> repay : repayments) {
                 //是否已还款/是否需要账单同步 
                 String repayId = (String) repay.get("repayId");
                 Double principal = (Double) repay.get("principal");
                 int serialId = Integer.parseInt( String.valueOf( (Long) repay.get("repaySerial") ) );
                 minSerialId = minSerialId >= serialId ? serialId : minSerialId;
                 
                 LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                 lnRepayScheduleExample.createCriteria().andLoanIdEqualTo(lnLoan.getId()).andPartnerRepayIdEqualTo(repayId);
                 List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);

                 //记录还款信息表和还款信息明细表
                 LnRepay lnRepay = new LnRepay();
                 if (CollectionUtils.isNotEmpty(lnRepaySchedules)) {
//                	 billsPushFlag = true;
                     LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);
                     String scheduleStatus = lnRepaySchedule.getStatus();
                     
                     if( !lnRepaySchedule.getSerialId().equals(serialId) ) {
                    	 throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + lnRepaySchedule.getPartnerRepayId() + "]与期数不一致");
                     }
                     
                     if( lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PENDING) ||
                             lnRepaySchedule.getReturnFlag().equals(Constants.LN_REPAY_RETURN_FLAG_PROCESS)) {
                         throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + lnRepaySchedule.getPartnerRepayId() + "]正在处理中");
                     }
                     
                     lnRepay.setRepayPlanId(lnRepaySchedule.getId());
                     
                     if( LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) ||
                             LoanStatus.REPAY_SCHEDULE_STATUS_CANCELLED.getCode().equals(scheduleStatus) ||
                             LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus) || 
                             LoanStatus.REPAY_SCHEDULE_STATUS_LATE_NOT.getCode().equals(scheduleStatus)) {
                         throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单[" + repayId + "]已存在还款或已废除或已代偿");
                     }
   
                     LnRepayScheduleDetailExample detailExample = new LnRepayScheduleDetailExample();
                     detailExample.createCriteria().andPlanIdEqualTo(lnRepaySchedule.getId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                     List<LnRepayScheduleDetail> repaySchDetailList = lnRepayScheduleDetailMapper.selectByExample(detailExample);
                     
                     //校验账单本金是否一致,不一致拒绝还款,告警
                     if( !repaySchDetailList.get(0).getPlanAmount().equals(principal) ) {
                    	 specialJnlService.warnAppoint4Fail(repaySchDetailList.get(0).getPlanAmount(), "账单["+ repayId +"]本金"+repaySchDetailList.get(0).getPlanAmount()+"本金"+principal+"不一致",
                                 "","还款申请失败", false, Constants.EMERGENCY_MOBILE);
                    	 throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "还款本金与账单[" + repayId + "]本金不一致");
                     }
                     //是否已存在重复还款记录
                     repeatRepayJudge( lnRepaySchedule.getId(), repayId);
                 } else {
                	 specialJnlService.warnAppoint4Fail( 0d , "借款编号 "+loanId+" 在还款时账单编号"+repayId+"不存在",
                				"","还款申请失败", false, Constants.EMERGENCY_MOBILE);
                	 throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "借款意向编号"+loanId+"账单编号"+repayId+"不存在,请联系币港湾");
                 }
           	}
           	//告警:不登记lnRepay,lnRepayDetail信息;管理台拉取账单;
//           	if( !billsPushFlag ) {
//           		specialJnlService.warnAppoint4Fail( 0d , "借款编号 "+loanId+" 在还款时账单列表不存在,请于管理台同步账单",
//           				"","还款申请失败", false, Constants.EMERGENCY_MOBILE);
//           		throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "账单列表不存在,请先推送借款人账单");
//           	}
           	//跨期还款校验
           	if( minSerialId > 1 ) {
           		LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                lnRepayScheduleExample.createCriteria().andLoanIdEqualTo(lnLoan.getId()).andSerialIdEqualTo( minSerialId - 1 );
                List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);
                String repayStatus = lnRepaySchedules.get(0).getStatus();
                if( LoanStatus.REPAY_SCHEDULE_STATUS_INIT.getCode().equals(repayStatus)
                		) {
                	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "借款编号"+lnLoan.getPartnerLoanId()+"账单"
                				+(minSerialId-1)+"期未还 ,不允许跨期还款");
                }
           	}
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
        	final LnLoan lnLoan = loanList.get(0);
             for (HashMap<String, Object> repay : repayments) {
                 //是否已还款/是否需要账单同步
                 String repayId = (String) repay.get("repayId");
                 Double total = (Double) repay.get("total");
                 Double principal = (Double) repay.get("principal");
                 Double interest = (Double) repay.get("interest");
                 Double principalOverdue = (Double) repay.get("principalOverdue");
                 Double interestOverdue = (Double) repay.get("interestOverdue");
                 String reservedField1 = (String) repay.get("reservedField1");//提前还款的违约金
                 
                 LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
                 lnRepayScheduleExample.createCriteria().andLoanIdEqualTo(lnLoan.getId()).andPartnerRepayIdEqualTo(repayId);
                 List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);

                 //记录还款信息表和还款信息明细表
                 LnRepay lnRepay = new LnRepay();
                 if (CollectionUtils.isNotEmpty(lnRepaySchedules)) {
                     LnRepaySchedule lnRepaySchedule = lnRepaySchedules.get(0);
                     lnRepay.setRepayPlanId(lnRepaySchedule.getId());
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
                 
                 if( !StringUtil.isEmpty(reservedField1)  ) {
                	 lnRepayDetail.setDoneAmount(MoneyUtil.divide(Double.parseDouble(reservedField1), 100, 2).doubleValue());
                     lnRepayDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_OTHER.getCode());
                     lnRepayDetailMapper.insertSelective(lnRepayDetail);
                 }
             }

         }
    }
	
}
