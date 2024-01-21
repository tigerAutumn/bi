package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.model.vo.LnCustomerSettlementVO;
import com.pinting.business.service.manage.LnCustomerSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cyb on 2017/11/16.
 */
@Service
public class LnCustomerSettlementServiceImpl implements LnCustomerSettlementService {

    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;

    @Override
    public List<LnCustomerSettlementVO> queryLnCustomerSettlementList(String fnUserName, String mobile, String repayType, String startDate, String endDate, String partnerCode, String queryPartnerBusinessFlag, Integer numPerPage, Integer pageNum) {
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        List<LnCustomerSettlementVO> result = lnRepayScheduleMapper.selectLnCustomerSettlementList(fnUserName, mobile, repayType, startDate, endDate, partnerCode, queryPartnerBusinessFlag, numPerPage, start);
//        for (LnCustomerSettlementVO vo: result) {
//            Double interest = 0d;
//            if("DC".equals(vo.getRepayType())) {
//                // 代偿
//                interest = lnRepayScheduleMapper.lnCustomerSettlementInterest(vo.getId(), vo.getRepayType());
//            } else if(vo.getSerialId().equals(0)) {
//                // 提前
//                interest = lnRepayScheduleMapper.lnCustomerSettlementInterest(vo.getId(), "TQ");
//            } else {
//                // 主动||代扣
//                interest = lnRepayScheduleMapper.lnCustomerSettlementInterest(vo.getId(), vo.getRepayType());
//            }
//            interest = interest == null ? 0 : interest;
//            vo.setLnPlanInterest(interest);
//            vo.setFee(interest - vo.getFnPlanInterest());
//        }
        return result;
    }

    @Override
    public int countLnCustomerSettlementList(String fnUserName, String mobile, String repayType, String startDate, String endDate, String partnerCode, String queryPartnerBusinessFlag) {
        int count = lnRepayScheduleMapper.countLnCustomerSettlementList(fnUserName, mobile, repayType, startDate, endDate, partnerCode, queryPartnerBusinessFlag);
        return count;
    }

    @Override
    public Double sumLnCustomerSettlementInterest(String fnUserName, String mobile, String repayType, String startDate, String endDate, String partnerCode, String queryPartnerBusinessFlag) {
        Double sum = lnRepayScheduleMapper.sumLnCustomerSettlementInterest(fnUserName, mobile, repayType, startDate, endDate, partnerCode, queryPartnerBusinessFlag);
        return sum == null ? 0d : sum;
    }
}
