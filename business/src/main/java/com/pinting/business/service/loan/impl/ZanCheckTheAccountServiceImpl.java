package com.pinting.business.service.loan.impl;

import com.pinting.business.dao.*;
import com.pinting.business.enums.TransTypeEnum;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.CommissionVO;
import com.pinting.business.model.vo.LnLoanForCheckVO;
import com.pinting.business.model.vo.LnRepayForCheckVO;
import com.pinting.business.model.vo.MarketingForCheckVO;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.loan.ZanCheckTheAccountService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.ExportUtil;
import com.pinting.core.util.GlobEnvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Author:      cyb
 * Date:        2016/9/23
 * Description: 币港湾-赞分期对账（服务方：币港湾）
 */
@Service
public class ZanCheckTheAccountServiceImpl implements ZanCheckTheAccountService {

    private Logger logger = LoggerFactory.getLogger(ZanCheckTheAccountServiceImpl.class);

    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnRepayMapper lnRepayMapper;
    @Autowired
    private LnSubjectMapper lnSubjectMapper;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private BsServiceFeeMapper bsServiceFeeMapper;
    @Autowired
    private LnMarketGrantRecordMapper lnMarketGrantRecordMapper;

    @Override
    public void checkTheAccount() {
        // 借款
        loanCheckTheAccount();
        // 还款
        repayCheckTheAccount();
        // 营销
        marketingCheckTheAccount();
    }

    /**
     * 借款对账明细
     */
    private void loanCheckTheAccount() {
        logger.info("loan");
        String title = "loan";
        String prePath = GlobEnvUtil.get("zan.loan.path");
        List<LnLoanForCheckVO> todayLoanList = lnLoanMapper.selectLnLoanForCheck();

        List<Map<String, List<Object>>> list = new ArrayList<>();
        // 设置标题
        list.add(loanTitles());

        if (!CollectionUtils.isEmpty(todayLoanList)) {
            int index = 1;
            for (LnLoanForCheckVO data : todayLoanList) {
                Map<String, List<Object>> resultMap = new HashMap<>();
                List<Object> obj = new ArrayList<>();
                obj.add(data.getInformStatus() == null ? "" : data.getInformStatus());  // 其实是 ln_user.partner_user_id
                obj.add(data.getPartnerLoanId() == null ? "" : data.getPartnerLoanId());
                obj.add("赞分期");
                obj.add("PAY");
                obj.add(data.getApproveAmount() == null ? "" : data.getApproveAmount());
                obj.add(data.getPeriod() == null ? "" : data.getPeriod());
                obj.add(data.getApplyTime() == null ? "" : DateUtil.format(data.getApplyTime(), "yyyy/MM/dd HH:mm:ss"));
                obj.add(data.getApplyTime() == null ? "" : DateUtil.format(data.getApplyTime(), "yyyy/MM/dd HH:mm:ss"));
                obj.add(data.getLoanTime() == null ? "" : DateUtil.format(data.getLoanTime(), "yyyy/MM/dd HH:mm:ss"));
                obj.add("宝付");
                obj.add(data.getStatus() == null ? "" : data.getStatus());
                obj.add(data.getErrorMsg() == null ? "" : data.getErrorMsg());
                resultMap.put("loan" + index, obj);
                list.add(resultMap);
                index++;
            }
        }
        try {
            ExportUtil.exportLocalExcel(title, prePath, list);
            logger.info("借款对账明细文件生成结束");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("借款对账明细文件生成失败：", e.getMessage());
        }
    }

    /**
     * 还款对账明细
     */
    private void repayCheckTheAccount() {
        logger.info("还款对账明细文件生成开始");
        String title = "repay";
        String prePath = GlobEnvUtil.get("zan.repay.path");
        List<LnRepayForCheckVO> todayRepayList = lnRepayMapper.selectLnRepayForCheck();

        List<Map<String, List<Object>>> list = new ArrayList<>();
        // 设置标题
        list.add(repayTitles());

        if (!CollectionUtils.isEmpty(todayRepayList)) {
            LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(todayRepayList.get(0).getLoanId());
            LnSubjectExample lnSubjectExample = new LnSubjectExample();
            lnSubjectExample.createCriteria().andChargeRuleIdEqualTo(lnLoan.getChargeRuleId());
            List<LnSubject> lnSubjectList = lnSubjectMapper.selectByExample(lnSubjectExample);

            Double principal = 0d;
            Double interest = 0d;
            Double late_fee = 0d;
            Double supervise_fee = 0d;
            Double premium = 0d;
            Double other = 0d;

            for (LnSubject lnSubject : lnSubjectList) {
                if (Constants.SUBJECT_PRINCIPAL.equals(lnSubject.getSubjectCode())) {
                    principal = lnSubject.getNumValue();
                }
                if (Constants.SUBJECT_INTEREST.equals(lnSubject.getSubjectCode())) {
                    interest = lnSubject.getNumValue();
                }
                if (Constants.SUBJECT_SUPERVISE_FEE.equals(lnSubject.getSubjectCode())) {
                    supervise_fee = lnSubject.getNumValue();
                }
                if (Constants.SUBJECT_PREMIUM.equals(lnSubject.getSubjectCode())) {
                    premium = lnSubject.getNumValue();
                }
                if (Constants.SUBJECT_LATE_FEE.equals(lnSubject.getSubjectCode())) {
                    late_fee = lnSubject.getNumValue();
                }
                if (Constants.SUBJECT_OTHER.equals(lnSubject.getSubjectCode())) {
                    other = lnSubject.getNumValue();
                }
            }


            int index = 1;
            for (LnRepayForCheckVO data : todayRepayList) {
                BsServiceFeeExample example = new BsServiceFeeExample();
                example.createCriteria().andOrderNoEqualTo(data.getPayOrderNo());
                List<BsServiceFee> feeList = bsServiceFeeMapper.selectByExample(example);
                Map<String, List<Object>> resultMap = new HashMap<>();
                List<Object> obj = new ArrayList<>();
                obj.add(data.getPartnerUserId() == null ? "" : data.getPartnerUserId());
                obj.add(data.getPartnerLoanId() == null ? "" : data.getPartnerLoanId());
                obj.add(data.getPartnerOrderNo() == null ? "" : data.getPartnerOrderNo());
                obj.add("赞分期");
                obj.add("REPAY");
                obj.add(data.getDoneTotal() == null ? "" : data.getDoneTotal());
                obj.add(principal);// 本金
                obj.add(interest);// 利息
                obj.add(late_fee);// 滞纳金
                if(!CollectionUtils.isEmpty(feeList)) {
                    obj.add(feeList.get(0).getDoneFee() == null ? 0d : feeList.get(0).getDoneFee());// 手续费
                } else {
                    obj.add(0d);
                }
                obj.add(supervise_fee);// 监管费
                obj.add(premium);// 保费
                obj.add(other);// 其他
                obj.add(data.getCreateTime() == null ? "" : DateUtil.format(data.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
                obj.add(data.getCreateTime() == null ? "" : DateUtil.format(data.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
                obj.add(data.getDoneTime() == null ? "" : DateUtil.format(data.getDoneTime(), "yyyy/MM/dd HH:mm:ss"));
                obj.add("宝付");
                obj.add(data.getStatus() == null ? "" : data.getStatus());
                obj.add(data.getErrorMsg() == null ? "" : data.getErrorMsg());
                resultMap.put("repay" + index, obj);
                list.add(resultMap);
                index++;
            }
        }
        try {
            ExportUtil.exportLocalExcel(title, prePath, list);
            logger.info("还款对账明细文件生成结束");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("还款对账明细文件生成失败：", e.getMessage());
        }
    }

    /**
     * 营销对账明细
     */
    private  void marketingCheckTheAccount(){
        logger.info("营销对账明细文件生成开始");
        String title = "marketing";
        String prePath = GlobEnvUtil.get("zan.marketing.path");

        List<MarketingForCheckVO> voList=lnMarketGrantRecordMapper.selectMarketingForCheck();

        List<Map<String, List<Object>>> list = new ArrayList<>();
        // 设置标题
        list.add(marketingTitles());

        if (!CollectionUtils.isEmpty(voList)) {
            int index = 1;
            for (MarketingForCheckVO data : voList) {
                Map<String, List<Object>> resultMap = new HashMap<>();
                List<Object> obj = new ArrayList<>();
                obj.add(data.getPartnerUserId() == null ? "" : data.getPartnerUserId());
                obj.add(data.getPartnerOrderNo() == null ? "" : data.getPartnerOrderNo());
                obj.add("赞分期");
                obj.add("MARKETING");
                obj.add(data.getAmount() == null ? "" : data.getAmount());
                obj.add(data.getCreateTime() == null ? "" : DateUtil.format(data.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
                obj.add(data.getFinishTime() == null ? "" : DateUtil.format(data.getFinishTime(), "yyyy/MM/dd HH:mm:ss"));
                obj.add("宝付");
                obj.add(data.getStatus() == null ? "" : data.getStatus());
                obj.add(data.getErrorReason() == null ? "" : data.getErrorReason());
                resultMap.put("marketing" + index, obj);
                list.add(resultMap);
                index++;
            }
        }
        try {
            ExportUtil.exportLocalExcel(title, prePath, list);
            logger.info("营销对账明细文件生成结束");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("营销对账明细文件生成失败：", e.getMessage());
        }

    }


    /**
     * 返回借款对账单的标题
     *
     * @return
     */
    private Map<String, List<Object>> loanTitles() {
        Map<String, List<Object>> titleMap = new HashMap<>();
        List<Object> titles = new ArrayList<>();
        titles.add("用户编号");
        titles.add("借款编号");
        titles.add("机构");
        titles.add("业务类型");
        titles.add("金额");
        titles.add("期数");
        titles.add("申请时间");
        titles.add("放款发起时间");
        titles.add("到账时间");
        titles.add("支付渠道");
        titles.add("是否成功");
        titles.add("失败原因");
        titleMap.put("title", titles);
        return titleMap;
    }

    /**
     * 返回营销对账单的标题
     * @return
     */
    private static Map<String, List<Object>> marketingTitles() {
        Map<String, List<Object>> titleMap = new HashMap<>();
        List<Object> titles = new ArrayList<>();
        titles.add("用户编号");
        titles.add("营销编号");
        titles.add("机构");
        titles.add("业务类型");
        titles.add("金额");
        titles.add("申请时间");
        titles.add("到账时间");
        titles.add("支付渠道");
        titles.add("是否成功");
        titles.add("失败原因");
        titleMap.put("title", titles);
        return titleMap;
    }

    /**
     * 返回还款对账单的标题
     *
     * @return
     */
    private Map<String, List<Object>> repayTitles() {
        Map<String, List<Object>> titleMap = new HashMap<>();
        List<Object> titles = new ArrayList<>();
        titles.add("用户编号");
        titles.add("借款编号");
        titles.add("还款编号");
        titles.add("机构");
        titles.add("业务类型");
        titles.add("总金额");
        titles.add("本金");
        titles.add("利息");
        titles.add("滞纳金");
        titles.add("手续费");
        titles.add("监管费");
        titles.add("保费");
        titles.add("其他");
        titles.add("申请时间");
        titles.add("支付时间");
        titles.add("到账时间");
        titles.add("支付渠道");
        titles.add("是否成功");
        titles.add("失败原因");
        titleMap.put("title", titles);
        return titleMap;
    }
}

