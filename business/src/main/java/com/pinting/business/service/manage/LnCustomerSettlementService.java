package com.pinting.business.service.manage;

import com.pinting.business.model.vo.LnCustomerSettlementVO;

import java.util.List;

/**
 * 融资客户结算（总）
 * Created by cyb on 2017/11/16.
 */
public interface LnCustomerSettlementService {

    /**
     * 融资客户结算（总）列表查询
     * @param fnUserName
     * @param mobile
     * @param repayType
     * @param startDate
     * @param endDate
     * @param partnerCode
     * @param partnerBusinessFlag 借款产品标识
     * @param numPerPage
     * @param pageNum
     * @return
     */
    List<LnCustomerSettlementVO> queryLnCustomerSettlementList(String fnUserName, String mobile, String repayType, String startDate, String endDate, String partnerCode, String partnerBusinessFlag, Integer numPerPage, Integer pageNum);

    /**
     * 融资客户结算（总）个数
     * @param fnUserName
     * @param mobile
     * @param repayType
     * @param startDate
     * @param endDate
     * @param partnerCode
     * @param partnerBusinessFlag 借款产品标识
     * @return
     */
    int countLnCustomerSettlementList(String fnUserName, String mobile, String repayType, String startDate, String endDate, String partnerCode, String partnerBusinessFlag);

    /**
     * 融资客户结算（总）融资客户应付利息总计
     * @param fnUserName
     * @param mobile
     * @param repayType
     * @param startDate
     * @param endDate
     * @param partnerCode
     * @param partnerBusinessFlag 借款产品标识
     * @return
     */
    Double sumLnCustomerSettlementInterest(String fnUserName, String mobile, String repayType, String startDate, String endDate, String partnerCode, String partnerBusinessFlag);

}
