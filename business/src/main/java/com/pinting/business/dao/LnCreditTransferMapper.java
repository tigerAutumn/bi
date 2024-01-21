package com.pinting.business.dao;

import com.pinting.business.model.LnCreditTransfer;
import com.pinting.business.model.LnCreditTransferExample;
import com.pinting.business.model.vo.BsLoanRelationTransferVO;
import com.pinting.business.model.vo.LnCreditTransferMQueryVO;
import com.pinting.business.model.vo.LnCreditTransferMVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnCreditTransferMapper {
    int countByExample(LnCreditTransferExample example);

    int deleteByExample(LnCreditTransferExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnCreditTransfer record);

    int insertSelective(LnCreditTransfer record);

    List<LnCreditTransfer> selectByExample(LnCreditTransferExample example);

    LnCreditTransfer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnCreditTransfer record, @Param("example") LnCreditTransferExample example);

    int updateByExample(@Param("record") LnCreditTransfer record, @Param("example") LnCreditTransferExample example);

    int updateByPrimaryKeySelective(LnCreditTransfer record);

    int updateByPrimaryKey(LnCreditTransfer record);

    /**
     * 根据 受让人借贷关系编号查询债权转让数据
     * @param inLoanRelationId
     * @return
     */
    BsLoanRelationTransferVO selectVOByLoanRelationId(@Param("inLoanRelationId")Integer inLoanRelationId);

    /**
     * 根据 受让人借贷关系编号查询债权转让数据
     * @param inLoanRelationId
     * @return
     */
    BsLoanRelationTransferVO selectVOByLoanRelationIdNew(@Param("inLoanRelationId")Integer inLoanRelationId);
    
    
    /**
     * 管理台-债权转让统计-列表查询
     * @param queryVO
     * @return
     */
    List<LnCreditTransferMVO> selectLnCreditTransferVO4Manage(LnCreditTransferMQueryVO queryVO);
    
    /**
     * 管理台-债权转让统计-总条数查询
     * @param queryVO
     * @return
     */
    int countLnCreditTransferVO4Manage(LnCreditTransferMQueryVO queryVO);
    
    /**
	 * 管理台-债权转让统计-债转本金总额
	 * @return
	 */
	Double transSumPrincipal(LnCreditTransferMQueryVO queryVO);
	
	/**
	 * 管理台-债权转让统计-投资客户总利息
	 * @return
	 */
	Double transSumInterest(LnCreditTransferMQueryVO queryVO);
	
	
	 /**
     * 管理台-债转支付VIP-列表查询
     * @param queryVO
     * @return
     */
    List<LnCreditTransferMVO> selectLnCreditTransferVO2VIP(LnCreditTransferMQueryVO queryVO);
    
    /**
     * 管理台-债转支付VIP-总条数查询
     * @param queryVO
     * @return
     */
    int countLnCreditTransferVO2VIP(LnCreditTransferMQueryVO queryVO);
    
    /**
	 * 管理台-债转支付VIP-应付本息总额
	 * @return
	 */
	Double transSumAmount2VIP(LnCreditTransferMQueryVO queryVO);

    /**
     * 存管港湾产品-债权转让协议 根据借贷关系编号查询债权转让数据
     * @param lnLoanRelationId
     * @return
     */
    BsLoanRelationTransferVO selectCustodyTransferClaims(@Param("lnLoanRelationId") Integer lnLoanRelationId);
    

    /**
     * 管理台-债转支付 存管后，云贷（结束日期在11月7日及之前）-列表查询
     * @param queryVO
     * @return
     */
    List<LnCreditTransferMVO> selectLnCreditTransferPayYun(LnCreditTransferMQueryVO queryVO);
   
    
    /**
	 * 管理台-债转支付  存管后，云贷（结束日期在11月7日及之前）
	 * -应付客户利息总额
	 * -应付公司手续费总额
	 * @return
	 */
    LnCreditTransferMVO transSumAmountYun(LnCreditTransferMQueryVO queryVO);
	
	
    /**
     * 管理台-债转支付 存管后，云贷/赞时贷-列表查询
     * @param queryVO
     * @return
     */
    List<LnCreditTransferMVO> selectLnCreditTransferPayYunZSD(LnCreditTransferMQueryVO queryVO);
    
    /**
     * 管理台-债转支付 存管后，云贷/赞时贷-总条数查询
     * @param queryVO
     * @return
     */
    int countLnCreditTransferPayYunZSD(LnCreditTransferMQueryVO queryVO);
    
    /**
	 * 管理台-债转支付  存管后，云贷/赞时贷
	 * -应付客户利息总额
	 * -应付公司手续费总额
	 * @return
	 */
    LnCreditTransferMVO transSumAmountYunZSD(LnCreditTransferMQueryVO queryVO);

    LnCreditTransfer selectLatestByLoanId(@Param("loanId") Integer loanId);
}