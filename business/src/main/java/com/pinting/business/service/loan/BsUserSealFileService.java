package com.pinting.business.service.loan;

import com.pinting.business.model.BsUserSealFile;

/**
 * Author:      cyb
 * Date:        2017/2/9
 * Description: 用户协议文件记录服务
 */
public interface BsUserSealFileService {

    /**
     * 用户协议文件下载
     * @param agreementUrl  协议下载地址
     * @param id            协议编号
     */
    String downloadSealFile(String agreementUrl, Integer id);

    /**
     * 根据合作方借款编号loan_id,用户来源查询四方借款协议编号
     * @param partnerLoanId 合作方借款编号
     * @param userSrc 用户来源
     * @return
     */
    BsUserSealFile queryAgreementNo(String partnerLoanId, String userSrc);
    
    /**
     * 用户协议文件下载
     * @param agreementUrl  协议下载地址
     * @param id            协议编号
     * @param partnerCode   合作方编号
     */
    String downloadPartnerSealFile(String agreementUrl, Integer id, String partnerCode);
    
}
