package com.pinting.business.dayend.task;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.coreflow.compensate.util.ConstantsForFields;
import com.pinting.business.coreflow.core.enums.TransCodeEnum;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DispatcherService;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dao.LnCompensateAgreementAddressMapper;
import com.pinting.business.dao.LnCompensateDetailMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.model.*;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author:      zousheng
 * Date:        2018/07/30
 * Description: 云贷代偿签章协议生成，一笔借款生成一份代偿协议（确认函 + 通知书）
 */
@Service
public class LateAgreementGenerateTask implements ConstantsForFields {

    private Logger logger = LoggerFactory.getLogger(LateAgreementGenerateTask.class);
    @Autowired
    private LnCompensateDetailMapper lnCompensateDetailMapper;
    @Autowired
    private DispatcherService dispatcherService;
    @Autowired
    private LnCompensateAgreementAddressMapper lnCompensateAgreementAddressMapper;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private BsSysConfigMapper bsSysConfigMapper;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;

    public void agreementGenerate() {
        try {
            logger.info("=========定时任务{云贷代偿协议生成}开始=========");

            Integer maxId = 0; // 记录已查询数据的最大id
            Integer selectNum = 1000; // 一次查询，处理1000条数据
            Integer selectTotal;
            int loopCounter = 1;

            Date endTime = DateUtil.getDateEnd(DateUtil.nextDay(new Date(), -1)); // 1天前零点 23:59:59
            Date startTime = DateUtil.getDateBegin(DateUtil.nextDay(endTime, -1)); // 2天前零点 00:00:00
            boolean isBatchSucc = true;

            JSONObject jsonObject = getTimeConfig();
            if (jsonObject != null) {
                logger.info("代偿协议生成时间区间配置{}", jsonObject.toJSONString());
                boolean isOpen = Boolean.valueOf(jsonObject.getString("isOpen"));
                if (isOpen) {
                    startTime = DateUtil.getDateBegin(DateUtil.strToDate(jsonObject.getString("startTime")));
                    endTime = DateUtil.getDateEnd2S(DateUtil.strToDate(jsonObject.getString("endTime")));
                    selectNum = jsonObject.getInteger("selectNum");
                }
            }

            do {
                // 查询已结清且有本金代偿记录、未生成代偿协议的借款
                List<LnLoan> lnLoanList = lnCompensateDetailMapper.selectSettledLnLoan4RepayScheduleFinishTime(maxId, selectNum, startTime, endTime);
                logger.info("第{}批次待生成代偿协议数量为{}", loopCounter, lnLoanList.size());
                if (CollectionUtils.isEmpty(lnLoanList)) {
                    return;
                }

                for (LnLoan lnLoan : lnLoanList) {
                    try {
                        Double leftTotalPrincipal = lnLoanRelationMapper.sumLeftAmountByLoanId(lnLoan.getId());
                        if (leftTotalPrincipal == null || leftTotalPrincipal.compareTo(0d) != 0) {
                            logger.info("借款未结清，借款id：{}", lnLoan.getId());
                            continue;
                        }

                        // 查询有本金代偿成功的明细列表
                        LnCompensateDetailExample lnCompensateDetailExample = new LnCompensateDetailExample();
                        lnCompensateDetailExample.createCriteria().andPartnerLoanIdEqualTo(lnLoan.getPartnerLoanId())
                                .andStatusEqualTo(Constants.COMPENSATE_REPAYS_STATUS_SUCC)
                                .andPrincipalGreaterThan(0d);
                        lnCompensateDetailExample.setOrderByClause("repay_serial DESC");
                        List<LnCompensateDetail> lnCompensateDetailList = lnCompensateDetailMapper.selectByExample(lnCompensateDetailExample);
                        if (CollectionUtils.isEmpty(lnCompensateDetailList)) {
                            logger.info("没有本金代偿明细信息，借款id：{}", lnLoan.getId());
                            continue;
                        }

                        // 取最后一笔代偿的明细信息
                        LnCompensateDetail lnCompensateDetail = lnCompensateDetailList.get(0);

                        // 债权转让通知书
                        List<LnCompensateAgreementAddress> debtTransNoticesList = lnCompensateAgreementAddressMapper.selectCompensateAgreementList(lnCompensateDetail.getCompensateId(),
                                lnCompensateDetail.getPartnerLoanId(), SealBusiness.DEBT_TRANS_NOTICES.getCode());
                        // 收款确认函（债转）
                        List<LnCompensateAgreementAddress> debtTransConfirmList = lnCompensateAgreementAddressMapper.selectCompensateAgreementList(lnCompensateDetail.getCompensateId(),
                                lnCompensateDetail.getPartnerLoanId(), SealBusiness.DEBT_TRANS_CONFIRM.getCode());
                        if (CollectionUtils.isNotEmpty(debtTransNoticesList) && CollectionUtils.isNotEmpty(debtTransConfirmList)) {
                            logger.info("代偿通知书与确认函已入签章文件服务队列，借款id：{}", lnLoan.getId());
                            continue;
                        }

                        // 单笔借款代偿协议签章
                        FlowContext flowContext = new FlowContext();
                        flowContext.setPartnerEnum(PartnerEnum.YUN_DAI_SELF);
                        flowContext.setTransCode(TransCodeEnum.COMPENSATE_AGREEMENT.getCode());
                        flowContext.setBusinessType(lnLoan.getPartnerBusinessFlag());
                        flowContext.setExtendData(LnCompensateDetail.class.getSimpleName(), lnCompensateDetail);
                        flowContext.setExtendData(LN_LOAN_ID, lnLoan.getId());
                        dispatcherService.dispatcherService(flowContext);
                    } catch (Exception e) {
                        logger.error("代偿签章协议生成失败", e);
                        isBatchSucc = false;
                    }
                }

                selectTotal = lnLoanList.size(); // 记录当前查询批次数量
                maxId = lnLoanList.get(selectTotal - 1).getId(); // 设置最后一个id为下次查询的最大ID值
                loopCounter++;
            } while (selectNum == selectTotal);

            // 一批次代偿协议生成执行中，存在生成失败数据则告警
            if (!isBatchSucc) {
                String detail = "存在代偿签章协议生成处理失败的数据";
                specialJnlService.warn4FailNoSMS(0d, detail, "", "【代偿协议生成定时】");
            }
        } catch (Exception e) {
            logger.error("==================定时任务{云贷代偿协议生成}线程执行异常==================", e);
        } finally {
            logger.info("=========定时任务{云贷代偿协议生成}结束=========");
        }
    }

    private JSONObject getTimeConfig() {
        try {
            BsSysConfig sysConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LATE_AGREEMENT_GENERATE_TIME_INTERVAL);
            if (sysConfig != null && StringUtils.isNotBlank(sysConfig.getConfValue())) {
                JSONObject jsonObject = JSONObject.parseObject(sysConfig.getConfValue());
                return jsonObject;
            }
        } catch (Exception e) {
            logger.info("读取配置LATE_AGREEMENT_GENERATE_TIME_INTERVAL失败", e);
        }
        return null;
    }
}
