package com.pinting.business.service.manage;

import com.pinting.business.model.vo.LoanBillVO;

import java.util.List;

/**
 * 日常业务-借款账单管理服务
 *
 * @author shh
 * @date 2018/7/9 10:55
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public interface LoanBillService {

    /**
     * 日常业务-借款账单管理，记录数统计
     * @param record
     * @return
     */
    public int queryLoanBillCount(LoanBillVO record);

    /**
     * 日常业务-借款账单管理，列表数据查询
     * @param record
     * @return
     */
    public List<LoanBillVO> queryLoanBillList(LoanBillVO record);

}
