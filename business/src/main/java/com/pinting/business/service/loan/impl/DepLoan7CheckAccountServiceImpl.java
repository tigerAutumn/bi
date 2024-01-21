package com.pinting.business.service.loan.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsRevenueTransRecordMapper;
import com.pinting.business.dao.LnCompensateMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnRepayMapper;
import com.pinting.business.model.vo.DafyLateRepaySelfForCheckVO;
import com.pinting.business.model.vo.DafyLoanSelfForCheckVO;
import com.pinting.business.model.vo.DafyRepaySelfForCheckVO;
import com.pinting.business.model.vo.DafyRevenueSelfForCheckVO;
import com.pinting.business.service.loan.DepLoan7CheckAccountService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.ExportUtil;
import com.pinting.core.util.GlobEnvUtil;

/**
 * 
 * @author SHENGUOPING
 * @date  2017年12月13日 下午6:39:14
 */
@Service
public class DepLoan7CheckAccountServiceImpl implements DepLoan7CheckAccountService {
	
	@Autowired
	private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnRepayMapper lnRepayMapper;
    @Autowired
    private LnCompensateMapper lnCompensateMapper;
    @Autowired
    private BsRevenueTransRecordMapper bsRevenueTransRecordMapper;
    @Override
	public void checkAccount(String time) {
    	// 1. 生成借款+借款手续费对账单
        depLoan7File(time);
        // 2. 生成还款对账单
        depRepaySevenFile(time);
        // 3. 成功代偿对账单
        sevenLateRepayFile(time);
        // 4. 成功七贷营收对账单
        sevenRevenueFile(time);
	}


	/**
     * 1. 生成借款+借款手续费对账单
     */
    private void depLoan7File(String time) {
        List<DafyLoanSelfForCheckVO> loanList = lnLoanMapper.selectForDepSevenCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("用户编号").append(",");
        header.append("借款编号").append(",");
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("业务标识").append(",");
        header.append("金额").append(",");
        header.append("实付金额").append(",");
        header.append("手续费").append(",");
        header.append("期限").append(",");
        header.append("7贷单号").append(",");
        header.append("支付单号").append(",");
        header.append("申请时间").append(",");
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
            contentBuffer.append(Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(vo.getPartner_business_flag())? "随借随还产品" : "先息后本产品").append(",");
            contentBuffer.append(vo.getApply_amount()).append(",");
            contentBuffer.append(vo.getApprove_amount()).append(",");
            contentBuffer.append(vo.getHead_fee()==null? 0d : vo.getHead_fee()).append(",");
            if (Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(vo.getPartner_business_flag())) {
            	contentBuffer.append(vo.getPeriod()+"天").append(",");
			} else {
				contentBuffer.append(vo.getPeriod()+"期").append(",");
			}
            contentBuffer.append(vo.getPartner_order_no()).append(",");
            contentBuffer.append(vo.getPay_order_no()).append(",");
            contentBuffer.append(DateUtil.format(vo.getApply_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(DateUtil.format(vo.getLoan_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getChannel()).append(",");
            contentBuffer.append("PAIED".equals(vo.getStatus()) ? "是" : "否").append(",");
            contentBuffer.append("PAIED".equals(vo.getStatus()) ? "无" : vo.getReturn_msg());
            content.add(contentBuffer.toString());
        }
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("7dai.loan.account.path") + File.separator + "loan", content, "loan_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }

    /**
     * 2. 生成还款对账单
     */
    private void depRepaySevenFile(String time) {
        List<DafyRepaySelfForCheckVO> loanList = lnRepayMapper.selectForDepSevenCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("用户编号").append(",");
        header.append("借款编号").append(",");
        header.append("还款编号").append(",");
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("业务标识").append(",");
        header.append("总金额").append(",");
        header.append("本金").append(",");
        header.append("利息").append(",");
        header.append("本金滞纳金").append(",");
        header.append("利息滞纳金").append(",");
        header.append("预留字段1").append(",");
        header.append("七贷单号").append(",");
        header.append("支付单号").append(",");
        header.append("申请时间").append(",");
        header.append("到账时间").append(",");
        header.append("支付渠道").append(",");
        header.append("支付类型").append(",");
        header.append("是否成功").append(",");
        header.append("失败原因");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        for (DafyRepaySelfForCheckVO vo: loanList) {
            StringBuffer contentBuffer = new StringBuffer();
            contentBuffer.append(vo.getLn_user_id() == null ? "" : vo.getLn_user_id()).append(",");
            contentBuffer.append(vo.getPartner_loan_id() == null ? "" : vo.getPartner_loan_id()).append(",");
            contentBuffer.append(vo.getPartner_repay_id() == null ? "" : vo.getPartner_repay_id()).append(",");
            contentBuffer.append(vo.getPartner_code() == null ? "" : vo.getPartner_code()).append(",");
            contentBuffer.append(vo.getTrans_type() == null ? "" : vo.getTrans_type()).append(",");
            contentBuffer.append(Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(vo.getPartner_business_flag())? "随借随还产品" : "先息后本产品").append(",");
            contentBuffer.append(vo.getPlan_total() == null ? "" : vo.getPlan_total()).append(",");
            contentBuffer.append(vo.getPrincipal() == null ? "" : vo.getPrincipal()).append(",");
            contentBuffer.append(vo.getInterest() == null ? "" : vo.getInterest()).append(",");
            contentBuffer.append(vo.getPrincipal_overdue() == null ? 0 : vo.getPrincipal_overdue()).append(",");
            contentBuffer.append(vo.getInterest_overdue() == null ? 0 : vo.getInterest_overdue()).append(",");
            contentBuffer.append("").append(",");
            contentBuffer.append(vo.getPartner_order_no() == null ? "" : vo.getPartner_order_no()).append(",");
            contentBuffer.append(vo.getOrder_no() == null ? "" : vo.getOrder_no()).append(",");
            contentBuffer.append(vo.getCreate_time() == null ? "" : DateUtil.format(vo.getCreate_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getDone_time() == null ? "" : DateUtil.format(vo.getDone_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getChannel() == null ? "" : vo.getChannel()).append(",");
            contentBuffer.append(vo.getChannel_trans_type() == null ? "" : vo.getChannel_trans_type()).append(",");
            contentBuffer.append(vo.getStatus() == null ? "" : vo.getStatus()).append(",");
            contentBuffer.append("REPAIED".equals(vo.getStatus()) ? "无" : vo.getReturn_msg());
            content.add(contentBuffer.toString());
        }
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("7dai.loan.account.path") + File.separator + "repay", content,  "repay_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }
    /**
     * 3. 成功代偿对账单
     */
    private void sevenLateRepayFile(String time) {
        List<DafyLateRepaySelfForCheckVO> loanList = lnCompensateMapper.selectFor7DaiCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("用户编号").append(",");
        header.append("借款编号").append(",");
        header.append("还款编号").append(",");
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("业务标识").append(",");
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
            contentBuffer.append(Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(vo.getPartner_business_flag())? "随借随还产品" : "先息后本产品").append(",");
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
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("7dai.loan.account.path") + File.separator + "lateRepay", content,  "lateRepay_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }

    /**
     * 4. 成功七贷营收对账单
     */
    private void sevenRevenueFile(String time) {
        List<DafyRevenueSelfForCheckVO> loanList = bsRevenueTransRecordMapper.selectForSevenCheck(time, 0, Integer.MAX_VALUE);
        StringBuffer header = new StringBuffer();
        header.append("机构").append(",");
        header.append("业务类型").append(",");
        header.append("业务标识").append(",");
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
            contentBuffer.append("").append(",");
            contentBuffer.append(vo.getAmount()).append(",");
            contentBuffer.append(vo.getOrder_no()).append(",");
            contentBuffer.append(DateUtil.format(vo.getSettle_date(), "yyyy-MM-dd")).append(",");
            contentBuffer.append(DateUtil.format(vo.getCreate_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(DateUtil.format(vo.getFinish_time(), "yyyy-MM-dd HH:mm:ss")).append(",");
            contentBuffer.append(vo.getChannel()).append(",");
            contentBuffer.append(vo.getPay_type());
            content.add(contentBuffer.toString());
        }
        ExportUtil.exportLocalCSV(GlobEnvUtil.get("7dai.loan.account.path") + File.separator + "revenue", content,  "revenue_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
    }

}
