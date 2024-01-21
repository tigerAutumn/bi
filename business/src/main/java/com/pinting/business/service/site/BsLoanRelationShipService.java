package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.model.vo.BsLoanRelationTransferVO;
import com.pinting.business.model.vo.LoanDetailInfoVO;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransferInfo;

/**
 * 债权关系相关
 * @author bianyatian
 * @2016-9-21 上午10:56:29
 */
public interface BsLoanRelationShipService {

	/**
	 * 债权转让协议需要信息获取
	 * @param inLoanRelationId 受让人借贷关系编号，必填
	 * @return
	 */
	BsLoanRelationTransferVO getLoanTransferInfos(Integer inLoanRelationId);

	/**
	 * 债权转让协议需要信息获取
	 * @param inLoanRelationId 受让人借贷关系编号，必填
	 * @return
	 */
	BsLoanRelationTransferVO getLoanTransferInfoNew(Integer inLoanRelationId);
	
	/**
	 * 根据 合作方借款编号 查询借款信息-借款协议数据准备
	 * @param partnerLoanId 合作方借款编号
	 * @return
	 */
	LoanDetailInfoVO getLoanDetailInfoVO(String partnerLoanId);
	
	/**
	 * 根据借款编号查询债转数据 代偿债权转让通知书数据准备（云贷、7贷）
	 * @param loanId 合作方借款编号
	 * @return
	 */
	List<DebtTransferInfo> queryTransferInfo(String loanId);

	/**
	 * 代偿协议 根据用户来源、签章类型、借款编号 查询债权转让协议的下载地址
	 * @param partnerCode 用户来源    look over class PartnerEnum
	 * @param agreementType 协议类型    look over class SealBusiness
	 * @param partnerLoanId 合作方借款编号
     * @return
     */
	List<String> queryCompenAgreeAddressList(String partnerCode, String agreementType, String partnerLoanId);

	/**
	 * 根据 合作方借款编号 查询借款信息-赞时贷APP借款协议数据准备
	 * @param partnerLoanId 合作方借款编号
	 * @return
	 */
	LoanDetailInfoVO getZsdLoanDetailInfoVO(String partnerLoanId);

	/**
	 * 根据 合作方借款编号 查询借款信息-云贷三方借款协议数据准备
	 * @param partnerLoanId 合作方借款编号
	 * @return
	 */
	LoanDetailInfoVO getYunLoanDetailInfoVO(String partnerLoanId);
	
	/**
	 * 根据 合作方借款编号 查询借款信息-七贷三方借款协议数据准备
	 * @param partnerLoanId 合作方借款编号
	 * @return
	 */
	LoanDetailInfoVO getSevenLoanDetailInfoVO(String partnerLoanId);
	
	/**
	 * 债权转让协议需要信息获取-赞分期债权转让协议签章 生成pdf数据准备
	 * @param inLoanRelationId 受让人借贷关系编号，必填
	 * @return
	 */
	BsLoanRelationTransferVO getLoanTransferInfoNewForPdf(Integer inLoanRelationId);
}
