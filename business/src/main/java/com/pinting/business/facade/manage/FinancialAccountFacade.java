/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.manage;

import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.vo.FinanceOverviewForBeijingVO;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.MoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.service.manage.FinancialAccountService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author HuanXiong
 * @version $Id: FinancialAccountFacade.java, v 0.1 2016-2-15 下午12:34:29 HuanXiong Exp $
 */
@Component("FinancialAccount")
public class FinancialAccountFacade {
    
    @Autowired
    private FinancialAccountService financialAccountService;
    
    public void listQuery2(ReqMsg_FinancialAccount_ListQuery2 req, ResMsg_FinancialAccount_ListQuery2 res) {
        financialAccountService.listQuery2(req, res);
    }
    
    public void listQuery1(ReqMsg_FinancialAccount_ListQuery1 req, ResMsg_FinancialAccount_ListQuery1 res) {
        financialAccountService.listQuery1(req, res);
    }
    
    public void listQuery3(ReqMsg_FinancialAccount_ListQuery3 req, ResMsg_FinancialAccount_ListQuery3 res) {
        financialAccountService.listQuery3(req, res);
    }
    
    public void saleReceivable(ReqMsg_FinancialAccount_SaleReceivable req, ResMsg_FinancialAccount_SaleReceivable res) {
        financialAccountService.saleReceivable(req, res);
    }
    
    public void saleAgentData(ReqMsg_FinancialAccount_SaleAgentData req,
                              ResMsg_FinancialAccount_SaleAgentData res) {
        financialAccountService.selectSaleAgentData(req, res);
    }

    //业务概览-北京财务
    public void accountDataList(ReqMsg_FinancialAccount_AccountDataList req, ResMsg_FinancialAccount_AccountDataList res) {
        Map<String,Object> accountDataMap = financialAccountService.queryAccountData();
        //存量总额
        res.setYundaiTotalAmount((Double) accountDataMap.get("yundaiTotalAmount"));
        res.setTotalAmount7Dai((Double) accountDataMap.get("totalAmount7Dai"));
        //赞分期存量金额  = 出借本金 (ln_loan) - 还款本金(bs_loan_finance_repay)
        Double zanLoanPrincipal = (Double) accountDataMap.get("zanLoanPrincipal");
        Double zanRepaymentPrincipal = (Double) accountDataMap.get("zanRepaymentPrincipal");
        res.setZanTotalAmount(MoneyUtil.subtract(zanLoanPrincipal, zanRepaymentPrincipal).doubleValue());
        
        //累计理财总额
        Double totalAmount = financialAccountService.queryTotalFinancialManagement();
        res.setTotalAmount(totalAmount);
        //融资总额
        Double yundaiFinancingAmount = (Double) accountDataMap.get("yundaiFinancingAmount");
        Double financingAmount7Dai = (Double) accountDataMap.get("financingAmount7Dai");
        Double zanFinancingAmount = (Double) accountDataMap.get("zanFinancingAmount");
        Double financingAmount = MoneyUtil.add(MoneyUtil.add(yundaiFinancingAmount, financingAmount7Dai).doubleValue(), zanFinancingAmount).doubleValue();
        res.setYundaiFinancingAmount(yundaiFinancingAmount);
        res.setFinancingAmount7Dai(financingAmount7Dai);
        res.setZanFinancingAmount(zanFinancingAmount);
        res.setFinancingAmount(financingAmount);

        //总记录数
        int total = financialAccountService.countFinanceOverviewForBeijingList(req.getStartTime(), req.getEndTime());
        res.setTotalRows(total);
        //列表
        List<FinanceOverviewForBeijingVO> resultList = financialAccountService.queryFinanceOverviewForBeijingList(req.getStartTime(), req.getEndTime(), req.getPageNum(), req.getNumPerPage());
        ArrayList<HashMap<String, Object>> valueList = BeanUtils.classToArrayList(resultList);

        //每列数据总计
        FinanceOverviewForBeijingVO totalAmont = financialAccountService.queryFinanceOverviewForForBeijingTotal(req.getStartTime(), req.getEndTime());
        org.springframework.beans.BeanUtils.copyProperties(totalAmont, res);

        res.setValueList(valueList);
    }
    
}
