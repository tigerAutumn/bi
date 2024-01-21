package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.LnCompensateAgreementAddressMapper;
import com.pinting.business.dao.LnCompensateDetailMapper;
import com.pinting.business.dao.LnCompensateMapper;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.model.*;
import com.pinting.business.service.loan.LateRepayAgreementService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:      shh
 * Date:        2018/3/15
 * Description: 代偿协议生成超时，缺失的代偿协议重新生成定时
 */
@Service
public class LateAgreementRepeatGenerateTask {

    private Logger logger = LoggerFactory.getLogger(LateAgreementRepeatGenerateTask.class);

    @Autowired
    private LnCompensateMapper lnCompensateMapper;
    @Autowired
    private LnCompensateDetailMapper lnCompensateDetailMapper;
    @Autowired
    private LnCompensateAgreementAddressMapper lnCompensateAgreementAddressMapper;
    @Autowired
    private BsSysConfigService bsSysConfigService;
    @Autowired
    private LateRepayAgreementService lateRepayAgreementService;

    private static Integer signId = 0;

    public void execute() {
        logger.info("=========定时任务{代偿协议重新生成}开始=========");
        // signId为0说明任务刚启动或者代偿协议已经重新生成完一轮
        if (signId == 0) {
            Integer maxId = lnCompensateDetailMapper.selectLnCompensateDetailMaxId();
            signId = maxId == null ? 0 : maxId + 1;
        }
        BsSysConfig sys = bsSysConfigService.findKey(Constants.LATE_AGREEMENT_GRNERATE_NUM);
        Integer selectNum = sys != null ? Integer.valueOf(sys.getConfValue()) : 5;
        // 按id从大到小排列查询n条
        List<LnCompensateDetail> compensateDetailList = lnCompensateDetailMapper.selectLateAgreementRepeatGenerateList(signId, selectNum);

        try {
            //校验代偿明细中的代偿单号是否生成了代偿协议
            if(compensateDetailList != null && compensateDetailList.size() > 0) {
                for(LnCompensateDetail lnCompensateDetail : compensateDetailList) {
                    Integer id = lnCompensateDetail.getId();
                    signId = id;

                    // 收款确认函（服务费）
                    List<LnCompensateAgreementAddress> serviceFeeConfirmList =
                            lnCompensateAgreementAddressMapper.selectCompensateServiceFeeTransList(lnCompensateDetail.getCompensateId(),
                                    SealBusiness.SERVICE_FEE_CONFIRM.getCode());
                    // 收款确认函（债转）
                    List<LnCompensateAgreementAddress> debtTransConfirmList =
                            lnCompensateAgreementAddressMapper.selectCompensateServiceFeeTransList(lnCompensateDetail.getCompensateId(),
                                    SealBusiness.DEBT_TRANS_CONFIRM.getCode());
                    // 债权转让通知书
                    List<LnCompensateAgreementAddress> debtTransNoticesList =
                            lnCompensateAgreementAddressMapper.selectCompensateAgreementList(lnCompensateDetail.getCompensateId(),
                            lnCompensateDetail.getPartnerLoanId(), SealBusiness.DEBT_TRANS_NOTICES.getCode());
                    // 债权转让协议
                    List<LnCompensateAgreementAddress> debtTransferList =
                            lnCompensateAgreementAddressMapper.selectCompensateDebtTransferAgreementList(lnCompensateDetail.getCompensateId(),
                            lnCompensateDetail.getPartnerLoanId(), SealBusiness.DEBT_TRANSFER.getCode());

                    // 根据compensate_id查询代偿单号、资产合作方
                    LnCompensateExample compensateExample = new LnCompensateExample();
                    compensateExample.createCriteria().andIdEqualTo(lnCompensateDetail.getCompensateId());
                    List<LnCompensate> compensateList =lnCompensateMapper.selectByExample(compensateExample);
                    PartnerEnum partnerEnum = null;
                    if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(compensateList.get(0).getPartnerCode())) {
                        partnerEnum = PartnerEnum.YUN_DAI_SELF;
                    } else {
                        partnerEnum = PartnerEnum.SEVEN_DAI_SELF;
                    }

                    // 1、收款确认函（服务费）
                    if(serviceFeeConfirmList == null || serviceFeeConfirmList.size() == 0) {
                        logger.info("==================【重新生成"+partnerEnum.getName()+"，代偿明细编号为"+lnCompensateDetail.getId()+"的代偿收款确认函（服务费）】==================");
                        try {
                            // 生成收款确认函服务费
                            lateRepayAgreementService.renewReceiptConfirmServiceFee2Pdf(lnCompensateDetail, compensateList.get(0).getOrderNo(), partnerEnum);
                        } catch (Exception e) {
                            logger.error("生成收款确认函服务费"+lnCompensateDetail.getId()+"异常");
                            continue;
                        }
                    }

                    // 2、收款确认函（债转）
                    if(debtTransConfirmList == null || debtTransConfirmList.size() == 0) {
                        logger.info("==================【重新生成"+partnerEnum.getName()+"，代偿明细编号为"+lnCompensateDetail.getId()+"的代偿收款确认函（债转）】==================");
                        try {
                            // 生成收款确认函债转
                            lateRepayAgreementService.renewCreditorNotice2Pdf(lnCompensateDetail, compensateList.get(0).getOrderNo(), partnerEnum);
                        } catch (Exception e) {
                            logger.error("生成收款确认函债转"+lnCompensateDetail.getId()+"异常");
                            continue;
                        }
                    }

                    // 3、债权转让通知书
                    if(debtTransNoticesList == null || debtTransNoticesList.size() == 0) {
                        logger.info("==================【重新生成"+partnerEnum.getName()+"，代偿明细编号为"+lnCompensateDetail.getId()+"的代偿债权转让通知书】==================");
                        try {
                            // 生成债权转让通知书
                            lateRepayAgreementService.renewReceiptConfirmLate2Pdf(lnCompensateDetail, compensateList.get(0).getOrderNo(), partnerEnum);
                        } catch (Exception e) {
                            logger.error("生成债权转让通知书"+lnCompensateDetail.getId()+"异常");
                            continue;
                        }
                    }

                    // 4、债权转让协议
                    if(debtTransferList == null || debtTransferList.size() == 0) {
                        logger.info("==================【重新生成"+partnerEnum.getName()+"，代偿明细编号为"+lnCompensateDetail.getId()+"的代偿债权转让协议】==================");
                        try {
                            // 生成债权转让协议
                            lateRepayAgreementService.renewReditor2Pdf(lnCompensateDetail, compensateList.get(0).getOrderNo(), partnerEnum);
                        } catch (Exception e) {
                            logger.error("生成债权转让协议"+lnCompensateDetail.getId()+"异常");
                            // 代偿明细表agreement_generate_status置为FAIL
                            updateCompensateDetailFail(lnCompensateDetail.getId());
                            continue;
                        }
                        // 代偿明细表agreement_generate_status置为SUCC
                        updateCompensateDetailSucc(lnCompensateDetail.getId());
                    }else {
                        updateCompensateDetailSucc(lnCompensateDetail.getId());
                    }

                    // 发送代偿协议通知
                    if((serviceFeeConfirmList == null || serviceFeeConfirmList.size() == 0) ||
                            (debtTransConfirmList == null || debtTransConfirmList.size() == 0) ||
                            (debtTransNoticesList == null || debtTransNoticesList.size() == 0) ||
                            (debtTransferList == null || debtTransferList.size() == 0)) {
                        logger.info("==================【重新生成代偿协议，发送"+partnerEnum.getName()+"代偿协议通知】==================");
                        List<LnCompensateDetail> list = new ArrayList<LnCompensateDetail>();
                        LnCompensateDetail vo = new LnCompensateDetail();
                        vo.setId(lnCompensateDetail.getId());
                        vo.setPartnerLoanId(lnCompensateDetail.getPartnerLoanId());
                        list.add(vo);
                        lateRepayAgreementService.renewNotifyAgreementUrls(compensateList.get(0).getOrderNo(), list, partnerEnum);
                    }

                }
            }else {
                logger.info("==================【轮询的代偿明细列表为空，代偿协议重新生成定时已执行完一轮】==================");
            }

            //查询出来的条目小于5证明已经代偿协议已经重新生成完了一轮
            if (compensateDetailList.size() < selectNum) {
                logger.info("==================代偿协议重新生成完一轮，开始新一轮轮询==================");
                signId = 0;
            }

        } catch (Exception e) {
            logger.error("==================重新生成代偿协议线程执行异常==================", e);
        }

    }

    /**
     * 更新代偿明细表-SUCC
     * @param lnCompensateDetailId
     */
    private void updateCompensateDetailSucc(Integer lnCompensateDetailId) {
        LnCompensateDetail detailTemp = new LnCompensateDetail();
        detailTemp.setId(lnCompensateDetailId);
        detailTemp.setAgreementGenerateStatus(Constants.AGREEMENT_GRNERATE_STATUS_SUCC);
        detailTemp.setUpdateTime(new Date());
        lnCompensateDetailMapper.updateByPrimaryKeySelective(detailTemp);
    }

    /**
     * 更新代偿明细表-FAILL
     * @param lnCompensateDetailId
     */
    private void updateCompensateDetailFail(Integer lnCompensateDetailId) {
        LnCompensateDetail detailTemp = new LnCompensateDetail();
        detailTemp.setId(lnCompensateDetailId);
        detailTemp.setAgreementGenerateStatus(Constants.AGREEMENT_GRNERATE_STATUS_FAIL);
        detailTemp.setUpdateTime(new Date());
        lnCompensateDetailMapper.updateByPrimaryKeySelective(detailTemp);
    }

}
