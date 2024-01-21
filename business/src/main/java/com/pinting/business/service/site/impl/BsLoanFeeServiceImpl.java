package com.pinting.business.service.site.impl;

import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.RepayScheduleVO;
import com.pinting.business.service.site.BsLoanFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shh on 2016/9/28 11:57.
 */
@Service
public class BsLoanFeeServiceImpl implements BsLoanFeeService {

    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;

    @Override
    public List<RepayScheduleVO> queryFeeByLoanId(Integer loanId) {
        List<RepayScheduleVO> list = lnRepayScheduleMapper.selectFeeByLoanId(loanId);
        return list;
    }

    @Override
    public LnLoan queryLoanIdByPartnerLoanId(String partnerLoanId) {
        return lnLoanMapper.selectLoanIdByPartnerLoanId(partnerLoanId);
    }
}
