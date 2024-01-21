package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedNotifyPartnerService;
import com.pinting.business.accounting.loan.service.LoanAccountService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.coreflow.core.enums.BusinessTypeEnum;
import com.pinting.business.dao.*;
import com.pinting.business.model.BsDailySettlementFile;
import com.pinting.business.model.LnAccountFill;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.LnAccountFillDetailVO;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.ExportUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.*;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_WaitFill;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_WaitFill;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_WaitFill;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_WaitFill;
import com.pinting.gateway.out.service.loan.DafyNoticeService;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/7
 * Description:
 */
@Service
public class DepFixedNotifyPartnerServiceImpl implements DepFixedNotifyPartnerService {

    private Logger log = LoggerFactory.getLogger(DepFixedNotifyPartnerServiceImpl.class);

    @Autowired
    private DafyNoticeService dafyNoticeService;
    @Autowired
    private LnAccountFillMapper lnAccountFillMapper;
    @Autowired
    private BsDailySettlementFileMapper bsDailySettlementFileMapper;
    @Autowired
    private DepLoan7NoticeService depLoan7NoticeService;
    @Autowired
    private LnLoanMapper lnLoanMapper;

    @Override
    public void notifyWaitFill2YunDai(LnAccountFill lnAccountFill, List<LnAccountFillDetailVO> detailList) {
        B2GReqMsg_DafyLoanNotice_WaitFill waitFillReq = new B2GReqMsg_DafyLoanNotice_WaitFill();

        //生成补账明细文件excel
        //结算明细文件下载地址配置
        String prePath = GlobEnvUtil.get("dep.yundai.waitFill.path");
        waitFillExcel(Constants.WAIT_FILL_FILE, prePath, detailList, PartnerEnum.YUN_DAI_SELF);
        String excel_name = Constants.WAIT_FILL_FILE + "_" + DateUtil.formatYYYYMMDD(new Date()) + ".csv";
        String fileUrl = GlobEnvUtil.get("dep.yundai.waitFill.path") + excel_name;

        //日结明细文件表生成
        BsDailySettlementFile file = new BsDailySettlementFile();
        file.setBusinessType(Constants.BUSINESS_TYPE_ACCOUNT_FILL_DETAIL);
        file.setCreateTime(new Date());
        file.setFileAddress(fileUrl);
        file.setNote("补账结算明细文件");
        file.setPartnerCode(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
        file.setTargetDate(lnAccountFill.getFillDate());
        file.setUpdateTime(new Date());
        bsDailySettlementFileMapper.insertSelective(file);

        waitFillReq.setAmount(lnAccountFill.getAmount());
        waitFillReq.setFillDate(lnAccountFill.getFillDate());
        waitFillReq.setFillType(lnAccountFill.getFillType());
        waitFillReq.setOrderNo(lnAccountFill.getOrderNo());
        waitFillReq.setFileUrl(fileUrl.substring(fileUrl.indexOf("/ftp", 0)+4));

        B2GResMsg_DafyLoanNotice_WaitFill waitFillRes = null;
        try {
            int i = 0;
            while (waitFillRes == null || !ConstantUtil.BSRESCODE_SUCCESS.equals(waitFillRes.getResCode())) {
                if(i > 0) {
                    log.error(waitFillReq.getFillDate()+"待补账通知发送失败");
                }
                i++;
                waitFillRes = dafyNoticeService.noticeWaitFill(waitFillReq);
                if(waitFillRes != null || ConstantUtil.BSRESCODE_SUCCESS.equals(waitFillRes.getResCode())){
                    log.info(waitFillReq.getFillDate()+"待补账通知发送成功");
					/*待补账通知发送成功，修改ln_account_fill状态为PROCESS：待补账*/
                    LnAccountFill lnAccountFillTemp = new LnAccountFill();
                    lnAccountFillTemp.setId(lnAccountFill.getId());
                    lnAccountFillTemp.setStatus(Constants.FILL_FILL_STATUS_PROCESS);
                    lnAccountFillTemp.setUpdateTime(new Date());
                    lnAccountFillMapper.updateByPrimaryKeySelective(lnAccountFillTemp);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyWaitFill2SevenDai(LnAccountFill lnAccountFill, List<LnAccountFillDetailVO> detailList) {
        B2GReqMsg_DepLoan7Notice_WaitFill waitFillReq = new B2GReqMsg_DepLoan7Notice_WaitFill();

        //生成补账明细文件excel
        //结算明细文件下载地址配置
        String prePath = GlobEnvUtil.get("dep.7dai.waitFill.path");
        waitFillExcel(Constants.WAIT_FILL_FILE, prePath, detailList, PartnerEnum.SEVEN_DAI_SELF);
        String excel_name = Constants.WAIT_FILL_FILE + "_" + DateUtil.formatYYYYMMDD(new Date()) + ".csv";
        String fileUrl = GlobEnvUtil.get("dep.7dai.waitFill.path") + File.separator + excel_name;

        //日结明细文件表生成
        BsDailySettlementFile file = new BsDailySettlementFile();
        file.setBusinessType(Constants.BUSINESS_TYPE_ACCOUNT_FILL_DETAIL);
        file.setCreateTime(new Date());
        file.setFileAddress(fileUrl);
        file.setNote("补账结算明细文件");
        file.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        file.setTargetDate(lnAccountFill.getFillDate());
        file.setUpdateTime(new Date());
        bsDailySettlementFileMapper.insertSelective(file);

        waitFillReq.setAmount(lnAccountFill.getAmount());
        waitFillReq.setFillDate(lnAccountFill.getFillDate());
        waitFillReq.setFillType(lnAccountFill.getFillType());
        waitFillReq.setOrderNo(lnAccountFill.getOrderNo());
        waitFillReq.setFileUrl(fileUrl.substring(fileUrl.indexOf("/ftp", 0)+4));

        B2GResMsg_DepLoan7Notice_WaitFill waitFillRes = null;
        try {
            int i = 0;
            while (waitFillRes == null || !ConstantUtil.BSRESCODE_SUCCESS.equals(waitFillRes.getResCode())) {
                if(i > 0) {
                    log.error(waitFillReq.getFillDate()+"待补账通知发送失败");
                }
                i++;
                waitFillRes = depLoan7NoticeService.noticeWaitFill(waitFillReq);
                if(waitFillRes != null || ConstantUtil.BSRESCODE_SUCCESS.equals(waitFillRes.getResCode())){
                    log.info(waitFillReq.getFillDate()+"待补账通知发送成功");
					/*待补账通知发送成功，修改ln_account_fill状态为PROCESS：待补账*/
                    LnAccountFill lnAccountFillTemp = new LnAccountFill();
                    lnAccountFillTemp.setId(lnAccountFill.getId());
                    lnAccountFillTemp.setStatus(Constants.FILL_FILL_STATUS_PROCESS);
                    lnAccountFillTemp.setUpdateTime(new Date());
                    lnAccountFillMapper.updateByPrimaryKeySelective(lnAccountFillTemp);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 补账明细文件excel
     */
    private void waitFillExcel(String prefixName, String prePath, List<LnAccountFillDetailVO> detailList, PartnerEnum partnerEnum) {

        List<String> content = new ArrayList<>();
        // 设置标题
        StringBuffer header = new StringBuffer();
        header.append("借款编号").append(",");
        header.append("还款编号").append(",");
        if(PartnerEnum.SEVEN_DAI_SELF.equals(partnerEnum) || PartnerEnum.YUN_DAI_SELF.equals(partnerEnum)) {
            header.append("结算金额").append(",");
        }
        header.append("完成时间");
        if(PartnerEnum.SEVEN_DAI_SELF.equals(partnerEnum) || PartnerEnum.YUN_DAI_SELF.equals(partnerEnum)) {
            header.append(",").append("业务标识");
        }
        content.add(header.toString());
        LnLoan lnLoan = null;
        if (!CollectionUtils.isEmpty(detailList)) {
            for (LnAccountFillDetailVO data : detailList) {
                StringBuffer contentBuffer = new StringBuffer();
                contentBuffer.append(StringUtil.isEmpty(data.getPartnerLoanId()) ? "" : String.valueOf(data.getPartnerLoanId())).append(",");
                contentBuffer.append(StringUtil.isEmpty(data.getPartnerRepayId()) ? "" : String.valueOf(data.getPartnerRepayId())).append(",");
                contentBuffer.append(data.getAmount() == null ? "" : data.getAmount()).append(",");
                contentBuffer.append(data.getFillDate() == null ? "" : DateUtil.formatYYYYMMDD(data.getFillDate()));

                if(PartnerEnum.SEVEN_DAI_SELF.equals(partnerEnum)) {
                    lnLoan = lnLoanMapper.selectLoanByPartnerLoanId(data.getPartnerLoanId());
                    if (StringUtil.isNotBlank(lnLoan.getPartnerBusinessFlag())) {
                        if(BusinessTypeEnum.REPAY_ANY_TIME.getCode().equals(lnLoan.getPartnerBusinessFlag())) {
                            contentBuffer.append(",").append("随借随还产品");
                        }else {
                            contentBuffer.append(",").append("先息后本产品");
                        }
                    }
                } else if (PartnerEnum.YUN_DAI_SELF.equals(partnerEnum)) {
                	lnLoan = lnLoanMapper.selectLoanByPartnerLoanId(data.getPartnerLoanId());
                    if (StringUtil.isNotBlank(lnLoan.getPartnerBusinessFlag())) {
                        if(BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode().equals(lnLoan.getPartnerBusinessFlag())) {
                            contentBuffer.append(",").append("等本等息产品");
                        } else if (BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode().equals(lnLoan.getPartnerBusinessFlag())) {
                            contentBuffer.append(",").append("等额本息产品");
                        }
                    }
                }

                content.add(contentBuffer.toString());
            }
        }
        try {
            log.info("补账明细文件内容：{}", content);
            ExportUtil.exportLocalCSV(prePath, content,  prefixName + "_" + DateUtil.formatYYYYMMDD(new Date()) + ".csv");
            log.info("补账明细文件生成结束");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("补账明细文件生成失败：", e.getMessage());
        }
    }
}
