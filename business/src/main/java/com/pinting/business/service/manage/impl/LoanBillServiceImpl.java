package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.LnRepayMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.model.vo.LoanBillVO;
import com.pinting.business.service.manage.LoanBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日常业务-借款账单管理服务
 *
 * @author shh
 * @date 2018/7/9 10:55
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class LoanBillServiceImpl implements LoanBillService {

    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;

    @Override
    public int queryLoanBillCount(LoanBillVO record) {
        return lnRepayScheduleMapper.selectLoanBillCount(record);
    }

    @Override
    public List<LoanBillVO> queryLoanBillList(LoanBillVO record) {
        List<LoanBillVO> list = lnRepayScheduleMapper.selectLoanBillList(record);
        return list.size() > 0 ? list : null;
    }
}
