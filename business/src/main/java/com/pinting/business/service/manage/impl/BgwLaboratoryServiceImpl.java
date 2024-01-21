package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.model.vo.LoanRepayVO;
import com.pinting.business.service.manage.BgwLaboratoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 币港湾实验室功能相关服务
 *
 * @author shh
 * @date 2018/6/1 13:57
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class BgwLaboratoryServiceImpl implements BgwLaboratoryService {

    @Autowired
    private LnUserMapper lnUserMapper;

    @Override
    public List<LoanRepayVO> queryLoanRepayList(LoanRepayVO record) {
        List<Integer> lnUserIdList = lnUserMapper.selectLoanRepayForLnUserIdList(record);
        List<LoanRepayVO> resultList = lnUserMapper.selectLoanRepayInfoByLnUserId(lnUserIdList,
                record.getOrderField(), record.getOrderDirection());
        return resultList;
    }

    @Override
    public Integer queryLoanRepayCount(LoanRepayVO record) {
        Integer count = lnUserMapper.selectLoanRepayForLnUserIdCount(record);
        return count;
    }
}
