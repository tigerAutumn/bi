package com.pinting.business.service.loan.impl;

import com.pinting.business.dao.BsRevenueTransRecordMapper;
import com.pinting.business.dao.LnCompensateMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnRepayMapper;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.loan.ZsdLoanCheckAccountService;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.ExportUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:      shh
 * Date:        2017/9/4
 * Description: 赞时贷对账文件生成服务
 */
@Service
public class ZsdLoanCheckAccountServiceImpl implements ZsdLoanCheckAccountService {

    @Autowired
    private LnCompensateMapper lnCompensateMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnRepayMapper lnRepayMapper;
    @Autowired
    private BsRevenueTransRecordMapper bsRevenueTransRecordMapper;
    
    @Override
    public void checkAccount(String time) {
    	// 1. 生成借款+借款手续费对账单
        zsdLoanFile(time);
        // 2. 生成还款对账单
        zsdRepayFile(time);
        // 3. 成功代偿对账单
        zsdLateRepayFile(time);
        // 4. 成功赞时贷营收对账单
        zsdRevenueFile(time);
        // 5. 重复还款对账单
        zsdRepeatReapyFile(time);
    }
    
    /**
     * 1. 生成借款+借款手续费对账单
     * @param time
     */
    private void zsdLoanFile(String time) {
        List<DafyLoanSelfForCheckVO> loanList = lnLoanMapper.selectForZsdCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("用户编号").append(",");
        header.append("借款编号").append(",");
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("金额").append(",");
        header.append("期数").append(",");
        header.append("借款手续费").append(",");
        header.append("实付金额").append(",");
        header.append("应收支付通道费用").append(",");
        header.append("申请时间").append(",");
        header.append("放款发起时间").append(",");
        header.append("到账时间").append(",");
        header.append("支付渠道").append(",");
        header.append("是否成功").append(",");
        header.append("失败原因");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        for (DafyLoanSelfForCheckVO vo: loanList) {
            StringBuffer contentBuffer = new StringBuffer();
            contentBuffer.append(vo.getLn_user_id()).append(",");
            contentBuffer.append(vo.getPartner_loan_id()).append(",");
            contentBuffer.append(vo.getPartner_code()).append(",");
            contentBuffer.append(vo.getTrans_type()).append(",");
            contentBuffer.append(vo.getApply_amount()).append(",");
            contentBuffer.append(vo.getPeriod()).append(",");
            contentBuffer.append(vo.getHead_fee()).append(",");
            contentBuffer.append(vo.getApprove_amount()).append(",");     
            contentBuffer.append("").append(",");
            contentBuffer.append(DateUtil.format(vo.getApply_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(DateUtil.format(vo.getApply_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(DateUtil.format(vo.getLoan_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getChannel()).append(",");
            contentBuffer.append(vo.getStatus()).append(",");
            contentBuffer.append(StringUtil.isBlank(vo.getReturn_msg()) ? "无" : vo.getReturn_msg());
            content.add(contentBuffer.toString());
        }
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("zsd.loan.account.path") + File.separator + "loan", content, "loan_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }

    /**
     * 2. 生成还款对账单
     */
    private void zsdRepayFile(String time) {
        List<ZsdRepayForCheckVO> loanList = lnRepayMapper.selectForZsdCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("用户编号").append(",");
        header.append("借款编号").append(",");
        header.append("还款订单号").append(",");
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("总金额").append(",");
        header.append("本金").append(",");
        header.append("利息").append(",");
        header.append("滞纳金").append(",");
        header.append("应收支付通道费用").append(",");
        header.append("平台服务费").append(",");
        header.append("信息认证费").append(",");
        header.append("风控服务费").append(",");
        header.append("代收通道费").append(",");
        header.append("其他").append(",");
        header.append("申请时间").append(",");
        header.append("支付时间").append(",");
        header.append("到账时间").append(",");
        header.append("支付渠道").append(",");
        header.append("是否成功").append(",");
        header.append("失败原因");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        for (ZsdRepayForCheckVO vo: loanList) {
            StringBuffer contentBuffer = new StringBuffer();
            contentBuffer.append(vo.getLn_user_id() == null ? "" : vo.getLn_user_id()).append(",");
            contentBuffer.append(vo.getPartner_loan_id() == null ? "" : vo.getPartner_loan_id()).append(",");
            contentBuffer.append(vo.getPartner_order_no() == null ? "" : vo.getPartner_order_no()).append(",");
            contentBuffer.append(vo.getPartner_code() == null ? "" : vo.getPartner_code()).append(",");
            contentBuffer.append(vo.getTrans_type() == null ? "" : vo.getTrans_type()).append(",");
            contentBuffer.append(vo.getPlan_total() == null ? "" : vo.getPlan_total()).append(",");
            contentBuffer.append(vo.getPrincipal() == null ? "" : vo.getPrincipal()).append(",");
            contentBuffer.append(vo.getInterest() == null ? "" : vo.getInterest()).append(",");
            contentBuffer.append(vo.getLate_fee() == null ? 0 : vo.getLate_fee()).append(",");
            contentBuffer.append(vo.getService_fee() == null ? 0 : vo.getService_fee()).append(",");
            contentBuffer.append(vo.getPlatform_service_fee() == null ? 0 : vo.getPlatform_service_fee()).append(",");
            contentBuffer.append(vo.getInfo_certified_fee() == null ? "" : vo.getInfo_certified_fee()).append(",");
            contentBuffer.append(vo.getRisk_manage_service_fee() == null ? "" : vo.getRisk_manage_service_fee()).append(",");
            contentBuffer.append(vo.getCollection_channel_fee() == null ? "" : vo.getCollection_channel_fee()).append(",");
            contentBuffer.append(vo.getOther() == null ? "" : vo.getOther()).append(",");
            contentBuffer.append(vo.getCreate_time() == null ? "" : DateUtil.format(vo.getCreate_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getDone_time() == null ? "" : DateUtil.format(vo.getDone_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getDone_time() == null ? "" : DateUtil.format(vo.getDone_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getChannel() == null ? "" : vo.getChannel()).append(",");
            contentBuffer.append(vo.getStatus() == null ? "" : vo.getStatus()).append(",");
            contentBuffer.append(StringUtil.isBlank(vo.getReturn_msg()) ? "无" : vo.getReturn_msg());
            content.add(contentBuffer.toString());
        }
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("zsd.loan.account.path") + File.separator + "repay", content,  "repay_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }

    /**
     * 3. 成功代偿对账单
     */
    private void zsdLateRepayFile(String time) {
        List<DafyLateRepaySelfForCheckVO> loanList = lnCompensateMapper.selectForZsdCheck(time, 0, Integer.MAX_VALUE);
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
        header.append("代偿单号").append(",");
        header.append("支付单号").append(",");
        header.append("申请时间").append(",");
        header.append("到账时间").append(",");
        header.append("支付渠道").append(",");
        header.append("支付类型");
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
            contentBuffer.append(vo.getPay_type());
            content.add(contentBuffer.toString());
        }
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("zsd.loan.account.path") + File.separator + "lateRepay", content,  "lateRepay_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }

    /**
     * 4. 成功赞时贷营收对账单
     */
    private void zsdRevenueFile(String time) {
        List<ZsdRevenueForCheckVO> loanList = bsRevenueTransRecordMapper.selectForZsdCheck(time, 0, Integer.MAX_VALUE);
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
        for (ZsdRevenueForCheckVO vo: loanList) {
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
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("zsd.loan.account.path") + File.separator + "revenue", content,  "revenue_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }
    

    /**
     * 5. 重复还款对账单
     * @param time
     */
    private void zsdRepeatReapyFile(String time) {
        List<DafyLoanSelfForCheckVO> loanList = lnLoanMapper.selectZsdRepeatRepayCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("用户编号").append(",");
        header.append("借款编号").append(",");
        header.append("还款编号").append(",");
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("金额").append(",");
        header.append("支付单号").append(",");
        header.append("申请时间").append(",");
        header.append("到账时间").append(",");
        header.append("支付渠道").append(",");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        for (DafyLoanSelfForCheckVO vo: loanList) {
            StringBuffer contentBuffer = new StringBuffer();
            contentBuffer.append(vo.getLn_user_id()).append(",");
            contentBuffer.append(vo.getPartner_loan_id()).append(",");
            contentBuffer.append(vo.getPartner_repay_id()).append(",");
            contentBuffer.append(vo.getPartner_code()).append(",");
            contentBuffer.append(vo.getTrans_type()).append(",");
            contentBuffer.append(vo.getApprove_amount()).append(",");     
            contentBuffer.append(vo.getPay_order_no()).append(",");
            contentBuffer.append(DateUtil.format(vo.getApply_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(DateUtil.format(vo.getLoan_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append("BAOFOO").append(",");
            content.add(contentBuffer.toString());
        }
		ExportUtil.exportLocalCSV(GlobEnvUtil.get("zsd.loan.account.path") + File.separator + "repeatReapy", content, "repeatReapy_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }

}
