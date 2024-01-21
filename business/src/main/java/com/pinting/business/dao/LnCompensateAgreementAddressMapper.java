package com.pinting.business.dao;

import com.pinting.business.model.LnCompensateAgreementAddress;
import com.pinting.business.model.LnCompensateAgreementAddressExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LnCompensateAgreementAddressMapper {
    long countByExample(LnCompensateAgreementAddressExample example);

    int deleteByExample(LnCompensateAgreementAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnCompensateAgreementAddress record);

    int insertSelective(LnCompensateAgreementAddress record);

    List<LnCompensateAgreementAddress> selectByExample(LnCompensateAgreementAddressExample example);

    LnCompensateAgreementAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnCompensateAgreementAddress record, @Param("example") LnCompensateAgreementAddressExample example);

    int updateByExample(@Param("record") LnCompensateAgreementAddress record, @Param("example") LnCompensateAgreementAddressExample example);

    int updateByPrimaryKeySelective(LnCompensateAgreementAddress record);

    int updateByPrimaryKey(LnCompensateAgreementAddress record);

	List<String> selectCompenAgreeAddressList(@Param("partnerCode") String partnerCode,
			@Param("agreementType") String agreementType,@Param("partnerLoanId") String partnerLoanId);

    /**
     * 查询代偿协议的生成时间
     * @param partnerCode
     * @param loanId
     * @param orderNo
     * @return
     */
    LnCompensateAgreementAddress selectCompensateTime(@Param("partnerCode") String partnerCode,
                         @Param("loanId") String loanId,@Param("orderNo") String orderNo);

    /**
     * 查询某种代偿协议是否存在 适用于债权转让通知书
     * @param compensateId 代偿id
     * @param partnerLoanId 合作方借款编号
     * @param agreementType 代偿协议类型
     * @return
     */
    List<LnCompensateAgreementAddress> selectCompensateAgreementList(@Param("compensateId") Integer compensateId,
                                               @Param("partnerLoanId") String partnerLoanId, @Param("agreementType") String agreementType);

    /**
     * 查询代偿-债权转让协议是否全部生成
     * @param compensateId 代偿id
     * @param partnerLoanId 合作方借款编号
     * @param agreementType 代偿协议类型
     * @return
     */
    List<LnCompensateAgreementAddress> selectCompensateDebtTransferAgreementList(@Param("compensateId") Integer compensateId,
                                              @Param("partnerLoanId") String partnerLoanId, @Param("agreementType") String agreementType);

    /**
     * 查询某种代偿协议是否存在 适用于收款确认函（服务费）、收款确认函（债转）
     * @param compensateId 代偿id
     * @param agreementType 代偿协议类型
     * @return
     */
    List<LnCompensateAgreementAddress> selectCompensateServiceFeeTransList(@Param("compensateId") Integer compensateId,
                                                                     @Param("agreementType") String agreementType);
    /**
     * 查询云贷待通知下载签章协议的合作方借款ID（代偿确认函，代偿通知书）
     * @param startTime
     * @param endTime
     * @return
     */
    List<LnCompensateAgreementAddress> selectWaitNotifyDownloadAgreement(@Param("maxId") Integer maxId, @Param("selectNum") Integer selectNum, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
}