package com.pinting.business.service.site;

import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.RepayScheduleVO;

import java.util.List;

/**
 * 赞分期App 借款咨询与服务协议所需要的数据
 * Created by shh on 2016/9/28 11:53.
 */
public interface BsLoanFeeService {

    /**
     * 根据借款编号查询管费、信息服务费、账户管理费、保费
     * @param loanId 借款编号
     * @return
     */
    List<RepayScheduleVO> queryFeeByLoanId(Integer loanId);

    /**
     * 根据合作方借款编号 查询借款信息-借款id
     * @param partnerLoanId 合作方借款编号
     * @return
     */
    LnLoan queryLoanIdByPartnerLoanId(String partnerLoanId);
}
