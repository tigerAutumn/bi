package com.pinting.business.service.manage.impl;

import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.dao.*;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.LoanDTO;
import com.pinting.business.model.vo.LoanDailyStatisticsVO;
import com.pinting.business.model.vo.LoanDailyVO;
import com.pinting.business.model.vo.LoanVO;
import com.pinting.business.model.vo.RepaySchedulePlanVO;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.service.manage.LoanService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 剑钊 on 2016/11/14.
 */
@Service
public class LoanServiceImpl implements LoanService {

    private Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnRepayMapper lnRepayMapper;
    @Autowired
    private LnRepayDetailMapper lnRepayDetailMapper;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private LoanUserService loanUserService;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private LnDepositionTargetJnlMapper depositionTargetJnlMapper;
    @Autowired
    private SysConfigService sysConfigService;
    
    @Override
    public List<RepaySchedulePlanVO> queryRepaySchedulePlanList(int loanId) {
        List<RepaySchedulePlanVO> list = lnRepayScheduleMapper.selectRepaySchedulePlanList(loanId);
        List<RepaySchedulePlanVO> dataList = new ArrayList<RepaySchedulePlanVO>();
        for(RepaySchedulePlanVO vo : list) {
            RepaySchedulePlanVO repaySchedulePlan = new RepaySchedulePlanVO();
            double overdueManagementFee = 0d;
            double shouldServiceCharges = 0d;
            double planTotal = 0d;
            // 1、逾期未还款
            if(Constants.REPAY_SCHEDULE_STATUS_LATE_NOT.equals(vo.getStatus())) {
                overdueManagementFee =  loanUserService.calLateFee(vo.getLoanId(), vo.getSerialId());
            // 2、逾期已还款
            }else if (Constants.REPAY_SCHEDULE_STATUS_LATE_REPAIED.equals(vo.getStatus())) {
                if(vo.getOverdueManagementFee() != null) {
                    overdueManagementFee = vo.getOverdueManagementFee();
                }else {
                    overdueManagementFee = 0d;
                }
            }
            // 3、应还利息=应还总额-应还本金
            shouldServiceCharges = MoneyUtil.subtract(vo.getPlanTotal(), vo.getShouldPrincipal()).doubleValue();
            // 4、应还总额 = 总额 + 逾期滞纳金
            planTotal = MoneyUtil.add(vo.getPlanTotal(), overdueManagementFee).doubleValue();

            repaySchedulePlan.setLoanId(vo.getLoanId());
            repaySchedulePlan.setSerialId(vo.getSerialId());
            repaySchedulePlan.setPlanDate(vo.getPlanDate());
            repaySchedulePlan.setShouldPrincipal(vo.getShouldPrincipal());
            repaySchedulePlan.setShouldServiceCharges(shouldServiceCharges);
            repaySchedulePlan.setOverdueManagementFee(overdueManagementFee);
            repaySchedulePlan.setPlanTotal(planTotal);
            repaySchedulePlan.setDoneTotal(vo.getDoneTotal());
            repaySchedulePlan.setDoneTime(vo.getDoneTime());
            repaySchedulePlan.setStatus(vo.getStatus());
            dataList.add(repaySchedulePlan);

        }
        return dataList;
    }

    @Override
    public List<LoanVO> queryLoanList(LoanDTO loanDTO) {

        if(StringUtils.isBlank(loanDTO.getPartner())){
            loanDTO.setPartner(null);
        }

        if(StringUtils.isBlank(loanDTO.getStatus())){
            loanDTO.setStatus(null);
        }

        if(StringUtils.isNotBlank(loanDTO.getLnUserid())){
            loanDTO.setLnUserId(Long.parseLong(loanDTO.getLnUserid()));
        }

        if(StringUtils.isNotBlank(loanDTO.getLoanid())){
            loanDTO.setLoanId(Long.parseLong(loanDTO.getLoanid()));
        }

        if(StringUtils.isNotBlank(loanDTO.getLoanStart())){
            loanDTO.setsLoan(Double.parseDouble(loanDTO.getLoanStart()));
        }

        if(StringUtils.isNotBlank(loanDTO.getLoanEnd())){
            loanDTO.seteLoan(Double.parseDouble(loanDTO.getLoanEnd()));
        }

        if(StringUtils.isNotBlank(loanDTO.getNoReturnLoanStart())){
            loanDTO.setsNoReturn(Double.parseDouble(loanDTO.getNoReturnLoanStart()));
        }

        List<LoanVO> voList=lnLoanMapper.selectLoanByDTO(loanDTO);

        double lateFee=0d;
        //计算逾期滞纳金
        if(CollectionUtils.isNotEmpty(voList) && voList.size()>0) {

            for (LoanVO vo : voList) {
                LnRepayScheduleExample example = new LnRepayScheduleExample();
                example.createCriteria().andLoanIdEqualTo(vo.getLoanId());
                List<LnRepaySchedule> list = lnRepayScheduleMapper.selectByExample(example);
                for (LnRepaySchedule repaySchedule : list) {
                    if (repaySchedule.getStatus().equals(Constants.REPAY_SCHEDULE_STATUS_LATE_REPAIED)) { //逾期已还款
                        LnRepayExample repayExample=new LnRepayExample();
                        repayExample.createCriteria().andRepayPlanIdEqualTo(repaySchedule.getId()).andStatusEqualTo(Constants.LN_REPAY_REPAIED);//已还款
                        List<LnRepay> repayList=lnRepayMapper.selectByExample(repayExample);
                        if(CollectionUtils.isNotEmpty(repayList) && repayList.size()>0){
                            LnRepayDetailExample detailExample=new LnRepayDetailExample();
                            detailExample.createCriteria().andRepayIdEqualTo(repayList.get(0).getId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());//滞纳金
                            List<LnRepayDetail> repayDetailList=lnRepayDetailMapper.selectByExample(detailExample);
                            lateFee = MoneyUtil.add(lateFee, repayDetailList.get(0).getDoneAmount()).doubleValue();
                        }
                    }else if(repaySchedule.getStatus().equals(Constants.REPAY_SCHEDULE_STATUS_LATE_NOT)){ //逾期未还款

                        lateFee = MoneyUtil.add(lateFee, loanUserService.calLateFee(repaySchedule.getLoanId(),repaySchedule.getSerialId())).doubleValue();
                    }
                }
                vo.setLateFee(lateFee);
                lateFee=0d;
            }

        }
        return voList;
    }

    @Override
    public int queryLoanCount(LoanDTO loanDTO) {
        return lnLoanMapper.selectLoanByDTOCount(loanDTO);
    }

    @Override
    public List<LoanDailyVO> queryLoanDailyInfo(LoanDailyVO record) {
        List<LoanDailyVO> list = lnLoanMapper.selectLoanDailyList(record);
        for (LoanDailyVO loanDailyVO : list) {
            if (LoanStatus.LOAN_STATUS_CHECKED.getCode().equals(loanDailyVO.getLoanStatus())) {

                // 标的未初始化，没有标的信息
                if (loanDailyVO.getDepositionId() == null) {
                    continue;
                }

                // 债权回退：标的发布成功状态停留时间（小时）
                int residenceTime = 6; // 默认处理滞留6小时数据
                BsSysConfig bsSysConfig = sysConfigService.findConfigByKey(Constants.LOAN_DEBT_FINANCING_BACK_RESIDENCE_TIME);

                if (bsSysConfig != null) {
                    try {
                        residenceTime = Integer.valueOf(bsSysConfig.getConfValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("配置中LOAN_DEBT_FINANCING_BACK键值内容错误："+ com.alibaba.fastjson.JSONObject.toJSONString(bsSysConfig));
                    }
                }

                // 标的审核通过，设置标的成功流水状态，与流水时间
                LnDepositionTargetJnlExample example = new LnDepositionTargetJnlExample();
                example.createCriteria().andProdIdEqualTo(loanDailyVO.getDepositionId()).andTransStatusEqualTo(Constants.DEP_TARGET_OPERATE_SUCC);
                List<LnDepositionTargetJnl> listSucc = depositionTargetJnlMapper.selectByExample(example);
                if (CollectionUtils.isNotEmpty(listSucc)) {
                    LnDepositionTargetJnl lnDepositionTargetJnlSucc = listSucc.get(listSucc.size() -1);

                    // 标的发布成功或标的投标成功，滞留时间超过配置时间，展示债权回退按钮
                    if (((Constants.DEP_TARGET_PUBLISH.equals(lnDepositionTargetJnlSucc.getProdStatus())
                            && DepTargetEnum.DEP_TARGET_OPERATE_PUBLISH.getCode().equals(lnDepositionTargetJnlSucc.getTransType()))
                            || (Constants.DEP_TARGET_BID.equals(lnDepositionTargetJnlSucc.getProdStatus())
                            && DepTargetEnum.DEP_TARGET_OPERATE_BID.getCode().equals(lnDepositionTargetJnlSucc.getTransType())))
                            && lnDepositionTargetJnlSucc.getCreateTime() != null && DateUtil.getDiffeHour(new Date() ,lnDepositionTargetJnlSucc.getCreateTime()) >= residenceTime) {
                        loanDailyVO.setSupportBackLoanDebtFinancing(true);
                    } else {
                        loanDailyVO.setSupportBackLoanDebtFinancing(false);
                    }
                }
            }
        }

        return list.size() > 0 ? list : null;
    }

    @Override
    public Integer queryLoanDailyCount(LoanDailyVO record) {
        return lnLoanMapper.selectLoanDailyCount(record);
    }

    @Override
    public double queryZanDailyTotalAmount(String startTime, String endTime) {
        return lnLoanMapper.selectZanDailyTotalAmount(startTime, endTime);
    }

    @Override
    public double queryYunDaiDailyTotalAmount(String startTime, String endTime) {
        return lnLoanMapper.selectYunDaiDailyTotalAmount(startTime, endTime);
    }

    @Override
    public double queryZsdDailyTotalAmount(String startTime, String endTime) {
        return lnLoanMapper.selectZsdDailyTotalAmount(startTime, endTime);
    }

    @Override
    public LoanDailyStatisticsVO queryLoanDailyPaiedStatistics(String partnerCode, String startTime, String endTime) {
        return lnLoanMapper.selectLoanDailyPaiedStatistics(partnerCode, startTime, endTime);
    }

    @Override
    public LoanDailyStatisticsVO queryLoanDailyPayingStatistics(String partnerCode, String startTime, String endTime) {
        return lnLoanMapper.selectLoanDailyPayingStatistics(partnerCode, startTime, endTime);
    }

	@Override
	public Double getRelationAmountByLoanIdAndStatus(String loanId, String status) {
		return lnLoanRelationMapper.getRelationAmountByLoanIdAndStatus(loanId, status);
	}

    @Override
    public double querySevenDailyTotalAmount(String startTime, String endTime) {
        return lnLoanMapper.selectSevenDailyTotalAmount(startTime, endTime);
    }
}
