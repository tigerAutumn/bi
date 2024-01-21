package com.pinting.business.service.loan.impl;

import com.pinting.business.coreflow.core.enums.BusinessTypeEnum;
import com.pinting.business.dao.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.loan.DafyLoanCheckAccountService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.ExportUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;

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
 * Date:        2017/2/6
 * Description: 达飞云贷自主放款对账文件生成服务
 */
@Service
public class DafyLoanCheckAccountServiceImpl implements DafyLoanCheckAccountService {
	private static final Logger log = LoggerFactory.getLogger(DafyLoanCheckAccountServiceImpl.class);
	
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnRepayMapper lnRepayMapper;
    @Autowired
    private LnCompensateMapper lnCompensateMapper;
    @Autowired
    private BsRevenueTransRecordMapper bsRevenueTransRecordMapper;
    @Autowired
    private LnAccountFillMapper lnAccountFillMapper;

    @Override
    public void checkAccount(String time) {
        // 1. 生成借款+借款手续费对账单
        dafyLoanFile(time);
        // 3. 成功代偿对账单
        dafyLateRepayFile(time);
        // 4. 成功云贷营收对账单
        dafyRevenueFile(time);
        // 5. 三方手续费对账单
        dafyPaymentFeeFile(time);
        // 6. 补账对账
        dafyAccountFillFile(time);
        // 2. 生成还款对账单
        dafyRepayFile(time);
    }

    /**
     * 1. 生成借款+借款手续费对账单
     */
    private void dafyLoanFile(String time) {
        List<DafyLoanSelfForCheckVO> loanList = lnLoanMapper.selectForCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("用户编号").append(",");
        header.append("借款编号").append(",");
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("金额").append(",");
        header.append("实付金额").append(",");
        header.append("手续费").append(",");
        header.append("期限").append(",");
        header.append("云贷单号").append(",");
        header.append("支付单号").append(",");
        header.append("申请时间").append(",");
        header.append("到账时间").append(",");
        header.append("支付渠道").append(",");
        header.append("是否成功").append(",");
        header.append("失败原因").append(",");
        header.append("业务标识");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        for (DafyLoanSelfForCheckVO vo: loanList) {
            if(null != vo.getPeriod() && 
            		!(BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode().equals(vo.getPartner_business_flag()) ||
            		BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode().equals(vo.getPartner_business_flag()))) {
                vo.setPeriod(vo.getPeriod().equals(12) ? 365 : vo.getPeriod() * 30);
            }
            StringBuffer contentBuffer = new StringBuffer();
            contentBuffer.append(vo.getLn_user_id()).append(",");
            contentBuffer.append(vo.getPartner_loan_id()).append(",");
            contentBuffer.append(vo.getPartner_code()).append(",");
            contentBuffer.append(vo.getTrans_type()).append(","); 
            contentBuffer.append(vo.getApply_amount()).append(",");
            contentBuffer.append(vo.getApprove_amount()).append(",");
            contentBuffer.append(vo.getHead_fee()).append(",");
            contentBuffer.append(vo.getPeriod()).append(",");
            contentBuffer.append(vo.getPartner_order_no()).append(",");
            contentBuffer.append(vo.getPay_order_no()).append(",");
            contentBuffer.append(DateUtil.format(vo.getApply_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(DateUtil.format(vo.getLoan_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getChannel()).append(",");
            contentBuffer.append(vo.getStatus()).append(",");
            contentBuffer.append("PAIED".equals(vo.getStatus()) ? "无" : vo.getReturn_msg()).append(",");
            if (!StringUtil.isEmpty(vo.getPartner_business_flag())) {
            	contentBuffer.append(vo.getPartner_business_flag());
            } else {
            	contentBuffer.append("");
            }
            content.add(contentBuffer.toString());
        }
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("self.loan.yundai.account.path") + File.separator + "loan", content, "loan_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }

    /**
     * 2. 生成还款对账单
     */
    private void dafyRepayFile(String time) {
        List<DafyRepaySelfForCheckVO> loanList = lnRepayMapper.selectForCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("用户编号").append(",");
        header.append("借款编号").append(",");
        header.append("还款编号").append(",");
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("总金额").append(",");
        header.append("本金").append(",");
        header.append("利息").append(",");
        header.append("本金滞纳金").append(",");
        header.append("利息滞纳金").append(",");
        header.append("提前还款违约金").append(",");
        header.append("云贷单号").append(",");
        header.append("支付单号").append(",");
        header.append("申请时间").append(",");
        header.append("到账时间").append(",");
        header.append("支付渠道").append(",");
        header.append("支付类型").append(",");
        header.append("是否成功").append(",");
        header.append("失败原因").append(",");
        header.append("业务标识");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        for (DafyRepaySelfForCheckVO vo: loanList) {
            StringBuffer contentBuffer = new StringBuffer();
            contentBuffer.append(vo.getLn_user_id() == null ? "" : vo.getLn_user_id()).append(",");
            contentBuffer.append(vo.getPartner_loan_id() == null ? "" : vo.getPartner_loan_id()).append(",");
            contentBuffer.append(vo.getPartner_repay_id() == null ? "" : vo.getPartner_repay_id()).append(",");
            contentBuffer.append(vo.getPartner_code() == null ? "" : vo.getPartner_code()).append(",");
            contentBuffer.append(vo.getTrans_type() == null ? "" : vo.getTrans_type()).append(",");
            contentBuffer.append(vo.getDone_total() == null ? "" : vo.getDone_total()).append(",");
            contentBuffer.append(vo.getPrincipal() == null ? "" : vo.getPrincipal()).append(",");
            contentBuffer.append(vo.getInterest() == null ? "" : vo.getInterest()).append(",");
            contentBuffer.append(vo.getPrincipal_overdue() == null ? 0 : vo.getPrincipal_overdue()).append(",");
            contentBuffer.append(vo.getInterest_overdue() == null ? 0 : vo.getInterest_overdue()).append(",");
            contentBuffer.append(vo.getLiquidated_amount() == null ? 0 : vo.getLiquidated_amount()).append(",");
            contentBuffer.append(vo.getPartner_order_no() == null ? "" : vo.getPartner_order_no()).append(",");
            contentBuffer.append(vo.getOrder_no() == null ? "" : vo.getOrder_no()).append(",");
            contentBuffer.append(vo.getCreate_time() == null ? "" : DateUtil.format(vo.getCreate_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getDone_time() == null ? "" : DateUtil.format(vo.getDone_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getChannel() == null ? "" : vo.getChannel()).append(",");
            contentBuffer.append(vo.getChannel_trans_type() == null ? "" : vo.getChannel_trans_type()).append(",");
            contentBuffer.append(vo.getStatus() == null ? "" : vo.getStatus()).append(",");
            contentBuffer.append("REPAIED".equals(vo.getStatus()) ? "无" : vo.getReturn_msg()).append(",");
            if (!StringUtil.isEmpty(vo.getPartner_business_flag())) {
            	contentBuffer.append(vo.getPartner_business_flag());
            } else {
            	contentBuffer.append("");
            }
            content.add(contentBuffer.toString());
        }
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("self.loan.yundai.account.path") + File.separator + "repay", content,  "repay_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }

    /**
     * 3. 成功代偿对账单
     */
    private void dafyLateRepayFile(String time) {
        List<DafyLateRepaySelfForCheckVO> loanList = lnCompensateMapper.selectForCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("用户编号").append(",");
        header.append("借款编号").append(",");
        header.append("还款编号").append(",");
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("代偿金额").append(",");
        header.append("代偿本金").append(",");
        header.append("代偿利息").append(",");
        header.append("预留字段1").append(",");
        header.append("云贷单号").append(",");
        header.append("支付单号").append(",");
        header.append("申请时间").append(",");
        header.append("到账时间").append(",");
        header.append("支付渠道").append(",");
        header.append("支付类型").append(",");
        header.append("业务标识");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        for (DafyLateRepaySelfForCheckVO vo: loanList) {
            StringBuffer contentBuffer = new StringBuffer();
            contentBuffer.append(vo.getPartner_user_id()).append(",");
            contentBuffer.append(vo.getPartner_loan_id()).append(",");
            contentBuffer.append(vo.getPartner_repay_id()).append(",");
            contentBuffer.append(vo.getPartner_code()).append(",");
            contentBuffer.append(vo.getTrans_type()).append(",");
            contentBuffer.append(vo.getTotal()).append(",");
            contentBuffer.append(vo.getPrincipal()).append(",");
            contentBuffer.append(vo.getInterest()).append(",");
            contentBuffer.append(vo.getReservedField1()).append(",");
            contentBuffer.append(vo.getOrder_no()).append(",");
            contentBuffer.append(vo.getPay_order_no()).append(",");
            contentBuffer.append(DateUtil.format(vo.getApply_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(DateUtil.format(vo.getFinish_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getChannel()).append(",");
            contentBuffer.append(vo.getPay_type()).append(",");
            if (!StringUtil.isEmpty(vo.getPartner_business_flag())) {
            	contentBuffer.append(vo.getPartner_business_flag());
            } else {
            	contentBuffer.append("");
            }
            content.add(contentBuffer.toString());
        }
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("self.loan.yundai.account.path") + File.separator + "lateRepay", content,  "lateRepay_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }

    /**
     * 4. 成功云贷营收对账单
     */
    private void dafyRevenueFile(String time) {
        List<DafyRevenueSelfForCheckVO> loanList = bsRevenueTransRecordMapper.selectForCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("金额").append(",");
        header.append("支付单号").append(",");
        header.append("结算日期").append(",");
        header.append("申请时间").append(",");
        header.append("到账时间").append(",");
        header.append("支付渠道").append(",");
        header.append("支付类型");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        for (DafyRevenueSelfForCheckVO vo: loanList) {
            StringBuffer contentBuffer = new StringBuffer();
            contentBuffer.append(vo.getPartner_code()).append(",");
            contentBuffer.append(vo.getTrans_type()).append(",");
            contentBuffer.append(vo.getAmount()).append(",");
            contentBuffer.append(vo.getOrder_no()).append(",");
            contentBuffer.append(DateUtil.format(vo.getSettle_date(), "yyyy-MM-dd")).append(",");
            contentBuffer.append(DateUtil.format(vo.getCreate_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(DateUtil.format(vo.getFinish_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getChannel()).append(",");
            contentBuffer.append(vo.getPay_type());
            content.add(contentBuffer.toString());
        }
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("self.loan.yundai.account.path") + File.separator + "revenue", content,  "revenue_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }

    /**
     * 5. 三方手续费对账单
     */
    private void dafyPaymentFeeFile(String time) {
        // TODO 待定
    }

    /**
     * 6. 补账对账
     */
    private void dafyAccountFillFile(String time) {
        List<DafyAccountFillSelfForCheckVO> loanList = lnAccountFillMapper.selectForCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("金额").append(",");
        header.append("支付单号").append(",");
        header.append("结算日期").append(",");
        header.append("申请时间").append(",");
        header.append("到账时间").append(",");
        header.append("支付渠道").append(",");
        header.append("支付类型");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        for (DafyAccountFillSelfForCheckVO vo: loanList) {
            StringBuffer contentBuffer = new StringBuffer();
            contentBuffer.append(vo.getPartner_code()).append(",");
            contentBuffer.append(vo.getTrans_type()).append(",");
            contentBuffer.append(vo.getAmount()).append(",");
            contentBuffer.append(vo.getPay_order_no()).append(",");
            contentBuffer.append(DateUtil.format(vo.getOver_time(), "yyyy-MM-dd")).append(",");
            contentBuffer.append(DateUtil.format(vo.getApply_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(DateUtil.format(vo.getFinish_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getChannel()).append(",");
            contentBuffer.append(vo.getPay_type());
            content.add(contentBuffer.toString());
        }
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("self.loan.yundai.account.path") + File.separator + "accountFill", content,  "accountFill_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }
}
