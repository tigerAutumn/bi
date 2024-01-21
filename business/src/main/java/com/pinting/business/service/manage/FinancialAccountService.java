/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.manage;

import java.util.List;
import java.util.Map;

import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_ListQuery1;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_ListQuery2;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_ListQuery3;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_SaleAgentData;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_SaleReceivable;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_ListQuery1;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_ListQuery2;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_ListQuery3;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_SaleAgentData;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_SaleReceivable;
import com.pinting.business.model.BsFinanceWithdrawRecord;
import com.pinting.business.model.vo.FinanceOverviewForBeijingVO;
import com.pinting.business.model.vo.FinanceWithdrawRecordVO;
import com.pinting.business.model.vo.SysNotReceivableVO;

/**
 * 财务对账数据
 * @author HuanXiong
 * @version $Id: FinancialAccountService.java, v 0.1 2016-2-15 下午12:06:09 HuanXiong Exp $
 */
public interface FinancialAccountService {
    
    public void listQuery2(ReqMsg_FinancialAccount_ListQuery2 req, ResMsg_FinancialAccount_ListQuery2 res);
    
    /**
     * 财务对账数据1.0（12月1号前）
     * @param req
     * @param res
     */
    public void listQuery1(ReqMsg_FinancialAccount_ListQuery1 req, ResMsg_FinancialAccount_ListQuery1 res);
    
    /**
     * 财务对账数据：结算3.0（2.1后)
     * @param req
     * @param res
     */
    public void listQuery3(ReqMsg_FinancialAccount_ListQuery3 req, ResMsg_FinancialAccount_ListQuery3 res);
    
    /**
     * 销售应收查询
     * 
     * @param req
     * @param res
     */
    public void saleReceivable(ReqMsg_FinancialAccount_SaleReceivable req,
                               ResMsg_FinancialAccount_SaleReceivable res);
    /**
     * 销售渠道结算查询
     */
    public void selectSaleAgentData(ReqMsg_FinancialAccount_SaleAgentData req,
                                    ResMsg_FinancialAccount_SaleAgentData res);
    
    /**
     * 提现财务登记
     * @param amount
     * @param opUserId
     */
    public void financialRegistry(Double amount, String withdrawTime, Integer opUserId);
    
    /**
     * 查询所有提现财务登记记录
     * @param req
     * @return
     */
    public List<FinanceWithdrawRecordVO> financeWithdrawRecords(FinanceWithdrawRecordVO req);
    
    public Integer countFinanceWithdrawRecords(FinanceWithdrawRecordVO req);

    /**
     * 
     * @param id
     * @param confirmUserId
     */
    public void confirmFinancialRegistry(Integer id, Integer confirmUserId);

    /**
     * 业务概览-北京财务
     * @return
     */
    public Map<String,Object> queryAccountData();

    /**
     * 业务概览-北京财务 列表数据
     * @param startTime     开始时间
     * @param endTime       截止时间
     * @param pageNum       页码
     * @param numPerPage    每页显示数据
     * @return
     */
    List<FinanceOverviewForBeijingVO> queryFinanceOverviewForBeijingList(String startTime, String endTime, Integer pageNum, Integer numPerPage);

    /**
     * 业务概览-北京财务 列表总个数
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    int countFinanceOverviewForBeijingList(String startTime, String endTime);

    /**
     * 业务概览-北京财务 每列数据总计
     * @param startTime
     * @param endTime
     * @return
     */
    FinanceOverviewForBeijingVO queryFinanceOverviewForForBeijingTotal(String startTime, String endTime);
    
    /**
     * 统计理财总额
     * @return
     */
    Double queryTotalFinancialManagement();

    /**
     * 财务功能-当日系统理财未回款查询
     * @param dateTime
     * @return
     */
    List<SysNotReceivableVO> querySysNotReceivableInfo(String dateTime);
}
