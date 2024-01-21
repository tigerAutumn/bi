package com.pinting.business.accounting.service;

import java.util.List;

import com.pinting.business.model.BsFileSealRelation;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.BsUserSignSeal;
import com.pinting.business.model.vo.BsUser4LoanVO;
import com.pinting.business.model.vo.SignSeal4PdfInfoVO;
import com.pinting.business.model.vo.UserSealFileVO;
import com.pinting.business.model.vo.UserSealInfoVO;
import com.pinting.business.model.vo.UserSealResultVO;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SignSealService.java, v 0.1 2015-9-17 上午9:44:12 BabyShark Exp $
 */
public interface SignSealService {

    /**
     * 查询用户签章信息
     * @param signSeal
     * @return
     */
    public BsUserSignSeal findUserSignSeal (BsUserSignSeal signSeal);

    /**
     * 新增用户签章信息
     * @param signSeal
     */
    public void addUserSeal(BsUserSignSeal signSeal);

    /**
     * 判断是否需要制章：
     * 若需要则进行制章（证书生成、制章）并返回印章信息
     * 若已有章，则不需要制章，返回印章信息
     * @param req
     * @return 成功（有章或制章成功)则isSucc返回true，否则返回false
     */
    public UserSealResultVO checkAndMakeSeal(UserSealInfoVO req);

    /**
     * 根据关键字对pdf文件签章
     * 
     * @param req
     * @return
     */
    public boolean signSeal4PdfByKeyword(SignSeal4PdfInfoVO req);
    
    /**
     * 用户签章文件记录
     * 
     * @param req
     */
    void addUserSealFile(UserSealFileVO req);
    
    /**
     * 根据子账户ID检查用户产品的资金接收方
     * @param subAccountId
     * @return
     */
    String checkPropertySymbol(Integer subAccountId);
    
    
    /**
     * 用户签章文件记录更新
     * 
     * @param req
     */
    void updateUserSealFile(UserSealFileVO req);
    /**
     * 根据借款协议编号查询签章信息
     * @param agreementNo 借款协议编号
     * @return
     */
	public BsUserSealFile querySealFileInfoByAgreementNo(String agreementNo);

    /**
     * 根据借款协议编号查询初始化的签章信息
     * @param agreementNo 借款协议编号
     * @return
     */
    public BsUserSealFile queryInitByAgreementNo(String agreementNo);

	/**
	 * 根据借款id查询出借人信息-借款协议出借人
	 * @param loanId
	 * @return
	 */
	public List<BsUser4LoanVO> selectUserListByLoanId(Integer loanId);
	
	/**
	 * 根据用户签章文件记录信息查询签章需要的信息
	 * @param bsUserSealFile 用户签章文件记录
	 * @return
	 */
	public SignSeal4PdfInfoVO getSignSeal(BsUserSealFile bsUserSealFile,BsFileSealRelation bsFileSealRelation);
    
    
}
