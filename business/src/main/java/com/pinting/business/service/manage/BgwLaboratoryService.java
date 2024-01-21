package com.pinting.business.service.manage;

import com.pinting.business.model.vo.LoanRepayVO;

import java.util.List;

/**
 * 币港湾实验室功能相关service
 *
 * @author shh
 * @date 2018/6/1 13:57
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public interface BgwLaboratoryService {


    /**
     * 币港湾实验室-借款用户还款情况查询列表
     * @param record
     * @return
     */
    List<LoanRepayVO> queryLoanRepayList(LoanRepayVO record);

    /**
     * 币港湾实验室-借款用户还款情况查询记录数统计
     * @param record
     * @return
     */
    Integer queryLoanRepayCount(LoanRepayVO record);
}
