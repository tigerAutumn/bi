package com.pinting.business.dayend.task;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.coreflow.compensate.util.ConstantsForFields;
import com.pinting.business.coreflow.core.enums.TransCodeEnum;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DispatcherService;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dao.LnCompensateAgreementAddressMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.LnCompensateAgreementAddress;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author:      zousheng
 * Date:        2018/07/30
 * Description: 云贷代偿签章协议通知，一笔借款生成一份代偿协议（确认函 + 通知书）
 */
@Service
public class LateAgreementNotifyTask implements ConstantsForFields {

    private Logger logger = LoggerFactory.getLogger(LateAgreementNotifyTask.class);
    @Autowired
    private DispatcherService dispatcherService;
    @Autowired
    private LnCompensateAgreementAddressMapper lnCompensateAgreementAddressMapper;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private BsSysConfigMapper bsSysConfigMapper;

    public void notifyAgreementDownload() {
        try {
            logger.info("=========定时任务{云贷代偿签章协议通知}开始=========");

            Integer maxId = 0; // 记录已查询数据的最大id
            Integer selectNum = 100; // 一次查询，处理100条数据
            Integer selectTotal;
            int loopCounter = 1;

            Date endTime = DateUtils.addHours(new Date(), -1); // 通知1小时前的数据（签章同步半小时一次）
            Date startTime = DateUtil.nextDay(endTime, -2); // 2天内数据
            boolean isBatchSucc = true;

            JSONObject jsonObject = getTimeConfig();
            if (jsonObject != null) {
                logger.info("代偿签章协议通知时间区间配置{}", jsonObject.toJSONString());
                boolean isOpen = Boolean.valueOf(jsonObject.getString("isOpen"));
                if (isOpen) {
                    startTime = DateUtil.getDateBegin(DateUtil.strToDate(jsonObject.getString("startTime")));
                    endTime = DateUtil.getDateEnd2S(DateUtil.strToDate(jsonObject.getString("endTime")));
                    selectNum = jsonObject.getInteger("selectNum");
                }
            }
            do {
                // 查询云贷待通知下载签章协议的合作方借款ID
                List<LnCompensateAgreementAddress> agreementAddressList = lnCompensateAgreementAddressMapper.selectWaitNotifyDownloadAgreement(maxId, selectNum, startTime, endTime);
                logger.info("第{}批次待通知的下载协议数量为{}", loopCounter, agreementAddressList.size());
                if (CollectionUtils.isEmpty(agreementAddressList)) {
                    return;
                }

                for (LnCompensateAgreementAddress agreementAddress : agreementAddressList) {
                    try {
                        // 单笔借款代偿协议签章通知服务
                        FlowContext flowContext = new FlowContext();
                        flowContext.setPartnerEnum(PartnerEnum.getEnumByCode(agreementAddress.getPartnerCode()));
                        flowContext.setTransCode(TransCodeEnum.COMPENSATE_NOTIFY_AGREEMENT_DOWNLOAD.getCode());
                        flowContext.setExtendData(PARTNER_LOAN_ID, agreementAddress.getPartnerLoanId());
                        dispatcherService.dispatcherService(flowContext);
                    } catch (Exception e) {
                        logger.info("代偿签章协议通知失败，合作方借款ID：{}", agreementAddress.getPartnerLoanId());
                        logger.error("代偿签章协议通知失败", e);
                        isBatchSucc = false;
                    }
                }

                selectTotal = agreementAddressList.size(); // 记录当前查询批次数量
                maxId = agreementAddressList.get(selectTotal - 1).getId(); // 设置最后一个id为下次查询的最大ID值
                loopCounter++;
            } while (selectNum == selectTotal);

            // 一批次代偿协议生成执行中，存在生成失败数据则告警
            if (!isBatchSucc) {
                String detail = "存在代偿签章协议通知失败的数据";
                specialJnlService.warn4FailNoSMS(0d, detail, "", "【代偿协议通知定时】");
            }
        } catch (Exception e) {
            logger.error("==================定时任务{云贷代偿签章协议通知}线程执行异常==================", e);
        } finally {
            logger.info("=========定时任务{云贷代偿签章协议通知}结束=========");
        }
    }

    private JSONObject getTimeConfig() {
        try {
            BsSysConfig sysConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LATE_AGREEMENT_NOTIFY_TIME_INTERVAL);
            if (sysConfig != null && StringUtils.isNotBlank(sysConfig.getConfValue())) {
                JSONObject jsonObject = JSONObject.parseObject(sysConfig.getConfValue());
                return jsonObject;
            }
        } catch (Exception e) {
            logger.info("读取配置LATE_AGREEMENT_NOTIFY_TIME_INTERVAL失败", e);
        }
        return null;
    }
}
